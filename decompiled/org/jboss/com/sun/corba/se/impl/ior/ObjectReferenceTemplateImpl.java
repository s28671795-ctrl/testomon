/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.omg.PortableInterceptor.ObjectReferenceTemplateHelper
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.ior.ObjectReferenceProducerBase;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.IORFactory;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplate;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplateList;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.StreamableValue;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;
import org.omg.PortableInterceptor.ObjectReferenceTemplateHelper;

public class ObjectReferenceTemplateImpl
extends ObjectReferenceProducerBase
implements ObjectReferenceTemplate,
StreamableValue {
    private static final long serialVersionUID = 2228154578761368474L;
    private transient IORTemplate iorTemplate;
    public static final String repositoryId = "IDL:com/sun/corba/se/impl/ior/ObjectReferenceTemplateImpl:1.0";

    public ObjectReferenceTemplateImpl(org.omg.CORBA.portable.InputStream is) {
        super((ORB)is.orb());
        this._read(is);
    }

    public ObjectReferenceTemplateImpl(ORB orb, IORTemplate iortemp) {
        super(orb);
        this.iorTemplate = iortemp;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ObjectReferenceTemplateImpl)) {
            return false;
        }
        ObjectReferenceTemplateImpl other = (ObjectReferenceTemplateImpl)obj;
        return this.iorTemplate != null && this.iorTemplate.equals(other.iorTemplate);
    }

    public int hashCode() {
        return this.iorTemplate.hashCode();
    }

    @Override
    public String[] _truncatable_ids() {
        return new String[]{repositoryId};
    }

    @Override
    public TypeCode _type() {
        return ObjectReferenceTemplateHelper.type();
    }

    @Override
    public void _read(org.omg.CORBA.portable.InputStream is) {
        InputStream istr = (InputStream)is;
        this.iorTemplate = IORFactories.makeIORTemplate(istr);
        this.orb = (ORB)istr.orb();
    }

    @Override
    public void _write(org.omg.CORBA.portable.OutputStream os) {
        OutputStream ostr = (OutputStream)os;
        this.iorTemplate.write(ostr);
    }

    @Override
    public String server_id() {
        int val = this.iorTemplate.getObjectKeyTemplate().getServerId();
        return Integer.toString(val);
    }

    @Override
    public String orb_id() {
        return this.iorTemplate.getObjectKeyTemplate().getORBId();
    }

    @Override
    public String[] adapter_name() {
        ObjectAdapterId poaid = this.iorTemplate.getObjectKeyTemplate().getObjectAdapterId();
        return poaid.getAdapterName();
    }

    @Override
    public IORFactory getIORFactory() {
        return this.iorTemplate;
    }

    @Override
    public IORTemplateList getIORTemplateList() {
        IORTemplateList tl = IORFactories.makeIORTemplateList();
        tl.add(this.iorTemplate);
        tl.makeImmutable();
        return tl;
    }
}

