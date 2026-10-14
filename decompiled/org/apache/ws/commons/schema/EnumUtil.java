/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.Locale;

final class EnumUtil {
    private EnumUtil() {
    }

    static <T extends Enum<T>> T valueOf(Class<T> enumClass, String name) {
        return Enum.valueOf(enumClass, name.toUpperCase(Locale.ENGLISH));
    }
}

