/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.spi.ConfigurationState;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.hibernate.validator.internal.engine.AbstractConfigurationImpl;
import org.hibernate.validator.internal.engine.DefaultPropertyNodeNameProvider;
import org.hibernate.validator.internal.engine.ServiceLoaderBasedConstraintMappingContributor;
import org.hibernate.validator.internal.engine.ValidatorFactoryScopedContext;
import org.hibernate.validator.internal.engine.constraintdefinition.ConstraintDefinitionContribution;
import org.hibernate.validator.internal.engine.messageinterpolation.DefaultLocaleResolver;
import org.hibernate.validator.internal.engine.scripting.DefaultScriptEvaluatorFactory;
import org.hibernate.validator.internal.metadata.DefaultBeanMetaDataClassNormalizer;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.properties.DefaultGetterPropertySelectionStrategy;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.hibernate.validator.metadata.BeanMetaDataClassNormalizer;
import org.hibernate.validator.spi.cfg.ConstraintMappingContributor;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.hibernate.validator.spi.properties.GetterPropertySelectionStrategy;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

final class ValidatorFactoryConfigurationHelper {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private ValidatorFactoryConfigurationHelper() {
    }

    static ClassLoader determineExternalClassLoader(ConfigurationState configurationState) {
        return configurationState instanceof AbstractConfigurationImpl ? ((AbstractConfigurationImpl)configurationState).getExternalClassLoader() : null;
    }

    static Set<DefaultConstraintMapping> determineConstraintMappings(TypeResolutionHelper typeResolutionHelper, ConfigurationState configurationState, JavaBeanHelper javaBeanHelper, ClassLoader externalClassLoader) {
        HashSet<DefaultConstraintMapping> constraintMappings = CollectionHelper.newHashSet();
        if (configurationState instanceof AbstractConfigurationImpl) {
            AbstractConfigurationImpl hibernateConfiguration = (AbstractConfigurationImpl)configurationState;
            constraintMappings.addAll(hibernateConfiguration.getProgrammaticMappings());
            ServiceLoaderBasedConstraintMappingContributor serviceLoaderBasedContributor = new ServiceLoaderBasedConstraintMappingContributor(typeResolutionHelper, externalClassLoader != null ? externalClassLoader : ValidatorFactoryConfigurationHelper.run(GetClassLoader.fromContext()));
            DefaultConstraintMappingBuilder builder = new DefaultConstraintMappingBuilder(javaBeanHelper, constraintMappings);
            serviceLoaderBasedContributor.createConstraintMappings(builder);
        }
        List<ConstraintMappingContributor> contributors = ValidatorFactoryConfigurationHelper.determinePropertyConfiguredConstraintMappingContributors(configurationState.getProperties(), externalClassLoader);
        for (ConstraintMappingContributor contributor : contributors) {
            DefaultConstraintMappingBuilder builder = new DefaultConstraintMappingBuilder(javaBeanHelper, constraintMappings);
            contributor.createConstraintMappings(builder);
        }
        return constraintMappings;
    }

    static boolean checkPropertiesForBoolean(Map<String, String> properties, String propertyKey, boolean programmaticValue) {
        boolean value = programmaticValue;
        String propertyStringValue = properties.get(propertyKey);
        if (propertyStringValue != null) {
            value = Boolean.valueOf(propertyStringValue);
        }
        return value;
    }

    static List<ConstraintMappingContributor> determinePropertyConfiguredConstraintMappingContributors(Map<String, String> properties, ClassLoader externalClassLoader) {
        String deprecatedPropertyValue = properties.get("hibernate.validator.constraint_mapping_contributor");
        String propertyValue = properties.get("hibernate.validator.constraint_mapping_contributors");
        if (StringHelper.isNullOrEmptyString(deprecatedPropertyValue) && StringHelper.isNullOrEmptyString(propertyValue)) {
            return Collections.emptyList();
        }
        StringBuilder assembledPropertyValue = new StringBuilder();
        if (!StringHelper.isNullOrEmptyString(deprecatedPropertyValue)) {
            assembledPropertyValue.append(deprecatedPropertyValue);
        }
        if (!StringHelper.isNullOrEmptyString(propertyValue)) {
            if (assembledPropertyValue.length() > 0) {
                assembledPropertyValue.append(",");
            }
            assembledPropertyValue.append(propertyValue);
        }
        String[] contributorNames = assembledPropertyValue.toString().split(",");
        ArrayList<ConstraintMappingContributor> contributors = CollectionHelper.newArrayList(contributorNames.length);
        for (String contributorName : contributorNames) {
            Class contributorType = (Class)ValidatorFactoryConfigurationHelper.run(LoadClass.action(contributorName, externalClassLoader));
            contributors.add((ConstraintMappingContributor)ValidatorFactoryConfigurationHelper.run(NewInstance.action(contributorType, "constraint mapping contributor class")));
        }
        return contributors;
    }

