/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.adapters.XmlAdapter
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Locale;

public class LocaleAdapter
extends XmlAdapter<String, Locale> {
    public Locale unmarshal(String v) throws Exception {
        Locale result = null;
        if (v == null) {
            return result;
        }
        result = Locale.forLanguageTag(v);
        return result;
    }

    public String marshal(Locale v) throws Exception {
        String result = null;
        if (v == null) {
            return result;
        }
        result = v.toLanguageTag();
        return result;
    }
}

