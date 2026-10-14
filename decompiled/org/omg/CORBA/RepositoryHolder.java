/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Repository;
import org.omg.CORBA.RepositoryHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class RepositoryHolder
implements Streamable {
    public Repository value;

    public RepositoryHolder() {
    }

    public RepositoryHolder(Repository initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RepositoryHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RepositoryHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RepositoryHelper.write(_out, this.value);
    }
}

