/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.omg.PortableInterceptor.ObjectReferenceFactoryHelper
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.ior.ObjectReferenceProducerBase;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.IORFactory;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplateList;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.StreamableValue;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.omg.PortableInterceptor.ObjectReferenceFactoryHelper;

public class ObjectReferenceFactoryImpl
extends ObjectReferenceProducerBase
implements ObjectReferenceFactory,
StreamableValue {
    private static final long serialVersionUID = -6209340217525197778L;
    private transient IORTemplateList iorTemplates;
    public static final String repositoryId = "IDL:com/sun/corba/se/impl/ior/ObjectReferenceFactoryImpl:1.0";

    public ObjectReferenceFactoryImpl(org.omg.CORBA.portable.InputStream is) {
        super((ORB)is.orb());
        this._read(is);
    }

    public ObjectReferenceFactoryImpl(ORB orb, IORTemplateList iortemps) {
        super(orb);
        this.iorTemplates = iortemps;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ObjectReferenceFactoryImpl)) {
            return false;
        }
        ObjectReferenceFactoryImpl other = (ObjectReferenceFactoryImpl)obj;
        return this.iorTemplates != null && this.iorTemplates.equals(other.iorTemplates);
    }

    public int hashCode() {
        return this.iorTemplates.hashCode();
    }

    @Override
    public String[] _truncatable_ids() {
        return new String[]{repositoryId};
    }

    @Override
    public TypeCode _type() {
        return ObjectReferenceFactoryHelper.type();
    }

    @Override
    public void _read(org.omg.CORBA.portable.InputStream is) {
        InputStream istr = (InputStream)is;
        this.iorTemplates = IORFactories.makeIORTemplateList(istr);
    }

    @Override
    public void _write(org.omg.CORBA.portable.OutputStream os) {
        OutputStream ostr = (OutputStream)os;
        this.iorTemplates.write(ostr);
    }

    @Override
    public IORFactory getIORFactory() {
        return this.iorTemplates;
    }

    @Override
    public IORTemplateList getIORTemplateList() {
        return this.iorTemplates;
    }
}

