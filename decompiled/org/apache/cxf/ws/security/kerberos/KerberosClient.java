/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.kerberos;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.Bus;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.Configurable;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.xml.security.utils.XMLUtils;
import org.ietf.jgss.GSSCredential;

public class KerberosClient
implements Configurable {
    private static final Logger LOG = LogUtils.getL7dLogger(KerberosClient.class);
    String name = "default.kerberos-client";
    private String serviceName;
    private CallbackHandler callbackHandler;
    private String contextName;
    private WSSConfig wssConfig = WSSConfig.getNewInstance();
    private boolean requestCredentialDelegation;
    private boolean isUsernameServiceNameForm;
    private boolean useDelegatedCredential;

    @Deprecated
    public KerberosClient(Bus b) {
    }

    public KerberosClient() {
    }

    @Override
    public String getBeanName() {
        return this.name;
    }

    public String getContextName() {
        return this.contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getJaasLoginModuleName() {
        return this.contextName;
    }

    public void setJaasLoginModuleName(String jaasLoginModuleName) {
        this.contextName = jaasLoginModuleName;
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

    public SecurityToken requestSecurityToken() throws Exception {
        Object obj;
        Message message = PhaseInterceptorChain.getCurrentMessage();
        GSSCredential delegatedCredential = null;
        if (message != null && this.useDelegatedCredential && (obj = message.getContextualProperty("ws-security.delegated.credential")) instanceof GSSCredential) {
            delegatedCredential = (GSSCredential)obj;
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Requesting Kerberos ticket for " + this.serviceName + " using JAAS Login Module: " + this.getContextName());
        }
        KerberosSecurity bst = this.createKerberosSecurity();
        bst.retrieveServiceTicket(this.getContextName(), this.callbackHandler, this.serviceName, this.isUsernameServiceNameForm, this.requestCredentialDelegation, delegatedCredential);
        bst.addWSUNamespace();
        bst.setID(this.wssConfig.getIdAllocator().createSecureId("BST-", bst));
        bst.addWSUNamespace();
        SecurityToken token = new SecurityToken(bst.getID());
        token.setToken(bst.getElement());
        token.setWsuId(bst.getID());
        SecretKey secretKey = bst.getSecretKey();
        if (secretKey != null) {
            token.setKey(secretKey);
            token.setSecret(secretKey.getEncoded());
        }
        String sha1 = XMLUtils.encodeToString(KeyUtils.generateDigest(bst.getToken()));
        token.setSHA1(sha1);
        token.setTokenType(bst.getValueType());
        return token;
    }

    protected KerberosSecurity createKerberosSecurity() {
        return new KerberosSecurity(DOMUtils.getEmptyDocument());
    }

    public boolean isUsernameServiceNameForm() {
        return this.isUsernameServiceNameForm;
    }

    public void setUsernameServiceNameForm(boolean usernameServiceNameForm) {
        this.isUsernameServiceNameForm = usernameServiceNameForm;
    }

    public boolean isRequestCredentialDelegation() {
        return this.requestCredentialDelegation;
    }

    public void setRequestCredentialDelegation(boolean requestCredentialDelegation) {
        this.requestCredentialDelegation = requestCredentialDelegation;
    }

    public boolean isUseDelegatedCredential() {
        return this.useDelegatedCredential;
    }

    public void setUseDelegatedCredential(boolean useDelegatedCredential) {
        this.useDelegatedCredential = useDelegatedCredential;
    }
}

