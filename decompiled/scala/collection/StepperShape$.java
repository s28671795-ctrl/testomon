/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.AnyStepper;
import scala.collection.DoubleStepper;
import scala.collection.IntStepper;
import scala.collection.LongStepper;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShapeLowPriority1;
import scala.collection.StepperShapeLowPriority2;

public final class StepperShape$
implements StepperShapeLowPriority1 {
    public static final StepperShape$ MODULE$ = new StepperShape$();
    private static final int ReferenceShape;
    private static final int IntShape;
    private static final int LongShape;
    private static final int DoubleShape;
    private static final int ByteShape;
    private static final int ShortShape;
    private static final int CharShape;
    private static final int FloatShape;
    private static final StepperShape<Object, IntStepper> intStepperShape;
    private static final StepperShape<Integer, IntStepper> jIntegerStepperShape;
    private static final StepperShape<Object, LongStepper> longStepperShape;
    private static final StepperShape<Long, LongStepper> jLongStepperShape;
    private static final StepperShape<Object, DoubleStepper> doubleStepperShape;
    private static final StepperShape<Double, DoubleStepper> jDoubleStepperShape;
    private static final StepperShape<Object, IntStepper> byteStepperShape;
    private static final StepperShape<Byte, IntStepper> jByteStepperShape;
    private static final StepperShape<Object, IntStepper> shortStepperShape;
    private static final StepperShape<Short, IntStepper> jShortStepperShape;
    private static final StepperShape<Object, IntStepper> charStepperShape;
    private static final StepperShape<Character, IntStepper> jCharacterStepperShape;
    private static final StepperShape<Object, DoubleStepper> floatStepperShape;
    private static final StepperShape<Float, DoubleStepper> jFloatStepperShape;
    private static StepperShape<Object, Stepper<Object>> anyStepperShapePrototype;

    static {
        StepperShapeLowPriority2.$init$(MODULE$);
        ReferenceShape = 0;
        IntShape = 1;
        LongShape = 2;
        DoubleShape = 3;
        ByteShape = 4;
        ShortShape = 5;
        CharShape = 6;
        FloatShape = 7;
        intStepperShape = new StepperShape<Object, IntStepper>(){

            public int shape() {
                return StepperShape$.MODULE$.IntShape();
            }

            public IntStepper seqUnbox(AnyStepper<Object> st) {
                return new Stepper.UnboxingIntStepper(st);
            }

            public IntStepper parUnbox(AnyStepper<Object> st) {
                return new Stepper.EfficientSplit(null, st){};
            }
        };
        jIntegerStepperShape = MODULE$.intStepperShape();
        longStepperShape = new StepperShape<Object, LongStepper>(){

            public int shape() {
                return StepperShape$.MODULE$.LongShape();
            }

            public LongStepper seqUnbox(AnyStepper<Object> st) {
                return new Stepper.UnboxingLongStepper(st);
            }

            public LongStepper parUnbox(AnyStepper<Object> st) {
                return new Stepper.EfficientSplit(null, st){};
            }
        };
        jLongStepperShape = MODULE$.longStepperShape();
        doubleStepperShape = new StepperShape<Object, DoubleStepper>(){

            public int shape() {
                return StepperShape$.MODULE$.DoubleShape();
            }

            public DoubleStepper seqUnbox(AnyStepper<Object> st) {
                return new Stepper.UnboxingDoubleStepper(st);
            }

            public DoubleStepper parUnbox(AnyStepper<Object> st) {
                return new Stepper.EfficientSplit(null, st){};
            }
        };
        jDoubleStepperShape = MODULE$.doubleStepperShape();
        byteStepperShape = new StepperShape<Object, IntStepper>(){

            public int shape() {
                return StepperShape$.MODULE$.ByteShape();
            }

            public IntStepper seqUnbox(AnyStepper<Object> st) {
                return new Stepper.UnboxingByteStepper(st);
            }

            public IntStepper parUnbox(AnyStepper<Object> st) {
                return new Stepper.EfficientSplit(null, st){};
            }
        };
        jByteStepperShape = MODULE$.byteStepperShape();
        shortStepperShape = new StepperShape<Object, IntStepper>(){

            public int shape() {
                return StepperShape$.MODULE$.ShortShape();
            }

            public IntStepper seqUnbox(AnyStepper<Object> st) {
                return new Stepper.UnboxingShortStepper(st);
            }

            public IntStepper parUnbox(AnyStepper<Object> st) {
                return new Stepper.EfficientSplit(null, st){};
            }
        };
        jShortStepperShape = MODULE$.shortStepperShape();
        charStepperShape = new StepperShape<Object, IntStepper>(){

            public int shape() {
                return StepperShape$.MODULE$.CharShape();
            }

            public IntStepper seqUnbox(AnyStepper<Object> st) {
                return new Stepper.UnboxingCharStepper(st);
            }

            public IntStepper parUnbox(AnyStepper<Object> st) {
                return new Stepper.EfficientSplit(null, st){};
            }
        };
        jCharacterStepperShape = MODULE$.charStepperShape();
        floatStepperShape = new StepperShape<Object, DoubleStepper>(){

            public int shape() {
                return StepperShape$.MODULE$.FloatShape();
            }

            public DoubleStepper seqUnbox(AnyStepper<Object> st) {
                return new Stepper.UnboxingFloatStepper(st);
            }

            public DoubleStepper parUnbox(AnyStepper<Object> st) {
                return new Stepper.EfficientSplit(null, st){};
            }
        };
        jFloatStepperShape = MODULE$.floatStepperShape();
    }

    @Override
    public <T> StepperShape<T, AnyStepper<T>> anyStepperShape() {
        return StepperShapeLowPriority1.anyStepperShape$(this);
    }

    @Override
    public <T> StepperShape<T, Stepper<T>> baseStepperShape() {
        return StepperShapeLowPriority2.baseStepperShape$(this);
    }

    @Override
    public StepperShape<Object, Stepper<Object>> anyStepperShapePrototype() {
        return anyStepperShapePrototype;
    }

    @Override
    public void scala$collection$StepperShapeLowPriority2$_setter_$anyStepperShapePrototype_$eq(StepperShape<Object, Stepper<Object>> x$1) {
        anyStepperShapePrototype = x$1;
    }

    public int ReferenceShape() {
        return ReferenceShape;
    }

    public int IntShape() {
        return IntShape;
    }

    public int LongShape() {
        return LongShape;
    }

    public int DoubleShape() {
        return DoubleShape;
    }

    public int ByteShape() {
        return ByteShape;
    }

    public int ShortShape() {
        return ShortShape;
    }

    public int CharShape() {
        return CharShape;
    }

    public int FloatShape() {
        return FloatShape;
    }

    public StepperShape<Object, IntStepper> intStepperShape() {
        return intStepperShape;
    }

    public StepperShape<Integer, IntStepper> jIntegerStepperShape() {
        return jIntegerStepperShape;
    }

    public StepperShape<Object, LongStepper> longStepperShape() {
        return longStepperShape;
    }

    public StepperShape<Long, LongStepper> jLongStepperShape() {
        return jLongStepperShape;
    }

    public StepperShape<Object, DoubleStepper> doubleStepperShape() {
        return doubleStepperShape;
    }

    public StepperShape<Double, DoubleStepper> jDoubleStepperShape() {
        return jDoubleStepperShape;
    }

    public StepperShape<Object, IntStepper> byteStepperShape() {
        return byteStepperShape;
    }

    public StepperShape<Byte, IntStepper> jByteStepperShape() {
        return jByteStepperShape;
    }

    public StepperShape<Object, IntStepper> shortStepperShape() {
        return shortStepperShape;
    }

    public StepperShape<Short, IntStepper> jShortStepperShape() {
        return jShortStepperShape;
    }

    public StepperShape<Object, IntStepper> charStepperShape() {
        return charStepperShape;
    }

    public StepperShape<Character, IntStepper> jCharacterStepperShape() {
        return jCharacterStepperShape;
    }

    public StepperShape<Object, DoubleStepper> floatStepperShape() {
        return floatStepperShape;
    }

    public StepperShape<Float, DoubleStepper> jFloatStepperShape() {
        return jFloatStepperShape;
    }

    private StepperShape$() {
    }
}

