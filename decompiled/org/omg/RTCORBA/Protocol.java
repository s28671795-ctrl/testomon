/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.RTCORBA.ProtocolProperties;

public final class Protocol
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int protocol_type;
    public ProtocolProperties orb_protocol_properties;
    public ProtocolProperties transport_protocol_properties;

    public Protocol() {
    }

    public Protocol(int protocol_type, ProtocolProperties orb_protocol_properties, ProtocolProperties transport_protocol_properties) {
        this.protocol_type = protocol_type;
        this.orb_protocol_properties = orb_protocol_properties;
        this.transport_protocol_properties = transport_protocol_properties;
    }
}

