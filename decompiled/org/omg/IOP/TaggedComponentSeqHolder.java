/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.TaggedComponent;
import org.omg.IOP.TaggedComponentSeqHelper;

public final class TaggedComponentSeqHolder
implements Streamable {
    public TaggedComponent[] value;

    public TaggedComponentSeqHolder() {
    }

    public TaggedComponentSeqHolder(TaggedComponent[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TaggedComponentSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = TaggedComponentSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        TaggedComponentSeqHelper.write(out, this.value);
    }
}

