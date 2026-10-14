/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.io.IOException;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.MessageEvaluationContext;

public class NonCachedMessageEvaluationContext
extends MessageEvaluationContext {
    @Override
    public Message getMessage() throws IOException {
        return this.messageReference != null ? this.messageReference.getMessage() : null;
    }

    @Override
    public void setMessageReference(MessageReference messageReference) {
        this.messageReference = messageReference;
    }

    @Override
    protected void clearMessageCache() {
    }
}

