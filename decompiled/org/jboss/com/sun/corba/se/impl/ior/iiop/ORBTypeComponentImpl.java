/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.spi.ior.TaggedComponentBase;
import org.jboss.com.sun.corba.se.spi.ior.iiop.ORBTypeComponent;
import org.omg.CORBA_2_3.portable.OutputStream;

public class ORBTypeComponentImpl
extends TaggedComponentBase
implements ORBTypeComponent {
    private int ORBType;

    public boolean equals(Object obj) {
        if (!(obj instanceof ORBTypeComponentImpl)) {
            return false;
        }
        ORBTypeComponentImpl other = (ORBTypeComponentImpl)obj;
        return this.ORBType == other.ORBType;
    }

    public int hashCode() {
        return this.ORBType;
    }

    public String toString() {
        return "ORBTypeComponentImpl[ORBType=" + this.ORBType + "]";
    }

    public ORBTypeComponentImpl(int ORBType) {
        this.ORBType = ORBType;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getORBType() {
        return this.ORBType;
    }

    @Override
    public void writeContents(OutputStream os) {
        os.write_ulong(this.ORBType);
    }
}

