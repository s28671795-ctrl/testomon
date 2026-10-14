/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.MultipleComponentProfileHelper;
import org.omg.IOP.TaggedComponent;

public final class MultipleComponentProfileHolder
implements Streamable {
    public TaggedComponent[] value;

    public MultipleComponentProfileHolder() {
    }

    public MultipleComponentProfileHolder(TaggedComponent[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MultipleComponentProfileHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = MultipleComponentProfileHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        MultipleComponentProfileHelper.write(out, this.value);
    }
}

