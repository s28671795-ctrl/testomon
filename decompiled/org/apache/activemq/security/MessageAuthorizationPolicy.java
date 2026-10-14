/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.Message;

public interface MessageAuthorizationPolicy {
    public boolean isAllowedToConsume(ConnectionContext var1, Message var2);
}

