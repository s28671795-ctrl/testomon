/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContexts;
import org.omg.CORBA.Principal;

public interface RequestMessage
extends Message {
    public static final byte RESPONSE_EXPECTED_BIT = 1;

    public ServiceContexts getServiceContexts();

    public int getRequestId();

    public boolean isResponseExpected();

    public byte[] getReserved();

    public ObjectKey getObjectKey();

    public String getOperation();

    public Principal getPrincipal();

    public void setThreadPoolToUse(int var1);
}

