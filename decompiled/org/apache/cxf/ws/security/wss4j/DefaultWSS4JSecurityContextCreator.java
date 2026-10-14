/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.kerberos.KerberosPrincipal;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.security.DefaultSecurityContext;
import org.apache.cxf.interceptor.security.RolePrefixSecurityContextImpl;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.rt.security.saml.claims.SAMLSecurityContext;
import org.apache.cxf.rt.security.saml.utils.SAMLUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.ws.security.wss4j.WSS4JSecurityContextCreator;
import org.apache.cxf.ws.security.wss4j.WSS4JTokenConverter;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;

public class DefaultWSS4JSecurityContextCreator
implements WSS4JSecurityContextCreator {
    private static final List<Integer> DEFAULT_SECURITY_PRIORITIES = new ArrayList<Integer>();
    private List<Integer> securityPriorities = new ArrayList<Integer>(DEFAULT_SECURITY_PRIORITIES);

    @Override
    public void createSecurityContext(SoapMessage msg, WSHandlerResult handlerResult) {
        boolean allowUnsignedSamlPrincipals = SecurityUtils.getSecurityPropertyBoolean("security.enable.unsigned-saml-assertion.principal", msg, false);
        boolean allowUTNoPassword = SecurityUtils.getSecurityPropertyBoolean("security.enable.ut-no-password.principal", msg, false);
        boolean useJAASSubject = true;
        String useJAASSubjectStr = (String)SecurityUtils.getSecurityPropertyValue("security.sc.jaas-subject", msg);
        if (useJAASSubjectStr != null) {
            useJAASSubject = Boolean.parseBoolean(useJAASSubjectStr);
        }
        Map<Integer, List<WSSecurityEngineResult>> actionResults = handlerResult.getActionResults();
        for (Integer resultPriority : this.securityPriorities) {
            List<WSSecurityEngineResult> foundResults;
            if (resultPriority == 8 && !allowUnsignedSamlPrincipals || resultPriority == 8192 && !allowUTNoPassword || (foundResults = actionResults.get(resultPriority)) == null || foundResults.isEmpty()) continue;
            for (WSSecurityEngineResult result : foundResults) {
                SecurityContext context;
                if (this.skipResult(resultPriority, result) || (context = this.createSecurityContext(msg, useJAASSubject, result)) == null) continue;
                msg.put(SecurityContext.class, context);
                return;
            }
        }
    }

    private boolean skipResult(Integer resultPriority, WSSecurityEngineResult result) {
        Object binarySecurity = result.get("binary-security-token");
        PublicKey publickey = (PublicKey)result.get("public-key");
        X509Certificate cert = (X509Certificate)result.get("x509-certificate");
        return resultPriority == 4096 && (binarySecurity instanceof X509Security || binarySecurity instanceof PKIPathSecurity) || resultPriority == 2 && publickey == null && cert == null;
    }

    protected SecurityContext createSecurityContext(SoapMessage msg, boolean useJAASSubject, WSSecurityEngineResult wsResult) {
        Principal p = (Principal)wsResult.get("principal");
        Subject subject = (Subject)wsResult.get("subject");
        if (subject != null && !(p instanceof KerberosPrincipal) && useJAASSubject) {
            String roleClassifier = (String)msg.getContextualProperty("ws-security.role.classifier");
            if (roleClassifier != null && !"".equals(roleClassifier)) {
                String roleClassifierType = (String)msg.getContextualProperty("ws-security.role.classifier.type");
                if (roleClassifierType == null || "".equals(roleClassifierType)) {
                    roleClassifierType = "prefix";
                }
                return new RolePrefixSecurityContextImpl(subject, roleClassifier, roleClassifierType);
            }
            return new DefaultSecurityContext(p, subject);
        }
        if (p != null) {
            Object receivedAssertion;
            boolean utWithCallbacks = MessageUtils.getContextualBoolean(msg, "ws-security.validate.token", true);
            if (!utWithCallbacks) {
                WSS4JTokenConverter.convertToken(msg, p);
            }
            if ((receivedAssertion = wsResult.get("transformed-token")) == null) {
                receivedAssertion = wsResult.get("saml-assertion");
            }
            if (wsResult.get("delegation-credential") != null) {
                msg.put("ws-security.delegated.credential", wsResult.get("delegation-credential"));
            }
            if (receivedAssertion instanceof SamlAssertionWrapper) {
                String roleAttributeName = (String)SecurityUtils.getSecurityPropertyValue("security.saml-role-attributename", msg);
                if (roleAttributeName == null || roleAttributeName.length() == 0) {
                    roleAttributeName = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role";
                }
                ClaimCollection claims = SAMLUtils.getClaims((SamlAssertionWrapper)receivedAssertion);
                Set<Principal> roles = SAMLUtils.parseRolesFromClaims(claims, roleAttributeName, null);
                SAMLSecurityContext context = new SAMLSecurityContext(p, roles, claims);
                context.setIssuer(SAMLUtils.getIssuer(receivedAssertion));
                context.setAssertionElement(SAMLUtils.getAssertionElement(receivedAssertion));
                return context;
            }
            return this.createSecurityContext(p);
        }
        return null;
    }

    protected SecurityContext createSecurityContext(final Principal p) {
        return new SecurityContext(){

            @Override
            public Principal getUserPrincipal() {
                return p;
            }

            @Override
            public boolean isUserInRole(String arg0) {
                return false;
            }
        };
    }

    public List<Integer> getSecurityPriorities() {
        return this.securityPriorities;
    }

    public void setSecurityPriorities(List<Integer> securityPriorities) {
        this.securityPriorities = securityPriorities;
    }

    static {
        DEFAULT_SECURITY_PRIORITIES.add(16);
        DEFAULT_SECURITY_PRIORITIES.add(8);
        DEFAULT_SECURITY_PRIORITIES.add(1);
        DEFAULT_SECURITY_PRIORITIES.add(4096);
        DEFAULT_SECURITY_PRIORITIES.add(2);
        DEFAULT_SECURITY_PRIORITIES.add(8192);
    }
}

