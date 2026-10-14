/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.protocol;

import org.jboss.com.sun.corba.se.impl.corba.RequestImpl;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapter;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaMessageMediator;
import org.omg.CORBA.Any;
import org.omg.CORBA.NVList;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.PortableInterceptor.Current;
import org.omg.PortableInterceptor.Interceptor;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;
import org.omg.PortableInterceptor.PolicyFactory;

public interface PIHandler {
    public void initialize();

    public void destroyInterceptors();

    public void objectAdapterCreated(ObjectAdapter var1);

    public void adapterManagerStateChanged(int var1, short var2);

    public void adapterStateChanged(ObjectReferenceTemplate[] var1, short var2);

    public void disableInterceptorsThisThread();

    public void enableInterceptorsThisThread();

    public void invokeClientPIStartingPoint() throws RemarshalException;

    public Exception invokeClientPIEndingPoint(int var1, Exception var2);

    public void initiateClientPIRequest(boolean var1);

    public void cleanupClientPIRequest();

    public void setClientPIInfo(RequestImpl var1);

    public void setClientPIInfo(CorbaMessageMediator var1);

    public void invokeServerPIStartingPoint();

    public void invokeServerPIIntermediatePoint();

    public void invokeServerPIEndingPoint(ReplyMessage var1);

    public void initializeServerPIInfo(CorbaMessageMediator var1, ObjectAdapter var2, byte[] var3, ObjectKeyTemplate var4);

    public void setServerPIInfo(Object var1, String var2);

    public void setServerPIInfo(Exception var1);

    public void setServerPIInfo(NVList var1);

    public void setServerPIExceptionInfo(Any var1);

    public void setServerPIInfo(Any var1);

    public void cleanupServerPIRequest();

    public Policy create_policy(int var1, Any var2) throws PolicyError;

    public void register_interceptor(Interceptor var1, int var2) throws DuplicateName;

    public Current getPICurrent();

    public void registerPolicyFactory(int var1, PolicyFactory var2);

    public int allocateServerRequestId();
}

