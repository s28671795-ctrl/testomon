/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActiveHelper;

public final class ObjectAlreadyActiveHolder
implements Streamable {
    public ObjectAlreadyActive value;

    public ObjectAlreadyActiveHolder() {
    }

    public ObjectAlreadyActiveHolder(ObjectAlreadyActive initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectAlreadyActiveHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ObjectAlreadyActiveHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ObjectAlreadyActiveHelper.write(_out, this.value);
    }
}

