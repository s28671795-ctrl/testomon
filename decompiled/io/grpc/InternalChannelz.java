/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.grpc.ConnectivityState;
import io.grpc.Internal;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.InternalWithLogId;
import java.net.SocketAddress;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

@Internal
public final class InternalChannelz {
    private static final Logger log = Logger.getLogger(InternalChannelz.class.getName());
    private static final InternalChannelz INSTANCE = new InternalChannelz();
    private final ConcurrentNavigableMap<Long, InternalInstrumented<ServerStats>> servers = new ConcurrentSkipListMap<Long, InternalInstrumented<ServerStats>>();
    private final ConcurrentNavigableMap<Long, InternalInstrumented<ChannelStats>> rootChannels = new ConcurrentSkipListMap<Long, InternalInstrumented<ChannelStats>>();
    private final ConcurrentMap<Long, InternalInstrumented<ChannelStats>> subchannels = new ConcurrentHashMap<Long, InternalInstrumented<ChannelStats>>();
    private final ConcurrentMap<Long, InternalInstrumented<SocketStats>> otherSockets = new ConcurrentHashMap<Long, InternalInstrumented<SocketStats>>();
    private final ConcurrentMap<Long, ServerSocketMap> perServerSockets = new ConcurrentHashMap<Long, ServerSocketMap>();

    @VisibleForTesting
    public InternalChannelz() {
    }

    public static InternalChannelz instance() {
        return INSTANCE;
    }

    public void addServer(InternalInstrumented<ServerStats> server) {
        ServerSocketMap prev = this.perServerSockets.put(InternalChannelz.id(server), new ServerSocketMap());
        assert (prev == null);
        InternalChannelz.add(this.servers, server);
    }

    public void addSubchannel(InternalInstrumented<ChannelStats> subchannel) {
        InternalChannelz.add(this.subchannels, subchannel);
    }

    public void addRootChannel(InternalInstrumented<ChannelStats> rootChannel) {
        InternalChannelz.add(this.rootChannels, rootChannel);
    }

    public void addClientSocket(InternalInstrumented<SocketStats> socket) {
        InternalChannelz.add(this.otherSockets, socket);
    }

    public void addListenSocket(InternalInstrumented<SocketStats> socket) {
        InternalChannelz.add(this.otherSockets, socket);
    }

    public void addServerSocket(InternalInstrumented<ServerStats> server, InternalInstrumented<SocketStats> socket) {
        ServerSocketMap serverSockets = (ServerSocketMap)this.perServerSockets.get(InternalChannelz.id(server));
        assert (serverSockets != null);
        InternalChannelz.add(serverSockets, socket);
    }

    public void removeServer(InternalInstrumented<ServerStats> server) {
        InternalChannelz.remove(this.servers, server);
        ServerSocketMap prev = (ServerSocketMap)this.perServerSockets.remove(InternalChannelz.id(server));
        assert (prev != null);
        assert (prev.isEmpty());
    }

    public void removeSubchannel(InternalInstrumented<ChannelStats> subchannel) {
        InternalChannelz.remove(this.subchannels, subchannel);
    }

    public void removeRootChannel(InternalInstrumented<ChannelStats> channel) {
        InternalChannelz.remove(this.rootChannels, channel);
    }

    public void removeClientSocket(InternalInstrumented<SocketStats> socket) {
        InternalChannelz.remove(this.otherSockets, socket);
    }

    public void removeListenSocket(InternalInstrumented<SocketStats> socket) {
        InternalChannelz.remove(this.otherSockets, socket);
    }

    public void removeServerSocket(InternalInstrumented<ServerStats> server, InternalInstrumented<SocketStats> socket) {
        ServerSocketMap socketsOfServer = (ServerSocketMap)this.perServerSockets.get(InternalChannelz.id(server));
        assert (socketsOfServer != null);
        InternalChannelz.remove(socketsOfServer, socket);
    }

