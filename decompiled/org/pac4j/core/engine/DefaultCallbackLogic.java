/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine;

import java.util.List;
import java.util.Optional;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.finder.ClientFinder;
import org.pac4j.core.client.finder.DefaultCallbackClientFinder;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.engine.AbstractExceptionAwareLogic;
import org.pac4j.core.engine.CallbackLogic;
import org.pac4j.core.engine.savedrequest.DefaultSavedRequestHandler;
import org.pac4j.core.engine.savedrequest.SavedRequestHandler;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCallbackLogic
extends AbstractExceptionAwareLogic
implements CallbackLogic {
    public static final DefaultCallbackLogic INSTANCE = new DefaultCallbackLogic();
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCallbackLogic.class);
    private ClientFinder clientFinder = new DefaultCallbackClientFinder();
    private SavedRequestHandler savedRequestHandler = new DefaultSavedRequestHandler();

    @Override
    public Object perform(WebContext webContext, SessionStore sessionStore, Config config, HttpActionAdapter httpActionAdapter, String inputDefaultUrl, Boolean inputRenewSession, String defaultClient) {
        HttpAction action;
        LOGGER.debug("=== CALLBACK ===");
        try {
            String defaultUrl = inputDefaultUrl == null ? "/" : inputDefaultUrl;
            boolean renewSession = inputRenewSession == null || inputRenewSession != false;
            CommonHelper.assertNotNull("clientFinder", this.clientFinder);
            CommonHelper.assertNotNull("webContext", webContext);
            CommonHelper.assertNotNull("config", config);
            CommonHelper.assertNotNull("httpActionAdapter", httpActionAdapter);
            CommonHelper.assertNotBlank("defaultUrl", defaultUrl);
            Clients clients = config.getClients();
            CommonHelper.assertNotNull("clients", clients);
            List<Client> foundClients = this.clientFinder.find(clients, webContext, defaultClient);
            CommonHelper.assertTrue(foundClients != null && foundClients.size() == 1, "unable to find one indirect client for the callback: check the callback URL for a client name parameter or suffix path or ensure that your configuration defaults to one indirect client");
            Client foundClient = foundClients.get(0);
            LOGGER.debug("foundClient: {}", (Object)foundClient);
            CommonHelper.assertNotNull("foundClient", foundClient);
            Optional<Credentials> credentials = foundClient.getCredentials(webContext, sessionStore);
            LOGGER.debug("credentials: {}", (Object)credentials);
            Optional<UserProfile> optProfile = foundClient.getUserProfile(credentials.orElse(null), webContext, sessionStore);
            LOGGER.debug("optProfile: {}", (Object)optProfile);
            if (optProfile.isPresent()) {
                UserProfile profile = optProfile.get();
                boolean saveProfileInSession = ((BaseClient)foundClient).getSaveProfileInSession(webContext, profile);
                boolean multiProfile = ((BaseClient)foundClient).isMultiProfile(webContext, profile);
                LOGGER.debug("saveProfileInSession: {} / multiProfile: {}", (Object)saveProfileInSession, (Object)multiProfile);
                this.saveUserProfile(webContext, sessionStore, config, profile, saveProfileInSession, multiProfile, renewSession);
            }
            action = this.redirectToOriginallyRequestedUrl(webContext, sessionStore, defaultUrl);
        }
        catch (RuntimeException e) {
            return this.handleException(e, httpActionAdapter, webContext);
        }
        return httpActionAdapter.adapt(action, webContext);
    }

    protected void saveUserProfile(WebContext context, SessionStore sessionStore, Config config, UserProfile profile, boolean saveProfileInSession, boolean multiProfile, boolean renewSession) {
        ProfileManager manager = this.getProfileManager(context, sessionStore);
        if (profile != null) {
            manager.save(saveProfileInSession, profile, multiProfile);
            if (renewSession) {
                this.renewSession(context, sessionStore, config);
            }
        }
    }

    protected void renewSession(WebContext context, SessionStore sessionStore, Config config) {
        Optional<String> optOldSessionId = sessionStore.getSessionId(context, true);
        if (optOldSessionId.isEmpty()) {
            LOGGER.error("No old session identifier retrieved although the session creation has been requested");
        } else {
            String oldSessionId = optOldSessionId.get();
            boolean renewed = sessionStore.renewSession(context);
            if (renewed) {
                Optional<String> optNewSessionId = sessionStore.getSessionId(context, true);
                if (optNewSessionId.isEmpty()) {
                    LOGGER.error("No new session identifier retrieved although the session creation has been requested");
                } else {
                    String newSessionId = optNewSessionId.get();
                    LOGGER.debug("Renewing session: {} -> {}", (Object)oldSessionId, (Object)newSessionId);
                    Clients clients = config.getClients();
                    if (clients != null) {
                        List<Client> clientList = clients.getClients();
                        for (Client client : clientList) {
                            BaseClient baseClient = (BaseClient)client;
                            baseClient.notifySessionRenewal(oldSessionId, context, sessionStore);
                        }
                    }
                }
            } else {
                LOGGER.error("Unable to renew the session. The session store may not support this feature");
            }
        }
    }

    protected HttpAction redirectToOriginallyRequestedUrl(WebContext context, SessionStore sessionStore, String defaultUrl) {
        return this.savedRequestHandler.restore(context, sessionStore, defaultUrl);
    }

    public ClientFinder getClientFinder() {
        return this.clientFinder;
    }

    public void setClientFinder(ClientFinder clientFinder) {
        this.clientFinder = clientFinder;
    }

    public SavedRequestHandler getSavedRequestHandler() {
        return this.savedRequestHandler;
    }

    public void setSavedRequestHandler(SavedRequestHandler savedRequestHandler) {
        this.savedRequestHandler = savedRequestHandler;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "clientFinder", this.clientFinder, "errorUrl", this.getErrorUrl(), "savedRequestHandler", this.savedRequestHandler);
    }
}

