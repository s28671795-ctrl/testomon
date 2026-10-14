/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.LocalObject;
import org.omg.RTCORBA.TCPProtocolProperties;

public abstract class _TCPProtocolPropertiesLocalBase
extends LocalObject
implements TCPProtocolProperties {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/RTCORBA/TCPProtocolProperties:1.0", "IDL:omg.org/RTCORBA/ProtocolProperties:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

