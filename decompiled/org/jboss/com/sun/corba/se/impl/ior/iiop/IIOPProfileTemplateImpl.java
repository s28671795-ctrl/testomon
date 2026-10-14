/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsOutputStream;
import org.jboss.com.sun.corba.se.impl.ior.EncapsulationUtility;
import org.jboss.com.sun.corba.se.impl.ior.iiop.IIOPAddressImpl;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfile;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfileTemplate;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfileTemplateBase;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPAddress;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class IIOPProfileTemplateImpl
extends TaggedProfileTemplateBase
implements IIOPProfileTemplate {
    private ORB orb;
    private GIOPVersion giopVersion;
    private IIOPAddress primary;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IIOPProfileTemplateImpl)) {
            return false;
        }
        IIOPProfileTemplateImpl other = (IIOPProfileTemplateImpl)obj;
        return super.equals(obj) && this.giopVersion.equals(other.giopVersion) && this.primary.equals(other.primary);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.giopVersion.hashCode() ^ this.primary.hashCode();
    }

    @Override
    public TaggedProfile create(ObjectKeyTemplate oktemp, ObjectId id) {
        return IIOPFactories.makeIIOPProfile(this.orb, oktemp, id, this);
    }

    @Override
    public GIOPVersion getGIOPVersion() {
        return this.giopVersion;
    }

    @Override
    public IIOPAddress getPrimaryAddress() {
        return this.primary;
    }

    public IIOPProfileTemplateImpl(ORB orb, GIOPVersion version, IIOPAddress primary) {
        this.orb = orb;
        this.giopVersion = version;
        this.primary = primary;
        if (this.giopVersion.getMinor() == 0) {
            this.makeImmutable();
        }
    }

    public IIOPProfileTemplateImpl(InputStream istr) {
        byte major = istr.read_octet();
        byte minor = istr.read_octet();
        this.giopVersion = GIOPVersion.getInstance(major, minor);
        this.primary = new IIOPAddressImpl(istr);
        this.orb = (ORB)istr.orb();
        if (minor > 0) {
            EncapsulationUtility.readIdentifiableSequence(this, this.orb.getTaggedComponentFactoryFinder(), istr);
        }
        this.makeImmutable();
    }

    @Override
    public void write(ObjectKeyTemplate okeyTemplate, ObjectId id, OutputStream os) {
        this.giopVersion.write(os);
        this.primary.write(os);
        EncapsOutputStream encapsulatedOS = new EncapsOutputStream((ORB)os.orb(), ((CDROutputStream)os).isLittleEndian());
        okeyTemplate.write(id, encapsulatedOS);
        EncapsulationUtility.writeOutputStream(encapsulatedOS, os);
        if (this.giopVersion.getMinor() > 0) {
            EncapsulationUtility.writeIdentifiableSequence(this, os);
        }
    }

    @Override
    public void writeContents(OutputStream os) {
        this.giopVersion.write(os);
        this.primary.write(os);
        if (this.giopVersion.getMinor() > 0) {
            EncapsulationUtility.writeIdentifiableSequence(this, os);
        }
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isEquivalent(TaggedProfileTemplate temp) {
        if (!(temp instanceof IIOPProfileTemplateImpl)) {
            return false;
        }
        IIOPProfileTemplateImpl tempimp = (IIOPProfileTemplateImpl)temp;
        return this.primary.equals(tempimp.primary);
    }
}

