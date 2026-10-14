/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile;

import java.net.URI;
import java.util.Date;
import java.util.Locale;
import org.pac4j.core.profile.BasicUserProfile;
import org.pac4j.core.profile.Gender;

public class CommonProfile
extends BasicUserProfile {
    private static final long serialVersionUID = -1856159870249261877L;

    public CommonProfile() {
        this(true);
    }

    public CommonProfile(boolean canMergeAttributes) {
        super(canMergeAttributes);
    }

    public String getEmail() {
        return this.getAttributeAsString("email");
    }

    public String getFirstName() {
        return this.getAttributeAsString("first_name");
    }

    public String getFamilyName() {
        return this.getAttributeAsString("family_name");
    }

    public String getDisplayName() {
        return this.getAttributeAsString("display_name");
    }

    @Override
    public String getUsername() {
        return this.getAttributeAsString("username");
    }

    public Gender getGender() {
        return this.getAttributeAsType("gender", Gender.class, Gender.UNSPECIFIED);
    }

    public Locale getLocale() {
        return this.getAttributeAsType("locale", Locale.class, null);
    }

    public URI getPictureUrl() {
        return this.getAttributeAsType("picture_url", URI.class, null);
    }

    public URI getProfileUrl() {
        return this.getAttributeAsType("profile_url", URI.class, null);
    }

    public String getLocation() {
        return this.getAttributeAsString("location");
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    protected String getAttributeAsString(String name) {
        Object value = this.getAttribute(name);
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    protected <T> T getAttributeAsType(String name, Class<T> clazz, T defaultValue) {
        Object value = this.getAttribute(name);
        if (value != null && clazz.isAssignableFrom(value.getClass())) {
            return clazz.cast(value);
        }
        return defaultValue;
    }

    protected Date getAttributeAsDate(String name) {
        Object value = this.getAttribute(name);
        if (value instanceof Long) {
            return new Date((Long)value);
        }
        if (value instanceof Double) {
            return new Date(((Double)value).longValue());
        }
        return (Date)this.getAttribute(name);
    }
}

