/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.common;

import java.util.Collection;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;

public interface ErrorHandlingSupport {
    public Collection<IPublicationErrorHandler> getRegisteredErrorHandlers();
}

