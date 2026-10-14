/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.Validator;
import javax.validation.spi.ConfigurationState;
import org.hibernate.validator.HibernateValidatorContext;
import org.hibernate.validator.HibernateValidatorFactory;
import org.hibernate.validator.PredefinedScopeHibernateValidatorFactory;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.engine.MethodValidationConfiguration;
import org.hibernate.validator.internal.engine.PredefinedScopeConfigurationImpl;
import org.hibernate.validator.internal.engine.PredefinedScopeValidatorContextImpl;
import org.hibernate.validator.internal.engine.ValidatorFactoryConfigurationHelper;
import org.hibernate.validator.internal.engine.ValidatorFactoryScopedContext;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.constraintvalidation.PredefinedScopeConstraintValidatorManagerImpl;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.PredefinedScopeBeanMetaDataManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.provider.MetaDataProvider;
import org.hibernate.validator.internal.metadata.provider.ProgrammaticMetaDataProvider;
import org.hibernate.validator.internal.metadata.provider.XmlMetaDataProvider;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.hibernate.validator.spi.properties.GetterPropertySelectionStrategy;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public class PredefinedScopeValidatorFactoryImpl
implements PredefinedScopeHibernateValidatorFactory {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final ValidatorFactoryScopedContext validatorFactoryScopedContext;
    private final ConstraintValidatorManager constraintValidatorManager;
    private final MethodValidationConfiguration methodValidationConfiguration;
    private final PredefinedScopeBeanMetaDataManager beanMetaDataManager;
    private final ValueExtractorManager valueExtractorManager;
    private final GetterPropertySelectionStrategy getterPropertySelectionStrategy;
    private final PropertyNodeNameProvider propertyNodeNameProvider;
    private final ValidationOrderGenerator validationOrderGenerator;

    public PredefinedScopeValidatorFactoryImpl(ConfigurationState configurationState) {
        Contracts.assertTrue(configurationState instanceof PredefinedScopeConfigurationImpl, "Only PredefinedScopeConfigurationImpl is supported.");
        ClassLoader externalClassLoader = ValidatorFactoryConfigurationHelper.determineExternalClassLoader(configurationState);
        PredefinedScopeConfigurationImpl hibernateSpecificConfig = (PredefinedScopeConfigurationImpl)configurationState;
        Map<String, String> properties = configurationState.getProperties();
        this.methodValidationConfiguration = new MethodValidationConfiguration.Builder().allowOverridingMethodAlterParameterConstraint(ValidatorFactoryConfigurationHelper.determineAllowOverridingMethodAlterParameterConstraint(hibernateSpecificConfig, properties)).allowMultipleCascadedValidationOnReturnValues(ValidatorFactoryConfigurationHelper.determineAllowMultipleCascadedValidationOnReturnValues(hibernateSpecificConfig, properties)).allowParallelMethodsDefineParameterConstraints(ValidatorFactoryConfigurationHelper.determineAllowParallelMethodsDefineParameterConstraints(hibernateSpecificConfig, properties)).build();
        this.validatorFactoryScopedContext = new ValidatorFactoryScopedContext(configurationState.getMessageInterpolator(), configurationState.getTraversableResolver(), new ExecutableParameterNameProvider(configurationState.getParameterNameProvider()), configurationState.getClockProvider(), ValidatorFactoryConfigurationHelper.determineTemporalValidationTolerance(configurationState, properties), ValidatorFactoryConfigurationHelper.determineScriptEvaluatorFactory(configurationState, properties, externalClassLoader), ValidatorFactoryConfigurationHelper.determineFailFast(hibernateSpecificConfig, properties), ValidatorFactoryConfigurationHelper.determineTraversableResolverResultCacheEnabled(hibernateSpecificConfig, properties), ValidatorFactoryConfigurationHelper.determineConstraintValidatorPayload(hibernateSpecificConfig), ValidatorFactoryConfigurationHelper.determineConstraintExpressionLanguageFeatureLevel(hibernateSpecificConfig, properties), ValidatorFactoryConfigurationHelper.determineCustomViolationExpressionLanguageFeatureLevel(hibernateSpecificConfig, properties));
        this.constraintValidatorManager = new PredefinedScopeConstraintValidatorManagerImpl(configurationState.getConstraintValidatorFactory(), this.validatorFactoryScopedContext.getConstraintValidatorInitializationContext());
        this.validationOrderGenerator = new ValidationOrderGenerator();
        this.getterPropertySelectionStrategy = ValidatorFactoryConfigurationHelper.determineGetterPropertySelectionStrategy(hibernateSpecificConfig, properties, externalClassLoader);
        this.propertyNodeNameProvider = ValidatorFactoryConfigurationHelper.determinePropertyNodeNameProvider(hibernateSpecificConfig, properties, externalClassLoader);
        this.valueExtractorManager = new ValueExtractorManager(configurationState.getValueExtractors());
        ConstraintHelper constraintHelper = ConstraintHelper.forBuiltinConstraints(hibernateSpecificConfig.getBuiltinConstraints());
        TypeResolutionHelper typeResolutionHelper = new TypeResolutionHelper();
        ConstraintCreationContext constraintCreationContext = new ConstraintCreationContext(constraintHelper, this.constraintValidatorManager, typeResolutionHelper, this.valueExtractorManager);
        ExecutableHelper executableHelper = new ExecutableHelper(typeResolutionHelper);
        JavaBeanHelper javaBeanHelper = new JavaBeanHelper(this.getterPropertySelectionStrategy, this.propertyNodeNameProvider);
        XmlMetaDataProvider xmlMetaDataProvider = configurationState.getMappingStreams().isEmpty() ? null : new XmlMetaDataProvider(constraintCreationContext, javaBeanHelper, configurationState.getMappingStreams(), externalClassLoader);
        Set<DefaultConstraintMapping> constraintMappings = Collections.unmodifiableSet(ValidatorFactoryConfigurationHelper.determineConstraintMappings(typeResolutionHelper, configurationState, javaBeanHelper, externalClassLoader));
        ValidatorFactoryConfigurationHelper.registerCustomConstraintValidators(constraintMappings, constraintHelper);
        this.beanMetaDataManager = new PredefinedScopeBeanMetaDataManager(constraintCreationContext, executableHelper, this.validatorFactoryScopedContext.getParameterNameProvider(), javaBeanHelper, this.validationOrderGenerator, PredefinedScopeValidatorFactoryImpl.buildMetaDataProviders(constraintCreationContext, xmlMetaDataProvider, constraintMappings), this.methodValidationConfiguration, ValidatorFactoryConfigurationHelper.determineBeanMetaDataClassNormalizer(hibernateSpecificConfig), hibernateSpecificConfig.getBeanClassesToInitialize());
        if (LOG.isDebugEnabled()) {
            ValidatorFactoryConfigurationHelper.logValidatorFactoryScopedConfiguration(this.validatorFactoryScopedContext);
        }
    }

    @Override
    public Validator getValidator() {
        return this.createValidator(this.validatorFactoryScopedContext);
    }

    @Override
    public MessageInterpolator getMessageInterpolator() {
        return this.validatorFactoryScopedContext.getMessageInterpolator();
    }

    @Override
    public TraversableResolver getTraversableResolver() {
        return this.validatorFactoryScopedContext.getTraversableResolver();
    }

    @Override
    public ConstraintValidatorFactory getConstraintValidatorFactory() {
        return this.constraintValidatorManager.getDefaultConstraintValidatorFactory();
    }

    @Override
    public ParameterNameProvider getParameterNameProvider() {
        return this.validatorFactoryScopedContext.getParameterNameProvider().getDelegate();
    }

    public ExecutableParameterNameProvider getExecutableParameterNameProvider() {
        return this.validatorFactoryScopedContext.getParameterNameProvider();
    }

    @Override
    public ClockProvider getClockProvider() {
        return this.validatorFactoryScopedContext.getClockProvider();
    }

    @Override
    public ScriptEvaluatorFactory getScriptEvaluatorFactory() {
        return this.validatorFactoryScopedContext.getScriptEvaluatorFactory();
    }

    @Override
    public Duration getTemporalValidationTolerance() {
        return this.validatorFactoryScopedContext.getTemporalValidationTolerance();
    }

    @Override
    public GetterPropertySelectionStrategy getGetterPropertySelectionStrategy() {
        return this.getterPropertySelectionStrategy;
    }

    @Override
    public PropertyNodeNameProvider getPropertyNodeNameProvider() {
        return this.propertyNodeNameProvider;
    }

    public boolean isFailFast() {
        return this.validatorFactoryScopedContext.isFailFast();
    }

    public boolean isTraversableResolverResultCacheEnabled() {
        return this.validatorFactoryScopedContext.isTraversableResolverResultCacheEnabled();
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        if (type.isAssignableFrom(PredefinedScopeHibernateValidatorFactory.class) || type.isAssignableFrom(HibernateValidatorFactory.class)) {
            return type.cast(this);
        }
        throw LOG.getTypeNotSupportedForUnwrappingException(type);
    }

    @Override
    public HibernateValidatorContext usingContext() {
        return new PredefinedScopeValidatorContextImpl(this);
    }

    @Override
    public void close() {
        this.constraintValidatorManager.clear();
        this.beanMetaDataManager.clear();
        this.validatorFactoryScopedContext.getScriptEvaluatorFactory().clear();
        this.valueExtractorManager.clear();
    }

    public ValidatorFactoryScopedContext getValidatorFactoryScopedContext() {
        return this.validatorFactoryScopedContext;
    }

    Validator createValidator(ValidatorFactoryScopedContext validatorFactoryScopedContext) {
        return new ValidatorImpl(this.constraintValidatorManager.getDefaultConstraintValidatorFactory(), this.beanMetaDataManager, this.valueExtractorManager, this.constraintValidatorManager, this.validationOrderGenerator, validatorFactoryScopedContext);
    }

    private static List<MetaDataProvider> buildMetaDataProviders(ConstraintCreationContext constraintCreationContext, XmlMetaDataProvider xmlMetaDataProvider, Set<DefaultConstraintMapping> constraintMappings) {
        ArrayList<MetaDataProvider> metaDataProviders = CollectionHelper.newArrayList();
        if (xmlMetaDataProvider != null) {
            metaDataProviders.add(xmlMetaDataProvider);
        }
        if (!constraintMappings.isEmpty()) {
            metaDataProviders.add(new ProgrammaticMetaDataProvider(constraintCreationContext, constraintMappings));
        }
        return metaDataProviders;
    }
}

