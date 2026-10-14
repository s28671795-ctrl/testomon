/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.types;

import java.io.Serializable;
import java.time.Duration;

public interface Interval
extends Serializable {
    public Interval neg();

    public Interval abs();

    public int getSign();

    public double doubleValue();

    public float floatValue();

    public long longValue();

    public int intValue();

    public byte byteValue();

    public short shortValue();

    public Duration toDuration();
}

