/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.serialization;

import java.util.regex.Pattern;
import org.apache.commons.io.serialization.ClassNameMatcher;

final class RegexpClassNameMatcher
implements ClassNameMatcher {
    private final Pattern pattern;

    public RegexpClassNameMatcher(String regex2) {
        this(Pattern.compile(regex2));
    }

    public RegexpClassNameMatcher(Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Null pattern");
        }
        this.pattern = pattern;
    }

    @Override
    public boolean matches(String className) {
        return this.pattern.matcher(className).matches();
    }
}

