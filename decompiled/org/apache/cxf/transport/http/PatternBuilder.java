/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.util.regex.Pattern;

public final class PatternBuilder {
    private PatternBuilder() {
    }

    public static Pattern build(String value) {
        String regexp = value.replace(".", "\\.");
        regexp = regexp.replace("*", ".*");
        return Pattern.compile(regexp);
    }
}

