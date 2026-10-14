/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ContainerPackage;

import org.omg.CORBA.ContainerPackage.Description;
import org.omg.CORBA.ContainerPackage.DescriptionSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DescriptionSeqHolder
implements Streamable {
    public Description[] value;

    public DescriptionSeqHolder() {
    }

    public DescriptionSeqHolder(Description[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DescriptionSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DescriptionSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        DescriptionSeqHelper.write(out, this.value);
    }
}

