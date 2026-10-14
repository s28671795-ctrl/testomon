/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.TableLike;
import org.jooq.Variable;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class RowAssignment
extends AbstractStatement
implements QOM.UNotYetImplemented {
    private static final Set<SQLDialect> EMULATE_ROW_ASSIGNMENT = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> EMULATE_SELECT_ASSIGNMENT = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    final Row target;
    final Row value;
    final Select<?> select;

    RowAssignment(Row target, Row value) {
        this.target = target;
        this.value = value;
        this.select = null;
    }

    RowAssignment(Row target, Select<?> select) {
        this.target = target;
        this.value = null;
        this.select = select;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.value != null) {
            this.acceptValue(ctx);
        } else {
            this.acceptSelect(ctx);
        }
    }

    private final void acceptValue(Context<?> ctx) {
        if (EMULATE_ROW_ASSIGNMENT.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.select(this.value.fields()).into(this.targetVariables()));
        } else {
            ctx.visit(Keywords.K_SET).sql(' ').visit(this.target).sql(" = ").visit(this.value);
        }
    }

    private final void acceptSelect(Context<?> ctx) {
        if (EMULATE_SELECT_ASSIGNMENT.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.select(DSL.asterisk()).into(this.targetVariables()).from((TableLike<?>)this.select.asTable("t", Tools.fieldNameStrings(this.target.size()))));
        } else {
            ctx.visit(Keywords.K_SET).sql(' ').visit(this.target).sql(" = ");
            Tools.visitSubquery(ctx, this.select);
        }
    }

    private final Variable<?>[] targetVariables() {
        return Tools.map(this.target.fields(), f -> RowAssignment.variable(f), Variable[]::new);
    }

    private static final Variable<?> variable(Field<?> field2) {
        Variable v;
        return field2 instanceof Variable ? (v = (Variable)field2) : DSL.var(field2.getQualifiedName(), field2.getDataType());
    }
}

