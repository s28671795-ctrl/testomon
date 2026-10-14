/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import java.net.URL;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.crypto.dsig.Reference;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.wss4j.AttachmentCallbackHandler;
import org.apache.cxf.ws.security.wss4j.CXFCallbackLookup;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.cxf.ws.security.wss4j.policyhandlers.AbstractCommonBindingHandler;
import org.apache.cxf.ws.security.wss4j.policyhandlers.WSSecurityTokenHolder;
import org.apache.neethi.Assertion;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.UsernameTokenPrincipal;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.saml.bean.Version;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.Loader;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.message.WSSecBase;
import org.apache.wss4j.dom.message.WSSecDKSign;
import org.apache.wss4j.dom.message.WSSecEncryptedKey;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecSignature;
import org.apache.wss4j.dom.message.WSSecSignatureConfirmation;
import org.apache.wss4j.dom.message.WSSecTimestamp;
import org.apache.wss4j.dom.message.WSSecUsernameToken;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractBinding;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.AsymmetricBinding;
import org.apache.wss4j.policy.model.Attachments;
import org.apache.wss4j.policy.model.ContentEncryptedElements;
import org.apache.wss4j.policy.model.EncryptedElements;
import org.apache.wss4j.policy.model.EncryptedParts;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.apache.wss4j.policy.model.Layout;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.model.SignedElements;
import org.apache.wss4j.policy.model.SignedParts;
import org.apache.wss4j.policy.model.SpnegoContextToken;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.SymmetricBinding;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.Wss10;
import org.apache.wss4j.policy.model.Wss11;
import org.apache.wss4j.policy.model.X509Token;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractBindingBuilder
extends AbstractCommonBindingHandler {
    public static final String CRYPTO_CACHE = "ws-security.crypto.cache";
    protected static final Logger LOG = LogUtils.getL7dLogger(AbstractBindingBuilder.class);
    protected AbstractSymmetricAsymmetricBinding.ProtectionOrder protectionOrder = AbstractSymmetricAsymmetricBinding.ProtectionOrder.SignBeforeEncrypting;
    protected final WSSConfig wssConfig;
    protected SOAPMessage saaj;
    protected WSSecHeader secHeader;
    protected AssertionInfoMap aim;
    protected AbstractBinding binding;
    protected WSSecTimestamp timestampEl;
    protected String mainSigId;
    protected List<WSEncryptionPart> sigConfList;
    protected Set<WSEncryptionPart> encryptedTokensList = new HashSet<WSEncryptionPart>();
    protected Set<Integer> signatures = new HashSet<Integer>();
    protected Element bottomUpElement;
    protected Element topDownElement;
    protected Element bstElement;
    protected Element lastEncryptedKeyElement;
    protected final CallbackLookup callbackLookup;
    protected boolean storeBytesInAttachment;
    protected WSDocInfo wsDocInfo;
    private boolean expandXopInclude;
    private Element lastSupportingTokenElement;
    private Element lastDerivedKeyElement;
    private List<AbstractSecurityAssertion> suppTokenParts = new ArrayList<AbstractSecurityAssertion>();
    private List<SupportingToken> endSuppTokList;
    private List<SupportingToken> sgndEndSuppTokList;

    public AbstractBindingBuilder(WSSConfig config, AbstractBinding binding, SOAPMessage saaj, WSSecHeader secHeader, AssertionInfoMap aim, SoapMessage message) throws SOAPException {
        super(message);
        this.wssConfig = config;
        this.binding = binding;
        this.aim = aim;
        this.secHeader = secHeader;
        this.saaj = saaj;
        message.getExchange().put("_sendSignatureValues_", this.signatures);
        boolean storeBytes = MessageUtils.getContextualBoolean(message, "ws-security.store.bytes.in.attachment", true);
        boolean mtomEnabled = AttachmentUtil.isMtomEnabled(message);
        if (storeBytes && mtomEnabled) {
            this.storeBytesInAttachment = true;
            if (binding instanceof AbstractSymmetricAsymmetricBinding && (AbstractSymmetricAsymmetricBinding.ProtectionOrder.EncryptBeforeSigning == ((AbstractSymmetricAsymmetricBinding)binding).getProtectionOrder() || ((AbstractSymmetricAsymmetricBinding)binding).isProtectTokens())) {
                LOG.fine("Disabling SecurityConstants.STORE_BYTES_IN_ATTACHMENT due to EncryptBeforeSigning or ProtectTokens policy.");
                this.storeBytesInAttachment = false;
            }
        }
        this.expandXopInclude = MessageUtils.getContextualBoolean(message, "ws-security.expand.xop.include", mtomEnabled);
        this.wsDocInfo = new WSDocInfo(secHeader.getSecurityHeaderElement().getOwnerDocument());
        SOAPBody soapBody = SAAJUtils.getBody(saaj);
        this.callbackLookup = soapBody != null ? new CXFCallbackLookup(soapBody.getOwnerDocument(), soapBody) : null;
    }

    protected void insertAfter(Element child, Element sib) {
        child = (Element)DOMUtils.getDomElement(child);
        if (sib.getNextSibling() == null) {
            this.secHeader.getSecurityHeaderElement().appendChild(child);
        } else {
            this.secHeader.getSecurityHeaderElement().insertBefore(child, sib.getNextSibling());
        }
    }

    protected void addDerivedKeyElement(Element el) {
        if (this.lastDerivedKeyElement != null) {
            this.insertAfter(el, this.lastDerivedKeyElement);
        } else if (this.lastEncryptedKeyElement != null) {
            this.insertAfter(el, this.lastEncryptedKeyElement);
        } else if (this.topDownElement != null) {
            this.insertAfter(el, this.topDownElement);
        } else if (this.secHeader.getSecurityHeaderElement().getFirstChild() != null) {
            this.secHeader.getSecurityHeaderElement().insertBefore(el, this.secHeader.getSecurityHeaderElement().getFirstChild());
        } else {
            this.secHeader.getSecurityHeaderElement().appendChild(el);
        }
        this.lastEncryptedKeyElement = el;
    }

    protected void addEncryptedKeyElement(Element el) {
        if (this.lastEncryptedKeyElement != null) {
            this.insertAfter(el, this.lastEncryptedKeyElement);
        } else if (this.lastDerivedKeyElement != null) {
            this.secHeader.getSecurityHeaderElement().insertBefore(el, this.lastDerivedKeyElement);
        } else if (this.topDownElement != null) {
            this.insertAfter(el, this.topDownElement);
        } else if (this.secHeader.getSecurityHeaderElement().getFirstChild() != null) {
            this.secHeader.getSecurityHeaderElement().insertBefore(el, this.secHeader.getSecurityHeaderElement().getFirstChild());
        } else {
            this.secHeader.getSecurityHeaderElement().appendChild(el);
        }
        this.lastEncryptedKeyElement = el;
    }

    protected void addSupportingElement(Element el) {
        el = (Element)DOMUtils.getDomElement(el);
        if (this.lastSupportingTokenElement != null) {
            this.insertAfter(el, this.lastSupportingTokenElement);
        } else if (this.lastDerivedKeyElement != null) {
            this.insertAfter(el, this.lastDerivedKeyElement);
        } else if (this.lastEncryptedKeyElement != null) {
            this.insertAfter(el, this.lastEncryptedKeyElement);
        } else if (this.topDownElement != null) {
            this.insertAfter(el, this.topDownElement);
        } else if (this.bottomUpElement != null) {
            this.secHeader.getSecurityHeaderElement().insertBefore(el, this.bottomUpElement);
        } else {
            this.secHeader.getSecurityHeaderElement().appendChild(el);
        }
        this.lastSupportingTokenElement = el;
    }

    protected void insertBeforeBottomUp(Element el) {
        if (this.bottomUpElement == null) {
            this.secHeader.getSecurityHeaderElement().appendChild(el);
        } else {
            this.secHeader.getSecurityHeaderElement().insertBefore(el, this.bottomUpElement);
        }
        this.bottomUpElement = el;
    }

    protected void addTopDownElement(Element el) {
        if (this.topDownElement == null) {
            if (this.secHeader.getSecurityHeaderElement().getFirstChild() == null) {
                this.secHeader.getSecurityHeaderElement().appendChild(el);
            } else {
                this.secHeader.getSecurityHeaderElement().insertBefore(el, this.secHeader.getSecurityHeaderElement().getFirstChild());
            }
        } else {
            this.insertAfter(el, this.topDownElement);
        }
        this.topDownElement = el;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final Map<Object, Crypto> getCryptoCache() {
        EndpointInfo info;
        EndpointInfo endpointInfo = info = this.message.getExchange().getEndpoint().getEndpointInfo();
        synchronized (endpointInfo) {
            Map<Object, Crypto> o = CastUtils.cast((Map)this.message.getContextualProperty(CRYPTO_CACHE));
            if (o == null) {
                o = CastUtils.cast((Map)info.getProperty(CRYPTO_CACHE));
            }
            if (o == null) {
                o = new ConcurrentHashMap();
                info.setProperty(CRYPTO_CACHE, o);
            }
            return o;
        }
    }

    protected final TokenStore getTokenStore() {
        return TokenStoreUtils.getTokenStore(this.message);
    }

    protected WSSecTimestamp createTimestamp() {
        if (this.binding.isIncludeTimestamp()) {
            Object o = this.message.getContextualProperty("ws-security.timestamp.timeToLive");
            int ttl = 300;
            if (o instanceof Number) {
                ttl = ((Number)o).intValue();
            } else if (o instanceof String) {
                ttl = Integer.parseInt((String)o);
            }
            if (ttl <= 0) {
                ttl = 300;
            }
            this.timestampEl = new WSSecTimestamp(this.secHeader);
            this.timestampEl.setIdAllocator(this.wssConfig.getIdAllocator());
            this.timestampEl.setWsTimeSource(this.wssConfig.getCurrentTime());
            this.timestampEl.setTimeToLive(ttl);
            this.timestampEl.prepare();
            String namespace = this.binding.getName().getNamespaceURI();
            PolicyUtils.assertPolicy(this.aim, new QName(namespace, "IncludeTimestamp"));
        }
        return this.timestampEl;
    }

    protected WSSecTimestamp handleLayout(WSSecTimestamp timestamp) {
        if (this.binding.getLayout() != null) {
            AssertionInfo ai = PolicyUtils.getFirstAssertionByLocalname(this.aim, "Layout");
            if (this.binding.getLayout().getLayoutType() == Layout.LayoutType.LaxTsLast) {
                if (timestamp == null) {
                    ai.setNotAsserted("LaxTsLast requires a timestamp");
                } else {
                    ai.setAsserted(true);
                    this.assertPolicy(new QName(this.binding.getLayout().getName().getNamespaceURI(), "LaxTsLast"));
                    Element el = timestamp.getElement();
                    this.secHeader.getSecurityHeaderElement().appendChild(el);
                    if (this.bottomUpElement == null) {
                        this.bottomUpElement = el;
                    }
                }
            } else if (this.binding.getLayout().getLayoutType() == Layout.LayoutType.LaxTsFirst) {
                if (timestamp == null) {
                    ai.setNotAsserted("LaxTsFirst requires a timestamp");
                } else {
                    this.addTopDownElement(this.timestampEl.getElement());
                    ai.setAsserted(true);
                    this.assertPolicy(new QName(this.binding.getLayout().getName().getNamespaceURI(), "LaxTsFirst"));
                }
            } else if (this.timestampEl != null) {
                if (ai != null) {
                    ai.setAsserted(true);
                }
                this.addTopDownElement(this.timestampEl.getElement());
            } else if (ai != null) {
                ai.setAsserted(true);
            }
            this.assertPolicy(new QName(this.binding.getLayout().getName().getNamespaceURI(), "Lax"));
            this.assertPolicy(new QName(this.binding.getLayout().getName().getNamespaceURI(), "Strict"));
        } else if (this.timestampEl != null) {
            this.addTopDownElement(this.timestampEl.getElement());
        }
        return timestamp;
    }

    protected void reshuffleTimestamp() {
        if (this.binding.getLayout() != null && this.timestampEl != null) {
            if (this.binding.getLayout().getLayoutType() == Layout.LayoutType.LaxTsFirst && this.secHeader.getSecurityHeaderElement().getFirstChild() != this.timestampEl.getElement()) {
                Node firstChild;
                for (firstChild = this.secHeader.getSecurityHeaderElement().getFirstChild(); firstChild != null && firstChild.getNodeType() != 1; firstChild = firstChild.getNextSibling()) {
                }
                if (firstChild != null && firstChild != this.timestampEl.getElement()) {
                    this.secHeader.getSecurityHeaderElement().insertBefore(this.timestampEl.getElement(), firstChild);
                }
            } else if (this.binding.getLayout().getLayoutType() == Layout.LayoutType.LaxTsLast && this.secHeader.getSecurityHeaderElement().getLastChild() != this.timestampEl.getElement()) {
                this.secHeader.getSecurityHeaderElement().appendChild(this.timestampEl.getElement());
            }
        }
    }

    private List<SupportingToken> handleSupportingTokens(Collection<AssertionInfo> tokensInfos, boolean endorse) throws WSSecurityException {
        ArrayList<SupportingToken> ret = new ArrayList<SupportingToken>();
        if (tokensInfos != null) {
            for (AssertionInfo assertionInfo : tokensInfos) {
                if (!(assertionInfo.getAssertion() instanceof SupportingTokens)) continue;
                assertionInfo.setAsserted(true);
                try {
                    this.handleSupportingTokens((SupportingTokens)assertionInfo.getAssertion(), endorse, ret);
                }
                catch (SOAPException ex) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)ex);
                }
            }
        }
        return ret;
    }

    protected List<SupportingToken> handleSupportingTokens(SupportingTokens suppTokens, boolean endorse, List<SupportingToken> ret) throws WSSecurityException, SOAPException {
        if (suppTokens == null) {
            return ret;
        }
        for (AbstractToken token : suppTokens.getTokens()) {
            SamlAssertionWrapper assertionWrapper;
            WSSecSignature sig;
            WSEncryptionPart part;
            this.assertToken(token);
            if (!this.isTokenRequired(token.getIncludeTokenType())) {
                this.getSignedParts(suppTokens);
                continue;
            }
            if (token instanceof UsernameToken) {
                this.handleUsernameTokenSupportingToken((UsernameToken)token, endorse, suppTokens.isEncryptedToken(), ret);
                continue;
            }
            if (token instanceof IssuedToken || token instanceof SecureConversationToken || token instanceof SecurityContextToken || token instanceof KerberosToken || token instanceof SpnegoContextToken) {
                SecurityToken secToken = this.getSecurityToken();
                if (secToken == null) {
                    this.unassertPolicy((Assertion)token, "Could not find IssuedToken");
                }
                Element clone = this.cloneElement(secToken.getToken());
                secToken.setToken(clone);
                this.addSupportingElement(clone);
                String id = XMLUtils.getIDFromReference(secToken.getId());
                if (suppTokens.isEncryptedToken()) {
                    part = new WSEncryptionPart(id, "Element");
                    part.setElement(clone);
                    this.encryptedTokensList.add(part);
                }
                if (secToken.getX509Certificate() == null) {
                    ret.add(new SupportingToken(token, new WSSecurityTokenHolder(secToken, this.secHeader), this.getSignedParts(suppTokens)));
                    continue;
                }
                ret.add(this.signSupportingToken(secToken, id, token, suppTokens));
                continue;
            }
            if (token instanceof X509Token) {
                sig = this.getSignatureBuilder(token, false, endorse);
                this.assertPolicy(suppTokens);
                Element bstElem = sig.getBinarySecurityTokenElement();
                if (bstElem != null) {
                    if (this.lastEncryptedKeyElement != null) {
                        if (this.lastEncryptedKeyElement.getNextSibling() != null) {
                            this.secHeader.getSecurityHeaderElement().insertBefore(bstElem, this.lastEncryptedKeyElement.getNextSibling());
                        } else {
                            this.secHeader.getSecurityHeaderElement().appendChild(bstElem);
                        }
                    } else {
                        sig.prependBSTElementToHeader();
                    }
                    if (suppTokens.isEncryptedToken()) {
                        WSEncryptionPart part2 = new WSEncryptionPart(sig.getBSTTokenId(), "Element");
                        part2.setElement(bstElem);
                        this.encryptedTokensList.add(part2);
                    }
                }
                ret.add(new SupportingToken(token, sig, this.getSignedParts(suppTokens)));
                continue;
            }
            if (token instanceof KeyValueToken) {
                sig = this.getSignatureBuilder(token, false, endorse);
                this.assertPolicy(suppTokens);
                if (suppTokens.isEncryptedToken()) {
                    WSEncryptionPart part3 = new WSEncryptionPart(sig.getBSTTokenId(), "Element");
                    this.encryptedTokensList.add(part3);
                }
                ret.add(new SupportingToken(token, sig, this.getSignedParts(suppTokens)));
                continue;
            }
            if (!(token instanceof SamlToken) || (assertionWrapper = this.addSamlToken((SamlToken)token)) == null) continue;
            Element envelope = this.saaj.getSOAPPart().getEnvelope();
            envelope = (Element)DOMUtils.getDomElement(envelope);
            Element assertionElement = assertionWrapper.toDOM(envelope.getOwnerDocument());
            this.addSupportingElement(assertionElement);
            ret.add(new SupportingToken(token, assertionWrapper, this.getSignedParts(suppTokens)));
            if (!suppTokens.isEncryptedToken()) continue;
            part = new WSEncryptionPart(assertionWrapper.getId(), "Element");
            part.setElement(assertionElement);
            this.encryptedTokensList.add(part);
        }
        return ret;
    }

    private SupportingToken signSupportingToken(SecurityToken secToken, String id, AbstractToken token, SupportingTokens suppTokens) throws SOAPException {
        WSSecSignature sig = new WSSecSignature(this.secHeader);
        sig.setIdAllocator(this.wssConfig.getIdAllocator());
        sig.setCallbackLookup(this.callbackLookup);
        sig.setX509Certificate(secToken.getX509Certificate());
        sig.setCustomTokenId(id);
        sig.setKeyIdentifierType(12);
        sig.setWsDocInfo(this.wsDocInfo);
        sig.setExpandXopInclude(this.isExpandXopInclude());
        sig.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
        sig.setStoreBytesInAttachment(this.storeBytesInAttachment);
        String tokenType = secToken.getTokenType();
        if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType)) {
            sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
        } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
            sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
        } else if (tokenType != null) {
            sig.setCustomTokenValueType(tokenType);
        } else {
            sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
        }
        sig.setSignatureAlgorithm(this.binding.getAlgorithmSuite().getAsymmetricSignature());
        sig.setSigCanonicalization(this.binding.getAlgorithmSuite().getC14n().getValue());
        Crypto crypto = secToken.getCrypto();
        String uname = null;
        try {
            uname = crypto.getX509Identifier(secToken.getX509Certificate());
        }
        catch (WSSecurityException e1) {
            LOG.log(Level.FINE, e1.getMessage(), e1);
            throw new Fault(e1);
        }
        String password = (String)SecurityUtils.getSecurityPropertyValue("security.signature.password", this.message);
        if (StringUtils.isEmpty(password)) {
            password = this.getPassword(uname, token, 3);
        }
        sig.setUserInfo(uname, password);
        try {
            sig.prepare(secToken.getCrypto());
        }
        catch (WSSecurityException e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            throw new Fault(e);
        }
        return new SupportingToken(token, sig, this.getSignedParts(suppTokens));
    }

    protected void handleUsernameTokenSupportingToken(UsernameToken token, boolean endorse, boolean encryptedToken, List<SupportingToken> ret) throws WSSecurityException {
        if (endorse) {
            WSSecUsernameToken utBuilder = this.addDKUsernameToken(token, true);
            if (utBuilder != null) {
                utBuilder.prepare();
                this.addSupportingElement(utBuilder.getUsernameTokenElement());
                ret.add(new SupportingToken(token, utBuilder, null));
                if (encryptedToken) {
                    WSEncryptionPart part = new WSEncryptionPart(utBuilder.getId(), "Element");
                    part.setElement(utBuilder.getUsernameTokenElement());
                    this.encryptedTokensList.add(part);
                }
            }
        } else {
            WSSecUsernameToken utBuilder = this.addUsernameToken(token);
            if (utBuilder != null) {
                utBuilder.prepare();
                this.addSupportingElement(utBuilder.getUsernameTokenElement());
                ret.add(new SupportingToken(token, utBuilder, null));
                if (encryptedToken || MessageUtils.getContextualBoolean(this.message, "ws-security.username-token.always.encrypted", true)) {
                    WSEncryptionPart part = new WSEncryptionPart(utBuilder.getId(), "Element");
                    part.setElement(utBuilder.getUsernameTokenElement());
                    this.encryptedTokensList.add(part);
                }
            }
        }
    }

    protected Element cloneElement(Element el) {
        Document doc = this.secHeader.getSecurityHeaderElement().getOwnerDocument();
        if (!doc.equals(el.getOwnerDocument())) {
            XMLStreamReader reader = StaxUtils.createXMLStreamReader(el);
            DocumentFragment fragment = doc.createDocumentFragment();
            W3CDOMStreamWriter writer = new W3CDOMStreamWriter(fragment);
            try {
                StaxUtils.copy(reader, (XMLStreamWriter)writer);
                return (Element)fragment.getFirstChild();
            }
            catch (XMLStreamException ex) {
                LOG.log(Level.FINE, "Error cloning security element", ex);
            }
        }
        return el;
    }

    protected void addSignatureParts(List<SupportingToken> tokenList, List<WSEncryptionPart> sigParts) {
        boolean useSTRTransform = MessageUtils.getContextualBoolean(this.message, "ws-security.use.str.transform", true);
        for (SupportingToken supportingToken : tokenList) {
            Object tempTok = supportingToken.getTokenImplementation();
            WSEncryptionPart part = null;
            if (tempTok instanceof WSSecSignature) {
                WSSecSignature tempSig = (WSSecSignature)tempTok;
                SecurityTokenReference secRef = tempSig.getSecurityTokenReference();
                if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(secRef.getKeyIdentifierValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(secRef.getKeyIdentifierValueType())) {
                    Element secRefElement = this.cloneElement(secRef.getElement());
                    this.addSupportingElement(secRefElement);
                    part = new WSEncryptionPart("STRTransform", null, "Element");
                    part.setId(tempSig.getSecurityTokenReferenceURI());
                    part.setElement(secRefElement);
                } else if (tempSig.getBSTTokenId() != null) {
                    part = new WSEncryptionPart(tempSig.getBSTTokenId());
                    part.setElement(tempSig.getBinarySecurityTokenElement());
                }
            } else if (tempTok instanceof WSSecUsernameToken) {
                WSSecUsernameToken unt = (WSSecUsernameToken)tempTok;
                part = new WSEncryptionPart(unt.getId());
                part.setElement(unt.getUsernameTokenElement());
            } else if (tempTok instanceof BinarySecurity) {
                BinarySecurity bst = (BinarySecurity)tempTok;
                part = new WSEncryptionPart(bst.getID());
                part.setElement(bst.getElement());
            } else if (tempTok instanceof SamlAssertionWrapper) {
                boolean saml1;
                SamlAssertionWrapper assertionWrapper = (SamlAssertionWrapper)tempTok;
                Document doc = assertionWrapper.getElement().getOwnerDocument();
                boolean bl = saml1 = assertionWrapper.getSaml1() != null;
                if (useSTRTransform) {
                    SecurityTokenReference secRef = this.createSTRForSamlAssertion(doc, assertionWrapper.getId(), saml1, false);
                    Element clone = this.cloneElement(secRef.getElement());
                    this.addSupportingElement(clone);
                    part = new WSEncryptionPart("STRTransform", null, "Element");
                    part.setId(secRef.getID());
                    part.setElement(clone);
                } else {
                    part = new WSEncryptionPart(assertionWrapper.getId());
                    part.setElement(assertionWrapper.getElement());
                }
            } else if (tempTok instanceof WSSecurityTokenHolder) {
                SecurityToken token = ((WSSecurityTokenHolder)tempTok).getToken();
                String tokenType = token.getTokenType();
                if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
                    Document doc = token.getToken().getOwnerDocument();
                    boolean saml1 = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType);
                    String id = token.getId();
                    if (id == null || "".equals(id)) {
                        id = saml1 ? token.getToken().getAttributeNS(null, "AssertionID") : token.getToken().getAttributeNS(null, "ID");
                    }
                    if (useSTRTransform) {
                        SecurityTokenReference secRef = this.createSTRForSamlAssertion(doc, id, saml1, false);
                        Element clone = this.cloneElement(secRef.getElement());
                        this.addSupportingElement(clone);
                        part = new WSEncryptionPart("STRTransform", null, "Element");
                        part.setId(secRef.getID());
                        part.setElement(clone);
                    } else {
                        part = new WSEncryptionPart(id);
                        part.setElement(token.getToken());
                    }
                } else {
                    String id = XMLUtils.getIDFromReference(token.getId());
                    part = new WSEncryptionPart(id);
                    part.setElement(token.getToken());
                }
            } else {
                this.unassertPolicy((Assertion)supportingToken.getToken(), "UnsupportedTokenInSupportingToken: " + tempTok);
            }
            if (part == null) continue;
            sigParts.add(part);
        }
    }

    private SecurityTokenReference createSTRForSamlAssertion(Document doc, String id, boolean saml1, boolean useDirectReferenceToAssertion) {
        SecurityTokenReference secRefSaml = new SecurityTokenReference(doc);
        String secRefID = this.wssConfig.getIdAllocator().createSecureId("STR-", secRefSaml);
        secRefSaml.setID(secRefID);
        if (useDirectReferenceToAssertion) {
            org.apache.wss4j.common.token.Reference ref = new org.apache.wss4j.common.token.Reference(doc);
            ref.setURI("#" + id);
            if (saml1) {
                ref.setValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
                secRefSaml.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
            } else {
                secRefSaml.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
            }
            secRefSaml.setReference(ref);
        } else {
            Element keyId = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:KeyIdentifier");
            String valueType = null;
            if (saml1) {
                valueType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID";
                secRefSaml.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
            } else {
                valueType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID";
                secRefSaml.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
            }
            keyId.setAttributeNS(null, "ValueType", valueType);
            keyId.appendChild(doc.createTextNode(id));
            Element elem = secRefSaml.getElement();
            elem.appendChild(keyId);
        }
        return secRefSaml;
    }

    protected WSSecUsernameToken addUsernameToken(UsernameToken token) {
        this.assertToken(token);
        if (!this.isTokenRequired(token.getIncludeTokenType())) {
            return null;
        }
        String userName = (String)SecurityUtils.getSecurityPropertyValue("security.username", this.message);
        if (!StringUtils.isEmpty(userName)) {
            WSSecUsernameToken utBuilder = new WSSecUsernameToken(this.secHeader);
            utBuilder.setIdAllocator(this.wssConfig.getIdAllocator());
            utBuilder.setWsTimeSource(this.wssConfig.getCurrentTime());
            if (token.getPasswordType() == UsernameToken.PasswordType.NoPassword) {
                utBuilder.setUserInfo(userName, null);
                utBuilder.setPasswordType(null);
            } else {
                String password = (String)SecurityUtils.getSecurityPropertyValue("security.password", this.message);
                if (StringUtils.isEmpty(password)) {
                    password = this.getPassword(userName, token, 2);
                }
                if (password != null) {
                    if (token.getPasswordType() == UsernameToken.PasswordType.HashPassword) {
                        utBuilder.setPasswordType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
                    } else {
                        utBuilder.setPasswordType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
                    }
                    utBuilder.setUserInfo(userName, password);
                } else {
                    this.unassertPolicy((Assertion)token, "No password available");
                    return null;
                }
            }
            if (token.isCreated() && token.getPasswordType() != UsernameToken.PasswordType.HashPassword) {
                utBuilder.addCreated();
            }
            if (token.isNonce() && token.getPasswordType() != UsernameToken.PasswordType.HashPassword) {
                utBuilder.addNonce();
            }
            return utBuilder;
        }
        this.unassertPolicy((Assertion)token, "No username available");
        return null;
    }

    protected WSSecUsernameToken addDKUsernameToken(UsernameToken token, boolean useMac) {
        this.assertToken(token);
        if (!this.isTokenRequired(token.getIncludeTokenType())) {
            return null;
        }
        String userName = (String)SecurityUtils.getSecurityPropertyValue("security.username", this.message);
        if (!StringUtils.isEmpty(userName)) {
            WSSecUsernameToken utBuilder = new WSSecUsernameToken(this.secHeader);
            utBuilder.setIdAllocator(this.wssConfig.getIdAllocator());
            utBuilder.setWsTimeSource(this.wssConfig.getCurrentTime());
            String password = (String)SecurityUtils.getSecurityPropertyValue("security.password", this.message);
            if (StringUtils.isEmpty(password)) {
                password = this.getPassword(userName, token, 2);
            }
            if (StringUtils.isEmpty(password)) {
                this.unassertPolicy((Assertion)token, "No password available");
                return null;
            }
            utBuilder.setUserInfo(userName, password);
            utBuilder.addDerivedKey(useMac, null, 1000);
            utBuilder.prepare();
            return utBuilder;
        }
        this.unassertPolicy((Assertion)token, "No username available");
        return null;
    }

    protected SamlAssertionWrapper addSamlToken(SamlToken token) throws WSSecurityException {
        SecurityToken securityToken;
        this.assertToken(token);
        if (!this.isTokenRequired(token.getIncludeTokenType())) {
            return null;
        }
        Object o = SecurityUtils.getSecurityPropertyValue("security.saml-callback-handler", this.message);
        if (o == null && (securityToken = this.getSecurityToken()) != null) {
            Element tokenElement = securityToken.getToken();
            String namespace = tokenElement.getNamespaceURI();
            String localname = tokenElement.getLocalName();
            SamlToken.SamlTokenType tokenType = token.getSamlTokenType();
            if ((tokenType == SamlToken.SamlTokenType.WssSamlV11Token10 || tokenType == SamlToken.SamlTokenType.WssSamlV11Token11) && "urn:oasis:names:tc:SAML:1.0:assertion".equals(namespace) && "Assertion".equals(localname)) {
                return new SamlAssertionWrapper(tokenElement);
            }
            if (tokenType == SamlToken.SamlTokenType.WssSamlV20Token11 && "urn:oasis:names:tc:SAML:2.0:assertion".equals(namespace) && "Assertion".equals(localname)) {
                return new SamlAssertionWrapper(tokenElement);
            }
        }
        SAMLCallback samlCallback = new SAMLCallback();
        SamlToken.SamlTokenType tokenType = token.getSamlTokenType();
        if (tokenType == SamlToken.SamlTokenType.WssSamlV11Token10 || tokenType == SamlToken.SamlTokenType.WssSamlV11Token11) {
            samlCallback.setSamlVersion(Version.SAML_11);
        } else if (tokenType == SamlToken.SamlTokenType.WssSamlV20Token11) {
            samlCallback.setSamlVersion(Version.SAML_20);
        }
        try {
            CallbackHandler handler = SecurityUtils.getCallbackHandler(o);
            if (handler == null) {
                this.unassertPolicy((Assertion)token, "No SAML CallbackHandler available");
                return null;
            }
            SAMLUtil.doSAMLCallback(handler, samlCallback);
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        SamlAssertionWrapper assertion = new SamlAssertionWrapper(samlCallback);
        if (samlCallback.isSignAssertion()) {
            Crypto crypto;
            String password;
            String issuerName = samlCallback.getIssuerKeyName();
            if (issuerName == null) {
                String userNameKey = "security.signature.username";
                issuerName = (String)SecurityUtils.getSecurityPropertyValue(userNameKey, this.message);
            }
            if ((password = samlCallback.getIssuerKeyPassword()) == null && StringUtils.isEmpty(password = (String)SecurityUtils.getSecurityPropertyValue("security.signature.password", this.message))) {
                password = this.getPassword(issuerName, token, 3);
            }
            if ((crypto = samlCallback.getIssuerCrypto()) == null) {
                crypto = this.getSignatureCrypto();
            }
            assertion.signAssertion(issuerName, password, crypto, samlCallback.isSendKeyValue(), samlCallback.getCanonicalizationAlgorithm(), samlCallback.getSignatureAlgorithm(), samlCallback.getSignatureDigestAlgorithm());
        }
        return assertion;
    }

    protected void storeAssertionAsSecurityToken(SamlAssertionWrapper assertion) {
        String id = this.findIDFromSamlToken(assertion.getElement());
        if (id == null) {
            return;
        }
        SecurityToken secToken = new SecurityToken(id);
        if (assertion.getSaml2() != null) {
            secToken.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        } else {
            secToken.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
        }
        secToken.setToken(assertion.getElement());
        this.getTokenStore().add(secToken);
        this.message.put("ws-security.token.id", (Object)secToken.getId());
    }

    protected String findIDFromSamlToken(Element samlToken) {
        String id = null;
        if (samlToken != null) {
            QName elName = DOMUtils.getElementQName(samlToken);
            if (elName.equals(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion")) && samlToken.hasAttributeNS(null, "AssertionID")) {
                id = samlToken.getAttributeNS(null, "AssertionID");
            } else if (elName.equals(new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion")) && samlToken.hasAttributeNS(null, "ID")) {
                id = samlToken.getAttributeNS(null, "ID");
            }
            if (id == null) {
                id = samlToken.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
            }
        }
        return id;
    }

    protected String getPassword(String userName, Assertion info, int usage) {
        Object o = SecurityUtils.getSecurityPropertyValue("security.callback-handler", this.message);
        CallbackHandler handler = null;
        try {
            handler = SecurityUtils.getCallbackHandler(o);
            if (handler == null) {
                if (usage == 3) {
                    LOG.info("No CallbackHandler available to retrieve a password. We will now try the crypto properties file for a private password");
                } else {
                    this.unassertPolicy(info, "No callback handler and no password available");
                }
                return null;
            }
        }
        catch (Exception ex) {
            if (usage == 3) {
                LOG.info("No CallbackHandler available to retrieve a password. We will now try the crypto properties file for a private password");
            } else {
                this.unassertPolicy(info, "No callback handler and no password available");
            }
            return null;
        }
        Callback[] cb = new WSPasswordCallback[]{new WSPasswordCallback(userName, usage)};
        try {
            handler.handle(cb);
        }
        catch (Exception e) {
            this.unassertPolicy(info, e);
        }
        return ((WSPasswordCallback)cb[0]).getPassword();
    }

    public String addWsuIdToElement(Element elem) {
        String id;
        Attr idAttr = elem.getAttributeNodeNS(null, "Id");
        if (idAttr == null) {
            idAttr = elem.getAttributeNodeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
        }
        if (idAttr != null) {
            id = idAttr.getValue();
        } else {
            id = this.wssConfig.getIdAllocator().createId("_", elem);
            String pfx = null;
            try {
                pfx = elem.lookupPrefix("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
            }
            catch (Throwable t2) {
                pfx = DOMUtils.getPrefixRecursive(elem, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
            }
            boolean found = !StringUtils.isEmpty(pfx);
            int cnt = 0;
            while (StringUtils.isEmpty(pfx)) {
                String ns;
                pfx = "wsu" + (cnt == 0 ? "" : Integer.valueOf(cnt));
                try {
                    ns = elem.lookupNamespaceURI(pfx);
                }
                catch (Throwable t3) {
                    ns = DOMUtils.getNamespace(elem, pfx);
                }
                if (StringUtils.isEmpty(ns)) continue;
                pfx = null;
                ++cnt;
            }
            if (!found) {
                idAttr = elem.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + pfx);
                idAttr.setValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
                elem.setAttributeNodeNS(idAttr);
            }
            idAttr = elem.getOwnerDocument().createAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", pfx + ":Id");
            idAttr.setValue(id);
            elem.setAttributeNodeNS(idAttr);
        }
        return id;
    }

    public List<WSEncryptionPart> getEncryptedParts() throws SOAPException {
        EncryptedParts parts = null;
        EncryptedElements elements = null;
        ContentEncryptedElements celements = null;
        Collection<AssertionInfo> ais = this.getAllAssertionsByLocalname("EncryptedParts");
        if (!ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                parts = (EncryptedParts)ai.getAssertion();
                ai.setAsserted(true);
            }
        }
        if (!(ais = this.getAllAssertionsByLocalname("EncryptedElements")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                elements = (EncryptedElements)ai.getAssertion();
                ai.setAsserted(true);
            }
        }
        if (!(ais = this.getAllAssertionsByLocalname("ContentEncryptedElements")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                celements = (ContentEncryptedElements)ai.getAssertion();
                ai.setAsserted(true);
            }
        }
        if (parts == null && elements == null && celements == null) {
            return new ArrayList<WSEncryptionPart>();
        }
        ArrayList<WSEncryptionPart> securedParts = new ArrayList<WSEncryptionPart>();
        boolean isBody = false;
        if (parts != null) {
            WSEncryptionPart wep;
            isBody = parts.isBody();
            for (Header head : parts.getHeaders()) {
                wep = new WSEncryptionPart(head.getName(), head.getNamespace(), "Header");
                securedParts.add(wep);
            }
            Attachments attachments = parts.getAttachments();
            if (attachments != null) {
                String encModifier = "Element";
                if (MessageUtils.getContextualBoolean(this.message, "ws-security.swa.encryption.attachment.transform.content", false)) {
                    encModifier = "Content";
                }
                wep = new WSEncryptionPart("cid:Attachments", encModifier);
                securedParts.add(wep);
            }
        }
        return this.getPartsAndElements(false, isBody, securedParts, elements == null ? null : elements.getXPaths(), celements == null ? null : celements.getXPaths());
    }

    public List<WSEncryptionPart> getSignedParts(SupportingTokens supportingToken) throws SOAPException {
        boolean isSignBody = false;
        SignedParts parts = null;
        SignedElements elements = null;
        if (supportingToken != null && supportingToken.isEndorsing()) {
            parts = supportingToken.getSignedParts();
            elements = supportingToken.getSignedElements();
            if (parts != null) {
                this.suppTokenParts.add(parts);
                this.assertPolicy(parts.getName());
            }
            if (elements != null) {
                this.suppTokenParts.add(elements);
                this.assertPolicy(elements.getName());
            }
        } else {
            Collection<AssertionInfo> ais = this.getAllAssertionsByLocalname("SignedParts");
            if (!ais.isEmpty()) {
                for (AssertionInfo ai : ais) {
                    SignedParts signedParts = (SignedParts)ai.getAssertion();
                    ai.setAsserted(true);
                    if (this.suppTokenParts.contains(signedParts)) continue;
                    parts = signedParts;
                }
            }
            if (!(ais = this.getAllAssertionsByLocalname("SignedElements")).isEmpty()) {
                for (AssertionInfo ai : ais) {
                    SignedElements signedElements = (SignedElements)ai.getAssertion();
                    ai.setAsserted(true);
                    if (this.suppTokenParts.contains(signedElements)) continue;
                    elements = signedElements;
                }
            }
        }
        if (parts == null && elements == null) {
            return new ArrayList<WSEncryptionPart>();
        }
        ArrayList<WSEncryptionPart> signedParts = new ArrayList<WSEncryptionPart>();
        if (parts != null) {
            WSEncryptionPart wep;
            isSignBody = parts.isBody();
            for (Header head : parts.getHeaders()) {
                wep = new WSEncryptionPart(head.getName(), head.getNamespace(), "Header");
                signedParts.add(wep);
            }
            Attachments attachments = parts.getAttachments();
            if (attachments != null) {
                String modifier = "Element";
                if (attachments.isContentSignatureTransform()) {
                    modifier = "Content";
                }
                wep = new WSEncryptionPart("cid:Attachments", modifier);
                signedParts.add(wep);
            }
        }
        return this.getPartsAndElements(true, isSignBody, signedParts, elements == null ? null : elements.getXPaths(), null);
    }

    public List<WSEncryptionPart> getPartsAndElements(boolean sign, boolean includeBody, List<WSEncryptionPart> parts, List<org.apache.wss4j.policy.model.XPath> xpaths, List<org.apache.wss4j.policy.model.XPath> contentXpaths) throws SOAPException {
        ArrayList<WSEncryptionPart> result = new ArrayList<WSEncryptionPart>();
        ArrayList<Element> found = new ArrayList<Element>();
        result.addAll(this.getParts(sign, includeBody, parts, found));
        result.addAll(this.getElements("Header", xpaths, found, sign));
        if (!sign) {
            result.addAll(this.getElements("Content", contentXpaths, found, sign));
        }
        return result;
    }

    protected List<WSEncryptionPart> getParts(boolean sign, boolean includeBody, List<WSEncryptionPart> parts, List<Element> found) throws SOAPException {
        ArrayList<WSEncryptionPart> result = new ArrayList<WSEncryptionPart>();
        Element soapBody = SAAJUtils.getBody(this.saaj);
        soapBody = (Element)DOMUtils.getDomElement(soapBody);
        if (includeBody && !found.contains(soapBody)) {
            WSEncryptionPart bodyPart;
            found.add(soapBody);
            String id = this.addWsuIdToElement(soapBody);
            if (sign) {
                bodyPart = new WSEncryptionPart(id, "Element");
                bodyPart.setElement(soapBody);
                result.add(bodyPart);
            } else {
                bodyPart = new WSEncryptionPart(id, "Content");
                bodyPart.setElement(soapBody);
                result.add(bodyPart);
            }
        }
        SOAPHeader header = SAAJUtils.getHeader(this.saaj);
        for (WSEncryptionPart part : parts) {
            if (part.getId() != null && part.getId().startsWith("cid:")) {
                result.add(part);
                continue;
            }
            List<Element> elements = StringUtils.isEmpty(part.getName()) ? DOMUtils.getChildrenWithNamespace(header, part.getNamespace()) : DOMUtils.getChildrenWithName(header, part.getNamespace(), part.getName());
            for (Element el : elements) {
                if (found.contains(el)) continue;
                found.add(el);
                String id = this.addWsuIdToElement(el);
                WSEncryptionPart elPart = new WSEncryptionPart(id, part.getEncModifier());
                elPart.setElement(el);
                result.add(elPart);
            }
        }
        return result;
    }

    protected List<WSEncryptionPart> getElements(String encryptionModifier, List<org.apache.wss4j.policy.model.XPath> xpaths, List<Element> found, boolean forceId) throws SOAPException {
        ArrayList<WSEncryptionPart> result = new ArrayList<WSEncryptionPart>();
        if (xpaths != null && !xpaths.isEmpty()) {
            boolean useSTRTransform = MessageUtils.getContextualBoolean(this.message, "ws-security.use.str.transform", true);
            XPathFactory factory2 = XPathFactory.newInstance();
            try {
                factory2.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            }
            catch (XPathFactoryConfigurationException xPathFactoryConfigurationException) {
                // empty catch block
            }
            for (org.apache.wss4j.policy.model.XPath xPath : xpaths) {
                XPath xpath = factory2.newXPath();
                if (xPath.getPrefixNamespaceMap() != null) {
                    xpath.setNamespaceContext(new MapNamespaceContext(xPath.getPrefixNamespaceMap()));
                }
                NodeList list = null;
                try {
                    Element envelope = this.saaj.getSOAPPart().getEnvelope();
                    envelope = (Element)DOMUtils.getDomElement(envelope);
                    list = (NodeList)xpath.evaluate(xPath.getXPath(), envelope, XPathConstants.NODESET);
                }
                catch (XPathExpressionException e) {
                    LOG.log(Level.WARNING, "Failure in evaluating an XPath expression", e);
                }
                if (list == null) continue;
                for (int x = 0; x < list.getLength(); ++x) {
                    String id;
                    boolean saml2;
                    Element el = (Element)list.item(x);
                    if (found.contains(el)) continue;
                    found.add(el);
                    WSEncryptionPart part = null;
                    boolean saml1 = "urn:oasis:names:tc:SAML:1.0:assertion".equals(el.getNamespaceURI()) && "Assertion".equals(el.getLocalName());
                    boolean bl = saml2 = "urn:oasis:names:tc:SAML:2.0:assertion".equals(el.getNamespaceURI()) && "Assertion".equals(el.getLocalName());
                    if (useSTRTransform && (saml1 || saml2)) {
                        id = saml2 ? el.getAttributeNS(null, "ID") : el.getAttributeNS(null, "AssertionID");
                        SecurityTokenReference secRef = this.createSTRForSamlAssertion(el.getOwnerDocument(), id, saml1, false);
                        Element clone = this.cloneElement(secRef.getElement());
                        this.addSupportingElement(clone);
                        part = new WSEncryptionPart("STRTransform", null, "Element");
                        part.setId(secRef.getID());
                        part.setElement(clone);
                    } else {
                        id = this.setIdOnElement(el, forceId);
                        part = new WSEncryptionPart(id, encryptionModifier);
                        part.setElement(el);
                    }
                    part.setXpath(xPath.getXPath());
                    result.add(part);
                }
            }
        }
        return result;
    }

    private String setIdOnElement(Element element, boolean forceId) {
        if (forceId) {
            return this.addWsuIdToElement(element);
        }
        Attr idAttr = element.getAttributeNodeNS(null, "Id");
        if (idAttr == null) {
            idAttr = element.getAttributeNodeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
        }
        if (idAttr != null) {
            return idAttr.getValue();
        }
        return null;
    }

    protected WSSecEncryptedKey getEncryptedKeyBuilder(AbstractToken token) throws WSSecurityException {
        WSSecEncryptedKey encrKey = new WSSecEncryptedKey(this.secHeader);
        encrKey.setIdAllocator(this.wssConfig.getIdAllocator());
        encrKey.setCallbackLookup(this.callbackLookup);
        encrKey.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
        encrKey.setStoreBytesInAttachment(this.storeBytesInAttachment);
        Crypto crypto = this.getEncryptionCrypto();
        this.message.getExchange().put("security.encryption.crypto", crypto);
        this.setKeyIdentifierType(encrKey, token);
        boolean alsoIncludeToken = false;
        if (token instanceof X509Token && token.getIncludeTokenType() != SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER && encrKey.getKeyIdentifierType() != 1) {
            alsoIncludeToken = true;
        }
        String encrUser = this.setEncryptionUser(encrKey, token, false, crypto);
        AlgorithmSuite.AlgorithmSuiteType algType = this.binding.getAlgorithmSuite().getAlgorithmSuiteType();
        encrKey.setSymmetricEncAlgorithm(algType.getEncryption());
        encrKey.setKeyEncAlgo(algType.getAsymmetricKeyWrap());
        encrKey.setMGFAlgorithm(algType.getMGFAlgo());
        encrKey.prepare(crypto);
        if (alsoIncludeToken) {
            X509Certificate encCert = this.getEncryptCert(crypto, encrUser);
            X509Security bstToken = new X509Security(this.saaj.getSOAPPart());
            bstToken.setX509Certificate(encCert);
            bstToken.addWSUNamespace();
            bstToken.setID(this.wssConfig.getIdAllocator().createSecureId("X509-", encCert));
            WSSecurityUtil.prependChildElement(this.secHeader.getSecurityHeaderElement(), bstToken.getElement());
            this.bstElement = bstToken.getElement();
        }
        return encrKey;
    }

    private X509Certificate getEncryptCert(Crypto crypto, String encrUser) throws WSSecurityException {
        X509Certificate encrCert = (X509Certificate)SecurityUtils.getSecurityPropertyValue("security.encryption.certificate", this.message);
        if (encrCert != null) {
            return encrCert;
        }
        CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
        cryptoType.setAlias(encrUser);
        X509Certificate[] certs = crypto.getX509Certificates(cryptoType);
        if (certs != null && certs.length > 0) {
            return certs[0];
        }
        return null;
    }

    public Crypto getSignatureCrypto() throws WSSecurityException {
        return this.getCrypto("security.signature.crypto", "security.signature.properties");
    }

    public Crypto getEncryptionCrypto() throws WSSecurityException {
        Crypto crypto = this.getCrypto("security.encryption.crypto", "security.encryption.properties");
        boolean enableRevocation = false;
        String enableRevStr = (String)SecurityUtils.getSecurityPropertyValue("security.enableRevocation", this.message);
        if (enableRevStr != null) {
            enableRevocation = Boolean.parseBoolean(enableRevStr);
        }
        if (enableRevocation && crypto != null) {
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
            String encrUser = (String)SecurityUtils.getSecurityPropertyValue("security.encryption.username", this.message);
            if (encrUser == null) {
                try {
                    encrUser = crypto.getDefaultX509Identifier();
                }
                catch (WSSecurityException e1) {
                    throw new Fault(e1);
                }
            }
            cryptoType.setAlias(encrUser);
            X509Certificate[] certs = crypto.getX509Certificates(cryptoType);
            if (certs != null && certs.length > 0) {
                crypto.verifyTrust(certs, enableRevocation, null, null);
            }
        }
        if (crypto != null) {
            this.message.getExchange().put("security.encryption.crypto", crypto);
        }
        return crypto;
    }

    protected Crypto getCrypto(String cryptoKey, String propKey) throws WSSecurityException {
        Crypto crypto = (Crypto)SecurityUtils.getSecurityPropertyValue(cryptoKey, this.message);
        if (crypto != null) {
            return crypto;
        }
        Object o = SecurityUtils.getSecurityPropertyValue(propKey, this.message);
        if (o == null) {
            return null;
        }
        crypto = this.getCryptoCache().get(o);
        if (crypto != null) {
            return crypto;
        }
        URL propsURL = SecurityUtils.loadResource(this.message, o);
        Properties properties = WSS4JUtils.getProps(o, propsURL);
        if (properties != null) {
            crypto = CryptoFactory.getInstance(properties, Loader.getClassLoader(CryptoFactory.class), WSS4JUtils.getPasswordEncryptor(this.message));
            this.getCryptoCache().put(o, crypto);
        }
        return crypto;
    }

    public void setKeyIdentifierType(WSSecBase secBase, AbstractToken token) {
        boolean tokenTypeSet = false;
        if (token instanceof X509Token) {
            X509Token x509Token = (X509Token)token;
            if (x509Token.isRequireIssuerSerialReference()) {
                secBase.setKeyIdentifierType(2);
                tokenTypeSet = true;
            } else if (x509Token.isRequireKeyIdentifierReference()) {
                secBase.setKeyIdentifierType(4);
                tokenTypeSet = true;
            } else if (x509Token.isRequireThumbprintReference()) {
                secBase.setKeyIdentifierType(8);
                tokenTypeSet = true;
            }
        } else if (token instanceof KeyValueToken) {
            secBase.setKeyIdentifierType(13);
            tokenTypeSet = true;
        }
        this.assertToken(token);
        if (!tokenTypeSet) {
            boolean requestor = this.isRequestor();
            if (token.getIncludeTokenType() == SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER || token instanceof X509Token && (token.getIncludeTokenType() == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_RECIPIENT && !requestor || token.getIncludeTokenType() == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_INITIATOR && requestor)) {
                Wss10 wss = this.getWss10();
                this.assertPolicy(wss);
                if (wss == null || wss.isMustSupportRefKeyIdentifier()) {
                    secBase.setKeyIdentifierType(4);
                } else if (wss.isMustSupportRefIssuerSerial()) {
                    secBase.setKeyIdentifierType(2);
                } else if (wss instanceof Wss11 && ((Wss11)wss).isMustSupportRefThumbprint()) {
                    secBase.setKeyIdentifierType(8);
                } else {
                    secBase.setKeyIdentifierType(2);
                }
            } else {
                secBase.setKeyIdentifierType(1);
            }
        }
    }

    public String setEncryptionUser(WSSecEncryptedKey encrKeyBuilder, AbstractToken token, boolean sign, Crypto crypto) {
        X509Certificate encrCert = (X509Certificate)SecurityUtils.getSecurityPropertyValue("security.encryption.certificate", this.message);
        if (encrCert != null) {
            encrKeyBuilder.setUseThisCert(encrCert);
            return null;
        }
        String key = sign ? "security.signature.username" : "security.encryption.username";
        String encrUser = (String)SecurityUtils.getSecurityPropertyValue(key, this.message);
        if (crypto != null && (encrUser == null || "".equals(encrUser))) {
            try {
                encrUser = crypto.getDefaultX509Identifier();
            }
            catch (WSSecurityException e1) {
                throw new Fault(e1);
            }
        }
        if (encrUser == null || "".equals(encrUser)) {
            this.unassertPolicy((Assertion)token, "A " + (sign ? "signature" : "encryption") + " username needs to be declared.");
        }
        if ("useReqSigCert".equals(encrUser)) {
            List<WSHandlerResult> results = CastUtils.cast((List)this.message.getExchange().getInMessage().get("RECV_RESULTS"));
            if (results != null) {
                encrKeyBuilder.setUseThisCert(WSS4JUtils.getReqSigCert(results));
                if (encrKeyBuilder.isCertSet()) {
                    encrKeyBuilder.setUserInfo(AbstractBindingBuilder.getUsername(results));
                }
            } else {
                this.unassertPolicy((Assertion)token, "No security results in incoming message");
            }
        } else {
            encrKeyBuilder.setUserInfo(encrUser);
        }
        return encrUser;
    }

    public static String getUsername(List<WSHandlerResult> results) {
        for (WSHandlerResult rResult : results) {
            List<WSSecurityEngineResult> wsSecEngineResults = rResult.getResults();
            for (WSSecurityEngineResult wser : wsSecEngineResults) {
                Integer actInt = (Integer)wser.get("action");
                if (actInt != 1) continue;
                UsernameTokenPrincipal principal = (UsernameTokenPrincipal)wser.get("principal");
                return principal.getName();
            }
        }
        return null;
    }

    protected WSSecurityEngineResult getEncryptedKeyResult() {
        List<WSHandlerResult> results = CastUtils.cast((List)this.message.getExchange().getInMessage().get("RECV_RESULTS"));
        for (WSHandlerResult rResult : results) {
            List<WSSecurityEngineResult> encryptedResults = rResult.getActionResults().get(4);
            if (encryptedResults == null) continue;
            for (WSSecurityEngineResult wser : encryptedResults) {
                String encryptedKeyID = (String)wser.get("id");
                if (encryptedKeyID == null || encryptedKeyID.length() == 0) continue;
                return wser;
            }
        }
        return null;
    }

    private void checkForX509PkiPath(WSSecSignature sig, AbstractToken token) {
        X509Token x509Token;
        X509Token.TokenType tokenType;
        if (token instanceof X509Token && ((tokenType = (x509Token = (X509Token)token).getTokenType()) == X509Token.TokenType.WssX509PkiPathV1Token10 || tokenType == X509Token.TokenType.WssX509PkiPathV1Token11)) {
            sig.setUseSingleCertificate(false);
        }
    }

    protected WSSecSignature getSignatureBuilder(AbstractToken token, boolean attached, boolean endorse) throws WSSecurityException {
        String password;
        String user;
        Crypto crypto;
        WSSecSignature sig;
        block24: {
            sig = new WSSecSignature(this.secHeader);
            sig.setIdAllocator(this.wssConfig.getIdAllocator());
            sig.setCallbackLookup(this.callbackLookup);
            sig.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
            sig.setStoreBytesInAttachment(this.storeBytesInAttachment);
            sig.setExpandXopInclude(this.isExpandXopInclude());
            sig.setWsDocInfo(this.wsDocInfo);
            this.checkForX509PkiPath(sig, token);
            if (token instanceof IssuedToken || token instanceof SamlToken) {
                String sigTokId;
                this.assertToken(token);
                SecurityToken securityToken = this.getSecurityToken();
                String tokenType = securityToken.getTokenType();
                Element ref = attached ? securityToken.getAttachedReference() : securityToken.getUnattachedReference();
                if (ref != null) {
                    SecurityTokenReference secRef = new SecurityTokenReference(this.cloneElement(ref), new BSPEnforcer());
                    sig.setSecurityTokenReference(secRef);
                    sig.setKeyIdentifierType(12);
                } else {
                    int type;
                    int n = type = attached ? 9 : 11;
                    if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType)) {
                        sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
                        sig.setKeyIdentifierType(12);
                    } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
                        sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
                        sig.setKeyIdentifierType(12);
                    } else {
                        sig.setCustomTokenValueType(tokenType);
                        sig.setKeyIdentifierType(type);
                    }
                }
                if (attached) {
                    sigTokId = securityToken.getWsuId();
                    if (sigTokId == null) {
                        sigTokId = securityToken.getId();
                    }
                    if (sigTokId.startsWith("#")) {
                        sigTokId = sigTokId.substring(1);
                    }
                } else {
                    sigTokId = securityToken.getId();
                }
                sig.setCustomTokenId(sigTokId);
            } else {
                this.setKeyIdentifierType(sig, token);
                if (token instanceof X509Token && token.getIncludeTokenType() != SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER && sig.getKeyIdentifierType() != 1 && sig.getKeyIdentifierType() != 13) {
                    sig.setIncludeSignatureToken(true);
                }
            }
            boolean encryptCrypto = false;
            String userNameKey = "security.signature.username";
            String type = "signature";
            if (this.binding instanceof SymmetricBinding && !endorse) {
                encryptCrypto = ((SymmetricBinding)this.binding).getProtectionToken() != null;
                userNameKey = "security.encryption.username";
            }
            Crypto crypto2 = crypto = encryptCrypto ? this.getEncryptionCrypto() : this.getSignatureCrypto();
            if (endorse && crypto == null && this.binding instanceof SymmetricBinding) {
                type = "encryption";
                userNameKey = "security.encryption.username";
                crypto = this.getEncryptionCrypto();
            }
            if (!encryptCrypto) {
                this.message.getExchange().put("security.signature.crypto", crypto);
            }
            if (StringUtils.isEmpty(user = (String)SecurityUtils.getSecurityPropertyValue(userNameKey, this.message))) {
                if (crypto != null) {
                    try {
                        user = crypto.getDefaultX509Identifier();
                        if (StringUtils.isEmpty(user)) {
                            this.unassertPolicy((Assertion)token, "No configured " + type + " username detected");
                            return null;
                        }
                        break block24;
                    }
                    catch (WSSecurityException e1) {
                        LOG.log(Level.FINE, e1.getMessage(), e1);
                        throw new Fault(e1);
                    }
                }
                this.unassertPolicy((Assertion)token, "Security configuration could not be detected. Potential cause: Make sure jaxws:client element with name attribute value matching endpoint port is defined as well as a security.signature.properties element within it.");
                return null;
            }
        }
        if (StringUtils.isEmpty(password = (String)SecurityUtils.getSecurityPropertyValue("security.signature.password", this.message))) {
            password = this.getPassword(user, token, 3);
        }
        sig.setUserInfo(user, password);
        sig.setSignatureAlgorithm(this.binding.getAlgorithmSuite().getAsymmetricSignature());
        AlgorithmSuite.AlgorithmSuiteType algType = this.binding.getAlgorithmSuite().getAlgorithmSuiteType();
        sig.setDigestAlgo(algType.getDigest());
        sig.setSigCanonicalization(this.binding.getAlgorithmSuite().getC14n().getValue());
        boolean includePrefixes = MessageUtils.getContextualBoolean(this.message, "ws-security.add.inclusive.prefixes", true);
        sig.setAddInclusivePrefixes(includePrefixes);
        try {
            sig.prepare(crypto);
        }
        catch (WSSecurityException e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            this.unassertPolicy((Assertion)token, e);
        }
        return sig;
    }

    protected void doEndorsedSignatures(List<SupportingToken> tokenList, boolean isTokenProtection, boolean isSigProtect) {
        for (SupportingToken supportingToken : tokenList) {
            Object tempTok = supportingToken.getTokenImplementation();
            ArrayList<WSEncryptionPart> sigParts = new ArrayList<WSEncryptionPart>();
            WSEncryptionPart sigPart = new WSEncryptionPart(this.mainSigId);
            sigPart.setElement(this.bottomUpElement);
            sigParts.add(sigPart);
            if (supportingToken.getSignedParts() != null) {
                for (WSEncryptionPart signedPart : supportingToken.getSignedParts()) {
                    sigParts.add(signedPart);
                }
            }
            if (tempTok instanceof WSSecSignature) {
                WSSecSignature sig = (WSSecSignature)tempTok;
                if (isTokenProtection && sig.getBSTTokenId() != null) {
                    WSEncryptionPart bstPart = new WSEncryptionPart(sig.getBSTTokenId());
                    bstPart.setElement(sig.getBinarySecurityTokenElement());
                    sigParts.add(bstPart);
                }
                try {
                    List<Reference> referenceList = sig.addReferencesToSign(sigParts);
                    sig.computeSignature(referenceList, false, null);
                    this.addSig(sig.getSignatureValue());
                    if (!isSigProtect) continue;
                    WSEncryptionPart part = new WSEncryptionPart(sig.getId(), "Element");
                    this.encryptedTokensList.add(part);
                }
                catch (WSSecurityException e) {
                    this.unassertPolicy((Assertion)supportingToken.getToken(), e);
                }
                continue;
            }
            if (tempTok instanceof WSSecurityTokenHolder) {
                SecurityToken token = ((WSSecurityTokenHolder)tempTok).getToken();
                if (isTokenProtection) {
                    sigParts.add(new WSEncryptionPart(token.getId()));
                }
                try {
                    if (supportingToken.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                        this.doSymmSignatureDerived(supportingToken.getToken(), token, sigParts, isTokenProtection, isSigProtect);
                        continue;
                    }
                    this.doSymmSignature(supportingToken.getToken(), token, sigParts, isSigProtect);
                }
                catch (Exception e) {
                    LOG.log(Level.FINE, e.getMessage(), e);
                }
                continue;
            }
            if (!(tempTok instanceof WSSecUsernameToken)) continue;
            WSSecUsernameToken utBuilder = (WSSecUsernameToken)tempTok;
            String id = utBuilder.getId();
            Instant created = Instant.now();
            Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(this.message) / 1000L);
            SecurityToken secToken = new SecurityToken(id, utBuilder.getUsernameTokenElement(), created, expires);
            if (isTokenProtection) {
                sigParts.add(new WSEncryptionPart(secToken.getId()));
            }
            try {
                byte[] secret = utBuilder.getDerivedKey();
                secToken.setSecret(secret);
                if (supportingToken.getToken().getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
                    this.doSymmSignatureDerived(supportingToken.getToken(), secToken, sigParts, isTokenProtection, isSigProtect);
                    continue;
                }
                this.doSymmSignature(supportingToken.getToken(), secToken, sigParts, isSigProtect);
            }
            catch (Exception e) {
                LOG.log(Level.FINE, e.getMessage(), e);
            }
        }
    }

    private void doSymmSignatureDerived(AbstractToken policyToken, SecurityToken tok, List<WSEncryptionPart> sigParts, boolean isTokenProtection, boolean isSigProtect) throws WSSecurityException {
        Element ref;
        SOAPPart doc = this.saaj.getSOAPPart();
        WSSecDKSign dkSign = new WSSecDKSign(this.secHeader);
        dkSign.setIdAllocator(this.wssConfig.getIdAllocator());
        dkSign.setCallbackLookup(this.callbackLookup);
        dkSign.setStoreBytesInAttachment(this.storeBytesInAttachment);
        dkSign.setExpandXopInclude(this.isExpandXopInclude());
        if (policyToken.getVersion() == SPConstants.SPVersion.SP11) {
            dkSign.setWscVersion(1);
        }
        boolean attached = false;
        if (this.isTokenRequired(policyToken.getIncludeTokenType())) {
            attached = true;
        }
        if ((ref = attached ? tok.getAttachedReference() : tok.getUnattachedReference()) != null) {
            ref = this.cloneElement(ref);
            dkSign.setExternalKey(tok.getSecret(), ref);
        } else if (!this.isRequestor() && policyToken.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            SecurityTokenReference tokenRef = new SecurityTokenReference(doc);
            if (tok.getSHA1() != null) {
                tokenRef.setKeyIdentifierEncKeySHA1(tok.getSHA1());
                tokenRef.addTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
            }
            dkSign.setExternalKey(tok.getSecret(), tokenRef.getElement());
        } else {
            dkSign.setExternalKey(tok.getSecret(), tok.getId());
        }
        dkSign.setSignatureAlgorithm(this.binding.getAlgorithmSuite().getSymmetricSignature());
        dkSign.setSigCanonicalization(this.binding.getAlgorithmSuite().getC14n().getValue());
        AlgorithmSuite.AlgorithmSuiteType algType = this.binding.getAlgorithmSuite().getAlgorithmSuiteType();
        dkSign.setDerivedKeyLength(algType.getSignatureDerivedKeyLength() / 8);
        if (tok.getSHA1() != null) {
            dkSign.setCustomValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
        } else if (policyToken instanceof UsernameToken) {
            dkSign.setCustomValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
        }
        dkSign.prepare();
        if (isTokenProtection) {
            String sigTokId = XMLUtils.getIDFromReference(tok.getId());
            sigParts.add(new WSEncryptionPart(sigTokId));
        }
        dkSign.getParts().addAll(sigParts);
        List<Reference> referenceList = dkSign.addReferencesToSign(sigParts);
        this.addSupportingElement(dkSign.getdktElement());
        dkSign.computeSignature(referenceList, false, null);
        if (isSigProtect) {
            WSEncryptionPart part = new WSEncryptionPart(dkSign.getSignatureId(), "Element");
            this.encryptedTokensList.add(part);
        }
        this.addSig(dkSign.getSignatureValue());
    }

    private void doSymmSignature(AbstractToken policyToken, SecurityToken tok, List<WSEncryptionPart> sigParts, boolean isSigProtect) throws WSSecurityException {
        WSSecSignature sig = new WSSecSignature(this.secHeader);
        sig.setIdAllocator(this.wssConfig.getIdAllocator());
        sig.setCallbackLookup(this.callbackLookup);
        sig.setAttachmentCallbackHandler(new AttachmentCallbackHandler(this.message));
        sig.setStoreBytesInAttachment(this.storeBytesInAttachment);
        sig.setExpandXopInclude(this.isExpandXopInclude());
        sig.setWsDocInfo(this.wsDocInfo);
        if (policyToken instanceof X509Token) {
            if (this.isRequestor()) {
                sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                sig.setKeyIdentifierType(9);
            } else {
                sig.setEncrKeySha1value(tok.getSHA1());
                sig.setKeyIdentifierType(10);
            }
        } else {
            String tokenType = tok.getTokenType();
            if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType)) {
                sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
            } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
                sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
            } else if (tokenType != null) {
                sig.setCustomTokenValueType(tokenType);
            } else if (policyToken instanceof UsernameToken) {
                sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
            } else {
                sig.setCustomTokenValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
            }
            sig.setKeyIdentifierType(9);
        }
        String sigTokId = tok.getWsuId();
        if (sigTokId == null) {
            sigTokId = tok.getId();
        }
        sigTokId = XMLUtils.getIDFromReference(sigTokId);
        sig.setCustomTokenId(sigTokId);
        sig.setSecretKey(tok.getSecret());
        sig.setSignatureAlgorithm(this.binding.getAlgorithmSuite().getSymmetricSignature());
        AlgorithmSuite.AlgorithmSuiteType algType = this.binding.getAlgorithmSuite().getAlgorithmSuiteType();
        sig.setDigestAlgo(algType.getDigest());
        sig.setSigCanonicalization(this.binding.getAlgorithmSuite().getC14n().getValue());
        sig.prepare(this.getSignatureCrypto());
        sig.getParts().addAll(sigParts);
        List<Reference> referenceList = sig.addReferencesToSign(sigParts);
        sig.computeSignature(referenceList, false, null);
        if (isSigProtect) {
            WSEncryptionPart part = new WSEncryptionPart(sig.getId(), "Element");
            this.encryptedTokensList.add(part);
        }
        this.addSig(sig.getSignatureValue());
    }

    protected void addSupportingTokens(List<WSEncryptionPart> sigs) throws WSSecurityException {
        Collection<AssertionInfo> sgndSuppTokens = PolicyUtils.getAllAssertionsByLocalname(this.aim, "SignedSupportingTokens");
        List<SupportingToken> sigSuppTokList = this.handleSupportingTokens(sgndSuppTokens, false);
        Collection<AssertionInfo> endSuppTokens = PolicyUtils.getAllAssertionsByLocalname(this.aim, "EndorsingSupportingTokens");
        this.endSuppTokList = this.handleSupportingTokens(endSuppTokens, true);
        Collection<AssertionInfo> sgndEndSuppTokens = PolicyUtils.getAllAssertionsByLocalname(this.aim, "SignedEndorsingSupportingTokens");
        this.sgndEndSuppTokList = this.handleSupportingTokens(sgndEndSuppTokens, true);
        Collection<AssertionInfo> sgndEncryptedSuppTokens = PolicyUtils.getAllAssertionsByLocalname(this.aim, "SignedEncryptedSupportingTokens");
        List<SupportingToken> sgndEncSuppTokList = this.handleSupportingTokens(sgndEncryptedSuppTokens, false);
        Collection<AssertionInfo> endorsingEncryptedSuppTokens = PolicyUtils.getAllAssertionsByLocalname(this.aim, "EndorsingEncryptedSupportingTokens");
        this.endSuppTokList.addAll(this.handleSupportingTokens(endorsingEncryptedSuppTokens, true));
        Collection<AssertionInfo> sgndEndEncSuppTokens = PolicyUtils.getAllAssertionsByLocalname(this.aim, "SignedEndorsingEncryptedSupportingTokens");
        this.sgndEndSuppTokList.addAll(this.handleSupportingTokens(sgndEndEncSuppTokens, true));
        Collection<AssertionInfo> supportingToks = PolicyUtils.getAllAssertionsByLocalname(this.aim, "SupportingTokens");
        this.handleSupportingTokens(supportingToks, false);
        Collection<AssertionInfo> encryptedSupportingToks = PolicyUtils.getAllAssertionsByLocalname(this.aim, "EncryptedSupportingTokens");
        this.handleSupportingTokens(encryptedSupportingToks, false);
        this.addSignatureParts(sigSuppTokList, sigs);
        this.addSignatureParts(sgndEncSuppTokList, sigs);
        this.addSignatureParts(this.sgndEndSuppTokList, sigs);
    }

    protected void doEndorse() {
        boolean tokenProtect = false;
        boolean sigProtect = false;
        if (this.binding instanceof AsymmetricBinding) {
            tokenProtect = ((AsymmetricBinding)this.binding).isProtectTokens();
            sigProtect = ((AsymmetricBinding)this.binding).isEncryptSignature();
        } else if (this.binding instanceof SymmetricBinding) {
            tokenProtect = ((SymmetricBinding)this.binding).isProtectTokens();
            sigProtect = ((SymmetricBinding)this.binding).isEncryptSignature();
        }
        this.doEndorsedSignatures(this.endSuppTokList, tokenProtect, sigProtect);
        this.doEndorsedSignatures(this.sgndEndSuppTokList, tokenProtect, sigProtect);
    }

    protected void addSignatureConfirmation(List<WSEncryptionPart> sigParts) {
        Wss10 wss10 = this.getWss10();
        if (!(wss10 instanceof Wss11) || !((Wss11)wss10).isRequireSignatureConfirmation()) {
            return;
        }
        List<Object> results = CastUtils.cast((List)this.message.getExchange().getInMessage().get("RECV_RESULTS"));
        ArrayList signatureActions = new ArrayList();
        for (Object wshResult : results) {
            if (((WSHandlerResult)wshResult).getActionResults().containsKey(2)) {
                signatureActions.addAll(((WSHandlerResult)wshResult).getActionResults().get(2));
            }
            if (!((WSHandlerResult)wshResult).getActionResults().containsKey(64)) continue;
            signatureActions.addAll(((WSHandlerResult)wshResult).getActionResults().get(64));
        }
        this.sigConfList = new ArrayList<WSEncryptionPart>();
        WSSecSignatureConfirmation wsc = new WSSecSignatureConfirmation(this.secHeader);
        wsc.setIdAllocator(this.wssConfig.getIdAllocator());
        if (!signatureActions.isEmpty()) {
            for (WSSecurityEngineResult wsr : signatureActions) {
                byte[] sigVal = (byte[])wsr.get("signature-value");
                wsc.setSignatureValue(sigVal);
                wsc.prepare();
                this.addSupportingElement(wsc.getSignatureConfirmationElement());
                if (sigParts == null) continue;
                WSEncryptionPart part = new WSEncryptionPart(wsc.getId(), "Element");
                part.setElement(wsc.getSignatureConfirmationElement());
                sigParts.add(part);
                this.sigConfList.add(part);
            }
        } else {
            wsc.prepare();
            this.addSupportingElement(wsc.getSignatureConfirmationElement());
            if (sigParts != null) {
                WSEncryptionPart part = new WSEncryptionPart(wsc.getId(), "Element");
                part.setElement(wsc.getSignatureConfirmationElement());
                sigParts.add(part);
                this.sigConfList.add(part);
            }
        }
        this.assertPolicy(new QName(wss10.getName().getNamespaceURI(), "RequireSignatureConfirmation"));
    }

    public void handleEncryptedSignedHeaders(List<WSEncryptionPart> encryptedParts, List<WSEncryptionPart> signedParts) {
        ArrayList<WSEncryptionPart> signedEncryptedParts = new ArrayList<WSEncryptionPart>();
        for (WSEncryptionPart encryptedPart : encryptedParts) {
            Iterator<WSEncryptionPart> signedPartsIt = signedParts.iterator();
            while (signedPartsIt.hasNext()) {
                WSEncryptionPart signedPart = signedPartsIt.next();
                if (signedPart.getId() == null && !"Token".equals(signedPart.getName())) {
                    throw new IllegalArgumentException("WSEncryptionPart must be ID based but no id was found.");
                }
                if (!"Header".equals(encryptedPart.getEncModifier()) || !signedPart.getId().equals(encryptedPart.getId())) continue;
                signedPartsIt.remove();
                WSEncryptionPart part = new WSEncryptionPart(encryptedPart.getEncId(), encryptedPart.getEncModifier());
                part.setElement(encryptedPart.getElement());
                signedEncryptedParts.add(part);
            }
        }
        signedParts.addAll(signedEncryptedParts);
    }

    public WSEncryptionPart convertToEncryptionPart(Element element) {
        String id = this.addWsuIdToElement(element);
        WSEncryptionPart part = new WSEncryptionPart(id);
        part.setElement(element);
        return part;
    }

    protected void addSig(byte[] val) {
        if (val != null && val.length > 0) {
            this.signatures.add(Arrays.hashCode(val));
        }
    }

    public boolean isExpandXopInclude() {
        return this.expandXopInclude;
    }

    static class SupportingToken {
        private final AbstractToken token;
        private final Object tokenImplementation;
        private final List<WSEncryptionPart> signedParts;

        SupportingToken(AbstractToken token, Object tokenImplementation, List<WSEncryptionPart> signedParts) {
            this.token = token;
            this.tokenImplementation = tokenImplementation;
            this.signedParts = signedParts;
        }

        public AbstractToken getToken() {
            return this.token;
        }

        public Object getTokenImplementation() {
            return this.tokenImplementation;
        }

        public List<WSEncryptionPart> getSignedParts() {
            return this.signedParts;
        }
    }
}

