package com.minagri.stats.vaadin.component.grid;

import com.minagri.stats.core.java.Collections;
import com.minagri.stats.core.jaxrs.entity.SortOrder;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class LCMGridLazyDataProvider<VALUECLASS> extends AbstractBackEndDataProvider<VALUECLASS, Void> {
    private LCMGrid<VALUECLASS> grid;
    private Supplier<Integer> countFunction;
    private Function<LCMGridPage, List<VALUECLASS>> itemFunction;

    private Predicate<VALUECLASS> selectionTriggerFunction;

    public LCMGridLazyDataProvider(LCMGrid<VALUECLASS> grid, Supplier<Integer> countFunction, Function<LCMGridPage, List<VALUECLASS>> itemFunction) {
        this.grid = grid;
        this.countFunction = countFunction;
        this.itemFunction = itemFunction;
    }

    @Override
    protected Stream<VALUECLASS> fetchFromBackEnd(Query<VALUECLASS, Void> query) {
        List<SortOrder> sortOrders = Collections.map(query.getSortOrders(), querySortOrder -> SortOrder.builder()
                .property(querySortOrder.getSorted())
                .direction(querySortOrder.getDirection() == SortDirection.DESCENDING ? SortOrder.Direction.DESC : SortOrder.Direction.ASC)
                .build());

        LCMGridPage lcmGridPage = LCMGridPage.builder()
                .offset(query.getOffset())
                .limit(query.getLimit())
                .sortOrders(sortOrders)
                .sortParameter(SortOrder.mapToSortParameter(sortOrders))
                .build();

        List<VALUECLASS> items = itemFunction.apply(lcmGridPage);

        if (selectionTriggerFunction != null) {
            Collections.filterFirstOptional(items, selectionTriggerFunction).ifPresent(item -> {
                grid.select(item);
                selectionTriggerFunction = null;
            });
        }

        return items.stream();
    }

    @Override
    protected int sizeInBackEnd(Query<VALUECLASS, Void> query) {
        Integer count = countFunction.get();

        if (count == 0) {
            grid.clearFooterItemCountLabel();
            grid.showEmptyStateText();
        } else {
            grid.setTotalResultsFooterItemCountLabel(count);
            grid.hideEmptyStateText();
        }

        return count;
    }

    public void triggerSelection(Predicate<VALUECLASS> selectionTriggerFunction) {
        this.selectionTriggerFunction = selectionTriggerFunction;
    }
}
