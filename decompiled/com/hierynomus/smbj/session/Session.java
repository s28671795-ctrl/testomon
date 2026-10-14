/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.session;

import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.SMB2MessageCommandCode;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.mssmb2.SMB2ShareCapabilities;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.mssmb2.messages.SMB2CreateRequest;
import com.hierynomus.mssmb2.messages.SMB2Logoff;
import com.hierynomus.mssmb2.messages.SMB2TreeConnectRequest;
import com.hierynomus.mssmb2.messages.SMB2TreeConnectResponse;
import com.hierynomus.protocol.commons.concurrent.Futures;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.common.SmbPath;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.connection.PacketEncryptor;
import com.hierynomus.smbj.connection.PacketSignatory;
import com.hierynomus.smbj.event.SMBEventBus;
import com.hierynomus.smbj.event.SessionLoggedOff;
import com.hierynomus.smbj.event.TreeDisconnected;
import com.hierynomus.smbj.paths.PathResolveException;
import com.hierynomus.smbj.paths.PathResolver;
import com.hierynomus.smbj.session.SessionContext;
import com.hierynomus.smbj.session.TreeConnectTable;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.PipeShare;
import com.hierynomus.smbj.share.PrinterShare;
import com.hierynomus.smbj.share.Share;
import com.hierynomus.smbj.share.TreeConnect;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.crypto.SecretKey;
import net.engio.mbassy.listener.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Session
implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(Session.class);
    private long sessionId;
    private Connection connection;
    private final SmbConfig config;
    private SMBEventBus bus;
    private final PathResolver pathResolver;
    private PacketSignatory signatory;
    private PacketEncryptor encryptor;
    private TreeConnectTable treeConnectTable = new TreeConnectTable();
    private Map<String, Session> nestedSessionsByHost = new HashMap<String, Session>();
    private ReentrantReadWriteLock nestedSessionsRwLock = new ReentrantReadWriteLock();
    private AuthenticationContext userCredentials;
    private SessionContext sessionContext;

    public Session(Connection connection, SmbConfig config, AuthenticationContext userCredentials, SMBEventBus bus, PathResolver pathResolver, PacketSignatory signatory, PacketEncryptor encryptor) {
        this.connection = connection;
        this.config = config;
        this.userCredentials = userCredentials;
        this.bus = bus;
        this.pathResolver = pathResolver;
        this.signatory = signatory;
        this.sessionContext = new SessionContext();
        this.encryptor = encryptor;
        if (bus != null) {
            bus.subscribe(this);
        }
    }

    public long getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public Share connectShare(String shareName) {
        if (shareName.contains("\\")) {
            throw new IllegalArgumentException(String.format("Share name (%s) cannot contain '\\' characters.", shareName));
        }
        Share connectedShare = this.treeConnectTable.getTreeConnect(shareName);
        if (connectedShare != null) {
            logger.debug("Returning cached Share {} for {}", (Object)connectedShare, (Object)shareName);
            return connectedShare;
        }
        return this.connectTree(shareName);
    }

    private Share connectTree(String shareName) {
        String remoteHostname = this.connection.getRemoteHostname();
        final SmbPath smbPath = new SmbPath(remoteHostname, shareName);
        logger.info("Connecting to {} on session {}", (Object)smbPath, (Object)this.sessionId);
        try {
            Share share;
            SMB2TreeConnectRequest smb2TreeConnectRequest = new SMB2TreeConnectRequest(this.connection.getNegotiatedProtocol().getDialect(), smbPath, this.sessionId);
            ((SMB2PacketHeader)smb2TreeConnectRequest.getHeader()).setCreditRequest(256);
            Future send = this.send(smb2TreeConnectRequest);
            SMB2TreeConnectResponse response = (SMB2TreeConnectResponse)Futures.get(send, this.config.getTransactTimeout(), TimeUnit.MILLISECONDS, TransportException.Wrapper);
            try {
                Share share2 = this.pathResolver.resolve(this, response, smbPath, new PathResolver.ResolveAction<Share>(){

                    @Override
                    public Share apply(SmbPath target) {
                        Session session = Session.this;
                        if (!target.isOnSameHost(smbPath)) {
                            logger.info("Re-routing the connection to host {}", (Object)target.getHostname());
                            session = Session.this.getNestedSession(target);
                        }
                        if (!target.isOnSameShare(smbPath)) {
                            return session.connectShare(target.getShareName());
                        }
                        return null;
                    }
                });
                if (share2 != null) {
                    return share2;
                }
            }
            catch (PathResolveException share2) {
                // empty catch block
            }
            if (NtStatus.isError(((SMB2PacketHeader)response.getHeader()).getStatusCode())) {
                logger.debug(((SMB2PacketHeader)response.getHeader()).toString());
                throw new SMBApiException((SMB2PacketHeader)response.getHeader(), "Could not connect to " + smbPath);
            }
            if (response.getCapabilities().contains(SMB2ShareCapabilities.SMB2_SHARE_CAP_ASYMMETRIC)) {
                throw new SMBRuntimeException("ASYMMETRIC capability unsupported");
            }
            long treeId = ((SMB2PacketHeader)response.getHeader()).getTreeId();
            TreeConnect treeConnect = new TreeConnect(treeId, smbPath, this, response.getCapabilities(), this.config, this.connection.getConnectionContext(), this.bus, response.getMaximalAccess(), response.getShareFlags());
            if (response.isDiskShare()) {
                share = new DiskShare(smbPath, treeConnect, this.pathResolver);
            } else if (response.isNamedPipe()) {
                share = new PipeShare(smbPath, treeConnect);
            } else if (response.isPrinterShare()) {
                share = new PrinterShare(smbPath, treeConnect);
            } else {
                throw new SMBRuntimeException("Unknown ShareType returned in the TREE_CONNECT Response");
            }
            this.treeConnectTable.register(share);
            return share;
        }
        catch (TransportException e) {
            throw new SMBRuntimeException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Session getNestedSession(SmbPath resolvedSharePath) {
        Session session;
        this.nestedSessionsRwLock.readLock().lock();
        try {
            Session existingSession = this.nestedSessionsByHost.get(resolvedSharePath.getHostname());
            if (existingSession != null) {
                Session session2 = existingSession;
                return session2;
            }
        }
        finally {
            this.nestedSessionsRwLock.readLock().unlock();
        }
        this.nestedSessionsRwLock.writeLock().lock();
        try {
            session = this.nestedSessionsByHost.get(resolvedSharePath.getHostname());
            if (session == null) {
                session = this.createNestedSession(resolvedSharePath);
                this.nestedSessionsByHost.put(resolvedSharePath.getHostname(), session);
            }
            this.nestedSessionsRwLock.readLock().lock();
        }
        finally {
            this.nestedSessionsRwLock.writeLock().unlock();
        }
        try {
            Session session3 = session;
            return session3;
        }
        finally {
            this.nestedSessionsRwLock.readLock().unlock();
        }
    }

    private Session createNestedSession(SmbPath smbPath) {
        try {
            Connection connection = this.getConnection().getClient().connect(smbPath.getHostname());
            return connection.authenticate(this.getAuthenticationContext());
        }
        catch (IOException e) {
            throw new SMBApiException(NtStatus.STATUS_OTHER.getValue(), SMB2MessageCommandCode.SMB2_NEGOTIATE, "Could not connect to DFS root " + smbPath, e);
        }
    }

    @Handler
    private void disconnectTree(TreeDisconnected disconnectEvent) {
        if (disconnectEvent.getSessionId() == this.sessionId) {
            logger.debug("Notified of TreeDisconnected <<{}>>", (Object)disconnectEvent.getTreeId());
            this.treeConnectTable.closed(disconnectEvent.getTreeId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void logoff() throws TransportException {
        try {
            logger.info("Logging off session {} from host {}", (Object)this.sessionId, (Object)this.connection.getRemoteHostname());
            for (Share share : this.treeConnectTable.getOpenTreeConnects()) {
                try {
                    share.close();
                }
                catch (IOException e) {
                    logger.error("Caught exception while closing TreeConnect with id: {}", (Object)share.getTreeConnect().getTreeId(), (Object)e);
                }
            }
            this.nestedSessionsRwLock.writeLock().lock();
            try {
                for (Session nestedSession : this.nestedSessionsByHost.values()) {
                    logger.info("Logging off nested session {} for session {}", (Object)nestedSession.getSessionId(), (Object)this.sessionId);
                    try {
                        nestedSession.logoff();
                    }
                    catch (TransportException te) {
                        logger.error("Caught exception while logging off nested session {}", (Object)nestedSession.getSessionId());
                    }
                }
            }
            finally {
                this.nestedSessionsRwLock.writeLock().unlock();
            }
            SMB2Logoff logoff = new SMB2Logoff(this.connection.getNegotiatedProtocol().getDialect(), this.sessionId);
            SMB2Logoff response = (SMB2Logoff)Futures.get(this.send(logoff), this.config.getTransactTimeout(), TimeUnit.MILLISECONDS, TransportException.Wrapper);
            if (!NtStatus.isSuccess(((SMB2PacketHeader)response.getHeader()).getStatusCode())) {
                throw new SMBApiException((SMB2PacketHeader)response.getHeader(), "Could not logoff session <<" + this.sessionId + ">>");
            }
        }
        finally {
            this.bus.publish(new SessionLoggedOff(this.sessionId));
        }
    }

    public boolean isSigningRequired() {
        return this.sessionContext.isSigningRequired();
    }

    public boolean isGuest() {
        return this.sessionContext.isGuest();
    }

    public boolean isAnonymous() {
        return this.sessionContext.isAnonymous();
    }

    @Override
    public void close() throws IOException {
        this.logoff();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public <T extends SMB2Packet> Future<T> send(SMB2Packet packet) throws TransportException {
        SecretKey signingKey = this.getSigningKey((SMB2PacketHeader)packet.getHeader(), true);
        if (this.sessionContext.isSigningRequired() && signingKey == null) {
            throw new TransportException("Message signing is required, but no signing key is negotiated");
        }
        if (this.shouldEncryptData()) {
            return this.connection.send(this.encryptor.encrypt(packet, this.sessionContext.getEncryptionKey()));
        }
        return this.connection.send(this.signatory.sign(packet, signingKey));
    }

    public <T extends SMB2Packet> T processSendResponse(SMB2CreateRequest packet) throws TransportException {
        Future<T> responseFuture = this.send(packet);
        return (T)((SMB2Packet)Futures.get(responseFuture, SMBRuntimeException.Wrapper));
    }

    public SecretKey getSigningKey(SMB2PacketHeader header, boolean signing) {
        if (this.connection.getNegotiatedProtocol().getDialect().isSmb3x()) {
            if (header.getMessage() == SMB2MessageCommandCode.SMB2_SESSION_SETUP) {
                if (signing) {
                    return this.sessionContext.getSigningKey();
                }
                if (header.getStatusCode() != NtStatus.STATUS_SUCCESS.getValue()) {
                    return this.sessionContext.getSigningKey();
                }
            }
            return this.sessionContext.getSigningKey();
        }
        return this.sessionContext.getSessionKey();
    }

    public boolean shouldEncryptData() throws TransportException {
        if (this.sessionContext.isEncryptData() && this.sessionContext.getEncryptionKey() == null) {
            throw new TransportException("Message encryption is required, but no encryption key is negotiated");
        }
        boolean encryptData = this.sessionContext.isEncryptData();
        return encryptData |= this.sessionContext.getEncryptionKey() != null && this.connection.getConnectionContext().clientPrefersEncryption();
    }

    public SessionContext getSessionContext() {
        return this.sessionContext;
    }

    public AuthenticationContext getAuthenticationContext() {
        return this.userCredentials;
    }
}

