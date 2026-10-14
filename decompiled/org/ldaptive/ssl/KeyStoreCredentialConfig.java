/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import org.ldaptive.LdapUtils;
import org.ldaptive.ssl.CredentialConfig;
import org.ldaptive.ssl.KeyStoreCredentialReader;
import org.ldaptive.ssl.KeyStoreSSLContextInitializer;
import org.ldaptive.ssl.SSLContextInitializer;

public class KeyStoreCredentialConfig
implements CredentialConfig {
    private static final int HASH_CODE_SEED = 1013;
    private final KeyStoreCredentialReader keyStoreReader = new KeyStoreCredentialReader();
    private String trustStore;
    private String trustStorePassword;
    private String trustStoreType;
    private String[] trustStoreAliases;
    private String keyStore;
    private String keyStorePassword;
    private String keyStoreType;
    private String[] keyStoreAliases;

    public String getTrustStore() {
        return this.trustStore;
    }

    public void setTrustStore(String name) {
        this.trustStore = name;
    }

    public String getTrustStorePassword() {
        return this.trustStorePassword;
    }

    public void setTrustStorePassword(String password) {
        this.trustStorePassword = password;
    }

    public String getTrustStoreType() {
        return this.trustStoreType;
    }

    public void setTrustStoreType(String type) {
        this.trustStoreType = type;
    }

    public String[] getTrustStoreAliases() {
        return this.trustStoreAliases;
    }

    public void setTrustStoreAliases(String ... aliases) {
        this.trustStoreAliases = aliases;
    }

    public String getKeyStore() {
        return this.keyStore;
    }

    public void setKeyStore(String name) {
        this.keyStore = name;
    }

    public String getKeyStorePassword() {
        return this.keyStorePassword;
    }

    public void setKeyStorePassword(String password) {
        this.keyStorePassword = password;
    }

    public String getKeyStoreType() {
        return this.keyStoreType;
    }

    public void setKeyStoreType(String type) {
        this.keyStoreType = type;
    }

    public String[] getKeyStoreAliases() {
        return this.keyStoreAliases;
    }

    public void setKeyStoreAliases(String ... aliases) {
        this.keyStoreAliases = aliases;
    }

    @Override
    public SSLContextInitializer createSSLContextInitializer() throws GeneralSecurityException {
        KeyStoreSSLContextInitializer sslInit = new KeyStoreSSLContextInitializer();
        try {
            if (this.trustStore != null) {
                sslInit.setTrustKeystore((KeyStore)this.keyStoreReader.read(this.trustStore, this.trustStorePassword, this.trustStoreType));
                sslInit.setTrustAliases(this.trustStoreAliases);
            }
            if (this.keyStore != null) {
                sslInit.setAuthenticationKeystore((KeyStore)this.keyStoreReader.read(this.keyStore, this.keyStorePassword, this.keyStoreType));
                sslInit.setAuthenticationPassword(this.keyStorePassword != null ? this.keyStorePassword.toCharArray() : null);
                sslInit.setAuthenticationAliases(this.keyStoreAliases);
            }
        }
        catch (IOException e) {
            throw new GeneralSecurityException(e);
        }
        return sslInit;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof KeyStoreCredentialConfig) {
            KeyStoreCredentialConfig v = (KeyStoreCredentialConfig)o;
            return LdapUtils.areEqual(this.trustStore, v.trustStore) && LdapUtils.areEqual(this.trustStoreType, v.trustStoreType) && LdapUtils.areEqual(this.trustStorePassword, v.trustStorePassword) && LdapUtils.areEqual(this.trustStoreAliases, v.trustStoreAliases) && LdapUtils.areEqual(this.keyStore, v.keyStore) && LdapUtils.areEqual(this.keyStoreType, v.keyStoreType) && LdapUtils.areEqual(this.keyStorePassword, v.keyStorePassword) && LdapUtils.areEqual(this.keyStoreAliases, v.keyStoreAliases);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(1013, this.trustStore, this.trustStoreType, this.trustStorePassword, this.trustStoreAliases, this.keyStore, this.keyStoreType, this.keyStorePassword, this.keyStoreAliases);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "trustStore=" + this.trustStore + ", " + "trustStoreType=" + this.trustStoreType + ", " + "trustStoreAliases=" + Arrays.toString(this.trustStoreAliases) + ", " + "keyStore=" + this.keyStore + ", " + "keyStoreType=" + this.keyStoreType + ", " + "keyStoreAliases=" + Arrays.toString(this.keyStoreAliases) + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final KeyStoreCredentialConfig object = new KeyStoreCredentialConfig();

        protected Builder() {
        }

        public Builder trustStore(String name) {
            this.object.setTrustStore(name);
            return this;
        }

        public Builder trustStorePassword(String password) {
            this.object.setTrustStorePassword(password);
            return this;
        }

        public Builder trustStoreType(String type) {
            this.object.setTrustStoreType(type);
            return this;
        }

        public Builder trustStoreAliases(String ... aliases) {
            this.object.setTrustStoreAliases(aliases);
            return this;
        }

        public Builder keyStore(String name) {
            this.object.setKeyStore(name);
            return this;
        }

        public Builder keyStorePassword(String password) {
            this.object.setKeyStorePassword(password);
            return this;
        }

        public Builder keyStoreType(String type) {
            this.object.setKeyStoreType(type);
            return this;
        }

        public Builder keyStoreAliases(String ... aliases) {
            this.object.setKeyStoreAliases(aliases);
            return this;
        }

        public KeyStoreCredentialConfig build() {
            return this.object;
        }
    }
}

