/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection;

import com.hierynomus.mssmb.SMB1PacketFactory;
import com.hierynomus.mssmb2.SMB2MessageConverter;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketFactory;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.mssmb2.SMB3CompressedPacketFactory;
import com.hierynomus.mssmb2.SMB3EncryptedPacketFactory;
import com.hierynomus.mssmb2.messages.SMB2Cancel;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.protocol.commons.concurrent.AFuture;
import com.hierynomus.protocol.commons.concurrent.CancellableFuture;
import com.hierynomus.protocol.commons.concurrent.Futures;
import com.hierynomus.protocol.transport.PacketFactory;
import com.hierynomus.protocol.transport.PacketHandlers;
import com.hierynomus.protocol.transport.PacketReceiver;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.protocol.transport.TransportLayer;
import com.hierynomus.smb.SMBPacket;
import com.hierynomus.smb.SMBPacketData;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.common.Pooled;
import com.hierynomus.smbj.connection.ConnectionContext;
import com.hierynomus.smbj.connection.NegotiatedProtocol;
import com.hierynomus.smbj.connection.OutstandingRequests;
import com.hierynomus.smbj.connection.PacketEncryptor;
import com.hierynomus.smbj.connection.PacketSignatory;
import com.hierynomus.smbj.connection.Request;
import com.hierynomus.smbj.connection.SMBPacketSerializer;
import com.hierynomus.smbj.connection.SMBProtocolNegotiator;
import com.hierynomus.smbj.connection.SMBSessionBuilder;
import com.hierynomus.smbj.connection.SequenceWindow;
import com.hierynomus.smbj.connection.SessionTable;
import com.hierynomus.smbj.connection.packet.DeadLetterPacketHandler;
import com.hierynomus.smbj.connection.packet.IncomingPacketHandler;
import com.hierynomus.smbj.connection.packet.SMB1PacketHandler;
import com.hierynomus.smbj.connection.packet.SMB2AsyncResponsePacketHandler;
import com.hierynomus.smbj.connection.packet.SMB2CompoundedPacketHandler;
import com.hierynomus.smbj.connection.packet.SMB2CreditGrantingPacketHandler;
import com.hierynomus.smbj.connection.packet.SMB2IsOutstandingPacketHandler;
import com.hierynomus.smbj.connection.packet.SMB2ProcessResponsePacketHandler;
import com.hierynomus.smbj.connection.packet.SMB2SignatureVerificationPacketHandler;
import com.hierynomus.smbj.connection.packet.SMB3DecryptingPacketHandler;
import com.hierynomus.smbj.event.ConnectionClosed;
import com.hierynomus.smbj.event.SMBEventBus;
import com.hierynomus.smbj.event.SessionLoggedOff;
import com.hierynomus.smbj.paths.DFSPathResolver;
import com.hierynomus.smbj.paths.PathResolver;
import com.hierynomus.smbj.paths.SymlinkPathResolver;
import com.hierynomus.smbj.server.ServerList;
import com.hierynomus.smbj.session.Session;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import net.engio.mbassy.listener.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connection
extends Pooled<Connection>
implements Closeable,
PacketReceiver<SMBPacketData<?>> {
    private static final Logger logger = LoggerFactory.getLogger(Connection.class);
    private static final DelegatingSMBMessageConverter converter = new DelegatingSMBMessageConverter(new SMB3EncryptedPacketFactory(), new SMB3CompressedPacketFactory(), new SMB2PacketFactory(), new SMB1PacketFactory());
    private IncomingPacketHandler packetHandlerChain;
    private ConnectionContext connectionContext;
    private SessionTable sessionTable = new SessionTable();
    private SessionTable preauthSessionTable = new SessionTable();
    OutstandingRequests outstandingRequests = new OutstandingRequests();
    SequenceWindow sequenceWindow;
    private SMB2MessageConverter smb2Converter = new SMB2MessageConverter();
    private PathResolver pathResolver;
    private final SMBClient client;
    final ServerList serverList;
    private PacketSignatory signatory;
    private PacketEncryptor encryptor;
    private SmbConfig config;
    TransportLayer<SMBPacket<?, ?>> transport;
    private final SMBEventBus bus;
    private final ReentrantLock lock = new ReentrantLock();

    public SMBClient getClient() {
        return this.client;
    }

    public Connection(SmbConfig config, SMBClient client, SMBEventBus bus, ServerList serverList) {
        this.config = config;
        this.client = client;
        this.transport = config.getTransportLayerFactory().createTransportLayer(new PacketHandlers(new SMBPacketSerializer(), this, converter), config);
        this.bus = bus;
        this.serverList = serverList;
        this.init();
    }

    private void init() {
        this.bus.subscribe(this);
        this.sequenceWindow = new SequenceWindow();
        this.signatory = new PacketSignatory(this.config.getSecurityProvider());
        this.encryptor = new PacketEncryptor(this.config.getSecurityProvider());
        this.packetHandlerChain = new SMB3DecryptingPacketHandler(this.sessionTable, this.encryptor).setNext(new SMB2CompoundedPacketHandler().setNext(new SMB2IsOutstandingPacketHandler(this.outstandingRequests).setNext(new SMB2SignatureVerificationPacketHandler(this.sessionTable, this.signatory).setNext(new SMB2CreditGrantingPacketHandler(this.sequenceWindow).setNext(new SMB2AsyncResponsePacketHandler(this.outstandingRequests).setNext(new SMB2ProcessResponsePacketHandler(this.smb2Converter, this.outstandingRequests).setNext(new SMB1PacketHandler().setNext(new DeadLetterPacketHandler()))))))));
    }

    public Connection(Connection connection) {
        this.client = connection.client;
        this.config = connection.config;
        this.transport = connection.transport;
        this.bus = connection.bus;
        this.serverList = connection.serverList;
        this.init();
    }

    public void connect(String hostname, int port) throws IOException {
        if (this.isConnected()) {
            throw new IllegalStateException(String.format("This connection is already connected to %s", this.getRemoteHostname()));
        }
        this.transport.connect(new InetSocketAddress(hostname, port));
        this.connectionContext = new ConnectionContext(this.config.getClientGuid(), hostname, port, this.config);
        new SMBProtocolNegotiator(this, this.config, this.connectionContext).negotiateDialect();
        this.signatory.init();
        this.encryptor.init(this.connectionContext);
        this.pathResolver = new SymlinkPathResolver(PathResolver.LOCAL);
        if (this.config.isDfsEnabled() && this.connectionContext.supportsDFS()) {
            this.pathResolver = new DFSPathResolver(this.pathResolver, this.config.getTransactTimeout());
        }
        logger.info("Successfully connected to: {}", (Object)this.getRemoteHostname());
    }

    @Override
    public void close() throws IOException {
        this.close(false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void close(boolean force) throws IOException {
        if (!force && !this.release()) {
            return;
        }
        try {
            if (!force) {
                for (Session session : this.sessionTable.activeSessions()) {
                    try {
                        session.close();
                    }
                    catch (IOException e) {
                        logger.warn("Exception while closing session {}", (Object)session.getSessionId(), (Object)e);
                    }
                }
            }
        }
        finally {
            this.transport.disconnect();
            logger.info("Closed connection to {}", (Object)this.getRemoteHostname());
            this.bus.publish(new ConnectionClosed(this.connectionContext.getServer().getServerName(), this.connectionContext.getServer().getPort()));
        }
    }

    public Session authenticate(AuthenticationContext authContext) {
        return new SMBSessionBuilder(this, this.config, new SMBSessionBuilder.SessionFactory(){

            @Override
            public Session createSession(AuthenticationContext context) {
                return new Session(Connection.this, Connection.this.config, context, Connection.this.bus, Connection.this.pathResolver, Connection.this.signatory, Connection.this.encryptor);
            }
        }).establish(authContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T extends SMB2Packet> Future<T> send(SMB2Packet packet) throws TransportException {
        this.lock.lock();
        AFuture f = null;
        try {
            if (!(packet.getPacket() instanceof SMB2Cancel)) {
                int availableCredits = this.sequenceWindow.available();
                int grantCredits = this.calculateGrantedCredits(packet, availableCredits);
                if (availableCredits == 0) {
                    logger.warn("There are no credits left to send {}, will block until there are more credits available.", (Object)((SMB2PacketHeader)packet.getHeader()).getMessage());
                }
                long[] messageIds = this.sequenceWindow.get(grantCredits);
                ((SMB2PacketHeader)packet.getHeader()).setMessageId(messageIds[0]);
                logger.debug("Granted {} (out of {}) credits to {}", grantCredits, availableCredits, packet);
                ((SMB2PacketHeader)packet.getHeader()).setCreditRequest(Math.max(512 - availableCredits - grantCredits, grantCredits));
                Request request = new Request(packet.getPacket(), messageIds[0], UUID.randomUUID());
                this.outstandingRequests.registerOutstanding(request);
                f = request.getFuture(new CancelRequest(request, ((SMB2PacketHeader)packet.getHeader()).getSessionId()));
            }
            this.transport.write(packet);
            AFuture aFuture = f;
            return aFuture;
        }
        finally {
            this.lock.unlock();
        }
    }

    <T extends SMB2Packet> T sendAndReceive(SMB2Packet packet) throws TransportException {
        return (T)((SMB2Packet)Futures.get(this.send(packet), this.config.getTransactTimeout(), TimeUnit.MILLISECONDS, TransportException.Wrapper));
    }

    private int calculateGrantedCredits(SMB2Packet packet, int availableCredits) {
        int grantCredits;
        int maxPayloadSize = packet.getMaxPayloadSize();
        int creditsNeeded = this.creditsNeeded(maxPayloadSize);
        if (creditsNeeded > 1 && !this.connectionContext.supportsMultiCredit()) {
            logger.trace("Connection to {} does not support multi-credit requests.", (Object)this.getRemoteHostname());
            grantCredits = 1;
        } else {
            grantCredits = creditsNeeded < availableCredits ? creditsNeeded : (creditsNeeded > 1 && availableCredits > 1 ? availableCredits - 1 : 1);
        }
        packet.setCreditsAssigned(grantCredits);
        return grantCredits;
    }

    private int creditsNeeded(int payloadSize) {
        return Math.abs((payloadSize - 1) / 65536) + 1;
    }

    public NegotiatedProtocol getNegotiatedProtocol() {
        return this.connectionContext.getNegotiatedProtocol();
    }

    @Override
    public void handle(SMBPacketData<?> uncheckedPacket) throws TransportException {
        this.packetHandlerChain.handle(uncheckedPacket);
    }

    @Override
    public void handleError(Throwable t2) {
        this.outstandingRequests.handleError(t2);
        try {
            this.close();
        }
        catch (Exception e) {
            String exceptionClass = e.getClass().getSimpleName();
            logger.debug("{} while closing connection on error, ignoring: {}", (Object)exceptionClass, (Object)e.getMessage());
        }
    }

    public String getRemoteHostname() {
        return this.connectionContext.getServer().getServerName();
    }

    public boolean isConnected() {
        return this.transport.isConnected();
    }

    public ConnectionContext getConnectionContext() {
        return this.connectionContext;
    }

    @Handler
    private void sessionLogoff(SessionLoggedOff loggedOff) {
        this.sessionTable.removeSession(loggedOff.getSessionId());
        logger.debug("Session << {} >> logged off", (Object)loggedOff.getSessionId());
    }

    SessionTable getSessionTable() {
        return this.sessionTable;
    }

    SessionTable getPreauthSessionTable() {
        return this.preauthSessionTable;
    }

    private class CancelRequest
    implements CancellableFuture.CancelCallback {
        private Request request;
        private long sessionId;

        public CancelRequest(Request request, long sessionId) {
            this.request = request;
            this.sessionId = sessionId;
        }

        @Override
        public void cancel() {
            SMB2Cancel cancel = new SMB2Cancel(Connection.this.connectionContext.getNegotiatedProtocol().getDialect(), this.sessionId, this.request.getMessageId(), this.request.getAsyncId());
            try {
                Connection.this.sessionTable.find(this.sessionId).send(cancel);
            }
            catch (TransportException e) {
                logger.error("Failed to send {}", (Object)cancel);
            }
        }
    }

    private static class DelegatingSMBMessageConverter
    implements PacketFactory<SMBPacketData<?>> {
        private PacketFactory<?>[] packetFactories;

        public DelegatingSMBMessageConverter(PacketFactory<?> ... packetFactories) {
            this.packetFactories = packetFactories;
        }

        @Override
        public SMBPacketData<?> read(byte[] data) throws Buffer.BufferException, IOException {
            for (PacketFactory<?> packetFactory : this.packetFactories) {
                if (!packetFactory.canHandle(data)) continue;
                return (SMBPacketData)packetFactory.read(data);
            }
            throw new IOException("Unknown packet format received.");
        }

        @Override
        public boolean canHandle(byte[] data) {
            for (PacketFactory<?> packetFactory : this.packetFactories) {
                if (!packetFactory.canHandle(data)) continue;
                return true;
            }
            return false;
        }
    }
}

