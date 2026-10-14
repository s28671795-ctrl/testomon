/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.AttributeType;
import org.omg.Security.AttributeTypeListHelper;

public final class AttributeTypeListHolder
implements Streamable {
    public AttributeType[] value;

    public AttributeTypeListHolder() {
    }

    public AttributeTypeListHolder(AttributeType[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AttributeTypeListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AttributeTypeListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AttributeTypeListHelper.write(out, this.value);
    }
}

