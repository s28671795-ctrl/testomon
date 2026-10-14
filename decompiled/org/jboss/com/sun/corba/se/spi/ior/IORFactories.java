/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import java.io.Serializable;
import org.jboss.com.sun.corba.se.impl.ior.IORImpl;
import org.jboss.com.sun.corba.se.impl.ior.IORTemplateImpl;
import org.jboss.com.sun.corba.se.impl.ior.IORTemplateListImpl;
import org.jboss.com.sun.corba.se.impl.ior.ObjectIdImpl;
import org.jboss.com.sun.corba.se.impl.ior.ObjectKeyFactoryImpl;
import org.jboss.com.sun.corba.se.impl.ior.ObjectKeyImpl;
import org.jboss.com.sun.corba.se.impl.ior.ObjectReferenceFactoryImpl;
import org.jboss.com.sun.corba.se.impl.ior.ObjectReferenceProducerBase;
import org.jboss.com.sun.corba.se.impl.ior.ObjectReferenceTemplateImpl;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactory;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplate;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplateList;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyFactory;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ValueFactory;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;

public class IORFactories {
    private IORFactories() {
    }

    public static ObjectId makeObjectId(byte[] id) {
        return new ObjectIdImpl(id);
    }

    public static ObjectKey makeObjectKey(ObjectKeyTemplate oktemp, ObjectId oid) {
        return new ObjectKeyImpl(oktemp, oid);
    }

    public static IOR makeIOR(ORB orb, String typeid) {
        return new IORImpl(orb, typeid);
    }

    public static IOR makeIOR(ORB orb) {
        return new IORImpl(orb);
    }

    public static IOR makeIOR(InputStream is) {
        return new IORImpl(is);
    }

    public static IORTemplate makeIORTemplate(ObjectKeyTemplate oktemp) {
        return new IORTemplateImpl(oktemp);
    }

    public static IORTemplate makeIORTemplate(InputStream is) {
        return new IORTemplateImpl(is);
    }

    public static IORTemplateList makeIORTemplateList() {
        return new IORTemplateListImpl();
    }

    public static IORTemplateList makeIORTemplateList(InputStream is) {
        return new IORTemplateListImpl(is);
    }

    public static IORFactory getIORFactory(ObjectReferenceTemplate ort) {
        if (ort instanceof ObjectReferenceTemplateImpl) {
            ObjectReferenceTemplateImpl orti = (ObjectReferenceTemplateImpl)ort;
            return orti.getIORFactory();
        }
        throw new BAD_PARAM();
    }

    public static IORTemplateList getIORTemplateList(ObjectReferenceFactory orf) {
        if (orf instanceof ObjectReferenceProducerBase) {
            ObjectReferenceProducerBase base = (ObjectReferenceProducerBase)((java.lang.Object)orf);
            return base.getIORTemplateList();
        }
        throw new BAD_PARAM();
    }

    public static ObjectReferenceTemplate makeObjectReferenceTemplate(ORB orb, IORTemplate iortemp) {
        return new ObjectReferenceTemplateImpl(orb, iortemp);
    }

    public static ObjectReferenceFactory makeObjectReferenceFactory(ORB orb, IORTemplateList iortemps) {
        return new ObjectReferenceFactoryImpl(orb, iortemps);
    }

    public static ObjectKeyFactory makeObjectKeyFactory(ORB orb) {
        return new ObjectKeyFactoryImpl(orb);
    }

    public static IOR getIOR(Object obj) {
        return ORBUtility.getIOR(obj);
    }

    public static Object makeObjectReference(IOR ior) {
        return ORBUtility.makeObjectReference(ior);
    }

    public static void registerValueFactories(ORB orb) {
        ValueFactory vf = new ValueFactory(){

            @Override
            public Serializable read_value(InputStream is) {
                return new ObjectReferenceTemplateImpl(is);
            }
        };
        orb.register_value_factory("IDL:com/sun/corba/se/impl/ior/ObjectReferenceTemplateImpl:1.0", vf);
        vf = new ValueFactory(){

            @Override
            public Serializable read_value(InputStream is) {
                return new ObjectReferenceFactoryImpl(is);
            }
        };
        orb.register_value_factory("IDL:com/sun/corba/se/impl/ior/ObjectReferenceFactoryImpl:1.0", vf);
    }
}

