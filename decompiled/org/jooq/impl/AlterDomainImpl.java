/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.AlterDomainDropConstraintCascadeStep;
import org.jooq.AlterDomainFinalStep;
import org.jooq.AlterDomainRenameConstraintStep;
import org.jooq.AlterDomainStep;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.Context;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.Function14;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.ConstraintImpl;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class AlterDomainImpl<T>
extends AbstractDDLQuery
implements QOM.AlterDomain<T>,
AlterDomainStep<T>,
AlterDomainDropConstraintCascadeStep,
AlterDomainRenameConstraintStep,
AlterDomainFinalStep {
    final Domain<T> domain;
    final boolean ifExists;
    Constraint addConstraint;
    Constraint dropConstraint;
    boolean dropConstraintIfExists;
    Domain<?> renameTo;
    Constraint renameConstraint;
    boolean renameConstraintIfExists;
    Field<T> setDefault;
    boolean dropDefault;
    boolean setNotNull;
    boolean dropNotNull;
    QOM.Cascade cascade;
    Constraint renameConstraintTo;
    private static final Set<SQLDialect> NO_SUPPORT_RENAME_CONSTRAINT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.POSTGRES);
    private static final Set<SQLDialect> NO_SUPPORT_DROP_CONSTRAINT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.FIREBIRD);

    AlterDomainImpl(Configuration configuration, Domain<T> domain, boolean ifExists) {
        this(configuration, domain, ifExists, null, null, false, null, null, false, null, false, false, false, null, null);
    }

    AlterDomainImpl(Configuration configuration, Domain<T> domain, boolean ifExists, Constraint addConstraint, Constraint dropConstraint, boolean dropConstraintIfExists, Domain<?> renameTo, Constraint renameConstraint, boolean renameConstraintIfExists, Field<T> setDefault, boolean dropDefault, boolean setNotNull, boolean dropNotNull, QOM.Cascade cascade, Constraint renameConstraintTo) {
        super(configuration);
        this.domain = domain;
        this.ifExists = ifExists;
        this.addConstraint = addConstraint;
        this.dropConstraint = dropConstraint;
        this.dropConstraintIfExists = dropConstraintIfExists;
        this.renameTo = renameTo;
        this.renameConstraint = renameConstraint;
        this.renameConstraintIfExists = renameConstraintIfExists;
        this.setDefault = setDefault;
        this.dropDefault = dropDefault;
        this.setNotNull = setNotNull;
        this.dropNotNull = dropNotNull;
        this.cascade = cascade;
        this.renameConstraintTo = renameConstraintTo;
    }

    @Override
    public final AlterDomainImpl<T> add(Constraint addConstraint) {
        this.addConstraint = addConstraint;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> dropConstraint(String dropConstraint) {
        return this.dropConstraint(DSL.constraint(DSL.name(dropConstraint)));
    }

    @Override
    public final AlterDomainImpl<T> dropConstraint(Name dropConstraint) {
        return this.dropConstraint(DSL.constraint(dropConstraint));
    }

    @Override
    public final AlterDomainImpl<T> dropConstraint(Constraint dropConstraint) {
        this.dropConstraint = dropConstraint;
        this.dropConstraintIfExists = false;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> dropConstraintIfExists(String dropConstraint) {
        return this.dropConstraintIfExists(DSL.constraint(DSL.name(dropConstraint)));
    }

    @Override
    public final AlterDomainImpl<T> dropConstraintIfExists(Name dropConstraint) {
        return this.dropConstraintIfExists(DSL.constraint(dropConstraint));
    }

    @Override
    public final AlterDomainImpl<T> dropConstraintIfExists(Constraint dropConstraint) {
        this.dropConstraint = dropConstraint;
        this.dropConstraintIfExists = true;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> renameTo(String renameTo) {
        return this.renameTo((Domain)DSL.domain(DSL.name(renameTo)));
    }

    @Override
    public final AlterDomainImpl<T> renameTo(Name renameTo) {
        return this.renameTo((Domain)DSL.domain(renameTo));
    }

    @Override
    public final AlterDomainImpl<T> renameTo(Domain<?> renameTo) {
        this.renameTo = renameTo;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> renameConstraint(String renameConstraint) {
        return this.renameConstraint(DSL.constraint(DSL.name(renameConstraint)));
    }

    @Override
    public final AlterDomainImpl<T> renameConstraint(Name renameConstraint) {
        return this.renameConstraint(DSL.constraint(renameConstraint));
    }

    @Override
    public final AlterDomainImpl<T> renameConstraint(Constraint renameConstraint) {
        this.renameConstraint = renameConstraint;
        this.renameConstraintIfExists = false;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> renameConstraintIfExists(String renameConstraint) {
        return this.renameConstraintIfExists(DSL.constraint(DSL.name(renameConstraint)));
    }

    @Override
    public final AlterDomainImpl<T> renameConstraintIfExists(Name renameConstraint) {
        return this.renameConstraintIfExists(DSL.constraint(renameConstraint));
    }

    @Override
    public final AlterDomainImpl<T> renameConstraintIfExists(Constraint renameConstraint) {
        this.renameConstraint = renameConstraint;
        this.renameConstraintIfExists = true;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> setDefault(T setDefault) {
        return this.setDefault((Field)Tools.field(setDefault));
    }

    @Override
    public final AlterDomainImpl<T> setDefault(Field<T> setDefault) {
        this.setDefault = setDefault;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> dropDefault() {
        this.dropDefault = true;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> setNotNull() {
        this.setNotNull = true;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> dropNotNull() {
        this.dropNotNull = true;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> cascade() {
        this.cascade = QOM.Cascade.CASCADE;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> restrict() {
        this.cascade = QOM.Cascade.RESTRICT;
        return this;
    }

    @Override
    public final AlterDomainImpl<T> to(String renameConstraintTo) {
        return this.to(DSL.constraint(DSL.name(renameConstraintTo)));
    }

    @Override
    public final AlterDomainImpl<T> to(Name renameConstraintTo) {
        return this.to(DSL.constraint(renameConstraintTo));
    }

    @Override
    public final AlterDomainImpl<T> to(Constraint renameConstraintTo) {
        this.renameConstraintTo = renameConstraintTo;
        return this;
    }

    private final boolean supportsRenameConstraintIfExists(Context<?> ctx) {
        return !NO_SUPPORT_RENAME_CONSTRAINT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    private final boolean supportsDropConstraintIfExists(Context<?> ctx) {
        return !NO_SUPPORT_DROP_CONSTRAINT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.renameConstraintIfExists && !this.supportsRenameConstraintIfExists(ctx) || this.dropConstraintIfExists && !this.supportsDropConstraintIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.ALTER_DOMAIN, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        ctx.visit(Keywords.K_ALTER).sql(' ').visit(Keywords.K_DOMAIN).sql(' ');
        if (this.ifExists) {
            ctx.visit(Keywords.K_IF_EXISTS).sql(' ');
        }
        ctx.visit(this.domain).sql(' ');
        if (this.addConstraint != null) {
            if (ctx.family() == SQLDialect.FIREBIRD) {
                ctx.visit(Keywords.K_ADD).sql(' ').visit(DSL.check(((ConstraintImpl)this.addConstraint).$check()));
            } else {
                ctx.visit(Keywords.K_ADD).sql(' ').visit(this.addConstraint);
            }
        } else if (this.dropConstraint != null) {
            ctx.visit(Keywords.K_DROP_CONSTRAINT);
            if (this.dropConstraintIfExists && this.supportsDropConstraintIfExists(ctx)) {
                ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
            }
            if (ctx.family() != SQLDialect.FIREBIRD) {
                ctx.sql(' ').data(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE, true, c -> c.visit(this.dropConstraint));
                AlterDomainImpl.acceptCascade(ctx, this.cascade);
            }
        } else if (this.renameTo != null) {
            ctx.visit(ctx.family() == SQLDialect.FIREBIRD ? Keywords.K_TO : Keywords.K_RENAME_TO).sql(' ').data(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE, true, c -> c.visit(this.renameTo));
        } else if (this.renameConstraint != null) {
            ctx.visit(Keywords.K_RENAME_CONSTRAINT).sql(' ').data(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE, true, c -> {
                if (this.renameConstraintIfExists && this.supportsRenameConstraintIfExists((Context<?>)c)) {
                    c.visit(Keywords.K_IF_EXISTS).sql(' ');
                }
                c.visit(this.renameConstraint).sql(' ').visit(Keywords.K_TO).sql(' ').visit(this.renameConstraintTo);
            });
        } else if (this.setDefault != null) {
            ctx.visit(Keywords.K_SET_DEFAULT).sql(' ').visit(this.setDefault);
        } else if (this.dropDefault) {
            ctx.visit(Keywords.K_DROP_DEFAULT);
        } else if (this.setNotNull) {
            ctx.visit(Keywords.K_SET_NOT_NULL);
        } else if (this.dropNotNull) {
            ctx.visit(Keywords.K_DROP_NOT_NULL);
        }
    }

    @Override
    public final Domain<T> $domain() {
        return this.domain;
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final Constraint $addConstraint() {
        return this.addConstraint;
    }

    @Override
    public final Constraint $dropConstraint() {
        return this.dropConstraint;
    }

    @Override
    public final boolean $dropConstraintIfExists() {
        return this.dropConstraintIfExists;
    }

    @Override
    public final Domain<?> $renameTo() {
        return this.renameTo;
    }

    @Override
    public final Constraint $renameConstraint() {
        return this.renameConstraint;
    }

    @Override
    public final boolean $renameConstraintIfExists() {
        return this.renameConstraintIfExists;
    }

    @Override
    public final Field<T> $setDefault() {
        return this.setDefault;
    }

    @Override
    public final boolean $dropDefault() {
        return this.dropDefault;
    }

    @Override
    public final boolean $setNotNull() {
        return this.setNotNull;
    }

    @Override
    public final boolean $dropNotNull() {
        return this.dropNotNull;
    }

    @Override
    public final QOM.Cascade $cascade() {
        return this.cascade;
    }

    @Override
    public final Constraint $renameConstraintTo() {
        return this.renameConstraintTo;
    }

    @Override
    public final QOM.AlterDomain<T> $domain(Domain<T> newValue) {
        return this.$constructor().apply(newValue, this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$domain(), newValue, this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $addConstraint(Constraint newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), newValue, this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $dropConstraint(Constraint newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), newValue, this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $dropConstraintIfExists(boolean newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), newValue, this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $renameTo(Domain<?> newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), newValue, this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $renameConstraint(Constraint newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), newValue, this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $renameConstraintIfExists(boolean newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), newValue, this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $setDefault(Field<T> newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), newValue, this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $dropDefault(boolean newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), newValue, this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $setNotNull(boolean newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), newValue, this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $dropNotNull(boolean newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), newValue, this.$cascade(), this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $cascade(QOM.Cascade newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), newValue, this.$renameConstraintTo());
    }

    @Override
    public final QOM.AlterDomain<T> $renameConstraintTo(Constraint newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), newValue);
    }

    public final Function14<? super Domain<T>, ? super Boolean, ? super Constraint, ? super Constraint, ? super Boolean, ? super Domain<?>, ? super Constraint, ? super Boolean, ? super Field<T>, ? super Boolean, ? super Boolean, ? super Boolean, ? super QOM.Cascade, ? super Constraint, ? extends QOM.AlterDomain<T>> $constructor() {
        return (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) -> new AlterDomainImpl<T>(this.configuration(), a1, (boolean)a2, (Constraint)a3, (Constraint)a4, (boolean)a5, (Domain<?>)a6, (Constraint)a7, (boolean)a8, a9, (boolean)a10, (boolean)a11, (boolean)a12, (QOM.Cascade)((Object)a13), (Constraint)a14);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$domain(), this.$ifExists(), this.$addConstraint(), this.$dropConstraint(), this.$dropConstraintIfExists(), this.$renameTo(), this.$renameConstraint(), this.$renameConstraintIfExists(), this.$setDefault(), this.$dropDefault(), this.$setNotNull(), this.$dropNotNull(), this.$cascade(), this.$renameConstraintTo(), (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) -> this.$constructor().apply((Domain<T>)a1, (Boolean)a2, (Constraint)a3, (Constraint)a4, (Boolean)a5, (Domain<?>)a6, (Constraint)a7, (Boolean)a8, (Field<T>)a9, (Boolean)a10, (Boolean)a11, (Boolean)a12, (QOM.Cascade)((Object)a13), (Constraint)a14), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$domain(), this.$addConstraint(), this.$dropConstraint(), this.$renameTo(), this.$renameConstraint(), this.$setDefault(), this.$renameConstraintTo());
    }
}

