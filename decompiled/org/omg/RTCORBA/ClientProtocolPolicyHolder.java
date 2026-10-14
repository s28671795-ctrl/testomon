/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.ClientProtocolPolicy;
import org.omg.RTCORBA.ClientProtocolPolicyHelper;

public final class ClientProtocolPolicyHolder
implements Streamable {
    public ClientProtocolPolicy value;

    public ClientProtocolPolicyHolder() {
    }

    public ClientProtocolPolicyHolder(ClientProtocolPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ClientProtocolPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ClientProtocolPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ClientProtocolPolicyHelper.write(_out, this.value);
    }
}

