/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.GSS_NT_ExportedNameListHelper;

public final class GSS_NT_ExportedNameListHolder
implements Streamable {
    public byte[][] value;

    public GSS_NT_ExportedNameListHolder() {
    }

    public GSS_NT_ExportedNameListHolder(byte[][] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return GSS_NT_ExportedNameListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = GSS_NT_ExportedNameListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        GSS_NT_ExportedNameListHelper.write(out, this.value);
    }
}

