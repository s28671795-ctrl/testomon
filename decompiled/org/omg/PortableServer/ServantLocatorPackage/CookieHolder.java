/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.ServantLocatorPackage;

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public class CookieHolder
implements Streamable {
    public Object value;

    public CookieHolder() {
    }

    public CookieHolder(Object intial) {
        this.value = intial;
    }

    @Override
    public void _read(InputStream input) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public void _write(OutputStream output) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public TypeCode _type() {
        throw new NO_IMPLEMENT();
    }
}

