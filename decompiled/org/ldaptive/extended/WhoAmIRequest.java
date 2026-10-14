/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import org.ldaptive.extended.ExtendedRequest;

public class WhoAmIRequest
extends ExtendedRequest {
    public static final String OID = "1.3.6.1.4.1.4203.1.11.3";

    public WhoAmIRequest() {
        super(OID);
    }
}

