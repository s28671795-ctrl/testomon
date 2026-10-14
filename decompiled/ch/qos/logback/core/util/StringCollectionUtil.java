/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

public class StringCollectionUtil {
    public static void retainMatching(Collection<String> values2, String ... patterns) {
        StringCollectionUtil.retainMatching(values2, Arrays.asList(patterns));
    }

    public static void retainMatching(Collection<String> values2, Collection<String> patterns) {
        if (patterns.isEmpty()) {
            return;
        }
        ArrayList<String> matches = new ArrayList<String>(values2.size());
        for (String p : patterns) {
            Pattern pattern = Pattern.compile(p);
            for (String value : values2) {
                if (!pattern.matcher(value).matches()) continue;
                matches.add(value);
            }
        }
        values2.retainAll(matches);
    }

    public static void removeMatching(Collection<String> values2, String ... patterns) {
        StringCollectionUtil.removeMatching(values2, Arrays.asList(patterns));
    }

    public static void removeMatching(Collection<String> values2, Collection<String> patterns) {
        ArrayList<String> matches = new ArrayList<String>(values2.size());
        for (String p : patterns) {
            Pattern pattern = Pattern.compile(p);
            for (String value : values2) {
                if (!pattern.matcher(value).matches()) continue;
                matches.add(value);
            }
        }
        values2.removeAll(matches);
    }
}

