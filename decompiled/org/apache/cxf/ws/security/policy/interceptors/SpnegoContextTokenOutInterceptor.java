/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.util.Collection;
import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.policy.interceptors.SpnegoTokenInterceptorProvider;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.cxf.ws.security.trust.STSUtils;
import org.apache.wss4j.common.spnego.SpnegoClientAction;
import org.apache.wss4j.common.spnego.SpnegoTokenContext;
import org.apache.xml.security.utils.XMLUtils;

class SpnegoContextTokenOutInterceptor
extends AbstractPhaseInterceptor<SoapMessage> {
    SpnegoContextTokenOutInterceptor() {
        super("prepare-send");
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (aim != null) {
            Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SpnegoContextToken");
            if (ais.isEmpty()) {
                return;
            }
            if (this.isRequestor(message)) {
                String tokId = (String)message.getContextualProperty("ws-security.token.id");
                SecurityToken tok = null;
                if (tokId != null && (tok = TokenStoreUtils.getTokenStore(message).getToken(tokId)) != null && tok.isExpired()) {
                    message.getExchange().getEndpoint().remove("ws-security.token.id");
                    message.getExchange().remove("ws-security.token.id");
                    TokenStoreUtils.getTokenStore(message).remove(tokId);
                    tok = null;
                }
                if (tok == null) {
                    tok = this.issueToken(message, aim);
                }
                for (AssertionInfo ai : ais) {
                    ai.setAsserted(true);
                }
                message.getExchange().getEndpoint().put("ws-security.token.id", tok.getId());
                message.getExchange().put("ws-security.token.id", tok.getId());
                TokenStoreUtils.getTokenStore(message).add(tok);
            } else {
                for (AssertionInfo ai : ais) {
                    ai.setAsserted(true);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SecurityToken issueToken(SoapMessage message, AssertionInfoMap aim) {
        String jaasContext = (String)message.getContextualProperty("ws-security.kerberos.jaas.context");
        String kerberosSpn = (String)message.getContextualProperty("ws-security.kerberos.spn");
        SpnegoTokenContext spnegoToken = new SpnegoTokenContext();
        Object spnegoClientAction = message.getContextualProperty("ws-security.spnego.client.action");
        if (spnegoClientAction instanceof SpnegoClientAction) {
            spnegoToken.setSpnegoClientAction((SpnegoClientAction)spnegoClientAction);
        }
        try {
            CallbackHandler callbackHandler = SecurityUtils.getCallbackHandler(SecurityUtils.getSecurityPropertyValue("security.callback-handler", message));
            spnegoToken.retrieveServiceTicket(jaasContext, callbackHandler, kerberosSpn);
        }
        catch (Exception e) {
            throw new Fault(e);
        }
        STSClient client = STSUtils.getClient(message, "spnego");
        AddressingProperties maps = (AddressingProperties)message.get("javax.xml.ws.addressing.context.outbound");
        if (maps == null) {
            maps = (AddressingProperties)message.get("javax.xml.ws.addressing.context");
        }
        STSClient sTSClient = client;
        synchronized (sTSClient) {
            try {
                String s2 = SpnegoTokenInterceptorProvider.setupClient(client, message, aim);
                if (maps != null) {
                    client.setAddressingNamespace(maps.getNamespaceURI());
                }
                SecurityToken tok = client.requestSecurityToken(s2, XMLUtils.encodeToString(spnegoToken.getToken()));
                byte[] wrappedTok = spnegoToken.unwrapKey(tok.getSecret());
                tok.setSecret(wrappedTok);
                spnegoToken.clear();
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

