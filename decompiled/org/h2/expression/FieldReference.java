/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.util.Map;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Operation1;
import org.h2.expression.TypedValueExpression;
import org.h2.message.DbException;
import org.h2.mvstore.db.Store;
import org.h2.util.ParserUtil;
import org.h2.util.json.JSONObject;
import org.h2.util.json.JSONValue;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueJson;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

public final class FieldReference
extends Operation1 {
    private final String fieldName;
    private int ordinal;

    public FieldReference(Expression expression, String string) {
        super(expression);
        this.fieldName = string;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return ParserUtil.quoteIdentifier(this.arg.getEnclosedSQL(stringBuilder, n).append('.'), this.fieldName, n);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.arg.getValue(sessionLocal);
        if (value != ValueNull.INSTANCE) {
            JSONValue jSONValue;
            if (this.ordinal >= 0) {
                return ((ValueRow)value).getList()[this.ordinal];
            }
            JSONValue jSONValue2 = value.convertToAnyJson().getDecomposition();
            if (jSONValue2 instanceof JSONObject && (jSONValue = ((JSONObject)jSONValue2).getFirst(this.fieldName)) != null) {
                return ValueJson.fromJson(jSONValue);
            }
        }
        return ValueNull.INSTANCE;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        TypeInfo typeInfo = this.arg.getType();
        int n = typeInfo.getValueType();
        block0 : switch (n) {
            case 38: {
                this.type = TypeInfo.TYPE_JSON;
                this.ordinal = -1;
                break;
            }
            case 41: {
                int n2 = 0;
                for (Map.Entry<String, TypeInfo> entry : ((ExtTypeInfoRow)typeInfo.getExtTypeInfo()).getFields()) {
                    if (this.fieldName.equals(entry.getKey())) {
                        this.type = typeInfo = entry.getValue();
                        this.ordinal = n2;
                        break block0;
                    }
                    ++n2;
                }
                throw DbException.get(42122, this.fieldName);
            }
            default: {
                throw Store.getInvalidExpressionTypeException("JSON | ROW", this.arg);
            }
        }
        if (this.arg.isConstant()) {
            return TypedValueExpression.get(this.getValue(sessionLocal), typeInfo);
        }
        return this;
    }
}

