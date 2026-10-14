/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.math.BigInteger;
import org.simpleframework.xml.transform.Transform;

class BigIntegerTransform
implements Transform<BigInteger> {
    BigIntegerTransform() {
    }

    @Override
    public BigInteger read(String value) {
        return new BigInteger(value);
    }

    @Override
    public String write(BigInteger value) {
        return value.toString();
    }
}

