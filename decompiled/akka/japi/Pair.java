/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.japi.Pair$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055f\u0001\u0002\u000e\u001c\u0001\u0002B\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\t\t\u0002\u0011\t\u0012)A\u0005s!AQ\t\u0001BK\u0002\u0013\u0005a\t\u0003\u0005K\u0001\tE\t\u0015!\u0003H\u0011\u0015Y\u0005\u0001\"\u0001M\u0011\u0015\t\u0006\u0001\"\u0001S\u0011\u001d1\u0006!!A\u0005\u0002]Cq\u0001\u0019\u0001\u0012\u0002\u0013\u0005\u0011\rC\u0004p\u0001E\u0005I\u0011\u00019\t\u000fU\u0004\u0011\u0011!C!m\"Aq\u0010AA\u0001\n\u0003\t\t\u0001C\u0005\u0002\n\u0001\t\t\u0011\"\u0001\u0002\f!I\u0011\u0011\u0003\u0001\u0002\u0002\u0013\u0005\u00131\u0003\u0005\n\u0003C\u0001\u0011\u0011!C\u0001\u0003GA\u0011\"!\f\u0001\u0003\u0003%\t%a\f\t\u0013\u0005M\u0002!!A\u0005B\u0005U\u0002\"CA\u001c\u0001\u0005\u0005I\u0011IA\u001d\u0011%\tY\u0004AA\u0001\n\u0003\nidB\u0004\u0002NmA\t!a\u0014\u0007\riY\u0002\u0012AA)\u0011\u0019YE\u0003\"\u0001\u0002^!9\u0011q\f\u000b\u0005\u0002\u0005\u0005\u0004\"CA:)\u0005\u0005I\u0011QA;\u0011%\t9\tFA\u0001\n\u0003\u000bI\tC\u0005\u0002$R\t\t\u0011\"\u0003\u0002&\n!\u0001+Y5s\u0015\taR$\u0001\u0003kCBL'\"\u0001\u0010\u0002\t\u0005\\7.Y\u0002\u0001+\r\t3\bS\n\u0005\u0001\tB3\u0006\u0005\u0002$M5\tAEC\u0001&\u0003\u0015\u00198-\u00197b\u0013\t9CE\u0001\u0004B]f\u0014VM\u001a\t\u0003G%J!A\u000b\u0013\u0003\u000fA\u0013x\u000eZ;diB\u0011A\u0006\u000e\b\u0003[Ir!AL\u0019\u000e\u0003=R!\u0001M\u0010\u0002\rq\u0012xn\u001c;?\u0013\u0005)\u0013BA\u001a%\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u000e\u001c\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005M\"\u0013!\u00024jeN$X#A\u001d\u0011\u0005iZD\u0002\u0001\u0003\u0006y\u0001\u0011\r!\u0010\u0002\u0002\u0003F\u0011a(\u0011\t\u0003G}J!\u0001\u0011\u0013\u0003\u000f9{G\u000f[5oOB\u00111EQ\u0005\u0003\u0007\u0012\u00121!\u00118z\u0003\u00191\u0017N]:uA\u000511/Z2p]\u0012,\u0012a\u0012\t\u0003u!#Q!\u0013\u0001C\u0002u\u0012\u0011AQ\u0001\bg\u0016\u001cwN\u001c3!\u0003\u0019a\u0014N\\5u}Q\u0019Qj\u0014)\u0011\t9\u0003\u0011hR\u0007\u00027!)q'\u0002a\u0001s!)Q)\u0002a\u0001\u000f\u00069Ao\\*dC2\fW#A*\u0011\t\r\"\u0016hR\u0005\u0003+\u0012\u0012a\u0001V;qY\u0016\u0014\u0014\u0001B2paf,2\u0001W.^)\rIfl\u0018\t\u0005\u001d\u0002QF\f\u0005\u0002;7\u0012)Ah\u0002b\u0001{A\u0011!(\u0018\u0003\u0006\u0013\u001e\u0011\r!\u0010\u0005\bo\u001d\u0001\n\u00111\u0001[\u0011\u001d)u\u0001%AA\u0002q\u000babY8qs\u0012\"WMZ1vYR$\u0013'F\u0002c[:,\u0012a\u0019\u0016\u0003s\u0011\\\u0013!\u001a\t\u0003M.l\u0011a\u001a\u0006\u0003Q&\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005)$\u0013AC1o]>$\u0018\r^5p]&\u0011An\u001a\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G!\u0002\u001f\t\u0005\u0004iD!B%\t\u0005\u0004i\u0014AD2paf$C-\u001a4bk2$HEM\u000b\u0004cN$X#\u0001:+\u0005\u001d#G!\u0002\u001f\n\u0005\u0004iD!B%\n\u0005\u0004i\u0014!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001x!\tAX0D\u0001z\u0015\tQ80\u0001\u0003mC:<'\"\u0001?\u0002\t)\fg/Y\u0005\u0003}f\u0014aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXCAA\u0002!\r\u0019\u0013QA\u0005\u0004\u0003\u000f!#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HcA!\u0002\u000e!I\u0011q\u0002\u0007\u0002\u0002\u0003\u0007\u00111A\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005U\u0001#BA\f\u0003;\tUBAA\r\u0015\r\tY\u0002J\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA\u0010\u00033\u0011\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011QEA\u0016!\r\u0019\u0013qE\u0005\u0004\u0003S!#a\u0002\"p_2,\u0017M\u001c\u0005\t\u0003\u001fq\u0011\u0011!a\u0001\u0003\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\r9\u0018\u0011\u0007\u0005\n\u0003\u001fy\u0011\u0011!a\u0001\u0003\u0007\t\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003\u0007\t\u0001\u0002^8TiJLgn\u001a\u000b\u0002o\u00061Q-];bYN$B!!\n\u0002@!A\u0011q\u0002\n\u0002\u0002\u0003\u0007\u0011\tK\u0004\u0001\u0003\u0007\nI%a\u0013\u0011\u0007\r\n)%C\u0002\u0002H\u0011\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005\tA\u0001U1jeB\u0011a\nF\n\u0005)\t\n\u0019\u0006\u0005\u0003\u0002V\u0005mSBAA,\u0015\r\tIf_\u0001\u0003S>L1!NA,)\t\ty%\u0001\u0004de\u0016\fG/Z\u000b\u0007\u0003G\nI'!\u001c\u0015\r\u0005\u0015\u0014qNA9!\u0019q\u0005!a\u001a\u0002lA\u0019!(!\u001b\u0005\u000bq2\"\u0019A\u001f\u0011\u0007i\ni\u0007B\u0003J-\t\u0007Q\b\u0003\u00048-\u0001\u0007\u0011q\r\u0005\u0007\u000bZ\u0001\r!a\u001b\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\r\u0005]\u0014QPAA)\u0019\tI(a!\u0002\u0006B1a\nAA>\u0003\u007f\u00022AOA?\t\u0015atC1\u0001>!\rQ\u0014\u0011\u0011\u0003\u0006\u0013^\u0011\r!\u0010\u0005\u0007o]\u0001\r!a\u001f\t\r\u0015;\u0002\u0019AA@\u0003\u001d)h.\u00199qYf,b!a#\u0002\u0018\u0006mE\u0003BAG\u0003;\u0003RaIAH\u0003'K1!!%%\u0005\u0019y\u0005\u000f^5p]B11\u0005VAK\u00033\u00032AOAL\t\u0015a\u0004D1\u0001>!\rQ\u00141\u0014\u0003\u0006\u0013b\u0011\r!\u0010\u0005\n\u0003?C\u0012\u0011!a\u0001\u0003C\u000b1\u0001\u001f\u00131!\u0019q\u0005!!&\u0002\u001a\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011q\u0015\t\u0004q\u0006%\u0016bAAVs\n1qJ\u00196fGR\u0004")
public class Pair<A, B>
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final A first;
    private final B second;

    public static <A, B> Option<Tuple2<A, B>> unapply(Pair<A, B> x$0) {
        return Pair$.MODULE$.unapply(x$0);
    }

    public static <A, B> Pair<A, B> apply(A first, B second) {
        return Pair$.MODULE$.apply(first, second);
    }

    public static <A, B> Pair<A, B> create(A first, B second) {
        return Pair$.MODULE$.create(first, second);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public A first() {
        return this.first;
    }

    public B second() {
        return this.second;
    }

    public Tuple2<A, B> toScala() {
        return new Tuple2<A, B>(this.first(), this.second());
    }

    public <A, B> Pair<A, B> copy(A first, B second) {
        return new Pair<A, B>(first, second);
    }

    public <A, B> A copy$default$1() {
        return this.first();
    }

    public <A, B> B copy$default$2() {
        return this.second();
    }

    @Override
    public String productPrefix() {
        return "Pair";
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
                return this.first();
            }
            case 1: {
                return this.second();
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
        return x$1 instanceof Pair;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "first";
            }
            case 1: {
                return "second";
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
        if (!(object instanceof Pair)) return false;
        boolean bl = true;
        if (!bl) return false;
        Pair pair = (Pair)x$1;
        if (!BoxesRunTime.equals(this.first(), pair.first())) return false;
        if (!BoxesRunTime.equals(this.second(), pair.second())) return false;
        if (!pair.canEqual(this)) return false;
        return true;
    }

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
        Product.$init$(this);
    }
}

