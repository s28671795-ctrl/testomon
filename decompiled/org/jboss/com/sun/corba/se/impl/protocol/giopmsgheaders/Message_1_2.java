/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_1;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.omg.CORBA.portable.OutputStream;

public class Message_1_2
extends Message_1_1 {
    protected int request_id = 0;

    Message_1_2() {
    }

    Message_1_2(int _magic, GIOPVersion _GIOP_version, byte _flags, byte _message_type, int _message_size) {
        super(_magic, _GIOP_version, _flags, _message_type, _message_size);
    }

    public void unmarshalRequestID(ByteBuffer byteBuffer) {
        int b4;
        int b3;
        int b2;
        int b1;
        if (!this.isLittleEndian()) {
            b1 = byteBuffer.get(12) << 24 & 0xFF000000;
            b2 = byteBuffer.get(13) << 16 & 0xFF0000;
            b3 = byteBuffer.get(14) << 8 & 0xFF00;
            b4 = byteBuffer.get(15) << 0 & 0xFF;
        } else {
            b1 = byteBuffer.get(15) << 24 & 0xFF000000;
            b2 = byteBuffer.get(14) << 16 & 0xFF0000;
            b3 = byteBuffer.get(13) << 8 & 0xFF00;
            b4 = byteBuffer.get(12) << 0 & 0xFF;
        }
        this.request_id = b1 | b2 | b3 | b4;
    }

    @Override
    public void write(OutputStream ostream) {
        if (this.encodingVersion == 0) {
            super.write(ostream);
            return;
        }
        GIOPVersion gv = this.GIOP_version;
        this.GIOP_version = GIOPVersion.getInstance((byte)13, this.encodingVersion);
        super.write(ostream);
        this.GIOP_version = gv;
    }
}

