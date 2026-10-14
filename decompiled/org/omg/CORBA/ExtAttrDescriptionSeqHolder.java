/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtAttrDescriptionSeqHelper;
import org.omg.CORBA.ExtAttributeDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtAttrDescriptionSeqHolder
implements Streamable {
    public ExtAttributeDescription[] value;

    public ExtAttrDescriptionSeqHolder() {
    }

    public ExtAttrDescriptionSeqHolder(ExtAttributeDescription[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtAttrDescriptionSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtAttrDescriptionSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExtAttrDescriptionSeqHelper.write(out, this.value);
    }
}

