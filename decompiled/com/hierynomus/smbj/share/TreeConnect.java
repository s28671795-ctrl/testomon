/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.share;

import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.mssmb2.SMB2ShareCapabilities;
import com.hierynomus.mssmb2.SMB2ShareFlags;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.mssmb2.messages.SMB2TreeDisconnect;
import com.hierynomus.protocol.commons.concurrent.Futures;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.common.SmbPath;
import com.hierynomus.smbj.connection.ConnectionContext;
import com.hierynomus.smbj.connection.NegotiatedProtocol;
import com.hierynomus.smbj.event.SMBEventBus;
import com.hierynomus.smbj.event.TreeDisconnected;
import com.hierynomus.smbj.session.Session;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TreeConnect {
    private long treeId;
    private SmbPath smbPath;
    private Session session;
    private final Set<SMB2ShareCapabilities> capabilities;
    private final NegotiatedProtocol negotiatedProtocol;
    private SmbConfig config;
    private final SMBEventBus bus;
    private final Set<AccessMask> maximalAccess;
    private final boolean encryptData;

    public TreeConnect(long treeId, SmbPath smbPath, Session session, Set<SMB2ShareCapabilities> capabilities, SmbConfig config, ConnectionContext connectionContext, SMBEventBus bus, Set<AccessMask> maximalAccess, Set<SMB2ShareFlags> shareFlags) {
        this.treeId = treeId;
        this.smbPath = smbPath;
        this.session = session;
        this.capabilities = capabilities;
        this.negotiatedProtocol = connectionContext.getNegotiatedProtocol();
        this.config = config;
        this.bus = bus;
        this.maximalAccess = maximalAccess;
        this.encryptData = shareFlags.contains(SMB2ShareFlags.SMB2_SHAREFLAG_ENCRYPT_DATA) && this.negotiatedProtocol.getDialect().isSmb3x() && connectionContext.supportsEncryption();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void close() throws TransportException {
        try {
            SMB2TreeDisconnect disconnect = new SMB2TreeDisconnect(this.negotiatedProtocol.getDialect(), this.session.getSessionId(), this.treeId);
            Future send = this.session.send(disconnect);
            SMB2Packet smb2Packet = (SMB2Packet)Futures.get(send, this.config.getTransactTimeout(), TimeUnit.MILLISECONDS, TransportException.Wrapper);
            if (!NtStatus.isSuccess(((SMB2PacketHeader)smb2Packet.getHeader()).getStatusCode())) {
                throw new SMBApiException((SMB2PacketHeader)smb2Packet.getHeader(), "Error closing connection to " + this.smbPath);
            }
        }
        finally {
            this.bus.publish(new TreeDisconnected(this.session.getSessionId(), this.treeId));
        }
    }

    public String getShareName() {
        return this.smbPath.getShareName();
    }

    public long getTreeId() {
        return this.treeId;
    }

    public Session getSession() {
        return this.session;
    }

    public Set<AccessMask> getMaximalAccess() {
        return this.maximalAccess;
    }

    public boolean isDfsShare() {
        return this.capabilities.contains(SMB2ShareCapabilities.SMB2_SHARE_CAP_DFS);
    }

    public boolean isCAShare() {
        return this.capabilities.contains(SMB2ShareCapabilities.SMB2_SHARE_CAP_CONTINUOUS_AVAILABILITY);
    }

    public boolean isScaleoutShare() {
        return this.capabilities.contains(SMB2ShareCapabilities.SMB2_SHARE_CAP_SCALEOUT);
    }

    public SmbConfig getConfig() {
        return this.config;
    }

    public NegotiatedProtocol getNegotiatedProtocol() {
        return this.negotiatedProtocol;
    }

    public String toString() {
        return String.format("TreeConnect[%s](%s)", this.treeId, this.smbPath);
    }
}

