/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.common;

public abstract class PublicationEvent {
    private Object relatedMessage;

    public PublicationEvent(Object message) {
        this.relatedMessage = message;
    }

    public Object getMessage() {
        return this.relatedMessage;
    }
}