    public RootChannelList getRootChannels(long fromId, int maxPageSize) {
        ArrayList<InternalInstrumented<ChannelStats>> channelList = new ArrayList<InternalInstrumented<ChannelStats>>();
        Iterator iterator2 = this.rootChannels.tailMap((Object)fromId).values().iterator();
        while (iterator2.hasNext() && channelList.size() < maxPageSize) {
            channelList.add((InternalInstrumented)iterator2.next());
        }
        return new RootChannelList(channelList, !iterator2.hasNext());
    }

    @Nullable
    public InternalInstrumented<ChannelStats> getChannel(long id) {
        return (InternalInstrumented)this.rootChannels.get(id);
    }

    @Nullable
    public InternalInstrumented<ChannelStats> getSubchannel(long id) {
        return (InternalInstrumented)this.subchannels.get(id);
    }

    public ServerList getServers(long fromId, int maxPageSize) {
        ArrayList<InternalInstrumented<ServerStats>> serverList = new ArrayList<InternalInstrumented<ServerStats>>(maxPageSize);
        Iterator iterator2 = this.servers.tailMap((Object)fromId).values().iterator();
        while (iterator2.hasNext() && serverList.size() < maxPageSize) {
            serverList.add((InternalInstrumented)iterator2.next());
        }
        return new ServerList(serverList, !iterator2.hasNext());
    }

    @Nullable
    public InternalInstrumented<ServerStats> getServer(long id) {
        return (InternalInstrumented)this.servers.get(id);
    }

    @Nullable
    public ServerSocketsList getServerSockets(long serverId, long fromId, int maxPageSize) {
        ServerSocketMap serverSockets = (ServerSocketMap)this.perServerSockets.get(serverId);
        if (serverSockets == null) {
            return null;
        }
        ArrayList<InternalWithLogId> socketList = new ArrayList<InternalWithLogId>(maxPageSize);
        Iterator iterator2 = serverSockets.tailMap((Object)fromId).values().iterator();
        while (socketList.size() < maxPageSize && iterator2.hasNext()) {
            socketList.add((InternalWithLogId)iterator2.next());
        }
        return new ServerSocketsList(socketList, !iterator2.hasNext());
    }

    @Nullable
    public InternalInstrumented<SocketStats> getSocket(long id) {
        InternalInstrumented clientSocket = (InternalInstrumented)this.otherSockets.get(id);
        if (clientSocket != null) {
            return clientSocket;
        }
        return this.getServerSocket(id);
    }

    private InternalInstrumented<SocketStats> getServerSocket(long id) {
        for (ServerSocketMap perServerSockets : this.perServerSockets.values()) {
            InternalInstrumented serverSocket = (InternalInstrumented)perServerSockets.get(id);
            if (serverSocket == null) continue;
            return serverSocket;
        }
        return null;
    }

    @VisibleForTesting
    public boolean containsServer(InternalLogId serverRef) {
        return InternalChannelz.contains(this.servers, serverRef);
    }

    @VisibleForTesting
    public boolean containsSubchannel(InternalLogId subchannelRef) {
        return InternalChannelz.contains(this.subchannels, subchannelRef);
    }

    public InternalInstrumented<ChannelStats> getRootChannel(long id) {
        return (InternalInstrumented)this.rootChannels.get(id);
    }

    @VisibleForTesting
    public boolean containsClientSocket(InternalLogId transportRef) {
        return InternalChannelz.contains(this.otherSockets, transportRef);
    }

    private static <T extends InternalInstrumented<?>> void add(Map<Long, T> map, T object) {
        InternalInstrumented prev = (InternalInstrumented)map.put(object.getLogId().getId(), object);
        assert (prev == null);
    }

    private static <T extends InternalInstrumented<?>> void remove(Map<Long, T> map, T object) {
        InternalInstrumented prev = (InternalInstrumented)map.remove(InternalChannelz.id(object));
        assert (prev != null);
    }

