/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.util.Locale;
import java.util.regex.Pattern;
import org.simpleframework.xml.transform.InvalidFormatException;
import org.simpleframework.xml.transform.Transform;

class LocaleTransform
implements Transform<Locale> {
    private final Pattern pattern = Pattern.compile("_");

    @Override
    public Locale read(String locale) throws Exception {
        String[] list = this.pattern.split(locale);
        if (list.length < 1) {
            throw new InvalidFormatException("Invalid locale %s", locale);
        }
        return this.read(list);
    }

    private Locale read(String[] locale) throws Exception {
        String[] list = new String[]{"", "", ""};
        for (int i = 0; i < list.length; ++i) {
            if (i >= locale.length) continue;
            list[i] = locale[i];
        }
        return new Locale(list[0], list[1], list[2]);
    }

    @Override
    public String write(Locale locale) {
        return locale.toString();
    }
}

