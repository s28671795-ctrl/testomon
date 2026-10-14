/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import org.apache.cxf.validation.AbstractBeanValidationInterceptor;

public class BeanValidationInInterceptor
extends AbstractBeanValidationInterceptor {
    public BeanValidationInInterceptor() {
        super("pre-invoke");
    }

    public BeanValidationInInterceptor(String phase) {
        super(phase);
    }
}

