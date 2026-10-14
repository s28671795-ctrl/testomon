/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.util.Arrays;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;

public final class SubstringFunction
extends FunctionN {
    public SubstringFunction() {
        super(new Expression[3]);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        if (this.type.getValueType() == 6) {
            byte[] byArray = value.getBytesNoCopy();
            int n = byArray.length;
            int n2 = value2.getInt();
            if (n2 == 0) {
                n2 = 1;
            } else if (n2 < 0) {
                n2 = n + n2 + 1;
            }
            int n3 = value3 == null ? Math.max(n + 1, n2) : n2 + value3.getInt();
            n2 = Math.max(n2, 1);
            n3 = Math.min(n3, n + 1);
            if (n2 > n || n3 <= n2) {
                return ValueVarbinary.EMPTY;
            }
            if (--n2 == 0 && --n3 == byArray.length) {
                return value.convertTo(TypeInfo.TYPE_VARBINARY);
            }
            return ValueVarbinary.getNoCopy(Arrays.copyOfRange(byArray, n2, n3));
        }
        String string = value.getString();
        int n = string.length();
        int n4 = value2.getInt();
        if (n4 == 0) {
            n4 = 1;
        } else if (n4 < 0) {
            n4 = n + n4 + 1;
        }
        int n5 = value3 == null ? Math.max(n + 1, n4) : n4 + value3.getInt();
        n4 = Math.max(n4, 1);
        n5 = Math.min(n5, n + 1);
        if (n4 > n || n5 <= n4) {
            return sessionLocal.getMode().treatEmptyStringsAsNull ? ValueNull.INSTANCE : ValueVarchar.EMPTY;
        }
        return ValueVarchar.get(string.substring(n4 - 1, n5 - 1), null);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        Value value;
        boolean bl = this.optimizeArguments(sessionLocal, true);
        int n = this.args.length;
        if (n < 2 || n > 3) {
            throw DbException.get(7001, this.getName(), "2..3");
        }
        TypeInfo typeInfo = this.args[0].getType();
        long l = typeInfo.getPrecision();
        Expression expression = this.args[1];
        if (expression.isConstant() && (value = expression.getValue(sessionLocal)) != ValueNull.INSTANCE) {
            l -= value.getLong() - 1L;
        }
        if (this.args.length == 3 && (expression = this.args[2]).isConstant() && (value = expression.getValue(sessionLocal)) != ValueNull.INSTANCE) {
            l = Math.min(l, value.getLong());
        }
        l = Math.max(0L, l);
        this.type = TypeInfo.getTypeInfo(DataType.isBinaryStringType(typeInfo.getValueType()) ? 6 : 2, l, 0, null);
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        this.args[0].getUnenclosedSQL(stringBuilder.append(this.getName()).append('('), n);
        this.args[1].getUnenclosedSQL(stringBuilder.append(" FROM "), n);
        if (this.args.length > 2) {
            this.args[2].getUnenclosedSQL(stringBuilder.append(" FOR "), n);
        }
        return stringBuilder.append(')');
    }

    @Override
    public String getName() {
        return "SUBSTRING";
    }
}

