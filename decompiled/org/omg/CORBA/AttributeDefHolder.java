/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeDef;
import org.omg.CORBA.AttributeDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AttributeDefHolder
implements Streamable {
    public AttributeDef value;

    public AttributeDefHolder() {
    }

    public AttributeDefHolder(AttributeDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AttributeDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AttributeDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AttributeDefHelper.write(_out, this.value);
    }
}

