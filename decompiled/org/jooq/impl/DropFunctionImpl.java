/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;
import org.jooq.impl.Tools;

@Pro
final class DropFunctionImpl
extends AbstractDDLQuery
implements QOM.DropFunction {
    final Name function;
    final boolean ifExists;
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.ORACLE);

    DropFunctionImpl(Configuration configuration, Name function, boolean ifExists) {
        super(configuration);
        this.function = function;
        this.ifExists = ifExists;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.DROP_FUNCTION, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        ctx.visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_FUNCTION);
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
        }
        ctx.sql(' ').visit(new QualifiedImpl(this.function));
    }

    @Override
    public final Name $function() {
        return this.function;
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final QOM.DropFunction $function(Name newValue) {
        return this.$constructor().apply(newValue, (Boolean)this.$ifExists());
    }

    @Override
    public final QOM.DropFunction $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$function(), (Boolean)newValue);
    }

    public final Function2<? super Name, ? super Boolean, ? extends QOM.DropFunction> $constructor() {
        return (a1, a2) -> new DropFunctionImpl(this.configuration(), (Name)a1, (boolean)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$function(), this.$ifExists(), (a1, a2) -> this.$constructor().apply((Name)a1, (Boolean)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$function());
    }
}

