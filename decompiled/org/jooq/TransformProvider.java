/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Field;
import org.jooq.Pro;

@Pro
public interface TransformProvider {
    default public boolean inlineBindValuesForFieldComparisons(Field<?> field2) {
        return false;
    }
}

