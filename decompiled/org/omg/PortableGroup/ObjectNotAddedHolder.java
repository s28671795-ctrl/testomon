/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.ObjectNotAdded;
import org.omg.PortableGroup.ObjectNotAddedHelper;

public final class ObjectNotAddedHolder
implements Streamable {
    public ObjectNotAdded value;

    public ObjectNotAddedHolder() {
    }

    public ObjectNotAddedHolder(ObjectNotAdded initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectNotAddedHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ObjectNotAddedHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ObjectNotAddedHelper.write(_out, this.value);
    }
}

