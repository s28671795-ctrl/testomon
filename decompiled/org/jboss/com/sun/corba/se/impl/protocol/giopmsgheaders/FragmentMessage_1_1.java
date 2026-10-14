/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_1;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class FragmentMessage_1_1
extends Message_1_1
implements FragmentMessage {
    FragmentMessage_1_1() {
    }

    FragmentMessage_1_1(Message_1_1 msg11) {
        this.magic = msg11.magic;
        this.GIOP_version = msg11.GIOP_version;
        this.flags = msg11.flags;
        this.message_type = (byte)7;
        this.message_size = 0;
    }

    @Override
    public int getRequestId() {
        return -1;
    }

    @Override
    public int getHeaderLength() {
        return 12;
    }

    @Override
    public void read(InputStream istream) {
        super.read(istream);
    }

    @Override
    public void write(OutputStream ostream) {
        super.write(ostream);
    }

    @Override
    public void callback(MessageHandler handler) throws IOException {
        handler.handleInput(this);
    }
}

