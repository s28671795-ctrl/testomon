/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.dispatch;

import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.dispatch.DelegatingMessageDispatcher;
import net.engio.mbassy.dispatch.IMessageDispatcher;
import net.engio.mbassy.listener.IMessageFilter;

public final class FilteredMessageDispatcher
extends DelegatingMessageDispatcher {
    private final IMessageFilter[] filter;

    public FilteredMessageDispatcher(IMessageDispatcher dispatcher) {
        super(dispatcher);
        this.filter = dispatcher.getContext().getHandler().getFilter();
    }

    private boolean passesFilter(Object message) {
        if (this.filter == null) {
            return true;
        }
        for (IMessageFilter aFilter : this.filter) {
            if (aFilter.accepts(message, this.getContext())) continue;
            return false;
        }
        return true;
    }

    @Override
    public void dispatch(MessagePublication publication, Object message, Iterable listeners) {
        if (this.passesFilter(message)) {
            this.getDelegate().dispatch(publication, message, listeners);
        }
    }
}

