/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_0;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class LocateRequestMessage_1_0
extends Message_1_0
implements LocateRequestMessage {
    private ORB orb = null;
    private int request_id = 0;
    private byte[] object_key = null;
    private ObjectKey objectKey = null;

    LocateRequestMessage_1_0(ORB orb) {
        this.orb = orb;
    }

    LocateRequestMessage_1_0(ORB orb, int _request_id, byte[] _object_key) {
        super(1195986768, false, (byte)3, 0);
        this.orb = orb;
        this.request_id = _request_id;
        this.object_key = _object_key;
    }

    @Override
    public int getRequestId() {
        return this.request_id;
    }

    @Override
    public ObjectKey getObjectKey() {
        if (this.objectKey == null) {
            this.objectKey = MessageBase.extractObjectKey(this.object_key, this.orb);
        }
        return this.objectKey;
    }

    @Override
    public void read(InputStream istream) {
        super.read(istream);
        this.request_id = istream.read_ulong();
        int _len0 = istream.read_long();
        this.object_key = new byte[_len0];
        istream.read_octet_array(this.object_key, 0, _len0);
    }

    @Override
    public void write(OutputStream ostream) {
        super.write(ostream);
        ostream.write_ulong(this.request_id);
        LocateRequestMessage_1_0.nullCheck(this.object_key);
        ostream.write_long(this.object_key.length);
        ostream.write_octet_array(this.object_key, 0, this.object_key.length);
    }

    @Override
    public void callback(MessageHandler handler) throws IOException {
        handler.handleInput(this);
    }
}