    private static <T extends InternalInstrumented<?>> boolean contains(Map<Long, T> map, InternalLogId id) {
        return map.containsKey(id.getId());
    }

    public static long id(InternalWithLogId withLogId) {
        return withLogId.getLogId().getId();
    }

    @Immutable
    public static final class TransportStats {
        public final long streamsStarted;
        public final long lastLocalStreamCreatedTimeNanos;
        public final long lastRemoteStreamCreatedTimeNanos;
        public final long streamsSucceeded;
        public final long streamsFailed;
        public final long messagesSent;
        public final long messagesReceived;
        public final long keepAlivesSent;
        public final long lastMessageSentTimeNanos;
        public final long lastMessageReceivedTimeNanos;
        public final long localFlowControlWindow;
        public final long remoteFlowControlWindow;

        public TransportStats(long streamsStarted, long lastLocalStreamCreatedTimeNanos, long lastRemoteStreamCreatedTimeNanos, long streamsSucceeded, long streamsFailed, long messagesSent, long messagesReceived, long keepAlivesSent, long lastMessageSentTimeNanos, long lastMessageReceivedTimeNanos, long localFlowControlWindow, long remoteFlowControlWindow) {
            this.streamsStarted = streamsStarted;
            this.lastLocalStreamCreatedTimeNanos = lastLocalStreamCreatedTimeNanos;
            this.lastRemoteStreamCreatedTimeNanos = lastRemoteStreamCreatedTimeNanos;
            this.streamsSucceeded = streamsSucceeded;
            this.streamsFailed = streamsFailed;
            this.messagesSent = messagesSent;
            this.messagesReceived = messagesReceived;
            this.keepAlivesSent = keepAlivesSent;
            this.lastMessageSentTimeNanos = lastMessageSentTimeNanos;
            this.lastMessageReceivedTimeNanos = lastMessageReceivedTimeNanos;
            this.localFlowControlWindow = localFlowControlWindow;
            this.remoteFlowControlWindow = remoteFlowControlWindow;
        }
    }

    public static final class SocketOptions {
        public final Map<String, String> others;
        @Nullable
        public final Integer soTimeoutMillis;
        @Nullable
        public final Integer lingerSeconds;
        @Nullable
        public final TcpInfo tcpInfo;

        public SocketOptions(@Nullable Integer timeoutMillis, @Nullable Integer lingerSeconds, @Nullable TcpInfo tcpInfo, Map<String, String> others) {
            Preconditions.checkNotNull(others);
            this.soTimeoutMillis = timeoutMillis;
            this.lingerSeconds = lingerSeconds;
            this.tcpInfo = tcpInfo;
            this.others = Collections.unmodifiableMap(new HashMap<String, String>(others));
        }

        public static final class Builder {
            private final Map<String, String> others = new HashMap<String, String>();
            private TcpInfo tcpInfo;
            private Integer timeoutMillis;
            private Integer lingerSeconds;

            public Builder setSocketOptionTimeoutMillis(Integer timeoutMillis) {
                this.timeoutMillis = timeoutMillis;
                return this;
            }

            public Builder setSocketOptionLingerSeconds(Integer lingerSeconds) {
                this.lingerSeconds = lingerSeconds;
                return this;
            }

            public Builder setTcpInfo(TcpInfo tcpInfo) {
                this.tcpInfo = tcpInfo;
                return this;
            }

            public Builder addOption(String name, String value) {
                this.others.put(name, Preconditions.checkNotNull(value));
                return this;
            }

            public Builder addOption(String name, int value) {
                this.others.put(name, Integer.toString(value));
                return this;
            }

            public Builder addOption(String name, boolean value) {
                this.others.put(name, Boolean.toString(value));
                return this;
            }

            public SocketOptions build() {
                return new SocketOptions(this.timeoutMillis, this.lingerSeconds, this.tcpInfo, this.others);
            }
        }
    }

