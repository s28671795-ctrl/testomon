/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.ParamMode;
import org.jooq.Parameter;
import org.jooq.Pro;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Statement;
import org.jooq.Variable;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Assignment;
import org.jooq.impl.CreateProcedureImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.TypedReference;
import org.jooq.impl.VariableImpl;

final class ParameterImpl<T>
extends AbstractField<T>
implements Parameter<T>,
QOM.UEmpty,
TypedReference<T> {
    private final ParamMode paramMode;
    private final boolean isDefaulted;
    private final boolean isUnnamed;
    @Pro
    private final Variable<T> variable;
    @Pro
    static final Set<SQLDialect> SUPPORT_TSQL_PARAMS = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    @Pro
    static final Set<SQLDialect> SUPPORT_PLSQL_PARAMS = SQLDialect.supportedBy(SQLDialect.ORACLE);
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_OUT = SQLDialect.supportedUntil(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES_13, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);

    ParameterImpl(ParamMode paramMode, Name name, DataType<T> type) {
        this(paramMode, name, type, type.defaulted(), name == null || name.empty());
    }

    @Deprecated
    ParameterImpl(ParamMode paramMode, Name name, DataType<T> type, boolean isDefaulted, boolean isUnnamed) {
        super(name, type);
        this.paramMode = paramMode;
        this.isDefaulted = isDefaulted;
        this.isUnnamed = isUnnamed;
        this.variable = new VariableImpl<T>(name, type);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.variable != null) {
            if (ctx.declareParameters()) {
                boolean tsql = SUPPORT_TSQL_PARAMS.contains((Object)ctx.dialect());
                boolean plsql = SUPPORT_PLSQL_PARAMS.contains((Object)ctx.dialect());
                if (!tsql && !plsql) {
                    if (this.paramMode == ParamMode.OUT) {
                        if (NO_SUPPORT_OUT.contains((Object)ctx.dialect())) {
                            ctx.visit(Keywords.K_INOUT).sql(' ');
                        } else {
                            ctx.visit(Keywords.K_OUT).sql(' ');
                        }
                    } else if (this.paramMode == ParamMode.INOUT) {
                        ctx.visit(Keywords.K_INOUT).sql(' ');
                    }
                }
                ctx.visit(this.variable);
                if (plsql) {
                    if (this.paramMode == ParamMode.OUT) {
                        ctx.sql(' ').visit(Keywords.K_OUT);
                    } else if (this.paramMode == ParamMode.INOUT) {
                        ctx.sql(' ').visit(Keywords.K_IN).sql(' ').visit(Keywords.K_OUT);
                    }
                }
                ctx.sql(' ');
                if (CreateProcedureImpl.REQUIRES_PARAM_TYPE_LPS.contains((Object)ctx.family())) {
                    Tools.toSQLDDLTypeDeclaration(ctx, this.getDataType());
                } else {
                    ctx.sql(this.getDataType().getTypeName(ctx.configuration()));
                }
                if (tsql && (this.paramMode == ParamMode.OUT || this.paramMode == ParamMode.INOUT)) {
                    ctx.sql(' ').visit(Keywords.K_OUT);
                }
            } else {
                ctx.visit(this.variable);
            }
        } else {
            ctx.visit(this.getUnqualifiedName());
        }
    }

    @Override
    public final ParamMode getParamMode() {
        return this.paramMode;
    }

    @Override
    public final boolean isDefaulted() {
        return this.isDefaulted;
    }

    @Override
    public final boolean isUnnamed() {
        return this.isUnnamed;
    }

    @Override
    @Pro
    public final boolean declaresParameters() {
        return true;
    }

    @Override
    public final Statement set(T value) {
        return this.set(Tools.field(value, this.getDataType()));
    }

    @Override
    public final Statement set(Field<T> value) {
        return new Assignment<T>(this, value);
    }

    @Override
    public final Statement set(Select<? extends Record1<T>> value) {
        return this.set(DSL.field(value));
    }

    @Override
    public final Statement setNull() {
        return this.set((T)null);
    }
}

