/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.omg.CORBA.SystemException;

public interface LocateReplyOrReplyMessage
extends Message {
    public int getRequestId();

    public int getReplyStatus();

    public SystemException getSystemException(String var1);

    public IOR getIOR();

    public short getAddrDisposition();
}

