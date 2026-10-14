/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerFactory;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.pept.encoding.InputObject;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.transport.CorbaConnection;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;

public class CDRInputObject
extends CDRInputStream
implements InputObject {
    private CorbaConnection corbaConnection;
    private Message header;
    private boolean unmarshaledHeader;
    private ORBUtilSystemException wrapper;
    private OMGSystemException omgWrapper;

    public CDRInputObject(ORB orb, CorbaConnection corbaConnection, ByteBuffer byteBuffer, Message header) {
        super(orb, byteBuffer, header.getSize(), header.isLittleEndian(), header.getGIOPVersion(), header.getEncodingVersion(), BufferManagerFactory.newBufferManagerRead(header.getGIOPVersion(), header.getEncodingVersion(), orb));
        this.corbaConnection = corbaConnection;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
        this.omgWrapper = OMGSystemException.get(orb, "rpc.encoding");
        if (orb.transportDebugFlag) {
            this.dprint(".CDRInputObject constructor:");
        }
        this.getBufferManager().init(header);
        this.header = header;
        this.unmarshaledHeader = false;
        this.setIndex(12);
        this.setBufferLength(header.getSize());
    }

    public final CorbaConnection getConnection() {
        return this.corbaConnection;
    }

    public Message getMessageHeader() {
        return this.header;
    }

    public void unmarshalHeader() {
        if (!this.unmarshaledHeader) {
            try {
                if (((ORB)this.orb()).transportDebugFlag) {
                    this.dprint(".unmarshalHeader->: " + this.getMessageHeader());
                }
                this.getMessageHeader().read(this);
                this.unmarshaledHeader = true;
            }
            catch (RuntimeException e) {
                if (((ORB)this.orb()).transportDebugFlag) {
                    this.dprint(".unmarshalHeader: !!ERROR!!: " + this.getMessageHeader() + ": " + e);
                }
                throw e;
            }
            finally {
                if (((ORB)this.orb()).transportDebugFlag) {
                    this.dprint(".unmarshalHeader<-: " + this.getMessageHeader());
                }
            }
        }
    }

    public final boolean unmarshaledHeader() {
        return this.unmarshaledHeader;
    }

    @Override
    protected CodeSetConversion.BTCConverter createCharBTCConverter() {
        CodeSetComponentInfo.CodeSetContext codesets = this.getCodeSets();
        if (codesets == null) {
            return super.createCharBTCConverter();
        }
        OSFCodeSetRegistry.Entry charSet = OSFCodeSetRegistry.lookupEntry(codesets.getCharCodeSet());
        if (charSet == null) {
            throw this.wrapper.unknownCodeset(charSet);
        }
        return CodeSetConversion.impl().getBTCConverter(charSet, this.isLittleEndian());
    }

    @Override
    protected CodeSetConversion.BTCConverter createWCharBTCConverter() {
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
        if (wcharSet == OSFCodeSetRegistry.UTF_16 && this.getGIOPVersion().equals(GIOPVersion.V1_2)) {
            return CodeSetConversion.impl().getBTCConverter(wcharSet, false);
        }
        return CodeSetConversion.impl().getBTCConverter(wcharSet, this.isLittleEndian());
    }

    private CodeSetComponentInfo.CodeSetContext getCodeSets() {
        if (this.getConnection() == null) {
            return CodeSetComponentInfo.LOCAL_CODE_SETS;
        }
        return this.getConnection().getCodeSetContext();
    }

    @Override
    public final CodeBase getCodeBase() {
        if (this.getConnection() == null) {
            return null;
        }
        return this.getConnection().getCodeBase();
    }

    @Override
    public CDRInputStream dup() {
        return null;
    }

    protected void dprint(String msg) {
        ORBUtility.dprint("CDRInputObject", msg);
    }
}

