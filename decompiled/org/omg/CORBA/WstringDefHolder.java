/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WstringDef;
import org.omg.CORBA.WstringDefHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class WstringDefHolder
implements Streamable {
    public WstringDef value;

    public WstringDefHolder() {
    }

    public WstringDefHolder(WstringDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return WstringDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = WstringDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        WstringDefHelper.write(_out, this.value);
    }
}

