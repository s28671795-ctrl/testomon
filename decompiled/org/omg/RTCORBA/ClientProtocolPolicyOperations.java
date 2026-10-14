/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.PolicyOperations;
import org.omg.RTCORBA.Protocol;

public interface ClientProtocolPolicyOperations
extends PolicyOperations {
    public Protocol[] protocols();
}

