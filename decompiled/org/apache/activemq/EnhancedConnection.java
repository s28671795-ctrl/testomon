/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.TopicConnection;
import org.apache.activemq.Closeable;
import org.apache.activemq.advisory.DestinationSource;

public interface EnhancedConnection
extends TopicConnection,
QueueConnection,
Closeable {
    public DestinationSource getDestinationSource() throws JMSException;
}

