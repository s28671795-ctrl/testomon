/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.compat.java8.converterImpl.Priority2AccumulatorConverters;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y2q!\u0002\u0004\u0011\u0002\u0007\u0005q\u0002C\u0003\u0019\u0001\u0011\u0005\u0011\u0004C\u0003\u001e\u0001\u0011\ra\u0004C\u0003+\u0001\u0011\r1\u0006C\u00035\u0001\u0011\rQG\u0001\u0010Qe&|'/\u001b;zc\u0005\u001b7-^7vY\u0006$xN]\"p]Z,'\u000f^3sg*\u0011q\u0001C\u0001\u000eG>tg/\u001a:uKJLU\u000e\u001d7\u000b\u0005%Q\u0011!\u00026bm\u0006D$BA\u0006\r\u0003\u0019\u0019w.\u001c9bi*\tQ\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001\u0001B\u0003\u0005\u0002\u0012%5\tA\"\u0003\u0002\u0014\u0019\t1\u0011I\\=SK\u001a\u0004\"!\u0006\f\u000e\u0003\u0019I!a\u0006\u0004\u0003=A\u0013\u0018n\u001c:jif\u0014\u0014iY2v[Vd\u0017\r^8s\u0007>tg/\u001a:uKJ\u001c\u0018A\u0002\u0013j]&$H\u0005F\u0001\u001b!\t\t2$\u0003\u0002\u001d\u0019\t!QK\\5u\u0003U\t7mY;nk2\fG/\u001a#pk\ndW-\u0011:sCf$\"a\b\u0012\u0011\u0005U\u0001\u0013BA\u0011\u0007\u0005U\t5mY;nk2\fG/\u001a#pk\ndW-\u0011:sCfDQa\t\u0002A\u0002\u0011\n!\"\u001e8eKJd\u00170\u001b8h!\r\tReJ\u0005\u0003M1\u0011Q!\u0011:sCf\u0004\"!\u0005\u0015\n\u0005%b!A\u0002#pk\ndW-\u0001\nbG\u000e,X.\u001e7bi\u0016Le\u000e^!se\u0006LHC\u0001\u00170!\t)R&\u0003\u0002/\r\t\u0011\u0012iY2v[Vd\u0017\r^3J]R\f%O]1z\u0011\u0015\u00193\u00011\u00011!\r\tR%\r\t\u0003#IJ!a\r\u0007\u0003\u0007%sG/A\nbG\u000e,X.\u001e7bi\u0016duN\\4BeJ\f\u0017\u0010\u0006\u00027sA\u0011QcN\u0005\u0003q\u0019\u00111#Q2dk6,H.\u0019;f\u0019>tw-\u0011:sCfDQa\t\u0003A\u0002i\u00022!E\u0013<!\t\tB(\u0003\u0002>\u0019\t!Aj\u001c8h\u0001")
public interface Priority1AccumulatorConverters
extends Priority2AccumulatorConverters {
    public static /* synthetic */ double[] accumulateDoubleArray$(Priority1AccumulatorConverters $this, double[] underlying) {
        return $this.accumulateDoubleArray(underlying);
    }

    default public double[] accumulateDoubleArray(double[] underlying) {
        return underlying;
    }

    public static /* synthetic */ int[] accumulateIntArray$(Priority1AccumulatorConverters $this, int[] underlying) {
        return $this.accumulateIntArray(underlying);
    }

    default public int[] accumulateIntArray(int[] underlying) {
        return underlying;
    }

    public static /* synthetic */ long[] accumulateLongArray$(Priority1AccumulatorConverters $this, long[] underlying) {
        return $this.accumulateLongArray(underlying);
    }

    default public long[] accumulateLongArray(long[] underlying) {
        return underlying;
    }

    public static void $init$(Priority1AccumulatorConverters $this) {
    }
}

