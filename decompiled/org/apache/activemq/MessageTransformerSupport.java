/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.activemq.ActiveMQMessageTransformation;
import org.apache.activemq.MessageTransformer;

public abstract class MessageTransformerSupport
implements MessageTransformer {
    protected void copyProperties(Message fromMessage, Message toMesage) throws JMSException {
        ActiveMQMessageTransformation.copyProperties(fromMessage, toMesage);
    }
}

