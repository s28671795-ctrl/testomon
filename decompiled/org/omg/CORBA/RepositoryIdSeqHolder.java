/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.RepositoryIdSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class RepositoryIdSeqHolder
implements Streamable {
    public String[] value;

    public RepositoryIdSeqHolder() {
    }

    public RepositoryIdSeqHolder(String[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RepositoryIdSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RepositoryIdSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        RepositoryIdSeqHelper.write(out, this.value);
    }
}

