/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import java.util.regex.Pattern;
import org.pac4j.core.profile.Gender;
import org.pac4j.core.profile.converter.AbstractAttributeConverter;

public final class GenderConverter
extends AbstractAttributeConverter {
    private final Pattern maleText;
    private final Pattern femaleText;

    public GenderConverter() {
        super(Gender.class);
        this.maleText = Pattern.compile("(^m$)|(^male$)", 2);
        this.femaleText = Pattern.compile("(^f$)|(^female$)", 2);
    }

    public GenderConverter(String maleText, String femaleText) {
        super(Gender.class);
        this.maleText = Pattern.compile(maleText);
        this.femaleText = Pattern.compile(femaleText);
    }

    protected Gender internalConvert(Object attribute) {
        String s2 = attribute.toString().toLowerCase();
        if (this.maleText.matcher(s2).matches()) {
            return Gender.MALE;
        }
        if (this.femaleText.matcher(s2).matches()) {
            return Gender.FEMALE;
        }
        return Gender.UNSPECIFIED;
    }
}

