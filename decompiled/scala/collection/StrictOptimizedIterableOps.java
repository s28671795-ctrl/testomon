/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;

@ScalaSignature(bytes="\u0006\u0005\teea\u0002\u000e\u001c!\u0003\r\t\u0001\t\u0005\u0006w\u0001!\t\u0001\u0010\u0005\u0006\u0001\u0002!\t%\u0011\u0005\u0006\u001b\u0002!\tE\u0014\u0005\u0006!\u0002!\t%\u0015\u0005\u0006A\u0002!\t%\u0019\u0005\u0006i\u0002!\t%\u001e\u0005\u0007}\u0002\u0001KQC@\t\u000f\u0005%\u0002\u0001\"\u0011\u0002,!A\u0011q\b\u0001!\n+\t\t\u0005\u0003\u0005\u0002Z\u0001\u0001KQCA.\u0011\u001d\t)\b\u0001C!\u0003oB\u0001\"a#\u0001A\u0013U\u0011Q\u0012\u0005\b\u0003G\u0003A\u0011IAS\u0011!\t9\f\u0001Q\u0005\u0016\u0005e\u0006bBAj\u0001\u0011\u0005\u0013Q\u001b\u0005\t\u0003s\u0004\u0001\u0015\"\u0006\u0002|\"9!1\u0003\u0001\u0005B\tU\u0001b\u0002B\u0011\u0001\u0011\u0005#1\u0005\u0005\b\u0005{\u0001A\u0011\tB \u0011\u001d\u0011)\u0005\u0001C!\u0005\u000fB\u0001Ba\u0013\u0001\t#Y\"Q\n\u0005\b\u0005+\u0002A\u0011\tB,\u0011\u001d\u0011Y\b\u0001C!\u0005{BqAa#\u0001\t\u0003\u0012i\tC\u0004\u0003\u0014\u0002!\tE!&\u00035M#(/[2u\u001fB$\u0018.\\5{K\u0012LE/\u001a:bE2,w\n]:\u000b\u0005qi\u0012AC2pY2,7\r^5p]*\ta$A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\t\u0005b3'O\n\u0004\u0001\t2\u0003CA\u0012%\u001b\u0005i\u0012BA\u0013\u001e\u0005\r\te.\u001f\t\u0006O!R#\u0007O\u0007\u00027%\u0011\u0011f\u0007\u0002\f\u0013R,'/\u00192mK>\u00038\u000f\u0005\u0002,Y1\u0001AAB\u0017\u0001\t\u000b\u0007aFA\u0001B#\ty#\u0005\u0005\u0002$a%\u0011\u0011'\b\u0002\b\u001d>$\b.\u001b8h!\tY3\u0007\u0002\u00045\u0001\u0011\u0015\r!\u000e\u0002\u0003\u0007\u000e+\"A\f\u001c\u0005\u000b]\u001a$\u0019\u0001\u0018\u0003\t}#C%\r\t\u0003We\"aA\u000f\u0001\u0005\u0006\u0004q#!A\"\u0002\r\u0011Jg.\u001b;%)\u0005i\u0004CA\u0012?\u0013\tyTD\u0001\u0003V]&$\u0018!\u00039beRLG/[8o)\t\u0011U\t\u0005\u0003$\u0007bB\u0014B\u0001#\u001e\u0005\u0019!V\u000f\u001d7fe!)aI\u0001a\u0001\u000f\u0006\t\u0001\u000f\u0005\u0003$\u0011*R\u0015BA%\u001e\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002$\u0017&\u0011A*\b\u0002\b\u0005>|G.Z1o\u0003\u0011\u0019\b/\u00198\u0015\u0005\t{\u0005\"\u0002$\u0004\u0001\u00049\u0015!B;ou&\u0004Xc\u0001*W5R\u00111\u000b\u0018\t\u0005G\r#\u0006\fE\u0002,gU\u0003\"a\u000b,\u0005\u000b]#!\u0019\u0001\u0018\u0003\u0005\u0005\u000b\u0004cA\u001643B\u00111F\u0017\u0003\u00067\u0012\u0011\rA\f\u0002\u0003\u0003JBQ!\u0018\u0003A\u0004y\u000ba!Y:QC&\u0014\b\u0003B\u0012IU}\u0003BaI\"V3\u00061QO\u001c>jaN*BA\u00195l]R\u00111\r\u001d\t\u0006G\u00114\u0017\u000e\\\u0005\u0003Kv\u0011a\u0001V;qY\u0016\u001c\u0004cA\u00164OB\u00111\u0006\u001b\u0003\u0006/\u0016\u0011\rA\f\t\u0004WMR\u0007CA\u0016l\t\u0015YVA1\u0001/!\rY3'\u001c\t\u0003W9$Qa\\\u0003C\u00029\u0012!!Q\u001a\t\u000bE,\u00019\u0001:\u0002\u0011\u0005\u001cHK]5qY\u0016\u0004Ba\t%+gB)1\u0005Z4k[\u0006\u0019Q.\u00199\u0016\u0005YLHCA<|!\rY3\u0007\u001f\t\u0003We$QA\u001f\u0004C\u00029\u0012\u0011A\u0011\u0005\u0006y\u001a\u0001\r!`\u0001\u0002MB!1\u0005\u0013\u0016y\u0003I\u0019HO]5di>\u0003H/[7ju\u0016$W*\u00199\u0016\r\u0005\u0005\u00111DA\u0003)\u0019\t\u0019!!\u0003\u0002\u001eA\u00191&!\u0002\u0005\r\u0005\u001dqA1\u0001/\u0005\t\u0019%\u0007C\u0004\u0002\f\u001d\u0001\r!!\u0004\u0002\u0003\t\u0004\u0002\"a\u0004\u0002\u0016\u0005e\u00111A\u0007\u0003\u0003#Q1!a\u0005\u001c\u0003\u001diW\u000f^1cY\u0016LA!a\u0006\u0002\u0012\t9!)^5mI\u0016\u0014\bcA\u0016\u0002\u001c\u0011)!p\u0002b\u0001]!1Ap\u0002a\u0001\u0003?\u0001Ra\t%+\u00033A3aBA\u0012!\r\u0019\u0013QE\u0005\u0004\u0003Oi\"AB5oY&tW-A\u0004gY\u0006$X*\u00199\u0016\t\u00055\u00121\u0007\u000b\u0005\u0003_\t)\u0004\u0005\u0003,g\u0005E\u0002cA\u0016\u00024\u0011)!\u0010\u0003b\u0001]!1A\u0010\u0003a\u0001\u0003o\u0001Ra\t%+\u0003s\u0001RaJA\u001e\u0003cI1!!\u0010\u001c\u00051IE/\u001a:bE2,wJ\\2f\u0003Y\u0019HO]5di>\u0003H/[7ju\u0016$g\t\\1u\u001b\u0006\u0004XCBA\"\u0003\u001f\n9\u0005\u0006\u0004\u0002F\u0005%\u0013\u0011\u000b\t\u0004W\u0005\u001dCABA\u0004\u0013\t\u0007a\u0006C\u0004\u0002\f%\u0001\r!a\u0013\u0011\u0011\u0005=\u0011QCA'\u0003\u000b\u00022aKA(\t\u0015Q\u0018B1\u0001/\u0011\u0019a\u0018\u00021\u0001\u0002TA)1\u0005\u0013\u0016\u0002VA)q%a\u000f\u0002N!\u001a\u0011\"a\t\u0002+M$(/[2u\u001fB$\u0018.\\5{K\u0012\u001cuN\\2biV1\u0011QLA6\u0003C\"b!a\u0018\u0002d\u0005=\u0004cA\u0016\u0002b\u00111\u0011q\u0001\u0006C\u00029Bq!!\u001a\u000b\u0001\u0004\t9'\u0001\u0003uQ\u0006$\b#B\u0014\u0002<\u0005%\u0004cA\u0016\u0002l\u00111!P\u0003b\u0001\u0003[\n\"A\u000b\u0012\t\u000f\u0005-!\u00021\u0001\u0002rAA\u0011qBA\u000b\u0003S\ny\u0006K\u0002\u000b\u0003G\tqaY8mY\u0016\u001cG/\u0006\u0003\u0002z\u0005}D\u0003BA>\u0003\u0003\u0003BaK\u001a\u0002~A\u00191&a \u0005\u000bi\\!\u0019\u0001\u0018\t\u000f\u0005\r5\u00021\u0001\u0002\u0006\u0006\u0011\u0001O\u001a\t\u0007G\u0005\u001d%&! \n\u0007\u0005%UDA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o\u0003Y\u0019HO]5di>\u0003H/[7ju\u0016$7i\u001c7mK\u000e$XCBAH\u00037\u000b\u0019\n\u0006\u0004\u0002\u0012\u0006U\u0015Q\u0014\t\u0004W\u0005MEABA\u0004\u0019\t\u0007a\u0006C\u0004\u0002\f1\u0001\r!a&\u0011\u0011\u0005=\u0011QCAM\u0003#\u00032aKAN\t\u0015QHB1\u0001/\u0011\u001d\t\u0019\t\u0004a\u0001\u0003?\u0003baIADU\u0005e\u0005f\u0001\u0007\u0002$\u00059a\r\\1ui\u0016tW\u0003BAT\u0003[#B!!+\u00020B!1fMAV!\rY\u0013Q\u0016\u0003\u0006u6\u0011\rA\f\u0005\b\u0003ck\u00019AAZ\u00039!x.\u0013;fe\u0006\u0014G.Z(oG\u0016\u0004Ra\t%+\u0003k\u0003RaJA\u001e\u0003W\u000bac\u001d;sS\u000e$x\n\u001d;j[&TX\r\u001a$mCR$XM\\\u000b\u0007\u0003w\u000bY-!1\u0015\t\u0005u\u0016Q\u001a\u000b\u0005\u0003\u007f\u000b\u0019\rE\u0002,\u0003\u0003$a!a\u0002\u000f\u0005\u0004q\u0003bBAY\u001d\u0001\u000f\u0011Q\u0019\t\u0006G!S\u0013q\u0019\t\u0006O\u0005m\u0012\u0011\u001a\t\u0004W\u0005-G!\u0002>\u000f\u0005\u0004q\u0003bBA\u0006\u001d\u0001\u0007\u0011q\u001a\t\t\u0003\u001f\t)\"!3\u0002@\"\u001aa\"a\t\u0002\u0007iL\u0007/\u0006\u0003\u0002X\u0006MH\u0003BAm\u0003k\u0004BaK\u001a\u0002\\B11eQAo\u0003cT3AKApW\t\t\t\u000f\u0005\u0003\u0002d\u00065XBAAs\u0015\u0011\t9/!;\u0002\u0013Ut7\r[3dW\u0016$'bAAv;\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005=\u0018Q\u001d\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007cA\u0016\u0002t\u0012)!p\u0004b\u0001]!9\u0011QM\bA\u0002\u0005]\b#B\u0014\u0002<\u0005E\u0018AE:ue&\u001cGo\u00149uS6L'0\u001a3[SB,b!!@\u0003\n\t\u0005ACBA\u0000\u0005\u0007\u0011Y\u0001E\u0002,\u0005\u0003!a!a\u0002\u0011\u0005\u0004q\u0003bBA3!\u0001\u0007!Q\u0001\t\u0006O\u0005m\"q\u0001\t\u0004W\t%A!\u0002>\u0011\u0005\u0004q\u0003bBA\u0006!\u0001\u0007!Q\u0002\t\t\u0003\u001f\t)Ba\u0004\u0002\u0000B)1e\u0011\u0016\u0003\b!\u001a\u0001#a\t\u0002\u0019iL\u0007oV5uQ&sG-\u001a=\u0016\u0005\t]\u0001\u0003B\u00164\u00053\u0001baI\"\u0002^\nm\u0001cA\u0012\u0003\u001e%\u0019!qD\u000f\u0003\u0007%sG/\u0001\u0005tG\u0006tG*\u001a4u+\u0011\u0011)C!\f\u0015\t\t\u001d\"\u0011\b\u000b\u0005\u0005S\u0011y\u0003\u0005\u0003,g\t-\u0002cA\u0016\u0003.\u0011)!P\u0005b\u0001]!9!\u0011\u0007\nA\u0002\tM\u0012AA8q!!\u0019#Q\u0007B\u0016U\t-\u0012b\u0001B\u001c;\tIa)\u001e8di&|gN\r\u0005\b\u0005w\u0011\u0002\u0019\u0001B\u0016\u0003\u0005Q\u0018A\u00024jYR,'\u000fF\u00029\u0005\u0003BaAa\u0011\u0014\u0001\u00049\u0015\u0001\u00029sK\u0012\f\u0011BZ5mi\u0016\u0014hj\u001c;\u0015\u0007a\u0012I\u0005\u0003\u0004\u0003DQ\u0001\raR\u0001\u000bM&dG/\u001a:J[BdG#\u0002\u001d\u0003P\tE\u0003B\u0002B\"+\u0001\u0007q\t\u0003\u0004\u0003TU\u0001\rAS\u0001\nSN4E.\u001b9qK\u0012\fA\u0002]1si&$\u0018n\u001c8NCB,bA!\u0017\u0003b\t\u001dD\u0003\u0002B.\u0005S\u0002baI\"\u0003^\t\r\u0004\u0003B\u00164\u0005?\u00022a\u000bB1\t\u00159fC1\u0001/!\u0011Y3G!\u001a\u0011\u0007-\u00129\u0007B\u0003\\-\t\u0007a\u0006\u0003\u0004}-\u0001\u0007!1\u000e\t\u0006G!S#Q\u000e\t\t\u0005_\u0012)Ha\u0018\u0003f9\u00191E!\u001d\n\u0007\tMT$A\u0004qC\u000e\\\u0017mZ3\n\t\t]$\u0011\u0010\u0002\u0007\u000b&$\b.\u001a:\u000b\u0007\tMT$A\u0004uCB,\u0015m\u00195\u0016\t\t}$q\u0011\u000b\u0004q\t\u0005\u0005B\u0002?\u0018\u0001\u0004\u0011\u0019\tE\u0003$\u0011*\u0012)\tE\u0002,\u0005\u000f#aA!#\u0018\u0005\u0004q#!A+\u0002\u0013Q\f7.\u001a*jO\"$Hc\u0001\u001d\u0003\u0010\"9!\u0011\u0013\rA\u0002\tm\u0011!\u00018\u0002\u0013\u0011\u0014x\u000e\u001d*jO\"$Hc\u0001\u001d\u0003\u0018\"9!\u0011S\rA\u0002\tm\u0001")
public interface StrictOptimizedIterableOps<A, CC, C>
extends IterableOps<A, CC, C> {
    public static /* synthetic */ Tuple2 partition$(StrictOptimizedIterableOps $this, Function1 p) {
        return $this.partition(p);
    }

    @Override
    default public Tuple2<C, C> partition(Function1<A, Object> p) {
        Builder l = this.newSpecificBuilder();
        Builder r = this.newSpecificBuilder();
        this.iterator().foreach((Function1<Object, Builder> & Serializable)x -> {
            Builder builder = BoxesRunTime.unboxToBoolean(p.apply(x)) ? l : r;
            if (builder == null) {
                throw null;
            }
            return (Builder)builder.addOne(x);
        });
        return new Tuple2(l.result(), r.result());
    }

    public static /* synthetic */ Tuple2 span$(StrictOptimizedIterableOps $this, Function1 p) {
        return $this.span(p);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public Tuple2<C, C> span(Function1<A, Object> p) {
        Builder first = this.newSpecificBuilder();
        Builder second = this.newSpecificBuilder();
        Iterator it = this.iterator();
        boolean inFirst = true;
        while (it.hasNext() && inFirst) {
            Object a = it.next();
            if (BoxesRunTime.unboxToBoolean(p.apply(a))) {
                if (first == null) {
                    throw null;
                }
                first.addOne(a);
                continue;
            }
            if (second == null) {
                throw null;
            }
            second.addOne(a);
            inFirst = false;
        }
        while (it.hasNext()) {
            void $plus$eq_elem;
            Object a = it.next();
            if (second == null) {
                throw null;
            }
            second.addOne($plus$eq_elem);
            a = null;
        }
        return new Tuple2(first.result(), second.result());
    }

    public static /* synthetic */ Tuple2 unzip$(StrictOptimizedIterableOps $this, Function1 asPair) {
        return $this.unzip(asPair);
    }

    @Override
    default public <A1, A2> Tuple2<CC, CC> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        Builder first = this.iterableFactory().newBuilder();
        Builder second = this.iterableFactory().newBuilder();
        this.foreach((Function1<Object, Builder> & Serializable)a -> {
            void $plus$eq_elem;
            void $plus$eq_elem2;
            Tuple2 pair = (Tuple2)asPair.apply(a);
            Object T1 = pair._1();
            if (first == null) {
                throw null;
            }
            first.addOne($plus$eq_elem2);
            T1 = null;
            Object T2 = pair._2();
            if (second == null) {
                throw null;
            }
            return (Builder)second.addOne($plus$eq_elem);
        });
        return new Tuple2(first.result(), second.result());
    }

    public static /* synthetic */ Tuple3 unzip3$(StrictOptimizedIterableOps $this, Function1 asTriple) {
        return $this.unzip3(asTriple);
    }

    @Override
    default public <A1, A2, A3> Tuple3<CC, CC, CC> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        Builder b1 = this.iterableFactory().newBuilder();
        Builder b2 = this.iterableFactory().newBuilder();
        Builder b3 = this.iterableFactory().newBuilder();
        this.foreach((Function1<Object, Builder> & Serializable)xyz -> {
            void $plus$eq_elem;
            void $plus$eq_elem2;
            void $plus$eq_elem3;
            Tuple3 triple = (Tuple3)asTriple.apply(xyz);
            Object T1 = triple._1();
            if (b1 == null) {
                throw null;
            }
            b1.addOne($plus$eq_elem3);
            T1 = null;
            Object T2 = triple._2();
            if (b2 == null) {
                throw null;
            }
            b2.addOne($plus$eq_elem2);
            T2 = null;
            Object T3 = triple._3();
            if (b3 == null) {
                throw null;
            }
            return (Builder)b3.addOne($plus$eq_elem);
        });
        return new Tuple3(b1.result(), b2.result(), b3.result());
    }

    public static /* synthetic */ Object map$(StrictOptimizedIterableOps $this, Function1 f) {
        return $this.map(f);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <B> CC map(Function1<A, B> f) {
        Builder strictOptimizedMap_b = this.iterableFactory().newBuilder();
        Iterator strictOptimizedMap_it = this.iterator();
        while (strictOptimizedMap_it.hasNext()) {
            void strictOptimizedMap_$plus$eq_elem;
            B b = f.apply(strictOptimizedMap_it.next());
            if (strictOptimizedMap_b == null) {
                throw null;
            }
            strictOptimizedMap_b.addOne(strictOptimizedMap_$plus$eq_elem);
            b = null;
        }
        return strictOptimizedMap_b.result();
    }

    public static /* synthetic */ Object strictOptimizedMap$(StrictOptimizedIterableOps $this, Builder b, Function1 f) {
        return $this.strictOptimizedMap(b, f);
    }

    /*
     * WARNING - void declaration
     */
    default public <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        Iterator it = this.iterator();
        while (it.hasNext()) {
            void $plus$eq_elem;
            B b2 = f.apply(it.next());
            if (b == null) {
                throw null;
            }
            b.addOne($plus$eq_elem);
            b2 = null;
        }
        return b.result();
    }

    public static /* synthetic */ Object flatMap$(StrictOptimizedIterableOps $this, Function1 f) {
        return $this.flatMap(f);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <B> CC flatMap(Function1<A, IterableOnce<B>> f) {
        Builder strictOptimizedFlatMap_b = this.iterableFactory().newBuilder();
        Iterator strictOptimizedFlatMap_it = this.iterator();
        while (strictOptimizedFlatMap_it.hasNext()) {
            void strictOptimizedFlatMap_$plus$plus$eq_xs;
            IterableOnce<B> iterableOnce = f.apply(strictOptimizedFlatMap_it.next());
            if (strictOptimizedFlatMap_b == null) {
                throw null;
            }
            strictOptimizedFlatMap_b.addAll(strictOptimizedFlatMap_$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return strictOptimizedFlatMap_b.result();
    }

    public static /* synthetic */ Object strictOptimizedFlatMap$(StrictOptimizedIterableOps $this, Builder b, Function1 f) {
        return $this.strictOptimizedFlatMap(b, f);
    }

    /*
     * WARNING - void declaration
     */
    default public <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
        Iterator it = this.iterator();
        while (it.hasNext()) {
            void $plus$plus$eq_xs;
            IterableOnce<B> iterableOnce = f.apply(it.next());
            if (b == null) {
                throw null;
            }
            b.addAll((IterableOnce<B>)$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return b.result();
    }

    public static /* synthetic */ Object strictOptimizedConcat$(StrictOptimizedIterableOps $this, IterableOnce that, Builder b) {
        return $this.strictOptimizedConcat(that, b);
    }

    default public <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        if (b == null) {
            throw null;
        }
        b.addAll(this);
        b.addAll(that);
        return b.result();
    }

    public static /* synthetic */ Object collect$(StrictOptimizedIterableOps $this, PartialFunction pf) {
        return $this.collect(pf);
    }

    @Override
    default public <B> CC collect(PartialFunction<A, B> pf) {
        Builder strictOptimizedCollect_b = this.iterableFactory().newBuilder();
        Object strictOptimizedCollect_marker = Statics.pfMarker;
        Iterator strictOptimizedCollect_it = this.iterator();
        while (strictOptimizedCollect_it.hasNext()) {
            Object strictOptimizedCollect_elem = strictOptimizedCollect_it.next();
            Object strictOptimizedCollect_v = pf.applyOrElse(strictOptimizedCollect_elem, (Function1<Object, Object> & Serializable)x -> strictOptimizedCollect_marker);
            if (strictOptimizedCollect_marker == strictOptimizedCollect_v) continue;
            if (strictOptimizedCollect_b == null) {
                throw null;
            }
            strictOptimizedCollect_b.addOne(strictOptimizedCollect_v);
        }
        return strictOptimizedCollect_b.result();
    }

    public static /* synthetic */ Object strictOptimizedCollect$(StrictOptimizedIterableOps $this, Builder b, PartialFunction pf) {
        return $this.strictOptimizedCollect(b, pf);
    }

    default public <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        Object marker = Statics.pfMarker;
        Iterator it = this.iterator();
        while (it.hasNext()) {
            Object elem = it.next();
            Object v = pf.applyOrElse(elem, (Function1<Object, Object> & Serializable)x -> strictOptimizedCollect_marker);
            if (marker == v) continue;
            if (b == null) {
                throw null;
            }
            b.addOne(v);
        }
        return b.result();
    }

    public static /* synthetic */ Object flatten$(StrictOptimizedIterableOps $this, Function1 toIterableOnce) {
        return $this.flatten(toIterableOnce);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <B> CC flatten(Function1<A, IterableOnce<B>> toIterableOnce) {
        Builder strictOptimizedFlatten_b = this.iterableFactory().newBuilder();
        Iterator strictOptimizedFlatten_it = this.iterator();
        while (strictOptimizedFlatten_it.hasNext()) {
            void strictOptimizedFlatten_$plus$plus$eq_xs;
            IterableOnce<B> iterableOnce = toIterableOnce.apply(strictOptimizedFlatten_it.next());
            if (strictOptimizedFlatten_b == null) {
                throw null;
            }
            strictOptimizedFlatten_b.addAll(strictOptimizedFlatten_$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return strictOptimizedFlatten_b.result();
    }

    public static /* synthetic */ Object strictOptimizedFlatten$(StrictOptimizedIterableOps $this, Builder b, Function1 toIterableOnce) {
        return $this.strictOptimizedFlatten(b, toIterableOnce);
    }

    /*
     * WARNING - void declaration
     */
    default public <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        Iterator it = this.iterator();
        while (it.hasNext()) {
            void $plus$plus$eq_xs;
            IterableOnce<B> iterableOnce = toIterableOnce.apply(it.next());
            if (b == null) {
                throw null;
            }
            b.addAll((IterableOnce<B>)$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return b.result();
    }

    public static /* synthetic */ Object zip$(StrictOptimizedIterableOps $this, IterableOnce that) {
        return $this.zip(that);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <B> CC zip(IterableOnce<B> that) {
        Builder strictOptimizedZip_b = this.iterableFactory().newBuilder();
        Iterator strictOptimizedZip_it1 = this.iterator();
        Iterator<B> strictOptimizedZip_it2 = that.iterator();
        while (strictOptimizedZip_it1.hasNext() && strictOptimizedZip_it2.hasNext()) {
            void strictOptimizedZip_$plus$eq_elem;
            Tuple2 tuple2 = new Tuple2(strictOptimizedZip_it1.next(), strictOptimizedZip_it2.next());
            if (strictOptimizedZip_b == null) {
                throw null;
            }
            strictOptimizedZip_b.addOne(strictOptimizedZip_$plus$eq_elem);
            tuple2 = null;
        }
        return strictOptimizedZip_b.result();
    }

    public static /* synthetic */ Object strictOptimizedZip$(StrictOptimizedIterableOps $this, IterableOnce that, Builder b) {
        return $this.strictOptimizedZip(that, b);
    }

    /*
     * WARNING - void declaration
     */
    default public <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
        Iterator it1 = this.iterator();
        Iterator<B> it2 = that.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            void $plus$eq_elem;
            Tuple2 tuple2 = new Tuple2(it1.next(), it2.next());
            if (b == null) {
                throw null;
            }
            b.addOne((Tuple2<void, B>)$plus$eq_elem);
            tuple2 = null;
        }
        return b.result();
    }

    public static /* synthetic */ Object zipWithIndex$(StrictOptimizedIterableOps $this) {
        return $this.zipWithIndex();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public CC zipWithIndex() {
        Builder b = this.iterableFactory().newBuilder();
        int i = 0;
        Iterator it = this.iterator();
        while (it.hasNext()) {
            void $plus$eq_elem;
            Tuple2 tuple2 = new Tuple2(it.next(), i);
            if (b == null) {
                throw null;
            }
            b.addOne($plus$eq_elem);
            tuple2 = null;
            ++i;
        }
        return b.result();
    }

    public static /* synthetic */ Object scanLeft$(StrictOptimizedIterableOps $this, Object z, Function2 op) {
        return $this.scanLeft(z, op);
    }

    @Override
    default public <B> CC scanLeft(B z, Function2<B, A, B> op) {
        Builder b = this.iterableFactory().newBuilder();
        b.sizeHint(this, 0);
        B acc = z;
        b.addOne(z);
        Iterator it = this.iterator();
        while (it.hasNext()) {
            acc = op.apply(acc, it.next());
            b.addOne(acc);
        }
        return b.result();
    }

    public static /* synthetic */ Object filter$(StrictOptimizedIterableOps $this, Function1 pred) {
        return $this.filter(pred);
    }

    @Override
    default public C filter(Function1<A, Object> pred) {
        return this.filterImpl(pred, false);
    }

    public static /* synthetic */ Object filterNot$(StrictOptimizedIterableOps $this, Function1 pred) {
        return $this.filterNot(pred);
    }

    @Override
    default public C filterNot(Function1<A, Object> pred) {
        return this.filterImpl(pred, true);
    }

    public static /* synthetic */ Object filterImpl$(StrictOptimizedIterableOps $this, Function1 pred, boolean isFlipped) {
        return $this.filterImpl(pred, isFlipped);
    }

    default public C filterImpl(Function1<A, Object> pred, boolean isFlipped) {
        Builder b = this.newSpecificBuilder();
        Iterator it = this.iterator();
        while (it.hasNext()) {
            Object elem = it.next();
            if (BoxesRunTime.unboxToBoolean(pred.apply(elem)) == isFlipped) continue;
            if (b == null) {
                throw null;
            }
            b.addOne(elem);
        }
        return b.result();
    }

    public static /* synthetic */ Tuple2 partitionMap$(StrictOptimizedIterableOps $this, Function1 f) {
        return $this.partitionMap(f);
    }

    @Override
    default public <A1, A2> Tuple2<CC, CC> partitionMap(Function1<A, Either<A1, A2>> f) {
        Builder l = this.iterableFactory().newBuilder();
        Builder r = this.iterableFactory().newBuilder();
        this.foreach((Function1<Object, Builder> & Serializable)x -> {
            Either either = (Either)f.apply(x);
            if (either instanceof Left) {
                Object x1 = ((Left)either).value();
                if (l == null) {
                    throw null;
                }
                return (Builder)l.addOne(x1);
            }
            if (either instanceof Right) {
                Object x2 = ((Right)either).value();
                if (r == null) {
                    throw null;
                }
                return (Builder)r.addOne(x2);
            }
            throw new MatchError(either);
        });
        return new Tuple2(l.result(), r.result());
    }

    public static /* synthetic */ Object tapEach$(StrictOptimizedIterableOps $this, Function1 f) {
        return $this.tapEach(f);
    }

    @Override
    default public <U> C tapEach(Function1<A, U> f) {
        this.foreach(f);
        return this.coll();
    }

    public static /* synthetic */ Object takeRight$(StrictOptimizedIterableOps $this, int n) {
        return $this.takeRight(n);
    }

    @Override
    default public C takeRight(int n) {
        Builder b = this.newSpecificBuilder();
        b.sizeHintBounded(n, this.toIterable());
        Iterator lead = this.iterator().drop(n);
        Iterator it = this.iterator();
        while (lead.hasNext()) {
            lead.next();
            it.next();
        }
        while (it.hasNext()) {
            Object $plus$eq_elem = it.next();
            b.addOne($plus$eq_elem);
            Object var5_5 = null;
        }
        return b.result();
    }

    public static /* synthetic */ Object dropRight$(StrictOptimizedIterableOps $this, int n) {
        return $this.dropRight(n);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public C dropRight(int n) {
        Builder b = this.newSpecificBuilder();
        if (n >= 0) {
            b.sizeHint(this, -n);
        }
        Iterator lead = this.iterator().drop(n);
        Iterator it = this.iterator();
        while (lead.hasNext()) {
            void $plus$eq_elem;
            Object a = it.next();
            if (b == null) {
                throw null;
            }
            b.addOne($plus$eq_elem);
            a = null;
            lead.next();
        }
        return b.result();
    }

    public static void $init$(StrictOptimizedIterableOps $this) {
    }
}

