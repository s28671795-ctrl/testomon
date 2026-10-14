/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.trust.DefaultSTSTokenCacher;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.cxf.ws.security.trust.STSTokenCacher;
import org.apache.cxf.ws.security.trust.STSUtils;
import org.apache.wss4j.policy.model.Trust10;
import org.apache.wss4j.policy.model.Trust13;
import org.w3c.dom.Element;

public final class STSTokenRetriever {
    private static final Logger LOG = LogUtils.getL7dLogger(STSTokenRetriever.class);
    private static final String ASSOCIATED_TOKEN = STSTokenRetriever.class.getName() + "-Associated_Token";

    private STSTokenRetriever() {
    }

    public static SecurityToken getToken(Message message, TokenRequestParams params) {
        return STSTokenRetriever.getToken(message, params, new DefaultSTSTokenCacher());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SecurityToken getToken(Message message, TokenRequestParams params, STSTokenCacher tokenCacher) {
        STSClient client;
        String appliesTo;
        Object o = SecurityUtils.getSecurityPropertyValue("security.sts.applies-to", message);
        String string = appliesTo = o == null ? null : o.toString();
        if (appliesTo == null) {
            String endpointAddress = message.getContextualProperty(Message.ENDPOINT_ADDRESS).toString();
            int query = endpointAddress.indexOf(63);
            if (query > 0) {
                endpointAddress = endpointAddress.substring(0, query);
            }
            appliesTo = endpointAddress;
        }
        STSClient sTSClient = client = STSUtils.getClientWithIssuer(message, "sts", params.getIssuer());
        synchronized (sTSClient) {
            try {
                boolean cacheToken;
                SecurityToken secToken;
                client.setMessage(message);
                Object token = SecurityUtils.getSecurityPropertyValue("security.sts.token.act-as", message);
                if (token != null) {
                    client.setActAs(token);
                }
                if ((token = SecurityUtils.getSecurityPropertyValue("security.sts.token.on-behalf-of", message)) != null) {
                    client.setOnBehalfOf(token);
                }
                boolean enableAppliesTo = client.isEnableAppliesTo();
                Element onBehalfOfToken = client.getOnBehalfOfToken();
                Element actAsToken = client.getActAsToken();
                String key = appliesTo;
                if (!enableAppliesTo || key == null || key.isEmpty()) {
                    key = ASSOCIATED_TOKEN;
                }
                if ((secToken = tokenCacher.retrieveToken(message, cacheToken = STSTokenRetriever.isCachedTokenFromEndpoint(message, onBehalfOfToken, actAsToken))) == null && onBehalfOfToken != null) {
                    secToken = tokenCacher.retrieveToken(message, onBehalfOfToken, key);
                }
                if (secToken == null && actAsToken != null) {
                    secToken = tokenCacher.retrieveToken(message, actAsToken, key);
                }
                if ((secToken = secToken != null ? STSTokenRetriever.renewToken(message, secToken, params, tokenCacher) : STSTokenRetriever.getTokenFromSTS(message, client, appliesTo, params)) != null) {
                    tokenCacher.storeToken(message, onBehalfOfToken, secToken.getId(), key);
                    tokenCacher.storeToken(message, actAsToken, secToken.getId(), key);
                    tokenCacher.storeToken(message, secToken, cacheToken);
                }
                SecurityToken securityToken = secToken;
                return securityToken;
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new Fault(e);
            }
            finally {
                client.setTrust(null);
                client.setTrust(null);
                client.setTemplate(null);
                client.setAddressingNamespace(null);
            }
        }
    }

    private static boolean isCachedTokenFromEndpoint(Message message, Element onBehalfOfToken, Element actAsToken) {
        if (onBehalfOfToken != null || actAsToken != null) {
            return false;
        }
        return SecurityUtils.getSecurityPropertyBoolean("security.cache.issued.token.in.endpoint", message, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static SecurityToken renewToken(Message message, SecurityToken tok, TokenRequestParams params, STSTokenCacher tokenCacher) {
        String imminentExpiryValue = (String)SecurityUtils.getSecurityPropertyValue("security.sts.token.imminent-expiry-value", message);
        long imminentExpiry = 10L;
        if (imminentExpiryValue != null) {
            imminentExpiry = Long.parseLong(imminentExpiryValue);
        }
        if (!tok.isExpired() && !tok.isAboutToExpire(imminentExpiry)) {
            return tok;
        }
        tokenCacher.removeToken(message, tok);
        STSClient client = STSUtils.getClientWithIssuer(message, "sts", params.getIssuer());
        if (!client.isAllowRenewing()) {
            return STSTokenRetriever.getToken(message, params, tokenCacher);
        }
        STSClient sTSClient = client;
        synchronized (sTSClient) {
            try {
                Map<String, Object> ctx = client.getRequestContext();
                STSTokenRetriever.mapSecurityProps(message, ctx);
                client.setMessage(message);
                String addressingNamespace = STSTokenRetriever.getAddressingNamespaceURI(message);
                if (addressingNamespace != null) {
                    client.setAddressingNamespace(addressingNamespace);
                }
                client.setTrust(params.getTrust10());
                client.setTrust(params.getTrust13());
                client.setTemplate(params.getTokenTemplate());
                SecurityToken securityToken = client.renewSecurityToken(tok);
                return securityToken;
            }
            catch (RuntimeException ex) {
                LOG.log(Level.WARNING, "Error renewing a token", ex);
                boolean issueAfterFailedRenew = SecurityUtils.getSecurityPropertyBoolean("security.issue.after.failed.renew", message, true);
                if (issueAfterFailedRenew) {
                    SecurityToken securityToken = STSTokenRetriever.getToken(message, params, tokenCacher);
                    return securityToken;
                }
                throw ex;
            }
            catch (Exception ex) {
                LOG.log(Level.WARNING, "Error renewing a token", ex);
                boolean issueAfterFailedRenew = SecurityUtils.getSecurityPropertyBoolean("security.issue.after.failed.renew", message, true);
                if (issueAfterFailedRenew) {
                    SecurityToken securityToken = STSTokenRetriever.getToken(message, params, tokenCacher);
                    return securityToken;
                }
                throw new Fault(ex);
            }
            finally {
                client.setTrust(null);
                client.setTrust(null);
                client.setTemplate(null);
                client.setAddressingNamespace(null);
            }
        }
    }

    private static String getAddressingNamespaceURI(Message message) {
        AddressingProperties maps = (AddressingProperties)message.get("javax.xml.ws.addressing.context.outbound");
        if (maps == null) {
            maps = (AddressingProperties)message.get("javax.xml.ws.addressing.context");
        }
        if (maps != null) {
            return maps.getNamespaceURI();
        }
        return null;
    }

    private static void mapSecurityProps(Message message, Map<String, Object> ctx) {
        for (String s2 : SecurityConstants.ALL_PROPERTIES) {
            Object v = message.getContextualProperty(s2 + ".it");
            if (v == null) {
                v = message.getContextualProperty(s2);
            }
            if (ctx.containsKey(s2) || v == null) continue;
            ctx.put(s2, v);
        }
    }

    private static SecurityToken getTokenFromSTS(Message message, STSClient client, String appliesTo, TokenRequestParams params) throws Exception {
        String addressingNamespace;
        client.setTrust(params.getTrust10());
        client.setTrust(params.getTrust13());
        client.setTemplate(params.getTokenTemplate());
        if (params.getWspNamespace() != null) {
            client.setWspNamespace(params.getWspNamespace());
        }
        if ((addressingNamespace = STSTokenRetriever.getAddressingNamespaceURI(message)) != null) {
            client.setAddressingNamespace(addressingNamespace);
        }
        if (params.getClaims() != null) {
            client.setClaims(params.getClaims());
        }
        Map<String, Object> ctx = client.getRequestContext();
        STSTokenRetriever.mapSecurityProps(message, ctx);
        return client.requestSecurityToken(appliesTo);
    }

    public static class TokenRequestParams {
        private Element issuer;
        private Trust10 trust10;
        private Trust13 trust13;
        private Element tokenTemplate;
        private String wspNamespace;
        private Element claims;

        public Element getIssuer() {
            return this.issuer;
        }

        public void setIssuer(Element issuer) {
            this.issuer = issuer;
        }

        public Trust10 getTrust10() {
            return this.trust10;
        }

        public void setTrust10(Trust10 trust10) {
            this.trust10 = trust10;
        }

        public Trust13 getTrust13() {
            return this.trust13;
        }

        public void setTrust13(Trust13 trust13) {
            this.trust13 = trust13;
        }

        public Element getTokenTemplate() {
            return this.tokenTemplate;
        }

        public void setTokenTemplate(Element tokenTemplate) {
            this.tokenTemplate = tokenTemplate;
        }

        public String getWspNamespace() {
            return this.wspNamespace;
        }

        public void setWspNamespace(String wspNamespace) {
            this.wspNamespace = wspNamespace;
        }

        public Element getClaims() {
            return this.claims;
        }

        public void setClaims(Element claims) {
            this.claims = claims;
        }
    }
}

