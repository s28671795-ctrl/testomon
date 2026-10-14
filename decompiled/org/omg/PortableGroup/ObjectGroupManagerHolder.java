/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.ObjectGroupManager;
import org.omg.PortableGroup.ObjectGroupManagerHelper;

public final class ObjectGroupManagerHolder
implements Streamable {
    public ObjectGroupManager value;

    public ObjectGroupManagerHolder() {
    }

    public ObjectGroupManagerHolder(ObjectGroupManager initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectGroupManagerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ObjectGroupManagerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ObjectGroupManagerHelper.write(_out, this.value);
    }
}

