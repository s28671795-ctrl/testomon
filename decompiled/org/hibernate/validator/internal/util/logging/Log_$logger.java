/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.util.logging;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ServiceConfigurationError;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import javax.validation.ClockProvider;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintTarget;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ElementKind;
import javax.validation.GroupDefinitionException;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;
import javax.validation.spi.ValidationProvider;
import javax.validation.valueextraction.ValueExtractor;
import javax.validation.valueextraction.ValueExtractorDeclarationException;
import javax.validation.valueextraction.ValueExtractorDefinitionException;
import javax.xml.stream.XMLStreamException;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanConstructor;
import org.hibernate.validator.internal.properties.javabean.JavaBeanMethod;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.formatter.ArrayOfClassesObjectFormatter;
import org.hibernate.validator.internal.util.logging.formatter.ClassObjectFormatter;
import org.hibernate.validator.internal.util.logging.formatter.CollectionOfClassesObjectFormatter;
import org.hibernate.validator.internal.util.logging.formatter.CollectionOfObjectsToStringFormatter;
import org.hibernate.validator.internal.util.logging.formatter.DurationFormatter;
import org.hibernate.validator.internal.util.logging.formatter.ExecutableFormatter;
import org.hibernate.validator.internal.util.logging.formatter.ObjectArrayFormatter;
import org.hibernate.validator.internal.util.logging.formatter.TypeFormatter;
import org.hibernate.validator.internal.xml.mapping.ContainerElementTypePath;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.hibernate.validator.spi.properties.GetterPropertySelectionStrategy;
import org.hibernate.validator.spi.scripting.ScriptEvaluationException;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorNotFoundException;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;

