/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.legacy.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;
import org.jboss.com.sun.corba.se.spi.transport.SocketInfo;
import org.omg.CORBA.ORB;

public interface ORBSocketFactory {
    public static final String IIOP_CLEAR_TEXT = "IIOP_CLEAR_TEXT";

    public ServerSocket createServerSocket(String var1, int var2) throws IOException;

    public SocketInfo getEndPointInfo(ORB var1, IOR var2, SocketInfo var3);

    public Socket createSocket(SocketInfo var1) throws IOException, GetEndPointInfoAgainException;
}

