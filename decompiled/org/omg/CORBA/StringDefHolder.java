/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.StringDef;
import org.omg.CORBA.StringDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class StringDefHolder
implements Streamable {
    public StringDef value;

    public StringDefHolder() {
    }

    public StringDefHolder(StringDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return StringDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = StringDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        StringDefHelper.write(_out, this.value);
    }
}

