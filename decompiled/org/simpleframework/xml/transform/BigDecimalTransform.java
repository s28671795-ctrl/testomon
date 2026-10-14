/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.math.BigDecimal;
import org.simpleframework.xml.transform.Transform;

class BigDecimalTransform
implements Transform<BigDecimal> {
    BigDecimalTransform() {
    }

    @Override
    public BigDecimal read(String value) {
        return new BigDecimal(value);
    }

    @Override
    public String write(BigDecimal value) {
        return value.toString();
    }
}

