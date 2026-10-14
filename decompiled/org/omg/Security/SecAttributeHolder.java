/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.SecAttribute;
import org.omg.Security.SecAttributeHelper;

public final class SecAttributeHolder
implements Streamable {
    public SecAttribute value;

    public SecAttributeHolder() {
    }

    public SecAttributeHolder(SecAttribute initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SecAttributeHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = SecAttributeHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        SecAttributeHelper.write(_out, this.value);
    }
}

