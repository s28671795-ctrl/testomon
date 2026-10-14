/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.AnyStepper;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000552q\u0001B\u0003\u0011\u0002\u0007\u0005!\u0002C\u0003\u0010\u0001\u0011\u0005\u0001\u0003C\u0003\u0015\u0001\u0011\rQ\u0003C\u0004*\u0001\t\u0007I\u0011\u0003\u0016\u00031M#X\r\u001d9feNC\u0017\r]3M_^\u0004&/[8sSRL(G\u0003\u0002\u0007\u000f\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003!\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u0001\u0017A\u0011A\"D\u0007\u0002\u000f%\u0011ab\u0002\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005\t\u0002C\u0001\u0007\u0013\u0013\t\u0019rA\u0001\u0003V]&$\u0018\u0001\u00052bg\u0016\u001cF/\u001a9qKJ\u001c\u0006.\u00199f+\t1R$F\u0001\u0018!\u0011A\u0012d\u0007\u0014\u000e\u0003\u0015I!AG\u0003\u0003\u0019M#X\r\u001d9feNC\u0017\r]3\u0011\u0005qiB\u0002\u0001\u0003\u0006=\t\u0011\ra\b\u0002\u0002)F\u0011\u0001e\t\t\u0003\u0019\u0005J!AI\u0004\u0003\u000f9{G\u000f[5oOB\u0011A\u0002J\u0005\u0003K\u001d\u00111!\u00118z!\rAreG\u0005\u0003Q\u0015\u0011qa\u0015;faB,'/\u0001\rb]f\u001cF/\u001a9qKJ\u001c\u0006.\u00199f!J|Go\u001c;za\u0016,\u0012a\u000b\t\u00051eYA\u0006E\u0002\u0019O-\u0001")
public interface StepperShapeLowPriority2 {
    public void scala$collection$StepperShapeLowPriority2$_setter_$anyStepperShapePrototype_$eq(StepperShape<Object, Stepper<Object>> var1);

    public static /* synthetic */ StepperShape baseStepperShape$(StepperShapeLowPriority2 $this) {
        return $this.baseStepperShape();
    }

    default public <T> StepperShape<T, Stepper<T>> baseStepperShape() {
        return this.anyStepperShapePrototype();
    }

    public StepperShape<Object, Stepper<Object>> anyStepperShapePrototype();

    public static void $init$(StepperShapeLowPriority2 $this) {
        $this.scala$collection$StepperShapeLowPriority2$_setter_$anyStepperShapePrototype_$eq(new StepperShape<Object, Stepper<Object>>(null){

            public int shape() {
                return StepperShape$.MODULE$.ReferenceShape();
            }

            public Stepper<Object> seqUnbox(AnyStepper<Object> st) {
                return st;
            }

            public Stepper<Object> parUnbox(AnyStepper<Object> st) {
                return st;
            }
        });
    }
}

