/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.text.similarity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.text.similarity.CharSequenceTokenizer;

final class RegexTokenizer
implements CharSequenceTokenizer<CharSequence> {
    private static final Pattern PATTERN = Pattern.compile("(\\w)+");
    static final RegexTokenizer INSTANCE = new RegexTokenizer();

    RegexTokenizer() {
    }

    @Override
    public CharSequence[] apply(CharSequence text) {
        Validate.isTrue(StringUtils.isNotBlank(text), "Invalid text", new Object[0]);
        Matcher matcher = PATTERN.matcher(text);
        ArrayList<String> tokens = new ArrayList<String>();
        while (matcher.find()) {
            tokens.add(matcher.group(0));
        }
        return tokens.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }
}

