/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.pept.encoding.OutputObject;
import org.jboss.com.sun.corba.se.pept.transport.Connection;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public class BufferManagerWriteGrow
extends BufferManagerWrite {
    BufferManagerWriteGrow(ORB orb) {
        super(orb);
    }

    @Override
    public boolean sentFragment() {
        return false;
    }

    @Override
    public int getBufferSize() {
        return this.orb.getORBData().getGIOPBufferSize();
    }

    @Override
    public void overflow(ByteBufferWithInfo bbwi) {
        bbwi.growBuffer(this.orb);
        bbwi.fragmented = false;
    }

    @Override
    public void sendMessage() {
        Connection conn = ((OutputObject)this.outputObject).getMessageMediator().getConnection();
        conn.writeLock();
        try {
            conn.sendWithoutLock((OutputObject)this.outputObject);
            this.sentFullMessage = true;
        }
        finally {
            conn.writeUnlock();
        }
    }

    @Override
    public void close() {
    }
}

