/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.stream.Stream;
import org.jooq.Catalog;
import org.jooq.Comment;
import org.jooq.Context;
import org.jooq.Domain;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Internal;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.UDT;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.LazySupplier;

@Internal
public final class LazySchema
extends AbstractNamed
implements Schema {
    final LazySupplier<Schema> supplier;
    transient Schema schema;

    public LazySchema(Name name, Comment comment, LazySupplier<Schema> supplier) {
        super(name, comment);
        this.supplier = supplier;
    }

    private final Schema schema() {
        if (this.schema == null) {
            try {
                this.schema = (Schema)this.supplier.get();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return this.schema;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.schema());
    }

    @Override
    public final Catalog getCatalog() {
        return this.schema().getCatalog();
    }

    @Override
    public final List<Table<?>> getTables() {
        return this.schema().getTables();
    }

    @Override
    public final Table<?> getTable(String name) {
        return this.schema().getTable(name);
    }

    @Override
    public final Table<?> getTable(Name name) {
        return this.schema().getTable(name);
    }

    @Override
    public final List<UniqueKey<?>> getPrimaryKeys() {
        return this.schema().getPrimaryKeys();
    }

    @Override
    public final List<UniqueKey<?>> getPrimaryKeys(String name) {
        return this.schema().getPrimaryKeys(name);
    }

    @Override
    public final List<UniqueKey<?>> getPrimaryKeys(Name name) {
        return this.schema().getPrimaryKeys(name);
    }

    @Override
    public final List<UniqueKey<?>> getUniqueKeys() {
        return this.schema().getUniqueKeys();
    }

    @Override
    public final List<UniqueKey<?>> getUniqueKeys(String name) {
        return this.schema().getUniqueKeys(name);
    }

    @Override
    public final List<UniqueKey<?>> getUniqueKeys(Name name) {
        return this.schema().getUniqueKeys(name);
    }

    @Override
    public final List<ForeignKey<?, ?>> getForeignKeys() {
        return this.schema().getForeignKeys();
    }

    @Override
    public final List<ForeignKey<?, ?>> getForeignKeys(String name) {
        return this.schema().getForeignKeys(name);
    }

    @Override
    public final List<ForeignKey<?, ?>> getForeignKeys(Name name) {
        return this.schema().getForeignKeys(name);
    }

    @Override
    public final List<Index> getIndexes() {
        return this.schema().getIndexes();
    }

    @Override
    public final List<Index> getIndexes(String name) {
        return this.schema().getIndexes(name);
    }

    @Override
    public final List<Index> getIndexes(Name name) {
        return this.schema().getIndexes(name);
    }

    @Override
    public final List<UDT<?>> getUDTs() {
        return this.schema().getUDTs();
    }

    @Override
    public final UDT<?> getUDT(String name) {
        return this.schema().getUDT(name);
    }

    @Override
    public final UDT<?> getUDT(Name name) {
        return this.schema().getUDT(name);
    }

    @Override
    public final List<Domain<?>> getDomains() {
        return this.schema().getDomains();
    }

    @Override
    public final Domain<?> getDomain(String name) {
        return this.schema().getDomain(name);
    }

    @Override
    public final Domain<?> getDomain(Name name) {
        return this.schema().getDomain(name);
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return this.schema().getSequences();
    }

    @Override
    public final Sequence<?> getSequence(String name) {
        return this.schema().getSequence(name);
    }

    @Override
    public final Sequence<?> getSequence(Name name) {
        return this.schema().getSequence(name);
    }

    @Override
    public final Stream<Table<?>> tableStream() {
        return this.schema().tableStream();
    }

    @Override
    public final Stream<UniqueKey<?>> primaryKeyStream() {
        return this.schema().primaryKeyStream();
    }

    @Override
    public final Stream<UniqueKey<?>> uniqueKeyStream() {
        return this.schema().uniqueKeyStream();
    }

    @Override
    public final Stream<ForeignKey<?, ?>> foreignKeyStream() {
        return this.schema().foreignKeyStream();
    }

    @Override
    public final Stream<Index> indexStream() {
        return this.schema().indexStream();
    }

    @Override
    public final Stream<UDT<?>> udtStream() {
        return this.schema().udtStream();
    }

    @Override
    public final Stream<Domain<?>> domainStream() {
        return this.schema().domainStream();
    }

    @Override
    public final Stream<Sequence<?>> sequenceStream() {
        return this.schema().sequenceStream();
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return this.schema().$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return this.schema().$replace(replacer);
    }
}

