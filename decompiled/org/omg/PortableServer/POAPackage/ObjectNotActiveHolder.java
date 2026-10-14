/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ObjectNotActiveHelper;

public final class ObjectNotActiveHolder
implements Streamable {
    public ObjectNotActive value;

    public ObjectNotActiveHolder() {
    }

    public ObjectNotActiveHolder(ObjectNotActive initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectNotActiveHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ObjectNotActiveHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ObjectNotActiveHelper.write(_out, this.value);
    }
}

