package com.minagri.stats.core.java;

import java.math.BigDecimal;
import java.util.*;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Collections {

    static <T, P> T getOptionalOnlyElementByProperty(Collection<T> collection, Function<T, P> propertyMapper, P property) {
        List<T> matchingElements = collection.stream().filter(element -> {
            P elementProperty = propertyMapper.apply(element);
            return Objects.equals(elementProperty, property);
        }).toList();

        if (matchingElements.isEmpty()) {
            return null;
        }

        if (matchingElements.size() > 1) {
            throw new RuntimeException("multiple elements with property " + property);
        }

        return matchingElements.get(0);
    }

    static <T, P> T getOnlyElementByProperty(Collection<T> collection, Function<T, P> propertyMapper, P property) {
        T optionalOnlyElement = getOptionalOnlyElementByProperty(collection, propertyMapper, property);
        if (optionalOnlyElement == null) {
            throw new RuntimeException("no element with property " + property);
        }
        return optionalOnlyElement;
    }

    static <T, P> boolean hasMultipleElementsWithProperty(Collection<T> collection, Function<T, P> propertyMapper, P property) {
        List<T> matchingElements = collection.stream().filter(element -> {
            P elementProperty = propertyMapper.apply(element);
            return Objects.equals(elementProperty, property);
        }).toList();

        return matchingElements.size() > 1;
    }

    static <T, P> boolean hasElementsWithProperty(Collection<T> collection, Function<T, P> propertyMapper, P property) {
        List<T> matchingElements = collection.stream().filter(element -> {
            P elementProperty = propertyMapper.apply(element);
            return Objects.equals(elementProperty, property);
        }).toList();

        return !matchingElements.isEmpty();
    }

    static <T extends Comparable<? super T>> List<T> getSortedList(Collection<T> collection) {
        return getSortedList(collection, Comparator.naturalOrder());
    }

    static <T> List<T> getSortedList(Collection<T> collection, Comparator<T> comparator) {
        return collection.stream().sorted(comparator).toList();
    }

    @SafeVarargs
    static <T, P extends Comparable<? super P>> List<T> getSortedList(Collection<T> collection, Function<T, P> propertyGetter, Function<T, P>... otherPropertyGetters) {
        Comparator<T> comparator = Comparator.comparing(propertyGetter);
        for (Function<T, P> otherPropertyGetter : otherPropertyGetters) {
            comparator = comparator.thenComparing(otherPropertyGetter);
        }
        return collection.stream().sorted(comparator).toList();
    }

    static <T, P extends Comparable<? super P>> List<T> getReverseSortedList(Collection<T> collection, Function<T, P> propertyGetter) {
        return collection.stream().sorted(Comparator.comparing(propertyGetter).reversed()).toList();
    }

    @SafeVarargs
    static <T, P extends Comparable<? super P>> void sort(List<T> list, Function<T, P> propertyGetter, Function<T, P>... otherPropertyGetters) {
        Comparator<T> comparator = Comparator.comparing(propertyGetter);
        for (Function<T, P> otherPropertyGetter : otherPropertyGetters) {
            comparator = comparator.thenComparing(otherPropertyGetter);
        }
        list.sort(comparator);
    }

    static <T> Stream<T> safeStream(Collection<T> collection) {
        if (collection != null) {
            return collection.stream();
        }
        return Stream.<T>builder().build();
    }

    static <T, R> Stream<R> safeMap(Collection<T> collection, Function<T, R> mapper) {
        return safeStream(collection).map(mapper).filter(Objects::nonNull);
    }

    static <T, R> T filterOnlyElementByProperty(Collection<T> collection, Function<T, R> propertyGetter, R property) {
        return filterOnlyElement(collection, v -> {
            R valueProperty = propertyGetter.apply(v);
            return Objects.equals(valueProperty, property);
        });
    }

    static <T, R> Optional<T> filterOnlyElementByPropertyOptional(Collection<T> collection, Function<T, R> propertyGetter, R property) {
        return filterOnlyElementOptional(collection, v -> {
            R valueProperty = propertyGetter.apply(v);
            return Objects.equals(valueProperty, property);
        });
    }

    static <T> T filterOnlyElement(Collection<T> collection, Predicate<T> filter) {
        List<T> filteredList = filter(collection, filter);
        if (filteredList.size() != 1) {
            throw new RuntimeException("At least one item expected");
        }
        return filteredList.get(0);
    }

    static <T> T filterOnlyOrNullElement(Collection<T> collection, Predicate<T> filter) {
        List<T> filteredList = filter(collection, filter);
        if (filteredList.size() > 1) {
            throw new RuntimeException("Maximum one item expected");
        }
        return getFirstOrNull(filteredList);
    }

    static <T> Optional<T> filterOnlyElementOptional(Collection<T> collection, Predicate<T> filter) {
        List<T> filteredList = filter(collection, filter);
        if (filteredList.size() > 1) {
            throw new RuntimeException("At least one item expected");
        }
        if (filteredList.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(filteredList.get(0));
    }

    static <T> List<T> filter(Collection<T> collection, Predicate<T> filter) {
        return collection.stream().filter(filter).toList();
    }

    static <K, V> Map<K, V> filter(Map<K, V> map, BiPredicate<K, V> filter) {
        Map<K, V> filteredMap = new HashMap<>();
        map.forEach((k, v) -> {
            if (filter.test(k, v)) {
                filteredMap.put(k, v);
            }
        });
        return filteredMap;
    }

    static <K, V> List<V> filterValues(Map<K, V> map, BiPredicate<K, V> filter) {
        return new ArrayList<>(filter(map, filter).values());
    }

    static <T, P> List<T> filterByProperty(Collection<T> collection, Function<T, P> propertyMapper, P property) {
        return filter(collection, item -> Objects.equals(propertyMapper.apply(item), property));
    }

    static <T, P> List<T> filterByPropertyNotEqual(Collection<T> collection, Function<T, P> propertyMapper, P property) {
        return filter(collection, item -> !Objects.equals(propertyMapper.apply(item), property));
    }

    static <T> Optional<T> filterFirstOptional(Collection<T> collection, Predicate<T> filter) {
        return getFirstOptional(filter(collection, filter));
    }

    static <T, R> List<R> map(T[] collection, Function<T, R> mapper) {
        return map(Arrays.asList(collection), mapper);
    }

    static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    static <T, R> Set<R> mapDistinct(Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }

    static <T, R> R mapFirst(Collection<T> collection, Function<T, R> mapper) {
        if (collection != null && !collection.isEmpty()) {
            return mapper.apply(collection.iterator().next());
        }
        return null;
    }

    static <T, R> Optional<R> mapFirstOptional(Collection<T> collection, Function<T, R> mapper) {
        return Optional.ofNullable(mapFirst(collection, mapper));
    }

    static boolean notNullOrEmpty(Collection<?> list) {
        return !nullOrEmpty(list);
    }

    static boolean nullOrEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }


    static <T> T getFirst(Collection<T> list) {
        if (list.isEmpty()) {
            throw new RuntimeException("At least one item expected");
        }
        return list.iterator().next();
    }

    static <T> T getFirstOrNull(Collection<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.iterator().next();
    }

    static <T> Optional<T> getFirstOptional(Collection<T> list) {
        return Optional.ofNullable(getFirstOrNull(list));
    }

    static <T> T getLast(Collection<T> list) {
        return list.stream().reduce((a, b) -> b).orElseThrow();
    }

    static <T> T getLastOrNull(Collection<T> list) {
        return list.stream().reduce((a, b) -> b).orElse(null);
    }

    static <T> Optional<T> getLastOptional(Collection<T> list) {
        return Optional.ofNullable(getLastOrNull(list));
    }

    static <T> T getOnlyElement(Collection<T> collection) {
        if (collection == null || collection.size() != 1) {
            throw new RuntimeException("Exactly one item expected: " + collection);
        }
        return getFirst(collection);
    }

    static <T> List<List<T>> partition(List<T> list, int partitionSize) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += partitionSize) {
            partitions.add(list.subList(i, Math.min(i + partitionSize, list.size())));
        }

        return partitions;
    }

    static <T> T getAtIndex(List<T> list, int index) {
        if (list == null || list.size() < index + 1) {
            return null;
        }
        return list.get(index);
    }

    static <T> Optional<T> getAtIndexOptional(List<T> list, int index) {
        return Optional.ofNullable(getAtIndex(list, index));
    }

    @SuppressWarnings("unchecked")
    static <T, P extends Comparable<C>, C> P getMinProperty(Collection<T> collection, Function<T, P> propertyGetter, P defaultValue) {
        List<P> properties = map(collection, propertyGetter);
        return properties.stream().filter(Objects::nonNull).min((p1, p2) -> p1.compareTo((C) p2)).orElse(defaultValue);
    }

    static <T, P extends Comparable<C>, C> P getMinProperty(Collection<T> collection, Function<T, P> propertyGetter) {
        return getMinProperty(collection, propertyGetter, null);
    }

    @SuppressWarnings("unchecked")
    static <T, P extends Comparable<C>, C> P getMaxProperty(Collection<T> collection, Function<T, P> propertyGetter, P defaultValue) {
        List<P> properties = map(collection, propertyGetter);
        return properties.stream().filter(Objects::nonNull).max((p1, p2) -> p1.compareTo((C) p2)).orElse(defaultValue);
    }

    static <T, P extends Comparable<C>, C> P getMaxProperty(Collection<T> collection, Function<T, P> propertyGetter) {
        return getMaxProperty(collection, propertyGetter, null);
    }

    static <T> BigDecimal sumBigDecimalProperty(Collection<T> collection, Function<T, BigDecimal> propertyGetter) {
        List<BigDecimal> properties = map(collection, propertyGetter);
        return BigDecimals.add(properties);
    }

    static <T> Long sumLongProperty(Collection<T> collection, Function<T, Long> propertyGetter) {
        List<Long> properties = map(collection, propertyGetter);
        return Longs.sum(properties);
    }

    static <T> Integer sumIntegerProperty(Collection<T> collection, Function<T, Integer> propertyGetter) {
        List<Integer> properties = map(collection, propertyGetter);
        return Integers.sum(properties);
    }

    @SafeVarargs
    static <T> List<List<T>> groupBy(Collection<T> collection, Function<T, ?>... groupByFunctions) {
        Map<List<Object>, List<T>> groupsByKeys = new LinkedHashMap<>();

        for (T element : collection) {
            List<Object> elementKeys = new ArrayList<>();
            for (Function<T, ?> groupByFunction : groupByFunctions) {
                elementKeys.add(groupByFunction.apply(element));
            }

            if (groupsByKeys.containsKey(elementKeys)) {
                groupsByKeys.get(elementKeys).add(element);
            } else {
                List<T> newGroup = new ArrayList<>();
                newGroup.add(element);
                groupsByKeys.put(elementKeys, newGroup);
            }
        }

        return new ArrayList<>(groupsByKeys.values());
    }

    static <T, R> List<R> safeMapToList(Collection<T> collection, Function<T, R> mapper) {
        return safeMap(collection, mapper).toList();
    }

    static <T, R> List<R> mapToList(Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).toList();
    }

    @SafeVarargs
    static <T> List<T> concat(Collection<T>... collections) {
        List<T> concatCollection = new ArrayList<>();
        for (Collection<T> collection : collections) {
            concatCollection.addAll(collection);
        }
        return concatCollection;
    }

    static <T> Set<T> distinct(Collection<T>... collections) {
        Set<T> distinctCollection = new LinkedHashSet<>();
        for (Collection<T> collection : collections) {
            distinctCollection.addAll(collection);
        }
        return distinctCollection;
    }

    @SuppressWarnings("unchecked")
    static <T, S extends T> List<S> cast(List<T> list) {
        return list.stream().map(item -> (S) item).collect(Collectors.toList());
    }

    /**
     * Checks if the given collection contains the given value.
     */
    static <T> boolean contains(Collection<T> collection, T value) {
        return collection != null && collection.contains(value);
    }

    /**
     * Checks if the given array contains the given value.
     */
    static <T> boolean contains(T[] array, T value) {
        List<T> list = Arrays.asList(array);
        return contains(list, value);
    }

    static <T> boolean containsAny(Collection<T> collection, Collection<T> values) {
        return collection != null && values != null && notEmptyAndAnyMatch(collection, values::contains);
    }

    static <T> boolean containsNone(Collection<T> collection, Collection<T> values) {
        return collection != null && values != null && emptyOrNoneMatch(collection, values::contains);
    }

    static <T> boolean notEmptyAndAllMatch(Collection<T> collection, Predicate<T> predicate) {
        return !collection.isEmpty() && collection.stream().allMatch(predicate);
    }

    static <T> boolean notEmptyAndAnyMatch(Collection<T> collection, Predicate<T> predicate) {
        return !collection.isEmpty() && collection.stream().anyMatch(predicate);
    }

    static <T> boolean notEmptyAndNoneMatch(Collection<T> collection, Predicate<T> predicate) {
        return !collection.isEmpty() && collection.stream().noneMatch(predicate);
    }

    static <T> boolean emptyOrAllMatch(Collection<T> collection, Predicate<T> predicate) {
        return collection.isEmpty() || collection.stream().allMatch(predicate);
    }

    static <T> boolean emptyOrAnyMatch(Collection<T> collection, Predicate<T> predicate) {
        return collection.isEmpty() || collection.stream().anyMatch(predicate);
    }

    static <T> boolean emptyOrNoneMatch(Collection<T> collection, Predicate<T> predicate) {
        return collection.isEmpty() || collection.stream().noneMatch(predicate);
    }

    static <T, P> void forEach(Collection<T> collection, BiConsumer<T, P> itemFunction, P parameter) {
        collection.forEach(item -> itemFunction.accept(item, parameter));
    }
}
