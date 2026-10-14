/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.NameDynAnyPair;
import org.omg.DynamicAny.NameDynAnyPairSeqHelper;

public final class NameDynAnyPairSeqHolder
implements Streamable {
    public NameDynAnyPair[] value;

    public NameDynAnyPairSeqHolder() {
    }

    public NameDynAnyPairSeqHolder(NameDynAnyPair[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NameDynAnyPairSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = NameDynAnyPairSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        NameDynAnyPairSeqHelper.write(out, this.value);
    }
}

