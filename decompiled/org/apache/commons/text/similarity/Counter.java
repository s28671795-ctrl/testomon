/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.text.similarity;

import java.util.HashMap;
import java.util.Map;

final class Counter {
    public static Map<CharSequence, Integer> of(CharSequence[] tokens) {
        HashMap<CharSequence, Integer> innerCounter = new HashMap<CharSequence, Integer>();
        CharSequence[] charSequenceArray = tokens;
        int n = charSequenceArray.length;
        for (int i = 0; i < n; ++i) {
            CharSequence token;
            Integer integer = (Integer)innerCounter.get(token = charSequenceArray[i]);
            innerCounter.put(token, integer != null ? integer + 1 : 1);
        }
        return innerCounter;
    }

    private Counter() {
    }
}

