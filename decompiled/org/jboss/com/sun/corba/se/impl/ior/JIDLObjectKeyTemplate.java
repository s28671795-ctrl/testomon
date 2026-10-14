/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class JIDLObjectKeyTemplate
extends NewObjectKeyTemplateBase {
    public JIDLObjectKeyTemplate(ORB orb, int magic, int scid, InputStream is) {
        super(orb, magic, scid, is.read_long(), "", JIDL_OAID);
        this.setORBVersion(is);
    }

    public JIDLObjectKeyTemplate(ORB orb, int magic, int scid, InputStream is, OctetSeqHolder osh) {
        super(orb, magic, scid, is.read_long(), "", JIDL_OAID);
        osh.value = this.readObjectKey(is);
        this.setORBVersion(is);
    }

    public JIDLObjectKeyTemplate(ORB orb, int scid, int serverid) {
        super(orb, -1347695872, scid, serverid, "", JIDL_OAID);
        this.setORBVersion(ORBVersionFactory.getORBVersion());
    }

    @Override
    protected void writeTemplate(OutputStream os) {
        os.write_long(this.getMagic());
        os.write_long(this.getSubcontractId());
        os.write_long(this.getServerId());
    }
}

