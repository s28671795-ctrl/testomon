/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorSelectionMessage$;
import akka.actor.AutoReceivedMessage;
import akka.actor.Identify;
import akka.actor.PossiblyHarmful;
import akka.actor.SelectionPathElement;
import akka.actor.WrappedMessage;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\rf!\u0002\u0010 \u0005\u0006\u001a\u0003\u0002\u0003#\u0001\u0005+\u0007I\u0011A#\t\u0011%\u0003!\u0011#Q\u0001\n\u0019C\u0001B\u0013\u0001\u0003\u0016\u0004%\ta\u0013\u0005\t/\u0002\u0011\t\u0012)A\u0005\u0019\"A\u0001\f\u0001BK\u0002\u0013\u0005\u0011\f\u0003\u0005^\u0001\tE\t\u0015!\u0003[\u0011\u0015q\u0006\u0001\"\u0001`\u0011\u0015!\u0007\u0001\"\u0001f\u0011\u0015a\u0007\u0001\"\u0011F\u0011\u001di\u0007!!A\u0005\u00029DqA\u001d\u0001\u0012\u0002\u0013\u00051\u000fC\u0004\u007f\u0001E\u0005I\u0011A@\t\u0013\u0005\r\u0001!%A\u0005\u0002\u0005\u0015\u0001\"CA\u0005\u0001\u0005\u0005I\u0011IA\u0006\u0011%\ti\u0002AA\u0001\n\u0003\ty\u0002C\u0005\u0002(\u0001\t\t\u0011\"\u0001\u0002*!I\u0011q\u0006\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0007\u0005\n\u0003w\u0001\u0011\u0011!C\u0001\u0003{A\u0011\"!\u0011\u0001\u0003\u0003%\t%a\u0011\t\u0013\u0005\u001d\u0003!!A\u0005B\u0005%\u0003\"CA&\u0001\u0005\u0005I\u0011IA'\u0011%\ty\u0005AA\u0001\n\u0003\n\tf\u0002\u0006\u0002b}\t\t\u0011#\u0001\"\u0003G2\u0011BH\u0010\u0002\u0002#\u0005\u0011%!\u001a\t\ryCB\u0011AA?\u0011%\tY\u0005GA\u0001\n\u000b\ni\u0005C\u0005\u0002\u0000a\t\t\u0011\"!\u0002\u0002\"I\u0011\u0011\u0012\r\u0002\u0002\u0013\u0005\u00151\u0012\u0005\n\u00033C\u0012\u0011!C\u0005\u00037\u0013Q#Q2u_J\u001cV\r\\3di&|g.T3tg\u0006<WM\u0003\u0002!C\u0005)\u0011m\u0019;pe*\t!%\u0001\u0003bW.\f7c\u0002\u0001%U9\nDg\u000e\t\u0003K!j\u0011A\n\u0006\u0002O\u0005)1oY1mC&\u0011\u0011F\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005-bS\"A\u0010\n\u00055z\"aE!vi>\u0014VmY3jm\u0016$W*Z:tC\u001e,\u0007CA\u00160\u0013\t\u0001tDA\bQ_N\u001c\u0018N\u00197z\u0011\u0006\u0014XNZ;m!\tY#'\u0003\u00024?\tqqK]1qa\u0016$W*Z:tC\u001e,\u0007CA\u00136\u0013\t1dEA\u0004Qe>$Wo\u0019;\u0011\u0005a\neBA\u001d@\u001d\tQd(D\u0001<\u0015\taT(\u0001\u0004=e>|GOP\u0002\u0001\u0013\u00059\u0013B\u0001!'\u0003\u001d\u0001\u0018mY6bO\u0016L!AQ\"\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005\u00013\u0013aA7tOV\ta\t\u0005\u0002&\u000f&\u0011\u0001J\n\u0002\u0004\u0003:L\u0018\u0001B7tO\u0002\n\u0001\"\u001a7f[\u0016tGo]\u000b\u0002\u0019B\u0019QJ\u0015+\u000e\u00039S!a\u0014)\u0002\u0013%lW.\u001e;bE2,'BA)'\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003':\u0013\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0003WUK!AV\u0010\u0003)M+G.Z2uS>t\u0007+\u0019;i\u000b2,W.\u001a8u\u0003%)G.Z7f]R\u001c\b%\u0001\bxS2$7-\u0019:e\r\u0006tw*\u001e;\u0016\u0003i\u0003\"!J.\n\u0005q3#a\u0002\"p_2,\u0017M\\\u0001\u0010o&dGmY1sI\u001a\u000bgnT;uA\u00051A(\u001b8jiz\"B\u0001Y1cGB\u00111\u0006\u0001\u0005\u0006\t\u001e\u0001\rA\u0012\u0005\u0006\u0015\u001e\u0001\r\u0001\u0014\u0005\u00061\u001e\u0001\rAW\u0001\u0010S\u0012,g\u000e^5gsJ+\u0017/^3tiV\ta\rE\u0002&O&L!\u0001\u001b\u0014\u0003\r=\u0003H/[8o!\tY#.\u0003\u0002l?\tA\u0011\nZ3oi&4\u00170A\u0004nKN\u001c\u0018mZ3\u0002\t\r|\u0007/\u001f\u000b\u0005A>\u0004\u0018\u000fC\u0004E\u0015A\u0005\t\u0019\u0001$\t\u000f)S\u0001\u0013!a\u0001\u0019\"9\u0001L\u0003I\u0001\u0002\u0004Q\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0002i*\u0012a)^\u0016\u0002mB\u0011q\u000f`\u0007\u0002q*\u0011\u0011P_\u0001\nk:\u001c\u0007.Z2lK\u0012T!a\u001f\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002~q\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u0011\u0011\u0001\u0016\u0003\u0019V\fabY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0002\b)\u0012!,^\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u00055\u0001\u0003BA\b\u00033i!!!\u0005\u000b\t\u0005M\u0011QC\u0001\u0005Y\u0006twM\u0003\u0002\u0002\u0018\u0005!!.\u0019<b\u0013\u0011\tY\"!\u0005\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\t\t\u0003E\u0002&\u0003GI1!!\n'\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r1\u00151\u0006\u0005\n\u0003[\u0001\u0012\u0011!a\u0001\u0003C\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u001a!\u0015\t)$a\u000eG\u001b\u0005\u0001\u0016bAA\u001d!\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\rQ\u0016q\b\u0005\t\u0003[\u0011\u0012\u0011!a\u0001\r\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\ti!!\u0012\t\u0013\u000552#!AA\u0002\u0005\u0005\u0012\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\u0005\u0012\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u00055\u0011AB3rk\u0006d7\u000fF\u0002[\u0003'B\u0001\"!\f\u0017\u0003\u0003\u0005\rA\u0012\u0015\b\u0001\u0005]\u0013QLA0!\r)\u0013\u0011L\u0005\u0004\u000372#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0011\u0011!F!di>\u00148+\u001a7fGRLwN\\'fgN\fw-\u001a\t\u0003Wa\u0019R\u0001GA4\u0003g\u0002\u0002\"!\u001b\u0002p\u0019c%\fY\u0007\u0003\u0003WR1!!\u001c'\u0003\u001d\u0011XO\u001c;j[\u0016LA!!\u001d\u0002l\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001a\u0011\t\u0005U\u00141P\u0007\u0003\u0003oRA!!\u001f\u0002\u0016\u0005\u0011\u0011n\\\u0005\u0004\u0005\u0006]DCAA2\u0003\u0015\t\u0007\u000f\u001d7z)\u001d\u0001\u00171QAC\u0003\u000fCQ\u0001R\u000eA\u0002\u0019CQAS\u000eA\u00021CQ\u0001W\u000eA\u0002i\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002\u000e\u0006U\u0005\u0003B\u0013h\u0003\u001f\u0003b!JAI\r2S\u0016bAAJM\t1A+\u001e9mKNB\u0001\"a&\u001d\u0003\u0003\u0005\r\u0001Y\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAO!\u0011\ty!a(\n\t\u0005\u0005\u0016\u0011\u0003\u0002\u0007\u001f\nTWm\u0019;")
public final class ActorSelectionMessage
implements AutoReceivedMessage,
PossiblyHarmful,
WrappedMessage,
Product {
    private static final long serialVersionUID = 2L;
    private final Object msg;
    private final Iterable<SelectionPathElement> elements;
    private final boolean wildcardFanOut;

    public static Option<Tuple3<Object, Iterable<SelectionPathElement>, Object>> unapply(ActorSelectionMessage x$0) {
        return ActorSelectionMessage$.MODULE$.unapply(x$0);
    }

    public static ActorSelectionMessage apply(Object msg, Iterable<SelectionPathElement> elements, boolean wildcardFanOut) {
        return ActorSelectionMessage$.MODULE$.apply(msg, elements, wildcardFanOut);
    }

    public static Function1<Tuple3<Object, Iterable<SelectionPathElement>, Object>, ActorSelectionMessage> tupled() {
        return ActorSelectionMessage$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Iterable<SelectionPathElement>, Function1<Object, ActorSelectionMessage>>> curried() {
        return ActorSelectionMessage$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Object msg() {
        return this.msg;
    }

    public Iterable<SelectionPathElement> elements() {
        return this.elements;
    }

    public boolean wildcardFanOut() {
        return this.wildcardFanOut;
    }

    public Option<Identify> identifyRequest() {
        Object object = this.msg();
        if (object instanceof Identify) {
            Identify identify = (Identify)object;
            return new Some<Identify>(identify);
        }
        return None$.MODULE$;
    }

    @Override
    public Object message() {
        return this.msg();
    }

    public ActorSelectionMessage copy(Object msg, Iterable<SelectionPathElement> elements, boolean wildcardFanOut) {
        return new ActorSelectionMessage(msg, elements, wildcardFanOut);
    }

    public Object copy$default$1() {
        return this.msg();
    }

    public Iterable<SelectionPathElement> copy$default$2() {
        return this.elements();
    }

    public boolean copy$default$3() {
        return this.wildcardFanOut();
    }

    @Override
    public String productPrefix() {
        return "ActorSelectionMessage";
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
                return this.msg();
            }
            case 1: {
                return this.elements();
            }
            case 2: {
                return BoxesRunTime.boxToBoolean(this.wildcardFanOut());
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
        return x$1 instanceof ActorSelectionMessage;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "msg";
            }
            case 1: {
                return "elements";
            }
            case 2: {
                return "wildcardFanOut";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.msg()));
        n = Statics.mix(n, Statics.anyHash(this.elements()));
        n = Statics.mix(n, this.wildcardFanOut() ? 1231 : 1237);
        return Statics.finalizeHash(n, 3);
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
        if (!(object instanceof ActorSelectionMessage)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorSelectionMessage actorSelectionMessage = (ActorSelectionMessage)x$1;
        if (this.wildcardFanOut() != actorSelectionMessage.wildcardFanOut()) return false;
        if (!BoxesRunTime.equals(this.msg(), actorSelectionMessage.msg())) return false;
        Iterable<SelectionPathElement> iterable = this.elements();
        Iterable<SelectionPathElement> iterable2 = actorSelectionMessage.elements();
        if (iterable != null) {
            if (!iterable.equals(iterable2)) return false;
            return true;
        }
        if (iterable2 == null) return true;
        return false;
    }

    public ActorSelectionMessage(Object msg, Iterable<SelectionPathElement> elements, boolean wildcardFanOut) {
        this.msg = msg;
        this.elements = elements;
        this.wildcardFanOut = wildcardFanOut;
        Product.$init$(this);
    }
}

