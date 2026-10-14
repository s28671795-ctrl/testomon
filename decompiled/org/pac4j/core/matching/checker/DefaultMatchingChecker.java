/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.checker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.checker.MatchingChecker;
import org.pac4j.core.matching.matcher.CacheControlMatcher;
import org.pac4j.core.matching.matcher.CorsMatcher;
import org.pac4j.core.matching.matcher.HttpMethodMatcher;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.matching.matcher.StrictTransportSecurityMatcher;
import org.pac4j.core.matching.matcher.XContentTypeOptionsMatcher;
import org.pac4j.core.matching.matcher.XFrameOptionsMatcher;
import org.pac4j.core.matching.matcher.XSSProtectionMatcher;
import org.pac4j.core.matching.matcher.csrf.CsrfTokenGeneratorMatcher;
import org.pac4j.core.matching.matcher.csrf.DefaultCsrfTokenGenerator;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMatchingChecker
implements MatchingChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMatchingChecker.class);
    protected static final Matcher GET_MATCHER = new HttpMethodMatcher(HttpConstants.HTTP_METHOD.GET);
    protected static final Matcher POST_MATCHER = new HttpMethodMatcher(HttpConstants.HTTP_METHOD.POST);
    protected static final Matcher PUT_MATCHER = new HttpMethodMatcher(HttpConstants.HTTP_METHOD.PUT);
    protected static final Matcher DELETE_MATCHER = new HttpMethodMatcher(HttpConstants.HTTP_METHOD.DELETE);
    protected static final StrictTransportSecurityMatcher STRICT_TRANSPORT_MATCHER = new StrictTransportSecurityMatcher();
    protected static final XContentTypeOptionsMatcher X_CONTENT_TYPE_OPTIONS_MATCHER = new XContentTypeOptionsMatcher();
    protected static final XFrameOptionsMatcher X_FRAME_OPTIONS_MATCHER = new XFrameOptionsMatcher();
    protected static final XSSProtectionMatcher XSS_PROTECTION_MATCHER = new XSSProtectionMatcher();
    protected static final CacheControlMatcher CACHE_CONTROL_MATCHER = new CacheControlMatcher();
    protected static final CsrfTokenGeneratorMatcher CSRF_TOKEN_MATCHER = new CsrfTokenGeneratorMatcher(new DefaultCsrfTokenGenerator());
    protected static final CorsMatcher CORS_MATCHER = new CorsMatcher();

    @Override
    public boolean matches(WebContext context, SessionStore sessionStore, String matchersValue, Map<String, Matcher> matchersMap, List<Client> clients) {
        List<Matcher> matchers = this.computeMatchers(context, sessionStore, matchersValue, matchersMap, clients);
        return this.matches(context, sessionStore, matchers);
    }

    protected List<Matcher> computeMatchers(WebContext context, SessionStore sessionStore, String matchersValue, Map<String, Matcher> matchersMap, List<Client> clients) {
        Object matcherNames = CommonHelper.isBlank(matchersValue) ? this.computeDefaultMatcherNames(context, sessionStore, clients, matchersMap) : (matchersValue.trim().startsWith("+") ? this.computeDefaultMatcherNames(context, sessionStore, clients, matchersMap) + "," + CommonHelper.substringAfter(matchersValue, "+") : matchersValue);
        return this.computeMatchersFromNames((String)matcherNames, matchersMap);
    }

    protected String computeDefaultMatcherNames(WebContext context, SessionStore sessionStore, List<Client> clients, Map<String, Matcher> matchersMap) {
        Object name = "securityheaders";
        if (sessionStore.getSessionId(context, false).isPresent()) {
            name = (String)name + ",csrfToken";
            return name;
        }
        for (Client client : clients) {
            if (!(client instanceof IndirectClient)) continue;
            name = (String)name + ",csrfToken";
            return name;
        }
        return name;
    }

    protected List<Matcher> computeMatchersFromNames(String matchersValue, Map<String, Matcher> matchersMap) {
        CommonHelper.assertNotNull("matchersMap", matchersMap);
        ArrayList<Matcher> matchers = new ArrayList<Matcher>();
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(matchersValue.split(",")));
        int i = 0;
        while (i < names.size()) {
            List<Matcher> results;
            String name;
            if ("securityheaders".equalsIgnoreCase(name = ((String)names.get(i++)).trim())) {
                results = this.retrieveMatchers(name, matchersMap);
                if (results.isEmpty()) {
                    names.add(i, "xssprotection");
                    names.add(i, "noframe");
                    names.add(i, "hsts");
                    names.add(i, "nosniff");
                    names.add(i, "nocache");
                    continue;
                }
                matchers.addAll(results);
                continue;
            }
            if ("none".equalsIgnoreCase(name)) continue;
            results = this.retrieveMatchers(name, matchersMap);
            CommonHelper.assertTrue(!results.isEmpty(), "The matcher '" + name + "' must be defined in the security configuration");
            matchers.addAll(results);
        }
        return matchers;
    }

    protected List<Matcher> retrieveMatchers(String matcherName, Map<String, Matcher> matchersMap) {
        ArrayList<Matcher> results = new ArrayList<Matcher>();
        for (Map.Entry<String, Matcher> entry : matchersMap.entrySet()) {
            if (!CommonHelper.areEqualsIgnoreCaseAndTrim(entry.getKey(), matcherName)) continue;
            results.add(entry.getValue());
            break;
        }
        if (results.size() == 0) {
            if ("hsts".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(STRICT_TRANSPORT_MATCHER);
            }
            if ("nosniff".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(X_CONTENT_TYPE_OPTIONS_MATCHER);
            }
            if ("noframe".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(X_FRAME_OPTIONS_MATCHER);
            }
            if ("xssprotection".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(XSS_PROTECTION_MATCHER);
            }
            if ("nocache".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(CACHE_CONTROL_MATCHER);
            }
            if ("csrfToken".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(CSRF_TOKEN_MATCHER);
            }
            if ("allowAjaxRequests".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(CORS_MATCHER);
            }
            if ("get".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(GET_MATCHER);
            }
            if ("post".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(POST_MATCHER);
            }
            if ("put".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(PUT_MATCHER);
            }
            if ("delete".equalsIgnoreCase(matcherName)) {
                return Arrays.asList(DELETE_MATCHER);
            }
        }
        return results;
    }

    protected boolean matches(WebContext context, SessionStore sessionStore, List<Matcher> matchers) {
        if (!matchers.isEmpty()) {
            for (Matcher matcher : matchers) {
                boolean matches = matcher.matches(context, sessionStore);
                LOGGER.debug("Checking matcher: {} -> {}", (Object)matcher, (Object)matches);
                if (matches) continue;
                return false;
            }
        }
        return true;
    }

    static {
        CORS_MATCHER.setAllowOrigin("*");
        CORS_MATCHER.setAllowCredentials(true);
        HashSet<HttpConstants.HTTP_METHOD> methods = new HashSet<HttpConstants.HTTP_METHOD>();
        methods.add(HttpConstants.HTTP_METHOD.GET);
        methods.add(HttpConstants.HTTP_METHOD.PUT);
        methods.add(HttpConstants.HTTP_METHOD.POST);
        methods.add(HttpConstants.HTTP_METHOD.DELETE);
        methods.add(HttpConstants.HTTP_METHOD.OPTIONS);
        CORS_MATCHER.setAllowMethods(methods);
    }
}

