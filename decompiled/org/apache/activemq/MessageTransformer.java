/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

public interface MessageTransformer {
    public Message producerTransform(Session var1, MessageProducer var2, Message var3) throws JMSException;

    public Message consumerTransform(Session var1, MessageConsumer var2, Message var3) throws JMSException;
}

