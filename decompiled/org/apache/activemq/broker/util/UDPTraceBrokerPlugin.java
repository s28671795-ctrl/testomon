/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import org.apache.activemq.broker.BrokerPluginSupport;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.JournalTrace;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.command.TransactionInfo;
import org.apache.activemq.openwire.OpenWireFormatFactory;
import org.apache.activemq.util.ByteArrayOutputStream;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.wireformat.WireFormat;
import org.apache.activemq.wireformat.WireFormatFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDPTraceBrokerPlugin
extends BrokerPluginSupport {
    private static final Logger LOG = LoggerFactory.getLogger(UDPTraceBrokerPlugin.class);
    protected WireFormat wireFormat;
    protected WireFormatFactory wireFormatFactory;
    protected int maxTraceDatagramSize = 4096;
    protected URI destination;
    protected DatagramSocket socket;
    protected BrokerId brokerId;
    protected SocketAddress address;
    protected boolean broadcast;

    public UDPTraceBrokerPlugin() {
        try {
            this.destination = new URI("udp://127.0.0.1:61616");
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
    }

    @Override
    public void start() throws Exception {
        super.start();
        if (this.getWireFormat() == null) {
            throw new IllegalArgumentException("Wireformat must be specifed.");
        }
        if (this.address == null) {
            this.address = this.createSocketAddress(this.destination);
        }
        this.socket = this.createSocket();
        this.brokerId = super.getBrokerId();
        this.trace(new JournalTrace("START"));
    }

    protected DatagramSocket createSocket() throws IOException {
        DatagramSocket s2 = new DatagramSocket();
        s2.setSendBufferSize(this.maxTraceDatagramSize);
        s2.setBroadcast(this.broadcast);
        return s2;
    }

    @Override
    public void stop() throws Exception {
        this.trace(new JournalTrace("STOP"));
        this.socket.close();
        super.stop();
    }

    private void trace(DataStructure command) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(this.maxTraceDatagramSize);
            DataOutputStream out = new DataOutputStream(baos);
            this.wireFormat.marshal(this.brokerId, out);
            this.wireFormat.marshal(command, out);
            out.close();
            ByteSequence sequence2 = baos.toByteSequence();
            DatagramPacket datagram = new DatagramPacket(sequence2.getData(), sequence2.getOffset(), sequence2.getLength(), this.address);
            this.socket.send(datagram);
        }
        catch (Throwable e) {
            LOG.debug("Failed to trace: {}", (Object)command, (Object)e);
        }
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        this.trace(messageSend);
        super.send(producerExchange, messageSend);
    }

    @Override
    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
        this.trace(ack);
        super.acknowledge(consumerExchange, ack);
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        this.trace(info);
        super.addConnection(context, info);
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        this.trace(info);
        return super.addConsumer(context, info);
    }

    @Override
    public void addDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        this.trace(info);
        super.addDestinationInfo(context, info);
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.trace(info);
        super.addProducer(context, info);
    }

    @Override
    public void addSession(ConnectionContext context, SessionInfo info) throws Exception {
        this.trace(info);
        super.addSession(context, info);
    }

    @Override
    public void beginTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        this.trace(new TransactionInfo(context.getConnectionId(), xid, 0));
        super.beginTransaction(context, xid);
    }

    @Override
    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
        this.trace(new TransactionInfo(context.getConnectionId(), xid, onePhase ? (byte)2 : 3));
        super.commitTransaction(context, xid, onePhase);
    }

    @Override
    public void forgetTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        this.trace(new TransactionInfo(context.getConnectionId(), xid, 6));
        super.forgetTransaction(context, xid);
    }

    @Override
    public Response messagePull(ConnectionContext context, MessagePull pull) throws Exception {
        this.trace(pull);
        return super.messagePull(context, pull);
    }

    @Override
    public int prepareTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        this.trace(new TransactionInfo(context.getConnectionId(), xid, 1));
        return super.prepareTransaction(context, xid);
    }

    @Override
    public void postProcessDispatch(MessageDispatch messageDispatch) {
        this.trace(messageDispatch);
        super.postProcessDispatch(messageDispatch);
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        this.trace(messageDispatchNotification);
        super.processDispatchNotification(messageDispatchNotification);
    }

    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        this.trace(info.createRemoveCommand());
        super.removeConnection(context, info, error);
    }

    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        this.trace(info.createRemoveCommand());
        super.removeConsumer(context, info);
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        super.removeDestination(context, destination, timeout2);
    }

    @Override
    public void removeDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        this.trace(info);
        super.removeDestinationInfo(context, info);
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.trace(info.createRemoveCommand());
        super.removeProducer(context, info);
    }

    @Override
    public void removeSession(ConnectionContext context, SessionInfo info) throws Exception {
        this.trace(info.createRemoveCommand());
        super.removeSession(context, info);
    }

    @Override
    public void removeSubscription(ConnectionContext context, RemoveSubscriptionInfo info) throws Exception {
        this.trace(info);
        super.removeSubscription(context, info);
    }

    @Override
    public void rollbackTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        this.trace(new TransactionInfo(context.getConnectionId(), xid, 4));
        super.rollbackTransaction(context, xid);
    }

    public WireFormat getWireFormat() {
        if (this.wireFormat == null) {
            this.wireFormat = this.createWireFormat();
        }
        return this.wireFormat;
    }

    protected WireFormat createWireFormat() {
        return this.getWireFormatFactory().createWireFormat();
    }

    public void setWireFormat(WireFormat wireFormat) {
        this.wireFormat = wireFormat;
    }

    public WireFormatFactory getWireFormatFactory() {
        if (this.wireFormatFactory == null) {
            this.wireFormatFactory = this.createWireFormatFactory();
        }
        return this.wireFormatFactory;
    }

    protected OpenWireFormatFactory createWireFormatFactory() {
        OpenWireFormatFactory wf = new OpenWireFormatFactory();
        wf.setCacheEnabled(false);
        wf.setVersion(1);
        wf.setTightEncodingEnabled(true);
        wf.setSizePrefixDisabled(true);
        return wf;
    }

    public void setWireFormatFactory(WireFormatFactory wireFormatFactory) {
        this.wireFormatFactory = wireFormatFactory;
    }

    protected SocketAddress createSocketAddress(URI location) throws UnknownHostException {
        InetAddress a = InetAddress.getByName(location.getHost());
        int port = location.getPort();
        return new InetSocketAddress(a, port);
    }

    public URI getDestination() {
        return this.destination;
    }

    public void setDestination(URI destination) {
        this.destination = destination;
    }

    public int getMaxTraceDatagramSize() {
        return this.maxTraceDatagramSize;
    }

    public void setMaxTraceDatagramSize(int maxTraceDatagramSize) {
        this.maxTraceDatagramSize = maxTraceDatagramSize;
    }

    public boolean isBroadcast() {
        return this.broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public SocketAddress getAddress() {
        return this.address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }
}

