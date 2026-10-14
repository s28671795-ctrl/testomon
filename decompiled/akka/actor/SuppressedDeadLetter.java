/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.AllDeadLetters;
import akka.actor.DeadLetterSuppression;
import akka.actor.SuppressedDeadLetter$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005ed\u0001\u0002\u000f\u001e\u0005\nB\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005}!A!\t\u0001BK\u0002\u0013\u00051\t\u0003\u0005H\u0001\tE\t\u0015!\u0003E\u0011!A\u0005A!f\u0001\n\u0003\u0019\u0005\u0002C%\u0001\u0005#\u0005\u000b\u0011\u0002#\t\u000b)\u0003A\u0011A&\t\u000fA\u0003\u0011\u0011!C\u0001#\"9Q\u000bAI\u0001\n\u00031\u0006bB1\u0001#\u0003%\tA\u0019\u0005\bI\u0002\t\n\u0011\"\u0001c\u0011\u001d)\u0007!!A\u0005B\u0019Dqa\u001c\u0001\u0002\u0002\u0013\u0005\u0001\u000fC\u0004u\u0001\u0005\u0005I\u0011A;\t\u000fm\u0004\u0011\u0011!C!y\"I\u0011q\u0001\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0002\u0005\n\u0003'\u0001\u0011\u0011!C!\u0003+A\u0011\"!\u0007\u0001\u0003\u0003%\t%a\u0007\t\u0013\u0005u\u0001!!A\u0005B\u0005}\u0001\"CA\u0011\u0001\u0005\u0005I\u0011IA\u0012\u000f%\t\u0019$HA\u0001\u0012\u0003\t)D\u0002\u0005\u001d;\u0005\u0005\t\u0012AA\u001c\u0011\u0019Qe\u0003\"\u0001\u0002P!I\u0011Q\u0004\f\u0002\u0002\u0013\u0015\u0013q\u0004\u0005\n\u0003#2\u0012\u0011!CA\u0003'B\u0011\"a\u0017\u0017\u0003\u0003%\t)!\u0018\t\u0013\u0005=d#!A\u0005\n\u0005E$\u0001F*vaB\u0014Xm]:fI\u0012+\u0017\r\u001a'fiR,'O\u0003\u0002\u001f?\u0005)\u0011m\u0019;pe*\t\u0001%\u0001\u0003bW.\f7\u0001A\n\u0006\u0001\rJS\u0006\r\t\u0003I\u001dj\u0011!\n\u0006\u0002M\u0005)1oY1mC&\u0011\u0001&\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005)ZS\"A\u000f\n\u00051j\"AD!mY\u0012+\u0017\r\u001a'fiR,'o\u001d\t\u0003I9J!aL\u0013\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0011'\u000f\b\u0003e]r!a\r\u001c\u000e\u0003QR!!N\u0011\u0002\rq\u0012xn\u001c;?\u0013\u00051\u0013B\u0001\u001d&\u0003\u001d\u0001\u0018mY6bO\u0016L!AO\u001e\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005a*\u0013aB7fgN\fw-Z\u000b\u0002}A\u0011!fP\u0005\u0003\u0001v\u0011Q\u0003R3bI2+G\u000f^3s'V\u0004\bO]3tg&|g.\u0001\u0005nKN\u001c\u0018mZ3!\u0003\u0019\u0019XM\u001c3feV\tA\t\u0005\u0002+\u000b&\u0011a)\b\u0002\t\u0003\u000e$xN\u001d*fM\u000691/\u001a8eKJ\u0004\u0013!\u0003:fG&\u0004\u0018.\u001a8u\u0003)\u0011XmY5qS\u0016tG\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\t1kej\u0014\t\u0003U\u0001AQ\u0001P\u0004A\u0002yBQAQ\u0004A\u0002\u0011CQ\u0001S\u0004A\u0002\u0011\u000bAaY8qsR!AJU*U\u0011\u001da\u0004\u0002%AA\u0002yBqA\u0011\u0005\u0011\u0002\u0003\u0007A\tC\u0004I\u0011A\u0005\t\u0019\u0001#\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tqK\u000b\u0002?1.\n\u0011\f\u0005\u0002[?6\t1L\u0003\u0002];\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003=\u0016\n!\"\u00198o_R\fG/[8o\u0013\t\u00017LA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'F\u0001dU\t!\u0005,\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u00059\u0007C\u00015n\u001b\u0005I'B\u00016l\u0003\u0011a\u0017M\\4\u000b\u00031\fAA[1wC&\u0011a.\u001b\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003E\u0004\"\u0001\n:\n\u0005M,#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HC\u0001<z!\t!s/\u0003\u0002yK\t\u0019\u0011I\\=\t\u000fit\u0011\u0011!a\u0001c\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012! \t\u0005}\u0006\ra/D\u0001\u0000\u0015\r\t\t!J\u0001\u000bG>dG.Z2uS>t\u0017bAA\u0003\u007f\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\tY!!\u0005\u0011\u0007\u0011\ni!C\u0002\u0002\u0010\u0015\u0012qAQ8pY\u0016\fg\u000eC\u0004{!\u0005\u0005\t\u0019\u0001<\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004O\u0006]\u0001b\u0002>\u0012\u0003\u0003\u0005\r!]\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0011/\u0001\u0005u_N#(/\u001b8h)\u00059\u0017AB3rk\u0006d7\u000f\u0006\u0003\u0002\f\u0005\u0015\u0002b\u0002>\u0015\u0003\u0003\u0005\rA\u001e\u0015\b\u0001\u0005%\u0012qFA\u0019!\r!\u00131F\u0005\u0004\u0003[)#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011\u0001F*vaB\u0014Xm]:fI\u0012+\u0017\r\u001a'fiR,'\u000f\u0005\u0002+-M)a#!\u000f\u0002FAA\u00111HA!}\u0011#E*\u0004\u0002\u0002>)\u0019\u0011qH\u0013\u0002\u000fI,h\u000e^5nK&!\u00111IA\u001f\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gn\r\t\u0005\u0003\u000f\ni%\u0004\u0002\u0002J)\u0019\u00111J6\u0002\u0005%|\u0017b\u0001\u001e\u0002JQ\u0011\u0011QG\u0001\u0006CB\u0004H.\u001f\u000b\b\u0019\u0006U\u0013qKA-\u0011\u0015a\u0014\u00041\u0001?\u0011\u0015\u0011\u0015\u00041\u0001E\u0011\u0015A\u0015\u00041\u0001E\u0003\u001d)h.\u00199qYf$B!a\u0018\u0002lA)A%!\u0019\u0002f%\u0019\u00111M\u0013\u0003\r=\u0003H/[8o!\u0019!\u0013q\r E\t&\u0019\u0011\u0011N\u0013\u0003\rQ+\b\u000f\\34\u0011!\tiGGA\u0001\u0002\u0004a\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\u000f\t\u0004Q\u0006U\u0014bAA<S\n1qJ\u00196fGR\u0004")
public final class SuppressedDeadLetter
implements AllDeadLetters,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final DeadLetterSuppression message;
    private final ActorRef sender;
    private final ActorRef recipient;

    public static Option<Tuple3<DeadLetterSuppression, ActorRef, ActorRef>> unapply(SuppressedDeadLetter x$0) {
        return SuppressedDeadLetter$.MODULE$.unapply(x$0);
    }

    public static SuppressedDeadLetter apply(DeadLetterSuppression message, ActorRef sender, ActorRef recipient) {
        return SuppressedDeadLetter$.MODULE$.apply(message, sender, recipient);
    }

    public static Function1<Tuple3<DeadLetterSuppression, ActorRef, ActorRef>, SuppressedDeadLetter> tupled() {
        return SuppressedDeadLetter$.MODULE$.tupled();
    }

    public static Function1<DeadLetterSuppression, Function1<ActorRef, Function1<ActorRef, SuppressedDeadLetter>>> curried() {
        return SuppressedDeadLetter$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public DeadLetterSuppression message() {
        return this.message;
    }

    @Override
    public ActorRef sender() {
        return this.sender;
    }

    @Override
    public ActorRef recipient() {
        return this.recipient;
    }

    public SuppressedDeadLetter copy(DeadLetterSuppression message, ActorRef sender, ActorRef recipient) {
        return new SuppressedDeadLetter(message, sender, recipient);
    }

    public DeadLetterSuppression copy$default$1() {
        return this.message();
    }

    public ActorRef copy$default$2() {
        return this.sender();
    }

    public ActorRef copy$default$3() {
        return this.recipient();
    }

    @Override
    public String productPrefix() {
        return "SuppressedDeadLetter";
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
                return this.message();
            }
            case 1: {
                return this.sender();
            }
            case 2: {
                return this.recipient();
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
        return x$1 instanceof SuppressedDeadLetter;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "message";
            }
            case 1: {
                return "sender";
            }
            case 2: {
                return "recipient";
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
        if (!(object instanceof SuppressedDeadLetter)) return false;
        boolean bl = true;
        if (!bl) return false;
        SuppressedDeadLetter suppressedDeadLetter = (SuppressedDeadLetter)x$1;
        DeadLetterSuppression deadLetterSuppression = this.message();
        DeadLetterSuppression deadLetterSuppression2 = suppressedDeadLetter.message();
        if (deadLetterSuppression == null) {
            if (deadLetterSuppression2 != null) {
                return false;
            }
        } else if (!deadLetterSuppression.equals(deadLetterSuppression2)) return false;
        ActorRef actorRef2 = this.sender();
        ActorRef actorRef3 = suppressedDeadLetter.sender();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        ActorRef actorRef4 = this.recipient();
        ActorRef actorRef5 = suppressedDeadLetter.recipient();
        if (actorRef4 == null) {
            if (actorRef5 == null) return true;
            return false;
        } else {
            if (!((Object)actorRef4).equals(actorRef5)) return false;
            return true;
        }
    }

    public SuppressedDeadLetter(DeadLetterSuppression message, ActorRef sender, ActorRef recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
        Product.$init$(this);
        Predef$.MODULE$.require(sender != null, (Function0<Object>)(Function0<String> & Serializable)() -> "DeadLetter sender may not be null");
        Predef$.MODULE$.require(recipient != null, (Function0<Object>)(Function0<String> & Serializable)() -> "DeadLetter recipient may not be null");
    }
}

