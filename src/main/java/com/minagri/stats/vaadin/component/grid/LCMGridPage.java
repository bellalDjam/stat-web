package com.minagri.stats.vaadin.component.grid;

import com.minagri.stats.core.jaxrs.entity.SortOrder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LCMGridPage {
    private Integer limit;
    private Integer offset;
    private List<SortOrder> sortOrders;
    private String sortParameter;
}
