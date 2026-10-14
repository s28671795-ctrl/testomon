/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import org.pac4j.core.profile.converter.BooleanConverter;
import org.pac4j.core.profile.converter.ColorConverter;
import org.pac4j.core.profile.converter.DateConverter;
import org.pac4j.core.profile.converter.GenderConverter;
import org.pac4j.core.profile.converter.IntegerConverter;
import org.pac4j.core.profile.converter.LocaleConverter;
import org.pac4j.core.profile.converter.LongConverter;
import org.pac4j.core.profile.converter.StringConverter;
import org.pac4j.core.profile.converter.UrlConverter;

public final class Converters {
    public static final LocaleConverter LOCALE = new LocaleConverter();
    public static final StringConverter STRING = new StringConverter();
    public static final BooleanConverter BOOLEAN = new BooleanConverter();
    public static final IntegerConverter INTEGER = new IntegerConverter();
    public static final LongConverter LONG = new LongConverter();
    public static final ColorConverter COLOR = new ColorConverter();
    public static final GenderConverter GENDER = new GenderConverter();
    public static final String DATE_TZ_GENERAL_FORMAT = "yyyy-MM-dd'T'HH:mm:ssz";
    public static final DateConverter DATE_TZ_GENERAL = new DateConverter("yyyy-MM-dd'T'HH:mm:ssz");
    public static final String DATE_TZ_RFC822_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final DateConverter DATE_TZ_RFC822 = new DateConverter("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public static final UrlConverter URL = new UrlConverter();
}