    public static final class TcpInfo {
        public final int state;
        public final int caState;
        public final int retransmits;
        public final int probes;
        public final int backoff;
        public final int options;
        public final int sndWscale;
        public final int rcvWscale;
        public final int rto;
        public final int ato;
        public final int sndMss;
        public final int rcvMss;
        public final int unacked;
        public final int sacked;
        public final int lost;
        public final int retrans;
        public final int fackets;
        public final int lastDataSent;
        public final int lastAckSent;
        public final int lastDataRecv;
        public final int lastAckRecv;
        public final int pmtu;
        public final int rcvSsthresh;
        public final int rtt;
        public final int rttvar;
        public final int sndSsthresh;
        public final int sndCwnd;
        public final int advmss;
        public final int reordering;

        TcpInfo(int state, int caState, int retransmits, int probes, int backoff, int options, int sndWscale, int rcvWscale, int rto, int ato, int sndMss, int rcvMss, int unacked, int sacked, int lost, int retrans, int fackets, int lastDataSent, int lastAckSent, int lastDataRecv, int lastAckRecv, int pmtu, int rcvSsthresh, int rtt, int rttvar, int sndSsthresh, int sndCwnd, int advmss, int reordering) {
            this.state = state;
            this.caState = caState;
            this.retransmits = retransmits;
            this.probes = probes;
            this.backoff = backoff;
            this.options = options;
            this.sndWscale = sndWscale;
            this.rcvWscale = rcvWscale;
            this.rto = rto;
            this.ato = ato;
            this.sndMss = sndMss;
            this.rcvMss = rcvMss;
            this.unacked = unacked;
            this.sacked = sacked;
            this.lost = lost;
            this.retrans = retrans;
            this.fackets = fackets;
            this.lastDataSent = lastDataSent;
            this.lastAckSent = lastAckSent;
            this.lastDataRecv = lastDataRecv;
            this.lastAckRecv = lastAckRecv;
            this.pmtu = pmtu;
            this.rcvSsthresh = rcvSsthresh;
            this.rtt = rtt;
            this.rttvar = rttvar;
            this.sndSsthresh = sndSsthresh;
            this.sndCwnd = sndCwnd;
            this.advmss = advmss;
            this.reordering = reordering;
        }

        public static final class Builder {
            private int state;
            private int caState;
            private int retransmits;
            private int probes;
            private int backoff;
            private int options;
            private int sndWscale;
            private int rcvWscale;
            private int rto;
            private int ato;
            private int sndMss;
            private int rcvMss;
            private int unacked;
            private int sacked;
            private int lost;
            private int retrans;
            private int fackets;
            private int lastDataSent;
            private int lastAckSent;
            private int lastDataRecv;
            private int lastAckRecv;
            private int pmtu;
            private int rcvSsthresh;
            private int rtt;
            private int rttvar;
            private int sndSsthresh;
            private int sndCwnd;
            private int advmss;
            private int reordering;

            public Builder setState(int state) {
                this.state = state;
                return this;
            }

            public Builder setCaState(int caState) {
                this.caState = caState;
                return this;
            }

            public Builder setRetransmits(int retransmits) {
                this.retransmits = retransmits;
                return this;
            }

            public Builder setProbes(int probes) {
                this.probes = probes;
                return this;
            }

            public Builder setBackoff(int backoff) {
                this.backoff = backoff;
                return this;
            }

            public Builder setOptions(int options) {
                this.options = options;
                return this;
            }

            public Builder setSndWscale(int sndWscale) {
                this.sndWscale = sndWscale;
                return this;
            }

            public Builder setRcvWscale(int rcvWscale) {
                this.rcvWscale = rcvWscale;
                return this;
            }

            public Builder setRto(int rto) {
                this.rto = rto;
                return this;
            }

            public Builder setAto(int ato) {
                this.ato = ato;
                return this;
            }

            public Builder setSndMss(int sndMss) {
                this.sndMss = sndMss;
                return this;
            }

