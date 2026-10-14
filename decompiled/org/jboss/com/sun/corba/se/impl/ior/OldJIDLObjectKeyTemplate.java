/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.ior.OldObjectKeyTemplateBase;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class OldJIDLObjectKeyTemplate
extends OldObjectKeyTemplateBase {
    public static final byte NULL_PATCH_VERSION = 0;
    byte patchVersion = 0;

    public OldJIDLObjectKeyTemplate(ORB orb, int magic, int scid, InputStream is, OctetSeqHolder osh) {
        this(orb, magic, scid, is);
        osh.value = this.readObjectKey(is);
        if (magic == -1347695873 && osh.value.length > ((CDRInputStream)is).getPosition()) {
            this.patchVersion = is.read_octet();
            if (this.patchVersion == 1) {
                this.setORBVersion(ORBVersionFactory.getJDK1_3_1_01());
            } else if (this.patchVersion > 1) {
                this.setORBVersion(ORBVersionFactory.getORBVersion());
            } else {
                throw this.wrapper.invalidJdk131PatchLevel(new Integer(this.patchVersion));
            }
        }
    }

    public OldJIDLObjectKeyTemplate(ORB orb, int magic, int scid, int serverid) {
        super(orb, magic, scid, serverid, "", JIDL_OAID);
    }

    public OldJIDLObjectKeyTemplate(ORB orb, int magic, int scid, InputStream is) {
        this(orb, magic, scid, is.read_long());
    }

    @Override
    protected void writeTemplate(OutputStream os) {
        os.write_long(this.getMagic());
        os.write_long(this.getSubcontractId());
        os.write_long(this.getServerId());
    }

    @Override
    public void write(ObjectId objectId, OutputStream os) {
        super.write(objectId, os);
        if (this.patchVersion != 0) {
            os.write_octet(this.patchVersion);
        }
    }
}

