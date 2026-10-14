/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.definition;

import java.util.List;
import org.pac4j.core.profile.converter.ChainingConverter;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.core.profile.definition.ProfileDefinition;
import org.pac4j.core.profile.factory.ProfileFactory;

public class CommonProfileDefinition
extends ProfileDefinition {
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String FAMILY_NAME = "family_name";
    public static final String DISPLAY_NAME = "display_name";
    public static final String GENDER = "gender";
    public static final String LOCALE = "locale";
    public static final String PICTURE_URL = "picture_url";
    public static final String PROFILE_URL = "profile_url";
    public static final String LOCATION = "location";

    public CommonProfileDefinition() {
        this.configurePrimaryAttributes();
    }

    protected void configurePrimaryAttributes() {
        this.primary(EMAIL, Converters.STRING);
        this.primary(FIRST_NAME, Converters.STRING);
        this.primary(FAMILY_NAME, Converters.STRING);
        this.primary(DISPLAY_NAME, Converters.STRING);
        this.primary(GENDER, new ChainingConverter(List.of(Converters.GENDER, Converters.STRING)));
        this.primary(LOCALE, new ChainingConverter(List.of(Converters.LOCALE, Converters.STRING)));
        this.primary(PICTURE_URL, new ChainingConverter(List.of(Converters.URL, Converters.STRING)));
        this.primary(PROFILE_URL, new ChainingConverter(List.of(Converters.URL, Converters.STRING)));
        this.primary(LOCATION, Converters.STRING);
        this.primary("username", Converters.STRING);
    }

    public CommonProfileDefinition(ProfileFactory profileFactory) {
        this();
        this.setProfileFactory(profileFactory);
    }
}

