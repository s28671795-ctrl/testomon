/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IIOP.ProfileBody_1_0;
import org.omg.IIOP.ProfileBody_1_0Helper;

public final class ProfileBody_1_0Holder
implements Streamable {
    public ProfileBody_1_0 value;

    public ProfileBody_1_0Holder() {
    }

    public ProfileBody_1_0Holder(ProfileBody_1_0 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ProfileBody_1_0Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ProfileBody_1_0Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ProfileBody_1_0Helper.write(_out, this.value);
    }
}

