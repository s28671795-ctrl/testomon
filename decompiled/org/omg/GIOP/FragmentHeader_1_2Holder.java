/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.FragmentHeader_1_2;
import org.omg.GIOP.FragmentHeader_1_2Helper;

public final class FragmentHeader_1_2Holder
implements Streamable {
    public FragmentHeader_1_2 value;

    public FragmentHeader_1_2Holder() {
    }

    public FragmentHeader_1_2Holder(FragmentHeader_1_2 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return FragmentHeader_1_2Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = FragmentHeader_1_2Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        FragmentHeader_1_2Helper.write(_out, this.value);
    }
}

