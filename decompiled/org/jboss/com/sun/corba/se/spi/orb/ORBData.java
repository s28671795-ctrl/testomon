/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orb;

import org.jboss.com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
import org.jboss.com.sun.corba.se.impl.legacy.connection.USLPort;
import org.jboss.com.sun.corba.se.pept.transport.Acceptor;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.legacy.connection.ORBSocketFactory;
import org.jboss.com.sun.corba.se.spi.orb.StringPair;
import org.jboss.com.sun.corba.se.spi.transport.CorbaContactInfoListFactory;
import org.jboss.com.sun.corba.se.spi.transport.IIOPPrimaryToContactInfo;
import org.jboss.com.sun.corba.se.spi.transport.IORToSocketInfo;
import org.jboss.com.sun.corba.se.spi.transport.ReadTimeouts;
import org.omg.PortableInterceptor.ORBInitializer;

public interface ORBData {
    public String getORBInitialHost();

    public int getORBInitialPort();

    public String getORBServerHost();

    public int getORBServerPort();

    public String getListenOnAllInterfaces();

    public ORBSocketFactory getLegacySocketFactory();

    public org.jboss.com.sun.corba.se.spi.transport.ORBSocketFactory getSocketFactory();

    public USLPort[] getUserSpecifiedListenPorts();

    public IORToSocketInfo getIORToSocketInfo();

    public IIOPPrimaryToContactInfo getIIOPPrimaryToContactInfo();

    public String getORBId();

    public boolean getORBServerIdPropertySpecified();

    public boolean isLocalOptimizationAllowed();

    public GIOPVersion getGIOPVersion();

    public int getHighWaterMark();

    public int getLowWaterMark();

    public int getNumberToReclaim();

    public int getGIOPFragmentSize();

    public int getGIOPBufferSize();

    public int getGIOPBuffMgrStrategy(GIOPVersion var1);

    public short getGIOPTargetAddressPreference();

    public short getGIOPAddressDisposition();

    public boolean useByteOrderMarkers();

    public boolean useByteOrderMarkersInEncapsulations();

    public boolean alwaysSendCodeSetServiceContext();

    public boolean getPersistentPortInitialized();

    public int getPersistentServerPort();

    public boolean getPersistentServerIdInitialized();

    public int getPersistentServerId();

    public boolean getServerIsORBActivated();

    public Class<?> getBadServerIdHandler();

    public CodeSetComponentInfo getCodeSetComponentInfo();

    public ORBInitializer[] getORBInitializers();

    public StringPair[] getORBInitialReferences();

    public String getORBDefaultInitialReference();

    public String[] getORBDebugFlags();

    public Acceptor[] getAcceptors();

    public CorbaContactInfoListFactory getCorbaContactInfoListFactory();

    public String acceptorSocketType();

    public boolean acceptorSocketUseSelectThreadToWait();

    public boolean acceptorSocketUseWorkerThreadForEvent();

    public String connectionSocketType();

    public boolean connectionSocketUseSelectThreadToWait();

    public boolean connectionSocketUseWorkerThreadForEvent();

    public ReadTimeouts getTransportTCPReadTimeouts();

    public boolean disableDirectByteBufferUse();

    public boolean isJavaSerializationEnabled();

    public boolean useRepId();
}

