/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.util.Set;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.kerberos.KerberosTicket;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.kerberos.KerberosClientExceptionAction;
import org.apache.wss4j.common.kerberos.KerberosContext;
import org.apache.wss4j.common.kerberos.KerberosContextAndServiceNameCallback;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.impl.securityToken.GenericOutboundSecurityToken;

public class KerberosClientSecurityToken
extends GenericOutboundSecurityToken {
    private CallbackHandler callbackHandler;
    private Key secretKey;
    private byte[] ticket;

    public KerberosClientSecurityToken(byte[] ticket, Key secretKey, String id) {
        super(id, WSSecurityTokenConstants.KERBEROS_TOKEN);
        this.ticket = ticket;
        this.secretKey = secretKey;
    }

    public KerberosClientSecurityToken(CallbackHandler callbackHandler, String id) {
        super(id, WSSecurityTokenConstants.KERBEROS_TOKEN);
        this.callbackHandler = callbackHandler;
    }

    private void getTGT() throws WSSecurityException {
        try {
            KerberosContextAndServiceNameCallback contextAndServiceNameCallback = new KerberosContextAndServiceNameCallback();
            this.callbackHandler.handle(new Callback[]{contextAndServiceNameCallback});
            if (contextAndServiceNameCallback.getContextName() == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosCallbackContextNameNotSupplied");
            }
            if (contextAndServiceNameCallback.getServiceName() == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosCallbackServiceNameNotSupplied");
            }
            LoginContext loginContext = new LoginContext(contextAndServiceNameCallback.getContextName(), this.callbackHandler);
            loginContext.login();
            Subject clientSubject = loginContext.getSubject();
            Set<Principal> clientPrincipals = clientSubject.getPrincipals();
            if (clientPrincipals.isEmpty()) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosLoginError", new Object[]{"No Client principals found after login"});
            }
            KerberosTicket tgt = this.getKerberosTicket(clientSubject, null);
            KerberosClientExceptionAction action = new KerberosClientExceptionAction(clientPrincipals.iterator().next(), contextAndServiceNameCallback.getServiceName(), contextAndServiceNameCallback.isUsernameServiceNameForm(), contextAndServiceNameCallback.isRequestCredDeleg());
            KerberosContext krbCtx = null;
            try {
                krbCtx = Subject.doAs(clientSubject, action);
                Key sessionKey = krbCtx.getSecretKey();
                if (sessionKey != null) {
                    this.secretKey = new SecretKeySpec(sessionKey.getEncoded(), sessionKey.getAlgorithm());
                } else {
                    KerberosTicket serviceTicket = this.getKerberosTicket(clientSubject, tgt);
                    this.secretKey = serviceTicket.getSessionKey();
                }
                this.ticket = krbCtx.getKerberosToken();
            }
            catch (PrivilegedActionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof WSSecurityException) {
                    throw (WSSecurityException)cause;
                }
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception(cause), "kerberosServiceTicketError");
            }
        }
        catch (IOException | UnsupportedCallbackException | LoginException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e);
        }
    }

    private KerberosTicket getKerberosTicket(Subject clientSubject, KerberosTicket previousTicket) {
        Set<KerberosTicket> privateCredentials = clientSubject.getPrivateCredentials(KerberosTicket.class);
        if (privateCredentials == null || privateCredentials.isEmpty()) {
            return null;
        }
        for (KerberosTicket privateCredential : privateCredentials) {
            if (privateCredential.equals(previousTicket)) continue;
            return privateCredential;
        }
        return null;
    }

    @Override
    public Key getSecretKey(String algorithmURI) throws XMLSecurityException {
        Key key = super.getSecretKey(algorithmURI);
        if (key != null) {
            return key;
        }
        if (this.secretKey == null) {
            this.getTGT();
        }
        byte[] sk = this.secretKey.getEncoded();
        key = KeyUtils.prepareSecretKey(algorithmURI, sk);
        this.setSecretKey(algorithmURI, key);
        return key;
    }

    public byte[] getTicket() throws XMLSecurityException {
        if (this.ticket == null) {
            this.getTGT();
        }
        return this.ticket;
    }
}

