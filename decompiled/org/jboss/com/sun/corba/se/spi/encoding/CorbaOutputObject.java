/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.encoding;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream;
import org.jboss.com.sun.corba.se.pept.encoding.OutputObject;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.transport.CorbaConnection;

public abstract class CorbaOutputObject
extends CDROutputStream
implements OutputObject {
    public CorbaOutputObject(ORB orb, GIOPVersion version, byte encodingVersion, boolean littleEndian, BufferManagerWrite bufferManager, byte streamFormatVersion, boolean usePooledByteBuffers) {
        super(orb, version, encodingVersion, littleEndian, bufferManager, streamFormatVersion, usePooledByteBuffers);
    }

    public abstract void writeTo(CorbaConnection var1) throws IOException;
}

