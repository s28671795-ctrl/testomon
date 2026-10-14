/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.ior.ObjectKeyTemplateBase;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public abstract class NewObjectKeyTemplateBase
extends ObjectKeyTemplateBase {
    public NewObjectKeyTemplateBase(ORB orb, int magic, int scid, int serverid, String orbid, ObjectAdapterId oaid) {
        super(orb, magic, scid, serverid, orbid, oaid);
        if (magic != -1347695872) {
            throw this.wrapper.badMagic(new Integer(magic));
        }
    }

    @Override
    public void write(ObjectId objectId, OutputStream os) {
        super.write(objectId, os);
        this.getORBVersion().write(os);
    }

    @Override
    public void write(OutputStream os) {
        super.write(os);
        this.getORBVersion().write(os);
    }

    protected void setORBVersion(InputStream is) {
        ORBVersion version = ORBVersionFactory.create(is);
        this.setORBVersion(version);
    }
}

