/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.kerberos;

import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.security.kerberos.KerberosClient;
import org.apache.wss4j.common.ext.WSSecurityException;

public final class KerberosUtils {
    private KerberosUtils() {
    }

    public static KerberosClient getClient(Message message, String type) throws WSSecurityException {
        KerberosClient client = (KerberosClient)message.getContextualProperty("ws-security.kerberos.client");
        if (client == null) {
            client = new KerberosClient();
            String jaasContext = (String)message.getContextualProperty("ws-security.kerberos.jaas.context");
            String kerberosSpn = (String)message.getContextualProperty("ws-security.kerberos.spn");
            try {
                CallbackHandler callbackHandler = SecurityUtils.getCallbackHandler(SecurityUtils.getSecurityPropertyValue("security.callback-handler", message));
                client.setCallbackHandler(callbackHandler);
            }
            catch (Exception ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
            }
            boolean useCredentialDelegation = MessageUtils.getContextualBoolean(message, "ws-security.kerberos.use.credential.delegation", false);
            boolean isInServiceNameForm = MessageUtils.getContextualBoolean(message, "ws-security.kerberos.is.username.in.servicename.form", false);
            boolean requestCredentialDelegation = MessageUtils.getContextualBoolean(message, "ws-security.kerberos.request.credential.delegation", false);
            client.setContextName(jaasContext);
            client.setServiceName(kerberosSpn);
            client.setUseDelegatedCredential(useCredentialDelegation);
            client.setUsernameServiceNameForm(isInServiceNameForm);
            client.setRequestCredentialDelegation(requestCredentialDelegation);
        }
        return client;
    }
}

