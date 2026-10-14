/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.Recreate$;
import akka.dispatch.sysmsg.StashWhenWaitingForChildren;
import akka.dispatch.sysmsg.SystemMessage;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055c!\u0002\f\u0018\u0005ni\u0002\u0002C\u001e\u0001\u0005+\u0007I\u0011\u0001\u001f\t\u0011\u0001\u0003!\u0011#Q\u0001\nuBQ!\u0011\u0001\u0005\u0002\tCq!\u0012\u0001\u0002\u0002\u0013\u0005a\tC\u0004I\u0001E\u0005I\u0011A%\t\u000fQ\u0003\u0011\u0011!C!+\"9a\fAA\u0001\n\u0003y\u0006bB2\u0001\u0003\u0003%\t\u0001\u001a\u0005\bU\u0002\t\t\u0011\"\u0011l\u0011\u001d\u0011\b!!A\u0005\u0002MDq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0013\u0010C\u0004|\u0001\u0005\u0005I\u0011\t?\t\u000fu\u0004\u0011\u0011!C!}\"Aq\u0010AA\u0001\n\u0003\n\ta\u0002\u0006\u0002\u0012]\t\t\u0011#\u0001\u001c\u0003'1\u0011BF\f\u0002\u0002#\u00051$!\u0006\t\r\u0005\u0003B\u0011AA\u0017\u0011\u001di\b#!A\u0005FyD\u0011\"a\f\u0011\u0003\u0003%\t)!\r\t\u0013\u0005U\u0002#!A\u0005\u0002\u0006]\u0002\"CA\"!\u0005\u0005I\u0011BA#\u0005!\u0011Vm\u0019:fCR,'B\u0001\r\u001a\u0003\u0019\u0019\u0018p]7tO*\u0011!dG\u0001\tI&\u001c\b/\u0019;dQ*\tA$\u0001\u0003bW.\f7C\u0002\u0001\u001fI!Zc\u0006\u0005\u0002 E5\t\u0001EC\u0001\"\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0003E\u0001\u0004B]f\u0014VM\u001a\t\u0003K\u0019j\u0011aF\u0005\u0003O]\u0011QbU=ti\u0016lW*Z:tC\u001e,\u0007CA\u0013*\u0013\tQsCA\u000eTi\u0006\u001c\bn\u00165f]^\u000b\u0017\u000e^5oO\u001a{'o\u00115jY\u0012\u0014XM\u001c\t\u0003?1J!!\f\u0011\u0003\u000fA\u0013x\u000eZ;diB\u0011q\u0006\u000f\b\u0003aYr!!M\u001b\u000e\u0003IR!a\r\u001b\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011!I\u0005\u0003o\u0001\nq\u0001]1dW\u0006<W-\u0003\u0002:u\ta1+\u001a:jC2L'0\u00192mK*\u0011q\u0007I\u0001\u0006G\u0006,8/Z\u000b\u0002{A\u0011qFP\u0005\u0003\u007fi\u0012\u0011\u0002\u00165s_^\f'\r\\3\u0002\r\r\fWo]3!\u0003\u0019a\u0014N\\5u}Q\u00111\t\u0012\t\u0003K\u0001AQaO\u0002A\u0002u\nAaY8qsR\u00111i\u0012\u0005\bw\u0011\u0001\n\u00111\u0001>\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012A\u0013\u0016\u0003{-[\u0013\u0001\u0014\t\u0003\u001bJk\u0011A\u0014\u0006\u0003\u001fB\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005E\u0003\u0013AC1o]>$\u0018\r^5p]&\u00111K\u0014\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001W!\t9F,D\u0001Y\u0015\tI&,\u0001\u0003mC:<'\"A.\u0002\t)\fg/Y\u0005\u0003;b\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLX#\u00011\u0011\u0005}\t\u0017B\u00012!\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t)\u0007\u000e\u0005\u0002 M&\u0011q\r\t\u0002\u0004\u0003:L\bbB5\t\u0003\u0003\u0005\r\u0001Y\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u00031\u00042!\u001c9f\u001b\u0005q'BA8!\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003c:\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0011Ao\u001e\t\u0003?UL!A\u001e\u0011\u0003\u000f\t{w\u000e\\3b]\"9\u0011NCA\u0001\u0002\u0004)\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"A\u0016>\t\u000f%\\\u0011\u0011!a\u0001A\u0006A\u0001.Y:i\u0007>$W\rF\u0001a\u0003!!xn\u0015;sS:<G#\u0001,\u0002\r\u0015\fX/\u00197t)\r!\u00181\u0001\u0005\bS:\t\t\u00111\u0001fQ\u001d\u0001\u0011qAA\u0007\u0003\u001f\u00012aHA\u0005\u0013\r\tY\u0001\t\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\t%\u0016\u001c'/Z1uKB\u0011Q\u0005E\n\u0006!\u0005]\u00111\u0005\t\u0007\u00033\ty\"P\"\u000e\u0005\u0005m!bAA\u000fA\u00059!/\u001e8uS6,\u0017\u0002BA\u0011\u00037\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\t)#a\u000b\u000e\u0005\u0005\u001d\"bAA\u00155\u0006\u0011\u0011n\\\u0005\u0004s\u0005\u001dBCAA\n\u0003\u0015\t\u0007\u000f\u001d7z)\r\u0019\u00151\u0007\u0005\u0006wM\u0001\r!P\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\tI$a\u0010\u0011\t}\tY$P\u0005\u0004\u0003{\u0001#AB(qi&|g\u000e\u0003\u0005\u0002BQ\t\t\u00111\u0001D\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u000f\u00022aVA%\u0013\r\tY\u0005\u0017\u0002\u0007\u001f\nTWm\u0019;")
public final class Recreate
implements SystemMessage,
StashWhenWaitingForChildren,
Product {
    private static final long serialVersionUID = 1L;
    private final Throwable cause;
    private transient SystemMessage next;

    public static Option<Throwable> unapply(Recreate x$0) {
        return Recreate$.MODULE$.unapply(x$0);
    }

    public static Recreate apply(Throwable cause) {
        return Recreate$.MODULE$.apply(cause);
    }

    public static <A> Function1<Throwable, A> andThen(Function1<Recreate, A> g2) {
        return Recreate$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, Recreate> compose(Function1<A, Throwable> g2) {
        return Recreate$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public void unlink() {
        SystemMessage.unlink$(this);
    }

    @Override
    public boolean unlinked() {
        return SystemMessage.unlinked$(this);
    }

    @Override
    public SystemMessage next() {
        return this.next;
    }

    @Override
    public void next_$eq(SystemMessage x$1) {
        this.next = x$1;
    }

    public Throwable cause() {
        return this.cause;
    }

    public Recreate copy(Throwable cause) {
        return new Recreate(cause);
    }

    public Throwable copy$default$1() {
        return this.cause();
    }

    @Override
    public String productPrefix() {
        return "Recreate";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.cause();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Recreate;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "cause";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof Recreate)) return false;
        boolean bl = true;
        if (!bl) return false;
        Recreate recreate = (Recreate)x$1;
        Throwable throwable = this.cause();
        Throwable throwable2 = recreate.cause();
        if (throwable != null) {
            if (!throwable.equals(throwable2)) return false;
            return true;
        }
        if (throwable2 == null) return true;
        return false;
    }

    public Recreate(Throwable cause) {
        this.cause = cause;
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

