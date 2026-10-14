/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util;

import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContextFactory;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.context.session.SessionStoreFactory;
import org.pac4j.core.engine.CallbackLogic;
import org.pac4j.core.engine.LogoutLogic;
import org.pac4j.core.engine.SecurityLogic;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.profile.factory.ProfileManagerFactory;
import org.pac4j.core.util.CommonHelper;

public class FindBest {
    public static HttpActionAdapter httpActionAdapter(HttpActionAdapter localAdapter, Config config, HttpActionAdapter defaultAdapter) {
        if (localAdapter != null) {
            return localAdapter;
        }
        if (config != null && config.getHttpActionAdapter() != null) {
            return config.getHttpActionAdapter();
        }
        CommonHelper.assertNotNull("defaultAdapter", defaultAdapter);
        return defaultAdapter;
    }

    @Deprecated
    public static SessionStore sessionStore(SessionStore localSessionStore, Config config, SessionStore defaultSessionStore) {
        if (localSessionStore != null) {
            return localSessionStore;
        }
        if (config != null && config.getSessionStore() != null) {
            return config.getSessionStore();
        }
        CommonHelper.assertNotNull("defaultSessionStore", defaultSessionStore);
        return defaultSessionStore;
    }

    public static ProfileManagerFactory profileManagerFactory(ProfileManagerFactory localProfileManagerFactory, Config config, ProfileManagerFactory defaultProfileManagerFactory) {
        if (localProfileManagerFactory != null) {
            return localProfileManagerFactory;
        }
        if (config != null && config.getProfileManagerFactory() != null) {
            return config.getProfileManagerFactory();
        }
        CommonHelper.assertNotNull("defaultProfileManagerFactory", defaultProfileManagerFactory);
        return defaultProfileManagerFactory;
    }

    public static SecurityLogic securityLogic(SecurityLogic localLogic, Config config, SecurityLogic defaultLogic) {
        if (localLogic != null) {
            return localLogic;
        }
        if (config != null && config.getSecurityLogic() != null) {
            return config.getSecurityLogic();
        }
        CommonHelper.assertNotNull("defaultLogic", defaultLogic);
        return defaultLogic;
    }

    public static CallbackLogic callbackLogic(CallbackLogic localLogic, Config config, CallbackLogic defaultLogic) {
        if (localLogic != null) {
            return localLogic;
        }
        if (config != null && config.getCallbackLogic() != null) {
            return config.getCallbackLogic();
        }
        CommonHelper.assertNotNull("defaultLogic", defaultLogic);
        return defaultLogic;
    }

    public static LogoutLogic logoutLogic(LogoutLogic localLogic, Config config, LogoutLogic defaultLogic) {
        if (localLogic != null) {
            return localLogic;
        }
        if (config != null && config.getLogoutLogic() != null) {
            return config.getLogoutLogic();
        }
        CommonHelper.assertNotNull("defaultLogic", defaultLogic);
        return defaultLogic;
    }

    public static WebContextFactory webContextFactory(WebContextFactory localFactory, Config config, WebContextFactory defaultFactory) {
        if (localFactory != null) {
            return localFactory;
        }
        if (config != null && config.getWebContextFactory() != null) {
            return config.getWebContextFactory();
        }
        CommonHelper.assertNotNull("defaultWebContextFactory", defaultFactory);
        return defaultFactory;
    }

    public static SessionStoreFactory sessionStoreFactory(SessionStoreFactory localFactory, final Config config, SessionStoreFactory defaultFactory) {
        if (localFactory != null) {
            return localFactory;
        }
        if (config != null && config.getSessionStoreFactory() != null) {
            return config.getSessionStoreFactory();
        }
        if (config != null && config.getSessionStore() != null) {
            return new SessionStoreFactory(){

                @Override
                public SessionStore newSessionStore(Object ... parameters) {
                    return config.getSessionStore();
                }
            };
        }
        CommonHelper.assertNotNull("defaultSessionStoreFactory", defaultFactory);
        return defaultFactory;
    }
}

