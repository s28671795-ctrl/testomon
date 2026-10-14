/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DomainManager;
import org.omg.CORBA.DomainManagersListHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DomainManagersListHolder
implements Streamable {
    public DomainManager[] value;

    public DomainManagersListHolder() {
    }

    public DomainManagersListHolder(DomainManager[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DomainManagersListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DomainManagersListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        DomainManagersListHelper.write(out, this.value);
    }
}

