/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.NameDynAnyPair;
import org.omg.DynamicAny.NameDynAnyPairHelper;

public final class NameDynAnyPairHolder
implements Streamable {
    public NameDynAnyPair value;

    public NameDynAnyPairHolder() {
    }

    public NameDynAnyPairHolder(NameDynAnyPair initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NameDynAnyPairHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NameDynAnyPairHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NameDynAnyPairHelper.write(_out, this.value);
    }
}

