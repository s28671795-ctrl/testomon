/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorInitializationException;
import akka.actor.ActorRef;
import akka.actor.PostRestartException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=d\u0001B\u000e\u001d\u0005\u0006B\u0001\"\b\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\ty\u0001\u0011\t\u0012)A\u0005s!AQ\b\u0001BK\u0002\u0013\u0005a\b\u0003\u0005C\u0001\tE\t\u0015!\u0003@\u0011!\u0019\u0005A!f\u0001\n\u0003q\u0004\u0002\u0003#\u0001\u0005#\u0005\u000b\u0011B \t\r\u0015\u0003A\u0011\u0001\u0010G\u0011\u001dY\u0005!!A\u0005\u00021Cq\u0001\u0015\u0001\u0012\u0002\u0013\u0005\u0011\u000bC\u0004]\u0001E\u0005I\u0011A/\t\u000f}\u0003\u0011\u0013!C\u0001;\"9\u0001\rAA\u0001\n\u0003\n\u0007b\u00026\u0001\u0003\u0003%\ta\u001b\u0005\b_\u0002\t\t\u0011\"\u0001q\u0011\u001d1\b!!A\u0005B]DqA \u0001\u0002\u0002\u0013\u0005q\u0010C\u0005\u0002\n\u0001\t\t\u0011\"\u0011\u0002\f!I\u0011q\u0002\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0003\u0005\n\u0003'\u0001\u0011\u0011!C!\u0003+9\u0011\"!\n\u001d\u0003\u0003E\t!a\n\u0007\u0011ma\u0012\u0011!E\u0001\u0003SAa!R\u000b\u0005\u0002\u0005\u0005\u0003\"CA\"+\u0005\u0005IQIA#\u0011%\t9%FA\u0001\n\u0003\u000bI\u0005C\u0005\u0002RU\t\t\u0011\"!\u0002T!I\u0011QM\u000b\u0002\u0002\u0013%\u0011q\r\u0002\u0015!>\u001cHOU3ti\u0006\u0014H/\u0012=dKB$\u0018n\u001c8\u000b\u0005uq\u0012!B1di>\u0014(\"A\u0010\u0002\t\u0005\\7.Y\u0002\u0001'\u0011\u0001!E\n\u0017\u0011\u0005\r\"S\"\u0001\u000f\n\u0005\u0015b\"\u0001H!di>\u0014\u0018J\\5uS\u0006d\u0017N_1uS>tW\t_2faRLwN\u001c\t\u0003O)j\u0011\u0001\u000b\u0006\u0002S\u0005)1oY1mC&\u00111\u0006\u000b\u0002\b!J|G-^2u!\tiSG\u0004\u0002/g9\u0011qFM\u0007\u0002a)\u0011\u0011\u0007I\u0001\u0007yI|w\u000e\u001e \n\u0003%J!\u0001\u000e\u0015\u0002\u000fA\f7m[1hK&\u0011ag\u000e\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003i!*\u0012!\u000f\t\u0003GiJ!a\u000f\u000f\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\fa!Y2u_J\u0004\u0013!B2bkN,W#A \u0011\u00055\u0002\u0015BA!8\u0005%!\u0006N]8xC\ndW-\u0001\u0004dCV\u001cX\rI\u0001\u000e_JLw-\u001b8bY\u000e\u000bWo]3\u0002\u001d=\u0014\u0018nZ5oC2\u001c\u0015-^:fA\u00051A(\u001b8jiz\"Ba\u0012%J\u0015B\u00111\u0005\u0001\u0005\u0006;\u001d\u0001\r!\u000f\u0005\u0006{\u001d\u0001\ra\u0010\u0005\u0006\u0007\u001e\u0001\raP\u0001\u0005G>\u0004\u0018\u0010\u0006\u0003H\u001b:{\u0005bB\u000f\t!\u0003\u0005\r!\u000f\u0005\b{!\u0001\n\u00111\u0001@\u0011\u001d\u0019\u0005\u0002%AA\u0002}\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001SU\tI4kK\u0001U!\t)&,D\u0001W\u0015\t9\u0006,A\u0005v]\u000eDWmY6fI*\u0011\u0011\fK\u0001\u000bC:tw\u000e^1uS>t\u0017BA.W\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0005q&FA T\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u00012\u0011\u0005\rDW\"\u00013\u000b\u0005\u00154\u0017\u0001\u00027b]\u001eT\u0011aZ\u0001\u0005U\u00064\u0018-\u0003\u0002jI\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012\u0001\u001c\t\u0003O5L!A\u001c\u0015\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005E$\bCA\u0014s\u0013\t\u0019\bFA\u0002B]fDq!\u001e\b\u0002\u0002\u0003\u0007A.A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002qB\u0019\u0011\u0010`9\u000e\u0003iT!a\u001f\u0015\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002~u\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\t\t!a\u0002\u0011\u0007\u001d\n\u0019!C\u0002\u0002\u0006!\u0012qAQ8pY\u0016\fg\u000eC\u0004v!\u0005\u0005\t\u0019A9\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004E\u00065\u0001bB;\u0012\u0003\u0003\u0005\r\u0001\\\u0001\tQ\u0006\u001c\bnQ8eKR\tA.\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u0003\t9\u0002C\u0004v'\u0005\u0005\t\u0019A9)\u000f\u0001\tY\"!\t\u0002$A\u0019q%!\b\n\u0007\u0005}\u0001F\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!\u0001\u000bQ_N$(+Z:uCJ$X\t_2faRLwN\u001c\t\u0003GU\u0019R!FA\u0016\u0003o\u0001\u0002\"!\f\u00024ezthR\u0007\u0003\u0003_Q1!!\r)\u0003\u001d\u0011XO\u001c;j[\u0016LA!!\u000e\u00020\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001a\u0011\t\u0005e\u0012qH\u0007\u0003\u0003wQ1!!\u0010g\u0003\tIw.C\u00027\u0003w!\"!a\n\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012AY\u0001\u0006CB\u0004H.\u001f\u000b\b\u000f\u0006-\u0013QJA(\u0011\u0015i\u0002\u00041\u0001:\u0011\u0015i\u0004\u00041\u0001@\u0011\u0015\u0019\u0005\u00041\u0001@\u0003\u001d)h.\u00199qYf$B!!\u0016\u0002bA)q%a\u0016\u0002\\%\u0019\u0011\u0011\f\u0015\u0003\r=\u0003H/[8o!\u00199\u0013QL\u001d@\u007f%\u0019\u0011q\f\u0015\u0003\rQ+\b\u000f\\34\u0011!\t\u0019'GA\u0001\u0002\u00049\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u000e\t\u0004G\u0006-\u0014bAA7I\n1qJ\u00196fGR\u0004")
public final class PostRestartException
extends ActorInitializationException
implements Product {
    private static final long serialVersionUID = 1L;
    private final ActorRef actor;
    private final Throwable cause;
    private final Throwable originalCause;

    public static Option<Tuple3<ActorRef, Throwable, Throwable>> unapply(PostRestartException x$0) {
        return PostRestartException$.MODULE$.unapply(x$0);
    }

    public static PostRestartException apply(ActorRef actor, Throwable cause, Throwable originalCause) {
        return PostRestartException$.MODULE$.apply(actor, cause, originalCause);
    }

    public static Function1<Tuple3<ActorRef, Throwable, Throwable>, PostRestartException> tupled() {
        return PostRestartException$.MODULE$.tupled();
    }

    public static Function1<ActorRef, Function1<Throwable, Function1<Throwable, PostRestartException>>> curried() {
        return PostRestartException$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef actor() {
        return this.actor;
    }

    public Throwable cause() {
        return this.cause;
    }

    public Throwable originalCause() {
        return this.originalCause;
    }

    public PostRestartException copy(ActorRef actor, Throwable cause, Throwable originalCause) {
        return new PostRestartException(actor, cause, originalCause);
    }

    public ActorRef copy$default$1() {
        return this.actor();
    }

    public Throwable copy$default$2() {
        return this.cause();
    }

    public Throwable copy$default$3() {
        return this.originalCause();
    }

    @Override
    public String productPrefix() {
        return "PostRestartException";
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
                return this.actor();
            }
            case 1: {
                return this.cause();
            }
            case 2: {
                return this.originalCause();
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
        return x$1 instanceof PostRestartException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "actor";
            }
            case 1: {
                return "cause";
            }
            case 2: {
                return "originalCause";
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
        if (!(object instanceof PostRestartException)) return false;
        boolean bl = true;
        if (!bl) return false;
        PostRestartException postRestartException = (PostRestartException)x$1;
        ActorRef actorRef2 = this.actor();
        ActorRef actorRef3 = postRestartException.actor();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        Throwable throwable = this.cause();
        Throwable throwable2 = postRestartException.cause();
        if (throwable == null) {
            if (throwable2 != null) {
                return false;
            }
        } else if (!throwable.equals(throwable2)) return false;
        Throwable throwable3 = this.originalCause();
        Throwable throwable4 = postRestartException.originalCause();
        if (throwable3 == null) {
            if (throwable4 == null) return true;
            return false;
        } else {
            if (!throwable3.equals(throwable4)) return false;
            return true;
        }
    }

    public PostRestartException(ActorRef actor, Throwable cause, Throwable originalCause) {
        this.actor = actor;
        this.cause = cause;
        this.originalCause = originalCause;
        super(actor, new StringBuilder(25).append("exception post restart (").append(originalCause == null ? "null" : originalCause.getClass()).append(")").toString(), cause);
        Product.$init$(this);
    }
}

