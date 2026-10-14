/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import org.h2.message.DbException;

public final class GeneratedKeysMode {
    public static final int NONE = 0;
    public static final int AUTO = 1;
    public static final int COLUMN_NUMBERS = 2;
    public static final int COLUMN_NAMES = 3;

    public static int valueOf(Object object) {
        if (object == null || Boolean.FALSE.equals(object)) {
            return 0;
        }
        if (Boolean.TRUE.equals(object)) {
            return 1;
        }
        if (object instanceof int[]) {
            return ((int[])object).length > 0 ? 2 : 0;
        }
        if (object instanceof String[]) {
            return ((String[])object).length > 0 ? 3 : 0;
        }
        throw DbException.getInternalError();
    }

    private GeneratedKeysMode() {
    }
}

