/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import org.apache.wss4j.binding.wssc.AbstractSecurityContextTokenType;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.wss4j.stax.validate.SecurityContextTokenValidator;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class SecurityContextTokenValidatorImpl
implements SecurityContextTokenValidator {
    @Override
    public InboundSecurityToken validate(final AbstractSecurityContextTokenType securityContextTokenType, final String identifier, final TokenContext tokenContext) throws WSSecurityException {
        AbstractInboundSecurityToken securityContextToken = new AbstractInboundSecurityToken(tokenContext.getWsSecurityContext(), identifier, WSSecurityTokenConstants.KEYIDENTIFIER_EXTERNAL_REFERENCE, true){

            @Override
            public boolean isAsymmetric() {
                return false;
            }

            @Override
            public Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
                Key key = this.getSecretKey().get(algorithmURI);
                if (key != null) {
                    return key;
                }
                WSPasswordCallback passwordCallback = new WSPasswordCallback(identifier, 6);
                WSSUtils.doSecretKeyCallback(tokenContext.getWssSecurityProperties().getCallbackHandler(), passwordCallback, null);
                if (passwordCallback.getKey() == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, "noKey", new Object[]{securityContextTokenType.getId()});
                }
                String keyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
                key = new SecretKeySpec(passwordCallback.getKey(), keyAlgorithm);
                this.setSecretKey(algorithmURI, key);
                return key;
            }

            @Override
            public SecurityTokenConstants.TokenType getTokenType() {
                return WSSecurityTokenConstants.SECURITY_CONTEXT_TOKEN;
            }
        };
        securityContextToken.setElementPath(tokenContext.getElementPath());
        securityContextToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
        return securityContextToken;
    }
}

