/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import javax.jms.JMSException;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.ConstantExpression;
import org.apache.activemq.filter.MessageEvaluationContext;

public final class XQueryExpression
implements BooleanExpression {
    private final String xpath;

    XQueryExpression(String xpath) {
        this.xpath = xpath;
    }

    @Override
    public Object evaluate(MessageEvaluationContext message) throws JMSException {
        return Boolean.FALSE;
    }

    public String toString() {
        return "XQUERY " + ConstantExpression.encodeString(this.xpath);
    }

    @Override
    public boolean matches(MessageEvaluationContext message) throws JMSException {
        Object object = this.evaluate(message);
        return object != null && object == Boolean.TRUE;
    }
}

