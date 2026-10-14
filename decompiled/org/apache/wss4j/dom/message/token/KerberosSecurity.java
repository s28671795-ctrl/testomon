/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message.token;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.util.Set;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.kerberos.KerberosTicket;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.kerberos.KerberosClientExceptionAction;
import org.apache.wss4j.common.kerberos.KerberosContext;
import org.apache.wss4j.common.kerberos.KerberosContextAndServiceNameCallback;
import org.apache.wss4j.common.token.BinarySecurity;
import org.ietf.jgss.GSSCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class KerberosSecurity
extends BinarySecurity {
    private static final Logger LOG = LoggerFactory.getLogger(KerberosSecurity.class);
    private SecretKey secretKey;

    public KerberosSecurity(Element elem, BSPEnforcer bspEnforcer) throws WSSecurityException {
        super(elem, bspEnforcer);
        String valueType = this.getValueType();
        if (!"http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(valueType)) {
            bspEnforcer.handleBSPRule(BSPRule.R6902);
        }
    }

    public KerberosSecurity(Document doc) {
        super(doc);
    }

    public boolean isV5ApReq() {
        String type = this.getValueType();
        return "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ1510".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ4120".equals(type);
    }

    public boolean isGssV5ApReq() {
        String type = this.getValueType();
        return "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510".equals(type) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ4120".equals(type);
    }

    public void retrieveServiceTicket(CallbackHandler callbackHandler) throws WSSecurityException {
        KerberosContextAndServiceNameCallback contextAndServiceNameCallback = new KerberosContextAndServiceNameCallback();
        try {
            callbackHandler.handle(new Callback[]{contextAndServiceNameCallback});
        }
        catch (IOException | UnsupportedCallbackException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e);
        }
        String jaasLoginModuleName = contextAndServiceNameCallback.getContextName();
        if (jaasLoginModuleName == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosCallbackContextNameNotSupplied");
        }
        String serviceName = contextAndServiceNameCallback.getServiceName();
        if (serviceName == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosCallbackServiceNameNotSupplied");
        }
        this.retrieveServiceTicket(jaasLoginModuleName, callbackHandler, serviceName);
    }

    public void retrieveServiceTicket(String jaasLoginModuleName, CallbackHandler callbackHandler, String serviceName) throws WSSecurityException {
        this.retrieveServiceTicket(jaasLoginModuleName, callbackHandler, serviceName, false);
    }

    public void retrieveServiceTicket(String jaasLoginModuleName, CallbackHandler callbackHandler, String serviceName, boolean isUsernameServiceNameForm) throws WSSecurityException {
        this.retrieveServiceTicket(jaasLoginModuleName, callbackHandler, serviceName, isUsernameServiceNameForm, false);
    }

    public void retrieveServiceTicket(String jaasLoginModuleName, CallbackHandler callbackHandler, String serviceName, boolean isUsernameServiceNameForm, boolean requestCredDeleg) throws WSSecurityException {
        this.retrieveServiceTicket(jaasLoginModuleName, callbackHandler, serviceName, isUsernameServiceNameForm, requestCredDeleg, null);
    }

    public void retrieveServiceTicket(String jaasLoginModuleName, CallbackHandler callbackHandler, String serviceName, boolean isUsernameServiceNameForm, boolean requestCredDeleg, GSSCredential delegatedCredential) throws WSSecurityException {
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
        KerberosTicket tgt = this.getKerberosTicket(clientSubject, null);
        this.decorateSubject(clientSubject);
        KerberosClientExceptionAction action = new KerberosClientExceptionAction(clientPrincipals.iterator().next(), serviceName, isUsernameServiceNameForm, requestCredDeleg, delegatedCredential, false, false);
        KerberosContext krbCtx = null;
        try {
            krbCtx = Subject.doAs(clientSubject, action);
            Key sessionKey = krbCtx.getSecretKey();
            if (sessionKey != null) {
                this.secretKey = new SecretKeySpec(sessionKey.getEncoded(), sessionKey.getAlgorithm());
            } else {
                KerberosTicket serviceTicket = this.getKerberosTicket(clientSubject, tgt);
                if (serviceTicket != null) {
                    this.secretKey = serviceTicket.getSessionKey();
                }
            }
            if (this.secretKey == null) {
                LOG.debug("No secret key for kerberos was found");
            } else {
                LOG.debug("Successfully retrieved a secret key for kerberos");
            }
            this.setToken(krbCtx.getKerberosToken());
        }
        catch (PrivilegedActionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof WSSecurityException) {
                throw (WSSecurityException)cause;
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception(cause), "kerberosServiceTicketError");
        }
        finally {
            if (krbCtx != null) {
                krbCtx.dispose();
            }
        }
        LOG.debug("Successfully retrieved a service ticket");
        if ("".equals(this.getValueType())) {
            this.setValueType("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ");
        }
    }

    protected void decorateSubject(Subject subject) {
    }

    private KerberosTicket getKerberosTicket(Subject clientSubject, KerberosTicket previousTicket) {
        Set<KerberosTicket> privateCredentials = clientSubject.getPrivateCredentials(KerberosTicket.class);
        if (privateCredentials == null || privateCredentials.isEmpty()) {
            LOG.debug("Kerberos client subject private credentials are null");
            return null;
        }
        for (KerberosTicket privateCredential : privateCredentials) {
            if (privateCredential.equals(previousTicket)) continue;
            return privateCredential;
        }
        return null;
    }

    public SecretKey getSecretKey() {
        return this.secretKey;
    }

    public static boolean isKerberosToken(String valueType) {
        return "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ1510".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ4120".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ4120".equals(valueType);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KerberosSecurity)) {
            return false;
        }
        KerberosSecurity that = (KerberosSecurity)object;
        if (this.secretKey != null && !this.secretKey.equals(that.secretKey)) {
            return false;
        }
        if (this.secretKey == null && that.secretKey != null) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        if (this.secretKey != null) {
            hashCode *= 31 + this.secretKey.hashCode();
        }
        return hashCode *= 31 + super.hashCode();
    }
}

