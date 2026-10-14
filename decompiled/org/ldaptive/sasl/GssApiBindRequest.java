/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;
import org.ldaptive.sasl.DefaultSaslClientRequest;
import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.SaslClient;
import org.ldaptive.transport.DefaultSaslClient;
import org.ldaptive.transport.GssApiSaslClient;

public class GssApiBindRequest
extends DefaultSaslClientRequest {
    private static final Mechanism MECHANISM = Mechanism.GSSAPI;
    private static final String JAAS_OPTIONS_PROPERTY_PREFIX = "org.ldaptive.sasl.gssapi.jaas.";
    public static final String JAAS_NAME_PROPERTY = "org.ldaptive.sasl.gssapi.jaas.name";
    private static final String DEFAULT_GSSAPI_JAAS_NAME = "ldaptive-gssapi";
    public static final String JAAS_REFRESH_CONFIG_PROPERTY = "org.ldaptive.sasl.gssapi.jaas.refreshConfig";
    private static final String JAAS_LOGIN_MODULE_PROPERTY = "org.ldaptive.sasl.gssapi.jaas.loginModule";
    private static final String DEFAULT_GSSAPI_LOGIN_MODULE = "com.sun.security.auth.module.Krb5LoginModule";
    private final String authenticationID;
    private final String authorizationID;
    private final String saslRealm;
    private final Map<String, ?> saslProperties;
    private final String jaasName;
    private final boolean jaasRefreshConfig;
    private final String jaasLoginModule;
    private final Map<String, ?> jaasOptions;
    private final String password;
    private final AtomicBoolean invokeOnce = new AtomicBoolean();

    public GssApiBindRequest(String authID, String authzID, String pass, String realm, Map<String, Object> props) {
        this.authenticationID = authID;
        this.authorizationID = authzID;
        this.password = pass;
        this.saslRealm = realm;
        this.saslProperties = props.entrySet().stream().filter(e -> !((String)e.getKey()).startsWith(JAAS_OPTIONS_PROPERTY_PREFIX)).collect(Collectors.collectingAndThen(Collectors.toMap(e -> (String)e.getKey(), e -> e.getValue()), Collections::unmodifiableMap));
        this.jaasLoginModule = (String)props.getOrDefault(JAAS_LOGIN_MODULE_PROPERTY, DEFAULT_GSSAPI_LOGIN_MODULE);
        this.jaasOptions = props.entrySet().stream().filter(e -> ((String)e.getKey()).startsWith(JAAS_OPTIONS_PROPERTY_PREFIX) && !((String)e.getKey()).equals(JAAS_NAME_PROPERTY) && !((String)e.getKey()).equals(JAAS_LOGIN_MODULE_PROPERTY)).collect(Collectors.collectingAndThen(Collectors.toMap(e -> ((String)e.getKey()).substring(JAAS_OPTIONS_PROPERTY_PREFIX.length()), e -> e.getValue()), Collections::unmodifiableMap));
        this.jaasName = props.get(JAAS_NAME_PROPERTY) == null ? (props.get(JAAS_LOGIN_MODULE_PROPERTY) == null && this.jaasOptions.isEmpty() ? DEFAULT_GSSAPI_JAAS_NAME : null) : (String)props.get(JAAS_NAME_PROPERTY);
        this.jaasRefreshConfig = Boolean.valueOf((String)props.getOrDefault(JAAS_REFRESH_CONFIG_PROPERTY, "false"));
    }

    @Override
    public SaslClient getSaslClient() {
        if (this.invokeOnce.compareAndSet(false, true)) {
            return new GssApiSaslClient();
        }
        return new DefaultSaslClient();
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
                    throw new IllegalStateException("Realm required, but none provided");
                }
                rc.setText(this.saslRealm);
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

    public String getJaasName() {
        return this.jaasName;
    }

    public boolean getJaasRefreshConfig() {
        return this.jaasRefreshConfig;
    }

    public String getJaasLoginModule() {
        return this.jaasLoginModule;
    }

    public Map<String, ?> getJaasOptions() {
        return this.jaasOptions;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "authenticationID=" + this.authenticationID + ", " + "authorizationID=" + this.authorizationID + ", " + "realm=" + this.saslRealm + ", " + "saslProperties=" + this.saslProperties + ", " + "jaasName=" + this.jaasName + ", " + "jaasRefreshConfig=" + this.jaasRefreshConfig + ", " + "jaasLoginModule=" + this.jaasLoginModule + ", " + "jaasOptions=" + this.jaasOptions;
    }
}

