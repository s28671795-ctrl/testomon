/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.WrappedMessage;

public final class WrappedMessage$ {
    public static final WrappedMessage$ MODULE$ = new WrappedMessage$();

    public Object unwrap(Object message) {
        Object object;
        while ((object = message) instanceof WrappedMessage) {
            WrappedMessage wrappedMessage = (WrappedMessage)object;
            message = wrappedMessage.message();
        }
        return message;
    }

    private WrappedMessage$() {
    }
}

