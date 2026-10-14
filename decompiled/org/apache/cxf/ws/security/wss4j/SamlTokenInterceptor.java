/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.net.URL;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.security.DefaultSecurityContext;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.AbstractTokenInterceptor;
import org.apache.cxf.ws.security.wss4j.CXFRequestData;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.saml.bean.Version;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.processor.SAMLTokenProcessor;
import org.apache.wss4j.dom.saml.DOMSAMLUtil;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.SamlToken;
import org.opensaml.saml.common.SAMLVersion;
import org.w3c.dom.Element;

public class SamlTokenInterceptor
extends AbstractTokenInterceptor {
    @Override
    protected void processToken(SoapMessage message) {
        Header h2 = this.findSecurityHeader(message, false);
        if (h2 == null) {
            return;
        }
        Element el = (Element)h2.getObject();
        Element child = DOMUtils.getFirstElement(el);
        while (child != null) {
            if ("Assertion".equals(child.getLocalName()) && ("urn:oasis:names:tc:SAML:1.0:assertion".equals(child.getNamespaceURI()) || "urn:oasis:names:tc:SAML:2.0:assertion".equals(child.getNamespaceURI()))) {
                try {
                    List<WSSecurityEngineResult> samlResults = this.processToken(child, message);
                    if (samlResults != null) {
                        List results = CastUtils.cast((List)message.get("RECV_RESULTS"));
                        if (results == null) {
                            results = new ArrayList();
                            message.put("RECV_RESULTS", (Object)results);
                        }
                        boolean signed = false;
                        for (WSSecurityEngineResult result : samlResults) {
                            SamlAssertionWrapper wrapper = (SamlAssertionWrapper)result.get("saml-assertion");
                            if (!wrapper.isSigned()) continue;
                            signed = true;
                            break;
                        }
                        this.assertTokens(message, "SamlToken", signed);
                        Integer key = 8;
                        if (signed) {
                            key = 16;
                        }
                        WSHandlerResult rResult = new WSHandlerResult(null, samlResults, Collections.singletonMap(key, samlResults));
                        results.add(0, rResult);
                        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
                        for (AssertionInfo ai : PolicyUtils.getAllAssertionsByLocalname(aim, "SamlToken")) {
                            SamlToken samlToken = (SamlToken)ai.getAssertion();
                            for (WSSecurityEngineResult result : samlResults) {
                                SamlAssertionWrapper assertionWrapper = (SamlAssertionWrapper)result.get("saml-assertion");
                                if (!this.checkVersion(aim, samlToken, assertionWrapper)) {
                                    ai.setNotAsserted("Wrong SAML Version");
                                }
                                TLSSessionInfo tlsInfo = message.get(TLSSessionInfo.class);
                                Certificate[] tlsCerts = null;
                                if (tlsInfo != null) {
                                    tlsCerts = tlsInfo.getPeerCertificates();
                                }
                                if (!DOMSAMLUtil.checkHolderOfKey(assertionWrapper, null, tlsCerts)) {
                                    ai.setNotAsserted("Assertion fails holder-of-key requirements");
                                    continue;
                                }
                                if (DOMSAMLUtil.checkSenderVouches(assertionWrapper, tlsCerts, null, null)) continue;
                                ai.setNotAsserted("Assertion fails sender-vouches requirements");
                            }
                        }
                        if (signed) {
                            Principal principal = (Principal)samlResults.get(0).get("principal");
                            SecurityContext sc = message.get(SecurityContext.class);
                            if (sc == null || sc.getUserPrincipal() == null) {
                                message.put(SecurityContext.class, new DefaultSecurityContext(principal, null));
                            }
                        }
                    }
                }
                catch (WSSecurityException ex) {
                    throw WSS4JUtils.createSoapFault(message, message.getVersion(), ex);
                }
            }
            child = DOMUtils.getNextElement(child);
        }
    }

    private List<WSSecurityEngineResult> processToken(Element tokenElement, SoapMessage message) throws WSSecurityException {
        CXFRequestData data = new CXFRequestData();
        Object o = SecurityUtils.getSecurityPropertyValue("security.callback-handler", message);
        try {
            data.setCallbackHandler(SecurityUtils.getCallbackHandler(o));
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        data.setMsgContext(message);
        data.setWssConfig(WSSConfig.getNewInstance());
        data.setSigVerCrypto(this.getCrypto("security.signature.crypto", "security.signature.properties", message));
        WSDocInfo wsDocInfo = new WSDocInfo(tokenElement.getOwnerDocument());
        data.setWsDocInfo(wsDocInfo);
        SAMLTokenProcessor p = new SAMLTokenProcessor();
        return p.handleToken(tokenElement, data);
    }

    @Override
    protected AbstractToken assertTokens(SoapMessage message) {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        PolicyUtils.assertPolicy(aim, "WssSamlV11Token10");
        PolicyUtils.assertPolicy(aim, "WssSamlV11Token11");
        PolicyUtils.assertPolicy(aim, "WssSamlV20Token11");
        return this.assertTokens(message, "SamlToken", true);
    }

    @Override
    protected void addToken(SoapMessage message) {
        WSSConfig.init();
        SamlToken tok = (SamlToken)this.assertTokens(message);
        Header h2 = this.findSecurityHeader(message, true);
        try {
            SamlAssertionWrapper wrapper = this.addSamlToken(tok, message);
            if (wrapper == null) {
                AssertionInfoMap aim = message.get(AssertionInfoMap.class);
                Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SamlToken");
                for (AssertionInfo ai : ais) {
                    if (!ai.isAsserted()) continue;
                    ai.setAsserted(false);
                }
                return;
            }
            Element el = (Element)h2.getObject();
            el = (Element)DOMUtils.getDomElement(el);
            el.appendChild(wrapper.toDOM(el.getOwnerDocument()));
        }
        catch (WSSecurityException ex) {
            this.policyNotAsserted((AbstractToken)tok, ex.getMessage(), message);
        }
    }

    private SamlAssertionWrapper addSamlToken(SamlToken token, SoapMessage message) throws WSSecurityException {
        Object o = SecurityUtils.getSecurityPropertyValue("security.saml-callback-handler", message);
        CallbackHandler handler = null;
        if (o instanceof CallbackHandler) {
            handler = (CallbackHandler)o;
        } else if (o instanceof String) {
            try {
                handler = (CallbackHandler)ClassLoaderUtils.loadClass((String)o, this.getClass()).newInstance();
            }
            catch (Exception e) {
                handler = null;
            }
        }
        if (handler == null) {
            return null;
        }
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        SAMLCallback samlCallback = new SAMLCallback();
        SamlToken.SamlTokenType tokenType = token.getSamlTokenType();
        if (tokenType == SamlToken.SamlTokenType.WssSamlV11Token10 || tokenType == SamlToken.SamlTokenType.WssSamlV11Token11) {
            samlCallback.setSamlVersion(Version.SAML_11);
            PolicyUtils.assertPolicy(aim, "WssSamlV11Token10");
            PolicyUtils.assertPolicy(aim, "WssSamlV11Token11");
        } else if (tokenType == SamlToken.SamlTokenType.WssSamlV20Token11) {
            samlCallback.setSamlVersion(Version.SAML_20);
            PolicyUtils.assertPolicy(aim, "WssSamlV20Token11");
        }
        SAMLUtil.doSAMLCallback(handler, samlCallback);
        SamlAssertionWrapper assertion = new SamlAssertionWrapper(samlCallback);
        if (samlCallback.isSignAssertion()) {
            Crypto crypto;
            String password;
            String issuerName = samlCallback.getIssuerKeyName();
            if (issuerName == null) {
                String userNameKey = "security.signature.username";
                issuerName = (String)SecurityUtils.getSecurityPropertyValue(userNameKey, message);
            }
            if ((password = samlCallback.getIssuerKeyPassword()) == null) {
                password = (String)SecurityUtils.getSecurityPropertyValue("security.password", message);
                if (StringUtils.isEmpty(password)) {
                    password = (String)SecurityUtils.getSecurityPropertyValue("security.signature.password", message);
                }
                if (StringUtils.isEmpty(password)) {
                    password = this.getPassword(issuerName, token, 3, message);
                }
            }
            if ((crypto = samlCallback.getIssuerCrypto()) == null) {
                crypto = this.getCrypto("security.signature.crypto", "security.signature.properties", message);
            }
            assertion.signAssertion(issuerName, password, crypto, samlCallback.isSendKeyValue(), samlCallback.getCanonicalizationAlgorithm(), samlCallback.getSignatureAlgorithm());
        }
        return assertion;
    }

    private Crypto getCrypto(String cryptoKey, String propKey, SoapMessage message) throws WSSecurityException {
        Crypto crypto = (Crypto)SecurityUtils.getSecurityPropertyValue(cryptoKey, message);
        if (crypto != null) {
            return crypto;
        }
        Object o = SecurityUtils.getSecurityPropertyValue(propKey, message);
        if (o == null) {
            return null;
        }
        URL propsURL = SecurityUtils.loadResource(message, o);
        Properties properties = WSS4JUtils.getProps(o, propsURL);
        if (properties != null) {
            PasswordEncryptor passwordEncryptor = WSS4JUtils.getPasswordEncryptor(message);
            crypto = CryptoFactory.getInstance(properties, this.getClass().getClassLoader(), passwordEncryptor);
        }
        return crypto;
    }

    private boolean checkVersion(AssertionInfoMap aim, SamlToken samlToken, SamlAssertionWrapper assertionWrapper) {
        SamlToken.SamlTokenType tokenType = samlToken.getSamlTokenType();
        if ((tokenType == SamlToken.SamlTokenType.WssSamlV11Token10 || tokenType == SamlToken.SamlTokenType.WssSamlV11Token11) && assertionWrapper.getSamlVersion() != SAMLVersion.VERSION_11) {
            return false;
        }
        if (tokenType == SamlToken.SamlTokenType.WssSamlV20Token11 && assertionWrapper.getSamlVersion() != SAMLVersion.VERSION_20) {
            return false;
        }
        PolicyUtils.assertPolicy(aim, new QName(samlToken.getVersion().getNamespace(), tokenType.name()));
        return true;
    }
}

