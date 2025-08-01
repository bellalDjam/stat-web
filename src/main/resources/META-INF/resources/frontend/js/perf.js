PerformanceCollector = {
    //Since multiple flows could be executed at the same time, each mark will be assigned to a group(flow) if specified
    // if not specified, the default group will be used
    markGroups : [],
    //window.performance object alias (since it depends on the browser)
    performance : null,
    DEFAULT_GROUP: 'DefaultGroup',
    init : function() {
        if(this.performance) {
            //already initialized
            return;
        }

        this.performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance;
        if(! this.performance) {
            console.warn('Performance API not supported by this browser');
            return;
        }
        //adding the listener on the body element allows to listen to event triggered by elements not yet in the DOM.
        document.getElementsByTagName('body')[0].addEventListener('click', e => this.triggerStartMarkOnClick(e));
    },

    triggerStartMarkOnClick: function(event) {
        if(event.target.attributes['data-perfMark']){
            var markName = event.target.attributes['data-perfMark']?.value;
            var groupName = event.target.attributes['data-perfGroup']?.value;
            this.mark(markName, groupName);
        }
    },

    triggerStartMarkOnKeydown: function(keys, markName, groupName) {
        document.getElementsByTagName('body')[0].addEventListener('keydown', event => {
            if(keys.includes(event.key)) {
                this.mark(markName, groupName);
            }
        });
    },

    mark: function(markName, groupName) {
        var group = this.getOrCreateMarkGroup(groupName);

        //If no mark name is given, or if the mark already exists ==> return (avoid double click metrics)
        if(!markName || group.marks.indexOf(markName) >= 0) {
            return;
        }

        group.marks.push(markName);
        this.performance.mark(this.getFullMarkName(group, markName));
    },

    /**
     * 1) if not initialized : return '[]'
     * 2) getGroup, if no groupName : use default
     * 3) add a mark as "now"
     * 4) get all matching performance marks in group.
     * 5) get all performance entries grouped by marks
     * 6) unregister all group's performance marks (to clean the context for subsequent calls)
     * 7) remove "now" mark and its entries (not needed anymore)
     * 8) compute metrics per mark
     * 9) return;
     * */
    collect: function(groupName) {
        //1
        if(!this.performance) {
            return '[]';
        }

        var performanceMarks = [];
        var performanceEntriesByMarks = [];
        var nowMarkName = 'NOW';

        //2
        var group = this.getOrCreateMarkGroup(groupName);

        //3
        this.mark(nowMarkName, group.name);
        //4
        // in some cases, previous marks are not kept by the performance api (page reload ?), need to ignore them : -> filter.
        performanceMarks = this.getPerformanceMarkNames(group)
            .map(n => this.performance.getEntriesByName(n)[0])
            .filter(v => typeof v !== 'undefined');
        //5
        performanceEntriesByMarks = this.groupPerformanceEntriesByMark(performanceMarks);
        //6
        this.clearEntriesInGroup(group);
        //7
        performanceEntriesByMarks = performanceEntriesByMarks.filter(item => item.performanceMark.name !== this.getFullMarkName(group, nowMarkName));
        //8 + 9
        return JSON.stringify(this.computePerformanceForMarks(performanceEntriesByMarks));
    },

    computePerformanceForMarks: function(performanceEntriesByMarks) {
        /*
        * [{
        *   performanceMark: PerformanceMark,
        *   entries: PerformanceResourceTiming[]
        * }, ...]
        * */
        var result = [];
        performanceEntriesByMarks.forEach(markAndEntries => {
            result.push(this.computePerformanceForMark(markAndEntries));
        });

        return result;
    },

    computePerformanceForMark: function(markAndEntries) {
        let timingsSum = null;
        markAndEntries.entries.forEach(performanceEntry => {
            timingsSum = this.computePerformance(performanceEntry, timingsSum);
        });

        return {
            flowName: this.getGroupNameFromFullName(markAndEntries.performanceMark.name),
            actionName: this.getMarkNameFromFullName(markAndEntries.performanceMark.name),
            metrics: timingsSum
        };
    },

    getOrCreateMarkGroup: function(groupName) {
        var nameToFind = groupName || this.DEFAULT_GROUP;
        var group = this.markGroups.find( group => group.name === nameToFind);

        if(!group) {
            group = {
                name : nameToFind,
                marks : []
            };
            this.markGroups.push(group);
        }
        return group;
    },

    getPerformanceMarkNames: function(group) {
        return group.marks.map(m => this.getFullMarkName(group, m));
    },

    groupPerformanceEntriesByMark: function(sortedPerformanceMarks) {
        //getting all performance entries since first mark
        const allEntries = this.performance.getEntriesByType("resource")
            .filter(e => e.startTime >= sortedPerformanceMarks[0].startTime && e.name.indexOf('heartbeat') === -1);
        //creating result structure
        const sortedPerformanceEntriesByMark = sortedPerformanceMarks.map(mark => {
            return {
                performanceMark: mark,
                entries: []
            }
        });
        allEntries.forEach(entry => this.addPerformanceEntryToMark(entry, sortedPerformanceEntriesByMark));
        return sortedPerformanceEntriesByMark;
    },

    addPerformanceEntryToMark: function(entry, sortedPerformanceEntriesByMark) {
        var mark;

        sortedPerformanceEntriesByMark.forEach(item => {
            //if entry happened after the current mark
            if(item.performanceMark.startTime <= entry.startTime) {
                //if the current mark happened after the "saved" mark, or no saved mark yet
                if(!mark || mark.performanceMark.startTime < item.performanceMark.startTime) {
                    if(!mark || item.performanceMark.name !== mark.performanceMark.name) {
                        item.entries.push(entry);
                    }
                    mark = item;
                }
            }
        });
    },

    clearEntriesInGroup: function(group) {
        group.marks.forEach(mark => this.performance.clearMarks(this.getFullMarkName(group, mark)));
        group.marks = [];
    },

    getFullMarkName: function(group, mark) {
        return group.name + '_' +mark;
    },

    getMarkNameFromFullName(fullName) {
        return fullName.substr(fullName.indexOf('_') +1);
    },

    getGroupNameFromFullName(fullName) {
        return fullName.substr(0,fullName.indexOf('_'));
    },


    computePerformance : function(performanceData, timingsSum) {
        // note: there might be negative values because of browser bugs see https://github.com/matomo-org/matomo/pull/16516 in this case we ignore the values
        // in that case, the sum is returned untouched.
        var timings = {
            networkTime : null,
            serverTime : null,
            transferTime : null,
            domProcessingTime : null,
            domCompletionTime : null,
            loadTime : null,
            duration : null,
        };

        if (performanceData.connectEnd && performanceData.fetchStart) {

            if (performanceData.connectEnd < performanceData.fetchStart) {
                return timingsSum;
            }

            timings.networkTime = Math.round(performanceData.connectEnd - performanceData.fetchStart);
        }

        if (performanceData.responseStart && performanceData.requestStart) {

            if (performanceData.responseStart < performanceData.requestStart) {
                return timingsSum;
            }

            timings.serverTime = Math.round(performanceData.responseStart - performanceData.requestStart);
        }

        if (performanceData.responseStart && performanceData.responseEnd) {

            if (performanceData.responseEnd < performanceData.responseStart) {
                return timingsSum;
            }

            timings.transferTime = Math.round(performanceData.responseEnd - performanceData.responseStart);
        }

        if (typeof performanceData.domLoading !== 'undefined') {
            if (performanceData.domInteractive && performanceData.domLoading) {

                if (performanceData.domInteractive < performanceData.domLoading) {
                    return timingsSum;
                }

                timings.domProcessingTime = Math.round(performanceData.domInteractive - performanceData.domLoading);
            }
        } else {
            if (performanceData.domInteractive && performanceData.responseEnd) {

                if (performanceData.domInteractive < performanceData.responseEnd) {
                    return timingsSum;
                }

                timings.domProcessingTime = Math.round(performanceData.domInteractive - performanceData.responseEnd);
            }
        }

        if (performanceData.domComplete && performanceData.domInteractive) {

            if (performanceData.domComplete < performanceData.domInteractive) {
                return timingsSum;
            }

            timings.domCompletionTime = Math.round(performanceData.domComplete - performanceData.domInteractive);
        }

        if (performanceData.loadEventEnd && performanceData.loadEventStart) {

            if (performanceData.loadEventEnd < performanceData.loadEventStart) {
                return timingsSum;
            }

            timings.loadTime = Math.round(performanceData.loadEventEnd - performanceData.loadEventStart);
        }

        timings.duration = performanceData.duration;

        //if no sum passed as argument (basically first iteration), timings are returned
        if(!timingsSum) {
            return timings;
        }

        //a sum was passed, summing the timings metrics and returning the sum.
        timingsSum.networkTime += timings.networkTime;
        timingsSum.serverTime += timings.serverTime;
        timingsSum.transferTime += timings.transferTime;
        timingsSum.domProcessingTime += timings.domProcessingTime;
        timingsSum.domCompletionTime += timings.domCompletionTime;
        timingsSum.loadTime += timings.loadTime;
        timingsSum.duration += timings.duration;

        return timingsSum;
    },

}
