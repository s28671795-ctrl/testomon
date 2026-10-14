/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.TagGroupTaggedComponent;
import org.omg.PortableGroup.TagGroupTaggedComponentHelper;

public final class TagGroupTaggedComponentHolder
implements Streamable {
    public TagGroupTaggedComponent value;

    public TagGroupTaggedComponentHolder() {
    }

    public TagGroupTaggedComponentHolder(TagGroupTaggedComponent initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TagGroupTaggedComponentHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = TagGroupTaggedComponentHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        TagGroupTaggedComponentHelper.write(_out, this.value);
    }
}

