/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product;
import scala.Some;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u;Qa\u0003\u0007\t\u0002=1Q!\u0005\u0007\t\u0002IAQAF\u0001\u0005\u0002]AQ\u0001G\u0001\u0005\u0002e1q!\u0005\u0007\u0011\u0002\u0007\u0005q\u0004C\u0003(\t\u0011\u0005\u0001\u0006C\u0003-\t\u0011\u0005S\u0006C\u00032\t\u0011\u0005#\u0007C\u0003B\t\u0019\u0005!\tC\u0003L\t\u0019\u0005A\nC\u0003Q\t\u0019\u0005\u0011+\u0001\u0005Qe>$Wo\u0019;4\u0015\u0005i\u0011!B:dC2\f7\u0001\u0001\t\u0003!\u0005i\u0011\u0001\u0004\u0002\t!J|G-^2ugM\u0011\u0011a\u0005\t\u0003!QI!!\u0006\u0007\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tq\"A\u0004v]\u0006\u0004\b\u000f\\=\u0016\ti1\u0006L\u0017\u000b\u00037m\u00032\u0001\u0005\u000f\u001f\u0013\tiBB\u0001\u0004PaRLwN\u001c\t\u0006!\u0011)v+W\u000b\u0005A\u0015s5kE\u0002\u0005C\u0011\u0002\"\u0001\u0005\u0012\n\u0005\rb!aA!osB\u0011\u0001#J\u0005\u0003M1\u0011q\u0001\u0015:pIV\u001cG/\u0001\u0004%S:LG\u000f\n\u000b\u0002SA\u0011\u0001CK\u0005\u0003W1\u0011A!\u00168ji\u0006a\u0001O]8ek\u000e$\u0018I]5usV\ta\u0006\u0005\u0002\u0011_%\u0011\u0001\u0007\u0004\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003CMBQ\u0001N\u0004A\u00029\n\u0011A\u001c\u0015\u0004\u000fY\u0002\u0005c\u0001\t8s%\u0011\u0001\b\u0004\u0002\u0007i\"\u0014xn^:\u0011\u0005ijdB\u0001\t<\u0013\taD\"A\u0004qC\u000e\\\u0017mZ3\n\u0005yz$!G%oI\u0016Dx*\u001e;PM\n{WO\u001c3t\u000bb\u001cW\r\u001d;j_:T!\u0001\u0010\u0007$\u0003e\n!aX\u0019\u0016\u0003\r\u0003\"\u0001R#\r\u0001\u00111a\t\u0002CC\u0002\u001d\u0013!\u0001V\u0019\u0012\u0005!\u000b\u0003C\u0001\tJ\u0013\tQEBA\u0004O_RD\u0017N\\4\u0002\u0005}\u0013T#A'\u0011\u0005\u0011sEAB(\u0005\t\u000b\u0007qI\u0001\u0002Ue\u0005\u0011qlM\u000b\u0002%B\u0011Ai\u0015\u0003\u0007)\u0012!)\u0019A$\u0003\u0005Q\u001b\u0004C\u0001#W\t\u001515A1\u0001H!\t!\u0005\fB\u0003P\u0007\t\u0007q\t\u0005\u0002E5\u0012)Ak\u0001b\u0001\u000f\")Al\u0001a\u0001=\u0005\t\u0001\u0010")
public interface Product3<T1, T2, T3>
extends Product {
    public static <T1, T2, T3> Option<Product3<T1, T2, T3>> unapply(Product3<T1, T2, T3> x) {
        return new Some<Product3<T1, T2, T3>>(x);
    }

    public static /* synthetic */ int productArity$(Product3 $this) {
        return $this.productArity();
    }

    @Override
    default public int productArity() {
        return 3;
    }

    public static /* synthetic */ Object productElement$(Product3 $this, int n) {
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
        }
        throw new IndexOutOfBoundsException(new StringBuilder(32).append(n).append(" is out of bounds (min 0, max 2)").toString());
    }

    public T1 _1();

    public T2 _2();

    public T3 _3();

    public static void $init$(Product3 $this) {
    }
}

