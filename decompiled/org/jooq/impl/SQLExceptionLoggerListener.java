/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import org.jooq.DataType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Row;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.SQLStateClass;
import org.jooq.impl.Default;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

final class SQLExceptionLoggerListener
implements ExecuteListener {
    private static final JooqLogger log = JooqLogger.getLogger(SQLExceptionLoggerListener.class);

    SQLExceptionLoggerListener() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void exception(ExecuteContext ctx) {
        try {
            if (this.logException(ctx)) {
                Object object = ctx.query();
                if (object instanceof QOM.Insert) {
                    QOM.Insert q = (QOM.Insert)object;
                    QOM.UnmodifiableList<Row> v = q.$values();
                    if (!v.isEmpty()) {
                        void var5_6;
                        boolean bl = false;
                        while (var5_6 < v.size()) {
                            for (Field field2 : q.$into().fields().length != 0 ? Arrays.asList(q.$into().fields()) : q.$columns()) {
                                DataType type = field2.getDataType();
                                int col = q.$columns().indexOf(field2);
                                if (col >= 0) {
                                    Field<?> value = ((Row)v.get((int)var5_6)).field(col);
                                    this.notNullCheck(ctx, field2, value, type, v, (int)var5_6);
                                    this.stringLengthCheck(ctx, field2, value, type, v, (int)var5_6);
                                    this.numericPrecisionCheck(ctx, field2, value, type, v, (int)var5_6);
                                    continue;
                                }
                                if (type.nullable() || type.defaulted()) continue;
                                log.info("NOT NULL column " + String.valueOf(field2) + " does not have a DEFAULT, and thus cannot be omitted from INSERT");
                            }
                            ++var5_6;
                        }
                    } else if (q.$defaultValues() && q.$into().fields().length != 0) {
                        void var7_14;
                        Field<?>[] fieldArray = q.$into().fields();
                        int n = fieldArray.length;
                        boolean bl = false;
                        while (var7_14 < n) {
                            Field<?> key = fieldArray[var7_14];
                            DataType type = key.getDataType();
                            if (!type.nullable() && !type.defaulted()) {
                                log.info("NOT NULL column " + String.valueOf(key) + " does not have a DEFAULT, and thus cannot be omitted from INSERT");
                            }
                            ++var7_14;
                        }
                    }
                } else {
                    object = ctx.query();
                    if (object instanceof QOM.Update) {
                        QOM.Update q = (QOM.Update)object;
                        for (Field field3 : q.$table().fields().length != 0 ? Arrays.asList(q.$table().fields()) : Tools.filter(q.$set().keySet(), f -> f instanceof Field)) {
                            DataType type = field3.getDataType();
                            Object v = q.$set().get(field3);
                            if (!(v instanceof Field)) continue;
                            Field field4 = (Field)v;
                            this.notNullCheck(ctx, field3, field4, type, null, 0);
                            this.stringLengthCheck(ctx, field3, field4, type, null, 0);
                            this.numericPrecisionCheck(ctx, field3, field4, type, null, 0);
                        }
                    }
                }
            }
        }
        catch (Exception x) {
            log.error((Object)"Error while logging", x);
        }
    }

    private final boolean logException(ExecuteContext ctx) {
        if (ctx.sqlException() instanceof SQLIntegrityConstraintViolationException || ctx.sqlException() instanceof SQLDataException) {
            return true;
        }
        RuntimeException runtimeException = ctx.exception();
        if (runtimeException instanceof DataAccessException) {
            DataAccessException e = (DataAccessException)runtimeException;
            if (e.sqlStateClass() == SQLStateClass.C23_INTEGRITY_CONSTRAINT_VIOLATION || e.sqlStateClass() == SQLStateClass.C22_DATA_EXCEPTION) {
                return true;
            }
            if (e.sqlStateClass() == SQLStateClass.OTHER) {
                switch (ctx.family()) {
                    case AURORA_MYSQL: 
                    case MARIADB: 
                    case MEMSQL: 
                    case MYSQL: {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final void notNullCheck(ExecuteContext ctx, Field<?> key, Field<?> value, DataType<?> type, QOM.UnmodifiableList<? extends Row> v, int row) {
        if (!type.nullable()) {
            if (value instanceof Param) {
                Param p = (Param)value;
                if (p.getValue() == null) {
                    log.info("NOT NULL column " + String.valueOf(key) + " cannot have an explicit NULL value" + (type.defaulted() ? " (overriding the DEFAULT)" : "") + this.rowSuffix(v, row));
                }
            } else if (value instanceof Default && !type.defaulted()) {
                log.info("NOT NULL column " + String.valueOf(key) + " does not have a DEFAULT, and thus cannot have an explicit DEFAULT value");
            }
        }
    }

    private final String rowSuffix(QOM.UnmodifiableList<? extends Row> v, int row) {
        return v != null && v.size() > 1 ? " in row " + (row + 1) + " with values: " + String.valueOf(v.get(row)) : "";
    }

    private final void numericPrecisionCheck(ExecuteContext ctx, Field<?> key, Field<?> value, DataType<?> type, QOM.UnmodifiableList<? extends Row> v, int row) {
        BigDecimal d;
        int precision;
        Param p;
        Object t2;
        if (type.isNumeric() && type.precisionDefined() && value instanceof Param && (t2 = (p = (Param)value).getValue()) instanceof BigDecimal && (precision = (d = (BigDecimal)t2).precision() + Math.abs(d.scale())) > type.precision()) {
            log.info("Column " + String.valueOf(key) + " of type " + type.getCastTypeName(ctx.configuration()) + " cannot accept number of precision " + precision + ": " + String.valueOf(d) + this.rowSuffix(v, row));
        }
    }

    private final void stringLengthCheck(ExecuteContext ctx, Field<?> key, Field<?> value, DataType<?> type, QOM.UnmodifiableList<? extends Row> v, int row) {
        String s2;
        Param p;
        Object t2;
        if (type.isString() && type.lengthDefined() && value instanceof Param && (t2 = (p = (Param)value).getValue()) instanceof String && (s2 = (String)t2).length() > type.length()) {
            log.info("Column " + String.valueOf(key) + " of type " + type.getCastTypeName(ctx.configuration()) + " cannot accept string of length " + s2.length() + ": " + StringUtils.abbreviate(s2, 100) + this.rowSuffix(v, row));
        }
    }
}

