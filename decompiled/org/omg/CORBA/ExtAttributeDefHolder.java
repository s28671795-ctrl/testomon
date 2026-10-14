/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtAttributeDef;
import org.omg.CORBA.ExtAttributeDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtAttributeDefHolder
implements Streamable {
    public ExtAttributeDef value;

    public ExtAttributeDefHolder() {
    }

    public ExtAttributeDefHolder(ExtAttributeDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtAttributeDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtAttributeDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtAttributeDefHelper.write(_out, this.value);
    }
}

