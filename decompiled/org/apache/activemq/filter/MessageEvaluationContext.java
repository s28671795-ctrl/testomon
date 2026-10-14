/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.io.IOException;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;

public class MessageEvaluationContext {
    protected MessageReference messageReference;
    protected boolean loaded;
    protected boolean dropped;
    protected Message message;
    protected ActiveMQDestination destination;

    public boolean isDropped() throws IOException {
        this.getMessage();
        return this.dropped;
    }

    public Message getMessage() throws IOException {
        if (!this.dropped && !this.loaded) {
            this.loaded = true;
            this.messageReference.incrementReferenceCount();
            this.message = this.messageReference.getMessage();
            if (this.message == null) {
                this.messageReference.decrementReferenceCount();
                this.dropped = true;
                this.loaded = false;
            }
        }
        return this.message;
    }

    public void setMessageReference(MessageReference messageReference) {
        if (this.messageReference != messageReference) {
            this.clearMessageCache();
        }
        this.messageReference = messageReference;
    }

    public void clear() {
        this.clearMessageCache();
        this.destination = null;
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    protected void clearMessageCache() {
        if (this.loaded) {
            this.messageReference.decrementReferenceCount();
        }
        this.message = null;
        this.dropped = false;
        this.loaded = false;
    }

    public MessageReference getMessageReference() {
        return this.messageReference;
    }
}

