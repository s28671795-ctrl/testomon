/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product;
import scala.Some;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00055<Q!\u0004\b\t\u0002E1Qa\u0005\b\t\u0002QAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0002m1qa\u0005\b\u0011\u0002\u0007\u0005\u0011\u0005C\u0003*\t\u0011\u0005!\u0006C\u0003/\t\u0011\u0005s\u0006C\u00034\t\u0011\u0005C\u0007C\u0003D\t\u0019\u0005A\tC\u0003N\t\u0019\u0005a\nC\u0003S\t\u0019\u00051\u000bC\u0003X\t\u0019\u0005\u0001\fC\u0003]\t\u0019\u0005Q,\u0001\u0005Qe>$Wo\u0019;6\u0015\u0005y\u0011!B:dC2\f7\u0001\u0001\t\u0003%\u0005i\u0011A\u0004\u0002\t!J|G-^2ukM\u0011\u0011!\u0006\t\u0003%YI!a\u0006\b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t\u0011#A\u0004v]\u0006\u0004\b\u000f\\=\u0016\rq\u0011GM\u001a5k)\ti2\u000eE\u0002\u0013=\u0001J!a\b\b\u0003\r=\u0003H/[8o!\u001d\u0011B!Y2fO&,bAI$Q+j{6c\u0001\u0003$MA\u0011!\u0003J\u0005\u0003K9\u00111!\u00118z!\t\u0011r%\u0003\u0002)\u001d\t9\u0001K]8ek\u000e$\u0018A\u0002\u0013j]&$H\u0005F\u0001,!\t\u0011B&\u0003\u0002.\u001d\t!QK\\5u\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005\u0001\u0004C\u0001\n2\u0013\t\u0011dBA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002$k!)ag\u0002a\u0001a\u0005\ta\u000eK\u0002\bq\t\u00032AE\u001d<\u0013\tQdB\u0001\u0004uQJ|wo\u001d\t\u0003y}r!AE\u001f\n\u0005yr\u0011a\u00029bG.\fw-Z\u0005\u0003\u0001\u0006\u0013\u0011$\u00138eKb|U\u000f^(g\u0005>,h\u000eZ:Fq\u000e,\u0007\u000f^5p]*\u0011aHD\u0012\u0002w\u0005\u0011q,M\u000b\u0002\u000bB\u0011ai\u0012\u0007\u0001\t\u0019AE\u0001\"b\u0001\u0013\n\u0011A+M\t\u0003\u0015\u000e\u0002\"AE&\n\u00051s!a\u0002(pi\"LgnZ\u0001\u0003?J*\u0012a\u0014\t\u0003\rB#a!\u0015\u0003\u0005\u0006\u0004I%A\u0001+3\u0003\ty6'F\u0001U!\t1U\u000b\u0002\u0004W\t\u0011\u0015\r!\u0013\u0002\u0003)N\n!a\u0018\u001b\u0016\u0003e\u0003\"A\u0012.\u0005\rm#AQ1\u0001J\u0005\t!F'\u0001\u0002`kU\ta\f\u0005\u0002G?\u00121\u0001\r\u0002CC\u0002%\u0013!\u0001V\u001b\u0011\u0005\u0019\u0013G!\u0002%\u0004\u0005\u0004I\u0005C\u0001$e\t\u0015\t6A1\u0001J!\t1e\rB\u0003W\u0007\t\u0007\u0011\n\u0005\u0002GQ\u0012)1l\u0001b\u0001\u0013B\u0011aI\u001b\u0003\u0006A\u000e\u0011\r!\u0013\u0005\u0006Y\u000e\u0001\r\u0001I\u0001\u0002q\u0002")
public interface Product5<T1, T2, T3, T4, T5>
extends Product {
    public static <T1, T2, T3, T4, T5> Option<Product5<T1, T2, T3, T4, T5>> unapply(Product5<T1, T2, T3, T4, T5> x) {
        return new Some<Product5<T1, T2, T3, T4, T5>>(x);
    }

    public static /* synthetic */ int productArity$(Product5 $this) {
        return $this.productArity();
    }

    @Override
    default public int productArity() {
        return 5;
    }

    public static /* synthetic */ Object productElement$(Product5 $this, int n) {
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
        }
        throw new IndexOutOfBoundsException(new StringBuilder(32).append(n).append(" is out of bounds (min 0, max 4)").toString());
    }

    public T1 _1();

    public T2 _2();

    public T3 _3();

    public T4 _4();

    public T5 _5();

    public static void $init$(Product5 $this) {
    }
}

