/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_1;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class LocateReplyMessage_1_1
extends Message_1_1
implements LocateReplyMessage {
    private int request_id = 0;
    private int reply_status = 0;
    private IOR ior = null;

    LocateReplyMessage_1_1(ORB orb) {
    }

    LocateReplyMessage_1_1(ORB orb, int _request_id, int _reply_status, IOR _ior) {
        super(1195986768, GIOPVersion.V1_1, (byte)0, (byte)4, 0);
        this.request_id = _request_id;
        this.reply_status = _reply_status;
        this.ior = _ior;
    }

    @Override
    public int getRequestId() {
        return this.request_id;
    }

    @Override
    public int getReplyStatus() {
        return this.reply_status;
    }

    @Override
    public short getAddrDisposition() {
        return 0;
    }

    @Override
    public SystemException getSystemException(String message) {
        return null;
    }

    @Override
    public IOR getIOR() {
        return this.ior;
    }

    @Override
    public void read(InputStream istream) {
        super.read(istream);
        this.request_id = istream.read_ulong();
        this.reply_status = istream.read_long();
        LocateReplyMessage_1_1.isValidReplyStatus(this.reply_status);
        if (this.reply_status == 2) {
            CDRInputStream cdr = (CDRInputStream)istream;
            this.ior = IORFactories.makeIOR(cdr);
        }
    }

    @Override
    public void write(OutputStream ostream) {
        super.write(ostream);
        ostream.write_ulong(this.request_id);
        ostream.write_long(this.reply_status);
    }

    public static void isValidReplyStatus(int replyStatus) {
        switch (replyStatus) {
            case 0: 
            case 1: 
            case 2: {
                break;
            }
            default: {
                ORBUtilSystemException localWrapper = ORBUtilSystemException.get("rpc.protocol");
                throw localWrapper.illegalReplyStatus(CompletionStatus.COMPLETED_MAYBE);
            }
        }
    }

    @Override
    public void callback(MessageHandler handler) throws IOException {
        handler.handleInput(this);
    }
}

