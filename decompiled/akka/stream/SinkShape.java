/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.Shape;
import akka.stream.SinkShape$;
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

@ScalaSignature(bytes="\u0006\u0005\u0005uf\u0001B\u000e\u001d\u0005\u0006B\u0001\"\u000f\u0001\u0003\u0016\u0004%\tA\u000f\u0005\t'\u0002\u0011\t\u0012)A\u0005w!)A\u000b\u0001C\u0001+\"9\u0001\f\u0001b\u0001\n\u0003J\u0006BB4\u0001A\u0003%!\fC\u0004i\u0001\t\u0007I\u0011I5\t\rI\u0004\u0001\u0015!\u0003k\u0011\u0015\u0019\b\u0001\"\u0011u\u0011\u001d)\b!!A\u0005\u0002YDqA \u0001\u0012\u0002\u0013\u0005q\u0010C\u0005\u0002\n\u0001\t\t\u0011\"\u0011\u0002\f!I\u0011Q\u0004\u0001\u0002\u0002\u0013\u0005\u0011q\u0004\u0005\n\u0003O\u0001\u0011\u0011!C\u0001\u0003SA\u0011\"a\f\u0001\u0003\u0003%\t%!\r\t\u0013\u0005m\u0002!!A\u0005\u0002\u0005u\u0002\"CA$\u0001\u0005\u0005I\u0011IA%\u0011%\ti\u0005AA\u0001\n\u0003\ny\u0005C\u0005\u0002R\u0001\t\t\u0011\"\u0011\u0002T!I\u0011Q\u000b\u0001\u0002\u0002\u0013\u0005\u0013qK\u0004\b\u00037b\u0002\u0012AA/\r\u0019YB\u0004#\u0001\u0002`!1A+\u0006C\u0001\u0003cBq!a\u001d\u0016\t\u0003\t)\bC\u0005\u0002\bV\t\t\u0011\"!\u0002\n\"I\u0011\u0011T\u000b\u0002\u0002\u0013\u0005\u00151\u0014\u0005\n\u0003g+\u0012\u0011!C\u0005\u0003k\u0013\u0011bU5oWNC\u0017\r]3\u000b\u0005uq\u0012AB:ue\u0016\fWNC\u0001 \u0003\u0011\t7n[1\u0004\u0001U\u0011!%Q\n\u0005\u0001\r:S\u0006\u0005\u0002%K5\tA$\u0003\u0002'9\t)1\u000b[1qKB\u0011\u0001fK\u0007\u0002S)\t!&A\u0003tG\u0006d\u0017-\u0003\u0002-S\t9\u0001K]8ek\u000e$\bC\u0001\u00187\u001d\tyCG\u0004\u00021g5\t\u0011G\u0003\u00023A\u00051AH]8pizJ\u0011AK\u0005\u0003k%\nq\u0001]1dW\u0006<W-\u0003\u00028q\ta1+\u001a:jC2L'0\u00192mK*\u0011Q'K\u0001\u0003S:,\u0012a\u000f\t\u0004Iqr\u0014BA\u001f\u001d\u0005\u0015Ie\u000e\\3uU\ty$\n\u0005\u0002A\u00032\u0001AA\u0002\"\u0001\u0011\u000b\u00071IA\u0001U#\t!u\t\u0005\u0002)\u000b&\u0011a)\u000b\u0002\b\u001d>$\b.\u001b8h!\tA\u0003*\u0003\u0002JS\t\u0019\u0011I\\=,\u0003-\u0003\"\u0001T)\u000e\u00035S!AT(\u0002\u0013Ut7\r[3dW\u0016$'B\u0001)*\u0003)\tgN\\8uCRLwN\\\u0005\u0003%6\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003\rIg\u000eI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005Y;\u0006c\u0001\u0013\u0001\u007f!)\u0011h\u0001a\u0001w\u00051\u0011N\u001c7fiN,\u0012A\u0017\t\u00047\u0002\u0014W\"\u0001/\u000b\u0005us\u0016!C5n[V$\u0018M\u00197f\u0015\ty\u0016&\u0001\u0006d_2dWm\u0019;j_:L!!\u0019/\u0003\u0007M+\u0017\u000f\r\u0002dKB\u0019A\u0005\u00103\u0011\u0005\u0001+G!\u00034\u0006\u0003\u0003\u0005\tQ!\u0001D\u0005\u0011yFE\r\u001a\u0002\u000f%tG.\u001a;tA\u00059q.\u001e;mKR\u001cX#\u00016\u0011\u0007m\u00037\u000e\r\u0002maB\u0019A%\\8\n\u00059d\"AB(vi2,G\u000f\u0005\u0002Aa\u0012I\u0011oBA\u0001\u0002\u0003\u0015\ta\u0011\u0002\u0005?\u0012\u00124'\u0001\u0005pkRdW\r^:!\u0003!!W-\u001a9D_BLH#\u0001,\u0002\t\r|\u0007/_\u000b\u0003oj$\"\u0001_>\u0011\u0007\u0011\u0002\u0011\u0010\u0005\u0002Au\u0012)!)\u0003b\u0001\u0007\"9\u0011(\u0003I\u0001\u0002\u0004a\bc\u0001\u0013={*\u0012\u0011PS\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0011\t\t!a\u0002\u0016\u0005\u0005\r!fAA\u0003\u0015B\u0019A\u0005P \u0005\u000b\tS!\u0019A\"\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\ti\u0001\u0005\u0003\u0002\u0010\u0005eQBAA\t\u0015\u0011\t\u0019\"!\u0006\u0002\t1\fgn\u001a\u0006\u0003\u0003/\tAA[1wC&!\u00111DA\t\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011\u0011\u0005\t\u0004Q\u0005\r\u0012bAA\u0013S\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019q)a\u000b\t\u0013\u00055R\"!AA\u0002\u0005\u0005\u0012a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u00024A)\u0011QGA\u001c\u000f6\ta,C\u0002\u0002:y\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011qHA#!\rA\u0013\u0011I\u0005\u0004\u0003\u0007J#a\u0002\"p_2,\u0017M\u001c\u0005\t\u0003[y\u0011\u0011!a\u0001\u000f\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\ti!a\u0013\t\u0013\u00055\u0002#!AA\u0002\u0005\u0005\u0012\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\u0005\u0012\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u00055\u0011AB3rk\u0006d7\u000f\u0006\u0003\u0002@\u0005e\u0003\u0002CA\u0017'\u0005\u0005\t\u0019A$\u0002\u0013MKgn[*iCB,\u0007C\u0001\u0013\u0016'\u0015)\u0012\u0011MA4!\rA\u00131M\u0005\u0004\u0003KJ#AB!osJ+g\r\u0005\u0003\u0002j\u0005=TBAA6\u0015\u0011\ti'!\u0006\u0002\u0005%|\u0017bA\u001c\u0002lQ\u0011\u0011QL\u0001\u0003_\u001a,B!a\u001e\u0002~Q!\u0011\u0011PA@!\u0011!\u0003!a\u001f\u0011\u0007\u0001\u000bi\bB\u0003C/\t\u00071\tC\u0004\u0002\u0002^\u0001\r!a!\u0002\u000b%tG.\u001a;\u0011\t\u0011b\u0014Q\u0011\u0016\u0004\u0003wR\u0015!B1qa2LX\u0003BAF\u0003##B!!$\u0002\u0014B!A\u0005AAH!\r\u0001\u0015\u0011\u0013\u0003\u0006\u0005b\u0011\ra\u0011\u0005\u0007sa\u0001\r!!&\u0011\t\u0011b\u0014q\u0013\u0016\u0004\u0003\u001fS\u0015aB;oCB\u0004H._\u000b\u0005\u0003;\u000bY\u000b\u0006\u0003\u0002 \u00065\u0006#\u0002\u0015\u0002\"\u0006\u0015\u0016bAARS\t1q\n\u001d;j_:\u0004B\u0001\n\u001f\u0002(*\u001a\u0011\u0011\u0016&\u0011\u0007\u0001\u000bY\u000bB\u0003C3\t\u00071\tC\u0005\u00020f\t\t\u00111\u0001\u00022\u0006\u0019\u0001\u0010\n\u0019\u0011\t\u0011\u0002\u0011\u0011V\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003o\u0003B!a\u0004\u0002:&!\u00111XA\t\u0005\u0019y%M[3di\u0002")
public final class SinkShape<T>
extends Shape
implements Product,
Serializable {
    private final Inlet<T> in;
    private final Seq<Inlet<?>> inlets;
    private final Seq<Outlet<?>> outlets;

    public static <T> Option<Inlet<T>> unapply(SinkShape<T> x$0) {
        return SinkShape$.MODULE$.unapply(x$0);
    }

    public static <T> SinkShape<T> apply(Inlet<T> in) {
        return SinkShape$.MODULE$.apply(in);
    }

    public static <T> SinkShape<T> of(Inlet<T> inlet) {
        return SinkShape$.MODULE$.of(inlet);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Inlet<T> in() {
        return this.in;
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
    public SinkShape<T> deepCopy() {
        return new SinkShape<T>(this.in().carbonCopy());
    }

    public <T> SinkShape<T> copy(Inlet<T> in) {
        return new SinkShape<T>(in);
    }

    public <T> Inlet<T> copy$default$1() {
        return this.in();
    }

    @Override
    public String productPrefix() {
        return "SinkShape";
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
                return this.in();
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
        return x$1 instanceof SinkShape;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "in";
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
        if (!(object instanceof SinkShape)) return false;
        boolean bl = true;
        if (!bl) return false;
        SinkShape sinkShape = (SinkShape)x$1;
        Inlet<T> inlet = this.in();
        Inlet<T> inlet2 = sinkShape.in();
        if (inlet != null) {
            if (!((Object)inlet).equals(inlet2)) return false;
            return true;
        }
        if (inlet2 == null) return true;
        return false;
    }

    public SinkShape(Inlet<T> in) {
        this.in = in;
        Product.$init$(this);
        Inlet<T> inlet = in;
        this.inlets = Nil$.MODULE$.$colon$colon(inlet);
        this.outlets = Collections$EmptyImmutableSeq$.MODULE$;
    }
}

