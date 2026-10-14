/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orb;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import org.jboss.com.sun.corba.se.impl.corba.TypeCodeFactory;
import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.oa.poa.BadServerIdHandler;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBClassLoader;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.PresentationManagerImpl;
import org.jboss.com.sun.corba.se.impl.transport.ByteBufferPoolImpl;
import org.jboss.com.sun.corba.se.pept.broker.Broker;
import org.jboss.com.sun.corba.se.pept.transport.ByteBufferPool;
import org.jboss.com.sun.corba.se.spi.copyobject.CopierManager;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyFactory;
import org.jboss.com.sun.corba.se.spi.ior.TaggedComponentFactoryFinder;
import org.jboss.com.sun.corba.se.spi.legacy.connection.LegacyServerSocketManager;
import org.jboss.com.sun.corba.se.spi.logging.CORBALogDomains;
import org.jboss.com.sun.corba.se.spi.logging.LogWrapperBase;
import org.jboss.com.sun.corba.se.spi.logging.LogWrapperFactory;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoringFactories;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoringManager;
import org.jboss.com.sun.corba.se.spi.oa.OAInvocationInfo;
import org.jboss.com.sun.corba.se.spi.orb.ORBData;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.orb.Operation;
import org.jboss.com.sun.corba.se.spi.orb.StringPair;
import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationDefaults;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.jboss.com.sun.corba.se.spi.protocol.ClientDelegateFactory;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
import org.jboss.com.sun.corba.se.spi.protocol.PIHandler;
import org.jboss.com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
import org.jboss.com.sun.corba.se.spi.resolver.LocalResolver;
import org.jboss.com.sun.corba.se.spi.resolver.Resolver;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContextRegistry;
import org.jboss.com.sun.corba.se.spi.transport.CorbaContactInfoListFactory;
import org.jboss.com.sun.corba.se.spi.transport.CorbaTransportManager;

