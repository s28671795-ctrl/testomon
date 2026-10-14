/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.text.lookup;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.lookup.AbstractStringLookup;

final class XmlDecoderStringLookup
extends AbstractStringLookup {
    static final XmlDecoderStringLookup INSTANCE = new XmlDecoderStringLookup();

    private XmlDecoderStringLookup() {
    }

    @Override
    public String lookup(String key) {
        return StringEscapeUtils.unescapeXml(key);
    }
}

