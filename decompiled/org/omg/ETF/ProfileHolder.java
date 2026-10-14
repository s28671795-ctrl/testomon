/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.ETF.Profile;
import org.omg.ETF.ProfileHelper;

public final class ProfileHolder
implements Streamable {
    public Profile value;

    public ProfileHolder() {
    }

    public ProfileHolder(Profile initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ProfileHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ProfileHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ProfileHelper.write(_out, this.value);
    }
}

