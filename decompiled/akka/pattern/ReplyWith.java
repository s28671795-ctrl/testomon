/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.pattern.HandlingWhileStopped;
import akka.pattern.ReplyWith$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Eb!\u0002\f\u0018\u0001fY\u0002\u0002\u0003\u001c\u0001\u0005+\u0007I\u0011A\u001c\t\u0011m\u0002!\u0011#Q\u0001\naBQ\u0001\u0010\u0001\u0005\u0002uBq\u0001\u0011\u0001\u0002\u0002\u0013\u0005\u0011\tC\u0004D\u0001E\u0005I\u0011\u0001#\t\u000f=\u0003\u0011\u0011!C!!\"9\u0011\fAA\u0001\n\u0003Q\u0006b\u00020\u0001\u0003\u0003%\ta\u0018\u0005\bE\u0002\t\t\u0011\"\u0011d\u0011\u001dQ\u0007!!A\u0005\u0002-Dq\u0001\u001d\u0001\u0002\u0002\u0013\u0005\u0013\u000fC\u0004t\u0001\u0005\u0005I\u0011\t;\t\u000fU\u0004\u0011\u0011!C!m\"9q\u000fAA\u0001\n\u0003Bx\u0001\u0003>\u0018\u0003\u0003E\t!G>\u0007\u0011Y9\u0012\u0011!E\u00013qDa\u0001\u0010\t\u0005\u0002\u0005E\u0001bB;\u0011\u0003\u0003%)E\u001e\u0005\n\u0003'\u0001\u0012\u0011!CA\u0003+A\u0011\"!\u0007\u0011\u0003\u0003%\t)a\u0007\t\u0013\u0005\u001d\u0002#!A\u0005\n\u0005%\"!\u0003*fa2Lx+\u001b;i\u0015\tA\u0012$A\u0004qCR$XM\u001d8\u000b\u0003i\tA!Y6lCN)\u0001\u0001\b\u0012'SA\u0011Q\u0004I\u0007\u0002=)\tq$A\u0003tG\u0006d\u0017-\u0003\u0002\"=\t1\u0011I\\=SK\u001a\u0004\"a\t\u0013\u000e\u0003]I!!J\f\u0003)!\u000bg\u000e\u001a7j]\u001e<\u0006.\u001b7f'R|\u0007\u000f]3e!\tir%\u0003\u0002)=\t9\u0001K]8ek\u000e$\bC\u0001\u00164\u001d\tY\u0013G\u0004\u0002-a5\tQF\u0003\u0002/_\u00051AH]8piz\u001a\u0001!C\u0001 \u0013\t\u0011d$A\u0004qC\u000e\\\u0017mZ3\n\u0005Q*$\u0001D*fe&\fG.\u001b>bE2,'B\u0001\u001a\u001f\u0003\ri7oZ\u000b\u0002qA\u0011Q$O\u0005\u0003uy\u00111!\u00118z\u0003\u0011i7o\u001a\u0011\u0002\rqJg.\u001b;?)\tqt\b\u0005\u0002$\u0001!)ag\u0001a\u0001q\u0005!1m\u001c9z)\tq$\tC\u00047\tA\u0005\t\u0019\u0001\u001d\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tQI\u000b\u00029\r.\nq\t\u0005\u0002I\u001b6\t\u0011J\u0003\u0002K\u0017\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003\u0019z\t!\"\u00198o_R\fG/[8o\u0013\tq\u0015JA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A)\u0011\u0005I;V\"A*\u000b\u0005Q+\u0016\u0001\u00027b]\u001eT\u0011AV\u0001\u0005U\u00064\u0018-\u0003\u0002Y'\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012a\u0017\t\u0003;qK!!\u0018\u0010\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005a\u0002\u0007bB1\t\u0003\u0003\u0005\raW\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003\u0011\u00042!\u001a59\u001b\u00051'BA4\u001f\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003S\u001a\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0011An\u001c\t\u0003;5L!A\u001c\u0010\u0003\u000f\t{w\u000e\\3b]\"9\u0011MCA\u0001\u0002\u0004A\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"!\u0015:\t\u000f\u0005\\\u0011\u0011!a\u00017\u0006A\u0001.Y:i\u0007>$W\rF\u0001\\\u0003!!xn\u0015;sS:<G#A)\u0002\r\u0015\fX/\u00197t)\ta\u0017\u0010C\u0004b\u001d\u0005\u0005\t\u0019\u0001\u001d\u0002\u0013I+\u0007\u000f\\=XSRD\u0007CA\u0012\u0011'\u0011\u0001R0a\u0002\u0011\u000by\f\u0019\u0001\u000f \u000e\u0003}T1!!\u0001\u001f\u0003\u001d\u0011XO\u001c;j[\u0016L1!!\u0002\u0000\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003\u0013\ty!\u0004\u0002\u0002\f)\u0019\u0011QB+\u0002\u0005%|\u0017b\u0001\u001b\u0002\fQ\t10A\u0003baBd\u0017\u0010F\u0002?\u0003/AQAN\nA\u0002a\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002\u001e\u0005\r\u0002\u0003B\u000f\u0002 aJ1!!\t\u001f\u0005\u0019y\u0005\u000f^5p]\"A\u0011Q\u0005\u000b\u0002\u0002\u0003\u0007a(A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a\u000b\u0011\u0007I\u000bi#C\u0002\u00020M\u0013aa\u00142kK\u000e$\b")
public class ReplyWith
implements HandlingWhileStopped,
Product,
Serializable {
    private final Object msg;

    public static Option<Object> unapply(ReplyWith x$0) {
        return ReplyWith$.MODULE$.unapply(x$0);
    }

    public static ReplyWith apply(Object msg) {
        return ReplyWith$.MODULE$.apply(msg);
    }

    public static <A> Function1<Object, A> andThen(Function1<ReplyWith, A> g2) {
        return ReplyWith$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, ReplyWith> compose(Function1<A, Object> g2) {
        return ReplyWith$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Object msg() {
        return this.msg;
    }

    public ReplyWith copy(Object msg) {
        return new ReplyWith(msg);
    }

    public Object copy$default$1() {
        return this.msg();
    }

    @Override
    public String productPrefix() {
        return "ReplyWith";
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
                return this.msg();
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
        return x$1 instanceof ReplyWith;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "msg";
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
        if (!(object instanceof ReplyWith)) return false;
        boolean bl = true;
        if (!bl) return false;
        ReplyWith replyWith = (ReplyWith)x$1;
        if (!BoxesRunTime.equals(this.msg(), replyWith.msg())) return false;
        if (!replyWith.canEqual(this)) return false;
        return true;
    }

    public ReplyWith(Object msg) {
        this.msg = msg;
        Product.$init$(this);
    }
}

