/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.MarkAndResetHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public interface BufferManagerRead {
    public void processFragment(ByteBuffer var1, FragmentMessage var2);

    public ByteBufferWithInfo underflow(ByteBufferWithInfo var1);

    public void init(Message var1);

    public MarkAndResetHandler getMarkAndResetHandler();

    public void cancelProcessing(int var1);

    public void close(ByteBufferWithInfo var1);
}

