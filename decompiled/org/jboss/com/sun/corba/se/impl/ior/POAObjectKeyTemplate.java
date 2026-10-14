/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;
import org.jboss.com.sun.corba.se.impl.ior.ObjectAdapterIdArray;
import org.jboss.com.sun.corba.se.spi.activation.POANameHelper;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class POAObjectKeyTemplate
extends NewObjectKeyTemplateBase {
    public POAObjectKeyTemplate(ORB orb, int magic, int scid, InputStream is) {
        super(orb, magic, scid, is.read_long(), is.read_string(), new ObjectAdapterIdArray(POANameHelper.read(is)));
        this.setORBVersion(is);
    }

    public POAObjectKeyTemplate(ORB orb, int magic, int scid, InputStream is, OctetSeqHolder osh) {
        super(orb, magic, scid, is.read_long(), is.read_string(), new ObjectAdapterIdArray(POANameHelper.read(is)));
        osh.value = this.readObjectKey(is);
        this.setORBVersion(is);
    }

    public POAObjectKeyTemplate(ORB orb, int scid, int serverid, String orbid, ObjectAdapterId objectAdapterId) {
        super(orb, -1347695872, scid, serverid, orbid, objectAdapterId);
        this.setORBVersion(ORBVersionFactory.getORBVersion());
    }

    @Override
    public void writeTemplate(OutputStream os) {
        os.write_long(this.getMagic());
        os.write_long(this.getSubcontractId());
        os.write_long(this.getServerId());
        os.write_string(this.getORBId());
        this.getObjectAdapterId().write(os);
    }
}

