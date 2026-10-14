/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import org.omg.CONV_FRAME.CodeSetContext;
import org.omg.CONV_FRAME.CodeSetContextHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CodeSetContextHolder
implements Streamable {
    public CodeSetContext value;

    public CodeSetContextHolder() {
    }

    public CodeSetContextHolder(CodeSetContext initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CodeSetContextHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CodeSetContextHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CodeSetContextHelper.write(_out, this.value);
    }
}

