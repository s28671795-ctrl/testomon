/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import org.jooq.ArrayOrAssociativeArrayRecord;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.ParamMode;
import org.jooq.QualifiedRecord;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractParamX;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.Internal;
import org.jooq.impl.LazyName;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleQueryPart;
import org.jooq.tools.StringUtils;

abstract class AbstractParam<T>
extends AbstractParamX<T>
implements SimpleQueryPart {
    private static final Clause[] CLAUSES = new Clause[]{Clause.FIELD, Clause.FIELD_VALUE};
    private final String paramName;
    T value;
    private boolean inline;

    AbstractParam(T value, DataType<T> type) {
        this(value, type, null);
    }

    AbstractParam(T value, DataType<T> type, String paramName) {
        super(AbstractParam.name(value, paramName), type);
        this.paramName = paramName;
        this.value = value;
    }

    @Override
    final boolean isPossiblyNullable() {
        return !this.inline || this.value == null;
    }

    static final Name name(Object value, String paramName) {
        return new LazyName(() -> {
            String string;
            if (paramName != null) {
                string = paramName;
            } else if (value instanceof QualifiedRecord) {
                QualifiedRecord q = (QualifiedRecord)value;
                string = q.getQualifier().getName();
            } else if (value instanceof ArrayOrAssociativeArrayRecord) {
                ArrayOrAssociativeArrayRecord a = (ArrayOrAssociativeArrayRecord)value;
                string = a.getName();
            } else {
                string = AbstractParam.name(value);
            }
            return DSL.name(string);
        });
    }

    private static final String name(Object value) {
        if (value instanceof byte[]) {
            byte[] b = (byte[])value;
            return "b_" + Internal.hash0(Arrays.hashCode(Arrays.copyOf(b, 16)));
        }
        if (value instanceof Object[]) {
            Object[] o = (Object[])value;
            return "a_" + Internal.hash0(Arrays.hashCode(Arrays.copyOf(o, 16)));
        }
        return String.valueOf(value);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return this.getBinding() instanceof DefaultBinding.InternalBinding && AbstractParam.positive(this.value);
    }

    private static boolean positive(Object value) {
        Number n;
        return value instanceof Number ? (n = (Number)value).doubleValue() >= 0.0 : false;
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final boolean generatesCast() {
        return true;
    }

    @Override
    final void setConverted0(Object value) {
        this.value = this.getDataType().convert(value);
    }

    @Override
    public final T getValue() {
        return this.value;
    }

    @Override
    public final String getParamName() {
        return this.paramName;
    }

    @Override
    final void setInline0(boolean inline2) {
        this.inline = inline2;
    }

    @Override
    public final boolean isInline() {
        return this.inline;
    }

    final boolean isInline(Context<?> ctx) {
        return this.isInline() || ctx.paramType() == ParamType.INLINED || ctx.paramType() == ParamType.NAMED_OR_INLINED && StringUtils.isBlank(this.paramName) || ctx.family() == SQLDialect.HANA && this.getDataType().isArray();
    }

    @Override
    public final ParamType getParamType() {
        return this.inline ? ParamType.INLINED : (StringUtils.isBlank(this.paramName) ? ParamType.INDEXED : ParamType.NAMED);
    }

    @Override
    public final ParamMode getParamMode() {
        return ParamMode.IN;
    }

    @Override
    public void accept(Context<?> ctx) {
    }

    @Override
    public final T $value() {
        return this.value;
    }

    @Override
    public final boolean $inline() {
        return this.inline;
    }

    @Override
    public Param<T> $value(T newValue) {
        throw new QOM.NotYetImplementedException();
    }

    @Override
    public Param<T> $inline(boolean inline2) {
        throw new QOM.NotYetImplementedException();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof Param) {
            Param p = (Param)that;
            Object thatValue = p.getValue();
            if (this.value == null) {
                return thatValue == null;
            }
            if (this.value instanceof byte[] && thatValue instanceof byte[]) {
                return Arrays.equals((byte[])this.value, (byte[])thatValue);
            }
            if (this.value instanceof Object[] && thatValue instanceof Object[]) {
                return Arrays.equals((Object[])this.value, (Object[])thatValue);
            }
            return this.value.equals(thatValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int n;
        if (this.value == null) {
            n = 0;
        } else {
            T t2 = this.value;
            if (t2 instanceof byte[]) {
                byte[] a = (byte[])t2;
                n = Arrays.hashCode(a);
            } else {
                t2 = this.value;
                if (t2 instanceof Object[]) {
                    Object[] a = (Object[])t2;
                    n = Arrays.hashCode(a);
                } else {
                    n = this.value.hashCode();
                }
            }
        }
        return n;
    }
}

