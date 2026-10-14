/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.TaggedProfile;
import org.omg.IOP.TaggedProfileSeqHelper;

public final class TaggedProfileSeqHolder
implements Streamable {
    public TaggedProfile[] value;

    public TaggedProfileSeqHolder() {
    }

    public TaggedProfileSeqHolder(TaggedProfile[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TaggedProfileSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = TaggedProfileSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        TaggedProfileSeqHelper.write(out, this.value);
    }
}

