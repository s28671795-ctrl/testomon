/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.IORFactory;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplateList;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.Object;

public abstract class ObjectReferenceProducerBase {
    protected transient ORB orb;

    public abstract IORFactory getIORFactory();

    public abstract IORTemplateList getIORTemplateList();

    public ObjectReferenceProducerBase(ORB orb) {
        this.orb = orb;
    }

    public Object make_object(String repositoryId, byte[] objectId) {
        ObjectId oid = IORFactories.makeObjectId(objectId);
        IOR ior = this.getIORFactory().makeIOR(this.orb, repositoryId, oid);
        return ORBUtility.makeObjectReference(ior);
    }
}

