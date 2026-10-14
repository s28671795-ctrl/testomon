/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import org.omg.CONV_FRAME.CodeSetComponent;
import org.omg.CONV_FRAME.CodeSetComponentHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CodeSetComponentHolder
implements Streamable {
    public CodeSetComponent value;

    public CodeSetComponentHolder() {
    }

    public CodeSetComponentHolder(CodeSetComponent initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CodeSetComponentHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CodeSetComponentHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CodeSetComponentHelper.write(_out, this.value);
    }
}

