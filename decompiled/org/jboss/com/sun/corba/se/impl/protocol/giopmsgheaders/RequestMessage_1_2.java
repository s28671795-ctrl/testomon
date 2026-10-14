/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.TargetAddress;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.TargetAddressHelper;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContexts;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Principal;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class RequestMessage_1_2
extends Message_1_2
implements RequestMessage {
    private ORB orb = null;
    private ORBUtilSystemException wrapper = null;
    private byte response_flags = 0;
    private byte[] reserved = null;
    private TargetAddress target = null;
    private String operation = null;
    private ServiceContexts service_contexts = null;
    private ObjectKey objectKey = null;

    RequestMessage_1_2(ORB orb) {
        this.orb = orb;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.protocol");
    }

    RequestMessage_1_2(ORB orb, int _request_id, byte _response_flags, byte[] _reserved, TargetAddress _target, String _operation, ServiceContexts _service_contexts) {
        super(1195986768, GIOPVersion.V1_2, (byte)0, (byte)0, 0);
        this.orb = orb;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.protocol");
        this.request_id = _request_id;
        this.response_flags = _response_flags;
        this.reserved = _reserved;
        this.target = _target;
        this.operation = _operation;
        this.service_contexts = _service_contexts;
    }

    @Override
    public int getRequestId() {
        return this.request_id;
    }

    @Override
    public boolean isResponseExpected() {
        return (this.response_flags & 1) == 1;
    }

    @Override
    public byte[] getReserved() {
        return this.reserved;
    }

    @Override
    public ObjectKey getObjectKey() {
        if (this.objectKey == null) {
            this.objectKey = MessageBase.extractObjectKey(this.target, this.orb);
        }
        return this.objectKey;
    }

    @Override
    public String getOperation() {
        return this.operation;
    }

    @Override
    public Principal getPrincipal() {
        return null;
    }

    @Override
    public ServiceContexts getServiceContexts() {
        return this.service_contexts;
    }

    @Override
    public void read(org.omg.CORBA.portable.InputStream istream) {
        super.read(istream);
        this.request_id = istream.read_ulong();
        this.response_flags = istream.read_octet();
        this.reserved = new byte[3];
        for (int _o0 = 0; _o0 < 3; ++_o0) {
            this.reserved[_o0] = istream.read_octet();
        }
        this.target = TargetAddressHelper.read(istream);
        this.getObjectKey();
        this.operation = istream.read_string();
        this.service_contexts = new ServiceContexts((InputStream)istream);
        ((CDRInputStream)istream).setHeaderPadding(true);
    }

    @Override
    public void write(org.omg.CORBA.portable.OutputStream ostream) {
        super.write(ostream);
        ostream.write_ulong(this.request_id);
        ostream.write_octet(this.response_flags);
        RequestMessage_1_2.nullCheck(this.reserved);
        if (this.reserved.length != 3) {
            throw this.wrapper.badReservedLength(CompletionStatus.COMPLETED_MAYBE);
        }
        for (int _i0 = 0; _i0 < 3; ++_i0) {
            ostream.write_octet(this.reserved[_i0]);
        }
        RequestMessage_1_2.nullCheck(this.target);
        TargetAddressHelper.write(ostream, this.target);
        ostream.write_string(this.operation);
        if (this.service_contexts != null) {
            this.service_contexts.write((OutputStream)ostream, GIOPVersion.V1_2);
        } else {
            ServiceContexts.writeNullServiceContext((OutputStream)ostream);
        }
        ((CDROutputStream)ostream).setHeaderPadding(true);
    }

    @Override
    public void callback(MessageHandler handler) throws IOException {
        handler.handleInput(this);
    }
}

