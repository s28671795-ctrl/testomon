/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.ETF.Factories;
import org.omg.ETF.FactoriesHelper;

public final class FactoriesHolder
implements Streamable {
    public Factories value;

    public FactoriesHolder() {
    }

    public FactoriesHolder(Factories initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return FactoriesHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = FactoriesHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        FactoriesHelper.write(_out, this.value);
    }
}

