/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;

public class SpringSecurityPropertiesAuthorizationGenerator
implements AuthorizationGenerator {
    public static final String DISABLED = "disabled";
    public static final String ENABLED = "enabled";
    private Map<String, List<String>> rolesByUsers = new HashMap<String, List<String>>();

    public SpringSecurityPropertiesAuthorizationGenerator(Properties properties) {
        Set<String> keys2 = properties.stringPropertyNames();
        for (String key : keys2) {
            String latest;
            String[] parts;
            int nb;
            String value = properties.getProperty(key);
            if (!CommonHelper.isNotBlank(value) || (nb = (parts = value.split(",")).length) <= 1 || DISABLED.equals(latest = parts[nb - 1])) continue;
            ArrayList<String> roles = new ArrayList<String>(Arrays.asList(parts));
            if (ENABLED.equals(latest)) {
                roles.remove(nb - 1);
            }
            roles.remove(0);
            this.rolesByUsers.put(key, roles);
        }
    }

    @Override
    public Optional<UserProfile> generate(WebContext context, SessionStore sessionStore, UserProfile profile) {
        String id = profile.getId();
        List<String> roles = this.rolesByUsers.get(id);
        if (roles != null && !roles.isEmpty()) {
            profile.addRoles(roles);
        }
        return Optional.of(profile);
    }
}

