/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContexts;
import org.omg.CORBA.Principal;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class RequestMessage_1_0
extends Message_1_0
implements RequestMessage {
    private ORB orb = null;
    private ServiceContexts service_contexts = null;
    private int request_id = 0;
    private boolean response_expected = false;
    private byte[] object_key = null;
    private String operation = null;
    private Principal requesting_principal = null;
    private ObjectKey objectKey = null;

    RequestMessage_1_0(ORB orb) {
        this.orb = orb;
    }

    RequestMessage_1_0(ORB orb, ServiceContexts _service_contexts, int _request_id, boolean _response_expected, byte[] _object_key, String _operation, Principal _requesting_principal) {
        super(1195986768, false, (byte)0, 0);
        this.orb = orb;
        this.service_contexts = _service_contexts;
        this.request_id = _request_id;
        this.response_expected = _response_expected;
        this.object_key = _object_key;
        this.operation = _operation;
        this.requesting_principal = _requesting_principal;
    }

    @Override
    public ServiceContexts getServiceContexts() {
        return this.service_contexts;
    }

    @Override
    public int getRequestId() {
        return this.request_id;
    }

    @Override
    public boolean isResponseExpected() {
        return this.response_expected;
    }

    @Override
    public byte[] getReserved() {
        return null;
    }

    @Override
    public ObjectKey getObjectKey() {
        if (this.objectKey == null) {
            this.objectKey = MessageBase.extractObjectKey(this.object_key, this.orb);
        }
        return this.objectKey;
    }

    @Override
    public String getOperation() {
        return this.operation;
    }

    @Override
    public Principal getPrincipal() {
        return this.requesting_principal;
    }

    @Override
    public void setThreadPoolToUse(int poolToUse) {
    }

    @Override
    public void read(org.omg.CORBA.portable.InputStream istream) {
        super.read(istream);
        this.service_contexts = new ServiceContexts((InputStream)istream);
        this.request_id = istream.read_ulong();
        this.response_expected = istream.read_boolean();
        int _len0 = istream.read_long();
        this.object_key = new byte[_len0];
        istream.read_octet_array(this.object_key, 0, _len0);
        this.operation = istream.read_string();
        this.requesting_principal = istream.read_Principal();
    }

    @Override
    public void write(org.omg.CORBA.portable.OutputStream ostream) {
        super.write(ostream);
        if (this.service_contexts != null) {
            this.service_contexts.write((OutputStream)ostream, GIOPVersion.V1_0);
        } else {
            ServiceContexts.writeNullServiceContext((OutputStream)ostream);
        }
        ostream.write_ulong(this.request_id);
        ostream.write_boolean(this.response_expected);
        RequestMessage_1_0.nullCheck(this.object_key);
        ostream.write_long(this.object_key.length);
        ostream.write_octet_array(this.object_key, 0, this.object_key.length);
        ostream.write_string(this.operation);
        if (this.requesting_principal != null) {
            ostream.write_Principal(this.requesting_principal);
        } else {
            ostream.write_long(0);
        }
    }

    @Override
    public void callback(MessageHandler handler) throws IOException {
        handler.handleInput(this);
    }
}

