/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.MessageConsumer;
import org.apache.activemq.MessageAvailableListener;

public interface MessageAvailableConsumer
extends MessageConsumer {
    public void setAvailableListener(MessageAvailableListener var1);

    public MessageAvailableListener getAvailableListener();
}

