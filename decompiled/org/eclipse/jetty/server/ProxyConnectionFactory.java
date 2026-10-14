/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ReadPendingException;
import java.nio.channels.WritePendingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.AbstractConnectionFactory;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.DetectorConnectionFactory;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.TypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyConnectionFactory
extends DetectorConnectionFactory {
    public static final String TLS_VERSION = "TLS_VERSION";
    private static final Logger LOG = LoggerFactory.getLogger(ProxyConnectionFactory.class);

    public ProxyConnectionFactory() {
        this((String)null);
    }

    public ProxyConnectionFactory(String nextProtocol) {
        super(new ProxyV1ConnectionFactory(nextProtocol), new ProxyV2ConnectionFactory(nextProtocol));
    }

    private static ConnectionFactory findNextConnectionFactory(String nextProtocol, Connector connector, String currentProtocol, EndPoint endp) {
        currentProtocol = "[" + (String)currentProtocol + "]";
        if (LOG.isDebugEnabled()) {
            LOG.debug("finding connection factory following {} for protocol {}", currentProtocol, (Object)nextProtocol);
        }
        String nextProtocolToFind = nextProtocol;
        if (nextProtocol == null) {
            nextProtocolToFind = AbstractConnectionFactory.findNextProtocol(connector, (String)currentProtocol);
        }
        if (nextProtocolToFind == null) {
            throw new IllegalStateException("Cannot find protocol following '" + (String)currentProtocol + "' in connector's protocol list " + connector.getProtocols() + " for " + endp);
        }
        ConnectionFactory connectionFactory = connector.getConnectionFactory(nextProtocolToFind);
        if (connectionFactory == null) {
            throw new IllegalStateException("Cannot find protocol '" + nextProtocol + "' in connector's protocol list " + connector.getProtocols() + " for " + endp);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("found next connection factory {} for protocol {}", (Object)connectionFactory, (Object)nextProtocol);
        }
        return connectionFactory;
    }

    public int getMaxProxyHeader() {
        ProxyV2ConnectionFactory v2 = this.getBean(ProxyV2ConnectionFactory.class);
        return v2.getMaxProxyHeader();
    }

    public void setMaxProxyHeader(int maxProxyHeader) {
        ProxyV2ConnectionFactory v2 = this.getBean(ProxyV2ConnectionFactory.class);
        v2.setMaxProxyHeader(maxProxyHeader);
    }

    private static class ProxyV1ConnectionFactory
    extends AbstractConnectionFactory
    implements ConnectionFactory.Detecting {
        private static final byte[] SIGNATURE = "PROXY".getBytes(StandardCharsets.US_ASCII);
        private final String _nextProtocol;

        private ProxyV1ConnectionFactory(String nextProtocol) {
            super("proxy");
            this._nextProtocol = nextProtocol;
        }

        @Override
        public ConnectionFactory.Detecting.Detection detect(ByteBuffer buffer) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Proxy v1 attempting detection with {} bytes", (Object)buffer.remaining());
            }
            if (buffer.remaining() < SIGNATURE.length) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v1 detection requires more bytes");
                }
                return ConnectionFactory.Detecting.Detection.NEED_MORE_BYTES;
            }
            for (int i = 0; i < SIGNATURE.length; ++i) {
                byte signatureByte = SIGNATURE[i];
                byte byteInBuffer = buffer.get(i);
                if (byteInBuffer == signatureByte) continue;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v1 detection unsuccessful");
                }
                return ConnectionFactory.Detecting.Detection.NOT_RECOGNIZED;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Proxy v1 detection succeeded");
            }
            return ConnectionFactory.Detecting.Detection.RECOGNIZED;
        }

        @Override
        public Connection newConnection(Connector connector, EndPoint endp) {
            ConnectionFactory nextConnectionFactory = ProxyConnectionFactory.findNextConnectionFactory(this._nextProtocol, connector, this.getProtocol(), endp);
            return this.configure(new ProxyProtocolV1Connection(endp, connector, nextConnectionFactory), connector, endp);
        }

        private static class ProxyProtocolV1Connection
        extends AbstractConnection
        implements Connection.UpgradeFrom,
        Connection.UpgradeTo {
            private static final int CR_INDEX = 6;
            private static final int LF_INDEX = 7;
            private final Connector _connector;
            private final ConnectionFactory _next;
            private final ByteBuffer _buffer;
            private final StringBuilder _builder = new StringBuilder();
            private final String[] _fields = new String[6];
            private int _index;
            private int _length;

            private ProxyProtocolV1Connection(EndPoint endp, Connector connector, ConnectionFactory next) {
                super(endp, connector.getExecutor());
                this._connector = connector;
                this._next = next;
                this._buffer = this._connector.getByteBufferPool().acquire(this.getInputBufferSize(), true);
            }

            @Override
            public void onFillable() {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v1 onFillable current index = {}", (Object)this._index);
                }
                try {
                    while (this._index < 7) {
                        int fill = this.getEndPoint().fill(this._buffer);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Proxy v1 filled buffer with {} bytes", (Object)fill);
                        }
                        if (fill < 0) {
                            this._connector.getByteBufferPool().release(this._buffer);
                            this.getEndPoint().shutdownOutput();
                            return;
                        }
                        if (fill == 0) {
                            this.fillInterested();
                            return;
                        }
                        if (!this.parse()) continue;
                        break;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Proxy v1 onFillable parsing done, now upgrading");
                    }
                    this.upgrade();
                }
                catch (Throwable x) {
                    LOG.warn("Proxy v1 error for {}", (Object)this.getEndPoint(), (Object)x);
                    this.releaseAndClose();
                }
            }

            @Override
            public void onOpen() {
                super.onOpen();
                try {
                    while (this._index < 7) {
                        if (this.parse()) continue;
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Proxy v1 onOpen parsing ran out of bytes, marking as fillInterested");
                        }
                        this.fillInterested();
                        return;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Proxy v1 onOpen parsing done, now upgrading");
                    }
                    this.upgrade();
                }
                catch (Throwable x) {
                    LOG.warn("Proxy v1 error for {}", (Object)this.getEndPoint(), (Object)x);
                    this.releaseAndClose();
                }
            }

            @Override
            public ByteBuffer onUpgradeFrom() {
                if (this._buffer.hasRemaining()) {
                    ByteBuffer unconsumed = ByteBuffer.allocateDirect(this._buffer.remaining());
                    unconsumed.put(this._buffer);
                    unconsumed.flip();
                    this._connector.getByteBufferPool().release(this._buffer);
                    return unconsumed;
                }
                return null;
            }

            @Override
            public void onUpgradeTo(ByteBuffer buffer) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v1 copying unconsumed buffer {}", (Object)BufferUtil.toDetailString(buffer));
                }
                BufferUtil.append(this._buffer, buffer);
            }

            private boolean parse() throws IOException {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v1 parsing {}", (Object)BufferUtil.toDetailString(this._buffer));
                }
                this._length += this._buffer.remaining();
                while (this._buffer.hasRemaining()) {
                    byte b = this._buffer.get();
                    if (this._index < 6) {
                        if (b == 32 || b == 13) {
                            this._fields[this._index++] = this._builder.toString();
                            this._builder.setLength(0);
                            if (b != 13) continue;
                            this._index = 6;
                            continue;
                        }
                        if (b < 32) {
                            throw new IOException("Proxy v1 bad character " + (b & 0xFF));
                        }
                        this._builder.append((char)b);
                        continue;
                    }
                    if (b == 10) {
                        this._index = 7;
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Proxy v1 parsing is done");
                        }
                        return true;
                    }
                    throw new IOException("Proxy v1 bad CRLF " + (b & 0xFF));
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v1 parsing requires more bytes");
                }
                return false;
            }

            private void releaseAndClose() {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v1 releasing buffer and closing");
                }
                this._connector.getByteBufferPool().release(this._buffer);
                this.close();
            }

            private void upgrade() {
                ProxyEndPoint proxyEndPoint;
                int proxyLineLength = this._length - this._buffer.remaining();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v1 pre-upgrade packet length (including CRLF) is {}", (Object)proxyLineLength);
                }
                if (proxyLineLength >= 110) {
                    LOG.warn("Proxy v1 PROXY line too long {} for {}", (Object)proxyLineLength, (Object)this.getEndPoint());
                    this.releaseAndClose();
                    return;
                }
                if (!"PROXY".equals(this._fields[0])) {
                    LOG.warn("Proxy v1 not PROXY protocol for {}", (Object)this.getEndPoint());
                    this.releaseAndClose();
                    return;
                }
                String srcIP = this._fields[2];
                String srcPort = this._fields[4];
                String dstIP = this._fields[3];
                String dstPort = this._fields[5];
                boolean unknown = "UNKNOWN".equalsIgnoreCase(this._fields[1]);
                if (unknown) {
                    EndPoint endPoint = this.getEndPoint();
                    proxyEndPoint = new ProxyEndPoint(endPoint, endPoint.getLocalSocketAddress(), endPoint.getRemoteSocketAddress());
                } else {
                    InetSocketAddress remote = new InetSocketAddress(srcIP, Integer.parseInt(srcPort));
                    InetSocketAddress local = new InetSocketAddress(dstIP, Integer.parseInt(dstPort));
                    proxyEndPoint = new ProxyEndPoint(this.getEndPoint(), (SocketAddress)local, (SocketAddress)remote);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v1 next protocol '{}' for {} -> {}", this._next, this.getEndPoint(), proxyEndPoint);
                }
                DetectorConnectionFactory.upgradeToConnectionFactory(this._next, this._connector, proxyEndPoint);
            }
        }
    }

    private static class ProxyV2ConnectionFactory
    extends AbstractConnectionFactory
    implements ConnectionFactory.Detecting {
        private static final byte[] SIGNATURE = new byte[]{13, 10, 13, 10, 0, 13, 10, 81, 85, 73, 84, 10};
        private final String _nextProtocol;
        private int _maxProxyHeader = 1024;

        private ProxyV2ConnectionFactory(String nextProtocol) {
            super("proxy");
            this._nextProtocol = nextProtocol;
        }

        @Override
        public ConnectionFactory.Detecting.Detection detect(ByteBuffer buffer) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Proxy v2 attempting detection with {} bytes", (Object)buffer.remaining());
            }
            if (buffer.remaining() < SIGNATURE.length) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v2 detection requires more bytes");
                }
                return ConnectionFactory.Detecting.Detection.NEED_MORE_BYTES;
            }
            for (int i = 0; i < SIGNATURE.length; ++i) {
                byte signatureByte = SIGNATURE[i];
                byte byteInBuffer = buffer.get(i);
                if (byteInBuffer == signatureByte) continue;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v2 detection unsuccessful");
                }
                return ConnectionFactory.Detecting.Detection.NOT_RECOGNIZED;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Proxy v2 detection succeeded");
            }
            return ConnectionFactory.Detecting.Detection.RECOGNIZED;
        }

        public int getMaxProxyHeader() {
            return this._maxProxyHeader;
        }

        public void setMaxProxyHeader(int maxProxyHeader) {
            this._maxProxyHeader = maxProxyHeader;
        }

        @Override
        public Connection newConnection(Connector connector, EndPoint endp) {
            ConnectionFactory nextConnectionFactory = ProxyConnectionFactory.findNextConnectionFactory(this._nextProtocol, connector, this.getProtocol(), endp);
            return this.configure(new ProxyProtocolV2Connection(endp, connector, nextConnectionFactory), connector, endp);
        }

        private class ProxyProtocolV2Connection
        extends AbstractConnection
        implements Connection.UpgradeFrom,
        Connection.UpgradeTo {
            private static final int HEADER_LENGTH = 16;
            private final Connector _connector;
            private final ConnectionFactory _next;
            private final ByteBuffer _buffer;
            private boolean _local;
            private Family _family;
            private int _length;
            private boolean _headerParsed;

            protected ProxyProtocolV2Connection(EndPoint endp, Connector connector, ConnectionFactory next) {
                super(endp, connector.getExecutor());
                this._connector = connector;
                this._next = next;
                this._buffer = this._connector.getByteBufferPool().acquire(this.getInputBufferSize(), true);
            }

            @Override
            public void onUpgradeTo(ByteBuffer buffer) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v2 copying unconsumed buffer {}", (Object)BufferUtil.toDetailString(buffer));
                }
                BufferUtil.append(this._buffer, buffer);
            }

            @Override
            public void onOpen() {
                super.onOpen();
                try {
                    this.parseHeader();
                    if (this._headerParsed && this._buffer.remaining() >= this._length) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Proxy v2 onOpen parsing fixed length packet part done, now upgrading");
                        }
                        this.parseBodyAndUpgrade();
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Proxy v2 onOpen parsing fixed length packet ran out of bytes, marking as fillInterested");
                        }
                        this.fillInterested();
                    }
                }
                catch (Exception x) {
                    LOG.warn("Proxy v2 error for {}", (Object)this.getEndPoint(), (Object)x);
                    this.releaseAndClose();
                }
            }

            @Override
            public void onFillable() {
                try {
                    int fill;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Proxy v2 onFillable header parsed? {}", (Object)this._headerParsed);
                    }
                    while (!this._headerParsed) {
                        fill = this.getEndPoint().fill(this._buffer);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Proxy v2 filled buffer with {} bytes", (Object)fill);
                        }
                        if (fill < 0) {
                            this._connector.getByteBufferPool().release(this._buffer);
                            this.getEndPoint().shutdownOutput();
                            return;
                        }
                        if (fill == 0) {
                            this.fillInterested();
                            return;
                        }
                        this.parseHeader();
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Proxy v2 onFillable header parsed, length = {}, buffer = {}", (Object)this._length, (Object)BufferUtil.toDetailString(this._buffer));
                    }
                    while (this._buffer.remaining() < this._length) {
                        fill = this.getEndPoint().fill(this._buffer);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Proxy v2 filled buffer with {} bytes", (Object)fill);
                        }
                        if (fill < 0) {
                            this._connector.getByteBufferPool().release(this._buffer);
                            this.getEndPoint().shutdownOutput();
                            return;
                        }
                        if (fill != 0) continue;
                        this.fillInterested();
                        return;
                    }
                    this.parseBodyAndUpgrade();
                }
                catch (Throwable x) {
                    LOG.warn("Proxy v2 error for {}", (Object)this.getEndPoint(), (Object)x);
                    this.releaseAndClose();
                }
            }

            @Override
            public ByteBuffer onUpgradeFrom() {
                if (this._buffer.hasRemaining()) {
                    ByteBuffer unconsumed = ByteBuffer.allocateDirect(this._buffer.remaining());
                    unconsumed.put(this._buffer);
                    unconsumed.flip();
                    this._connector.getByteBufferPool().release(this._buffer);
                    return unconsumed;
                }
                return null;
            }

            private void parseBodyAndUpgrade() throws IOException {
                ProxyEndPoint proxyEndPoint;
                int nonProxyRemaining = this._buffer.remaining() - this._length;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v2 parsing body, length = {}, buffer = {}", (Object)this._length, (Object)BufferUtil.toHexSummary(this._buffer));
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v2 body {} from {} for {}", this._next, BufferUtil.toHexSummary(this._buffer), this);
                }
                EndPoint endPoint = this.getEndPoint();
                if (this._local) {
                    this._buffer.position(this._buffer.position() + this._length);
                    proxyEndPoint = new ProxyEndPoint(endPoint, endPoint.getLocalSocketAddress(), endPoint.getRemoteSocketAddress());
                } else {
                    SocketAddress remote;
                    SocketAddress local;
                    switch (this._family) {
                        case INET: {
                            byte[] addr = new byte[4];
                            this._buffer.get(addr);
                            InetAddress srcAddr = Inet4Address.getByAddress(addr);
                            this._buffer.get(addr);
                            InetAddress dstAddr = Inet4Address.getByAddress(addr);
                            char srcPort = this._buffer.getChar();
                            char dstPort = this._buffer.getChar();
                            local = new InetSocketAddress(dstAddr, (int)dstPort);
                            remote = new InetSocketAddress(srcAddr, (int)srcPort);
                            break;
                        }
                        case INET6: {
                            byte[] addr = new byte[16];
                            this._buffer.get(addr);
                            InetAddress srcAddr = Inet6Address.getByAddress(addr);
                            this._buffer.get(addr);
                            InetAddress dstAddr = Inet6Address.getByAddress(addr);
                            char srcPort = this._buffer.getChar();
                            char dstPort = this._buffer.getChar();
                            local = new InetSocketAddress(dstAddr, (int)dstPort);
                            remote = new InetSocketAddress(srcAddr, (int)srcPort);
                            break;
                        }
                        case UNIX: {
                            byte[] addr = new byte[108];
                            this._buffer.get(addr);
                            String src = UnixDomain.toPath(addr);
                            this._buffer.get(addr);
                            String dst = UnixDomain.toPath(addr);
                            local = UnixDomain.newSocketAddress(dst);
                            remote = UnixDomain.newSocketAddress(src);
                            break;
                        }
                        default: {
                            throw new IllegalStateException("Unsupported family " + this._family);
                        }
                    }
                    proxyEndPoint = new ProxyEndPoint(endPoint, local, remote);
                    while (this._buffer.remaining() > nonProxyRemaining) {
                        int subLength;
                        int client;
                        int type = 0xFF & this._buffer.get();
                        int length = this._buffer.getChar();
                        byte[] value = new byte[length];
                        this._buffer.get(value);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(String.format("Proxy v2 T=%x L=%d V=%s for %s", type, length, TypeUtil.toHexString(value), this));
                        }
                        if (type != 4) {
                            proxyEndPoint.putTLV(type, value);
                        }
                        if (type != 32 || (client = value[0] & 0xFF) != 1) continue;
                        for (int i = 5; i < length; i += subLength) {
                            int subType = value[i++] & 0xFF;
                            subLength = (value[i++] & 0xFF) * 256 + (value[i++] & 0xFF);
                            byte[] subValue = new byte[subLength];
                            System.arraycopy(value, i, subValue, 0, subLength);
                            if (subType != 33) continue;
                            String tlsVersion = new String(subValue, StandardCharsets.US_ASCII);
                            proxyEndPoint.setAttribute(ProxyConnectionFactory.TLS_VERSION, tlsVersion);
                        }
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Proxy v2 {} {}", (Object)endPoint, (Object)proxyEndPoint);
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v2 parsing dynamic packet part is now done, upgrading to {}", (Object)ProxyV2ConnectionFactory.this._nextProtocol);
                }
                DetectorConnectionFactory.upgradeToConnectionFactory(this._next, this._connector, proxyEndPoint);
            }

            private void parseHeader() throws IOException {
                Transport transport;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v2 parsing fixed length packet part, buffer = {}", (Object)BufferUtil.toDetailString(this._buffer));
                }
                if (this._buffer.remaining() < 16) {
                    return;
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v2 header {} for {}", (Object)BufferUtil.toHexSummary(this._buffer), (Object)this);
                }
                for (byte signatureByte : SIGNATURE) {
                    if (this._buffer.get() == signatureByte) continue;
                    throw new IOException("Proxy v2 bad PROXY signature");
                }
                int versionAndCommand = 0xFF & this._buffer.get();
                if ((versionAndCommand & 0xF0) != 32) {
                    throw new IOException("Proxy v2 bad PROXY version");
                }
                this._local = (versionAndCommand & 0xF) == 0;
                int transportAndFamily = 0xFF & this._buffer.get();
                switch (transportAndFamily >> 4) {
                    case 0: {
                        this._family = Family.UNSPEC;
                        break;
                    }
                    case 1: {
                        this._family = Family.INET;
                        break;
                    }
                    case 2: {
                        this._family = Family.INET6;
                        break;
                    }
                    case 3: {
                        this._family = Family.UNIX;
                        break;
                    }
                    default: {
                        throw new IOException("Proxy v2 bad PROXY family");
                    }
                }
                switch (transportAndFamily & 0xF) {
                    case 0: {
                        transport = Transport.UNSPEC;
                        break;
                    }
                    case 1: {
                        transport = Transport.STREAM;
                        break;
                    }
                    case 2: {
                        transport = Transport.DGRAM;
                        break;
                    }
                    default: {
                        throw new IOException("Proxy v2 bad PROXY family");
                    }
                }
                this._length = this._buffer.getChar();
                if (!(this._local || this._family != Family.UNSPEC && transport == Transport.STREAM)) {
                    throw new IOException(String.format("Proxy v2 unsupported PROXY mode 0x%x,0x%x", versionAndCommand, transportAndFamily));
                }
                if (this._length > ProxyV2ConnectionFactory.this.getMaxProxyHeader()) {
                    throw new IOException(String.format("Proxy v2 Unsupported PROXY mode 0x%x,0x%x,0x%x", versionAndCommand, transportAndFamily, this._length));
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proxy v2 fixed length packet part is now parsed");
                }
                this._headerParsed = true;
            }

            private void releaseAndClose() {
                this._connector.getByteBufferPool().release(this._buffer);
                this.close();
            }
        }

        private static enum Transport {
            UNSPEC,
            STREAM,
            DGRAM;

        }

        private static enum Family {
            UNSPEC,
            INET,
            INET6,
            UNIX;

        }
    }

    private static class UnixDomain {
        private static final Class<?> unixDomainSocketAddress = UnixDomain.probe();

        private UnixDomain() {
        }

        private static Class<?> probe() {
            try {
                return ClassLoader.getPlatformClassLoader().loadClass("java.net.UnixDomainSocketAddress");
            }
            catch (Throwable ignored) {
                return null;
            }
        }

        private static SocketAddress newSocketAddress(String path) {
            try {
                if (unixDomainSocketAddress != null) {
                    return (SocketAddress)unixDomainSocketAddress.getMethod("of", String.class).invoke(null, path);
                }
                return null;
            }
            catch (Throwable ignored) {
                return null;
            }
        }

        private static String toPath(byte[] bytes) {
            int i = 0;
            while (i < bytes.length && bytes[i++] != 0) {
            }
            return new String(bytes, 0, i, StandardCharsets.US_ASCII).trim();
        }
    }

    public static class ProxyEndPoint
    extends AttributesMap
    implements EndPoint,
    EndPoint.Wrapper {
        private static final int PP2_TYPE_NOOP = 4;
        private static final int PP2_TYPE_SSL = 32;
        private static final int PP2_TYPE_SSL_PP2_CLIENT_SSL = 1;
        private static final int PP2_SUBTYPE_SSL_VERSION = 33;
        private final EndPoint _endPoint;
        private final SocketAddress _local;
        private final SocketAddress _remote;
        private Map<Integer, byte[]> _tlvs;

        @Deprecated
        public ProxyEndPoint(EndPoint endPoint, InetSocketAddress remote, InetSocketAddress local) {
            this(endPoint, (SocketAddress)local, (SocketAddress)remote);
        }

        public ProxyEndPoint(EndPoint endPoint, SocketAddress local, SocketAddress remote) {
            this._endPoint = endPoint;
            this._local = local;
            this._remote = remote;
        }

        @Override
        public EndPoint unwrap() {
            return this._endPoint;
        }

        private void putTLV(int type, byte[] value) {
            if (this._tlvs == null) {
                this._tlvs = new HashMap<Integer, byte[]>();
            }
            this._tlvs.put(type, value);
        }

        public byte[] getTLV(int type) {
            return this._tlvs != null ? this._tlvs.get(type) : null;
        }

        @Override
        public void close(Throwable cause) {
            this._endPoint.close(cause);
        }

        @Override
        public int fill(ByteBuffer buffer) throws IOException {
            return this._endPoint.fill(buffer);
        }

        @Override
        public void fillInterested(Callback callback) throws ReadPendingException {
            this._endPoint.fillInterested(callback);
        }

        @Override
        public boolean flush(ByteBuffer ... buffer) throws IOException {
            return this._endPoint.flush(buffer);
        }

        @Override
        public Connection getConnection() {
            return this._endPoint.getConnection();
        }

        @Override
        public void setConnection(Connection connection) {
            this._endPoint.setConnection(connection);
        }

        @Override
        public long getCreatedTimeStamp() {
            return this._endPoint.getCreatedTimeStamp();
        }

        @Override
        public long getIdleTimeout() {
            return this._endPoint.getIdleTimeout();
        }

        @Override
        public void setIdleTimeout(long idleTimeout) {
            this._endPoint.setIdleTimeout(idleTimeout);
        }

        @Override
        public InetSocketAddress getLocalAddress() {
            SocketAddress local = this.getLocalSocketAddress();
            if (local instanceof InetSocketAddress) {
                return (InetSocketAddress)local;
            }
            return null;
        }

        @Override
        public SocketAddress getLocalSocketAddress() {
            return this._local;
        }

        @Override
        public InetSocketAddress getRemoteAddress() {
            SocketAddress remote = this.getRemoteSocketAddress();
            if (remote instanceof InetSocketAddress) {
                return (InetSocketAddress)remote;
            }
            return null;
        }

        @Override
        public SocketAddress getRemoteSocketAddress() {
            return this._remote;
        }

        @Override
        public Object getTransport() {
            return this._endPoint.getTransport();
        }

        @Override
        public boolean isFillInterested() {
            return this._endPoint.isFillInterested();
        }

        @Override
        public boolean isInputShutdown() {
            return this._endPoint.isInputShutdown();
        }

        @Override
        public boolean isOpen() {
            return this._endPoint.isOpen();
        }

        @Override
        public boolean isOutputShutdown() {
            return this._endPoint.isOutputShutdown();
        }

        @Override
        public void onClose(Throwable cause) {
            this._endPoint.onClose(cause);
        }

        @Override
        public void onOpen() {
            this._endPoint.onOpen();
        }

        @Override
        public void shutdownOutput() {
            this._endPoint.shutdownOutput();
        }

        @Override
        public String toString() {
            return String.format("%s@%x[remote=%s,local=%s,endpoint=%s]", this.getClass().getSimpleName(), this.hashCode(), this._remote, this._local, this._endPoint);
        }

        @Override
        public boolean tryFillInterested(Callback callback) {
            return this._endPoint.tryFillInterested(callback);
        }

        @Override
        public void upgrade(Connection newConnection) {
            this._endPoint.upgrade(newConnection);
        }

        @Override
        public void write(Callback callback, ByteBuffer ... buffers) throws WritePendingException {
            this._endPoint.write(callback, buffers);
        }
    }
}

