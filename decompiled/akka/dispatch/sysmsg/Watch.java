/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.InternalActorRef;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.Watch$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015d!B\r\u001b\u0005z\u0001\u0003\u0002C\u001e\u0001\u0005+\u0007I\u0011\u0001\u001f\t\u0011\r\u0003!\u0011#Q\u0001\nuB\u0001\u0002\u0012\u0001\u0003\u0016\u0004%\t\u0001\u0010\u0005\t\u000b\u0002\u0011\t\u0012)A\u0005{!)a\t\u0001C\u0001\u000f\"91\nAA\u0001\n\u0003a\u0005bB(\u0001#\u0003%\t\u0001\u0015\u0005\b7\u0002\t\n\u0011\"\u0001Q\u0011\u001da\u0006!!A\u0005BuCqA\u001a\u0001\u0002\u0002\u0013\u0005q\rC\u0004l\u0001\u0005\u0005I\u0011\u00017\t\u000fI\u0004\u0011\u0011!C!g\"9!\u0010AA\u0001\n\u0003Y\b\"CA\u0001\u0001\u0005\u0005I\u0011IA\u0002\u0011%\t9\u0001AA\u0001\n\u0003\nI\u0001C\u0005\u0002\f\u0001\t\t\u0011\"\u0011\u0002\u000e!I\u0011q\u0002\u0001\u0002\u0002\u0013\u0005\u0013\u0011C\u0004\u000b\u0003CQ\u0012\u0011!E\u0001=\u0005\rb!C\r\u001b\u0003\u0003E\tAHA\u0013\u0011\u001915\u0003\"\u0001\u0002>!I\u00111B\n\u0002\u0002\u0013\u0015\u0013Q\u0002\u0005\n\u0003\u007f\u0019\u0012\u0011!CA\u0003\u0003B\u0011\"a\u0012\u0014\u0003\u0003%\t)!\u0013\t\u0013\u0005m3#!A\u0005\n\u0005u#!B,bi\u000eD'BA\u000e\u001d\u0003\u0019\u0019\u0018p]7tO*\u0011QDH\u0001\tI&\u001c\b/\u0019;dQ*\tq$\u0001\u0003bW.\f7#\u0002\u0001\"O-r\u0003C\u0001\u0012&\u001b\u0005\u0019#\"\u0001\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0019\u001a#AB!osJ+g\r\u0005\u0002)S5\t!$\u0003\u0002+5\ti1+_:uK6lUm]:bO\u0016\u0004\"A\t\u0017\n\u00055\u001a#a\u0002)s_\u0012,8\r\u001e\t\u0003_ar!\u0001\r\u001c\u000f\u0005E*T\"\u0001\u001a\u000b\u0005M\"\u0014A\u0002\u001fs_>$hh\u0001\u0001\n\u0003\u0011J!aN\u0012\u0002\u000fA\f7m[1hK&\u0011\u0011H\u000f\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003o\r\nqa^1uG\",W-F\u0001>!\tq\u0014)D\u0001@\u0015\t\u0001e$A\u0003bGR|'/\u0003\u0002C\u007f\t\u0001\u0012J\u001c;fe:\fG.Q2u_J\u0014VMZ\u0001\to\u0006$8\r[3fA\u00059q/\u0019;dQ\u0016\u0014\u0018\u0001C<bi\u000eDWM\u001d\u0011\u0002\rqJg.\u001b;?)\rA\u0015J\u0013\t\u0003Q\u0001AQaO\u0003A\u0002uBQ\u0001R\u0003A\u0002u\nAaY8qsR\u0019\u0001*\u0014(\t\u000fm2\u0001\u0013!a\u0001{!9AI\u0002I\u0001\u0002\u0004i\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002#*\u0012QHU\u0016\u0002'B\u0011A+W\u0007\u0002+*\u0011akV\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001W\u0012\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002[+\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005i\u0001O]8ek\u000e$\bK]3gSb,\u0012A\u0018\t\u0003?\u0012l\u0011\u0001\u0019\u0006\u0003C\n\fA\u0001\\1oO*\t1-\u0001\u0003kCZ\f\u0017BA3a\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\t\u0001\u000e\u0005\u0002#S&\u0011!n\t\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003[B\u0004\"A\t8\n\u0005=\u001c#aA!os\"9\u0011oCA\u0001\u0002\u0004A\u0017a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001u!\r)\b0\\\u0007\u0002m*\u0011qoI\u0001\u000bG>dG.Z2uS>t\u0017BA=w\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005q|\bC\u0001\u0012~\u0013\tq8EA\u0004C_>dW-\u00198\t\u000fEl\u0011\u0011!a\u0001[\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\rq\u0016Q\u0001\u0005\bc:\t\t\u00111\u0001i\u0003!A\u0017m\u001d5D_\u0012,G#\u00015\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012AX\u0001\u0007KF,\u0018\r\\:\u0015\u0007q\f\u0019\u0002C\u0004r#\u0005\u0005\t\u0019A7)\u000f\u0001\t9\"!\b\u0002 A\u0019!%!\u0007\n\u0007\u0005m1E\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!A\u0003XCR\u001c\u0007\u000e\u0005\u0002)'M)1#a\n\u00024A9\u0011\u0011FA\u0018{uBUBAA\u0016\u0015\r\ticI\u0001\beVtG/[7f\u0013\u0011\t\t$a\u000b\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t'\u0007\u0005\u0003\u00026\u0005mRBAA\u001c\u0015\r\tIDY\u0001\u0003S>L1!OA\u001c)\t\t\u0019#A\u0003baBd\u0017\u0010F\u0003I\u0003\u0007\n)\u0005C\u0003<-\u0001\u0007Q\bC\u0003E-\u0001\u0007Q(A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005-\u0013q\u000b\t\u0006E\u00055\u0013\u0011K\u0005\u0004\u0003\u001f\u001a#AB(qi&|g\u000eE\u0003#\u0003'jT(C\u0002\u0002V\r\u0012a\u0001V;qY\u0016\u0014\u0004\u0002CA-/\u0005\u0005\t\u0019\u0001%\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002`A\u0019q,!\u0019\n\u0007\u0005\r\u0004M\u0001\u0004PE*,7\r\u001e")
public final class Watch
implements SystemMessage,
Product {
    private static final long serialVersionUID = 1L;
    private final InternalActorRef watchee;
    private final InternalActorRef watcher;
    private transient SystemMessage next;

    public static Option<Tuple2<InternalActorRef, InternalActorRef>> unapply(Watch x$0) {
        return Watch$.MODULE$.unapply(x$0);
    }

    public static Watch apply(InternalActorRef watchee, InternalActorRef watcher) {
        return Watch$.MODULE$.apply(watchee, watcher);
    }

    public static Function1<Tuple2<InternalActorRef, InternalActorRef>, Watch> tupled() {
        return Watch$.MODULE$.tupled();
    }

    public static Function1<InternalActorRef, Function1<InternalActorRef, Watch>> curried() {
        return Watch$.MODULE$.curried();
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

    public InternalActorRef watchee() {
        return this.watchee;
    }

    public InternalActorRef watcher() {
        return this.watcher;
    }

    public Watch copy(InternalActorRef watchee, InternalActorRef watcher) {
        return new Watch(watchee, watcher);
    }

    public InternalActorRef copy$default$1() {
        return this.watchee();
    }

    public InternalActorRef copy$default$2() {
        return this.watcher();
    }

    @Override
    public String productPrefix() {
        return "Watch";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.watchee();
            }
            case 1: {
                return this.watcher();
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
        return x$1 instanceof Watch;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "watchee";
            }
            case 1: {
                return "watcher";
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
        if (!(object instanceof Watch)) return false;
        boolean bl = true;
        if (!bl) return false;
        Watch watch = (Watch)x$1;
        InternalActorRef internalActorRef = this.watchee();
        InternalActorRef internalActorRef2 = watch.watchee();
        if (internalActorRef == null) {
            if (internalActorRef2 != null) {
                return false;
            }
        } else if (!((Object)internalActorRef).equals(internalActorRef2)) return false;
        InternalActorRef internalActorRef3 = this.watcher();
        InternalActorRef internalActorRef4 = watch.watcher();
        if (internalActorRef3 == null) {
            if (internalActorRef4 == null) return true;
            return false;
        } else {
            if (!((Object)internalActorRef3).equals(internalActorRef4)) return false;
            return true;
        }
    }

    public Watch(InternalActorRef watchee, InternalActorRef watcher) {
        this.watchee = watchee;
        this.watcher = watcher;
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

