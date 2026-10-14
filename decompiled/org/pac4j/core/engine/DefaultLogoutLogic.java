/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.engine.AbstractExceptionAwareLogic;
import org.pac4j.core.engine.LogoutLogic;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.NoContentAction;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.HttpActionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogoutLogic
extends AbstractExceptionAwareLogic
implements LogoutLogic {
    public static final DefaultLogoutLogic INSTANCE = new DefaultLogoutLogic();
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLogoutLogic.class);

    @Override
    public Object perform(WebContext context, SessionStore sessionStore, Config config, HttpActionAdapter httpActionAdapter, String defaultUrl, String inputLogoutUrlPattern, Boolean inputLocalLogout, Boolean inputDestroySession, Boolean inputCentralLogout) {
        HttpAction action;
        block10: {
            LOGGER.debug("=== LOGOUT ===");
            try {
                String logoutUrlPattern = inputLogoutUrlPattern == null ? "^(\\/|\\/[^\\/].*)$" : inputLogoutUrlPattern;
                boolean localLogout = inputLocalLogout == null || inputLocalLogout != false;
                boolean destroySession = inputDestroySession != null && inputDestroySession != false;
                boolean centralLogout = inputCentralLogout != null && inputCentralLogout != false;
                CommonHelper.assertNotNull("context", context);
                CommonHelper.assertNotNull("config", config);
                CommonHelper.assertNotNull("httpActionAdapter", httpActionAdapter);
                CommonHelper.assertNotBlank("logoutUrlPattern", logoutUrlPattern);
                Clients configClients = config.getClients();
                CommonHelper.assertNotNull("configClients", configClients);
                ProfileManager manager = this.getProfileManager(context, sessionStore);
                manager.setConfig(config);
                List<UserProfile> profiles = manager.getProfiles();
                Optional<String> url2 = context.getRequestParameter("url");
                String redirectUrl = defaultUrl;
                if (url2.isPresent() && Pattern.matches(logoutUrlPattern, url2.get())) {
                    redirectUrl = url2.get();
                }
                LOGGER.debug("redirectUrl: {}", (Object)redirectUrl);
                action = redirectUrl != null ? HttpActionHelper.buildRedirectUrlAction(context, redirectUrl) : NoContentAction.INSTANCE;
                if (localLogout || profiles.size() > 1) {
                    LOGGER.debug("Performing application logout");
                    manager.removeProfiles();
                    if (destroySession) {
                        if (sessionStore != null) {
                            boolean removed = sessionStore.destroySession(context);
                            if (!removed) {
                                LOGGER.error("Unable to destroy the web session. The session store may not support this feature");
                            }
                        } else {
                            LOGGER.error("No session store available for this web context");
                        }
                    }
                }
                if (!centralLogout) break block10;
                LOGGER.debug("Performing central logout");
                for (UserProfile profile : profiles) {
                    Optional<Client> client;
                    LOGGER.debug("Profile: {}", (Object)profile);
                    String clientName = profile.getClientName();
                    if (clientName == null || !(client = configClients.findClient(clientName)).isPresent()) continue;
                    String targetUrl = null;
                    if (redirectUrl != null && ((redirectUrl = this.enhanceRedirectUrl(config, client.get(), context, sessionStore, redirectUrl)).startsWith("http") || redirectUrl.startsWith("https"))) {
                        targetUrl = redirectUrl;
                    }
                    Optional<RedirectionAction> logoutAction = client.get().getLogoutAction(context, sessionStore, profile, targetUrl);
                    LOGGER.debug("Logout action: {}", (Object)logoutAction);
                    if (!logoutAction.isPresent()) continue;
                    action = logoutAction.get();
                    break;
                }
            }
            catch (RuntimeException e) {
                return this.handleException(e, httpActionAdapter, context);
            }
        }
        return httpActionAdapter.adapt(action, context);
    }

    protected String enhanceRedirectUrl(Config config, Client client, WebContext context, SessionStore sessionStore, String redirectUrl) {
        return redirectUrl;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "errorUrl", this.getErrorUrl());
    }
}