            public Builder setRcvMss(int rcvMss) {
                this.rcvMss = rcvMss;
                return this;
            }

            public Builder setUnacked(int unacked) {
                this.unacked = unacked;
                return this;
            }

            public Builder setSacked(int sacked) {
                this.sacked = sacked;
                return this;
            }

            public Builder setLost(int lost) {
                this.lost = lost;
                return this;
            }

            public Builder setRetrans(int retrans) {
                this.retrans = retrans;
                return this;
            }

            public Builder setFackets(int fackets) {
                this.fackets = fackets;
                return this;
            }

            public Builder setLastDataSent(int lastDataSent) {
                this.lastDataSent = lastDataSent;
                return this;
            }

            public Builder setLastAckSent(int lastAckSent) {
                this.lastAckSent = lastAckSent;
                return this;
            }

            public Builder setLastDataRecv(int lastDataRecv) {
                this.lastDataRecv = lastDataRecv;
                return this;
            }

            public Builder setLastAckRecv(int lastAckRecv) {
                this.lastAckRecv = lastAckRecv;
                return this;
            }

            public Builder setPmtu(int pmtu) {
                this.pmtu = pmtu;
                return this;
            }

            public Builder setRcvSsthresh(int rcvSsthresh) {
                this.rcvSsthresh = rcvSsthresh;
                return this;
            }

            public Builder setRtt(int rtt) {
                this.rtt = rtt;
                return this;
            }

            public Builder setRttvar(int rttvar) {
                this.rttvar = rttvar;
                return this;
            }

            public Builder setSndSsthresh(int sndSsthresh) {
                this.sndSsthresh = sndSsthresh;
                return this;
            }

            public Builder setSndCwnd(int sndCwnd) {
                this.sndCwnd = sndCwnd;
                return this;
            }

            public Builder setAdvmss(int advmss) {
                this.advmss = advmss;
                return this;
            }

            public Builder setReordering(int reordering) {
                this.reordering = reordering;
                return this;
            }

            public TcpInfo build() {
                return new TcpInfo(this.state, this.caState, this.retransmits, this.probes, this.backoff, this.options, this.sndWscale, this.rcvWscale, this.rto, this.ato, this.sndMss, this.rcvMss, this.unacked, this.sacked, this.lost, this.retrans, this.fackets, this.lastDataSent, this.lastAckSent, this.lastDataRecv, this.lastAckRecv, this.pmtu, this.rcvSsthresh, this.rtt, this.rttvar, this.sndSsthresh, this.sndCwnd, this.advmss, this.reordering);
            }
        }
    }

    public static final class SocketStats {
        @Nullable
        public final TransportStats data;
        @Nullable
        public final SocketAddress local;
        @Nullable
        public final SocketAddress remote;
        public final SocketOptions socketOptions;
        @Nullable
        public final Security security;

        public SocketStats(TransportStats data, @Nullable SocketAddress local, @Nullable SocketAddress remote, SocketOptions socketOptions, Security security) {
            this.data = data;
            this.local = Preconditions.checkNotNull(local, "local socket");
            this.remote = remote;
            this.socketOptions = Preconditions.checkNotNull(socketOptions);
            this.security = security;
        }
    }

    @Immutable
    public static final class Tls {
        public final String cipherSuiteStandardName;
        @Nullable
        public final Certificate localCert;
        @Nullable
        public final Certificate remoteCert;

        public Tls(String cipherSuiteName, Certificate localCert, Certificate remoteCert) {
            this.cipherSuiteStandardName = cipherSuiteName;
            this.localCert = localCert;
            this.remoteCert = remoteCert;
        }

