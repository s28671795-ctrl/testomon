/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.IllegalTokenRequest;
import org.omg.ATLAS.IllegalTokenRequestHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class IllegalTokenRequestHolder
implements Streamable {
    public IllegalTokenRequest value;

    public IllegalTokenRequestHolder() {
    }

    public IllegalTokenRequestHolder(IllegalTokenRequest initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IllegalTokenRequestHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = IllegalTokenRequestHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        IllegalTokenRequestHelper.write(_out, this.value);
    }
}

