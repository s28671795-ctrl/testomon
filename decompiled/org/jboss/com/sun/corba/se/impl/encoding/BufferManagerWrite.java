/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public abstract class BufferManagerWrite {
    protected ORB orb;
    protected ORBUtilSystemException wrapper;
    protected Object outputObject;
    protected boolean sentFullMessage = false;

    BufferManagerWrite(ORB orb) {
        this.orb = orb;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
    }

    public abstract boolean sentFragment();

    public boolean sentFullMessage() {
        return this.sentFullMessage;
    }

    public abstract int getBufferSize();

    public abstract void overflow(ByteBufferWithInfo var1);

    public abstract void sendMessage();

    public void setOutputObject(Object outputObject) {
        this.outputObject = outputObject;
    }

    public abstract void close();
}

