/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Arrays;
import org.ldaptive.AnonymousBindRequest;
import org.ldaptive.BindRequest;
import org.ldaptive.BindResponse;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionInitializer;
import org.ldaptive.Credential;
import org.ldaptive.LdapException;
import org.ldaptive.Result;
import org.ldaptive.SimpleBindRequest;
import org.ldaptive.control.RequestControl;
import org.ldaptive.sasl.CramMD5BindRequest;
import org.ldaptive.sasl.DigestMD5BindRequest;
import org.ldaptive.sasl.GssApiBindRequest;
import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.SaslBindRequest;
import org.ldaptive.sasl.SaslConfig;
import org.ldaptive.sasl.ScramBindRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BindConnectionInitializer
implements ConnectionInitializer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String bindDn;
    private Credential bindCredential;
    private SaslConfig bindSaslConfig;
    private RequestControl[] bindControls;

    public BindConnectionInitializer() {
    }

    public BindConnectionInitializer(String dn, String credential) {
        this.setBindDn(dn);
        this.setBindCredential(new Credential(credential));
    }

    public BindConnectionInitializer(String dn, Credential credential) {
        this.setBindDn(dn);
        this.setBindCredential(credential);
    }

    public String getBindDn() {
        return this.bindDn;
    }

    public void setBindDn(String dn) {
        this.logger.trace("setting bindDn: {}", (Object)dn);
        this.bindDn = dn;
    }

    public Credential getBindCredential() {
        return this.bindCredential;
    }

    public void setBindCredential(Credential credential) {
        this.logger.trace("setting bindCredential: <suppressed>");
        this.bindCredential = credential;
    }

    public SaslConfig getBindSaslConfig() {
        return this.bindSaslConfig;
    }

    public void setBindSaslConfig(SaslConfig config) {
        this.logger.trace("setting bindSaslConfig: {}", (Object)config);
        this.bindSaslConfig = config;
    }

    public RequestControl[] getBindControls() {
        return this.bindControls;
    }

    public void setBindControls(RequestControl ... cntrls) {
        this.logger.trace("setting bindControls: {}", (Object)Arrays.toString(cntrls));
        this.bindControls = cntrls;
    }

    @Override
    public Result initialize(Connection c) throws LdapException {
        BindResponse result;
        block10: {
            block9: {
                if (this.bindSaslConfig == null) break block9;
                switch (this.bindSaslConfig.getMechanism()) {
                    case EXTERNAL: {
                        result = c.operation((BindRequest)((Object)((SaslBindRequest.Builder)SaslBindRequest.builder().mechanism(Mechanism.EXTERNAL.mechanism()).credentials(this.bindSaslConfig.getAuthorizationId() != null ? this.bindSaslConfig.getAuthorizationId() : "").controls(this.bindControls)).build())).execute();
                        break block10;
                    }
                    case DIGEST_MD5: {
                        result = c.operation(new DigestMD5BindRequest(this.bindDn, this.bindSaslConfig.getAuthorizationId(), this.bindCredential != null ? this.bindCredential.getString() : null, this.bindSaslConfig.getRealm(), DigestMD5BindRequest.createProperties(this.bindSaslConfig)));
                        break block10;
                    }
                    case CRAM_MD5: {
                        result = c.operation(new CramMD5BindRequest(this.bindDn, this.bindCredential != null ? this.bindCredential.getString() : null));
                        break block10;
                    }
                    case GSSAPI: {
                        result = c.operation(new GssApiBindRequest(this.bindDn, this.bindSaslConfig.getAuthorizationId(), this.bindCredential != null ? this.bindCredential.getString() : null, this.bindSaslConfig.getRealm(), GssApiBindRequest.createProperties(this.bindSaslConfig)));
                        break block10;
                    }
                    case SCRAM_SHA_1: {
                        result = c.operation(new ScramBindRequest(Mechanism.SCRAM_SHA_1, this.bindDn, this.bindCredential.getString()));
                        break block10;
                    }
                    case SCRAM_SHA_256: {
                        result = c.operation(new ScramBindRequest(Mechanism.SCRAM_SHA_256, this.bindDn, this.bindCredential.getString()));
                        break block10;
                    }
                    case SCRAM_SHA_512: {
                        result = c.operation(new ScramBindRequest(Mechanism.SCRAM_SHA_512, this.bindDn, this.bindCredential.getString()));
                        break block10;
                    }
                    default: {
                        throw new IllegalStateException("Unknown SASL mechanism: " + this.bindSaslConfig.getMechanism());
                    }
                }
            }
            result = this.bindDn == null && this.bindCredential == null ? c.operation((BindRequest)((Object)((AnonymousBindRequest.Builder)AnonymousBindRequest.builder().controls(this.bindControls)).build())).execute() : c.operation((BindRequest)((Object)((SimpleBindRequest.Builder)SimpleBindRequest.builder().dn(this.bindDn).password(this.bindCredential.getString()).controls(this.bindControls)).build())).execute();
        }
        return result;
    }

    public boolean isEmpty() {
        return this.bindDn == null && this.bindCredential == null && this.bindSaslConfig == null && this.bindControls == null;
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "bindDn=" + this.bindDn + ", " + "bindSaslConfig=" + this.bindSaslConfig + ", " + "bindControls=" + Arrays.toString(this.bindControls);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final BindConnectionInitializer object = new BindConnectionInitializer();

        protected Builder() {
        }

        public Builder dn(String dn) {
            this.object.setBindDn(dn);
            return this;
        }

        public Builder credential(Credential credential) {
            this.object.setBindCredential(credential);
            return this;
        }

        public Builder credential(String credential) {
            this.object.setBindCredential(new Credential(credential));
            return this;
        }

        public Builder credential(byte[] credential) {
            this.object.setBindCredential(new Credential(credential));
            return this;
        }

        public Builder saslConfig(SaslConfig config) {
            this.object.setBindSaslConfig(config);
            return this;
        }

        public Builder controls(RequestControl ... controls) {
            this.object.setBindControls(controls);
            return this;
        }

        public BindConnectionInitializer build() {
            return this.object;
        }
    }
}

