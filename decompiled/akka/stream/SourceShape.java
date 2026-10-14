/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.Shape;
import akka.stream.SourceShape$;
import akka.util.Collections$EmptyImmutableSeq$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005uf\u0001B\u000e\u001d\u0005\u0006B\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t'\u0002\u0011\t\u0012)A\u0005w!)A\u000b\u0001C\u0001+\"9\u0001\f\u0001b\u0001\n\u0003J\u0006BB5\u0001A\u0003%!\fC\u0004k\u0001\t\u0007I\u0011I6\t\rI\u0004\u0001\u0015!\u0003m\u0011\u0015\u0019\b\u0001\"\u0011u\u0011\u001d)\b!!A\u0005\u0002YDqA \u0001\u0012\u0002\u0013\u0005q\u0010C\u0005\u0002\n\u0001\t\t\u0011\"\u0011\u0002\f!I\u0011Q\u0004\u0001\u0002\u0002\u0013\u0005\u0011q\u0004\u0005\n\u0003O\u0001\u0011\u0011!C\u0001\u0003SA\u0011\"a\f\u0001\u0003\u0003%\t%!\r\t\u0013\u0005m\u0002!!A\u0005\u0002\u0005u\u0002\"CA$\u0001\u0005\u0005I\u0011IA%\u0011%\ti\u0005AA\u0001\n\u0003\ny\u0005C\u0005\u0002R\u0001\t\t\u0011\"\u0011\u0002T!I\u0011Q\u000b\u0001\u0002\u0002\u0013\u0005\u0013qK\u0004\b\u00037b\u0002\u0012AA/\r\u0019YB\u0004#\u0001\u0002`!1A+\u0006C\u0001\u0003cBq!a\u001d\u0016\t\u0003\t)\bC\u0005\u0002\bV\t\t\u0011\"!\u0002\n\"I\u0011\u0011T\u000b\u0002\u0002\u0013\u0005\u00151\u0014\u0005\n\u0003g+\u0012\u0011!C\u0005\u0003k\u00131bU8ve\u000e,7\u000b[1qK*\u0011QDH\u0001\u0007gR\u0014X-Y7\u000b\u0003}\tA!Y6lC\u000e\u0001QC\u0001\u0012B'\u0011\u00011eJ\u0017\u0011\u0005\u0011*S\"\u0001\u000f\n\u0005\u0019b\"!B*iCB,\u0007C\u0001\u0015,\u001b\u0005I#\"\u0001\u0016\u0002\u000bM\u001c\u0017\r\\1\n\u00051J#a\u0002)s_\u0012,8\r\u001e\t\u0003]Yr!a\f\u001b\u000f\u0005A\u001aT\"A\u0019\u000b\u0005I\u0002\u0013A\u0002\u001fs_>$h(C\u0001+\u0013\t)\u0014&A\u0004qC\u000e\\\u0017mZ3\n\u0005]B$\u0001D*fe&\fG.\u001b>bE2,'BA\u001b*\u0003\ryW\u000f^\u000b\u0002wA\u0019A\u0005\u0010 \n\u0005ub\"AB(vi2,GO\u000b\u0002@\u0015B\u0011\u0001)\u0011\u0007\u0001\t\u0019\u0011\u0005\u0001\"b\u0001\u0007\n\tA+\u0005\u0002E\u000fB\u0011\u0001&R\u0005\u0003\r&\u0012qAT8uQ&tw\r\u0005\u0002)\u0011&\u0011\u0011*\u000b\u0002\u0004\u0003:L8&A&\u0011\u00051\u000bV\"A'\u000b\u00059{\u0015!C;oG\",7m[3e\u0015\t\u0001\u0016&\u0001\u0006b]:|G/\u0019;j_:L!AU'\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0003pkR\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002W/B\u0019A\u0005A \t\u000be\u001a\u0001\u0019A\u001e\u0002\r%tG.\u001a;t+\u0005Q\u0006cA.aE6\tAL\u0003\u0002^=\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003?&\n!bY8mY\u0016\u001cG/[8o\u0013\t\tGLA\u0002TKF\u0004$aY4\u0011\u0007\u0011\"g-\u0003\u0002f9\t)\u0011J\u001c7fiB\u0011\u0001i\u001a\u0003\nQ\u0016\t\t\u0011!A\u0003\u0002\r\u0013Aa\u0018\u00132q\u00059\u0011N\u001c7fiN\u0004\u0013aB8vi2,Go]\u000b\u0002YB\u00191\fY71\u00059\u0004\bc\u0001\u0013=_B\u0011\u0001\t\u001d\u0003\nc\u001e\t\t\u0011!A\u0003\u0002\r\u0013Aa\u0018\u00132s\u0005Aq.\u001e;mKR\u001c\b%\u0001\u0005eK\u0016\u00048i\u001c9z)\u00051\u0016\u0001B2paf,\"a\u001e>\u0015\u0005a\\\bc\u0001\u0013\u0001sB\u0011\u0001I\u001f\u0003\u0006\u0005&\u0011\ra\u0011\u0005\bs%\u0001\n\u00111\u0001}!\r!C( \u0016\u0003s*\u000babY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0003\u0002\u0002\u0005\u001dQCAA\u0002U\r\t)A\u0013\t\u0004IqzD!\u0002\"\u000b\u0005\u0004\u0019\u0015!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002\u000eA!\u0011qBA\r\u001b\t\t\tB\u0003\u0003\u0002\u0014\u0005U\u0011\u0001\u00027b]\u001eT!!a\u0006\u0002\t)\fg/Y\u0005\u0005\u00037\t\tB\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003C\u00012\u0001KA\u0012\u0013\r\t)#\u000b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004\u000f\u0006-\u0002\"CA\u0017\u001b\u0005\u0005\t\u0019AA\u0011\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\u0007\t\u0006\u0003k\t9dR\u0007\u0002=&\u0019\u0011\u0011\b0\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u007f\t)\u0005E\u0002)\u0003\u0003J1!a\u0011*\u0005\u001d\u0011un\u001c7fC:D\u0001\"!\f\u0010\u0003\u0003\u0005\raR\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002\u000e\u0005-\u0003\"CA\u0017!\u0005\u0005\t\u0019AA\u0011\u0003!A\u0017m\u001d5D_\u0012,GCAA\u0011\u0003!!xn\u0015;sS:<GCAA\u0007\u0003\u0019)\u0017/^1mgR!\u0011qHA-\u0011!\ticEA\u0001\u0002\u00049\u0015aC*pkJ\u001cWm\u00155ba\u0016\u0004\"\u0001J\u000b\u0014\u000bU\t\t'a\u001a\u0011\u0007!\n\u0019'C\u0002\u0002f%\u0012a!\u00118z%\u00164\u0007\u0003BA5\u0003_j!!a\u001b\u000b\t\u00055\u0014QC\u0001\u0003S>L1aNA6)\t\ti&\u0001\u0002pMV!\u0011qOA?)\u0011\tI(a \u0011\t\u0011\u0002\u00111\u0010\t\u0004\u0001\u0006uD!\u0002\"\u0018\u0005\u0004\u0019\u0005bBAA/\u0001\u0007\u00111Q\u0001\u0007_V$H.\u001a;\u0011\t\u0011b\u0014Q\u0011\u0016\u0004\u0003wR\u0015!B1qa2LX\u0003BAF\u0003##B!!$\u0002\u0014B!A\u0005AAH!\r\u0001\u0015\u0011\u0013\u0003\u0006\u0005b\u0011\ra\u0011\u0005\u0007sa\u0001\r!!&\u0011\t\u0011b\u0014q\u0013\u0016\u0004\u0003\u001fS\u0015aB;oCB\u0004H._\u000b\u0005\u0003;\u000bY\u000b\u0006\u0003\u0002 \u00065\u0006#\u0002\u0015\u0002\"\u0006\u0015\u0016bAARS\t1q\n\u001d;j_:\u0004B\u0001\n\u001f\u0002(*\u001a\u0011\u0011\u0016&\u0011\u0007\u0001\u000bY\u000bB\u0003C3\t\u00071\tC\u0005\u00020f\t\t\u00111\u0001\u00022\u0006\u0019\u0001\u0010\n\u0019\u0011\t\u0011\u0002\u0011\u0011V\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003o\u0003B!a\u0004\u0002:&!\u00111XA\t\u0005\u0019y%M[3di\u0002")
public final class SourceShape<T>
extends Shape
implements Product,
Serializable {
    private final Outlet<T> out;
    private final Seq<Inlet<?>> inlets;
    private final Seq<Outlet<?>> outlets;

    public static <T> Option<Outlet<T>> unapply(SourceShape<T> x$0) {
        return SourceShape$.MODULE$.unapply(x$0);
    }

    public static <T> SourceShape<T> apply(Outlet<T> out) {
        return SourceShape$.MODULE$.apply(out);
    }

    public static <T> SourceShape<T> of(Outlet<T> outlet) {
        return SourceShape$.MODULE$.of(outlet);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Outlet<T> out() {
        return this.out;
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
    public SourceShape<T> deepCopy() {
        return new SourceShape<T>(this.out().carbonCopy());
    }

    public <T> SourceShape<T> copy(Outlet<T> out) {
        return new SourceShape<T>(out);
    }

    public <T> Outlet<T> copy$default$1() {
        return this.out();
    }

    @Override
    public String productPrefix() {
        return "SourceShape";
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
                return this.out();
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
        return x$1 instanceof SourceShape;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "out";
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
        if (!(object instanceof SourceShape)) return false;
        boolean bl = true;
        if (!bl) return false;
        SourceShape sourceShape = (SourceShape)x$1;
        Outlet<T> outlet = this.out();
        Outlet<T> outlet2 = sourceShape.out();
        if (outlet != null) {
            if (!((Object)outlet).equals(outlet2)) return false;
            return true;
        }
        if (outlet2 == null) return true;
        return false;
    }

    public SourceShape(Outlet<T> out) {
        this.out = out;
        Product.$init$(this);
        this.inlets = Collections$EmptyImmutableSeq$.MODULE$;
        Outlet<T> outlet = out;
        this.outlets = Nil$.MODULE$.$colon$colon(outlet);
    }
}

