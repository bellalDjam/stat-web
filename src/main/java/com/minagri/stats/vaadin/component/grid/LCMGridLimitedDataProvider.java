package com.minagri.stats.vaadin.component.grid;

import com.minagri.stats.core.java.Collections;
import com.minagri.stats.core.java.Strings;
import com.minagri.stats.core.jaxrs.entity.SortOrder;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class LCMGridLimitedDataProvider<VALUECLASS> extends AbstractBackEndDataProvider<VALUECLASS, Void> {
    private LCMGrid<VALUECLASS> grid;
    private Integer limit;
    private Supplier<Integer> countFunction;
    private Function<LCMGridPage, List<VALUECLASS>> itemFunction;
    
    private String currentSortParameter;
    private List<VALUECLASS> items;

    public LCMGridLimitedDataProvider(LCMGrid<VALUECLASS> grid, Integer limit, Supplier<Integer> countFunction, Function<LCMGridPage, List<VALUECLASS>> itemFunction) {
        this.grid = grid;
        this.limit = limit;
        this.countFunction = countFunction;
        this.itemFunction = itemFunction;
    }

    @Override
    protected Stream<VALUECLASS> fetchFromBackEnd(Query<VALUECLASS, Void> query) {
        // when select all checkbox is clicked, the limit is set to Integer.MAX_VALUE
        if (query.getLimit() == Integer.MAX_VALUE) {
            return items != null ? items.stream() : Stream.empty();
        }
        
        List<SortOrder> sortOrders = Collections.map(query.getSortOrders(), querySortOrder -> SortOrder.builder()
                .property(querySortOrder.getSorted())
                .direction(querySortOrder.getDirection() == SortDirection.DESCENDING ? SortOrder.Direction.DESC : SortOrder.Direction.ASC)
                .build());

        String sortParameter = SortOrder.mapToSortParameter(sortOrders);
        
        if (items == null || Strings.notEqualTo(sortParameter, currentSortParameter)) {
            LCMGridPage gridPage = LCMGridPage.builder()
                    .offset(0)
                    .limit(limit)
                    .sortOrders(sortOrders)
                    .sortParameter(sortParameter)
                    .build();
            
            items = itemFunction.apply(gridPage);
            currentSortParameter = sortParameter;
        }
        
        return items.subList(query.getOffset(), Math.min(query.getOffset() + query.getLimit(), items.size())).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<VALUECLASS, Void> query) {
        Integer count = countFunction.get();

        if (count == 0) {
            grid.clearFooterItemCountLabel();
            grid.showEmptyStateText();
        } else if (count <= limit) {
            grid.setTotalResultsFooterItemCountLabel(count);
            grid.hideEmptyStateText();
        } else {
            grid.setLimitedResultsFooterItemCountLabel(limit, count);
            grid.hideEmptyStateText();
        }

        return Math.min(limit, count);
    }
}
