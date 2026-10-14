/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.ServerProtocolPolicy;
import org.omg.RTCORBA.ServerProtocolPolicyHelper;

public final class ServerProtocolPolicyHolder
implements Streamable {
    public ServerProtocolPolicy value;

    public ServerProtocolPolicyHolder() {
    }

    public ServerProtocolPolicyHolder(ServerProtocolPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServerProtocolPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServerProtocolPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServerProtocolPolicyHelper.write(_out, this.value);
    }
}

