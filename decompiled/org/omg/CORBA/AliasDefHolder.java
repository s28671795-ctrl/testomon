/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AliasDef;
import org.omg.CORBA.AliasDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AliasDefHolder
implements Streamable {
    public AliasDef value;

    public AliasDefHolder() {
    }

    public AliasDefHolder(AliasDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AliasDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AliasDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AliasDefHelper.write(_out, this.value);
    }
}

