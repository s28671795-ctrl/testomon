/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.pac4j.core.authorization.checker.AuthorizationChecker;
import org.pac4j.core.authorization.checker.DefaultAuthorizationChecker;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.DirectClient;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.client.finder.ClientFinder;
import org.pac4j.core.client.finder.DefaultSecurityClientFinder;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.engine.AbstractExceptionAwareLogic;
import org.pac4j.core.engine.SecurityGrantedAccessAdapter;
import org.pac4j.core.engine.SecurityLogic;
import org.pac4j.core.engine.savedrequest.DefaultSavedRequestHandler;
import org.pac4j.core.engine.savedrequest.SavedRequestHandler;
import org.pac4j.core.exception.http.ForbiddenAction;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.matching.checker.DefaultMatchingChecker;
import org.pac4j.core.matching.checker.MatchingChecker;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.HttpActionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSecurityLogic
extends AbstractExceptionAwareLogic
implements SecurityLogic {
    public static final DefaultSecurityLogic INSTANCE = new DefaultSecurityLogic();
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSecurityLogic.class);
    private ClientFinder clientFinder = new DefaultSecurityClientFinder();
    private AuthorizationChecker authorizationChecker = new DefaultAuthorizationChecker();
    private MatchingChecker matchingChecker = new DefaultMatchingChecker();
    private SavedRequestHandler savedRequestHandler = new DefaultSavedRequestHandler();
    private boolean loadProfilesFromSession = true;

    @Override
    public Object perform(WebContext context, SessionStore sessionStore, Config config, SecurityGrantedAccessAdapter securityGrantedAccessAdapter, HttpActionAdapter httpActionAdapter, String clients, String authorizers, String matchers, Object ... parameters) {
        HttpAction action;
        block11: {
            LOGGER.debug("=== SECURITY ===");
            try {
                CommonHelper.assertNotNull("context", context);
                CommonHelper.assertNotNull("config", config);
                CommonHelper.assertNotNull("httpActionAdapter", httpActionAdapter);
                CommonHelper.assertNotNull("clientFinder", this.clientFinder);
                CommonHelper.assertNotNull("authorizationChecker", this.authorizationChecker);
                CommonHelper.assertNotNull("matchingChecker", this.matchingChecker);
                Clients configClients = config.getClients();
                CommonHelper.assertNotNull("configClients", configClients);
                LOGGER.debug("url: {}", (Object)context.getFullRequestURL());
                LOGGER.debug("clients: {} | matchers: {}", (Object)clients, (Object)matchers);
                List<Client> currentClients = this.clientFinder.find(configClients, context, clients);
                LOGGER.debug("currentClients: {}", (Object)currentClients);
                if (this.matchingChecker.matches(context, sessionStore, matchers, config.getMatchers(), currentClients)) {
                    ProfileManager manager = this.getProfileManager(context, sessionStore);
                    manager.setConfig(config);
                    List<UserProfile> profiles = this.loadProfilesFromSession ? this.loadProfiles(manager, context, sessionStore, currentClients) : List.of();
                    LOGGER.debug("Loaded profiles (from session: {}): {} ", (Object)this.loadProfilesFromSession, (Object)profiles);
                    if (CommonHelper.isEmpty(profiles) && CommonHelper.isNotEmpty(currentClients)) {
                        boolean updated = false;
                        for (Client currentClient : currentClients) {
                            if (!(currentClient instanceof DirectClient)) continue;
                            LOGGER.debug("Performing authentication for direct client: {}", (Object)currentClient);
                            Optional<Credentials> credentials = currentClient.getCredentials(context, sessionStore);
                            LOGGER.debug("credentials: {}", (Object)credentials);
                            if (!credentials.isPresent()) continue;
                            Optional<UserProfile> optProfile = currentClient.getUserProfile(credentials.get(), context, sessionStore);
                            LOGGER.debug("profile: {}", (Object)optProfile);
                            if (!optProfile.isPresent()) continue;
                            UserProfile profile = optProfile.get();
                            DirectClient directClient = (DirectClient)currentClient;
                            boolean saveProfileInSession = directClient.getSaveProfileInSession(context, profile);
                            boolean multiProfile = directClient.isMultiProfile(context, profile);
                            LOGGER.debug("saveProfileInSession: {} / multiProfile: {}", (Object)saveProfileInSession, (Object)multiProfile);
                            manager.save(saveProfileInSession, profile, multiProfile);
                            updated = true;
                            if (multiProfile) continue;
                            break;
                        }
                        if (updated) {
                            profiles = this.loadProfiles(manager, context, sessionStore, currentClients);
                            LOGGER.debug("Reloaded profiles: {}", (Object)profiles);
                        }
                    }
                    if (CommonHelper.isNotEmpty(profiles)) {
                        LOGGER.debug("authorizers: {}", (Object)authorizers);
                        if (this.authorizationChecker.isAuthorized(context, sessionStore, profiles, authorizers, config.getAuthorizers(), currentClients)) {
                            LOGGER.debug("authenticated and authorized -> grant access");
                            return securityGrantedAccessAdapter.adapt(context, sessionStore, profiles, parameters);
                        }
                        LOGGER.debug("forbidden");
                        action = this.forbidden(context, sessionStore, currentClients, profiles, authorizers);
                    } else if (this.startAuthentication(context, sessionStore, currentClients)) {
                        LOGGER.debug("Starting authentication");
                        this.saveRequestedUrl(context, sessionStore, currentClients, config.getClients().getAjaxRequestResolver());
                        action = this.redirectToIdentityProvider(context, sessionStore, currentClients);
                    } else {
                        LOGGER.debug("unauthorized");
                        action = this.unauthorized(context, sessionStore, currentClients);
                    }
                    break block11;
                }
                LOGGER.debug("no matching for this request -> grant access");
                return securityGrantedAccessAdapter.adapt(context, sessionStore, Collections.emptyList(), parameters);
            }
            catch (Exception e) {
                return this.handleException(e, httpActionAdapter, context);
            }
        }
        return httpActionAdapter.adapt(action, context);
    }

    protected List<UserProfile> loadProfiles(ProfileManager manager, WebContext context, SessionStore sessionStore, List<Client> clients) {
        return manager.getProfiles();
    }

    protected HttpAction forbidden(WebContext context, SessionStore sessionStore, List<Client> currentClients, List<UserProfile> profiles, String authorizers) {
        return new ForbiddenAction();
    }

    protected boolean startAuthentication(WebContext context, SessionStore sessionStore, List<Client> currentClients) {
        return CommonHelper.isNotEmpty(currentClients) && currentClients.get(0) instanceof IndirectClient;
    }

    protected void saveRequestedUrl(WebContext context, SessionStore sessionStore, List<Client> currentClients, AjaxRequestResolver ajaxRequestResolver) {
        if (ajaxRequestResolver == null || !ajaxRequestResolver.isAjax(context, sessionStore)) {
            this.savedRequestHandler.save(context, sessionStore);
        }
    }

    protected HttpAction redirectToIdentityProvider(WebContext context, SessionStore sessionStore, List<Client> currentClients) {
        IndirectClient currentClient = (IndirectClient)currentClients.get(0);
        return currentClient.getRedirectionAction(context, sessionStore).get();
    }

    protected HttpAction unauthorized(WebContext context, SessionStore sessionStore, List<Client> currentClients) {
        return HttpActionHelper.buildUnauthenticatedAction(context);
    }

    public ClientFinder getClientFinder() {
        return this.clientFinder;
    }

    public void setClientFinder(ClientFinder clientFinder) {
        this.clientFinder = clientFinder;
    }

    public AuthorizationChecker getAuthorizationChecker() {
        return this.authorizationChecker;
    }

    public void setAuthorizationChecker(AuthorizationChecker authorizationChecker) {
        this.authorizationChecker = authorizationChecker;
    }

    public MatchingChecker getMatchingChecker() {
        return this.matchingChecker;
    }

    public void setMatchingChecker(MatchingChecker matchingChecker) {
        this.matchingChecker = matchingChecker;
    }

    public SavedRequestHandler getSavedRequestHandler() {
        return this.savedRequestHandler;
    }

    public void setSavedRequestHandler(SavedRequestHandler savedRequestHandler) {
        this.savedRequestHandler = savedRequestHandler;
    }

    public void setLoadProfilesFromSession(boolean loadProfilesFromSession) {
        this.loadProfilesFromSession = loadProfilesFromSession;
    }

    public boolean isLoadProfilesFromSession() {
        return this.loadProfilesFromSession;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "clientFinder", this.clientFinder, "authorizationChecker", this.authorizationChecker, "matchingChecker", this.matchingChecker, "errorUrl", this.getErrorUrl(), "savedRequestHandler", this.savedRequestHandler);
    }
}

