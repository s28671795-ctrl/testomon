/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.PolicyOperations;
import org.omg.RTCORBA.PriorityBand;

public interface PriorityBandedConnectionPolicyOperations
extends PolicyOperations {
    public PriorityBand[] priority_bands();
}

