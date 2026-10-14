/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.spnego;

import java.security.Principal;
import java.security.PrivilegedActionException;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.kerberos.KerberosClientExceptionAction;
import org.apache.wss4j.common.kerberos.KerberosContext;
import org.apache.wss4j.common.kerberos.KerberosServiceContext;
import org.apache.wss4j.common.kerberos.KerberosServiceExceptionAction;
import org.apache.wss4j.common.spnego.SpnegoClientAction;
import org.apache.wss4j.common.spnego.SpnegoServiceAction;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.MessageProp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpnegoTokenContext {
    private static final Logger LOG = LoggerFactory.getLogger(SpnegoTokenContext.class);
    private GSSContext secContext;
    private byte[] token;
    private boolean mutualAuth;
    private SpnegoClientAction clientAction;
    private SpnegoServiceAction serviceAction;
    private GSSCredential delegationCredential;
    private Principal spnegoPrincipal;

    public void retrieveServiceTicket(String jaasLoginModuleName, CallbackHandler callbackHandler, String serviceName) throws WSSecurityException {
        this.retrieveServiceTicket(jaasLoginModuleName, callbackHandler, serviceName, false);
    }

    public void retrieveServiceTicket(String jaasLoginModuleName, CallbackHandler callbackHandler, String serviceName, boolean isUsernameServiceNameForm) throws WSSecurityException {
        this.retrieveServiceTicket(jaasLoginModuleName, callbackHandler, serviceName, isUsernameServiceNameForm, false, null);
    }

    public void retrieveServiceTicket(String jaasLoginModuleName, CallbackHandler callbackHandler, String serviceName, boolean isUsernameServiceNameForm, boolean requestCredDeleg, GSSCredential delegationCredential) throws WSSecurityException {
        LoginContext loginContext = null;
        try {
            loginContext = callbackHandler == null ? new LoginContext(jaasLoginModuleName) : new LoginContext(jaasLoginModuleName, callbackHandler);
            loginContext.login();
        }
        catch (LoginException ex) {
            LOG.debug(ex.getMessage(), ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "kerberosLoginError", new Object[]{ex.getMessage()});
        }
        LOG.debug("Successfully authenticated to the TGT");
        Subject clientSubject = loginContext.getSubject();
        Set<Principal> clientPrincipals = clientSubject.getPrincipals();
        if (clientPrincipals.isEmpty()) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosLoginError", new Object[]{"No Client principals found after login"});
        }
        if (this.clientAction != null) {
            this.clientAction.setServiceName(serviceName);
            this.clientAction.setMutualAuth(this.mutualAuth);
            this.clientAction.setUserNameServiceForm(isUsernameServiceNameForm);
            this.token = Subject.doAs(clientSubject, this.clientAction);
            if (this.token == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosServiceTicketError");
            }
            this.secContext = this.clientAction.getContext();
        } else {
            KerberosClientExceptionAction action = new KerberosClientExceptionAction(null, serviceName, isUsernameServiceNameForm, requestCredDeleg, delegationCredential, true, this.mutualAuth);
            KerberosContext krbCtx = null;
            try {
                krbCtx = Subject.doAs(clientSubject, action);
                this.token = krbCtx.getKerberosToken();
                if (this.token == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosServiceTicketError");
                }
                this.secContext = krbCtx.getGssContext();
            }
            catch (PrivilegedActionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof WSSecurityException) {
                    throw (WSSecurityException)cause;
                }
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception(cause), "kerberosServiceTicketError");
            }
        }
        LOG.debug("Successfully retrieved a service ticket");
    }

    public void validateServiceTicket(String jaasLoginModuleName, CallbackHandler callbackHandler, String serviceName, byte[] ticket) throws WSSecurityException {
        this.validateServiceTicket(jaasLoginModuleName, callbackHandler, serviceName, false, ticket);
    }

    public void validateServiceTicket(String jaasLoginModuleName, CallbackHandler callbackHandler, String serviceName, boolean isUsernameServiceNameForm, byte[] ticket) throws WSSecurityException {
        LoginContext loginContext = null;
        try {
            loginContext = callbackHandler == null ? new LoginContext(jaasLoginModuleName) : new LoginContext(jaasLoginModuleName, callbackHandler);
            loginContext.login();
        }
        catch (LoginException ex) {
            LOG.debug(ex.getMessage(), ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "kerberosLoginError", new Object[]{ex.getMessage()});
        }
        LOG.debug("Successfully authenticated to the TGT");
        Subject subject = loginContext.getSubject();
        String service2 = serviceName;
        if (service2 == null) {
            Set<Principal> principals = subject.getPrincipals();
            if (principals.isEmpty()) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosLoginError", new Object[]{"No Client principals found after login"});
            }
            service2 = principals.iterator().next().getName();
        }
        if (this.serviceAction != null) {
            this.serviceAction.setTicket(ticket);
            this.serviceAction.setServiceName(service2);
            this.serviceAction.setUsernameServiceNameForm(isUsernameServiceNameForm);
            this.token = Subject.doAs(subject, this.serviceAction);
            this.secContext = this.serviceAction.getContext();
        } else {
            KerberosServiceExceptionAction action = new KerberosServiceExceptionAction(ticket, service2, isUsernameServiceNameForm, true);
            KerberosServiceContext krbCtx = null;
            try {
                krbCtx = Subject.doAs(subject, action);
                this.token = krbCtx.getKerberosToken();
                if (this.token == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosServiceTicketError");
                }
                this.secContext = krbCtx.getGssContext();
                this.delegationCredential = krbCtx.getDelegationCredential();
                this.spnegoPrincipal = krbCtx.getPrincipal();
            }
            catch (PrivilegedActionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof WSSecurityException) {
                    throw (WSSecurityException)cause;
                }
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception(cause), "kerberosServiceTicketError");
            }
        }
        LOG.debug("Successfully validated a service ticket");
    }

    public void setMutualAuth(boolean mutualAuthentication) {
        this.mutualAuth = mutualAuthentication;
    }

    public byte[] getToken() {
        return this.token;
    }

    public boolean isEstablished() {
        if (this.secContext == null) {
            return false;
        }
        return this.secContext.isEstablished();
    }

    public byte[] unwrapKey(byte[] secret) throws WSSecurityException {
        MessageProp mProp = new MessageProp(0, true);
        try {
            return this.secContext.unwrap(secret, 0, secret.length, mProp);
        }
        catch (GSSException e) {
            LOG.debug("Error in cleaning up a GSS context", e);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "spnegoKeyError");
        }
    }

    public byte[] wrapKey(byte[] secret) throws WSSecurityException {
        MessageProp mProp = new MessageProp(0, true);
        try {
            return this.secContext.wrap(secret, 0, secret.length, mProp);
        }
        catch (GSSException e) {
            LOG.debug("Error in cleaning up a GSS context", e);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "spnegoKeyError");
        }
    }

    public void setSpnegoClientAction(SpnegoClientAction spnegoClientAction) {
        this.clientAction = spnegoClientAction;
    }

    public void setSpnegoServiceAction(SpnegoServiceAction spnegoServiceAction) {
        this.serviceAction = spnegoServiceAction;
    }

    public void clear() {
        this.token = null;
        this.mutualAuth = false;
        this.delegationCredential = null;
        this.spnegoPrincipal = null;
        try {
            this.secContext.dispose();
        }
        catch (GSSException e) {
            LOG.debug("Error in cleaning up a GSS context", e);
        }
    }

    public GSSCredential getDelegationCredential() {
        return this.delegationCredential;
    }

    public Principal getSpnegoPrincipal() {
        return this.spnegoPrincipal;
    }
}