        public Tls(SSLSession session) {
            String cipherSuiteStandardName = session.getCipherSuite();
            Certificate localCert = null;
            Certificate remoteCert = null;
            Certificate[] localCerts = session.getLocalCertificates();
            if (localCerts != null) {
                localCert = localCerts[0];
            }
            try {
                Certificate[] peerCerts = session.getPeerCertificates();
                if (peerCerts != null) {
                    remoteCert = peerCerts[0];
                }
            }
            catch (SSLPeerUnverifiedException e) {
                log.log(Level.FINE, String.format("Peer cert not available for peerHost=%s", session.getPeerHost()), e);
            }
            this.cipherSuiteStandardName = cipherSuiteStandardName;
            this.localCert = localCert;
            this.remoteCert = remoteCert;
        }
    }

    public static final class OtherSecurity {
        public final String name;
        @Nullable
        public final Object any;

        public OtherSecurity(String name, @Nullable Object any) {
            this.name = Preconditions.checkNotNull(name);
            Preconditions.checkState(any == null || any.getClass().getName().endsWith("com.google.protobuf.Any"), "the 'any' object must be of type com.google.protobuf.Any");
            this.any = any;
        }
    }

    public static final class Security {
        @Nullable
        public final Tls tls;
        @Nullable
        public final OtherSecurity other;

        public Security(Tls tls) {
            this.tls = Preconditions.checkNotNull(tls);
            this.other = null;
        }

        public Security(OtherSecurity other) {
            this.tls = null;
            this.other = Preconditions.checkNotNull(other);
        }
    }

    @Immutable
    public static final class ChannelTrace {
        public final long numEventsLogged;
        public final long creationTimeNanos;
        public final List<Event> events;

        private ChannelTrace(long numEventsLogged, long creationTimeNanos, List<Event> events) {
            this.numEventsLogged = numEventsLogged;
            this.creationTimeNanos = creationTimeNanos;
            this.events = events;
        }

        @Immutable
        public static final class Event {
            public final String description;
            public final Severity severity;
            public final long timestampNanos;
            @Nullable
            public final InternalWithLogId channelRef;
            @Nullable
            public final InternalWithLogId subchannelRef;

            private Event(String description, Severity severity, long timestampNanos, @Nullable InternalWithLogId channelRef, @Nullable InternalWithLogId subchannelRef) {
                this.description = description;
                this.severity = Preconditions.checkNotNull(severity, "severity");
                this.timestampNanos = timestampNanos;
                this.channelRef = channelRef;
                this.subchannelRef = subchannelRef;
            }

            public int hashCode() {
                return Objects.hashCode(new Object[]{this.description, this.severity, this.timestampNanos, this.channelRef, this.subchannelRef});
            }

            public boolean equals(Object o) {
                if (o instanceof Event) {
                    Event that = (Event)o;
                    return Objects.equal(this.description, that.description) && Objects.equal((Object)this.severity, (Object)that.severity) && this.timestampNanos == that.timestampNanos && Objects.equal(this.channelRef, that.channelRef) && Objects.equal(this.subchannelRef, that.subchannelRef);
                }
                return false;
            }

            public String toString() {
                return MoreObjects.toStringHelper(this).add("description", this.description).add("severity", (Object)this.severity).add("timestampNanos", this.timestampNanos).add("channelRef", this.channelRef).add("subchannelRef", this.subchannelRef).toString();
            }

            public static final class Builder {
                private String description;
                private Severity severity;
                private Long timestampNanos;
                private InternalWithLogId channelRef;
                private InternalWithLogId subchannelRef;

                public Builder setDescription(String description) {
                    this.description = description;
                    return this;
                }

                public Builder setTimestampNanos(long timestampNanos) {
                    this.timestampNanos = timestampNanos;
                    return this;
                }

                public Builder setSeverity(Severity severity) {
                    this.severity = severity;
                    return this;
                }

                public Builder setChannelRef(InternalWithLogId channelRef) {
                    this.channelRef = channelRef;
                    return this;
                }

                public Builder setSubchannelRef(InternalWithLogId subchannelRef) {
                    this.subchannelRef = subchannelRef;
                    return this;
                }

