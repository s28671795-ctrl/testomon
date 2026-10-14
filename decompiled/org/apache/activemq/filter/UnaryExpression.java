/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.jms.JMSException;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.PropertyExpression;
import org.apache.activemq.filter.XPathExpression;
import org.apache.activemq.filter.XQueryExpression;

public abstract class UnaryExpression
implements Expression {
    private static final BigDecimal BD_LONG_MIN_VALUE = BigDecimal.valueOf(Long.MIN_VALUE);
    protected Expression right;

    public UnaryExpression(Expression left) {
        this.right = left;
    }

    public static Expression createNegate(Expression left) {
        return new UnaryExpression(left){

            @Override
            public Object evaluate(MessageEvaluationContext message) throws JMSException {
                Object rvalue = this.right.evaluate(message);
                if (rvalue == null) {
                    return null;
                }
                if (rvalue instanceof Number) {
                    return UnaryExpression.negate((Number)rvalue);
                }
                return null;
            }

            @Override
            public String getExpressionSymbol() {
                return "-";
            }
        };
    }

    public static BooleanExpression createInExpression(PropertyExpression right, List<Object> elements, final boolean not) {
        Collection<Object> t2 = elements.size() == 0 ? null : (elements.size() < 5 ? elements : new HashSet<Object>(elements));
        final List<Object> inList = t2;
        return new BooleanUnaryExpression(right){

            @Override
            public Object evaluate(MessageEvaluationContext message) throws JMSException {
                Object rvalue = this.right.evaluate(message);
                if (rvalue == null) {
                    return null;
                }
                if (rvalue.getClass() != String.class) {
                    return null;
                }
                if ((inList != null && inList.contains(rvalue)) ^ not) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }

            @Override
            public String toString() {
                StringBuffer answer = new StringBuffer();
                answer.append(this.right);
                answer.append(" ");
                answer.append(this.getExpressionSymbol());
                answer.append(" ( ");
                int count = 0;
                for (Object o : inList) {
                    if (count != 0) {
                        answer.append(", ");
                    }
                    answer.append(o);
                    ++count;
                }
                answer.append(" )");
                return answer.toString();
            }

            @Override
            public String getExpressionSymbol() {
                if (not) {
                    return "NOT IN";
                }
                return "IN";
            }
        };
    }

    public static BooleanExpression createNOT(BooleanExpression left) {
        return new BooleanUnaryExpression(left){

            @Override
            public Object evaluate(MessageEvaluationContext message) throws JMSException {
                Boolean lvalue = (Boolean)this.right.evaluate(message);
                if (lvalue == null) {
                    return null;
                }
                return lvalue != false ? Boolean.FALSE : Boolean.TRUE;
            }

            @Override
            public String getExpressionSymbol() {
                return "NOT";
            }
        };
    }

    public static BooleanExpression createXPath(String xpath) {
        return new XPathExpression(xpath);
    }

    public static BooleanExpression createXQuery(String xpath) {
        return new XQueryExpression(xpath);
    }

    public static BooleanExpression createBooleanCast(Expression left) {
        return new BooleanUnaryExpression(left){

            @Override
            public Object evaluate(MessageEvaluationContext message) throws JMSException {
                Object rvalue = this.right.evaluate(message);
                if (rvalue == null) {
                    return null;
                }
                if (!rvalue.getClass().equals(Boolean.class)) {
                    return Boolean.FALSE;
                }
                return (Boolean)rvalue != false ? Boolean.TRUE : Boolean.FALSE;
            }

            @Override
            public String toString() {
                return this.right.toString();
            }

            @Override
            public String getExpressionSymbol() {
                return "";
            }
        };
    }

    private static Number negate(Number left) {
        Class<?> clazz = left.getClass();
        if (clazz == Integer.class) {
            return new Integer(-left.intValue());
        }
        if (clazz == Long.class) {
            return new Long(-left.longValue());
        }
        if (clazz == Float.class) {
            return new Float(-left.floatValue());
        }
        if (clazz == Double.class) {
            return new Double(-left.doubleValue());
        }
        if (clazz == BigDecimal.class) {
            BigDecimal bd = (BigDecimal)left;
            if (BD_LONG_MIN_VALUE.compareTo(bd = bd.negate()) == 0) {
                return Long.MIN_VALUE;
            }
            return bd;
        }
        throw new RuntimeException("Don't know how to negate: " + left);
    }

    public Expression getRight() {
        return this.right;
    }

    public void setRight(Expression expression) {
        this.right = expression;
    }

    public String toString() {
        return "(" + this.getExpressionSymbol() + " " + this.right.toString() + ")";
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object o) {
        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        return this.toString().equals(o.toString());
    }

    public abstract String getExpressionSymbol();

    static abstract class BooleanUnaryExpression
    extends UnaryExpression
    implements BooleanExpression {
        public BooleanUnaryExpression(Expression left) {
            super(left);
        }

        @Override
        public boolean matches(MessageEvaluationContext message) throws JMSException {
            Object object = this.evaluate(message);
            return object != null && object == Boolean.TRUE;
        }
    }
}

