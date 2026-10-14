/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.xacml2;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.AccessDeniedException;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.rt.security.saml.xacml2.DefaultXACMLRequestBuilder;
import org.apache.cxf.rt.security.saml.xacml2.XACMLRequestBuilder;
import org.apache.cxf.security.LoginSecurityContext;
import org.apache.cxf.security.SecurityContext;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.util.DOM2Writer;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResponseType;
import org.opensaml.xacml.ctx.ResultType;
import org.opensaml.xacml.ctx.StatusType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractXACMLAuthorizingInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractXACMLAuthorizingInterceptor.class);
    private XACMLRequestBuilder requestBuilder = new DefaultXACMLRequestBuilder();

    public AbstractXACMLAuthorizingInterceptor() {
        super("pre-invoke");
        OpenSAMLUtil.initSamlEngine();
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        block7: {
            SecurityContext sc = message.get(SecurityContext.class);
            if (sc instanceof LoginSecurityContext) {
                Principal principal = sc.getUserPrincipal();
                String principalName = null;
                if (principal != null) {
                    principalName = principal.getName();
                }
                LoginSecurityContext loginSecurityContext = (LoginSecurityContext)sc;
                Set<Principal> principalRoles = loginSecurityContext.getUserRoles();
                ArrayList<String> roles = new ArrayList<String>();
                if (principalRoles != null) {
                    for (Principal p : principalRoles) {
                        if (p == null || p.getName() == null || p.getName().equals(principalName)) continue;
                        roles.add(p.getName());
                    }
                }
                try {
                    if (this.authorize(principal, roles, message)) {
                        return;
                    }
                    break block7;
                }
                catch (Exception e) {
                    LOG.log(Level.FINE, "Unauthorized: " + e.getMessage(), e);
                    throw new AccessDeniedException("Unauthorized");
                }
            }
            LOG.log(Level.FINE, "The SecurityContext was not an instance of LoginSecurityContext. No authorization is possible as a result");
        }
        throw new AccessDeniedException("Unauthorized");
    }

    public XACMLRequestBuilder getRequestBuilder() {
        return this.requestBuilder;
    }

    public void setRequestBuilder(XACMLRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    protected boolean authorize(Principal principal, List<String> roles, Message message) throws Exception {
        ResponseType response;
        List<ResultType> results;
        RequestType request = this.requestBuilder.createRequest(principal, roles, message);
        if (LOG.isLoggable(Level.FINE)) {
            Document doc = DOMUtils.createDocument();
            Element requestElement = OpenSAMLUtil.toDom(request, doc);
            LOG.log(Level.FINE, DOM2Writer.nodeToString(requestElement));
        }
        if ((results = (response = this.performRequest(request, message)).getResults()) == null) {
            return false;
        }
        Iterator<ResultType> iterator2 = results.iterator();
        if (iterator2.hasNext()) {
            ResultType result = iterator2.next();
            this.handleObligations(request, principal, message, result);
            DecisionType.DECISION decision = result.getDecision() != null ? result.getDecision().getDecision() : DecisionType.DECISION.Deny;
            String code = "";
            String statusMessage = "";
            if (result.getStatus() != null) {
                StatusType status = result.getStatus();
                code = status.getStatusCode() != null ? status.getStatusCode().getValue() : "";
                String string = statusMessage = status.getStatusMessage() != null ? status.getStatusMessage().getValue() : "";
            }
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("XACML authorization result: " + (Object)((Object)decision) + ", code: " + code + ", message: " + statusMessage);
            }
            return decision == DecisionType.DECISION.Permit;
        }
        return false;
    }

    protected void handleObligations(RequestType request, Principal principal, Message message, ResultType result) throws Exception {
    }

    protected abstract ResponseType performRequest(RequestType var1, Message var2) throws Exception;
}

