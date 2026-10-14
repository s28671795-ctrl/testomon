/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.jooq.Catalog;
import org.jooq.Clause;
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
import org.jooq.impl.CatalogImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Lazy;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleQueryPart;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

@Internal
public class SchemaImpl
extends AbstractNamed
implements Schema,
SimpleQueryPart {
    private static final Clause[] CLAUSES = new Clause[]{Clause.SCHEMA, Clause.SCHEMA_REFERENCE};
    static final Lazy<Schema> DEFAULT_SCHEMA = Lazy.of(() -> new SchemaImpl(""));
    private Catalog catalog;

    public SchemaImpl(String name) {
        this(name, null);
    }

    public SchemaImpl(String name, Catalog catalog) {
        this(DSL.name(name), catalog);
    }

    public SchemaImpl(String name, Catalog catalog, String comment) {
        this(DSL.name(name), catalog, DSL.comment(comment));
    }

    public SchemaImpl(Name name) {
        this(name, (Catalog)null);
    }

    public SchemaImpl(Name name, Catalog catalog) {
        this(name, catalog, null);
    }

    public SchemaImpl(Name name, Catalog catalog, Comment comment) {
        super(SchemaImpl.qualify(catalog, name), comment);
        this.catalog = catalog;
    }

    @Override
    public Catalog getCatalog() {
        if (this.catalog == null) {
            this.catalog = this.getQualifiedName().qualified() ? DSL.catalog(this.getQualifiedName().qualifier()) : null;
        }
        return this.catalog;
    }

    @Override
    public final void accept(Context<?> ctx) {
        Schema mappedSchema;
        Catalog mappedCatalog;
        if (ctx.qualifyCatalog() && (mappedCatalog = Tools.getMappedCatalog(ctx, this.getCatalog())) != null && !"".equals(mappedCatalog.getName())) {
            ctx.visit(mappedCatalog).sql('.');
        }
        ctx.visit((mappedSchema = Tools.getMappedSchema(ctx, this)) != null ? mappedSchema.getUnqualifiedName() : this.getUnqualifiedName());
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Table<?> getTable(String name) {
        return SchemaImpl.find(name, this.getTables());
    }

    @Override
    public final Table<?> getTable(Name name) {
        return SchemaImpl.find(name, this.getTables());
    }

    @Override
    public final List<UniqueKey<?>> getPrimaryKeys(String name) {
        return SchemaImpl.findAll(name, this.getPrimaryKeys());
    }

    @Override
    public final List<UniqueKey<?>> getPrimaryKeys(Name name) {
        return SchemaImpl.findAll(name, this.getPrimaryKeys());
    }

    @Override
    public final List<UniqueKey<?>> getUniqueKeys(String name) {
        return SchemaImpl.findAll(name, this.getUniqueKeys());
    }

    @Override
    public final List<UniqueKey<?>> getUniqueKeys(Name name) {
        return SchemaImpl.findAll(name, this.getUniqueKeys());
    }

    @Override
    public final List<ForeignKey<?, ?>> getForeignKeys(String name) {
        return SchemaImpl.findAll(name, this.getForeignKeys());
    }

    @Override
    public final List<ForeignKey<?, ?>> getForeignKeys(Name name) {
        return SchemaImpl.findAll(name, this.getForeignKeys());
    }

    @Override
    public final List<Index> getIndexes(String name) {
        return SchemaImpl.findAll(name, this.getIndexes());
    }

    @Override
    public final List<Index> getIndexes(Name name) {
        return SchemaImpl.findAll(name, this.getIndexes());
    }

    @Override
    public final UDT<?> getUDT(String name) {
        return SchemaImpl.find(name, this.getUDTs());
    }

    @Override
    public final UDT<?> getUDT(Name name) {
        return SchemaImpl.find(name, this.getUDTs());
    }

    @Override
    public final Domain<?> getDomain(String name) {
        return SchemaImpl.find(name, this.getDomains());
    }

    @Override
    public final Domain<?> getDomain(Name name) {
        return SchemaImpl.find(name, this.getDomains());
    }

    @Override
    public final Sequence<?> getSequence(String name) {
        return SchemaImpl.find(name, this.getSequences());
    }

    @Override
    public final Sequence<?> getSequence(Name name) {
        return SchemaImpl.find(name, this.getSequences());
    }

    @Override
    public List<Table<?>> getTables() {
        return Collections.emptyList();
    }

    @Override
    public List<UniqueKey<?>> getPrimaryKeys() {
        ArrayList result = new ArrayList();
        for (Table<?> table : this.getTables()) {
            if (table.getPrimaryKey() == null) continue;
            result.add(table.getPrimaryKey());
        }
        return result;
    }

    @Override
    public List<UniqueKey<?>> getUniqueKeys() {
        return Tools.flatMap(this.getTables(), t2 -> t2.getKeys());
    }

    @Override
    public List<ForeignKey<?, ?>> getForeignKeys() {
        return Tools.flatMap(this.getTables(), t2 -> t2.getReferences());
    }

    @Override
    public List<Index> getIndexes() {
        return Tools.flatMap(this.getTables(), t2 -> t2.getIndexes());
    }

    @Override
    public List<UDT<?>> getUDTs() {
        return Collections.emptyList();
    }

    @Override
    public List<Domain<?>> getDomains() {
        return Collections.emptyList();
    }

    @Override
    public List<Sequence<?>> getSequences() {
        return Collections.emptyList();
    }

    @Override
    public final Stream<Table<?>> tableStream() {
        return this.getTables().stream();
    }

    @Override
    public final Stream<UniqueKey<?>> primaryKeyStream() {
        return this.getPrimaryKeys().stream();
    }

    @Override
    public final Stream<UniqueKey<?>> uniqueKeyStream() {
        return this.getUniqueKeys().stream();
    }

    @Override
    public final Stream<ForeignKey<?, ?>> foreignKeyStream() {
        return this.getForeignKeys().stream();
    }

    @Override
    public final Stream<Index> indexStream() {
        return this.getIndexes().stream();
    }

    @Override
    public final Stream<UDT<?>> udtStream() {
        return this.getUDTs().stream();
    }

    @Override
    public final Stream<Domain<?>> domainStream() {
        return this.getDomains().stream();
    }

    @Override
    public final Stream<Sequence<?>> sequenceStream() {
        return this.getSequences().stream();
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.getQualifiedName(), this.getCatalog());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.getQualifiedName(), this.getCatalog(), this.getCommentPart(), SchemaImpl::new, replacer);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof SchemaImpl) {
            SchemaImpl other = (SchemaImpl)that;
            return StringUtils.equals(StringUtils.defaultIfNull(this.getCatalog(), CatalogImpl.DEFAULT_CATALOG), StringUtils.defaultIfNull(other.getCatalog(), CatalogImpl.DEFAULT_CATALOG)) && StringUtils.equals(this.getName(), other.getName());
        }
        return super.equals(that);
    }
}

