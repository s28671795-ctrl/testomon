/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import liquibase.util.ObjectUtil;

public class CollectionUtil {
    public static <T> Set<Set<T>> powerSet(Collection<T> originalSet) {
        HashSet<Set<T>> sets = new HashSet<Set<T>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet());
            return sets;
        }
        ArrayList<T> list = new ArrayList<T>(originalSet);
        Object head = list.get(0);
        List rest = list.subList(1, list.size());
        for (Set set : CollectionUtil.powerSet(rest)) {
            HashSet newSet = new HashSet();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    public static <T> List<Map<String, T>> permutations(Map<String, List<T>> parameterValues) {
        ArrayList<Map<String, T>> list = new ArrayList<Map<String, T>>();
        if (parameterValues == null || parameterValues.isEmpty()) {
            return list;
        }
        CollectionUtil.permute(new HashMap(), new ArrayList<String>(parameterValues.keySet()), parameterValues, list);
        return list;
    }

    private static <T> void permute(Map<String, T> basePermutation, List<String> remainingKeys, Map<String, List<T>> parameterValues, List<Map<String, T>> returnList) {
        String thisKey = remainingKeys.get(0);
        remainingKeys = remainingKeys.subList(1, remainingKeys.size());
        for (T value : parameterValues.get(thisKey)) {
            HashMap<String, T> permutation = new HashMap<String, T>(basePermutation);
            permutation.put(thisKey, value);
            if (remainingKeys.isEmpty()) {
                returnList.add(permutation);
                continue;
            }
            CollectionUtil.permute(permutation, remainingKeys, parameterValues, returnList);
        }
    }

    @Deprecated
    public static <T> List<T> createIfNull(List<T> currentValue) {
        return ObjectUtil.defaultIfNull(currentValue, new ArrayList());
    }

    @Deprecated
    public static <T> T[] createIfNull(T[] arguments) {
        return ObjectUtil.defaultIfNull(arguments, new Object[0]);
    }

    @Deprecated
    public static <T> Set<T> createIfNull(Set<T> currentValue) {
        return ObjectUtil.defaultIfNull(currentValue, new HashSet());
    }

    @Deprecated
    public static <T, E> Map<T, E> createIfNull(Map<T, E> currentValue) {
        return ObjectUtil.defaultIfNull(currentValue, new HashMap());
    }

    public static Map<String, Object> flatten(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        return CollectionUtil.flatten(null, map);
    }

    private static Map<String, Object> flatten(String prefix, Map<String, Object> map) {
        HashMap<String, Object> outMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String propertyName = entry.getKey();
            if (prefix != null) {
                propertyName = prefix + "." + propertyName;
            }
            if (entry.getValue() instanceof Map) {
                outMap.putAll(CollectionUtil.flatten(propertyName, (Map)entry.getValue()));
                continue;
            }
            outMap.put(propertyName, entry.getValue());
        }
        return outMap;
    }

    public static String findKeyInMapIgnoreCase(String key, Map<String, Object> map) {
        for (Map.Entry<String, Object> mapEntry : map.entrySet()) {
            String actualKey = mapEntry.getKey();
            if (!actualKey.equalsIgnoreCase(key)) continue;
            return actualKey;
        }
        return null;
    }
}

