/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.text.lookup;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.lookup.AbstractStringLookup;

final class XmlEncoderStringLookup
extends AbstractStringLookup {
    static final XmlEncoderStringLookup INSTANCE = new XmlEncoderStringLookup();

    XmlEncoderStringLookup() {
    }

    @Override
    public String lookup(String key) {
        return StringEscapeUtils.escapeXml10(key);
    }
}

