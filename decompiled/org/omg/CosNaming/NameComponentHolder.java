/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameComponentHelper;

public final class NameComponentHolder
implements Streamable {
    public NameComponent value;

    public NameComponentHolder() {
    }

    public NameComponentHolder(NameComponent initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NameComponentHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NameComponentHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NameComponentHelper.write(_out, this.value);
    }
}

