/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.io.ByteArrayOutputStream;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionWithFlags;
import org.h2.expression.Format;
import org.h2.expression.OperationN;
import org.h2.expression.Subquery;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.NamedExpression;
import org.h2.message.DbException;
import org.h2.util.json.JsonConstructorUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueJson;
import org.h2.value.ValueNull;

public final class JsonConstructorFunction
extends OperationN
implements ExpressionWithFlags,
NamedExpression {
    private final boolean array;
    private int flags;

    public JsonConstructorFunction(boolean bl) {
        super(new Expression[4]);
        this.array = bl;
    }

    @Override
    public void setFlags(int n) {
        this.flags = n;
    }

    @Override
    public int getFlags() {
        return this.flags;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.array ? this.jsonArray(sessionLocal, this.args) : this.jsonObject(sessionLocal, this.args);
    }

    private Value jsonObject(SessionLocal sessionLocal, Expression[] expressionArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(123);
        int n = 0;
        int n2 = expressionArray.length;
        while (n < n2) {
            Value value;
            String string;
            if ((string = expressionArray[n++].getValue(sessionLocal).getString()) == null) {
                throw DbException.getInvalidValueException("JSON_OBJECT key", "NULL");
            }
            if ((value = expressionArray[n++].getValue(sessionLocal)) == ValueNull.INSTANCE) {
                if ((this.flags & 1) != 0) continue;
                value = ValueJson.NULL;
            }
            JsonConstructorUtils.jsonObjectAppend(byteArrayOutputStream, string, value);
        }
        return JsonConstructorUtils.jsonObjectFinish(byteArrayOutputStream, this.flags);
    }

    /*
     * Unable to fully structure code
     */
    private Value jsonArray(SessionLocal var1_1, Expression[] var2_2) {
        var3_3 = new ByteArrayOutputStream();
        var3_3.write(91);
        var4_4 = var2_2.length;
        if (var4_4 != 1) ** GOTO lbl-1000
        var5_5 = var2_2[0];
        if (var5_5 instanceof Subquery) {
            var6_7 = (Subquery)var5_5;
            for (Value var8_11 : var6_7.getAllRows(var1_1)) {
                JsonConstructorUtils.jsonArrayAppend(var3_3, var8_11, this.flags);
            }
        } else if (var5_5 instanceof Format && (var5_5 = (var6_8 = (Format)var5_5).getSubexpression(0)) instanceof Subquery) {
            var7_10 = (Subquery)var5_5;
            for (Value var9_13 : var7_10.getAllRows(var1_1)) {
                JsonConstructorUtils.jsonArrayAppend(var3_3, var6_8.getValue(var9_13), this.flags);
            }
        } else lbl-1000:
        // 2 sources

        {
            var5_6 = 0;
            while (var5_6 < var4_4) {
                JsonConstructorUtils.jsonArrayAppend(var3_3, var2_2[var5_6++].getValue(var1_1), this.flags);
            }
        }
        var3_3.write(93);
        return ValueJson.getInternal(var3_3.toByteArray());
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal, true);
        this.type = TypeInfo.TYPE_JSON;
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.getName()).append('(');
        if (this.array) {
            JsonConstructorFunction.writeExpressions(stringBuilder, this.args, n);
        } else {
            int n2 = 0;
            int n3 = this.args.length;
            while (n2 < n3) {
                if (n2 > 0) {
                    stringBuilder.append(", ");
                }
                this.args[n2++].getUnenclosedSQL(stringBuilder, n).append(": ");
                this.args[n2++].getUnenclosedSQL(stringBuilder, n);
            }
        }
        return JsonConstructorFunction.getJsonFunctionFlagsSQL(stringBuilder, this.flags, this.array).append(')');
    }

    public static StringBuilder getJsonFunctionFlagsSQL(StringBuilder stringBuilder, int n, boolean bl) {
        if ((n & 1) != 0) {
            if (!bl) {
                stringBuilder.append(" ABSENT ON NULL");
            }
        } else if (bl) {
            stringBuilder.append(" NULL ON NULL");
        }
        if (!bl && (n & 2) != 0) {
            stringBuilder.append(" WITH UNIQUE KEYS");
        }
        return stringBuilder;
    }

    @Override
    public String getName() {
        return this.array ? "JSON_ARRAY" : "JSON_OBJECT";
    }
}

