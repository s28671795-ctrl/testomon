/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity.messaging;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.soap.wssecurity.messaging.Token;

public abstract class AbstractToken<TokenType>
implements Token<TokenType> {
    private TokenType wrappedToken;
    private Token.ValidationStatus validationStatus;

    protected AbstractToken(@Nonnull TokenType token) {
        this.wrappedToken = Constraint.isNotNull(token, "Wrapped token may not be null");
        this.validationStatus = Token.ValidationStatus.VALIDATION_NOT_ATTEMPTED;
    }

    @Override
    @Nonnull
    public TokenType getWrappedToken() {
        return this.wrappedToken;
    }

    @Override
    @Nonnull
    public Token.ValidationStatus getValidationStatus() {
        return this.validationStatus;
    }

    public void setValidationStatus(@Nonnull Token.ValidationStatus status) {
        this.validationStatus = Constraint.isNotNull(status, "Validation status may not be null");
    }
}

