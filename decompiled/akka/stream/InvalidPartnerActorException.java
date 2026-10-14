/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorRef;
import akka.stream.InvalidPartnerActorException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Md\u0001B\u000e\u001d\u0005\u0006B\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\t\u0002\u0011\t\u0012)A\u0005}!AQ\t\u0001BK\u0002\u0013\u0005Q\b\u0003\u0005G\u0001\tE\t\u0015!\u0003?\u0011!9\u0005A!f\u0001\n\u0003A\u0005\u0002C)\u0001\u0005#\u0005\u000b\u0011B%\t\u000bI\u0003A\u0011A*\t\u000fe\u0003\u0011\u0011!C\u00015\"9a\fAI\u0001\n\u0003y\u0006b\u00026\u0001#\u0003%\ta\u0018\u0005\bW\u0002\t\n\u0011\"\u0001m\u0011\u001dq\u0007!!A\u0005B=DqA\u001d\u0001\u0002\u0002\u0013\u00051\u000fC\u0004x\u0001\u0005\u0005I\u0011\u0001=\t\u000fy\u0004\u0011\u0011!C!\u007f\"I\u0011Q\u0002\u0001\u0002\u0002\u0013\u0005\u0011q\u0002\u0005\n\u00033\u0001\u0011\u0011!C!\u00037A\u0011\"a\b\u0001\u0003\u0003%\t%!\t\t\u0013\u0005\r\u0002!!A\u0005B\u0005\u0015r!CA\u00159\u0005\u0005\t\u0012AA\u0016\r!YB$!A\t\u0002\u00055\u0002B\u0002*\u0016\t\u0003\t)\u0005C\u0005\u0002HU\t\t\u0011\"\u0012\u0002J!I\u00111J\u000b\u0002\u0002\u0013\u0005\u0015Q\n\u0005\n\u0003+*\u0012\u0011!CA\u0003/B\u0011\"!\u001b\u0016\u0003\u0003%I!a\u001b\u00039%sg/\u00197jIB\u000b'\u000f\u001e8fe\u0006\u001bGo\u001c:Fq\u000e,\u0007\u000f^5p]*\u0011QDH\u0001\u0007gR\u0014X-Y7\u000b\u0003}\tA!Y6lC\u000e\u00011\u0003\u0002\u0001#UA\u0002\"a\t\u0015\u000e\u0003\u0011R!!\n\u0014\u0002\t1\fgn\u001a\u0006\u0002O\u0005!!.\u0019<b\u0013\tICEA\u000bJY2,w-\u00197Ti\u0006$X-\u0012=dKB$\u0018n\u001c8\u0011\u0005-rS\"\u0001\u0017\u000b\u00035\nQa]2bY\u0006L!a\f\u0017\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0011'\u000f\b\u0003e]r!a\r\u001c\u000e\u0003QR!!\u000e\u0011\u0002\rq\u0012xn\u001c;?\u0013\u0005i\u0013B\u0001\u001d-\u0003\u001d\u0001\u0018mY6bO\u0016L!AO\u001e\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005ab\u0013aC3ya\u0016\u001cG/\u001a3SK\u001a,\u0012A\u0010\t\u0003\u007f\tk\u0011\u0001\u0011\u0006\u0003\u0003z\tQ!Y2u_JL!a\u0011!\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\fA\"\u001a=qK\u000e$X\r\u001a*fM\u0002\naaZ8u%\u00164\u0017aB4piJ+g\rI\u0001\u0004[N<W#A%\u0011\u0005)seBA&M!\t\u0019D&\u0003\u0002NY\u00051\u0001K]3eK\u001aL!a\u0014)\u0003\rM#(/\u001b8h\u0015\tiE&\u0001\u0003ng\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0003U-^C\u0006CA+\u0001\u001b\u0005a\u0002\"\u0002\u001f\b\u0001\u0004q\u0004\"B#\b\u0001\u0004q\u0004\"B$\b\u0001\u0004I\u0015\u0001B2paf$B\u0001V.];\"9A\b\u0003I\u0001\u0002\u0004q\u0004bB#\t!\u0003\u0005\rA\u0010\u0005\b\u000f\"\u0001\n\u00111\u0001J\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012\u0001\u0019\u0016\u0003}\u0005\\\u0013A\u0019\t\u0003G\"l\u0011\u0001\u001a\u0006\u0003K\u001a\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005\u001dd\u0013AC1o]>$\u0018\r^5p]&\u0011\u0011\u000e\u001a\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\u0005i'FA%b\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t\u0001\u000f\u0005\u0002$c&\u0011q\nJ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002iB\u00111&^\u0005\u0003m2\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"!\u001f?\u0011\u0005-R\u0018BA>-\u0005\r\te.\u001f\u0005\b{:\t\t\u00111\u0001u\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011\u0011\u0001\t\u0006\u0003\u0007\tI!_\u0007\u0003\u0003\u000bQ1!a\u0002-\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003\u0017\t)A\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\t\u0003/\u00012aKA\n\u0013\r\t)\u0002\f\u0002\b\u0005>|G.Z1o\u0011\u001di\b#!AA\u0002e\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019\u0001/!\b\t\u000fu\f\u0012\u0011!a\u0001i\u0006A\u0001.Y:i\u0007>$W\rF\u0001u\u0003\u0019)\u0017/^1mgR!\u0011\u0011CA\u0014\u0011\u001di8#!AA\u0002e\fA$\u00138wC2LG\rU1si:,'/Q2u_J,\u0005pY3qi&|g\u000e\u0005\u0002V+M)Q#a\f\u0002<AA\u0011\u0011GA\u001c}yJE+\u0004\u0002\u00024)\u0019\u0011Q\u0007\u0017\u0002\u000fI,h\u000e^5nK&!\u0011\u0011HA\u001a\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gn\r\t\u0005\u0003{\t\u0019%\u0004\u0002\u0002@)\u0019\u0011\u0011\t\u0014\u0002\u0005%|\u0017b\u0001\u001e\u0002@Q\u0011\u00111F\u0001\ti>\u001cFO]5oOR\t\u0001/A\u0003baBd\u0017\u0010F\u0004U\u0003\u001f\n\t&a\u0015\t\u000bqB\u0002\u0019\u0001 \t\u000b\u0015C\u0002\u0019\u0001 \t\u000b\u001dC\u0002\u0019A%\u0002\u000fUt\u0017\r\u001d9msR!\u0011\u0011LA3!\u0015Y\u00131LA0\u0013\r\ti\u0006\f\u0002\u0007\u001fB$\u0018n\u001c8\u0011\r-\n\tG\u0010 J\u0013\r\t\u0019\u0007\f\u0002\u0007)V\u0004H.Z\u001a\t\u0011\u0005\u001d\u0014$!AA\u0002Q\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\ti\u0007E\u0002$\u0003_J1!!\u001d%\u0005\u0019y%M[3di\u0002")
public final class InvalidPartnerActorException
extends IllegalStateException
implements Product {
    private final ActorRef expectedRef;
    private final ActorRef gotRef;
    private final String msg;

    public static Option<Tuple3<ActorRef, ActorRef, String>> unapply(InvalidPartnerActorException x$0) {
        return InvalidPartnerActorException$.MODULE$.unapply(x$0);
    }

    public static InvalidPartnerActorException apply(ActorRef expectedRef, ActorRef gotRef, String msg) {
        return InvalidPartnerActorException$.MODULE$.apply(expectedRef, gotRef, msg);
    }

    public static Function1<Tuple3<ActorRef, ActorRef, String>, InvalidPartnerActorException> tupled() {
        return InvalidPartnerActorException$.MODULE$.tupled();
    }

    public static Function1<ActorRef, Function1<ActorRef, Function1<String, InvalidPartnerActorException>>> curried() {
        return InvalidPartnerActorException$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef expectedRef() {
        return this.expectedRef;
    }

    public ActorRef gotRef() {
        return this.gotRef;
    }

    public String msg() {
        return this.msg;
    }

    public InvalidPartnerActorException copy(ActorRef expectedRef, ActorRef gotRef, String msg) {
        return new InvalidPartnerActorException(expectedRef, gotRef, msg);
    }

    public ActorRef copy$default$1() {
        return this.expectedRef();
    }

    public ActorRef copy$default$2() {
        return this.gotRef();
    }

    public String copy$default$3() {
        return this.msg();
    }

    @Override
    public String productPrefix() {
        return "InvalidPartnerActorException";
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
                return this.expectedRef();
            }
            case 1: {
                return this.gotRef();
            }
            case 2: {
                return this.msg();
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
        return x$1 instanceof InvalidPartnerActorException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "expectedRef";
            }
            case 1: {
                return "gotRef";
            }
            case 2: {
                return "msg";
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
        if (!(object instanceof InvalidPartnerActorException)) return false;
        boolean bl = true;
        if (!bl) return false;
        InvalidPartnerActorException invalidPartnerActorException = (InvalidPartnerActorException)x$1;
        ActorRef actorRef2 = this.expectedRef();
        ActorRef actorRef3 = invalidPartnerActorException.expectedRef();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        ActorRef actorRef4 = this.gotRef();
        ActorRef actorRef5 = invalidPartnerActorException.gotRef();
        if (actorRef4 == null) {
            if (actorRef5 != null) {
                return false;
            }
        } else if (!((Object)actorRef4).equals(actorRef5)) return false;
        String string = this.msg();
        String string2 = invalidPartnerActorException.msg();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public InvalidPartnerActorException(ActorRef expectedRef, ActorRef gotRef, String msg) {
        this.expectedRef = expectedRef;
        this.gotRef = gotRef;
        this.msg = msg;
        super(new StringBuilder(291).append(msg).append(" (expected: ").append(expectedRef).append(", got: ").append(gotRef).append("). ").append("This may happen due to 'double-materialization' on the other side of this stream ref. ").append("Do note that stream refs are one-shot references and have to be paired up in 1:1 pairs. ").append("Multi-cast such as broadcast etc can be implemented by sharing multiple new stream references. ").toString());
        Product.$init$(this);
    }
}

