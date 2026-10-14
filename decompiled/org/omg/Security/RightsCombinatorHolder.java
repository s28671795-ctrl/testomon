/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.RightsCombinator;
import org.omg.Security.RightsCombinatorHelper;

public final class RightsCombinatorHolder
implements Streamable {
    public RightsCombinator value;

    public RightsCombinatorHolder() {
    }

    public RightsCombinatorHolder(RightsCombinator initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RightsCombinatorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RightsCombinatorHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        RightsCombinatorHelper.write(out, this.value);
    }
}

