/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.io.IOException;
import javax.jms.JMSException;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.util.JMSExceptionSupport;

public class NoLocalExpression
implements BooleanExpression {
    private final String connectionId;

    public NoLocalExpression(String connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    public boolean matches(MessageEvaluationContext message) throws JMSException {
        try {
            if (message.isDropped()) {
                return false;
            }
            String messageConnectionId = message.getMessage().getProducerId().getConnectionId();
            return !this.connectionId.equals(messageConnectionId);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.create(e);
        }
    }

    @Override
    public Object evaluate(MessageEvaluationContext message) throws JMSException {
        return this.matches(message) ? Boolean.TRUE : Boolean.FALSE;
    }
}