    static boolean determineAllowParallelMethodsDefineParameterConstraints(AbstractConfigurationImpl<?> hibernateSpecificConfig, Map<String, String> properties) {
        return ValidatorFactoryConfigurationHelper.checkPropertiesForBoolean(properties, "hibernate.validator.allow_parallel_method_parameter_constraint", hibernateSpecificConfig != null ? hibernateSpecificConfig.getMethodValidationConfiguration().isAllowParallelMethodsDefineParameterConstraints() : false);
    }

    static boolean determineAllowMultipleCascadedValidationOnReturnValues(AbstractConfigurationImpl<?> hibernateSpecificConfig, Map<String, String> properties) {
        return ValidatorFactoryConfigurationHelper.checkPropertiesForBoolean(properties, "hibernate.validator.allow_multiple_cascaded_validation_on_result", hibernateSpecificConfig != null ? hibernateSpecificConfig.getMethodValidationConfiguration().isAllowMultipleCascadedValidationOnReturnValues() : false);
    }

    static boolean determineAllowOverridingMethodAlterParameterConstraint(AbstractConfigurationImpl<?> hibernateSpecificConfig, Map<String, String> properties) {
        return ValidatorFactoryConfigurationHelper.checkPropertiesForBoolean(properties, "hibernate.validator.allow_parameter_constraint_override", hibernateSpecificConfig != null ? hibernateSpecificConfig.getMethodValidationConfiguration().isAllowOverridingMethodAlterParameterConstraint() : false);
    }

    static boolean determineTraversableResolverResultCacheEnabled(AbstractConfigurationImpl<?> configuration, Map<String, String> properties) {
        return ValidatorFactoryConfigurationHelper.checkPropertiesForBoolean(properties, "hibernate.validator.enable_traversable_resolver_result_cache", configuration != null ? configuration.isTraversableResolverResultCacheEnabled() : true);
    }

    static boolean determineFailFast(AbstractConfigurationImpl<?> configuration, Map<String, String> properties) {
        boolean tmpFailFast = configuration != null ? configuration.getFailFast() : false;
        String propertyStringValue = properties.get("hibernate.validator.fail_fast");
        if (propertyStringValue != null) {
            boolean configurationValue = Boolean.valueOf(propertyStringValue);
            if (tmpFailFast && !configurationValue) {
                throw LOG.getInconsistentFailFastConfigurationException();
            }
            tmpFailFast = configurationValue;
        }
        return tmpFailFast;
    }

    static ScriptEvaluatorFactory determineScriptEvaluatorFactory(ConfigurationState configurationState, Map<String, String> properties, ClassLoader externalClassLoader) {
        AbstractConfigurationImpl hibernateSpecificConfig;
        if (configurationState instanceof AbstractConfigurationImpl && (hibernateSpecificConfig = (AbstractConfigurationImpl)configurationState).getScriptEvaluatorFactory() != null) {
            LOG.usingScriptEvaluatorFactory(hibernateSpecificConfig.getScriptEvaluatorFactory().getClass());
            return hibernateSpecificConfig.getScriptEvaluatorFactory();
        }
        String scriptEvaluatorFactoryFqcn = properties.get("hibernate.validator.script_evaluator_factory");
        if (scriptEvaluatorFactoryFqcn != null) {
            try {
                Class clazz = (Class)ValidatorFactoryConfigurationHelper.run(LoadClass.action(scriptEvaluatorFactoryFqcn, externalClassLoader));
                ScriptEvaluatorFactory scriptEvaluatorFactory = (ScriptEvaluatorFactory)ValidatorFactoryConfigurationHelper.run(NewInstance.action(clazz, "script evaluator factory class"));
                LOG.usingScriptEvaluatorFactory(clazz);
                return scriptEvaluatorFactory;
            }
            catch (Exception e) {
                throw LOG.getUnableToInstantiateScriptEvaluatorFactoryClassException(scriptEvaluatorFactoryFqcn, e);
            }
        }
        return new DefaultScriptEvaluatorFactory(externalClassLoader);
    }

