/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.CosNamingLocator;
import org.omg.ATLAS.CosNamingLocatorHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CosNamingLocatorHolder
implements Streamable {
    public CosNamingLocator value;

    public CosNamingLocatorHolder() {
    }

    public CosNamingLocatorHolder(CosNamingLocator initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CosNamingLocatorHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CosNamingLocatorHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CosNamingLocatorHelper.write(_out, this.value);
    }
}

