/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.generator;

import java.util.Optional;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.service.ProfileService;
import org.pac4j.core.util.CommonHelper;

public class LoadLinkedUserAuthorizationGenerator
implements AuthorizationGenerator {
    private ProfileService profileService;
    private boolean failIfLinkedUserNotFound = true;

    public LoadLinkedUserAuthorizationGenerator() {
    }

    public LoadLinkedUserAuthorizationGenerator(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public Optional<UserProfile> generate(WebContext context, SessionStore sessionStore, UserProfile profile) {
        CommonHelper.assertNotNull("profileService", this.profileService);
        Object linkedProfile = this.profileService.findByLinkedId(profile.getId());
        if (linkedProfile != null) {
            return Optional.ofNullable(linkedProfile);
        }
        if (this.failIfLinkedUserNotFound) {
            throw new TechnicalException("No linked account found for: " + profile);
        }
        return Optional.ofNullable(profile);
    }

    public ProfileService getProfileService() {
        return this.profileService;
    }

    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public boolean isFailIfLinkedUserNotFound() {
        return this.failIfLinkedUserNotFound;
    }

    public void setFailIfLinkedUserNotFound(boolean failIfLinkedUserNotFound) {
        this.failIfLinkedUserNotFound = failIfLinkedUserNotFound;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "profileService", this.profileService, "failIfLinkedUserNotFound", this.failIfLinkedUserNotFound);
    }
}

