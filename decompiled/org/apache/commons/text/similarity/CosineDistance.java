/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.text.similarity;

import java.util.Map;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.Counter;
import org.apache.commons.text.similarity.EditDistance;
import org.apache.commons.text.similarity.RegexTokenizer;

public class CosineDistance
implements EditDistance<Double> {
    @Override
    public Double apply(CharSequence left, CharSequence right) {
        CharSequence[] leftTokens = RegexTokenizer.INSTANCE.apply(left);
        CharSequence[] rightTokens = RegexTokenizer.INSTANCE.apply(right);
        Map<CharSequence, Integer> leftVector = Counter.of(leftTokens);
        Map<CharSequence, Integer> rightVector = Counter.of(rightTokens);
        double similarity = CosineSimilarity.INSTANCE.cosineSimilarity(leftVector, rightVector);
        return 1.0 - similarity;
    }
}

