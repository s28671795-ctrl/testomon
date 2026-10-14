/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputObject;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.pept.encoding.OutputObject;
import org.jboss.com.sun.corba.se.pept.transport.Connection;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public class BufferManagerWriteStream
extends BufferManagerWrite {
    private int fragmentCount = 0;

    BufferManagerWriteStream(ORB orb) {
        super(orb);
    }

    @Override
    public boolean sentFragment() {
        return this.fragmentCount > 0;
    }

    @Override
    public int getBufferSize() {
        return this.orb.getORBData().getGIOPFragmentSize();
    }

    @Override
    public void overflow(ByteBufferWithInfo bbwi) {
        MessageBase.setFlag(bbwi.byteBuffer, 2);
        this.sendFragment(false);
        bbwi.position(0);
        bbwi.buflen = bbwi.byteBuffer.limit();
        bbwi.fragmented = true;
        FragmentMessage header = ((CDROutputObject)this.outputObject).getMessageHeader().createFragmentMessage();
        header.write((CDROutputObject)this.outputObject);
    }

    private void sendFragment(boolean isLastFragment) {
        Connection conn = ((OutputObject)this.outputObject).getMessageMediator().getConnection();
        conn.writeLock();
        try {
            conn.sendWithoutLock((OutputObject)this.outputObject);
            ++this.fragmentCount;
        }
        finally {
            conn.writeUnlock();
        }
    }

    @Override
    public void sendMessage() {
        this.sendFragment(true);
        this.sentFullMessage = true;
    }

    @Override
    public void close() {
    }
}

