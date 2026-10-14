/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsOutputStream;
import org.jboss.com.sun.corba.se.impl.ior.EncapsulationUtility;
import org.jboss.com.sun.corba.se.impl.logging.IORSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.HexOutputStream;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplate;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplateList;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableContainerBase;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfile;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfileTemplate;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfile;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.IOP.IORHelper;

public class IORImpl
extends IdentifiableContainerBase
implements IOR {
    private String typeId;
    private ORB factory = null;
    private boolean isCachedHashValue = false;
    private int cachedHashValue;
    IORSystemException wrapper;
    private IORTemplateList iortemps = null;

    @Override
    public ORB getORB() {
        return this.factory;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IOR)) {
            return false;
        }
        IOR other = (IOR)obj;
        return super.equals(obj) && this.typeId.equals(other.getTypeId());
    }

    @Override
    public synchronized int hashCode() {
        if (!this.isCachedHashValue) {
            this.cachedHashValue = super.hashCode() ^ this.typeId.hashCode();
            this.isCachedHashValue = true;
        }
        return this.cachedHashValue;
    }

    public IORImpl(ORB orb) {
        this(orb, "");
    }

    public IORImpl(ORB orb, String typeid) {
        this.factory = orb;
        this.wrapper = IORSystemException.get(orb, "oa.ior");
        this.typeId = typeid;
    }

    public IORImpl(ORB orb, String typeId, IORTemplate iortemp, ObjectId id) {
        this(orb, typeId);
        this.iortemps = IORFactories.makeIORTemplateList();
        this.iortemps.add(iortemp);
        this.addTaggedProfiles(iortemp, id);
        this.makeImmutable();
    }

    private void addTaggedProfiles(IORTemplate iortemp, ObjectId id) {
        ObjectKeyTemplate oktemp = iortemp.getObjectKeyTemplate();
        for (TaggedProfileTemplate ptemp : iortemp) {
            TaggedProfile profile = ptemp.create(oktemp, id);
            this.add(profile);
        }
    }

    public IORImpl(ORB orb, String typeId, IORTemplateList iortemps, ObjectId id) {
        this(orb, typeId);
        this.iortemps = iortemps;
        for (IORTemplate iortemp : iortemps) {
            this.addTaggedProfiles(iortemp, id);
        }
        this.makeImmutable();
    }

    public IORImpl(InputStream is) {
        this((ORB)is.orb(), is.read_string());
        IdentifiableFactoryFinder finder = this.factory.getTaggedProfileFactoryFinder();
        EncapsulationUtility.readIdentifiableSequence(this, finder, is);
        this.makeImmutable();
    }

    @Override
    public String getTypeId() {
        return this.typeId;
    }

    @Override
    public void write(OutputStream os) {
        os.write_string(this.typeId);
        EncapsulationUtility.writeIdentifiableSequence(this, os);
    }

    @Override
    public String stringify() {
        EncapsOutputStream s2 = new EncapsOutputStream(this.factory);
        s2.putEndian();
        this.write(s2);
        StringWriter bs = new StringWriter();
        try {
            s2.writeTo(new HexOutputStream(bs));
        }
        catch (IOException ex) {
            throw this.wrapper.stringifyWriteError(ex);
        }
        return "IOR:" + bs;
    }

    @Override
    public synchronized void makeImmutable() {
        this.makeElementsImmutable();
        if (this.iortemps != null) {
            this.iortemps.makeImmutable();
        }
        super.makeImmutable();
    }

    @Override
    public org.omg.IOP.IOR getIOPIOR() {
        EncapsOutputStream os = new EncapsOutputStream(this.factory);
        this.write(os);
        InputStream is = (InputStream)os.create_input_stream();
        return IORHelper.read(is);
    }

    @Override
    public boolean isNil() {
        return this.size() == 0;
    }

    @Override
    public boolean isEquivalent(IOR ior) {
        Iterator myIterator = this.iterator();
        Iterator otherIterator = ior.iterator();
        while (myIterator.hasNext() && otherIterator.hasNext()) {
            TaggedProfile otherProfile;
            TaggedProfile myProfile = (TaggedProfile)myIterator.next();
            if (myProfile.isEquivalent(otherProfile = (TaggedProfile)otherIterator.next())) continue;
            return false;
        }
        return myIterator.hasNext() == otherIterator.hasNext();
    }

    private void initializeIORTemplateList() {
        HashMap<ObjectKeyTemplate, IORTemplate> oktempToIORTemplate = new HashMap<ObjectKeyTemplate, IORTemplate>();
        this.iortemps = IORFactories.makeIORTemplateList();
        Iterator iter = this.iterator();
        ObjectId oid = null;
        while (iter.hasNext()) {
            TaggedProfile prof = (TaggedProfile)iter.next();
            TaggedProfileTemplate ptemp = prof.getTaggedProfileTemplate();
            ObjectKeyTemplate oktemp = prof.getObjectKeyTemplate();
            if (oid == null) {
                oid = prof.getObjectId();
            } else if (!oid.equals(prof.getObjectId())) {
                throw this.wrapper.badOidInIorTemplateList();
            }
            IORTemplate iortemp = (IORTemplate)oktempToIORTemplate.get(oktemp);
            if (iortemp == null) {
                iortemp = IORFactories.makeIORTemplate(oktemp);
                oktempToIORTemplate.put(oktemp, iortemp);
                this.iortemps.add(iortemp);
            }
            iortemp.add(ptemp);
        }
        this.iortemps.makeImmutable();
    }

    @Override
    public synchronized IORTemplateList getIORTemplates() {
        if (this.iortemps == null) {
            this.initializeIORTemplateList();
        }
        return this.iortemps;
    }

    @Override
    public IIOPProfile getProfile() {
        IIOPProfile iop = null;
        Iterator<Object> iter = this.iteratorById(0);
        if (iter.hasNext()) {
            iop = (IIOPProfile)iter.next();
        }
        if (iop != null) {
            return iop;
        }
        throw this.wrapper.iorMustHaveIiopProfile();
    }
}

