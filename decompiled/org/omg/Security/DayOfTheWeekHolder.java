/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.DayOfTheWeek;
import org.omg.Security.DayOfTheWeekHelper;

public final class DayOfTheWeekHolder
implements Streamable {
    public DayOfTheWeek value;

    public DayOfTheWeekHolder() {
    }

    public DayOfTheWeekHolder(DayOfTheWeek initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DayOfTheWeekHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DayOfTheWeekHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        DayOfTheWeekHelper.write(out, this.value);
    }
}

