/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product;
import scala.Some;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U<QAD\b\t\u0002I1Q\u0001F\b\t\u0002UAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002q1q\u0001F\b\u0011\u0002\u0007\u0005!\u0005C\u0003+\t\u0011\u00051\u0006C\u00030\t\u0011\u0005\u0003\u0007C\u00035\t\u0011\u0005S\u0007C\u0003E\t\u0019\u0005Q\tC\u0003O\t\u0019\u0005q\nC\u0003T\t\u0019\u0005A\u000bC\u0003Y\t\u0019\u0005\u0011\fC\u0003^\t\u0019\u0005a\fC\u0003c\t\u0019\u00051-\u0001\u0005Qe>$Wo\u0019;7\u0015\u0005\u0001\u0012!B:dC2\f7\u0001\u0001\t\u0003'\u0005i\u0011a\u0004\u0002\t!J|G-^2umM\u0011\u0011A\u0006\t\u0003']I!\u0001G\b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!#A\u0004v]\u0006\u0004\b\u000f\\=\u0016\u000fuA'\u000e\u001c8qeR\u0011ad\u001d\t\u0004'}\t\u0013B\u0001\u0011\u0010\u0005\u0019y\u0005\u000f^5p]BA1\u0003B4jW6|\u0017/F\u0004$\u0011F36\fY3\u0014\u0007\u0011!s\u0005\u0005\u0002\u0014K%\u0011ae\u0004\u0002\u0004\u0003:L\bCA\n)\u0013\tIsBA\u0004Qe>$Wo\u0019;\u0002\r\u0011Jg.\u001b;%)\u0005a\u0003CA\n.\u0013\tqsB\u0001\u0003V]&$\u0018\u0001\u00049s_\u0012,8\r^!sSRLX#A\u0019\u0011\u0005M\u0011\u0014BA\u001a\u0010\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t!c\u0007C\u00038\u000f\u0001\u0007\u0011'A\u0001oQ\r9\u0011h\u0011\t\u0004'ib\u0014BA\u001e\u0010\u0005\u0019!\bN]8xgB\u0011Q\b\u0011\b\u0003'yJ!aP\b\u0002\u000fA\f7m[1hK&\u0011\u0011I\u0011\u0002\u001a\u0013:$W\r_(vi>3'i\\;oIN,\u0005pY3qi&|gN\u0003\u0002@\u001f\r\nA(\u0001\u0002`cU\ta\t\u0005\u0002H\u00112\u0001AAB%\u0005\t\u000b\u0007!J\u0001\u0002UcE\u00111\n\n\t\u0003'1K!!T\b\u0003\u000f9{G\u000f[5oO\u0006\u0011qLM\u000b\u0002!B\u0011q)\u0015\u0003\u0007%\u0012!)\u0019\u0001&\u0003\u0005Q\u0013\u0014AA04+\u0005)\u0006CA$W\t\u00199F\u0001\"b\u0001\u0015\n\u0011AkM\u0001\u0003?R*\u0012A\u0017\t\u0003\u000fn#a\u0001\u0018\u0003\u0005\u0006\u0004Q%A\u0001+5\u0003\tyV'F\u0001`!\t9\u0005\r\u0002\u0004b\t\u0011\u0015\rA\u0013\u0002\u0003)V\n!a\u0018\u001c\u0016\u0003\u0011\u0004\"aR3\u0005\r\u0019$AQ1\u0001K\u0005\t!f\u0007\u0005\u0002HQ\u0012)\u0011j\u0001b\u0001\u0015B\u0011qI\u001b\u0003\u0006%\u000e\u0011\rA\u0013\t\u0003\u000f2$QaV\u0002C\u0002)\u0003\"a\u00128\u0005\u000bq\u001b!\u0019\u0001&\u0011\u0005\u001d\u0003H!B1\u0004\u0005\u0004Q\u0005CA$s\t\u001517A1\u0001K\u0011\u0015!8\u00011\u0001\"\u0003\u0005A\b")
public interface Product6<T1, T2, T3, T4, T5, T6>
extends Product {
    public static <T1, T2, T3, T4, T5, T6> Option<Product6<T1, T2, T3, T4, T5, T6>> unapply(Product6<T1, T2, T3, T4, T5, T6> x) {
        return new Some<Product6<T1, T2, T3, T4, T5, T6>>(x);
    }

    public static /* synthetic */ int productArity$(Product6 $this) {
        return $this.productArity();
    }

    @Override
    default public int productArity() {
        return 6;
    }

    public static /* synthetic */ Object productElement$(Product6 $this, int n) {
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
        }
        throw new IndexOutOfBoundsException(new StringBuilder(32).append(n).append(" is out of bounds (min 0, max 5)").toString());
    }

    public T1 _1();

    public T2 _2();

    public T3 _3();

    public T4 _4();

    public T5 _5();

    public T6 _6();

    public static void $init$(Product6 $this) {
    }
}

