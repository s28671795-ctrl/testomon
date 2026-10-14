/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.AkkaException;
import akka.actor.ActorRef;
import akka.actor.DeathPactException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u00055c\u0001B\u000b\u0017\u0005nA\u0001B\u000f\u0001\u0003\u0016\u0004%\ta\u000f\u0005\t\u0001\u0002\u0011\t\u0012)A\u0005y!1\u0011\t\u0001C\u00011\tCq!\u0012\u0001\u0002\u0002\u0013\u0005a\tC\u0004I\u0001E\u0005I\u0011A%\t\u000fQ\u0003\u0011\u0011!C!+\"9a\fAA\u0001\n\u0003y\u0006bB2\u0001\u0003\u0003%\t\u0001\u001a\u0005\bU\u0002\t\t\u0011\"\u0011l\u0011\u001d\u0011\b!!A\u0005\u0002MDq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0013\u0010C\u0004|\u0001\u0005\u0005I\u0011\t?\t\u000fu\u0004\u0011\u0011!C!}\u001eI\u0011Q\u0002\f\u0002\u0002#\u0005\u0011q\u0002\u0004\t+Y\t\t\u0011#\u0001\u0002\u0012!1\u0011i\u0004C\u0001\u0003SA\u0011\"a\u000b\u0010\u0003\u0003%)%!\f\t\u0013\u0005=r\"!A\u0005\u0002\u0006E\u0002\"CA\u001b\u001f\u0005\u0005I\u0011QA\u001c\u0011%\t\u0019eDA\u0001\n\u0013\t)E\u0001\nEK\u0006$\b\u000eU1di\u0016C8-\u001a9uS>t'BA\f\u0019\u0003\u0015\t7\r^8s\u0015\u0005I\u0012\u0001B1lW\u0006\u001c\u0001aE\u0003\u00019\u0001Rc\u0006\u0005\u0002\u001e=5\t\u0001$\u0003\u0002 1\ti\u0011i[6b\u000bb\u001cW\r\u001d;j_:\u0004\"!\t\u0015\u000e\u0003\tR!a\t\u0013\u0002\u000f\r|g\u000e\u001e:pY*\u0011QEJ\u0001\u0005kRLGNC\u0001(\u0003\u0015\u00198-\u00197b\u0013\tI#E\u0001\u0007O_N#\u0018mY6Ue\u0006\u001cW\r\u0005\u0002,Y5\ta%\u0003\u0002.M\t9\u0001K]8ek\u000e$\bCA\u00188\u001d\t\u0001TG\u0004\u00022i5\t!G\u0003\u000245\u00051AH]8pizJ\u0011aJ\u0005\u0003m\u0019\nq\u0001]1dW\u0006<W-\u0003\u00029s\ta1+\u001a:jC2L'0\u00192mK*\u0011aGJ\u0001\u0005I\u0016\fG-F\u0001=!\tid(D\u0001\u0017\u0013\tydC\u0001\u0005BGR|'OU3g\u0003\u0015!W-\u00193!\u0003\u0019a\u0014N\\5u}Q\u00111\t\u0012\t\u0003{\u0001AQAO\u0002A\u0002q\nAaY8qsR\u00111i\u0012\u0005\bu\u0011\u0001\n\u00111\u0001=\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012A\u0013\u0016\u0003y-[\u0013\u0001\u0014\t\u0003\u001bJk\u0011A\u0014\u0006\u0003\u001fB\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005E3\u0013AC1o]>$\u0018\r^5p]&\u00111K\u0014\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001W!\t9F,D\u0001Y\u0015\tI&,\u0001\u0003mC:<'\"A.\u0002\t)\fg/Y\u0005\u0003;b\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLX#\u00011\u0011\u0005-\n\u0017B\u00012'\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t)\u0007\u000e\u0005\u0002,M&\u0011qM\n\u0002\u0004\u0003:L\bbB5\t\u0003\u0003\u0005\r\u0001Y\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u00031\u00042!\u001c9f\u001b\u0005q'BA8'\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003c:\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0011Ao\u001e\t\u0003WUL!A\u001e\u0014\u0003\u000f\t{w\u000e\\3b]\"9\u0011NCA\u0001\u0002\u0004)\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"A\u0016>\t\u000f%\\\u0011\u0011!a\u0001A\u0006A\u0001.Y:i\u0007>$W\rF\u0001a\u0003\u0019)\u0017/^1mgR\u0011Ao \u0005\bS6\t\t\u00111\u0001fQ\u001d\u0001\u00111AA\u0005\u0003\u0017\u00012aKA\u0003\u0013\r\t9A\n\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\u0013\t\u0016\fG\u000f\u001b)bGR,\u0005pY3qi&|g\u000e\u0005\u0002>\u001fM)q\"a\u0005\u0002 A1\u0011QCA\u000ey\rk!!a\u0006\u000b\u0007\u0005ea%A\u0004sk:$\u0018.\\3\n\t\u0005u\u0011q\u0003\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\u0011\u0003Oi!!a\t\u000b\u0007\u0005\u0015\",\u0001\u0002j_&\u0019\u0001(a\t\u0015\u0005\u0005=\u0011\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003Y\u000bQ!\u00199qYf$2aQA\u001a\u0011\u0015Q$\u00031\u0001=\u0003\u001d)h.\u00199qYf$B!!\u000f\u0002@A!1&a\u000f=\u0013\r\tiD\n\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005\u00053#!AA\u0002\r\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t9\u0005E\u0002X\u0003\u0013J1!a\u0013Y\u0005\u0019y%M[3di\u0002")
public final class DeathPactException
extends AkkaException
implements NoStackTrace,
Product {
    private static final long serialVersionUID = 1L;
    private final ActorRef dead;

    public static Option<ActorRef> unapply(DeathPactException x$0) {
        return DeathPactException$.MODULE$.unapply(x$0);
    }

    public static DeathPactException apply(ActorRef dead) {
        return DeathPactException$.MODULE$.apply(dead);
    }

    public static <A> Function1<ActorRef, A> andThen(Function1<DeathPactException, A> g2) {
        return DeathPactException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, DeathPactException> compose(Function1<A, ActorRef> g2) {
        return DeathPactException$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    public ActorRef dead() {
        return this.dead;
    }

    public DeathPactException copy(ActorRef dead) {
        return new DeathPactException(dead);
    }

    public ActorRef copy$default$1() {
        return this.dead();
    }

    @Override
    public String productPrefix() {
        return "DeathPactException";
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
                return this.dead();
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
        return x$1 instanceof DeathPactException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "dead";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof DeathPactException)) return false;
        boolean bl = true;
        if (!bl) return false;
        DeathPactException deathPactException = (DeathPactException)x$1;
        ActorRef actorRef2 = this.dead();
        ActorRef actorRef3 = deathPactException.dead();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public DeathPactException(ActorRef dead) {
        this.dead = dead;
        super(new StringBuilder(29).append("Monitored actor [").append(dead).append("] terminated").toString());
        NoStackTrace.$init$(this);
        Product.$init$(this);
    }
}

