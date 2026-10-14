/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.spi.ior.TaggedComponentBase;
import org.jboss.com.sun.corba.se.spi.ior.iiop.AlternateIIOPAddressComponent;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPAddress;
import org.omg.CORBA_2_3.portable.OutputStream;

public class AlternateIIOPAddressComponentImpl
extends TaggedComponentBase
implements AlternateIIOPAddressComponent {
    private IIOPAddress addr;

    public boolean equals(Object obj) {
        if (!(obj instanceof AlternateIIOPAddressComponentImpl)) {
            return false;
        }
        AlternateIIOPAddressComponentImpl other = (AlternateIIOPAddressComponentImpl)obj;
        return this.addr.equals(other.addr);
    }

    public int hashCode() {
        return this.addr.hashCode();
    }

    public String toString() {
        return "AlternateIIOPAddressComponentImpl[addr=" + this.addr + "]";
    }

    public AlternateIIOPAddressComponentImpl(IIOPAddress addr) {
        this.addr = addr;
    }

    @Override
    public IIOPAddress getAddress() {
        return this.addr;
    }

    @Override
    public void writeContents(OutputStream os) {
        this.addr.write(os);
    }

    @Override
    public int getId() {
        return 3;
    }
}

