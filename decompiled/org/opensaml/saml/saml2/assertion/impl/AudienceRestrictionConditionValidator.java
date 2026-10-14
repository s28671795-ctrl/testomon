/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.assertion.impl;

import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.ConditionValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public class AudienceRestrictionConditionValidator
implements ConditionValidator {
    private Logger log = LoggerFactory.getLogger(AudienceRestrictionConditionValidator.class);

    @Override
    @Nonnull
    public QName getServicedCondition() {
        return AudienceRestriction.DEFAULT_ELEMENT_NAME;
    }

    @Override
    @Nonnull
    public ValidationResult validate(@Nonnull Condition condition, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        Set validAudiences;
        if (!(condition instanceof AudienceRestriction)) {
            this.log.warn("Condition '{}' of type '{}' in assertion '{}' was not an '{}' condition.  Unable to process.", condition.getElementQName(), condition.getSchemaType(), assertion.getID(), this.getServicedCondition());
            return ValidationResult.INDETERMINATE;
        }
        try {
            validAudiences = (Set)context.getStaticParameters().get("saml2.Conditions.ValidAudiences");
        }
        catch (ClassCastException e) {
            this.log.warn("The value of the static validation parameter '{}' was not java.util.Set<String>", (Object)"saml2.Conditions.ValidAudiences");
            context.setValidationFailureMessage("Unable to determine list of valid audiences");
            return ValidationResult.INDETERMINATE;
        }
        if (validAudiences == null || validAudiences.isEmpty()) {
            this.log.warn("Set of valid audiences was not available from the validation context, unable to evaluate AudienceRestriction Condition");
            context.setValidationFailureMessage("Unable to determine list of valid audiences");
            return ValidationResult.INDETERMINATE;
        }
        this.log.debug("Evaluating the Assertion's AudienceRestriction/Audience values against the list of valid audiences: {}", (Object)validAudiences.toString());
        AudienceRestriction audienceRestriction = (AudienceRestriction)condition;
        List<Audience> audiences = audienceRestriction.getAudiences();
        if (audiences == null || audiences.isEmpty()) {
            context.setValidationFailureMessage(String.format("'%s' condition in assertion '%s' is malformed as it does not contain any audiences", this.getServicedCondition(), assertion.getID()));
            return ValidationResult.INVALID;
        }
        for (Audience audience : audiences) {
            String audienceURI = StringSupport.trimOrNull(audience.getAudienceURI());
            if (!validAudiences.contains(audienceURI)) continue;
            this.log.debug("Matched valid audience: {}", (Object)audienceURI);
            return ValidationResult.VALID;
        }
        String msg = String.format("None of the audiences within Assertion '%s' matched the list of valid audiances", assertion.getID());
        this.log.debug(msg);
        context.setValidationFailureMessage(msg);
        return ValidationResult.INVALID;
    }
}

