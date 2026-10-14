/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.messaging.context;

import org.opensaml.saml.common.messaging.context.AbstractSAMLEntityContext;

public abstract class AbstractAuthenticatableSAMLEntityContext
extends AbstractSAMLEntityContext {
    private boolean authenticated;

    public boolean isAuthenticated() {
        return this.authenticated;
    }

    public void setAuthenticated(boolean flag) {
        this.authenticated = flag;
    }
}

