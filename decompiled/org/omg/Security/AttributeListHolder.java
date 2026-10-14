/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.AttributeListHelper;
import org.omg.Security.SecAttribute;

public final class AttributeListHolder
implements Streamable {
    public SecAttribute[] value;

    public AttributeListHolder() {
    }

    public AttributeListHolder(SecAttribute[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AttributeListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AttributeListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AttributeListHelper.write(out, this.value);
    }
}

