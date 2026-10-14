/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.SaslBindRequest;

public class ExternalBindRequest
extends SaslBindRequest {
    public static final Mechanism MECHANISM = Mechanism.EXTERNAL;

    public ExternalBindRequest() {
        this(null);
    }

    public ExternalBindRequest(String authzID) {
        super(MECHANISM.mechanism(), authzID != null ? authzID : "");
    }
}

