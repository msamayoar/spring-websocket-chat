package com.tempest.moonlight.server.util;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Yurii on 2015-06-11.
 */
public class CollectionsUtils extends CollectionUtils {

    public static <Key, Value> Collection<Value> filterMapValuesByKey(Map<Key, Value> map, Predicate<Key> keyPredicate) {
        return map.entrySet().parallelStream().filter(entry -> keyPredicate.test(entry.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public static <Key, Value> Collection<Value> filterMapValues(Map<Key, Value> map, Predicate<Map.Entry<Key, Value>> entryPredicate) {
        return map.entrySet().parallelStream().filter(entryPredicate).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public static <Key, Value> boolean mapKeysContain(Map<Key, Value> map, Predicate<Map.Entry<Key, Value>> predicate) {
        return map.entrySet().parallelStream().anyMatch(predicate);
    }

    public static <From, To> Set<To> convertToSet(Collection<From> fromCollection, Function<From, To> mapper) {
        return mapStream(fromCollection, mapper).collect(Collectors.toSet());
    }

    public static <From, To> List<To> convertToList(Collection<From> fromCollection, Function<From, To> mapper) {
        return mapStream(fromCollection, mapper).collect(Collectors.toList());
    }

    private static <From, To> Stream<To> mapStream(Collection<From> fromCollection, Function<From, To> mapper) {
        return mapStream(fromCollection.stream(), mapper);
    }

    private static <From, To> Stream<To> mapStream(Stream<From> fromStream, Function<From, To> mapper) {
        return fromStream.map(mapper);
    }
}
