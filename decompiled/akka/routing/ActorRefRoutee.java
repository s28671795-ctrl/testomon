/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.routing.ActorRefRoutee$;
import akka.routing.Routee;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Ec\u0001B\f\u0019\u0005vA\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\t\u007f\u0001\u0011\t\u0012)A\u0005s!)\u0001\t\u0001C\u0001\u0003\")A\t\u0001C!\u000b\"9\u0001\u000bAA\u0001\n\u0003\t\u0006bB*\u0001#\u0003%\t\u0001\u0016\u0005\b?\u0002\t\t\u0011\"\u0011a\u0011\u001dI\u0007!!A\u0005\u0002)DqA\u001c\u0001\u0002\u0002\u0013\u0005q\u000eC\u0004s\u0001\u0005\u0005I\u0011I:\t\u000fi\u0004\u0011\u0011!C\u0001w\"I\u0011\u0011\u0001\u0001\u0002\u0002\u0013\u0005\u00131\u0001\u0005\n\u0003\u000f\u0001\u0011\u0011!C!\u0003\u0013A\u0011\"a\u0003\u0001\u0003\u0003%\t%!\u0004\t\u0013\u0005=\u0001!!A\u0005B\u0005Eq!CA\u000b1\u0005\u0005\t\u0012AA\f\r!9\u0002$!A\t\u0002\u0005e\u0001B\u0002!\u0012\t\u0003\t\t\u0004C\u0005\u0002\fE\t\t\u0011\"\u0012\u0002\u000e!I\u00111G\t\u0002\u0002\u0013\u0005\u0015Q\u0007\u0005\n\u0003s\t\u0012\u0011!CA\u0003wA\u0011\"a\u0012\u0012\u0003\u0003%I!!\u0013\u0003\u001d\u0005\u001bGo\u001c:SK\u001a\u0014v.\u001e;fK*\u0011\u0011DG\u0001\be>,H/\u001b8h\u0015\u0005Y\u0012\u0001B1lW\u0006\u001c\u0001aE\u0003\u0001=\u0011B3\u0006\u0005\u0002 E5\t\u0001EC\u0001\"\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0003E\u0001\u0004B]f\u0014VM\u001a\t\u0003K\u0019j\u0011\u0001G\u0005\u0003Oa\u0011aAU8vi\u0016,\u0007CA\u0010*\u0013\tQ\u0003EA\u0004Qe>$Wo\u0019;\u0011\u00051\"dBA\u00173\u001d\tq\u0013'D\u00010\u0015\t\u0001D$\u0001\u0004=e>|GOP\u0005\u0002C%\u00111\u0007I\u0001\ba\u0006\u001c7.Y4f\u0013\t)dG\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00024A\u0005\u0019!/\u001a4\u0016\u0003e\u0002\"AO\u001f\u000e\u0003mR!\u0001\u0010\u000e\u0002\u000b\u0005\u001cGo\u001c:\n\u0005yZ$\u0001C!di>\u0014(+\u001a4\u0002\tI,g\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\t\u001b\u0005CA\u0013\u0001\u0011\u001594\u00011\u0001:\u0003\u0011\u0019XM\u001c3\u0015\u0007\u0019Ke\n\u0005\u0002 \u000f&\u0011\u0001\n\t\u0002\u0005+:LG\u000fC\u0003K\t\u0001\u00071*A\u0004nKN\u001c\u0018mZ3\u0011\u0005}a\u0015BA'!\u0005\r\te.\u001f\u0005\u0006\u001f\u0012\u0001\r!O\u0001\u0007g\u0016tG-\u001a:\u0002\t\r|\u0007/\u001f\u000b\u0003\u0005JCqaN\u0003\u0011\u0002\u0003\u0007\u0011(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003US#!\u000f,,\u0003]\u0003\"\u0001W/\u000e\u0003eS!AW.\u0002\u0013Ut7\r[3dW\u0016$'B\u0001/!\u0003)\tgN\\8uCRLwN\\\u0005\u0003=f\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t\u0011\r\u0005\u0002cO6\t1M\u0003\u0002eK\u0006!A.\u00198h\u0015\u00051\u0017\u0001\u00026bm\u0006L!\u0001[2\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005Y\u0007CA\u0010m\u0013\ti\u0007EA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002La\"9\u0011/CA\u0001\u0002\u0004Y\u0017a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001u!\r)\bpS\u0007\u0002m*\u0011q\u000fI\u0001\u000bG>dG.Z2uS>t\u0017BA=w\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005q|\bCA\u0010~\u0013\tq\bEA\u0004C_>dW-\u00198\t\u000fE\\\u0011\u0011!a\u0001\u0017\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\r\t\u0017Q\u0001\u0005\bc2\t\t\u00111\u0001l\u0003!A\u0017m\u001d5D_\u0012,G#A6\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012!Y\u0001\u0007KF,\u0018\r\\:\u0015\u0007q\f\u0019\u0002C\u0004r\u001f\u0005\u0005\t\u0019A&\u0002\u001d\u0005\u001bGo\u001c:SK\u001a\u0014v.\u001e;fKB\u0011Q%E\n\u0006#\u0005m\u0011q\u0005\t\u0007\u0003;\t\u0019#\u000f\"\u000e\u0005\u0005}!bAA\u0011A\u00059!/\u001e8uS6,\u0017\u0002BA\u0013\u0003?\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\tI#a\f\u000e\u0005\u0005-\"bAA\u0017K\u0006\u0011\u0011n\\\u0005\u0004k\u0005-BCAA\f\u0003\u0015\t\u0007\u000f\u001d7z)\r\u0011\u0015q\u0007\u0005\u0006oQ\u0001\r!O\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ti$a\u0011\u0011\t}\ty$O\u0005\u0004\u0003\u0003\u0002#AB(qi&|g\u000e\u0003\u0005\u0002FU\t\t\u00111\u0001C\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u0017\u00022AYA'\u0013\r\tye\u0019\u0002\u0007\u001f\nTWm\u0019;")
public final class ActorRefRoutee
implements Routee,
Product,
Serializable {
    private final ActorRef ref;

    public static Option<ActorRef> unapply(ActorRefRoutee x$0) {
        return ActorRefRoutee$.MODULE$.unapply(x$0);
    }

    public static ActorRefRoutee apply(ActorRef ref) {
        return ActorRefRoutee$.MODULE$.apply(ref);
    }

    public static <A> Function1<ActorRef, A> andThen(Function1<ActorRefRoutee, A> g2) {
        return ActorRefRoutee$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, ActorRefRoutee> compose(Function1<A, ActorRef> g2) {
        return ActorRefRoutee$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef ref() {
        return this.ref;
    }

    @Override
    public void send(Object message, ActorRef sender) {
        this.ref().tell(message, sender);
    }

    public ActorRefRoutee copy(ActorRef ref) {
        return new ActorRefRoutee(ref);
    }

    public ActorRef copy$default$1() {
        return this.ref();
    }

    @Override
    public String productPrefix() {
        return "ActorRefRoutee";
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
                return this.ref();
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
        return x$1 instanceof ActorRefRoutee;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "ref";
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
        if (!(object instanceof ActorRefRoutee)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorRefRoutee actorRefRoutee = (ActorRefRoutee)x$1;
        ActorRef actorRef2 = this.ref();
        ActorRef actorRef3 = actorRefRoutee.ref();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public ActorRefRoutee(ActorRef ref) {
        this.ref = ref;
        Product.$init$(this);
    }
}

