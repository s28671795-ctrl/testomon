/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.assertion.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.SAML20AssertionValidator;
import org.opensaml.saml.saml2.assertion.SubjectConfirmationValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public abstract class AbstractSubjectConfirmationValidator
implements SubjectConfirmationValidator {
    private Logger log = LoggerFactory.getLogger(AbstractSubjectConfirmationValidator.class);

    @Override
    @Nonnull
    public ValidationResult validate(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        if (confirmation.getSubjectConfirmationData() != null) {
            ValidationResult result = this.validateNotBefore(confirmation, assertion, context);
            if (result != ValidationResult.VALID) {
                return result;
            }
            result = this.validateNotOnOrAfter(confirmation, assertion, context);
            if (result != ValidationResult.VALID) {
                return result;
            }
            result = this.validateRecipient(confirmation, assertion, context);
            if (result != ValidationResult.VALID) {
                return result;
            }
            result = this.validateAddress(confirmation, assertion, context);
            if (result != ValidationResult.VALID) {
                return result;
            }
        }
        return this.doValidate(confirmation, assertion, context);
    }

    @Nonnull
    protected ValidationResult validateNotBefore(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        DateTime skewedNow = new DateTime(ISOChronology.getInstanceUTC()).plus(SAML20AssertionValidator.getClockSkew(context));
        DateTime notBefore = confirmation.getSubjectConfirmationData().getNotBefore();
        this.log.debug("Evaluating SubjectConfirmationData NotBefore '{}' against 'skewed now' time '{}'", (Object)notBefore, (Object)skewedNow);
        if (notBefore != null && notBefore.isAfter(skewedNow)) {
            context.setValidationFailureMessage(String.format("Subject confirmation, in assertion '%s', with NotBefore condition of '%s' is not yet valid", assertion.getID(), notBefore));
            return ValidationResult.INVALID;
        }
        return ValidationResult.VALID;
    }

    @Nonnull
    protected ValidationResult validateNotOnOrAfter(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        DateTime skewedNow = new DateTime(ISOChronology.getInstanceUTC()).minus(SAML20AssertionValidator.getClockSkew(context));
        DateTime notOnOrAfter = confirmation.getSubjectConfirmationData().getNotOnOrAfter();
        this.log.debug("Evaluating SubjectConfirmationData NotOnOrAfter '{}' against 'skewed now' time '{}'", (Object)notOnOrAfter, (Object)skewedNow);
        if (notOnOrAfter != null && notOnOrAfter.isBefore(skewedNow)) {
            context.setValidationFailureMessage(String.format("Subject confirmation, in assertion '%s', with NotOnOrAfter condition of '%s' is no longer valid", assertion.getID(), notOnOrAfter));
            return ValidationResult.INVALID;
        }
        return ValidationResult.VALID;
    }

    @Nonnull
    protected ValidationResult validateRecipient(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        Set validRecipients;
        String recipient = StringSupport.trimOrNull(confirmation.getSubjectConfirmationData().getRecipient());
        if (recipient == null) {
            return ValidationResult.VALID;
        }
        this.log.debug("Evaluating SubjectConfirmationData@Recipient of : {}", (Object)recipient);
        try {
            validRecipients = (Set)context.getStaticParameters().get("saml2.SubjectConfirmation.ValidRecipients");
        }
        catch (ClassCastException e) {
            this.log.warn("The value of the static validation parameter '{}' was not java.util.Set<String>", (Object)"saml2.SubjectConfirmation.ValidRecipients");
            context.setValidationFailureMessage("Unable to determine list of valid subject confirmation recipient endpoints");
            return ValidationResult.INDETERMINATE;
        }
        if (validRecipients == null || validRecipients.isEmpty()) {
            this.log.warn("Set of valid recipient URI's was not available from the validation context, unable to evaluate SubjectConfirmationData@Recipient");
            context.setValidationFailureMessage("Unable to determine list of valid subject confirmation recipient endpoints");
            return ValidationResult.INDETERMINATE;
        }
        if (validRecipients.contains(recipient)) {
            this.log.debug("Matched valid recipient: {}", (Object)recipient);
            return ValidationResult.VALID;
        }
        this.log.debug("Failed to match SubjectConfirmationData@Recipient to any supplied valid recipients: {}", (Object)validRecipients);
        context.setValidationFailureMessage(String.format("Subject confirmation recipient for asertion '%s' did not match any valid recipients", assertion.getID()));
        return ValidationResult.INVALID;
    }

    @Nonnull
    protected ValidationResult validateAddress(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        Set validAddresses;
        InetAddress[] confirmingAddresses;
        String address = StringSupport.trimOrNull(confirmation.getSubjectConfirmationData().getAddress());
        if (address == null) {
            return ValidationResult.VALID;
        }
        this.log.debug("Evaluating SubjectConfirmationData@Address of : {}", (Object)address);
        try {
            confirmingAddresses = InetAddress.getAllByName(address);
        }
        catch (UnknownHostException e) {
            this.log.warn("The subject confirmation address '{}' in assetion '{}' can not be resolved to a valid set of IP address(s)", (Object)address, (Object)assertion.getID());
            context.setValidationFailureMessage(String.format("Subject confirmation address '%s' is not resolvable hostname or IP address", address));
            return ValidationResult.INDETERMINATE;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("SubjectConfirmationData/@Address was resolved to addresses: {}", (Object)Arrays.asList(confirmingAddresses));
        }
        try {
            validAddresses = (Set)context.getStaticParameters().get("saml2.SubjectConfirmation.ValidAddresses");
        }
        catch (ClassCastException e) {
            this.log.warn("The value of the static validation parameter '{}' was not java.util.Set<InetAddress>", (Object)"saml2.SubjectConfirmation.ValidAddresses");
            context.setValidationFailureMessage("Unable to determine list of valid subject confirmation addresses");
            return ValidationResult.INDETERMINATE;
        }
        if (validAddresses == null || validAddresses.isEmpty()) {
            this.log.warn("Set of valid addresses was not available from the validation context, unable to evaluate SubjectConfirmationData@Address");
            context.setValidationFailureMessage("Unable to determine list of valid subject confirmation addresses");
            return ValidationResult.INDETERMINATE;
        }
        for (InetAddress confirmingAddress : confirmingAddresses) {
            if (!validAddresses.contains(confirmingAddress)) continue;
            this.log.debug("Matched SubjectConfirmationData address '{}' to valid address", (Object)confirmingAddress.getHostAddress());
            return ValidationResult.VALID;
        }
        this.log.debug("Failed to match SubjectConfirmationData@Address to any supplied valid addresses", (Object)validAddresses);
        context.setValidationFailureMessage(String.format("Subject confirmation address for asertion '%s' did not match any valid addresses", assertion.getID()));
        return ValidationResult.INVALID;
    }

    @Nonnull
    protected abstract ValidationResult doValidate(@Nonnull SubjectConfirmation var1, @Nonnull Assertion var2, @Nonnull ValidationContext var3) throws AssertionValidationException;
}

