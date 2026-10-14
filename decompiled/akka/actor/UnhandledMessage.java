/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.AllDeadLetters;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.UnhandledMessage$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005%f\u0001B\u0010!\u0005\u0016B\u0001\"\u0012\u0001\u0003\u0016\u0004%\tA\u0012\u0005\t\u0015\u0002\u0011\t\u0012)A\u0005\u000f\")!\u000b\u0001C\u0001'\"Aa\u000b\u0001BK\u0002\u0013\u0005q\u000b\u0003\u0005\\\u0001\tE\t\u0015!\u0003Y\u0011\u0015i\u0006\u0001\"\u0001_\u0011!\u0001\u0007A!f\u0001\n\u00039\u0006\u0002C1\u0001\u0005#\u0005\u000b\u0011\u0002-\t\u000b\r\u0004A\u0011\u00010\t\u000b\u0015\u0004A\u0011\u00014\t\u000f-\u0004\u0011\u0011!C\u0001Y\"9\u0001\u000fAI\u0001\n\u0003\t\bb\u0002?\u0001#\u0003%\t! \u0005\b\u007f\u0002\t\n\u0011\"\u0001~\u0011%\t\t\u0001AA\u0001\n\u0003\n\u0019\u0001C\u0005\u0002\u0016\u0001\t\t\u0011\"\u0001\u0002\u0018!I\u0011q\u0004\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0005\u0005\n\u0003O\u0001\u0011\u0011!C!\u0003SA\u0011\"a\u000e\u0001\u0003\u0003%\t!!\u000f\t\u0013\u0005\r\u0003!!A\u0005B\u0005\u0015\u0003\"CA%\u0001\u0005\u0005I\u0011IA&\u0011%\ti\u0005AA\u0001\n\u0003\ny\u0005C\u0005\u0002R\u0001\t\t\u0011\"\u0011\u0002T\u001dI\u00111\r\u0011\u0002\u0002#\u0005\u0011Q\r\u0004\t?\u0001\n\t\u0011#\u0001\u0002h!1Q-\u0007C\u0001\u0003\u007fB\u0011\"!\u0014\u001a\u0003\u0003%)%a\u0014\t\u0013\u0005\u0005\u0015$!A\u0005\u0002\u0006\r\u0005\"CAF3\u0005\u0005I\u0011QAG\u0011%\ty*GA\u0001\n\u0013\t\tK\u0001\tV]\"\fg\u000e\u001a7fI6+7o]1hK*\u0011\u0011EI\u0001\u0006C\u000e$xN\u001d\u0006\u0002G\u0005!\u0011m[6b\u0007\u0001\u0019r\u0001\u0001\u0014-aM2\u0014\b\u0005\u0002(U5\t\u0001FC\u0001*\u0003\u0015\u00198-\u00197b\u0013\tY\u0003F\u0001\u0004B]f\u0014VM\u001a\t\u0003[9j\u0011\u0001I\u0005\u0003_\u0001\u0012\u0011ET8TKJL\u0017\r\\5{CRLwN\u001c,fe&4\u0017nY1uS>tg*Z3eK\u0012\u0004\"!L\u0019\n\u0005I\u0002#AD,sCB\u0004X\rZ'fgN\fw-\u001a\t\u0003[QJ!!\u000e\u0011\u0003\u001d\u0005cG\u000eR3bI2+G\u000f^3sgB\u0011qeN\u0005\u0003q!\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002;\u0005:\u00111\b\u0011\b\u0003y}j\u0011!\u0010\u0006\u0003}\u0011\na\u0001\u0010:p_Rt\u0014\"A\u0015\n\u0005\u0005C\u0013a\u00029bG.\fw-Z\u0005\u0003\u0007\u0012\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!!\u0011\u0015\u0002\u000f5,7o]1hKV\tq\t\u0005\u0002(\u0011&\u0011\u0011\n\u000b\u0002\u0004\u0003:L\u0018\u0001C7fgN\fw-\u001a\u0011)\u0005\ta\u0005CA'Q\u001b\u0005q%BA()\u0003\u0015\u0011W-\u00198t\u0013\t\tfJ\u0001\u0007CK\u0006t\u0007K]8qKJ$\u00180\u0001\u0006hKRlUm]:bO\u0016$\u0012a\u0012\u0015\u0003\u0007U[\u0001!\u0001\u0004tK:$WM]\u000b\u00021B\u0011Q&W\u0005\u00035\u0002\u0012\u0001\"Q2u_J\u0014VMZ\u0001\bg\u0016tG-\u001a:!Q\t)A*A\u0005hKR\u001cVM\u001c3feR\t\u0001\f\u000b\u0002\u0007+\u0006I!/Z2ja&,g\u000e^\u0001\u000be\u0016\u001c\u0017\u000e]5f]R\u0004\u0003F\u0001\u0005M\u000319W\r\u001e*fG&\u0004\u0018.\u001a8uQ\tIQ+\u0001\u0004=S:LGO\u0010\u000b\u0005O\"L'\u000e\u0005\u0002.\u0001!)QI\u0003a\u0001\u000f\")aK\u0003a\u00011\")\u0001M\u0003a\u00011\u0006!1m\u001c9z)\u00119WN\\8\t\u000f\u0015[\u0001\u0013!a\u0001\u000f\"9ak\u0003I\u0001\u0002\u0004A\u0006b\u00021\f!\u0003\u0005\r\u0001W\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005\u0011(FA$tW\u0005!\bCA;{\u001b\u00051(BA<y\u0003%)hn\u00195fG.,GM\u0003\u0002zQ\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005m4(!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T#\u0001@+\u0005a\u001b\u0018AD2paf$C-\u001a4bk2$HeM\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\u0015\u0001\u0003BA\u0004\u0003#i!!!\u0003\u000b\t\u0005-\u0011QB\u0001\u0005Y\u0006twM\u0003\u0002\u0002\u0010\u0005!!.\u0019<b\u0013\u0011\t\u0019\"!\u0003\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\tI\u0002E\u0002(\u00037I1!!\b)\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r9\u00151\u0005\u0005\n\u0003K\t\u0012\u0011!a\u0001\u00033\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u0016!\u0015\ti#a\rH\u001b\t\tyCC\u0002\u00022!\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\t)$a\f\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003w\t\t\u0005E\u0002(\u0003{I1!a\u0010)\u0005\u001d\u0011un\u001c7fC:D\u0001\"!\n\u0014\u0003\u0003\u0005\raR\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002\u0006\u0005\u001d\u0003\"CA\u0013)\u0005\u0005\t\u0019AA\r\u0003!A\u0017m\u001d5D_\u0012,GCAA\r\u0003!!xn\u0015;sS:<GCAA\u0003\u0003\u0019)\u0017/^1mgR!\u00111HA+\u0011!\t)cFA\u0001\u0002\u00049\u0005f\u0002\u0001\u0002Z\u0005}\u0013\u0011\r\t\u0004O\u0005m\u0013bAA/Q\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005\u0001RK\u001c5b]\u0012dW\rZ'fgN\fw-\u001a\t\u0003[e\u0019R!GA5\u0003k\u0002\u0002\"a\u001b\u0002r\u001dC\u0006lZ\u0007\u0003\u0003[R1!a\u001c)\u0003\u001d\u0011XO\u001c;j[\u0016LA!a\u001d\u0002n\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001a\u0011\t\u0005]\u0014QP\u0007\u0003\u0003sRA!a\u001f\u0002\u000e\u0005\u0011\u0011n\\\u0005\u0004\u0007\u0006eDCAA3\u0003\u0015\t\u0007\u000f\u001d7z)\u001d9\u0017QQAD\u0003\u0013CQ!\u0012\u000fA\u0002\u001dCQA\u0016\u000fA\u0002aCQ\u0001\u0019\u000fA\u0002a\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002\u0010\u0006m\u0005#B\u0014\u0002\u0012\u0006U\u0015bAAJQ\t1q\n\u001d;j_:\u0004baJAL\u000fbC\u0016bAAMQ\t1A+\u001e9mKNB\u0001\"!(\u001e\u0003\u0003\u0005\raZ\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAR!\u0011\t9!!*\n\t\u0005\u001d\u0016\u0011\u0002\u0002\u0007\u001f\nTWm\u0019;")
public final class UnhandledMessage
implements NoSerializationVerificationNeeded,
AllDeadLetters,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final Object message;
    private final ActorRef sender;
    private final ActorRef recipient;

    public static Option<Tuple3<Object, ActorRef, ActorRef>> unapply(UnhandledMessage x$0) {
        return UnhandledMessage$.MODULE$.unapply(x$0);
    }

    public static UnhandledMessage apply(Object message, ActorRef sender, ActorRef recipient) {
        return UnhandledMessage$.MODULE$.apply(message, sender, recipient);
    }

    public static Function1<Tuple3<Object, ActorRef, ActorRef>, UnhandledMessage> tupled() {
        return UnhandledMessage$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<ActorRef, Function1<ActorRef, UnhandledMessage>>> curried() {
        return UnhandledMessage$.MODULE$.curried();
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

    public UnhandledMessage copy(Object message, ActorRef sender, ActorRef recipient) {
        return new UnhandledMessage(message, sender, recipient);
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

    public Object getMessage() {
        return this.message();
    }

    public ActorRef getRecipient() {
        return this.recipient();
    }

    public ActorRef getSender() {
        return this.sender();
    }

    @Override
    public String productPrefix() {
        return "UnhandledMessage";
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
        return x$1 instanceof UnhandledMessage;
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
        if (!(object instanceof UnhandledMessage)) return false;
        boolean bl = true;
        if (!bl) return false;
        UnhandledMessage unhandledMessage = (UnhandledMessage)x$1;
        if (!BoxesRunTime.equals(this.message(), unhandledMessage.message())) return false;
        ActorRef actorRef2 = this.sender();
        ActorRef actorRef3 = unhandledMessage.sender();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        ActorRef actorRef4 = this.recipient();
        ActorRef actorRef5 = unhandledMessage.recipient();
        if (actorRef4 == null) {
            if (actorRef5 == null) return true;
            return false;
        } else {
            if (!((Object)actorRef4).equals(actorRef5)) return false;
            return true;
        }
    }

    public UnhandledMessage(Object message, ActorRef sender, ActorRef recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
        Product.$init$(this);
    }
}

