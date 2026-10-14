/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.state;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.state.ConsumerState;
import org.apache.activemq.state.ProducerState;

public class SessionState {
    final SessionInfo info;
    private final Map<ProducerId, ProducerState> producers = new ConcurrentHashMap<ProducerId, ProducerState>();
    private final Map<ConsumerId, ConsumerState> consumers = new ConcurrentHashMap<ConsumerId, ConsumerState>();
    private final AtomicBoolean shutdown = new AtomicBoolean(false);

    public SessionState(SessionInfo info) {
        this.info = info;
    }

    public String toString() {
        return this.info.toString();
    }

    public void addProducer(ProducerInfo info) {
        this.checkShutdown();
        this.producers.put(info.getProducerId(), new ProducerState(info));
    }

    public ProducerState removeProducer(ProducerId id) {
        ProducerState producerState = this.producers.remove(id);
        if (producerState != null && producerState.getTransactionState() != null) {
            producerState.getTransactionState().addProducerState(producerState);
        }
        return producerState;
    }

    public void addConsumer(ConsumerInfo info) {
        this.checkShutdown();
        this.consumers.put(info.getConsumerId(), new ConsumerState(info));
    }

    public ConsumerState removeConsumer(ConsumerId id) {
        return this.consumers.remove(id);
    }

    public SessionInfo getInfo() {
        return this.info;
    }

    public Set<ConsumerId> getConsumerIds() {
        return this.consumers.keySet();
    }

    public Set<ProducerId> getProducerIds() {
        return this.producers.keySet();
    }

    public Collection<ProducerState> getProducerStates() {
        return this.producers.values();
    }

    public ProducerState getProducerState(ProducerId producerId) {
        return this.producers.get(producerId);
    }

    public Collection<ConsumerState> getConsumerStates() {
        return this.consumers.values();
    }

    public ConsumerState getConsumerState(ConsumerId consumerId) {
        return this.consumers.get(consumerId);
    }

    private void checkShutdown() {
        if (this.shutdown.get()) {
            throw new IllegalStateException("Disposed");
        }
    }

    public void shutdown() {
        this.shutdown.set(false);
    }
}

