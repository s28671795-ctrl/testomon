/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import org.jboss.com.sun.corba.se.impl.encoding.EncapsOutputStream;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
import org.omg.CORBA.ORB;
import org.omg.CORBA_2_3.portable.OutputStream;

public class ObjectKeyImpl
implements ObjectKey {
    private ObjectKeyTemplate oktemp;
    private ObjectId id;

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ObjectKeyImpl)) {
            return false;
        }
        ObjectKeyImpl other = (ObjectKeyImpl)obj;
        return this.oktemp.equals(other.oktemp) && this.id.equals(other.id);
    }

    public int hashCode() {
        return this.oktemp.hashCode() ^ this.id.hashCode();
    }

    @Override
    public ObjectKeyTemplate getTemplate() {
        return this.oktemp;
    }

    @Override
    public ObjectId getId() {
        return this.id;
    }

    public ObjectKeyImpl(ObjectKeyTemplate oktemp, ObjectId id) {
        this.oktemp = oktemp;
        this.id = id;
    }

    @Override
    public void write(OutputStream os) {
        this.oktemp.write(this.id, os);
    }

    @Override
    public byte[] getBytes(ORB orb) {
        EncapsOutputStream os = new EncapsOutputStream((org.jboss.com.sun.corba.se.spi.orb.ORB)orb);
        this.write(os);
        return os.toByteArray();
    }

    @Override
    public CorbaServerRequestDispatcher getServerRequestDispatcher(org.jboss.com.sun.corba.se.spi.orb.ORB orb) {
        return this.oktemp.getServerRequestDispatcher(orb, this.id);
    }
}

