/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorRef;
import akka.dispatch.sysmsg.Failed$;
import akka.dispatch.sysmsg.StashWhenFailed;
import akka.dispatch.sysmsg.StashWhenWaitingForChildren;
import akka.dispatch.sysmsg.SystemMessage;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Me!\u0002\u000f\u001e\u0005\u0006\u001a\u0003\u0002\u0003#\u0001\u0005+\u0007I\u0011A#\t\u00111\u0003!\u0011#Q\u0001\n\u0019C\u0001\"\u0014\u0001\u0003\u0016\u0004%\tA\u0014\u0005\t%\u0002\u0011\t\u0012)A\u0005\u001f\"A1\u000b\u0001BK\u0002\u0013\u0005A\u000b\u0003\u0005Y\u0001\tE\t\u0015!\u0003V\u0011\u0015I\u0006\u0001\"\u0001[\u0011\u001dy\u0006!!A\u0005\u0002\u0001Dq\u0001\u001a\u0001\u0012\u0002\u0013\u0005Q\rC\u0004q\u0001E\u0005I\u0011A9\t\u000fM\u0004\u0011\u0013!C\u0001i\"9a\u000fAA\u0001\n\u0003:\b\u0002CA\u0001\u0001\u0005\u0005I\u0011\u0001+\t\u0013\u0005\r\u0001!!A\u0005\u0002\u0005\u0015\u0001\"CA\t\u0001\u0005\u0005I\u0011IA\n\u0011%\t\t\u0003AA\u0001\n\u0003\t\u0019\u0003C\u0005\u0002.\u0001\t\t\u0011\"\u0011\u00020!I\u00111\u0007\u0001\u0002\u0002\u0013\u0005\u0013Q\u0007\u0005\n\u0003o\u0001\u0011\u0011!C!\u0003sA\u0011\"a\u000f\u0001\u0003\u0003%\t%!\u0010\b\u0015\u00055S$!A\t\u0002\u0005\nyEB\u0005\u001d;\u0005\u0005\t\u0012A\u0011\u0002R!1\u0011L\u0006C\u0001\u0003SB\u0011\"a\u000e\u0017\u0003\u0003%)%!\u000f\t\u0013\u0005-d#!A\u0005\u0002\u00065\u0004\"CA;-\u0005\u0005I\u0011QA<\u0011%\tIIFA\u0001\n\u0013\tYI\u0001\u0004GC&dW\r\u001a\u0006\u0003=}\taa]=t[N<'B\u0001\u0011\"\u0003!!\u0017n\u001d9bi\u000eD'\"\u0001\u0012\u0002\t\u0005\\7.Y\n\b\u0001\u0011Rc&\r\u001b8!\t)\u0003&D\u0001'\u0015\u00059\u0013!B:dC2\f\u0017BA\u0015'\u0005\u0019\te.\u001f*fMB\u00111\u0006L\u0007\u0002;%\u0011Q&\b\u0002\u000e'f\u001cH/Z7NKN\u001c\u0018mZ3\u0011\u0005-z\u0013B\u0001\u0019\u001e\u0005=\u0019F/Y:i/\",gNR1jY\u0016$\u0007CA\u00163\u0013\t\u0019TDA\u000eTi\u0006\u001c\bn\u00165f]^\u000b\u0017\u000e^5oO\u001a{'o\u00115jY\u0012\u0014XM\u001c\t\u0003KUJ!A\u000e\u0014\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001(\u0011\b\u0003s}r!A\u000f \u000e\u0003mR!\u0001P\u001f\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011aJ\u0005\u0003\u0001\u001a\nq\u0001]1dW\u0006<W-\u0003\u0002C\u0007\na1+\u001a:jC2L'0\u00192mK*\u0011\u0001IJ\u0001\u0006G\"LG\u000eZ\u000b\u0002\rB\u0011qIS\u0007\u0002\u0011*\u0011\u0011*I\u0001\u0006C\u000e$xN]\u0005\u0003\u0017\"\u0013\u0001\"Q2u_J\u0014VMZ\u0001\u0007G\"LG\u000e\u001a\u0011\u0002\u000b\r\fWo]3\u0016\u0003=\u0003\"\u0001\u000f)\n\u0005E\u001b%!\u0003+ie><\u0018M\u00197f\u0003\u0019\u0019\u0017-^:fA\u0005\u0019Q/\u001b3\u0016\u0003U\u0003\"!\n,\n\u0005]3#aA%oi\u0006!Q/\u001b3!\u0003\u0019a\u0014N\\5u}Q!1\fX/_!\tY\u0003\u0001C\u0003E\u000f\u0001\u0007a\tC\u0003N\u000f\u0001\u0007q\nC\u0003T\u000f\u0001\u0007Q+\u0001\u0003d_BLH\u0003B.bE\u000eDq\u0001\u0012\u0005\u0011\u0002\u0003\u0007a\tC\u0004N\u0011A\u0005\t\u0019A(\t\u000fMC\u0001\u0013!a\u0001+\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u00014+\u0005\u0019;7&\u00015\u0011\u0005%tW\"\u00016\u000b\u0005-d\u0017!C;oG\",7m[3e\u0015\tig%\u0001\u0006b]:|G/\u0019;j_:L!a\u001c6\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003IT#aT4\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\tQO\u000b\u0002VO\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012\u0001\u001f\t\u0003szl\u0011A\u001f\u0006\u0003wr\fA\u0001\\1oO*\tQ0\u0001\u0003kCZ\f\u0017BA@{\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BA\u0004\u0003\u001b\u00012!JA\u0005\u0013\r\tYA\n\u0002\u0004\u0003:L\b\u0002CA\b\u001d\u0005\u0005\t\u0019A+\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t)\u0002\u0005\u0004\u0002\u0018\u0005u\u0011qA\u0007\u0003\u00033Q1!a\u0007'\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003?\tIB\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0013\u0003W\u00012!JA\u0014\u0013\r\tIC\n\u0002\b\u0005>|G.Z1o\u0011%\ty\u0001EA\u0001\u0002\u0004\t9!\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,Gc\u0001=\u00022!A\u0011qB\t\u0002\u0002\u0003\u0007Q+\u0001\u0005iCND7i\u001c3f)\u0005)\u0016\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003a\fa!Z9vC2\u001cH\u0003BA\u0013\u0003\u007fA\u0011\"a\u0004\u0015\u0003\u0003\u0005\r!a\u0002)\u000f\u0001\t\u0019%!\u0013\u0002LA\u0019Q%!\u0012\n\u0007\u0005\u001dcE\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!\u0001\u0004GC&dW\r\u001a\t\u0003WY\u0019RAFA*\u0003?\u0002\u0002\"!\u0016\u0002\\\u0019{UkW\u0007\u0003\u0003/R1!!\u0017'\u0003\u001d\u0011XO\u001c;j[\u0016LA!!\u0018\u0002X\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001a\u0011\t\u0005\u0005\u0014qM\u0007\u0003\u0003GR1!!\u001a}\u0003\tIw.C\u0002C\u0003G\"\"!a\u0014\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000fm\u000by'!\u001d\u0002t!)A)\u0007a\u0001\r\")Q*\u0007a\u0001\u001f\")1+\u0007a\u0001+\u00069QO\\1qa2LH\u0003BA=\u0003\u000b\u0003R!JA>\u0003\u007fJ1!! '\u0005\u0019y\u0005\u000f^5p]B1Q%!!G\u001fVK1!a!'\u0005\u0019!V\u000f\u001d7fg!A\u0011q\u0011\u000e\u0002\u0002\u0003\u00071,A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!$\u0011\u0007e\fy)C\u0002\u0002\u0012j\u0014aa\u00142kK\u000e$\b")
public final class Failed
implements SystemMessage,
StashWhenFailed,
StashWhenWaitingForChildren,
Product {
    private static final long serialVersionUID = 1L;
    private final ActorRef child;
    private final Throwable cause;
    private final int uid;
    private transient SystemMessage next;

    public static Option<Tuple3<ActorRef, Throwable, Object>> unapply(Failed x$0) {
        return Failed$.MODULE$.unapply(x$0);
    }

    public static Failed apply(ActorRef child, Throwable cause, int uid) {
        return Failed$.MODULE$.apply(child, cause, uid);
    }

    public static Function1<Tuple3<ActorRef, Throwable, Object>, Failed> tupled() {
        return Failed$.MODULE$.tupled();
    }

    public static Function1<ActorRef, Function1<Throwable, Function1<Object, Failed>>> curried() {
        return Failed$.MODULE$.curried();
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

    public ActorRef child() {
        return this.child;
    }

    public Throwable cause() {
        return this.cause;
    }

    public int uid() {
        return this.uid;
    }

    public Failed copy(ActorRef child, Throwable cause, int uid) {
        return new Failed(child, cause, uid);
    }

    public ActorRef copy$default$1() {
        return this.child();
    }

    public Throwable copy$default$2() {
        return this.cause();
    }

    public int copy$default$3() {
        return this.uid();
    }

    @Override
    public String productPrefix() {
        return "Failed";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.child();
            }
            case 1: {
                return this.cause();
            }
            case 2: {
                return BoxesRunTime.boxToInteger(this.uid());
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
        return x$1 instanceof Failed;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "child";
            }
            case 1: {
                return "cause";
            }
            case 2: {
                return "uid";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.child()));
        n = Statics.mix(n, Statics.anyHash(this.cause()));
        n = Statics.mix(n, this.uid());
        return Statics.finalizeHash(n, 3);
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
        if (!(object instanceof Failed)) return false;
        boolean bl = true;
        if (!bl) return false;
        Failed failed = (Failed)x$1;
        if (this.uid() != failed.uid()) return false;
        ActorRef actorRef2 = this.child();
        ActorRef actorRef3 = failed.child();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        Throwable throwable = this.cause();
        Throwable throwable2 = failed.cause();
        if (throwable == null) {
            if (throwable2 == null) return true;
            return false;
        } else {
            if (!throwable.equals(throwable2)) return false;
            return true;
        }
    }

    public Failed(ActorRef child, Throwable cause, int uid) {
        this.child = child;
        this.cause = cause;
        this.uid = uid;
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