public class Log_$logger
extends DelegatingBasicLogger
implements Log,
BasicLogger,
Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = Log_$logger.class.getName();
    private static final Locale LOCALE = Locale.ROOT;

    public Log_$logger(Logger log) {
        super(log);
    }

    protected Locale getLoggingLocale() {
        return LOCALE;
    }

    @Override
    public final void version(String version) {
        this.log.logf(FQCN, Logger.Level.INFO, (Throwable)null, this.version$str(), (Object)version);
    }

    protected String version$str() {
        return "HV000001: Hibernate Validator %s";
    }

    @Override
    public final void ignoringXmlConfiguration() {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.ignoringXmlConfiguration$str(), new Object[0]);
    }

    protected String ignoringXmlConfiguration$str() {
        return "HV000002: Ignoring XML configuration.";
    }

    @Override
    public final void usingConstraintValidatorFactory(Class<? extends ConstraintValidatorFactory> constraintValidatorFactoryClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingConstraintValidatorFactory$str(), (Object)new ClassObjectFormatter(constraintValidatorFactoryClass));
    }

    protected String usingConstraintValidatorFactory$str() {
        return "HV000003: Using %s as constraint validator factory.";
    }

    @Override
    public final void usingMessageInterpolator(Class<? extends MessageInterpolator> messageInterpolatorClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingMessageInterpolator$str(), (Object)new ClassObjectFormatter(messageInterpolatorClass));
    }

    protected String usingMessageInterpolator$str() {
        return "HV000004: Using %s as message interpolator.";
    }

    @Override
    public final void usingTraversableResolver(Class<? extends TraversableResolver> traversableResolverClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingTraversableResolver$str(), (Object)new ClassObjectFormatter(traversableResolverClass));
    }

    protected String usingTraversableResolver$str() {
        return "HV000005: Using %s as traversable resolver.";
    }

    @Override
    public final void usingValidationProvider(Class<? extends ValidationProvider<?>> validationProviderClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingValidationProvider$str(), (Object)new ClassObjectFormatter(validationProviderClass));
    }

    protected String usingValidationProvider$str() {
        return "HV000006: Using %s as validation provider.";
    }

    @Override
    public final void parsingXMLFile(String fileName) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.parsingXMLFile$str(), (Object)fileName);
    }

    protected String parsingXMLFile$str() {
        return "HV000007: %s found. Parsing XML based configuration.";
    }

    @Override
    public final void unableToCloseInputStream() {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)null, this.unableToCloseInputStream$str(), new Object[0]);
    }

    protected String unableToCloseInputStream$str() {
        return "HV000008: Unable to close input stream.";
    }

    @Override
    public final void unableToCloseXMLFileInputStream(String fileName) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)null, this.unableToCloseXMLFileInputStream$str(), (Object)fileName);
    }

    protected String unableToCloseXMLFileInputStream$str() {
        return "HV000010: Unable to close input stream for %s.";
    }

    @Override
    public final void unableToCreateSchema(String fileName, String message) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)null, this.unableToCreateSchema$str(), (Object)fileName, (Object)message);
    }

    protected String unableToCreateSchema$str() {
        return "HV000011: Unable to create schema for %1$s: %2$s";
    }

    protected String getUnableToCreateAnnotationForConfiguredConstraintException$str() {
        return "HV000012: Unable to create annotation for configured constraint";
    }

    @Override
    public final ValidationException getUnableToCreateAnnotationForConfiguredConstraintException(RuntimeException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToCreateAnnotationForConfiguredConstraintException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    private static void _copyStackTraceMinusOne(Throwable e) {
        StackTraceElement[] st = e.getStackTrace();
        e.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
    }

    protected String getUnableToFindPropertyWithAccessException$str() {
        return "HV000013: The class %1$s does not have a property '%2$s' with access %3$s.";
    }

    @Override
    public final ValidationException getUnableToFindPropertyWithAccessException(Class<?> beanClass, String property, ElementType elementType) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToFindPropertyWithAccessException$str(), new Object[]{new ClassObjectFormatter(beanClass), property, elementType}));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidBigDecimalFormatException$str() {
        return "HV000016: %s does not represent a valid BigDecimal format.";
    }

    @Override
    public final IllegalArgumentException getInvalidBigDecimalFormatException(String value, NumberFormatException e) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidBigDecimalFormatException$str(), value), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidLengthForIntegerPartException$str() {
        return "HV000017: The length of the integer part cannot be negative.";
    }

    @Override
    public final IllegalArgumentException getInvalidLengthForIntegerPartException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidLengthForIntegerPartException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidLengthForFractionPartException$str() {
        return "HV000018: The length of the fraction part cannot be negative.";
    }

    @Override
    public final IllegalArgumentException getInvalidLengthForFractionPartException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidLengthForFractionPartException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMinCannotBeNegativeException$str() {
        return "HV000019: The min parameter cannot be negative.";
    }

    @Override
    public final IllegalArgumentException getMinCannotBeNegativeException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMinCannotBeNegativeException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMaxCannotBeNegativeException$str() {
        return "HV000020: The max parameter cannot be negative.";
    }

    @Override
    public final IllegalArgumentException getMaxCannotBeNegativeException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMaxCannotBeNegativeException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getLengthCannotBeNegativeException$str() {
        return "HV000021: The length cannot be negative.";
    }

    @Override
    public final IllegalArgumentException getLengthCannotBeNegativeException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getLengthCannotBeNegativeException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidRegularExpressionException$str() {
        return "HV000022: Invalid regular expression.";
    }

    @Override
    public final IllegalArgumentException getInvalidRegularExpressionException(PatternSyntaxException e) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidRegularExpressionException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getErrorDuringScriptExecutionException$str() {
        return "HV000023: Error during execution of script \"%s\" occurred.";
    }

    @Override
    public final ConstraintDeclarationException getErrorDuringScriptExecutionException(String script, Exception e) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getErrorDuringScriptExecutionException$str(), script), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getScriptMustReturnTrueOrFalseException1$str() {
        return "HV000024: Script \"%s\" returned null, but must return either true or false.";
    }

    @Override
    public final ConstraintDeclarationException getScriptMustReturnTrueOrFalseException(String script) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getScriptMustReturnTrueOrFalseException1$str(), script));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getScriptMustReturnTrueOrFalseException3$str() {
        return "HV000025: Script \"%1$s\" returned %2$s (of type %3$s), but must return either true or false.";
    }

    @Override
    public final ConstraintDeclarationException getScriptMustReturnTrueOrFalseException(String script, Object executionResult, String type) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getScriptMustReturnTrueOrFalseException3$str(), script, executionResult, type));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInconsistentConfigurationException$str() {
        return "HV000026: Assertion error: inconsistent ConfigurationImpl construction.";
    }

    @Override
    public final ValidationException getInconsistentConfigurationException() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInconsistentConfigurationException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToFindProviderException$str() {
        return "HV000027: Unable to find provider: %s.";
    }

    @Override
    public final ValidationException getUnableToFindProviderException(Class<?> providerClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToFindProviderException$str(), new ClassObjectFormatter(providerClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getExceptionDuringIsValidCallException$str() {
        return "HV000028: Unexpected exception during isValid call.";
    }

    @Override
    public final ValidationException getExceptionDuringIsValidCallException(RuntimeException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getExceptionDuringIsValidCallException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getConstraintValidatorFactoryMustNotReturnNullException$str() {
        return "HV000029: Constraint factory returned null when trying to create instance of %s.";
    }

    @Override
    public final ValidationException getConstraintValidatorFactoryMustNotReturnNullException(Class<? extends ConstraintValidator<?, ?>> validatorClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstraintValidatorFactoryMustNotReturnNullException$str(), new ClassObjectFormatter(validatorClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNoValidatorFoundForTypeException$str() {
        return "HV000030: No validator could be found for constraint '%s' validating type '%s'. Check configuration for '%s'";
    }

    @Override
    public final UnexpectedTypeException getNoValidatorFoundForTypeException(Class<? extends Annotation> constraintType, String validatedValueType, String path) {
        UnexpectedTypeException result = new UnexpectedTypeException(String.format(this.getLoggingLocale(), this.getNoValidatorFoundForTypeException$str(), new ClassObjectFormatter(constraintType), validatedValueType, path));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMoreThanOneValidatorFoundForTypeException$str() {
        return "HV000031: There are multiple validator classes which could validate the type %1$s. The validator classes are: %2$s.";
    }

    @Override
    public final UnexpectedTypeException getMoreThanOneValidatorFoundForTypeException(Type type, Collection<Type> validatorClasses) {
        UnexpectedTypeException result = new UnexpectedTypeException(String.format(this.getLoggingLocale(), this.getMoreThanOneValidatorFoundForTypeException$str(), type, new CollectionOfObjectsToStringFormatter(validatorClasses)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToInitializeConstraintValidatorException$str() {
        return "HV000032: Unable to initialize %s.";
    }

    @Override
    public final ValidationException getUnableToInitializeConstraintValidatorException(Class<? extends ConstraintValidator> validatorClass, RuntimeException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInitializeConstraintValidatorException$str(), new ClassObjectFormatter(validatorClass)), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getAtLeastOneCustomMessageMustBeCreatedException$str() {
        return "HV000033: At least one custom message must be created if the default error message gets disabled.";
    }

    @Override
    public final ValidationException getAtLeastOneCustomMessageMustBeCreatedException() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getAtLeastOneCustomMessageMustBeCreatedException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidJavaIdentifierException$str() {
        return "HV000034: %s is not a valid Java Identifier.";
    }

    @Override
    public final IllegalArgumentException getInvalidJavaIdentifierException(String identifier) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidJavaIdentifierException$str(), identifier));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToParsePropertyPathException$str() {
        return "HV000035: Unable to parse property path %s.";
    }

    @Override
    public final IllegalArgumentException getUnableToParsePropertyPathException(String propertyPath) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getUnableToParsePropertyPathException$str(), propertyPath));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getTypeNotSupportedForUnwrappingException$str() {
        return "HV000036: Type %s not supported for unwrapping.";
    }

    @Override
    public final ValidationException getTypeNotSupportedForUnwrappingException(Class<?> type) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getTypeNotSupportedForUnwrappingException$str(), new ClassObjectFormatter(type)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInconsistentFailFastConfigurationException$str() {
        return "HV000037: Inconsistent fail fast configuration. Fail fast enabled via programmatic API, but explicitly disabled via properties.";
    }

    @Override
    public final ValidationException getInconsistentFailFastConfigurationException() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInconsistentFailFastConfigurationException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidPropertyPathException0$str() {
        return "HV000038: Invalid property path.";
    }

    @Override
    public final IllegalArgumentException getInvalidPropertyPathException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidPropertyPathException0$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidPropertyPathException2$str() {
        return "HV000039: Invalid property path. Either there is no property %2$s in entity %1$s or it is not possible to cascade to the property.";
    }

    @Override
    public final IllegalArgumentException getInvalidPropertyPathException(Class<?> beanClass, String propertyName) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidPropertyPathException2$str(), new ClassObjectFormatter(beanClass), propertyName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getPropertyPathMustProvideIndexOrMapKeyException$str() {
        return "HV000040: Property path must provide index or map key.";
    }

    @Override
    public final IllegalArgumentException getPropertyPathMustProvideIndexOrMapKeyException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getPropertyPathMustProvideIndexOrMapKeyException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getErrorDuringCallOfTraversableResolverIsReachableException$str() {
        return "HV000041: Call to TraversableResolver.isReachable() threw an exception.";
    }

    @Override
    public final ValidationException getErrorDuringCallOfTraversableResolverIsReachableException(RuntimeException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getErrorDuringCallOfTraversableResolverIsReachableException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getErrorDuringCallOfTraversableResolverIsCascadableException$str() {
        return "HV000042: Call to TraversableResolver.isCascadable() threw an exception.";
    }

    @Override
    public final ValidationException getErrorDuringCallOfTraversableResolverIsCascadableException(RuntimeException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getErrorDuringCallOfTraversableResolverIsCascadableException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToExpandDefaultGroupListException$str() {
        return "HV000043: Unable to expand default group list %1$s into sequence %2$s.";
    }

    @Override
    public final GroupDefinitionException getUnableToExpandDefaultGroupListException(List<?> defaultGroupList, List<?> groupList) {
        GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getUnableToExpandDefaultGroupListException$str(), new CollectionOfObjectsToStringFormatter(defaultGroupList), new CollectionOfObjectsToStringFormatter(groupList)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getAtLeastOneGroupHasToBeSpecifiedException$str() {
        return "HV000044: At least one group has to be specified.";
    }

    @Override
    public final IllegalArgumentException getAtLeastOneGroupHasToBeSpecifiedException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getAtLeastOneGroupHasToBeSpecifiedException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getGroupHasToBeAnInterfaceException$str() {
        return "HV000045: A group has to be an interface. %s is not.";
    }

    @Override
    public final ValidationException getGroupHasToBeAnInterfaceException(Class<?> clazz) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getGroupHasToBeAnInterfaceException$str(), new ClassObjectFormatter(clazz)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getSequenceDefinitionsNotAllowedException$str() {
        return "HV000046: Sequence definitions are not allowed as composing parts of a sequence.";
    }

    @Override
    public final GroupDefinitionException getSequenceDefinitionsNotAllowedException() {
        GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getSequenceDefinitionsNotAllowedException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getCyclicDependencyInGroupsDefinitionException$str() {
        return "HV000047: Cyclic dependency in groups definition";
    }

    @Override
    public final GroupDefinitionException getCyclicDependencyInGroupsDefinitionException() {
        GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getCyclicDependencyInGroupsDefinitionException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToExpandGroupSequenceException$str() {
        return "HV000048: Unable to expand group sequence.";
    }

    @Override
    public final GroupDefinitionException getUnableToExpandGroupSequenceException() {
        GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getUnableToExpandGroupSequenceException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidDefaultGroupSequenceDefinitionException$str() {
        return "HV000052: Default group sequence and default group sequence provider cannot be defined at the same time.";
    }

    @Override
    public final GroupDefinitionException getInvalidDefaultGroupSequenceDefinitionException() {
        GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getInvalidDefaultGroupSequenceDefinitionException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNoDefaultGroupInGroupSequenceException$str() {
        return "HV000053: 'Default.class' cannot appear in default group sequence list.";
    }

    @Override
    public final GroupDefinitionException getNoDefaultGroupInGroupSequenceException() {
        GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getNoDefaultGroupInGroupSequenceException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getBeanClassMustBePartOfRedefinedDefaultGroupSequenceException$str() {
        return "HV000054: %s must be part of the redefined default group sequence.";
    }

    @Override
    public final GroupDefinitionException getBeanClassMustBePartOfRedefinedDefaultGroupSequenceException(Class<?> beanClass) {
        GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getBeanClassMustBePartOfRedefinedDefaultGroupSequenceException$str(), new ClassObjectFormatter(beanClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWrongDefaultGroupSequenceProviderTypeException$str() {
        return "HV000055: The default group sequence provider defined for %s has the wrong type";
    }

    @Override
    public final GroupDefinitionException getWrongDefaultGroupSequenceProviderTypeException(Class<?> beanClass) {
        GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getWrongDefaultGroupSequenceProviderTypeException$str(), new ClassObjectFormatter(beanClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidExecutableParameterIndexException$str() {
        return "HV000056: Method or constructor %1$s doesn't have a parameter with index %2$d.";
    }

    @Override
    public final IllegalArgumentException getInvalidExecutableParameterIndexException(Callable callable, int index) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidExecutableParameterIndexException$str(), callable, index));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToRetrieveAnnotationParameterValueException$str() {
        return "HV000059: Unable to retrieve annotation parameter value.";
    }

    @Override
    public final ValidationException getUnableToRetrieveAnnotationParameterValueException(Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToRetrieveAnnotationParameterValueException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidLengthOfParameterMetaDataListException$str() {
        return "HV000062: Method or constructor %1$s has %2$s parameters, but the passed list of parameter meta data has a size of %3$s.";
    }

    @Override
    public final IllegalArgumentException getInvalidLengthOfParameterMetaDataListException(Callable callable, int nbParameters, int listSize) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidLengthOfParameterMetaDataListException$str(), callable, nbParameters, listSize));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToInstantiateException1$str() {
        return "HV000063: Unable to instantiate %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateException(Class<?> clazz, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateException1$str(), new ClassObjectFormatter(clazz)), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToInstantiateException2$str() {
        return "HV000064: Unable to instantiate %1$s: %2$s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateException(String message, Class<?> clazz, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateException2$str(), message, new ClassObjectFormatter(clazz)), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToLoadClassException$str() {
        return "HV000065: Unable to load class: %s from %s.";
    }

    @Override
    public final ValidationException getUnableToLoadClassException(String className, ClassLoader loader, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToLoadClassException$str(), className, loader), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getStartIndexCannotBeNegativeException$str() {
        return "HV000068: Start index cannot be negative: %d.";
    }

    @Override
    public final IllegalArgumentException getStartIndexCannotBeNegativeException(int startIndex) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getStartIndexCannotBeNegativeException$str(), startIndex));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getEndIndexCannotBeNegativeException$str() {
        return "HV000069: End index cannot be negative: %d.";
    }

    @Override
    public final IllegalArgumentException getEndIndexCannotBeNegativeException(int endIndex) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getEndIndexCannotBeNegativeException$str(), endIndex));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidRangeException$str() {
        return "HV000070: Invalid Range: %1$d > %2$d.";
    }

    @Override
    public final IllegalArgumentException getInvalidRangeException(int startIndex, int endIndex) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidRangeException$str(), startIndex, endIndex));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidCheckDigitException$str() {
        return "HV000071: A explicitly specified check digit must lie outside the interval: [%1$d, %2$d].";
    }

    @Override
    public final IllegalArgumentException getInvalidCheckDigitException(int startIndex, int endIndex) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidCheckDigitException$str(), startIndex, endIndex));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getCharacterIsNotADigitException$str() {
        return "HV000072: '%c' is not a digit.";
    }

    @Override
    public final NumberFormatException getCharacterIsNotADigitException(char c) {
        NumberFormatException result = new NumberFormatException(String.format(this.getLoggingLocale(), this.getCharacterIsNotADigitException$str(), Character.valueOf(c)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getConstraintParametersCannotStartWithValidException$str() {
        return "HV000073: Parameters starting with 'valid' are not allowed in a constraint.";
    }

    @Override
    public final ConstraintDefinitionException getConstraintParametersCannotStartWithValidException() {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getConstraintParametersCannotStartWithValidException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getConstraintWithoutMandatoryParameterException$str() {
        return "HV000074: %2$s contains Constraint annotation, but does not contain a %1$s parameter.";
    }

    @Override
    public final ConstraintDefinitionException getConstraintWithoutMandatoryParameterException(String parameterName, String constraintName) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getConstraintWithoutMandatoryParameterException$str(), parameterName, constraintName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWrongDefaultValueForPayloadParameterException$str() {
        return "HV000075: %s contains Constraint annotation, but the payload parameter default value is not the empty array.";
    }

    @Override
    public final ConstraintDefinitionException getWrongDefaultValueForPayloadParameterException(String constraintName) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongDefaultValueForPayloadParameterException$str(), constraintName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWrongTypeForPayloadParameterException$str() {
        return "HV000076: %s contains Constraint annotation, but the payload parameter is of wrong type.";
    }

    @Override
    public final ConstraintDefinitionException getWrongTypeForPayloadParameterException(String constraintName, ClassCastException e) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongTypeForPayloadParameterException$str(), constraintName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWrongDefaultValueForGroupsParameterException$str() {
        return "HV000077: %s contains Constraint annotation, but the groups parameter default value is not the empty array.";
    }

    @Override
    public final ConstraintDefinitionException getWrongDefaultValueForGroupsParameterException(String constraintName) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongDefaultValueForGroupsParameterException$str(), constraintName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWrongTypeForGroupsParameterException$str() {
        return "HV000078: %s contains Constraint annotation, but the groups parameter is of wrong type.";
    }

    @Override
    public final ConstraintDefinitionException getWrongTypeForGroupsParameterException(String constraintName, ClassCastException e) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongTypeForGroupsParameterException$str(), constraintName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWrongTypeForMessageParameterException$str() {
        return "HV000079: %s contains Constraint annotation, but the message parameter is not of type java.lang.String.";
    }

    @Override
    public final ConstraintDefinitionException getWrongTypeForMessageParameterException(String constraintName) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongTypeForMessageParameterException$str(), constraintName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getOverriddenConstraintAttributeNotFoundException$str() {
        return "HV000080: Overridden constraint does not define an attribute with name %s.";
    }

    @Override
    public final ConstraintDefinitionException getOverriddenConstraintAttributeNotFoundException(String attributeName) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getOverriddenConstraintAttributeNotFoundException$str(), attributeName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWrongAttributeTypeForOverriddenConstraintException$str() {
        return "HV000081: The overriding type of a composite constraint must be identical to the overridden one. Expected %1$s found %2$s.";
    }

    @Override
    public final ConstraintDefinitionException getWrongAttributeTypeForOverriddenConstraintException(Class<?> expectedReturnType, Class<?> currentReturnType) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongAttributeTypeForOverriddenConstraintException$str(), new ClassObjectFormatter(expectedReturnType), new ClassObjectFormatter(currentReturnType)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWrongAnnotationAttributeTypeException$str() {
        return "HV000082: Wrong type for attribute '%2$s' of annotation %1$s. Expected: %3$s. Actual: %4$s.";
    }

    @Override
    public final ValidationException getWrongAnnotationAttributeTypeException(Class<? extends Annotation> annotationClass, String attributeName, Class<?> expectedType, Class<?> currentType) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getWrongAnnotationAttributeTypeException$str(), new ClassObjectFormatter(annotationClass), attributeName, new ClassObjectFormatter(expectedType), new ClassObjectFormatter(currentType)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToFindAnnotationAttributeException$str() {
        return "HV000083: The specified annotation %1$s defines no attribute '%2$s'.";
    }

    @Override
    public final ValidationException getUnableToFindAnnotationAttributeException(Class<? extends Annotation> annotationClass, String parameterName, NoSuchMethodException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToFindAnnotationAttributeException$str(), new ClassObjectFormatter(annotationClass), parameterName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToGetAnnotationAttributeException$str() {
        return "HV000084: Unable to get attribute '%2$s' from annotation %1$s.";
    }

    @Override
    public final ValidationException getUnableToGetAnnotationAttributeException(Class<? extends Annotation> annotationClass, String parameterName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToGetAnnotationAttributeException$str(), new ClassObjectFormatter(annotationClass), parameterName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNoValueProvidedForAnnotationAttributeException$str() {
        return "HV000085: No value provided for attribute '%1$s' of annotation @%2$s.";
    }

    @Override
    public final IllegalArgumentException getNoValueProvidedForAnnotationAttributeException(String parameterName, Class<? extends Annotation> annotation) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getNoValueProvidedForAnnotationAttributeException$str(), parameterName, new ClassObjectFormatter(annotation)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getTryingToInstantiateAnnotationWithUnknownAttributesException$str() {
        return "HV000086: Trying to instantiate annotation %1$s with unknown attribute(s): %2$s.";
    }

    @Override
    public final RuntimeException getTryingToInstantiateAnnotationWithUnknownAttributesException(Class<? extends Annotation> annotationType, Set<String> unknownParameters) {
        RuntimeException result = new RuntimeException(String.format(this.getLoggingLocale(), this.getTryingToInstantiateAnnotationWithUnknownAttributesException$str(), new ClassObjectFormatter(annotationType), unknownParameters));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getPropertyNameCannotBeNullOrEmptyException$str() {
        return "HV000087: Property name cannot be null or empty.";
    }

    @Override
    public final IllegalArgumentException getPropertyNameCannotBeNullOrEmptyException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getPropertyNameCannotBeNullOrEmptyException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getElementTypeHasToBeFieldOrMethodException$str() {
        return "HV000088: Element type has to be FIELD or METHOD.";
    }

    @Override
    public final IllegalArgumentException getElementTypeHasToBeFieldOrMethodException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getElementTypeHasToBeFieldOrMethodException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMemberIsNeitherAFieldNorAMethodException$str() {
        return "HV000089: Member %s is neither a field nor a method.";
    }

    @Override
    public final IllegalArgumentException getMemberIsNeitherAFieldNorAMethodException(Member member) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMemberIsNeitherAFieldNorAMethodException$str(), member));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToAccessMemberException$str() {
        return "HV000090: Unable to access %s.";
    }

    @Override
    public final ValidationException getUnableToAccessMemberException(String memberName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToAccessMemberException$str(), memberName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getHasToBeAPrimitiveTypeException$str() {
        return "HV000091: %s has to be a primitive type.";
    }

    @Override
    public final IllegalArgumentException getHasToBeAPrimitiveTypeException(Class<?> clazz) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getHasToBeAPrimitiveTypeException$str(), new ClassObjectFormatter(clazz)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNullIsAnInvalidTypeForAConstraintValidatorException$str() {
        return "HV000093: null is an invalid type for a constraint validator.";
    }

    @Override
    public final ValidationException getNullIsAnInvalidTypeForAConstraintValidatorException() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getNullIsAnInvalidTypeForAConstraintValidatorException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMissingActualTypeArgumentForTypeParameterException$str() {
        return "HV000094: Missing actual type argument for type parameter: %s.";
    }

    @Override
    public final IllegalArgumentException getMissingActualTypeArgumentForTypeParameterException(TypeVariable<?> typeParameter) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMissingActualTypeArgumentForTypeParameterException$str(), typeParameter));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToInstantiateConstraintValidatorFactoryClassException$str() {
        return "HV000095: Unable to instantiate constraint factory class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateConstraintValidatorFactoryClassException(String constraintValidatorFactoryClassName, ValidationException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateConstraintValidatorFactoryClassException$str(), constraintValidatorFactoryClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToOpenInputStreamForMappingFileException$str() {
        return "HV000096: Unable to open input stream for mapping file %s.";
    }

    @Override
    public final ValidationException getUnableToOpenInputStreamForMappingFileException(String mappingFileName) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToOpenInputStreamForMappingFileException$str(), mappingFileName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToInstantiateMessageInterpolatorClassException$str() {
        return "HV000097: Unable to instantiate message interpolator class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateMessageInterpolatorClassException(String messageInterpolatorClassName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateMessageInterpolatorClassException$str(), messageInterpolatorClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToInstantiateTraversableResolverClassException$str() {
        return "HV000098: Unable to instantiate traversable resolver class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateTraversableResolverClassException(String traversableResolverClassName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateTraversableResolverClassException$str(), traversableResolverClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToInstantiateValidationProviderClassException$str() {
        return "HV000099: Unable to instantiate validation provider class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateValidationProviderClassException(String providerClassName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateValidationProviderClassException$str(), providerClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToParseValidationXmlFileException$str() {
        return "HV000100: Unable to parse %s.";
    }

    @Override
    public final ValidationException getUnableToParseValidationXmlFileException(String file, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToParseValidationXmlFileException$str(), file), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getIsNotAnAnnotationException$str() {
        return "HV000101: %s is not an annotation.";
    }

    @Override
    public final ValidationException getIsNotAnAnnotationException(Class<?> annotationClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getIsNotAnAnnotationException$str(), new ClassObjectFormatter(annotationClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getIsNotAConstraintValidatorClassException$str() {
        return "HV000102: %s is not a constraint validator class.";
    }

    @Override
    public final ValidationException getIsNotAConstraintValidatorClassException(Class<?> validatorClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getIsNotAConstraintValidatorClassException$str(), new ClassObjectFormatter(validatorClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getBeanClassHasAlreadyBeenConfiguredInXmlException$str() {
        return "HV000103: %s is configured at least twice in xml.";
    }

    @Override
    public final ValidationException getBeanClassHasAlreadyBeenConfiguredInXmlException(Class<?> beanClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanClassHasAlreadyBeenConfiguredInXmlException$str(), new ClassObjectFormatter(beanClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getIsDefinedTwiceInMappingXmlForBeanException$str() {
        return "HV000104: %1$s is defined twice in mapping xml for bean %2$s.";
    }

    @Override
    public final ValidationException getIsDefinedTwiceInMappingXmlForBeanException(String name, Class<?> beanClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getIsDefinedTwiceInMappingXmlForBeanException$str(), name, new ClassObjectFormatter(beanClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getBeanDoesNotContainTheFieldException$str() {
        return "HV000105: %1$s does not contain the fieldType %2$s.";
    }

    @Override
    public final ValidationException getBeanDoesNotContainTheFieldException(Class<?> beanClass, String fieldName) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanDoesNotContainTheFieldException$str(), new ClassObjectFormatter(beanClass), fieldName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getBeanDoesNotContainThePropertyException$str() {
        return "HV000106: %1$s does not contain the property %2$s.";
    }

    @Override
    public final ValidationException getBeanDoesNotContainThePropertyException(Class<?> beanClass, String getterName) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanDoesNotContainThePropertyException$str(), new ClassObjectFormatter(beanClass), getterName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getAnnotationDoesNotContainAParameterException$str() {
        return "HV000107: Annotation of type %1$s does not contain a parameter %2$s.";
    }

    @Override
    public final ValidationException getAnnotationDoesNotContainAParameterException(Class<? extends Annotation> annotationClass, String parameterName) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getAnnotationDoesNotContainAParameterException$str(), new ClassObjectFormatter(annotationClass), parameterName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getAttemptToSpecifyAnArrayWhereSingleValueIsExpectedException$str() {
        return "HV000108: Attempt to specify an array where single value is expected.";
    }

    @Override
    public final ValidationException getAttemptToSpecifyAnArrayWhereSingleValueIsExpectedException() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getAttemptToSpecifyAnArrayWhereSingleValueIsExpectedException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnexpectedParameterValueException$str() {
        return "HV000109: Unexpected parameter value.";
    }

    @Override
    public final ValidationException getUnexpectedParameterValueException() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnexpectedParameterValueException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final ValidationException getUnexpectedParameterValueException(ClassCastException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnexpectedParameterValueException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidNumberFormatException$str() {
        return "HV000110: Invalid %s format.";
    }

    @Override
    public final ValidationException getInvalidNumberFormatException(String formatName, NumberFormatException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidNumberFormatException$str(), formatName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidCharValueException$str() {
        return "HV000111: Invalid char value: %s.";
    }

    @Override
    public final ValidationException getInvalidCharValueException(String value) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidCharValueException$str(), value));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidReturnTypeException$str() {
        return "HV000112: Invalid return type: %s. Should be a enumeration type.";
    }

    @Override
    public final ValidationException getInvalidReturnTypeException(Class<?> returnType, ClassCastException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidReturnTypeException$str(), new ClassObjectFormatter(returnType)), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getReservedParameterNamesException$str() {
        return "HV000113: %s, %s, %s are reserved parameter names.";
    }

    @Override
    public final ValidationException getReservedParameterNamesException(String messageParameterName, String groupsParameterName, String payloadParameterName) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getReservedParameterNamesException$str(), messageParameterName, groupsParameterName, payloadParameterName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWrongPayloadClassException$str() {
        return "HV000114: Specified payload class %s does not implement javax.validation.Payload";
    }

    @Override
    public final ValidationException getWrongPayloadClassException(Class<?> payloadClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getWrongPayloadClassException$str(), new ClassObjectFormatter(payloadClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getErrorParsingMappingFileException$str() {
        return "HV000115: Error parsing mapping file.";
    }

    @Override
    public final ValidationException getErrorParsingMappingFileException(Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getErrorParsingMappingFileException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getIllegalArgumentException$str() {
        return "HV000116: %s";
    }

    @Override
    public final IllegalArgumentException getIllegalArgumentException(String message) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getIllegalArgumentException$str(), message));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToNarrowNodeTypeException$str() {
        return "HV000118: Unable to cast %s (with element kind %s) to %s";
    }

    @Override
    public final ClassCastException getUnableToNarrowNodeTypeException(Class<?> actualDescriptorType, ElementKind kind, Class<?> expectedDescriptorType) {
        ClassCastException result = new ClassCastException(String.format(this.getLoggingLocale(), this.getUnableToNarrowNodeTypeException$str(), new Object[]{new ClassObjectFormatter(actualDescriptorType), kind, new ClassObjectFormatter(expectedDescriptorType)}));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void usingParameterNameProvider(Class<? extends ParameterNameProvider> parameterNameProviderClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingParameterNameProvider$str(), (Object)new ClassObjectFormatter(parameterNameProviderClass));
    }

    protected String usingParameterNameProvider$str() {
        return "HV000119: Using %s as parameter name provider.";
    }

    protected String getUnableToInstantiateParameterNameProviderClassException$str() {
        return "HV000120: Unable to instantiate parameter name provider class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateParameterNameProviderClassException(String parameterNameProviderClassName, ValidationException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateParameterNameProviderClassException$str(), parameterNameProviderClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToDetermineSchemaVersionException$str() {
        return "HV000121: Unable to parse %s.";
    }

    @Override
    public final ValidationException getUnableToDetermineSchemaVersionException(String file, XMLStreamException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToDetermineSchemaVersionException$str(), file), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnsupportedSchemaVersionException$str() {
        return "HV000122: Unsupported schema version for %s: %s.";
    }

    @Override
    public final ValidationException getUnsupportedSchemaVersionException(String file, String version) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnsupportedSchemaVersionException$str(), file, version));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMultipleGroupConversionsForSameSourceException$str() {
        return "HV000124: Found multiple group conversions for source group %s: %s.";
    }

    @Override
    public final ConstraintDeclarationException getMultipleGroupConversionsForSameSourceException(Class<?> from, Collection<Class<?>> tos) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getMultipleGroupConversionsForSameSourceException$str(), new ClassObjectFormatter(from), new CollectionOfClassesObjectFormatter(tos)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getGroupConversionOnNonCascadingElementException$str() {
        return "HV000125: Found group conversions for non-cascading element at: %s.";
    }

    @Override
    public final ConstraintDeclarationException getGroupConversionOnNonCascadingElementException(Object context) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getGroupConversionOnNonCascadingElementException$str(), context));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getGroupConversionForSequenceException$str() {
        return "HV000127: Found group conversion using a group sequence as source at: %s.";
    }

    @Override
    public final ConstraintDeclarationException getGroupConversionForSequenceException(Class<?> from) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getGroupConversionForSequenceException$str(), new ClassObjectFormatter(from)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void unknownPropertyInExpressionLanguage(String expression, Exception e) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)e, this.unknownPropertyInExpressionLanguage$str(), (Object)expression);
    }

    protected String unknownPropertyInExpressionLanguage$str() {
        return "HV000129: EL expression '%s' references an unknown property";
    }

    @Override
    public final void errorInExpressionLanguage(String expression, Exception e) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)e, this.errorInExpressionLanguage$str(), (Object)expression);
    }

    protected String errorInExpressionLanguage$str() {
        return "HV000130: Error in EL expression '%s'";
    }

    protected String getMethodReturnValueMustNotBeMarkedMoreThanOnceForCascadedValidationException$str() {
        return "HV000131: A method return value must not be marked for cascaded validation more than once in a class hierarchy, but the following two methods are marked as such: %s, %s.";
    }

    @Override
    public final ConstraintDeclarationException getMethodReturnValueMustNotBeMarkedMoreThanOnceForCascadedValidationException(Callable callable1, Callable callable2) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getMethodReturnValueMustNotBeMarkedMoreThanOnceForCascadedValidationException$str(), callable1, callable2));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getVoidMethodsMustNotBeConstrainedException$str() {
        return "HV000132: Void methods must not be constrained or marked for cascaded validation, but method %s is.";
    }

    @Override
    public final ConstraintDeclarationException getVoidMethodsMustNotBeConstrainedException(Callable callable) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getVoidMethodsMustNotBeConstrainedException$str(), callable));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getBeanDoesNotContainConstructorException$str() {
        return "HV000133: %1$s does not contain a constructor with the parameter types %2$s.";
    }

    @Override
    public final ValidationException getBeanDoesNotContainConstructorException(Class<?> beanClass, Class<?>[] parameterTypes) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanDoesNotContainConstructorException$str(), new ClassObjectFormatter(beanClass), new ArrayOfClassesObjectFormatter(parameterTypes)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidParameterTypeException$str() {
        return "HV000134: Unable to load parameter of type '%1$s' in %2$s.";
    }

    @Override
    public final ValidationException getInvalidParameterTypeException(String type, Class<?> beanClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidParameterTypeException$str(), type, new ClassObjectFormatter(beanClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getBeanDoesNotContainMethodException$str() {
        return "HV000135: %1$s does not contain a method with the name '%2$s' and parameter types %3$s.";
    }

    @Override
    public final ValidationException getBeanDoesNotContainMethodException(Class<?> beanClass, String methodName, Class<?>[] parameterTypes) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanDoesNotContainMethodException$str(), new ClassObjectFormatter(beanClass), methodName, new ArrayOfClassesObjectFormatter(parameterTypes)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToLoadConstraintAnnotationClassException$str() {
        return "HV000136: The specified constraint annotation class %1$s cannot be loaded.";
    }

    @Override
    public final ValidationException getUnableToLoadConstraintAnnotationClassException(String constraintAnnotationClassName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToLoadConstraintAnnotationClassException$str(), constraintAnnotationClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMethodIsDefinedTwiceInMappingXmlForBeanException$str() {
        return "HV000137: The method '%1$s' is defined twice in the mapping xml for bean %2$s.";
    }

    @Override
    public final ValidationException getMethodIsDefinedTwiceInMappingXmlForBeanException(JavaBeanMethod javaBeanMethod, Class<?> beanClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getMethodIsDefinedTwiceInMappingXmlForBeanException$str(), javaBeanMethod, new ClassObjectFormatter(beanClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getConstructorIsDefinedTwiceInMappingXmlForBeanException$str() {
        return "HV000138: The constructor '%1$s' is defined twice in the mapping xml for bean %2$s.";
    }

    @Override
    public final ValidationException getConstructorIsDefinedTwiceInMappingXmlForBeanException(JavaBeanConstructor javaBeanConstructor, Class<?> beanClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstructorIsDefinedTwiceInMappingXmlForBeanException$str(), javaBeanConstructor, new ClassObjectFormatter(beanClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMultipleCrossParameterValidatorClassesException$str() {
        return "HV000139: The constraint '%1$s' defines multiple cross parameter validators. Only one is allowed.";
    }

    @Override
    public final ConstraintDefinitionException getMultipleCrossParameterValidatorClassesException(Class<? extends Annotation> constraint) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getMultipleCrossParameterValidatorClassesException$str(), new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getImplicitConstraintTargetInAmbiguousConfigurationException$str() {
        return "HV000141: The constraint %1$s used ConstraintTarget#IMPLICIT where the target cannot be inferred.";
    }

    @Override
    public final ConstraintDeclarationException getImplicitConstraintTargetInAmbiguousConfigurationException(Class<? extends Annotation> constraint) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getImplicitConstraintTargetInAmbiguousConfigurationException$str(), new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getCrossParameterConstraintOnMethodWithoutParametersException$str() {
        return "HV000142: Cross parameter constraint %1$s is illegally placed on a parameterless method or constructor '%2$s'.";
    }

    @Override
    public final ConstraintDeclarationException getCrossParameterConstraintOnMethodWithoutParametersException(Class<? extends Annotation> constraint, Constrainable executable) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCrossParameterConstraintOnMethodWithoutParametersException$str(), new ClassObjectFormatter(constraint), executable));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getCrossParameterConstraintOnClassException$str() {
        return "HV000143: Cross parameter constraint %1$s is illegally placed on class level.";
    }

    @Override
    public final ConstraintDeclarationException getCrossParameterConstraintOnClassException(Class<? extends Annotation> constraint) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCrossParameterConstraintOnClassException$str(), new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getCrossParameterConstraintOnFieldException$str() {
        return "HV000144: Cross parameter constraint %1$s is illegally placed on field '%2$s'.";
    }

    @Override
    public final ConstraintDeclarationException getCrossParameterConstraintOnFieldException(Class<? extends Annotation> constraint, Constrainable field2) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCrossParameterConstraintOnFieldException$str(), new ClassObjectFormatter(constraint), field2));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getParameterNodeAddedForNonCrossParameterConstraintException$str() {
        return "HV000146: No parameter nodes may be added since path %s doesn't refer to a cross-parameter constraint.";
    }

    @Override
    public final IllegalStateException getParameterNodeAddedForNonCrossParameterConstraintException(Path path) {
        IllegalStateException result = new IllegalStateException(String.format(this.getLoggingLocale(), this.getParameterNodeAddedForNonCrossParameterConstraintException$str(), path));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getConstrainedElementConfiguredMultipleTimesException$str() {
        return "HV000147: %1$s is configured multiple times (note, <getter> and <method> nodes for the same method are not allowed)";
    }

    @Override
    public final ValidationException getConstrainedElementConfiguredMultipleTimesException(String location) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstrainedElementConfiguredMultipleTimesException$str(), location));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void evaluatingExpressionLanguageExpressionCausedException(String expression, Exception e) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)e, this.evaluatingExpressionLanguageExpressionCausedException$str(), (Object)expression);
    }

    protected String evaluatingExpressionLanguageExpressionCausedException$str() {
        return "HV000148: An exception occurred during evaluation of EL expression '%s'";
    }

    protected String getExceptionOccurredDuringMessageInterpolationException$str() {
        return "HV000149: An exception occurred during message interpolation";
    }

    @Override
    public final ValidationException getExceptionOccurredDuringMessageInterpolationException(Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getExceptionOccurredDuringMessageInterpolationException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMultipleValidatorsForSameTypeException$str() {
        return "HV000150: The constraint %1$s defines multiple validators for the type %2$s: %3$s, %4$s. Only one is allowed.";
    }

    @Override
    public final UnexpectedTypeException getMultipleValidatorsForSameTypeException(Class<? extends Annotation> constraint, Type type, Class<? extends ConstraintValidator<?, ?>> validatorClass1, Class<? extends ConstraintValidator<?, ?>> validatorClass2) {
        UnexpectedTypeException result = new UnexpectedTypeException(String.format(this.getLoggingLocale(), this.getMultipleValidatorsForSameTypeException$str(), new ClassObjectFormatter(constraint), new TypeFormatter(type), new ClassObjectFormatter(validatorClass1), new ClassObjectFormatter(validatorClass2)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getParameterConfigurationAlteredInSubTypeException$str() {
        return "HV000151: A method overriding another method must not redefine the parameter constraint configuration, but method %2$s redefines the configuration of %1$s.";
    }

    @Override
    public final ConstraintDeclarationException getParameterConfigurationAlteredInSubTypeException(Callable superMethod, Callable subMethod) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getParameterConfigurationAlteredInSubTypeException$str(), superMethod, subMethod));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getParameterConstraintsDefinedInMethodsFromParallelTypesException$str() {
        return "HV000152: Two methods defined in parallel types must not declare parameter constraints, if they are overridden by the same method, but methods %s and %s both define parameter constraints.";
    }

    @Override
    public final ConstraintDeclarationException getParameterConstraintsDefinedInMethodsFromParallelTypesException(Callable method1, Callable method2) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getParameterConstraintsDefinedInMethodsFromParallelTypesException$str(), method1, method2));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getParametersOrReturnValueConstraintTargetGivenAtNonExecutableException$str() {
        return "HV000153: The constraint %1$s used ConstraintTarget#%2$s but is not specified on a method or constructor.";
    }

    @Override
    public final ConstraintDeclarationException getParametersOrReturnValueConstraintTargetGivenAtNonExecutableException(Class<? extends Annotation> constraint, ConstraintTarget target) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getParametersOrReturnValueConstraintTargetGivenAtNonExecutableException$str(), new Object[]{new ClassObjectFormatter(constraint), target}));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getCrossParameterConstraintHasNoValidatorException$str() {
        return "HV000154: Cross parameter constraint %1$s has no cross-parameter validator.";
    }

    @Override
    public final ConstraintDefinitionException getCrossParameterConstraintHasNoValidatorException(Class<? extends Annotation> constraint) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getCrossParameterConstraintHasNoValidatorException$str(), new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getComposedAndComposingConstraintsHaveDifferentTypesException$str() {
        return "HV000155: Composed and composing constraints must have the same constraint type, but composed constraint %1$s has type %3$s, while composing constraint %2$s has type %4$s.";
    }

    @Override
    public final ConstraintDefinitionException getComposedAndComposingConstraintsHaveDifferentTypesException(Class<? extends Annotation> composedConstraintClass, Class<? extends Annotation> composingConstraintClass, ConstraintDescriptorImpl.ConstraintType composedConstraintType, ConstraintDescriptorImpl.ConstraintType composingConstraintType) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getComposedAndComposingConstraintsHaveDifferentTypesException$str(), new Object[]{new ClassObjectFormatter(composedConstraintClass), new ClassObjectFormatter(composingConstraintClass), composedConstraintType, composingConstraintType}));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getGenericAndCrossParameterConstraintDoesNotDefineValidationAppliesToParameterException$str() {
        return "HV000156: Constraints with generic as well as cross-parameter validators must define an attribute validationAppliesTo(), but constraint %s doesn't.";
    }

    @Override
    public final ConstraintDefinitionException getGenericAndCrossParameterConstraintDoesNotDefineValidationAppliesToParameterException(Class<? extends Annotation> constraint) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getGenericAndCrossParameterConstraintDoesNotDefineValidationAppliesToParameterException$str(), new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getValidationAppliesToParameterMustHaveReturnTypeConstraintTargetException$str() {
        return "HV000157: Return type of the attribute validationAppliesTo() of the constraint %s must be javax.validation.ConstraintTarget.";
    }

    @Override
    public final ConstraintDefinitionException getValidationAppliesToParameterMustHaveReturnTypeConstraintTargetException(Class<? extends Annotation> constraint) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getValidationAppliesToParameterMustHaveReturnTypeConstraintTargetException$str(), new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getValidationAppliesToParameterMustHaveDefaultValueImplicitException$str() {
        return "HV000158: Default value of the attribute validationAppliesTo() of the constraint %s must be ConstraintTarget#IMPLICIT.";
    }

    @Override
    public final ConstraintDefinitionException getValidationAppliesToParameterMustHaveDefaultValueImplicitException(Class<? extends Annotation> constraint) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getValidationAppliesToParameterMustHaveDefaultValueImplicitException$str(), new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getValidationAppliesToParameterMustNotBeDefinedForNonGenericAndCrossParameterConstraintException$str() {
        return "HV000159: Only constraints with generic as well as cross-parameter validators must define an attribute validationAppliesTo(), but constraint %s does.";
    }

    @Override
    public final ConstraintDefinitionException getValidationAppliesToParameterMustNotBeDefinedForNonGenericAndCrossParameterConstraintException(Class<? extends Annotation> constraint) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getValidationAppliesToParameterMustNotBeDefinedForNonGenericAndCrossParameterConstraintException$str(), new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getValidatorForCrossParameterConstraintMustEitherValidateObjectOrObjectArrayException$str() {
        return "HV000160: Validator for cross-parameter constraint %s does not validate Object nor Object[].";
    }

    @Override
    public final ConstraintDefinitionException getValidatorForCrossParameterConstraintMustEitherValidateObjectOrObjectArrayException(Class<? extends Annotation> constraint) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getValidatorForCrossParameterConstraintMustEitherValidateObjectOrObjectArrayException$str(), new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMethodsFromParallelTypesMustNotDefineGroupConversionsForCascadedReturnValueException$str() {
        return "HV000161: Two methods defined in parallel types must not define group conversions for a cascaded method return value, if they are overridden by the same method, but methods %s and %s both define parameter constraints.";
    }

    @Override
    public final ConstraintDeclarationException getMethodsFromParallelTypesMustNotDefineGroupConversionsForCascadedReturnValueException(Callable method1, Callable method2) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getMethodsFromParallelTypesMustNotDefineGroupConversionsForCascadedReturnValueException$str(), method1, method2));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMethodOrConstructorNotDefinedByValidatedTypeException$str() {
        return "HV000162: The validated type %1$s does not specify the constructor/method: %2$s";
    }

    @Override
    public final IllegalArgumentException getMethodOrConstructorNotDefinedByValidatedTypeException(Class<?> validatedType, Executable executable) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMethodOrConstructorNotDefinedByValidatedTypeException$str(), new ClassObjectFormatter(validatedType), new ExecutableFormatter(executable)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getParameterTypesDoNotMatchException$str() {
        return "HV000163: The actual parameter type '%1$s' is not assignable to the expected one '%2$s' for parameter %3$d of '%4$s'";
    }

    @Override
    public final IllegalArgumentException getParameterTypesDoNotMatchException(Class<?> actualType, Type expectedType, int index, Executable executable) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getParameterTypesDoNotMatchException$str(), new ClassObjectFormatter(actualType), expectedType, index, new ExecutableFormatter(executable)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getHasToBeABoxedTypeException$str() {
        return "HV000164: %s has to be a auto-boxed type.";
    }

    @Override
    public final IllegalArgumentException getHasToBeABoxedTypeException(Class<?> clazz) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getHasToBeABoxedTypeException$str(), new ClassObjectFormatter(clazz)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMixingImplicitWithOtherExecutableTypesException$str() {
        return "HV000165: Mixing IMPLICIT and other executable types is not allowed.";
    }

    @Override
    public final IllegalArgumentException getMixingImplicitWithOtherExecutableTypesException() {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMixingImplicitWithOtherExecutableTypesException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getValidateOnExecutionOnOverriddenOrInterfaceMethodException$str() {
        return "HV000166: @ValidateOnExecution is not allowed on methods overriding a superclass method or implementing an interface. Check configuration for %1$s";
    }

    @Override
    public final ValidationException getValidateOnExecutionOnOverriddenOrInterfaceMethodException(Executable executable) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getValidateOnExecutionOnOverriddenOrInterfaceMethodException$str(), new ExecutableFormatter(executable)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getOverridingConstraintDefinitionsInMultipleMappingFilesException$str() {
        return "HV000167: A given constraint definition can only be overridden in one mapping file. %1$s is overridden in multiple files";
    }

    @Override
    public final ValidationException getOverridingConstraintDefinitionsInMultipleMappingFilesException(String constraintClassName) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getOverridingConstraintDefinitionsInMultipleMappingFilesException$str(), constraintClassName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnbalancedBeginEndParameterException$str() {
        return "HV000168: The message descriptor '%1$s' contains an unbalanced meta character '%2$c'.";
    }

    @Override
    public final MessageDescriptorFormatException getUnbalancedBeginEndParameterException(String messageDescriptor, char character) {
        MessageDescriptorFormatException result = new MessageDescriptorFormatException(String.format(this.getLoggingLocale(), this.getUnbalancedBeginEndParameterException$str(), messageDescriptor, Character.valueOf(character)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNestedParameterException$str() {
        return "HV000169: The message descriptor '%1$s' has nested parameters.";
    }

    @Override
    public final MessageDescriptorFormatException getNestedParameterException(String messageDescriptor) {
        MessageDescriptorFormatException result = new MessageDescriptorFormatException(String.format(this.getLoggingLocale(), this.getNestedParameterException$str(), messageDescriptor));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getCreationOfScriptExecutorFailedException$str() {
        return "HV000170: No JSR-223 scripting engine could be bootstrapped for language \"%s\".";
    }

    @Override
    public final ConstraintDeclarationException getCreationOfScriptExecutorFailedException(String languageName, Exception e) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCreationOfScriptExecutorFailedException$str(), languageName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
        return "HV000171: %s is configured more than once via the programmatic constraint declaration API.";
    }

    @Override
    public final ValidationException getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException(Class<?> beanClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
        return "HV000172: Property \"%2$s\" of type %1$s is configured more than once via the programmatic constraint declaration API.";
    }

    @Override
    public final ValidationException getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException(Class<?> beanClass, String propertyName) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), propertyName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMethodHasAlreadyBeenConfiguredViaProgrammaticApiException$str() {
        return "HV000173: Method %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
    }

    @Override
    public final ValidationException getMethodHasAlreadyBeenConfiguredViaProgrammaticApiException(Class<?> beanClass, String method2) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getMethodHasAlreadyBeenConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), method2));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getParameterHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
        return "HV000174: Parameter %3$s of method or constructor %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
    }

    @Override
    public final ValidationException getParameterHasAlreadyBeConfiguredViaProgrammaticApiException(Class<?> beanClass, Callable callable, int parameterIndex) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getParameterHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), callable, parameterIndex));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getReturnValueHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
        return "HV000175: The return value of method or constructor %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
    }

    @Override
    public final ValidationException getReturnValueHasAlreadyBeConfiguredViaProgrammaticApiException(Class<?> beanClass, Callable callable) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getReturnValueHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), callable));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getConstructorHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
        return "HV000176: Constructor %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
    }

    @Override
    public final ValidationException getConstructorHasAlreadyBeConfiguredViaProgrammaticApiException(Class<?> beanClass, String constructor) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstructorHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), constructor));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getCrossParameterElementHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
        return "HV000177: Cross-parameter constraints for the method or constructor %2$s of type %1$s are declared more than once via the programmatic constraint declaration API.";
    }

    @Override
    public final ValidationException getCrossParameterElementHasAlreadyBeConfiguredViaProgrammaticApiException(Class<?> beanClass, Callable callable) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getCrossParameterElementHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), callable));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getMultiplierCannotBeNegativeException$str() {
        return "HV000178: Multiplier cannot be negative: %d.";
    }

    @Override
    public final IllegalArgumentException getMultiplierCannotBeNegativeException(int multiplier) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMultiplierCannotBeNegativeException$str(), multiplier));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getWeightCannotBeNegativeException$str() {
        return "HV000179: Weight cannot be negative: %d.";
    }

    @Override
    public final IllegalArgumentException getWeightCannotBeNegativeException(int weight) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getWeightCannotBeNegativeException$str(), weight));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getTreatCheckAsIsNotADigitNorALetterException$str() {
        return "HV000180: '%c' is not a digit nor a letter.";
    }

    @Override
    public final IllegalArgumentException getTreatCheckAsIsNotADigitNorALetterException(int weight) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getTreatCheckAsIsNotADigitNorALetterException$str(), weight));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidParameterCountForExecutableException$str() {
        return "HV000181: Wrong number of parameters. Method or constructor %1$s expects %2$d parameters, but got %3$d.";
    }

    @Override
    public final IllegalArgumentException getInvalidParameterCountForExecutableException(String executable, int expectedParameterCount, int actualParameterCount) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidParameterCountForExecutableException$str(), executable, expectedParameterCount, actualParameterCount));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNoUnwrapperFoundForTypeException$str() {
        return "HV000182: No validation value unwrapper is registered for type '%1$s'.";
    }

    @Override
    public final ValidationException getNoUnwrapperFoundForTypeException(Type type) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getNoUnwrapperFoundForTypeException$str(), type));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToInitializeELExpressionFactoryException$str() {
        return "HV000183: Unable to initialize 'javax.el.ExpressionFactory'. Check that you have the EL dependencies on the classpath, or use ParameterMessageInterpolator instead";
    }

    @Override
    public final ValidationException getUnableToInitializeELExpressionFactoryException(Throwable e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInitializeELExpressionFactoryException$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void warnElIsUnsupported(String expression) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)null, this.warnElIsUnsupported$str(), (Object)expression);
    }

    protected String warnElIsUnsupported$str() {
        return "HV000185: Message contains EL expression: %1s, which is not supported by the selected message interpolator";
    }

    protected String getInconsistentValueUnwrappingConfigurationBetweenFieldAndItsGetterException$str() {
        return "HV000189: The configuration of value unwrapping for property '%s' of bean '%s' is inconsistent between the field and its getter.";
    }

    @Override
    public final ConstraintDeclarationException getInconsistentValueUnwrappingConfigurationBetweenFieldAndItsGetterException(String property, Class<?> beanClass) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getInconsistentValueUnwrappingConfigurationBetweenFieldAndItsGetterException$str(), property, new ClassObjectFormatter(beanClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToCreateXMLEventReader$str() {
        return "HV000190: Unable to parse %s.";
    }

    @Override
    public final ValidationException getUnableToCreateXMLEventReader(String file, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToCreateXMLEventReader$str(), file), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void unknownJvmVersion(String vmVersionStr) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)null, this.unknownJvmVersion$str(), (Object)vmVersionStr);
    }

    protected String unknownJvmVersion$str() {
        return "HV000192: Couldn't determine Java version from value %1s; Not enabling features requiring Java 8";
    }

    protected String getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException$str() {
        return "HV000193: %s is configured more than once via the programmatic constraint definition API.";
    }

    @Override
    public final ValidationException getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException(Class<? extends Annotation> annotationClass) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(annotationClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getEmptyElementOnlySupportedWhenCharSequenceIsExpectedExpection$str() {
        return "HV000194: An empty element is only supported when a CharSequence is expected.";
    }

    @Override
    public final ValidationException getEmptyElementOnlySupportedWhenCharSequenceIsExpectedExpection() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getEmptyElementOnlySupportedWhenCharSequenceIsExpectedExpection$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToReachPropertyToValidateException$str() {
        return "HV000195: Unable to reach the property to validate for the bean %s and the property path %s. A property is null along the way.";
    }

    @Override
    public final ValidationException getUnableToReachPropertyToValidateException(Object bean, Path path) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToReachPropertyToValidateException$str(), bean, path));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToConvertTypeToClassException$str() {
        return "HV000196: Unable to convert the Type %s to a Class.";
    }

    @Override
    public final ValidationException getUnableToConvertTypeToClassException(Type type) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToConvertTypeToClassException$str(), type));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNoValueExtractorFoundForTypeException2$str() {
        return "HV000197: No value extractor found for type parameter '%2$s' of type %1$s.";
    }

    @Override
    public final ConstraintDeclarationException getNoValueExtractorFoundForTypeException(Class<?> type, TypeVariable<?> typeParameter) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getNoValueExtractorFoundForTypeException2$str(), new ClassObjectFormatter(type), typeParameter));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNoValueExtractorFoundForUnwrapException$str() {
        return "HV000198: No suitable value extractor found for type %1$s.";
    }

    @Override
    public final ConstraintDeclarationException getNoValueExtractorFoundForUnwrapException(Type type) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getNoValueExtractorFoundForUnwrapException$str(), type));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void usingClockProvider(Class<? extends ClockProvider> clockProviderClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingClockProvider$str(), (Object)new ClassObjectFormatter(clockProviderClass));
    }

    protected String usingClockProvider$str() {
        return "HV000200: Using %s as clock provider.";
    }

    protected String getUnableToInstantiateClockProviderClassException$str() {
        return "HV000201: Unable to instantiate clock provider class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateClockProviderClassException(String clockProviderClassName, ValidationException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateClockProviderClassException$str(), clockProviderClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToGetCurrentTimeFromClockProvider$str() {
        return "HV000202: Unable to get the current time from the clock provider";
    }

    @Override
    public final ValidationException getUnableToGetCurrentTimeFromClockProvider(Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToGetCurrentTimeFromClockProvider$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getValueExtractorFailsToDeclareExtractedValueException$str() {
        return "HV000203: Value extractor type %1s fails to declare the extracted type parameter using @ExtractedValue.";
    }

    @Override
    public final ValueExtractorDefinitionException getValueExtractorFailsToDeclareExtractedValueException(Class<?> extractorType) {
        ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getValueExtractorFailsToDeclareExtractedValueException$str(), new ClassObjectFormatter(extractorType)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getValueExtractorDeclaresExtractedValueMultipleTimesException$str() {
        return "HV000204: Only one type parameter must be marked with @ExtractedValue for value extractor type %1s.";
    }

    @Override
    public final ValueExtractorDefinitionException getValueExtractorDeclaresExtractedValueMultipleTimesException(Class<?> extractorType) {
        ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getValueExtractorDeclaresExtractedValueMultipleTimesException$str(), new ClassObjectFormatter(extractorType)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidUnwrappingConfigurationForConstraintException$str() {
        return "HV000205: Invalid unwrapping configuration for constraint %2$s on %1$s. You can only define one of 'Unwrapping.Skip' or 'Unwrapping.Unwrap'.";
    }

    @Override
    public final ConstraintDeclarationException getInvalidUnwrappingConfigurationForConstraintException(Constrainable constrainable, Class<? extends Annotation> constraint) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getInvalidUnwrappingConfigurationForConstraintException$str(), constrainable, new ClassObjectFormatter(constraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToInstantiateValueExtractorClassException$str() {
        return "HV000206: Unable to instantiate value extractor class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateValueExtractorClassException(String valueExtractorClassName, ValidationException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateValueExtractorClassException$str(), valueExtractorClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void addingValueExtractor(Class<? extends ValueExtractor<?>> valueExtractorClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.addingValueExtractor$str(), (Object)new ClassObjectFormatter(valueExtractorClass));
    }

    protected String addingValueExtractor$str() {
        return "HV000207: Adding value extractor %s.";
    }

    protected String getValueExtractorForTypeAndTypeUseAlreadyPresentException$str() {
        return "HV000208: Given value extractor %2$s handles the same type and type use as previously given value extractor %1$s.";
    }

    @Override
    public final ValueExtractorDeclarationException getValueExtractorForTypeAndTypeUseAlreadyPresentException(ValueExtractor<?> first, ValueExtractor<?> second) {
        ValueExtractorDeclarationException result = new ValueExtractorDeclarationException(String.format(this.getLoggingLocale(), this.getValueExtractorForTypeAndTypeUseAlreadyPresentException$str(), first, second));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getCannotMixDirectAnnotationAndListContainerOnComposedConstraintException$str() {
        return "HV000209: A composing constraint (%2$s) must not be given directly on the composed constraint (%1$s) and using the corresponding List annotation at the same time.";
    }

    @Override
    public final ConstraintDeclarationException getCannotMixDirectAnnotationAndListContainerOnComposedConstraintException(Class<? extends Annotation> composedConstraint, Class<? extends Annotation> composingConstraint) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCannotMixDirectAnnotationAndListContainerOnComposedConstraintException$str(), new ClassObjectFormatter(composedConstraint), new ClassObjectFormatter(composingConstraint)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToFindTypeParameterInClass$str() {
        return "HV000210: Unable to find the type parameter %2$s in class %1$s.";
    }

    @Override
    public final IllegalArgumentException getUnableToFindTypeParameterInClass(Class<?> clazz, Object typeParameterReference) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getUnableToFindTypeParameterInClass$str(), new ClassObjectFormatter(clazz), typeParameterReference));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getTypeIsNotAParameterizedNorArrayTypeException$str() {
        return "HV000211: Given type is neither a parameterized nor an array type: %s.";
    }

    @Override
    public final ValidationException getTypeIsNotAParameterizedNorArrayTypeException(Type type) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getTypeIsNotAParameterizedNorArrayTypeException$str(), new TypeFormatter(type)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getInvalidTypeArgumentIndexException$str() {
        return "HV000212: Given type has no type argument with index %2$s: %1$s.";
    }

    @Override
    public final ValidationException getInvalidTypeArgumentIndexException(Type type, int index) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidTypeArgumentIndexException$str(), new TypeFormatter(type), index));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNoTypeArgumentIndexIsGivenForTypeWithMultipleTypeArgumentsException$str() {
        return "HV000213: Given type has more than one type argument, hence an argument index must be specified: %s.";
    }

    @Override
    public final ValidationException getNoTypeArgumentIndexIsGivenForTypeWithMultipleTypeArgumentsException(Type type) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getNoTypeArgumentIndexIsGivenForTypeWithMultipleTypeArgumentsException$str(), new TypeFormatter(type)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getContainerElementTypeHasAlreadyBeenConfiguredViaProgrammaticApiException$str() {
        return "HV000214: The same container element type of type %1$s is configured more than once via the programmatic constraint declaration API.";
    }

    @Override
    public final ValidationException getContainerElementTypeHasAlreadyBeenConfiguredViaProgrammaticApiException(Type type) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getContainerElementTypeHasAlreadyBeenConfiguredViaProgrammaticApiException$str(), new TypeFormatter(type)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getParameterIsNotAValidCallException$str() {
        return "HV000215: Calling parameter() is not allowed for the current element.";
    }

    @Override
    public final ValidationException getParameterIsNotAValidCallException() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getParameterIsNotAValidCallException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getReturnValueIsNotAValidCallException$str() {
        return "HV000216: Calling returnValue() is not allowed for the current element.";
    }

    @Override
    public final ValidationException getReturnValueIsNotAValidCallException() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getReturnValueIsNotAValidCallException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getContainerElementTypeHasAlreadyBeenConfiguredViaXmlMappingConfigurationException$str() {
        return "HV000217: The same container element type %2$s is configured more than once for location %1$s via the XML mapping configuration.";
    }

    @Override
    public final ValidationException getContainerElementTypeHasAlreadyBeenConfiguredViaXmlMappingConfigurationException(ConstraintLocation rootConstraintLocation, ContainerElementTypePath path) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getContainerElementTypeHasAlreadyBeenConfiguredViaXmlMappingConfigurationException$str(), rootConstraintLocation, path));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getParallelDefinitionsOfValueExtractorsException$str() {
        return "HV000218: Having parallel definitions of value extractors on a given class is not allowed: %s.";
    }

    @Override
    public final ValueExtractorDefinitionException getParallelDefinitionsOfValueExtractorsException(Class<?> extractorImplementationType) {
        ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getParallelDefinitionsOfValueExtractorsException$str(), new ClassObjectFormatter(extractorImplementationType)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToGetMostSpecificValueExtractorDueToSeveralMaximallySpecificValueExtractorsDeclaredException$str() {
        return "HV000219: Unable to get the most specific value extractor for type %1$s as several most specific value extractors are declared: %2$s.";
    }

    @Override
    public final ConstraintDeclarationException getUnableToGetMostSpecificValueExtractorDueToSeveralMaximallySpecificValueExtractorsDeclaredException(Class<?> valueType, Collection<Class<? extends ValueExtractor>> valueExtractors) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getUnableToGetMostSpecificValueExtractorDueToSeveralMaximallySpecificValueExtractorsDeclaredException$str(), new ClassObjectFormatter(valueType), new CollectionOfClassesObjectFormatter(valueExtractors)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getExtractedValueOnTypeParameterOfContainerTypeMayNotDefineTypeAttributeException$str() {
        return "HV000220: When @ExtractedValue is defined on a type parameter of a container type, the type attribute may not be set: %1$s.";
    }

    @Override
    public final ValueExtractorDefinitionException getExtractedValueOnTypeParameterOfContainerTypeMayNotDefineTypeAttributeException(Class<? extends ValueExtractor> extractorImplementationType) {
        ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getExtractedValueOnTypeParameterOfContainerTypeMayNotDefineTypeAttributeException$str(), new ClassObjectFormatter(extractorImplementationType)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getErrorWhileExtractingValuesInValueExtractorException$str() {
        return "HV000221: An error occurred while extracting values in value extractor %1$s.";
    }

    @Override
    public final ValidationException getErrorWhileExtractingValuesInValueExtractorException(Class<? extends ValueExtractor> extractorImplementationType, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getErrorWhileExtractingValuesInValueExtractorException$str(), new ClassObjectFormatter(extractorImplementationType)), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getDuplicateDefinitionsOfValueExtractorException$str() {
        return "HV000222: The same value extractor %s is added more than once via the XML configuration.";
    }

    @Override
    public final ValueExtractorDeclarationException getDuplicateDefinitionsOfValueExtractorException(String className) {
        ValueExtractorDeclarationException result = new ValueExtractorDeclarationException(String.format(this.getLoggingLocale(), this.getDuplicateDefinitionsOfValueExtractorException$str(), className));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getImplicitUnwrappingNotAllowedWhenSeveralMaximallySpecificValueExtractorsMarkedWithUnwrapByDefaultDeclaredException$str() {
        return "HV000223: Implicit unwrapping is not allowed for type %1$s as several maximally specific value extractors marked with @UnwrapByDefault are declared: %2$s.";
    }

    @Override
    public final ConstraintDeclarationException getImplicitUnwrappingNotAllowedWhenSeveralMaximallySpecificValueExtractorsMarkedWithUnwrapByDefaultDeclaredException(Class<?> valueType, Collection<Class<? extends ValueExtractor>> valueExtractors) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getImplicitUnwrappingNotAllowedWhenSeveralMaximallySpecificValueExtractorsMarkedWithUnwrapByDefaultDeclaredException$str(), new ClassObjectFormatter(valueType), new CollectionOfClassesObjectFormatter(valueExtractors)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnwrappingOfConstraintDescriptorNotSupportedYetException$str() {
        return "HV000224: Unwrapping of ConstraintDescriptor is not supported yet.";
    }

    @Override
    public final ValidationException getUnwrappingOfConstraintDescriptorNotSupportedYetException() {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnwrappingOfConstraintDescriptorNotSupportedYetException$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getOnlyUnboundWildcardTypeArgumentsSupportedForContainerTypeOfValueExtractorException$str() {
        return "HV000225: Only unbound wildcard type arguments are supported for the container type of the value extractor: %1$s.";
    }

    @Override
    public final ValueExtractorDefinitionException getOnlyUnboundWildcardTypeArgumentsSupportedForContainerTypeOfValueExtractorException(Class<? extends ValueExtractor> valueExtractorClass) {
        ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getOnlyUnboundWildcardTypeArgumentsSupportedForContainerTypeOfValueExtractorException$str(), new ClassObjectFormatter(valueExtractorClass)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getContainerElementConstraintsAndCascadedValidationNotSupportedOnArraysException$str() {
        return "HV000226: Container element constraints and cascading validation are not supported on arrays: %1$s";
    }

    @Override
    public final ValidationException getContainerElementConstraintsAndCascadedValidationNotSupportedOnArraysException(Type type) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getContainerElementConstraintsAndCascadedValidationNotSupportedOnArraysException$str(), new TypeFormatter(type)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getPropertyNotDefinedByValidatedTypeException$str() {
        return "HV000227: The validated type %1$s does not specify the property: %2$s";
    }

    @Override
    public final IllegalArgumentException getPropertyNotDefinedByValidatedTypeException(Class<?> validatedType, String propertyName) {
        IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getPropertyNotDefinedByValidatedTypeException$str(), new ClassObjectFormatter(validatedType), propertyName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getNoValueExtractorFoundForTypeException3$str() {
        return "HV000228: No value extractor found when narrowing down to the runtime type %3$s among the value extractors for type parameter '%2$s' of type %1$s.";
    }

    @Override
    public final ConstraintDeclarationException getNoValueExtractorFoundForTypeException(Type declaredType, TypeVariable<?> declaredTypeParameter, Class<?> valueType) {
        ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getNoValueExtractorFoundForTypeException3$str(), new TypeFormatter(declaredType), declaredTypeParameter, new ClassObjectFormatter(valueType)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToCastException$str() {
        return "HV000229: Unable to cast %1$s to %2$s.";
    }

    @Override
    public final ClassCastException getUnableToCastException(Object object, Class<?> clazz) {
        ClassCastException result = new ClassCastException(String.format(this.getLoggingLocale(), this.getUnableToCastException$str(), object, new ClassObjectFormatter(clazz)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void usingScriptEvaluatorFactory(Class<? extends ScriptEvaluatorFactory> scriptEvaluatorFactoryClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingScriptEvaluatorFactory$str(), (Object)new ClassObjectFormatter(scriptEvaluatorFactoryClass));
    }

    protected String usingScriptEvaluatorFactory$str() {
        return "HV000230: Using %s as script evaluator factory.";
    }

    protected String getUnableToInstantiateScriptEvaluatorFactoryClassException$str() {
        return "HV000231: Unable to instantiate script evaluator factory class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateScriptEvaluatorFactoryClassException(String scriptEvaluatorFactoryClassName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateScriptEvaluatorFactoryClassException$str(), scriptEvaluatorFactoryClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToFindScriptEngineException$str() {
        return "HV000232: No JSR 223 script engine found for language \"%s\".";
    }

    @Override
    public final ScriptEvaluatorNotFoundException getUnableToFindScriptEngineException(String languageName) {
        ScriptEvaluatorNotFoundException result = new ScriptEvaluatorNotFoundException(String.format(this.getLoggingLocale(), this.getUnableToFindScriptEngineException$str(), languageName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getErrorExecutingScriptException$str() {
        return "HV000233: An error occurred while executing the script: \"%s\".";
    }

    @Override
    public final ScriptEvaluationException getErrorExecutingScriptException(String script, Exception e) {
        ScriptEvaluationException result = new ScriptEvaluationException(String.format(this.getLoggingLocale(), this.getErrorExecutingScriptException$str(), script), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void logValidatorFactoryScopedConfiguration(Class<?> configuredClass, String configuredElement) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.logValidatorFactoryScopedConfiguration$str(), (Object)new ClassObjectFormatter(configuredClass), (Object)configuredElement);
    }

    protected String logValidatorFactoryScopedConfiguration$str() {
        return "HV000234: Using %1$s as ValidatorFactory-scoped %2$s.";
    }

    protected String getUnableToCreateAnnotationDescriptor$str() {
        return "HV000235: Unable to create an annotation descriptor for %1$s.";
    }

    @Override
    public final ValidationException getUnableToCreateAnnotationDescriptor(Class<?> configuredClass, Throwable e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToCreateAnnotationDescriptor$str(), new ClassObjectFormatter(configuredClass)), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToFindAnnotationDefDeclaredMethods$str() {
        return "HV000236: Unable to find the method required to create the constraint annotation descriptor.";
    }

    @Override
    public final ValidationException getUnableToFindAnnotationDefDeclaredMethods(Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToFindAnnotationDefDeclaredMethods$str(), new Object[0]), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnableToAccessMethodException$str() {
        return "HV000237: Unable to access method %3$s of class %2$s with parameters %4$s using lookup %1$s.";
    }

    @Override
    public final ValidationException getUnableToAccessMethodException(MethodHandles.Lookup lookup, Class<?> clazz, String methodName, Object[] parameterTypes, Throwable e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToAccessMethodException$str(), lookup, new ClassObjectFormatter(clazz), methodName, new ObjectArrayFormatter(parameterTypes)), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void logTemporalValidationTolerance(Duration tolerance) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.logTemporalValidationTolerance$str(), (Object)new DurationFormatter(tolerance));
    }

    protected String logTemporalValidationTolerance$str() {
        return "HV000238: Temporal validation tolerance set to %1$s.";
    }

    protected String getUnableToParseTemporalValidationToleranceException$str() {
        return "HV000239: Unable to parse the temporal validation tolerance property %s. It should be a duration represented in milliseconds.";
    }

    @Override
    public final ValidationException getUnableToParseTemporalValidationToleranceException(String toleranceProperty, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToParseTemporalValidationToleranceException$str(), toleranceProperty), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void logConstraintValidatorPayload(Object payload) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.logConstraintValidatorPayload$str(), payload);
    }

    protected String logConstraintValidatorPayload$str() {
        return "HV000240: Constraint validator payload set to %1$s.";
    }

    protected String logUnknownElementInXmlConfiguration$str() {
        return "HV000241: Encountered unsupported element %1$s while parsing the XML configuration.";
    }

    @Override
    public final ValidationException logUnknownElementInXmlConfiguration(String tag) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.logUnknownElementInXmlConfiguration$str(), tag));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void logUnableToLoadOrInstantiateJPAAwareResolver(String traversableResolverClassName) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)null, this.logUnableToLoadOrInstantiateJPAAwareResolver$str(), (Object)traversableResolverClassName);
    }

    protected String logUnableToLoadOrInstantiateJPAAwareResolver$str() {
        return "HV000242: Unable to load or instantiate JPA aware resolver %1$s. All properties will per default be traversable.";
    }

    protected String getConstraintValidatorDefinitionConstraintMismatchException$str() {
        return "HV000243: Constraint %2$s references constraint validator type %1$s, but this validator is defined for constraint type %3$s.";
    }

    @Override
    public final ConstraintDefinitionException getConstraintValidatorDefinitionConstraintMismatchException(Class<? extends ConstraintValidator<?, ?>> constraintValidatorImplementationType, Class<? extends Annotation> registeredConstraintAnnotationType, Type declaredConstraintAnnotationType) {
        ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getConstraintValidatorDefinitionConstraintMismatchException$str(), new ClassObjectFormatter(constraintValidatorImplementationType), new ClassObjectFormatter(registeredConstraintAnnotationType), new TypeFormatter(declaredConstraintAnnotationType)));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String getUnexpectedConstraintElementType$str() {
        return "HV000244: ConstrainedElement expected class was %1$s, but instead received %2$s.";
    }

    @Override
    public final AssertionError getUnexpectedConstraintElementType(Class<?> expecting, Class<?> got) {
        AssertionError result = new AssertionError((Object)String.format(this.getLoggingLocale(), this.getUnexpectedConstraintElementType$str(), new ClassObjectFormatter(expecting), new ClassObjectFormatter(got)));
        Log_$logger._copyStackTraceMinusOne((Throwable)((Object)result));
        return result;
    }

    protected String getUnsupportedConstraintElementType$str() {
        return "HV000245: Allowed constraint element types are FIELD and GETTER, but instead received %1$s.";
    }

    @Override
    public final AssertionError getUnsupportedConstraintElementType(ConstrainedElement.ConstrainedElementKind kind) {
        AssertionError result = new AssertionError((Object)String.format(this.getLoggingLocale(), this.getUnsupportedConstraintElementType$str(), new Object[]{kind}));
        Log_$logger._copyStackTraceMinusOne((Throwable)((Object)result));
        return result;
    }

    @Override
    public final void usingGetterPropertySelectionStrategy(Class<? extends GetterPropertySelectionStrategy> getterPropertySelectionStrategyClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingGetterPropertySelectionStrategy$str(), (Object)new ClassObjectFormatter(getterPropertySelectionStrategyClass));
    }

    protected String usingGetterPropertySelectionStrategy$str() {
        return "HV000246: Using %s as getter property selection strategy.";
    }

    protected String getUnableToInstantiateGetterPropertySelectionStrategyClassException$str() {
        return "HV000247: Unable to instantiate getter property selection strategy class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateGetterPropertySelectionStrategyClassException(String getterPropertySelectionStrategyClassName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateGetterPropertySelectionStrategyClassException$str(), getterPropertySelectionStrategyClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String unableToGetXmlSchema$str() {
        return "HV000248: Unable to get an XML schema named %s.";
    }

    @Override
    public final ValidationException unableToGetXmlSchema(String schemaResourceName) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.unableToGetXmlSchema$str(), schemaResourceName));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String uninitializedLocale$str() {
        return "HV000250: Uninitialized locale: %s. Please register your locale as a locale to initialize when initializing your ValidatorFactory.";
    }

    @Override
    public final ValidationException uninitializedLocale(Locale locale) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.uninitializedLocale$str(), locale));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void unableToLoadInstanceOfService(String serviceName, ServiceConfigurationError e) {
        this.log.logf(FQCN, Logger.Level.ERROR, (Throwable)e, this.unableToLoadInstanceOfService$str(), (Object)serviceName);
    }

    protected String unableToLoadInstanceOfService$str() {
        return "HV000251: An error occurred while loading an instance of service %s.";
    }

    @Override
    public final void usingPropertyNodeNameProvider(Class<? extends PropertyNodeNameProvider> propertyNodeNameProviderClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingPropertyNodeNameProvider$str(), (Object)new ClassObjectFormatter(propertyNodeNameProviderClass));
    }

    protected String usingPropertyNodeNameProvider$str() {
        return "HV000252: Using %s as property node name provider.";
    }

    protected String getUnableToInstantiatePropertyNodeNameProviderClassException$str() {
        return "HV000253: Unable to instantiate property node name provider class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiatePropertyNodeNameProviderClassException(String propertyNodeNameProviderClassName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiatePropertyNodeNameProviderClassException$str(), propertyNodeNameProviderClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void missingParameterMetadataWithSyntheticOrImplicitParameters(Executable executable) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)null, this.missingParameterMetadataWithSyntheticOrImplicitParameters$str(), (Object)new ExecutableFormatter(executable));
    }

    protected String missingParameterMetadataWithSyntheticOrImplicitParameters$str() {
        return "HV000254: Missing parameter metadata for %s, which declares implicit or synthetic parameters. Automatic resolution of generic type information for method parameters may yield incorrect results if multiple parameters have the same erasure. To solve this, compile your code with the '-parameters' flag.";
    }

    @Override
    public final void usingLocaleResolver(Class<? extends LocaleResolver> localeResolverClass) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.usingLocaleResolver$str(), (Object)new ClassObjectFormatter(localeResolverClass));
    }

    protected String usingLocaleResolver$str() {
        return "HV000255: Using %s as locale resolver.";
    }

    protected String getUnableToInstantiateLocaleResolverClassException$str() {
        return "HV000256: Unable to instantiate locale resolver class %s.";
    }

    @Override
    public final ValidationException getUnableToInstantiateLocaleResolverClassException(String localeResolverClassName, Exception e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateLocaleResolverClassException$str(), localeResolverClassName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void expressionVariablesDefinedWithExpressionLanguageNotEnabled(Class<? extends Annotation> constraintAnnotation) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)null, this.expressionVariablesDefinedWithExpressionLanguageNotEnabled$str(), (Object)constraintAnnotation);
    }

    protected String expressionVariablesDefinedWithExpressionLanguageNotEnabled$str() {
        return "HV000257: Expression variables have been defined for constraint %1$s while Expression Language is not enabled.";
    }

    protected String expressionsNotResolvedWhenExpressionLanguageFeaturesDisabled$str() {
        return "HV000258: Expressions should not be resolved when Expression Language features are disabled.";
    }

    @Override
    public final IllegalStateException expressionsNotResolvedWhenExpressionLanguageFeaturesDisabled() {
        IllegalStateException result = new IllegalStateException(String.format(this.getLoggingLocale(), this.expressionsNotResolvedWhenExpressionLanguageFeaturesDisabled$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    protected String expressionsLanguageFeatureLevelNotSupported$str() {
        return "HV000259: Provided Expression Language feature level is not supported.";
    }

    @Override
    public final IllegalStateException expressionsLanguageFeatureLevelNotSupported() {
        IllegalStateException result = new IllegalStateException(String.format(this.getLoggingLocale(), this.expressionsLanguageFeatureLevelNotSupported$str(), new Object[0]));
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void logConstraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.logConstraintExpressionLanguageFeatureLevel$str(), (Object)expressionLanguageFeatureLevel);
    }

    protected String logConstraintExpressionLanguageFeatureLevel$str() {
        return "HV000260: Expression Language feature level for constraints set to %1$s.";
    }

    @Override
    public final void logCustomViolationExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel) {
        this.log.logf(FQCN, Logger.Level.DEBUG, (Throwable)null, this.logCustomViolationExpressionLanguageFeatureLevel$str(), (Object)expressionLanguageFeatureLevel);
    }

    protected String logCustomViolationExpressionLanguageFeatureLevel$str() {
        return "HV000261: Expression Language feature level for custom violations set to %1$s.";
    }

    protected String invalidExpressionLanguageFeatureLevelValue$str() {
        return "HV000262: Unable to find an expression language feature level for value %s.";
    }

    @Override
    public final ValidationException invalidExpressionLanguageFeatureLevelValue(String expressionLanguageFeatureLevelName, IllegalArgumentException e) {
        ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.invalidExpressionLanguageFeatureLevelValue$str(), expressionLanguageFeatureLevelName), e);
        Log_$logger._copyStackTraceMinusOne(result);
        return result;
    }

    @Override
    public final void unknownMethodInExpressionLanguage(String expression, Exception e) {
        this.log.logf(FQCN, Logger.Level.WARN, (Throwable)e, this.unknownMethodInExpressionLanguage$str(), (Object)expression);
    }

    protected String unknownMethodInExpressionLanguage$str() {
        return "HV000263: EL expression '%s' references an unknown method.";
    }

    @Override
    public final void disabledFeatureInExpressionLanguage(String expression, Exception e) {
        this.log.logf(FQCN, Logger.Level.ERROR, (Throwable)e, this.disabledFeatureInExpressionLanguage$str(), (Object)expression);
    }

    protected String disabledFeatureInExpressionLanguage$str() {
        return "HV000264: Unable to interpolate EL expression '%s' as it uses a disabled feature.";
    }
}

