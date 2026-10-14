/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.EnterIsland$;
import akka.stream.impl.IslandTag;
import akka.stream.impl.Traversal;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dc!\u0002\f\u0018\u0005ni\u0002\u0002\u0003\u001d\u0001\u0005+\u0007I\u0011A\u001d\t\u0011u\u0002!\u0011#Q\u0001\niBQA\u0010\u0001\u0005\u0002}BqA\u0011\u0001\u0002\u0002\u0013\u00051\tC\u0004F\u0001E\u0005I\u0011\u0001$\t\u000fE\u0003\u0011\u0011!C!%\"91\fAA\u0001\n\u0003a\u0006b\u00021\u0001\u0003\u0003%\t!\u0019\u0005\bO\u0002\t\t\u0011\"\u0011i\u0011\u001dy\u0007!!A\u0005\u0002ADq!\u001e\u0001\u0002\u0002\u0013\u0005c\u000fC\u0004y\u0001\u0005\u0005I\u0011I=\t\u000fi\u0004\u0011\u0011!C!w\"9A\u0010AA\u0001\n\u0003jxACA\u0006/\u0005\u0005\t\u0012A\u000e\u0002\u000e\u0019IacFA\u0001\u0012\u0003Y\u0012q\u0002\u0005\u0007}A!\t!a\n\t\u000fi\u0004\u0012\u0011!C#w\"I\u0011\u0011\u0006\t\u0002\u0002\u0013\u0005\u00151\u0006\u0005\n\u0003_\u0001\u0012\u0011!CA\u0003cA\u0011\"!\u0010\u0011\u0003\u0003%I!a\u0010\u0003\u0017\u0015sG/\u001a:Jg2\fg\u000e\u001a\u0006\u00031e\tA![7qY*\u0011!dG\u0001\u0007gR\u0014X-Y7\u000b\u0003q\tA!Y6lCN)\u0001A\b\u0013)WA\u0011qDI\u0007\u0002A)\t\u0011%A\u0003tG\u0006d\u0017-\u0003\u0002$A\t1\u0011I\\=SK\u001a\u0004\"!\n\u0014\u000e\u0003]I!aJ\f\u0003\u0013Q\u0013\u0018M^3sg\u0006d\u0007CA\u0010*\u0013\tQ\u0003EA\u0004Qe>$Wo\u0019;\u0011\u00051*dBA\u00174\u001d\tq#'D\u00010\u0015\t\u0001\u0014'\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005\t\u0013B\u0001\u001b!\u0003\u001d\u0001\u0018mY6bO\u0016L!AN\u001c\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005Q\u0002\u0013!C5tY\u0006tG\rV1h+\u0005Q\u0004CA\u0013<\u0013\tatCA\u0005Jg2\fg\u000e\u001a+bO\u0006Q\u0011n\u001d7b]\u0012$\u0016m\u001a\u0011\u0002\rqJg.\u001b;?)\t\u0001\u0015\t\u0005\u0002&\u0001!)\u0001h\u0001a\u0001u\u0005!1m\u001c9z)\t\u0001E\tC\u00049\tA\u0005\t\u0019\u0001\u001e\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tqI\u000b\u0002;\u0011.\n\u0011\n\u0005\u0002K\u001f6\t1J\u0003\u0002M\u001b\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003\u001d\u0002\n!\"\u00198o_R\fG/[8o\u0013\t\u00016JA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A*\u0011\u0005QKV\"A+\u000b\u0005Y;\u0016\u0001\u00027b]\u001eT\u0011\u0001W\u0001\u0005U\u00064\u0018-\u0003\u0002[+\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012!\u0018\t\u0003?yK!a\u0018\u0011\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005\t,\u0007CA\u0010d\u0013\t!\u0007EA\u0002B]fDqA\u001a\u0005\u0002\u0002\u0003\u0007Q,A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002SB\u0019!.\u001c2\u000e\u0003-T!\u0001\u001c\u0011\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002oW\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\t\tH\u000f\u0005\u0002 e&\u00111\u000f\t\u0002\b\u0005>|G.Z1o\u0011\u001d1'\"!AA\u0002\t\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u00111k\u001e\u0005\bM.\t\t\u00111\u0001^\u0003!A\u0017m\u001d5D_\u0012,G#A/\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012aU\u0001\u0007KF,\u0018\r\\:\u0015\u0005Et\bb\u00024\u000f\u0003\u0003\u0005\rA\u0019\u0015\u0004\u0001\u0005\u0005\u0001\u0003BA\u0002\u0003\u000fi!!!\u0002\u000b\u00059[\u0012\u0002BA\u0005\u0003\u000b\u00111\"\u00138uKJt\u0017\r\\!qS\u0006YQI\u001c;fe&\u001bH.\u00198e!\t)\u0003cE\u0003\u0011\u0003#\ti\u0002\u0005\u0004\u0002\u0014\u0005e!\bQ\u0007\u0003\u0003+Q1!a\u0006!\u0003\u001d\u0011XO\u001c;j[\u0016LA!a\u0007\u0002\u0016\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005}\u0011QE\u0007\u0003\u0003CQ1!a\tX\u0003\tIw.C\u00027\u0003C!\"!!\u0004\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0007\u0001\u000bi\u0003C\u00039'\u0001\u0007!(A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005M\u0012\u0011\b\t\u0005?\u0005U\"(C\u0002\u00028\u0001\u0012aa\u00149uS>t\u0007\u0002CA\u001e)\u0005\u0005\t\u0019\u0001!\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002BA\u0019A+a\u0011\n\u0007\u0005\u0015SK\u0001\u0004PE*,7\r\u001e")
@InternalApi
public final class EnterIsland
implements Traversal,
Product,
Serializable {
    private final IslandTag islandTag;

    public static Option<IslandTag> unapply(EnterIsland x$0) {
        return EnterIsland$.MODULE$.unapply(x$0);
    }

    public static EnterIsland apply(IslandTag islandTag) {
        return EnterIsland$.MODULE$.apply(islandTag);
    }

    public static <A> Function1<IslandTag, A> andThen(Function1<EnterIsland, A> g2) {
        return EnterIsland$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, EnterIsland> compose(Function1<A, IslandTag> g2) {
        return EnterIsland$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Traversal concat(Traversal that) {
        return Traversal.concat$(this, that);
    }

    @Override
    public Traversal rewireFirstTo(int relativeOffset) {
        return Traversal.rewireFirstTo$(this, relativeOffset);
    }

    public IslandTag islandTag() {
        return this.islandTag;
    }

    public EnterIsland copy(IslandTag islandTag) {
        return new EnterIsland(islandTag);
    }

    public IslandTag copy$default$1() {
        return this.islandTag();
    }

    @Override
    public String productPrefix() {
        return "EnterIsland";
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
                return this.islandTag();
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
        return x$1 instanceof EnterIsland;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "islandTag";
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
        if (!(object instanceof EnterIsland)) return false;
        boolean bl = true;
        if (!bl) return false;
        EnterIsland enterIsland = (EnterIsland)x$1;
        IslandTag islandTag = this.islandTag();
        IslandTag islandTag2 = enterIsland.islandTag();
        if (islandTag != null) {
            if (!islandTag.equals(islandTag2)) return false;
            return true;
        }
        if (islandTag2 == null) return true;
        return false;
    }

    public EnterIsland(IslandTag islandTag) {
        this.islandTag = islandTag;
        Traversal.$init$(this);
        Product.$init$(this);
    }
}

