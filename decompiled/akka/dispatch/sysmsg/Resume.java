/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.Resume$;
import akka.dispatch.sysmsg.StashWhenWaitingForChildren;
import akka.dispatch.sysmsg.SystemMessage;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055c!\u0002\f\u0018\u0005ni\u0002\u0002C\u001e\u0001\u0005+\u0007I\u0011\u0001\u001f\t\u0011\u0001\u0003!\u0011#Q\u0001\nuBQ!\u0011\u0001\u0005\u0002\tCq!\u0012\u0001\u0002\u0002\u0013\u0005a\tC\u0004I\u0001E\u0005I\u0011A%\t\u000fQ\u0003\u0011\u0011!C!+\"9a\fAA\u0001\n\u0003y\u0006bB2\u0001\u0003\u0003%\t\u0001\u001a\u0005\bU\u0002\t\t\u0011\"\u0011l\u0011\u001d\u0011\b!!A\u0005\u0002MDq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0013\u0010C\u0004|\u0001\u0005\u0005I\u0011\t?\t\u000fu\u0004\u0011\u0011!C!}\"Aq\u0010AA\u0001\n\u0003\n\ta\u0002\u0006\u0002\u0012]\t\t\u0011#\u0001\u001c\u0003'1\u0011BF\f\u0002\u0002#\u00051$!\u0006\t\r\u0005\u0003B\u0011AA\u0017\u0011\u001di\b#!A\u0005FyD\u0011\"a\f\u0011\u0003\u0003%\t)!\r\t\u0013\u0005U\u0002#!A\u0005\u0002\u0006]\u0002\"CA\"!\u0005\u0005I\u0011BA#\u0005\u0019\u0011Vm];nK*\u0011\u0001$G\u0001\u0007gf\u001cXn]4\u000b\u0005iY\u0012\u0001\u00033jgB\fGo\u00195\u000b\u0003q\tA!Y6lCN1\u0001A\b\u0013)W9\u0002\"a\b\u0012\u000e\u0003\u0001R\u0011!I\u0001\u0006g\u000e\fG.Y\u0005\u0003G\u0001\u0012a!\u00118z%\u00164\u0007CA\u0013'\u001b\u00059\u0012BA\u0014\u0018\u00055\u0019\u0016p\u001d;f[6+7o]1hKB\u0011Q%K\u0005\u0003U]\u00111d\u0015;bg\"<\u0006.\u001a8XC&$\u0018N\\4G_J\u001c\u0005.\u001b7ee\u0016t\u0007CA\u0010-\u0013\ti\u0003EA\u0004Qe>$Wo\u0019;\u0011\u0005=BdB\u0001\u00197\u001d\t\tT'D\u00013\u0015\t\u0019D'\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005\t\u0013BA\u001c!\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u000f\u001e\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005]\u0002\u0013aD2bkN,GMQ=GC&dWO]3\u0016\u0003u\u0002\"a\f \n\u0005}R$!\u0003+ie><\u0018M\u00197f\u0003A\u0019\u0017-^:fI\nKh)Y5mkJ,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003\u0007\u0012\u0003\"!\n\u0001\t\u000bm\u001a\u0001\u0019A\u001f\u0002\t\r|\u0007/\u001f\u000b\u0003\u0007\u001eCqa\u000f\u0003\u0011\u0002\u0003\u0007Q(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003)S#!P&,\u00031\u0003\"!\u0014*\u000e\u00039S!a\u0014)\u0002\u0013Ut7\r[3dW\u0016$'BA)!\u0003)\tgN\\8uCRLwN\\\u0005\u0003':\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\ta\u000b\u0005\u0002X96\t\u0001L\u0003\u0002Z5\u0006!A.\u00198h\u0015\u0005Y\u0016\u0001\u00026bm\u0006L!!\u0018-\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005\u0001\u0007CA\u0010b\u0013\t\u0011\u0007EA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002fQB\u0011qDZ\u0005\u0003O\u0002\u00121!\u00118z\u0011\u001dI\u0007\"!AA\u0002\u0001\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u00017\u0011\u00075\u0004X-D\u0001o\u0015\ty\u0007%\u0001\u0006d_2dWm\u0019;j_:L!!\u001d8\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003i^\u0004\"aH;\n\u0005Y\u0004#a\u0002\"p_2,\u0017M\u001c\u0005\bS*\t\t\u00111\u0001f\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0005YS\bbB5\f\u0003\u0003\u0005\r\u0001Y\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0001-\u0001\u0005u_N#(/\u001b8h)\u00051\u0016AB3rk\u0006d7\u000fF\u0002u\u0003\u0007Aq!\u001b\b\u0002\u0002\u0003\u0007Q\rK\u0004\u0001\u0003\u000f\ti!a\u0004\u0011\u0007}\tI!C\u0002\u0002\f\u0001\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005\taAU3tk6,\u0007CA\u0013\u0011'\u0015\u0001\u0012qCA\u0012!\u0019\tI\"a\b>\u00076\u0011\u00111\u0004\u0006\u0004\u0003;\u0001\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003C\tYBA\tBEN$(/Y2u\rVt7\r^5p]F\u0002B!!\n\u0002,5\u0011\u0011q\u0005\u0006\u0004\u0003SQ\u0016AA5p\u0013\rI\u0014q\u0005\u000b\u0003\u0003'\tQ!\u00199qYf$2aQA\u001a\u0011\u0015Y4\u00031\u0001>\u0003\u001d)h.\u00199qYf$B!!\u000f\u0002@A!q$a\u000f>\u0013\r\ti\u0004\t\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005\u0005C#!AA\u0002\r\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t9\u0005E\u0002X\u0003\u0013J1!a\u0013Y\u0005\u0019y%M[3di\u0002")
public final class Resume
implements SystemMessage,
StashWhenWaitingForChildren,
Product {
    private static final long serialVersionUID = 1L;
    private final Throwable causedByFailure;
    private transient SystemMessage next;

    public static Option<Throwable> unapply(Resume x$0) {
        return Resume$.MODULE$.unapply(x$0);
    }

    public static Resume apply(Throwable causedByFailure) {
        return Resume$.MODULE$.apply(causedByFailure);
    }

    public static <A> Function1<Throwable, A> andThen(Function1<Resume, A> g2) {
        return Resume$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, Resume> compose(Function1<A, Throwable> g2) {
        return Resume$.MODULE$.compose(g2);
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

    public Throwable causedByFailure() {
        return this.causedByFailure;
    }

    public Resume copy(Throwable causedByFailure) {
        return new Resume(causedByFailure);
    }

    public Throwable copy$default$1() {
        return this.causedByFailure();
    }

    @Override
    public String productPrefix() {
        return "Resume";
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
                return this.causedByFailure();
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
        return x$1 instanceof Resume;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "causedByFailure";
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
        if (!(object instanceof Resume)) return false;
        boolean bl = true;
        if (!bl) return false;
        Resume resume = (Resume)x$1;
        Throwable throwable = this.causedByFailure();
        Throwable throwable2 = resume.causedByFailure();
        if (throwable != null) {
            if (!throwable.equals(throwable2)) return false;
            return true;
        }
        if (throwable2 == null) return true;
        return false;
    }

    public Resume(Throwable causedByFailure) {
        this.causedByFailure = causedByFailure;
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

