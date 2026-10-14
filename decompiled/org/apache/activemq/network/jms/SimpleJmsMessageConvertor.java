/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.activemq.network.jms.JmsMesageConvertor;

public class SimpleJmsMessageConvertor
implements JmsMesageConvertor {
    @Override
    public Message convert(Message message) throws JMSException {
        return message;
    }

    @Override
    public Message convert(Message message, Destination replyTo) throws JMSException {
        Message msg = this.convert(message);
        if (replyTo != null) {
            msg.setJMSReplyTo(replyTo);
        } else {
            msg.setJMSReplyTo(null);
        }
        return msg;
    }

    @Override
    public void setConnection(Connection connection) {
    }
}

