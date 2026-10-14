/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.LocateStatusType_1_2;
import org.omg.GIOP.LocateStatusType_1_2Helper;

public final class LocateStatusType_1_2Holder
implements Streamable {
    public LocateStatusType_1_2 value;

    public LocateStatusType_1_2Holder() {
    }

    public LocateStatusType_1_2Holder(LocateStatusType_1_2 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LocateStatusType_1_2Helper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LocateStatusType_1_2Helper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        LocateStatusType_1_2Helper.write(out, this.value);
    }
}

