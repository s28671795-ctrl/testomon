/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.securityToken;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.kerberos.KerberosContextAndServiceNameCallback;
import org.apache.wss4j.common.kerberos.KerberosServiceContext;
import org.apache.wss4j.common.kerberos.KerberosServiceExceptionAction;
import org.apache.wss4j.common.kerberos.KerberosTokenDecoder;
import org.apache.wss4j.common.kerberos.KerberosTokenDecoderException;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.securityToken.KerberosServiceSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class KerberosServiceSecurityTokenImpl
extends AbstractInboundSecurityToken
implements KerberosServiceSecurityToken {
    private CallbackHandler callbackHandler;
    private byte[] binaryContent;
    private String kerberosTokenValueType;
    private KerberosTokenDecoder kerberosTokenDecoder;
    private Subject subject;
    private Principal principal;
    private byte[] sessionKey;

    public KerberosServiceSecurityTokenImpl(WSInboundSecurityContext wsInboundSecurityContext, CallbackHandler callbackHandler, byte[] binaryContent, String kerberosTokenValueType, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier) {
        super(wsInboundSecurityContext, id, keyIdentifier, true);
        this.callbackHandler = callbackHandler;
        this.binaryContent = binaryContent;
        this.kerberosTokenValueType = kerberosTokenValueType;
    }

    @Override
    public boolean isAsymmetric() throws XMLSecurityException {
        return false;
    }

    @Override
    public SecurityTokenConstants.TokenType getTokenType() {
        return WSSecurityTokenConstants.KERBEROS_TOKEN;
    }

    protected byte[] getTGTSessionKey() throws WSSecurityException {
        if (this.sessionKey != null) {
            return this.sessionKey;
        }
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
            this.subject = loginContext.getSubject();
            String service2 = contextAndServiceNameCallback.getServiceName();
            if (service2 == null) {
                Set<Principal> principals = this.subject.getPrincipals();
                if (principals.isEmpty()) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "kerberosLoginError", new Object[]{"No Client principals found after login"});
                }
                service2 = principals.iterator().next().getName();
            }
            KerberosServiceExceptionAction action = new KerberosServiceExceptionAction(this.binaryContent, service2, contextAndServiceNameCallback.isUsernameServiceNameForm(), false);
            KerberosServiceContext krbServiceCtx = null;
            try {
                krbServiceCtx = Subject.doAs(this.subject, action);
            }
            catch (PrivilegedActionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof WSSecurityException) {
                    throw (WSSecurityException)cause;
                }
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception(cause), "kerberosTicketValidationError");
            }
            this.principal = krbServiceCtx.getPrincipal();
            Key key = krbServiceCtx.getSessionKey();
            if (key != null) {
                this.sessionKey = key.getEncoded();
            } else if (this.kerberosTokenDecoder != null) {
                this.kerberosTokenDecoder.clear();
                this.kerberosTokenDecoder.setToken(this.binaryContent);
                this.kerberosTokenDecoder.setSubject(this.subject);
                this.sessionKey = this.kerberosTokenDecoder.getSessionKey();
            }
            return this.sessionKey;
        }
        catch (IOException | UnsupportedCallbackException | LoginException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e);
        }
        catch (KerberosTokenDecoderException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, (Exception)e);
        }
    }

    @Override
    protected Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
        Key key = this.getSecretKey().get(algorithmURI);
        if (key != null) {
            return key;
        }
        byte[] sk = this.getTGTSessionKey();
        key = KeyUtils.prepareSecretKey(algorithmURI, sk);
        this.setSecretKey(algorithmURI, key);
        return key;
    }

    @Override
    public byte[] getBinaryContent() {
        return this.binaryContent;
    }

    @Override
    public String getKerberosTokenValueType() {
        return this.kerberosTokenValueType;
    }

    @Override
    public Subject getSubject() throws WSSecurityException {
        return this.subject;
    }

    @Override
    public Principal getPrincipal() throws WSSecurityException {
        return this.principal;
    }

    public KerberosTokenDecoder getKerberosTokenDecoder() {
        return this.kerberosTokenDecoder;
    }

    public void setKerberosTokenDecoder(KerberosTokenDecoder kerberosTokenDecoder) {
        this.kerberosTokenDecoder = kerberosTokenDecoder;
    }
}

