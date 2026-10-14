/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.server;

import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2GlobalCapability;
import java.util.Set;
import java.util.UUID;

public class Server {
    private boolean initialized;
    private String serverName;
    private int port;
    private UUID serverGUID;
    private SMB2Dialect dialectRevision;
    private int securityMode;
    private Set<SMB2GlobalCapability> capabilities;

    public Server(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
        this.initialized = false;
    }

    public void init(UUID serverGUID, SMB2Dialect dialectRevision, int securityMode, Set<SMB2GlobalCapability> capabilities) {
        if (this.initialized) {
            throw new IllegalStateException(String.format("Server object '%s' already initialized", this.serverName));
        }
        this.initialized = true;
        this.serverGUID = serverGUID;
        this.dialectRevision = dialectRevision;
        this.securityMode = securityMode;
        this.capabilities = capabilities;
    }

    public String getServerName() {
        return this.serverName;
    }

    public int getPort() {
        return this.port;
    }

    public UUID getServerGUID() {
        return this.serverGUID;
    }

    public SMB2Dialect getDialectRevision() {
        return this.dialectRevision;
    }

    public int getSecurityMode() {
        return this.securityMode;
    }

    public Set<SMB2GlobalCapability> getCapabilities() {
        return this.capabilities;
    }

    public boolean validate(Server other) {
        boolean guids = other.getServerGUID().equals(this.serverGUID);
        boolean dialects = other.getDialectRevision().equals((Object)this.dialectRevision);
        boolean securityModes = other.getSecurityMode() == this.securityMode;
        boolean capabilities = other.getCapabilities().equals(this.capabilities);
        return guids && dialects && securityModes && capabilities;
    }
}

