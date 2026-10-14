/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class FragmentMessage_1_2
extends Message_1_2
implements FragmentMessage {
    FragmentMessage_1_2() {
    }

    FragmentMessage_1_2(int _request_id) {
        super(1195986768, GIOPVersion.V1_2, (byte)0, (byte)7, 0);
        this.message_type = (byte)7;
        this.request_id = _request_id;
    }

    FragmentMessage_1_2(Message_1_1 msg12) {
        this.magic = msg12.magic;
        this.GIOP_version = msg12.GIOP_version;
        this.flags = msg12.flags;
        this.message_type = (byte)7;
        this.message_size = 0;
        switch (msg12.message_type) {
            case 0: {
                this.request_id = ((RequestMessage)((Object)msg12)).getRequestId();
                break;
            }
            case 1: {
                this.request_id = ((ReplyMessage)((Object)msg12)).getRequestId();
                break;
            }
            case 3: {
                this.request_id = ((LocateRequestMessage)((Object)msg12)).getRequestId();
                break;
            }
            case 4: {
                this.request_id = ((LocateReplyMessage)((Object)msg12)).getRequestId();
                break;
            }
            case 7: {
                this.request_id = ((FragmentMessage)((Object)msg12)).getRequestId();
            }
        }
    }

    @Override
    public int getRequestId() {
        return this.request_id;
    }

    @Override
    public int getHeaderLength() {
        return 16;
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

