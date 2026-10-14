/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SelectChildName$;
import akka.actor.SelectionPathElement;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055c!\u0002\f\u0018\u0005fY\u0002\u0002\u0003\u001c\u0001\u0005+\u0007I\u0011A\u001c\t\u0011\u0001\u0003!\u0011#Q\u0001\naBQ!\u0011\u0001\u0005\u0002\tCQ!\u0012\u0001\u0005B\u0019Cqa\u0012\u0001\u0002\u0002\u0013\u0005\u0001\nC\u0004K\u0001E\u0005I\u0011A&\t\u000fY\u0003\u0011\u0011!C!/\"9q\fAA\u0001\n\u0003\u0001\u0007b\u00023\u0001\u0003\u0003%\t!\u001a\u0005\bW\u0002\t\t\u0011\"\u0011m\u0011\u001d\u0019\b!!A\u0005\u0002QDq!\u001f\u0001\u0002\u0002\u0013\u0005#\u0010C\u0004}\u0001\u0005\u0005I\u0011I?\t\u000fy\u0004\u0011\u0011!C!\u007f\u001eQ\u0011qB\f\u0002\u0002#\u0005\u0011$!\u0005\u0007\u0013Y9\u0012\u0011!E\u00013\u0005M\u0001BB!\u0011\t\u0003\tY\u0003\u0003\u0005F!\u0005\u0005IQIA\u0017\u0011%\ty\u0003EA\u0001\n\u0003\u000b\t\u0004C\u0005\u00026A\t\t\u0011\"!\u00028!I\u00111\t\t\u0002\u0002\u0013%\u0011Q\t\u0002\u0010'\u0016dWm\u0019;DQ&dGMT1nK*\u0011\u0001$G\u0001\u0006C\u000e$xN\u001d\u0006\u00025\u0005!\u0011m[6b'\u0015\u0001AD\t\u0014*!\ti\u0002%D\u0001\u001f\u0015\u0005y\u0012!B:dC2\f\u0017BA\u0011\u001f\u0005\u0019\te.\u001f*fMB\u00111\u0005J\u0007\u0002/%\u0011Qe\u0006\u0002\u0015'\u0016dWm\u0019;j_:\u0004\u0016\r\u001e5FY\u0016lWM\u001c;\u0011\u0005u9\u0013B\u0001\u0015\u001f\u0005\u001d\u0001&o\u001c3vGR\u0004\"AK\u001a\u000f\u0005-\ndB\u0001\u00171\u001b\u0005i#B\u0001\u00180\u0003\u0019a$o\\8u}\r\u0001\u0011\"A\u0010\n\u0005Ir\u0012a\u00029bG.\fw-Z\u0005\u0003iU\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!A\r\u0010\u0002\t9\fW.Z\u000b\u0002qA\u0011\u0011(\u0010\b\u0003um\u0002\"\u0001\f\u0010\n\u0005qr\u0012A\u0002)sK\u0012,g-\u0003\u0002?\u007f\t11\u000b\u001e:j]\u001eT!\u0001\u0010\u0010\u0002\u000b9\fW.\u001a\u0011\u0002\rqJg.\u001b;?)\t\u0019E\t\u0005\u0002$\u0001!)ag\u0001a\u0001q\u0005AAo\\*ue&tw\rF\u00019\u0003\u0011\u0019w\u000e]=\u0015\u0005\rK\u0005b\u0002\u001c\u0006!\u0003\u0005\r\u0001O\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005a%F\u0001\u001dNW\u0005q\u0005CA(U\u001b\u0005\u0001&BA)S\u0003%)hn\u00195fG.,GM\u0003\u0002T=\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005U\u0003&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012\u0001\u0017\t\u00033zk\u0011A\u0017\u0006\u00037r\u000bA\u0001\\1oO*\tQ,\u0001\u0003kCZ\f\u0017B\u0001 [\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005\t\u0007CA\u000fc\u0013\t\u0019gDA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002gSB\u0011QdZ\u0005\u0003Qz\u00111!\u00118z\u0011\u001dQ\u0017\"!AA\u0002\u0005\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A7\u0011\u00079\fh-D\u0001p\u0015\t\u0001h$\u0001\u0006d_2dWm\u0019;j_:L!A]8\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003kb\u0004\"!\b<\n\u0005]t\"a\u0002\"p_2,\u0017M\u001c\u0005\bU.\t\t\u00111\u0001g\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0005a[\bb\u00026\r\u0003\u0003\u0005\r!Y\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0011-\u0001\u0004fcV\fGn\u001d\u000b\u0004k\u0006\u0005\u0001b\u00026\u000f\u0003\u0003\u0005\rA\u001a\u0015\b\u0001\u0005\u0015\u00111BA\u0007!\ri\u0012qA\u0005\u0004\u0003\u0013q\"\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0011\u0011aD*fY\u0016\u001cGo\u00115jY\u0012t\u0015-\\3\u0011\u0005\r\u00022#\u0002\t\u0002\u0016\u0005\u0005\u0002CBA\f\u0003;A4)\u0004\u0002\u0002\u001a)\u0019\u00111\u0004\u0010\u0002\u000fI,h\u000e^5nK&!\u0011qDA\r\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003G\tI#\u0004\u0002\u0002&)\u0019\u0011q\u0005/\u0002\u0005%|\u0017b\u0001\u001b\u0002&Q\u0011\u0011\u0011\u0003\u000b\u00021\u0006)\u0011\r\u001d9msR\u00191)a\r\t\u000bY\u001a\u0002\u0019\u0001\u001d\u0002\u000fUt\u0017\r\u001d9msR!\u0011\u0011HA !\u0011i\u00121\b\u001d\n\u0007\u0005ubD\u0001\u0004PaRLwN\u001c\u0005\t\u0003\u0003\"\u0012\u0011!a\u0001\u0007\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u001d\u0003cA-\u0002J%\u0019\u00111\n.\u0003\r=\u0013'.Z2u\u0001")
public final class SelectChildName
implements SelectionPathElement,
Product,
Serializable {
    private static final long serialVersionUID = 2L;
    private final String name;

    public static Option<String> unapply(SelectChildName x$0) {
        return SelectChildName$.MODULE$.unapply(x$0);
    }

    public static SelectChildName apply(String name) {
        return SelectChildName$.MODULE$.apply(name);
    }

    public static <A> Function1<String, A> andThen(Function1<SelectChildName, A> g2) {
        return SelectChildName$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, SelectChildName> compose(Function1<A, String> g2) {
        return SelectChildName$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String name() {
        return this.name;
    }

    public String toString() {
        return this.name();
    }

    public SelectChildName copy(String name) {
        return new SelectChildName(name);
    }

    public String copy$default$1() {
        return this.name();
    }

    @Override
    public String productPrefix() {
        return "SelectChildName";
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
                return this.name();
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
        return x$1 instanceof SelectChildName;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "name";
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
        if (!(object instanceof SelectChildName)) return false;
        boolean bl = true;
        if (!bl) return false;
        SelectChildName selectChildName = (SelectChildName)x$1;
        String string = this.name();
        String string2 = selectChildName.name();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public SelectChildName(String name) {
        this.name = name;
        Product.$init$(this);
    }
}

