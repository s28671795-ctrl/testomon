/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.inject.Binding;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.spi.ProvisionListener;
import java.util.LinkedHashSet;
import java.util.List;

final class ProvisionListenerStackCallback<T> {
    private static final ProvisionListener[] EMPTY_LISTENER = new ProvisionListener[0];
    private static final ProvisionListenerStackCallback<?> EMPTY_CALLBACK = new ProvisionListenerStackCallback(null, ImmutableList.of());
    private final ProvisionListener[] listeners;
    private final Binding<T> binding;

    public static <T> ProvisionListenerStackCallback<T> emptyListener() {
        return EMPTY_CALLBACK;
    }

    public ProvisionListenerStackCallback(Binding<T> binding, List<ProvisionListener> listeners) {
        this.binding = binding;
        if (listeners.isEmpty()) {
            this.listeners = EMPTY_LISTENER;
        } else {
            LinkedHashSet<ProvisionListener> deDuplicated = Sets.newLinkedHashSet(listeners);
            this.listeners = deDuplicated.toArray(new ProvisionListener[deDuplicated.size()]);
        }
    }

    public boolean hasListeners() {
        return this.listeners.length > 0;
    }

    public T provision(InternalContext context, ProvisionCallback<T> callable) throws InternalProvisionException {
        Provision provision = new Provision(callable);
        RuntimeException caught = null;
        try {
            provision.provision();
        }
        catch (RuntimeException t2) {
            caught = t2;
        }
        if (provision.exceptionDuringProvision != null) {
            throw provision.exceptionDuringProvision;
        }
        if (caught != null) {
            String listener = provision.erredListener != null ? provision.erredListener.getClass() : "(unknown)";
            throw InternalProvisionException.errorInUserCode(ErrorId.OTHER, caught, "Error notifying ProvisionListener %s of %s.\n Reason: %s", listener, this.binding.getKey(), caught);
        }
        return provision.result;
    }

    private class Provision
    extends ProvisionListener.ProvisionInvocation<T> {
        final ProvisionCallback<T> callable;
        int index = -1;
        T result;
        InternalProvisionException exceptionDuringProvision;
        ProvisionListener erredListener;

        public Provision(ProvisionCallback<T> callable) {
            this.callable = callable;
        }

        @Override
        public T provision() {
            ++this.index;
            if (this.index == ProvisionListenerStackCallback.this.listeners.length) {
                try {
                    this.result = this.callable.call();
                }
                catch (InternalProvisionException ipe) {
                    this.exceptionDuringProvision = ipe;
                    throw ipe.toProvisionException();
                }
            }
            if (this.index < ProvisionListenerStackCallback.this.listeners.length) {
                int currentIdx = this.index;
                try {
                    ProvisionListenerStackCallback.this.listeners[this.index].onProvision(this);
                }
                catch (RuntimeException re) {
                    this.erredListener = ProvisionListenerStackCallback.this.listeners[currentIdx];
                    throw re;
                }
                if (currentIdx == this.index) {
                    this.provision();
                }
            } else {
                throw new IllegalStateException("Already provisioned in this listener.");
            }
            return this.result;
        }

        @Override
        public Binding<T> getBinding() {
            return ProvisionListenerStackCallback.this.binding;
        }
    }

    public static interface ProvisionCallback<T> {
        public T call() throws InternalProvisionException;
    }
}

