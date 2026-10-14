/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.jooq.Binding;
import org.jooq.Catalog;
import org.jooq.Check;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.ContextConverter;
import org.jooq.DataType;
import org.jooq.Domain;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.DomainDataType;
import org.jooq.impl.QOM;

class DomainImpl<T>
extends AbstractNamed
implements Domain<T>,
QOM.UNotYetImplemented {
    private final Schema schema;
    private final Check<?>[] checks;
    private final DataType<T> type;

    DomainImpl(Schema schema, Name name, DataType<T> type, Check<?> ... checks) {
        super(DomainImpl.qualify(schema, name), null);
        this.schema = schema;
        this.checks = checks;
        this.type = new DomainDataType<T>(this, type);
    }

    @Override
    public final Catalog getCatalog() {
        return this.getSchema() == null ? null : this.getSchema().getCatalog();
    }

    @Override
    public final Schema getSchema() {
        return this.schema;
    }

    @Override
    public final List<Check<?>> getChecks() {
        return Collections.unmodifiableList(Arrays.asList(this.checks));
    }

    @Override
    public final ContextConverter<?, T> getConverter() {
        return this.type.getConverter();
    }

    @Override
    public final Binding<?, T> getBinding() {
        return this.type.getBinding();
    }

    @Override
    public final Class<T> getType() {
        return this.type.getType();
    }

    @Override
    public final DataType<T> getDataType() {
        return this.type;
    }

    @Override
    public final DataType<T> getDataType(Configuration configuration) {
        return this.type.getDataType(configuration);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.getQualifiedName());
    }

    @Override
    public final DataType<T> $dataType() {
        return this.type;
    }

    @Override
    public final Schema $schema() {
        return this.getSchema();
    }
}

