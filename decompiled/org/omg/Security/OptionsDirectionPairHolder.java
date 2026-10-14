/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.OptionsDirectionPair;
import org.omg.Security.OptionsDirectionPairHelper;

public final class OptionsDirectionPairHolder
implements Streamable {
    public OptionsDirectionPair value;

    public OptionsDirectionPairHolder() {
    }

    public OptionsDirectionPairHolder(OptionsDirectionPair initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OptionsDirectionPairHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = OptionsDirectionPairHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        OptionsDirectionPairHelper.write(_out, this.value);
    }
}

