/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public final class SimpleCase
extends Expression {
    private Expression operand;
    private SimpleWhen when;
    private Expression elseResult;
    private TypeInfo type;

    public SimpleCase(Expression expression, SimpleWhen simpleWhen, Expression expression2) {
        this.operand = expression;
        this.when = simpleWhen;
        this.elseResult = expression2;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.operand.getValue(sessionLocal);
        SimpleWhen simpleWhen = this.when;
        while (simpleWhen != null) {
            for (Expression expression : simpleWhen.operands) {
                if (!expression.getWhenValue(sessionLocal, value)) continue;
                return simpleWhen.result.getValue(sessionLocal).convertTo(this.type, (CastDataProvider)sessionLocal);
            }
            simpleWhen = simpleWhen.next;
        }
        if (this.elseResult != null) {
            return this.elseResult.getValue(sessionLocal).convertTo(this.type, (CastDataProvider)sessionLocal);
        }
        return ValueNull.INSTANCE;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        TypeInfo typeInfo = TypeInfo.TYPE_UNKNOWN;
        this.operand = this.operand.optimize(sessionLocal);
        boolean bl = this.operand.isConstant();
        Value value = null;
        if (bl) {
            value = this.operand.getValue(sessionLocal);
        }
        TypeInfo typeInfo2 = this.operand.getType();
        SimpleWhen simpleWhen = this.when;
        while (simpleWhen != null) {
            Expression[] expressionArray = simpleWhen.operands;
            for (int i = 0; i < expressionArray.length; ++i) {
                Expression expression = expressionArray[i].optimize(sessionLocal);
                if (!expression.isWhenConditionOperand()) {
                    TypeInfo.checkComparable(typeInfo2, expression.getType());
                }
                if (bl) {
                    if (expression.isConstant()) {
                        if (expression.getWhenValue(sessionLocal, value)) {
                            return simpleWhen.result.optimize(sessionLocal);
                        }
                    } else {
                        bl = false;
                    }
                }
                expressionArray[i] = expression;
            }
            simpleWhen.result = simpleWhen.result.optimize(sessionLocal);
            typeInfo = SimpleCase.combineTypes(typeInfo, simpleWhen.result);
            simpleWhen = simpleWhen.next;
        }
        if (this.elseResult != null) {
            this.elseResult = this.elseResult.optimize(sessionLocal);
            if (bl) {
                return this.elseResult;
            }
            typeInfo = SimpleCase.combineTypes(typeInfo, this.elseResult);
        } else if (bl) {
            return ValueExpression.NULL;
        }
        if (typeInfo.getValueType() == -1) {
            typeInfo = TypeInfo.TYPE_VARCHAR;
        }
        this.type = typeInfo;
        return this;
    }

    static TypeInfo combineTypes(TypeInfo typeInfo, Expression expression) {
        TypeInfo typeInfo2;
        int n;
        if (!expression.isNullConstant() && (n = (typeInfo2 = expression.getType()).getValueType()) != -1 && n != 0) {
            typeInfo = TypeInfo.getHigherType(typeInfo, typeInfo2);
        }
        return typeInfo;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        this.operand.getUnenclosedSQL(stringBuilder.append("CASE "), n);
        SimpleWhen simpleWhen = this.when;
        while (simpleWhen != null) {
            stringBuilder.append(" WHEN");
            Expression[] expressionArray = simpleWhen.operands;
            int n2 = expressionArray.length;
            for (int i = 0; i < n2; ++i) {
                if (i > 0) {
                    stringBuilder.append(',');
                }
                expressionArray[i].getWhenSQL(stringBuilder, n);
            }
            simpleWhen.result.getUnenclosedSQL(stringBuilder.append(" THEN "), n);
            simpleWhen = simpleWhen.next;
        }
        if (this.elseResult != null) {
            this.elseResult.getUnenclosedSQL(stringBuilder.append(" ELSE "), n);
        }
        return stringBuilder.append(" END");
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.operand.mapColumns(columnResolver, n, n2);
        SimpleWhen simpleWhen = this.when;
        while (simpleWhen != null) {
            for (Expression expression : simpleWhen.operands) {
                expression.mapColumns(columnResolver, n, n2);
            }
            simpleWhen.result.mapColumns(columnResolver, n, n2);
            simpleWhen = simpleWhen.next;
        }
        if (this.elseResult != null) {
            this.elseResult.mapColumns(columnResolver, n, n2);
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.operand.setEvaluatable(tableFilter, bl);
        SimpleWhen simpleWhen = this.when;
        while (simpleWhen != null) {
            for (Expression expression : simpleWhen.operands) {
                expression.setEvaluatable(tableFilter, bl);
            }
            simpleWhen.result.setEvaluatable(tableFilter, bl);
            simpleWhen = simpleWhen.next;
        }
        if (this.elseResult != null) {
            this.elseResult.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.operand.updateAggregate(sessionLocal, n);
        SimpleWhen simpleWhen = this.when;
        while (simpleWhen != null) {
            for (Expression expression : simpleWhen.operands) {
                expression.updateAggregate(sessionLocal, n);
            }
            simpleWhen.result.updateAggregate(sessionLocal, n);
            simpleWhen = simpleWhen.next;
        }
        if (this.elseResult != null) {
            this.elseResult.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (!this.operand.isEverything(expressionVisitor)) {
            return false;
        }
        SimpleWhen simpleWhen = this.when;
        while (simpleWhen != null) {
            for (Expression expression : simpleWhen.operands) {
                if (expression.isEverything(expressionVisitor)) continue;
                return false;
            }
            if (!simpleWhen.result.isEverything(expressionVisitor)) {
                return false;
            }
            simpleWhen = simpleWhen.next;
        }
        return this.elseResult == null || this.elseResult.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        int n = 1;
        int n2 = 0;
        n += this.operand.getCost();
        SimpleWhen simpleWhen = this.when;
        while (simpleWhen != null) {
            for (Expression expression : simpleWhen.operands) {
                n += expression.getCost();
            }
            n2 = Math.max(n2, simpleWhen.result.getCost());
            simpleWhen = simpleWhen.next;
        }
        if (this.elseResult != null) {
            n2 = Math.max(n2, this.elseResult.getCost());
        }
        return n + n2;
    }

    @Override
    public int getSubexpressionCount() {
        int n = 1;
        SimpleWhen simpleWhen = this.when;
        while (simpleWhen != null) {
            n += simpleWhen.operands.length + 1;
            simpleWhen = simpleWhen.next;
        }
        if (this.elseResult != null) {
            ++n;
        }
        return n;
    }

    @Override
    public Expression getSubexpression(int n) {
        if (n >= 0) {
            if (n == 0) {
                return this.operand;
            }
            int n2 = 1;
            SimpleWhen simpleWhen = this.when;
            while (simpleWhen != null) {
                int n3 = n - n2;
                Expression[] expressionArray = simpleWhen.operands;
                int n4 = expressionArray.length;
                if (n3 < n4) {
                    return expressionArray[n3];
                }
                n2 += n4;
                if (n == n2++) {
                    return simpleWhen.result;
                }
                simpleWhen = simpleWhen.next;
            }
            if (this.elseResult != null && n == n2) {
                return this.elseResult;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public static final class SimpleWhen {
        Expression[] operands;
        Expression result;
        SimpleWhen next;

        public SimpleWhen(Expression expression, Expression expression2) {
            this(new Expression[]{expression}, expression2);
        }

        public SimpleWhen(Expression[] expressionArray, Expression expression) {
            this.operands = expressionArray;
            this.result = expression;
        }

        public void setWhen(SimpleWhen simpleWhen) {
            this.next = simpleWhen;
        }
    }
}

