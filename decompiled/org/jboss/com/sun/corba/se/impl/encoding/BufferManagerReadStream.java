/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.ListIterator;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerRead;
import org.jboss.com.sun.corba.se.impl.encoding.BufferQueue;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.MarkAndResetHandler;
import org.jboss.com.sun.corba.se.impl.encoding.RestorableInputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.protocol.RequestCanceledException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.pept.transport.ByteBufferPool;
import org.omg.CORBA.ORB;

public class BufferManagerReadStream
implements BufferManagerRead,
MarkAndResetHandler {
    private boolean receivedCancel = false;
    private int cancelReqId = 0;
    private boolean endOfStream = true;
    private BufferQueue fragmentQueue = new BufferQueue();
    private long FRAGMENT_TIMEOUT = 60000L;
    private org.jboss.com.sun.corba.se.spi.orb.ORB orb;
    private ORBUtilSystemException wrapper;
    private boolean debug = false;
    private boolean markEngaged = false;
    private LinkedList<ByteBufferWithInfo> fragmentStack = null;
    private RestorableInputStream inputStream = null;
    private Object streamMemento = null;

    BufferManagerReadStream(org.jboss.com.sun.corba.se.spi.orb.ORB orb) {
        this.orb = orb;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
        this.debug = orb.transportDebugFlag;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void cancelProcessing(int requestId) {
        BufferQueue bufferQueue = this.fragmentQueue;
        synchronized (bufferQueue) {
            this.receivedCancel = true;
            this.cancelReqId = requestId;
            this.fragmentQueue.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processFragment(ByteBuffer byteBuffer, FragmentMessage msg) {
        ByteBufferWithInfo bbwi = new ByteBufferWithInfo((ORB)this.orb, byteBuffer, msg.getHeaderLength());
        BufferQueue bufferQueue = this.fragmentQueue;
        synchronized (bufferQueue) {
            if (this.debug) {
                int bbAddress = System.identityHashCode(byteBuffer);
                StringBuffer sb = new StringBuffer(80);
                sb.append("processFragment() - queueing ByteBuffer id (");
                sb.append(bbAddress).append(") to fragment queue.");
                String strMsg = sb.toString();
                this.dprint(strMsg);
            }
            this.fragmentQueue.enqueue(bbwi);
            this.endOfStream = !msg.moreFragmentsToFollow();
            this.fragmentQueue.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ByteBufferWithInfo underflow(ByteBufferWithInfo bbwi) {
        ByteBufferWithInfo result = null;
        BufferQueue bufferQueue = this.fragmentQueue;
        synchronized (bufferQueue) {
            if (this.receivedCancel) {
                throw new RequestCanceledException(this.cancelReqId);
            }
            while (this.fragmentQueue.size() == 0) {
                if (this.endOfStream) {
                    throw this.wrapper.endOfStream();
                }
                boolean interrupted = false;
                try {
                    this.fragmentQueue.wait(this.FRAGMENT_TIMEOUT);
                }
                catch (InterruptedException e) {
                    interrupted = true;
                }
                if (!interrupted && this.fragmentQueue.size() == 0) {
                    throw this.wrapper.bufferReadManagerTimeout();
                }
                if (!this.receivedCancel) continue;
                throw new RequestCanceledException(this.cancelReqId);
            }
            result = this.fragmentQueue.dequeue();
            result.fragmented = true;
            if (this.debug) {
                int bbAddr = System.identityHashCode(result.byteBuffer);
                StringBuffer sb1 = new StringBuffer(80);
                sb1.append("underflow() - dequeued ByteBuffer id (");
                sb1.append(bbAddr).append(") from fragment queue.");
                String msg1 = sb1.toString();
                this.dprint(msg1);
            }
            if (!this.markEngaged && bbwi != null && bbwi.byteBuffer != null) {
                ByteBufferPool byteBufferPool = this.getByteBufferPool();
                if (this.debug) {
                    int bbAddress = System.identityHashCode(bbwi.byteBuffer);
                    StringBuffer sb = new StringBuffer(80);
                    sb.append("underflow() - releasing ByteBuffer id (");
                    sb.append(bbAddress).append(") to ByteBufferPool.");
                    String msg = sb.toString();
                    this.dprint(msg);
                }
                byteBufferPool.releaseByteBuffer(bbwi.byteBuffer);
                bbwi.byteBuffer = null;
                bbwi = null;
            }
        }
        return result;
    }

    @Override
    public void init(Message msg) {
        if (msg != null) {
            this.endOfStream = !msg.moreFragmentsToFollow();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close(ByteBufferWithInfo bbwi) {
        String msg;
        StringBuffer sb;
        int bbAddress;
        int inputBbAddress = 0;
        if (this.fragmentQueue != null) {
            BufferQueue bufferQueue = this.fragmentQueue;
            synchronized (bufferQueue) {
                if (bbwi != null) {
                    inputBbAddress = System.identityHashCode(bbwi.byteBuffer);
                }
                ByteBufferWithInfo abbwi = null;
                ByteBufferPool byteBufferPool = this.getByteBufferPool();
                while (this.fragmentQueue.size() != 0) {
                    abbwi = this.fragmentQueue.dequeue();
                    if (abbwi == null || abbwi.byteBuffer == null) continue;
                    bbAddress = System.identityHashCode(abbwi.byteBuffer);
                    if (inputBbAddress != bbAddress && this.debug) {
                        sb = new StringBuffer(80);
                        sb.append("close() - fragmentQueue is ").append("releasing ByteBuffer id (").append(bbAddress).append(") to ").append("ByteBufferPool.");
                        msg = sb.toString();
                        this.dprint(msg);
                    }
                    byteBufferPool.releaseByteBuffer(abbwi.byteBuffer);
                }
            }
            this.fragmentQueue = null;
        }
        if (this.fragmentStack != null && this.fragmentStack.size() != 0) {
            if (bbwi != null) {
                inputBbAddress = System.identityHashCode(bbwi.byteBuffer);
            }
            ByteBufferWithInfo abbwi = null;
            ByteBufferPool byteBufferPool = this.getByteBufferPool();
            ListIterator itr = this.fragmentStack.listIterator();
            while (itr.hasNext()) {
                abbwi = (ByteBufferWithInfo)itr.next();
                if (abbwi == null || abbwi.byteBuffer == null || inputBbAddress == (bbAddress = System.identityHashCode(abbwi.byteBuffer))) continue;
                if (this.debug) {
                    sb = new StringBuffer(80);
                    sb.append("close() - fragmentStack - releasing ").append("ByteBuffer id (" + bbAddress + ") to ").append("ByteBufferPool.");
                    msg = sb.toString();
                    this.dprint(msg);
                }
                byteBufferPool.releaseByteBuffer(abbwi.byteBuffer);
            }
            this.fragmentStack = null;
        }
    }

    protected ByteBufferPool getByteBufferPool() {
        return this.orb.getByteBufferPool();
    }

    private void dprint(String msg) {
        ORBUtility.dprint("BufferManagerReadStream", msg);
    }

    @Override
    public void mark(RestorableInputStream inputStream2) {
        this.inputStream = inputStream2;
        this.markEngaged = true;
        this.streamMemento = inputStream2.createStreamMemento();
        if (this.fragmentStack != null) {
            this.fragmentStack.clear();
        }
    }

    @Override
    public void fragmentationOccured(ByteBufferWithInfo newFragment) {
        if (!this.markEngaged) {
            return;
        }
        if (this.fragmentStack == null) {
            this.fragmentStack = new LinkedList();
        }
        this.fragmentStack.addFirst(new ByteBufferWithInfo(newFragment));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void reset() {
        if (!this.markEngaged) {
            return;
        }
        this.markEngaged = false;
        if (this.fragmentStack != null && this.fragmentStack.size() != 0) {
            ListIterator iter = this.fragmentStack.listIterator();
            BufferQueue bufferQueue = this.fragmentQueue;
            synchronized (bufferQueue) {
                while (iter.hasNext()) {
                    this.fragmentQueue.push((ByteBufferWithInfo)iter.next());
                }
            }
            this.fragmentStack.clear();
        }
        this.inputStream.restoreInternalState(this.streamMemento);
    }

    @Override
    public MarkAndResetHandler getMarkAndResetHandler() {
        return this;
    }
}

