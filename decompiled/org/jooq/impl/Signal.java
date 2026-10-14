/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.ParamOrVariable;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.SignalSetStep;
import org.jooq.Statement;
import org.jooq.Variable;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

@Pro
final class Signal
extends AbstractStatement
implements QOM.Signal,
SignalSetStep {
    final Field<?> value;
    Field<String> messageText;

    Signal(Field<?> value) {
        this(value, null);
    }

    Signal(Field<?> value, Field<String> messageText) {
        this.value = value;
        this.messageText = messageText;
    }

    @Override
    public final Signal setMessageText(String messageText) {
        return this.setMessageText(Tools.field(messageText));
    }

    @Override
    public final Signal setMessageText(Field<String> messageText) {
        this.messageText = messageText;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case POSTGRES: 
            case YUGABYTEDB: {
                this.acceptPostgres(ctx);
                break;
            }
            case DB2: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: {
                this.acceptDefaultPullingUpDeclarations(ctx);
                break;
            }
            default: {
                Signal.acceptDefault(ctx, this.value, this.messageText);
            }
        }
    }

    private final void acceptPostgres(Context<?> ctx) {
        ctx.visit(Keywords.K_RAISE).sql(' ').visit(Keywords.K_SQLSTATE).sql(' ').visit(this.value);
        if (this.messageText != null) {
            ctx.sql(' ').visit(Keywords.K_USING).sql(' ').visit(Keywords.K_MESSAGE).sql(" = ").visit(this.messageText);
        }
    }

    private final void acceptDefaultPullingUpDeclarations(Context<?> ctx) {
        boolean bm;
        boolean bv = this.value instanceof ParamOrVariable;
        boolean bl = bm = this.messageText == null || this.messageText instanceof ParamOrVariable;
        if (bv && bm) {
            ctx.paramType(ParamType.INLINED, c -> Signal.acceptDefault(c, this.value, this.messageText));
        } else {
            Field<String> m4;
            ArrayList<Statement> s2 = new ArrayList<Statement>();
            Field<?> v = bv ? this.value : DSL.variable("sqlstate", SQLDataType.CHAR(5));
            Field<String> field2 = m4 = bm ? this.messageText : DSL.variable("messagetext", SQLDataType.VARCHAR);
            if (v != this.value) {
                s2.add(DSL.declare((Variable)v).set(this.value));
            }
            if (m4 != this.messageText) {
                s2.add(DSL.declare((Variable)m4).set(this.messageText));
            }
            s2.add(this.messageText != null ? DSL.signalSQLState(v).setMessageText(m4) : DSL.signalSQLState(v));
            ctx.visit(DSL.begin(s2));
        }
    }

    private static final void acceptDefault(Context<?> ctx, Field<?> value, Field<String> messageText) {
        ctx.visit(Keywords.K_SIGNAL).sql(' ').visit(ctx.family() == SQLDialect.HANA ? Keywords.K_SQL_ERROR_CODE : Keywords.K_SQLSTATE).sql(' ').visit(value);
        if (messageText != null) {
            ctx.sql(' ').visit(Keywords.K_SET).sql(' ').visit(Keywords.K_MESSAGE_TEXT).sql(" = ").visit(messageText);
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("signalSQLState(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.messageText));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<?> $arg1() {
        return this.value;
    }

    @Override
    public final Field<String> $arg2() {
        return this.messageText;
    }

    @Override
    public final QOM.Signal $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue, (Field<String>)this.$arg2());
    }

    @Override
    public final QOM.Signal $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<?>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<?>, ? super Field<String>, ? extends QOM.Signal> $constructor() {
        return (a1, a2) -> new Signal((Field<?>)a1, (Field<String>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Signal) {
            QOM.Signal o = (QOM.Signal)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$messageText(), o.$messageText());
        }
        return super.equals(that);
    }
}

