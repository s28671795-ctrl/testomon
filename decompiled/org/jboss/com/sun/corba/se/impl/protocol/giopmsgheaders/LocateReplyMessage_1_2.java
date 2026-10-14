/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.AddressingDispositionHelper;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_2;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class LocateReplyMessage_1_2
extends Message_1_2
implements LocateReplyMessage {
    private ORBUtilSystemException wrapper = null;
    private int reply_status = 0;
    private IOR ior = null;
    private String exClassName = null;
    private int minorCode = 0;
    private CompletionStatus completionStatus = null;
    private short addrDisposition = 0;

    LocateReplyMessage_1_2(ORB orb) {
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.protocol");
    }

    LocateReplyMessage_1_2(ORB orb, int _request_id, int _reply_status, IOR _ior) {
        super(1195986768, GIOPVersion.V1_2, (byte)0, (byte)4, 0);
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.protocol");
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
        return this.addrDisposition;
    }

    @Override
    public SystemException getSystemException(String message) {
        return MessageBase.getSystemException(this.exClassName, this.minorCode, this.completionStatus, message, this.wrapper);
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
        LocateReplyMessage_1_2.isValidReplyStatus(this.reply_status);
        if (this.reply_status == 4) {
            String reposId = istream.read_string();
            this.exClassName = ORBUtility.classNameOf(reposId);
            this.minorCode = istream.read_long();
            int status = istream.read_long();
            switch (status) {
                case 0: {
                    this.completionStatus = CompletionStatus.COMPLETED_YES;
                    break;
                }
                case 1: {
                    this.completionStatus = CompletionStatus.COMPLETED_NO;
                    break;
                }
                case 2: {
                    this.completionStatus = CompletionStatus.COMPLETED_MAYBE;
                    break;
                }
                default: {
                    throw this.wrapper.badCompletionStatusInLocateReply(CompletionStatus.COMPLETED_MAYBE, (Object)new Integer(status));
                }
            }
        } else if (this.reply_status == 2 || this.reply_status == 3) {
            CDRInputStream cdr = (CDRInputStream)istream;
            this.ior = IORFactories.makeIOR(cdr);
        } else if (this.reply_status == 5) {
            this.addrDisposition = AddressingDispositionHelper.read(istream);
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
            case 2: 
            case 3: 
            case 4: 
            case 5: {
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

