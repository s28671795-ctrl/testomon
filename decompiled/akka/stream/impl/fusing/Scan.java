/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet$;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Scan$;
import akka.stream.impl.fusing.Scan$$anon$11$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005=h!B\u000f\u001f\u0005\u00122\u0003\u0002C*\u0001\u0005+\u0007I\u0011\u0001+\t\u0011U\u0003!\u0011#Q\u0001\n\u0005C\u0001B\u0016\u0001\u0003\u0016\u0004%\ta\u0016\u0005\t7\u0002\u0011\t\u0012)A\u00051\")A\f\u0001C\u0001;\"9!\r\u0001b\u0001\n\u0003\u001a\u0007B\u00023\u0001A\u0003%a\u0006C\u0003f\u0001\u0011\u0005c\rC\u0003k\u0001\u0011\u00053\u000eC\u0003u\u0001\u0011\u0005S\u000fC\u0004|\u0001\u0005\u0005I\u0011\u0001?\t\u0013\u00055\u0001!%A\u0005\u0002\u0005=\u0001\"CA\u0016\u0001E\u0005I\u0011AA\u0017\u0011%\t9\u0004AA\u0001\n\u0003\nI\u0004C\u0005\u0002J\u0001\t\t\u0011\"\u0001\u0002L!I\u00111\u000b\u0001\u0002\u0002\u0013\u0005\u0011Q\u000b\u0005\n\u00037\u0002\u0011\u0011!C!\u0003;B\u0011\"a\u001b\u0001\u0003\u0003%\t!!\u001c\t\u0013\u0005]\u0004!!A\u0005B\u0005e\u0004\"CA?\u0001\u0005\u0005I\u0011IA@\u0011%\t\t\tAA\u0001\n\u0003\n\u0019i\u0002\u0006\u0002\u0014z\t\t\u0011#\u0001%\u0003+3\u0011\"\b\u0010\u0002\u0002#\u0005A%a&\t\rq;B\u0011AAU\u0011!Qw#!A\u0005F\u0005-\u0006\"CAW/\u0005\u0005I\u0011QAX\u0011%\t\u0019mFA\u0001\n\u0003\u000b)\rC\u0005\u0002f^\t\t\u0011\"\u0003\u0002h\n!1kY1o\u0015\ty\u0002%\u0001\u0004gkNLgn\u001a\u0006\u0003C\t\nA![7qY*\u00111\u0005J\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0015\nA!Y6lCV\u0019q\u0005\u000e\"\u0014\t\u0001ACi\u0012\t\u0004S1rS\"\u0001\u0016\u000b\u0005-\u0012\u0013!B:uC\u001e,\u0017BA\u0017+\u0005)9%/\u00199i'R\fw-\u001a\t\u0005_A\u0012\u0014)D\u0001#\u0013\t\t$EA\u0005GY><8\u000b[1qKB\u00111\u0007\u000e\u0007\u0001\t\u0015)\u0004A1\u00018\u0005\tIen\u0001\u0001\u0012\u0005ar\u0004CA\u001d=\u001b\u0005Q$\"A\u001e\u0002\u000bM\u001c\u0017\r\\1\n\u0005uR$a\u0002(pi\"Lgn\u001a\t\u0003s}J!\u0001\u0011\u001e\u0003\u0007\u0005s\u0017\u0010\u0005\u00024\u0005\u0012)1\t\u0001b\u0001o\t\u0019q*\u001e;\u0011\u0005e*\u0015B\u0001$;\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001\u0013)\u000f\u0005%seB\u0001&N\u001b\u0005Y%B\u0001'7\u0003\u0019a$o\\8u}%\t1(\u0003\u0002Pu\u00059\u0001/Y2lC\u001e,\u0017BA)S\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\ty%(\u0001\u0003{KJ|W#A!\u0002\u000bi,'o\u001c\u0011\u0002\u0003\u0019,\u0012\u0001\u0017\t\u0006se\u000b%'Q\u0005\u00035j\u0012\u0011BR;oGRLwN\u001c\u001a\u0002\u0005\u0019\u0004\u0013A\u0002\u001fj]&$h\bF\u0002_A\u0006\u0004Ba\u0018\u00013\u00036\ta\u0004C\u0003T\u000b\u0001\u0007\u0011\tC\u0003W\u000b\u0001\u0007\u0001,A\u0003tQ\u0006\u0004X-F\u0001/\u0003\u0019\u0019\b.\u00199fA\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0003\u001d\u0004\"a\f5\n\u0005%\u0014#AC!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\rF\u0001m!\ti\u0017O\u0004\u0002o_B\u0011!JO\u0005\u0003aj\na\u0001\u0015:fI\u00164\u0017B\u0001:t\u0005\u0019\u0019FO]5oO*\u0011\u0001OO\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002wsB\u0011\u0011f^\u0005\u0003q*\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0006u*\u0001\raZ\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm]\u0001\u0005G>\u0004\u00180F\u0003~\u0003\u0003\t)\u0001F\u0003\u007f\u0003\u000f\tI\u0001E\u0003`\u0001}\f\u0019\u0001E\u00024\u0003\u0003!Q!N\u0006C\u0002]\u00022aMA\u0003\t\u0015\u00195B1\u00018\u0011!\u00196\u0002%AA\u0002\u0005\r\u0001\u0002\u0003,\f!\u0003\u0005\r!a\u0003\u0011\u000feJ\u00161A@\u0002\u0004\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCBA\t\u0003O\tI#\u0006\u0002\u0002\u0014)\u001a\u0011)!\u0006,\u0005\u0005]\u0001\u0003BA\r\u0003Gi!!a\u0007\u000b\t\u0005u\u0011qD\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\t;\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003K\tYBA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Q!\u000e\u0007C\u0002]\"Qa\u0011\u0007C\u0002]\nabY8qs\u0012\"WMZ1vYR$#'\u0006\u0004\u00020\u0005M\u0012QG\u000b\u0003\u0003cQ3\u0001WA\u000b\t\u0015)TB1\u00018\t\u0015\u0019UB1\u00018\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\b\t\u0005\u0003{\t9%\u0004\u0002\u0002@)!\u0011\u0011IA\"\u0003\u0011a\u0017M\\4\u000b\u0005\u0005\u0015\u0013\u0001\u00026bm\u0006L1A]A \u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\ti\u0005E\u0002:\u0003\u001fJ1!!\u0015;\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\rq\u0014q\u000b\u0005\n\u00033\u0002\u0012\u0011!a\u0001\u0003\u001b\n1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA0!\u0015\t\t'a\u001a?\u001b\t\t\u0019GC\u0002\u0002fi\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\tI'a\u0019\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003_\n)\bE\u0002:\u0003cJ1!a\u001d;\u0005\u001d\u0011un\u001c7fC:D\u0001\"!\u0017\u0013\u0003\u0003\u0005\rAP\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002<\u0005m\u0004\"CA-'\u0005\u0005\t\u0019AA'\u0003!A\u0017m\u001d5D_\u0012,GCAA'\u0003\u0019)\u0017/^1mgR!\u0011qNAC\u0011!\tI&FA\u0001\u0002\u0004q\u0004f\u0001\u0001\u0002\nB!\u00111RAH\u001b\t\tiIC\u0002\u0002\"\u0011JA!!%\u0002\u000e\nY\u0011J\u001c;fe:\fG.\u00119j\u0003\u0011\u00196-\u00198\u0011\u0005};2#B\f\u0002\u001a\u0006}\u0005cA\u001d\u0002\u001c&\u0019\u0011Q\u0014\u001e\u0003\r\u0005s\u0017PU3g!\u0011\t\t+a*\u000e\u0005\u0005\r&\u0002BAS\u0003\u0007\n!![8\n\u0007E\u000b\u0019\u000b\u0006\u0002\u0002\u0016R\u0011\u00111H\u0001\u0006CB\u0004H._\u000b\u0007\u0003c\u000b9,a/\u0015\r\u0005M\u0016QXA`!\u0019y\u0006!!.\u0002:B\u00191'a.\u0005\u000bUR\"\u0019A\u001c\u0011\u0007M\nY\fB\u0003D5\t\u0007q\u0007\u0003\u0004T5\u0001\u0007\u0011\u0011\u0018\u0005\u0007-j\u0001\r!!1\u0011\u0011eJ\u0016\u0011XA[\u0003s\u000bq!\u001e8baBd\u00170\u0006\u0004\u0002H\u0006u\u0017q\u001b\u000b\u0005\u0003\u0013\fy\u000eE\u0003:\u0003\u0017\fy-C\u0002\u0002Nj\u0012aa\u00149uS>t\u0007cB\u001d\u0002R\u0006U\u0017\u0011\\\u0005\u0004\u0003'T$A\u0002+va2,'\u0007E\u00024\u0003/$QaQ\u000eC\u0002]\u0002\u0002\"O-\u0002V\u0006m\u0017Q\u001b\t\u0004g\u0005uG!B\u001b\u001c\u0005\u00049\u0004\"CAq7\u0005\u0005\t\u0019AAr\u0003\rAH\u0005\r\t\u0007?\u0002\tY.!6\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005%\b\u0003BA\u001f\u0003WLA!!<\u0002@\t1qJ\u00196fGR\u0004")
@InternalApi
public final class Scan<In, Out>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final Out zero;
    private final Function2<Out, In, Out> f;
    private final FlowShape<In, Out> shape;

    public static <In, Out> Option<Tuple2<Out, Function2<Out, In, Out>>> unapply(Scan<In, Out> x$0) {
        return Scan$.MODULE$.unapply(x$0);
    }

    public static <In, Out> Scan<In, Out> apply(Out zero, Function2<Out, In, Out> f) {
        return Scan$.MODULE$.apply(zero, f);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Out zero() {
        return this.zero;
    }

    public Function2<Out, In, Out> f() {
        return this.f;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.scan().and(Attributes$SourceLocation$.MODULE$.forLambda(this.f()));
    }

    public String toString() {
        return "Scan";
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> decider;
            private Out akka$stream$impl$fusing$Scan$$anon$$aggregator;
            private volatile boolean bitmap$0;
            private final /* synthetic */ Scan $outer;
            private Attributes inheritedAttributes$6;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public Out akka$stream$impl$fusing$Scan$$anon$$aggregator() {
                return this.akka$stream$impl$fusing$Scan$$anon$$aggregator;
            }

            private void aggregator_$eq(Out x$1) {
                this.akka$stream$impl$fusing$Scan$$anon$$aggregator = x$1;
            }

            private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                $anon$11 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$6.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                this.inheritedAttributes$6 = null;
                return this.decider;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.decider;
            }

            public void onPull() {
                this.pull(((FlowShape)this.$outer.shape()).in());
            }

            public void onPush() {
                try {
                    this.aggregator_$eq(this.$outer.f().apply(this.akka$stream$impl$fusing$Scan$$anon$$aggregator(), this.grab(((FlowShape)this.$outer.shape()).in())));
                    this.push(((FlowShape)this.$outer.shape()).out(), this.akka$stream$impl$fusing$Scan$$anon$$aggregator());
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        Supervision.Directive directive = this.decider().apply(ex);
                        if (Supervision$Resume$.MODULE$.equals(directive)) {
                            if (!this.hasBeenPulled(((FlowShape)this.$outer.shape()).in())) {
                                this.pull(((FlowShape)this.$outer.shape()).in());
                            }
                        }
                        if (Supervision$Stop$.MODULE$.equals(directive)) {
                            this.failStage(ex);
                        }
                        if (Supervision$Restart$.MODULE$.equals(directive)) {
                            this.aggregator_$eq(this.$outer.zero());
                            this.push(((FlowShape)this.$outer.shape()).out(), this.akka$stream$impl$fusing$Scan$$anon$$aggregator());
                        }
                        throw new MatchError(directive);
                    }
                    throw throwable;
                }
            }

            public /* synthetic */ Scan akka$stream$impl$fusing$Scan$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$6 = inheritedAttributes$6;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.akka$stream$impl$fusing$Scan$$anon$$aggregator = $outer.zero();
                this.setHandlers(((FlowShape)$outer.shape()).in(), ((FlowShape)$outer.shape()).out(), new InHandler(this){
                    private final /* synthetic */ $anon$11 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                    }

                    public void onUpstreamFinish() {
                        this.$outer.setHandler(((FlowShape)this.$outer.akka$stream$impl$fusing$Scan$$anon$$$outer().shape()).out(), new OutHandler(this){
                            private final /* synthetic */ $anon$11$$anon$12 $outer;

                            public void onDownstreamFinish() throws Exception {
                                OutHandler.onDownstreamFinish$(this);
                            }

                            public void onDownstreamFinish(Throwable cause) throws Exception {
                                OutHandler.onDownstreamFinish$(this, cause);
                            }

                            public void onPull() {
                                this.$outer.akka$stream$impl$fusing$Scan$$anon$$anon$$$outer().push(((FlowShape)this.$outer.akka$stream$impl$fusing$Scan$$anon$$anon$$$outer().akka$stream$impl$fusing$Scan$$anon$$$outer().shape()).out(), this.$outer.akka$stream$impl$fusing$Scan$$anon$$anon$$$outer().akka$stream$impl$fusing$Scan$$anon$$aggregator());
                                this.$outer.akka$stream$impl$fusing$Scan$$anon$$anon$$$outer().completeStage();
                            }
                            {
                                if ($outer == null) {
                                    throw null;
                                }
                                this.$outer = $outer;
                                OutHandler.$init$(this);
                            }
                        });
                    }

                    public void onPull() {
                        this.$outer.push(((FlowShape)this.$outer.akka$stream$impl$fusing$Scan$$anon$$$outer().shape()).out(), this.$outer.akka$stream$impl$fusing$Scan$$anon$$aggregator());
                        this.$outer.setHandlers(((FlowShape)this.$outer.akka$stream$impl$fusing$Scan$$anon$$$outer().shape()).in(), ((FlowShape)this.$outer.akka$stream$impl$fusing$Scan$$anon$$$outer().shape()).out(), this.$outer);
                    }

                    public /* synthetic */ $anon$11 akka$stream$impl$fusing$Scan$$anon$$anon$$$outer() {
                        return this.$outer;
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                        OutHandler.$init$(this);
                    }
                });
            }
        };
    }

    public <In, Out> Scan<In, Out> copy(Out zero, Function2<Out, In, Out> f) {
        return new Scan<In, Out>(zero, f);
    }

    public <In, Out> Out copy$default$1() {
        return this.zero();
    }

    public <In, Out> Function2<Out, In, Out> copy$default$2() {
        return this.f();
    }

    @Override
    public String productPrefix() {
        return "Scan";
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
                return this.zero();
            }
            case 1: {
                return this.f();
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
        return x$1 instanceof Scan;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "zero";
            }
            case 1: {
                return "f";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof Scan)) return false;
        boolean bl = true;
        if (!bl) return false;
        Scan scan = (Scan)x$1;
        if (!BoxesRunTime.equals(this.zero(), scan.zero())) return false;
        Function2<Out, In, Out> function2 = this.f();
        Function2<Out, In, Out> function22 = scan.f();
        if (function2 != null) {
            if (!function2.equals(function22)) return false;
            return true;
        }
        if (function22 == null) return true;
        return false;
    }

    public Scan(Out zero, Function2<Out, In, Out> f) {
        this.zero = zero;
        this.f = f;
        Product.$init$(this);
        this.shape = new FlowShape(Inlet$.MODULE$.apply("Scan.in"), Outlet$.MODULE$.apply("Scan.out"));
    }
}

