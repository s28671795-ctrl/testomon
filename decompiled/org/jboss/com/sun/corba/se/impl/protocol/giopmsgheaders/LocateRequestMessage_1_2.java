/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.TargetAddress;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.TargetAddressHelper;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class LocateRequestMessage_1_2
extends Message_1_2
implements LocateRequestMessage {
    private ORB orb = null;
    private ObjectKey objectKey = null;
    private TargetAddress target = null;

    LocateRequestMessage_1_2(ORB orb) {
        this.orb = orb;
    }

    LocateRequestMessage_1_2(ORB orb, int _request_id, TargetAddress _target) {
        super(1195986768, GIOPVersion.V1_2, (byte)0, (byte)3, 0);
        this.orb = orb;
        this.request_id = _request_id;
        this.target = _target;
    }

    @Override
    public int getRequestId() {
        return this.request_id;
    }

    @Override
    public ObjectKey getObjectKey() {
        if (this.objectKey == null) {
            this.objectKey = MessageBase.extractObjectKey(this.target, this.orb);
        }
        return this.objectKey;
    }

    @Override
    public void read(InputStream istream) {
        super.read(istream);
        this.request_id = istream.read_ulong();
        this.target = TargetAddressHelper.read(istream);
        this.getObjectKey();
    }

    @Override
    public void write(OutputStream ostream) {
        super.write(ostream);
        ostream.write_ulong(this.request_id);
        LocateRequestMessage_1_2.nullCheck(this.target);
        TargetAddressHelper.write(ostream, this.target);
    }

    @Override
    public void callback(MessageHandler handler) throws IOException {
        handler.handleInput(this);
    }
}

