/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Variable;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.BlockImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

@Pro
final class Return
extends AbstractStatement
implements QOM.Return {
    final Field<?> value;
    private static final Set<SQLDialect> NO_SUPPORT_RETURN_SCALAR_SUBQUERY = SQLDialect.supportedBy(SQLDialect.ORACLE);

    Return() {
        this(null);
    }

    Return(Field<?> value) {
        this.value = value;
    }

    @Override
    public final void accept(Context<?> ctx) {
        BlockImpl.TopLevelBlock block;
        if (this.value != null) {
            if (this.value instanceof ScalarSubquery && NO_SUPPORT_RETURN_SCALAR_SUBQUERY.contains((Object)ctx.dialect())) {
                Variable variable = DSL.variable("result", this.value.getDataType());
                ctx.visit(DSL.begin(DSL.declare(variable).set(((ScalarSubquery)this.value).query), DSL.return_(variable)));
            } else if (ctx.family() == SQLDialect.HANA) {
                ctx.visit(DSL.statements(DSL.variable("result", this.value.getDataType()).set(this.value), DSL.signalSQLState(DSL.inline(19999))));
            } else {
                ctx.visit(Keywords.K_RETURN).sql(' ').visit(this.value);
            }
        } else if (ctx.family() == SQLDialect.FIREBIRD) {
            ctx.visit(Keywords.K_EXIT);
        } else if (ctx.data(Tools.ExtendedDataKey.DATA_TOP_SCOPE_ROUTINE_NAME) != null && (block = (BlockImpl.TopLevelBlock)ctx.data(Tools.ExtendedDataKey.DATA_TOP_LEVEL_BLOCK)) != null) {
            Name name;
            block.label = name = (Name)ctx.data(Tools.ExtendedDataKey.DATA_TOP_SCOPE_ROUTINE_NAME);
            ctx.visit(DSL.exit(DSL.label(name)));
        } else {
            ctx.visit(Keywords.K_RETURN);
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("return_(");
        if (this.value != null) {
            ctx.visit(Val.getJavaValue(this.value));
        }
        ctx.sql(')');
    }

    @Override
    public final Field<?> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Return $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<?>, ? extends QOM.Return> $constructor() {
        return a1 -> new Return((Field<?>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Return) {
            QOM.Return o = (QOM.Return)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

