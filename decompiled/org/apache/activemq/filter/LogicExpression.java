/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import javax.jms.JMSException;
import org.apache.activemq.filter.BinaryExpression;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.MessageEvaluationContext;

public abstract class LogicExpression
extends BinaryExpression
implements BooleanExpression {
    public LogicExpression(BooleanExpression left, BooleanExpression right) {
        super(left, right);
    }

    public static BooleanExpression createOR(BooleanExpression lvalue, BooleanExpression rvalue) {
        return new LogicExpression(lvalue, rvalue){

            @Override
            public Object evaluate(MessageEvaluationContext message) throws JMSException {
                Boolean lv = (Boolean)this.left.evaluate(message);
                if (lv != null && lv.booleanValue()) {
                    return Boolean.TRUE;
                }
                Boolean rv = (Boolean)this.right.evaluate(message);
                if (rv != null && rv.booleanValue()) {
                    return Boolean.TRUE;
                }
                if (lv == null || rv == null) {
                    return null;
                }
                return Boolean.FALSE;
            }

            @Override
            public String getExpressionSymbol() {
                return "OR";
            }
        };
    }

    public static BooleanExpression createAND(BooleanExpression lvalue, BooleanExpression rvalue) {
        return new LogicExpression(lvalue, rvalue){

            @Override
            public Object evaluate(MessageEvaluationContext message) throws JMSException {
                Boolean lv = (Boolean)this.left.evaluate(message);
                if (lv != null && !lv.booleanValue()) {
                    return Boolean.FALSE;
                }
                Boolean rv = (Boolean)this.right.evaluate(message);
                if (rv != null && !rv.booleanValue()) {
                    return Boolean.FALSE;
                }
                if (lv == null || rv == null) {
                    return null;
                }
                return Boolean.TRUE;
            }

            @Override
            public String getExpressionSymbol() {
                return "AND";
            }
        };
    }

    @Override
    public abstract Object evaluate(MessageEvaluationContext var1) throws JMSException;

    @Override
    public boolean matches(MessageEvaluationContext message) throws JMSException {
        Object object = this.evaluate(message);
        return object != null && object == Boolean.TRUE;
    }
}

