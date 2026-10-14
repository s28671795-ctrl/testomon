/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product;
import scala.Some;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u<Qa\u0004\t\t\u0002M1Q!\u0006\t\t\u0002YAQAG\u0001\u0005\u0002mAQ\u0001H\u0001\u0005\u0002u1q!\u0006\t\u0011\u0002\u0007\u00051\u0005C\u0003,\t\u0011\u0005A\u0006C\u00031\t\u0011\u0005\u0013\u0007C\u00036\t\u0011\u0005c\u0007C\u0003F\t\u0019\u0005a\tC\u0003P\t\u0019\u0005\u0001\u000bC\u0003U\t\u0019\u0005Q\u000bC\u0003Z\t\u0019\u0005!\fC\u0003_\t\u0019\u0005q\fC\u0003d\t\u0019\u0005A\rC\u0003i\t\u0019\u0005\u0011.\u0001\u0005Qe>$Wo\u0019;8\u0015\u0005\t\u0012!B:dC2\f7\u0001\u0001\t\u0003)\u0005i\u0011\u0001\u0005\u0002\t!J|G-^2uoM\u0011\u0011a\u0006\t\u0003)aI!!\u0007\t\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t1#A\u0004v]\u0006\u0004\b\u000f\\=\u0016\u0011yq\u0007O\u001d;wqj$\"aH>\u0011\u0007Q\u0001#%\u0003\u0002\"!\t1q\n\u001d;j_:\u0004\u0012\u0002\u0006\u0003n_F\u001cXo^=\u0016\u0011\u0011J%k\u0016/bM.\u001c2\u0001B\u0013)!\t!b%\u0003\u0002(!\t\u0019\u0011I\\=\u0011\u0005QI\u0013B\u0001\u0016\u0011\u0005\u001d\u0001&o\u001c3vGR\fa\u0001J5oSR$C#A\u0017\u0011\u0005Qq\u0013BA\u0018\u0011\u0005\u0011)f.\u001b;\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003I\u0002\"\u0001F\u001a\n\u0005Q\u0002\"aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HCA\u00138\u0011\u0015At\u00011\u00013\u0003\u0005q\u0007fA\u0004;\tB\u0019AcO\u001f\n\u0005q\u0002\"A\u0002;ie><8\u000f\u0005\u0002?\u0003:\u0011AcP\u0005\u0003\u0001B\tq\u0001]1dW\u0006<W-\u0003\u0002C\u0007\nI\u0012J\u001c3fq>+Ho\u00144C_VtGm]#yG\u0016\u0004H/[8o\u0015\t\u0001\u0005cI\u0001>\u0003\ty\u0016'F\u0001H!\tA\u0015\n\u0004\u0001\u0005\r)#AQ1\u0001L\u0005\t!\u0016'\u0005\u0002MKA\u0011A#T\u0005\u0003\u001dB\u0011qAT8uQ&tw-\u0001\u0002`eU\t\u0011\u000b\u0005\u0002I%\u001211\u000b\u0002CC\u0002-\u0013!\u0001\u0016\u001a\u0002\u0005}\u001bT#\u0001,\u0011\u0005!;FA\u0002-\u0005\t\u000b\u00071J\u0001\u0002Ug\u0005\u0011q\fN\u000b\u00027B\u0011\u0001\n\u0018\u0003\u0007;\u0012!)\u0019A&\u0003\u0005Q#\u0014AA06+\u0005\u0001\u0007C\u0001%b\t\u0019\u0011G\u0001\"b\u0001\u0017\n\u0011A+N\u0001\u0003?Z*\u0012!\u001a\t\u0003\u0011\u001a$aa\u001a\u0003\u0005\u0006\u0004Y%A\u0001+7\u0003\tyv'F\u0001k!\tA5\u000e\u0002\u0004m\t\u0011\u0015\ra\u0013\u0002\u0003)^\u0002\"\u0001\u00138\u0005\u000b)\u001b!\u0019A&\u0011\u0005!\u0003H!B*\u0004\u0005\u0004Y\u0005C\u0001%s\t\u0015A6A1\u0001L!\tAE\u000fB\u0003^\u0007\t\u00071\n\u0005\u0002Im\u0012)!m\u0001b\u0001\u0017B\u0011\u0001\n\u001f\u0003\u0006O\u000e\u0011\ra\u0013\t\u0003\u0011j$Q\u0001\\\u0002C\u0002-CQ\u0001`\u0002A\u0002\t\n\u0011\u0001\u001f")
public interface Product7<T1, T2, T3, T4, T5, T6, T7>
extends Product {
    public static <T1, T2, T3, T4, T5, T6, T7> Option<Product7<T1, T2, T3, T4, T5, T6, T7>> unapply(Product7<T1, T2, T3, T4, T5, T6, T7> x) {
        return new Some<Product7<T1, T2, T3, T4, T5, T6, T7>>(x);
    }

    public static /* synthetic */ int productArity$(Product7 $this) {
        return $this.productArity();
    }

    @Override
    default public int productArity() {
        return 7;
    }

    public static /* synthetic */ Object productElement$(Product7 $this, int n) {
        return $this.productElement(n);
    }

    @Override
    default public Object productElement(int n) throws IndexOutOfBoundsException {
        switch (n) {
            case 0: {
                return this._1();
            }
            case 1: {
                return this._2();
            }
            case 2: {
                return this._3();
            }
            case 3: {
                return this._4();
            }
            case 4: {
                return this._5();
            }
            case 5: {
                return this._6();
            }
            case 6: {
                return this._7();
            }
        }
        throw new IndexOutOfBoundsException(new StringBuilder(32).append(n).append(" is out of bounds (min 0, max 6)").toString());
    }

    public T1 _1();

    public T2 _2();

    public T3 _3();

    public T4 _4();

    public T5 _5();

    public T6 _6();

    public T7 _7();

    public static void $init$(Product7 $this) {
    }
}

