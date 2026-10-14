/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.validate;

import java.security.Key;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.kerberos.KerberosServiceContext;
import org.apache.wss4j.common.kerberos.KerberosServiceExceptionAction;
import org.apache.wss4j.common.kerberos.KerberosTokenDecoder;
import org.apache.wss4j.common.kerberos.KerberosTokenDecoderException;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KerberosTokenValidator
implements Validator {
    private static final Logger LOG = LoggerFactory.getLogger(KerberosTokenValidator.class);
    private String serviceName;
    private CallbackHandler callbackHandler;
    private String contextName;
    private KerberosTokenDecoder kerberosTokenDecoder;
    private boolean isUsernameServiceNameForm;
    private boolean spnego;

    public String getContextName() {
        return this.contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public CallbackHandler getCallbackHandler() {
        return this.callbackHandler;
    }

    public void setCallbackHandler(CallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public KerberosTokenDecoder getKerberosTokenDecoder() {
        return this.kerberosTokenDecoder;
    }

    public void setKerberosTokenDecoder(KerberosTokenDecoder kerberosTokenDecoder) {
        this.kerberosTokenDecoder = kerberosTokenDecoder;
    }

    @Override
    public Credential validate(Credential credential, RequestData data) throws WSSecurityException {
        if (credential == null || credential.getBinarySecurityToken() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCredential");
        }
        BinarySecurity binarySecurity = credential.getBinarySecurityToken();
        if (!(binarySecurity instanceof KerberosSecurity)) {
            return credential;
        }
        if (LOG.isDebugEnabled()) {
            try {
                String jaasAuth = System.getProperty("java.security.auth.login.config");
                String krbConf = System.getProperty("java.security.krb5.conf");
                LOG.debug("KerberosTokenValidator - Using JAAS auth login file: " + jaasAuth);
                LOG.debug("KerberosTokenValidator - Using KRB conf file: " + krbConf);
            }
            catch (SecurityException ex) {
                LOG.debug(ex.getMessage(), ex);
            }
        }
        LoginContext loginContext = null;
        try {
            loginContext = this.callbackHandler != null ? new LoginContext(this.getContextName(), this.callbackHandler) : (data.getCallbackHandler() != null ? new LoginContext(this.getContextName(), data.getCallbackHandler()) : new LoginContext(this.getContextName()));
            loginContext.login();
        }
        catch (LoginException ex) {
            LOG.debug(ex.getMessage(), ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "kerberosLoginError", new Object[]{ex.getMessage()});
        }
        LOG.debug("Successfully authenticated to the TGT");
        byte[] token = binarySecurity.getToken();
        Subject subject = loginContext.getSubject();
        String service2 = this.serviceName;
        if (service2 == null) {
            Set<Principal> principals = subject.getPrincipals();
            if (principals.isEmpty()) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosLoginError", new Object[]{"No Client principals found after login"});
            }
            service2 = principals.iterator().next().getName();
        }
        KerberosServiceExceptionAction action = new KerberosServiceExceptionAction(token, service2, this.isUsernameServiceNameForm(), this.spnego);
        KerberosServiceContext krbServiceCtx = null;
        try {
            krbServiceCtx = Subject.doAs(subject, action);
        }
        catch (PrivilegedActionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof WSSecurityException) {
                throw (WSSecurityException)cause;
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception(cause), "kerberosTicketValidationError");
        }
        credential.setPrincipal(krbServiceCtx.getPrincipal());
        credential.setDelegationCredential(krbServiceCtx.getDelegationCredential());
        LOG.debug("Trying to obtain the Session Key from the KerberosServiceContext.");
        Key sessionKey = krbServiceCtx.getSessionKey();
        if (null != sessionKey) {
            LOG.debug("Found session key in the KerberosServiceContext.");
            credential.setSecretKey(sessionKey.getEncoded());
        } else {
            LOG.debug("Session key is not found in the KerberosServiceContext.");
        }
        if (null == credential.getSecretKey() && this.kerberosTokenDecoder != null) {
            LOG.debug("KerberosTokenDecoder is set.Trying to obtain the session key from it.");
            this.kerberosTokenDecoder.clear();
            this.kerberosTokenDecoder.setToken(token);
            this.kerberosTokenDecoder.setSubject(subject);
            try {
                byte[] key = this.kerberosTokenDecoder.getSessionKey();
                if (null != key) {
                    LOG.debug("Session key obtained from the KerberosTokenDecoder.");
                    credential.setSecretKey(key);
                }
                LOG.debug("Session key could not be obtained from the KerberosTokenDecoder.");
            }
            catch (KerberosTokenDecoderException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "Error retrieving session key.");
            }
        } else {
            LOG.debug("KerberosTokenDecoder is not set.");
        }
        LOG.debug("Successfully validated a ticket");
        return credential;
    }

    public boolean isUsernameServiceNameForm() {
        return this.isUsernameServiceNameForm;
    }

    public void setUsernameServiceNameForm(boolean isUsernameServiceNameForm) {
        this.isUsernameServiceNameForm = isUsernameServiceNameForm;
    }

    public boolean isSpnego() {
        return this.spnego;
    }

    public void setSpnego(boolean spnego) {
        this.spnego = spnego;
    }
}

