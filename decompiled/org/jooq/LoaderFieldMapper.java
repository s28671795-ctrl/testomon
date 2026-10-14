/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Field;

@FunctionalInterface
public interface LoaderFieldMapper {
    public Field<?> map(LoaderFieldContext var1);

    public static interface LoaderFieldContext {
        public Field<?> field();

        public int index();
    }
}

