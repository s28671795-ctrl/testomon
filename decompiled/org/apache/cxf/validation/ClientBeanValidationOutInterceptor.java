/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import org.apache.cxf.validation.AbstractBeanValidationInterceptor;

public class ClientBeanValidationOutInterceptor
extends AbstractBeanValidationInterceptor {
    public ClientBeanValidationOutInterceptor() {
        super("pre-logical");
    }

    public ClientBeanValidationOutInterceptor(String phase) {
        super(phase);
    }
}

