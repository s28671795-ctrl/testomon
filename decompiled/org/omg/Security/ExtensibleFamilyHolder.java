/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.ExtensibleFamily;
import org.omg.Security.ExtensibleFamilyHelper;

public final class ExtensibleFamilyHolder
implements Streamable {
    public ExtensibleFamily value;

    public ExtensibleFamilyHolder() {
    }

    public ExtensibleFamilyHolder(ExtensibleFamily initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtensibleFamilyHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ExtensibleFamilyHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtensibleFamilyHelper.write(_out, this.value);
    }
}

