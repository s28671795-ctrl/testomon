/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.Routee;
import akka.routing.Routees$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.List;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005mc\u0001B\f\u0019\u0005vA\u0001b\r\u0001\u0003\u0016\u0004%\t\u0001\u000e\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005k!)!\t\u0001C\u0001\u0007\")a\t\u0001C\u0001\u000f\"9\u0001\u000bAA\u0001\n\u0003\t\u0006bB*\u0001#\u0003%\t\u0001\u0016\u0005\b?\u0002\t\t\u0011\"\u0011a\u0011\u001d9\u0007!!A\u0005\u0002!Dq\u0001\u001c\u0001\u0002\u0002\u0013\u0005Q\u000eC\u0004t\u0001\u0005\u0005I\u0011\t;\t\u000fe\u0004\u0011\u0011!C\u0001u\"Aq\u0010AA\u0001\n\u0003\n\t\u0001C\u0005\u0002\u0006\u0001\t\t\u0011\"\u0011\u0002\b!I\u0011\u0011\u0002\u0001\u0002\u0002\u0013\u0005\u00131\u0002\u0005\n\u0003\u001b\u0001\u0011\u0011!C!\u0003\u001f9\u0011\"a\b\u0019\u0003\u0003E\t!!\t\u0007\u0011]A\u0012\u0011!E\u0001\u0003GAaAQ\t\u0005\u0002\u0005m\u0002\"CA\u0005#\u0005\u0005IQIA\u0006\u0011%\ti$EA\u0001\n\u0003\u000by\u0004C\u0005\u0002DE\t\t\u0011\"!\u0002F!I\u0011\u0011K\t\u0002\u0002\u0013%\u00111\u000b\u0002\b%>,H/Z3t\u0015\tI\"$A\u0004s_V$\u0018N\\4\u000b\u0003m\tA!Y6lC\u000e\u00011\u0003\u0002\u0001\u001fI\u001d\u0002\"a\b\u0012\u000e\u0003\u0001R\u0011!I\u0001\u0006g\u000e\fG.Y\u0005\u0003G\u0001\u0012a!\u00118z%\u00164\u0007CA\u0010&\u0013\t1\u0003EA\u0004Qe>$Wo\u0019;\u0011\u0005!\u0002dBA\u0015/\u001d\tQS&D\u0001,\u0015\taC$\u0001\u0004=e>|GOP\u0005\u0002C%\u0011q\u0006I\u0001\ba\u0006\u001c7.Y4f\u0013\t\t$G\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00020A\u00059!o\\;uK\u0016\u001cX#A\u001b\u0011\u0007YZT(D\u00018\u0015\tA\u0014(A\u0005j[6,H/\u00192mK*\u0011!\bI\u0001\u000bG>dG.Z2uS>t\u0017B\u0001\u001f8\u0005)Ie\u000eZ3yK\u0012\u001cV-\u001d\t\u0003}}j\u0011\u0001G\u0005\u0003\u0001b\u0011aAU8vi\u0016,\u0017\u0001\u0003:pkR,Wm\u001d\u0011\u0002\rqJg.\u001b;?)\t!U\t\u0005\u0002?\u0001!)1g\u0001a\u0001k\u0005Qq-\u001a;S_V$X-Z:\u0016\u0003!\u00032!\u0013(>\u001b\u0005Q%BA&M\u0003\u0011)H/\u001b7\u000b\u00035\u000bAA[1wC&\u0011qJ\u0013\u0002\u0005\u0019&\u001cH/\u0001\u0003d_BLHC\u0001#S\u0011\u001d\u0019T\u0001%AA\u0002U\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001VU\t)dkK\u0001X!\tAV,D\u0001Z\u0015\tQ6,A\u0005v]\u000eDWmY6fI*\u0011A\fI\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00010Z\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003\u0005\u0004\"AY3\u000e\u0003\rT!\u0001\u001a'\u0002\t1\fgnZ\u0005\u0003M\u000e\u0014aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLX#A5\u0011\u0005}Q\u0017BA6!\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\tq\u0017\u000f\u0005\u0002 _&\u0011\u0001\u000f\t\u0002\u0004\u0003:L\bb\u0002:\n\u0003\u0003\u0005\r![\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003U\u00042A^<o\u001b\u0005I\u0014B\u0001=:\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005mt\bCA\u0010}\u0013\ti\bEA\u0004C_>dW-\u00198\t\u000fI\\\u0011\u0011!a\u0001]\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\r\t\u00171\u0001\u0005\be2\t\t\u00111\u0001j\u0003!A\u0017m\u001d5D_\u0012,G#A5\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012!Y\u0001\u0007KF,\u0018\r\\:\u0015\u0007m\f\t\u0002C\u0004s\u001f\u0005\u0005\t\u0019\u00018)\u000f\u0001\t)\"a\u0007\u0002\u001eA\u0019q$a\u0006\n\u0007\u0005e\u0001E\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!A\u0004S_V$X-Z:\u0011\u0005y\n2#B\t\u0002&\u0005E\u0002CBA\u0014\u0003[)D)\u0004\u0002\u0002*)\u0019\u00111\u0006\u0011\u0002\u000fI,h\u000e^5nK&!\u0011qFA\u0015\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003g\tI$\u0004\u0002\u00026)\u0019\u0011q\u0007'\u0002\u0005%|\u0017bA\u0019\u00026Q\u0011\u0011\u0011E\u0001\u0006CB\u0004H.\u001f\u000b\u0004\t\u0006\u0005\u0003\"B\u001a\u0015\u0001\u0004)\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0003\u000f\ni\u0005\u0005\u0003 \u0003\u0013*\u0014bAA&A\t1q\n\u001d;j_:D\u0001\"a\u0014\u0016\u0003\u0003\u0005\r\u0001R\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA+!\r\u0011\u0017qK\u0005\u0004\u00033\u001a'AB(cU\u0016\u001cG\u000f")
public final class Routees
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final IndexedSeq<Routee> routees;

    public static Option<IndexedSeq<Routee>> unapply(Routees x$0) {
        return Routees$.MODULE$.unapply(x$0);
    }

    public static Routees apply(IndexedSeq<Routee> routees) {
        return Routees$.MODULE$.apply(routees);
    }

    public static <A> Function1<IndexedSeq<Routee>, A> andThen(Function1<Routees, A> g2) {
        return Routees$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, Routees> compose(Function1<A, IndexedSeq<Routee>> g2) {
        return Routees$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public IndexedSeq<Routee> routees() {
        return this.routees;
    }

    public List<Routee> getRoutees() {
        return package$JavaConverters$.MODULE$.SeqHasAsJava(this.routees()).asJava();
    }

    public Routees copy(IndexedSeq<Routee> routees) {
        return new Routees(routees);
    }

    public IndexedSeq<Routee> copy$default$1() {
        return this.routees();
    }

    @Override
    public String productPrefix() {
        return "Routees";
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
                return this.routees();
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
        return x$1 instanceof Routees;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "routees";
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
        if (!(object instanceof Routees)) return false;
        boolean bl = true;
        if (!bl) return false;
        Routees routees = (Routees)x$1;
        IndexedSeq<Routee> indexedSeq = this.routees();
        IndexedSeq<Routee> indexedSeq2 = routees.routees();
        if (indexedSeq != null) {
            if (!indexedSeq.equals(indexedSeq2)) return false;
            return true;
        }
        if (indexedSeq2 == null) return true;
        return false;
    }

    public Routees(IndexedSeq<Routee> routees) {
        this.routees = routees;
        Product.$init$(this);
    }
}

