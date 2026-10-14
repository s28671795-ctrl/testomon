/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.ior.ObjectAdapterIdNumber;
import org.jboss.com.sun.corba.se.impl.ior.OldObjectKeyTemplateBase;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class OldPOAObjectKeyTemplate
extends OldObjectKeyTemplateBase {
    public OldPOAObjectKeyTemplate(ORB orb, int magic, int scid, InputStream is) {
        this(orb, magic, scid, is.read_long(), is.read_long(), is.read_long());
    }

    public OldPOAObjectKeyTemplate(ORB orb, int magic, int scid, InputStream is, OctetSeqHolder osh) {
        this(orb, magic, scid, is);
        osh.value = this.readObjectKey(is);
    }

    public OldPOAObjectKeyTemplate(ORB orb, int magic, int scid, int serverid, int orbid, int poaid) {
        super(orb, magic, scid, serverid, Integer.toString(orbid), new ObjectAdapterIdNumber(poaid));
    }

    @Override
    public void writeTemplate(OutputStream os) {
        os.write_long(this.getMagic());
        os.write_long(this.getSubcontractId());
        os.write_long(this.getServerId());
        int orbid = Integer.parseInt(this.getORBId());
        os.write_long(orbid);
        ObjectAdapterIdNumber oaid = (ObjectAdapterIdNumber)this.getObjectAdapterId();
        int poaid = oaid.getOldPOAId();
        os.write_long(poaid);
    }

    @Override
    public ORBVersion getORBVersion() {
        if (this.getMagic() == -1347695874) {
            return ORBVersionFactory.getOLD();
        }
        if (this.getMagic() == -1347695873) {
            return ORBVersionFactory.getNEW();
        }
        throw new INTERNAL();
    }
}

