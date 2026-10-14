/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.policy.interceptors.IssuedTokenInterceptorProvider;
import org.apache.cxf.ws.security.policy.interceptors.SecureConversationTokenInterceptorProvider;
import org.apache.cxf.ws.security.policy.interceptors.SpnegoContextTokenOutInterceptor;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.cxf.ws.security.trust.STSUtils;
import org.apache.wss4j.policy.model.SecureConversationToken;

class SecureConversationOutInterceptor
extends AbstractPhaseInterceptor<SoapMessage> {
    private static final Logger LOG = LogUtils.getL7dLogger(SecureConversationOutInterceptor.class);

    SecureConversationOutInterceptor() {
        super("prepare-send");
        this.addBefore(SpnegoContextTokenOutInterceptor.class.getName());
        this.addBefore(IssuedTokenInterceptorProvider.IssuedTokenOutInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (aim != null) {
            Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SecureConversationToken");
            if (ais.isEmpty()) {
                return;
            }
            if (this.isRequestor(message)) {
                String tokId;
                SecureConversationToken itok = (SecureConversationToken)ais.iterator().next().getAssertion();
                SecurityToken tok = (SecurityToken)message.getContextualProperty("ws-security.token");
                if (tok == null && (tokId = (String)message.getContextualProperty("ws-security.token.id")) != null) {
                    tok = TokenStoreUtils.getTokenStore(message).getToken(tokId);
                }
                if ((tok = tok == null ? this.issueToken(message, aim, itok) : this.renewToken(message, aim, tok, itok)) != null) {
                    for (AssertionInfo ai : ais) {
                        ai.setAsserted(true);
                    }
                    message.getExchange().getEndpoint().put("ws-security.token", tok);
                    message.getExchange().getEndpoint().put("ws-security.token.id", tok.getId());
                    message.getExchange().put("ws-security.token.id", tok.getId());
                    message.getExchange().put("ws-security.token", tok);
                    TokenStoreUtils.getTokenStore(message).add(tok);
                }
                PolicyUtils.assertPolicy(aim, "BootstrapPolicy");
            } else {
                for (AssertionInfo ai : ais) {
                    ai.setAsserted(true);
                }
                PolicyUtils.assertPolicy(aim, "BootstrapPolicy");
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SecurityToken renewToken(SoapMessage message, AssertionInfoMap aim, SecurityToken tok, SecureConversationToken itok) {
        if (!tok.isExpired()) {
            return tok;
        }
        message.getExchange().getEndpoint().remove("ws-security.token");
        message.getExchange().getEndpoint().remove("ws-security.token.id");
        message.getExchange().remove("ws-security.token.id");
        message.getExchange().remove("ws-security.token");
        TokenStoreUtils.getTokenStore(message).remove(tok.getId());
        STSClient client = STSUtils.getClient(message, "sct");
        AddressingProperties maps = (AddressingProperties)message.get("javax.xml.ws.addressing.context.outbound");
        if (maps == null) {
            maps = (AddressingProperties)message.get("javax.xml.ws.addressing.context");
        } else if (maps.getAction().getValue().endsWith("Renew")) {
            return tok;
        }
        STSClient sTSClient = client;
        synchronized (sTSClient) {
            try {
                SecureConversationTokenInterceptorProvider.setupClient(client, message, aim, itok, true);
                String s2 = message.getContextualProperty(Message.ENDPOINT_ADDRESS).toString();
                client.setLocation(s2);
                Map<String, Object> ctx = client.getRequestContext();
                ctx.put("ws-security.token.id", tok.getId());
                if (maps != null) {
                    client.setAddressingNamespace(maps.getNamespaceURI());
                }
                SecurityToken securityToken = client.renewSecurityToken(tok);
                return securityToken;
            }
            catch (RuntimeException ex) {
                LOG.log(Level.WARNING, "Error renewing a token", ex);
                boolean issueAfterFailedRenew = MessageUtils.getContextualBoolean(message, "security.issue.after.failed.renew", true);
                if (issueAfterFailedRenew) {
                    SecurityToken securityToken = this.issueToken(message, aim, itok);
                    return securityToken;
                }
                throw ex;
            }
            catch (Exception ex) {
                LOG.log(Level.WARNING, "Error renewing a token", ex);
                boolean issueAfterFailedRenew = MessageUtils.getContextualBoolean(message, "security.issue.after.failed.renew", true);
                if (issueAfterFailedRenew) {
                    SecurityToken securityToken = this.issueToken(message, aim, itok);
                    return securityToken;
                }
                throw new Fault(ex);
            }
            finally {
                client.setTrust(null);
                client.setTrust(null);
                client.setTemplate(null);
                client.setLocation(null);
                client.setAddressingNamespace(null);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SecurityToken issueToken(SoapMessage message, AssertionInfoMap aim, SecureConversationToken itok) {
        STSClient client = STSUtils.getClient(message, "sct");
        AddressingProperties maps = (AddressingProperties)message.get("javax.xml.ws.addressing.context.outbound");
        if (maps == null) {
            maps = (AddressingProperties)message.get("javax.xml.ws.addressing.context");
        }
        STSClient sTSClient = client;
        synchronized (sTSClient) {
            try {
                String s2 = SecureConversationTokenInterceptorProvider.setupClient(client, message, aim, itok, false);
                if (maps != null) {
                    client.setAddressingNamespace(maps.getNamespaceURI());
                }
                SecurityToken tok = client.requestSecurityToken(s2);
                String tokenType = tok.getTokenType();
                tok.setTokenType(tokenType);
                if (tokenType == null || "".equals(tokenType)) {
                    tok.setTokenType("http://schemas.xmlsoap.org/ws/2005/02/sc/sct");
                }
                SecurityToken securityToken = tok;
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
                client.setLocation(null);
                client.setAddressingNamespace(null);
            }
        }
    }
}

