/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.text.matcher;

import org.apache.commons.lang3.CharSequenceUtils;
import org.apache.commons.text.matcher.StringMatcherFactory;

public interface StringMatcher {
    default public StringMatcher andThen(StringMatcher stringMatcher) {
        return StringMatcherFactory.INSTANCE.andMatcher(this, stringMatcher);
    }

    default public int isMatch(char[] buffer, int pos) {
        return this.isMatch(buffer, pos, 0, buffer.length);
    }

    public int isMatch(char[] var1, int var2, int var3, int var4);

    default public int isMatch(CharSequence buffer, int pos) {
        return this.isMatch(buffer, pos, 0, buffer.length());
    }

    default public int isMatch(CharSequence buffer, int start2, int bufferStart, int bufferEnd) {
        return this.isMatch(CharSequenceUtils.toCharArray(buffer), start2, bufferEnd, bufferEnd);
    }

    default public int size() {
        return 0;
    }
}

