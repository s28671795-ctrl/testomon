/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ORBPackage;

import org.omg.CORBA.ORBPackage.ObjectIdListHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ObjectIdListHolder
implements Streamable {
    public String[] value;

    public ObjectIdListHolder() {
    }

    public ObjectIdListHolder(String[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectIdListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ObjectIdListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ObjectIdListHelper.write(out, this.value);
    }
}

