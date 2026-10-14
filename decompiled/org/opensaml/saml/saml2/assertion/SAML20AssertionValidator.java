/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.assertion;

import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.ConditionValidator;
import org.opensaml.saml.saml2.assertion.StatementValidator;
import org.opensaml.saml.saml2.assertion.SubjectConfirmationValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Condition;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignaturePrevalidator;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class SAML20AssertionValidator {
    public static final long DEFAULT_CLOCK_SKEW = 300000L;
    private final Logger log = LoggerFactory.getLogger(SAML20AssertionValidator.class);
    private LazyMap<QName, ConditionValidator> conditionValidators = new LazyMap();
    private LazyMap<String, SubjectConfirmationValidator> subjectConfirmationValidators;
    private LazyMap<QName, StatementValidator> statementValidators;
    private SignatureTrustEngine trustEngine;
    private SignaturePrevalidator signaturePrevalidator;

    public SAML20AssertionValidator(@Nullable Collection<ConditionValidator> newConditionValidators, @Nullable Collection<SubjectConfirmationValidator> newConfirmationValidators, @Nullable Collection<StatementValidator> newStatementValidators, @Nullable SignatureTrustEngine newTrustEngine, @Nullable SignaturePrevalidator newSignaturePrevalidator) {
        if (newConditionValidators != null) {
            for (ConditionValidator conditionValidator : newConditionValidators) {
                if (conditionValidator == null) continue;
                this.conditionValidators.put(conditionValidator.getServicedCondition(), conditionValidator);
            }
        }
        this.subjectConfirmationValidators = new LazyMap();
        if (newConfirmationValidators != null) {
            for (SubjectConfirmationValidator subjectConfirmationValidator : newConfirmationValidators) {
                if (subjectConfirmationValidator == null) continue;
                this.subjectConfirmationValidators.put(subjectConfirmationValidator.getServicedMethod(), subjectConfirmationValidator);
            }
        }
        this.statementValidators = new LazyMap();
        if (newStatementValidators != null) {
            for (StatementValidator statementValidator : newStatementValidators) {
                if (statementValidator == null) continue;
                this.statementValidators.put(statementValidator.getServicedStatement(), statementValidator);
            }
        }
        this.trustEngine = newTrustEngine;
        this.signaturePrevalidator = newSignaturePrevalidator;
    }

    public static long getClockSkew(@Nonnull ValidationContext context) {
        long clockSkew = 300000L;
        if (context.getStaticParameters().containsKey("saml2.ClockSkew")) {
            try {
                clockSkew = (Long)context.getStaticParameters().get("saml2.ClockSkew");
                if (clockSkew < 1L) {
                    clockSkew = 300000L;
                }
            }
            catch (ClassCastException e) {
                clockSkew = 300000L;
            }
        }
        return clockSkew;
    }

    @Nonnull
    public ValidationResult validate(@Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        this.log(assertion, context);
        ValidationResult result = this.validateVersion(assertion, context);
        if (result != ValidationResult.VALID) {
            return result;
        }
        result = this.validateSignature(assertion, context);
        if (result != ValidationResult.VALID) {
            return result;
        }
        result = this.validateConditions(assertion, context);
        if (result != ValidationResult.VALID) {
            return result;
        }
        result = this.validateSubjectConfirmation(assertion, context);
        if (result != ValidationResult.VALID) {
            return result;
        }
        return this.validateStatements(assertion, context);
    }

    protected void log(@Nonnull Assertion assertion, @Nonnull ValidationContext context) {
        if (this.log.isTraceEnabled()) {
            try {
                Element dom = XMLObjectSupport.marshall(assertion);
                this.log.trace("SAML 2 Assertion being validated:\n{}", (Object)SerializeSupport.prettyPrintXML(dom));
            }
            catch (MarshallingException e) {
                this.log.error("Unable to marshall SAML 2 Assertion for logging purposes", e);
            }
            this.log.trace("SAML 2 Assertion ValidationContext - static parameters: {}", (Object)context.getStaticParameters());
            this.log.trace("SAML 2 Assertion ValidationContext - dynamic parameters: {}", (Object)context.getDynamicParameters());
        }
    }

    @Nonnull
    protected ValidationResult validateVersion(@Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        if (assertion.getVersion() != SAMLVersion.VERSION_20) {
            context.setValidationFailureMessage(String.format("Assertion '%s' is not a SAML 2.0 version Assertion", assertion.getID()));
            return ValidationResult.INVALID;
        }
        return ValidationResult.VALID;
    }

    @Nonnull
    protected ValidationResult validateSignature(@Nonnull Assertion token, @Nonnull ValidationContext context) throws AssertionValidationException {
        Boolean signatureRequired = (Boolean)context.getStaticParameters().get("saml2.SignatureRequired");
        if (signatureRequired == null) {
            signatureRequired = Boolean.TRUE;
        }
        if (!token.isSigned()) {
            if (signatureRequired.booleanValue()) {
                context.setValidationFailureMessage("Assertion was required to be signed, but was not");
                return ValidationResult.INVALID;
            }
            this.log.debug("Assertion was not required to be signed, and was not signed.  Skipping further signature evaluation");
            return ValidationResult.VALID;
        }
        if (this.trustEngine == null) {
            this.log.warn("Signature validation was necessary, but no signature trust engine was available");
            context.setValidationFailureMessage("Assertion signature could not be evaluated due to internal error");
            return ValidationResult.INDETERMINATE;
        }
        return this.performSignatureValidation(token, context);
    }

    @Nonnull
    protected ValidationResult performSignatureValidation(@Nonnull Assertion token, @Nonnull ValidationContext context) throws AssertionValidationException {
        Signature signature = token.getSignature();
        String tokenIssuer = null;
        if (token.getIssuer() != null) {
            tokenIssuer = token.getIssuer().getValue();
        }
        this.log.debug("Attempting signature validation on Assertion '{}' from Issuer '{}'", (Object)token.getID(), (Object)tokenIssuer);
        try {
            this.signaturePrevalidator.validate(signature);
        }
        catch (SignatureException e) {
            String msg = String.format("Assertion Signature failed pre-validation: %s", e.getMessage());
            this.log.warn(msg);
            context.setValidationFailureMessage(msg);
            return ValidationResult.INVALID;
        }
        CriteriaSet criteriaSet = this.getSignatureValidationCriteriaSet(token, context);
        try {
            if (this.trustEngine.validate(signature, criteriaSet)) {
                this.log.debug("Validation of signature of Assertion '{}' from Issuer '{}' was successful", (Object)token.getID(), (Object)tokenIssuer);
                return ValidationResult.VALID;
            }
            String msg = String.format("Signature of Assertion '%s' from Issuer '%s' was not valid", token.getID(), tokenIssuer);
            this.log.warn(msg);
            context.setValidationFailureMessage(msg);
            return ValidationResult.INVALID;
        }
        catch (SecurityException e) {
            String msg = String.format("A problem was encountered evaluating the signature over Assertion with ID '%s': %s", token.getID(), e.getMessage());
            this.log.warn(msg);
            context.setValidationFailureMessage(msg);
            return ValidationResult.INDETERMINATE;
        }
    }

    @Nonnull
    protected CriteriaSet getSignatureValidationCriteriaSet(@Nonnull Assertion token, @Nonnull ValidationContext context) {
        CriteriaSet criteriaSet = (CriteriaSet)context.getStaticParameters().get("saml2.SignatureValidationCriteriaSet");
        if (criteriaSet == null) {
            criteriaSet = new CriteriaSet();
        }
        if (!criteriaSet.contains(EntityIdCriterion.class)) {
            String issuer = null;
            if (token.getIssuer() != null) {
                issuer = StringSupport.trimOrNull(token.getIssuer().getValue());
            }
            if (issuer != null) {
                criteriaSet.add(new EntityIdCriterion(issuer));
            }
        }
        if (!criteriaSet.contains(UsageCriterion.class)) {
            criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
        }
        return criteriaSet;
    }

    @Nonnull
    protected ValidationResult validateConditions(@Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        Conditions conditions = assertion.getConditions();
        if (conditions == null) {
            this.log.debug("Assertion contained no Conditions element");
            return ValidationResult.VALID;
        }
        ValidationResult timeboundsResult = this.validateConditionsTimeBounds(assertion, context);
        if (timeboundsResult != ValidationResult.VALID) {
            return timeboundsResult;
        }
        for (Condition condition : conditions.getConditions()) {
            ConditionValidator validator = this.conditionValidators.get(condition.getElementQName());
            if (validator == null && condition.getSchemaType() != null) {
                validator = this.conditionValidators.get(condition.getSchemaType());
            }
            if (validator == null) {
                String msg = String.format("Unknown Condition '%s' of type '%s' in assertion '%s'", condition.getElementQName(), condition.getSchemaType(), assertion.getID());
                this.log.debug(msg);
                context.setValidationFailureMessage(msg);
                return ValidationResult.INDETERMINATE;
            }
            if (validator.validate(condition, assertion, context) == ValidationResult.VALID) continue;
            String msg = String.format("Condition '%s' of type '%s' in assertion '%s' was not valid.", condition.getElementQName(), condition.getSchemaType(), assertion.getID());
            if (context.getValidationFailureMessage() != null) {
                msg = msg + ": " + context.getValidationFailureMessage();
            }
            this.log.debug(msg);
            context.setValidationFailureMessage(msg);
            return ValidationResult.INVALID;
        }
        return ValidationResult.VALID;
    }

    @Nonnull
    protected ValidationResult validateConditionsTimeBounds(@Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        Conditions conditions = assertion.getConditions();
        if (conditions == null) {
            return ValidationResult.VALID;
        }
        DateTime now = new DateTime(ISOChronology.getInstanceUTC());
        long clockSkew = SAML20AssertionValidator.getClockSkew(context);
        DateTime notBefore = conditions.getNotBefore();
        this.log.debug("Evaluating Conditions NotBefore '{}' against 'skewed now' time '{}'", (Object)notBefore, (Object)now.plus(clockSkew));
        if (notBefore != null && notBefore.isAfter(now.plus(clockSkew))) {
            context.setValidationFailureMessage(String.format("Assertion '%s' with NotBefore condition of '%s' is not yet valid", assertion.getID(), notBefore));
            return ValidationResult.INVALID;
        }
        DateTime notOnOrAfter = conditions.getNotOnOrAfter();
        this.log.debug("Evaluating Conditions NotOnOrAfter '{}' against 'skewed now' time '{}'", (Object)notOnOrAfter, (Object)now.minus(clockSkew));
        if (notOnOrAfter != null && notOnOrAfter.isBefore(now.minus(clockSkew))) {
            context.setValidationFailureMessage(String.format("Assertion '%s' with NotOnOrAfter condition of '%s' is no longer valid", assertion.getID(), notOnOrAfter));
            return ValidationResult.INVALID;
        }
        return ValidationResult.VALID;
    }

    @Nonnull
    protected ValidationResult validateSubjectConfirmation(@Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        Subject assertionSubject = assertion.getSubject();
        if (assertionSubject == null) {
            this.log.debug("Assertion contains no Subject, skipping subject confirmation");
            return ValidationResult.VALID;
        }
        List<SubjectConfirmation> confirmations = assertionSubject.getSubjectConfirmations();
        if (confirmations == null || confirmations.isEmpty()) {
            this.log.debug("Assertion contains no SubjectConfirmations, skipping subject confirmation");
            return ValidationResult.VALID;
        }
        this.log.debug("Assertion contains at least 1 SubjectConfirmation, proceeding with subject confirmation");
        for (SubjectConfirmation confirmation : confirmations) {
            SubjectConfirmationValidator validator = this.subjectConfirmationValidators.get(confirmation.getMethod());
            if (validator == null) continue;
            try {
                if (validator.validate(confirmation, assertion, context) != ValidationResult.VALID) continue;
                context.getDynamicParameters().put("saml2.ConfirmedSubjectConfirmation", confirmation);
                return ValidationResult.VALID;
            }
            catch (AssertionValidationException e) {
                this.log.warn("Error while executing subject confirmation validation " + validator.getClass().getName(), e);
            }
        }
        String msg = String.format("No subject confirmation methods were met for assertion with ID '%s'", assertion.getID());
        this.log.debug(msg);
        context.setValidationFailureMessage(msg);
        return ValidationResult.INVALID;
    }

    @Nonnull
    protected ValidationResult validateStatements(@Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
        List<Statement> statements = assertion.getStatements();
        if (statements == null || statements.isEmpty()) {
            return ValidationResult.VALID;
        }
        for (Statement statement : statements) {
            ValidationResult result;
            StatementValidator validator = this.statementValidators.get(statement.getElementQName());
            if (validator == null && statement.getSchemaType() != null) {
                validator = this.statementValidators.get(statement.getSchemaType());
            }
            if (validator == null || (result = validator.validate(statement, assertion, context)) == ValidationResult.VALID) continue;
            return result;
        }
        return ValidationResult.VALID;
    }
}

