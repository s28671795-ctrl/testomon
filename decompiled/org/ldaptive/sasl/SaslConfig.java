/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.ldaptive.AbstractConfig;
import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.QualityOfProtection;
import org.ldaptive.sasl.SecurityStrength;

public class SaslConfig
extends AbstractConfig {
    private Mechanism mechanism;
    private String authorizationId;
    private Boolean mutualAuthentication;
    private QualityOfProtection[] qualityOfProtection;
    private SecurityStrength[] securityStrength;
    private String saslRealm;
    private Map<String, Object> properties = new HashMap<String, Object>();

    public Mechanism getMechanism() {
        return this.mechanism;
    }

    public void setMechanism(Mechanism m4) {
        this.checkImmutable();
        this.logger.trace("setting mechanism: {}", (Object)m4);
        this.mechanism = m4;
    }

    public String getAuthorizationId() {
        return this.authorizationId;
    }

    public void setAuthorizationId(String id) {
        this.checkImmutable();
        this.logger.trace("setting authorizationId: {}", (Object)id);
        this.authorizationId = id;
    }

    public Boolean getMutualAuthentication() {
        return this.mutualAuthentication;
    }

    public void setMutualAuthentication(Boolean b) {
        this.checkImmutable();
        this.logger.trace("setting mutualAuthentication: {}", (Object)b);
        this.mutualAuthentication = b;
    }

    public QualityOfProtection[] getQualityOfProtection() {
        return this.qualityOfProtection;
    }

    public void setQualityOfProtection(QualityOfProtection ... qop) {
        this.checkImmutable();
        this.checkArrayContainsNull((Object[])qop);
        this.logger.trace("setting qualityOfProtection: {}", (Object)Arrays.toString((Object[])qop));
        this.qualityOfProtection = qop;
    }

    public SecurityStrength[] getSecurityStrength() {
        return this.securityStrength;
    }

    public void setSecurityStrength(SecurityStrength ... ss) {
        this.checkImmutable();
        this.checkArrayContainsNull((Object[])ss);
        this.logger.trace("setting securityStrength: {}", (Object)Arrays.toString((Object[])ss));
        this.securityStrength = ss;
    }

    public String getRealm() {
        return this.saslRealm;
    }

    public void setRealm(String realm) {
        this.checkImmutable();
        this.logger.trace("setting realm: {}", (Object)realm);
        this.saslRealm = realm;
    }

    public Map<String, ?> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, ?> props) {
        this.checkImmutable();
        this.logger.trace("setting properties: {}", (Object)props);
        this.properties.putAll(props);
    }

    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    public void setProperty(String name, Object value) {
        this.checkImmutable();
        this.logger.trace("setting property: {}={}", (Object)name, value);
        this.properties.put(name, value);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "mechanism=" + (Object)((Object)this.mechanism) + ", " + "authorizationId=" + this.authorizationId + ", " + "mutualAuthentication=" + this.mutualAuthentication + ", " + "qualityOfProtection=" + Arrays.toString((Object[])this.qualityOfProtection) + ", " + "securityStrength=" + Arrays.toString((Object[])this.securityStrength) + ", " + "realm=" + this.saslRealm + ", " + "properties=" + this.properties + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final SaslConfig object = new SaslConfig();

        protected Builder() {
        }

        public Builder mechanism(Mechanism mechanism) {
            this.object.setMechanism(mechanism);
            return this;
        }

        public Builder authorizationId(String id) {
            this.object.setAuthorizationId(id);
            return this;
        }

        public Builder mutualAuthentication(Boolean b) {
            this.object.setMutualAuthentication(b);
            return this;
        }

        public Builder qualityOfProtection(QualityOfProtection ... protections) {
            this.object.setQualityOfProtection(protections);
            return this;
        }

        public Builder securityStrength(SecurityStrength ... strengths) {
            this.object.setSecurityStrength(strengths);
            return this;
        }

        public Builder realm(String realm) {
            this.object.setRealm(realm);
            return this;
        }

        public Builder property(String name, Object value) {
            this.object.setProperty(name, value);
            return this;
        }

        public SaslConfig build() {
            return this.object;
        }
    }
}

