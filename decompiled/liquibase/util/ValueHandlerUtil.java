/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.util.List;
import java.util.stream.Collectors;
import liquibase.util.StringUtil;

public class ValueHandlerUtil {
    public static Integer getIntegerWithConstraints(Object input, String errorMessage, List<Integer> validValues) {
        if (input == null) {
            return null;
        }
        Integer convertedInput = Integer.valueOf(String.valueOf(input));
        boolean anyMatch = validValues.contains(convertedInput);
        if (!anyMatch) {
            throw new IllegalArgumentException(errorMessage + StringUtil.join(validValues.stream().sorted().map(String::valueOf).collect(Collectors.toList()), ", "));
        }
        return convertedInput;
    }
}

