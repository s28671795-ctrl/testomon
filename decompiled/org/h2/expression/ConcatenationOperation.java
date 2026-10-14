/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.util.Arrays;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.OperationN;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.ValueExpression;
import org.h2.expression.function.CastSpecification;
import org.h2.expression.function.ConcatFunction;
import org.h2.util.HasSQL;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;

public final class ConcatenationOperation
extends OperationN {
    public ConcatenationOperation() {
        super(new Expression[4]);
    }

    public ConcatenationOperation(Expression expression, Expression expression2) {
        super(new Expression[]{expression, expression2});
        this.argsCount = 2;
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        int n2 = this.args.length;
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                stringBuilder.append(" || ");
            }
            this.args[i].getSQL(stringBuilder, n, 0);
        }
        return stringBuilder;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        int n = this.args.length;
        if (n == 2) {
            Value value = this.args[0].getValue(sessionLocal);
            if ((value = value.convertTo(this.type, (CastDataProvider)sessionLocal)) == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
            Value value2 = this.args[1].getValue(sessionLocal);
            if ((value2 = value2.convertTo(this.type, (CastDataProvider)sessionLocal)) == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
            return this.getValue(sessionLocal, value, value2);
        }
        return this.getValue(sessionLocal, n);
    }

    private Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        int n = this.type.getValueType();
        if (n == 2) {
            String string = value.getString();
            String string2 = value2.getString();
            return ValueVarchar.get(new StringBuilder(string.length() + string2.length()).append(string).append(string2).toString());
        }
        if (n == 6) {
            byte[] byArray = value.getBytesNoCopy();
            byte[] byArray2 = value2.getBytesNoCopy();
            int n2 = byArray.length;
            int n3 = byArray2.length;
            byte[] byArray3 = Arrays.copyOf(byArray, n2 + n3);
            System.arraycopy(byArray2, 0, byArray3, n2, n3);
            return ValueVarbinary.getNoCopy(byArray3);
        }
        Value[] valueArray = ((ValueArray)value).getList();
        Value[] valueArray2 = ((ValueArray)value2).getList();
        int n4 = valueArray.length;
        int n5 = valueArray2.length;
        Value[] valueArray3 = Arrays.copyOf(valueArray, n4 + n5);
        System.arraycopy(valueArray2, 0, valueArray3, n4, n5);
        return ValueArray.get((TypeInfo)this.type.getExtTypeInfo(), valueArray3, sessionLocal);
    }

    private Value getValue(SessionLocal sessionLocal, int n) {
        Object object;
        int n2;
        Value[] valueArray = new Value[n];
        for (n2 = 0; n2 < n; ++n2) {
            object = this.args[n2].getValue(sessionLocal).convertTo(this.type, (CastDataProvider)sessionLocal);
            if (object == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
            valueArray[n2] = object;
        }
        n2 = this.type.getValueType();
        if (n2 == 2) {
            object = new StringBuilder();
            for (int i = 0; i < n; ++i) {
                ((StringBuilder)object).append(valueArray[i].getString());
            }
            return ValueVarchar.get(((StringBuilder)object).toString(), sessionLocal);
        }
        if (n2 == 6) {
            int n3 = 0;
            for (int i = 0; i < n; ++i) {
                n3 += valueArray[i].getBytesNoCopy().length;
            }
            byte[] byArray = new byte[n3];
            int n4 = 0;
            for (int i = 0; i < n; ++i) {
                byte[] byArray2 = valueArray[i].getBytesNoCopy();
                int n5 = byArray2.length;
                System.arraycopy(byArray2, 0, byArray, n4, n5);
                n4 += n5;
            }
            return ValueVarbinary.getNoCopy(byArray);
        }
        int n6 = 0;
        for (int i = 0; i < n; ++i) {
            n6 += ((ValueArray)valueArray[i]).getList().length;
        }
        Value[] valueArray2 = new Value[n6];
        int n7 = 0;
        for (int i = 0; i < n; ++i) {
            Value[] valueArray3 = ((ValueArray)valueArray[i]).getList();
            int n8 = valueArray3.length;
            System.arraycopy(valueArray3, 0, valueArray2, n7, n8);
            n7 += n8;
        }
        return ValueArray.get((TypeInfo)this.type.getExtTypeInfo(), valueArray2, sessionLocal);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        int n;
        this.determineType(sessionLocal);
        this.inlineArguments();
        if (this.type.getValueType() == 2 && sessionLocal.getMode().treatEmptyStringsAsNull) {
            return new ConcatFunction(0, this.args).optimize(sessionLocal);
        }
        int n2 = this.args.length;
        boolean bl = true;
        boolean bl2 = false;
        for (n = 0; n < n2; ++n) {
            if (this.args[n].isConstant()) {
                bl2 = true;
                continue;
            }
            bl = false;
        }
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        if (bl2) {
            HasSQL hasSQL;
            n = 0;
            for (int i = 0; i < n2; ++i) {
                hasSQL = this.args[i];
                if (hasSQL.isConstant()) {
                    Expression expression;
                    Value value = hasSQL.getValue(sessionLocal).convertTo(this.type, (CastDataProvider)sessionLocal);
                    if (value == ValueNull.INSTANCE) {
                        return TypedValueExpression.get(ValueNull.INSTANCE, this.type);
                    }
                    if (ConcatenationOperation.isEmpty(value)) continue;
                    while (i + 1 < n2 && (expression = this.args[i + 1]).isConstant()) {
                        Value value2 = expression.getValue(sessionLocal).convertTo(this.type, (CastDataProvider)sessionLocal);
                        if (value2 == ValueNull.INSTANCE) {
                            return TypedValueExpression.get(ValueNull.INSTANCE, this.type);
                        }
                        if (!ConcatenationOperation.isEmpty(value2)) {
                            value = this.getValue(sessionLocal, value, value2);
                        }
                        ++i;
                    }
                    hasSQL = ValueExpression.get(value);
                }
                this.args[n++] = hasSQL;
            }
            if (n == 1) {
                Expression expression = this.args[0];
                hasSQL = expression.getType();
                if (TypeInfo.areSameTypes(this.type, (TypeInfo)hasSQL)) {
                    return expression;
                }
                return new CastSpecification(expression, this.type);
            }
            this.argsCount = n;
            this.doneWithParameters();
        }
        return this;
    }

    private void determineType(SessionLocal sessionLocal) {
        int n;
        int n2 = this.args.length;
        boolean bl = false;
        boolean bl2 = true;
        boolean bl3 = true;
        for (int i = 0; i < n2; ++i) {
            Expression expression;
            this.args[i] = expression = this.args[i].optimize(sessionLocal);
            n = expression.getType().getValueType();
            if (n == 40) {
                bl = true;
                bl3 = false;
                bl2 = false;
                continue;
            }
            if (n == 0) continue;
            if (DataType.isBinaryStringType(n)) {
                bl3 = false;
                continue;
            }
            if (DataType.isCharacterStringType(n)) {
                bl2 = false;
                continue;
            }
            bl3 = false;
            bl2 = false;
        }
        if (bl) {
            this.type = TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.getHigherType(this.args).getExtTypeInfo());
        } else if (bl2) {
            long l = this.getPrecision(0);
            for (n = 1; n < n2; ++n) {
                l = DataType.addPrecision(l, this.getPrecision(n));
            }
            this.type = TypeInfo.getTypeInfo(6, l, 0, null);
        } else if (bl3) {
            long l = this.getPrecision(0);
            for (n = 1; n < n2; ++n) {
                l = DataType.addPrecision(l, this.getPrecision(n));
            }
            this.type = TypeInfo.getTypeInfo(2, l, 0, null);
        } else {
            this.type = TypeInfo.TYPE_VARCHAR;
        }
    }

    private long getPrecision(int n) {
        TypeInfo typeInfo = this.args[n].getType();
        return typeInfo.getValueType() != 0 ? typeInfo.getPrecision() : 0L;
    }

    private void inlineArguments() {
        int n;
        int n2 = this.type.getValueType();
        int n3 = n = this.args.length;
        for (int i = 0; i < n; ++i) {
            Expression expression = this.args[i];
            if (!(expression instanceof ConcatenationOperation) || expression.getType().getValueType() != n2) continue;
            n3 += expression.getSubexpressionCount() - 1;
        }
        if (n3 > n) {
            Expression[] expressionArray = new Expression[n3];
            int n4 = 0;
            for (int i = 0; i < n; ++i) {
                Expression expression = this.args[i];
                if (expression instanceof ConcatenationOperation && expression.getType().getValueType() == n2) {
                    ConcatenationOperation concatenationOperation = (ConcatenationOperation)expression;
                    Expression[] expressionArray2 = concatenationOperation.args;
                    int n5 = expressionArray2.length;
                    System.arraycopy(expressionArray2, 0, expressionArray, n4, n5);
                    n4 += n5;
                    continue;
                }
                expressionArray[n4++] = expression;
            }
            this.args = expressionArray;
            this.argsCount = n3;
        }
    }

    private static boolean isEmpty(Value value) {
        int n = value.getValueType();
        if (n == 2) {
            return value.getString().isEmpty();
        }
        if (n == 6) {
            return value.getBytesNoCopy().length == 0;
        }
        return ((ValueArray)value).getList().length == 0;
    }
}

