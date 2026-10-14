/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.CannotProceedHelper;

public final class CannotProceedHolder
implements Streamable {
    public CannotProceed value;

    public CannotProceedHolder() {
    }

    public CannotProceedHolder(CannotProceed initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CannotProceedHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CannotProceedHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CannotProceedHelper.write(_out, this.value);
    }
}

