/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.GroupIIOPProfileHelper;

public final class GroupIIOPProfileHolder
implements Streamable {
    public byte[] value;

    public GroupIIOPProfileHolder() {
    }

    public GroupIIOPProfileHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return GroupIIOPProfileHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = GroupIIOPProfileHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        GroupIIOPProfileHelper.write(out, this.value);
    }
}

