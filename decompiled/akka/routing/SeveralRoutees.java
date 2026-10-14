/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.japi.Util$;
import akka.routing.Routee;
import akka.routing.SeveralRoutees$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.List;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005ud\u0001B\r\u001b\u0005~A\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t\u0007\u0002\u0011\t\u0012)A\u0005w!)A\t\u0001C\u0001\u000b\")A\t\u0001C\u0001\u0011\")1\u000b\u0001C\u0001)\")1\f\u0001C!9\"9Q\u000eAA\u0001\n\u0003q\u0007b\u00029\u0001#\u0003%\t!\u001d\u0005\by\u0002\t\t\u0011\"\u0011~\u0011%\t\u0019\u0001AA\u0001\n\u0003\t)\u0001C\u0005\u0002\u000e\u0001\t\t\u0011\"\u0001\u0002\u0010!I\u0011Q\u0003\u0001\u0002\u0002\u0013\u0005\u0013q\u0003\u0005\n\u0003C\u0001\u0011\u0011!C\u0001\u0003GA\u0011\"!\f\u0001\u0003\u0003%\t%a\f\t\u0013\u0005M\u0002!!A\u0005B\u0005U\u0002\"CA\u001c\u0001\u0005\u0005I\u0011IA\u001d\u0011%\tY\u0004AA\u0001\n\u0003\nidB\u0005\u0002Bi\t\t\u0011#\u0001\u0002D\u0019A\u0011DGA\u0001\u0012\u0003\t)\u0005\u0003\u0004E'\u0011\u0005\u0011Q\f\u0005\n\u0003o\u0019\u0012\u0011!C#\u0003sA\u0011\"a\u0018\u0014\u0003\u0003%\t)!\u0019\t\u0013\u0005\u00154#!A\u0005\u0002\u0006\u001d\u0004\"CA:'\u0005\u0005I\u0011BA;\u00059\u0019VM^3sC2\u0014v.\u001e;fKNT!a\u0007\u000f\u0002\u000fI|W\u000f^5oO*\tQ$\u0001\u0003bW.\f7\u0001A\n\u0006\u0001\u00012#&\f\t\u0003C\u0011j\u0011A\t\u0006\u0002G\u0005)1oY1mC&\u0011QE\t\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u001dBS\"\u0001\u000e\n\u0005%R\"A\u0002*pkR,W\r\u0005\u0002\"W%\u0011AF\t\u0002\b!J|G-^2u!\tqcG\u0004\u00020i9\u0011\u0001gM\u0007\u0002c)\u0011!GH\u0001\u0007yI|w\u000e\u001e \n\u0003\rJ!!\u000e\u0012\u0002\u000fA\f7m[1hK&\u0011q\u0007\u000f\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003k\t\nqA]8vi\u0016,7/F\u0001<!\ra\u0014IJ\u0007\u0002{)\u0011ahP\u0001\nS6lW\u000f^1cY\u0016T!\u0001\u0011\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002C{\tQ\u0011J\u001c3fq\u0016$7+Z9\u0002\u0011I|W\u000f^3fg\u0002\na\u0001P5oSRtDC\u0001$H!\t9\u0003\u0001C\u0003:\u0007\u0001\u00071\b\u0006\u0002G\u0013\")!\n\u0002a\u0001\u0017\u0006\u0011!o\u001d\t\u0004\u0019F3S\"A'\u000b\u00059{\u0015\u0001\u00027b]\u001eT\u0011\u0001U\u0001\u0005U\u00064\u0018-\u0003\u0002S\u001b\nA\u0011\n^3sC\ndW-\u0001\u0006hKR\u0014v.\u001e;fKN$\u0012!\u0016\t\u0004-f3S\"A,\u000b\u0005a{\u0015\u0001B;uS2L!AW,\u0003\t1K7\u000f^\u0001\u0005g\u0016tG\rF\u0002^A\u0016\u0004\"!\t0\n\u0005}\u0013#\u0001B+oSRDQ!\u0019\u0004A\u0002\t\fq!\\3tg\u0006<W\r\u0005\u0002\"G&\u0011AM\t\u0002\u0004\u0003:L\b\"\u00024\u0007\u0001\u00049\u0017AB:f]\u0012,'\u000f\u0005\u0002iW6\t\u0011N\u0003\u0002k9\u0005)\u0011m\u0019;pe&\u0011A.\u001b\u0002\t\u0003\u000e$xN\u001d*fM\u0006!1m\u001c9z)\t1u\u000eC\u0004:\u000fA\u0005\t\u0019A\u001e\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\t!O\u000b\u0002<g.\nA\u000f\u0005\u0002vu6\taO\u0003\u0002xq\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003s\n\n!\"\u00198o_R\fG/[8o\u0013\tYhOA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u0001@\u0011\u00051{\u0018bAA\u0001\u001b\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!a\u0002\u0011\u0007\u0005\nI!C\u0002\u0002\f\t\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2AYA\t\u0011%\t\u0019bCA\u0001\u0002\u0004\t9!A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u00033\u0001R!a\u0007\u0002\u001e\tl\u0011aP\u0005\u0004\u0003?y$\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\n\u0002,A\u0019\u0011%a\n\n\u0007\u0005%\"EA\u0004C_>dW-\u00198\t\u0011\u0005MQ\"!AA\u0002\t\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019a0!\r\t\u0013\u0005Ma\"!AA\u0002\u0005\u001d\u0011\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\u001d\u0011\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003y\fa!Z9vC2\u001cH\u0003BA\u0013\u0003\u007fA\u0001\"a\u0005\u0012\u0003\u0003\u0005\rAY\u0001\u000f'\u00164XM]1m%>,H/Z3t!\t93cE\u0003\u0014\u0003\u000f\n\u0019\u0006\u0005\u0004\u0002J\u0005=3HR\u0007\u0003\u0003\u0017R1!!\u0014#\u0003\u001d\u0011XO\u001c;j[\u0016LA!!\u0015\u0002L\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005U\u00131L\u0007\u0003\u0003/R1!!\u0017P\u0003\tIw.C\u00028\u0003/\"\"!a\u0011\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0007\u0019\u000b\u0019\u0007C\u0003:-\u0001\u00071(A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005%\u0014q\u000e\t\u0005C\u0005-4(C\u0002\u0002n\t\u0012aa\u00149uS>t\u0007\u0002CA9/\u0005\u0005\t\u0019\u0001$\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002xA\u0019A*!\u001f\n\u0007\u0005mTJ\u0001\u0004PE*,7\r\u001e")
public final class SeveralRoutees
implements Routee,
Product,
Serializable {
    private final IndexedSeq<Routee> routees;

    public static Option<IndexedSeq<Routee>> unapply(SeveralRoutees x$0) {
        return SeveralRoutees$.MODULE$.unapply(x$0);
    }

    public static SeveralRoutees apply(IndexedSeq<Routee> routees) {
        return SeveralRoutees$.MODULE$.apply(routees);
    }

    public static <A> Function1<IndexedSeq<Routee>, A> andThen(Function1<SeveralRoutees, A> g2) {
        return SeveralRoutees$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, SeveralRoutees> compose(Function1<A, IndexedSeq<Routee>> g2) {
        return SeveralRoutees$.MODULE$.compose(g2);
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

    @Override
    public void send(Object message, ActorRef sender) {
        this.routees().foreach((Function1<Routee, Object> & Serializable)x$1 -> {
            x$1.send(message, sender);
            return BoxedUnit.UNIT;
        });
    }

    public SeveralRoutees copy(IndexedSeq<Routee> routees) {
        return new SeveralRoutees(routees);
    }

    public IndexedSeq<Routee> copy$default$1() {
        return this.routees();
    }

    @Override
    public String productPrefix() {
        return "SeveralRoutees";
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
        return x$1 instanceof SeveralRoutees;
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
        if (!(object instanceof SeveralRoutees)) return false;
        boolean bl = true;
        if (!bl) return false;
        SeveralRoutees severalRoutees = (SeveralRoutees)x$1;
        IndexedSeq<Routee> indexedSeq = this.routees();
        IndexedSeq<Routee> indexedSeq2 = severalRoutees.routees();
        if (indexedSeq != null) {
            if (!indexedSeq.equals(indexedSeq2)) return false;
            return true;
        }
        if (indexedSeq2 == null) return true;
        return false;
    }

    public SeveralRoutees(IndexedSeq<Routee> routees) {
        this.routees = routees;
        Product.$init$(this);
    }

    public SeveralRoutees(Iterable<Routee> rs) {
        this(Util$.MODULE$.immutableSeq(rs).toVector());
    }
}

