/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.factory;

import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.factory.ProfileManagerFactory;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.FindBest;

public class ProfileManagerFactoryAware {
    private ProfileManagerFactory profileManagerFactory;

    protected ProfileManager getProfileManager(WebContext context, SessionStore sessionStore) {
        return (ProfileManager)FindBest.profileManagerFactory(this.profileManagerFactory, Config.INSTANCE, ProfileManagerFactory.DEFAULT).apply(context, sessionStore);
    }

    public ProfileManagerFactory getProfileManagerFactory() {
        return this.profileManagerFactory;
    }

    public void setProfileManagerFactory(ProfileManagerFactory factory2) {
        CommonHelper.assertNotNull("factory", factory2);
        this.profileManagerFactory = factory2;
    }
}

