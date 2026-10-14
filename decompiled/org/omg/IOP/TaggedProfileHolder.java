/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.TaggedProfile;
import org.omg.IOP.TaggedProfileHelper;

public final class TaggedProfileHolder
implements Streamable {
    public TaggedProfile value;

    public TaggedProfileHolder() {
    }

    public TaggedProfileHolder(TaggedProfile initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TaggedProfileHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = TaggedProfileHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        TaggedProfileHelper.write(_out, this.value);
    }
}

