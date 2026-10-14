/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnknownUserException;
import org.omg.CORBA.UnknownUserExceptionHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class UnknownUserExceptionHolder
implements Streamable {
    public UnknownUserException value;

    public UnknownUserExceptionHolder() {
    }

    public UnknownUserExceptionHolder(UnknownUserException initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UnknownUserExceptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = UnknownUserExceptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        UnknownUserExceptionHelper.write(_out, this.value);
    }
}

