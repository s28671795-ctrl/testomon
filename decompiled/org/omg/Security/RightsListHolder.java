/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.Right;
import org.omg.Security.RightsListHelper;

public final class RightsListHolder
implements Streamable {
    public Right[] value;

    public RightsListHolder() {
    }

    public RightsListHolder(Right[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RightsListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RightsListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        RightsListHelper.write(out, this.value);
    }
}

