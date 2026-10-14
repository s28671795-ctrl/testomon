/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DomainManager;
import org.omg.CORBA.DomainManagerHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DomainManagerHolder
implements Streamable {
    public DomainManager value;

    public DomainManagerHolder() {
    }

    public DomainManagerHolder(DomainManager initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DomainManagerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DomainManagerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DomainManagerHelper.write(_out, this.value);
    }
}

