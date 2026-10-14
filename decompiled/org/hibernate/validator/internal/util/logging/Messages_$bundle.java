/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.util.logging;

import java.io.Serializable;
import java.util.Locale;
import org.hibernate.validator.internal.util.logging.Messages;

public class Messages_$bundle
implements Messages,
Serializable {
    private static final long serialVersionUID = 1L;
    public static final Messages_$bundle INSTANCE = new Messages_$bundle();
    private static final Locale LOCALE = Locale.ROOT;

    protected Messages_$bundle() {
    }

    protected Object readResolve() {
        return INSTANCE;
    }

    protected Locale getLoggingLocale() {
        return LOCALE;
    }

    protected String mustNotBeNull0$str() {
        return "must not be null.";
    }

    @Override
    public final String mustNotBeNull() {
        return this.mustNotBeNull0$str();
    }

    protected String mustNotBeNull1$str() {
        return "%s must not be null.";
    }

    @Override
    public final String mustNotBeNull(String parameterName) {
        return String.format(this.getLoggingLocale(), this.mustNotBeNull1$str(), parameterName);
    }

    protected String parameterMustNotBeNull$str() {
        return "The parameter \"%s\" must not be null.";
    }

    @Override
    public final String parameterMustNotBeNull(String parameterName) {
        return String.format(this.getLoggingLocale(), this.parameterMustNotBeNull$str(), parameterName);
    }

    protected String parameterMustNotBeEmpty$str() {
        return "The parameter \"%s\" must not be empty.";
    }

    @Override
    public final String parameterMustNotBeEmpty(String parameterName) {
        return String.format(this.getLoggingLocale(), this.parameterMustNotBeEmpty$str(), parameterName);
    }

    protected String beanTypeCannotBeNull$str() {
        return "The bean type cannot be null.";
    }

    @Override
    public final String beanTypeCannotBeNull() {
        return this.beanTypeCannotBeNull$str();
    }

    protected String propertyPathCannotBeNull$str() {
        return "null is not allowed as property path.";
    }

    @Override
    public final String propertyPathCannotBeNull() {
        return this.propertyPathCannotBeNull$str();
    }

    protected String propertyNameMustNotBeEmpty$str() {
        return "The property name must not be empty.";
    }

    @Override
    public final String propertyNameMustNotBeEmpty() {
        return this.propertyNameMustNotBeEmpty$str();
    }

    protected String groupMustNotBeNull$str() {
        return "null passed as group name.";
    }

    @Override
    public final String groupMustNotBeNull() {
        return this.groupMustNotBeNull$str();
    }

    protected String beanTypeMustNotBeNull$str() {
        return "The bean type must not be null when creating a constraint mapping.";
    }

    @Override
    public final String beanTypeMustNotBeNull() {
        return this.beanTypeMustNotBeNull$str();
    }

    protected String methodNameMustNotBeNull$str() {
        return "The method name must not be null.";
    }

    @Override
    public final String methodNameMustNotBeNull() {
        return this.methodNameMustNotBeNull$str();
    }

    protected String validatedObjectMustNotBeNull$str() {
        return "The object to be validated must not be null.";
    }

    @Override
    public final String validatedObjectMustNotBeNull() {
        return this.validatedObjectMustNotBeNull$str();
    }

    protected String validatedMethodMustNotBeNull$str() {
        return "The method to be validated must not be null.";
    }

    @Override
    public final String validatedMethodMustNotBeNull() {
        return this.validatedMethodMustNotBeNull$str();
    }

    protected String classCannotBeNull$str() {
        return "The class cannot be null.";
    }

    @Override
    public final String classCannotBeNull() {
        return this.classCannotBeNull$str();
    }

    protected String classIsNull$str() {
        return "Class is null.";
    }

    @Override
    public final String classIsNull() {
        return this.classIsNull$str();
    }

    protected String validatedConstructorMustNotBeNull$str() {
        return "The constructor to be validated must not be null.";
    }

    @Override
    public final String validatedConstructorMustNotBeNull() {
        return this.validatedConstructorMustNotBeNull$str();
    }

    protected String validatedParameterArrayMustNotBeNull$str() {
        return "The method parameter array cannot not be null.";
    }

    @Override
    public final String validatedParameterArrayMustNotBeNull() {
        return this.validatedParameterArrayMustNotBeNull$str();
    }

    protected String validatedConstructorCreatedInstanceMustNotBeNull$str() {
        return "The created instance must not be null.";
    }

    @Override
    public final String validatedConstructorCreatedInstanceMustNotBeNull() {
        return this.validatedConstructorCreatedInstanceMustNotBeNull$str();
    }

    protected String inputStreamCannotBeNull$str() {
        return "The input stream for #addMapping() cannot be null.";
    }

    @Override
    public final String inputStreamCannotBeNull() {
        return String.format(this.getLoggingLocale(), this.inputStreamCannotBeNull$str(), new Object[0]);
    }

    protected String constraintOnConstructorOfNonStaticInnerClass$str() {
        return "Constraints on the parameters of constructors of non-static inner classes are not supported if those parameters have a generic type due to JDK bug JDK-5087240.";
    }

    @Override
    public final String constraintOnConstructorOfNonStaticInnerClass() {
        return String.format(this.getLoggingLocale(), this.constraintOnConstructorOfNonStaticInnerClass$str(), new Object[0]);
    }

    protected String parameterizedTypesWithMoreThanOneTypeArgument$str() {
        return "Custom parameterized types with more than one type argument are not supported and will not be checked for type use constraints.";
    }

    @Override
    public final String parameterizedTypesWithMoreThanOneTypeArgument() {
        return String.format(this.getLoggingLocale(), this.parameterizedTypesWithMoreThanOneTypeArgument$str(), new Object[0]);
    }

    protected String unableToUseResourceBundleAggregation$str() {
        return "Hibernate Validator cannot instantiate AggregateResourceBundle.CONTROL. This can happen most notably in a Google App Engine environment or when running Hibernate Validator as Java 9 named module. A PlatformResourceBundleLocator without bundle aggregation was created. This only affects you in case you are using multiple ConstraintDefinitionContributor JARs. ConstraintDefinitionContributors are a Hibernate Validator specific feature. All Jakarta Bean Validation features work as expected. See also https://hibernate.atlassian.net/browse/HV-1023.";
    }

    @Override
    public final String unableToUseResourceBundleAggregation() {
        return String.format(this.getLoggingLocale(), this.unableToUseResourceBundleAggregation$str(), new Object[0]);
    }

    protected String annotationTypeMustNotBeNull$str() {
        return "The annotation type must not be null when creating a constraint definition.";
    }

    @Override
    public final String annotationTypeMustNotBeNull() {
        return this.annotationTypeMustNotBeNull$str();
    }

    protected String annotationTypeMustBeAnnotatedWithConstraint$str() {
        return "The annotation type must be annotated with @javax.validation.Constraint when creating a constraint definition.";
    }

    @Override
    public final String annotationTypeMustBeAnnotatedWithConstraint() {
        return this.annotationTypeMustBeAnnotatedWithConstraint$str();
    }
}

