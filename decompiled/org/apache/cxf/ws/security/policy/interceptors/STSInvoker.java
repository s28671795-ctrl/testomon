/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.security.policy.interceptors.NegotiationUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.trust.STSUtils;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.derivedKey.ConversationConstants;
import org.apache.wss4j.common.derivedKey.P_SHA1;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.DateUtil;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

abstract class STSInvoker
implements Invoker {
    private static final Logger LOG = LogUtils.getL7dLogger(STSInvoker.class);

    STSInvoker() {
    }

    @Override
    public Object invoke(Exchange exchange, Object o) {
        AddressingProperties inProps = (AddressingProperties)exchange.getInMessage().getContextualProperty("javax.xml.ws.addressing.context.inbound");
        if (inProps != null) {
            AddressingProperties props = inProps.createCompatibleResponseProperties();
            AttributedURIType action = new AttributedURIType();
            action.setValue(inProps.getAction().getValue().replace("/RST/", "/RSTR/"));
            props.setAction(action);
            exchange.getOutMessage().put("javax.xml.ws.addressing.context.outbound", props);
        }
        MessageContentsList lst = (MessageContentsList)o;
        DOMSource src = (DOMSource)lst.get(0);
        Node nd = src.getNode();
        Element requestEl = null;
        requestEl = nd instanceof Document ? ((Document)nd).getDocumentElement() : (Element)nd;
        String namespace = requestEl.getNamespaceURI();
        String prefix = requestEl.getPrefix();
        SecurityToken cancelOrRenewToken = null;
        if ("RequestSecurityToken".equals(requestEl.getLocalName())) {
            try {
                String requestType = null;
                Element binaryExchange = null;
                String tokenType = null;
                Element el = DOMUtils.getFirstElement(requestEl);
                while (el != null) {
                    String localName = el.getLocalName();
                    if (namespace.equals(el.getNamespaceURI())) {
                        if ("RequestType".equals(localName)) {
                            requestType = el.getTextContent();
                        } else if ("CancelTarget".equals(localName) || "RenewTarget".equals(localName)) {
                            cancelOrRenewToken = this.findCancelOrRenewToken(exchange, el);
                        } else if ("BinaryExchange".equals(localName)) {
                            binaryExchange = el;
                        } else if ("TokenType".equals(localName)) {
                            tokenType = DOMUtils.getContent(el);
                        }
                    }
                    el = DOMUtils.getNextElement(el);
                }
                if (requestType == null) {
                    requestType = "/Issue";
                }
                if (requestType.endsWith("/Issue") && !STSUtils.getTokenTypeSCT(namespace).equals(tokenType)) {
                    throw new Exception("Unknown token type: " + tokenType);
                }
                W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
                writer.setNsRepairing(true);
                if (requestType.endsWith("/Issue")) {
                    this.doIssue(requestEl, exchange, binaryExchange, writer, prefix, namespace);
                } else if (requestType.endsWith("/Cancel")) {
                    this.doCancel(exchange, cancelOrRenewToken, writer, prefix, namespace);
                } else if (requestType.endsWith("/Renew")) {
                    this.doRenew(requestEl, exchange, cancelOrRenewToken, binaryExchange, writer, prefix, namespace);
                }
                return new MessageContentsList(new DOMSource(writer.getDocument()));
            }
            catch (RuntimeException ex) {
                throw ex;
            }
            catch (Exception ex) {
                throw new Fault(ex);
            }
        }
        throw new Fault("Unknown SecureConversation element: " + requestEl.getLocalName(), LOG);
    }

    abstract void doIssue(Element var1, Exchange var2, Element var3, W3CDOMStreamWriter var4, String var5, String var6) throws Exception;

    abstract void doRenew(Element var1, Exchange var2, SecurityToken var3, Element var4, W3CDOMStreamWriter var5, String var6, String var7) throws Exception;

    private void doCancel(Exchange exchange, SecurityToken cancelToken, W3CDOMStreamWriter writer, String prefix, String namespace) throws Exception {
        if ("http://docs.oasis-open.org/ws-sx/ws-trust/200512".equals(namespace)) {
            writer.writeStartElement(prefix, "RequestSecurityTokenResponseCollection", namespace);
        }
        writer.writeStartElement(prefix, "RequestSecurityTokenResponse", namespace);
        TokenStore store = (TokenStore)exchange.getEndpoint().getEndpointInfo().getProperty(TokenStore.class.getName());
        store.remove(cancelToken.getId());
        exchange.put("ws-security.token", cancelToken);
        writer.writeEmptyElement(prefix, "RequestedTokenCancelled", namespace);
        writer.writeEndElement();
        if ("http://docs.oasis-open.org/ws-sx/ws-trust/200512".equals(namespace)) {
            writer.writeEndElement();
        }
    }

    private SecurityToken findCancelOrRenewToken(Exchange exchange, Element el) throws WSSecurityException {
        String uri;
        Element childElement = DOMUtils.getFirstElement(el);
        if ("SecurityContextToken".equals(childElement.getLocalName())) {
            SecurityContextToken sct = new SecurityContextToken(childElement);
            uri = sct.getIdentifier();
        } else {
            SecurityTokenReference ref = new SecurityTokenReference(childElement, new BSPEnforcer());
            uri = ref.getReference().getURI();
        }
        TokenStore store = (TokenStore)exchange.getEndpoint().getEndpointInfo().getProperty(TokenStore.class.getName());
        return store.getToken(uri);
    }

    byte[] writeProofToken(String prefix, String namespace, W3CDOMStreamWriter writer, byte[] clientEntropy, int keySize) throws NoSuchAlgorithmException, WSSecurityException, XMLStreamException {
        byte[] secret = null;
        writer.writeStartElement(prefix, "RequestedProofToken", namespace);
        if (clientEntropy == null) {
            secret = WSSecurityUtil.generateNonce(keySize / 8);
            writer.writeStartElement(prefix, "BinarySecret", namespace);
            writer.writeAttribute("Type", namespace + "/Nonce");
            writer.writeCharacters(XMLUtils.encodeToString(secret));
            writer.writeEndElement();
        } else {
            byte[] entropy = WSSecurityUtil.generateNonce(keySize / 8);
            P_SHA1 psha1 = new P_SHA1();
            secret = psha1.createKey(clientEntropy, entropy, 0, keySize / 8);
            writer.writeStartElement(prefix, "ComputedKey", namespace);
            writer.writeCharacters(namespace + "/CK/PSHA1");
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeStartElement(prefix, "Entropy", namespace);
            writer.writeStartElement(prefix, "BinarySecret", namespace);
            writer.writeAttribute("Type", namespace + "/Nonce");
            writer.writeCharacters(XMLUtils.encodeToString(entropy));
            writer.writeEndElement();
        }
        writer.writeEndElement();
        return secret;
    }

    Element writeSecurityTokenReference(W3CDOMStreamWriter writer, String id, String refValueType) {
        Reference ref = new Reference(writer.getDocument());
        ref.setURI(id);
        if (refValueType != null) {
            ref.setValueType(refValueType);
        }
        SecurityTokenReference str = new SecurityTokenReference(writer.getDocument());
        str.addWSSENamespace();
        str.setReference(ref);
        writer.getCurrentNode().appendChild(str.getElement());
        return str.getElement();
    }

    Element writeSecurityTokenReference(W3CDOMStreamWriter writer, String id, String instance, String refValueType) {
        Reference ref = new Reference(writer.getDocument());
        ref.setURI(id);
        if (refValueType != null) {
            ref.setValueType(refValueType);
        }
        SecurityTokenReference str = new SecurityTokenReference(writer.getDocument());
        str.addWSSENamespace();
        str.setReference(ref);
        if (instance != null) {
            try {
                Element firstChildElement = str.getFirstElement();
                if (firstChildElement != null) {
                    int version = NegotiationUtils.getWSCVersion(refValueType);
                    String ns = ConversationConstants.getWSCNs(version);
                    firstChildElement.setAttributeNS(ns, "wsc:Instance", instance);
                }
            }
            catch (WSSecurityException wSSecurityException) {
                // empty catch block
            }
        }
        writer.getCurrentNode().appendChild(str.getElement());
        return str.getElement();
    }

    void writeLifetime(W3CDOMStreamWriter writer, Instant created, Instant expires, String prefix, String namespace) throws Exception {
        writer.writeStartElement(prefix, "Lifetime", namespace);
        writer.writeNamespace("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        writer.writeStartElement("wsu", "Created", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        writer.writeCharacters(created.atZone(ZoneOffset.UTC).format(DateUtil.getDateTimeFormatter(true)));
        writer.writeEndElement();
        writer.writeStartElement("wsu", "Expires", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        writer.writeCharacters(expires.atZone(ZoneOffset.UTC).format(DateUtil.getDateTimeFormatter(true)));
        writer.writeEndElement();
        writer.writeEndElement();
    }
}

