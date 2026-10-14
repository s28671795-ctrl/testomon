/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IIOP.ProfileBody_1_1;
import org.omg.IIOP.ProfileBody_1_1Helper;

public final class ProfileBody_1_1Holder
implements Streamable {
    public ProfileBody_1_1 value;

    public ProfileBody_1_1Holder() {
    }

    public ProfileBody_1_1Holder(ProfileBody_1_1 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ProfileBody_1_1Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ProfileBody_1_1Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ProfileBody_1_1Helper.write(_out, this.value);
    }
}

