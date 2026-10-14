/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.collection.IterableOnce;
import scala.compat.java8.converterImpl.CollectionCanAccumulate$;
import scala.jdk.AnyAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!Ia\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006o\u0001!\t\u0001\u000f\u0005\u0006{\u0001!\tA\u0010\u0005\b\u001b\u0002\t\t\u0011\"\u0011O\u0011\u001d\u0011\u0006!!A\u0005BM;q!\u0017\b\u0002\u0002#\u0005!LB\u0004\u000e\u001d\u0005\u0005\t\u0012A.\t\u000b]BA\u0011A0\t\u000b\u0001DAQA1\t\u000f%D\u0011\u0011!C\u0003U\"9\u0001\u000fCA\u0001\n\u000b\t(aF\"pY2,7\r^5p]\u000e\u000bg.Q2dk6,H.\u0019;f\u0015\ty\u0001#A\u0007d_:4XM\u001d;fe&k\u0007\u000f\u001c\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\u000b\u000315\u001a\"\u0001A\r\u0011\u0005iYR\"\u0001\u000b\n\u0005q!\"AB!osZ\u000bG.\u0001#tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDeY8om\u0016\u0014H/\u001a:J[BdGeQ8mY\u0016\u001cG/[8o\u0007\u0006t\u0017iY2v[Vd\u0017\r^3%IUtG-\u001a:ms&tw-F\u0001 !\r\u0001\u0003f\u000b\b\u0003C\u0019r!AI\u0013\u000e\u0003\rR!\u0001\n\f\u0002\rq\u0012xn\u001c;?\u0013\u0005)\u0012BA\u0014\u0015\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u000b\u0016\u0003\u0019%#XM]1cY\u0016|enY3\u000b\u0005\u001d\"\u0002C\u0001\u0017.\u0019\u0001!QA\f\u0001C\u0002=\u0012\u0011!Q\t\u0003aM\u0002\"AG\u0019\n\u0005I\"\"a\u0002(pi\"Lgn\u001a\t\u00035QJ!!\u000e\u000b\u0003\u0007\u0005s\u00170A#tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDeY8om\u0016\u0014H/\u001a:J[BdGeQ8mY\u0016\u001cG/[8o\u0007\u0006t\u0017iY2v[Vd\u0017\r^3%IUtG-\u001a:ms&tw\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005eZ\u0004c\u0001\u001e\u0001W5\ta\u0002C\u0003=\u0007\u0001\u0007q$\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\f!\"Y2dk6,H.\u0019;f+\u0005y\u0004c\u0001!KW9\u0011\u0011\t\u0013\b\u0003\u0005\u001as!aQ#\u000f\u0005\u0005\"\u0015BA\n\u0015\u0013\t\t\"#\u0003\u0002H!\u0005q1m\u001c7mK\u000e$\u0018n\u001c8J[Bd\u0017BA\u0014J\u0015\t9\u0005#\u0003\u0002L\u0019\nY\u0011iY2v[Vd\u0017\r^8s\u0015\t9\u0013*\u0001\u0005iCND7i\u001c3f)\u0005y\u0005C\u0001\u000eQ\u0013\t\tFCA\u0002J]R\fa!Z9vC2\u001cHC\u0001+X!\tQR+\u0003\u0002W)\t9!i\\8mK\u0006t\u0007b\u0002-\u0007\u0003\u0003\u0005\raM\u0001\u0004q\u0012\n\u0014aF\"pY2,7\r^5p]\u000e\u000bg.Q2dk6,H.\u0019;f!\tQ\u0004b\u0005\u0002\t9B\u0011!$X\u0005\u0003=R\u0011a!\u00118z%\u00164G#\u0001.\u0002)\u0005\u001c7-^7vY\u0006$X\rJ3yi\u0016t7/[8o+\t\u0011W\r\u0006\u0002dMB\u0019\u0001I\u00133\u0011\u00051*G!\u0002\u0018\u000b\u0005\u0004y\u0003\"B4\u000b\u0001\u0004A\u0017!\u0002\u0013uQ&\u001c\bc\u0001\u001e\u0001I\u0006\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o+\tYw\u000e\u0006\u0002OY\")qm\u0003a\u0001[B\u0019!\b\u00018\u0011\u00051zG!\u0002\u0018\f\u0005\u0004y\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\t\u0011\b\u0010\u0006\u0002tkR\u0011A\u000b\u001e\u0005\b12\t\t\u00111\u00014\u0011\u00159G\u00021\u0001w!\rQ\u0004a\u001e\t\u0003Ya$QA\f\u0007C\u0002=\u0002")
public final class CollectionCanAccumulate<A> {
    private final IterableOnce<A> scala$compat$java8$converterImpl$CollectionCanAccumulate$$underlying;

    public static <A> boolean equals$extension(IterableOnce<A> iterableOnce, Object object) {
        return CollectionCanAccumulate$.MODULE$.equals$extension(iterableOnce, object);
    }

    public static <A> int hashCode$extension(IterableOnce<A> iterableOnce) {
        return CollectionCanAccumulate$.MODULE$.hashCode$extension(iterableOnce);
    }

    public static <A> AnyAccumulator<A> accumulate$extension(IterableOnce<A> iterableOnce) {
        return CollectionCanAccumulate$.MODULE$.accumulate$extension(iterableOnce);
    }

    public IterableOnce<A> scala$compat$java8$converterImpl$CollectionCanAccumulate$$underlying() {
        return this.scala$compat$java8$converterImpl$CollectionCanAccumulate$$underlying;
    }

    public AnyAccumulator<A> accumulate() {
        return CollectionCanAccumulate$.MODULE$.accumulate$extension(this.scala$compat$java8$converterImpl$CollectionCanAccumulate$$underlying());
    }

    public int hashCode() {
        return CollectionCanAccumulate$.MODULE$.hashCode$extension(this.scala$compat$java8$converterImpl$CollectionCanAccumulate$$underlying());
    }

    public boolean equals(Object x$1) {
        return CollectionCanAccumulate$.MODULE$.equals$extension(this.scala$compat$java8$converterImpl$CollectionCanAccumulate$$underlying(), x$1);
    }

    public CollectionCanAccumulate(IterableOnce<A> underlying) {
        this.scala$compat$java8$converterImpl$CollectionCanAccumulate$$underlying = underlying;
    }
}

