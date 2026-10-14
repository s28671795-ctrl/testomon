/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity.messaging;

public interface Token<TokenType> {
    public TokenType getWrappedToken();

    public ValidationStatus getValidationStatus();

    public static enum ValidationStatus {
        VALID,
        INVALID,
        INDETERMINATE,
        VALIDATION_NOT_ATTEMPTED;

    }
}

