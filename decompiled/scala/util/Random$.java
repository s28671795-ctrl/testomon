/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import scala.runtime.ModuleSerializationProxy;
import scala.util.Random;

public final class Random$
extends Random {
    public static final Random$ MODULE$ = new Random$();

    @Override
    public Random javaRandomToRandom(java.util.Random r) {
        return new Random(r);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Random$.class);
    }

    private Random$() {
    }
}

