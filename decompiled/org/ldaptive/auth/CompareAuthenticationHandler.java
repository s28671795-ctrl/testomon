/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.ldaptive.CompareRequest;
import org.ldaptive.CompareResponse;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.Credential;
import org.ldaptive.LdapException;
import org.ldaptive.LdapUtils;
import org.ldaptive.ResultCode;
import org.ldaptive.auth.AbstractAuthenticationHandler;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.ldaptive.auth.AuthenticationResultCode;

public class CompareAuthenticationHandler
extends AbstractAuthenticationHandler {
    protected static final String DEFAULT_SCHEME = "SHA:SHA";
    protected static final String DEFAULT_ATTRIBUTE = "userPassword";
    private Scheme passwordScheme = new Scheme("SHA:SHA");
    private String passwordAttribute = "userPassword";

    public CompareAuthenticationHandler() {
    }

    public CompareAuthenticationHandler(ConnectionFactory cf) {
        this.setConnectionFactory(cf);
    }

    public String getPasswordScheme() {
        return this.passwordScheme.toString();
    }

    public void setPasswordScheme(String s2) {
        this.passwordScheme = new Scheme(s2);
    }

    public String getPasswordAttribute() {
        return this.passwordAttribute;
    }

    public void setPasswordAttribute(String s2) {
        this.passwordAttribute = s2;
    }

    @Override
    protected AuthenticationHandlerResponse authenticateInternal(Connection c, AuthenticationCriteria criteria) throws LdapException {
        CompareResponse compareResponse;
        byte[] hash = this.digestCredential(criteria.getCredential(), this.passwordScheme.getAlgorithm());
        return new AuthenticationHandlerResponse(compareResponse, (compareResponse = c.operation((CompareRequest)((CompareRequest.Builder)CompareRequest.builder().controls(this.processRequestControls(criteria))).dn(criteria.getDn()).name(this.passwordAttribute).value(String.format("{%s}%s", this.passwordScheme.getLabel(), LdapUtils.base64Encode(hash))).build()).execute()).isTrue() ? AuthenticationResultCode.AUTHENTICATION_HANDLER_SUCCESS : AuthenticationResultCode.AUTHENTICATION_HANDLER_FAILURE, c);
    }

    protected byte[] digestCredential(Credential credential, String algorithm) throws LdapException {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(credential.getBytes());
            return md.digest();
        }
        catch (NoSuchAlgorithmException e) {
            throw new LdapException(ResultCode.AUTH_UNKNOWN, (Throwable)e);
        }
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "factory=" + this.getConnectionFactory() + ", " + "passwordAttribute=" + this.passwordAttribute + ", " + "passwordScheme=" + this.passwordScheme + ", " + "controls=" + Arrays.toString(this.getAuthenticationControls()) + "]";
    }

    public static class Scheme {
        private final String label;
        private final String algorithm;

        public Scheme(String labelAndAlgorithm) {
            String[] s2 = labelAndAlgorithm.split(":", 2);
            this.label = s2[0];
            this.algorithm = s2.length == 2 ? s2[1] : s2[0];
        }

        public Scheme(String l, String a) {
            this.label = l;
            this.algorithm = a;
        }

        public String getLabel() {
            return this.label;
        }

        public String getAlgorithm() {
            return this.algorithm;
        }

        public String toString() {
            return String.format("%s:%s", this.label, this.algorithm);
        }
    }
}

