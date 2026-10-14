/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerFactory;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.impl.encoding.EncapsInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.InputStream;

public class EncapsOutputStream
extends CDROutputStream {
    static final boolean usePooledByteBuffers = false;

    public EncapsOutputStream(ORB orb) {
        this(orb, GIOPVersion.V1_2);
    }

    public EncapsOutputStream(ORB orb, GIOPVersion version) {
        this(orb, version, false);
    }

    public EncapsOutputStream(ORB orb, boolean isLittleEndian) {
        this(orb, GIOPVersion.V1_2, isLittleEndian);
    }

    public EncapsOutputStream(ORB orb, GIOPVersion version, boolean isLittleEndian) {
        super(orb, version, (byte)0, isLittleEndian, BufferManagerFactory.newBufferManagerWrite(0, (byte)0, orb), (byte)1, false);
    }

    @Override
    public InputStream create_input_stream() {
        this.freeInternalCaches();
        return new EncapsInputStream(this.orb(), this.getByteBuffer(), this.getSize(), this.isLittleEndian(), this.getGIOPVersion());
    }

    @Override
    protected CodeSetConversion.CTBConverter createCharCTBConverter() {
        return CodeSetConversion.impl().getCTBConverter(OSFCodeSetRegistry.ISO_8859_1);
    }

    @Override
    protected CodeSetConversion.CTBConverter createWCharCTBConverter() {
        if (this.getGIOPVersion().equals(GIOPVersion.V1_0)) {
            throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
        }
        if (this.getGIOPVersion().equals(GIOPVersion.V1_1)) {
            return CodeSetConversion.impl().getCTBConverter(OSFCodeSetRegistry.UTF_16, this.isLittleEndian(), false);
        }
        boolean useBOM = ((ORB)this.orb()).getORBData().useByteOrderMarkersInEncapsulations();
        return CodeSetConversion.impl().getCTBConverter(OSFCodeSetRegistry.UTF_16, false, useBOM);
    }
}

