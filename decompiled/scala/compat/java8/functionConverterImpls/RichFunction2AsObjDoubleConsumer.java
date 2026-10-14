/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjDoubleConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsObjDoubleConsumer$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005]4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I1\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006i\u0001!\t!\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000bQBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f\u001dD\u0011\u0011!C\u0003Q\"9a\u000eCA\u0001\n\u000by'\u0001\t*jG\"4UO\\2uS>t''Q:PE*$u.\u001e2mK\u000e{gn];nKJT!a\u0004\t\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!!\u0005\n\u0002\u000b)\fg/\u0019\u001d\u000b\u0005M!\u0012AB2p[B\fGOC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0007\u0001)\"\u0001\u0007\u0013\u0014\u0005\u0001I\u0002C\u0001\u000e\u001c\u001b\u0005!\u0012B\u0001\u000f\u0015\u0005\u0019\te.\u001f,bY\u000616oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\rVt7\r^5p]J\n5o\u00142k\t>,(\r\\3D_:\u001cX/\\3sI\u0011*h\u000eZ3sYfLgnZ\u000b\u0002?A)!\u0004\t\u0012.a%\u0011\u0011\u0005\u0006\u0002\n\rVt7\r^5p]J\u0002\"a\t\u0013\r\u0001\u0011)Q\u0005\u0001b\u0001M\t\tA+\u0005\u0002(UA\u0011!\u0004K\u0005\u0003SQ\u0011qAT8uQ&tw\r\u0005\u0002\u001bW%\u0011A\u0006\u0006\u0002\u0004\u0003:L\bC\u0001\u000e/\u0013\tyCC\u0001\u0004E_V\u0014G.\u001a\t\u00035EJ!A\r\u000b\u0003\tUs\u0017\u000e^\u0001Xg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\u001c\u001aBg>\u0013'\u000eR8vE2,7i\u001c8tk6,'\u000f\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u00027qA\u0019q\u0007\u0001\u0012\u000e\u00039AQ!O\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u0019\t7OS1wCV\tA\bE\u0002>\t\nj\u0011A\u0010\u0006\u0003\u007f\u0001\u000b\u0001BZ;oGRLwN\u001c\u0006\u0003\u0003\n\u000bA!\u001e;jY*\t1)\u0001\u0003kCZ\f\u0017BA#?\u0005Ey%M\u001b#pk\ndWmQ8ogVlWM\u001d\u0015\u0003\t\u001d\u0003\"A\u0007%\n\u0005%#\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005a\u0005C\u0001\u000eN\u0013\tqECA\u0002J]R\fa!Z9vC2\u001cHCA)U!\tQ\"+\u0003\u0002T)\t9!i\\8mK\u0006t\u0007bB+\u0007\u0003\u0003\u0005\rAK\u0001\u0004q\u0012\n\u0014\u0001\t*jG\"4UO\\2uS>t''Q:PE*$u.\u001e2mK\u000e{gn];nKJ\u0004\"a\u000e\u0005\u0014\u0005!I\u0006C\u0001\u000e[\u0013\tYFC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002/\u0006\u0001\u0012m\u001d&bm\u0006$S\r\u001f;f]NLwN\\\u000b\u0003?\n$\"\u0001Y2\u0011\u0007u\"\u0015\r\u0005\u0002$E\u0012)QE\u0003b\u0001M!)AM\u0003a\u0001K\u0006)A\u0005\u001e5jgB\u0019q\u0007A1)\u0005)9\u0015A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:,\"![7\u0015\u0005-S\u0007\"\u00023\f\u0001\u0004Y\u0007cA\u001c\u0001YB\u00111%\u001c\u0003\u0006K-\u0011\rAJ\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,\"\u0001\u001d<\u0015\u0005E\u001cHCA)s\u0011\u001d)F\"!AA\u0002)BQ\u0001\u001a\u0007A\u0002Q\u00042a\u000e\u0001v!\t\u0019c\u000fB\u0003&\u0019\t\u0007a\u0005")
public final class RichFunction2AsObjDoubleConsumer<T> {
    private final Function2<T, Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction2AsObjDoubleConsumer$$underlying;

    public static <T> boolean equals$extension(Function2<T, Object, BoxedUnit> function2, Object object) {
        return RichFunction2AsObjDoubleConsumer$.MODULE$.equals$extension(function2, object);
    }

    public static <T> int hashCode$extension(Function2<T, Object, BoxedUnit> function2) {
        return RichFunction2AsObjDoubleConsumer$.MODULE$.hashCode$extension(function2);
    }

    public static <T> ObjDoubleConsumer<T> asJava$extension(Function2<T, Object, BoxedUnit> function2) {
        return RichFunction2AsObjDoubleConsumer$.MODULE$.asJava$extension(function2);
    }

    public Function2<T, Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction2AsObjDoubleConsumer$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjDoubleConsumer$$underlying;
    }

    public ObjDoubleConsumer<T> asJava() {
        return RichFunction2AsObjDoubleConsumer$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjDoubleConsumer$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsObjDoubleConsumer$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjDoubleConsumer$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsObjDoubleConsumer$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjDoubleConsumer$$underlying(), x$1);
    }

    public RichFunction2AsObjDoubleConsumer(Function2<T, Object, BoxedUnit> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjDoubleConsumer$$underlying = underlying;
    }
}

