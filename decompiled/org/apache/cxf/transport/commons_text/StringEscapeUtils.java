/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.commons_text;

import org.apache.cxf.transport.commons_text.AggregateTranslator;
import org.apache.cxf.transport.commons_text.CharSequenceTranslator;
import org.apache.cxf.transport.commons_text.EntityArrays;
import org.apache.cxf.transport.commons_text.LookupTranslator;

public final class StringEscapeUtils {
    public static final CharSequenceTranslator ESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE), new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE));

    private StringEscapeUtils() {
    }

    public static String escapeHtml4(String input) {
        return ESCAPE_HTML4.translate(input);
    }
}

