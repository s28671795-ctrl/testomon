/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import org.omg.CONV_FRAME.CodeSetComponentInfo;
import org.omg.CONV_FRAME.CodeSetComponentInfoHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CodeSetComponentInfoHolder
implements Streamable {
    public CodeSetComponentInfo value;

    public CodeSetComponentInfoHolder() {
    }

    public CodeSetComponentInfoHolder(CodeSetComponentInfo initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CodeSetComponentInfoHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CodeSetComponentInfoHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CodeSetComponentInfoHelper.write(_out, this.value);
    }
}

