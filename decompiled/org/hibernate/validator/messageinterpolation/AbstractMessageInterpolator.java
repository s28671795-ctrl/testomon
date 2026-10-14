/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.messageinterpolation;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.MessageInterpolator;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.internal.engine.messageinterpolation.DefaultLocaleResolver;
import org.hibernate.validator.internal.engine.messageinterpolation.DefaultLocaleResolverContext;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTermType;
import org.hibernate.validator.internal.engine.messageinterpolation.LocalizedMessage;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.Token;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenCollector;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenIterator;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ConcurrentReferenceHashMap;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.hibernate.validator.messageinterpolation.HibernateMessageInterpolatorContext;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolverContext;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public abstract class AbstractMessageInterpolator
implements MessageInterpolator {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final int DEFAULT_INITIAL_CAPACITY = 100;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    public static final String DEFAULT_VALIDATION_MESSAGES = "org.hibernate.validator.ValidationMessages";
    public static final String USER_VALIDATION_MESSAGES = "ValidationMessages";
    public static final String CONTRIBUTOR_VALIDATION_MESSAGES = "ContributorValidationMessages";
    private final LocaleResolver localeResolver;
    private final LocaleResolverContext localeResolverContext;
    private final ResourceBundleLocator userResourceBundleLocator;
    private final ResourceBundleLocator defaultResourceBundleLocator;
    private final ResourceBundleLocator contributorResourceBundleLocator;
    private final ConcurrentReferenceHashMap<LocalizedMessage, String> resolvedMessages;
    private final ConcurrentReferenceHashMap<String, List<Token>> tokenizedParameterMessages;
    private final ConcurrentReferenceHashMap<String, List<Token>> tokenizedELMessages;
    private final boolean cachingEnabled;
    private static final Pattern LEFT_BRACE = Pattern.compile("\\{", 16);
    private static final Pattern RIGHT_BRACE = Pattern.compile("\\}", 16);
    private static final Pattern SLASH = Pattern.compile("\\\\", 16);
    private static final Pattern DOLLAR = Pattern.compile("\\$", 16);

    public AbstractMessageInterpolator() {
        this(Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false);
    }

    public AbstractMessageInterpolator(ResourceBundleLocator userResourceBundleLocator) {
        this(userResourceBundleLocator, Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false);
    }

    public AbstractMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator) {
        this(userResourceBundleLocator, contributorResourceBundleLocator, Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false);
    }

    public AbstractMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator, boolean cacheMessages) {
        this(userResourceBundleLocator, contributorResourceBundleLocator, Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), cacheMessages);
    }

    @Incubating
    public AbstractMessageInterpolator(Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles) {
        this(null, locales, defaultLocale, localeResolver, preloadResourceBundles);
    }

    @Incubating
    public AbstractMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles) {
        this(userResourceBundleLocator, null, locales, defaultLocale, localeResolver, preloadResourceBundles);
    }

    @Incubating
    public AbstractMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator, Set<Locale> localesToInitialize, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles) {
        this(userResourceBundleLocator, contributorResourceBundleLocator, localesToInitialize, defaultLocale, localeResolver, preloadResourceBundles, true);
    }

    @Incubating
    public AbstractMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator, Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles, boolean cacheMessages) {
        Contracts.assertNotNull(locales, Messages.MESSAGES.parameterMustNotBeNull("localesToInitialize"));
        Contracts.assertNotNull(defaultLocale, Messages.MESSAGES.parameterMustNotBeNull("defaultLocale"));
        Contracts.assertNotNull(localeResolver, Messages.MESSAGES.parameterMustNotBeNull("localeResolver"));
        Set<Locale> allLocales = CollectionHelper.toImmutableSet(this.getAllLocales(locales, defaultLocale));
        this.localeResolverContext = new DefaultLocaleResolverContext(allLocales, defaultLocale);
        this.localeResolver = localeResolver;
        Set<Locale> allLocalesToInitialize = preloadResourceBundles ? allLocales : Collections.emptySet();
        this.userResourceBundleLocator = userResourceBundleLocator == null ? new PlatformResourceBundleLocator(USER_VALIDATION_MESSAGES, allLocalesToInitialize) : userResourceBundleLocator;
        this.contributorResourceBundleLocator = contributorResourceBundleLocator == null ? new PlatformResourceBundleLocator(CONTRIBUTOR_VALIDATION_MESSAGES, allLocalesToInitialize, null, true) : contributorResourceBundleLocator;
        this.defaultResourceBundleLocator = new PlatformResourceBundleLocator(DEFAULT_VALIDATION_MESSAGES, allLocalesToInitialize);
        this.cachingEnabled = cacheMessages;
        if (this.cachingEnabled) {
            this.resolvedMessages = new ConcurrentReferenceHashMap(100, 0.75f, 16, ConcurrentReferenceHashMap.ReferenceType.SOFT, ConcurrentReferenceHashMap.ReferenceType.SOFT, EnumSet.noneOf(ConcurrentReferenceHashMap.Option.class));
            this.tokenizedParameterMessages = new ConcurrentReferenceHashMap(100, 0.75f, 16, ConcurrentReferenceHashMap.ReferenceType.SOFT, ConcurrentReferenceHashMap.ReferenceType.SOFT, EnumSet.noneOf(ConcurrentReferenceHashMap.Option.class));
            this.tokenizedELMessages = new ConcurrentReferenceHashMap(100, 0.75f, 16, ConcurrentReferenceHashMap.ReferenceType.SOFT, ConcurrentReferenceHashMap.ReferenceType.SOFT, EnumSet.noneOf(ConcurrentReferenceHashMap.Option.class));
        } else {
            this.resolvedMessages = null;
            this.tokenizedParameterMessages = null;
            this.tokenizedELMessages = null;
        }
    }

    @Override
    public String interpolate(String message, MessageInterpolator.Context context) {
        String interpolatedMessage = message;
        try {
            interpolatedMessage = this.interpolateMessage(message, context, this.localeResolver.resolve(this.localeResolverContext));
        }
        catch (MessageDescriptorFormatException e) {
            LOG.warn(e.getMessage());
        }
        return interpolatedMessage;
    }

    @Override
    public String interpolate(String message, MessageInterpolator.Context context, Locale locale) {
        String interpolatedMessage = message;
        try {
            interpolatedMessage = this.interpolateMessage(message, context, locale);
        }
        catch (MessageDescriptorFormatException e) {
            LOG.warn(e.getMessage());
        }
        return interpolatedMessage;
    }

    private Set<Locale> getAllLocales(Set<Locale> localesToInitialize, Locale defaultLocale) {
        if (localesToInitialize.contains(defaultLocale)) {
            return localesToInitialize;
        }
        HashSet<Locale> allLocales = new HashSet<Locale>(localesToInitialize.size() + 1);
        allLocales.addAll(localesToInitialize);
        allLocales.add(defaultLocale);
        return allLocales;
    }

    private String interpolateMessage(String message, MessageInterpolator.Context context, Locale locale) throws MessageDescriptorFormatException {
        if (message.indexOf(123) < 0) {
            return this.replaceEscapedLiterals(message);
        }
        String resolvedMessage = null;
        resolvedMessage = this.cachingEnabled ? this.resolvedMessages.computeIfAbsent(new LocalizedMessage(message, locale), lm -> this.resolveMessage(message, locale)) : this.resolveMessage(message, locale);
        if (resolvedMessage.indexOf(123) > -1) {
            resolvedMessage = this.interpolateExpression(new TokenIterator(this.getParameterTokens(resolvedMessage, this.tokenizedParameterMessages, InterpolationTermType.PARAMETER)), context, locale);
            if (!(context instanceof HibernateMessageInterpolatorContext) || ((HibernateMessageInterpolatorContext)context).getExpressionLanguageFeatureLevel() != ExpressionLanguageFeatureLevel.NONE) {
                resolvedMessage = this.interpolateExpression(new TokenIterator(this.getParameterTokens(resolvedMessage, this.tokenizedELMessages, InterpolationTermType.EL)), context, locale);
            }
        }
        resolvedMessage = this.replaceEscapedLiterals(resolvedMessage);
        return resolvedMessage;
    }

    private List<Token> getParameterTokens(String resolvedMessage, ConcurrentReferenceHashMap<String, List<Token>> cache, InterpolationTermType termType) {
        if (this.cachingEnabled) {
            return cache.computeIfAbsent(resolvedMessage, rm -> new TokenCollector(resolvedMessage, termType).getTokenList());
        }
        return new TokenCollector(resolvedMessage, termType).getTokenList();
    }

    private String resolveMessage(String message, Locale locale) {
        String resolvedMessage = message;
        ResourceBundle userResourceBundle = this.userResourceBundleLocator.getResourceBundle(locale);
        ResourceBundle constraintContributorResourceBundle = this.contributorResourceBundleLocator.getResourceBundle(locale);
        ResourceBundle defaultResourceBundle = this.defaultResourceBundleLocator.getResourceBundle(locale);
        boolean evaluatedDefaultBundleOnce = false;
        while (true) {
            String userBundleResolvedMessage;
            if (!this.hasReplacementTakenPlace(userBundleResolvedMessage = this.interpolateBundleMessage(resolvedMessage, userResourceBundle, locale, true), resolvedMessage)) {
                userBundleResolvedMessage = this.interpolateBundleMessage(resolvedMessage, constraintContributorResourceBundle, locale, true);
            }
            if (evaluatedDefaultBundleOnce && !this.hasReplacementTakenPlace(userBundleResolvedMessage, resolvedMessage)) break;
            resolvedMessage = this.interpolateBundleMessage(userBundleResolvedMessage, defaultResourceBundle, locale, false);
            evaluatedDefaultBundleOnce = true;
        }
        return resolvedMessage;
    }

    private String replaceEscapedLiterals(String resolvedMessage) {
        if (resolvedMessage.indexOf(92) > -1) {
            resolvedMessage = LEFT_BRACE.matcher(resolvedMessage).replaceAll("{");
            resolvedMessage = RIGHT_BRACE.matcher(resolvedMessage).replaceAll("}");
            resolvedMessage = SLASH.matcher(resolvedMessage).replaceAll(Matcher.quoteReplacement("\\"));
            resolvedMessage = DOLLAR.matcher(resolvedMessage).replaceAll(Matcher.quoteReplacement("$"));
        }
        return resolvedMessage;
    }

    private boolean hasReplacementTakenPlace(String origMessage, String newMessage) {
        return !origMessage.equals(newMessage);
    }

    private String interpolateBundleMessage(String message, ResourceBundle bundle2, Locale locale, boolean recursive) throws MessageDescriptorFormatException {
        TokenCollector tokenCollector = new TokenCollector(message, InterpolationTermType.PARAMETER);
        TokenIterator tokenIterator = new TokenIterator(tokenCollector.getTokenList());
        while (tokenIterator.hasMoreInterpolationTerms()) {
            String term = tokenIterator.nextInterpolationTerm();
            String resolvedParameterValue = this.resolveParameter(term, bundle2, locale, recursive);
            tokenIterator.replaceCurrentInterpolationTerm(resolvedParameterValue);
        }
        return tokenIterator.getInterpolatedMessage();
    }

    private String interpolateExpression(TokenIterator tokenIterator, MessageInterpolator.Context context, Locale locale) throws MessageDescriptorFormatException {
        while (tokenIterator.hasMoreInterpolationTerms()) {
            String term = tokenIterator.nextInterpolationTerm();
            String resolvedExpression = this.interpolate(context, locale, term);
            tokenIterator.replaceCurrentInterpolationTerm(resolvedExpression);
        }
        return tokenIterator.getInterpolatedMessage();
    }

    protected abstract String interpolate(MessageInterpolator.Context var1, Locale var2, String var3);

    private String resolveParameter(String parameterName, ResourceBundle bundle2, Locale locale, boolean recursive) throws MessageDescriptorFormatException {
        String parameterValue;
        try {
            if (bundle2 != null) {
                parameterValue = bundle2.getString(this.removeCurlyBraces(parameterName));
                if (recursive) {
                    parameterValue = this.interpolateBundleMessage(parameterValue, bundle2, locale, recursive);
                }
            } else {
                parameterValue = parameterName;
            }
        }
        catch (MissingResourceException e) {
            parameterValue = parameterName;
        }
        return parameterValue;
    }

    private String removeCurlyBraces(String parameter) {
        return parameter.substring(1, parameter.length() - 1);
    }
}

