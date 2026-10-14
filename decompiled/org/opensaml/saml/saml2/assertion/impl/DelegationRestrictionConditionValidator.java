/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.assertion.impl;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.ext.saml2delrestrict.DelegationRestrictionType;
import org.opensaml.saml.saml2.assertion.ConditionValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Condition;

@ThreadSafe
public class DelegationRestrictionConditionValidator
implements ConditionValidator {
    @Override
    @Nonnull
    public QName getServicedCondition() {
        return DelegationRestrictionType.TYPE_NAME;
    }

    @Override
    @Nonnull
    public ValidationResult validate(@Nonnull Condition condition, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        if (condition instanceof DelegationRestrictionType || Objects.equals(condition.getSchemaType(), DelegationRestrictionType.TYPE_NAME)) {
            return ValidationResult.VALID;
        }
        return ValidationResult.INDETERMINATE;
    }
}

