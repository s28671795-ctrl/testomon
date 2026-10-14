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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.Validator;
import javax.validation.spi.ConfigurationState;
import org.hibernate.validator.HibernateValidatorContext;
import org.hibernate.validator.HibernateValidatorFactory;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.hibernate.validator.internal.engine.ConfigurationImpl;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.engine.MethodValidationConfiguration;
import org.hibernate.validator.internal.engine.ValidatorContextImpl;
import org.hibernate.validator.internal.engine.ValidatorFactoryConfigurationHelper;
import org.hibernate.validator.internal.engine.ValidatorFactoryScopedContext;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManagerImpl;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.metadata.BeanMetaDataManagerImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.provider.MetaDataProvider;
import org.hibernate.validator.internal.metadata.provider.ProgrammaticMetaDataProvider;
import org.hibernate.validator.internal.metadata.provider.XmlMetaDataProvider;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.metadata.BeanMetaDataClassNormalizer;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.hibernate.validator.spi.properties.GetterPropertySelectionStrategy;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public class ValidatorFactoryImpl
implements HibernateValidatorFactory {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final ValidatorFactoryScopedContext validatorFactoryScopedContext;
    private final Set<DefaultConstraintMapping> constraintMappings;
    private final ConstraintCreationContext constraintCreationContext;
    private final ExecutableHelper executableHelper;
    private final MethodValidationConfiguration methodValidationConfiguration;
    private final XmlMetaDataProvider xmlMetaDataProvider;
    private final ConcurrentMap<BeanMetaDataManagerKey, BeanMetaDataManager> beanMetaDataManagers = new ConcurrentHashMap<BeanMetaDataManagerKey, BeanMetaDataManager>();
    private final JavaBeanHelper javaBeanHelper;
    private final BeanMetaDataClassNormalizer beanMetadataClassNormalizer;
    private final ValidationOrderGenerator validationOrderGenerator;

    public ValidatorFactoryImpl(ConfigurationState configurationState) {
        ClassLoader externalClassLoader = ValidatorFactoryConfigurationHelper.determineExternalClassLoader(configurationState);
        ConfigurationImpl hibernateSpecificConfig = null;
        if (configurationState instanceof ConfigurationImpl) {
            hibernateSpecificConfig = (ConfigurationImpl)configurationState;
        }
        Map<String, String> properties = configurationState.getProperties();
        this.methodValidationConfiguration = new MethodValidationConfiguration.Builder().allowOverridingMethodAlterParameterConstraint(ValidatorFactoryConfigurationHelper.determineAllowOverridingMethodAlterParameterConstraint(hibernateSpecificConfig, properties)).allowMultipleCascadedValidationOnReturnValues(ValidatorFactoryConfigurationHelper.determineAllowMultipleCascadedValidationOnReturnValues(hibernateSpecificConfig, properties)).allowParallelMethodsDefineParameterConstraints(ValidatorFactoryConfigurationHelper.determineAllowParallelMethodsDefineParameterConstraints(hibernateSpecificConfig, properties)).build();
        this.validatorFactoryScopedContext = new ValidatorFactoryScopedContext(configurationState.getMessageInterpolator(), configurationState.getTraversableResolver(), new ExecutableParameterNameProvider(configurationState.getParameterNameProvider()), configurationState.getClockProvider(), ValidatorFactoryConfigurationHelper.determineTemporalValidationTolerance(configurationState, properties), ValidatorFactoryConfigurationHelper.determineScriptEvaluatorFactory(configurationState, properties, externalClassLoader), ValidatorFactoryConfigurationHelper.determineFailFast(hibernateSpecificConfig, properties), ValidatorFactoryConfigurationHelper.determineTraversableResolverResultCacheEnabled(hibernateSpecificConfig, properties), ValidatorFactoryConfigurationHelper.determineConstraintValidatorPayload(hibernateSpecificConfig), ValidatorFactoryConfigurationHelper.determineConstraintExpressionLanguageFeatureLevel(hibernateSpecificConfig, properties), ValidatorFactoryConfigurationHelper.determineCustomViolationExpressionLanguageFeatureLevel(hibernateSpecificConfig, properties));
        ConstraintValidatorManagerImpl constraintValidatorManager = new ConstraintValidatorManagerImpl(configurationState.getConstraintValidatorFactory(), this.validatorFactoryScopedContext.getConstraintValidatorInitializationContext());
        this.validationOrderGenerator = new ValidationOrderGenerator();
        ValueExtractorManager valueExtractorManager = new ValueExtractorManager(configurationState.getValueExtractors());
        ConstraintHelper constraintHelper = ConstraintHelper.forAllBuiltinConstraints();
        TypeResolutionHelper typeResolutionHelper = new TypeResolutionHelper();
        this.constraintCreationContext = new ConstraintCreationContext(constraintHelper, constraintValidatorManager, typeResolutionHelper, valueExtractorManager);
        this.executableHelper = new ExecutableHelper(typeResolutionHelper);
        this.javaBeanHelper = new JavaBeanHelper(ValidatorFactoryConfigurationHelper.determineGetterPropertySelectionStrategy(hibernateSpecificConfig, properties, externalClassLoader), ValidatorFactoryConfigurationHelper.determinePropertyNodeNameProvider(hibernateSpecificConfig, properties, externalClassLoader));
        this.beanMetadataClassNormalizer = ValidatorFactoryConfigurationHelper.determineBeanMetaDataClassNormalizer(hibernateSpecificConfig);
        this.xmlMetaDataProvider = configurationState.getMappingStreams().isEmpty() ? null : new XmlMetaDataProvider(this.constraintCreationContext, this.javaBeanHelper, configurationState.getMappingStreams(), externalClassLoader);
        this.constraintMappings = Collections.unmodifiableSet(ValidatorFactoryConfigurationHelper.determineConstraintMappings(typeResolutionHelper, configurationState, this.javaBeanHelper, externalClassLoader));
        ValidatorFactoryConfigurationHelper.registerCustomConstraintValidators(this.constraintMappings, constraintHelper);
        if (LOG.isDebugEnabled()) {
            ValidatorFactoryConfigurationHelper.logValidatorFactoryScopedConfiguration(this.validatorFactoryScopedContext);
        }
    }

    @Override
    public Validator getValidator() {
        return this.createValidator(this.constraintCreationContext.getConstraintValidatorManager().getDefaultConstraintValidatorFactory(), this.constraintCreationContext, this.validatorFactoryScopedContext, this.methodValidationConfiguration);
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
        return this.constraintCreationContext.getConstraintValidatorManager().getDefaultConstraintValidatorFactory();
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
        return this.javaBeanHelper.getGetterPropertySelectionStrategy();
    }

    @Override
    public PropertyNodeNameProvider getPropertyNodeNameProvider() {
        return this.javaBeanHelper.getPropertyNodeNameProvider();
    }

    public boolean isFailFast() {
        return this.validatorFactoryScopedContext.isFailFast();
    }

    MethodValidationConfiguration getMethodValidationConfiguration() {
        return this.methodValidationConfiguration;
    }

    public boolean isTraversableResolverResultCacheEnabled() {
        return this.validatorFactoryScopedContext.isTraversableResolverResultCacheEnabled();
    }

    ConstraintCreationContext getConstraintCreationContext() {
        return this.constraintCreationContext;
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        if (type.isAssignableFrom(HibernateValidatorFactory.class)) {
            return type.cast(this);
        }
        throw LOG.getTypeNotSupportedForUnwrappingException(type);
    }

    @Override
    public HibernateValidatorContext usingContext() {
        return new ValidatorContextImpl(this);
    }

    @Override
    public void close() {
        this.constraintCreationContext.getConstraintValidatorManager().clear();
        this.constraintCreationContext.getConstraintHelper().clear();
        for (BeanMetaDataManager beanMetaDataManager : this.beanMetaDataManagers.values()) {
            beanMetaDataManager.clear();
        }
        this.validatorFactoryScopedContext.getScriptEvaluatorFactory().clear();
        this.constraintCreationContext.getValueExtractorManager().clear();
    }

    public ValidatorFactoryScopedContext getValidatorFactoryScopedContext() {
        return this.validatorFactoryScopedContext;
    }

    Validator createValidator(ConstraintValidatorFactory constraintValidatorFactory, ConstraintCreationContext constraintCreationContext, ValidatorFactoryScopedContext validatorFactoryScopedContext, MethodValidationConfiguration methodValidationConfiguration) {
        BeanMetaDataManager beanMetaDataManager = this.beanMetaDataManagers.computeIfAbsent(new BeanMetaDataManagerKey(validatorFactoryScopedContext.getParameterNameProvider(), constraintCreationContext.getValueExtractorManager(), methodValidationConfiguration), key -> new BeanMetaDataManagerImpl(constraintCreationContext, this.executableHelper, validatorFactoryScopedContext.getParameterNameProvider(), this.javaBeanHelper, this.beanMetadataClassNormalizer, this.validationOrderGenerator, this.buildMetaDataProviders(), methodValidationConfiguration));
        return new ValidatorImpl(constraintValidatorFactory, beanMetaDataManager, constraintCreationContext.getValueExtractorManager(), constraintCreationContext.getConstraintValidatorManager(), this.validationOrderGenerator, validatorFactoryScopedContext);
    }

    private List<MetaDataProvider> buildMetaDataProviders() {
        ArrayList<MetaDataProvider> metaDataProviders = CollectionHelper.newArrayList();
        if (this.xmlMetaDataProvider != null) {
            metaDataProviders.add(this.xmlMetaDataProvider);
        }
        if (!this.constraintMappings.isEmpty()) {
            metaDataProviders.add(new ProgrammaticMetaDataProvider(this.constraintCreationContext, this.constraintMappings));
        }
        return metaDataProviders;
    }

    private static class BeanMetaDataManagerKey {
        private final ExecutableParameterNameProvider parameterNameProvider;
        private final ValueExtractorManager valueExtractorManager;
        private final MethodValidationConfiguration methodValidationConfiguration;
        private final int hashCode;

        public BeanMetaDataManagerKey(ExecutableParameterNameProvider parameterNameProvider, ValueExtractorManager valueExtractorManager, MethodValidationConfiguration methodValidationConfiguration) {
            this.parameterNameProvider = parameterNameProvider;
            this.valueExtractorManager = valueExtractorManager;
            this.methodValidationConfiguration = methodValidationConfiguration;
            this.hashCode = BeanMetaDataManagerKey.buildHashCode(parameterNameProvider, valueExtractorManager, methodValidationConfiguration);
        }

        private static int buildHashCode(ExecutableParameterNameProvider parameterNameProvider, ValueExtractorManager valueExtractorManager, MethodValidationConfiguration methodValidationConfiguration) {
            int prime = 31;
            int result = 1;
            result = 31 * result + (methodValidationConfiguration == null ? 0 : methodValidationConfiguration.hashCode());
            result = 31 * result + (parameterNameProvider == null ? 0 : parameterNameProvider.hashCode());
            result = 31 * result + (valueExtractorManager == null ? 0 : valueExtractorManager.hashCode());
            return result;
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            BeanMetaDataManagerKey other = (BeanMetaDataManagerKey)obj;
            return this.methodValidationConfiguration.equals(other.methodValidationConfiguration) && this.parameterNameProvider.equals(other.parameterNameProvider) && this.valueExtractorManager.equals(other.valueExtractorManager);
        }

        public String toString() {
            return "BeanMetaDataManagerKey [parameterNameProvider=" + this.parameterNameProvider + ", valueExtractorManager=" + this.valueExtractorManager + ", methodValidationConfiguration=" + this.methodValidationConfiguration + "]";
        }
    }
}

