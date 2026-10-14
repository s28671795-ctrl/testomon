/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.io.IOException;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.w3c.dom.Element;

public class ExternalSecurityTokenImpl
extends AbstractInboundSecurityToken {
    private Element tokenElement;
    private byte[] key;

    public ExternalSecurityTokenImpl(WSInboundSecurityContext wsInboundSecurityContext, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier, WSSSecurityProperties securityProperties, boolean included) throws WSSecurityException {
        super(wsInboundSecurityContext, id, keyIdentifier, included);
        if (securityProperties.getCallbackHandler() != null) {
            WSPasswordCallback pwcb = new WSPasswordCallback(id, 7);
            try {
                securityProperties.getCallbackHandler().handle(new Callback[]{pwcb});
            }
            catch (IOException | UnsupportedCallbackException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "noPassword");
            }
            this.tokenElement = pwcb.getCustomToken();
            this.key = pwcb.getKey();
        }
        if (this.tokenElement == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, "noToken", new Object[]{id});
        }
    }

    @Override
    protected Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
        String keyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
        return new SecretKeySpec(this.key, keyAlgorithm);
    }

    @Override
    public SecurityTokenConstants.TokenType getTokenType() {
        if ("SecurityContextToken".equals(this.tokenElement.getLocalName())) {
            return WSSecurityTokenConstants.SECURITY_CONTEXT_TOKEN;
        }
        return null;
    }
}

