/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.ObjectGroupNotFound;
import org.omg.PortableGroup.ObjectGroupNotFoundHelper;

public final class ObjectGroupNotFoundHolder
implements Streamable {
    public ObjectGroupNotFound value;

    public ObjectGroupNotFoundHolder() {
    }

    public ObjectGroupNotFoundHolder(ObjectGroupNotFound initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectGroupNotFoundHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ObjectGroupNotFoundHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ObjectGroupNotFoundHelper.write(_out, this.value);
    }
}

