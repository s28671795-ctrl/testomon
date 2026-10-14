/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

public interface JmsMesageConvertor {
    public Message convert(Message var1) throws JMSException;

    public Message convert(Message var1, Destination var2) throws JMSException;

    public void setConnection(Connection var1);
}

