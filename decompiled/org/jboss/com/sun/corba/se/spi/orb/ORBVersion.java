/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orb;

import org.omg.CORBA.portable.OutputStream;

public interface ORBVersion
extends Comparable<ORBVersion> {
    public static final byte FOREIGN = 0;
    public static final byte OLD = 1;
    public static final byte NEW = 2;
    public static final byte JDK1_3_1_01 = 3;
    public static final byte NEWER = 10;
    public static final byte PEORB = 20;

    public byte getORBType();

    public void write(OutputStream var1);

    public boolean lessThan(ORBVersion var1);
}

