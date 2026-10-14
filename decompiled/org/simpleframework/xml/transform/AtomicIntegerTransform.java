/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.util.concurrent.atomic.AtomicInteger;
import org.simpleframework.xml.transform.Transform;

class AtomicIntegerTransform
implements Transform<AtomicInteger> {
    AtomicIntegerTransform() {
    }

    @Override
    public AtomicInteger read(String value) {
        Integer number = Integer.valueOf(value);
        return new AtomicInteger(number);
    }

    @Override
    public String write(AtomicInteger value) {
        return value.toString();
    }
}

