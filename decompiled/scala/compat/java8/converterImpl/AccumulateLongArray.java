/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.compat.java8.converterImpl.AccumulateLongArray$;
import scala.jdk.LongAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r4A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IA\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006K\u0001!\tA\n\u0005\u0006W\u0001!\t\u0001\f\u0005\b\u0003\u0002\t\t\u0011\"\u0011C\u0011\u001d1\u0005!!A\u0005B\u001d;q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000b\u0015BA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000fmC\u0011\u0011!C\u00039\"9a\fCA\u0001\n\u000by&aE!dGVlW\u000f\\1uK2{gnZ!se\u0006L(BA\b\u0011\u00035\u0019wN\u001c<feR,'/S7qY*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0011\u0001\u0001\u0007\t\u00033ii\u0011\u0001F\u0005\u00037Q\u0011a!\u00118z-\u0006d\u0017\u0001Q:dC2\fGeY8na\u0006$HE[1wCb\"3m\u001c8wKJ$XM]%na2$\u0013iY2v[Vd\u0017\r^3M_:<\u0017I\u001d:bs\u0012\"SO\u001c3fe2L\u0018N\\4\u0016\u0003y\u00012!G\u0010\"\u0013\t\u0001CCA\u0003BeJ\f\u0017\u0010\u0005\u0002\u001aE%\u00111\u0005\u0006\u0002\u0005\u0019>tw-A!tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDeY8om\u0016\u0014H/\u001a:J[BdG%Q2dk6,H.\u0019;f\u0019>tw-\u0011:sCf$C%\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"aJ\u0015\u0011\u0005!\u0002Q\"\u0001\b\t\u000b)\u001a\u0001\u0019\u0001\u0010\u0002\u0015UtG-\u001a:ms&tw-\u0001\u0006bG\u000e,X.\u001e7bi\u0016,\u0012!\f\t\u0003]yr!aL\u001e\u000f\u0005AJdBA\u00199\u001d\t\u0011tG\u0004\u00024m5\tAG\u0003\u00026-\u00051AH]8pizJ\u0011!F\u0005\u0003'QI!!\u0005\n\n\u0005i\u0002\u0012AD2pY2,7\r^5p]&k\u0007\u000f\\\u0005\u0003yu\nq\u0001]1dW\u0006<WM\u0003\u0002;!%\u0011q\b\u0011\u0002\u0010\u0019>tw-Q2dk6,H.\u0019;pe*\u0011A(P\u0001\tQ\u0006\u001c\bnQ8eKR\t1\t\u0005\u0002\u001a\t&\u0011Q\t\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002I\u0017B\u0011\u0011$S\u0005\u0003\u0015R\u0011qAQ8pY\u0016\fg\u000eC\u0004M\r\u0005\u0005\t\u0019A'\u0002\u0007a$\u0013\u0007\u0005\u0002\u001a\u001d&\u0011q\n\u0006\u0002\u0004\u0003:L\u0018aE!dGVlW\u000f\\1uK2{gnZ!se\u0006L\bC\u0001\u0015\t'\tA1\u000b\u0005\u0002\u001a)&\u0011Q\u000b\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003E\u000bA#Y2dk6,H.\u0019;fI\u0015DH/\u001a8tS>tGCA\u0017Z\u0011\u0015Q&\u00021\u0001(\u0003\u0015!C\u000f[5t\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005\tk\u0006\"\u0002.\f\u0001\u00049\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\t\u0001'\r\u0006\u0002IC\"9A\nDA\u0001\u0002\u0004i\u0005\"\u0002.\r\u0001\u00049\u0003")
public final class AccumulateLongArray {
    private final long[] scala$compat$java8$converterImpl$AccumulateLongArray$$underlying;

    public static boolean equals$extension(long[] lArray, Object object) {
        return AccumulateLongArray$.MODULE$.equals$extension(lArray, object);
    }

    public static int hashCode$extension(long[] lArray) {
        return AccumulateLongArray$.MODULE$.hashCode$extension(lArray);
    }

    public static LongAccumulator accumulate$extension(long[] lArray) {
        return AccumulateLongArray$.MODULE$.accumulate$extension(lArray);
    }

    public long[] scala$compat$java8$converterImpl$AccumulateLongArray$$underlying() {
        return this.scala$compat$java8$converterImpl$AccumulateLongArray$$underlying;
    }

    public LongAccumulator accumulate() {
        return AccumulateLongArray$.MODULE$.accumulate$extension(this.scala$compat$java8$converterImpl$AccumulateLongArray$$underlying());
    }

    public int hashCode() {
        return AccumulateLongArray$.MODULE$.hashCode$extension(this.scala$compat$java8$converterImpl$AccumulateLongArray$$underlying());
    }

    public boolean equals(Object x$1) {
        return AccumulateLongArray$.MODULE$.equals$extension(this.scala$compat$java8$converterImpl$AccumulateLongArray$$underlying(), x$1);
    }

    public AccumulateLongArray(long[] underlying) {
        this.scala$compat$java8$converterImpl$AccumulateLongArray$$underlying = underlying;
    }
}

