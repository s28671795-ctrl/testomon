/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.math.BigDecimal;
import javax.jms.JMSException;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.MessageEvaluationContext;

public class ConstantExpression
implements Expression {
    public static final BooleanConstantExpression NULL = new BooleanConstantExpression(null);
    public static final BooleanConstantExpression TRUE = new BooleanConstantExpression(Boolean.TRUE);
    public static final BooleanConstantExpression FALSE = new BooleanConstantExpression(Boolean.FALSE);
    private Object value;

    public ConstantExpression(Object value) {
        this.value = value;
    }

    public static ConstantExpression createFromDecimal(String text) {
        Number value;
        if (text.endsWith("l") || text.endsWith("L")) {
            text = text.substring(0, text.length() - 1);
        }
        try {
            value = new Long(text);
        }
        catch (NumberFormatException e) {
            value = new BigDecimal(text);
        }
        long l = value.longValue();
        if (Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE) {
            value = value.intValue();
        }
        return new ConstantExpression(value);
    }

    public static ConstantExpression createFromHex(String text) {
        Number value = Long.parseLong(text.substring(2), 16);
        long l = value;
        if (Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE) {
            value = ((Number)value).intValue();
        }
        return new ConstantExpression(value);
    }

    public static ConstantExpression createFromOctal(String text) {
        Number value = Long.parseLong(text, 8);
        long l = value;
        if (Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE) {
            value = ((Number)value).intValue();
        }
        return new ConstantExpression(value);
    }

    public static ConstantExpression createFloat(String text) {
        Double value = new Double(text);
        return new ConstantExpression(value);
    }

    @Override
    public Object evaluate(MessageEvaluationContext message) throws JMSException {
        return this.value;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        if (this.value == null) {
            return "NULL";
        }
        if (this.value instanceof Boolean) {
            return (Boolean)this.value != false ? "TRUE" : "FALSE";
        }
        if (this.value instanceof String) {
            return ConstantExpression.encodeString((String)this.value);
        }
        return this.value.toString();
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

    public static String encodeString(String s2) {
        StringBuffer b = new StringBuffer();
        b.append('\'');
        for (int i = 0; i < s2.length(); ++i) {
            char c = s2.charAt(i);
            if (c == '\'') {
                b.append(c);
            }
            b.append(c);
        }
        b.append('\'');
        return b.toString();
    }

    static class BooleanConstantExpression
    extends ConstantExpression
    implements BooleanExpression {
        public BooleanConstantExpression(Object value) {
            super(value);
        }

        @Override
        public boolean matches(MessageEvaluationContext message) throws JMSException {
            Object object = this.evaluate(message);
            return object != null && object == Boolean.TRUE;
        }
    }
}

