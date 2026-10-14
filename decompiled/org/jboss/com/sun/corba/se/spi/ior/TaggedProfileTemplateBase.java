/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.ior.EncapsulationUtility;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableContainerBase;
import org.jboss.com.sun.corba.se.spi.ior.TaggedComponent;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfileTemplate;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.OutputStream;

public abstract class TaggedProfileTemplateBase
extends IdentifiableContainerBase
implements TaggedProfileTemplate {
    @Override
    public void write(OutputStream os) {
        EncapsulationUtility.writeEncapsulation(this, os);
    }

    @Override
    public org.omg.IOP.TaggedComponent[] getIOPComponents(ORB orb, int id) {
        int count = 0;
        Iterator<Object> iter = this.iteratorById(id);
        while (iter.hasNext()) {
            iter.next();
            ++count;
        }
        org.omg.IOP.TaggedComponent[] result = new org.omg.IOP.TaggedComponent[count];
        int index = 0;
        iter = this.iteratorById(id);
        while (iter.hasNext()) {
            TaggedComponent comp = (TaggedComponent)iter.next();
            result[index++] = comp.getIOPComponent(orb);
        }
        return result;
    }
}

