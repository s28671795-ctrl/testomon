/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerFactory;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream_1_0;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.spi.encoding.CorbaOutputObject;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaMessageMediator;
import org.jboss.com.sun.corba.se.spi.transport.CorbaConnection;
import org.omg.CORBA.portable.InputStream;

public class CDROutputObject
extends CorbaOutputObject {
    private Message header;
    private ORBUtilSystemException wrapper;
    private OMGSystemException omgWrapper;
    private CorbaConnection connection;

    private CDROutputObject(ORB orb, GIOPVersion giopVersion, Message header, BufferManagerWrite manager, byte streamFormatVersion, CorbaMessageMediator mediator) {
        super(orb, giopVersion, header.getEncodingVersion(), false, manager, streamFormatVersion, mediator != null && mediator.getConnection() != null ? ((CorbaConnection)mediator.getConnection()).shouldUseDirectByteBuffers() : false);
        this.header = header;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
        this.omgWrapper = OMGSystemException.get(orb, "rpc.encoding");
        this.getBufferManager().setOutputObject(this);
        this.corbaMessageMediator = mediator;
    }

    public CDROutputObject(ORB orb, MessageMediator messageMediator, Message header, byte streamFormatVersion) {
        this(orb, ((CorbaMessageMediator)messageMediator).getGIOPVersion(), header, BufferManagerFactory.newBufferManagerWrite(((CorbaMessageMediator)messageMediator).getGIOPVersion(), header.getEncodingVersion(), orb), streamFormatVersion, (CorbaMessageMediator)messageMediator);
    }

    public CDROutputObject(ORB orb, MessageMediator messageMediator, Message header, byte streamFormatVersion, int strategy) {
        this(orb, ((CorbaMessageMediator)messageMediator).getGIOPVersion(), header, BufferManagerFactory.newBufferManagerWrite(strategy, header.getEncodingVersion(), orb), streamFormatVersion, (CorbaMessageMediator)messageMediator);
    }

    public CDROutputObject(ORB orb, CorbaMessageMediator mediator, GIOPVersion giopVersion, CorbaConnection connection, Message header, byte streamFormatVersion) {
        this(orb, giopVersion, header, BufferManagerFactory.newBufferManagerWrite(giopVersion, header.getEncodingVersion(), orb), streamFormatVersion, mediator);
        this.connection = connection;
    }

    public Message getMessageHeader() {
        return this.header;
    }

    public final void finishSendingMessage() {
        this.getBufferManager().sendMessage();
    }

    @Override
    public void writeTo(CorbaConnection connection) throws IOException {
        ByteBufferWithInfo bbwi = this.getByteBufferWithInfo();
        this.getMessageHeader().setSize(bbwi.byteBuffer, bbwi.getSize());
        if (this.orb() != null) {
            if (((ORB)this.orb()).transportDebugFlag) {
                this.dprint(".writeTo: " + connection);
            }
            if (((ORB)this.orb()).giopDebugFlag) {
                CDROutputStream_1_0.printBuffer(bbwi);
            }
        }
        bbwi.byteBuffer.position(0).limit(bbwi.getSize());
        connection.write(bbwi.byteBuffer);
    }

    @Override
    public InputStream create_input_stream() {
        return null;
    }

    public CorbaConnection getConnection() {
        if (this.connection != null) {
            return this.connection;
        }
        return (CorbaConnection)this.corbaMessageMediator.getConnection();
    }

    @Override
    public final ByteBufferWithInfo getByteBufferWithInfo() {
        return super.getByteBufferWithInfo();
    }

    @Override
    public final void setByteBufferWithInfo(ByteBufferWithInfo bbwi) {
        super.setByteBufferWithInfo(bbwi);
    }

    @Override
    protected CodeSetConversion.CTBConverter createCharCTBConverter() {
        CodeSetComponentInfo.CodeSetContext codesets = this.getCodeSets();
        if (codesets == null) {
            return super.createCharCTBConverter();
        }
        OSFCodeSetRegistry.Entry charSet = OSFCodeSetRegistry.lookupEntry(codesets.getCharCodeSet());
        if (charSet == null) {
            throw this.wrapper.unknownCodeset(charSet);
        }
        return CodeSetConversion.impl().getCTBConverter(charSet, this.isLittleEndian(), false);
    }

    @Override
    protected CodeSetConversion.CTBConverter createWCharCTBConverter() {
        CodeSetComponentInfo.CodeSetContext codesets = this.getCodeSets();
        if (codesets == null) {
            if (this.getConnection().isServer()) {
                throw this.omgWrapper.noClientWcharCodesetCtx();
            }
            throw this.omgWrapper.noServerWcharCodesetCmp();
        }
        OSFCodeSetRegistry.Entry wcharSet = OSFCodeSetRegistry.lookupEntry(codesets.getWCharCodeSet());
        if (wcharSet == null) {
            throw this.wrapper.unknownCodeset(wcharSet);
        }
        boolean useByteOrderMarkers = ((ORB)this.orb()).getORBData().useByteOrderMarkers();
        if (wcharSet == OSFCodeSetRegistry.UTF_16) {
            if (this.getGIOPVersion().equals(GIOPVersion.V1_2)) {
                return CodeSetConversion.impl().getCTBConverter(wcharSet, false, useByteOrderMarkers);
            }
            if (this.getGIOPVersion().equals(GIOPVersion.V1_1)) {
                return CodeSetConversion.impl().getCTBConverter(wcharSet, this.isLittleEndian(), false);
            }
        }
        return CodeSetConversion.impl().getCTBConverter(wcharSet, this.isLittleEndian(), useByteOrderMarkers);
    }

    private CodeSetComponentInfo.CodeSetContext getCodeSets() {
        if (this.getConnection() == null) {
            return CodeSetComponentInfo.LOCAL_CODE_SETS;
        }
        return this.getConnection().getCodeSetContext();
    }

    protected void dprint(String msg) {
        ORBUtility.dprint("CDROutputObject", msg);
    }
}

