/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.assertion.impl;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.impl.AbstractSubjectConfirmationValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.SubjectConfirmation;

@ThreadSafe
public class BearerSubjectConfirmationValidator
extends AbstractSubjectConfirmationValidator {
    @Override
    @Nonnull
    public String getServicedMethod() {
        return "urn:oasis:names:tc:SAML:2.0:cm:bearer";
    }

    @Override
    @Nonnull
    protected ValidationResult doValidate(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        if (Objects.equals(confirmation.getMethod(), "urn:oasis:names:tc:SAML:2.0:cm:bearer")) {
            return ValidationResult.VALID;
        }
        return ValidationResult.INDETERMINATE;
    }
}

