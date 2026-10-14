/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.ObjectNotFound;
import org.omg.PortableGroup.ObjectNotFoundHelper;

public final class ObjectNotFoundHolder
implements Streamable {
    public ObjectNotFound value;

    public ObjectNotFoundHolder() {
    }

    public ObjectNotFoundHolder(ObjectNotFound initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectNotFoundHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ObjectNotFoundHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ObjectNotFoundHelper.write(_out, this.value);
    }
}

