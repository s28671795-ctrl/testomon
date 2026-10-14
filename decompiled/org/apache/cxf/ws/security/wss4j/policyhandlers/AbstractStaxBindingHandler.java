/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import java.io.IOException;
import java.security.Key;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.wss4j.policyhandlers.AbstractCommonBindingHandler;
import org.apache.neethi.Assertion;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.bean.KeyInfoBean;
import org.apache.wss4j.common.saml.bean.SubjectBean;
import org.apache.wss4j.common.saml.bean.Version;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractBinding;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.Attachments;
import org.apache.wss4j.policy.model.ContentEncryptedElements;
import org.apache.wss4j.policy.model.EncryptedElements;
import org.apache.wss4j.policy.model.EncryptedParts;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.apache.wss4j.policy.model.Layout;
import org.apache.wss4j.policy.model.RequiredElements;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SignedElements;
import org.apache.wss4j.policy.model.SignedParts;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.SymmetricBinding;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.Wss10;
import org.apache.wss4j.policy.model.Wss11;
import org.apache.wss4j.policy.model.X509Token;
import org.apache.wss4j.policy.model.XPath;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.KerberosClientSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.securityToken.GenericOutboundSecurityToken;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;

public abstract class AbstractStaxBindingHandler
extends AbstractCommonBindingHandler {
    protected boolean timestampAdded;
    protected boolean signatureConfirmationAdded;
    protected Set<SecurePart> encryptedTokensList = new HashSet<SecurePart>();
    protected Map<AbstractToken, SecurePart> endEncSuppTokMap;
    protected Map<AbstractToken, SecurePart> endSuppTokMap;
    protected Map<AbstractToken, SecurePart> sgndEndEncSuppTokMap;
    protected Map<AbstractToken, SecurePart> sgndEndSuppTokMap;
    protected final OutboundSecurityContext outboundSecurityContext;
    private final WSSSecurityProperties properties;
    private AbstractBinding binding;

    public AbstractStaxBindingHandler(WSSSecurityProperties properties, SoapMessage msg, AbstractBinding binding, OutboundSecurityContext outboundSecurityContext) {
        super(msg);
        this.properties = properties;
        this.binding = binding;
        this.outboundSecurityContext = outboundSecurityContext;
    }

    protected SecurePart addUsernameToken(UsernameToken usernameToken) {
        String password;
        this.assertToken(usernameToken);
        SPConstants.IncludeTokenType includeToken = usernameToken.getIncludeTokenType();
        if (!this.isTokenRequired(includeToken)) {
            return null;
        }
        this.properties.addAction(WSSConstants.USERNAMETOKEN);
        UsernameToken.PasswordType passwordType = usernameToken.getPasswordType();
        if (passwordType == UsernameToken.PasswordType.HashPassword) {
            this.properties.setUsernameTokenPasswordType(WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST);
        } else if (passwordType == UsernameToken.PasswordType.NoPassword) {
            this.properties.setUsernameTokenPasswordType(WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE);
        } else {
            this.properties.setUsernameTokenPasswordType(WSSConstants.UsernameTokenPasswordType.PASSWORD_TEXT);
        }
        if (usernameToken.isNonce()) {
            this.properties.setAddUsernameTokenNonce(true);
        }
        if (usernameToken.isCreated()) {
            this.properties.setAddUsernameTokenCreated(true);
        }
        if (this.properties.getCallbackHandler() == null && (password = (String)SecurityUtils.getSecurityPropertyValue("security.password", this.message)) != null) {
            String username = (String)SecurityUtils.getSecurityPropertyValue("security.username", this.message);
            UTCallbackHandler callbackHandler = new UTCallbackHandler(username, password);
            this.properties.setCallbackHandler(callbackHandler);
        }
        return new SecurePart(WSSConstants.TAG_WSSE_USERNAME_TOKEN, SecurePart.Modifier.Element);
    }

    protected SecurePart addKerberosToken(KerberosToken token, boolean signed, boolean endorsing, boolean encrypting) throws WSSecurityException {
        String text;
        this.assertToken(token);
        SPConstants.IncludeTokenType includeToken = token.getIncludeTokenType();
        if (!this.isTokenRequired(includeToken)) {
            return null;
        }
        final SecurityToken secToken = this.getSecurityToken();
        if (secToken == null) {
            this.unassertPolicy((Assertion)token, "Could not find KerberosToken");
        }
        byte[] data = null;
        if (secToken.getToken() != null && (text = org.apache.wss4j.common.util.XMLUtils.getElementText(secToken.getToken())) != null) {
            data = XMLUtils.decode(text);
        }
        final KerberosClientSecurityToken wss4jToken = new KerberosClientSecurityToken(data, secToken.getKey(), secToken.getId()){

            @Override
            public Key getSecretKey(String algorithmURI) throws XMLSecurityException {
                if (secToken.getSecret() != null && algorithmURI != null && !"".equals(algorithmURI)) {
                    return KeyUtils.prepareSecretKey(algorithmURI, secToken.getSecret());
                }
                return secToken.getKey();
            }
        };
        wss4jToken.setSha1Identifier(secToken.getSHA1());
        SecurityTokenProvider<OutboundSecurityToken> kerberosSecurityTokenProvider = new SecurityTokenProvider<OutboundSecurityToken>(){

            @Override
            public OutboundSecurityToken getSecurityToken() throws WSSecurityException {
                return wss4jToken;
            }

            @Override
            public String getId() {
                return wss4jToken.getId();
            }
        };
        this.outboundSecurityContext.registerSecurityTokenProvider(kerberosSecurityTokenProvider.getId(), kerberosSecurityTokenProvider);
        this.outboundSecurityContext.put("PROP_USE_THIS_TOKEN_ID_FOR_KERBEROS", kerberosSecurityTokenProvider.getId());
        if (encrypting) {
            this.outboundSecurityContext.put("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION", kerberosSecurityTokenProvider.getId());
        }
        if (endorsing) {
            this.outboundSecurityContext.put("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE", kerberosSecurityTokenProvider.getId());
        }
        this.properties.addAction(WSSConstants.KERBEROS_TOKEN);
        SecurePart securePart = new SecurePart(WSSConstants.TAG_WSSE_BINARY_SECURITY_TOKEN, SecurePart.Modifier.Element);
        securePart.setIdToSign(wss4jToken.getId());
        return securePart;
    }

    protected SecurePart addSamlToken(SamlToken token, boolean signed, boolean endorsing) throws WSSecurityException {
        this.assertToken(token);
        SPConstants.IncludeTokenType includeToken = token.getIncludeTokenType();
        if (!this.isTokenRequired(includeToken)) {
            return null;
        }
        Object o = SecurityUtils.getSecurityPropertyValue("security.saml-callback-handler", this.message);
        try {
            CallbackHandler handler = SecurityUtils.getCallbackHandler(o);
            if (handler == null) {
                this.unassertPolicy((Assertion)token, "No SAML CallbackHandler available");
                return null;
            }
            this.properties.setSamlCallbackHandler(handler);
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        XMLSecurityConstants.Action actionToPerform = WSSConstants.SAML_TOKEN_UNSIGNED;
        if (signed || endorsing) {
            actionToPerform = WSSConstants.SAML_TOKEN_SIGNED;
        }
        this.properties.addAction(actionToPerform);
        QName qname = WSSConstants.TAG_SAML2_ASSERTION;
        SamlToken.SamlTokenType tokenType = token.getSamlTokenType();
        if (tokenType == SamlToken.SamlTokenType.WssSamlV11Token10 || tokenType == SamlToken.SamlTokenType.WssSamlV11Token11) {
            qname = WSSConstants.TAG_SAML_ASSERTION;
        }
        return new SecurePart(qname, SecurePart.Modifier.Element);
    }

    protected SecurePart addIssuedToken(AbstractToken token, SecurityToken secToken, boolean signed, boolean endorsing) {
        this.assertToken(token);
        if (this.isTokenRequired(token.getIncludeTokenType())) {
            final Element el = secToken.getToken();
            if (el != null && "Assertion".equals(el.getLocalName()) && ("urn:oasis:names:tc:SAML:1.0:assertion".equals(el.getNamespaceURI()) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(el.getNamespaceURI()))) {
                SubjectBean subjectBean;
                XMLSecurityConstants.Action actionToPerform = WSSConstants.SAML_TOKEN_UNSIGNED;
                if (endorsing) {
                    actionToPerform = WSSConstants.SAML_TOKEN_SIGNED;
                }
                this.properties.addAction(actionToPerform);
                if (signed || endorsing) {
                    KeyInfoBean keyInfo = new KeyInfoBean();
                    keyInfo.setCertificate(secToken.getX509Certificate());
                    keyInfo.setEphemeralKey(secToken.getSecret());
                    subjectBean = new SubjectBean("", "", "");
                    subjectBean.setKeyInfo(keyInfo);
                } else {
                    subjectBean = null;
                }
                CallbackHandler callbackHandler = new CallbackHandler(){

                    @Override
                    public void handle(Callback[] callbacks) {
                        for (Callback callback : callbacks) {
                            if (!(callback instanceof SAMLCallback)) continue;
                            SAMLCallback samlCallback = (SAMLCallback)callback;
                            samlCallback.setAssertionElement(el);
                            samlCallback.setSubject(subjectBean);
                            if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(el.getNamespaceURI())) {
                                samlCallback.setSamlVersion(Version.SAML_11);
                                continue;
                            }
                            samlCallback.setSamlVersion(Version.SAML_20);
                        }
                    }
                };
                this.properties.setSamlCallbackHandler(callbackHandler);
                QName qname = WSSConstants.TAG_SAML2_ASSERTION;
                if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(el.getNamespaceURI())) {
                    qname = WSSConstants.TAG_SAML_ASSERTION;
                }
                return new SecurePart(qname, SecurePart.Modifier.Element);
            }
            if (this.isRequestor()) {
                this.properties.addAction(WSSConstants.CUSTOM_TOKEN);
            }
        }
        return null;
    }

    protected void storeSecurityToken(AbstractToken policyToken, SecurityToken tok) {
        SecurityTokenConstants.TokenType tokenType = WSSecurityTokenConstants.EncryptedKeyToken;
        if (tok.getTokenType() != null) {
            if (tok.getTokenType().startsWith("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#")) {
                tokenType = WSSecurityTokenConstants.KERBEROS_TOKEN;
            } else if (tok.getTokenType().startsWith("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0") || tok.getTokenType().startsWith("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1")) {
                tokenType = WSSecurityTokenConstants.SAML_11_TOKEN;
            } else if (tok.getTokenType().startsWith("http://schemas.xmlsoap.org/ws/2005/02/sc") || tok.getTokenType().startsWith("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512")) {
                tokenType = WSSecurityTokenConstants.SECURE_CONVERSATION_TOKEN;
            }
        }
        final Key key = tok.getKey();
        final byte[] secret = tok.getSecret();
        X509Certificate[] certs = new X509Certificate[1];
        if (tok.getX509Certificate() != null) {
            certs[0] = tok.getX509Certificate();
        }
        final GenericOutboundSecurityToken encryptedKeySecurityToken = new GenericOutboundSecurityToken(tok.getId(), tokenType, key, certs){

            @Override
            public Key getSecretKey(String algorithmURI) throws XMLSecurityException {
                if (secret != null && algorithmURI != null && !"".equals(algorithmURI)) {
                    return KeyUtils.prepareSecretKey(algorithmURI, secret);
                }
                if (key != null) {
                    return key;
                }
                if (secret != null) {
                    String jceAlg = JCEMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
                    if (jceAlg == null || "".equals(jceAlg)) {
                        jceAlg = "HmacSHA1";
                    }
                    return new SecretKeySpec(secret, jceAlg);
                }
                return super.getSecretKey(algorithmURI);
            }
        };
        Element ref = this.isTokenRequired(policyToken.getIncludeTokenType()) ? tok.getAttachedReference() : tok.getUnattachedReference();
        if (ref != null && policyToken instanceof IssuedToken) {
            encryptedKeySecurityToken.setCustomTokenReference(ref);
        }
        SecurityTokenProvider<OutboundSecurityToken> encryptedKeySecurityTokenProvider = new SecurityTokenProvider<OutboundSecurityToken>(){

            @Override
            public OutboundSecurityToken getSecurityToken() throws XMLSecurityException {
                return encryptedKeySecurityToken;
            }

            @Override
            public String getId() {
                return encryptedKeySecurityToken.getId();
            }
        };
        encryptedKeySecurityToken.setSha1Identifier(tok.getSHA1());
        this.outboundSecurityContext.registerSecurityTokenProvider(encryptedKeySecurityTokenProvider.getId(), encryptedKeySecurityTokenProvider);
        this.outboundSecurityContext.put("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION", encryptedKeySecurityTokenProvider.getId());
        this.outboundSecurityContext.put("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE", encryptedKeySecurityTokenProvider.getId());
        this.outboundSecurityContext.put("PROP_USE_THIS_TOKEN_ID_FOR_CUSTOM_TOKEN", encryptedKeySecurityTokenProvider.getId());
    }

    protected void configureTimestamp(AssertionInfoMap aim) {
        if (this.binding != null && this.binding.isIncludeTimestamp()) {
            this.timestampAdded = true;
            this.assertPolicy(new QName(this.binding.getName().getNamespaceURI(), "IncludeTimestamp"));
        }
    }

    protected void configureLayout(AssertionInfoMap aim) {
        AssertionInfo ai = PolicyUtils.getFirstAssertionByLocalname(aim, "Layout");
        Layout layout = null;
        if (ai != null) {
            layout = (Layout)ai.getAssertion();
            ai.setAsserted(true);
        }
        if (layout != null && layout.getLayoutType() != null) {
            this.assertPolicy(new QName(layout.getName().getNamespaceURI(), layout.getLayoutType().name()));
        }
        if (!this.timestampAdded) {
            return;
        }
        boolean timestampLast = layout != null && layout.getLayoutType() == Layout.LayoutType.LaxTsLast;
        XMLSecurityConstants.Action actionToPerform = WSSConstants.TIMESTAMP;
        List<XMLSecurityConstants.Action> actionList = this.properties.getActions();
        if (timestampLast) {
            actionList.add(0, actionToPerform);
        } else {
            actionList.add(actionToPerform);
        }
    }

    protected WSSSecurityProperties getProperties() {
        return this.properties;
    }

    protected void configureSignature(AbstractToken token, boolean attached) throws WSSecurityException {
        Object x509Token;
        X509Token.TokenType tokenType;
        if (token instanceof X509Token && ((tokenType = ((X509Token)(x509Token = (X509Token)token)).getTokenType()) == X509Token.TokenType.WssX509PkiPathV1Token10 || tokenType == X509Token.TokenType.WssX509PkiPathV1Token11)) {
            this.properties.setUseSingleCert(false);
        }
        this.properties.setSignatureKeyIdentifier(this.getKeyIdentifierType(token));
        this.properties.setIncludeSignatureToken(false);
        for (SecurityTokenConstants.KeyIdentifier keyIdentifier : this.properties.getSignatureKeyIdentifiers()) {
            if (!(token instanceof X509Token) || !this.isTokenRequired(token.getIncludeTokenType()) || !WSSecurityTokenConstants.KeyIdentifier_IssuerSerial.equals(keyIdentifier) && !WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER.equals(keyIdentifier) && !WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier)) continue;
            this.properties.setIncludeSignatureToken(true);
        }
        String userNameKey = "security.signature.username";
        if (this.binding instanceof SymmetricBinding) {
            userNameKey = "security.encryption.username";
            this.properties.setSignatureAlgorithm(this.binding.getAlgorithmSuite().getSymmetricSignature());
        } else {
            this.properties.setSignatureAlgorithm(this.binding.getAlgorithmSuite().getAsymmetricSignature());
        }
        this.properties.setSignatureCanonicalizationAlgorithm(this.binding.getAlgorithmSuite().getC14n().getValue());
        String sigUser = (String)SecurityUtils.getSecurityPropertyValue(userNameKey, this.message);
        if (sigUser == null) {
            sigUser = (String)SecurityUtils.getSecurityPropertyValue("security.username", this.message);
        }
        if (sigUser != null && this.properties.getSignatureUser() == null) {
            this.properties.setSignatureUser(sigUser);
        }
        AlgorithmSuite.AlgorithmSuiteType algType = this.binding.getAlgorithmSuite().getAlgorithmSuiteType();
        this.properties.setSignatureDigestAlgorithm(algType.getDigest());
        boolean includePrefixes = MessageUtils.getContextualBoolean(this.message, "ws-security.add.inclusive.prefixes", true);
        this.properties.setAddExcC14NInclusivePrefixes(includePrefixes);
    }

    protected SecurityTokenConstants.KeyIdentifier getKeyIdentifierType(AbstractToken token) {
        SecurityTokenConstants.KeyIdentifier identifier = null;
        if (token instanceof X509Token) {
            X509Token x509Token = (X509Token)token;
            if (x509Token.isRequireIssuerSerialReference()) {
                identifier = WSSecurityTokenConstants.KeyIdentifier_IssuerSerial;
            } else if (x509Token.isRequireKeyIdentifierReference()) {
                identifier = WSSecurityTokenConstants.KeyIdentifier_SkiKeyIdentifier;
            } else if (x509Token.isRequireThumbprintReference()) {
                identifier = WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER;
            }
        } else if (token instanceof KeyValueToken) {
            identifier = WSSecurityTokenConstants.KeyIdentifier_KeyValue;
        }
        if (identifier != null) {
            return identifier;
        }
        if (token.getIncludeTokenType() == SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER) {
            Wss10 wss = this.getWss10();
            if (wss == null || wss.isMustSupportRefKeyIdentifier()) {
                identifier = WSSecurityTokenConstants.KeyIdentifier_SkiKeyIdentifier;
            } else if (wss.isMustSupportRefIssuerSerial()) {
                identifier = WSSecurityTokenConstants.KeyIdentifier_IssuerSerial;
            } else if (wss instanceof Wss11 && ((Wss11)wss).isMustSupportRefThumbprint()) {
                identifier = WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER;
            }
        } else if (token.getIncludeTokenType() == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_RECIPIENT && !this.isRequestor() && token instanceof X509Token) {
            identifier = WSSecurityTokenConstants.KeyIdentifier_IssuerSerial;
        } else if (token.getIncludeTokenType() == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_INITIATOR && this.isRequestor() && token instanceof X509Token) {
            identifier = WSSecurityTokenConstants.KeyIdentifier_IssuerSerial;
        }
        if (identifier != null) {
            return identifier;
        }
        return WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE;
    }

    protected Map<AbstractToken, SecurePart> handleSupportingTokens(Collection<AssertionInfo> tokenAssertions, boolean signed, boolean endorse) throws Exception {
        if (tokenAssertions != null && !tokenAssertions.isEmpty()) {
            HashMap<AbstractToken, SecurePart> ret = new HashMap<AbstractToken, SecurePart>();
            for (AssertionInfo assertionInfo : tokenAssertions) {
                if (!(assertionInfo.getAssertion() instanceof SupportingTokens)) continue;
                assertionInfo.setAsserted(true);
                this.handleSupportingTokens((SupportingTokens)assertionInfo.getAssertion(), signed, endorse, ret);
            }
            return ret;
        }
        return Collections.emptyMap();
    }

    protected Map<AbstractToken, SecurePart> handleSupportingTokens(SupportingTokens suppTokens, boolean signed, boolean endorse) throws Exception {
        return this.handleSupportingTokens(suppTokens, signed, endorse, new HashMap<AbstractToken, SecurePart>());
    }

    protected Map<AbstractToken, SecurePart> handleSupportingTokens(SupportingTokens suppTokens, boolean signed, boolean endorse, Map<AbstractToken, SecurePart> ret) throws Exception {
        if (suppTokens == null) {
            return ret;
        }
        for (AbstractToken token : suppTokens.getTokens()) {
            SecurePart securePart;
            this.assertToken(token);
            if (!this.isTokenRequired(token.getIncludeTokenType())) continue;
            if (token instanceof UsernameToken) {
                this.handleUsernameTokenSupportingToken((UsernameToken)token, endorse, suppTokens.isEncryptedToken(), ret);
                continue;
            }
            if (token instanceof IssuedToken) {
                SecurityToken sigTok = this.getSecurityToken();
                SecurePart securePart2 = this.addIssuedToken(token, sigTok, signed, endorse);
                if (securePart2 == null) continue;
                ret.put(token, securePart2);
                if (!suppTokens.isEncryptedToken()) continue;
                this.encryptedTokensList.add(securePart2);
                continue;
            }
            if (token instanceof KerberosToken) {
                securePart = this.addKerberosToken((KerberosToken)token, signed, endorse, false);
                if (securePart == null) continue;
                ret.put(token, securePart);
                if (!suppTokens.isEncryptedToken()) continue;
                this.encryptedTokensList.add(securePart);
                continue;
            }
            if (token instanceof X509Token || token instanceof KeyValueToken) {
                this.assertToken(token);
                this.configureSignature(token, false);
                if (suppTokens.isEncryptedToken()) {
                    SecurePart part = new SecurePart(WSSConstants.TAG_WSSE_BINARY_SECURITY_TOKEN, SecurePart.Modifier.Element);
                    this.encryptedTokensList.add(part);
                }
                ret.put(token, new SecurePart(XMLSecurityConstants.TAG_dsig_Signature, SecurePart.Modifier.Element));
                continue;
            }
            if (!(token instanceof SamlToken) || (securePart = this.addSamlToken((SamlToken)token, signed, endorse)) == null) continue;
            ret.put(token, securePart);
            if (!suppTokens.isEncryptedToken()) continue;
            this.encryptedTokensList.add(securePart);
        }
        return ret;
    }

    protected void handleUsernameTokenSupportingToken(UsernameToken token, boolean endorse, boolean encryptedToken, Map<AbstractToken, SecurePart> ret) throws Exception {
        if (endorse) {
            throw new Exception("Endorsing UsernameTokens are not supported in the streaming code");
        }
        SecurePart securePart = this.addUsernameToken(token);
        if (securePart != null) {
            ret.put(token, securePart);
            if (encryptedToken || MessageUtils.getContextualBoolean(this.message, "ws-security.username-token.always.encrypted", true)) {
                this.encryptedTokensList.add(securePart);
            }
        }
    }

    protected void addSupportingTokens() throws Exception {
        Collection<AssertionInfo> encryptedSupportingToks;
        Collection<AssertionInfo> supportingToks;
        Collection<AssertionInfo> sgndEndEncSuppTokens;
        Collection<AssertionInfo> endorsingEncryptedSuppTokens;
        Collection<AssertionInfo> sgndEncryptedSuppTokens;
        Collection<AssertionInfo> sgndEndSuppTokens;
        Collection<AssertionInfo> endSuppTokens;
        Collection<AssertionInfo> sgndSuppTokens = this.getAllAssertionsByLocalname("SignedSupportingTokens");
        if (!sgndSuppTokens.isEmpty()) {
            Map<AbstractToken, SecurePart> sigSuppTokMap = this.handleSupportingTokens(sgndSuppTokens, true, false);
            this.addSignatureParts(sigSuppTokMap);
        }
        if (!(endSuppTokens = this.getAllAssertionsByLocalname("EndorsingSupportingTokens")).isEmpty()) {
            this.endSuppTokMap = this.handleSupportingTokens(endSuppTokens, false, true);
        }
        if (!(sgndEndSuppTokens = this.getAllAssertionsByLocalname("SignedEndorsingSupportingTokens")).isEmpty()) {
            this.sgndEndSuppTokMap = this.handleSupportingTokens(sgndEndSuppTokens, true, true);
            this.addSignatureParts(this.sgndEndSuppTokMap);
        }
        if (!(sgndEncryptedSuppTokens = this.getAllAssertionsByLocalname("SignedEncryptedSupportingTokens")).isEmpty()) {
            Map<AbstractToken, SecurePart> sgndEncSuppTokMap = this.handleSupportingTokens(sgndEncryptedSuppTokens, true, false);
            this.addSignatureParts(sgndEncSuppTokMap);
        }
        if (!(endorsingEncryptedSuppTokens = this.getAllAssertionsByLocalname("EndorsingEncryptedSupportingTokens")).isEmpty()) {
            this.endEncSuppTokMap = this.handleSupportingTokens(endorsingEncryptedSuppTokens, false, true);
        }
        if (!(sgndEndEncSuppTokens = this.getAllAssertionsByLocalname("SignedEndorsingEncryptedSupportingTokens")).isEmpty()) {
            this.sgndEndEncSuppTokMap = this.handleSupportingTokens(sgndEndEncSuppTokens, true, true);
            this.addSignatureParts(this.sgndEndEncSuppTokMap);
        }
        if (!(supportingToks = this.getAllAssertionsByLocalname("SupportingTokens")).isEmpty()) {
            this.handleSupportingTokens(supportingToks, false, false);
        }
        if (!(encryptedSupportingToks = this.getAllAssertionsByLocalname("EncryptedSupportingTokens")).isEmpty()) {
            this.handleSupportingTokens(encryptedSupportingToks, false, false);
        }
    }

    protected void addSignatureParts(Map<AbstractToken, SecurePart> tokenMap) {
        if (tokenMap != null) {
            for (Map.Entry<AbstractToken, SecurePart> entry : tokenMap.entrySet()) {
                SecurePart part = entry.getValue();
                QName name = part.getName();
                List<XMLSecurityConstants.Action> actionList = this.properties.getActions();
                if ((WSSConstants.TAG_SAML_ASSERTION.equals(name) || WSSConstants.TAG_SAML2_ASSERTION.equals(name)) && actionList != null && actionList.contains(WSSConstants.SAML_TOKEN_SIGNED)) continue;
                this.properties.addSignaturePart(part);
            }
        }
    }

    protected void addSignatureConfirmation(List<SecurePart> sigParts) {
        Wss10 wss10 = this.getWss10();
        if (!(wss10 instanceof Wss11) || !((Wss11)wss10).isRequireSignatureConfirmation()) {
            return;
        }
        if (this.isRequestor()) {
            this.properties.setEnableSignatureConfirmationVerification(true);
        } else {
            this.properties.getActions().add(WSSConstants.SIGNATURE_CONFIRMATION);
        }
        if (sigParts != null) {
            SecurePart securePart = new SecurePart(WSSConstants.TAG_WSSE11_SIG_CONF, SecurePart.Modifier.Element);
            sigParts.add(securePart);
        }
        this.signatureConfirmationAdded = true;
    }

    protected List<SecurePart> getSignedParts() throws SOAPException {
        SignedParts parts = null;
        SignedElements elements = null;
        AssertionInfoMap aim = this.message.get(AssertionInfoMap.class);
        AssertionInfo assertionInfo = PolicyUtils.getFirstAssertionByLocalname(aim, "SignedParts");
        if (assertionInfo != null) {
            parts = (SignedParts)assertionInfo.getAssertion();
            assertionInfo.setAsserted(true);
        }
        if ((assertionInfo = PolicyUtils.getFirstAssertionByLocalname(aim, "SignedElements")) != null) {
            elements = (SignedElements)assertionInfo.getAssertion();
            assertionInfo.setAsserted(true);
        }
        ArrayList<SecurePart> signedParts = new ArrayList<SecurePart>();
        if (parts != null) {
            if (parts.isBody()) {
                QName soapBody = new QName("http://www.w3.org/2003/05/soap-envelope", "Body");
                SecurePart securePart = new SecurePart(soapBody, SecurePart.Modifier.Element);
                signedParts.add(securePart);
            }
            for (Header head : parts.getHeaders()) {
                String localName = head.getName();
                if (localName == null) {
                    localName = "*";
                }
                QName qname = new QName(head.getNamespace(), localName);
                SecurePart securePart = new SecurePart(qname, SecurePart.Modifier.Element);
                securePart.setRequired(false);
                signedParts.add(securePart);
            }
            Attachments attachments = parts.getAttachments();
            if (attachments != null) {
                SecurePart.Modifier modifier = SecurePart.Modifier.Element;
                if (attachments.isContentSignatureTransform()) {
                    modifier = SecurePart.Modifier.Content;
                }
                SecurePart securePart = new SecurePart("cid:Attachments", modifier);
                securePart.setRequired(false);
                signedParts.add(securePart);
            }
        }
        if (elements != null && elements.getXPaths() != null) {
            for (XPath xPath : elements.getXPaths()) {
                List<QName> qnames = org.apache.wss4j.policy.stax.PolicyUtils.getElementPath(xPath);
                if (qnames.isEmpty()) continue;
                SecurePart securePart = new SecurePart(qnames.get(qnames.size() - 1), SecurePart.Modifier.Element);
                signedParts.add(securePart);
            }
        }
        return signedParts;
    }

    protected List<SecurePart> getEncryptedParts() throws SOAPException {
        SecurePart securePart;
        List<QName> qnames;
        EncryptedParts parts = null;
        EncryptedElements elements = null;
        RequiredElements celements = null;
        AssertionInfoMap aim = this.message.get(AssertionInfoMap.class);
        Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "EncryptedParts");
        if (!ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                parts = (EncryptedParts)ai.getAssertion();
                ai.setAsserted(true);
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "EncryptedElements")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                elements = (EncryptedElements)ai.getAssertion();
                ai.setAsserted(true);
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "ContentEncryptedElements")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                celements = (ContentEncryptedElements)ai.getAssertion();
                ai.setAsserted(true);
            }
        }
        ArrayList<SecurePart> encryptedParts = new ArrayList<SecurePart>();
        if (parts != null) {
            SecurePart securePart2;
            if (parts.isBody()) {
                QName soapBody = new QName("http://www.w3.org/2003/05/soap-envelope", "Body");
                securePart2 = new SecurePart(soapBody, SecurePart.Modifier.Content);
                encryptedParts.add(securePart2);
            }
            for (Header head : parts.getHeaders()) {
                String localName = head.getName();
                if (localName == null) {
                    localName = "*";
                }
                QName qname = new QName(head.getNamespace(), localName);
                SecurePart securePart3 = new SecurePart(qname, SecurePart.Modifier.Element);
                securePart3.setRequired(false);
                encryptedParts.add(securePart3);
            }
            Attachments attachments = parts.getAttachments();
            if (attachments != null) {
                securePart2 = new SecurePart("cid:Attachments", SecurePart.Modifier.Element);
                if (MessageUtils.getContextualBoolean(this.message, "ws-security.swa.encryption.attachment.transform.content", false)) {
                    securePart2.setModifier(SecurePart.Modifier.Content);
                }
                securePart2.setRequired(false);
                encryptedParts.add(securePart2);
            }
        }
        if (elements != null && elements.getXPaths() != null) {
            for (XPath xPath : elements.getXPaths()) {
                qnames = org.apache.wss4j.policy.stax.PolicyUtils.getElementPath(xPath);
                if (qnames.isEmpty()) continue;
                securePart = new SecurePart(qnames.get(qnames.size() - 1), SecurePart.Modifier.Element);
                encryptedParts.add(securePart);
            }
        }
        if (celements != null && celements.getXPaths() != null) {
            for (XPath xPath : celements.getXPaths()) {
                qnames = org.apache.wss4j.policy.stax.PolicyUtils.getElementPath(xPath);
                if (qnames.isEmpty()) continue;
                securePart = new SecurePart(qnames.get(qnames.size() - 1), SecurePart.Modifier.Content);
                encryptedParts.add(securePart);
            }
        }
        return encryptedParts;
    }

    protected org.apache.xml.security.stax.securityToken.SecurityToken findInboundSecurityToken(SecurityEventConstants.Event event) throws XMLSecurityException {
        List incomingEventList = (List)this.message.getExchange().get(SecurityEvent.class.getName() + ".in");
        if (incomingEventList != null) {
            for (SecurityEvent incomingEvent : incomingEventList) {
                if (event != incomingEvent.getSecurityEventType()) continue;
                return ((TokenSecurityEvent)incomingEvent).getSecurityToken();
            }
        }
        return null;
    }

    protected void removeSignatureIfSignedSAML() {
        List<XMLSecurityConstants.Action> actionList;
        if (this.properties.getActions() != null && (actionList = this.properties.getActions()).contains(WSSConstants.SAML_TOKEN_SIGNED) && actionList.contains(XMLSecurityConstants.SIGNATURE)) {
            actionList.remove(XMLSecurityConstants.SIGNATURE);
        }
    }

    protected void prependSignatureToSC() {
        if (this.properties.getActions() != null) {
            List<XMLSecurityConstants.Action> actionList = this.properties.getActions();
            boolean sigConf = actionList.contains(WSSConstants.SIGNATURE_CONFIRMATION);
            if (sigConf && actionList.contains(XMLSecurityConstants.SIGNATURE)) {
                actionList.remove(WSSConstants.SIGNATURE_CONFIRMATION);
                actionList.add(actionList.indexOf(XMLSecurityConstants.SIGNATURE) + 1, WSSConstants.SIGNATURE_CONFIRMATION);
            } else if (sigConf && actionList.contains(WSSConstants.SIGNATURE_WITH_DERIVED_KEY)) {
                actionList.remove(WSSConstants.SIGNATURE_CONFIRMATION);
                actionList.add(actionList.indexOf(WSSConstants.SIGNATURE_WITH_DERIVED_KEY) + 1, WSSConstants.SIGNATURE_CONFIRMATION);
            }
        }
    }

    protected void enforceEncryptBeforeSigningWithSignedSAML() {
        List<XMLSecurityConstants.Action> actionList;
        if (this.properties.getActions() != null && (actionList = this.properties.getActions()).contains(WSSConstants.SAML_TOKEN_SIGNED)) {
            actionList.remove(WSSConstants.SAML_TOKEN_SIGNED);
            actionList.add(WSSConstants.SAML_TOKEN_SIGNED);
        }
    }

    protected void putCustomTokenAfterSignature() {
        List<XMLSecurityConstants.Action> actionList;
        if (this.properties.getActions() != null && ((actionList = this.properties.getActions()).contains(XMLSecurityConstants.SIGNATURE) || actionList.contains(WSSConstants.SIGNATURE_WITH_DERIVED_KEY) || actionList.contains(WSSConstants.SIGNATURE_WITH_KERBEROS_TOKEN)) && actionList.contains(WSSConstants.CUSTOM_TOKEN)) {
            this.getProperties().getActions().remove(WSSConstants.CUSTOM_TOKEN);
            this.getProperties().getActions().add(WSSConstants.CUSTOM_TOKEN);
        }
    }

    private static class UTCallbackHandler
    implements CallbackHandler {
        private final String username;
        private final String password;

        UTCallbackHandler(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (Callback callback : callbacks) {
                WSPasswordCallback pwcb;
                if (!(callback instanceof WSPasswordCallback) || !(pwcb = (WSPasswordCallback)callback).getIdentifier().equals(this.username)) continue;
                pwcb.setPassword(this.password);
            }
        }
    }
}

