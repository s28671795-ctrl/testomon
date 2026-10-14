/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.CallArgsStep;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

@Pro
final class Call
extends AbstractRowCountQuery
implements QOM.Call,
CallArgsStep {
    final Name procedure;
    QueryPartListView<? extends Field<?>> args;

    Call(Configuration configuration, Name procedure) {
        this(configuration, procedure, null);
    }

    Call(Configuration configuration, Name procedure, Collection<? extends Field<?>> args2) {
        super(configuration);
        this.procedure = procedure;
        this.args = new QueryPartList((Iterable<Field<?>>)args2);
    }

    @Override
    public final Call args(Field<?> ... args2) {
        return this.args(Arrays.asList(args2));
    }

    @Override
    public final Call args(Collection<? extends Field<?>> args2) {
        this.args = new QueryPartList((Iterable<Field<?>>)args2);
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case FIREBIRD: {
                ctx.visit(Keywords.K_EXECUTE).sql(' ').visit(Keywords.K_PROCEDURE).sql(' ').visit(this.procedure);
                if (Tools.isEmpty(this.args)) break;
                ctx.sql('(').visit(QueryPartCollectionView.wrap(this.args)).sql(')');
                break;
            }
            case INFORMIX: {
                ctx.visit(Keywords.K_EXECUTE).sql(' ').visit(Keywords.K_PROCEDURE).sql(' ').visit(this.procedure).sql('(').visit(QueryPartCollectionView.wrap(this.args)).sql(')');
                break;
            }
            case ORACLE: {
                if (ctx.scopeLevel() >= 0) {
                    ctx.visit(this.procedure).sql('(').visit(QueryPartCollectionView.wrap(this.args)).sql(')');
                    break;
                }
                ctx.visit(DSL.begin(this));
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Keywords.K_EXEC).sql(' ').visit(this.procedure).separatorRequired(true).visit(QueryPartCollectionView.wrap(this.args));
                break;
            }
            default: {
                ctx.visit(Keywords.K_CALL).sql(' ').visit(this.procedure).sql('(').visit(QueryPartCollectionView.wrap(this.args)).sql(')');
            }
        }
    }

    @Override
    public final Name $procedure() {
        return this.procedure;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $args() {
        return QOM.unmodifiable(this.args);
    }

    @Override
    public final QOM.Call $procedure(Name newValue) {
        return this.$constructor().apply((Name)((Name)newValue), (Collection<Field<?>>)this.$args());
    }

    @Override
    public final QOM.Call $args(Collection<? extends Field<?>> newValue) {
        return this.$constructor().apply(this.$procedure(), newValue);
    }

    public final Function2<? super Name, ? super Collection<? extends Field<?>>, ? extends QOM.Call> $constructor() {
        return (a1, a2) -> new Call(this.configuration(), (Name)a1, (Collection<? extends Field<?>>)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$procedure(), this.$args(), (a1, a2) -> this.$constructor().apply((Name)((Name)a1), (Collection<Field<?>>)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$procedure(), this.$args());
    }
}

