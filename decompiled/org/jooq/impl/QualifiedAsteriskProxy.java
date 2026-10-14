/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QualifiedAsterisk;
import org.jooq.Query;
import org.jooq.Table;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedAsteriskImpl;
import org.jooq.impl.ScopeMappable;

final class QualifiedAsteriskProxy
extends AbstractQueryPart
implements QualifiedAsterisk,
QOM.UProxy<QualifiedAsterisk>,
ScopeMappable {
    private QualifiedAsteriskImpl delegate;
    private final int position;
    Query scopeOwner;
    boolean resolved;

    QualifiedAsteriskProxy(QualifiedAsteriskImpl delegate, int position) {
        this.delegate = delegate;
        this.position = position;
    }

    final int position() {
        return this.position;
    }

    final void delegate(QualifiedAsteriskImpl newDelegate) {
        this.resolve();
        this.delegate = newDelegate;
    }

    final QualifiedAsteriskProxy resolve() {
        this.resolved = true;
        this.scopeOwner = null;
        return this;
    }

    final void scopeOwner(Query query) {
        if (!this.resolved && this.scopeOwner == null) {
            this.scopeOwner = query;
        }
    }

    @Override
    public final int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public final boolean equals(Object that) {
        return this.delegate.equals(that);
    }

    @Override
    public final boolean declaresFields() {
        return this.delegate.declaresFields();
    }

    @Override
    public final boolean declaresTables() {
        return this.delegate.declaresTables();
    }

    @Override
    public final boolean declaresWindows() {
        return this.delegate.declaresWindows();
    }

    @Override
    public final boolean declaresCTE() {
        return this.delegate.declaresCTE();
    }

    @Override
    public final boolean generatesCast() {
        return this.delegate.generatesCast();
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return this.delegate.rendersContent(ctx);
    }

    @Override
    public final void accept(Context<?> ctx) {
        this.delegate.accept(ctx);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return this.delegate.clauses(ctx);
    }

    @Override
    public final String toString() {
        return this.delegate.toString();
    }

    @Override
    public final Table<?> qualifier() {
        return this.delegate.qualifier();
    }

    @Override
    public final QualifiedAsterisk except(String ... fieldNames) {
        return new QualifiedAsteriskProxy((QualifiedAsteriskImpl)this.delegate.except(fieldNames), this.position);
    }

    @Override
    public final QualifiedAsterisk except(Name ... fieldNames) {
        return new QualifiedAsteriskProxy((QualifiedAsteriskImpl)this.delegate.except(fieldNames), this.position);
    }

    @Override
    public final QualifiedAsterisk except(Field<?> ... fields) {
        return new QualifiedAsteriskProxy((QualifiedAsteriskImpl)this.delegate.except(fields), this.position);
    }

    @Override
    public final QualifiedAsterisk $delegate() {
        return this.delegate;
    }

    @Override
    public final Table<?> $table() {
        return this.delegate.$table();
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $except() {
        return this.delegate.$except();
    }
}

