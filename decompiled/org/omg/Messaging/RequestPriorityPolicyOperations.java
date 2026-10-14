/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.PolicyOperations;
import org.omg.Messaging.PriorityRange;

public interface RequestPriorityPolicyOperations
extends PolicyOperations {
    public PriorityRange priority_range();
}

