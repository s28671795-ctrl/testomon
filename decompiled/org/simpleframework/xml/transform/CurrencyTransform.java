/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.util.Currency;
import org.simpleframework.xml.transform.Transform;

class CurrencyTransform
implements Transform<Currency> {
    CurrencyTransform() {
    }

    @Override
    public Currency read(String symbol) {
        return Currency.getInstance(symbol);
    }

    @Override
    public String write(Currency currency) {
        return currency.toString();
    }
}

