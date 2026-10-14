/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.BooleanUtils;

public class BooleanUtil {
    private static final Set<String> trueValues = new HashSet<String>(Arrays.asList("true", "t", "yes", "y", "1"));
    private static final Set<String> falseValues = new HashSet<String>(Arrays.asList("false", "f", "no", "n", "0"));

    public static boolean parseBoolean(String booleanStr) {
        if (booleanStr == null) {
            return false;
        }
        String value = booleanStr.trim().toLowerCase();
        return trueValues.contains(value) || !falseValues.contains(value) && BooleanUtil.isTrue(value);
    }

    private static boolean isTrue(String str) {
        try {
            return Integer.parseInt(str) > 0;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    @Deprecated
    public static boolean isTrue(Boolean value) {
        return BooleanUtils.isTrue(value);
    }
}

