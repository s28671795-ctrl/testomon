/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orb;

import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.omg.CORBA.portable.OutputStream;

public class ORBVersionImpl
implements ORBVersion {
    private byte orbType;
    public static final ORBVersion FOREIGN = new ORBVersionImpl(0);
    public static final ORBVersion OLD = new ORBVersionImpl(1);
    public static final ORBVersion NEW = new ORBVersionImpl(2);
    public static final ORBVersion JDK1_3_1_01 = new ORBVersionImpl(3);
    public static final ORBVersion NEWER = new ORBVersionImpl(10);
    public static final ORBVersion PEORB = new ORBVersionImpl(20);

    public ORBVersionImpl(byte orbType) {
        this.orbType = orbType;
    }

    @Override
    public byte getORBType() {
        return this.orbType;
    }

    @Override
    public void write(OutputStream os) {
        os.write_octet(this.orbType);
    }

    public String toString() {
        return "ORBVersionImpl[" + Byte.toString(this.orbType) + "]";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ORBVersion)) {
            return false;
        }
        ORBVersion version = (ORBVersion)obj;
        return version.getORBType() == this.orbType;
    }

    public int hashCode() {
        return this.orbType;
    }

    @Override
    public boolean lessThan(ORBVersion version) {
        return this.orbType < version.getORBType();
    }

    @Override
    public int compareTo(ORBVersion obj) {
        return this.getORBType() - obj.getORBType();
    }
}

