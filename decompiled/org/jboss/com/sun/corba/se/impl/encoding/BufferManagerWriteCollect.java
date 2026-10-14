/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.encoding.BufferQueue;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputObject;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.pept.encoding.OutputObject;
import org.jboss.com.sun.corba.se.pept.transport.ByteBufferPool;
import org.jboss.com.sun.corba.se.pept.transport.Connection;
import org.omg.CORBA.ORB;

public class BufferManagerWriteCollect
extends BufferManagerWrite {
    private BufferQueue queue = new BufferQueue();
    private boolean sentFragment = false;
    private boolean debug = false;

    BufferManagerWriteCollect(org.jboss.com.sun.corba.se.spi.orb.ORB orb) {
        super(orb);
        if (orb != null) {
            this.debug = orb.transportDebugFlag;
        }
    }

    @Override
    public boolean sentFragment() {
        return this.sentFragment;
    }

    @Override
    public int getBufferSize() {
        return this.orb.getORBData().getGIOPFragmentSize();
    }

    @Override
    public void overflow(ByteBufferWithInfo bbwi) {
        MessageBase.setFlag(bbwi.byteBuffer, 2);
        this.queue.enqueue(bbwi);
        ByteBufferWithInfo newBbwi = new ByteBufferWithInfo((ORB)this.orb, this);
        newBbwi.fragmented = true;
        ((CDROutputObject)this.outputObject).setByteBufferWithInfo(newBbwi);
        FragmentMessage header = ((CDROutputObject)this.outputObject).getMessageHeader().createFragmentMessage();
        header.write((CDROutputObject)this.outputObject);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void sendMessage() {
        this.queue.enqueue(((CDROutputObject)this.outputObject).getByteBufferWithInfo());
        Iterator<ByteBufferWithInfo> bufs = this.iterator();
        Connection conn = ((OutputObject)this.outputObject).getMessageMediator().getConnection();
        conn.writeLock();
        try {
            ByteBufferPool byteBufferPool = this.orb.getByteBufferPool();
            while (bufs.hasNext()) {
                ByteBufferWithInfo bbwi = bufs.next();
                ((CDROutputObject)this.outputObject).setByteBufferWithInfo(bbwi);
                conn.sendWithoutLock((CDROutputObject)this.outputObject);
                this.sentFragment = true;
                if (this.debug) {
                    int bbAddress = System.identityHashCode(bbwi.byteBuffer);
                    StringBuffer sb = new StringBuffer(80);
                    sb.append("sendMessage() - releasing ByteBuffer id (");
                    sb.append(bbAddress).append(") to ByteBufferPool.");
                    String msg = sb.toString();
                    this.dprint(msg);
                }
                byteBufferPool.releaseByteBuffer(bbwi.byteBuffer);
                bbwi.byteBuffer = null;
                bbwi = null;
            }
            this.sentFullMessage = true;
        }
        finally {
            conn.writeUnlock();
        }
    }

    @Override
    public void close() {
        Iterator<ByteBufferWithInfo> bufs = this.iterator();
        ByteBufferPool byteBufferPool = this.orb.getByteBufferPool();
        while (bufs.hasNext()) {
            ByteBufferWithInfo bbwi = bufs.next();
            if (bbwi == null || bbwi.byteBuffer == null) continue;
            if (this.debug) {
                int bbAddress = System.identityHashCode(bbwi.byteBuffer);
                StringBuffer sb = new StringBuffer(80);
                sb.append("close() - releasing ByteBuffer id (");
                sb.append(bbAddress).append(") to ByteBufferPool.");
                String msg = sb.toString();
                this.dprint(msg);
            }
            byteBufferPool.releaseByteBuffer(bbwi.byteBuffer);
            bbwi.byteBuffer = null;
            Object var3_3 = null;
        }
    }

    private void dprint(String msg) {
        ORBUtility.dprint("BufferManagerWriteCollect", msg);
    }

    private Iterator<ByteBufferWithInfo> iterator() {
        return new BufferManagerWriteCollectIterator();
    }

    private class BufferManagerWriteCollectIterator
    implements Iterator<ByteBufferWithInfo> {
        private BufferManagerWriteCollectIterator() {
        }

        @Override
        public boolean hasNext() {
            return BufferManagerWriteCollect.this.queue.size() != 0;
        }

        @Override
        public ByteBufferWithInfo next() {
            return BufferManagerWriteCollect.this.queue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

