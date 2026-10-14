/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;

public interface LocateRequestMessage
extends Message {
    public int getRequestId();

    public ObjectKey getObjectKey();
}

