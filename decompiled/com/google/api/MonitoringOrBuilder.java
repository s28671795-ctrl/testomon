/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Monitoring;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface MonitoringOrBuilder
extends MessageOrBuilder {
    public List<Monitoring.MonitoringDestination> getProducerDestinationsList();

    public Monitoring.MonitoringDestination getProducerDestinations(int var1);

    public int getProducerDestinationsCount();

    public List<? extends Monitoring.MonitoringDestinationOrBuilder> getProducerDestinationsOrBuilderList();

    public Monitoring.MonitoringDestinationOrBuilder getProducerDestinationsOrBuilder(int var1);

    public List<Monitoring.MonitoringDestination> getConsumerDestinationsList();

    public Monitoring.MonitoringDestination getConsumerDestinations(int var1);

    public int getConsumerDestinationsCount();

    public List<? extends Monitoring.MonitoringDestinationOrBuilder> getConsumerDestinationsOrBuilderList();

    public Monitoring.MonitoringDestinationOrBuilder getConsumerDestinationsOrBuilder(int var1);
}

