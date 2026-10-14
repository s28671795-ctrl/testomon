/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import org.apache.wss4j.common.util.UsernameTokenUtil;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.impl.securityToken.GenericOutboundSecurityToken;

public class OutboundUsernameSecurityToken
extends GenericOutboundSecurityToken {
    private String username;
    private String password;
    private String createdTime;
    private byte[] nonce;
    private byte[] salt;
    private int iterations;

    public OutboundUsernameSecurityToken(String username, String password, String createdTime, byte[] nonce, String id, byte[] salt, int iterations) {
        super(id, WSSecurityTokenConstants.USERNAME_TOKEN);
        this.username = username;
        this.password = password;
        this.createdTime = createdTime;
        this.nonce = nonce;
        this.salt = salt;
        this.iterations = iterations;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getCreated() {
        return this.createdTime;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    @Override
    public Key getSecretKey(String algorithmURI) throws XMLSecurityException {
        Key key = super.getSecretKey(algorithmURI);
        if (key != null) {
            return key;
        }
        byte[] secretToken = UsernameTokenUtil.generateDerivedKey(this.getPassword(), this.salt, this.iterations);
        String algoFamily = JCEAlgorithmMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
        key = new SecretKeySpec(secretToken, algoFamily);
        this.setSecretKey(algorithmURI, key);
        return key;
    }
}

