/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SendingContext.CodeBase;
import org.omg.SendingContext.CodeBaseHelper;

public final class CodeBaseHolder
implements Streamable {
    public CodeBase value;

    public CodeBaseHolder() {
    }

    public CodeBaseHolder(CodeBase initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CodeBaseHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CodeBaseHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        CodeBaseHelper.write(_out, this.value);
    }
}

