/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.OptionsDirectionPair;
import org.omg.Security.OptionsDirectionPairListHelper;

public final class OptionsDirectionPairListHolder
implements Streamable {
    public OptionsDirectionPair[] value;

    public OptionsDirectionPairListHolder() {
    }

    public OptionsDirectionPairListHolder(OptionsDirectionPair[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OptionsDirectionPairListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = OptionsDirectionPairListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        OptionsDirectionPairListHelper.write(out, this.value);
    }
}

