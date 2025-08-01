package com.minagri.stats.core.jaxrs.entity;

import com.minagri.stats.core.java.Strings;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class SortOrder {
    public enum Direction {
        ASC, DESC
    }

    private String property;
    private Direction direction;

    public static List<SortOrder> mapFromSortParameter(String sortParameter) {
        if (Strings.isEmpty(sortParameter)) {
            return new ArrayList<>();
        }

        List<String> unparsedSortOrders = Stream.of(sortParameter.split(";", -1)).toList();
        List<SortOrder> sortOrders = new ArrayList<>();
        for (String unparsedSortOrder : unparsedSortOrders) {
            int index = unparsedSortOrder.indexOf(":");
            index = index > 0 ? index : unparsedSortOrder.length();
            String property = unparsedSortOrder.substring(0, index);
            String sorting = unparsedSortOrder.substring(Math.min(index + 1, unparsedSortOrder.length()));
            sortOrders.add(SortOrder.builder()
                    .property(property)
                    .direction(sorting.equalsIgnoreCase("DESC") ? Direction.DESC : Direction.ASC)
                    .build());
        }
        return sortOrders;
    }

    public static String mapToSortParameter(List<SortOrder> sortOrders) {
        if (sortOrders == null || sortOrders.isEmpty()) {
            return null;
        }

        return sortOrders.stream()
                .map(sortOrder -> {
                    if (sortOrder.getDirection() == null) {
                        return sortOrder.getProperty();
                    }
                    return sortOrder.getProperty() + ":" + sortOrder.getDirection().name();
                })
                .collect(Collectors.joining(";"));
    }

    public static List<SortOrder> mapProperties(List<SortOrder> sortOrders, Map<String, String> propertyMap) {
        return sortOrders.stream()
                .map(sortOrder -> SortOrder.builder()
                        .property(propertyMap.getOrDefault(sortOrder.getProperty(), sortOrder.getProperty()))
                        .direction(sortOrder.getDirection())
                        .build())
                .collect(Collectors.toList());
    }
}
