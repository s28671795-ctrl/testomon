/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.security.Key;
import java.security.Principal;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.Subject;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.UsernameTokenPrincipal;
import org.apache.wss4j.common.util.UsernameTokenUtil;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class UsernameSecurityTokenImpl
extends AbstractInboundSecurityToken
implements UsernameSecurityToken {
    private static final long DEFAULT_ITERATION = 1000L;
    private WSSConstants.UsernameTokenPasswordType usernameTokenPasswordType;
    private String username;
    private String password;
    private String createdTime;
    private byte[] nonce;
    private byte[] salt;
    private Long iteration;
    private final WSInboundSecurityContext wsInboundSecurityContext;
    private Subject subject;
    private Principal principal;

    public UsernameSecurityTokenImpl(WSSConstants.UsernameTokenPasswordType usernameTokenPasswordType, String username, String password, String createdTime, byte[] nonce, byte[] salt, Long iteration, WSInboundSecurityContext wsInboundSecurityContext, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier) {
        super(wsInboundSecurityContext, id, keyIdentifier, true);
        this.usernameTokenPasswordType = usernameTokenPasswordType;
        this.username = username;
        this.password = password;
        this.createdTime = createdTime;
        this.nonce = nonce;
        this.salt = salt;
        this.iteration = iteration;
        this.wsInboundSecurityContext = wsInboundSecurityContext;
    }

    @Override
    public boolean isAsymmetric() throws XMLSecurityException {
        return false;
    }

    @Override
    protected Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
        Key key = this.getSecretKey().get(algorithmURI);
        if (key != null) {
            return key;
        }
        byte[] secretToken = this.generateDerivedKey(this.wsInboundSecurityContext);
        String algoFamily = JCEAlgorithmMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
        key = new SecretKeySpec(secretToken, algoFamily);
        this.setSecretKey(algorithmURI, key);
        return key;
    }

    @Override
    public SecurityTokenConstants.TokenType getTokenType() {
        return WSSecurityTokenConstants.USERNAME_TOKEN;
    }

    @Override
    public byte[] generateDerivedKey() throws WSSecurityException {
        return this.generateDerivedKey(this.wsInboundSecurityContext);
    }

    protected byte[] generateDerivedKey(WSInboundSecurityContext wsInboundSecurityContext) throws WSSecurityException {
        if (wsInboundSecurityContext != null) {
            if (this.salt == null || this.salt.length == 0) {
                wsInboundSecurityContext.handleBSPRule(BSPRule.R4217);
            }
            if (this.iteration == null || this.iteration < 1000L) {
                wsInboundSecurityContext.handleBSPRule(BSPRule.R4218);
            }
        }
        return UsernameTokenUtil.generateDerivedKey(this.password, this.salt, this.iteration.intValue());
    }

    @Override
    public Principal getPrincipal() throws WSSecurityException {
        if (this.principal == null) {
            this.principal = new UsernameTokenPrincipal(){

                @Override
                public boolean isPasswordDigest() {
                    return UsernameSecurityTokenImpl.this.usernameTokenPasswordType == WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST;
                }

                @Override
                public String getPasswordType() {
                    return UsernameSecurityTokenImpl.this.usernameTokenPasswordType.getNamespace();
                }

                @Override
                public String getName() {
                    return UsernameSecurityTokenImpl.this.username;
                }

                @Override
                public String getPassword() {
                    return UsernameSecurityTokenImpl.this.password;
                }

                @Override
                public String getCreatedTime() {
                    return UsernameSecurityTokenImpl.this.createdTime;
                }

                @Override
                public byte[] getNonce() {
                    return UsernameSecurityTokenImpl.this.nonce;
                }
            };
        }
        return this.principal;
    }

    @Override
    public WSSConstants.UsernameTokenPasswordType getUsernameTokenPasswordType() {
        return this.usernameTokenPasswordType;
    }

    @Override
    public String getCreatedTime() {
        return this.createdTime;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public byte[] getNonce() {
        return this.nonce;
    }

    @Override
    public byte[] getSalt() {
        return this.salt;
    }

    @Override
    public Long getIteration() {
        return this.iteration;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Subject getSubject() throws WSSecurityException {
        return this.subject;
    }
}

