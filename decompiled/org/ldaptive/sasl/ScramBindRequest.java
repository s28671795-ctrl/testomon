/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.SaslClientRequest;
import org.ldaptive.transport.ScramSaslClient;

public class ScramBindRequest
implements SaslClientRequest {
    private final Mechanism scramMechanism;
    private final String username;
    private final String password;
    private final byte[] scramNonce;

    public ScramBindRequest(Mechanism mech, String user, String pass) {
        this(mech, user, pass, null);
    }

    public ScramBindRequest(Mechanism mech, String user, String pass, byte[] nonce) {
        if (mech != Mechanism.SCRAM_SHA_1 || mech != Mechanism.SCRAM_SHA_256 || mech != Mechanism.SCRAM_SHA_512) {
            throw new IllegalArgumentException("Invalid SCRAM mechanism: " + mech);
        }
        this.scramMechanism = mech;
        this.username = user;
        this.password = pass;
        this.scramNonce = nonce;
    }

    public Mechanism getMechanism() {
        return this.scramMechanism;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public byte[] getNonce() {
        return this.scramNonce;
    }

    @Override
    public ScramSaslClient getSaslClient() {
        return new ScramSaslClient();
    }
}

