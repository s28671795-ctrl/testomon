/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Catalog;
import org.jooq.EnumTypes;
import org.jooq.Schema;

public interface EnumType {
    @NotNull
    public String getLiteral();

    @Nullable
    default public Catalog getCatalog() {
        return null;
    }

    @Nullable
    default public Schema getSchema() {
        return null;
    }

    @Nullable
    public String getName();

    @Nullable
    public static <E extends Enum<E>> E lookupLiteral(Class<E> enumType, String literal) {
        return (E)((Enum)EnumTypes.lookupLiteral(enumType, literal));
    }
}

