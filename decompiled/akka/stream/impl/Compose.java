/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Compose$;
import akka.stream.impl.MaterializedValueOp;
import akka.stream.impl.Traversal;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055e!\u0002\u000f\u001e\u0005\u0006\u001a\u0003\u0002\u0003 \u0001\u0005+\u0007I\u0011A \t\u00115\u0003!\u0011#Q\u0001\n\u0001C\u0001B\u0014\u0001\u0003\u0016\u0004%\ta\u0014\u0005\t'\u0002\u0011\t\u0012)A\u0005!\")A\u000b\u0001C\u0001+\")\u0011\f\u0001C\u00015\"9!\rAA\u0001\n\u0003\u0019\u0007b\u00024\u0001#\u0003%\ta\u001a\u0005\be\u0002\t\n\u0011\"\u0001t\u0011\u001d)\b!!A\u0005BYD\u0001b \u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0001\u0005\n\u0003\u0013\u0001\u0011\u0011!C\u0001\u0003\u0017A\u0011\"!\u0005\u0001\u0003\u0003%\t%a\u0005\t\u0013\u0005\u0005\u0002!!A\u0005\u0002\u0005\r\u0002\"CA\u0014\u0001\u0005\u0005I\u0011IA\u0015\u0011%\ti\u0003AA\u0001\n\u0003\ny\u0003C\u0005\u00022\u0001\t\t\u0011\"\u0011\u00024!I\u0011Q\u0007\u0001\u0002\u0002\u0013\u0005\u0013qG\u0004\u000b\u0003\u000fj\u0012\u0011!E\u0001C\u0005%c!\u0003\u000f\u001e\u0003\u0003E\t!IA&\u0011\u0019!F\u0003\"\u0001\u0002d!I\u0011\u0011\u0007\u000b\u0002\u0002\u0013\u0015\u00131\u0007\u0005\t3R\t\t\u0011\"!\u0002f!A\u00111\u000e\u000b\u0012\u0002\u0013\u00051\u000fC\u0005\u0002nQ\t\t\u0011\"!\u0002p!A\u0011\u0011\u0011\u000b\u0012\u0002\u0013\u00051\u000fC\u0005\u0002\u0004R\t\t\u0011\"\u0003\u0002\u0006\n91i\\7q_N,'B\u0001\u0010 \u0003\u0011IW\u000e\u001d7\u000b\u0005\u0001\n\u0013AB:ue\u0016\fWNC\u0001#\u0003\u0011\t7n[1\u0014\u000b\u0001!#FL\u0019\u0011\u0005\u0015BS\"\u0001\u0014\u000b\u0003\u001d\nQa]2bY\u0006L!!\u000b\u0014\u0003\r\u0005s\u0017PU3g!\tYC&D\u0001\u001e\u0013\tiSDA\nNCR,'/[1mSj,GMV1mk\u0016|\u0005\u000f\u0005\u0002&_%\u0011\u0001G\n\u0002\b!J|G-^2u!\t\u00114H\u0004\u00024s9\u0011A\u0007O\u0007\u0002k)\u0011agN\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\tq%\u0003\u0002;M\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001f>\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tQd%\u0001\u0005d_6\u0004xn]3s+\u0005\u0001\u0005CA!K\u001d\t\u0011\u0005J\u0004\u0002D\u000f:\u0011AI\u0012\b\u0003i\u0015K\u0011AI\u0005\u0003A\u0005J!AH\u0010\n\u0005%k\u0012\u0001\u0005+sCZ,'o]1m\u0005VLG\u000eZ3s\u0013\tYEJ\u0001\u0007B]f4UO\\2uS>t'G\u0003\u0002J;\u0005I1m\\7q_N,'\u000fI\u0001\be\u00164XM]:f+\u0005\u0001\u0006CA\u0013R\u0013\t\u0011fEA\u0004C_>dW-\u00198\u0002\u0011I,g/\u001a:tK\u0002\na\u0001P5oSRtDc\u0001,X1B\u00111\u0006\u0001\u0005\u0006}\u0015\u0001\r\u0001\u0011\u0005\b\u001d\u0016\u0001\n\u00111\u0001Q\u0003\u0015\t\u0007\u000f\u001d7z)\rYf\f\u0019\t\u0003KqK!!\u0018\u0014\u0003\u0007\u0005s\u0017\u0010C\u0003`\r\u0001\u00071,\u0001\u0003be\u001e\f\u0004\"B1\u0007\u0001\u0004Y\u0016\u0001B1sOJ\nAaY8qsR\u0019a\u000bZ3\t\u000fy:\u0001\u0013!a\u0001\u0001\"9aj\u0002I\u0001\u0002\u0004\u0001\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0002Q*\u0012\u0001)[\u0016\u0002UB\u00111\u000e]\u0007\u0002Y*\u0011QN\\\u0001\nk:\u001c\u0007.Z2lK\u0012T!a\u001c\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002rY\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\tAO\u000b\u0002QS\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\u001e\t\u0003qvl\u0011!\u001f\u0006\u0003un\fA\u0001\\1oO*\tA0\u0001\u0003kCZ\f\u0017B\u0001@z\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u00111\u0001\t\u0004K\u0005\u0015\u0011bAA\u0004M\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u00191,!\u0004\t\u0013\u0005=A\"!AA\u0002\u0005\r\u0011a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\u0016A)\u0011qCA\u000f76\u0011\u0011\u0011\u0004\u0006\u0004\u000371\u0013AC2pY2,7\r^5p]&!\u0011qDA\r\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007A\u000b)\u0003\u0003\u0005\u0002\u00109\t\t\u00111\u0001\\\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007]\fY\u0003C\u0005\u0002\u0010=\t\t\u00111\u0001\u0002\u0004\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\u0004\u0005AAo\\*ue&tw\rF\u0001x\u0003\u0019)\u0017/^1mgR\u0019\u0001+!\u000f\t\u0011\u0005=!#!AA\u0002mC3\u0001AA\u001f!\u0011\ty$a\u0011\u000e\u0005\u0005\u0005#BA8\"\u0013\u0011\t)%!\u0011\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\b\u0007>l\u0007o\\:f!\tYCcE\u0003\u0015\u0003\u001b\nI\u0006E\u0004\u0002P\u0005U\u0003\t\u0015,\u000e\u0005\u0005E#bAA*M\u00059!/\u001e8uS6,\u0017\u0002BA,\u0003#\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83!\u0011\tY&!\u0019\u000e\u0005\u0005u#bAA0w\u0006\u0011\u0011n\\\u0005\u0004y\u0005uCCAA%)\u00151\u0016qMA5\u0011\u0015qt\u00031\u0001A\u0011\u001dqu\u0003%AA\u0002A\u000bq\"\u00199qYf$C-\u001a4bk2$HEM\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\t(! \u0011\u000b\u0015\n\u0019(a\u001e\n\u0007\u0005UdE\u0001\u0004PaRLwN\u001c\t\u0006K\u0005e\u0004\tU\u0005\u0004\u0003w2#A\u0002+va2,'\u0007\u0003\u0005\u0002\u0000e\t\t\u00111\u0001W\u0003\rAH\u0005M\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u001d\u0005c\u0001=\u0002\n&\u0019\u00111R=\u0003\r=\u0013'.Z2u\u0001")
@InternalApi
public final class Compose
implements MaterializedValueOp,
Product,
Serializable {
    private final Function2<Nothing$, Nothing$, Object> composer;
    private final boolean reverse;

    public static boolean $lessinit$greater$default$2() {
        return Compose$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple2<Function2<Nothing$, Nothing$, Object>, Object>> unapply(Compose x$0) {
        return Compose$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$2() {
        return Compose$.MODULE$.apply$default$2();
    }

    public static Function1<Tuple2<Function2<Nothing$, Nothing$, Object>, Object>, Compose> tupled() {
        return Compose$.MODULE$.tupled();
    }

    public static Function1<Function2<Nothing$, Nothing$, Object>, Function1<Object, Compose>> curried() {
        return Compose$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Traversal concat(Traversal that) {
        return Traversal.concat$(this, that);
    }

    @Override
    public Traversal rewireFirstTo(int relativeOffset) {
        return Traversal.rewireFirstTo$(this, relativeOffset);
    }

    public Function2<Nothing$, Nothing$, Object> composer() {
        return this.composer;
    }

    public boolean reverse() {
        return this.reverse;
    }

    public Object apply(Object arg1, Object arg2) {
        if (this.reverse()) {
            return this.composer().apply((Nothing$)arg2, (Nothing$)arg1);
        }
        return this.composer().apply((Nothing$)arg1, (Nothing$)arg2);
    }

    public Compose copy(Function2<Nothing$, Nothing$, Object> composer, boolean reverse) {
        return new Compose(composer, reverse);
    }

    public Function2<Nothing$, Nothing$, Object> copy$default$1() {
        return this.composer();
    }

    public boolean copy$default$2() {
        return this.reverse();
    }

    @Override
    public String productPrefix() {
        return "Compose";
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
                return this.composer();
            }
            case 1: {
                return BoxesRunTime.boxToBoolean(this.reverse());
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
        return x$1 instanceof Compose;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "composer";
            }
            case 1: {
                return "reverse";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.composer()));
        n = Statics.mix(n, this.reverse() ? 1231 : 1237);
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
        if (!(object instanceof Compose)) return false;
        boolean bl = true;
        if (!bl) return false;
        Compose compose = (Compose)x$1;
        if (this.reverse() != compose.reverse()) return false;
        Function2<Nothing$, Nothing$, Object> function2 = this.composer();
        Function2<Nothing$, Nothing$, Object> function22 = compose.composer();
        if (function2 != null) {
            if (!function2.equals(function22)) return false;
            return true;
        }
        if (function22 == null) return true;
        return false;
    }

    public Compose(Function2<Nothing$, Nothing$, Object> composer, boolean reverse) {
        this.composer = composer;
        this.reverse = reverse;
        Traversal.$init$(this);
        Product.$init$(this);
    }
}

