/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAManagerFactoryPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAManagerFactoryPackage.ManagerAlreadyExists;
import org.omg.PortableServer.POAManagerFactoryPackage.ManagerAlreadyExistsHelper;

public final class ManagerAlreadyExistsHolder
implements Streamable {
    public ManagerAlreadyExists value;

    public ManagerAlreadyExistsHolder() {
    }

    public ManagerAlreadyExistsHolder(ManagerAlreadyExists initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ManagerAlreadyExistsHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ManagerAlreadyExistsHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ManagerAlreadyExistsHelper.write(_out, this.value);
    }
}

