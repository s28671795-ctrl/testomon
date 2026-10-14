/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.security.auth.Subject;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.DefaultSecurityContext;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.rt.security.saml.claims.SAMLSecurityContext;
import org.apache.cxf.rt.security.saml.utils.SAMLUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.AbstractTokenInterceptor;
import org.apache.cxf.ws.security.wss4j.CXFRequestData;
import org.apache.cxf.ws.security.wss4j.WSS4JTokenConverter;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.SAMLTokenPrincipalImpl;
import org.apache.wss4j.common.principal.UsernameTokenPrincipal;
import org.apache.wss4j.common.principal.WSUsernameTokenPrincipalImpl;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.message.WSSecUsernameToken;
import org.apache.wss4j.dom.processor.UsernameTokenProcessor;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UsernameTokenInterceptor
extends AbstractTokenInterceptor {
    @Override
    protected void processToken(SoapMessage message) {
        Header h2 = this.findSecurityHeader(message, false);
        if (h2 == null) {
            return;
        }
        boolean utWithCallbacks = MessageUtils.getContextualBoolean(message, "ws-security.validate.token", true);
        Element el = (Element)h2.getObject();
        Element child = DOMUtils.getFirstElement(el);
        while (child != null) {
            if ("UsernameToken".equals(child.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(child.getNamespaceURI())) {
                try {
                    boolean bspCompliant = this.isWsiBSPCompliant(message);
                    boolean allowNSPasswdTypes = this.allowNamespaceQualifiedPWDTypes(message);
                    Principal principal = null;
                    Subject subject = null;
                    Object transformedToken = null;
                    if (utWithCallbacks) {
                        WSSecurityEngineResult result = this.validateToken(child, message);
                        subject = (Subject)result.get("subject");
                        transformedToken = result.get("transformed-token");
                        principal = (Principal)result.get("principal");
                        if (principal == null) {
                            principal = this.parseTokenAndCreatePrincipal(child, bspCompliant, allowNSPasswdTypes);
                        }
                    } else {
                        principal = this.parseTokenAndCreatePrincipal(child, bspCompliant, allowNSPasswdTypes);
                        WSS4JTokenConverter.convertToken(message, principal);
                    }
                    SecurityContext sc = message.get(SecurityContext.class);
                    if (sc == null || sc.getUserPrincipal() == null) {
                        if (transformedToken instanceof SamlAssertionWrapper) {
                            message.put(SecurityContext.class, this.createSecurityContext(message, transformedToken));
                        } else if (subject != null && principal != null) {
                            message.put(SecurityContext.class, this.createSecurityContext(principal, subject));
                        } else {
                            Principal utPrincipal = principal;
                            String nonce = null;
                            if (utPrincipal.getNonce() != null) {
                                nonce = XMLUtils.encodeToString(utPrincipal.getNonce());
                            }
                            subject = this.createSubject(utPrincipal.getName(), utPrincipal.getPassword(), utPrincipal.isPasswordDigest(), nonce, utPrincipal.getCreatedTime());
                            message.put(SecurityContext.class, this.createSecurityContext(utPrincipal, subject));
                        }
                    }
                    if (principal instanceof UsernameTokenPrincipal) {
                        this.storeResults((UsernameTokenPrincipal)principal, subject, message);
                    }
                }
                catch (WSSecurityException | Base64DecodingException ex) {
                    throw new Fault(ex);
                }
            }
            child = DOMUtils.getNextElement(child);
        }
    }

    private SecurityContext createSecurityContext(Message msg, SamlAssertionWrapper samlAssertion) {
        String roleAttributeName = (String)SecurityUtils.getSecurityPropertyValue("security.saml-role-attributename", msg);
        if (roleAttributeName == null || roleAttributeName.length() == 0) {
            roleAttributeName = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role";
        }
        ClaimCollection claims = SAMLUtils.getClaims(samlAssertion);
        Set<Principal> roles = SAMLUtils.parseRolesFromClaims(claims, roleAttributeName, null);
        SAMLSecurityContext context = new SAMLSecurityContext(new SAMLTokenPrincipalImpl(samlAssertion), roles, claims);
        context.setIssuer(SAMLUtils.getIssuer(samlAssertion));
        context.setAssertionElement(SAMLUtils.getAssertionElement(samlAssertion));
        return context;
    }

    private void storeResults(UsernameTokenPrincipal principal, Subject subject, SoapMessage message) {
        ArrayList<WSSecurityEngineResult> v = new ArrayList<WSSecurityEngineResult>();
        int action = 1;
        if (principal.getPassword() == null) {
            action = 8192;
        }
        WSSecurityEngineResult result = new WSSecurityEngineResult(action, principal, null, null, null);
        if (subject != null) {
            result.put("subject", subject);
        }
        v.add(0, result);
        List results = CastUtils.cast((List)message.get("RECV_RESULTS"));
        if (results == null) {
            results = new ArrayList();
            message.put("RECV_RESULTS", (Object)results);
        }
        WSHandlerResult rResult = new WSHandlerResult(null, v, Collections.singletonMap(action, v));
        results.add(0, rResult);
        this.assertTokens(message, principal, false);
    }

    protected WSSecurityEngineResult validateToken(Element tokenElement, SoapMessage message) throws WSSecurityException, Base64DecodingException {
        boolean bspCompliant = this.isWsiBSPCompliant(message);
        boolean allowNoPassword = this.isAllowNoPassword(message.get(AssertionInfoMap.class));
        UsernameTokenProcessor p = new UsernameTokenProcessor();
        CXFRequestData data = new CXFRequestData();
        Object o = SecurityUtils.getSecurityPropertyValue("security.callback-handler", message);
        try {
            data.setCallbackHandler(SecurityUtils.getCallbackHandler(o));
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        data.setMsgContext(message);
        ReplayCache nonceCache = WSS4JUtils.getReplayCache(message, "ws-security.enable.nonce.cache", "ws-security.nonce.cache.instance");
        data.setNonceReplayCache(nonceCache);
        data.setAllowUsernameTokenNoPassword(allowNoPassword);
        data.setWssConfig(WSSConfig.getNewInstance());
        if (!bspCompliant) {
            data.setDisableBSPEnforcement(true);
        }
        data.setMsgContext(message);
        WSDocInfo wsDocInfo = new WSDocInfo(tokenElement.getOwnerDocument());
        data.setWsDocInfo(wsDocInfo);
        try {
            List<WSSecurityEngineResult> results = p.handleToken(tokenElement, data);
            return results.get(0);
        }
        catch (WSSecurityException ex) {
            throw WSS4JUtils.createSoapFault(message, message.getVersion(), ex);
        }
    }

    protected UsernameTokenPrincipal parseTokenAndCreatePrincipal(Element tokenElement, boolean bspCompliant, boolean allowNamespaceQualifiedPWDTypes) throws WSSecurityException, Base64DecodingException {
        BSPEnforcer bspEnforcer = new BSPEnforcer(!bspCompliant);
        org.apache.wss4j.dom.message.token.UsernameToken ut = new org.apache.wss4j.dom.message.token.UsernameToken(tokenElement, allowNamespaceQualifiedPWDTypes, bspEnforcer);
        WSUsernameTokenPrincipalImpl principal = new WSUsernameTokenPrincipalImpl(ut.getName(), ut.isHashed());
        if (ut.getNonce() != null) {
            principal.setNonce(XMLUtils.decode(ut.getNonce()));
        }
        principal.setPassword(ut.getPassword());
        principal.setCreatedTime(ut.getCreated());
        principal.setPasswordType(ut.getPasswordType());
        return principal;
    }

    protected boolean isWsiBSPCompliant(SoapMessage message) {
        String bspc = (String)message.getContextualProperty("ws-security.is-bsp-compliant");
        return !"false".equals(bspc) && !"0".equals(bspc);
    }

    private boolean allowNamespaceQualifiedPWDTypes(SoapMessage message) {
        String allow = (String)message.getContextualProperty("allowNamespaceQualifiedPasswordTypes");
        return "true".equals(allow) || "1".equals(allow);
    }

    private boolean isAllowNoPassword(AssertionInfoMap aim) throws WSSecurityException {
        Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "UsernameToken");
        if (!ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                UsernameToken policy = (UsernameToken)ai.getAssertion();
                if (policy.getPasswordType() != UsernameToken.PasswordType.NoPassword) continue;
                return true;
            }
        }
        return false;
    }

    protected SecurityContext createSecurityContext(Principal p, Subject subject) {
        return new DefaultSecurityContext(p, subject);
    }

    protected Subject createSubject(String name, String password, boolean isDigest, String nonce, String created) throws SecurityException {
        return null;
    }

    @Override
    protected UsernameToken assertTokens(SoapMessage message) {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        PolicyUtils.assertPolicy(aim, "WssUsernameToken10");
        PolicyUtils.assertPolicy(aim, "WssUsernameToken11");
        PolicyUtils.assertPolicy(aim, "HashPassword");
        PolicyUtils.assertPolicy(aim, "NoPassword");
        PolicyUtils.assertPolicy(aim, SP13Constants.NONCE);
        PolicyUtils.assertPolicy(aim, SP13Constants.CREATED);
        return (UsernameToken)this.assertTokens(message, "UsernameToken", true);
    }

    private UsernameToken assertTokens(SoapMessage message, UsernameTokenPrincipal princ, boolean signed) {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "UsernameToken");
        UsernameToken tok = null;
        for (AssertionInfo ai : ais) {
            tok = (UsernameToken)ai.getAssertion();
            ai.setAsserted(true);
            if (!(tok.getPasswordType() != UsernameToken.PasswordType.HashPassword || princ != null && princ.isPasswordDigest())) {
                ai.setNotAsserted("Password hashing policy not enforced");
            } else {
                PolicyUtils.assertPolicy(aim, "HashPassword");
            }
            if (tok.getPasswordType() != UsernameToken.PasswordType.NoPassword && this.isNonEndorsingSupportingToken(tok) && (princ == null || princ.getPassword() == null)) {
                ai.setNotAsserted("Username Token No Password supplied");
            } else {
                PolicyUtils.assertPolicy(aim, "NoPassword");
            }
            if (tok.isCreated() && (princ == null || princ.getCreatedTime() == null)) {
                ai.setNotAsserted("No Created Time");
            } else {
                PolicyUtils.assertPolicy(aim, SP13Constants.CREATED);
            }
            if (tok.isNonce() && princ.getNonce() == null) {
                ai.setNotAsserted("No Nonce");
                continue;
            }
            PolicyUtils.assertPolicy(aim, SP13Constants.NONCE);
        }
        PolicyUtils.assertPolicy(aim, "WssUsernameToken10");
        PolicyUtils.assertPolicy(aim, "WssUsernameToken11");
        PolicyUtils.assertPolicy(aim, "SupportingTokens");
        if (signed || this.isTLSInUse(message)) {
            PolicyUtils.assertPolicy(aim, "SignedSupportingTokens");
        }
        return tok;
    }

    private boolean isNonEndorsingSupportingToken(UsernameToken usernameTokenPolicy) {
        AbstractSecurityAssertion supportingToken = usernameTokenPolicy.getParentAssertion();
        return !(supportingToken instanceof SupportingTokens) || !((SupportingTokens)supportingToken).isEndorsing();
    }

    @Override
    protected void addToken(SoapMessage message) {
        UsernameToken tok = this.assertTokens(message);
        Header h2 = this.findSecurityHeader(message, true);
        Element el = (Element)h2.getObject();
        Document doc = el.getOwnerDocument();
        WSSecUsernameToken utBuilder = this.addUsernameToken(message, doc, tok);
        if (utBuilder == null) {
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "UsernameToken");
            for (AssertionInfo ai : ais) {
                if (!ai.isAsserted()) continue;
                ai.setAsserted(false);
            }
            return;
        }
        utBuilder.prepare();
        el.appendChild(utBuilder.getUsernameTokenElement());
    }

    protected WSSecUsernameToken addUsernameToken(SoapMessage message, Document doc, UsernameToken token) {
        String userName = (String)SecurityUtils.getSecurityPropertyValue("security.username", message);
        WSSConfig wssConfig = (WSSConfig)message.getContextualProperty(WSSConfig.class.getName());
        if (wssConfig == null) {
            wssConfig = WSSConfig.getNewInstance();
        }
        if (!StringUtils.isEmpty(userName)) {
            if (token.getPasswordType() == UsernameToken.PasswordType.NoPassword) {
                WSSecUsernameToken utBuilder = new WSSecUsernameToken(doc);
                utBuilder.setIdAllocator(wssConfig.getIdAllocator());
                utBuilder.setWsTimeSource(wssConfig.getCurrentTime());
                utBuilder.setUserInfo(userName, null);
                utBuilder.setPasswordType(null);
                return utBuilder;
            }
            String password = (String)SecurityUtils.getSecurityPropertyValue("security.password", message);
            if (StringUtils.isEmpty(password)) {
                password = this.getPassword(userName, token, 2, message);
            }
            if (!StringUtils.isEmpty(password)) {
                WSSecUsernameToken utBuilder = new WSSecUsernameToken(doc);
                utBuilder.setIdAllocator(wssConfig.getIdAllocator());
                utBuilder.setWsTimeSource(wssConfig.getCurrentTime());
                if (token.getPasswordType() == UsernameToken.PasswordType.HashPassword) {
                    utBuilder.setPasswordType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
                } else {
                    utBuilder.setPasswordType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
                }
                if (token.isCreated()) {
                    utBuilder.addCreated();
                }
                if (token.isNonce()) {
                    utBuilder.addNonce();
                }
                utBuilder.setUserInfo(userName, password);
                return utBuilder;
            }
            this.policyNotAsserted((AbstractToken)token, "No username available", message);
        } else {
            this.policyNotAsserted((AbstractToken)token, "No username available", message);
        }
        return null;
    }
}

