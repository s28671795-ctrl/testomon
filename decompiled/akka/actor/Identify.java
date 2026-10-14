/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AutoReceivedMessage;
import akka.actor.Identify$;
import akka.actor.NotInfluenceReceiveTimeout;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\rc\u0001\u0002\f\u0018\u0005rA\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t}\u0001\u0011\t\u0012)A\u0005w!)q\b\u0001C\u0001\u0001\"91\tAA\u0001\n\u0003!\u0005b\u0002$\u0001#\u0003%\ta\u0012\u0005\b%\u0002\t\t\u0011\"\u0011T\u0011\u001da\u0006!!A\u0005\u0002uCq!\u0019\u0001\u0002\u0002\u0013\u0005!\rC\u0004f\u0001\u0005\u0005I\u0011\t4\t\u000f5\u0004\u0011\u0011!C\u0001]\"91\u000fAA\u0001\n\u0003\"\bb\u0002<\u0001\u0003\u0003%\te\u001e\u0005\bq\u0002\t\t\u0011\"\u0011z\u0011\u001dQ\b!!A\u0005Bm<\u0011\"a\u0002\u0018\u0003\u0003E\t!!\u0003\u0007\u0011Y9\u0012\u0011!E\u0001\u0003\u0017Aaa\u0010\t\u0005\u0002\u0005\r\u0002b\u0002=\u0011\u0003\u0003%)%\u001f\u0005\n\u0003K\u0001\u0012\u0011!CA\u0003OA\u0011\"a\u000b\u0011\u0003\u0003%\t)!\f\t\u0013\u0005e\u0002#!A\u0005\n\u0005m\"\u0001C%eK:$\u0018NZ=\u000b\u0005aI\u0012!B1di>\u0014(\"\u0001\u000e\u0002\t\u0005\\7.Y\u0002\u0001'\u0019\u0001QdI\u0014+[A\u0011a$I\u0007\u0002?)\t\u0001%A\u0003tG\u0006d\u0017-\u0003\u0002#?\t1\u0011I\\=SK\u001a\u0004\"\u0001J\u0013\u000e\u0003]I!AJ\f\u0003'\u0005+Ho\u001c*fG\u0016Lg/\u001a3NKN\u001c\u0018mZ3\u0011\u0005\u0011B\u0013BA\u0015\u0018\u0005iqu\u000e^%oM2,XM\\2f%\u0016\u001cW-\u001b<f)&lWm\\;u!\tq2&\u0003\u0002-?\t9\u0001K]8ek\u000e$\bC\u0001\u00187\u001d\tyCG\u0004\u00021g5\t\u0011G\u0003\u000237\u00051AH]8pizJ\u0011\u0001I\u0005\u0003k}\tq\u0001]1dW\u0006<W-\u0003\u00028q\ta1+\u001a:jC2L'0\u00192mK*\u0011QgH\u0001\n[\u0016\u001c8/Y4f\u0013\u0012,\u0012a\u000f\t\u0003=qJ!!P\u0010\u0003\u0007\u0005s\u00170\u0001\u0006nKN\u001c\u0018mZ3JI\u0002\na\u0001P5oSRtDCA!C!\t!\u0003\u0001C\u0003:\u0007\u0001\u00071(\u0001\u0003d_BLHCA!F\u0011\u001dID\u0001%AA\u0002m\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001IU\tY\u0014jK\u0001K!\tY\u0005+D\u0001M\u0015\tie*A\u0005v]\u000eDWmY6fI*\u0011qjH\u0001\u000bC:tw\u000e^1uS>t\u0017BA)M\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003Q\u0003\"!\u0016.\u000e\u0003YS!a\u0016-\u0002\t1\fgn\u001a\u0006\u00023\u0006!!.\u0019<b\u0013\tYfK\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002=B\u0011adX\u0005\u0003A~\u00111!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"aO2\t\u000f\u0011D\u0011\u0011!a\u0001=\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012a\u001a\t\u0004Q.\\T\"A5\u000b\u0005)|\u0012AC2pY2,7\r^5p]&\u0011A.\u001b\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002peB\u0011a\u0004]\u0005\u0003c~\u0011qAQ8pY\u0016\fg\u000eC\u0004e\u0015\u0005\u0005\t\u0019A\u001e\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0003)VDq\u0001Z\u0006\u0002\u0002\u0003\u0007a,\u0001\u0005iCND7i\u001c3f)\u0005q\u0016\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003Q\u000ba!Z9vC2\u001cHCA8}\u0011\u001d!g\"!AA\u0002mBc\u0001\u0001@\u0002\u0004\u0005\u0015\u0001C\u0001\u0010\u0000\u0013\r\t\ta\b\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\t\u0013\u0012,g\u000e^5gsB\u0011A\u0005E\n\u0006!\u00055\u0011\u0011\u0004\t\u0007\u0003\u001f\t)bO!\u000e\u0005\u0005E!bAA\n?\u00059!/\u001e8uS6,\u0017\u0002BA\f\u0003#\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\tY\"!\t\u000e\u0005\u0005u!bAA\u00101\u0006\u0011\u0011n\\\u0005\u0004o\u0005uACAA\u0005\u0003\u0015\t\u0007\u000f\u001d7z)\r\t\u0015\u0011\u0006\u0005\u0006sM\u0001\raO\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ty#!\u000e\u0011\ty\t\tdO\u0005\u0004\u0003gy\"AB(qi&|g\u000e\u0003\u0005\u00028Q\t\t\u00111\u0001B\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003{\u00012!VA \u0013\r\t\tE\u0016\u0002\u0007\u001f\nTWm\u0019;")
public final class Identify
implements AutoReceivedMessage,
NotInfluenceReceiveTimeout,
Product {
    private static final long serialVersionUID = 1L;
    private final Object messageId;

    public static Option<Object> unapply(Identify x$0) {
        return Identify$.MODULE$.unapply(x$0);
    }

    public static Identify apply(Object messageId) {
        return Identify$.MODULE$.apply(messageId);
    }

    public static <A> Function1<Object, A> andThen(Function1<Identify, A> g2) {
        return Identify$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, Identify> compose(Function1<A, Object> g2) {
        return Identify$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Object messageId() {
        return this.messageId;
    }

    public Identify copy(Object messageId) {
        return new Identify(messageId);
    }

    public Object copy$default$1() {
        return this.messageId();
    }

    @Override
    public String productPrefix() {
        return "Identify";
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
                return this.messageId();
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
        return x$1 instanceof Identify;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "messageId";
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
        if (!(object instanceof Identify)) return false;
        boolean bl = true;
        if (!bl) return false;
        Identify identify = (Identify)x$1;
        if (!BoxesRunTime.equals(this.messageId(), identify.messageId())) return false;
        return true;
    }

    public Identify(Object messageId) {
        this.messageId = messageId;
        Product.$init$(this);
    }
}

