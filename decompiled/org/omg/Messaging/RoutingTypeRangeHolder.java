/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.RoutingTypeRange;
import org.omg.Messaging.RoutingTypeRangeHelper;

public final class RoutingTypeRangeHolder
implements Streamable {
    public RoutingTypeRange value;

    public RoutingTypeRangeHolder() {
    }

    public RoutingTypeRangeHolder(RoutingTypeRange initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RoutingTypeRangeHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = RoutingTypeRangeHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        RoutingTypeRangeHelper.write(_out, this.value);
    }
}

