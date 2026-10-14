/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.handler.MessageHandler
 */
package org.opensaml.soap.common;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.messaging.handler.MessageHandler;

public interface SOAPHandler<MessageType>
extends MessageHandler<MessageType> {
    @Nonnull
    public Set<QName> understandsHeaders();
}

