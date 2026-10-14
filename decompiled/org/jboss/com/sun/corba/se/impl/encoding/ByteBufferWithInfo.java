/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.pept.transport.ByteBufferPool;
import org.omg.CORBA.ORB;

public class ByteBufferWithInfo {
    private org.jboss.com.sun.corba.se.spi.orb.ORB orb;
    private boolean debug;
    private int index;
    public ByteBuffer byteBuffer;
    public int buflen;
    public int needed;
    public boolean fragmented;

    public ByteBufferWithInfo(ORB orb, ByteBuffer byteBuffer, int index) {
        this.orb = (org.jboss.com.sun.corba.se.spi.orb.ORB)orb;
        this.debug = this.orb.transportDebugFlag;
        this.byteBuffer = byteBuffer;
        if (byteBuffer != null) {
            this.buflen = byteBuffer.limit();
        }
        this.position(index);
        this.needed = 0;
        this.fragmented = false;
    }

    public ByteBufferWithInfo(ORB orb, ByteBuffer byteBuffer) {
        this(orb, byteBuffer, 0);
    }

    public ByteBufferWithInfo(ORB orb, BufferManagerWrite bufferManager) {
        this(orb, bufferManager, true);
    }

    public ByteBufferWithInfo(ORB orb, BufferManagerWrite bufferManager, boolean usePooledByteBuffers) {
        this.orb = (org.jboss.com.sun.corba.se.spi.orb.ORB)orb;
        this.debug = this.orb.transportDebugFlag;
        int bufferSize = bufferManager.getBufferSize();
        if (usePooledByteBuffers) {
            ByteBufferPool byteBufferPool = this.orb.getByteBufferPool();
            this.byteBuffer = byteBufferPool.getByteBuffer(bufferSize);
            if (this.debug) {
                int bbAddress = System.identityHashCode(this.byteBuffer);
                StringBuffer sb = new StringBuffer(80);
                sb.append("constructor (ORB, BufferManagerWrite) - got ").append("ByteBuffer id (").append(bbAddress).append(") from ByteBufferPool.");
                String msgStr = sb.toString();
                this.dprint(msgStr);
            }
        } else {
            this.byteBuffer = ByteBuffer.allocate(bufferSize);
        }
        this.position(0);
        this.buflen = bufferSize;
        this.byteBuffer.limit(this.buflen);
        this.needed = 0;
        this.fragmented = false;
    }

    public ByteBufferWithInfo(ByteBufferWithInfo bbwi) {
        this.orb = bbwi.orb;
        this.debug = bbwi.debug;
        this.byteBuffer = bbwi.byteBuffer;
        this.buflen = bbwi.buflen;
        this.byteBuffer.limit(this.buflen);
        this.position(bbwi.position());
        this.needed = bbwi.needed;
        this.fragmented = bbwi.fragmented;
    }

    public int getSize() {
        return this.position();
    }

    public int getLength() {
        return this.buflen;
    }

    public int position() {
        return this.index;
    }

    public void position(int newPosition) {
        this.byteBuffer.position(newPosition);
        this.index = newPosition;
    }

    public void setLength(int theLength) {
        this.buflen = theLength;
        this.byteBuffer.limit(this.buflen);
    }

    public void growBuffer(org.jboss.com.sun.corba.se.spi.orb.ORB orb) {
        StringBuffer sb;
        int newLength;
        for (newLength = this.byteBuffer.limit() * 2; this.position() + this.needed >= newLength; newLength *= 2) {
        }
        ByteBufferPool byteBufferPool = orb.getByteBufferPool();
        ByteBuffer newBB = byteBufferPool.getByteBuffer(newLength);
        if (this.debug) {
            int newbbAddress = System.identityHashCode(newBB);
            sb = new StringBuffer(80);
            sb.append("growBuffer() - got ByteBuffer id (");
            sb.append(newbbAddress).append(") from ByteBufferPool.");
            String msgStr = sb.toString();
            this.dprint(msgStr);
        }
        this.byteBuffer.position(0);
        newBB.put(this.byteBuffer);
        if (this.debug) {
            int bbAddress = System.identityHashCode(this.byteBuffer);
            sb = new StringBuffer(80);
            sb.append("growBuffer() - releasing ByteBuffer id (");
            sb.append(bbAddress).append(") to ByteBufferPool.");
            String msgStr2 = sb.toString();
            this.dprint(msgStr2);
        }
        byteBufferPool.releaseByteBuffer(this.byteBuffer);
        this.byteBuffer = newBB;
        this.buflen = newLength;
        this.byteBuffer.limit(this.buflen);
    }

    public String toString() {
        StringBuffer str = new StringBuffer("ByteBufferWithInfo:");
        str.append(" buflen = " + this.buflen);
        str.append(" byteBuffer.limit = " + this.byteBuffer.limit());
        str.append(" index = " + this.index);
        str.append(" position = " + this.position());
        str.append(" needed = " + this.needed);
        str.append(" byteBuffer = " + (this.byteBuffer == null ? "null" : "not null"));
        str.append(" fragmented = " + this.fragmented);
        return str.toString();
    }

    protected void dprint(String msg) {
        ORBUtility.dprint("ByteBufferWithInfo", msg);
    }
}

