/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.transport;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.jboss.com.sun.corba.se.pept.transport.ByteBufferPool;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public class ByteBufferPoolImpl
implements ByteBufferPool {
    private ORB itsOrb;
    private int itsByteBufferSize;
    private ArrayList<ByteBuffer> itsPool;
    private int itsObjectCounter = 0;
    private boolean debug;

    public ByteBufferPoolImpl(ORB theORB) {
        this.itsByteBufferSize = theORB.getORBData().getGIOPFragmentSize();
        this.itsPool = new ArrayList();
        this.itsOrb = theORB;
        this.debug = theORB.transportDebugFlag;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ByteBuffer getByteBuffer(int theAskSize) {
        ByteBuffer abb = null;
        if (theAskSize <= this.itsByteBufferSize && !this.itsOrb.getORBData().disableDirectByteBufferUse()) {
            int poolSize;
            ArrayList<ByteBuffer> arrayList = this.itsPool;
            synchronized (arrayList) {
                poolSize = this.itsPool.size();
                if (poolSize > 0) {
                    abb = this.itsPool.remove(poolSize - 1);
                    abb.clear();
                }
            }
            if (poolSize <= 0) {
                abb = ByteBuffer.allocateDirect(this.itsByteBufferSize);
            }
            ++this.itsObjectCounter;
        } else {
            abb = ByteBuffer.allocate(theAskSize);
        }
        return abb;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void releaseByteBuffer(ByteBuffer thebb) {
        if (thebb.isDirect()) {
            ArrayList<ByteBuffer> arrayList = this.itsPool;
            synchronized (arrayList) {
                boolean refInPool = false;
                int bbAddr = 0;
                if (this.debug) {
                    for (int i = 0; i < this.itsPool.size() && !refInPool; ++i) {
                        ByteBuffer tmpbb = this.itsPool.get(i);
                        if (thebb != tmpbb) continue;
                        refInPool = true;
                        bbAddr = System.identityHashCode(thebb);
                    }
                }
                if (!refInPool || !this.debug) {
                    this.itsPool.add(thebb);
                } else {
                    String threadName = Thread.currentThread().getName();
                    Throwable t2 = new Throwable(threadName + ": Duplicate ByteBuffer reference (" + bbAddr + ")");
                    t2.printStackTrace(System.out);
                }
            }
            --this.itsObjectCounter;
        } else {
            thebb = null;
        }
    }

    @Override
    public int activeCount() {
        return this.itsObjectCounter;
    }
}

