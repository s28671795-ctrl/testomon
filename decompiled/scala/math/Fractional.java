/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import scala.math.Numeric;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}4qa\u0005\u000b\u0011\u0002\u0007\u0005\u0011\u0004C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0019\u0005\u0011H\u0002\u0003?\u0001\u0001y\u0004\u0002\u0003#\u0004\u0005\u0003\u0005\u000b\u0011B\u0014\t\u000b\u0015\u001bA\u0011\u0001$\t\u000b%\u001bA\u0011\u0001&\t\u000b5\u0003A1\t(\b\u000bA#\u0002\u0012A)\u0007\u000bM!\u0002\u0012\u0001*\t\u000b\u0015KA\u0011\u0001/\t\u000buKA\u0011\u00010\u0007\u000f%L\u0001\u0013aA\u0001U\")1\u0007\u0004C\u0001i!)1\u000e\u0004C\u0002Y\u001e)a/\u0003E\u0001o\u001a)\u00110\u0003E\u0001u\")Q\t\u0005C\u0001y\"9Q0CA\u0001\n\u0013q(A\u0003$sC\u000e$\u0018n\u001c8bY*\u0011QCF\u0001\u0005[\u0006$\bNC\u0001\u0018\u0003\u0015\u00198-\u00197b\u0007\u0001)\"AG\u0015\u0014\u0007\u0001Y2\u0005\u0005\u0002\u001dC5\tQD\u0003\u0002\u001f?\u0005!A.\u00198h\u0015\u0005\u0001\u0013\u0001\u00026bm\u0006L!AI\u000f\u0003\r=\u0013'.Z2u!\r!SeJ\u0007\u0002)%\u0011a\u0005\u0006\u0002\b\u001dVlWM]5d!\tA\u0013\u0006\u0004\u0001\u0005\u000b)\u0002!\u0019A\u0016\u0003\u0003Q\u000b\"\u0001\f\u0019\u0011\u00055rS\"\u0001\f\n\u0005=2\"a\u0002(pi\"Lgn\u001a\t\u0003[EJ!A\r\f\u0003\u0007\u0005s\u00170\u0001\u0004%S:LG\u000f\n\u000b\u0002kA\u0011QFN\u0005\u0003oY\u0011A!\u00168ji\u0006\u0019A-\u001b<\u0015\u0007\u001dRD\bC\u0003<\u0005\u0001\u0007q%A\u0001y\u0011\u0015i$\u00011\u0001(\u0003\u0005I(!\u0004$sC\u000e$\u0018n\u001c8bY>\u00038o\u0005\u0002\u0004\u0001B\u0011\u0011IQ\u0007\u0002\u0001%\u00111)\n\u0002\u000b\u001dVlWM]5d\u001fB\u001c\u0018a\u00017ig\u00061A(\u001b8jiz\"\"a\u0012%\u0011\u0005\u0005\u001b\u0001\"\u0002#\u0006\u0001\u00049\u0013\u0001\u0002\u0013eSZ$\"aJ&\t\u000b13\u0001\u0019A\u0014\u0002\u0007ID7/\u0001\u0007nW:+X.\u001a:jG>\u00038\u000f\u0006\u0002H\u001f\")Ai\u0002a\u0001O\u0005QaI]1di&|g.\u00197\u0011\u0005\u0011J1cA\u0005T-B\u0011Q\u0006V\u0005\u0003+Z\u0011a!\u00118z%\u00164\u0007CA,[\u001b\u0005A&BA- \u0003\tIw.\u0003\u0002\\1\na1+\u001a:jC2L'0\u00192mKR\t\u0011+A\u0003baBd\u00170\u0006\u0002`ER\u0011\u0001m\u0019\t\u0004I\u0001\t\u0007C\u0001\u0015c\t\u0015Q3B1\u0001,\u0011\u0015!7\u0002q\u0001a\u0003\u00111'/Y2)\u0005-1\u0007CA\u0017h\u0013\tAgC\u0001\u0004j]2Lg.\u001a\u0002\u000f\u000bb$(/Y%na2L7-\u001b;t'\ta1+\u0001\nj]\u001aL\u0007P\u0012:bGRLwN\\1m\u001fB\u001cXCA7s)\tqW\u000f\u0006\u0002pgB\u0011\u0001o\u0001\t\u0004I\u0001\t\bC\u0001\u0015s\t\u0015QcB1\u0001,\u0011\u0015!h\u0002q\u0001q\u0003\rqW/\u001c\u0005\u0006w9\u0001\r!]\u0001\n\u00136\u0004H.[2jiN\u0004\"\u0001\u001f\t\u000e\u0003%\u0011\u0011\"S7qY&\u001c\u0017\u000e^:\u0014\u0007A\u00196\u0010\u0005\u0002y\u0019Q\tq/\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001\u001c\u0001")
public interface Fractional<T>
extends Numeric<T> {
    public static <T> Fractional<T> apply(Fractional<T> frac) {
        return frac;
    }

    public T div(T var1, T var2);

    public static /* synthetic */ FractionalOps mkNumericOps$(Fractional $this, Object lhs) {
        return $this.mkNumericOps(lhs);
    }

    @Override
    default public FractionalOps mkNumericOps(T lhs) {
        return new FractionalOps(this, lhs);
    }

    public static void $init$(Fractional $this) {
    }

    public static interface ExtraImplicits {
        public static /* synthetic */ FractionalOps infixFractionalOps$(ExtraImplicits $this, Object x, Fractional num) {
            return $this.infixFractionalOps(x, num);
        }

        default public <T> FractionalOps infixFractionalOps(T x, Fractional<T> num) {
            return new FractionalOps(num, x);
        }

        public static void $init$(ExtraImplicits $this) {
        }
    }

    public class FractionalOps
    extends Numeric.NumericOps {
        private final T lhs;

        public T $div(T rhs) {
            return this.scala$math$Fractional$FractionalOps$$$outer().div(this.lhs, rhs);
        }

        public /* synthetic */ Fractional scala$math$Fractional$FractionalOps$$$outer() {
            return (Fractional)this.$outer;
        }

        public FractionalOps(Fractional $outer, T lhs) {
            this.lhs = lhs;
            super($outer, lhs);
        }
    }
}

