/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

import java.util.Arrays;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class ObjectIdImpl
implements ObjectId {
    private byte[] id;

    public boolean equals(Object obj) {
        if (!(obj instanceof ObjectIdImpl)) {
            return false;
        }
        ObjectIdImpl other = (ObjectIdImpl)obj;
        return Arrays.equals(this.id, other.id);
    }

    public int hashCode() {
        int result = 17;
        for (int ctr = 0; ctr < this.id.length; ++ctr) {
            result = 37 * result + this.id[ctr];
        }
        return result;
    }

    public ObjectIdImpl(byte[] id) {
        this.id = id;
    }

    @Override
    public byte[] getId() {
        return this.id;
    }

    @Override
    public void write(OutputStream os) {
        os.write_long(this.id.length);
        os.write_octet_array(this.id, 0, this.id.length);
    }
}

