/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.Context;
import org.jooq.CreateDomainAsStep;
import org.jooq.CreateDomainConstraintStep;
import org.jooq.CreateDomainDefaultStep;
import org.jooq.CreateDomainFinalStep;
import org.jooq.DataType;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.Function5;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.ConstraintImpl;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

final class CreateDomainImpl<T>
extends AbstractDDLQuery
implements QOM.CreateDomain<T>,
CreateDomainAsStep,
CreateDomainDefaultStep<T>,
CreateDomainConstraintStep,
CreateDomainFinalStep {
    final Domain<?> domain;
    final boolean ifNotExists;
    DataType<T> dataType;
    Field<T> default_;
    QueryPartListView<? extends Constraint> constraints;
    private static final Set<SQLDialect> NO_SUPPORT_IF_NOT_EXISTS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);

    CreateDomainImpl(Configuration configuration, Domain<?> domain, boolean ifNotExists) {
        this(configuration, domain, ifNotExists, null, null, null);
    }

    CreateDomainImpl(Configuration configuration, Domain<?> domain, boolean ifNotExists, DataType<T> dataType, Field<T> default_, Collection<? extends Constraint> constraints) {
        super(configuration);
        this.domain = domain;
        this.ifNotExists = ifNotExists;
        this.dataType = dataType;
        this.default_ = default_;
        this.constraints = new QueryPartList<Constraint>((Iterable<? extends Constraint>)constraints);
    }

    public final <T> CreateDomainImpl<T> as(Class<T> dataType) {
        return this.as((DataType)DefaultDataType.getDataType(null, dataType));
    }

    public final <T> CreateDomainImpl<T> as(DataType<T> dataType) {
        this.dataType = dataType;
        return this;
    }

    @Override
    public final CreateDomainImpl<T> default_(T default_) {
        return this.default_((Field)Tools.field(default_));
    }

    @Override
    public final CreateDomainImpl<T> default_(Field<T> default_) {
        this.default_ = default_;
        return this;
    }

    @Override
    public final CreateDomainImpl<T> constraints(Constraint ... constraints) {
        return this.constraints(Arrays.asList(constraints));
    }

    @Override
    public final CreateDomainImpl<T> constraints(Collection<? extends Constraint> constraints) {
        this.constraints = new QueryPartList<Constraint>((Iterable<? extends Constraint>)constraints);
        return this;
    }

    private final boolean supportsIfNotExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_NOT_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.CREATE_DOMAIN, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLSERVER: {
                ctx.visit(Keywords.K_CREATE).sql(' ').visit(Keywords.K_TYPE).sql(' ').visit(this.domain).sql(' ').visit(Keywords.K_FROM).sql(' ');
                break;
            }
            default: {
                ctx.visit(Keywords.K_CREATE).sql(' ').visit(Keywords.K_DOMAIN);
                if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
                    ctx.sql(' ').visit(Keywords.K_IF_NOT_EXISTS);
                }
                ctx.sql(' ').visit(this.domain).sql(' ').visit(Keywords.K_AS).sql(' ');
            }
        }
        Tools.toSQLDDLTypeDeclaration(ctx, this.dataType);
        if (this.default_ != null) {
            ctx.formatSeparator().visit(Keywords.K_DEFAULT).sql(' ').visit(this.default_);
        }
        if (!Tools.isEmpty(this.constraints)) {
            if (ctx.family() == SQLDialect.FIREBIRD) {
                ctx.formatSeparator().visit(DSL.check(DSL.and(Tools.map(this.constraints, c -> ((ConstraintImpl)c).$check()))));
            } else {
                for (Constraint constraint : this.constraints) {
                    ctx.formatSeparator().visit(constraint);
                }
            }
        }
    }

    @Override
    public final Domain<?> $domain() {
        return this.domain;
    }

    @Override
    public final boolean $ifNotExists() {
        return this.ifNotExists;
    }

    @Override
    public final DataType<T> $dataType() {
        return this.dataType;
    }

    @Override
    public final Field<T> $default_() {
        return this.default_;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Constraint> $constraints() {
        return QOM.unmodifiable(this.constraints);
    }

    @Override
    public final QOM.CreateDomain<T> $domain(Domain<?> newValue) {
        return this.$constructor().apply(newValue, this.$ifNotExists(), this.$dataType(), this.$default_(), this.$constraints());
    }

    @Override
    public final QOM.CreateDomain<T> $ifNotExists(boolean newValue) {
        return this.$constructor().apply(this.$domain(), newValue, this.$dataType(), this.$default_(), this.$constraints());
    }

    @Override
    public final QOM.CreateDomain<T> $dataType(DataType<T> newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifNotExists(), newValue, this.$default_(), this.$constraints());
    }

    @Override
    public final QOM.CreateDomain<T> $default_(Field<T> newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifNotExists(), this.$dataType(), newValue, this.$constraints());
    }

    @Override
    public final QOM.CreateDomain<T> $constraints(Collection<? extends Constraint> newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifNotExists(), this.$dataType(), this.$default_(), newValue);
    }

    public final Function5<? super Domain<?>, ? super Boolean, ? super DataType<T>, ? super Field<T>, ? super Collection<? extends Constraint>, ? extends QOM.CreateDomain<T>> $constructor() {
        return (a1, a2, a3, a4, a5) -> new CreateDomainImpl<T>(this.configuration(), (Domain<?>)a1, (boolean)a2, a3, a4, (Collection<Constraint>)a5);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$domain(), this.$ifNotExists(), this.$dataType(), this.$default_(), this.$constraints(), (a1, a2, a3, a4, a5) -> this.$constructor().apply((Domain<?>)a1, (Boolean)a2, (DataType<T>)a3, (Field<T>)a4, (Collection<Constraint>)a5), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$domain(), this.$dataType(), this.$default_(), this.$constraints());
    }
}

