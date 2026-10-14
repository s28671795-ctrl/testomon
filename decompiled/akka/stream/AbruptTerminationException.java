/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorRef;
import akka.stream.AbruptTerminationException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u0005\rc\u0001B\u000b\u0017\u0005nA\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t\u0001\u0002\u0011\t\u0012)A\u0005w!)\u0011\t\u0001C\u0001\u0005\"9a\tAA\u0001\n\u00039\u0005bB%\u0001#\u0003%\tA\u0013\u0005\b+\u0002\t\t\u0011\"\u0011W\u0011\u001dy\u0006!!A\u0005\u0002\u0001Dq\u0001\u001a\u0001\u0002\u0002\u0013\u0005Q\rC\u0004l\u0001\u0005\u0005I\u0011\t7\t\u000fM\u0004\u0011\u0011!C\u0001i\"9\u0011\u0010AA\u0001\n\u0003R\bb\u0002?\u0001\u0003\u0003%\t% \u0005\b}\u0002\t\t\u0011\"\u0011\u0000\u000f%\t\u0019AFA\u0001\u0012\u0003\t)A\u0002\u0005\u0016-\u0005\u0005\t\u0012AA\u0004\u0011\u0019\tu\u0002\"\u0001\u0002 !I\u0011\u0011E\b\u0002\u0002\u0013\u0015\u00131\u0005\u0005\n\u0003Ky\u0011\u0011!CA\u0003OA\u0011\"a\u000b\u0010\u0003\u0003%\t)!\f\t\u0013\u0005er\"!A\u0005\n\u0005m\"AG!ceV\u0004H\u000fV3s[&t\u0017\r^5p]\u0016C8-\u001a9uS>t'BA\f\u0019\u0003\u0019\u0019HO]3b[*\t\u0011$\u0001\u0003bW.\f7\u0001A\n\u0006\u0001qQ#G\u000e\t\u0003;\u001dr!A\b\u0013\u000f\u0005}\u0011S\"\u0001\u0011\u000b\u0005\u0005R\u0012A\u0002\u001fs_>$h(C\u0001$\u0003\u0015\u00198-\u00197b\u0013\t)c%A\u0004qC\u000e\\\u0017mZ3\u000b\u0003\rJ!\u0001K\u0015\u0003!I+h\u000e^5nK\u0016C8-\u001a9uS>t'BA\u0013'!\tY\u0003'D\u0001-\u0015\tic&A\u0004d_:$(o\u001c7\u000b\u0005=2\u0013\u0001B;uS2L!!\r\u0017\u0003\u00199{7\u000b^1dWR\u0013\u0018mY3\u0011\u0005M\"T\"\u0001\u0014\n\u0005U2#a\u0002)s_\u0012,8\r\u001e\t\u0003;]J!\u0001O\u0015\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\u000b\u0005\u001cGo\u001c:\u0016\u0003m\u0002\"\u0001\u0010 \u000e\u0003uR!!\u000f\r\n\u0005}j$\u0001C!di>\u0014(+\u001a4\u0002\r\u0005\u001cGo\u001c:!\u0003\u0019a\u0014N\\5u}Q\u00111)\u0012\t\u0003\t\u0002i\u0011A\u0006\u0005\u0006s\r\u0001\raO\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002D\u0011\"9\u0011\b\u0002I\u0001\u0002\u0004Y\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0002\u0017*\u00121\bT\u0016\u0002\u001bB\u0011ajU\u0007\u0002\u001f*\u0011\u0001+U\u0001\nk:\u001c\u0007.Z2lK\u0012T!A\u0015\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002U\u001f\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u00059\u0006C\u0001-^\u001b\u0005I&B\u0001.\\\u0003\u0011a\u0017M\\4\u000b\u0003q\u000bAA[1wC&\u0011a,\u0017\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003\u0005\u0004\"a\r2\n\u0005\r4#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HC\u00014j!\t\u0019t-\u0003\u0002iM\t\u0019\u0011I\\=\t\u000f)D\u0011\u0011!a\u0001C\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012!\u001c\t\u0004]F4W\"A8\u000b\u0005A4\u0013AC2pY2,7\r^5p]&\u0011!o\u001c\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002vqB\u00111G^\u0005\u0003o\u001a\u0012qAQ8pY\u0016\fg\u000eC\u0004k\u0015\u0005\u0005\t\u0019\u00014\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0003/nDqA[\u0006\u0002\u0002\u0003\u0007\u0011-\u0001\u0005iCND7i\u001c3f)\u0005\t\u0017AB3rk\u0006d7\u000fF\u0002v\u0003\u0003AqA[\u0007\u0002\u0002\u0003\u0007a-\u0001\u000eBEJ,\b\u000f\u001e+fe6Lg.\u0019;j_:,\u0005pY3qi&|g\u000e\u0005\u0002E\u001fM)q\"!\u0003\u0002\u0016A1\u00111BA\tw\rk!!!\u0004\u000b\u0007\u0005=a%A\u0004sk:$\u0018.\\3\n\t\u0005M\u0011Q\u0002\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\f\u0003;i!!!\u0007\u000b\u0007\u0005m1,\u0001\u0002j_&\u0019\u0001(!\u0007\u0015\u0005\u0005\u0015\u0011\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003]\u000bQ!\u00199qYf$2aQA\u0015\u0011\u0015I$\u00031\u0001<\u0003\u001d)h.\u00199qYf$B!a\f\u00026A!1'!\r<\u0013\r\t\u0019D\n\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005]2#!AA\u0002\r\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\ti\u0004E\u0002Y\u0003\u007fI1!!\u0011Z\u0005\u0019y%M[3di\u0002")
public final class AbruptTerminationException
extends RuntimeException
implements NoStackTrace,
Product {
    private final ActorRef actor;

    public static Option<ActorRef> unapply(AbruptTerminationException x$0) {
        return AbruptTerminationException$.MODULE$.unapply(x$0);
    }

    public static AbruptTerminationException apply(ActorRef actor) {
        return AbruptTerminationException$.MODULE$.apply(actor);
    }

    public static <A> Function1<ActorRef, A> andThen(Function1<AbruptTerminationException, A> g2) {
        return AbruptTerminationException$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, AbruptTerminationException> compose(Function1<A, ActorRef> g2) {
        return AbruptTerminationException$.MODULE$.compose(g2);
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

    public ActorRef actor() {
        return this.actor;
    }

    public AbruptTerminationException copy(ActorRef actor) {
        return new AbruptTerminationException(actor);
    }

    public ActorRef copy$default$1() {
        return this.actor();
    }

    @Override
    public String productPrefix() {
        return "AbruptTerminationException";
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
                return this.actor();
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
        return x$1 instanceof AbruptTerminationException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "actor";
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
        if (!(object instanceof AbruptTerminationException)) return false;
        boolean bl = true;
        if (!bl) return false;
        AbruptTerminationException abruptTerminationException = (AbruptTerminationException)x$1;
        ActorRef actorRef2 = this.actor();
        ActorRef actorRef3 = abruptTerminationException.actor();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public AbruptTerminationException(ActorRef actor) {
        this.actor = actor;
        super(new StringBuilder(38).append("Processor actor [").append(actor).append("] terminated abruptly").toString());
        NoStackTrace.$init$(this);
        Product.$init$(this);
    }
}

