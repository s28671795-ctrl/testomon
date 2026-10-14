/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.InvalidCriteria;
import org.omg.PortableGroup.InvalidCriteriaHelper;

public final class InvalidCriteriaHolder
implements Streamable {
    public InvalidCriteria value;

    public InvalidCriteriaHolder() {
    }

    public InvalidCriteriaHolder(InvalidCriteria initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidCriteriaHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidCriteriaHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidCriteriaHelper.write(_out, this.value);
    }
}

