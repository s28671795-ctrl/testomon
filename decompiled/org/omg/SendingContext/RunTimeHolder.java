/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SendingContext.RunTime;
import org.omg.SendingContext.RunTimeHelper;

public final class RunTimeHolder
implements Streamable {
    public RunTime value;

    public RunTimeHolder() {
    }

    public RunTimeHolder(RunTime initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RunTimeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RunTimeHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RunTimeHelper.write(_out, this.value);
    }
}

