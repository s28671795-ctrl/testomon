/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.omg.CosNaming.NamingContextPackage.NotEmptyHelper;

public final class NotEmptyHolder
implements Streamable {
    public NotEmpty value;

    public NotEmptyHolder() {
    }

    public NotEmptyHolder(NotEmpty initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NotEmptyHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NotEmptyHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NotEmptyHelper.write(_out, this.value);
    }
}

