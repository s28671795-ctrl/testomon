/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.extended;

import org.ldaptive.extended.ExtendedRequest;

public class FastBindRequest
extends ExtendedRequest {
    public static final String OID = "1.2.840.113556.1.4.1781";

    public FastBindRequest() {
        super(OID);
    }
}

