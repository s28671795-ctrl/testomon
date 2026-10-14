/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AttributeModeHolder
implements Streamable {
    public AttributeMode value;

    public AttributeModeHolder() {
    }

    public AttributeModeHolder(AttributeMode initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AttributeModeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AttributeModeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AttributeModeHelper.write(out, this.value);
    }
}

