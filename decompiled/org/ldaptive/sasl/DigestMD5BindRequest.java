/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.IntStream;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.RealmChoiceCallback;
import org.ldaptive.sasl.DefaultSaslClientRequest;
import org.ldaptive.sasl.Mechanism;

public class DigestMD5BindRequest
extends DefaultSaslClientRequest {
    public static final Mechanism MECHANISM = Mechanism.DIGEST_MD5;
    private final String authenticationID;
    private final String authorizationID;
    private final String saslRealm;
    private final Map<String, ?> saslProperties;
    private final String password;

    public DigestMD5BindRequest(String authID, String authzID, String pass, String realm, Map<String, Object> props) {
        this.authenticationID = authID;
        this.authorizationID = authzID;
        this.password = pass;
        this.saslRealm = realm;
        this.saslProperties = Collections.unmodifiableMap(props);
    }

    @Override
    public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                ((NameCallback)callback).setName(this.authenticationID);
                continue;
            }
            if (callback instanceof PasswordCallback && this.password != null) {
                ((PasswordCallback)callback).setPassword(this.password.toCharArray());
                continue;
            }
            if (callback instanceof RealmCallback) {
                RealmCallback rc = (RealmCallback)callback;
                if (this.saslRealm == null) {
                    String defaultRealm = rc.getDefaultText();
                    if (defaultRealm == null) {
                        throw new IllegalStateException("Default realm required, but none provided");
                    }
                    rc.setText(defaultRealm);
                    continue;
                }
                rc.setText(this.saslRealm);
                continue;
            }
            if (callback instanceof RealmChoiceCallback) {
                RealmChoiceCallback rcc = (RealmChoiceCallback)callback;
                if (this.saslRealm == null) {
                    throw new IllegalStateException("Realm required, choose one of the following: " + Arrays.toString(rcc.getChoices()));
                }
                if (rcc.getChoices() == null) continue;
                int selectedIndex = IntStream.range(0, rcc.getChoices().length).filter(i -> rcc.getChoices()[i].equals(this.saslRealm)).findFirst().getAsInt();
                rcc.setSelectedIndex(selectedIndex);
                continue;
            }
            throw new UnsupportedCallbackException(callback);
        }
    }

    @Override
    public Mechanism getMechanism() {
        return MECHANISM;
    }

    @Override
    public String getAuthorizationID() {
        return this.authorizationID;
    }

    @Override
    public Map<String, ?> getSaslProperties() {
        return this.saslProperties;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "authenticationID=" + this.authenticationID + ", " + "authorizationID=" + this.authorizationID + ", " + "saslRealm=" + this.saslRealm + ", " + "saslProperties=" + this.saslProperties;
    }
}

