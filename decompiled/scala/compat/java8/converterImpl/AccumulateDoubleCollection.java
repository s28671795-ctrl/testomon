/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.collection.IterableOnce;
import scala.compat.java8.converterImpl.AccumulateDoubleCollection$;
import scala.jdk.DoubleAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00194A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IQ\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006]\u0001!\ta\f\u0005\u0006i\u0001!\t!\u000e\u0005\b\t\u0002\t\t\u0011\"\u0011F\u0011\u001dI\u0005!!A\u0005B);qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000b9BA\u0011A-\t\u000biCAQA.\t\u000fyC\u0011\u0011!C\u0003?\"9\u0011\rCA\u0001\n\u000b\u0011'AG!dGVlW\u000f\\1uK\u0012{WO\u00197f\u0007>dG.Z2uS>t'BA\b\u0011\u00035\u0019wN\u001c<feR,'/S7qY*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0011\u0001\u0001\u0007\t\u00033ii\u0011\u0001F\u0005\u00037Q\u0011a!\u00118z-\u0006d\u0017aR:dC2\fGeY8na\u0006$HE[1wCb\"3m\u001c8wKJ$XM]%na2$\u0013iY2v[Vd\u0017\r^3E_V\u0014G.Z\"pY2,7\r^5p]\u0012\"SO\u001c3fe2L\u0018N\\4\u0016\u0003y\u00012aH\u0014+\u001d\t\u0001SE\u0004\u0002\"I5\t!E\u0003\u0002$-\u00051AH]8pizJ\u0011!F\u0005\u0003MQ\tq\u0001]1dW\u0006<W-\u0003\u0002)S\ta\u0011\n^3sC\ndWm\u00148dK*\u0011a\u0005\u0006\t\u00033-J!\u0001\f\u000b\u0003\r\u0011{WO\u00197f\u0003!\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%G>tg/\u001a:uKJLU\u000e\u001d7%\u0003\u000e\u001cW/\\;mCR,Gi\\;cY\u0016\u001cu\u000e\u001c7fGRLwN\u001c\u0013%k:$WM\u001d7zS:<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003aI\u0002\"!\r\u0001\u000e\u00039AQaM\u0002A\u0002y\t!\"\u001e8eKJd\u00170\u001b8h\u0003)\t7mY;nk2\fG/Z\u000b\u0002mA\u0011q'\u0011\b\u0003q}r!!O\u001f\u000f\u0005ibdB\u0001\u0011<\u0013\t\u0019B#\u0003\u0002\u0012%%\u0011a\bE\u0001\u000fG>dG.Z2uS>t\u0017*\u001c9m\u0013\t1\u0003I\u0003\u0002?!%\u0011!i\u0011\u0002\u0012\t>,(\r\\3BG\u000e,X.\u001e7bi>\u0014(B\u0001\u0014A\u0003!A\u0017m\u001d5D_\u0012,G#\u0001$\u0011\u0005e9\u0015B\u0001%\u0015\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u0005-s\u0005CA\rM\u0013\tiECA\u0004C_>dW-\u00198\t\u000f=3\u0011\u0011!a\u0001!\u0006\u0019\u0001\u0010J\u0019\u0011\u0005e\t\u0016B\u0001*\u0015\u0005\r\te._\u0001\u001b\u0003\u000e\u001cW/\\;mCR,Gi\\;cY\u0016\u001cu\u000e\u001c7fGRLwN\u001c\t\u0003c!\u0019\"\u0001\u0003,\u0011\u0005e9\u0016B\u0001-\u0015\u0005\u0019\te.\u001f*fMR\tA+\u0001\u000bbG\u000e,X.\u001e7bi\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0003mqCQ!\u0018\u0006A\u0002A\nQ\u0001\n;iSN\f!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]R\u0011Q\t\u0019\u0005\u0006;.\u0001\r\u0001M\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:$\"aY3\u0015\u0005-#\u0007bB(\r\u0003\u0003\u0005\r\u0001\u0015\u0005\u0006;2\u0001\r\u0001\r")
public final class AccumulateDoubleCollection {
    private final IterableOnce<Object> scala$compat$java8$converterImpl$AccumulateDoubleCollection$$underlying;

    public static boolean equals$extension(IterableOnce iterableOnce, Object object) {
        return AccumulateDoubleCollection$.MODULE$.equals$extension(iterableOnce, object);
    }

    public static int hashCode$extension(IterableOnce iterableOnce) {
        return AccumulateDoubleCollection$.MODULE$.hashCode$extension(iterableOnce);
    }

    public static DoubleAccumulator accumulate$extension(IterableOnce iterableOnce) {
        return AccumulateDoubleCollection$.MODULE$.accumulate$extension(iterableOnce);
    }

    public IterableOnce<Object> scala$compat$java8$converterImpl$AccumulateDoubleCollection$$underlying() {
        return this.scala$compat$java8$converterImpl$AccumulateDoubleCollection$$underlying;
    }

    public DoubleAccumulator accumulate() {
        return AccumulateDoubleCollection$.MODULE$.accumulate$extension(this.scala$compat$java8$converterImpl$AccumulateDoubleCollection$$underlying());
    }

    public int hashCode() {
        return AccumulateDoubleCollection$.MODULE$.hashCode$extension(this.scala$compat$java8$converterImpl$AccumulateDoubleCollection$$underlying());
    }

    public boolean equals(Object x$1) {
        return AccumulateDoubleCollection$.MODULE$.equals$extension(this.scala$compat$java8$converterImpl$AccumulateDoubleCollection$$underlying(), x$1);
    }

    public AccumulateDoubleCollection(IterableOnce<Object> underlying) {
        this.scala$compat$java8$converterImpl$AccumulateDoubleCollection$$underlying = underlying;
    }
}

