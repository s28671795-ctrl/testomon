/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.validation.Configuration;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.hibernate.validator.metadata.BeanMetaDataClassNormalizer;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.hibernate.validator.spi.properties.GetterPropertySelectionStrategy;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public interface BaseHibernateValidatorConfiguration<S extends BaseHibernateValidatorConfiguration<S>>
extends Configuration<S> {
    public static final String FAIL_FAST = "hibernate.validator.fail_fast";
    public static final String ALLOW_PARAMETER_CONSTRAINT_OVERRIDE = "hibernate.validator.allow_parameter_constraint_override";
    public static final String ALLOW_MULTIPLE_CASCADED_VALIDATION_ON_RESULT = "hibernate.validator.allow_multiple_cascaded_validation_on_result";
    public static final String ALLOW_PARALLEL_METHODS_DEFINE_PARAMETER_CONSTRAINTS = "hibernate.validator.allow_parallel_method_parameter_constraint";
    @Deprecated
    public static final String CONSTRAINT_MAPPING_CONTRIBUTOR = "hibernate.validator.constraint_mapping_contributor";
    public static final String CONSTRAINT_MAPPING_CONTRIBUTORS = "hibernate.validator.constraint_mapping_contributors";
    public static final String ENABLE_TRAVERSABLE_RESOLVER_RESULT_CACHE = "hibernate.validator.enable_traversable_resolver_result_cache";
    @Incubating
    public static final String SCRIPT_EVALUATOR_FACTORY_CLASSNAME = "hibernate.validator.script_evaluator_factory";
    @Incubating
    public static final String TEMPORAL_VALIDATION_TOLERANCE = "hibernate.validator.temporal_validation_tolerance";
    @Incubating
    public static final String GETTER_PROPERTY_SELECTION_STRATEGY_CLASSNAME = "hibernate.validator.getter_property_selection_strategy";
    @Incubating
    public static final String PROPERTY_NODE_NAME_PROVIDER_CLASSNAME = "hibernate.validator.property_node_name_provider";
    @Incubating
    public static final String LOCALE_RESOLVER_CLASSNAME = "hibernate.validator.locale_resolver";
    @Incubating
    public static final String CONSTRAINT_EXPRESSION_LANGUAGE_FEATURE_LEVEL = "hibernate.validator.constraint_expression_language_feature_level";
    @Incubating
    public static final String CUSTOM_VIOLATION_EXPRESSION_LANGUAGE_FEATURE_LEVEL = "hibernate.validator.custom_violation_expression_language_feature_level";

    public ResourceBundleLocator getDefaultResourceBundleLocator();

    public ConstraintMapping createConstraintMapping();

    @Incubating
    public Set<ValueExtractor<?>> getDefaultValueExtractors();

    public S addMapping(ConstraintMapping var1);

    public S failFast(boolean var1);

    public S externalClassLoader(ClassLoader var1);

    public S allowOverridingMethodAlterParameterConstraint(boolean var1);

    public S allowMultipleCascadedValidationOnReturnValues(boolean var1);

    public S allowParallelMethodsDefineParameterConstraints(boolean var1);

    public S enableTraversableResolverResultCache(boolean var1);

    @Incubating
    public S scriptEvaluatorFactory(ScriptEvaluatorFactory var1);

    @Incubating
    public S temporalValidationTolerance(Duration var1);

    @Incubating
    public S constraintValidatorPayload(Object var1);

    @Incubating
    public S getterPropertySelectionStrategy(GetterPropertySelectionStrategy var1);

    @Incubating
    public S propertyNodeNameProvider(PropertyNodeNameProvider var1);

    @Incubating
    public S locales(Set<Locale> var1);

    @Incubating
    default public S locales(Locale ... locales) {
        return this.locales(new HashSet<Locale>(Arrays.asList(locales)));
    }

    @Incubating
    public S defaultLocale(Locale var1);

    @Incubating
    public S localeResolver(LocaleResolver var1);

    @Incubating
    public S beanMetaDataClassNormalizer(BeanMetaDataClassNormalizer var1);

    @Incubating
    public S constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel var1);

    @Incubating
    public S customViolationExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel var1);
}

