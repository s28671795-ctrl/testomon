/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Operation2;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.mvstore.db.Store;
import org.h2.util.json.JSONArray;
import org.h2.util.json.JSONValue;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueJson;
import org.h2.value.ValueNull;

public final class ArrayElementReference
extends Operation2 {
    public ArrayElementReference(Expression expression, Expression expression2) {
        super(expression, expression2);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        this.left.getSQL(stringBuilder, n, 0).append('[');
        return this.right.getUnenclosedSQL(stringBuilder, n).append(']');
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal);
        Value value2 = this.right.getValue(sessionLocal);
        if (value != ValueNull.INSTANCE && value2 != ValueNull.INSTANCE) {
            JSONValue jSONValue;
            int n = value2.getInt();
            if (this.left.getType().getValueType() == 40) {
                Value[] valueArray = ((ValueArray)value).getList();
                int n2 = valueArray.length;
                if (n >= 1 && n <= n2) {
                    return valueArray[n - 1];
                }
                throw DbException.get(22034, Integer.toString(n), "1.." + n2);
            }
            JSONValue jSONValue2 = value.convertToAnyJson().getDecomposition();
            if (jSONValue2 instanceof JSONArray && (jSONValue = ((JSONArray)jSONValue2).getElement(n - 1)) != null) {
                return ValueJson.fromJson(jSONValue);
            }
        }
        return ValueNull.INSTANCE;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        TypeInfo typeInfo = this.left.getType();
        switch (typeInfo.getValueType()) {
            case 0: {
                return ValueExpression.NULL;
            }
            case 38: {
                this.type = TypeInfo.TYPE_JSON;
                if (!this.left.isConstant() || !this.right.isConstant()) break;
                return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
            }
            case 40: {
                this.type = (TypeInfo)typeInfo.getExtTypeInfo();
                if (!this.left.isConstant() || !this.right.isConstant()) break;
                return TypedValueExpression.get(this.getValue(sessionLocal), this.type);
            }
            default: {
                throw Store.getInvalidExpressionTypeException("Array", this.left);
            }
        }
        return this;
    }
}

