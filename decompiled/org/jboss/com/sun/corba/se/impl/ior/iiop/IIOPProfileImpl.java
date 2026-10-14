/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsOutputStream;
import org.jboss.com.sun.corba.se.impl.ior.EncapsulationUtility;
import org.jboss.com.sun.corba.se.impl.ior.iiop.IIOPAddressImpl;
import org.jboss.com.sun.corba.se.impl.logging.IORSystemException;
import org.jboss.com.sun.corba.se.impl.util.JDKBridge;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableBase;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfile;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfileTemplate;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfile;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
import org.jboss.com.sun.corba.se.spi.ior.iiop.JavaCodebaseComponent;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapter;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapterFactory;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.IOP.TaggedProfileHelper;

public class IIOPProfileImpl
extends IdentifiableBase
implements IIOPProfile {
    private ORB orb;
    private IORSystemException wrapper;
    private ObjectId oid;
    private IIOPProfileTemplate proftemp;
    private ObjectKeyTemplate oktemp;
    protected String codebase = null;
    protected boolean cachedCodebase = false;
    private boolean checkedIsLocal = false;
    private boolean cachedIsLocal = false;

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IIOPProfileImpl)) {
            return false;
        }
        IIOPProfileImpl other = (IIOPProfileImpl)obj;
        return this.oid.equals(other.oid) && this.proftemp.equals(other.proftemp) && this.oktemp.equals(other.oktemp);
    }

    public int hashCode() {
        return this.oid.hashCode() ^ this.proftemp.hashCode() ^ this.oktemp.hashCode();
    }

    @Override
    public ObjectId getObjectId() {
        return this.oid;
    }

    @Override
    public TaggedProfileTemplate getTaggedProfileTemplate() {
        return this.proftemp;
    }

    @Override
    public ObjectKeyTemplate getObjectKeyTemplate() {
        return this.oktemp;
    }

    private IIOPProfileImpl(ORB orb) {
        this.orb = orb;
        this.wrapper = IORSystemException.get(orb, "oa.ior");
    }

    public IIOPProfileImpl(ORB orb, ObjectKeyTemplate oktemp, ObjectId oid, IIOPProfileTemplate proftemp) {
        this(orb);
        this.oktemp = oktemp;
        this.oid = oid;
        this.proftemp = proftemp;
    }

    public IIOPProfileImpl(InputStream is) {
        this((ORB)is.orb());
        this.init(is);
    }

    public IIOPProfileImpl(ORB orb, org.omg.IOP.TaggedProfile profile) {
        this(orb);
        if (profile == null || profile.tag != 0 || profile.profile_data == null) {
            throw this.wrapper.invalidTaggedProfile();
        }
        EncapsInputStream istr = new EncapsInputStream(orb, profile.profile_data, profile.profile_data.length);
        istr.consumeEndian();
        this.init(istr);
    }

    private void init(InputStream istr) {
        GIOPVersion version = new GIOPVersion();
        version.read(istr);
        IIOPAddressImpl primary = new IIOPAddressImpl(istr);
        byte[] key = EncapsulationUtility.readOctets(istr);
        ObjectKey okey = this.orb.getObjectKeyFactory().create(key);
        this.oktemp = okey.getTemplate();
        this.oid = okey.getId();
        this.proftemp = IIOPFactories.makeIIOPProfileTemplate(this.orb, version, primary);
        if (version.getMinor() > 0) {
            EncapsulationUtility.readIdentifiableSequence(this.proftemp, this.orb.getTaggedComponentFactoryFinder(), istr);
        }
        if (this.uncachedGetCodeBase() == null) {
            JavaCodebaseComponent jcc = LocalCodeBaseSingletonHolder.comp;
            if (jcc != null) {
                if (version.getMinor() > 0) {
                    this.proftemp.add(jcc);
                }
                this.codebase = jcc.getURLs();
            }
            this.cachedCodebase = true;
        }
    }

    @Override
    public void writeContents(OutputStream os) {
        this.proftemp.write(this.oktemp, this.oid, os);
    }

    @Override
    public int getId() {
        return this.proftemp.getId();
    }

    @Override
    public boolean isEquivalent(TaggedProfile prof) {
        if (!(prof instanceof IIOPProfile)) {
            return false;
        }
        IIOPProfile other = (IIOPProfile)prof;
        return this.oid.equals(other.getObjectId()) && this.proftemp.isEquivalent(other.getTaggedProfileTemplate()) && this.oktemp.equals(other.getObjectKeyTemplate());
    }

    @Override
    public ObjectKey getObjectKey() {
        ObjectKey result = IORFactories.makeObjectKey(this.oktemp, this.oid);
        return result;
    }

    @Override
    public org.omg.IOP.TaggedProfile getIOPProfile() {
        EncapsOutputStream os = new EncapsOutputStream(this.orb);
        os.write_long(this.getId());
        this.write(os);
        InputStream is = (InputStream)os.create_input_stream();
        return TaggedProfileHelper.read(is);
    }

    private String uncachedGetCodeBase() {
        Iterator<java.lang.Object> iter = this.proftemp.iteratorById(25);
        if (iter.hasNext()) {
            JavaCodebaseComponent jcbc = (JavaCodebaseComponent)iter.next();
            return jcbc.getURLs();
        }
        return null;
    }

    @Override
    public synchronized String getCodebase() {
        if (!this.cachedCodebase) {
            this.cachedCodebase = true;
            this.codebase = this.uncachedGetCodeBase();
        }
        return this.codebase;
    }

    @Override
    public ORBVersion getORBVersion() {
        return this.oktemp.getORBVersion();
    }

    @Override
    public synchronized boolean isLocal() {
        if (!this.checkedIsLocal) {
            this.checkedIsLocal = true;
            String host2 = this.proftemp.getPrimaryAddress().getHost();
            this.cachedIsLocal = this.orb.isLocalHost(host2) && this.orb.isLocalServerId(this.oktemp.getSubcontractId(), this.oktemp.getServerId()) && this.orb.getLegacyServerSocketManager().legacyIsLocalServerPort(this.proftemp.getPrimaryAddress().getPort());
        }
        return this.cachedIsLocal;
    }

    @Override
    public java.lang.Object getServant() {
        if (!this.isLocal()) {
            return null;
        }
        RequestDispatcherRegistry scr = this.orb.getRequestDispatcherRegistry();
        ObjectAdapterFactory oaf = scr.getObjectAdapterFactory(this.oktemp.getSubcontractId());
        ObjectAdapterId oaid = this.oktemp.getObjectAdapterId();
        ObjectAdapter oa = null;
        try {
            oa = oaf.find(oaid);
        }
        catch (SystemException exc) {
            this.wrapper.getLocalServantFailure(exc, (java.lang.Object)oaid.toString());
            return null;
        }
        byte[] boid = this.oid.getId();
        Object servant = oa.getLocalServant(boid);
        return servant;
    }

    @Override
    public synchronized GIOPVersion getGIOPVersion() {
        return this.proftemp.getGIOPVersion();
    }

    @Override
    public void makeImmutable() {
        this.proftemp.makeImmutable();
    }

    private static class LocalCodeBaseSingletonHolder {
        public static JavaCodebaseComponent comp;

        private LocalCodeBaseSingletonHolder() {
        }

        static {
            String localCodebase = JDKBridge.getLocalCodebase();
            comp = localCodebase == null ? null : IIOPFactories.makeJavaCodebaseComponent(localCodebase);
        }
    }
}