                public Event build() {
                    Preconditions.checkNotNull(this.description, "description");
                    Preconditions.checkNotNull(this.severity, "severity");
                    Preconditions.checkNotNull(this.timestampNanos, "timestampNanos");
                    Preconditions.checkState(this.channelRef == null || this.subchannelRef == null, "at least one of channelRef and subchannelRef must be null");
                    return new Event(this.description, this.severity, this.timestampNanos, this.channelRef, this.subchannelRef);
                }
            }

            public static enum Severity {
                CT_UNKNOWN,
                CT_INFO,
                CT_WARNING,
                CT_ERROR;

            }
        }

        public static final class Builder {
            private Long numEventsLogged;
            private Long creationTimeNanos;
            private List<Event> events = Collections.emptyList();

            public Builder setNumEventsLogged(long numEventsLogged) {
                this.numEventsLogged = numEventsLogged;
                return this;
            }

            public Builder setCreationTimeNanos(long creationTimeNanos) {
                this.creationTimeNanos = creationTimeNanos;
                return this;
            }

            public Builder setEvents(List<Event> events) {
                this.events = Collections.unmodifiableList(new ArrayList<Event>(events));
                return this;
            }

            public ChannelTrace build() {
                Preconditions.checkNotNull(this.numEventsLogged, "numEventsLogged");
                Preconditions.checkNotNull(this.creationTimeNanos, "creationTimeNanos");
                return new ChannelTrace(this.numEventsLogged, this.creationTimeNanos, this.events);
            }
        }
    }

    @Immutable
    public static final class ChannelStats {
        public final String target;
        public final ConnectivityState state;
        @Nullable
        public final ChannelTrace channelTrace;
        public final long callsStarted;
        public final long callsSucceeded;
        public final long callsFailed;
        public final long lastCallStartedNanos;
        public final List<InternalWithLogId> subchannels;
        public final List<InternalWithLogId> sockets;

        private ChannelStats(String target, ConnectivityState state, @Nullable ChannelTrace channelTrace, long callsStarted, long callsSucceeded, long callsFailed, long lastCallStartedNanos, List<InternalWithLogId> subchannels, List<InternalWithLogId> sockets) {
            Preconditions.checkState(subchannels.isEmpty() || sockets.isEmpty(), "channels can have subchannels only, subchannels can have either sockets OR subchannels, neither can have both");
            this.target = target;
            this.state = state;
            this.channelTrace = channelTrace;
            this.callsStarted = callsStarted;
            this.callsSucceeded = callsSucceeded;
            this.callsFailed = callsFailed;
            this.lastCallStartedNanos = lastCallStartedNanos;
            this.subchannels = Preconditions.checkNotNull(subchannels);
            this.sockets = Preconditions.checkNotNull(sockets);
        }

        public static final class Builder {
            private String target;
            private ConnectivityState state;
            private ChannelTrace channelTrace;
            private long callsStarted;
            private long callsSucceeded;
            private long callsFailed;
            private long lastCallStartedNanos;
            private List<InternalWithLogId> subchannels = Collections.emptyList();
            private List<InternalWithLogId> sockets = Collections.emptyList();

            public Builder setTarget(String target) {
                this.target = target;
                return this;
            }

            public Builder setState(ConnectivityState state) {
                this.state = state;
                return this;
            }

            public Builder setChannelTrace(ChannelTrace channelTrace) {
                this.channelTrace = channelTrace;
                return this;
            }

            public Builder setCallsStarted(long callsStarted) {
                this.callsStarted = callsStarted;
                return this;
            }

            public Builder setCallsSucceeded(long callsSucceeded) {
                this.callsSucceeded = callsSucceeded;
                return this;
            }

            public Builder setCallsFailed(long callsFailed) {
                this.callsFailed = callsFailed;
                return this;
            }

            public Builder setLastCallStartedNanos(long lastCallStartedNanos) {
                this.lastCallStartedNanos = lastCallStartedNanos;
                return this;
            }

