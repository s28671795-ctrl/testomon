/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.client.Client;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.creator.AuthenticatorProfileCreator;
import org.pac4j.core.profile.creator.ProfileCreator;
import org.pac4j.core.profile.factory.ProfileFactory;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.InitializableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseClient
extends InitializableObject
implements Client {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String name;
    private List<AuthorizationGenerator> authorizationGenerators = new ArrayList<AuthorizationGenerator>();
    private CredentialsExtractor credentialsExtractor;
    private Authenticator authenticator;
    private ProfileCreator profileCreator = AuthenticatorProfileCreator.INSTANCE;
    private Map<String, Object> customProperties = new LinkedHashMap<String, Object>();
    private ProfileFactory profileFactoryWhenNotAuthenticated;
    private boolean multiProfile = false;
    protected Boolean saveProfileInSession;
    private static boolean warned;

    protected Optional<Credentials> retrieveCredentials(WebContext context, SessionStore sessionStore) {
        try {
            Optional<Credentials> optCredentials = this.credentialsExtractor.extract(context, sessionStore);
            optCredentials.ifPresent(credentials -> {
                long t0 = System.currentTimeMillis();
                try {
                    this.authenticator.validate((Credentials)credentials, context, sessionStore);
                }
                finally {
                    long t1 = System.currentTimeMillis();
                    this.logger.debug("Credentials validation took: {} ms", (Object)(t1 - t0));
                }
            });
            return optCredentials;
        }
        catch (CredentialsException e) {
            this.logger.info("Failed to retrieve or validate credentials: {}", (Object)e.getMessage());
            this.logger.debug("Failed to retrieve or validate credentials", e);
            return Optional.empty();
        }
    }

    @Override
    public final Optional<UserProfile> getUserProfile(Credentials credentials, WebContext context, SessionStore sessionStore) {
        this.init();
        this.logger.debug("credentials : {}", (Object)credentials);
        if (credentials == null) {
            if (this.profileFactoryWhenNotAuthenticated != null) {
                UserProfile customProfile = (UserProfile)this.profileFactoryWhenNotAuthenticated.apply(new Object[]{context});
                this.logger.debug("force custom profile when not authenticated: {}", (Object)customProfile);
                return Optional.ofNullable(customProfile);
            }
            return Optional.empty();
        }
        Optional<UserProfile> profile = this.retrieveUserProfile(credentials, context, sessionStore);
        if (profile.isPresent()) {
            profile.get().setClientName(this.getName());
            if (this.authorizationGenerators != null) {
                for (AuthorizationGenerator authorizationGenerator : this.authorizationGenerators) {
                    profile = authorizationGenerator.generate(context, sessionStore, profile.get());
                }
            }
        }
        return profile;
    }

    protected final Optional<UserProfile> retrieveUserProfile(Credentials credentials, WebContext context, SessionStore sessionStore) {
        Optional<UserProfile> profile = this.profileCreator.create(credentials, context, sessionStore);
        this.logger.debug("profile: {}", (Object)profile);
        return profile;
    }

    @Override
    public Optional<UserProfile> renewUserProfile(UserProfile profile, WebContext context, SessionStore sessionStore) {
        return Optional.empty();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        if (CommonHelper.isBlank(this.name)) {
            return this.getClass().getSimpleName();
        }
        return this.name;
    }

    public void notifySessionRenewal(String oldSessionId, WebContext context, SessionStore sessionStore) {
    }

    public List<AuthorizationGenerator> getAuthorizationGenerators() {
        return this.authorizationGenerators;
    }

    public void setAuthorizationGenerators(List<AuthorizationGenerator> authorizationGenerators) {
        CommonHelper.assertNotNull("authorizationGenerators", authorizationGenerators);
        this.authorizationGenerators = authorizationGenerators;
    }

    public void setAuthorizationGenerators(AuthorizationGenerator ... authorizationGenerators) {
        CommonHelper.assertNotNull("authorizationGenerators", authorizationGenerators);
        this.authorizationGenerators = Arrays.asList(authorizationGenerators);
    }

    public void setAuthorizationGenerator(AuthorizationGenerator authorizationGenerator) {
        this.addAuthorizationGenerator(authorizationGenerator);
    }

    public void addAuthorizationGenerator(AuthorizationGenerator authorizationGenerator) {
        CommonHelper.assertNotNull("authorizationGenerator", authorizationGenerator);
        this.authorizationGenerators.add(authorizationGenerator);
    }

    public void addAuthorizationGenerators(List<AuthorizationGenerator> authorizationGenerators) {
        CommonHelper.assertNotNull("authorizationGenerators", authorizationGenerators);
        this.authorizationGenerators.addAll(authorizationGenerators);
    }

    public CredentialsExtractor getCredentialsExtractor() {
        return this.credentialsExtractor;
    }

    protected void defaultCredentialsExtractor(CredentialsExtractor credentialsExtractor) {
        if (this.credentialsExtractor == null) {
            this.credentialsExtractor = credentialsExtractor;
        }
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

    protected void defaultAuthenticator(Authenticator authenticator) {
        if (this.authenticator == null) {
            this.authenticator = authenticator;
        }
    }

    public ProfileCreator getProfileCreator() {
        return this.profileCreator;
    }

    protected void defaultProfileCreator(ProfileCreator profileCreator) {
        if (this.profileCreator == null || this.profileCreator == AuthenticatorProfileCreator.INSTANCE) {
            this.profileCreator = profileCreator;
        }
    }

    public void setCredentialsExtractor(CredentialsExtractor credentialsExtractor) {
        this.credentialsExtractor = credentialsExtractor;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setProfileCreator(ProfileCreator profileCreator) {
        this.profileCreator = profileCreator;
    }

    public Map<String, Object> getCustomProperties() {
        return this.customProperties;
    }

    public void setCustomProperties(Map<String, Object> customProperties) {
        CommonHelper.assertNotNull("customProperties", customProperties);
        this.customProperties = customProperties;
    }

    public ProfileFactory getProfileFactoryWhenNotAuthenticated() {
        return this.profileFactoryWhenNotAuthenticated;
    }

    public void setProfileFactoryWhenNotAuthenticated(ProfileFactory profileFactoryWhenNotAuthenticated) {
        if (!warned) {
            this.logger.warn("Be careful when using the 'setProfileFactoryWhenNotAuthenticated' method: a custom profile is returned when the authentication fails or is cancelled and it is stored for the whole session. You may need to define additional 'Authorizer's to secure your web resources.");
            warned = true;
        }
        this.profileFactoryWhenNotAuthenticated = profileFactoryWhenNotAuthenticated;
    }

    public boolean isMultiProfile(WebContext context, UserProfile profile) {
        return this.multiProfile;
    }

    public void setMultiProfile(boolean multiProfile) {
        this.multiProfile = multiProfile;
    }

    public Boolean getSaveProfileInSession(WebContext context, UserProfile profile) {
        return this.saveProfileInSession;
    }

    public void setSaveProfileInSession(boolean saveProfileInSession) {
        this.saveProfileInSession = saveProfileInSession;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "name", this.getName(), "credentialsExtractor", this.credentialsExtractor, "authenticator", this.authenticator, "profileCreator", this.profileCreator, "authorizationGenerators", this.authorizationGenerators, "customProperties", this.customProperties, "profileFactoryWhenNotAuthenticated", this.profileFactoryWhenNotAuthenticated, "multiProfile", this.multiProfile, "saveProfileInSession", this.saveProfileInSession);
    }
}

