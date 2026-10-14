/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeDescription;
import org.omg.CORBA.AttributeDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AttributeDescriptionHolder
implements Streamable {
    public AttributeDescription value;

    public AttributeDescriptionHolder() {
    }

    public AttributeDescriptionHolder(AttributeDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AttributeDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AttributeDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AttributeDescriptionHelper.write(_out, this.value);
    }
}

