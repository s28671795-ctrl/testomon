/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function1;
import org.h2.message.DbException;
import org.h2.util.MathUtils;
import org.h2.util.json.JSONArray;
import org.h2.util.json.JSONValue;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;

public final class CardinalityExpression
extends Function1 {
    private final boolean max;

    public CardinalityExpression(Expression expression, boolean bl) {
        super(expression);
        this.max = bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Value getValue(SessionLocal sessionLocal) {
        int n;
        if (this.max) {
            TypeInfo typeInfo = this.arg.getType();
            if (typeInfo.getValueType() != 40) throw DbException.getInvalidValueException("array", this.arg.getValue(sessionLocal).getTraceSQL());
            n = MathUtils.convertLongToInt(typeInfo.getPrecision());
            return ValueInteger.get(n);
        } else {
            Value value = this.arg.getValue(sessionLocal);
            if (value == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
            switch (value.getValueType()) {
                case 38: {
                    JSONValue jSONValue = value.convertToAnyJson().getDecomposition();
                    if (!(jSONValue instanceof JSONArray)) return ValueNull.INSTANCE;
                    n = ((JSONArray)jSONValue).length();
                    return ValueInteger.get(n);
                }
                case 40: {
                    n = ((ValueArray)value).getList().length;
                    return ValueInteger.get(n);
                }
                default: {
                    throw DbException.getInvalidValueException("array", value.getTraceSQL());
                }
            }
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        this.type = TypeInfo.TYPE_INTEGER;
        if (this.arg.isConstant()) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return this.max ? "ARRAY_MAX_CARDINALITY" : "CARDINALITY";
    }
}

