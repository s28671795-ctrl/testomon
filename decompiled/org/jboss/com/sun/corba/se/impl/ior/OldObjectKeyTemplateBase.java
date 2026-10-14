/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.ior.ObjectKeyTemplateBase;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;

public abstract class OldObjectKeyTemplateBase
extends ObjectKeyTemplateBase {
    public OldObjectKeyTemplateBase(ORB orb, int magic, int scid, int serverid, String orbid, ObjectAdapterId oaid) {
        super(orb, magic, scid, serverid, orbid, oaid);
        if (magic == -1347695874) {
            this.setORBVersion(ORBVersionFactory.getOLD());
        } else if (magic == -1347695873) {
            this.setORBVersion(ORBVersionFactory.getNEW());
        } else {
            throw this.wrapper.badMagic(new Integer(magic));
        }
    }
}

