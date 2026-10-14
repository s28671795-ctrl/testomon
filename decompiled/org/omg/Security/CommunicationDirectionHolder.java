/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.CommunicationDirection;
import org.omg.Security.CommunicationDirectionHelper;

public final class CommunicationDirectionHolder
implements Streamable {
    public CommunicationDirection value;

    public CommunicationDirectionHolder() {
    }

    public CommunicationDirectionHolder(CommunicationDirection initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CommunicationDirectionHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CommunicationDirectionHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        CommunicationDirectionHelper.write(out, this.value);
    }
}

