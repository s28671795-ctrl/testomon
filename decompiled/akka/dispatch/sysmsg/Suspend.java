/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.StashWhenWaitingForChildren;
import akka.dispatch.sysmsg.Suspend$;
import akka.dispatch.sysmsg.SystemMessage;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Ma!B\n\u0015\u0005bQ\u0002\"\u0002\u001d\u0001\t\u0003I\u0004bB\u001e\u0001\u0003\u0003%\t!\u000f\u0005\by\u0001\t\t\u0011\"\u0011>\u0011\u001d1\u0005!!A\u0005\u0002\u001dCqa\u0013\u0001\u0002\u0002\u0013\u0005A\nC\u0004S\u0001\u0005\u0005I\u0011I*\t\u000fi\u0003\u0011\u0011!C\u00017\"9\u0001\rAA\u0001\n\u0003\n\u0007bB2\u0001\u0003\u0003%\t\u0005\u001a\u0005\bK\u0002\t\t\u0011\"\u0011g\u0011\u001d9\u0007!!A\u0005B!<\u0001\u0002\u001d\u000b\u0002\u0002#\u0005\u0001$\u001d\u0004\t'Q\t\t\u0011#\u0001\u0019e\")\u0001(\u0004C\u0001}\"9Q-DA\u0001\n\u000b2\u0007bB@\u000e\u0003\u0003%\t)\u000f\u0005\n\u0003\u0003i\u0011\u0011!CA\u0003\u0007A\u0011\"!\u0003\u000e\u0003\u0003%I!a\u0003\u0003\u000fM+8\u000f]3oI*\u0011QCF\u0001\u0007gf\u001cXn]4\u000b\u0005]A\u0012\u0001\u00033jgB\fGo\u00195\u000b\u0003e\tA!Y6lCN1\u0001aG\u0011&Q-\u0002\"\u0001H\u0010\u000e\u0003uQ\u0011AH\u0001\u0006g\u000e\fG.Y\u0005\u0003Au\u0011a!\u00118z%\u00164\u0007C\u0001\u0012$\u001b\u0005!\u0012B\u0001\u0013\u0015\u00055\u0019\u0016p\u001d;f[6+7o]1hKB\u0011!EJ\u0005\u0003OQ\u00111d\u0015;bg\"<\u0006.\u001a8XC&$\u0018N\\4G_J\u001c\u0005.\u001b7ee\u0016t\u0007C\u0001\u000f*\u0013\tQSDA\u0004Qe>$Wo\u0019;\u0011\u00051*dBA\u00174\u001d\tq#'D\u00010\u0015\t\u0001\u0014'\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005q\u0012B\u0001\u001b\u001e\u0003\u001d\u0001\u0018mY6bO\u0016L!AN\u001c\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005Qj\u0012A\u0002\u001fj]&$h\bF\u0001;!\t\u0011\u0003!\u0001\u0003d_BL\u0018!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001?!\tyD)D\u0001A\u0015\t\t%)\u0001\u0003mC:<'\"A\"\u0002\t)\fg/Y\u0005\u0003\u000b\u0002\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLX#\u0001%\u0011\u0005qI\u0015B\u0001&\u001e\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\ti\u0005\u000b\u0005\u0002\u001d\u001d&\u0011q*\b\u0002\u0004\u0003:L\bbB)\u0006\u0003\u0003\u0005\r\u0001S\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003Q\u00032!\u0016-N\u001b\u00051&BA,\u001e\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u00033Z\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0011Al\u0018\t\u00039uK!AX\u000f\u0003\u000f\t{w\u000e\\3b]\"9\u0011kBA\u0001\u0002\u0004i\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"A\u00102\t\u000fEC\u0011\u0011!a\u0001\u0011\u0006A\u0001.Y:i\u0007>$W\rF\u0001I\u0003!!xn\u0015;sS:<G#\u0001 \u0002\r\u0015\fX/\u00197t)\ta\u0016\u000eC\u0004R\u0017\u0005\u0005\t\u0019A')\t\u0001Ygn\u001c\t\u000391L!!\\\u000f\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001\u0002\u000fM+8\u000f]3oIB\u0011!%D\n\u0004\u001bML\bc\u0001;xu5\tQO\u0003\u0002w;\u00059!/\u001e8uS6,\u0017B\u0001=v\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g\u000e\r\t\u0003uvl\u0011a\u001f\u0006\u0003y\n\u000b!![8\n\u0005YZH#A9\u0002\u000b\u0005\u0004\b\u000f\\=\u0002\u000fUt\u0017\r\u001d9msR\u0019A,!\u0002\t\u0011\u0005\u001d\u0011#!AA\u0002i\n1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\ti\u0001E\u0002@\u0003\u001fI1!!\u0005A\u0005\u0019y%M[3di\u0002")
public final class Suspend
implements SystemMessage,
StashWhenWaitingForChildren,
Product {
    private static final long serialVersionUID = 1L;
    private transient SystemMessage next;

    public static boolean unapply(Suspend x$0) {
        return Suspend$.MODULE$.unapply(x$0);
    }

    public static Suspend apply() {
        return Suspend$.MODULE$.apply();
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

    public Suspend copy() {
        return new Suspend();
    }

    @Override
    public String productPrefix() {
        return "Suspend";
    }

    @Override
    public int productArity() {
        return 0;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Suspend;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    @Override
    public boolean equals(Object x$1) {
        Object object = x$1;
        return object instanceof Suspend;
    }

    public Suspend() {
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

