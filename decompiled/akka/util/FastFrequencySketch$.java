/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.FastFrequencySketch;
import akka.util.FrequencySketch$Bits$;

@InternalApi
public final class FastFrequencySketch$ {
    public static final FastFrequencySketch$ MODULE$ = new FastFrequencySketch$();

    public <A> FastFrequencySketch<A> apply(int capacity, int widthMultiplier, double resetMultiplier) {
        int width = widthMultiplier * FrequencySketch$Bits$.MODULE$.ceilingPowerOfTwo(capacity);
        int resetSize = (int)(resetMultiplier * (double)capacity);
        return new FastFrequencySketch(width, resetSize);
    }

    public <A> int apply$default$2() {
        return 4;
    }

    public <A> double apply$default$3() {
        return 10.0;
    }

    private FastFrequencySketch$() {
    }
}

