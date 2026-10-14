/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.kerberos;

import javax.security.auth.callback.Callback;

public class KerberosContextAndServiceNameCallback
implements Callback {
    private String contextName;
    private String serviceName;
    private boolean isUsernameServiceNameForm;
    private boolean requestCredDeleg;

    public String getContextName() {
        return this.contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setIsUsernameServiceNameForm(boolean isUsernameServiceNameForm) {
        this.isUsernameServiceNameForm = isUsernameServiceNameForm;
    }

    public boolean isUsernameServiceNameForm() {
        return this.isUsernameServiceNameForm;
    }

    public boolean isRequestCredDeleg() {
        return this.requestCredDeleg;
    }

    public void setRequestCredDeleg(boolean requestCredDeleg) {
        this.requestCredDeleg = requestCredDeleg;
    }
}

