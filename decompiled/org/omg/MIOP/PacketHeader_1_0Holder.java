/*
 * Decompiled with CFR 0.152.
 */
package org.omg.MIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.MIOP.PacketHeader_1_0;
import org.omg.MIOP.PacketHeader_1_0Helper;

public final class PacketHeader_1_0Holder
implements Streamable {
    public PacketHeader_1_0 value;

    public PacketHeader_1_0Holder() {
    }

    public PacketHeader_1_0Holder(PacketHeader_1_0 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PacketHeader_1_0Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = PacketHeader_1_0Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        PacketHeader_1_0Helper.write(_out, this.value);
    }
}

