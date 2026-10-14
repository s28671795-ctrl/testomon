/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.time.Instant;
import java.util.Collection;
import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.policy.interceptors.HttpsTokenInterceptorProvider;
import org.apache.cxf.ws.security.policy.interceptors.NegotiationUtils;
import org.apache.cxf.ws.security.policy.interceptors.STSInvoker;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JStaxInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.wss4j.common.spnego.SpnegoTokenContext;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;

class SpnegoContextTokenInInterceptor
extends AbstractPhaseInterceptor<SoapMessage> {
    SpnegoContextTokenInInterceptor() {
        super("pre-stream");
        this.addBefore(WSS4JStaxInInterceptor.class.getName());
        this.addBefore(HttpsTokenInterceptorProvider.HttpsTokenInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (aim != null) {
            AddressingProperties inProps;
            Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SpnegoContextToken");
            if (ais.isEmpty()) {
                return;
            }
            if (this.isRequestor(message)) {
                for (AssertionInfo ai : ais) {
                    ai.setAsserted(true);
                }
                return;
            }
            String s2 = (String)message.get("SOAPAction");
            if (s2 == null) {
                s2 = SoapActionInInterceptor.getSoapAction(message);
            }
            if ((inProps = (AddressingProperties)message.getContextualProperty("javax.xml.ws.addressing.context.inbound")) != null && s2 == null) {
                s2 = inProps.getAction().getValue();
            }
            if (s2 != null && s2.contains("/RST/Issue") && (s2.startsWith("http://schemas.xmlsoap.org/ws/2005/02/trust") || s2.startsWith("http://docs.oasis-open.org/ws-sx/ws-trust/200512"))) {
                Policy p = new Policy();
                ExactlyOne ea = new ExactlyOne();
                p.addPolicyComponent(ea);
                All all = new All();
                Assertion ass = NegotiationUtils.getAddressingPolicy(aim, false);
                all.addPolicyComponent(ass);
                ea.addPolicyComponent(all);
                this.unmapSecurityProps(message);
                String ns = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
                if (s2.startsWith("http://schemas.xmlsoap.org/ws/2005/02/trust")) {
                    ns = "http://schemas.xmlsoap.org/ws/2005/02/trust";
                }
                NegotiationUtils.recalcEffectivePolicy(message, ns, p, new SpnegoSTSInvoker(), false);
            } else {
                message.getInterceptorChain().add(SpnegoContextTokenFinderInterceptor.INSTANCE);
            }
        }
    }

    private void unmapSecurityProps(Message message) {
        Exchange ex = message.getExchange();
        for (String s2 : SecurityConstants.ALL_PROPERTIES) {
            Object v = message.getContextualProperty(s2);
            if (v == null) continue;
            ex.put(s2, v);
        }
    }

    static final class SpnegoContextTokenFinderInterceptor
    extends AbstractPhaseInterceptor<SoapMessage> {
        static final SpnegoContextTokenFinderInterceptor INSTANCE = new SpnegoContextTokenFinderInterceptor();

        private SpnegoContextTokenFinderInterceptor() {
            super("pre-protocol");
            this.addAfter(WSS4JInInterceptor.class.getName());
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            boolean foundSCT = NegotiationUtils.parseSCTResult(message);
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (aim != null) {
                Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SpnegoContextToken");
                if (ais.isEmpty()) {
                    return;
                }
                for (AssertionInfo inf : ais) {
                    if (foundSCT) {
                        inf.setAsserted(true);
                        continue;
                    }
                    inf.setNotAsserted("No SecurityContextToken token found in message.");
                }
            }
        }
    }

    public class SpnegoSTSInvoker
    extends STSInvoker {
        @Override
        void doIssue(Element requestEl, Exchange exchange, Element binaryExchange, W3CDOMStreamWriter writer, String prefix, String namespace) throws Exception {
            SpnegoTokenContext spnegoToken = this.handleBinaryExchange(binaryExchange, exchange.getInMessage(), namespace);
            writer.writeStartElement(prefix, "RequestSecurityTokenResponseCollection", namespace);
            writer.writeStartElement(prefix, "RequestSecurityTokenResponse", namespace);
            String context = requestEl.getAttributeNS(null, "Context");
            if (context != null && !"".equals(context)) {
                writer.writeAttribute("Context", context);
            }
            int keySize = 256;
            String tokenType = null;
            Element el = DOMUtils.getFirstElement(requestEl);
            while (el != null) {
                String localName = el.getLocalName();
                if (namespace.equals(el.getNamespaceURI())) {
                    if ("KeySize".equals(localName)) {
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
            writer.writeStartElement(prefix, "TokenType", namespace);
            writer.writeCharacters(tokenType);
            writer.writeEndElement();
            writer.writeStartElement(prefix, "RequestedSecurityToken", namespace);
            SecurityContextToken sct = new SecurityContextToken(NegotiationUtils.getWSCVersion(tokenType), writer.getDocument());
            WSSConfig wssConfig = WSSConfig.getNewInstance();
            sct.setID(wssConfig.getIdAllocator().createId("sctId-", sct));
            Instant created = Instant.now();
            Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(exchange.getOutMessage()) / 1000L);
            SecurityToken token = new SecurityToken(sct.getIdentifier(), created, expires);
            token.setToken(sct.getElement());
            token.setTokenType(sct.getTokenType());
            SecurityContext sc = exchange.getInMessage().get(SecurityContext.class);
            if (sc != null) {
                token.setSecurityContext(sc);
            }
            writer.getCurrentNode().appendChild(sct.getElement());
            writer.writeEndElement();
            writer.writeStartElement(prefix, "RequestedAttachedReference", namespace);
            token.setAttachedReference(this.writeSecurityTokenReference(writer, "#" + sct.getID(), tokenType));
            writer.writeEndElement();
            writer.writeStartElement(prefix, "RequestedUnattachedReference", namespace);
            token.setUnattachedReference(this.writeSecurityTokenReference(writer, sct.getIdentifier(), tokenType));
            writer.writeEndElement();
            this.writeLifetime(writer, created, expires, prefix, namespace);
            writer.writeStartElement(prefix, "KeySize", namespace);
            writer.writeCharacters(Integer.toString(keySize));
            writer.writeEndElement();
            byte[] secret = WSSecurityUtil.generateNonce(keySize / 8);
            byte[] key = spnegoToken.wrapKey(secret);
            this.writeProofToken(writer, prefix, namespace, key);
            writer.writeEndElement();
            writer.writeEndElement();
            spnegoToken.clear();
            token.setSecret(secret);
            ((TokenStore)exchange.getEndpoint().getEndpointInfo().getProperty(TokenStore.class.getName())).add(token);
        }

        @Override
        void doRenew(Element requestEl, Exchange exchange, SecurityToken securityToken, Element binaryExchange, W3CDOMStreamWriter writer, String prefix, String namespace) {
        }

        private SpnegoTokenContext handleBinaryExchange(Element binaryExchange, Message message, String namespace) throws Exception {
            if (binaryExchange == null) {
                throw new Exception("No BinaryExchange element received");
            }
            String encoding = binaryExchange.getAttributeNS(null, "EncodingType");
            if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(encoding)) {
                throw new Exception("Unknown encoding type: " + encoding);
            }
            String valueType = binaryExchange.getAttributeNS(null, "ValueType");
            if (!(namespace + "/spnego").equals(valueType)) {
                throw new Exception("Unknown value type: " + valueType);
            }
            String content = DOMUtils.getContent(binaryExchange);
            byte[] decodedContent = XMLUtils.decode(content);
            String jaasContext = (String)message.getContextualProperty("ws-security.kerberos.jaas.context");
            String kerberosSpn = (String)message.getContextualProperty("ws-security.kerberos.spn");
            CallbackHandler callbackHandler = SecurityUtils.getCallbackHandler(SecurityUtils.getSecurityPropertyValue("security.callback-handler", message));
            SpnegoTokenContext spnegoToken = new SpnegoTokenContext();
            spnegoToken.validateServiceTicket(jaasContext, callbackHandler, kerberosSpn, decodedContent);
            return spnegoToken;
        }

        private void writeProofToken(W3CDOMStreamWriter writer, String prefix, String namespace, byte[] key) throws Exception {
            writer.writeStartElement(prefix, "RequestedProofToken", namespace);
            writer.writeStartElement("xenc", "EncryptedKey", "http://www.w3.org/2001/04/xmlenc#");
            writer.writeStartElement("xenc", "EncryptionMethod", "http://www.w3.org/2001/04/xmlenc#");
            writer.writeAttribute("Algorithm", namespace + "/spnego#GSS_Wrap");
            writer.writeEndElement();
            writer.writeStartElement("xenc", "CipherData", "http://www.w3.org/2001/04/xmlenc#");
            writer.writeStartElement("xenc", "CipherValue", "http://www.w3.org/2001/04/xmlenc#");
            writer.writeCharacters(XMLUtils.encodeToString(key));
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndElement();
        }
    }
}

