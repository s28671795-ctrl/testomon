/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.AmorphousShape$;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.Shape;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Mg\u0001\u0002\u000e\u001c\u0001\u0002B\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\t\u0013\u0002\u0011\t\u0012)A\u0005s!A\u0011\u000b\u0001BK\u0002\u0013\u0005!\u000b\u0003\u0005\\\u0001\tE\t\u0015!\u0003T\u0011\u0015a\u0006\u0001\"\u0001^\u0011\u0015Y\u0007\u0001\"\u0011m\u0011\u001di\u0007!!A\u0005\u00029Dq!\u001d\u0001\u0012\u0002\u0013\u0005!\u000fC\u0004~\u0001E\u0005I\u0011\u0001@\t\u0013\u0005\u0005\u0001!!A\u0005B\u0005\r\u0001\"CA\u000b\u0001\u0005\u0005I\u0011AA\f\u0011%\ty\u0002AA\u0001\n\u0003\t\t\u0003C\u0005\u0002(\u0001\t\t\u0011\"\u0011\u0002*!I\u00111\u0007\u0001\u0002\u0002\u0013\u0005\u0011Q\u0007\u0005\n\u0003\u007f\u0001\u0011\u0011!C!\u0003\u0003B\u0011\"!\u0012\u0001\u0003\u0003%\t%a\u0012\t\u0013\u0005%\u0003!!A\u0005B\u0005-\u0003\"CA'\u0001\u0005\u0005I\u0011IA(\u000f%\t\u0019fGA\u0001\u0012\u0003\t)F\u0002\u0005\u001b7\u0005\u0005\t\u0012AA,\u0011\u0019aF\u0003\"\u0001\u0002\u0004\"I\u0011\u0011\n\u000b\u0002\u0002\u0013\u0015\u00131\n\u0005\n\u0003\u000b#\u0012\u0011!CA\u0003\u000fC\u0011\"!)\u0015\u0003\u0003%\t)a)\t\u0013\u0005%G#!A\u0005\n\u0005-'AD!n_J\u0004\bn\\;t'\"\f\u0007/\u001a\u0006\u00039u\taa\u001d;sK\u0006l'\"\u0001\u0010\u0002\t\u0005\\7.Y\u0002\u0001'\u0011\u0001\u0011%J\u0016\u0011\u0005\t\u001aS\"A\u000e\n\u0005\u0011Z\"!B*iCB,\u0007C\u0001\u0014*\u001b\u00059#\"\u0001\u0015\u0002\u000bM\u001c\u0017\r\\1\n\u0005):#a\u0002)s_\u0012,8\r\u001e\t\u0003YQr!!\f\u001a\u000f\u00059\nT\"A\u0018\u000b\u0005Az\u0012A\u0002\u001fs_>$h(C\u0001)\u0013\t\u0019t%A\u0004qC\u000e\\\u0017mZ3\n\u0005U2$\u0001D*fe&\fG.\u001b>bE2,'BA\u001a(\u0003\u0019Ig\u000e\\3ugV\t\u0011\bE\u0002;\u007f\u0005k\u0011a\u000f\u0006\u0003yu\n\u0011\"[7nkR\f'\r\\3\u000b\u0005y:\u0013AC2pY2,7\r^5p]&\u0011\u0001i\u000f\u0002\u0004'\u0016\f\bG\u0001\"H!\r\u00113)R\u0005\u0003\tn\u0011Q!\u00138mKR\u0004\"AR$\r\u0001\u0011I\u0001JAA\u0001\u0002\u0003\u0015\tA\u0013\u0002\u0005?\u0012\nd'A\u0004j]2,Go\u001d\u0011\u0012\u0005-s\u0005C\u0001\u0014M\u0013\tiuEA\u0004O_RD\u0017N\\4\u0011\u0005\u0019z\u0015B\u0001)(\u0005\r\te._\u0001\b_V$H.\u001a;t+\u0005\u0019\u0006c\u0001\u001e@)B\u0012Q+\u0017\t\u0004EYC\u0016BA,\u001c\u0005\u0019yU\u000f\u001e7fiB\u0011a)\u0017\u0003\n5\u0012\t\t\u0011!A\u0003\u0002)\u0013Aa\u0018\u00132o\u0005Aq.\u001e;mKR\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0004=~+\u0007C\u0001\u0012\u0001\u0011\u00159T\u00011\u0001a!\rQt(\u0019\u0019\u0003E\u0012\u00042AI\"d!\t1E\rB\u0005I?\u0006\u0005\t\u0011!B\u0001\u0015\")\u0011+\u0002a\u0001MB\u0019!hP41\u0005!T\u0007c\u0001\u0012WSB\u0011aI\u001b\u0003\n5\u0016\f\t\u0011!A\u0003\u0002)\u000b\u0001\u0002Z3fa\u000e{\u0007/\u001f\u000b\u0002=\u0006!1m\u001c9z)\rqv\u000e\u001d\u0005\bo\u001d\u0001\n\u00111\u0001a\u0011\u001d\tv\u0001%AA\u0002\u0019\fabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001tU\tIDoK\u0001v!\t180D\u0001x\u0015\tA\u00180A\u0005v]\u000eDWmY6fI*\u0011!pJ\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001?x\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0005y(FA*u\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011Q\u0001\t\u0005\u0003\u000f\t\t\"\u0004\u0002\u0002\n)!\u00111BA\u0007\u0003\u0011a\u0017M\\4\u000b\u0005\u0005=\u0011\u0001\u00026bm\u0006LA!a\u0005\u0002\n\t11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!!\u0007\u0011\u0007\u0019\nY\"C\u0002\u0002\u001e\u001d\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2ATA\u0012\u0011%\t)\u0003DA\u0001\u0002\u0004\tI\"A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003W\u0001R!!\f\u000209k\u0011!P\u0005\u0004\u0003ci$\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a\u000e\u0002>A\u0019a%!\u000f\n\u0007\u0005mrEA\u0004C_>dW-\u00198\t\u0011\u0005\u0015b\"!AA\u00029\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011QAA\"\u0011%\t)cDA\u0001\u0002\u0004\tI\"\u0001\u0005iCND7i\u001c3f)\t\tI\"\u0001\u0005u_N#(/\u001b8h)\t\t)!\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003o\t\t\u0006\u0003\u0005\u0002&I\t\t\u00111\u0001O\u00039\tUn\u001c:qQ>,8o\u00155ba\u0016\u0004\"A\t\u000b\u0014\u000bQ\tI&!\u001f\u0011\u0013\u0005m\u0013\u0011MA3\u0003_rVBAA/\u0015\r\tyfJ\u0001\beVtG/[7f\u0013\u0011\t\u0019'!\u0018\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t'\u0007\u0005\u0003;\u007f\u0005\u001d\u0004\u0007BA5\u0003[\u0002BAI\"\u0002lA\u0019a)!\u001c\u0005\u0013!#\u0012\u0011!A\u0001\u0006\u0003Q\u0005\u0003\u0002\u001e@\u0003c\u0002D!a\u001d\u0002xA!!EVA;!\r1\u0015q\u000f\u0003\n5R\t\t\u0011!A\u0003\u0002)\u0003B!a\u001f\u0002\u00026\u0011\u0011Q\u0010\u0006\u0005\u0003\u007f\ni!\u0001\u0002j_&\u0019Q'! \u0015\u0005\u0005U\u0013!B1qa2LH#\u00020\u0002\n\u0006U\u0005BB\u001c\u0018\u0001\u0004\tY\t\u0005\u0003;\u007f\u00055\u0005\u0007BAH\u0003'\u0003BAI\"\u0002\u0012B\u0019a)a%\u0005\u0015!\u000bI)!A\u0001\u0002\u000b\u0005!\n\u0003\u0004R/\u0001\u0007\u0011q\u0013\t\u0005u}\nI\n\r\u0003\u0002\u001c\u0006}\u0005\u0003\u0002\u0012W\u0003;\u00032ARAP\t)Q\u0016QSA\u0001\u0002\u0003\u0015\tAS\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t)+!2\u0011\u000b\u0019\n9+a+\n\u0007\u0005%vE\u0001\u0004PaRLwN\u001c\t\bM\u00055\u0016\u0011WA^\u0013\r\tyk\n\u0002\u0007)V\u0004H.\u001a\u001a\u0011\tiz\u00141\u0017\u0019\u0005\u0003k\u000bI\f\u0005\u0003#\u0007\u0006]\u0006c\u0001$\u0002:\u0012I\u0001\nGA\u0001\u0002\u0003\u0015\tA\u0013\t\u0005u}\ni\f\r\u0003\u0002@\u0006\r\u0007\u0003\u0002\u0012W\u0003\u0003\u00042ARAb\t%Q\u0006$!A\u0001\u0002\u000b\u0005!\n\u0003\u0005\u0002Hb\t\t\u00111\u0001_\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u001b\u0004B!a\u0002\u0002P&!\u0011\u0011[A\u0005\u0005\u0019y%M[3di\u0002")
public class AmorphousShape
extends Shape
implements Product,
Serializable {
    private final Seq<Inlet<?>> inlets;
    private final Seq<Outlet<?>> outlets;

    public static Option<Tuple2<Seq<Inlet<?>>, Seq<Outlet<?>>>> unapply(AmorphousShape x$0) {
        return AmorphousShape$.MODULE$.unapply(x$0);
    }

    public static AmorphousShape apply(Seq<Inlet<?>> inlets, Seq<Outlet<?>> outlets) {
        return AmorphousShape$.MODULE$.apply(inlets, outlets);
    }

    public static Function1<Tuple2<Seq<Inlet<?>>, Seq<Outlet<?>>>, AmorphousShape> tupled() {
        return AmorphousShape$.MODULE$.tupled();
    }

    public static Function1<Seq<Inlet<?>>, Function1<Seq<Outlet<?>>, AmorphousShape>> curried() {
        return AmorphousShape$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Seq<Inlet<?>> inlets() {
        return this.inlets;
    }

    @Override
    public Seq<Outlet<?>> outlets() {
        return this.outlets;
    }

    @Override
    public AmorphousShape deepCopy() {
        return new AmorphousShape((Seq)this.inlets().map((Function1<Inlet, Inlet> & Serializable)x$1 -> x$1.carbonCopy()), (Seq)this.outlets().map((Function1<Outlet, Outlet> & Serializable)x$2 -> x$2.carbonCopy()));
    }

    public AmorphousShape copy(Seq<Inlet<?>> inlets, Seq<Outlet<?>> outlets) {
        return new AmorphousShape(inlets, outlets);
    }

    public Seq<Inlet<?>> copy$default$1() {
        return this.inlets();
    }

    public Seq<Outlet<?>> copy$default$2() {
        return this.outlets();
    }

    @Override
    public String productPrefix() {
        return "AmorphousShape";
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
                return this.inlets();
            }
            case 1: {
                return this.outlets();
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
        return x$1 instanceof AmorphousShape;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "inlets";
            }
            case 1: {
                return "outlets";
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
        if (!(object instanceof AmorphousShape)) return false;
        boolean bl = true;
        if (!bl) return false;
        AmorphousShape amorphousShape = (AmorphousShape)x$1;
        Seq<Inlet<?>> seq = this.inlets();
        Seq<Inlet<?>> seq2 = amorphousShape.inlets();
        if (seq == null) {
            if (seq2 != null) {
                return false;
            }
        } else if (!seq.equals(seq2)) return false;
        Seq<Outlet<?>> seq3 = this.outlets();
        Seq<Outlet<?>> seq4 = amorphousShape.outlets();
        if (seq3 == null) {
            if (seq4 != null) {
                return false;
            }
        } else if (!seq3.equals(seq4)) return false;
        if (!amorphousShape.canEqual(this)) return false;
        return true;
    }

    public AmorphousShape(Seq<Inlet<?>> inlets, Seq<Outlet<?>> outlets) {
        this.inlets = inlets;
        this.outlets = outlets;
        Product.$init$(this);
    }
}

