/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Catalog;
import org.jooq.Domain;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.UDT;
import org.jooq.UniqueKey;

public interface Schema
extends Named {
    @Nullable
    public Catalog getCatalog();

    @NotNull
    public Stream<Table<?>> tableStream();

    @NotNull
    public List<Table<?>> getTables();

    @Nullable
    public Table<?> getTable(String var1);

    @Nullable
    public Table<?> getTable(Name var1);

    @NotNull
    public Stream<UniqueKey<?>> primaryKeyStream();

    @NotNull
    public List<UniqueKey<?>> getPrimaryKeys();

    @NotNull
    public List<UniqueKey<?>> getPrimaryKeys(String var1);

    @NotNull
    public List<UniqueKey<?>> getPrimaryKeys(Name var1);

    @NotNull
    public Stream<UniqueKey<?>> uniqueKeyStream();

    @NotNull
    public List<UniqueKey<?>> getUniqueKeys();

    @NotNull
    public List<UniqueKey<?>> getUniqueKeys(String var1);

    @NotNull
    public List<UniqueKey<?>> getUniqueKeys(Name var1);

    @NotNull
    public Stream<ForeignKey<?, ?>> foreignKeyStream();

    @NotNull
    public List<ForeignKey<?, ?>> getForeignKeys();

    @NotNull
    public List<ForeignKey<?, ?>> getForeignKeys(String var1);

    @NotNull
    public List<ForeignKey<?, ?>> getForeignKeys(Name var1);

    @NotNull
    public Stream<Index> indexStream();

    @NotNull
    public List<Index> getIndexes();

    @NotNull
    public List<Index> getIndexes(String var1);

    @NotNull
    public List<Index> getIndexes(Name var1);

    @NotNull
    public Stream<UDT<?>> udtStream();

    @NotNull
    public List<UDT<?>> getUDTs();

    @Nullable
    public UDT<?> getUDT(String var1);

    @Nullable
    public UDT<?> getUDT(Name var1);

    @NotNull
    public Stream<Domain<?>> domainStream();

    @NotNull
    public List<Domain<?>> getDomains();

    @Nullable
    public Domain<?> getDomain(String var1);

    @Nullable
    public Domain<?> getDomain(Name var1);

    @NotNull
    public Stream<Sequence<?>> sequenceStream();

    @NotNull
    public List<Sequence<?>> getSequences();

    @Nullable
    public Sequence<?> getSequence(String var1);

    @Nullable
    public Sequence<?> getSequence(Name var1);
}

