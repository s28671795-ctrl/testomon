/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttrDescriptionSeqHelper;
import org.omg.CORBA.AttributeDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AttrDescriptionSeqHolder
implements Streamable {
    public AttributeDescription[] value;

    public AttrDescriptionSeqHolder() {
    }

    public AttrDescriptionSeqHolder(AttributeDescription[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AttrDescriptionSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AttrDescriptionSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AttrDescriptionSeqHelper.write(out, this.value);
    }
}

