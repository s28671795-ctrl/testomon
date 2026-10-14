/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class RefUtils {
    public static String constructRef(String simpleRef) {
        return "#/components/schemas/" + simpleRef;
    }

    public static String constructRef(String simpleRef, String prefix) {
        return prefix + simpleRef;
    }

    public static Pair extractSimpleName(String ref) {
        String simple;
        int idx = ref.lastIndexOf(47);
        if (idx > 0 && !StringUtils.isBlank(simple = ref.substring(idx + 1))) {
            return new ImmutablePair<String, String>(simple, ref.substring(0, idx + 1));
        }
        return new ImmutablePair<String, Object>(ref, null);
    }
}