            public Builder setSubchannels(List<InternalWithLogId> subchannels) {
                Preconditions.checkState(this.sockets.isEmpty());
                this.subchannels = Collections.unmodifiableList(Preconditions.checkNotNull(subchannels));
                return this;
            }

            public Builder setSockets(List<InternalWithLogId> sockets) {
                Preconditions.checkState(this.subchannels.isEmpty());
                this.sockets = Collections.unmodifiableList(Preconditions.checkNotNull(sockets));
                return this;
            }

            public ChannelStats build() {
                return new ChannelStats(this.target, this.state, this.channelTrace, this.callsStarted, this.callsSucceeded, this.callsFailed, this.lastCallStartedNanos, this.subchannels, this.sockets);
            }
        }
    }

    @Immutable
    public static final class ServerStats {
        public final long callsStarted;
        public final long callsSucceeded;
        public final long callsFailed;
        public final long lastCallStartedNanos;
        public final List<InternalInstrumented<SocketStats>> listenSockets;

        public ServerStats(long callsStarted, long callsSucceeded, long callsFailed, long lastCallStartedNanos, List<InternalInstrumented<SocketStats>> listenSockets) {
            this.callsStarted = callsStarted;
            this.callsSucceeded = callsSucceeded;
            this.callsFailed = callsFailed;
            this.lastCallStartedNanos = lastCallStartedNanos;
            this.listenSockets = Preconditions.checkNotNull(listenSockets);
        }

        public static final class Builder {
            private long callsStarted;
            private long callsSucceeded;
            private long callsFailed;
            private long lastCallStartedNanos;
            public List<InternalInstrumented<SocketStats>> listenSockets = new ArrayList<InternalInstrumented<SocketStats>>();

            public Builder setCallsStarted(long callsStarted) {
                this.callsStarted = callsStarted;
                return this;
            }

            public Builder setCallsSucceeded(long callsSucceeded) {
                this.callsSucceeded = callsSucceeded;
                return this;
            }

            public Builder setCallsFailed(long callsFailed) {
                this.callsFailed = callsFailed;
                return this;
            }

            public Builder setLastCallStartedNanos(long lastCallStartedNanos) {
                this.lastCallStartedNanos = lastCallStartedNanos;
                return this;
            }

            public Builder addListenSockets(List<InternalInstrumented<SocketStats>> listenSockets) {
                Preconditions.checkNotNull(listenSockets, "listenSockets");
                for (InternalInstrumented<SocketStats> ss : listenSockets) {
                    this.listenSockets.add(Preconditions.checkNotNull(ss, "null listen socket"));
                }
                return this;
            }

            public ServerStats build() {
                return new ServerStats(this.callsStarted, this.callsSucceeded, this.callsFailed, this.lastCallStartedNanos, this.listenSockets);
            }
        }
    }

    public static final class ServerSocketsList {
        public final List<InternalWithLogId> sockets;
        public final boolean end;

        public ServerSocketsList(List<InternalWithLogId> sockets, boolean end) {
            this.sockets = sockets;
            this.end = end;
        }
    }

    public static final class ServerList {
        public final List<InternalInstrumented<ServerStats>> servers;
        public final boolean end;

        public ServerList(List<InternalInstrumented<ServerStats>> servers, boolean end) {
            this.servers = Preconditions.checkNotNull(servers);
            this.end = end;
        }
    }

    public static final class RootChannelList {
        public final List<InternalInstrumented<ChannelStats>> channels;
        public final boolean end;

        public RootChannelList(List<InternalInstrumented<ChannelStats>> channels, boolean end) {
            this.channels = Preconditions.checkNotNull(channels);
            this.end = end;
        }
    }

    private static final class ServerSocketMap
    extends ConcurrentSkipListMap<Long, InternalInstrumented<SocketStats>> {
        private static final long serialVersionUID = -7883772124944661414L;

        private ServerSocketMap() {
        }
    }
}

