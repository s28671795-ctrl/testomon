/*
 * Decompiled with CFR 0.152.
 */
package org.omg.MIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.MIOP.UIPMC_ProfileBody;
import org.omg.MIOP.UIPMC_ProfileBodyHelper;

public final class UIPMC_ProfileBodyHolder
implements Streamable {
    public UIPMC_ProfileBody value;

    public UIPMC_ProfileBodyHolder() {
    }

    public UIPMC_ProfileBodyHolder(UIPMC_ProfileBody initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UIPMC_ProfileBodyHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = UIPMC_ProfileBodyHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        UIPMC_ProfileBodyHelper.write(_out, this.value);
    }
}

