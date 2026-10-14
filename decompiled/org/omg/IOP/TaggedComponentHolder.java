/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.TaggedComponent;
import org.omg.IOP.TaggedComponentHelper;

public final class TaggedComponentHolder
implements Streamable {
    public TaggedComponent value;

    public TaggedComponentHolder() {
    }

    public TaggedComponentHolder(TaggedComponent initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TaggedComponentHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = TaggedComponentHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        TaggedComponentHelper.write(_out, this.value);
    }
}

