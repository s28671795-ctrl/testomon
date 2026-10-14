/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.FrequencySketch;
import akka.util.FrequencySketch$Bits$;

@InternalApi
public final class FrequencySketch$ {
    public static final FrequencySketch$ MODULE$ = new FrequencySketch$();

    public <A> FrequencySketch<A> apply(int capacity, int widthMultiplier, double resetMultiplier, int depth, int counterBits, FrequencySketch.Hasher<A> hasher) {
        int width = widthMultiplier * FrequencySketch$Bits$.MODULE$.ceilingPowerOfTwo(capacity);
        int resetSize = (int)(resetMultiplier * (double)capacity);
        return new FrequencySketch<A>(depth, width, counterBits, resetSize, hasher);
    }

    public <A> int apply$default$2() {
        return 4;
    }

    public <A> double apply$default$3() {
        return 10.0;
    }

    public <A> int apply$default$4() {
        return 4;
    }

    public <A> int apply$default$5() {
        return 4;
    }

    private FrequencySketch$() {
    }
}

