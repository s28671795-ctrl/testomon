/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.result.ResultInterface;

public class ResultWithGeneratedKeys {
    private final long updateCount;

    public static ResultWithGeneratedKeys of(long l) {
        return new ResultWithGeneratedKeys(l);
    }

    ResultWithGeneratedKeys(long l) {
        this.updateCount = l;
    }

    public ResultInterface getGeneratedKeys() {
        return null;
    }

    public long getUpdateCount() {
        return this.updateCount;
    }

    public static final class WithKeys
    extends ResultWithGeneratedKeys {
        private final ResultInterface generatedKeys;

        public WithKeys(long l, ResultInterface resultInterface) {
            super(l);
            this.generatedKeys = resultInterface;
        }

        @Override
        public ResultInterface getGeneratedKeys() {
            return this.generatedKeys;
        }
    }
}

