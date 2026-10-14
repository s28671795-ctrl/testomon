/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.AttributeType;
import org.omg.Security.AttributeTypeHelper;

public final class AttributeTypeHolder
implements Streamable {
    public AttributeType value;

    public AttributeTypeHolder() {
    }

    public AttributeTypeHolder(AttributeType initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AttributeTypeHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AttributeTypeHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AttributeTypeHelper.write(_out, this.value);
    }
}

