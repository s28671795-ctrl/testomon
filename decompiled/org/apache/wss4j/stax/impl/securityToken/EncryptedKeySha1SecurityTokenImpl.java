/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.securityToken.EncryptedKeySha1SecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class EncryptedKeySha1SecurityTokenImpl
extends AbstractInboundSecurityToken
implements EncryptedKeySha1SecurityToken {
    private CallbackHandler callbackHandler;

    public EncryptedKeySha1SecurityTokenImpl(WSInboundSecurityContext inboundSecurityContext, CallbackHandler callbackHandler, String sha1Identifier, String id) {
        super(inboundSecurityContext, id, WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER, false);
        this.callbackHandler = callbackHandler;
        this.setSha1Identifier(sha1Identifier);
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
        WSPasswordCallback secretKeyCallback = new WSPasswordCallback(this.getSha1Identifier(), null, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1", 9);
        WSSUtils.doSecretKeyCallback(this.callbackHandler, secretKeyCallback, this.getSha1Identifier());
        if (secretKeyCallback.getKey() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noKey", new Object[]{this.getSha1Identifier()});
        }
        String keyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
        key = new SecretKeySpec(secretKeyCallback.getKey(), keyAlgorithm);
        this.setSecretKey(algorithmURI, key);
        return key;
    }

    @Override
    public SecurityTokenConstants.TokenType getTokenType() {
        return WSSecurityTokenConstants.EncryptedKeyToken;
    }
}