    static Duration determineTemporalValidationTolerance(ConfigurationState configurationState, Map<String, String> properties) {
        AbstractConfigurationImpl hibernateSpecificConfig;
        if (configurationState instanceof AbstractConfigurationImpl && (hibernateSpecificConfig = (AbstractConfigurationImpl)configurationState).getTemporalValidationTolerance() != null) {
            LOG.logTemporalValidationTolerance(hibernateSpecificConfig.getTemporalValidationTolerance());
            return hibernateSpecificConfig.getTemporalValidationTolerance();
        }
        String temporalValidationToleranceProperty = properties.get("hibernate.validator.temporal_validation_tolerance");
        if (temporalValidationToleranceProperty != null) {
            try {
                Duration tolerance = Duration.ofMillis(Long.parseLong(temporalValidationToleranceProperty)).abs();
                LOG.logTemporalValidationTolerance(tolerance);
                return tolerance;
            }
            catch (Exception e) {
                throw LOG.getUnableToParseTemporalValidationToleranceException(temporalValidationToleranceProperty, e);
            }
        }
        return Duration.ZERO;
    }

    static Object determineConstraintValidatorPayload(ConfigurationState configurationState) {
        AbstractConfigurationImpl hibernateSpecificConfig;
        if (configurationState instanceof AbstractConfigurationImpl && (hibernateSpecificConfig = (AbstractConfigurationImpl)configurationState).getConstraintValidatorPayload() != null) {
            LOG.logConstraintValidatorPayload(hibernateSpecificConfig.getConstraintValidatorPayload());
            return hibernateSpecificConfig.getConstraintValidatorPayload();
        }
        return null;
    }

    static ExpressionLanguageFeatureLevel determineConstraintExpressionLanguageFeatureLevel(AbstractConfigurationImpl<?> hibernateSpecificConfig, Map<String, String> properties) {
        if (hibernateSpecificConfig != null && hibernateSpecificConfig.getConstraintExpressionLanguageFeatureLevel() != null) {
            LOG.logConstraintExpressionLanguageFeatureLevel(hibernateSpecificConfig.getConstraintExpressionLanguageFeatureLevel());
            return ExpressionLanguageFeatureLevel.interpretDefaultForConstraints(hibernateSpecificConfig.getConstraintExpressionLanguageFeatureLevel());
        }
        String expressionLanguageFeatureLevelName = properties.get("hibernate.validator.constraint_expression_language_feature_level");
        if (expressionLanguageFeatureLevelName != null) {
            try {
                ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel = ExpressionLanguageFeatureLevel.of(expressionLanguageFeatureLevelName);
                LOG.logConstraintExpressionLanguageFeatureLevel(expressionLanguageFeatureLevel);
                return ExpressionLanguageFeatureLevel.interpretDefaultForConstraints(expressionLanguageFeatureLevel);
            }
            catch (IllegalArgumentException e) {
                throw LOG.invalidExpressionLanguageFeatureLevelValue(expressionLanguageFeatureLevelName, e);
            }
        }
        return ExpressionLanguageFeatureLevel.interpretDefaultForConstraints(ExpressionLanguageFeatureLevel.DEFAULT);
    }

