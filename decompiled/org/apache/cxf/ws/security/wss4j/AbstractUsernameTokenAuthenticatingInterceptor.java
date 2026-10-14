/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.xml.soap.SOAPException;
import javax.xml.stream.XMLStreamException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.security.SecurityToken;
import org.apache.cxf.common.security.UsernameToken;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.DefaultSecurityContext;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.ws.security.wss4j.DefaultWSS4JSecurityContextCreator;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngine;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.validate.UsernameTokenValidator;
import org.w3c.dom.Element;

public abstract class AbstractUsernameTokenAuthenticatingInterceptor
extends WSS4JInInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractUsernameTokenAuthenticatingInterceptor.class);
    private boolean supportDigestPasswords;

    public AbstractUsernameTokenAuthenticatingInterceptor() {
        this(new HashMap<String, Object>());
    }

    public AbstractUsernameTokenAuthenticatingInterceptor(Map<String, Object> properties) {
        super(properties);
        this.getAfter().add(PolicyBasedWSS4JInInterceptor.class.getName());
    }

    public void setSupportDigestPasswords(boolean support) {
        this.supportDigestPasswords = support;
    }

    public boolean getSupportDigestPasswords() {
        return this.supportDigestPasswords;
    }

    @Override
    public void handleMessage(SoapMessage msg) throws Fault {
        SecurityToken token = msg.get(SecurityToken.class);
        SecurityContext context = msg.get(SecurityContext.class);
        if (token == null || context == null || context.getUserPrincipal() == null) {
            super.handleMessage(msg);
            return;
        }
        UsernameToken ut = (UsernameToken)token;
        Subject subject = this.createSubject(ut.getName(), ut.getPassword(), ut.isHashed(), ut.getNonce(), ut.getCreatedTime());
        SecurityContext sc = this.doCreateSecurityContext(context.getUserPrincipal(), subject);
        msg.put(SecurityContext.class, sc);
    }

    @Override
    protected void doResults(SoapMessage msg, String actor, Element soapHeader, Element soapBody, WSHandlerResult wsResult, boolean utWithCallbacks) throws SOAPException, XMLStreamException, WSSecurityException {
        List results = CastUtils.cast((List)msg.get("RECV_RESULTS"));
        if (results == null) {
            results = new LinkedList();
            msg.put("RECV_RESULTS", (Object)results);
        }
        results.add(0, wsResult);
        new UsernameTokenSecurityContextCreator().createSecurityContext(msg, wsResult);
    }

    protected SecurityContext doCreateSecurityContext(Principal p, Subject subject) {
        return new DefaultSecurityContext(p, subject);
    }

    protected void setSubject(String name, String password, boolean isDigest, String nonce, String created) throws WSSecurityException {
        Message msg = PhaseInterceptorChain.getCurrentMessage();
        if (msg == null) {
            throw new IllegalStateException("Current message is not available");
        }
        Subject subject = null;
        try {
            subject = this.createSubject(name, password, isDigest, nonce, created);
        }
        catch (Exception ex) {
            String errorMessage = "Failed Authentication : Subject has not been created";
            LOG.severe(errorMessage);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, ex);
        }
        if (subject == null || subject.getPrincipals().isEmpty() || !subject.getPrincipals().iterator().next().getName().equals(name)) {
            String errorMessage = "Failed Authentication : Invalid Subject";
            LOG.severe(errorMessage);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, new Exception(errorMessage));
        }
        msg.put(Subject.class, subject);
    }

    protected abstract Subject createSubject(String var1, String var2, boolean var3, String var4, String var5) throws SecurityException;

    @Override
    protected WSSecurityEngine getSecurityEngine(boolean utNoCallbacks) {
        WSSConfig config = WSSConfig.getNewInstance();
        config.setValidator(WSConstants.USERNAME_TOKEN, new CustomValidator());
        WSSecurityEngine ret = new WSSecurityEngine();
        ret.setWssConfig(config);
        return ret;
    }

    private static class UsernameTokenSecurityContextCreator
    extends DefaultWSS4JSecurityContextCreator {
        private UsernameTokenSecurityContextCreator() {
        }

        @Override
        protected SecurityContext createSecurityContext(Principal p) {
            Message msg = PhaseInterceptorChain.getCurrentMessage();
            if (msg == null) {
                throw new IllegalStateException("Current message is not available");
            }
            return new DefaultSecurityContext(p, msg.get(Subject.class));
        }
    }

    protected class CustomValidator
    extends UsernameTokenValidator {
        protected CustomValidator() {
        }

        @Override
        protected void verifyCustomPassword(org.apache.wss4j.dom.message.token.UsernameToken usernameToken, RequestData data) throws WSSecurityException {
            AbstractUsernameTokenAuthenticatingInterceptor.this.setSubject(usernameToken.getName(), usernameToken.getPassword(), false, null, null);
        }

        @Override
        protected void verifyPlaintextPassword(org.apache.wss4j.dom.message.token.UsernameToken usernameToken, RequestData data) throws WSSecurityException {
            AbstractUsernameTokenAuthenticatingInterceptor.this.setSubject(usernameToken.getName(), usernameToken.getPassword(), false, null, null);
        }

        @Override
        protected void verifyDigestPassword(org.apache.wss4j.dom.message.token.UsernameToken usernameToken, RequestData data) throws WSSecurityException {
            if (!AbstractUsernameTokenAuthenticatingInterceptor.this.supportDigestPasswords) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
            }
            String user = usernameToken.getName();
            String password = usernameToken.getPassword();
            boolean isHashed = usernameToken.isHashed();
            String nonce = usernameToken.getNonce();
            String createdTime = usernameToken.getCreated();
            AbstractUsernameTokenAuthenticatingInterceptor.this.setSubject(user, password, isHashed, nonce, createdTime);
        }

        @Override
        protected void verifyUnknownPassword(org.apache.wss4j.dom.message.token.UsernameToken usernameToken, RequestData data) throws WSSecurityException {
            AbstractUsernameTokenAuthenticatingInterceptor.this.setSubject(usernameToken.getName(), null, false, null, null);
        }
    }
}

