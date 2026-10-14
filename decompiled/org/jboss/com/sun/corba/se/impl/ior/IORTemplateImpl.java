/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.ior.EncapsulationUtility;
import org.jboss.com.sun.corba.se.impl.ior.IORImpl;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactory;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplate;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableContainerBase;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfileTemplate;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class IORTemplateImpl
extends IdentifiableContainerBase
implements IORTemplate {
    private ObjectKeyTemplate oktemp;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IORTemplateImpl)) {
            return false;
        }
        IORTemplateImpl other = (IORTemplateImpl)obj;
        return super.equals(obj) && this.oktemp.equals(other.getObjectKeyTemplate());
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.oktemp.hashCode();
    }

    @Override
    public ObjectKeyTemplate getObjectKeyTemplate() {
        return this.oktemp;
    }

    public IORTemplateImpl(ObjectKeyTemplate oktemp) {
        this.oktemp = oktemp;
    }

    @Override
    public IOR makeIOR(ORB orb, String typeid, ObjectId oid) {
        return new IORImpl(orb, typeid, this, oid);
    }

    @Override
    public boolean isEquivalent(IORFactory other) {
        if (!(other instanceof IORTemplate)) {
            return false;
        }
        IORTemplate list = (IORTemplate)other;
        Iterator thisIterator = this.iterator();
        Iterator listIterator = list.iterator();
        while (thisIterator.hasNext() && listIterator.hasNext()) {
            TaggedProfileTemplate listTemplate;
            TaggedProfileTemplate thisTemplate = (TaggedProfileTemplate)thisIterator.next();
            if (thisTemplate.isEquivalent(listTemplate = (TaggedProfileTemplate)listIterator.next())) continue;
            return false;
        }
        return thisIterator.hasNext() == listIterator.hasNext() && this.getObjectKeyTemplate().equals(list.getObjectKeyTemplate());
    }

    @Override
    public void makeImmutable() {
        this.makeElementsImmutable();
        super.makeImmutable();
    }

    @Override
    public void write(OutputStream os) {
        this.oktemp.write(os);
        EncapsulationUtility.writeIdentifiableSequence(this, os);
    }

    public IORTemplateImpl(InputStream is) {
        ORB orb = (ORB)is.orb();
        IdentifiableFactoryFinder finder = orb.getTaggedProfileTemplateFactoryFinder();
        this.oktemp = orb.getObjectKeyFactory().createTemplate(is);
        EncapsulationUtility.readIdentifiableSequence(this, finder, is);
        this.makeImmutable();
    }
}

