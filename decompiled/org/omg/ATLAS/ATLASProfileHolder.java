/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.ATLASProfile;
import org.omg.ATLAS.ATLASProfileHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ATLASProfileHolder
implements Streamable {
    public ATLASProfile value;

    public ATLASProfileHolder() {
    }

    public ATLASProfileHolder(ATLASProfile initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ATLASProfileHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ATLASProfileHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ATLASProfileHelper.write(_out, this.value);
    }
}

