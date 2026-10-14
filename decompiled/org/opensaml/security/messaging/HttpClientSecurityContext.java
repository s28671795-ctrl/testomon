/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.security.messaging;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;

public class HttpClientSecurityContext
extends BaseContext {
    private HttpClientSecurityParameters securityParameters;

    @Nullable
    public HttpClientSecurityParameters getSecurityParameters() {
        return this.securityParameters;
    }

    public void setSecurityParameters(@Nullable HttpClientSecurityParameters parameters) {
        this.securityParameters = parameters;
    }
}

