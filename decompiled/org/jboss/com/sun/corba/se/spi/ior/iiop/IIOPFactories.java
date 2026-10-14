/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior.iiop;

import org.jboss.com.sun.corba.se.impl.ior.iiop.AlternateIIOPAddressComponentImpl;
import org.jboss.com.sun.corba.se.impl.ior.iiop.CodeSetsComponentImpl;
import org.jboss.com.sun.corba.se.impl.ior.iiop.IIOPAddressImpl;
import org.jboss.com.sun.corba.se.impl.ior.iiop.IIOPProfileImpl;
import org.jboss.com.sun.corba.se.impl.ior.iiop.IIOPProfileTemplateImpl;
import org.jboss.com.sun.corba.se.impl.ior.iiop.JavaCodebaseComponentImpl;
import org.jboss.com.sun.corba.se.impl.ior.iiop.JavaSerializationComponent;
import org.jboss.com.sun.corba.se.impl.ior.iiop.MaxStreamFormatVersionComponentImpl;
import org.jboss.com.sun.corba.se.impl.ior.iiop.ORBTypeComponentImpl;
import org.jboss.com.sun.corba.se.impl.ior.iiop.RequestPartitioningComponentImpl;
import org.jboss.com.sun.corba.se.spi.ior.EncapsulationFactoryBase;
import org.jboss.com.sun.corba.se.spi.ior.Identifiable;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableFactory;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.ior.iiop.AlternateIIOPAddressComponent;
import org.jboss.com.sun.corba.se.spi.ior.iiop.CodeSetsComponent;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPAddress;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfile;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
import org.jboss.com.sun.corba.se.spi.ior.iiop.JavaCodebaseComponent;
import org.jboss.com.sun.corba.se.spi.ior.iiop.MaxStreamFormatVersionComponent;
import org.jboss.com.sun.corba.se.spi.ior.iiop.ORBTypeComponent;
import org.jboss.com.sun.corba.se.spi.ior.iiop.RequestPartitioningComponent;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.IOP.TaggedProfile;

public abstract class IIOPFactories {
    private IIOPFactories() {
    }

    public static IdentifiableFactory makeRequestPartitioningComponentFactory() {
        return new EncapsulationFactoryBase(1398099457){

            @Override
            public Identifiable readContents(InputStream in) {
                int threadPoolToUse = in.read_ulong();
                RequestPartitioningComponentImpl comp = new RequestPartitioningComponentImpl(threadPoolToUse);
                return comp;
            }
        };
    }

    public static RequestPartitioningComponent makeRequestPartitioningComponent(int threadPoolToUse) {
        return new RequestPartitioningComponentImpl(threadPoolToUse);
    }

    public static IdentifiableFactory makeAlternateIIOPAddressComponentFactory() {
        return new EncapsulationFactoryBase(3){

            @Override
            public Identifiable readContents(InputStream in) {
                IIOPAddressImpl addr = new IIOPAddressImpl(in);
                AlternateIIOPAddressComponentImpl comp = new AlternateIIOPAddressComponentImpl(addr);
                return comp;
            }
        };
    }

    public static AlternateIIOPAddressComponent makeAlternateIIOPAddressComponent(IIOPAddress addr) {
        return new AlternateIIOPAddressComponentImpl(addr);
    }

    public static IdentifiableFactory makeCodeSetsComponentFactory() {
        return new EncapsulationFactoryBase(1){

            @Override
            public Identifiable readContents(InputStream in) {
                return new CodeSetsComponentImpl(in);
            }
        };
    }

    public static CodeSetsComponent makeCodeSetsComponent(ORB orb) {
        return new CodeSetsComponentImpl(orb);
    }

    public static IdentifiableFactory makeJavaCodebaseComponentFactory() {
        return new EncapsulationFactoryBase(25){

            @Override
            public Identifiable readContents(InputStream in) {
                String url2 = in.read_string();
                JavaCodebaseComponentImpl comp = new JavaCodebaseComponentImpl(url2);
                return comp;
            }
        };
    }

    public static JavaCodebaseComponent makeJavaCodebaseComponent(String codebase) {
        return new JavaCodebaseComponentImpl(codebase);
    }

    public static IdentifiableFactory makeORBTypeComponentFactory() {
        return new EncapsulationFactoryBase(0){

            @Override
            public Identifiable readContents(InputStream in) {
                int type = in.read_ulong();
                ORBTypeComponentImpl comp = new ORBTypeComponentImpl(type);
                return comp;
            }
        };
    }

    public static ORBTypeComponent makeORBTypeComponent(int type) {
        return new ORBTypeComponentImpl(type);
    }

    public static IdentifiableFactory makeMaxStreamFormatVersionComponentFactory() {
        return new EncapsulationFactoryBase(38){

            @Override
            public Identifiable readContents(InputStream in) {
                byte version = in.read_octet();
                MaxStreamFormatVersionComponentImpl comp = new MaxStreamFormatVersionComponentImpl(version);
                return comp;
            }
        };
    }

    public static MaxStreamFormatVersionComponent makeMaxStreamFormatVersionComponent() {
        return new MaxStreamFormatVersionComponentImpl();
    }

    public static IdentifiableFactory makeJavaSerializationComponentFactory() {
        return new EncapsulationFactoryBase(1398099458){

            @Override
            public Identifiable readContents(InputStream in) {
                byte version = in.read_octet();
                JavaSerializationComponent cmp = new JavaSerializationComponent(version);
                return cmp;
            }
        };
    }

    public static JavaSerializationComponent makeJavaSerializationComponent() {
        return JavaSerializationComponent.singleton();
    }

    public static IdentifiableFactory makeIIOPProfileFactory() {
        return new EncapsulationFactoryBase(0){

            @Override
            public Identifiable readContents(InputStream in) {
                IIOPProfileImpl result = new IIOPProfileImpl(in);
                return result;
            }
        };
    }

    public static IIOPProfile makeIIOPProfile(ORB orb, ObjectKeyTemplate oktemp, ObjectId oid, IIOPProfileTemplate ptemp) {
        return new IIOPProfileImpl(orb, oktemp, oid, ptemp);
    }

    public static IIOPProfile makeIIOPProfile(ORB orb, TaggedProfile profile) {
        return new IIOPProfileImpl(orb, profile);
    }

    public static IdentifiableFactory makeIIOPProfileTemplateFactory() {
        return new EncapsulationFactoryBase(0){

            @Override
            public Identifiable readContents(InputStream in) {
                IIOPProfileTemplateImpl result = new IIOPProfileTemplateImpl(in);
                return result;
            }
        };
    }

    public static IIOPProfileTemplate makeIIOPProfileTemplate(ORB orb, GIOPVersion version, IIOPAddress primary) {
        return new IIOPProfileTemplateImpl(orb, version, primary);
    }

    public static IIOPAddress makeIIOPAddress(ORB orb, String host2, int port) {
        return new IIOPAddressImpl(orb, host2, port);
    }

    public static IIOPAddress makeIIOPAddress(InputStream is) {
        return new IIOPAddressImpl(is);
    }
}

