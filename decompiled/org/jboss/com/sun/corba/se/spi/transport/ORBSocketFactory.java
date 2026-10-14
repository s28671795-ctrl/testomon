/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.transport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import org.jboss.com.sun.corba.se.pept.transport.Acceptor;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public interface ORBSocketFactory {
    public void setORB(ORB var1);

    public ServerSocket createServerSocket(String var1, InetSocketAddress var2) throws IOException;

    public Socket createSocket(String var1, InetSocketAddress var2) throws IOException;

    public void setAcceptedSocketOptions(Acceptor var1, ServerSocket var2, Socket var3) throws SocketException;
}

