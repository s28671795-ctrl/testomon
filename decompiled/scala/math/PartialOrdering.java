/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import scala.Option;
import scala.math.Equiv;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U4qAD\b\u0011\u0002\u0007\u0005A\u0003C\u0003*\u0001\u0011\u0005!\u0006C\u0003/\u0001\u0019\u0005q\u0006C\u0003;\u0001\u0019\u00051\bC\u0003B\u0001\u0011\u0005!\tC\u0003F\u0001\u0011\u0005a\tC\u0003J\u0001\u0011\u0005!\nC\u0003N\u0001\u0011\u0005a\nC\u0003R\u0001\u0011\u0005!kB\u0003U\u001f!\u0005QKB\u0003\u000f\u001f!\u0005a\u000bC\u0003`\u0015\u0011\u0005\u0001\rC\u0003b\u0015\u0011\u0005!\rC\u0004n\u0015\u0005\u0005I\u0011\u00028\u0003\u001fA\u000b'\u000f^5bY>\u0013H-\u001a:j]\u001eT!\u0001E\t\u0002\t5\fG\u000f\u001b\u0006\u0002%\u0005)1oY1mC\u000e\u0001QCA\u000b!'\r\u0001aC\u0007\t\u0003/ai\u0011!E\u0005\u00033E\u0011a!\u00118z%\u00164\u0007cA\u000e\u001d=5\tq\"\u0003\u0002\u001e\u001f\t)Q)];jmB\u0011q\u0004\t\u0007\u0001\t\u0015\t\u0003A1\u0001#\u0005\u0005!\u0016CA\u0012'!\t9B%\u0003\u0002&#\t9aj\u001c;iS:<\u0007CA\f(\u0013\tA\u0013CA\u0002B]f\fa\u0001J5oSR$C#A\u0016\u0011\u0005]a\u0013BA\u0017\u0012\u0005\u0011)f.\u001b;\u0002\u0015Q\u0014\u0018pQ8na\u0006\u0014X\rF\u00021ma\u00022aF\u00194\u0013\t\u0011\u0014C\u0001\u0004PaRLwN\u001c\t\u0003/QJ!!N\t\u0003\u0007%sG\u000fC\u00038\u0005\u0001\u0007a$A\u0001y\u0011\u0015I$\u00011\u0001\u001f\u0003\u0005I\u0018\u0001\u00027uKF$2\u0001P A!\t9R(\u0003\u0002?#\t9!i\\8mK\u0006t\u0007\"B\u001c\u0004\u0001\u0004q\u0002\"B\u001d\u0004\u0001\u0004q\u0012\u0001B4uKF$2\u0001P\"E\u0011\u00159D\u00011\u0001\u001f\u0011\u0015ID\u00011\u0001\u001f\u0003\taG\u000fF\u0002=\u000f\"CQaN\u0003A\u0002yAQ!O\u0003A\u0002y\t!a\u001a;\u0015\u0007qZE\nC\u00038\r\u0001\u0007a\u0004C\u0003:\r\u0001\u0007a$A\u0003fcVLg\u000fF\u0002=\u001fBCQaN\u0004A\u0002yAQ!O\u0004A\u0002y\tqA]3wKJ\u001cX-F\u0001T!\rY\u0002AH\u0001\u0010!\u0006\u0014H/[1m\u001fJ$WM]5oOB\u00111DC\n\u0004\u0015Y9\u0006C\u0001-^\u001b\u0005I&B\u0001.\\\u0003\tIwNC\u0001]\u0003\u0011Q\u0017M^1\n\u0005yK&\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\bF\u0001V\u0003\u0015\t\u0007\u000f\u001d7z+\t\u0019g\r\u0006\u0002eOB\u00191\u0004A3\u0011\u0005}1G!B\u0011\r\u0005\u0004\u0011\u0003\"\u00025\r\u0001\b!\u0017AA3wQ\ta!\u000e\u0005\u0002\u0018W&\u0011A.\u0005\u0002\u0007S:d\u0017N\\3\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003=\u0004\"\u0001]:\u000e\u0003ET!A].\u0002\t1\fgnZ\u0005\u0003iF\u0014aa\u00142kK\u000e$\b")
public interface PartialOrdering<T>
extends Equiv<T> {
    public static <T> PartialOrdering<T> apply(PartialOrdering<T> ev) {
        return ev;
    }

    public Option<Object> tryCompare(T var1, T var2);

    public boolean lteq(T var1, T var2);

    public static /* synthetic */ boolean gteq$(PartialOrdering $this, Object x, Object y) {
        return $this.gteq(x, y);
    }

    default public boolean gteq(T x, T y) {
        return this.lteq(y, x);
    }

    public static /* synthetic */ boolean lt$(PartialOrdering $this, Object x, Object y) {
        return $this.lt(x, y);
    }

    default public boolean lt(T x, T y) {
        return this.lteq(x, y) && !this.equiv(x, y);
    }

    public static /* synthetic */ boolean gt$(PartialOrdering $this, Object x, Object y) {
        return $this.gt(x, y);
    }

    default public boolean gt(T x, T y) {
        return this.gteq(x, y) && !this.equiv(x, y);
    }

    public static /* synthetic */ boolean equiv$(PartialOrdering $this, Object x, Object y) {
        return $this.equiv(x, y);
    }

    @Override
    default public boolean equiv(T x, T y) {
        return this.lteq(x, y) && this.lteq(y, x);
    }

    public static /* synthetic */ PartialOrdering reverse$(PartialOrdering $this) {
        return $this.reverse();
    }

    default public PartialOrdering<T> reverse() {
        return new PartialOrdering<T>(this){
            private final /* synthetic */ PartialOrdering $outer;

            public PartialOrdering<T> reverse() {
                return this.$outer;
            }

            public Option<Object> tryCompare(T x, T y) {
                return this.$outer.tryCompare(y, x);
            }

            public boolean lteq(T x, T y) {
                return this.$outer.lteq(y, x);
            }

            public boolean gteq(T x, T y) {
                return this.$outer.gteq(y, x);
            }

            public boolean lt(T x, T y) {
                return this.$outer.lt(y, x);
            }

            public boolean gt(T x, T y) {
                return this.$outer.gt(y, x);
            }

            public boolean equiv(T x, T y) {
                return this.$outer.equiv(y, x);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public static void $init$(PartialOrdering $this) {
    }
}

