/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FlowShape$;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.Shape;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tMa\u0001\u0002\u0010 \u0005\u0012B\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t-\u0002\u0011\t\u0012)A\u0005}!Aq\u000b\u0001BK\u0002\u0013\u0005\u0001\f\u0003\u0005a\u0001\tE\t\u0015!\u0003Z\u0011\u0015\t\u0007\u0001\"\u0001c\u0011\u001d1\u0007A1A\u0005B\u001dDa!\u001e\u0001!\u0002\u0013A\u0007b\u0002<\u0001\u0005\u0004%\te\u001e\u0005\u0007}\u0002\u0001\u000b\u0011\u0002=\t\r}\u0004A\u0011IA\u0001\u0011%\t\u0019\u0001AA\u0001\n\u0003\t)\u0001C\u0005\u0002 \u0001\t\n\u0011\"\u0001\u0002\"!I\u0011Q\u0006\u0001\u0012\u0002\u0013\u0005\u0011q\u0006\u0005\n\u0003w\u0001\u0011\u0011!C!\u0003{A\u0011\"a\u0014\u0001\u0003\u0003%\t!!\u0015\t\u0013\u0005e\u0003!!A\u0005\u0002\u0005m\u0003\"CA1\u0001\u0005\u0005I\u0011IA2\u0011%\ti\u0007AA\u0001\n\u0003\ty\u0007C\u0005\u0002z\u0001\t\t\u0011\"\u0011\u0002|!I\u0011q\u0010\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0011\u0005\n\u0003\u0007\u0003\u0011\u0011!C!\u0003\u000bC\u0011\"a\"\u0001\u0003\u0003%\t%!#\b\u000f\u00055u\u0004#\u0001\u0002\u0010\u001a1ad\bE\u0001\u0003#Ca!\u0019\r\u0005\u0002\u0005\r\u0006bBAS1\u0011\u0005\u0011q\u0015\u0005\n\u0003\u000bD\u0012\u0011!CA\u0003\u000fD\u0011\"!9\u0019\u0003\u0003%\t)a9\t\u0013\t%\u0001$!A\u0005\n\t-!!\u0003$m_^\u001c\u0006.\u00199f\u0015\t\u0001\u0013%\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002E\u0005!\u0011m[6b\u0007\u0001)2!\n#_'\u0011\u0001aE\u000b\u0019\u0011\u0005\u001dBS\"A\u0010\n\u0005%z\"!B*iCB,\u0007CA\u0016/\u001b\u0005a#\"A\u0017\u0002\u000bM\u001c\u0017\r\\1\n\u0005=b#a\u0002)s_\u0012,8\r\u001e\t\u0003cer!AM\u001c\u000f\u0005M2T\"\u0001\u001b\u000b\u0005U\u001a\u0013A\u0002\u001fs_>$h(C\u0001.\u0013\tAD&A\u0004qC\u000e\\\u0017mZ3\n\u0005iZ$\u0001D*fe&\fG.\u001b>bE2,'B\u0001\u001d-\u0003\tIg.F\u0001?!\r9s(Q\u0005\u0003\u0001~\u0011Q!\u00138mKRT#AQ'\u0011\u0005\r#E\u0002\u0001\u0003\u0007\u000b\u0002A)\u0019\u0001$\u0003\u0003%\u000b\"a\u0012&\u0011\u0005-B\u0015BA%-\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aK&\n\u00051c#aA!os.\na\n\u0005\u0002P)6\t\u0001K\u0003\u0002R%\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003'2\n!\"\u00198o_R\fG/[8o\u0013\t)\u0006KA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\f1!\u001b8!\u0003\ryW\u000f^\u000b\u00023B\u0019qE\u0017/\n\u0005m{\"AB(vi2,GO\u000b\u0002^\u001bB\u00111I\u0018\u0003\u0007?\u0002!)\u0019\u0001$\u0003\u0003=\u000bAa\\;uA\u00051A(\u001b8jiz\"2a\u00193f!\u00119\u0003AQ/\t\u000bq*\u0001\u0019\u0001 \t\u000b]+\u0001\u0019A-\u0002\r%tG.\u001a;t+\u0005A\u0007cA5oa6\t!N\u0003\u0002lY\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003[2\n!bY8mY\u0016\u001cG/[8o\u0013\ty'NA\u0002TKF\u0004$!]:\u0011\u0007\u001dz$\u000f\u0005\u0002Dg\u0012IAoBA\u0001\u0002\u0003\u0015\tA\u0012\u0002\u0005?\u0012\u0012\u0004'A\u0004j]2,Go\u001d\u0011\u0002\u000f=,H\u000f\\3ugV\t\u0001\u0010E\u0002j]f\u0004$A\u001f?\u0011\u0007\u001dR6\u0010\u0005\u0002Dy\u0012IQ0CA\u0001\u0002\u0003\u0015\tA\u0012\u0002\u0005?\u0012\u0012\u0014'\u0001\u0005pkRdW\r^:!\u0003!!W-\u001a9D_BLH#A2\u0002\t\r|\u0007/_\u000b\u0007\u0003\u000f\ti!!\u0005\u0015\r\u0005%\u00111CA\r!\u00199\u0003!a\u0003\u0002\u0010A\u00191)!\u0004\u0005\u000b\u0015[!\u0019\u0001$\u0011\u0007\r\u000b\t\u0002B\u0003`\u0017\t\u0007a\t\u0003\u0005=\u0017A\u0005\t\u0019AA\u000b!\u00119s(a\u0006+\u0007\u0005-Q\n\u0003\u0005X\u0017A\u0005\t\u0019AA\u000e!\u00119#,!\b+\u0007\u0005=Q*\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\r\u0005\r\u0012\u0011FA\u0016+\t\t)CK\u0002\u0002(5\u00032aJ C\t\u0015)EB1\u0001G\t\u0015yFB1\u0001G\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*b!!\r\u00028\u0005eRCAA\u001aU\r\t)$\u0014\t\u0004OikF!B#\u000e\u0005\u00041E!B0\u000e\u0005\u00041\u0015!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002@A!\u0011\u0011IA&\u001b\t\t\u0019E\u0003\u0003\u0002F\u0005\u001d\u0013\u0001\u00027b]\u001eT!!!\u0013\u0002\t)\fg/Y\u0005\u0005\u0003\u001b\n\u0019E\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003'\u00022aKA+\u0013\r\t9\u0006\f\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004\u0015\u0006u\u0003\"CA0!\u0005\u0005\t\u0019AA*\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\r\t\u0006\u0003O\nIGS\u0007\u0002Y&\u0019\u00111\u000e7\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003c\n9\bE\u0002,\u0003gJ1!!\u001e-\u0005\u001d\u0011un\u001c7fC:D\u0001\"a\u0018\u0013\u0003\u0003\u0005\rAS\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002@\u0005u\u0004\"CA0'\u0005\u0005\t\u0019AA*\u0003!A\u0017m\u001d5D_\u0012,GCAA*\u0003!!xn\u0015;sS:<GCAA \u0003\u0019)\u0017/^1mgR!\u0011\u0011OAF\u0011!\tyFFA\u0001\u0002\u0004Q\u0015!\u0003$m_^\u001c\u0006.\u00199f!\t9\u0003dE\u0003\u0019\u0003'\u000bI\nE\u0002,\u0003+K1!a&-\u0005\u0019\te.\u001f*fMB!\u00111TAQ\u001b\t\tiJ\u0003\u0003\u0002 \u0006\u001d\u0013AA5p\u0013\rQ\u0014Q\u0014\u000b\u0003\u0003\u001f\u000b!a\u001c4\u0016\r\u0005%\u0016qVAZ)\u0019\tY+!.\u0002>B1q\u0005AAW\u0003c\u00032aQAX\t\u0015)%D1\u0001G!\r\u0019\u00151\u0017\u0003\u0006?j\u0011\rA\u0012\u0005\b\u0003oS\u0002\u0019AA]\u0003\u0015Ig\u000e\\3u!\u00119s(a/+\u0007\u00055V\nC\u0004\u0002@j\u0001\r!!1\u0002\r=,H\u000f\\3u!\u00119#,a1+\u0007\u0005EV*A\u0003baBd\u00170\u0006\u0004\u0002J\u0006=\u00171\u001b\u000b\u0007\u0003\u0017\f).a7\u0011\r\u001d\u0002\u0011QZAi!\r\u0019\u0015q\u001a\u0003\u0006\u000bn\u0011\rA\u0012\t\u0004\u0007\u0006MG!B0\u001c\u0005\u00041\u0005B\u0002\u001f\u001c\u0001\u0004\t9\u000e\u0005\u0003(\u007f\u0005e'fAAg\u001b\"1qk\u0007a\u0001\u0003;\u0004Ba\n.\u0002`*\u001a\u0011\u0011['\u0002\u000fUt\u0017\r\u001d9msV1\u0011Q]A}\u0005\u0003!B!a:\u0003\u0004A)1&!;\u0002n&\u0019\u00111\u001e\u0017\u0003\r=\u0003H/[8o!\u001dY\u0013q^Az\u0003wL1!!=-\u0005\u0019!V\u000f\u001d7feA!qePA{U\r\t90\u0014\t\u0004\u0007\u0006eH!B#\u001d\u0005\u00041\u0005\u0003B\u0014[\u0003{T3!a@N!\r\u0019%\u0011\u0001\u0003\u0006?r\u0011\rA\u0012\u0005\n\u0005\u000ba\u0012\u0011!a\u0001\u0005\u000f\t1\u0001\u001f\u00131!\u00199\u0003!a>\u0002\u0000\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011!Q\u0002\t\u0005\u0003\u0003\u0012y!\u0003\u0003\u0003\u0012\u0005\r#AB(cU\u0016\u001cG\u000f")
public final class FlowShape<I, O>
extends Shape
implements Product,
Serializable {
    private final Inlet<I> in;
    private final Outlet<O> out;
    private final Seq<Inlet<?>> inlets;
    private final Seq<Outlet<?>> outlets;

    public static <I, O> Option<Tuple2<Inlet<I>, Outlet<O>>> unapply(FlowShape<I, O> x$0) {
        return FlowShape$.MODULE$.unapply(x$0);
    }

    public static <I, O> FlowShape<I, O> apply(Inlet<I> in, Outlet<O> out) {
        return FlowShape$.MODULE$.apply(in, out);
    }

    public static <I, O> FlowShape<I, O> of(Inlet<I> inlet, Outlet<O> outlet) {
        return FlowShape$.MODULE$.of(inlet, outlet);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Inlet<I> in() {
        return this.in;
    }

    public Outlet<O> out() {
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
    public FlowShape<I, O> deepCopy() {
        return new FlowShape<I, O>(this.in().carbonCopy(), this.out().carbonCopy());
    }

    public <I, O> FlowShape<I, O> copy(Inlet<I> in, Outlet<O> out) {
        return new FlowShape<I, O>(in, out);
    }

    public <I, O> Inlet<I> copy$default$1() {
        return this.in();
    }

    public <I, O> Outlet<O> copy$default$2() {
        return this.out();
    }

    @Override
    public String productPrefix() {
        return "FlowShape";
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
                return this.in();
            }
            case 1: {
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
        return x$1 instanceof FlowShape;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "in";
            }
            case 1: {
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
        if (!(object instanceof FlowShape)) return false;
        boolean bl = true;
        if (!bl) return false;
        FlowShape flowShape = (FlowShape)x$1;
        Inlet<I> inlet = this.in();
        Inlet<I> inlet2 = flowShape.in();
        if (inlet == null) {
            if (inlet2 != null) {
                return false;
            }
        } else if (!((Object)inlet).equals(inlet2)) return false;
        Outlet<O> outlet = this.out();
        Outlet<O> outlet2 = flowShape.out();
        if (outlet == null) {
            if (outlet2 == null) return true;
            return false;
        } else {
            if (!((Object)outlet).equals(outlet2)) return false;
            return true;
        }
    }

    public FlowShape(Inlet<I> in, Outlet<O> out) {
        this.in = in;
        this.out = out;
        Product.$init$(this);
        Inlet<I> inlet = in;
        this.inlets = Nil$.MODULE$.$colon$colon(inlet);
        Outlet<O> outlet = out;
        this.outlets = Nil$.MODULE$.$colon$colon(outlet);
    }
}

