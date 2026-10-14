/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.LocateStatusType_1_0;
import org.omg.GIOP.LocateStatusType_1_0Helper;

public final class LocateStatusType_1_0Holder
implements Streamable {
    public LocateStatusType_1_0 value;

    public LocateStatusType_1_0Holder() {
    }

    public LocateStatusType_1_0Holder(LocateStatusType_1_0 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LocateStatusType_1_0Helper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LocateStatusType_1_0Helper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        LocateStatusType_1_0Helper.write(out, this.value);
    }
}

