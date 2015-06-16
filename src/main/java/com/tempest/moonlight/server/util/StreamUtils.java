package com.tempest.moonlight.server.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Yurii on 2015-06-11.
 */
public class StreamUtils {
    public static <Key, Value> Collection<Value> filterMap(Map<Key, Value> map, Predicate<Map.Entry<Key, Value>> predicate) {
        return map.entrySet().parallelStream().filter(predicate).map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    public static <Key, Value> boolean mapKeysContain(Map<Key, Value> map, Predicate<Map.Entry<Key, Value>> predicate) {
        return map.entrySet().parallelStream().anyMatch(predicate);
    }

    public static <From, To> Set<To> convert(Collection<From> fromCollection, Function<From, To> mapper) {
        return fromCollection.stream().map(mapper).collect(Collectors.toSet());
    }
}
