/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.interceptor;

import java.security.Principal;
import java.util.Set;
import java.util.logging.Logger;
import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.rt.security.saml.claims.SAMLSecurityContext;
import org.apache.cxf.rt.security.saml.utils.SAMLUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.SecurityContext;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.WSUsernameTokenPrincipalImpl;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.UsernameTokenValidator;
import org.apache.wss4j.dom.validate.Validator;
import org.w3c.dom.Document;

public abstract class WSS4JBasicAuthValidator {
    private static final Logger LOG = LogUtils.getL7dLogger(WSS4JBasicAuthValidator.class);
    private static final String SAML_ROLE_ATTRIBUTENAME_DEFAULT = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role";
    private Validator validator;
    private CallbackHandler callbackHandler;

    protected void validate(Message message) throws WSSecurityException {
        AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
        if (policy == null || policy.getUserName() == null || policy.getPassword() == null) {
            String name = null;
            if (policy != null) {
                name = policy.getUserName();
            }
            String errorMsg = "No user name and/or password is available, name: " + name;
            LOG.warning(errorMsg);
            throw new SecurityException(errorMsg);
        }
        UsernameToken token = this.convertPolicyToToken(policy);
        Credential credential = new Credential();
        credential.setUsernametoken(token);
        RequestData data = new RequestData();
        data.setMsgContext(message);
        data.setCallbackHandler(this.callbackHandler);
        credential = this.getValidator().validate(credential, data);
        SecurityContext sc = null;
        if (credential != null && credential.getPrincipal() != null) {
            sc = this.createSecurityContext(message, credential);
        } else {
            WSUsernameTokenPrincipalImpl p = new WSUsernameTokenPrincipalImpl(policy.getUserName(), false);
            p.setPassword(policy.getPassword());
            sc = this.createSecurityContext(p);
        }
        message.put(SecurityContext.class, sc);
    }

    protected UsernameToken convertPolicyToToken(AuthorizationPolicy policy) {
        Document doc = DOMUtils.getEmptyDocument();
        UsernameToken token = new UsernameToken(false, doc, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
        token.setName(policy.getUserName());
        token.setPassword(policy.getPassword());
        return token;
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

    protected SecurityContext createSecurityContext(Message msg, Credential credential) {
        SamlAssertionWrapper samlAssertion = credential.getTransformedToken();
        if (samlAssertion == null) {
            samlAssertion = credential.getSamlAssertion();
        }
        if (samlAssertion != null) {
            String roleAttributeName = (String)SecurityUtils.getSecurityPropertyValue("security.saml-role-attributename", msg);
            if (roleAttributeName == null || roleAttributeName.length() == 0) {
                roleAttributeName = SAML_ROLE_ATTRIBUTENAME_DEFAULT;
            }
            ClaimCollection claims = SAMLUtils.getClaims(samlAssertion);
            Set<Principal> roles = SAMLUtils.parseRolesFromClaims(claims, roleAttributeName, null);
            SAMLSecurityContext context = new SAMLSecurityContext(credential.getPrincipal(), roles, claims);
            context.setIssuer(SAMLUtils.getIssuer(samlAssertion));
            context.setAssertionElement(SAMLUtils.getAssertionElement(samlAssertion));
            return context;
        }
        return this.createSecurityContext(credential.getPrincipal());
    }

    public Validator getValidator() {
        if (this.validator != null) {
            return this.validator;
        }
        return new UsernameTokenValidator();
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public CallbackHandler getCallbackHandler() {
        return this.callbackHandler;
    }

    public void setCallbackHandler(CallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }
}

