/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.PolicyOperations;
import org.omg.RTCORBA.PriorityModel;

public interface PriorityModelPolicyOperations
extends PolicyOperations {
    public PriorityModel priority_model();

    public short server_priority();
}

