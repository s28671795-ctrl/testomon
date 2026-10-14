/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WrongTransaction;
import org.omg.CORBA.WrongTransactionHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class WrongTransactionHolder
implements Streamable {
    public WrongTransaction value;

    public WrongTransactionHolder() {
    }

    public WrongTransactionHolder(WrongTransaction initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return WrongTransactionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = WrongTransactionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        WrongTransactionHelper.write(_out, this.value);
    }
}

