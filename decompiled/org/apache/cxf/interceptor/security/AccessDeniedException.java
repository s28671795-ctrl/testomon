/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

public class AccessDeniedException
extends SecurityException {
    private static final long serialVersionUID = 9169091575394965388L;

    public AccessDeniedException(String reason) {
        super(reason);
    }
}

