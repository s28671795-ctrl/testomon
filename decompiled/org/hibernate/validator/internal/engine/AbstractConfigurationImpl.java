/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.BootstrapConfiguration;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.ValidationProviderResolver;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.BaseHibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.hibernate.validator.internal.engine.DefaultClockProvider;
import org.hibernate.validator.internal.engine.DefaultParameterNameProvider;
import org.hibernate.validator.internal.engine.DefaultPropertyNodeNameProvider;
import org.hibernate.validator.internal.engine.MethodValidationConfiguration;
import org.hibernate.validator.internal.engine.ValidatorFactoryConfigurationHelper;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl;
import org.hibernate.validator.internal.engine.resolver.TraversableResolvers;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.properties.DefaultGetterPropertySelectionStrategy;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.Version;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.GetInstancesFromServiceLoader;
import org.hibernate.validator.internal.util.privilegedactions.SetContextClassLoader;
import org.hibernate.validator.internal.xml.config.ValidationBootstrapParameters;
import org.hibernate.validator.internal.xml.config.ValidationXmlParser;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.metadata.BeanMetaDataClassNormalizer;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.hibernate.validator.spi.properties.GetterPropertySelectionStrategy;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public abstract class AbstractConfigurationImpl<T extends BaseHibernateValidatorConfiguration<T>>
implements BaseHibernateValidatorConfiguration<T>,
ConfigurationState {
    private static final Log LOG;
    private ResourceBundleLocator defaultResourceBundleLocator;
    private MessageInterpolator defaultMessageInterpolator;
    private MessageInterpolator messageInterpolator;
    private TraversableResolver defaultTraversableResolver;
    private final ConstraintValidatorFactory defaultConstraintValidatorFactory;
    private final ParameterNameProvider defaultParameterNameProvider;
    private final ClockProvider defaultClockProvider;
    private final PropertyNodeNameProvider defaultPropertyNodeNameProvider;
    private ValidationProviderResolver providerResolver;
    private final ValidationBootstrapParameters validationBootstrapParameters;
    private boolean ignoreXmlConfiguration = false;
    private final Set<InputStream> configurationStreams = CollectionHelper.newHashSet();
    private BootstrapConfiguration bootstrapConfiguration;
    private final Map<ValueExtractorDescriptor.Key, ValueExtractorDescriptor> valueExtractorDescriptors = new HashMap<ValueExtractorDescriptor.Key, ValueExtractorDescriptor>();
    private final Set<DefaultConstraintMapping> programmaticMappings = CollectionHelper.newHashSet();
    private boolean failFast;
    private ClassLoader externalClassLoader;
    private final MethodValidationConfiguration.Builder methodValidationConfigurationBuilder = new MethodValidationConfiguration.Builder();
    private boolean traversableResolverResultCacheEnabled = true;
    private ScriptEvaluatorFactory scriptEvaluatorFactory;
    private Duration temporalValidationTolerance;
    private Object constraintValidatorPayload;
    private GetterPropertySelectionStrategy getterPropertySelectionStrategy;
    private Set<Locale> locales = Collections.emptySet();
    private Locale defaultLocale = Locale.getDefault();
    private LocaleResolver localeResolver;
    private BeanMetaDataClassNormalizer beanMetaDataClassNormalizer;
    private ExpressionLanguageFeatureLevel constraintExpressionLanguageFeatureLevel;
    private ExpressionLanguageFeatureLevel customViolationExpressionLanguageFeatureLevel;

    protected AbstractConfigurationImpl(BootstrapState state) {
        this();
        this.providerResolver = state.getValidationProviderResolver() == null ? state.getDefaultValidationProviderResolver() : state.getValidationProviderResolver();
    }

    protected AbstractConfigurationImpl(ValidationProvider<?> provider) {
        this();
        if (provider == null) {
            throw LOG.getInconsistentConfigurationException();
        }
        this.providerResolver = null;
        this.validationBootstrapParameters.setProvider(provider);
    }

    private AbstractConfigurationImpl() {
        this.validationBootstrapParameters = new ValidationBootstrapParameters();
        this.defaultConstraintValidatorFactory = new ConstraintValidatorFactoryImpl();
        this.defaultParameterNameProvider = new DefaultParameterNameProvider();
        this.defaultClockProvider = DefaultClockProvider.INSTANCE;
        this.defaultPropertyNodeNameProvider = new DefaultPropertyNodeNameProvider();
    }

    @Override
    public final T ignoreXmlConfiguration() {
        this.ignoreXmlConfiguration = true;
        return this.thisAsT();
    }

    @Override
    public final T messageInterpolator(MessageInterpolator interpolator) {
        if (LOG.isDebugEnabled() && interpolator != null) {
            LOG.debug("Setting custom MessageInterpolator of type " + interpolator.getClass().getName());
        }
        this.validationBootstrapParameters.setMessageInterpolator(interpolator);
        return this.thisAsT();
    }

    @Override
    public final T traversableResolver(TraversableResolver resolver2) {
        if (LOG.isDebugEnabled() && resolver2 != null) {
            LOG.debug("Setting custom TraversableResolver of type " + resolver2.getClass().getName());
        }
        this.validationBootstrapParameters.setTraversableResolver(resolver2);
        return this.thisAsT();
    }

    @Override
    public final T enableTraversableResolverResultCache(boolean enabled) {
        this.traversableResolverResultCacheEnabled = enabled;
        return this.thisAsT();
    }

    public final boolean isTraversableResolverResultCacheEnabled() {
        return this.traversableResolverResultCacheEnabled;
    }

    @Override
    public final T constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
        if (LOG.isDebugEnabled() && constraintValidatorFactory != null) {
            LOG.debug("Setting custom ConstraintValidatorFactory of type " + constraintValidatorFactory.getClass().getName());
        }
        this.validationBootstrapParameters.setConstraintValidatorFactory(constraintValidatorFactory);
        return this.thisAsT();
    }

    @Override
    public T parameterNameProvider(ParameterNameProvider parameterNameProvider) {
        if (LOG.isDebugEnabled() && parameterNameProvider != null) {
            LOG.debug("Setting custom ParameterNameProvider of type " + parameterNameProvider.getClass().getName());
        }
        this.validationBootstrapParameters.setParameterNameProvider(parameterNameProvider);
        return this.thisAsT();
    }

    @Override
    public T clockProvider(ClockProvider clockProvider) {
        if (LOG.isDebugEnabled() && clockProvider != null) {
            LOG.debug("Setting custom ClockProvider of type " + clockProvider.getClass().getName());
        }
        this.validationBootstrapParameters.setClockProvider(clockProvider);
        return this.thisAsT();
    }

    @Override
    public T propertyNodeNameProvider(PropertyNodeNameProvider propertyNodeNameProvider) {
        if (LOG.isDebugEnabled() && propertyNodeNameProvider != null) {
            LOG.debug("Setting custom PropertyNodeNameProvider of type " + propertyNodeNameProvider.getClass().getName());
        }
        this.validationBootstrapParameters.setPropertyNodeNameProvider(propertyNodeNameProvider);
        return this.thisAsT();
    }

    @Override
    public T localeResolver(LocaleResolver localeResolver) {
        if (LOG.isDebugEnabled() && localeResolver != null) {
            LOG.debug("Setting custom LocaleResolver of type " + localeResolver.getClass().getName());
        }
        this.localeResolver = localeResolver;
        return this.thisAsT();
    }

    @Override
    public T addValueExtractor(ValueExtractor<?> extractor) {
        Contracts.assertNotNull(extractor, Messages.MESSAGES.parameterMustNotBeNull("extractor"));
        ValueExtractorDescriptor descriptor = new ValueExtractorDescriptor(extractor);
        ValueExtractorDescriptor previous = this.valueExtractorDescriptors.put(descriptor.getKey(), descriptor);
        if (previous != null) {
            throw LOG.getValueExtractorForTypeAndTypeUseAlreadyPresentException(extractor, previous.getValueExtractor());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Adding value extractor " + extractor);
        }
        return this.thisAsT();
    }

    @Override
    public final T addMapping(InputStream stream) {
        Contracts.assertNotNull(stream, Messages.MESSAGES.inputStreamCannotBeNull());
        this.validationBootstrapParameters.addMapping(stream.markSupported() ? stream : new BufferedInputStream(stream));
        return this.thisAsT();
    }

    @Override
    public final T failFast(boolean failFast) {
        this.failFast = failFast;
        return this.thisAsT();
    }

    @Override
    public T allowOverridingMethodAlterParameterConstraint(boolean allow) {
        this.methodValidationConfigurationBuilder.allowOverridingMethodAlterParameterConstraint(allow);
        return this.thisAsT();
    }

    public boolean isAllowOverridingMethodAlterParameterConstraint() {
        return this.methodValidationConfigurationBuilder.isAllowOverridingMethodAlterParameterConstraint();
    }

    @Override
    public T allowMultipleCascadedValidationOnReturnValues(boolean allow) {
        this.methodValidationConfigurationBuilder.allowMultipleCascadedValidationOnReturnValues(allow);
        return this.thisAsT();
    }

    public boolean isAllowMultipleCascadedValidationOnReturnValues() {
        return this.methodValidationConfigurationBuilder.isAllowMultipleCascadedValidationOnReturnValues();
    }

    @Override
    public T allowParallelMethodsDefineParameterConstraints(boolean allow) {
        this.methodValidationConfigurationBuilder.allowParallelMethodsDefineParameterConstraints(allow);
        return this.thisAsT();
    }

    @Override
    public T scriptEvaluatorFactory(ScriptEvaluatorFactory scriptEvaluatorFactory) {
        Contracts.assertNotNull(scriptEvaluatorFactory, Messages.MESSAGES.parameterMustNotBeNull("scriptEvaluatorFactory"));
        this.scriptEvaluatorFactory = scriptEvaluatorFactory;
        return this.thisAsT();
    }

    @Override
    public T temporalValidationTolerance(Duration temporalValidationTolerance) {
        Contracts.assertNotNull(temporalValidationTolerance, Messages.MESSAGES.parameterMustNotBeNull("temporalValidationTolerance"));
        this.temporalValidationTolerance = temporalValidationTolerance.abs();
        return this.thisAsT();
    }

    @Override
    public T constraintValidatorPayload(Object constraintValidatorPayload) {
        Contracts.assertNotNull(constraintValidatorPayload, Messages.MESSAGES.parameterMustNotBeNull("constraintValidatorPayload"));
        this.constraintValidatorPayload = constraintValidatorPayload;
        return this.thisAsT();
    }

    @Override
    public T getterPropertySelectionStrategy(GetterPropertySelectionStrategy getterPropertySelectionStrategy) {
        Contracts.assertNotNull(getterPropertySelectionStrategy, Messages.MESSAGES.parameterMustNotBeNull("getterPropertySelectionStrategy"));
        this.getterPropertySelectionStrategy = getterPropertySelectionStrategy;
        return this.thisAsT();
    }

    @Override
    public T locales(Set<Locale> locales) {
        Contracts.assertNotNull(this.defaultLocale, Messages.MESSAGES.parameterMustNotBeNull("locales"));
        this.locales = locales;
        return this.thisAsT();
    }

    @Override
    public T defaultLocale(Locale defaultLocale) {
        Contracts.assertNotNull(defaultLocale, Messages.MESSAGES.parameterMustNotBeNull("defaultLocale"));
        this.defaultLocale = defaultLocale;
        return this.thisAsT();
    }

    public boolean isAllowParallelMethodsDefineParameterConstraints() {
        return this.methodValidationConfigurationBuilder.isAllowParallelMethodsDefineParameterConstraints();
    }

    public MethodValidationConfiguration getMethodValidationConfiguration() {
        return this.methodValidationConfigurationBuilder.build();
    }

    @Override
    public final DefaultConstraintMapping createConstraintMapping() {
        GetterPropertySelectionStrategy getterPropertySelectionStrategyToUse = null;
        getterPropertySelectionStrategyToUse = this.getterPropertySelectionStrategy == null ? new DefaultGetterPropertySelectionStrategy() : this.getterPropertySelectionStrategy;
        return new DefaultConstraintMapping(new JavaBeanHelper(getterPropertySelectionStrategyToUse, this.defaultPropertyNodeNameProvider));
    }

    @Override
    public final T addMapping(ConstraintMapping mapping) {
        Contracts.assertNotNull(mapping, Messages.MESSAGES.parameterMustNotBeNull("mapping"));
        this.programmaticMappings.add((DefaultConstraintMapping)mapping);
        return this.thisAsT();
    }

    @Override
    public final T addProperty(String name, String value) {
        if (value != null) {
            this.validationBootstrapParameters.addConfigProperty(name, value);
        }
        return this.thisAsT();
    }

    @Override
    public T externalClassLoader(ClassLoader externalClassLoader) {
        Contracts.assertNotNull(externalClassLoader, Messages.MESSAGES.parameterMustNotBeNull("externalClassLoader"));
        this.externalClassLoader = externalClassLoader;
        this.messageInterpolator = null;
        return this.thisAsT();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final ValidatorFactory buildValidatorFactory() {
        this.loadValueExtractorsFromServiceLoader();
        this.parseValidationXml();
        for (ValueExtractorDescriptor valueExtractorDescriptor : this.valueExtractorDescriptors.values()) {
            this.validationBootstrapParameters.addValueExtractorDescriptor(valueExtractorDescriptor);
        }
        ValidatorFactory factory2 = null;
        try {
            if (this.isSpecificProvider()) {
                factory2 = this.validationBootstrapParameters.getProvider().buildValidatorFactory(this);
            } else {
                Class<ValidationProvider<?>> providerClass = this.validationBootstrapParameters.getProviderClass();
                if (providerClass != null) {
                    for (ValidationProvider<?> provider : this.providerResolver.getValidationProviders()) {
                        if (!providerClass.isAssignableFrom(provider.getClass())) continue;
                        factory2 = provider.buildValidatorFactory(this);
                        break;
                    }
                    if (factory2 == null) {
                        throw LOG.getUnableToFindProviderException(providerClass);
                    }
                } else {
                    List<ValidationProvider<?>> providers = this.providerResolver.getValidationProviders();
                    assert (providers.size() != 0);
                    factory2 = providers.get(0).buildValidatorFactory(this);
                }
            }
        }
        finally {
            for (InputStream in : this.configurationStreams) {
                try {
                    in.close();
                }
                catch (IOException io) {
                    LOG.unableToCloseInputStream();
                }
            }
        }
        return factory2;
    }

    @Override
    public final boolean isIgnoreXmlConfiguration() {
        return this.ignoreXmlConfiguration;
    }

    @Override
    public final MessageInterpolator getMessageInterpolator() {
        MessageInterpolator selectedInterpolator = this.validationBootstrapParameters.getMessageInterpolator();
        if (selectedInterpolator != null) {
            return selectedInterpolator;
        }
        if (this.messageInterpolator == null) {
            this.messageInterpolator = this.getDefaultMessageInterpolatorConfiguredWithClassLoader();
        }
        return this.messageInterpolator;
    }

    @Override
    public final Set<InputStream> getMappingStreams() {
        return this.validationBootstrapParameters.getMappings();
    }

    public final boolean getFailFast() {
        return this.failFast;
    }

    @Override
    public final ConstraintValidatorFactory getConstraintValidatorFactory() {
        return this.validationBootstrapParameters.getConstraintValidatorFactory();
    }

    @Override
    public final TraversableResolver getTraversableResolver() {
        return this.validationBootstrapParameters.getTraversableResolver();
    }

    @Override
    public BootstrapConfiguration getBootstrapConfiguration() {
        if (this.bootstrapConfiguration == null) {
            this.bootstrapConfiguration = new ValidationXmlParser(this.externalClassLoader).parseValidationXml();
        }
        return this.bootstrapConfiguration;
    }

    @Override
    public ParameterNameProvider getParameterNameProvider() {
        return this.validationBootstrapParameters.getParameterNameProvider();
    }

    @Override
    public ClockProvider getClockProvider() {
        return this.validationBootstrapParameters.getClockProvider();
    }

    public PropertyNodeNameProvider getPropertyNodeNameProvider() {
        return this.validationBootstrapParameters.getPropertyNodeNameProvider();
    }

    public LocaleResolver getLocaleResolver() {
        return this.localeResolver;
    }

    public ScriptEvaluatorFactory getScriptEvaluatorFactory() {
        return this.scriptEvaluatorFactory;
    }

    public Duration getTemporalValidationTolerance() {
        return this.temporalValidationTolerance;
    }

    public Object getConstraintValidatorPayload() {
        return this.constraintValidatorPayload;
    }

    public GetterPropertySelectionStrategy getGetterPropertySelectionStrategy() {
        return this.getterPropertySelectionStrategy;
    }

    @Override
    public Set<ValueExtractor<?>> getValueExtractors() {
        return this.validationBootstrapParameters.getValueExtractorDescriptors().values().stream().map(ValueExtractorDescriptor::getValueExtractor).collect(Collectors.toSet());
    }

    @Override
    public final Map<String, String> getProperties() {
        return this.validationBootstrapParameters.getConfigProperties();
    }

    public ClassLoader getExternalClassLoader() {
        return this.externalClassLoader;
    }

    @Override
    public final MessageInterpolator getDefaultMessageInterpolator() {
        if (this.defaultMessageInterpolator == null) {
            this.defaultMessageInterpolator = new ResourceBundleMessageInterpolator(this.getDefaultResourceBundleLocator(), this.getAllSupportedLocales(), this.defaultLocale, ValidatorFactoryConfigurationHelper.determineLocaleResolver(this, this.getProperties(), this.externalClassLoader), this.preloadResourceBundles());
        }
        return this.defaultMessageInterpolator;
    }

    @Override
    public final TraversableResolver getDefaultTraversableResolver() {
        if (this.defaultTraversableResolver == null) {
            this.defaultTraversableResolver = TraversableResolvers.getDefault();
        }
        return this.defaultTraversableResolver;
    }

    @Override
    public final ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
        return this.defaultConstraintValidatorFactory;
    }

    @Override
    public final ResourceBundleLocator getDefaultResourceBundleLocator() {
        if (this.defaultResourceBundleLocator == null) {
            this.defaultResourceBundleLocator = new PlatformResourceBundleLocator("ValidationMessages", this.preloadResourceBundles() ? this.getAllSupportedLocales() : Collections.emptySet());
        }
        return this.defaultResourceBundleLocator;
    }

    @Override
    public ParameterNameProvider getDefaultParameterNameProvider() {
        return this.defaultParameterNameProvider;
    }

    @Override
    public ClockProvider getDefaultClockProvider() {
        return this.defaultClockProvider;
    }

    @Override
    public Set<ValueExtractor<?>> getDefaultValueExtractors() {
        return ValueExtractorManager.getDefaultValueExtractors();
    }

    @Override
    public T beanMetaDataClassNormalizer(BeanMetaDataClassNormalizer beanMetaDataClassNormalizer) {
        if (LOG.isDebugEnabled() && beanMetaDataClassNormalizer != null) {
            LOG.debug("Setting custom BeanMetaDataClassNormalizer of type " + beanMetaDataClassNormalizer.getClass().getName());
        }
        this.beanMetaDataClassNormalizer = beanMetaDataClassNormalizer;
        return this.thisAsT();
    }

    public BeanMetaDataClassNormalizer getBeanMetaDataClassNormalizer() {
        return this.beanMetaDataClassNormalizer;
    }

    @Override
    public T constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel) {
        if (LOG.isDebugEnabled() && expressionLanguageFeatureLevel != null) {
            LOG.debug("Setting ExpressionLanguageFeatureLevel for constraints to " + expressionLanguageFeatureLevel.name());
        }
        this.constraintExpressionLanguageFeatureLevel = expressionLanguageFeatureLevel;
        return this.thisAsT();
    }

    public ExpressionLanguageFeatureLevel getConstraintExpressionLanguageFeatureLevel() {
        return this.constraintExpressionLanguageFeatureLevel;
    }

    @Override
    public T customViolationExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel) {
        if (LOG.isDebugEnabled() && expressionLanguageFeatureLevel != null) {
            LOG.debug("Setting ExpressionLanguageFeatureLevel for custom violations to " + expressionLanguageFeatureLevel.name());
        }
        this.customViolationExpressionLanguageFeatureLevel = expressionLanguageFeatureLevel;
        return this.thisAsT();
    }

    public ExpressionLanguageFeatureLevel getCustomViolationExpressionLanguageFeatureLevel() {
        return this.customViolationExpressionLanguageFeatureLevel;
    }

    public final Set<DefaultConstraintMapping> getProgrammaticMappings() {
        return this.programmaticMappings;
    }

    private boolean isSpecificProvider() {
        return this.validationBootstrapParameters.getProvider() != null;
    }

    private void parseValidationXml() {
        if (this.ignoreXmlConfiguration) {
            LOG.ignoringXmlConfiguration();
            if (this.validationBootstrapParameters.getTraversableResolver() == null) {
                this.validationBootstrapParameters.setTraversableResolver(this.getDefaultTraversableResolver());
            }
            if (this.validationBootstrapParameters.getConstraintValidatorFactory() == null) {
                this.validationBootstrapParameters.setConstraintValidatorFactory(this.defaultConstraintValidatorFactory);
            }
            if (this.validationBootstrapParameters.getParameterNameProvider() == null) {
                this.validationBootstrapParameters.setParameterNameProvider(this.defaultParameterNameProvider);
            }
            if (this.validationBootstrapParameters.getClockProvider() == null) {
                this.validationBootstrapParameters.setClockProvider(this.defaultClockProvider);
            }
            if (this.validationBootstrapParameters.getPropertyNodeNameProvider() == null) {
                this.validationBootstrapParameters.setPropertyNodeNameProvider(this.defaultPropertyNodeNameProvider);
            }
        } else {
            ValidationBootstrapParameters xmlParameters = new ValidationBootstrapParameters(this.getBootstrapConfiguration(), this.externalClassLoader);
            this.applyXmlSettings(xmlParameters);
        }
    }

    private void loadValueExtractorsFromServiceLoader() {
        List valueExtractors = (List)((Object)AbstractConfigurationImpl.run(GetInstancesFromServiceLoader.action(this.externalClassLoader != null ? this.externalClassLoader : AbstractConfigurationImpl.run(GetClassLoader.fromContext()), ValueExtractor.class)));
        for (ValueExtractor valueExtractor : valueExtractors) {
            this.validationBootstrapParameters.addValueExtractorDescriptor(new ValueExtractorDescriptor(valueExtractor));
        }
    }

    private void applyXmlSettings(ValidationBootstrapParameters xmlParameters) {
        this.validationBootstrapParameters.setProviderClass(xmlParameters.getProviderClass());
        if (this.validationBootstrapParameters.getMessageInterpolator() == null && xmlParameters.getMessageInterpolator() != null) {
            this.validationBootstrapParameters.setMessageInterpolator(xmlParameters.getMessageInterpolator());
        }
        if (this.validationBootstrapParameters.getTraversableResolver() == null) {
            if (xmlParameters.getTraversableResolver() != null) {
                this.validationBootstrapParameters.setTraversableResolver(xmlParameters.getTraversableResolver());
            } else {
                this.validationBootstrapParameters.setTraversableResolver(this.getDefaultTraversableResolver());
            }
        }
        if (this.validationBootstrapParameters.getConstraintValidatorFactory() == null) {
            if (xmlParameters.getConstraintValidatorFactory() != null) {
                this.validationBootstrapParameters.setConstraintValidatorFactory(xmlParameters.getConstraintValidatorFactory());
            } else {
                this.validationBootstrapParameters.setConstraintValidatorFactory(this.defaultConstraintValidatorFactory);
            }
        }
        if (this.validationBootstrapParameters.getParameterNameProvider() == null) {
            if (xmlParameters.getParameterNameProvider() != null) {
                this.validationBootstrapParameters.setParameterNameProvider(xmlParameters.getParameterNameProvider());
            } else {
                this.validationBootstrapParameters.setParameterNameProvider(this.defaultParameterNameProvider);
            }
        }
        if (this.validationBootstrapParameters.getClockProvider() == null) {
            if (xmlParameters.getClockProvider() != null) {
                this.validationBootstrapParameters.setClockProvider(xmlParameters.getClockProvider());
            } else {
                this.validationBootstrapParameters.setClockProvider(this.defaultClockProvider);
            }
        }
        if (this.validationBootstrapParameters.getPropertyNodeNameProvider() == null) {
            if (xmlParameters.getPropertyNodeNameProvider() != null) {
                this.validationBootstrapParameters.setPropertyNodeNameProvider(xmlParameters.getPropertyNodeNameProvider());
            } else {
                this.validationBootstrapParameters.setPropertyNodeNameProvider(this.defaultPropertyNodeNameProvider);
            }
        }
        for (ValueExtractorDescriptor valueExtractorDescriptor : xmlParameters.getValueExtractorDescriptors().values()) {
            this.validationBootstrapParameters.addValueExtractorDescriptor(valueExtractorDescriptor);
        }
        this.validationBootstrapParameters.addAllMappings(xmlParameters.getMappings());
        this.configurationStreams.addAll(xmlParameters.getMappings());
        for (Map.Entry entry : xmlParameters.getConfigProperties().entrySet()) {
            if (this.validationBootstrapParameters.getConfigProperties().get(entry.getKey()) != null) continue;
            this.validationBootstrapParameters.addConfigProperty((String)entry.getKey(), (String)entry.getValue());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private MessageInterpolator getDefaultMessageInterpolatorConfiguredWithClassLoader() {
        if (this.externalClassLoader != null) {
            PlatformResourceBundleLocator userResourceBundleLocator = new PlatformResourceBundleLocator("ValidationMessages", this.preloadResourceBundles() ? this.getAllSupportedLocales() : Collections.emptySet(), this.externalClassLoader);
            PlatformResourceBundleLocator contributorResourceBundleLocator = new PlatformResourceBundleLocator("ContributorValidationMessages", this.preloadResourceBundles() ? this.getAllSupportedLocales() : Collections.emptySet(), this.externalClassLoader, true);
            ClassLoader originalContextClassLoader = AbstractConfigurationImpl.run(GetClassLoader.fromContext());
            try {
                AbstractConfigurationImpl.run(SetContextClassLoader.action(this.externalClassLoader));
                ResourceBundleMessageInterpolator resourceBundleMessageInterpolator = new ResourceBundleMessageInterpolator((ResourceBundleLocator)userResourceBundleLocator, contributorResourceBundleLocator, this.getAllSupportedLocales(), this.defaultLocale, ValidatorFactoryConfigurationHelper.determineLocaleResolver(this, this.getProperties(), this.externalClassLoader), this.preloadResourceBundles());
                return resourceBundleMessageInterpolator;
            }
            finally {
                AbstractConfigurationImpl.run(SetContextClassLoader.action(originalContextClassLoader));
            }
        }
        return this.getDefaultMessageInterpolator();
    }

    private Set<Locale> getAllSupportedLocales() {
        if (this.locales.isEmpty()) {
            return Collections.singleton(this.defaultLocale);
        }
        if (this.locales.contains(this.defaultLocale)) {
            return this.locales;
        }
        HashSet<Locale> allLocales = CollectionHelper.newHashSet(this.locales.size() + 1);
        allLocales.addAll(this.locales);
        allLocales.add(this.defaultLocale);
        return allLocales;
    }

    protected abstract boolean preloadResourceBundles();

    protected T thisAsT() {
        return (T)this;
    }

    private static <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }

    static {
        Version.touch();
        LOG = LoggerFactory.make(MethodHandles.lookup());
    }
}

