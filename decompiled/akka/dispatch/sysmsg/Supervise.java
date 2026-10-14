/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.actor.ActorRef;
import akka.dispatch.sysmsg.Supervise$;
import akka.dispatch.sysmsg.SystemMessage;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005-d!B\r\u001b\u0005z\u0001\u0003\u0002C\u001e\u0001\u0005+\u0007I\u0011\u0001\u001f\t\u0011\r\u0003!\u0011#Q\u0001\nuB\u0001\u0002\u0012\u0001\u0003\u0016\u0004%\t!\u0012\u0005\t\u0013\u0002\u0011\t\u0012)A\u0005\r\")!\n\u0001C\u0001\u0017\"9q\nAA\u0001\n\u0003\u0001\u0006bB*\u0001#\u0003%\t\u0001\u0016\u0005\b?\u0002\t\n\u0011\"\u0001a\u0011\u001d\u0011\u0007!!A\u0005B\rDq\u0001\u001c\u0001\u0002\u0002\u0013\u0005Q\u000eC\u0004r\u0001\u0005\u0005I\u0011\u0001:\t\u000fa\u0004\u0011\u0011!C!s\"I\u0011\u0011\u0001\u0001\u0002\u0002\u0013\u0005\u00111\u0001\u0005\n\u0003\u000f\u0001\u0011\u0011!C!\u0003\u0013A\u0011\"!\u0004\u0001\u0003\u0003%\t%a\u0004\t\u0013\u0005E\u0001!!A\u0005B\u0005M\u0001\"CA\u000b\u0001\u0005\u0005I\u0011IA\f\u000f)\t9CGA\u0001\u0012\u0003q\u0012\u0011\u0006\u0004\n3i\t\t\u0011#\u0001\u001f\u0003WAaAS\n\u0005\u0002\u0005\r\u0003\"CA\t'\u0005\u0005IQIA\n\u0011%\t)eEA\u0001\n\u0003\u000b9\u0005C\u0005\u0002NM\t\t\u0011\"!\u0002P!I\u0011\u0011M\n\u0002\u0002\u0013%\u00111\r\u0002\n'V\u0004XM\u001d<jg\u0016T!a\u0007\u000f\u0002\rML8/\\:h\u0015\tib$\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005y\u0012\u0001B1lW\u0006\u001cR\u0001A\u0011(W9\u0002\"AI\u0013\u000e\u0003\rR\u0011\u0001J\u0001\u0006g\u000e\fG.Y\u0005\u0003M\r\u0012a!\u00118z%\u00164\u0007C\u0001\u0015*\u001b\u0005Q\u0012B\u0001\u0016\u001b\u00055\u0019\u0016p\u001d;f[6+7o]1hKB\u0011!\u0005L\u0005\u0003[\r\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u00020q9\u0011\u0001G\u000e\b\u0003cUj\u0011A\r\u0006\u0003gQ\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002I%\u0011qgI\u0001\ba\u0006\u001c7.Y4f\u0013\tI$H\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00028G\u0005)1\r[5mIV\tQ\b\u0005\u0002?\u00036\tqH\u0003\u0002A=\u0005)\u0011m\u0019;pe&\u0011!i\u0010\u0002\t\u0003\u000e$xN\u001d*fM\u000611\r[5mI\u0002\nQ!Y:z]\u000e,\u0012A\u0012\t\u0003E\u001dK!\u0001S\u0012\u0003\u000f\t{w\u000e\\3b]\u00061\u0011m]=oG\u0002\na\u0001P5oSRtDc\u0001'N\u001dB\u0011\u0001\u0006\u0001\u0005\u0006w\u0015\u0001\r!\u0010\u0005\u0006\t\u0016\u0001\rAR\u0001\u0005G>\u0004\u0018\u0010F\u0002M#JCqa\u000f\u0004\u0011\u0002\u0003\u0007Q\bC\u0004E\rA\u0005\t\u0019\u0001$\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tQK\u000b\u0002>-.\nq\u000b\u0005\u0002Y;6\t\u0011L\u0003\u0002[7\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u00039\u000e\n!\"\u00198o_R\fG/[8o\u0013\tq\u0016LA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'F\u0001bU\t1e+A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002IB\u0011QM[\u0007\u0002M*\u0011q\r[\u0001\u0005Y\u0006twMC\u0001j\u0003\u0011Q\u0017M^1\n\u0005-4'AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001o!\t\u0011s.\u0003\u0002qG\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u00111O\u001e\t\u0003EQL!!^\u0012\u0003\u0007\u0005s\u0017\u0010C\u0004x\u0017\u0005\u0005\t\u0019\u00018\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005Q\bcA>\u007fg6\tAP\u0003\u0002~G\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005}d(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$2ARA\u0003\u0011\u001d9X\"!AA\u0002M\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019A-a\u0003\t\u000f]t\u0011\u0011!a\u0001]\u0006A\u0001.Y:i\u0007>$W\rF\u0001o\u0003!!xn\u0015;sS:<G#\u00013\u0002\r\u0015\fX/\u00197t)\r1\u0015\u0011\u0004\u0005\boF\t\t\u00111\u0001tQ\u001d\u0001\u0011QDA\u0012\u0003K\u00012AIA\u0010\u0013\r\t\tc\t\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\n'V\u0004XM\u001d<jg\u0016\u0004\"\u0001K\n\u0014\u000bM\ti#!\u000f\u0011\u000f\u0005=\u0012QG\u001fG\u00196\u0011\u0011\u0011\u0007\u0006\u0004\u0003g\u0019\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003o\t\tDA\tBEN$(/Y2u\rVt7\r^5p]J\u0002B!a\u000f\u0002B5\u0011\u0011Q\b\u0006\u0004\u0003\u007fA\u0017AA5p\u0013\rI\u0014Q\b\u000b\u0003\u0003S\tQ!\u00199qYf$R\u0001TA%\u0003\u0017BQa\u000f\fA\u0002uBQ\u0001\u0012\fA\u0002\u0019\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002R\u0005u\u0003#\u0002\u0012\u0002T\u0005]\u0013bAA+G\t1q\n\u001d;j_:\u0004RAIA-{\u0019K1!a\u0017$\u0005\u0019!V\u000f\u001d7fe!A\u0011qL\f\u0002\u0002\u0003\u0007A*A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u001a\u0011\u0007\u0015\f9'C\u0002\u0002j\u0019\u0014aa\u00142kK\u000e$\b")
public final class Supervise
implements SystemMessage,
Product {
    private static final long serialVersionUID = 1L;
    private final ActorRef child;
    private final boolean async;
    private transient SystemMessage next;

    public static Option<Tuple2<ActorRef, Object>> unapply(Supervise x$0) {
        return Supervise$.MODULE$.unapply(x$0);
    }

    public static Supervise apply(ActorRef child, boolean async) {
        return Supervise$.MODULE$.apply(child, async);
    }

    public static Function1<Tuple2<ActorRef, Object>, Supervise> tupled() {
        return Supervise$.MODULE$.tupled();
    }

    public static Function1<ActorRef, Function1<Object, Supervise>> curried() {
        return Supervise$.MODULE$.curried();
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

    public boolean async() {
        return this.async;
    }

    public Supervise copy(ActorRef child, boolean async) {
        return new Supervise(child, async);
    }

    public ActorRef copy$default$1() {
        return this.child();
    }

    public boolean copy$default$2() {
        return this.async();
    }

    @Override
    public String productPrefix() {
        return "Supervise";
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
                return this.child();
            }
            case 1: {
                return BoxesRunTime.boxToBoolean(this.async());
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
        return x$1 instanceof Supervise;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "child";
            }
            case 1: {
                return "async";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.child()));
        n = Statics.mix(n, this.async() ? 1231 : 1237);
        return Statics.finalizeHash(n, 2);
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
        if (!(object instanceof Supervise)) return false;
        boolean bl = true;
        if (!bl) return false;
        Supervise supervise = (Supervise)x$1;
        if (this.async() != supervise.async()) return false;
        ActorRef actorRef2 = this.child();
        ActorRef actorRef3 = supervise.child();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public Supervise(ActorRef child, boolean async) {
        this.child = child;
        this.async = async;
        SystemMessage.$init$(this);
        Product.$init$(this);
    }
}

