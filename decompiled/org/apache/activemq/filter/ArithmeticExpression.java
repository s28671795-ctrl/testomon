/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import javax.jms.JMSException;
import org.apache.activemq.filter.BinaryExpression;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.MessageEvaluationContext;

public abstract class ArithmeticExpression
extends BinaryExpression {
    protected static final int INTEGER = 1;
    protected static final int LONG = 2;
    protected static final int DOUBLE = 3;

    public ArithmeticExpression(Expression left, Expression right) {
        super(left, right);
    }

    public static Expression createPlus(Expression left, Expression right) {
        return new ArithmeticExpression(left, right){

            @Override
            protected Object evaluate(Object lvalue, Object rvalue) {
                if (lvalue instanceof String) {
                    String text = (String)lvalue;
                    String answer = text + rvalue;
                    return answer;
                }
                if (lvalue instanceof Number) {
                    return this.plus((Number)lvalue, this.asNumber(rvalue));
                }
                throw new RuntimeException("Cannot call plus operation on: " + lvalue + " and: " + rvalue);
            }

            @Override
            public String getExpressionSymbol() {
                return "+";
            }
        };
    }

    public static Expression createMinus(Expression left, Expression right) {
        return new ArithmeticExpression(left, right){

            @Override
            protected Object evaluate(Object lvalue, Object rvalue) {
                if (lvalue instanceof Number) {
                    return this.minus((Number)lvalue, this.asNumber(rvalue));
                }
                throw new RuntimeException("Cannot call minus operation on: " + lvalue + " and: " + rvalue);
            }

            @Override
            public String getExpressionSymbol() {
                return "-";
            }
        };
    }

    public static Expression createMultiply(Expression left, Expression right) {
        return new ArithmeticExpression(left, right){

            @Override
            protected Object evaluate(Object lvalue, Object rvalue) {
                if (lvalue instanceof Number) {
                    return this.multiply((Number)lvalue, this.asNumber(rvalue));
                }
                throw new RuntimeException("Cannot call multiply operation on: " + lvalue + " and: " + rvalue);
            }

            @Override
            public String getExpressionSymbol() {
                return "*";
            }
        };
    }

    public static Expression createDivide(Expression left, Expression right) {
        return new ArithmeticExpression(left, right){

            @Override
            protected Object evaluate(Object lvalue, Object rvalue) {
                if (lvalue instanceof Number) {
                    return this.divide((Number)lvalue, this.asNumber(rvalue));
                }
                throw new RuntimeException("Cannot call divide operation on: " + lvalue + " and: " + rvalue);
            }

            @Override
            public String getExpressionSymbol() {
                return "/";
            }
        };
    }

    public static Expression createMod(Expression left, Expression right) {
        return new ArithmeticExpression(left, right){

            @Override
            protected Object evaluate(Object lvalue, Object rvalue) {
                if (lvalue instanceof Number) {
                    return this.mod((Number)lvalue, this.asNumber(rvalue));
                }
                throw new RuntimeException("Cannot call mod operation on: " + lvalue + " and: " + rvalue);
            }

            @Override
            public String getExpressionSymbol() {
                return "%";
            }
        };
    }

    protected Number plus(Number left, Number right) {
        switch (this.numberType(left, right)) {
            case 1: {
                return new Integer(left.intValue() + right.intValue());
            }
            case 2: {
                return new Long(left.longValue() + right.longValue());
            }
        }
        return new Double(left.doubleValue() + right.doubleValue());
    }

    protected Number minus(Number left, Number right) {
        switch (this.numberType(left, right)) {
            case 1: {
                return new Integer(left.intValue() - right.intValue());
            }
            case 2: {
                return new Long(left.longValue() - right.longValue());
            }
        }
        return new Double(left.doubleValue() - right.doubleValue());
    }

    protected Number multiply(Number left, Number right) {
        switch (this.numberType(left, right)) {
            case 1: {
                return new Integer(left.intValue() * right.intValue());
            }
            case 2: {
                return new Long(left.longValue() * right.longValue());
            }
        }
        return new Double(left.doubleValue() * right.doubleValue());
    }

    protected Number divide(Number left, Number right) {
        return new Double(left.doubleValue() / right.doubleValue());
    }

    protected Number mod(Number left, Number right) {
        return new Double(left.doubleValue() % right.doubleValue());
    }

    private int numberType(Number left, Number right) {
        if (this.isDouble(left) || this.isDouble(right)) {
            return 3;
        }
        if (left instanceof Long || right instanceof Long) {
            return 2;
        }
        return 1;
    }

    private boolean isDouble(Number n) {
        return n instanceof Float || n instanceof Double;
    }

    protected Number asNumber(Object value) {
        if (value instanceof Number) {
            return (Number)value;
        }
        throw new RuntimeException("Cannot convert value: " + value + " into a number");
    }

    @Override
    public Object evaluate(MessageEvaluationContext message) throws JMSException {
        Object lvalue = this.left.evaluate(message);
        if (lvalue == null) {
            return null;
        }
        Object rvalue = this.right.evaluate(message);
        if (rvalue == null) {
            return null;
        }
        return this.evaluate(lvalue, rvalue);
    }

    protected abstract Object evaluate(Object var1, Object var2);
}

