/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.ExceptionHolder;
import org.omg.Messaging.ExceptionHolderHelper;

public final class ExceptionHolderHolder
implements Streamable {
    public ExceptionHolder value;

    public ExceptionHolderHolder() {
    }

    public ExceptionHolderHolder(ExceptionHolder initial) {
        this.value = initial;
    }

    @Override
    public void _read(InputStream is) {
        this.value = ExceptionHolderHelper.read(is);
    }

    @Override
    public void _write(OutputStream os) {
        ExceptionHolderHelper.write(os, this.value);
    }

    @Override
    public TypeCode _type() {
        return this.value._type();
    }
}

