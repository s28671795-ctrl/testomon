/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsInputStream;
import org.jboss.com.sun.corba.se.impl.ior.Handler;
import org.jboss.com.sun.corba.se.impl.ior.JIDLObjectKeyTemplate;
import org.jboss.com.sun.corba.se.impl.ior.ObjectIdImpl;
import org.jboss.com.sun.corba.se.impl.ior.ObjectKeyImpl;
import org.jboss.com.sun.corba.se.impl.ior.ObjectKeyTemplateBase;
import org.jboss.com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.jboss.com.sun.corba.se.impl.ior.OldPOAObjectKeyTemplate;
import org.jboss.com.sun.corba.se.impl.ior.POAObjectKeyTemplate;
import org.jboss.com.sun.corba.se.impl.ior.WireObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyFactory;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA_2_3.portable.InputStream;

public class ObjectKeyFactoryImpl
implements ObjectKeyFactory {
    public static final int MAGIC_BASE = -1347695874;
    public static final int JAVAMAGIC_OLD = -1347695874;
    public static final int JAVAMAGIC_NEW = -1347695873;
    public static final int JAVAMAGIC_NEWER = -1347695872;
    public static final int MAX_MAGIC = -1347695872;
    public static final byte JDK1_3_1_01_PATCH_LEVEL = 1;
    private final ORB orb;
    private Handler fullKey = new Handler(){

        @Override
        public ObjectKeyTemplate handle(int magic, int scid, InputStream is, OctetSeqHolder osh) {
            ObjectKeyTemplateBase oktemp = null;
            if (scid >= 32 && scid <= 63) {
                oktemp = magic >= -1347695872 ? new POAObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, magic, scid, is, osh) : new OldPOAObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, magic, scid, is, osh);
            } else if (scid >= 0 && scid < 32) {
                oktemp = magic >= -1347695872 ? new JIDLObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, magic, scid, is, osh) : new OldJIDLObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, magic, scid, is, osh);
            }
            return oktemp;
        }
    };
    private Handler oktempOnly = new Handler(){

        @Override
        public ObjectKeyTemplate handle(int magic, int scid, InputStream is, OctetSeqHolder osh) {
            ObjectKeyTemplateBase oktemp = null;
            if (scid >= 32 && scid <= 63) {
                oktemp = magic >= -1347695872 ? new POAObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, magic, scid, is) : new OldPOAObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, magic, scid, is);
            } else if (scid >= 0 && scid < 32) {
                oktemp = magic >= -1347695872 ? new JIDLObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, magic, scid, is) : new OldJIDLObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, magic, scid, is);
            }
            return oktemp;
        }
    };

    public ObjectKeyFactoryImpl(ORB orb) {
        this.orb = orb;
    }

    private boolean validMagic(int magic) {
        return magic >= -1347695874 && magic <= -1347695872;
    }

    private ObjectKeyTemplate create(InputStream is, Handler handler, OctetSeqHolder osh) {
        ObjectKeyTemplate oktemp = null;
        try {
            is.mark(0);
            int magic = is.read_long();
            if (this.validMagic(magic)) {
                int scid = is.read_long();
                oktemp = handler.handle(magic, scid, is, osh);
            }
        }
        catch (MARSHAL mARSHAL) {
            // empty catch block
        }
        if (oktemp == null) {
            try {
                is.reset();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return oktemp;
    }

    @Override
    public ObjectKey create(byte[] key) {
        EncapsInputStream is = new EncapsInputStream(this.orb, key, key.length);
        OctetSeqHolder osh = new OctetSeqHolder();
        ObjectKeyTemplate oktemp = this.create(is, this.fullKey, osh);
        if (oktemp == null) {
            oktemp = new WireObjectKeyTemplate(is, osh);
        }
        ObjectIdImpl oid = new ObjectIdImpl(osh.value);
        return new ObjectKeyImpl(oktemp, oid);
    }

    @Override
    public ObjectKeyTemplate createTemplate(InputStream is) {
        ObjectKeyTemplate oktemp = this.create(is, this.oktempOnly, null);
        if (oktemp == null) {
            oktemp = new WireObjectKeyTemplate(this.orb);
        }
        return oktemp;
    }
}

