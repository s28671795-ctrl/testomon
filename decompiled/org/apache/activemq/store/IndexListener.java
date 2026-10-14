/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.Message;

public interface IndexListener {
    public void onAdd(MessageContext var1);

    public static final class MessageContext {
        public final Message message;
        public final ConnectionContext context;
        public final Runnable onCompletion;
        public boolean duplicate;

        public MessageContext(ConnectionContext context, Message message, Runnable onCompletion) {
            this.context = context;
            this.message = message;
            this.onCompletion = onCompletion;
        }
    }
}

