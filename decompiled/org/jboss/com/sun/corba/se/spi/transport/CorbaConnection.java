/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.transport;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.pept.transport.ResponseWaitingRoom;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.legacy.connection.Connection;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaMessageMediator;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.omg.CORBA.SystemException;

public interface CorbaConnection
extends org.jboss.com.sun.corba.se.pept.transport.Connection,
Connection {
    public static final int OPENING = 1;
    public static final int ESTABLISHED = 2;
    public static final int CLOSE_SENT = 3;
    public static final int CLOSE_RECVD = 4;
    public static final int ABORT = 5;

    public boolean shouldUseDirectByteBuffers();

    public boolean shouldReadGiopHeaderOnly();

    public ByteBuffer read(int var1, int var2, int var3, long var4) throws IOException;

    public ByteBuffer read(ByteBuffer var1, int var2, int var3, long var4) throws IOException;

    public void write(ByteBuffer var1) throws IOException;

    public void dprint(String var1);

    public int getNextRequestId();

    public ORB getBroker();

    public CodeSetComponentInfo.CodeSetContext getCodeSetContext();

    public void setCodeSetContext(CodeSetComponentInfo.CodeSetContext var1);

    public MessageMediator clientRequestMapGet(int var1);

    public void clientReply_1_1_Put(MessageMediator var1);

    public MessageMediator clientReply_1_1_Get();

    public void clientReply_1_1_Remove();

    public void serverRequest_1_1_Put(MessageMediator var1);

    public MessageMediator serverRequest_1_1_Get();

    public void serverRequest_1_1_Remove();

    public boolean isPostInitialContexts();

    public void setPostInitialContexts();

    public void purgeCalls(SystemException var1, boolean var2, boolean var3);

    public void setCodeBaseIOR(IOR var1);

    public IOR getCodeBaseIOR();

    public CodeBase getCodeBase();

    public void sendCloseConnection(GIOPVersion var1) throws IOException;

    public void sendMessageError(GIOPVersion var1) throws IOException;

    public void sendCancelRequest(GIOPVersion var1, int var2) throws IOException;

    public void sendCancelRequestWithLock(GIOPVersion var1, int var2) throws IOException;

    public ResponseWaitingRoom getResponseWaitingRoom();

    public void serverRequestMapPut(int var1, CorbaMessageMediator var2);

    public CorbaMessageMediator serverRequestMapGet(int var1);

    public void serverRequestMapRemove(int var1);

    public SocketChannel getSocketChannel();

    public void serverRequestProcessingBegins();

    public void serverRequestProcessingEnds();
}

