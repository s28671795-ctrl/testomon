/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAManagerPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAManagerPackage.State;
import org.omg.PortableServer.POAManagerPackage.StateHelper;

public final class StateHolder
implements Streamable {
    public State value;

    public StateHolder() {
    }

    public StateHolder(State initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return StateHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = StateHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        StateHelper.write(out, this.value);
    }
}

