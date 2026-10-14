/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.CosNaming.NamingContextPackage.NotFoundHelper;

public final class NotFoundHolder
implements Streamable {
    public NotFound value;

    public NotFoundHolder() {
    }

    public NotFoundHolder(NotFound initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NotFoundHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NotFoundHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NotFoundHelper.write(_out, this.value);
    }
}

