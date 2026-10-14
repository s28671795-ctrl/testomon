/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.DefaultSecurityContext;
import org.apache.cxf.interceptor.security.RolePrefixSecurityContextImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.rt.security.saml.claims.SAMLSecurityContext;
import org.apache.cxf.rt.security.saml.utils.SAMLUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.SecurityContext;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.SAMLTokenPrincipal;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.stax.securityEvent.KerberosTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.KeyValueTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.SamlTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.UsernameTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityEvent.X509TokenSecurityEvent;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;
import org.apache.wss4j.stax.securityToken.SubjectAndPrincipalSecurityToken;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class StaxSecurityContextInInterceptor
extends AbstractPhaseInterceptor<SoapMessage> {
    public static final String SAML_ROLE_ATTRIBUTENAME_DEFAULT = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role";

    public StaxSecurityContextInInterceptor() {
        super("pre-protocol");
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        List incomingSecurityEventList = (List)soapMessage.get(SecurityEvent.class.getName() + ".in");
        if (incomingSecurityEventList != null) {
            try {
                this.doResults(soapMessage, incomingSecurityEventList);
            }
            catch (WSSecurityException e) {
                throw this.createSoapFault(soapMessage.getVersion(), e);
            }
        }
    }

    private void doResults(SoapMessage msg, List<SecurityEvent> incomingSecurityEventList) throws WSSecurityException {
        ArrayList<SecurityEventConstants.Event> desiredSecurityEvents = new ArrayList<SecurityEventConstants.Event>();
        desiredSecurityEvents.add(WSSecurityEventConstants.SAML_TOKEN);
        desiredSecurityEvents.add(WSSecurityEventConstants.USERNAME_TOKEN);
        desiredSecurityEvents.add(WSSecurityEventConstants.KERBEROS_TOKEN);
        desiredSecurityEvents.add(WSSecurityEventConstants.X509Token);
        desiredSecurityEvents.add(WSSecurityEventConstants.KeyValueToken);
        for (SecurityEventConstants.Event desiredEvent : desiredSecurityEvents) {
            SubjectAndPrincipalSecurityToken token = null;
            try {
                token = this.getSubjectPrincipalToken(incomingSecurityEventList, desiredEvent, msg);
            }
            catch (XMLSecurityException xMLSecurityException) {
                // empty catch block
            }
            if (token == null) continue;
            Principal p = token.getPrincipal();
            Subject subject = token.getSubject();
            if (subject != null) {
                String roleClassifier = (String)msg.getContextualProperty("ws-security.role.classifier");
                if (roleClassifier != null && !"".equals(roleClassifier)) {
                    String roleClassifierType = (String)msg.getContextualProperty("ws-security.role.classifier.type");
                    if (roleClassifierType == null || "".equals(roleClassifierType)) {
                        roleClassifierType = "prefix";
                    }
                    msg.put(SecurityContext.class, new RolePrefixSecurityContextImpl(subject, roleClassifier, roleClassifierType));
                    break;
                }
                msg.put(SecurityContext.class, new DefaultSecurityContext(subject));
                break;
            }
            if (p == null) continue;
            SamlAssertionWrapper receivedAssertion = null;
            if (desiredEvent == WSSecurityEventConstants.SAML_TOKEN) {
                String roleAttributeName = (String)SecurityUtils.getSecurityPropertyValue("security.saml-role-attributename", msg);
                if (roleAttributeName == null || roleAttributeName.length() == 0) {
                    roleAttributeName = SAML_ROLE_ATTRIBUTENAME_DEFAULT;
                }
                if ((receivedAssertion = ((SAMLTokenPrincipal)token.getPrincipal()).getToken()) == null) break;
                ClaimCollection claims = SAMLUtils.getClaims(receivedAssertion);
                Set<Principal> roles = SAMLUtils.parseRolesFromClaims(claims, roleAttributeName, null);
                SAMLSecurityContext context = new SAMLSecurityContext(p, roles, claims);
                msg.put(SecurityContext.class, context);
                break;
            }
            msg.put(SecurityContext.class, this.createSecurityContext(p));
            break;
        }
    }

    private SubjectAndPrincipalSecurityToken getSubjectPrincipalToken(List<SecurityEvent> incomingSecurityEventList, SecurityEventConstants.Event desiredEvent, Message msg) throws XMLSecurityException {
        for (SecurityEvent event : incomingSecurityEventList) {
            if (desiredEvent != event.getSecurityEventType()) continue;
            if (event.getSecurityEventType() == WSSecurityEventConstants.USERNAME_TOKEN && this.isUsernameTokenEventAllowed((UsernameTokenSecurityEvent)event, msg)) {
                return (SubjectAndPrincipalSecurityToken)((UsernameTokenSecurityEvent)event).getSecurityToken();
            }
            if (event.getSecurityEventType() == WSSecurityEventConstants.SAML_TOKEN && this.isSamlEventAllowed((SamlTokenSecurityEvent)event, msg)) {
                return (SubjectAndPrincipalSecurityToken)((SamlTokenSecurityEvent)event).getSecurityToken();
            }
            if (event.getSecurityEventType() == WSSecurityEventConstants.X509Token && this.isUsedForPublicKeySignature((SubjectAndPrincipalSecurityToken)((X509TokenSecurityEvent)event).getSecurityToken())) {
                return (SubjectAndPrincipalSecurityToken)((X509TokenSecurityEvent)event).getSecurityToken();
            }
            if (event.getSecurityEventType() == WSSecurityEventConstants.KeyValueToken && this.isUsedForPublicKeySignature((SubjectAndPrincipalSecurityToken)((KeyValueTokenSecurityEvent)event).getSecurityToken())) {
                return (SubjectAndPrincipalSecurityToken)((KeyValueTokenSecurityEvent)event).getSecurityToken();
            }
            if (event.getSecurityEventType() != WSSecurityEventConstants.KERBEROS_TOKEN) continue;
            return (SubjectAndPrincipalSecurityToken)((KerberosTokenSecurityEvent)event).getSecurityToken();
        }
        return null;
    }

    private boolean isUsedForPublicKeySignature(SubjectAndPrincipalSecurityToken token) throws XMLSecurityException {
        if (token == null) {
            return false;
        }
        List<SecurityTokenConstants.TokenUsage> tokenUsages = token.getTokenUsages();
        boolean usedForSignature = false;
        if (tokenUsages != null) {
            for (SecurityTokenConstants.TokenUsage usage : tokenUsages) {
                if (!"MainSignature".equals(usage.getName())) continue;
                usedForSignature = true;
                break;
            }
        }
        if (!usedForSignature) {
            return false;
        }
        return token.getPublicKey() != null || token.getX509Certificates() != null && token.getX509Certificates().length > 0;
    }

    private boolean isSamlEventAllowed(SamlTokenSecurityEvent event, Message msg) {
        if (event == null) {
            return false;
        }
        boolean allowUnsignedSamlPrincipals = SecurityUtils.getSecurityPropertyBoolean("security.enable.unsigned-saml-assertion.principal", msg, false);
        return event.getSecurityToken() != null && ((SamlSecurityToken)event.getSecurityToken()).getSamlAssertionWrapper() != null && (allowUnsignedSamlPrincipals || ((SamlSecurityToken)event.getSecurityToken()).getSamlAssertionWrapper().isSigned());
    }

    private boolean isUsernameTokenEventAllowed(UsernameTokenSecurityEvent event, Message msg) {
        if (event == null) {
            return false;
        }
        boolean allowUTNoPassword = SecurityUtils.getSecurityPropertyBoolean("security.enable.ut-no-password.principal", msg, false);
        return event.getSecurityToken() != null && (allowUTNoPassword || ((UsernameSecurityToken)event.getSecurityToken()).getPassword() != null);
    }

    private SecurityContext createSecurityContext(final Principal p) {
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

    private SoapFault createSoapFault(SoapVersion version, WSSecurityException e) {
        SoapFault fault;
        QName faultCode = e.getFaultCode();
        if (version.getVersion() == 1.1 && faultCode != null) {
            fault = new SoapFault(e.getMessage(), (Throwable)e, faultCode);
        } else {
            fault = new SoapFault(e.getMessage(), (Throwable)e, version.getSender());
            if (version.getVersion() != 1.1 && faultCode != null) {
                fault.setSubCode(faultCode);
            }
        }
        return fault;
    }
}

