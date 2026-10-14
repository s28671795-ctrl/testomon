/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.index;

import com.incoresoft.plugin.commons.qdrant.index.FieldType;
import com.incoresoft.plugin.commons.qdrant.index.QDrantIndex;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class IntegerIndex
implements QDrantIndex {
    private final String name;

    public IntegerIndex(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.INTEGER;
    }
}

