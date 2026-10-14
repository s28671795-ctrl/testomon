/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractParam;
import org.jooq.impl.AbstractRowAsField;
import org.jooq.impl.ConvertedDataType;
import org.jooq.impl.ConvertedVal;
import org.jooq.impl.DSL;
import org.jooq.impl.DataTypeProxy;
import org.jooq.impl.DefaultBindingSQLContext;
import org.jooq.impl.LegacyConvertedDataType;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SQLFieldSimple;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.YearToMonth;

final class Val<T>
extends AbstractParam<T>
implements QOM.UEmpty {
    private static final JooqLogger log = JooqLogger.getLogger(Val.class);
    private static final ConcurrentHashMap<Class<?>, Object> legacyWarnings = new ConcurrentHashMap();
    final boolean inferredDataType;

    Val(T value, DataType<T> type, boolean inferredDataType) {
        super(value, Val.type(value, type));
        this.inferredDataType = inferredDataType;
    }

    Val(T value, DataType<T> type, boolean inferredDataType, String paramName) {
        super(value, Val.type(value, type), paramName);
        this.inferredDataType = inferredDataType;
    }

    private static final <T> DataType<T> type(T value, DataType<T> type) {
        return value == null ? type.null_() : type.notNull();
    }

    final <U> Param<U> convertTo(DataType<U> type) {
        DataType dataType = this.getDataType();
        if (dataType instanceof DataTypeProxy) {
            DataTypeProxy p = (DataTypeProxy)dataType;
            if (p.type() instanceof LegacyConvertedDataType && type == SQLDataType.OTHER) {
                type = p.type();
                if (legacyWarnings.size() < 8 && legacyWarnings.put(type.getType(), "") == null) {
                    log.warn("Deprecation", "User-defined, converted data type " + String.valueOf(type.getType()) + " was registered statically, which will be unsupported in the future, see https://github.com/jOOQ/jOOQ/issues/9492. Please use explicit data types in generated code, or e.g. with DSL.val(Object, DataType), or DSL.inline(Object, DataType).", new SQLWarning("Static type registry usage"));
                }
            }
            return this.convertTo0(type);
        }
        if (type instanceof ConvertedDataType) {
            return this.convertTo0(type);
        }
        if (SQLDataType.OTHER.equals(this.getDataType())) {
            return new ConvertedVal<U>(this, type);
        }
        return this;
    }

    final Val<T> copy(Object newValue) {
        Val w = new Val(this.getDataType().convert(newValue), this.getDataType(), this.inferredDataType, this.getParamName());
        w.setInline0(this.isInline());
        return w;
    }

    final <U> Val<U> convertTo0(DataType<U> type) {
        Val<U> w = new Val<U>(type.convert(this.getValue()), type, this.inferredDataType, this.getParamName());
        w.setInline0(this.isInline());
        return w;
    }

    @Override
    public void accept(Context<?> ctx) {
        if (this.getDataType().isEmbeddable()) {
            if (AbstractRowAsField.forceMultisetContent(ctx, () -> Tools.embeddedFields(this).length > 1)) {
                AbstractRowAsField.acceptMultisetContent(ctx, Tools.row0(Tools.embeddedFields(this)), this, this::acceptDefaultEmbeddable);
            } else {
                this.acceptDefaultEmbeddable(ctx);
            }
        } else if (ctx instanceof RenderContext) {
            RenderContext r = (RenderContext)ctx;
            ParamType paramType = ctx.paramType();
            if (this.isInline(ctx)) {
                ctx.paramType(ParamType.INLINED);
            }
            if (ctx.family() == SQLDialect.JAVA) {
                this.acceptJava(ctx);
            } else {
                try {
                    this.getBinding().sql(new DefaultBindingSQLContext<Object>(ctx.configuration(), ctx.data(), r, this.value, this.getBindVariable(ctx)));
                }
                catch (SQLException e) {
                    throw new DataAccessException("Error while generating SQL for Binding", e);
                }
            }
            ctx.paramType(paramType);
        } else if (!this.isInline(ctx)) {
            ctx.bindValue(this.value, this);
        }
    }

    private void acceptDefaultEmbeddable(Context<?> ctx) {
        ctx.data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED, true, c -> c.visit(QueryPartListView.wrap((QueryPart[])Tools.embeddedFields(this))));
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        if (ctx.paramType() == ParamType.INLINED) {
            ctx.sql("inline(").visit(this.getJavaValue()).sql(')');
        } else {
            ctx.sql("val(").visit(this.getJavaValue()).sql(')');
        }
    }

    @Pro
    static final Field<?> getJavaValue(Field<?> f) {
        if (f instanceof Val && !((Val)f).isInline()) {
            return ((Val)f).getJavaValue();
        }
        return f;
    }

    @Pro
    static final Field<?>[] getJavaValues(Field<?>[] fields) {
        if (Tools.findAny(fields, f -> !(f instanceof Val) || ((Val)f).isInline()) != null) {
            return fields;
        }
        return Tools.map(fields, f -> ((Val)f).getJavaValue(), Field[]::new);
    }

    @Pro
    private final Field<?> getJavaValue() {
        DataType type = this.getDataType();
        return new SQLFieldSimple<String>(SQLDataType.VARCHAR, DSL.sql(Val.getJavaValueString(type, this.getValue())));
    }

    @Pro
    private static final String getJavaValueString(DataType<?> type, Object v) {
        if (v == null) {
            if (type.isBinary()) {
                return "(byte[]) " + String.valueOf(v);
            }
            if (type.getType().isArray()) {
                return "(" + type.getArrayComponentType().getSimpleName() + "[]) " + String.valueOf(v);
            }
            return "(" + type.getType().getSimpleName() + ") " + String.valueOf(v);
        }
        if (type.isDate()) {
            if (type.getType() == Date.class) {
                return "Date.valueOf(" + Tools.stringLiteral(String.valueOf(v)) + ")";
            }
            return "LocalDate.parse(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.isTime()) {
            if (type.getType() == Time.class) {
                return "Time.valueOf(" + Tools.stringLiteral(String.valueOf(v)) + ")";
            }
            return "LocalTime.parse(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.isTimestamp()) {
            if (type.getType() == Timestamp.class) {
                return "Timestamp.valueOf(" + Tools.stringLiteral(String.valueOf(v)) + ")";
            }
            return "LocalDateTime.parse(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.getType() == OffsetTime.class) {
            return "OffsetTime.parse(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.getType() == OffsetDateTime.class) {
            return "OffsetDateTime.parse(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.getType() == Instant.class) {
            return "Instant.parse(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.isInterval()) {
            if (type.getType() == YearToMonth.class) {
                return "YearToMonth.valueOf(" + Tools.stringLiteral(String.valueOf(v)) + ")";
            }
            if (type.getType() == DayToSecond.class) {
                return "DayToSecond.valueOf(" + Tools.stringLiteral(String.valueOf(v)) + ")";
            }
            return "YearToSecond.valueOf(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.isString()) {
            return Tools.stringLiteral(String.valueOf(v));
        }
        if (type.isJSON()) {
            if (type.getType() == JSONB.class) {
                return "JSONB.json(" + Tools.stringLiteral(String.valueOf(v)) + ")";
            }
            return "JSON.json(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.isXML()) {
            return "XML.xml(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.getType() == UUID.class) {
            return "UUID.fromString(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.getType() == UByte.class) {
            return "UByte.valueOf(" + String.valueOf(v) + ")";
        }
        if (type.getType() == UShort.class) {
            return "UShort.valueOf(" + String.valueOf(v) + ")";
        }
        if (type.getType() == UInteger.class) {
            return "UInteger.valueOf(" + String.valueOf(v) + "L)";
        }
        if (type.getType() == ULong.class) {
            return "ULong.valueOf(" + Tools.stringLiteral(String.valueOf(v)) + ")";
        }
        if (type.isBinary()) {
            return "new byte[] { " + Arrays.toString((byte[])v).replaceFirst("^\\[(.*)\\]$", "$1") + " }";
        }
        if (type.isArray()) {
            if (((Object[])v).length == 0) {
                return "new " + type.getArrayComponentType().getSimpleName() + "[0]";
            }
            return "new " + type.getArrayComponentType().getSimpleName() + "[] { " + Arrays.stream((Object[])v).map(o -> Val.getJavaValueString(type.getArrayComponentDataType(), o)).collect(Collectors.joining(", ")) + " }";
        }
        if (type.getType() == Double.class) {
            if ((Double)v == Double.POSITIVE_INFINITY) {
                return "Double.POSITIVE_INFINITY";
            }
            if ((Double)v == Double.NEGATIVE_INFINITY) {
                return "Double.NEGATIVE_INFINITY";
            }
            if (Double.isNaN((Double)v)) {
                return "Double.NaN";
            }
            return String.valueOf(v);
        }
        if (type.getType() == Float.class) {
            if (((Float)v).floatValue() == Float.POSITIVE_INFINITY) {
                return "Float.POSITIVE_INFINITY";
            }
            if (((Float)v).floatValue() == Float.NEGATIVE_INFINITY) {
                return "Float.NEGATIVE_INFINITY";
            }
            if (Float.isNaN(((Float)v).floatValue())) {
                return "Float.NaN";
            }
            return String.valueOf(v);
        }
        if (type.getType() == Year.class) {
            return "Year.of(" + ((Year)v).getValue() + ")";
        }
        return String.valueOf(v);
    }

    @NotNull
    final String getBindVariable(Context<?> ctx) {
        if (ctx.paramType() == ParamType.NAMED || ctx.paramType() == ParamType.NAMED_OR_INLINED) {
            int index = ctx.nextIndex();
            String prefix = StringUtils.defaultIfNull(ctx.settings().getRenderNamedParamPrefix(), ":");
            if (StringUtils.isBlank(this.getParamName())) {
                return prefix + index;
            }
            return prefix + this.getParamName();
        }
        return "?";
    }

    @Override
    public final Param<T> $value(T newValue) {
        return this.copy(newValue);
    }

    @Override
    public final Param<T> $inline(boolean inline2) {
        Val<Object> w = new Val<Object>(this.value, this.getDataType(), this.inferredDataType, this.getParamName());
        w.setInline0(inline2);
        return w;
    }
}

