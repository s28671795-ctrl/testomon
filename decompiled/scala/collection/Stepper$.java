/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.util.NoSuchElementException;
import scala.runtime.Nothing$;

public final class Stepper$ {
    public static final Stepper$ MODULE$ = new Stepper$();

    public final Nothing$ throwNSEE() {
        throw new NoSuchElementException("Empty Stepper");
    }

    private Stepper$() {
    }
}

