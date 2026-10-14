/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.GroupedWeighted$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.collection.mutable.ReusableBuilder;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005mh!B\u0011#\u0005\"R\u0003\u0002\u0003/\u0001\u0005+\u0007I\u0011A/\t\u0011\u0005\u0004!\u0011#Q\u0001\nyC\u0001B\u0019\u0001\u0003\u0016\u0004%\ta\u0019\u0005\tO\u0002\u0011\t\u0012)A\u0005I\")\u0001\u000e\u0001C\u0001S\"9a\u000e\u0001b\u0001\n\u0003y\u0007BB:\u0001A\u0003%\u0001\u000fC\u0004u\u0001\t\u0007I\u0011A;\t\re\u0004\u0001\u0015!\u0003w\u0011\u001dQ\bA1A\u0005BmDa\u0001 \u0001!\u0002\u0013\u0011\u0004\"B?\u0001\t\u0003r\bbBA\u0003\u0001\u0011\u0005\u0013q\u0001\u0005\n\u0003'\u0001\u0011\u0011!C\u0001\u0003+A\u0011\"!\n\u0001#\u0003%\t!a\n\t\u0013\u0005\u0005\u0003!%A\u0005\u0002\u0005\r\u0003\"CA&\u0001\u0005\u0005I\u0011IA'\u0011%\ty\u0006AA\u0001\n\u0003\t\t\u0007C\u0005\u0002j\u0001\t\t\u0011\"\u0001\u0002l!I\u0011\u0011\u000f\u0001\u0002\u0002\u0013\u0005\u00131\u000f\u0005\n\u0003{\u0002\u0011\u0011!C\u0001\u0003\u007fB\u0011\"!#\u0001\u0003\u0003%\t%a#\t\u0013\u0005=\u0005!!A\u0005B\u0005E\u0005\"CAJ\u0001\u0005\u0005I\u0011IAK\u0011%\t9\nAA\u0001\n\u0003\nIj\u0002\u0006\u0002*\n\n\t\u0011#\u0001)\u0003W3\u0011\"\t\u0012\u0002\u0002#\u0005\u0001&!,\t\r!\\B\u0011AA`\u0011%\t\u0019jGA\u0001\n\u000b\n)\nC\u0005\u0002Bn\t\t\u0011\"!\u0002D\"I\u00111[\u000e\u0002\u0002\u0013\u0005\u0015Q\u001b\u0005\n\u0003c\\\u0012\u0011!C\u0005\u0003g\u0014qb\u0012:pkB,GmV3jO\"$X\r\u001a\u0006\u0003G\u0011\naAZ;tS:<'BA\u0013'\u0003\u0011IW\u000e\u001d7\u000b\u0005\u001dB\u0013AB:ue\u0016\fWNC\u0001*\u0003\u0011\t7n[1\u0016\u0005-B4\u0003\u0002\u0001-\u001bB\u00032!\f\u00193\u001b\u0005q#BA\u0018'\u0003\u0015\u0019H/Y4f\u0013\t\tdF\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004Ba\r\u001b7\u000b6\ta%\u0003\u00026M\tIa\t\\8x'\"\f\u0007/\u001a\t\u0003oab\u0001\u0001B\u0003:\u0001\t\u00071HA\u0001U\u0007\u0001\t\"\u0001\u0010\"\u0011\u0005u\u0002U\"\u0001 \u000b\u0003}\nQa]2bY\u0006L!!\u0011 \u0003\u000f9{G\u000f[5oOB\u0011QhQ\u0005\u0003\tz\u00121!\u00118z!\r15JN\u0007\u0002\u000f*\u0011\u0001*S\u0001\nS6lW\u000f^1cY\u0016T!A\u0013 \u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002M\u000f\n\u00191+Z9\u0011\u0005ur\u0015BA(?\u0005\u001d\u0001&o\u001c3vGR\u0004\"!U-\u000f\u0005I;fBA*W\u001b\u0005!&BA+;\u0003\u0019a$o\\8u}%\tq(\u0003\u0002Y}\u00059\u0001/Y2lC\u001e,\u0017B\u0001.\\\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tAf(A\u0005nS:<V-[4iiV\ta\f\u0005\u0002>?&\u0011\u0001M\u0010\u0002\u0005\u0019>tw-\u0001\u0006nS:<V-[4ii\u0002\naaY8ti\u001asW#\u00013\u0011\tu*gGX\u0005\u0003Mz\u0012\u0011BR;oGRLwN\\\u0019\u0002\u000f\r|7\u000f\u001e$oA\u00051A(\u001b8jiz\"2A\u001b7n!\rY\u0007AN\u0007\u0002E!)A,\u0002a\u0001=\")!-\u0002a\u0001I\u0006\u0011\u0011N\\\u000b\u0002aB\u00191'\u001d\u001c\n\u0005I4#!B%oY\u0016$\u0018aA5oA\u0005\u0019q.\u001e;\u0016\u0003Y\u00042aM<F\u0013\tAhE\u0001\u0004PkRdW\r^\u0001\u0005_V$\b%A\u0003tQ\u0006\u0004X-F\u00013\u0003\u0019\u0019\b.\u00199fA\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0003}\u00042aMA\u0001\u0013\r\t\u0019A\n\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018aC2sK\u0006$X\rT8hS\u000e$B!!\u0003\u0002\u0010A\u0019Q&a\u0003\n\u0007\u00055aFA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0019\t\t\"\u0004a\u0001\u007f\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006!1m\u001c9z+\u0011\t9\"!\b\u0015\r\u0005e\u0011qDA\u0011!\u0011Y\u0007!a\u0007\u0011\u0007]\ni\u0002B\u0003:\u001d\t\u00071\bC\u0004]\u001dA\u0005\t\u0019\u00010\t\u0011\tt\u0001\u0013!a\u0001\u0003G\u0001R!P3\u0002\u001cy\u000babY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0003\u0002*\u0005}RCAA\u0016U\rq\u0016QF\u0016\u0003\u0003_\u0001B!!\r\u0002<5\u0011\u00111\u0007\u0006\u0005\u0003k\t9$A\u0005v]\u000eDWmY6fI*\u0019\u0011\u0011\b \u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002>\u0005M\"!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)\u0011h\u0004b\u0001w\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T\u0003BA#\u0003\u0013*\"!a\u0012+\u0007\u0011\fi\u0003B\u0003:!\t\u00071(A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u001f\u0002B!!\u0015\u0002\\5\u0011\u00111\u000b\u0006\u0005\u0003+\n9&\u0001\u0003mC:<'BAA-\u0003\u0011Q\u0017M^1\n\t\u0005u\u00131\u000b\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005\r\u0004cA\u001f\u0002f%\u0019\u0011q\r \u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0007\t\u000bi\u0007C\u0005\u0002pM\t\t\u00111\u0001\u0002d\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!\u001e\u0011\u000b\u0005]\u0014\u0011\u0010\"\u000e\u0003%K1!a\u001fJ\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\u0005\u0015q\u0011\t\u0004{\u0005\r\u0015bAAC}\t9!i\\8mK\u0006t\u0007\u0002CA8+\u0005\u0005\t\u0019\u0001\"\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u001f\ni\tC\u0005\u0002pY\t\t\u00111\u0001\u0002d\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002d\u0005AAo\\*ue&tw\r\u0006\u0002\u0002P\u00051Q-];bYN$B!!!\u0002\u001c\"A\u0011qN\r\u0002\u0002\u0003\u0007!\tK\u0002\u0001\u0003?\u0003B!!)\u0002&6\u0011\u00111\u0015\u0006\u0004\u0003sA\u0013\u0002BAT\u0003G\u00131\"\u00138uKJt\u0017\r\\!qS\u0006yqI]8va\u0016$w+Z5hQR,G\r\u0005\u0002l7M)1$a,\u00026B\u0019Q(!-\n\u0007\u0005MfH\u0001\u0004B]f\u0014VM\u001a\t\u0005\u0003o\u000bi,\u0004\u0002\u0002:*!\u00111XA,\u0003\tIw.C\u0002[\u0003s#\"!a+\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\t\u0005\u0015\u00171\u001a\u000b\u0007\u0003\u000f\fi-a4\u0011\t-\u0004\u0011\u0011\u001a\t\u0004o\u0005-G!B\u001d\u001f\u0005\u0004Y\u0004\"\u0002/\u001f\u0001\u0004q\u0006B\u00022\u001f\u0001\u0004\t\t\u000eE\u0003>K\u0006%g,A\u0004v]\u0006\u0004\b\u000f\\=\u0016\t\u0005]\u0017\u0011\u001e\u000b\u0005\u00033\fY\u000fE\u0003>\u00037\fy.C\u0002\u0002^z\u0012aa\u00149uS>t\u0007CB\u001f\u0002bz\u000b)/C\u0002\u0002dz\u0012a\u0001V;qY\u0016\u0014\u0004#B\u001ff\u0003Ot\u0006cA\u001c\u0002j\u0012)\u0011h\bb\u0001w!I\u0011Q^\u0010\u0002\u0002\u0003\u0007\u0011q^\u0001\u0004q\u0012\u0002\u0004\u0003B6\u0001\u0003O\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!>\u0011\t\u0005E\u0013q_\u0005\u0005\u0003s\f\u0019F\u0001\u0004PE*,7\r\u001e")
@InternalApi
public final class GroupedWeighted<T>
extends GraphStage<FlowShape<T, Seq<T>>>
implements Product,
Serializable {
    private final long minWeight;
    private final Function1<T, Object> costFn;
    private final Inlet<T> in;
    private final Outlet<Seq<T>> out;
    private final FlowShape<T, Seq<T>> shape;

    public static <T> Option<Tuple2<Object, Function1<T, Object>>> unapply(GroupedWeighted<T> x$0) {
        return GroupedWeighted$.MODULE$.unapply(x$0);
    }

    public static <T> GroupedWeighted<T> apply(long minWeight, Function1<T, Object> costFn) {
        return GroupedWeighted$.MODULE$.apply(minWeight, costFn);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public long minWeight() {
        return this.minWeight;
    }

    public Function1<T, Object> costFn() {
        return this.costFn;
    }

    public Inlet<T> in() {
        return this.in;
    }

    public Outlet<Seq<T>> out() {
        return this.out;
    }

    @Override
    public FlowShape<T, Seq<T>> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.groupedWeighted();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private final ReusableBuilder<T, Vector<T>> buf;
            private long left;
            private final /* synthetic */ GroupedWeighted $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private ReusableBuilder<T, Vector<T>> buf() {
                return this.buf;
            }

            private long left() {
                return this.left;
            }

            private void left_$eq(long x$1) {
                this.left = x$1;
            }

            public void onPush() {
                T elem = this.grab(this.$outer.in());
                long cost = BoxesRunTime.unboxToLong(this.$outer.costFn().apply(elem));
                if (cost < 0L) {
                    this.failStage(new IllegalArgumentException(new StringBuilder(48).append("Negative weight [").append(cost).append("] for element [").append(elem).append("] is not allowed").toString()));
                    return;
                }
                this.buf().$plus$eq(elem);
                this.left_$eq(this.left() - cost);
                if (this.left() <= 0L) {
                    Vector<T> elements = this.buf().result();
                    this.buf().clear();
                    this.left_$eq(this.$outer.minWeight());
                    this.push(this.$outer.out(), elements);
                    return;
                }
                this.pull(this.$outer.in());
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public void onUpstreamFinish() {
                Vector<T> elements = this.buf().result();
                if (elements.nonEmpty()) {
                    this.buf().clear();
                    this.left_$eq(this.$outer.minWeight());
                    this.push(this.$outer.out(), elements);
                }
                this.completeStage();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.buf = package$.MODULE$.Vector().newBuilder();
                this.left = $outer.minWeight();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <T> GroupedWeighted<T> copy(long minWeight, Function1<T, Object> costFn) {
        return new GroupedWeighted<T>(minWeight, costFn);
    }

    public <T> long copy$default$1() {
        return this.minWeight();
    }

    public <T> Function1<T, Object> copy$default$2() {
        return this.costFn();
    }

    @Override
    public String productPrefix() {
        return "GroupedWeighted";
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
                return BoxesRunTime.boxToLong(this.minWeight());
            }
            case 1: {
                return this.costFn();
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
        return x$1 instanceof GroupedWeighted;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "minWeight";
            }
            case 1: {
                return "costFn";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.longHash(this.minWeight()));
        n = Statics.mix(n, Statics.anyHash(this.costFn()));
        return Statics.finalizeHash(n, 2);
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
        if (!(object instanceof GroupedWeighted)) return false;
        boolean bl = true;
        if (!bl) return false;
        GroupedWeighted groupedWeighted = (GroupedWeighted)x$1;
        if (this.minWeight() != groupedWeighted.minWeight()) return false;
        Function1<T, Object> function1 = this.costFn();
        Function1<T, Object> function12 = groupedWeighted.costFn();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public GroupedWeighted(long minWeight, Function1<T, Object> costFn) {
        this.minWeight = minWeight;
        this.costFn = costFn;
        Product.$init$(this);
        Predef$.MODULE$.require(minWeight > 0L, (Function0<Object>)(Function0<String> & Serializable)() -> "minWeight must be greater than 0");
        this.in = Inlet$.MODULE$.apply("GroupedWeighted.in");
        this.out = Outlet$.MODULE$.apply("GroupedWeighted.out");
        this.shape = new FlowShape<T, Seq<T>>(this.in(), this.out());
    }
}

