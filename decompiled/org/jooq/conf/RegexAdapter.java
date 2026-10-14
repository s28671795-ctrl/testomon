/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.adapters.XmlAdapter
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.regex.Pattern;

public class RegexAdapter
extends XmlAdapter<String, Pattern> {
    public Pattern unmarshal(String v) throws Exception {
        return v == null ? null : Pattern.compile(v);
    }

    public String marshal(Pattern v) throws Exception {
        return v == null ? null : v.pattern();
    }
}

