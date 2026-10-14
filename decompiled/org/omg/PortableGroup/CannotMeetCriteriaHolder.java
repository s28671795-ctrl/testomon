/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.CannotMeetCriteria;
import org.omg.PortableGroup.CannotMeetCriteriaHelper;

public final class CannotMeetCriteriaHolder
implements Streamable {
    public CannotMeetCriteria value;

    public CannotMeetCriteriaHolder() {
    }

    public CannotMeetCriteriaHolder(CannotMeetCriteria initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CannotMeetCriteriaHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CannotMeetCriteriaHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CannotMeetCriteriaHelper.write(_out, this.value);
    }
}

