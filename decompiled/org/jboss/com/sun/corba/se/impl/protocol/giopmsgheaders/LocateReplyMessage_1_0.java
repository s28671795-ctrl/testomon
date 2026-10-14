/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_0;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class LocateReplyMessage_1_0
extends Message_1_0
implements LocateReplyMessage {
    private int request_id = 0;
    private int locate_status = 0;
    private IOR ior = null;

    LocateReplyMessage_1_0(ORB orb) {
    }

    LocateReplyMessage_1_0(ORB orb, int _request_id, int _locate_status, IOR _ior) {
        super(1195986768, false, (byte)4, 0);
        this.request_id = _request_id;
        this.locate_status = _locate_status;
        this.ior = _ior;
    }

    @Override
    public int getRequestId() {
        return this.request_id;
    }

    @Override
    public int getReplyStatus() {
        return this.locate_status;
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
        this.locate_status = istream.read_long();
        LocateReplyMessage_1_0.isValidReplyStatus(this.locate_status);
        if (this.locate_status == 2) {
            CDRInputStream cdr = (CDRInputStream)istream;
            this.ior = IORFactories.makeIOR(cdr);
        }
    }

    @Override
    public void write(OutputStream ostream) {
        super.write(ostream);
        ostream.write_ulong(this.request_id);
        ostream.write_long(this.locate_status);
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

