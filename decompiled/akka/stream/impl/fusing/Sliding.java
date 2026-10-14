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
import akka.stream.impl.fusing.Sliding$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function0;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015h!\u0002\u0012$\u0005&Z\u0003\u0002C/\u0001\u0005+\u0007I\u0011\u00010\t\u0011\t\u0004!\u0011#Q\u0001\n}C\u0001b\u0019\u0001\u0003\u0016\u0004%\tA\u0018\u0005\tI\u0002\u0011\t\u0012)A\u0005?\")Q\r\u0001C\u0001M\"91\u000e\u0001b\u0001\n\u0003a\u0007B\u00029\u0001A\u0003%Q\u000eC\u0004r\u0001\t\u0007I\u0011\u0001:\t\rY\u0004\u0001\u0015!\u0003t\u0011\u001d9\bA1A\u0005BaDa!\u001f\u0001!\u0002\u0013\u0019\u0004b\u0002>\u0001\u0005\u0004%\tf\u001f\u0005\u0007\u007f\u0002\u0001\u000b\u0011\u0002?\t\u000f\u0005\u0005\u0001\u0001\"\u0011\u0002\u0004!I\u0011q\u0002\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0003\u0005\n\u0003?\u0001\u0011\u0013!C\u0001\u0003CA\u0011\"a\u000f\u0001#\u0003%\t!!\u0010\t\u0013\u0005\u0005\u0003!!A\u0005B\u0005\r\u0003\u0002CA+\u0001\u0005\u0005I\u0011\u00010\t\u0013\u0005]\u0003!!A\u0005\u0002\u0005e\u0003\"CA0\u0001\u0005\u0005I\u0011IA1\u0011%\tY\u0007AA\u0001\n\u0003\ti\u0007C\u0005\u0002x\u0001\t\t\u0011\"\u0011\u0002z!I\u0011Q\u0010\u0001\u0002\u0002\u0013\u0005\u0013q\u0010\u0005\n\u0003\u0003\u0003\u0011\u0011!C!\u0003\u0007C\u0011\"!\"\u0001\u0003\u0003%\t%a\"\b\u0015\u0005]5%!A\t\u0002%\nIJB\u0005#G\u0005\u0005\t\u0012A\u0015\u0002\u001c\"1Q\r\bC\u0001\u0003[C\u0011\"!!\u001d\u0003\u0003%)%a!\t\u0013\u0005=F$!A\u0005\u0002\u0006E\u0006\"CA`9\u0005\u0005I\u0011QAa\u0011%\tY\u000eHA\u0001\n\u0013\tiNA\u0004TY&$\u0017N\\4\u000b\u0005\u0011*\u0013A\u00024vg&twM\u0003\u0002'O\u0005!\u0011.\u001c9m\u0015\tA\u0013&\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002U\u0005!\u0011m[6b+\ta\u0013h\u0005\u0003\u0001[9\u000b\u0006c\u0001\u00182g5\tqF\u0003\u00021O\u0005)1\u000f^1hK&\u0011!g\f\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007\u0003\u0002\u001b6o\u0019k\u0011aJ\u0005\u0003m\u001d\u0012\u0011B\u00127poNC\u0017\r]3\u0011\u0005aJD\u0002\u0001\u0003\u0006u\u0001\u0011\r\u0001\u0010\u0002\u0002)\u000e\u0001\u0011CA\u001fD!\tq\u0014)D\u0001@\u0015\u0005\u0001\u0015!B:dC2\f\u0017B\u0001\"@\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0010#\n\u0005\u0015{$aA!osB\u0019q\tT\u001c\u000e\u0003!S!!\u0013&\u0002\u0013%lW.\u001e;bE2,'BA&@\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003\u001b\"\u00131aU3r!\tqt*\u0003\u0002Q\u007f\t9\u0001K]8ek\u000e$\bC\u0001*[\u001d\t\u0019\u0006L\u0004\u0002U/6\tQK\u0003\u0002Ww\u00051AH]8pizJ\u0011\u0001Q\u0005\u00033~\nq\u0001]1dW\u0006<W-\u0003\u0002\\9\na1+\u001a:jC2L'0\u00192mK*\u0011\u0011lP\u0001\u0002]V\tq\f\u0005\u0002?A&\u0011\u0011m\u0010\u0002\u0004\u0013:$\u0018A\u00018!\u0003\u0011\u0019H/\u001a9\u0002\u000bM$X\r\u001d\u0011\u0002\rqJg.\u001b;?)\r9\u0017N\u001b\t\u0004Q\u00029T\"A\u0012\t\u000bu+\u0001\u0019A0\t\u000b\r,\u0001\u0019A0\u0002\u0005%tW#A7\u0011\u0007Qrw'\u0003\u0002pO\t)\u0011J\u001c7fi\u0006\u0019\u0011N\u001c\u0011\u0002\u0007=,H/F\u0001t!\r!DOR\u0005\u0003k\u001e\u0012aaT;uY\u0016$\u0018\u0001B8vi\u0002\nQa\u001d5ba\u0016,\u0012aM\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001}!\t!T0\u0003\u0002\u007fO\tQ\u0011\t\u001e;sS\n,H/Z:\u0002%%t\u0017\u000e^5bY\u0006#HO]5ckR,7\u000fI\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002\u0006\u0005-\u0001c\u0001\u0018\u0002\b%\u0019\u0011\u0011B\u0018\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDa!!\u0004\u000f\u0001\u0004a\u0018aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001B2paf,B!a\u0005\u0002\u001aQ1\u0011QCA\u000e\u0003;\u0001B\u0001\u001b\u0001\u0002\u0018A\u0019\u0001(!\u0007\u0005\u000biz!\u0019\u0001\u001f\t\u000fu{\u0001\u0013!a\u0001?\"91m\u0004I\u0001\u0002\u0004y\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0005\u0003G\tI$\u0006\u0002\u0002&)\u001aq,a\n,\u0005\u0005%\u0002\u0003BA\u0016\u0003ki!!!\f\u000b\t\u0005=\u0012\u0011G\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\r@\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003o\tiCA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$QA\u000f\tC\u0002q\nabY8qs\u0012\"WMZ1vYR$#'\u0006\u0003\u0002$\u0005}B!\u0002\u001e\u0012\u0005\u0004a\u0014!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002FA!\u0011qIA)\u001b\t\tIE\u0003\u0003\u0002L\u00055\u0013\u0001\u00027b]\u001eT!!a\u0014\u0002\t)\fg/Y\u0005\u0005\u0003'\nIE\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r\u0019\u00151\f\u0005\t\u0003;\"\u0012\u0011!a\u0001?\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\u0019\u0011\u000b\u0005\u0015\u0014qM\"\u000e\u0003)K1!!\u001bK\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005=\u0014Q\u000f\t\u0004}\u0005E\u0014bAA:\u007f\t9!i\\8mK\u0006t\u0007\u0002CA/-\u0005\u0005\t\u0019A\"\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u000b\nY\b\u0003\u0005\u0002^]\t\t\u00111\u0001`\u0003!A\u0017m\u001d5D_\u0012,G#A0\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0012\u0002\r\u0015\fX/\u00197t)\u0011\ty'!#\t\u0011\u0005u#$!AA\u0002\rC3\u0001AAG!\u0011\ty)a%\u000e\u0005\u0005E%bAA\u001aS%!\u0011QSAI\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u000fMc\u0017\u000eZ5oOB\u0011\u0001\u000eH\n\u00069\u0005u\u00151\u0015\t\u0004}\u0005}\u0015bAAQ\u007f\t1\u0011I\\=SK\u001a\u0004B!!*\u0002,6\u0011\u0011q\u0015\u0006\u0005\u0003S\u000bi%\u0001\u0002j_&\u00191,a*\u0015\u0005\u0005e\u0015!B1qa2LX\u0003BAZ\u0003s#b!!.\u0002<\u0006u\u0006\u0003\u00025\u0001\u0003o\u00032\u0001OA]\t\u0015QtD1\u0001=\u0011\u0015iv\u00041\u0001`\u0011\u0015\u0019w\u00041\u0001`\u0003\u001d)h.\u00199qYf,B!a1\u0002ZR!\u0011QYAi!\u0015q\u0014qYAf\u0013\r\tIm\u0010\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000by\nimX0\n\u0007\u0005=wH\u0001\u0004UkBdWM\r\u0005\n\u0003'\u0004\u0013\u0011!a\u0001\u0003+\f1\u0001\u001f\u00131!\u0011A\u0007!a6\u0011\u0007a\nI\u000eB\u0003;A\t\u0007A(\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002`B!\u0011qIAq\u0013\u0011\t\u0019/!\u0013\u0003\r=\u0013'.Z2u\u0001")
@InternalApi
public final class Sliding<T>
extends GraphStage<FlowShape<T, Seq<T>>>
implements Product,
Serializable {
    private final int n;
    private final int step;
    private final Inlet<T> in;
    private final Outlet<Seq<T>> out;
    private final FlowShape<T, Seq<T>> shape;
    private final Attributes initialAttributes;

    public static <T> Option<Tuple2<Object, Object>> unapply(Sliding<T> x$0) {
        return Sliding$.MODULE$.unapply(x$0);
    }

    public static <T> Sliding<T> apply(int n, int step) {
        return Sliding$.MODULE$.apply(n, step);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int n() {
        return this.n;
    }

    public int step() {
        return this.step;
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
        return this.initialAttributes;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private Vector<T> buf;
            private final /* synthetic */ Sliding $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private Vector<T> buf() {
                return this.buf;
            }

            private void buf_$eq(Vector<T> x$1) {
                this.buf = x$1;
            }

            public void onPush() {
                this.buf_$eq((Vector)this.buf().$colon$plus(this.grab(this.$outer.in())));
                if (this.buf().size() < this.$outer.n()) {
                    this.pull(this.$outer.in());
                    return;
                }
                if (this.buf().size() == this.$outer.n()) {
                    this.push(this.$outer.out(), this.buf());
                    return;
                }
                if (this.$outer.step() <= this.$outer.n()) {
                    this.buf_$eq((Vector<T>)this.buf().drop(this.$outer.step()));
                    if (this.buf().size() == this.$outer.n()) {
                        this.push(this.$outer.out(), this.buf());
                        return;
                    }
                    this.pull(this.$outer.in());
                    return;
                }
                if (this.buf().size() == this.$outer.step()) {
                    this.buf_$eq((Vector<T>)this.buf().drop(this.$outer.step()));
                }
                this.pull(this.$outer.in());
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public void onUpstreamFinish() {
                if (this.buf().size() < this.$outer.n() && this.buf().size() > 0) {
                    this.push(this.$outer.out(), this.buf());
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
                this.buf = package$.MODULE$.Vector().empty();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <T> Sliding<T> copy(int n, int step) {
        return new Sliding<T>(n, step);
    }

    public <T> int copy$default$1() {
        return this.n();
    }

    public <T> int copy$default$2() {
        return this.step();
    }

    @Override
    public String productPrefix() {
        return "Sliding";
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
                return BoxesRunTime.boxToInteger(this.n());
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.step());
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
        return x$1 instanceof Sliding;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "n";
            }
            case 1: {
                return "step";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.n());
        n = Statics.mix(n, this.step());
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
        if (!(object instanceof Sliding)) return false;
        boolean bl = true;
        if (!bl) return false;
        Sliding sliding = (Sliding)x$1;
        if (this.n() != sliding.n()) return false;
        if (this.step() != sliding.step()) return false;
        return true;
    }

    public Sliding(int n, int step) {
        this.n = n;
        this.step = step;
        Product.$init$(this);
        Predef$.MODULE$.require(n > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "n must be greater than 0");
        Predef$.MODULE$.require(step > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "step must be greater than 0");
        this.in = Inlet$.MODULE$.apply("Sliding.in");
        this.out = Outlet$.MODULE$.apply("Sliding.out");
        this.shape = new FlowShape<T, Seq<T>>(this.in(), this.out());
        this.initialAttributes = Stages$DefaultAttributes$.MODULE$.sliding();
    }
}

