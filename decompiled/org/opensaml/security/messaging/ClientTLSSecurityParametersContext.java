/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.security.messaging;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.security.x509.tls.ClientTLSValidationParameters;

public class ClientTLSSecurityParametersContext
extends BaseContext {
    @Nullable
    private ClientTLSValidationParameters validationParameters;
    private boolean evaluateClientCertificate = true;

    public boolean isEvaluateClientCertificate() {
        return this.evaluateClientCertificate;
    }

    public void setEvaluateClientCertificate(boolean flag) {
        this.evaluateClientCertificate = flag;
    }

    @Nullable
    public ClientTLSValidationParameters getValidationParameters() {
        return this.validationParameters;
    }

    public void setValidationParameters(@Nullable ClientTLSValidationParameters params) {
        this.validationParameters = params;
    }
}

