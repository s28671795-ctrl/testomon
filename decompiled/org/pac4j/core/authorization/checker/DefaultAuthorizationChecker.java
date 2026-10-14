/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.authorization.authorizer.CsrfAuthorizer;
import org.pac4j.core.authorization.authorizer.IsAnonymousAuthorizer;
import org.pac4j.core.authorization.authorizer.IsAuthenticatedAuthorizer;
import org.pac4j.core.authorization.authorizer.IsFullyAuthenticatedAuthorizer;
import org.pac4j.core.authorization.authorizer.IsRememberedAuthorizer;
import org.pac4j.core.authorization.checker.AuthorizationChecker;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.client.direct.AnonymousClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAuthorizationChecker
implements AuthorizationChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthorizationChecker.class);
    protected static final CsrfAuthorizer CSRF_AUTHORIZER = new CsrfAuthorizer();
    protected static final IsAnonymousAuthorizer IS_ANONYMOUS_AUTHORIZER = new IsAnonymousAuthorizer();
    protected static final IsAuthenticatedAuthorizer IS_AUTHENTICATED_AUTHORIZER = new IsAuthenticatedAuthorizer();
    protected static final IsFullyAuthenticatedAuthorizer IS_FULLY_AUTHENTICATED_AUTHORIZER = new IsFullyAuthenticatedAuthorizer();
    protected static final IsRememberedAuthorizer IS_REMEMBERED_AUTHORIZER = new IsRememberedAuthorizer();

    @Override
    public boolean isAuthorized(WebContext context, SessionStore sessionStore, List<UserProfile> profiles, String authorizersValue, Map<String, Authorizer> authorizersMap, List<Client> clients) {
        List<Authorizer> authorizers = this.computeAuthorizers(context, profiles, authorizersValue, authorizersMap, clients);
        return this.isAuthorized(context, sessionStore, profiles, authorizers);
    }

    protected List<Authorizer> computeAuthorizers(WebContext context, List<UserProfile> profiles, String authorizersValue, Map<String, Authorizer> authorizersMap, List<Client> clients) {
        List<Authorizer> authorizers;
        if (CommonHelper.isBlank(authorizersValue)) {
            authorizers = this.computeDefaultAuthorizers(context, profiles, clients, authorizersMap);
        } else if (authorizersValue.trim().startsWith("+")) {
            String authorizerNames = CommonHelper.substringAfter(authorizersValue, "+");
            authorizers = this.computeDefaultAuthorizers(context, profiles, clients, authorizersMap);
            authorizers.addAll(this.computeAuthorizersFromNames(authorizerNames, authorizersMap));
        } else {
            authorizers = this.computeAuthorizersFromNames(authorizersValue, authorizersMap);
        }
        return authorizers;
    }

    protected List<Authorizer> computeDefaultAuthorizers(WebContext context, List<UserProfile> profiles, List<Client> clients, Map<String, Authorizer> authorizersMap) {
        ArrayList<Authorizer> authorizers = new ArrayList<Authorizer>();
        if (this.containsClientType(clients, IndirectClient.class)) {
            authorizers.add(this.retrieveAuthorizer("csrfCheck", authorizersMap));
        }
        if (!this.containsClientType(clients, AnonymousClient.class)) {
            authorizers.add(this.retrieveAuthorizer("isAuthenticated", authorizersMap));
        }
        return authorizers;
    }

    protected List<Authorizer> computeAuthorizersFromNames(String authorizerNames, Map<String, Authorizer> authorizersMap) {
        CommonHelper.assertNotNull("authorizersMap", authorizersMap);
        ArrayList<Authorizer> authorizers = new ArrayList<Authorizer>();
        String[] names = authorizerNames.split(",");
        int nb = names.length;
        for (int i = 0; i < nb; ++i) {
            String name = names[i].trim();
            if ("none".equalsIgnoreCase(name)) continue;
            Authorizer result = this.retrieveAuthorizer(name, authorizersMap);
            CommonHelper.assertTrue(result != null, "The authorizer '" + name + "' must be defined in the security configuration");
            authorizers.add(result);
        }
        return authorizers;
    }

    protected Authorizer retrieveAuthorizer(String authorizerName, Map<String, Authorizer> authorizersMap) {
        Authorizer authorizer = null;
        for (Map.Entry<String, Authorizer> entry : authorizersMap.entrySet()) {
            if (!CommonHelper.areEqualsIgnoreCaseAndTrim(entry.getKey(), authorizerName)) continue;
            authorizer = entry.getValue();
            break;
        }
        if (authorizer == null) {
            if ("csrfCheck".equalsIgnoreCase(authorizerName)) {
                return CSRF_AUTHORIZER;
            }
            if ("isAnonymous".equalsIgnoreCase(authorizerName)) {
                return IS_ANONYMOUS_AUTHORIZER;
            }
            if ("isAuthenticated".equalsIgnoreCase(authorizerName)) {
                return IS_AUTHENTICATED_AUTHORIZER;
            }
            if ("isFullyAuthenticated".equalsIgnoreCase(authorizerName)) {
                return IS_FULLY_AUTHENTICATED_AUTHORIZER;
            }
            if ("isRemembered".equalsIgnoreCase(authorizerName)) {
                return IS_REMEMBERED_AUTHORIZER;
            }
        }
        return authorizer;
    }

    protected boolean containsClientType(List<Client> clients, Class<? extends Client> clazz) {
        for (Client client : clients) {
            if (!clazz.isAssignableFrom(client.getClass())) continue;
            return true;
        }
        return false;
    }

    protected boolean isAuthorized(WebContext context, SessionStore sessionStore, List<UserProfile> profiles, List<Authorizer> authorizers) {
        CommonHelper.assertTrue(CommonHelper.isNotEmpty(profiles), "profiles must not be null or empty");
        if (CommonHelper.isNotEmpty(authorizers)) {
            for (Authorizer authorizer : authorizers) {
                boolean isAuthorized = authorizer.isAuthorized(context, sessionStore, profiles);
                LOGGER.debug("Checking authorizer: {} -> {}", (Object)authorizer, (Object)isAuthorized);
                if (isAuthorized) continue;
                return false;
            }
        }
        return true;
    }
}

