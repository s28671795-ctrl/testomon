/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.soap.client;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.soap.client.SOAPClient;

public class SOAPClientContext
extends BaseContext {
    private SOAPClient.SOAPRequestParameters requestParameters;

    @Nullable
    public SOAPClient.SOAPRequestParameters getSOAPRequestParameters() {
        return this.requestParameters;
    }

    public void setSOAPRequestParameters(@Nullable SOAPClient.SOAPRequestParameters parameters) {
        this.requestParameters = parameters;
    }
}

