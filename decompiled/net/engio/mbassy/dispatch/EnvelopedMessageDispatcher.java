/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.dispatch;

import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.dispatch.DelegatingMessageDispatcher;
import net.engio.mbassy.dispatch.IMessageDispatcher;
import net.engio.mbassy.subscription.MessageEnvelope;

public class EnvelopedMessageDispatcher
extends DelegatingMessageDispatcher {
    public EnvelopedMessageDispatcher(IMessageDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void dispatch(MessagePublication publication, Object message, Iterable listeners) {
        this.getDelegate().dispatch(publication, new MessageEnvelope(message), listeners);
    }
}

