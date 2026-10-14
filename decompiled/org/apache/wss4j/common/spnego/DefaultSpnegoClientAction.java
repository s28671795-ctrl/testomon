/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.spnego;

import org.apache.wss4j.common.spnego.SpnegoClientAction;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSpnegoClientAction
implements SpnegoClientAction {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSpnegoClientAction.class);
    private String serviceName;
    private GSSContext secContext;
    private boolean mutualAuth;
    private boolean isUsernameServiceNameForm;

    @Override
    public void setMutualAuth(boolean mutualAuthentication) {
        this.mutualAuth = mutualAuthentication;
    }

    @Override
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public byte[] run() {
        try {
            GSSManager gssManager = GSSManager.getInstance();
            Oid oid = new Oid("1.3.6.1.5.5.2");
            GSSName gssService = gssManager.createName(this.serviceName, this.isUsernameServiceNameForm ? GSSName.NT_USER_NAME : GSSName.NT_HOSTBASED_SERVICE);
            this.secContext = gssManager.createContext(gssService, oid, null, 0);
            this.secContext.requestMutualAuth(this.mutualAuth);
            this.secContext.requestCredDeleg(Boolean.FALSE);
            byte[] token = new byte[]{};
            return this.secContext.initSecContext(token, 0, token.length);
        }
        catch (GSSException e) {
            LOG.debug("Error in obtaining a Kerberos token", e);
            return null;
        }
    }

    @Override
    public GSSContext getContext() {
        return this.secContext;
    }

    @Override
    public void setUserNameServiceForm(boolean isUsernameServiceNameForm) {
        this.isUsernameServiceNameForm = isUsernameServiceNameForm;
    }
}

