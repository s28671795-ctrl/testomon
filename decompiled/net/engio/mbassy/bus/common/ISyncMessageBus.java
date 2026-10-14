/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.common;

import net.engio.mbassy.bus.common.ErrorHandlingSupport;
import net.engio.mbassy.bus.common.GenericMessagePublicationSupport;
import net.engio.mbassy.bus.common.PubSubSupport;
import net.engio.mbassy.bus.publication.IPublicationCommand;

public interface ISyncMessageBus<T, P extends IPublicationCommand>
extends PubSubSupport<T>,
ErrorHandlingSupport,
GenericMessagePublicationSupport<T, P> {
}

