/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Billing;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface BillingOrBuilder
extends MessageOrBuilder {
    public List<Billing.BillingDestination> getConsumerDestinationsList();

    public Billing.BillingDestination getConsumerDestinations(int var1);

    public int getConsumerDestinationsCount();

    public List<? extends Billing.BillingDestinationOrBuilder> getConsumerDestinationsOrBuilderList();

    public Billing.BillingDestinationOrBuilder getConsumerDestinationsOrBuilder(int var1);
}

