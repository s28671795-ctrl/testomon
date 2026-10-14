/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerRead;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerReadGrow;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerReadStream;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWriteCollect;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWriteGrow;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWriteStream;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.INTERNAL;

public class BufferManagerFactory {
    public static final int GROW = 0;
    public static final int COLLECT = 1;
    public static final int STREAM = 2;

    public static BufferManagerRead newBufferManagerRead(GIOPVersion version, byte encodingVersion, ORB orb) {
        if (encodingVersion != 0) {
            return new BufferManagerReadGrow(orb);
        }
        switch (version.intValue()) {
            case 256: {
                return new BufferManagerReadGrow(orb);
            }
            case 257: 
            case 258: {
                return new BufferManagerReadStream(orb);
            }
        }
        throw new INTERNAL("Unknown GIOP version: " + version);
    }

    public static BufferManagerRead newBufferManagerRead(int strategy, byte encodingVersion, ORB orb) {
        if (encodingVersion != 0) {
            if (strategy != 0) {
                ORBUtilSystemException wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
                throw wrapper.invalidBuffMgrStrategy("newBufferManagerRead");
            }
            return new BufferManagerReadGrow(orb);
        }
        switch (strategy) {
            case 0: {
                return new BufferManagerReadGrow(orb);
            }
            case 1: {
                throw new INTERNAL("Collect strategy invalid for reading");
            }
            case 2: {
                return new BufferManagerReadStream(orb);
            }
        }
        throw new INTERNAL("Unknown buffer manager read strategy: " + strategy);
    }

    public static BufferManagerWrite newBufferManagerWrite(int strategy, byte encodingVersion, ORB orb) {
        if (encodingVersion != 0) {
            if (strategy != 0) {
                ORBUtilSystemException wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
                throw wrapper.invalidBuffMgrStrategy("newBufferManagerWrite");
            }
            return new BufferManagerWriteGrow(orb);
        }
        switch (strategy) {
            case 0: {
                return new BufferManagerWriteGrow(orb);
            }
            case 1: {
                return new BufferManagerWriteCollect(orb);
            }
            case 2: {
                return new BufferManagerWriteStream(orb);
            }
        }
        throw new INTERNAL("Unknown buffer manager write strategy: " + strategy);
    }

    public static BufferManagerWrite newBufferManagerWrite(GIOPVersion version, byte encodingVersion, ORB orb) {
        if (encodingVersion != 0) {
            return new BufferManagerWriteGrow(orb);
        }
        return BufferManagerFactory.newBufferManagerWrite(orb.getORBData().getGIOPBuffMgrStrategy(version), encodingVersion, orb);
    }

    public static BufferManagerRead defaultBufferManagerRead(ORB orb) {
        return new BufferManagerReadGrow(orb);
    }
}

