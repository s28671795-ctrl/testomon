/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerFactory;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.ORB;

public class EncapsInputStream
extends CDRInputStream {
    private ORBUtilSystemException wrapper;
    private CodeBase codeBase;

    public EncapsInputStream(ORB orb, byte[] buf, int size, boolean littleEndian, GIOPVersion version) {
        super(orb, ByteBuffer.wrap(buf), size, littleEndian, version, (byte)0, BufferManagerFactory.newBufferManagerRead(0, (byte)0, (org.jboss.com.sun.corba.se.spi.orb.ORB)orb));
        this.wrapper = ORBUtilSystemException.get((org.jboss.com.sun.corba.se.spi.orb.ORB)orb, "rpc.encoding");
        this.performORBVersionSpecificInit();
    }

    public EncapsInputStream(ORB orb, ByteBuffer byteBuffer, int size, boolean littleEndian, GIOPVersion version) {
        super(orb, byteBuffer, size, littleEndian, version, (byte)0, BufferManagerFactory.newBufferManagerRead(0, (byte)0, (org.jboss.com.sun.corba.se.spi.orb.ORB)orb));
        this.performORBVersionSpecificInit();
    }

    public EncapsInputStream(ORB orb, byte[] data, int size) {
        this(orb, data, size, GIOPVersion.V1_2);
    }

    public EncapsInputStream(EncapsInputStream eis) {
        super(eis);
        this.wrapper = ORBUtilSystemException.get((org.jboss.com.sun.corba.se.spi.orb.ORB)eis.orb(), "rpc.encoding");
        this.performORBVersionSpecificInit();
    }

    public EncapsInputStream(ORB orb, byte[] data, int size, GIOPVersion version) {
        this(orb, data, size, false, version);
    }

    public EncapsInputStream(ORB orb, byte[] data, int size, GIOPVersion version, CodeBase codeBase) {
        super(orb, ByteBuffer.wrap(data), size, false, version, (byte)0, BufferManagerFactory.newBufferManagerRead(0, (byte)0, (org.jboss.com.sun.corba.se.spi.orb.ORB)orb));
        this.codeBase = codeBase;
        this.performORBVersionSpecificInit();
    }

    @Override
    public CDRInputStream dup() {
        return new EncapsInputStream(this);
    }

    @Override
    protected CodeSetConversion.BTCConverter createCharBTCConverter() {
        return CodeSetConversion.impl().getBTCConverter(OSFCodeSetRegistry.ISO_8859_1);
    }

    @Override
    protected CodeSetConversion.BTCConverter createWCharBTCConverter() {
        if (this.getGIOPVersion().equals(GIOPVersion.V1_0)) {
            throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
        }
        if (this.getGIOPVersion().equals(GIOPVersion.V1_1)) {
            return CodeSetConversion.impl().getBTCConverter(OSFCodeSetRegistry.UTF_16, this.isLittleEndian());
        }
        return CodeSetConversion.impl().getBTCConverter(OSFCodeSetRegistry.UTF_16, false);
    }

    @Override
    public CodeBase getCodeBase() {
        return this.codeBase;
    }
}

