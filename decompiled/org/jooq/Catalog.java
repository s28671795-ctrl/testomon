/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Schema;

public interface Catalog
extends Named {
    @NotNull
    public List<Schema> getSchemas();

    @Nullable
    public Schema getSchema(String var1);

    @Nullable
    public Schema getSchema(Name var1);

    @NotNull
    public Stream<Schema> schemaStream();
}

