/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.authorization.authorizer.IsAuthenticatedAuthorizer;
import org.pac4j.core.client.Client;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileManager.class);
    private final Authorizer IS_AUTHENTICATED_AUTHORIZER = new IsAuthenticatedAuthorizer();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final WebContext context;
    protected SessionStore sessionStore;
    protected Config config;

    public ProfileManager(WebContext context, SessionStore sessionStore) {
        CommonHelper.assertNotNull("context", context);
        CommonHelper.assertNotNull("sessionStore", sessionStore);
        this.context = context;
        this.sessionStore = sessionStore;
    }

    public Optional<UserProfile> getProfile() {
        LinkedHashMap<String, UserProfile> allProfiles = this.retrieveAll(true);
        return ProfileHelper.flatIntoOneProfile(allProfiles.values());
    }

    public <U extends UserProfile> Optional<U> getProfile(Class<U> clazz) {
        return this.getProfile();
    }

    public List<UserProfile> getProfiles() {
        LinkedHashMap<String, UserProfile> profiles = this.retrieveAll(true);
        return ProfileHelper.flatIntoAProfileList(profiles);
    }

    protected LinkedHashMap<String, UserProfile> retrieveAll(boolean readFromSession) {
        LinkedHashMap<String, UserProfile> profiles = new LinkedHashMap<String, UserProfile>();
        this.context.getRequestAttribute("pac4jUserProfiles").ifPresent(requestAttribute -> {
            LOGGER.debug("Retrieved profiles (request): {}", requestAttribute);
            profiles.putAll((Map)requestAttribute);
        });
        if (readFromSession) {
            this.sessionStore.get(this.context, "pac4jUserProfiles").ifPresent(sessionAttribute -> {
                LOGGER.debug("Retrieved profiles (session): {}", sessionAttribute);
                profiles.putAll((Map)sessionAttribute);
            });
        }
        this.removeOrRenewExpiredProfiles(profiles, readFromSession);
        return profiles;
    }

    protected void removeOrRenewExpiredProfiles(LinkedHashMap<String, UserProfile> profiles, boolean readFromSession) {
        boolean profilesUpdated = false;
        for (Map.Entry<String, UserProfile> entry : profiles.entrySet()) {
            Optional<Client> client;
            String key = entry.getKey();
            UserProfile profile = entry.getValue();
            if (!profile.isExpired()) continue;
            LOGGER.debug("Expired profile: {}", (Object)profile);
            profilesUpdated = true;
            profiles.remove(key);
            if (this.config == null || profile.getClientName() == null || !(client = this.config.getClients().findClient(profile.getClientName())).isPresent()) continue;
            try {
                Optional<UserProfile> newProfile = client.get().renewUserProfile(profile, this.context, this.sessionStore);
                if (!newProfile.isPresent()) continue;
                LOGGER.debug("Renewed by profile: {}", (Object)newProfile);
                profiles.put(key, newProfile.get());
            }
            catch (RuntimeException e) {
                this.logger.error("Unable to renew the user profile for key: {}", (Object)key, (Object)e);
            }
        }
        if (profilesUpdated) {
            this.saveAll(profiles, readFromSession);
        }
    }

    public void removeProfiles() {
        boolean sessionExists = this.sessionStore.getSessionId(this.context, false).isPresent();
        if (sessionExists) {
            LOGGER.debug("Removing profiles from session");
            this.sessionStore.set(this.context, "pac4jUserProfiles", new LinkedHashMap());
        }
        LOGGER.debug("Removing profiles from request");
        this.context.setRequestAttribute("pac4jUserProfiles", new LinkedHashMap());
    }

    public void save(boolean saveInSession, UserProfile profile, boolean multiProfile) {
        LinkedHashMap<String, UserProfile> profiles;
        String clientName = this.retrieveClientName(profile);
        if (multiProfile) {
            profiles = this.retrieveAll(saveInSession);
            profiles.remove(clientName);
        } else {
            profiles = new LinkedHashMap();
        }
        profiles.put(clientName, profile);
        this.saveAll(profiles, saveInSession);
    }

    protected String retrieveClientName(UserProfile profile) {
        String clientName = profile.getClientName();
        if (clientName == null) {
            clientName = "DEFAULT";
        }
        return clientName;
    }

    protected void saveAll(LinkedHashMap<String, UserProfile> profiles, boolean saveInSession) {
        if (saveInSession) {
            LOGGER.debug("Saving profiles (session): {}", (Object)profiles);
            this.sessionStore.set(this.context, "pac4jUserProfiles", profiles);
        }
        LOGGER.debug("Saving profiles (request): {}", (Object)profiles);
        this.context.setRequestAttribute("pac4jUserProfiles", profiles);
    }

    public boolean isAuthenticated() {
        try {
            return this.IS_AUTHENTICATED_AUTHORIZER.isAuthorized(this.context, this.sessionStore, this.getProfiles());
        }
        catch (HttpAction e) {
            throw new TechnicalException(e);
        }
    }

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}

