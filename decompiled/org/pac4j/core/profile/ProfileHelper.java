/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.AnonymousProfile;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;

public final class ProfileHelper {
    private static List<String> profileClassPrefixes = Arrays.asList("org.pac4j.");

    private ProfileHelper() {
    }

    public static boolean isTypedIdOf(String id, Class<? extends UserProfile> clazz) {
        return id != null && clazz != null && id.startsWith(clazz.getName() + "#");
    }

    public static UserProfile buildUserProfileByClassCompleteName(String completeName) {
        try {
            Constructor constructor = CommonHelper.getConstructor(completeName);
            return (UserProfile)constructor.newInstance(new Object[0]);
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new TechnicalException(e);
        }
    }

    public static <U extends UserProfile> Optional<U> flatIntoOneProfile(Collection<U> profiles) {
        Optional<UserProfile> profile = profiles.stream().filter(p -> p != null && !(p instanceof AnonymousProfile)).findFirst();
        if (profile.isPresent()) {
            return profile;
        }
        return profiles.stream().filter(Objects::nonNull).findFirst();
    }

    public static <U extends UserProfile> List<U> flatIntoAProfileList(Map<String, U> profiles) {
        return new ArrayList<U>(profiles.values());
    }

    public static String sanitizeIdentifier(Object id) {
        if (id != null) {
            String sId = id.toString();
            if (sId.contains("#")) {
                String profileClass = CommonHelper.substringBefore(sId, "#");
                for (String profileClassPrefix : ProfileHelper.getProfileClassPrefixes()) {
                    if (!profileClass.startsWith(profileClassPrefix)) continue;
                    return sId.substring(profileClass.length() + 1);
                }
            }
            return sId;
        }
        return null;
    }

    public static List<String> getProfileClassPrefixes() {
        return profileClassPrefixes;
    }

    public static void setProfileClassPrefixes(List<String> profileClassPrefixes) {
        CommonHelper.assertNotNull("profileClassPrefixes", profileClassPrefixes);
        ProfileHelper.profileClassPrefixes = profileClassPrefixes;
    }
}

