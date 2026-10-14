/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Concat$;
import akka.stream.impl.Traversal;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015tA\u0002\u000e\u001c\u0011\u0003y\u0012E\u0002\u0004$7!\u0005q\u0004\n\u0005\u0006g\u0005!\t!\u000e\u0005\u0006m\u0005!\ta\u000e\u0005\b\u007f\u0005\t\t\u0011\"!A\u0011%\t\u0019%AA\u0001\n\u0003\u000b)\u0005C\u0005\u0002X\u0005\t\t\u0011\"\u0003\u0002Z\u0019)1e\u0007\" \u0005\"AAh\u0002BK\u0002\u0013\u0005\u0011\u000b\u0003\u0005S\u000f\tE\t\u0015!\u00039\u0011!\u0019vA!f\u0001\n\u0003\t\u0006\u0002\u0003+\b\u0005#\u0005\u000b\u0011\u0002\u001d\t\u000bM:A\u0011A+\t\u000ba;A\u0011I-\t\u000f};\u0011\u0011!C\u0001A\"91mBI\u0001\n\u0003!\u0007bB8\b#\u0003%\t\u0001\u001a\u0005\ba\u001e\t\t\u0011\"\u0011r\u0011\u001dAx!!A\u0005\u0002eDqA_\u0004\u0002\u0002\u0013\u00051\u0010C\u0005\u0002\u0004\u001d\t\t\u0011\"\u0011\u0002\u0006!I\u00111C\u0004\u0002\u0002\u0013\u0005\u0011Q\u0003\u0005\n\u0003?9\u0011\u0011!C!\u0003CA\u0011\"!\n\b\u0003\u0003%\t%a\n\t\u0013\u0005%r!!A\u0005B\u0005-\u0002\"CA\u0017\u000f\u0005\u0005I\u0011IA\u0018\u0003\u0019\u0019uN\\2bi*\u0011A$H\u0001\u0005S6\u0004HN\u0003\u0002\u001f?\u000511\u000f\u001e:fC6T\u0011\u0001I\u0001\u0005C.\\\u0017\r\u0005\u0002#\u00035\t1D\u0001\u0004D_:\u001c\u0017\r^\n\u0004\u0003\u0015Z\u0003C\u0001\u0014*\u001b\u00059#\"\u0001\u0015\u0002\u000bM\u001c\u0017\r\\1\n\u0005):#AB!osJ+g\r\u0005\u0002-c5\tQF\u0003\u0002/_\u0005\u0011\u0011n\u001c\u0006\u0002a\u0005!!.\u0019<b\u0013\t\u0011TF\u0001\u0007TKJL\u0017\r\\5{C\ndW-\u0001\u0004=S:LGOP\u0002\u0001)\u0005\t\u0013a\u00048pe6\fG.\u001b>f\u0007>t7-\u0019;\u0015\u0007aZT\b\u0005\u0002#s%\u0011!h\u0007\u0002\n)J\fg/\u001a:tC2DQ\u0001P\u0002A\u0002a\nQAZ5sgRDQAP\u0002A\u0002a\naa]3d_:$\u0017!B1qa2LH#B!\u0002@\u0005\u0005\u0003C\u0001\u0012\b'\u00159Q\u0005O\"G!\t1C)\u0003\u0002FO\t9\u0001K]8ek\u000e$\bCA$P\u001d\tAUJ\u0004\u0002J\u00196\t!J\u0003\u0002Li\u00051AH]8pizJ\u0011\u0001K\u0005\u0003\u001d\u001e\nq\u0001]1dW\u0006<W-\u0003\u00023!*\u0011ajJ\u000b\u0002q\u00051a-\u001b:ti\u0002\nAA\\3yi\u0006)a.\u001a=uAQ\u0019\u0011IV,\t\u000bqb\u0001\u0019\u0001\u001d\t\u000bMc\u0001\u0019\u0001\u001d\u0002\u001bI,w/\u001b:f\r&\u00148\u000f\u001e+p)\tA$\fC\u0003\\\u001b\u0001\u0007A,\u0001\bsK2\fG/\u001b<f\u001f\u001a47/\u001a;\u0011\u0005\u0019j\u0016B\u00010(\u0005\rIe\u000e^\u0001\u0005G>\u0004\u0018\u0010F\u0002BC\nDq\u0001\u0010\b\u0011\u0002\u0003\u0007\u0001\bC\u0004T\u001dA\u0005\t\u0019\u0001\u001d\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tQM\u000b\u00029M.\nq\r\u0005\u0002i[6\t\u0011N\u0003\u0002kW\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003Y\u001e\n!\"\u00198o_R\fG/[8o\u0013\tq\u0017NA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002eB\u00111O^\u0007\u0002i*\u0011QoL\u0001\u0005Y\u0006tw-\u0003\u0002xi\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012\u0001X\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\tax\u0010\u0005\u0002'{&\u0011ap\n\u0002\u0004\u0003:L\b\u0002CA\u0001'\u0005\u0005\t\u0019\u0001/\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t9\u0001E\u0003\u0002\n\u0005=A0\u0004\u0002\u0002\f)\u0019\u0011QB\u0014\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\u0012\u0005-!\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a\u0006\u0002\u001eA\u0019a%!\u0007\n\u0007\u0005mqEA\u0004C_>dW-\u00198\t\u0011\u0005\u0005Q#!AA\u0002q\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019!/a\t\t\u0011\u0005\u0005a#!AA\u0002q\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u00029\u0006AAo\\*ue&tw\rF\u0001s\u0003\u0019)\u0017/^1mgR!\u0011qCA\u0019\u0011!\t\t!GA\u0001\u0002\u0004a\bfA\u0004\u00026A!\u0011qGA\u001e\u001b\t\tID\u0003\u0002m?%!\u0011QHA\u001d\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\t\u000bq\"\u0001\u0019\u0001\u001d\t\u000bM#\u0001\u0019\u0001\u001d\u0002\u000fUt\u0017\r\u001d9msR!\u0011qIA*!\u00151\u0013\u0011JA'\u0013\r\tYe\n\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000b\u0019\ny\u0005\u000f\u001d\n\u0007\u0005EsE\u0001\u0004UkBdWM\r\u0005\t\u0003+*\u0011\u0011!a\u0001\u0003\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005m\u0003cA:\u0002^%\u0019\u0011q\f;\u0003\r=\u0013'.Z2uQ\r\t\u0011Q\u0007\u0015\u0004\u0001\u0005U\u0002")
@InternalApi
public final class Concat
implements Traversal,
Product,
Serializable {
    private final Traversal first;
    private final Traversal next;

    public static Option<Tuple2<Traversal, Traversal>> unapply(Concat x$0) {
        return Concat$.MODULE$.unapply(x$0);
    }

    public static Concat apply(Traversal first, Traversal next) {
        return Concat$.MODULE$.apply(first, next);
    }

    public static Traversal normalizeConcat(Traversal first, Traversal second) {
        return Concat$.MODULE$.normalizeConcat(first, second);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Traversal concat(Traversal that) {
        return Traversal.concat$(this, that);
    }

    public Traversal first() {
        return this.first;
    }

    public Traversal next() {
        return this.next;
    }

    @Override
    public Traversal rewireFirstTo(int relativeOffset) {
        Traversal firstResult = this.first().rewireFirstTo(relativeOffset);
        if (firstResult != null) {
            return firstResult.concat(this.next());
        }
        return this.first().concat(this.next().rewireFirstTo(relativeOffset));
    }

    public Concat copy(Traversal first, Traversal next) {
        return new Concat(first, next);
    }

    public Traversal copy$default$1() {
        return this.first();
    }

    public Traversal copy$default$2() {
        return this.next();
    }

    @Override
    public String productPrefix() {
        return "Concat";
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
                return this.next();
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
        return x$1 instanceof Concat;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "first";
            }
            case 1: {
                return "next";
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
        if (!(object instanceof Concat)) return false;
        boolean bl = true;
        if (!bl) return false;
        Concat concat = (Concat)x$1;
        Traversal traversal = this.first();
        Traversal traversal2 = concat.first();
        if (traversal == null) {
            if (traversal2 != null) {
                return false;
            }
        } else if (!traversal.equals(traversal2)) return false;
        Traversal traversal3 = this.next();
        Traversal traversal4 = concat.next();
        if (traversal3 == null) {
            if (traversal4 == null) return true;
            return false;
        } else {
            if (!traversal3.equals(traversal4)) return false;
            return true;
        }
    }

    public Concat(Traversal first, Traversal next) {
        this.first = first;
        this.next = next;
        Traversal.$init$(this);
        Product.$init$(this);
    }
}

