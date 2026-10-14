/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CompletionStatusHolder
implements Streamable {
    public CompletionStatus value;

    public CompletionStatusHolder() {
    }

    public CompletionStatusHolder(CompletionStatus initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CompletionStatusHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CompletionStatusHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        CompletionStatusHelper.write(out, this.value);
    }
}

