/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper;

public final class AlreadyBoundHolder
implements Streamable {
    public AlreadyBound value;

    public AlreadyBoundHolder() {
    }

    public AlreadyBoundHolder(AlreadyBound initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AlreadyBoundHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AlreadyBoundHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AlreadyBoundHelper.write(_out, this.value);
    }
}

