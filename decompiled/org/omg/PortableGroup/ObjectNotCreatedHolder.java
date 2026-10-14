/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.ObjectNotCreated;
import org.omg.PortableGroup.ObjectNotCreatedHelper;

public final class ObjectNotCreatedHolder
implements Streamable {
    public ObjectNotCreated value;

    public ObjectNotCreatedHolder() {
    }

    public ObjectNotCreatedHolder(ObjectNotCreated initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectNotCreatedHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ObjectNotCreatedHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ObjectNotCreatedHelper.write(_out, this.value);
    }
}

