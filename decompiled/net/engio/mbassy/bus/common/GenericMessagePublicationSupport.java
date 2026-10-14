/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.common;

import net.engio.mbassy.bus.common.ErrorHandlingSupport;
import net.engio.mbassy.bus.common.PubSubSupport;
import net.engio.mbassy.bus.publication.IPublicationCommand;

public interface GenericMessagePublicationSupport<T, P extends IPublicationCommand>
extends PubSubSupport<T>,
ErrorHandlingSupport {
    public P post(T var1);
}

