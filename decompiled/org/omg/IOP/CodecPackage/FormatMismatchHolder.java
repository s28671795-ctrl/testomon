/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP.CodecPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.CodecPackage.FormatMismatch;
import org.omg.IOP.CodecPackage.FormatMismatchHelper;

public final class FormatMismatchHolder
implements Streamable {
    public FormatMismatch value;

    public FormatMismatchHolder() {
    }

    public FormatMismatchHolder(FormatMismatch initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return FormatMismatchHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = FormatMismatchHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        FormatMismatchHelper.write(_out, this.value);
    }
}

