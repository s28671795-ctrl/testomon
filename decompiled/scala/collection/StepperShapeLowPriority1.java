/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.AnyStepper;
import scala.collection.StepperShape;
import scala.collection.StepperShapeLowPriority2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-2qa\u0001\u0003\u0011\u0002\u0007\u0005\u0011\u0002C\u0003\u0013\u0001\u0011\u00051\u0003C\u0003\u0018\u0001\u0011\r\u0001D\u0001\rTi\u0016\u0004\b/\u001a:TQ\u0006\u0004X\rT8x!JLwN]5usFR!!\u0002\u0004\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\b\u0003\u0015\u00198-\u00197b\u0007\u0001\u00192\u0001\u0001\u0006\u000f!\tYA\"D\u0001\u0007\u0013\tiaA\u0001\u0004B]f\u0014VM\u001a\t\u0003\u001fAi\u0011\u0001B\u0005\u0003#\u0011\u0011\u0001d\u0015;faB,'o\u00155ba\u0016dun\u001e)sS>\u0014\u0018\u000e^=3\u0003\u0019!\u0013N\\5uIQ\tA\u0003\u0005\u0002\f+%\u0011aC\u0002\u0002\u0005+:LG/A\bb]f\u001cF/\u001a9qKJ\u001c\u0006.\u00199f+\tIr$F\u0001\u001b!\u0011y1$\b\u0015\n\u0005q!!\u0001D*uKB\u0004XM]*iCB,\u0007C\u0001\u0010 \u0019\u0001!Q\u0001\t\u0002C\u0002\u0005\u0012\u0011\u0001V\t\u0003E\u0015\u0002\"aC\u0012\n\u0005\u00112!a\u0002(pi\"Lgn\u001a\t\u0003\u0017\u0019J!a\n\u0004\u0003\u0007\u0005s\u0017\u0010E\u0002\u0010SuI!A\u000b\u0003\u0003\u0015\u0005s\u0017p\u0015;faB,'\u000f")
public interface StepperShapeLowPriority1
extends StepperShapeLowPriority2 {
    public static /* synthetic */ StepperShape anyStepperShape$(StepperShapeLowPriority1 $this) {
        return $this.anyStepperShape();
    }

    default public <T> StepperShape<T, AnyStepper<T>> anyStepperShape() {
        return this.anyStepperShapePrototype();
    }

    public static void $init$(StepperShapeLowPriority1 $this) {
    }
}

