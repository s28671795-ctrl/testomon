/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtAttributeDescription;
import org.omg.CORBA.ExtAttributeDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtAttributeDescriptionHolder
implements Streamable {
    public ExtAttributeDescription value;

    public ExtAttributeDescriptionHolder() {
    }

    public ExtAttributeDescriptionHolder(ExtAttributeDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtAttributeDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ExtAttributeDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtAttributeDescriptionHelper.write(_out, this.value);
    }
}