    static ExpressionLanguageFeatureLevel determineCustomViolationExpressionLanguageFeatureLevel(AbstractConfigurationImpl<?> hibernateSpecificConfig, Map<String, String> properties) {
        if (hibernateSpecificConfig != null && hibernateSpecificConfig.getCustomViolationExpressionLanguageFeatureLevel() != null) {
            LOG.logCustomViolationExpressionLanguageFeatureLevel(hibernateSpecificConfig.getCustomViolationExpressionLanguageFeatureLevel());
            return ExpressionLanguageFeatureLevel.interpretDefaultForCustomViolations(hibernateSpecificConfig.getCustomViolationExpressionLanguageFeatureLevel());
        }
        String expressionLanguageFeatureLevelName = properties.get("hibernate.validator.custom_violation_expression_language_feature_level");
        if (expressionLanguageFeatureLevelName != null) {
            try {
                ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel = ExpressionLanguageFeatureLevel.of(expressionLanguageFeatureLevelName);
                LOG.logCustomViolationExpressionLanguageFeatureLevel(expressionLanguageFeatureLevel);
                return ExpressionLanguageFeatureLevel.interpretDefaultForCustomViolations(expressionLanguageFeatureLevel);
            }
            catch (IllegalArgumentException e) {
                throw LOG.invalidExpressionLanguageFeatureLevelValue(expressionLanguageFeatureLevelName, e);
            }
        }
        return ExpressionLanguageFeatureLevel.NONE;
    }

    static GetterPropertySelectionStrategy determineGetterPropertySelectionStrategy(AbstractConfigurationImpl<?> hibernateSpecificConfig, Map<String, String> properties, ClassLoader externalClassLoader) {
        if (hibernateSpecificConfig != null && hibernateSpecificConfig.getGetterPropertySelectionStrategy() != null) {
            LOG.usingGetterPropertySelectionStrategy(hibernateSpecificConfig.getGetterPropertySelectionStrategy().getClass());
            return hibernateSpecificConfig.getGetterPropertySelectionStrategy();
        }
        String getterPropertySelectionStrategyFqcn = properties.get("hibernate.validator.getter_property_selection_strategy");
        if (getterPropertySelectionStrategyFqcn != null) {
            try {
                Class clazz = (Class)ValidatorFactoryConfigurationHelper.run(LoadClass.action(getterPropertySelectionStrategyFqcn, externalClassLoader));
                GetterPropertySelectionStrategy getterPropertySelectionStrategy = (GetterPropertySelectionStrategy)ValidatorFactoryConfigurationHelper.run(NewInstance.action(clazz, "getter property selection strategy class"));
                LOG.usingGetterPropertySelectionStrategy(clazz);
                return getterPropertySelectionStrategy;
            }
            catch (Exception e) {
                throw LOG.getUnableToInstantiateGetterPropertySelectionStrategyClassException(getterPropertySelectionStrategyFqcn, e);
            }
        }
        return new DefaultGetterPropertySelectionStrategy();
    }

    static BeanMetaDataClassNormalizer determineBeanMetaDataClassNormalizer(AbstractConfigurationImpl<?> hibernateSpecificConfig) {
        if (hibernateSpecificConfig != null && hibernateSpecificConfig.getBeanMetaDataClassNormalizer() != null) {
            return hibernateSpecificConfig.getBeanMetaDataClassNormalizer();
        }
        return new DefaultBeanMetaDataClassNormalizer();
    }

    static PropertyNodeNameProvider determinePropertyNodeNameProvider(AbstractConfigurationImpl<?> hibernateSpecificConfig, Map<String, String> properties, ClassLoader externalClassLoader) {
        if (hibernateSpecificConfig != null && hibernateSpecificConfig.getPropertyNodeNameProvider() != null) {
            LOG.usingPropertyNodeNameProvider(hibernateSpecificConfig.getPropertyNodeNameProvider().getClass());
            return hibernateSpecificConfig.getPropertyNodeNameProvider();
        }
        String propertyNodeNameProviderFqcn = properties.get("hibernate.validator.property_node_name_provider");
        if (propertyNodeNameProviderFqcn != null) {
            try {
                Class clazz = (Class)ValidatorFactoryConfigurationHelper.run(LoadClass.action(propertyNodeNameProviderFqcn, externalClassLoader));
                PropertyNodeNameProvider propertyNodeNameProvider = (PropertyNodeNameProvider)ValidatorFactoryConfigurationHelper.run(NewInstance.action(clazz, "property node name provider class"));
                LOG.usingPropertyNodeNameProvider(clazz);
                return propertyNodeNameProvider;
            }
            catch (Exception e) {
                throw LOG.getUnableToInstantiatePropertyNodeNameProviderClassException(propertyNodeNameProviderFqcn, e);
            }
        }
        return new DefaultPropertyNodeNameProvider();
    }

