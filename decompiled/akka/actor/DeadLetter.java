/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.AllDeadLetters;
import akka.actor.DeadLetter$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Md\u0001\u0002\u000f\u001e\u0005\nB\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005}!A!\t\u0001BK\u0002\u0013\u00051\t\u0003\u0005H\u0001\tE\t\u0015!\u0003E\u0011!A\u0005A!f\u0001\n\u0003\u0019\u0005\u0002C%\u0001\u0005#\u0005\u000b\u0011\u0002#\t\u000b)\u0003A\u0011A&\t\u000fA\u0003\u0011\u0011!C\u0001#\"9Q\u000bAI\u0001\n\u00031\u0006bB1\u0001#\u0003%\tA\u0019\u0005\bI\u0002\t\n\u0011\"\u0001c\u0011\u001d)\u0007!!A\u0005B\u0019Dqa\u001c\u0001\u0002\u0002\u0013\u0005\u0001\u000fC\u0004u\u0001\u0005\u0005I\u0011A;\t\u000fa\u0004\u0011\u0011!C!s\"I\u0011\u0011\u0001\u0001\u0002\u0002\u0013\u0005\u00111\u0001\u0005\n\u0003\u001b\u0001\u0011\u0011!C!\u0003\u001fA\u0011\"a\u0005\u0001\u0003\u0003%\t%!\u0006\t\u0013\u0005]\u0001!!A\u0005B\u0005e\u0001\"CA\u000e\u0001\u0005\u0005I\u0011IA\u000f\u000f%\ti#HA\u0001\u0012\u0003\tyC\u0002\u0005\u001d;\u0005\u0005\t\u0012AA\u0019\u0011\u0019Qe\u0003\"\u0001\u0002J!I\u0011q\u0003\f\u0002\u0002\u0013\u0015\u0013\u0011\u0004\u0005\n\u0003\u00172\u0012\u0011!CA\u0003\u001bB\u0011\"!\u0016\u0017\u0003\u0003%\t)a\u0016\t\u0013\u0005%d#!A\u0005\n\u0005-$A\u0003#fC\u0012dU\r\u001e;fe*\u0011adH\u0001\u0006C\u000e$xN\u001d\u0006\u0002A\u0005!\u0011m[6b\u0007\u0001\u0019R\u0001A\u0012*[A\u0002\"\u0001J\u0014\u000e\u0003\u0015R\u0011AJ\u0001\u0006g\u000e\fG.Y\u0005\u0003Q\u0015\u0012a!\u00118z%\u00164\u0007C\u0001\u0016,\u001b\u0005i\u0012B\u0001\u0017\u001e\u00059\tE\u000e\u001c#fC\u0012dU\r\u001e;feN\u0004\"\u0001\n\u0018\n\u0005=*#a\u0002)s_\u0012,8\r\u001e\t\u0003cer!AM\u001c\u000f\u0005M2T\"\u0001\u001b\u000b\u0005U\n\u0013A\u0002\u001fs_>$h(C\u0001'\u0013\tAT%A\u0004qC\u000e\\\u0017mZ3\n\u0005iZ$\u0001D*fe&\fG.\u001b>bE2,'B\u0001\u001d&\u0003\u001diWm]:bO\u0016,\u0012A\u0010\t\u0003I}J!\u0001Q\u0013\u0003\u0007\u0005s\u00170\u0001\u0005nKN\u001c\u0018mZ3!\u0003\u0019\u0019XM\u001c3feV\tA\t\u0005\u0002+\u000b&\u0011a)\b\u0002\t\u0003\u000e$xN\u001d*fM\u000691/\u001a8eKJ\u0004\u0013!\u0003:fG&\u0004\u0018.\u001a8u\u0003)\u0011XmY5qS\u0016tG\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\t1kej\u0014\t\u0003U\u0001AQ\u0001P\u0004A\u0002yBQAQ\u0004A\u0002\u0011CQ\u0001S\u0004A\u0002\u0011\u000bAaY8qsR!AJU*U\u0011\u001da\u0004\u0002%AA\u0002yBqA\u0011\u0005\u0011\u0002\u0003\u0007A\tC\u0004I\u0011A\u0005\t\u0019\u0001#\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tqK\u000b\u0002?1.\n\u0011\f\u0005\u0002[?6\t1L\u0003\u0002];\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003=\u0016\n!\"\u00198o_R\fG/[8o\u0013\t\u00017LA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'F\u0001dU\t!\u0005,\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u00059\u0007C\u00015n\u001b\u0005I'B\u00016l\u0003\u0011a\u0017M\\4\u000b\u00031\fAA[1wC&\u0011a.\u001b\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003E\u0004\"\u0001\n:\n\u0005M,#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HC\u0001 w\u0011\u001d9h\"!AA\u0002E\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u0001>\u0011\u0007mth(D\u0001}\u0015\tiX%\u0001\u0006d_2dWm\u0019;j_:L!a ?\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u000b\tY\u0001E\u0002%\u0003\u000fI1!!\u0003&\u0005\u001d\u0011un\u001c7fC:Dqa\u001e\t\u0002\u0002\u0003\u0007a(\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GcA4\u0002\u0012!9q/EA\u0001\u0002\u0004\t\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003E\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002O\u00061Q-];bYN$B!!\u0002\u0002 !9q\u000fFA\u0001\u0002\u0004q\u0004f\u0002\u0001\u0002$\u0005%\u00121\u0006\t\u0004I\u0005\u0015\u0012bAA\u0014K\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005QA)Z1e\u0019\u0016$H/\u001a:\u0011\u0005)22#\u0002\f\u00024\u0005}\u0002\u0003CA\u001b\u0003wqD\t\u0012'\u000e\u0005\u0005]\"bAA\u001dK\u00059!/\u001e8uS6,\u0017\u0002BA\u001f\u0003o\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c84!\u0011\t\t%a\u0012\u000e\u0005\u0005\r#bAA#W\u0006\u0011\u0011n\\\u0005\u0004u\u0005\rCCAA\u0018\u0003\u0015\t\u0007\u000f\u001d7z)\u001da\u0015qJA)\u0003'BQ\u0001P\rA\u0002yBQAQ\rA\u0002\u0011CQ\u0001S\rA\u0002\u0011\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002Z\u0005\u0015\u0004#\u0002\u0013\u0002\\\u0005}\u0013bAA/K\t1q\n\u001d;j_:\u0004b\u0001JA1}\u0011#\u0015bAA2K\t1A+\u001e9mKNB\u0001\"a\u001a\u001b\u0003\u0003\u0005\r\u0001T\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA7!\rA\u0017qN\u0005\u0004\u0003cJ'AB(cU\u0016\u001cG\u000f")
public final class DeadLetter
implements AllDeadLetters,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final Object message;
    private final ActorRef sender;
    private final ActorRef recipient;

    public static Option<Tuple3<Object, ActorRef, ActorRef>> unapply(DeadLetter x$0) {
        return DeadLetter$.MODULE$.unapply(x$0);
    }

    public static DeadLetter apply(Object message, ActorRef sender, ActorRef recipient) {
        return DeadLetter$.MODULE$.apply(message, sender, recipient);
    }

    public static Function1<Tuple3<Object, ActorRef, ActorRef>, DeadLetter> tupled() {
        return DeadLetter$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<ActorRef, Function1<ActorRef, DeadLetter>>> curried() {
        return DeadLetter$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Object message() {
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

    public DeadLetter copy(Object message, ActorRef sender, ActorRef recipient) {
        return new DeadLetter(message, sender, recipient);
    }

    public Object copy$default$1() {
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
        return "DeadLetter";
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
        return x$1 instanceof DeadLetter;
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
        if (!(object instanceof DeadLetter)) return false;
        boolean bl = true;
        if (!bl) return false;
        DeadLetter deadLetter = (DeadLetter)x$1;
        if (!BoxesRunTime.equals(this.message(), deadLetter.message())) return false;
        ActorRef actorRef2 = this.sender();
        ActorRef actorRef3 = deadLetter.sender();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        ActorRef actorRef4 = this.recipient();
        ActorRef actorRef5 = deadLetter.recipient();
        if (actorRef4 == null) {
            if (actorRef5 == null) return true;
            return false;
        } else {
            if (!((Object)actorRef4).equals(actorRef5)) return false;
            return true;
        }
    }

    public DeadLetter(Object message, ActorRef sender, ActorRef recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
        Product.$init$(this);
        Predef$.MODULE$.require(sender != null, (Function0<Object>)(Function0<String> & Serializable)() -> "DeadLetter sender may not be null");
        Predef$.MODULE$.require(recipient != null, (Function0<Object>)(Function0<String> & Serializable)() -> "DeadLetter recipient may not be null");
    }
}

