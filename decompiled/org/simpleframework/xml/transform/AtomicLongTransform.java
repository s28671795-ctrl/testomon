/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.util.concurrent.atomic.AtomicLong;
import org.simpleframework.xml.transform.Transform;

class AtomicLongTransform
implements Transform<AtomicLong> {
    AtomicLongTransform() {
    }

    @Override
    public AtomicLong read(String value) {
        Long number = Long.valueOf(value);
        return new AtomicLong(number);
    }

    @Override
    public String write(AtomicLong value) {
        return value.toString();
    }
}