    static LocaleResolver determineLocaleResolver(AbstractConfigurationImpl<?> hibernateSpecificConfig, Map<String, String> properties, ClassLoader externalClassLoader) {
        if (hibernateSpecificConfig != null && hibernateSpecificConfig.getLocaleResolver() != null) {
            LOG.usingLocaleResolver(hibernateSpecificConfig.getLocaleResolver().getClass());
            return hibernateSpecificConfig.getLocaleResolver();
        }
        String localeResolverFqcn = properties.get("hibernate.validator.locale_resolver");
        if (localeResolverFqcn != null) {
            try {
                Class clazz = (Class)ValidatorFactoryConfigurationHelper.run(LoadClass.action(localeResolverFqcn, externalClassLoader));
                LocaleResolver localeResolver = (LocaleResolver)ValidatorFactoryConfigurationHelper.run(NewInstance.action(clazz, "locale resolver class"));
                LOG.usingLocaleResolver(clazz);
                return localeResolver;
            }
            catch (Exception e) {
                throw LOG.getUnableToInstantiateLocaleResolverClassException(localeResolverFqcn, e);
            }
        }
        return new DefaultLocaleResolver();
    }

    static void registerCustomConstraintValidators(Set<DefaultConstraintMapping> constraintMappings, ConstraintHelper constraintHelper) {
        HashSet<Class<?>> definedConstraints = CollectionHelper.newHashSet();
        for (DefaultConstraintMapping constraintMapping : constraintMappings) {
            for (ConstraintDefinitionContribution<?> contribution : constraintMapping.getConstraintDefinitionContributions()) {
                ValidatorFactoryConfigurationHelper.processConstraintDefinitionContribution(contribution, constraintHelper, definedConstraints);
            }
        }
    }

    static <A extends Annotation> void processConstraintDefinitionContribution(ConstraintDefinitionContribution<A> constraintDefinitionContribution, ConstraintHelper constraintHelper, Set<Class<?>> definedConstraints) {
        Class<A> constraintType = constraintDefinitionContribution.getConstraintType();
        if (definedConstraints.contains(constraintType)) {
            throw LOG.getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException(constraintType);
        }
        definedConstraints.add(constraintType);
        constraintHelper.putValidatorDescriptors(constraintType, constraintDefinitionContribution.getValidatorDescriptors(), constraintDefinitionContribution.includeExisting());
    }

    static void logValidatorFactoryScopedConfiguration(ValidatorFactoryScopedContext context) {
        LOG.logValidatorFactoryScopedConfiguration(context.getMessageInterpolator().getClass(), "message interpolator");
        LOG.logValidatorFactoryScopedConfiguration(context.getTraversableResolver().getClass(), "traversable resolver");
        LOG.logValidatorFactoryScopedConfiguration(context.getParameterNameProvider().getClass(), "parameter name provider");
        LOG.logValidatorFactoryScopedConfiguration(context.getClockProvider().getClass(), "clock provider");
        LOG.logValidatorFactoryScopedConfiguration(context.getScriptEvaluatorFactory().getClass(), "script evaluator factory");
    }

    private static <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }

    private static class DefaultConstraintMappingBuilder
    implements ConstraintMappingContributor.ConstraintMappingBuilder {
        private final JavaBeanHelper javaBeanHelper;
        private final Set<DefaultConstraintMapping> mappings;

        public DefaultConstraintMappingBuilder(JavaBeanHelper javaBeanHelper, Set<DefaultConstraintMapping> mappings) {
            this.javaBeanHelper = javaBeanHelper;
            this.mappings = mappings;
        }

        @Override
        public ConstraintMapping addConstraintMapping() {
            DefaultConstraintMapping mapping = new DefaultConstraintMapping(this.javaBeanHelper);
            this.mappings.add(mapping);
            return mapping;
        }
    }
}

