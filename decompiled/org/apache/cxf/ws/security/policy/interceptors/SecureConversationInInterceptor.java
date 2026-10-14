/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.soap.MAPCodec;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertion;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.policy.interceptors.HttpsTokenInterceptorProvider;
import org.apache.cxf.ws.security.policy.interceptors.NegotiationUtils;
import org.apache.cxf.ws.security.policy.interceptors.STSInvoker;
import org.apache.cxf.ws.security.policy.interceptors.SecureConversationTokenInterceptorProvider;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.trust.DefaultSymmetricBinding;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.cxf.ws.security.trust.STSUtils;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JStaxInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractBinding;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.ProtectionToken;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SignedParts;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;

class SecureConversationInInterceptor
extends AbstractPhaseInterceptor<SoapMessage> {
    SecureConversationInInterceptor() {
        super("pre-stream");
        this.addBefore(WSS4JStaxInInterceptor.class.getName());
        this.addBefore(HttpsTokenInterceptorProvider.HttpsTokenInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        final AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (aim != null) {
            final Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SecureConversationToken");
            if (ais.isEmpty()) {
                return;
            }
            if (this.isRequestor(message)) {
                for (AssertionInfo ai : ais) {
                    ai.setAsserted(true);
                }
                this.assertPolicies(aim);
                Object s2 = message.getContextualProperty("security.sts.token.do.cancel");
                if (s2 != null && (Boolean.TRUE.equals(s2) || "true".equalsIgnoreCase(s2.toString()))) {
                    message.getInterceptorChain().add(SecureConversationCancelInterceptor.INSTANCE);
                }
                return;
            }
            String s3 = (String)message.get("SOAPAction");
            if (s3 == null) {
                s3 = SoapActionInInterceptor.getSoapAction(message);
            }
            if (s3 != null) {
                this.handleMessageForAction(message, s3, aim, ais);
            } else {
                AbstractPhaseInterceptor<SoapMessage> post = new AbstractPhaseInterceptor<SoapMessage>("pre-protocol"){

                    @Override
                    public void handleMessage(SoapMessage message) throws Fault {
                        String s2 = (String)message.get("SOAPAction");
                        if (s2 == null) {
                            s2 = SoapActionInInterceptor.getSoapAction(message);
                        }
                        SecureConversationInInterceptor.this.handleMessageForAction(message, s2, aim, ais);
                    }
                };
                post.addAfter(MAPCodec.class.getName());
                post.addBefore(PolicyBasedWSS4JInInterceptor.class.getName());
                message.put("ws-security.enable.streaming", (Object)Boolean.FALSE);
                message.getInterceptorChain().add((Interceptor<? extends Message>)post);
            }
        }
    }

    void handleMessageForAction(SoapMessage message, String s2, AssertionInfoMap aim, Collection<AssertionInfo> ais) {
        String addNs = null;
        AddressingProperties inProps = (AddressingProperties)message.getContextualProperty("javax.xml.ws.addressing.context.inbound");
        if (inProps != null) {
            addNs = inProps.getNamespaceURI();
            if (s2 == null) {
                s2 = inProps.getAction().getValue();
            }
        }
        if (s2 != null && s2.contains("/RST/SCT") && (s2.startsWith("http://schemas.xmlsoap.org/ws/2005/02/trust") || s2.startsWith("http://docs.oasis-open.org/ws-sx/ws-trust/200512"))) {
            Policy p;
            SecureConversationToken tok = (SecureConversationToken)ais.iterator().next().getAssertion();
            Policy pol = tok.getBootstrapPolicy().getPolicy();
            if (s2.endsWith("Cancel")) {
                p = new Policy();
                ExactlyOne ea = new ExactlyOne();
                p.addPolicyComponent(ea);
                All all = new All();
                Assertion ass = NegotiationUtils.getAddressingPolicy(aim, false);
                all.addPolicyComponent(ass);
                ea.addPolicyComponent(all);
                SecureConversationToken secureConversationToken = new SecureConversationToken(SPConstants.SPVersion.SP12, SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER, null, null, null, new Policy());
                Policy sctPolicy = new Policy();
                ExactlyOne sctPolicyEa = new ExactlyOne();
                sctPolicy.addPolicyComponent(sctPolicyEa);
                All sctPolicyAll = new All();
                sctPolicyAll.addPolicyComponent(secureConversationToken);
                sctPolicyEa.addPolicyComponent(sctPolicyAll);
                Policy bindingPolicy = new Policy();
                ExactlyOne bindingPolicyEa = new ExactlyOne();
                bindingPolicy.addPolicyComponent(bindingPolicyEa);
                All bindingPolicyAll = new All();
                AbstractBinding origBinding = PolicyUtils.getSecurityBinding(aim);
                bindingPolicyAll.addPolicyComponent(origBinding.getAlgorithmSuite());
                bindingPolicyAll.addPolicyComponent(new ProtectionToken(SPConstants.SPVersion.SP12, sctPolicy));
                bindingPolicyAll.addAssertion(new PrimitiveAssertion(SP12Constants.INCLUDE_TIMESTAMP));
                bindingPolicyAll.addAssertion(new PrimitiveAssertion(SP12Constants.ONLY_SIGN_ENTIRE_HEADERS_AND_BODY));
                bindingPolicyEa.addPolicyComponent(bindingPolicyAll);
                DefaultSymmetricBinding binding = new DefaultSymmetricBinding(SPConstants.SPVersion.SP12, bindingPolicy);
                binding.setOnlySignEntireHeadersAndBody(true);
                binding.setProtectTokens(false);
                all.addPolicyComponent(binding);
                SignedParts signedParts = this.getSignedParts(aim, addNs);
                all.addPolicyComponent(signedParts);
                pol = p;
                message.getInterceptorChain().add(SecureConversationTokenFinderInterceptor.INSTANCE);
            } else {
                p = new Policy();
                ExactlyOne ea = new ExactlyOne();
                p.addPolicyComponent(ea);
                All all = new All();
                Assertion ass = NegotiationUtils.getAddressingPolicy(aim, false);
                all.addPolicyComponent(ass);
                ea.addPolicyComponent(all);
                pol = p.merge(pol);
            }
            this.unmapSecurityProps(message);
            String ns = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
            if (s2.startsWith("http://schemas.xmlsoap.org/ws/2005/02/trust")) {
                ns = "http://schemas.xmlsoap.org/ws/2005/02/trust";
            }
            NegotiationUtils.recalcEffectivePolicy(message, ns, pol, new SecureConversationSTSInvoker(), true);
            SoapActionInInterceptor.getAndSetOperation(message, s2);
        } else {
            message.getInterceptorChain().add(SecureConversationTokenFinderInterceptor.INSTANCE);
        }
        this.assertPolicies(aim);
    }

    private SignedParts getSignedParts(AssertionInfoMap aim, String addNs) {
        AssertionInfo signedPartsAi = PolicyUtils.getFirstAssertionByLocalname(aim, "SignedParts");
        SignedParts signedParts = null;
        if (signedPartsAi != null) {
            signedParts = (SignedParts)signedPartsAi.getAssertion();
        }
        if (signedParts == null) {
            ArrayList<Header> headers = new ArrayList<Header>();
            if (addNs != null) {
                headers.add(new Header("To", addNs));
                headers.add(new Header("From", addNs));
                headers.add(new Header("FaultTo", addNs));
                headers.add(new Header("ReplyTo", addNs));
                headers.add(new Header("Action", addNs));
                headers.add(new Header("MessageID", addNs));
                headers.add(new Header("RelatesTo", addNs));
            }
            signedParts = new SignedParts(SPConstants.SPVersion.SP12, true, null, headers, false);
        }
        return signedParts;
    }

    private void assertPolicies(AssertionInfoMap aim) {
        PolicyUtils.assertPolicy(aim, "BootstrapPolicy");
        PolicyUtils.assertPolicy(aim, "MustNotSendAmend");
        PolicyUtils.assertPolicy(aim, "MustNotSendCancel");
        PolicyUtils.assertPolicy(aim, "MustNotSendRenew");
        QName oldCancelQName = new QName("http://schemas.microsoft.com/ws/2005/07/securitypolicy", "MustNotSendCancel");
        PolicyUtils.assertPolicy(aim, oldCancelQName);
    }

    private void unmapSecurityProps(Message message) {
        Exchange ex = message.getExchange();
        for (String s2 : SecurityConstants.ALL_PROPERTIES) {
            Object v = message.getContextualProperty(s2 + ".sct");
            if (v == null) {
                v = message.getContextualProperty(s2);
            }
            if (v == null) continue;
            ex.put(s2, v);
        }
    }

    static class SecureConversationCancelInterceptor
    extends AbstractPhaseInterceptor<SoapMessage> {
        static final SecureConversationCancelInterceptor INSTANCE = new SecureConversationCancelInterceptor();

        SecureConversationCancelInterceptor() {
            super("post-logical");
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (aim == null) {
                return;
            }
            AssertionInfo ai = PolicyUtils.getFirstAssertionByLocalname(aim, "SecureConversationToken");
            if (ai == null) {
                return;
            }
            SecureConversationToken tok = (SecureConversationToken)ai.getAssertion();
            this.doCancel(message, aim, tok);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void doCancel(SoapMessage message, AssertionInfoMap aim, SecureConversationToken itok) {
            String tokId;
            Message m22 = message.getExchange().getOutMessage();
            SecurityToken tok = (SecurityToken)m22.getContextualProperty("ws-security.token");
            if (tok == null && (tokId = (String)m22.getContextualProperty("ws-security.token.id")) != null) {
                tok = TokenStoreUtils.getTokenStore(m22).getToken(tokId);
            }
            STSClient client = STSUtils.getClient(m22, "sct");
            AddressingProperties maps = (AddressingProperties)message.get("javax.xml.ws.addressing.context.inbound");
            if (maps == null) {
                maps = (AddressingProperties)m22.get("javax.xml.ws.addressing.context");
            }
            STSClient sTSClient = client;
            synchronized (sTSClient) {
                try {
                    SecureConversationTokenInterceptorProvider.setupClient(client, message, aim, itok, true);
                    if (maps != null) {
                        client.setAddressingNamespace(maps.getNamespaceURI());
                    }
                    client.cancelSecurityToken(tok);
                    TokenStore tokenStore = TokenStoreUtils.getTokenStore(m22);
                    if (tokenStore != null) {
                        tokenStore.remove(tok.getId());
                    }
                    m22.put("ws-security.token", null);
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

    static final class SecureConversationTokenFinderInterceptor
    extends AbstractPhaseInterceptor<SoapMessage> {
        static final SecureConversationTokenFinderInterceptor INSTANCE = new SecureConversationTokenFinderInterceptor();

        private SecureConversationTokenFinderInterceptor() {
            super("pre-protocol");
            this.addAfter(WSS4JInInterceptor.class.getName());
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            boolean foundSCT = NegotiationUtils.parseSCTResult(message);
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (aim != null) {
                Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SecureConversationToken");
                if (ais.isEmpty()) {
                    return;
                }
                for (AssertionInfo inf : ais) {
                    SecureConversationToken token = (SecureConversationToken)inf.getAssertion();
                    SPConstants.IncludeTokenType inclusion = token.getIncludeTokenType();
                    if (foundSCT || token.isOptional() || !foundSCT && inclusion == SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER) {
                        inf.setAsserted(true);
                        continue;
                    }
                    inf.setNotAsserted("No SecureConversation token found in message.");
                }
            }
        }
    }

    public class SecureConversationSTSInvoker
    extends STSInvoker {
        @Override
        void doIssue(Element requestEl, Exchange exchange, Element binaryExchange, W3CDOMStreamWriter writer, String prefix, String namespace) throws Exception {
            this.doIssueOrRenew(requestEl, exchange, writer, prefix, namespace, null);
        }

        @Override
        void doRenew(Element requestEl, Exchange exchange, SecurityToken renewToken, Element binaryExchange, W3CDOMStreamWriter writer, String prefix, String namespace) throws Exception {
            this.doIssueOrRenew(requestEl, exchange, writer, prefix, namespace, renewToken.getId());
        }

        private void doIssueOrRenew(Element requestEl, Exchange exchange, W3CDOMStreamWriter writer, String prefix, String namespace, String tokenIdToRenew) throws Exception {
            SecurityToken bootstrapToken;
            SecurityContextToken sct;
            if ("http://docs.oasis-open.org/ws-sx/ws-trust/200512".equals(namespace)) {
                writer.writeStartElement(prefix, "RequestSecurityTokenResponseCollection", namespace);
            }
            writer.writeStartElement(prefix, "RequestSecurityTokenResponse", namespace);
            byte[] clientEntropy = null;
            int keySize = 256;
            long ttl = WSS4JUtils.getSecurityTokenLifetime(exchange.getOutMessage());
            String tokenType = null;
            Element el = DOMUtils.getFirstElement(requestEl);
            while (el != null) {
                String localName = el.getLocalName();
                if (namespace.equals(el.getNamespaceURI())) {
                    if ("Entropy".equals(localName)) {
                        Element bs = DOMUtils.getFirstElement(el);
                        if (bs != null) {
                            clientEntropy = XMLUtils.decode(bs.getTextContent());
                        }
                    } else if ("KeySize".equals(localName)) {
                        keySize = Integer.parseInt(el.getTextContent());
                    } else if ("TokenType".equals(localName)) {
                        tokenType = el.getTextContent();
                    }
                }
                el = DOMUtils.getNextElement(el);
            }
            if (keySize < 128 || keySize > 512) {
                keySize = 256;
            }
            writer.writeStartElement(prefix, "RequestedSecurityToken", namespace);
            if (tokenIdToRenew != null) {
                ((TokenStore)exchange.getEndpoint().getEndpointInfo().getProperty(TokenStore.class.getName())).remove(tokenIdToRenew);
                String instance = IDGenerator.generateID(null);
                sct = new SecurityContextToken(NegotiationUtils.getWSCVersion(tokenType), writer.getDocument(), tokenIdToRenew, instance);
                sct.setID(WSSConfig.getNewInstance().getIdAllocator().createSecureId("sctId-", sct.getElement()));
            } else {
                sct = new SecurityContextToken(NegotiationUtils.getWSCVersion(tokenType), writer.getDocument());
                sct.setID(WSSConfig.getNewInstance().getIdAllocator().createSecureId("sctId-", sct.getElement()));
            }
            Instant created = Instant.now();
            Instant expires = created.plusSeconds(ttl / 1000L);
            SecurityToken token = new SecurityToken(sct.getIdentifier(), created, expires);
            token.setToken(sct.getElement());
            token.setTokenType(sct.getTokenType());
            writer.getCurrentNode().appendChild(sct.getElement());
            writer.writeEndElement();
            writer.writeStartElement(prefix, "RequestedAttachedReference", namespace);
            token.setAttachedReference(this.writeSecurityTokenReference(writer, "#" + sct.getID(), tokenType));
            writer.writeEndElement();
            writer.writeStartElement(prefix, "RequestedUnattachedReference", namespace);
            token.setUnattachedReference(this.writeSecurityTokenReference(writer, sct.getIdentifier(), sct.getInstance(), tokenType));
            writer.writeEndElement();
            this.writeLifetime(writer, created, expires, prefix, namespace);
            byte[] secret = this.writeProofToken(prefix, namespace, writer, clientEntropy, keySize);
            token.setSecret(secret);
            SecurityContext sc = exchange.getInMessage().get(SecurityContext.class);
            if (sc != null) {
                token.setSecurityContext(sc);
            }
            if ((bootstrapToken = this.getBootstrapToken(exchange.getInMessage())) != null) {
                HashMap<String, Object> properties = new HashMap<String, Object>(1);
                properties.put("bootstrap_security_token_id", bootstrapToken.getId());
                token.setProperties(properties);
            }
            ((TokenStore)exchange.getEndpoint().getEndpointInfo().getProperty(TokenStore.class.getName())).add(token);
            writer.writeEndElement();
            if ("http://docs.oasis-open.org/ws-sx/ws-trust/200512".equals(namespace)) {
                writer.writeEndElement();
            }
        }

        private SecurityToken getBootstrapToken(Message message) {
            String id;
            SecurityToken st = (SecurityToken)message.getContextualProperty("ws-security.token");
            if (st == null && (id = (String)message.getContextualProperty("ws-security.token.id")) != null) {
                st = TokenStoreUtils.getTokenStore(message).getToken(id);
            }
            if (st != null && !st.isExpired()) {
                return st;
            }
            return null;
        }
    }
}

