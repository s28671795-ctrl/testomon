/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import javax.jms.JMSException;
import org.apache.activemq.filter.MessageEvaluationContext;

public interface Expression {
    public Object evaluate(MessageEvaluationContext var1) throws JMSException;
}