public abstract class ORB
extends org.jboss.com.sun.corba.se.org.omg.CORBA.ORB
implements Broker,
TypeCodeFactory {
    public static boolean ORBInitDebug = false;
    public boolean transportDebugFlag = false;
    public boolean subcontractDebugFlag = false;
    public boolean poaDebugFlag = false;
    public boolean poaConcurrencyDebugFlag = false;
    public boolean poaFSMDebugFlag = false;
    public boolean orbdDebugFlag = false;
    public boolean namingDebugFlag = false;
    public boolean serviceContextDebugFlag = false;
    public boolean transientObjectManagerDebugFlag = false;
    public boolean giopVersionDebugFlag = false;
    public boolean shutdownDebugFlag = false;
    public boolean giopDebugFlag = false;
    public boolean invocationTimingDebugFlag = false;
    protected static ORBUtilSystemException staticWrapper;
    protected ORBUtilSystemException wrapper;
    protected OMGSystemException omgWrapper;
    private Map<String, TypeCodeImpl> typeCodeMap;
    private TypeCodeImpl[] primitiveTypeCodeConstants;
    ByteBufferPool byteBufferPool;
    private Map<StringPair, LogWrapperBase> wrapperMap = new ConcurrentHashMap<StringPair, LogWrapperBase>();
    private static Map<StringPair, LogWrapperBase> staticWrapperMap;
    private MonitoringManager monitoringManager;
    protected static PresentationManager globalPM;

    public abstract boolean isLocalHost(String var1);

    public abstract boolean isLocalServerId(int var1, int var2);

    public abstract OAInvocationInfo peekInvocationInfo();

    public abstract void pushInvocationInfo(OAInvocationInfo var1);

    public abstract OAInvocationInfo popInvocationInfo();

    public abstract CorbaTransportManager getCorbaTransportManager();

    public abstract LegacyServerSocketManager getLegacyServerSocketManager();

    public static PresentationManager getPresentationManager() {
        return globalPM;
    }

    public static PresentationManager.StubFactoryFactory getStubFactoryFactory() {
        boolean useDynamicStubs = globalPM.useDynamicStubs();
        return globalPM.getStubFactoryFactory(useDynamicStubs);
    }

    protected ORB() {
        this.wrapper = ORBUtilSystemException.get(this, "rpc.presentation");
        this.omgWrapper = OMGSystemException.get(this, "rpc.presentation");
        this.typeCodeMap = new HashMap<String, TypeCodeImpl>();
        this.primitiveTypeCodeConstants = new TypeCodeImpl[]{new TypeCodeImpl(this, 0), new TypeCodeImpl(this, 1), new TypeCodeImpl(this, 2), new TypeCodeImpl(this, 3), new TypeCodeImpl(this, 4), new TypeCodeImpl(this, 5), new TypeCodeImpl(this, 6), new TypeCodeImpl(this, 7), new TypeCodeImpl(this, 8), new TypeCodeImpl(this, 9), new TypeCodeImpl(this, 10), new TypeCodeImpl(this, 11), new TypeCodeImpl(this, 12), new TypeCodeImpl(this, 13), new TypeCodeImpl(this, 14), null, null, null, new TypeCodeImpl(this, 18), null, null, null, null, new TypeCodeImpl(this, 23), new TypeCodeImpl(this, 24), new TypeCodeImpl(this, 25), new TypeCodeImpl(this, 26), new TypeCodeImpl(this, 27), new TypeCodeImpl(this, 28), new TypeCodeImpl(this, 29), new TypeCodeImpl(this, 30), new TypeCodeImpl(this, 31), new TypeCodeImpl(this, 32)};
        this.monitoringManager = MonitoringFactories.getMonitoringManagerFactory().createMonitoringManager("orb", "ORB Management and Monitoring Root");
    }

    public TypeCodeImpl get_primitive_tc(int kind) {
        try {
            return this.primitiveTypeCodeConstants[kind];
        }
        catch (Throwable t2) {
            throw this.wrapper.invalidTypecodeKind(t2, (Object)new Integer(kind));
        }
    }

    @Override
    public synchronized void setTypeCode(String id, TypeCodeImpl code) {
        this.typeCodeMap.put(id, code);
    }

    @Override
    public synchronized TypeCodeImpl getTypeCode(String id) {
        return this.typeCodeMap.get(id);
    }

    public MonitoringManager getMonitoringManager() {
        return this.monitoringManager;
    }

    public abstract void set_parameters(Properties var1);

    public abstract ORBVersion getORBVersion();

    public abstract void setORBVersion(ORBVersion var1);

    public abstract IOR getFVDCodeBaseIOR();

    public abstract void handleBadServerId(ObjectKey var1);

    public abstract void setBadServerIdHandler(BadServerIdHandler var1);

    public abstract void initBadServerIdHandler();

    public abstract void notifyORB();

    public abstract PIHandler getPIHandler();

    public abstract void checkShutdownState();

    public abstract boolean isDuringDispatch();

    public abstract void startingDispatch();

    public abstract void finishedDispatch();

    public abstract int getTransientServerId();

    public abstract ServiceContextRegistry getServiceContextRegistry();

    public abstract RequestDispatcherRegistry getRequestDispatcherRegistry();

    public abstract ORBData getORBData();

    public abstract void setClientDelegateFactory(ClientDelegateFactory var1);

    public abstract ClientDelegateFactory getClientDelegateFactory();

    public abstract void setCorbaContactInfoListFactory(CorbaContactInfoListFactory var1);

    public abstract CorbaContactInfoListFactory getCorbaContactInfoListFactory();

    public abstract void setResolver(Resolver var1);

    public abstract Resolver getResolver();

    public abstract void setLocalResolver(LocalResolver var1);

    public abstract LocalResolver getLocalResolver();

    public abstract void setURLOperation(Operation var1);

    public abstract Operation getURLOperation();

    public abstract void setINSDelegate(CorbaServerRequestDispatcher var1);

    public abstract TaggedComponentFactoryFinder getTaggedComponentFactoryFinder();

    public abstract IdentifiableFactoryFinder getTaggedProfileFactoryFinder();

    public abstract IdentifiableFactoryFinder getTaggedProfileTemplateFactoryFinder();

    public abstract ObjectKeyFactory getObjectKeyFactory();

    public abstract void setObjectKeyFactory(ObjectKeyFactory var1);

    public Logger getLogger(String domain) {
        String ORBId;
        ORBData odata = this.getORBData();
        if (odata == null) {
            ORBId = "_INITIALIZING_";
        } else {
            ORBId = odata.getORBId();
            if (ORBId.equals("")) {
                ORBId = "_DEFAULT_";
            }
        }
        return ORB.getCORBALogger(ORBId, domain);
    }

    public static Logger staticGetLogger(String domain) {
        return ORB.getCORBALogger("_CORBA_", domain);
    }

    private static Logger getCORBALogger(String ORBId, String domain) {
        String fqLogDomain = CORBALogDomains.TOP_LEVEL_DOMAIN + "." + ORBId + "." + domain;
        return Logger.getLogger(fqLogDomain);
    }

    public LogWrapperBase getLogWrapper(String logDomain, String exceptionGroup, LogWrapperFactory factory2) {
        StringPair key = new StringPair(logDomain, exceptionGroup);
        LogWrapperBase logWrapper = this.wrapperMap.get(key);
        if (logWrapper == null) {
            logWrapper = factory2.create(this.getLogger(logDomain));
            this.wrapperMap.put(key, logWrapper);
        }
        return logWrapper;
    }

    public static LogWrapperBase staticGetLogWrapper(String logDomain, String exceptionGroup, LogWrapperFactory factory2) {
        StringPair key = new StringPair(logDomain, exceptionGroup);
        LogWrapperBase logWrapper = staticWrapperMap.get(key);
        if (logWrapper == null) {
            logWrapper = factory2.create(ORB.staticGetLogger(logDomain));
            staticWrapperMap.put(key, logWrapper);
        }
        return logWrapper;
    }

    public ByteBufferPool getByteBufferPool() {
        if (this.byteBufferPool == null) {
            this.byteBufferPool = new ByteBufferPoolImpl(this);
        }
        return this.byteBufferPool;
    }

    public abstract void setThreadPoolManager(ThreadPoolManager var1);

    public abstract ThreadPoolManager getThreadPoolManager();

    public abstract CopierManager getCopierManager();

    static {
        staticWrapperMap = new ConcurrentHashMap<StringPair, LogWrapperBase>();
        globalPM = null;
        staticWrapper = ORBUtilSystemException.get("rpc.presentation");
        boolean useDynamicStub = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                return Boolean.getBoolean("org.jboss.com.sun.CORBA.ORBUseDynamicStub");
            }
        });
        PresentationManager.StubFactoryFactory dynamicStubFactoryFactory = AccessController.doPrivileged(new PrivilegedAction<PresentationManager.StubFactoryFactory>(){

            @Override
            public PresentationManager.StubFactoryFactory run() {
                PresentationManager.StubFactoryFactory sff = PresentationDefaults.getProxyStubFactoryFactory();
                String className = System.getProperty("org.jboss.com.sun.CORBA.ORBDynamicStubFactoryFactoryClass", "org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryFactoryProxyImpl");
                try {
                    Class<?> cls = ORBClassLoader.loadClass(className);
                    sff = (PresentationManager.StubFactoryFactory)cls.newInstance();
                }
                catch (Exception exc) {
                    staticWrapper.errorInSettingDynamicStubFactoryFactory(exc, (Object)className);
                }
                return sff;
            }
        });
        globalPM = new PresentationManagerImpl(useDynamicStub);
        globalPM.setStubFactoryFactory(false, PresentationDefaults.getStaticStubFactoryFactory());
        globalPM.setStubFactoryFactory(true, dynamicStubFactoryFactory);
    }
}

