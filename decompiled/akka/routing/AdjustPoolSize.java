/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.AdjustPoolSize$;
import akka.routing.RouterManagementMesssage;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005mb\u0001\u0002\f\u0018\u0005rA\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\tw\u0001\u0011\t\u0012)A\u0005q!)A\b\u0001C\u0001{!9\u0001\tAA\u0001\n\u0003\t\u0005bB\"\u0001#\u0003%\t\u0001\u0012\u0005\b\u001f\u0002\t\t\u0011\"\u0011Q\u0011\u001dI\u0006!!A\u0005\u0002]BqA\u0017\u0001\u0002\u0002\u0013\u00051\fC\u0004b\u0001\u0005\u0005I\u0011\t2\t\u000f%\u0004\u0011\u0011!C\u0001U\"9q\u000eAA\u0001\n\u0003\u0002\bb\u0002:\u0001\u0003\u0003%\te\u001d\u0005\bi\u0002\t\t\u0011\"\u0011v\u0011\u001d1\b!!A\u0005B]<\u0001b`\f\u0002\u0002#\u0005\u0011\u0011\u0001\u0004\t-]\t\t\u0011#\u0001\u0002\u0004!1A\b\u0005C\u0001\u00037Aq\u0001\u001e\t\u0002\u0002\u0013\u0015S\u000fC\u0005\u0002\u001eA\t\t\u0011\"!\u0002 !I\u00111\u0005\t\u0002\u0002\u0013\u0005\u0015Q\u0005\u0005\n\u0003c\u0001\u0012\u0011!C\u0005\u0003g\u0011a\"\u00113kkN$\bk\\8m'&TXM\u0003\u0002\u00193\u00059!o\\;uS:<'\"\u0001\u000e\u0002\t\u0005\\7.Y\u0002\u0001'\u0015\u0001QdI\u0014+!\tq\u0012%D\u0001 \u0015\u0005\u0001\u0013!B:dC2\f\u0017B\u0001\u0012 \u0005\u0019\te.\u001f*fMB\u0011A%J\u0007\u0002/%\u0011ae\u0006\u0002\u0019%>,H/\u001a:NC:\fw-Z7f]RlUm]:tC\u001e,\u0007C\u0001\u0010)\u0013\tIsDA\u0004Qe>$Wo\u0019;\u0011\u0005-\u001adB\u0001\u00172\u001d\ti\u0003'D\u0001/\u0015\ty3$\u0001\u0004=e>|GOP\u0005\u0002A%\u0011!gH\u0001\ba\u0006\u001c7.Y4f\u0013\t!TG\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00023?\u000511\r[1oO\u0016,\u0012\u0001\u000f\t\u0003=eJ!AO\u0010\u0003\u0007%sG/A\u0004dQ\u0006tw-\u001a\u0011\u0002\rqJg.\u001b;?)\tqt\b\u0005\u0002%\u0001!)ag\u0001a\u0001q\u0005!1m\u001c9z)\tq$\tC\u00047\tA\u0005\t\u0019\u0001\u001d\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tQI\u000b\u00029\r.\nq\t\u0005\u0002I\u001b6\t\u0011J\u0003\u0002K\u0017\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003\u0019~\t!\"\u00198o_R\fG/[8o\u0013\tq\u0015JA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A)\u0011\u0005I;V\"A*\u000b\u0005Q+\u0016\u0001\u00027b]\u001eT\u0011AV\u0001\u0005U\u00064\u0018-\u0003\u0002Y'\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002]?B\u0011a$X\u0005\u0003=~\u00111!\u00118z\u0011\u001d\u0001\u0007\"!AA\u0002a\n1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A2\u0011\u0007\u0011<G,D\u0001f\u0015\t1w$\u0001\u0006d_2dWm\u0019;j_:L!\u0001[3\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003W:\u0004\"A\b7\n\u00055|\"a\u0002\"p_2,\u0017M\u001c\u0005\bA*\t\t\u00111\u0001]\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0005E\u000b\bb\u00021\f\u0003\u0003\u0005\r\u0001O\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0001(\u0001\u0005u_N#(/\u001b8h)\u0005\t\u0016AB3rk\u0006d7\u000f\u0006\u0002lq\"9\u0001MDA\u0001\u0002\u0004a\u0006\u0006\u0002\u0001{{z\u0004\"AH>\n\u0005q|\"\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011AD!eUV\u001cH\u000fU8pYNK'0\u001a\t\u0003IA\u0019R\u0001EA\u0003\u0003#\u0001b!a\u0002\u0002\u000earTBAA\u0005\u0015\r\tYaH\u0001\beVtG/[7f\u0013\u0011\ty!!\u0003\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0002\u0014\u0005eQBAA\u000b\u0015\r\t9\"V\u0001\u0003S>L1\u0001NA\u000b)\t\t\t!A\u0003baBd\u0017\u0010F\u0002?\u0003CAQAN\nA\u0002a\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002(\u00055\u0002\u0003\u0002\u0010\u0002*aJ1!a\u000b \u0005\u0019y\u0005\u000f^5p]\"A\u0011q\u0006\u000b\u0002\u0002\u0003\u0007a(A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u000e\u0011\u0007I\u000b9$C\u0002\u0002:M\u0013aa\u00142kK\u000e$\b")
public final class AdjustPoolSize
implements RouterManagementMesssage,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final int change;

    public static Option<Object> unapply(AdjustPoolSize x$0) {
        return AdjustPoolSize$.MODULE$.unapply(x$0);
    }

    public static AdjustPoolSize apply(int change) {
        return AdjustPoolSize$.MODULE$.apply(change);
    }

    public static <A> Function1<Object, A> andThen(Function1<AdjustPoolSize, A> g2) {
        return AdjustPoolSize$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, AdjustPoolSize> compose(Function1<A, Object> g2) {
        return AdjustPoolSize$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int change() {
        return this.change;
    }

    public AdjustPoolSize copy(int change) {
        return new AdjustPoolSize(change);
    }

    public int copy$default$1() {
        return this.change();
    }

    @Override
    public String productPrefix() {
        return "AdjustPoolSize";
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
                return BoxesRunTime.boxToInteger(this.change());
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
        return x$1 instanceof AdjustPoolSize;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "change";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.change());
        return Statics.finalizeHash(n, 1);
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
        if (!(object instanceof AdjustPoolSize)) return false;
        boolean bl = true;
        if (!bl) return false;
        AdjustPoolSize adjustPoolSize = (AdjustPoolSize)x$1;
        if (this.change() != adjustPoolSize.change()) return false;
        return true;
    }

    public AdjustPoolSize(int change) {
        this.change = change;
        Product.$init$(this);
    }
}

