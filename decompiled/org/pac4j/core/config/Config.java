/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.context.WebContextFactory;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.context.session.SessionStoreFactory;
import org.pac4j.core.engine.CallbackLogic;
import org.pac4j.core.engine.LogoutLogic;
import org.pac4j.core.engine.SecurityLogic;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.profile.factory.ProfileManagerFactory;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    public static final Config INSTANCE = new Config();
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private ProfileManagerFactory profileManagerFactory;
    protected Clients clients = new Clients();
    protected Map<String, Authorizer> authorizers = new HashMap<String, Authorizer>();
    protected Map<String, Matcher> matchers = new HashMap<String, Matcher>();
    protected SessionStore sessionStore;
    protected HttpActionAdapter httpActionAdapter;
    protected SecurityLogic securityLogic;
    protected CallbackLogic callbackLogic;
    protected LogoutLogic logoutLogic;
    protected WebContextFactory webContextFactory;
    protected SessionStoreFactory sessionStoreFactory;

    public Config() {
    }

    public Config(Client client) {
        this.clients = new Clients(client);
    }

    public Config(Clients clients) {
        this.clients = clients;
    }

    public Config(List<Client> clients) {
        this.clients = new Clients(clients);
    }

    public Config(Client ... clients) {
        this.clients = new Clients(clients);
    }

    public Config(String callbackUrl, Client client) {
        this.clients = new Clients(callbackUrl, client);
    }

    public Config(String callbackUrl, Client ... clients) {
        this.clients = new Clients(callbackUrl, clients);
    }

    public Config(String callbackUrl, List<Client> clients) {
        this.clients = new Clients(callbackUrl, clients);
    }

    public Config(Map<String, Authorizer> authorizers) {
        this.setAuthorizers(authorizers);
    }

    public Config(Clients clients, Map<String, Authorizer> authorizers) {
        this.clients = clients;
        this.setAuthorizers(authorizers);
    }

    public Config(Client client, Map<String, Authorizer> authorizers) {
        this.clients = new Clients(client);
        this.setAuthorizers(authorizers);
    }

    public Config(Map<String, Authorizer> authorizers, Client ... clients) {
        this.clients = new Clients(clients);
        this.setAuthorizers(authorizers);
    }

    public Config(String callbackUrl, Map<String, Authorizer> authorizers, Client ... clients) {
        this.clients = new Clients(callbackUrl, clients);
        this.setAuthorizers(authorizers);
    }

    public Config(String callbackUrl, Client client, Map<String, Authorizer> authorizers) {
        this.clients = new Clients(callbackUrl, client);
        this.setAuthorizers(authorizers);
    }

    public Clients getClients() {
        return this.clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public Map<String, Authorizer> getAuthorizers() {
        return this.authorizers;
    }

    public void setAuthorizer(Authorizer authorizer) {
        CommonHelper.assertNotNull("authorizer", authorizer);
        this.authorizers.put(authorizer.getClass().getSimpleName(), authorizer);
    }

    public void setAuthorizers(Map<String, Authorizer> authorizers) {
        CommonHelper.assertNotNull("authorizers", authorizers);
        this.authorizers = authorizers;
    }

    public void addAuthorizer(String name, Authorizer authorizer) {
        this.authorizers.put(name, authorizer);
    }

    public Map<String, Matcher> getMatchers() {
        return this.matchers;
    }

    public void setMatcher(Matcher matcher) {
        CommonHelper.assertNotNull("matcher", matcher);
        this.matchers.put(matcher.getClass().getSimpleName(), matcher);
    }

    public void setMatchers(Map<String, Matcher> matchers) {
        CommonHelper.assertNotNull("matchers", matchers);
        this.matchers = matchers;
    }

    public void addMatcher(String name, Matcher matcher) {
        this.matchers.put(name, matcher);
    }

    @Deprecated
    public SessionStore getSessionStore() {
        return this.sessionStore;
    }

    @Deprecated
    public void setSessionStore(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Deprecated
    public void defaultSessionStore(SessionStore sessionStore) {
        if (this.sessionStore == null) {
            this.sessionStore = sessionStore;
        }
    }

    public HttpActionAdapter getHttpActionAdapter() {
        return this.httpActionAdapter;
    }

    public void setHttpActionAdapter(HttpActionAdapter httpActionAdapter) {
        this.httpActionAdapter = httpActionAdapter;
    }

    public SecurityLogic getSecurityLogic() {
        return this.securityLogic;
    }

    public void setSecurityLogic(SecurityLogic securityLogic) {
        this.securityLogic = securityLogic;
    }

    public CallbackLogic getCallbackLogic() {
        return this.callbackLogic;
    }

    public void setCallbackLogic(CallbackLogic callbackLogic) {
        this.callbackLogic = callbackLogic;
    }

    public LogoutLogic getLogoutLogic() {
        return this.logoutLogic;
    }

    public void setLogoutLogic(LogoutLogic logoutLogic) {
        this.logoutLogic = logoutLogic;
    }

    public WebContextFactory getWebContextFactory() {
        return this.webContextFactory;
    }

    public void setWebContextFactory(WebContextFactory webContextFactory) {
        this.webContextFactory = webContextFactory;
    }

    public SessionStoreFactory getSessionStoreFactory() {
        return this.sessionStoreFactory;
    }

    public void setSessionStoreFactory(SessionStoreFactory sessionStoreFactory) {
        this.sessionStoreFactory = sessionStoreFactory;
    }

    public void defaultSessionStoreFactory(SessionStoreFactory sessionStoreFactory) {
        if (this.sessionStoreFactory == null) {
            this.sessionStoreFactory = sessionStoreFactory;
        }
    }

    public ProfileManagerFactory getProfileManagerFactory() {
        if (this.profileManagerFactory != null) {
            return this.profileManagerFactory;
        }
        return Config.INSTANCE.profileManagerFactory;
    }

    public void setProfileManagerFactory(ProfileManagerFactory profileManagerFactory) {
        this.profileManagerFactory = profileManagerFactory;
        Config.INSTANCE.profileManagerFactory = profileManagerFactory;
    }

    public void defaultProfileManagerFactory(ProfileManagerFactory profileManagerFactory) {
        if (this.profileManagerFactory == null) {
            this.setProfileManagerFactory(profileManagerFactory);
        }
    }

    @Deprecated
    public static void setProfileManagerFactory(String name, ProfileManagerFactory profileManagerFactory) {
        CommonHelper.assertNotNull("profileManagerFactory", profileManagerFactory);
        LOGGER.info("Setting Config.profileManagerFactory: {}", (Object)name);
        Config.INSTANCE.profileManagerFactory = profileManagerFactory;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public static void defaultProfileManagerFactory(String name, ProfileManagerFactory profileManagerFactory) {
        if (Config.INSTANCE.profileManagerFactory == null) {
            Config config = INSTANCE;
            synchronized (config) {
                if (Config.INSTANCE.profileManagerFactory == null) {
                    Config.setProfileManagerFactory(name, profileManagerFactory);
                }
            }
        }
    }

    public static void setConfig(Config config) {
        INSTANCE.setClients(config.getClients());
        INSTANCE.setAuthorizers(config.getAuthorizers());
        INSTANCE.setMatchers(config.getMatchers());
        INSTANCE.setSessionStore(config.getSessionStore());
        INSTANCE.setHttpActionAdapter(config.getHttpActionAdapter());
        INSTANCE.setSecurityLogic(config.getSecurityLogic());
        INSTANCE.setCallbackLogic(config.getCallbackLogic());
        INSTANCE.setLogoutLogic(config.getLogoutLogic());
        INSTANCE.setWebContextFactory(config.getWebContextFactory());
        INSTANCE.setSessionStoreFactory(config.getSessionStoreFactory());
    }
}

