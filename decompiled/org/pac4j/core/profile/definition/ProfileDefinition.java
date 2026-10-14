/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.AttributeLocation;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.converter.AttributeConverter;
import org.pac4j.core.profile.factory.ProfileFactory;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProfileDefinition {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String profileId = null;
    private final List<String> primaries = new ArrayList<String>();
    private final List<String> secondaries = new ArrayList<String>();
    private final Map<String, AttributeConverter> converters = new HashMap<String, AttributeConverter>();
    private ProfileFactory profileFactory = parameters -> new CommonProfile();
    private boolean restoreProfileFromTypedId = false;

    public UserProfile newProfile(Object ... parameters) {
        Object typedId;
        if (this.restoreProfileFromTypedId && (typedId = this.getParameter(parameters, 0)) instanceof String) {
            this.logger.debug("Building user profile based on typedId: {}", typedId);
            String sTypedId = (String)typedId;
            if (sTypedId.contains("#")) {
                String profileClass = CommonHelper.substringBefore(sTypedId, "#");
                for (String profileClassPrefix : ProfileHelper.getProfileClassPrefixes()) {
                    if (!profileClass.startsWith(profileClassPrefix)) continue;
                    try {
                        return ProfileHelper.buildUserProfileByClassCompleteName(profileClass);
                    }
                    catch (TechnicalException e) {
                        this.logger.error("Cannot build instance for class name: {}", (Object)profileClass, (Object)e);
                    }
                }
            }
        }
        return (UserProfile)this.profileFactory.apply(parameters);
    }

    protected Object getParameter(Object[] parameters, int num) {
        if (parameters != null && parameters.length >= num) {
            return parameters[num];
        }
        return null;
    }

    public void convertAndAdd(UserProfile profile, AttributeLocation attributeLocation, String name, Object value) {
        if (value != null) {
            Object convertedValue;
            AttributeConverter converter = this.converters.get(name);
            if (converter != null) {
                convertedValue = converter.convert(value);
                if (convertedValue != null) {
                    this.logger.debug("converted to => key: {} / value: {} / {}", name, convertedValue, convertedValue.getClass());
                }
            } else {
                convertedValue = value;
                this.logger.debug("no conversion => key: {} / value: {} / {}", name, convertedValue, convertedValue.getClass());
            }
            if (attributeLocation.equals((Object)AttributeLocation.AUTHENTICATION_ATTRIBUTE)) {
                profile.addAuthenticationAttribute(name, convertedValue);
            } else {
                profile.addAttribute(name, convertedValue);
            }
        }
    }

    public void convertAndAdd(UserProfile profile, Map<String, Object> profileAttributes, Map<String, Object> authenticationAttributes) {
        if (profileAttributes != null) {
            profileAttributes.entrySet().stream().forEach(entry -> this.convertAndAdd(profile, AttributeLocation.PROFILE_ATTRIBUTE, (String)entry.getKey(), entry.getValue()));
        }
        if (authenticationAttributes != null) {
            authenticationAttributes.entrySet().stream().forEach(entry -> this.convertAndAdd(profile, AttributeLocation.AUTHENTICATION_ATTRIBUTE, (String)entry.getKey(), entry.getValue()));
        }
    }

    protected void setProfileFactory(ProfileFactory profileFactory) {
        CommonHelper.assertNotNull("profileFactory", profileFactory);
        this.profileFactory = profileFactory;
    }

    protected void primary(String name, AttributeConverter converter) {
        this.primaries.add(name);
        this.converters.put(name, converter);
    }

    protected void secondary(String name, AttributeConverter converter) {
        this.secondaries.add(name);
        this.converters.put(name, converter);
    }

    public List<String> getPrimaryAttributes() {
        return this.primaries;
    }

    public List<String> getSecondaryAttributes() {
        return this.secondaries;
    }

    public Map<String, AttributeConverter> getConverters() {
        return this.converters;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileId() {
        return this.profileId;
    }

    public boolean isRestoreProfileFromTypedId() {
        return this.restoreProfileFromTypedId;
    }

    public void setRestoreProfileFromTypedId(boolean restoreProfileFromTypedId) {
        this.restoreProfileFromTypedId = restoreProfileFromTypedId;
    }
}

