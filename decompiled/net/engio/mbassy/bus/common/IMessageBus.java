/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.common;

import net.engio.mbassy.bus.common.GenericMessagePublicationSupport;
import net.engio.mbassy.bus.publication.ISyncAsyncPublicationCommand;

public interface IMessageBus<T, P extends ISyncAsyncPublicationCommand>
extends GenericMessagePublicationSupport<T, P> {
    @Override
    public P post(T var1);

    public boolean hasPendingMessages();

    public void shutdown();
}

