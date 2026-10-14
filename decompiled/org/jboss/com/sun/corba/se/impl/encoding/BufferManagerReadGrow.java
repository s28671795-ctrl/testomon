/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerRead;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.MarkAndResetHandler;
import org.jboss.com.sun.corba.se.impl.encoding.RestorableInputStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public class BufferManagerReadGrow
implements BufferManagerRead,
MarkAndResetHandler {
    private ORBUtilSystemException wrapper;
    private Object streamMemento;
    private RestorableInputStream inputStream;
    private boolean markEngaged = false;

    BufferManagerReadGrow(ORB orb) {
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
    }

    @Override
    public void processFragment(ByteBuffer byteBuffer, FragmentMessage header) {
    }

    @Override
    public void init(Message msg) {
    }

    @Override
    public ByteBufferWithInfo underflow(ByteBufferWithInfo bbwi) {
        throw this.wrapper.unexpectedEof();
    }

    @Override
    public void cancelProcessing(int requestId) {
    }

    @Override
    public MarkAndResetHandler getMarkAndResetHandler() {
        return this;
    }

    @Override
    public void mark(RestorableInputStream is) {
        this.markEngaged = true;
        this.inputStream = is;
        this.streamMemento = this.inputStream.createStreamMemento();
    }

    @Override
    public void fragmentationOccured(ByteBufferWithInfo newFragment) {
    }

    @Override
    public void reset() {
        if (!this.markEngaged) {
            return;
        }
        this.markEngaged = false;
        this.inputStream.restoreInternalState(this.streamMemento);
        this.streamMemento = null;
    }

    @Override
    public void close(ByteBufferWithInfo bbwi) {
    }
}

