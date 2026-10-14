/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Logging;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface LoggingOrBuilder
extends MessageOrBuilder {
    public List<Logging.LoggingDestination> getProducerDestinationsList();

    public Logging.LoggingDestination getProducerDestinations(int var1);

    public int getProducerDestinationsCount();

    public List<? extends Logging.LoggingDestinationOrBuilder> getProducerDestinationsOrBuilderList();

    public Logging.LoggingDestinationOrBuilder getProducerDestinationsOrBuilder(int var1);

    public List<Logging.LoggingDestination> getConsumerDestinationsList();

    public Logging.LoggingDestination getConsumerDestinations(int var1);

    public int getConsumerDestinationsCount();

    public List<? extends Logging.LoggingDestinationOrBuilder> getConsumerDestinationsOrBuilderList();

    public Logging.LoggingDestinationOrBuilder getConsumerDestinationsOrBuilder(int var1);
}

