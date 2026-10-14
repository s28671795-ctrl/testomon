/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPAddress;
import org.omg.CORBA_2_3.portable.OutputStream;

abstract class IIOPAddressBase
implements IIOPAddress {
    IIOPAddressBase() {
    }

    protected short intToShort(int value) {
        if (value > Short.MAX_VALUE) {
            return (short)(value - 65536);
        }
        return (short)value;
    }

    protected int shortToInt(short value) {
        if (value < 0) {
            return value + 65536;
        }
        return value;
    }

    @Override
    public void write(OutputStream os) {
        os.write_string(this.getHost());
        int port = this.getPort();
        os.write_short(this.intToShort(port));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IIOPAddress)) {
            return false;
        }
        IIOPAddress other = (IIOPAddress)obj;
        return this.getHost().equals(other.getHost()) && this.getPort() == other.getPort();
    }

    public int hashCode() {
        return this.getHost().hashCode() ^ this.getPort();
    }

    public String toString() {
        return "IIOPAddress[" + this.getHost() + "," + this.getPort() + "]";
    }
}

