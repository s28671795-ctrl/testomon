/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_0;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class CancelRequestMessage_1_0
extends Message_1_0
implements CancelRequestMessage {
    private int request_id = 0;

    CancelRequestMessage_1_0() {
    }

    CancelRequestMessage_1_0(int _request_id) {
        super(1195986768, false, (byte)2, 4);
        this.request_id = _request_id;
    }

    @Override
    public int getRequestId() {
        return this.request_id;
    }

    @Override
    public void read(InputStream istream) {
        super.read(istream);
        this.request_id = istream.read_ulong();
    }

    @Override
    public void write(OutputStream ostream) {
        super.write(ostream);
        ostream.write_ulong(this.request_id);
    }

    @Override
    public void callback(MessageHandler handler) throws IOException {
        handler.handleInput(this);
    }
}

