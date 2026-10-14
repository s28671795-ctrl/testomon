/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection;

import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2GlobalCapability;
import com.hierynomus.mssmb2.SMB3CompressionAlgorithm;
import com.hierynomus.mssmb2.SMB3EncryptionCipher;
import com.hierynomus.mssmb2.SMB3HashAlgorithm;
import com.hierynomus.mssmb2.messages.SMB2NegotiateResponse;
import com.hierynomus.ntlm.messages.WindowsVersion;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.connection.NegotiatedProtocol;
import com.hierynomus.smbj.connection.SMBProtocolNegotiator;
import com.hierynomus.smbj.server.Server;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

public class ConnectionContext {
    private WindowsVersion windowsVersion;
    private String netBiosName;
    private byte[] gssNegotiateToken;
    private NegotiatedProtocol negotiatedProtocol;
    private Server server;
    private final UUID clientGuid;
    private EnumSet<SMB2GlobalCapability> clientCapabilities;
    private int clientSecurityMode;
    private SMB3HashAlgorithm preauthIntegrityHashId;
    private byte[] preauthIntegrityHashValue;
    private SMB3EncryptionCipher cipherId;
    private Set<SMB3CompressionAlgorithm> compressionIds;
    private Long timeOffsetMillis;

    ConnectionContext(UUID clientGuid, String hostname, int port, SmbConfig config) {
        this.clientGuid = clientGuid;
        this.gssNegotiateToken = new byte[0];
        this.clientCapabilities = EnumSet.copyOf(config.getClientCapabilities());
        this.clientSecurityMode = config.isSigningRequired() ? 2 : 1;
        this.server = new Server(hostname, port);
    }

    void negotiated(SMBProtocolNegotiator.NegotiationContext negotiationContext) {
        SMB2NegotiateResponse response = negotiationContext.getNegotiationResponse();
        this.server = negotiationContext.getServer();
        this.negotiatedProtocol = new NegotiatedProtocol(response.getDialect(), response.getMaxTransactSize(), response.getMaxReadSize(), response.getMaxWriteSize(), this.supportsMultiCredit());
        this.cipherId = negotiationContext.getCipher();
        this.compressionIds = negotiationContext.getCompressionIds();
        this.preauthIntegrityHashId = negotiationContext.getPreauthIntegrityHashId();
        this.preauthIntegrityHashValue = negotiationContext.getPreauthIntegrityHashValue() != null ? negotiationContext.getPreauthIntegrityHashValue() : new byte[]{};
        this.timeOffsetMillis = System.currentTimeMillis() - response.getSystemTime().toEpochMillis();
    }

    public UUID getClientGuid() {
        return this.clientGuid;
    }

    public boolean isServerRequiresSigning() {
        return (this.server.getSecurityMode() & 2) > 0;
    }

    public boolean isServerSigningEnabled() {
        return (this.server.getSecurityMode() & 1) > 0;
    }

    int getServerSecurityMode() {
        return this.server.getSecurityMode();
    }

    Set<SMB2GlobalCapability> getServerCapabilities() {
        return this.server.getCapabilities();
    }

    public NegotiatedProtocol getNegotiatedProtocol() {
        return this.negotiatedProtocol;
    }

    public byte[] getGssNegotiateToken() {
        return Arrays.copyOf(this.gssNegotiateToken, this.gssNegotiateToken.length);
    }

    public UUID getServerGuid() {
        return this.server.getServerGUID();
    }

    public String getServerName() {
        return this.server.getServerName();
    }

    private boolean supports(SMB2GlobalCapability capability) {
        return this.server.getCapabilities().contains(capability);
    }

    public EnumSet<SMB2GlobalCapability> getClientCapabilities() {
        return this.clientCapabilities;
    }

    public WindowsVersion getWindowsVersion() {
        return this.windowsVersion;
    }

    public void setWindowsVersion(WindowsVersion windowsVersion) {
        this.windowsVersion = windowsVersion;
    }

    public String getNetBiosName() {
        return this.netBiosName;
    }

    public void setNetBiosName(String netBiosName) {
        this.netBiosName = netBiosName;
    }

    public SMB3EncryptionCipher getCipherId() {
        return this.cipherId;
    }

    public boolean supportsEncryption() {
        SMB2Dialect dialect = this.negotiatedProtocol.getDialect();
        if (dialect == SMB2Dialect.SMB_3_1_1) {
            return this.cipherId != null;
        }
        return this.clientCapabilities.contains(SMB2GlobalCapability.SMB2_GLOBAL_CAP_ENCRYPTION) && this.supports(SMB2GlobalCapability.SMB2_GLOBAL_CAP_ENCRYPTION);
    }

    public boolean clientPrefersEncryption() {
        return this.negotiatedProtocol.getDialect().isSmb3x() && this.supportsEncryption();
    }

    public boolean supportsDFS() {
        return this.supports(SMB2GlobalCapability.SMB2_GLOBAL_CAP_DFS);
    }

    public boolean supportsFileLeasing() {
        return this.supports(SMB2GlobalCapability.SMB2_GLOBAL_CAP_LEASING);
    }

    public boolean supportsMultiCredit() {
        return this.supports(SMB2GlobalCapability.SMB2_GLOBAL_CAP_LARGE_MTU);
    }

    public boolean supportsDirectoryLeasing() {
        return this.negotiatedProtocol.getDialect().isSmb3x() && this.supports(SMB2GlobalCapability.SMB2_GLOBAL_CAP_DIRECTORY_LEASING);
    }

    public boolean supportsMultiChannel() {
        return this.negotiatedProtocol.getDialect().isSmb3x() && this.supports(SMB2GlobalCapability.SMB2_GLOBAL_CAP_MULTI_CHANNEL);
    }

    public Long getTimeOffsetMillis() {
        return this.timeOffsetMillis;
    }

    public String toString() {
        return "ConnectionContext{\n  serverGuid=" + this.server.getServerGUID() + ",\n  serverName='" + this.server.getServerName() + "',\n  negotiatedProtocol=" + this.negotiatedProtocol + ",\n  clientGuid=" + this.clientGuid + ",\n  clientCapabilities=" + this.clientCapabilities + ",\n  serverCapabilities=" + this.server.getCapabilities() + ",\n  clientSecurityMode=" + this.clientSecurityMode + ",\n  serverSecurityMode=" + this.server.getSecurityMode() + ",\n  server='" + this.server + "'\n" + '}';
    }

    public Server getServer() {
        return this.server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public SMB3HashAlgorithm getPreauthIntegrityHashId() {
        return this.preauthIntegrityHashId;
    }

    public byte[] getPreauthIntegrityHashValue() {
        return this.preauthIntegrityHashValue;
    }

    public Set<SMB3CompressionAlgorithm> getCompressionIds() {
        return this.compressionIds;
    }
}

