/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.AnyStepper;
import scala.collection.DoubleStepper;
import scala.collection.IntStepper;
import scala.collection.LongStepper;
import scala.collection.Stepper;

public final class AnyStepper$ {
    public static final AnyStepper$ MODULE$ = new AnyStepper$();

    public AnyStepper<Object> ofSeqDoubleStepper(DoubleStepper st) {
        return new AnyStepper.BoxedDoubleStepper(st);
    }

    public AnyStepper<Object> ofParDoubleStepper(DoubleStepper st) {
        return new Stepper.EfficientSplit(st){};
    }

    public AnyStepper<Object> ofSeqIntStepper(IntStepper st) {
        return new AnyStepper.BoxedIntStepper(st);
    }

    public AnyStepper<Object> ofParIntStepper(IntStepper st) {
        return new Stepper.EfficientSplit(st){};
    }

    public AnyStepper<Object> ofSeqLongStepper(LongStepper st) {
        return new AnyStepper.BoxedLongStepper(st);
    }

    public AnyStepper<Object> ofParLongStepper(LongStepper st) {
        return new Stepper.EfficientSplit(st){};
    }

    private AnyStepper$() {
    }
}

