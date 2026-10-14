/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.ATLASLocator;
import org.omg.ATLAS.ATLASLocatorHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ATLASLocatorHolder
implements Streamable {
    public ATLASLocator value;

    public ATLASLocatorHolder() {
    }

    public ATLASLocatorHolder(ATLASLocator initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ATLASLocatorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ATLASLocatorHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ATLASLocatorHelper.write(out, this.value);
    }
}

