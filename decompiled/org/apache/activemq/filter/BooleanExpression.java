/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import javax.jms.JMSException;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.MessageEvaluationContext;

public interface BooleanExpression
extends Expression {
    public boolean matches(MessageEvaluationContext var1) throws JMSException;
}

