/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.SAMLTokenPrincipalImpl;
import org.apache.wss4j.common.principal.WSUsernameTokenPrincipalImpl;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class UsernameTokenProcessor
implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(UsernameTokenProcessor.class);

    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        LOG.debug("Found UsernameToken list element");
        String id = elem.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
        if (!"".equals(id)) {
            Element foundElement = data.getWsDocInfo().getTokenElement(id);
            if (elem.equals(foundElement)) {
                WSSecurityEngineResult result = data.getWsDocInfo().getResult(id);
                return Collections.singletonList(result);
            }
            if (foundElement != null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "duplicateError");
            }
        }
        Validator validator = data.getValidator(WSConstants.USERNAME_TOKEN);
        Credential credential = this.handleUsernameToken(elem, validator, data);
        UsernameToken token = credential.getUsernametoken();
        int action = 1;
        byte[] secretKey = null;
        if (token.getPassword() == null) {
            action = 8192;
            if (token.isDerivedKey()) {
                token.setRawPassword(data.getCallbackHandler());
                secretKey = token.getDerivedKey(data.getBSPEnforcer());
            }
        }
        WSSecurityEngineResult result = new WSSecurityEngineResult(action, token);
        String tokenId = token.getID();
        if (!"".equals(tokenId)) {
            result.put("id", tokenId);
        }
        result.put("secret", secretKey);
        if (validator != null) {
            result.put("validated-token", Boolean.TRUE);
            if (credential.getTransformedToken() != null) {
                result.put("transformed-token", credential.getTransformedToken());
                if (credential.getPrincipal() != null) {
                    result.put("principal", credential.getPrincipal());
                } else {
                    SAMLTokenPrincipalImpl samlPrincipal = new SAMLTokenPrincipalImpl(credential.getTransformedToken());
                    result.put("principal", samlPrincipal);
                }
            } else if (credential.getPrincipal() != null) {
                result.put("principal", credential.getPrincipal());
            } else {
                WSUsernameTokenPrincipalImpl principal = new WSUsernameTokenPrincipalImpl(token.getName(), token.isHashed());
                if (token.getNonce() != null) {
                    principal.setNonce(XMLUtils.decode(token.getNonce()));
                }
                principal.setPassword(token.getPassword());
                principal.setCreatedTime(token.getCreated());
                principal.setPasswordType(token.getPasswordType());
                result.put("principal", principal);
            }
            result.put("subject", credential.getSubject());
        }
        data.getWsDocInfo().addTokenElement(elem);
        data.getWsDocInfo().addResult(result);
        return Collections.singletonList(result);
    }

    private Credential handleUsernameToken(Element token, Validator validator, RequestData data) throws WSSecurityException {
        boolean allowNamespaceQualifiedPasswordTypes = data.isAllowNamespaceQualifiedPasswordTypes();
        int utTTL = data.getUtTTL();
        int futureTimeToLive = data.getUtFutureTTL();
        UsernameToken ut = new UsernameToken(token, allowNamespaceQualifiedPasswordTypes, data.getBSPEnforcer());
        ReplayCache replayCache = data.getNonceReplayCache();
        if (replayCache != null && ut.getNonce() != null) {
            if (replayCache.contains(ut.getNonce())) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "badUsernameToken", new Object[]{"A replay attack has been detected"});
            }
            Instant created = ut.getCreatedDate();
            if (created == null || utTTL <= 0) {
                replayCache.add(ut.getNonce());
            } else {
                replayCache.add(ut.getNonce(), (long)utTTL + 1L);
            }
        }
        if (!ut.verifyCreated(utTTL, futureTimeToLive)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.MESSAGE_EXPIRED);
        }
        Credential credential = new Credential();
        credential.setUsernametoken(ut);
        if (validator != null) {
            return validator.validate(credential, data);
        }
        return credential;
    }
}

