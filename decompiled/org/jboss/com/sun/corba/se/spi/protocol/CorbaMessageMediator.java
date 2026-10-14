/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.protocol;

import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyOrReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaProtocolHandler;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContexts;
import org.omg.CORBA.Request;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CORBA_2_3.portable.InputStream;

public interface CorbaMessageMediator
extends MessageMediator,
ResponseHandler {
    public void setReplyHeader(LocateReplyOrReplyMessage var1);

    public LocateReplyMessage getLocateReplyHeader();

    public ReplyMessage getReplyHeader();

    public void setReplyExceptionDetailMessage(String var1);

    public RequestMessage getRequestHeader();

    public GIOPVersion getGIOPVersion();

    public byte getEncodingVersion();

    public int getRequestId();

    public Integer getRequestIdInteger();

    public boolean isOneWay();

    public short getAddrDisposition();

    public String getOperationName();

    public ServiceContexts getRequestServiceContexts();

    public ServiceContexts getReplyServiceContexts();

    public Message getDispatchHeader();

    public void setDispatchHeader(Message var1);

    public ByteBuffer getDispatchBuffer();

    public void setDispatchBuffer(ByteBuffer var1);

    public int getThreadPoolToUse();

    public byte getStreamFormatVersion();

    public byte getStreamFormatVersionForReply();

    public void sendCancelRequestIfFinalFragmentNotSent();

    public void setDIIInfo(Request var1);

    public boolean isDIIRequest();

    public Exception unmarshalDIIUserException(String var1, InputStream var2);

    public void setDIIException(Exception var1);

    public void handleDIIReply(InputStream var1);

    public boolean isSystemExceptionReply();

    public boolean isUserExceptionReply();

    public boolean isLocationForwardReply();

    public boolean isDifferentAddrDispositionRequestedReply();

    public short getAddrDispositionReply();

    public IOR getForwardedIOR();

    public SystemException getSystemExceptionReply();

    public ObjectKey getObjectKey();

    public void setProtocolHandler(CorbaProtocolHandler var1);

    public CorbaProtocolHandler getProtocolHandler();

    @Override
    public OutputStream createReply();

    @Override
    public OutputStream createExceptionReply();

    public boolean executeReturnServantInResponseConstructor();

    public void setExecuteReturnServantInResponseConstructor(boolean var1);

    public boolean executeRemoveThreadInfoInResponseConstructor();

    public void setExecuteRemoveThreadInfoInResponseConstructor(boolean var1);

    public boolean executePIInResponseConstructor();

    public void setExecutePIInResponseConstructor(boolean var1);
}

