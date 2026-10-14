/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContexts;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class ReplyMessage_1_1
extends Message_1_1
implements ReplyMessage {
    private ORBUtilSystemException wrapper = null;
    private ServiceContexts service_contexts = null;
    private int request_id = 0;
    private int reply_status = 0;
    private IOR ior = null;
    private String exClassName = null;
    private int minorCode = 0;
    private CompletionStatus completionStatus = null;

    ReplyMessage_1_1(ORB orb) {
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.protocol");
    }

    ReplyMessage_1_1(ORB orb, ServiceContexts _service_contexts, int _request_id, int _reply_status, IOR _ior) {
        super(1195986768, GIOPVersion.V1_1, (byte)0, (byte)1, 0);
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.protocol");
        this.service_contexts = _service_contexts;
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
    public ServiceContexts getServiceContexts() {
        return this.service_contexts;
    }

    @Override
    public void setServiceContexts(ServiceContexts sc) {
        this.service_contexts = sc;
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
    public void setIOR(IOR ior) {
        this.ior = ior;
    }

    @Override
    public void read(org.omg.CORBA.portable.InputStream istream) {
        super.read(istream);
        this.service_contexts = new ServiceContexts((InputStream)istream);
        this.request_id = istream.read_ulong();
        this.reply_status = istream.read_long();
        ReplyMessage_1_1.isValidReplyStatus(this.reply_status);
        if (this.reply_status == 2) {
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
                    throw this.wrapper.badCompletionStatusInReply(CompletionStatus.COMPLETED_MAYBE, (Object)new Integer(status));
                }
            }
        } else if (this.reply_status != 1 && this.reply_status == 3) {
            CDRInputStream cdr = (CDRInputStream)istream;
            this.ior = IORFactories.makeIOR(cdr);
        }
    }

    @Override
    public void write(org.omg.CORBA.portable.OutputStream ostream) {
        super.write(ostream);
        if (this.service_contexts != null) {
            this.service_contexts.write((OutputStream)ostream, GIOPVersion.V1_1);
        } else {
            ServiceContexts.writeNullServiceContext((OutputStream)ostream);
        }
        ostream.write_ulong(this.request_id);
        ostream.write_long(this.reply_status);
    }

    public static void isValidReplyStatus(int replyStatus) {
        switch (replyStatus) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
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

