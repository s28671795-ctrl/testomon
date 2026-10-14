/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.function.BooleanSupplier;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Keyword;
import org.jooq.LanguageContext;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.RenderContext;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDataType;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.ThrowingConsumer;

final class Cast<T>
extends AbstractField<T>
implements QOM.Cast<T> {
    private final Field<?> field;

    public Cast(Field<?> field2, DataType<T> type) {
        super(Names.N_CAST, type.nullable(field2.getDataType().nullable()));
        this.field = field2;
    }

    private final DataType<T> getSQLDataType() {
        return this.getDataType().getSQLDataType();
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("cast(").visit(this.field).sql(", ").visit(this.getDataType()).sql(')');
                break;
            }
            case ACCESS: {
                this.acceptAccess(ctx);
                break;
            }
            case ASE: {
                ctx.visit(new CastASE());
                break;
            }
            case INFORMIX: {
                ctx.visit(new CastInformix());
                break;
            }
            case ORACLE: {
                ctx.visit(new CastOracle());
                break;
            }
            case DERBY: {
                ctx.visit(new CastDerby());
                break;
            }
            default: {
                ctx.visit(new CastNative(this.field, this.getDataType()));
            }
        }
    }

    @Pro
    private final void acceptAccess(Context<?> ctx) {
        if ("null".equals(this.field.toString())) {
            ctx.visit(this.field);
            return;
        }
        Class type = this.getType();
        String function = type == Boolean.class || type == Boolean.TYPE ? "cbool" : (type == Byte.class || type == Byte.TYPE ? "cbyte" : (type == Short.class || type == Short.TYPE ? "cint" : (type == Integer.class || type == Integer.TYPE ? "clng" : (type == Double.class || type == Double.TYPE ? "cdbl" : (type == Float.class || type == Float.TYPE ? "csng" : (Number.class.isAssignableFrom(type) ? "cdec" : (type == Date.class || type == Time.class || type == Timestamp.class ? "cdate" : "cstr")))))));
        ctx.sql(function).sql('(').visit(this.field).sql(')');
    }

    static <E extends Throwable> void renderCast(Context<?> ctx, ThrowingConsumer<? super Context<?>, E> expression, ThrowingConsumer<? super Context<?>, E> type) throws E {
        RenderContext.CastMode castMode = ctx.castMode();
        ctx.visit(Keywords.K_CAST).sql('(').castMode(RenderContext.CastMode.NEVER);
        expression.accept(ctx);
        ctx.castMode(castMode).sql(' ').visit(Keywords.K_AS).sql(' ');
        type.accept(ctx);
        ctx.sql(')');
    }

    static <E extends Throwable> void renderCastIf(Context<?> ctx, ThrowingConsumer<? super Context<?>, E> expression, ThrowingConsumer<? super Context<?>, E> type, BooleanSupplier test) throws E {
        if (test.getAsBoolean()) {
            Cast.renderCast(ctx, expression, type);
        } else {
            expression.accept(ctx);
        }
    }

    @Override
    public final Field<?> $field() {
        return this.field;
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$field(), this.$dataType(), Cast::new, replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$field(), this.$dataType());
    }

    @Pro
    private final class CastASE
    extends CastNative<T> {
        CastASE() {
            super(Cast.this.field, Cast.this.getDataType());
        }

        @Override
        public final void accept(Context<?> ctx) {
            DataType type = Cast.this.getSQLDataType();
            if (Cast.this.field.getDataType().isNumeric() && type.isString() && type.isLob()) {
                ctx.visit(new CastNative(new CastNative<String>(Cast.this.field, SQLDataType.VARCHAR(38)), Cast.this.getDataType()));
            } else {
                super.accept(ctx);
            }
        }
    }

    @Pro
    private final class CastInformix
    extends CastNative<T> {
        CastInformix() {
            super(Cast.this.field, Cast.this.getDataType());
        }

        @Override
        public void accept(Context<?> ctx) {
            DataType type = Cast.this.getDataType(ctx.configuration());
            if (type.isTimestamp() && type.precision() < 5) {
                ctx.visit(new CastNative(new CastNative(Cast.this.field, type.precision(5)), type));
            } else {
                super.accept(ctx);
            }
        }
    }

    @Pro
    private final class CastOracle
    extends CastNative<T> {
        CastOracle() {
            super(Cast.this.field, Cast.this.getDataType());
        }

        @Override
        public void accept(Context<?> ctx) {
            DataType type = Cast.this.getDataType(ctx.configuration());
            if (type.isLob() && type.isString()) {
                ctx.visit(Names.N_TO_CLOB).sql('(').visit(Cast.this.field).sql(')');
            } else if (Cast.this.field.getDataType().isString() && type.isDate()) {
                ctx.visit(Names.N_TO_DATE).sql('(').visit(Cast.this.field).sql(", 'YYYY-MM-DD')");
            } else if (Cast.this.field.getDataType().isString() && type.isTimestamp()) {
                if (type.precisionDefined()) {
                    ctx.visit(Keywords.K_CAST).sql('(');
                }
                ctx.visit(Names.N_TO_TIMESTAMP).sql('(').visit(Cast.this.field).sql(", 'YYYY-MM-DD HH24:MI:SS.FF')");
                if (type.precisionDefined()) {
                    if (ctx.languageContext() == LanguageContext.QUERY) {
                        ctx.sql(' ').visit(Keywords.K_AS).sql(' ').sql(type.getCastTypeName(ctx.configuration())).sql(')');
                    } else {
                        ctx.sql(' ').visit(Keywords.K_AS).sql(' ').sql(type.getTypeName(ctx.configuration())).sql(')');
                    }
                }
            } else if (type.isXML()) {
                ctx.visit(Names.N_XMLTYPE).sql('(').visit(Cast.this.field).sql(')');
            } else {
                super.accept(ctx);
            }
        }
    }

    private final class CastDerby
    extends CastNative<T> {
        CastDerby() {
            super(Cast.this.field, Cast.this.getDataType());
        }

        private final Field<Boolean> asDecodeNumberToBoolean() {
            return DSL.choose(Cast.this.field).when((Field<?>)DSL.inline(0), DSL.inline(false)).when(DSL.inline((Integer)null), DSL.inline((Boolean)null)).otherwise(DSL.inline(true));
        }

        private final Field<Boolean> asDecodeVarcharToBoolean() {
            Field<String> s2 = Cast.this.field;
            return DSL.when(s2.equal(DSL.inline("0")), DSL.inline(false)).when(DSL.lower(s2).equal(DSL.inline("false")), (Field<Boolean>)DSL.inline(false)).when(DSL.lower(s2).equal(DSL.inline("f")), (Field<Boolean>)DSL.inline(false)).when(s2.isNull(), (Field<Boolean>)DSL.inline((Boolean)null)).otherwise(DSL.inline(true));
        }

        @Override
        public final void accept(Context<?> ctx) {
            DataType type = Cast.this.getSQLDataType();
            if (Cast.this.field.getDataType().isNumeric() && type.isString() && !SQLDataType.CHAR.equals(type)) {
                ctx.visit(Keywords.K_TRIM).sql('(').visit(new CastNative(new CastNative<String>(Cast.this.field, SQLDataType.CHAR(38)), Cast.this.getDataType())).sql(')');
            } else if (Cast.this.field.getDataType().isString() && (SQLDataType.FLOAT.equals(type) || SQLDataType.DOUBLE.equals(type) || SQLDataType.REAL.equals(type))) {
                ctx.visit(new CastNative(new CastNative<BigDecimal>(Cast.this.field, SQLDataType.DECIMAL), Cast.this.getDataType()));
            } else if (Cast.this.field.getDataType().isNumeric() && SQLDataType.BOOLEAN.equals(type)) {
                ctx.visit(this.asDecodeNumberToBoolean());
            } else if (Cast.this.field.getDataType().isString() && SQLDataType.BOOLEAN.equals(type)) {
                ctx.visit(this.asDecodeVarcharToBoolean());
            } else {
                super.accept(ctx);
            }
        }
    }

    static class CastNative<T>
    extends AbstractQueryPart
    implements QOM.UTransient {
        private final QueryPart expression;
        private final DataType<T> type;
        private final Keyword typeAsKeyword;

        CastNative(QueryPart expression, DataType<T> type) {
            this.expression = expression;
            this.type = type;
            this.typeAsKeyword = null;
        }

        CastNative(QueryPart expression, Keyword typeAsKeyword) {
            this.expression = expression;
            this.type = null;
            this.typeAsKeyword = typeAsKeyword;
        }

        @Override
        public void accept(Context<?> ctx) {
            Cast.renderCast(ctx, c -> c.visit(this.expression), c -> {
                if (this.typeAsKeyword != null) {
                    c.visit(this.typeAsKeyword);
                } else if (c.family() == SQLDialect.ORACLE && c.languageContext() != LanguageContext.QUERY) {
                    c.sql(((AbstractDataType)this.type.getDataType(c.configuration())).castTypePrefix0());
                } else {
                    c.sql(this.type.getCastTypeName(c.configuration()));
                }
            });
        }
    }
}

