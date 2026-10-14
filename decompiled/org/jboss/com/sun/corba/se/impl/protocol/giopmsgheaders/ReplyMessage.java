/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyOrReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContexts;

public interface ReplyMessage
extends Message,
LocateReplyOrReplyMessage {
    public static final int NO_EXCEPTION = 0;
    public static final int USER_EXCEPTION = 1;
    public static final int SYSTEM_EXCEPTION = 2;
    public static final int LOCATION_FORWARD = 3;
    public static final int LOCATION_FORWARD_PERM = 4;
    public static final int NEEDS_ADDRESSING_MODE = 5;

    public ServiceContexts getServiceContexts();

    public void setServiceContexts(ServiceContexts var1);

    public void setIOR(IOR var1);
}

