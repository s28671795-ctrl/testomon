/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.protocol;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import org.jboss.com.sun.corba.se.pept.protocol.ProtocolHandler;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaMessageMediator;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContexts;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.UnknownException;

public interface CorbaProtocolHandler
extends ProtocolHandler {
    public void handleRequest(RequestMessage var1, CorbaMessageMediator var2);

    public void handleRequest(LocateRequestMessage var1, CorbaMessageMediator var2);

    public CorbaMessageMediator createResponse(CorbaMessageMediator var1, ServiceContexts var2);

    public CorbaMessageMediator createUserExceptionResponse(CorbaMessageMediator var1, ServiceContexts var2);

    public CorbaMessageMediator createUnknownExceptionResponse(CorbaMessageMediator var1, UnknownException var2);

    public CorbaMessageMediator createSystemExceptionResponse(CorbaMessageMediator var1, SystemException var2, ServiceContexts var3);

    public CorbaMessageMediator createLocationForward(CorbaMessageMediator var1, IOR var2, ServiceContexts var3);

    public void handleThrowableDuringServerDispatch(CorbaMessageMediator var1, Throwable var2, CompletionStatus var3);
}

