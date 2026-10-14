/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.trust.STSTokenCacher;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.util.XMLUtils;
import org.w3c.dom.Element;

public class DefaultSTSTokenCacher
implements STSTokenCacher {
    @Override
    public SecurityToken retrieveToken(Message message, boolean retrieveTokenFromEndpoint) {
        SecurityToken tok = null;
        if (retrieveTokenFromEndpoint) {
            String tokId;
            tok = (SecurityToken)message.getContextualProperty("ws-security.token");
            if (tok == null && (tokId = (String)message.getContextualProperty("ws-security.token.id")) != null) {
                tok = TokenStoreUtils.getTokenStore(message).getToken(tokId);
            }
        } else {
            String tokId;
            tok = (SecurityToken)message.get("ws-security.token");
            if (tok == null && (tokId = (String)message.get("ws-security.token.id")) != null) {
                tok = TokenStoreUtils.getTokenStore(message).getToken(tokId);
            }
        }
        return tok;
    }

    @Override
    public SecurityToken retrieveToken(Message message, Element delegationToken, String cacheKey) {
        String associatedToken;
        SecurityToken issuedToken;
        Map<String, Object> properties;
        String id;
        if (delegationToken == null) {
            return null;
        }
        TokenStore tokenStore = TokenStoreUtils.getTokenStore(message);
        SecurityToken cachedToken = tokenStore.getToken(id = DefaultSTSTokenCacher.getIdFromToken(delegationToken));
        if (cachedToken != null && (properties = cachedToken.getProperties()) != null && properties.containsKey(cacheKey) && (issuedToken = tokenStore.getToken(associatedToken = (String)properties.get(cacheKey))) != null) {
            return issuedToken;
        }
        return null;
    }

    @Override
    public void storeToken(Message message, SecurityToken securityToken, boolean storeTokenInEndpoint) {
        if (storeTokenInEndpoint && !DefaultSTSTokenCacher.isOneTimeUse(securityToken)) {
            message.getExchange().getEndpoint().put("ws-security.token", securityToken);
            message.getExchange().put("ws-security.token", securityToken);
            message.getExchange().put("ws-security.token.id", securityToken.getId());
            message.getExchange().getEndpoint().put("ws-security.token.id", securityToken.getId());
        } else {
            message.put("ws-security.token", securityToken);
            message.put("ws-security.token.id", securityToken.getId());
            message.put("ws-security.token.element", securityToken.getToken());
        }
        TokenStoreUtils.getTokenStore(message).add(securityToken);
    }

    @Override
    public void storeToken(Message message, Element delegationToken, String secTokenId, String cacheKey) {
        Map<String, Object> properties;
        String id;
        if (secTokenId == null || delegationToken == null) {
            return;
        }
        TokenStore tokenStore = TokenStoreUtils.getTokenStore(message);
        SecurityToken cachedToken = tokenStore.getToken(id = DefaultSTSTokenCacher.getIdFromToken(delegationToken));
        if (cachedToken == null) {
            cachedToken = new SecurityToken(id);
            cachedToken.setToken(delegationToken);
        }
        if ((properties = cachedToken.getProperties()) == null) {
            properties = new HashMap<String, Object>();
            cachedToken.setProperties(properties);
        }
        properties.put(cacheKey, secTokenId);
        tokenStore.add(cachedToken);
    }

    @Override
    public void removeToken(Message message, SecurityToken securityToken) {
        message.getExchange().getEndpoint().remove("ws-security.token");
        message.getExchange().getEndpoint().remove("ws-security.token.id");
        message.getExchange().remove("ws-security.token.id");
        message.getExchange().remove("ws-security.token");
        message.resetContextCache();
        if (securityToken != null) {
            TokenStoreUtils.getTokenStore(message).remove(securityToken.getId());
        }
    }

    private static boolean isOneTimeUse(SecurityToken issuedToken) {
        Element token = issuedToken.getToken();
        if (token != null && "Assertion".equals(token.getLocalName()) && "urn:oasis:names:tc:SAML:2.0:assertion".equals(token.getNamespaceURI())) {
            try {
                SamlAssertionWrapper assertion = new SamlAssertionWrapper(token);
                if (assertion.getSaml2().getConditions() != null && assertion.getSaml2().getConditions().getOneTimeUse() != null) {
                    return true;
                }
            }
            catch (WSSecurityException ex) {
                throw new Fault(ex);
            }
        }
        return false;
    }

    private static String getIdFromToken(Element token) {
        if (token != null) {
            String text;
            Element usernameElement;
            if ("Assertion".equals(token.getLocalName()) && "urn:oasis:names:tc:SAML:2.0:assertion".equals(token.getNamespaceURI())) {
                return token.getAttributeNS(null, "ID");
            }
            if ("Assertion".equals(token.getLocalName()) && "urn:oasis:names:tc:SAML:1.0:assertion".equals(token.getNamespaceURI())) {
                return token.getAttributeNS(null, "AssertionID");
            }
            if ("UsernameToken".equals(token.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(token.getNamespaceURI()) && (usernameElement = XMLUtils.getDirectChildElement(token, "Username", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")) != null) {
                return XMLUtils.getElementText(usernameElement);
            }
            if ("BinarySecurityToken".equals(token.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(token.getNamespaceURI()) && (text = XMLUtils.getElementText(token)) != null && !"".equals(text)) {
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] bytes = digest.digest(text.getBytes());
                    return org.apache.xml.security.utils.XMLUtils.encodeToString(bytes);
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    // empty catch block
                }
            }
        }
        return "";
    }
}

