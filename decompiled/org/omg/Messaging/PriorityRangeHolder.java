/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.PriorityRange;
import org.omg.Messaging.PriorityRangeHelper;

public final class PriorityRangeHolder
implements Streamable {
    public PriorityRange value;

    public PriorityRangeHolder() {
    }

    public PriorityRangeHolder(PriorityRange initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PriorityRangeHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = PriorityRangeHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        PriorityRangeHelper.write(_out, this.value);
    }
}

