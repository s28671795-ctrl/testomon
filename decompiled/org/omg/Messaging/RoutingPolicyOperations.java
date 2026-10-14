/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.PolicyOperations;
import org.omg.Messaging.RoutingTypeRange;

public interface RoutingPolicyOperations
extends PolicyOperations {
    public RoutingTypeRange routing_range();
}

