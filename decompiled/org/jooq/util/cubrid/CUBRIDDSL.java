/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.cubrid;

import org.jooq.Field;
import org.jooq.impl.DSL;

@Deprecated(forRemoval=true, since="3.13")
public class CUBRIDDSL
extends DSL {
    protected CUBRIDDSL() {
    }

    public static <T> Field<T> incr(Field<T> field2) {
        return CUBRIDDSL.function("incr", field2.getDataType(), field2);
    }

    public static <T> Field<T> decr(Field<T> field2) {
        return CUBRIDDSL.function("decr", field2.getDataType(), field2);
    }
}

