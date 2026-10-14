/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.security.auth.callback.CallbackHandler;
import org.ldaptive.control.RequestControl;
import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.QualityOfProtection;
import org.ldaptive.sasl.SaslBindRequest;
import org.ldaptive.sasl.SaslClient;
import org.ldaptive.sasl.SaslConfig;
import org.ldaptive.transport.DefaultSaslClient;

public abstract class DefaultSaslClientRequest
implements CallbackHandler {
    private RequestControl[] controls;

    public RequestControl[] getControls() {
        return this.controls;
    }

    public void setControls(RequestControl ... cntrls) {
        this.controls = cntrls;
    }

    public abstract Mechanism getMechanism();

    public String getAuthorizationID() {
        return null;
    }

    public Map<String, ?> getSaslProperties() {
        return null;
    }

    public SaslClient getSaslClient() {
        return new DefaultSaslClient();
    }

    public static Map<String, Object> createProperties(SaslConfig config) {
        HashMap<String, Object> props = new HashMap<String, Object>();
        if (!config.getProperties().isEmpty()) {
            props.putAll(config.getProperties());
        }
        if (config.getQualityOfProtection() != null) {
            if (config.getQualityOfProtection().length == 0) {
                throw new IllegalArgumentException("QOP cannot be empty");
            }
            props.put("javax.security.sasl.qop", Stream.of(config.getQualityOfProtection()).peek(q -> {
                if (q == null) {
                    throw new IllegalArgumentException("QOP cannot be null");
                }
            }).map(QualityOfProtection::string).collect(Collectors.joining(",")));
        }
        if (config.getSecurityStrength() != null) {
            if (config.getSecurityStrength().length == 0) {
                throw new IllegalArgumentException("Security strength cannot be empty");
            }
            props.put("javax.security.sasl.strength", Stream.of(config.getSecurityStrength()).peek(s2 -> {
                if (s2 == null) {
                    throw new IllegalArgumentException("Security strength cannot be null");
                }
            }).map(s2 -> s2.name().toLowerCase()).collect(Collectors.joining(",")));
        }
        if (config.getMutualAuthentication() != null) {
            props.put("javax.security.sasl.server.authentication", config.getMutualAuthentication().toString());
        }
        return Collections.unmodifiableMap(props);
    }

    public SaslBindRequest createBindRequest(byte[] saslCredentials) {
        SaslBindRequest req = new SaslBindRequest(this.getMechanism().mechanism(), saslCredentials);
        req.setControls(this.getControls());
        return req;
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "controls=" + Arrays.toString(this.controls);
    }
}

