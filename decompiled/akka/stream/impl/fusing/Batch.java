/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.FlowShape$;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.impl.fusing.Batch$;
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
import scala.Tuple4;
import scala.collection.Iterator;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t=c!\u0002\u0014(\u00056z\u0003\u0002\u0003/\u0001\u0005+\u0007I\u0011A/\t\u0011\u0005\u0004!\u0011#Q\u0001\nyC\u0001B\u0019\u0001\u0003\u0016\u0004%\ta\u0019\u0005\tO\u0002\u0011\t\u0012)A\u0005I\"A\u0001\u000e\u0001BK\u0002\u0013\u0005\u0011\u000e\u0003\u0005l\u0001\tE\t\u0015!\u0003k\u0011!a\u0007A!f\u0001\n\u0003i\u0007\u0002C9\u0001\u0005#\u0005\u000b\u0011\u00028\t\u000bI\u0004A\u0011A:\t\u000fi\u0004!\u0019!C\u0001w\"1q\u0010\u0001Q\u0001\nqD\u0011\"!\u0001\u0001\u0005\u0004%\t!a\u0001\t\u0011\u0005-\u0001\u0001)A\u0005\u0003\u000bA\u0011\"!\u0004\u0001\u0005\u0004%\t%a\u0004\t\u000f\u0005E\u0001\u0001)A\u0005o!9\u00111\u0003\u0001\u0005B\u0005U\u0001\"CA\u0014\u0001\u0005\u0005I\u0011AA\u0015\u0011%\t)\u0005AI\u0001\n\u0003\t9\u0005C\u0005\u0002d\u0001\t\n\u0011\"\u0001\u0002f!I\u0011q\u000e\u0001\u0012\u0002\u0013\u0005\u0011\u0011\u000f\u0005\n\u0003w\u0002\u0011\u0013!C\u0001\u0003{B\u0011\"a\"\u0001\u0003\u0003%\t%!#\t\u0013\u0005m\u0005!!A\u0005\u0002\u0005u\u0005\"CAS\u0001\u0005\u0005I\u0011AAT\u0011%\ti\u000bAA\u0001\n\u0003\ny\u000bC\u0005\u0002>\u0002\t\t\u0011\"\u0001\u0002@\"I\u0011\u0011\u001a\u0001\u0002\u0002\u0013\u0005\u00131\u001a\u0005\n\u0003\u001f\u0004\u0011\u0011!C!\u0003#D\u0011\"a5\u0001\u0003\u0003%\t%!6\t\u0013\u0005]\u0007!!A\u0005B\u0005ewACAuO\u0005\u0005\t\u0012A\u0017\u0002l\u001aIaeJA\u0001\u0012\u0003i\u0013Q\u001e\u0005\u0007e\u0002\"\t!a@\t\u0013\u0005M\u0007%!A\u0005F\u0005U\u0007\"\u0003B\u0001A\u0005\u0005I\u0011\u0011B\u0002\u0011%\u0011y\u0002IA\u0001\n\u0003\u0013\t\u0003C\u0005\u0003F\u0001\n\t\u0011\"\u0003\u0003H\t)!)\u0019;dQ*\u0011\u0001&K\u0001\u0007MV\u001c\u0018N\\4\u000b\u0005)Z\u0013\u0001B5na2T!\u0001L\u0017\u0002\rM$(/Z1n\u0015\u0005q\u0013\u0001B1lW\u0006,2\u0001M\u001fL'\u0011\u0001\u0011'\u0014)\u0011\u0007I*t'D\u00014\u0015\t!4&A\u0003ti\u0006<W-\u0003\u00027g\tQqI]1qQN#\u0018mZ3\u0011\taJ4HS\u0007\u0002W%\u0011!h\u000b\u0002\n\r2|wo\u00155ba\u0016\u0004\"\u0001P\u001f\r\u0001\u0011)a\b\u0001b\u0001\u0001\n\u0011\u0011J\\\u0002\u0001#\t\tu\t\u0005\u0002C\u000b6\t1IC\u0001E\u0003\u0015\u00198-\u00197b\u0013\t15IA\u0004O_RD\u0017N\\4\u0011\u0005\tC\u0015BA%D\u0005\r\te.\u001f\t\u0003y-#Q\u0001\u0014\u0001C\u0002\u0001\u00131aT;u!\t\u0011e*\u0003\u0002P\u0007\n9\u0001K]8ek\u000e$\bCA)Z\u001d\t\u0011vK\u0004\u0002T-6\tAK\u0003\u0002V\u007f\u00051AH]8pizJ\u0011\u0001R\u0005\u00031\u000e\u000bq\u0001]1dW\u0006<W-\u0003\u0002[7\na1+\u001a:jC2L'0\u00192mK*\u0011\u0001lQ\u0001\u0004[\u0006DX#\u00010\u0011\u0005\t{\u0016B\u00011D\u0005\u0011auN\\4\u0002\t5\f\u0007\u0010I\u0001\u0007G>\u001cHO\u00128\u0016\u0003\u0011\u0004BAQ3<=&\u0011am\u0011\u0002\n\rVt7\r^5p]F\nqaY8ti\u001as\u0007%\u0001\u0003tK\u0016$W#\u00016\u0011\t\t+7HS\u0001\u0006g\u0016,G\rI\u0001\nC\u001e<'/Z4bi\u0016,\u0012A\u001c\t\u0006\u0005>T5HS\u0005\u0003a\u000e\u0013\u0011BR;oGRLwN\u001c\u001a\u0002\u0015\u0005<wM]3hCR,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0006iZ<\b0\u001f\t\u0005k\u0002Y$*D\u0001(\u0011\u0015a\u0016\u00021\u0001_\u0011\u0015\u0011\u0017\u00021\u0001e\u0011\u0015A\u0017\u00021\u0001k\u0011\u0015a\u0017\u00021\u0001o\u0003\tIg.F\u0001}!\rATpO\u0005\u0003}.\u0012Q!\u00138mKR\f1!\u001b8!\u0003\ryW\u000f^\u000b\u0003\u0003\u000b\u0001B\u0001OA\u0004\u0015&\u0019\u0011\u0011B\u0016\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f\u001e\u0011\u0002\u000bMD\u0017\r]3\u0016\u0003]\naa\u001d5ba\u0016\u0004\u0013aC2sK\u0006$X\rT8hS\u000e$B!a\u0006\u0002\u001eA\u0019!'!\u0007\n\u0007\u0005m1GA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u001d\ty\u0002\u0005a\u0001\u0003C\t1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\u00042\u0001OA\u0012\u0013\r\t)c\u000b\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001B2paf,b!a\u000b\u00022\u0005UBCCA\u0017\u0003o\tI$!\u0010\u0002BA1Q\u000fAA\u0018\u0003g\u00012\u0001PA\u0019\t\u0015q\u0014C1\u0001A!\ra\u0014Q\u0007\u0003\u0006\u0019F\u0011\r\u0001\u0011\u0005\b9F\u0001\n\u00111\u0001_\u0011!\u0011\u0017\u0003%AA\u0002\u0005m\u0002#\u0002\"f\u0003_q\u0006\u0002\u00035\u0012!\u0003\u0005\r!a\u0010\u0011\r\t+\u0017qFA\u001a\u0011!a\u0017\u0003%AA\u0002\u0005\r\u0003\u0003\u0003\"p\u0003g\ty#a\r\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU1\u0011\u0011JA0\u0003C*\"!a\u0013+\u0007y\u000bie\u000b\u0002\u0002PA!\u0011\u0011KA.\u001b\t\t\u0019F\u0003\u0003\u0002V\u0005]\u0013!C;oG\",7m[3e\u0015\r\tIfQ\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA/\u0003'\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u0015q$C1\u0001A\t\u0015a%C1\u0001A\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*b!a\u001a\u0002l\u00055TCAA5U\r!\u0017Q\n\u0003\u0006}M\u0011\r\u0001\u0011\u0003\u0006\u0019N\u0011\r\u0001Q\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\u0019\t\u0019(a\u001e\u0002zU\u0011\u0011Q\u000f\u0016\u0004U\u00065C!\u0002 \u0015\u0005\u0004\u0001E!\u0002'\u0015\u0005\u0004\u0001\u0015AD2paf$C-\u001a4bk2$H\u0005N\u000b\u0007\u0003\u007f\n\u0019)!\"\u0016\u0005\u0005\u0005%f\u00018\u0002N\u0011)a(\u0006b\u0001\u0001\u0012)A*\u0006b\u0001\u0001\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"!a#\u0011\t\u00055\u0015qS\u0007\u0003\u0003\u001fSA!!%\u0002\u0014\u0006!A.\u00198h\u0015\t\t)*\u0001\u0003kCZ\f\u0017\u0002BAM\u0003\u001f\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXCAAP!\r\u0011\u0015\u0011U\u0005\u0004\u0003G\u001b%aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HcA$\u0002*\"I\u00111\u0016\r\u0002\u0002\u0003\u0007\u0011qT\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005E\u0006#BAZ\u0003s;UBAA[\u0015\r\t9lQ\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA^\u0003k\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011\u0011YAd!\r\u0011\u00151Y\u0005\u0004\u0003\u000b\u001c%a\u0002\"p_2,\u0017M\u001c\u0005\t\u0003WS\u0012\u0011!a\u0001\u000f\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\tY)!4\t\u0013\u0005-6$!AA\u0002\u0005}\u0015\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005}\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005-\u0015AB3rk\u0006d7\u000f\u0006\u0003\u0002B\u0006m\u0007\u0002CAV=\u0005\u0005\t\u0019A$)\u0007\u0001\ty\u000e\u0005\u0003\u0002b\u0006\u0015XBAAr\u0015\r\tI&L\u0005\u0005\u0003O\f\u0019OA\u0006J]R,'O\\1m\u0003BL\u0017!\u0002\"bi\u000eD\u0007CA;!'\u0015\u0001\u0013q^A{!\r\u0011\u0015\u0011_\u0005\u0004\u0003g\u001c%AB!osJ+g\r\u0005\u0003\u0002x\u0006uXBAA}\u0015\u0011\tY0a%\u0002\u0005%|\u0017b\u0001.\u0002zR\u0011\u00111^\u0001\u0006CB\u0004H._\u000b\u0007\u0005\u000b\u0011YAa\u0004\u0015\u0015\t\u001d!\u0011\u0003B\n\u0005/\u0011Y\u0002\u0005\u0004v\u0001\t%!Q\u0002\t\u0004y\t-A!\u0002 $\u0005\u0004\u0001\u0005c\u0001\u001f\u0003\u0010\u0011)Aj\tb\u0001\u0001\")Al\ta\u0001=\"1!m\ta\u0001\u0005+\u0001RAQ3\u0003\nyCa\u0001[\u0012A\u0002\te\u0001C\u0002\"f\u0005\u0013\u0011i\u0001\u0003\u0004mG\u0001\u0007!Q\u0004\t\t\u0005>\u0014iA!\u0003\u0003\u000e\u00059QO\\1qa2LXC\u0002B\u0012\u0005k\u0011Y\u0004\u0006\u0003\u0003&\t}\u0002#\u0002\"\u0003(\t-\u0012b\u0001B\u0015\u0007\n1q\n\u001d;j_:\u0004\"B\u0011B\u0017=\nE\"q\u0007B\u001f\u0013\r\u0011yc\u0011\u0002\u0007)V\u0004H.\u001a\u001b\u0011\u000b\t+'1\u00070\u0011\u0007q\u0012)\u0004B\u0003?I\t\u0007\u0001\t\u0005\u0004CK\nM\"\u0011\b\t\u0004y\tmB!\u0002'%\u0005\u0004\u0001\u0005\u0003\u0003\"p\u0005s\u0011\u0019D!\u000f\t\u0013\t\u0005C%!AA\u0002\t\r\u0013a\u0001=%aA1Q\u000f\u0001B\u001a\u0005s\tAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"A!\u0013\u0011\t\u00055%1J\u0005\u0005\u0005\u001b\nyI\u0001\u0004PE*,7\r\u001e")
@InternalApi
public final class Batch<In, Out>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final long max;
    private final Function1<In, Object> costFn;
    private final Function1<In, Out> seed;
    private final Function2<Out, In, Out> aggregate;
    private final Inlet<In> in;
    private final Outlet<Out> out;
    private final FlowShape<In, Out> shape;

    public static <In, Out> Option<Tuple4<Object, Function1<In, Object>, Function1<In, Out>, Function2<Out, In, Out>>> unapply(Batch<In, Out> x$0) {
        return Batch$.MODULE$.unapply(x$0);
    }

    public static <In, Out> Batch<In, Out> apply(long max, Function1<In, Object> costFn, Function1<In, Out> seed, Function2<Out, In, Out> aggregate) {
        return Batch$.MODULE$.apply(max, costFn, seed, aggregate);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public long max() {
        return this.max;
    }

    public Function1<In, Object> costFn() {
        return this.costFn;
    }

    public Function1<In, Out> seed() {
        return this.seed;
    }

    public Function2<Out, In, Out> aggregate() {
        return this.aggregate;
    }

    public Inlet<In> in() {
        return this.in;
    }

    public Outlet<Out> out() {
        return this.out;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> decider;
            private Out agg;
            private long left;
            private In pending;
            private final ContextPropagation contextPropagation;
            private volatile boolean bitmap$0;
            private final /* synthetic */ Batch $outer;
            private Attributes inheritedAttributes$12;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                $anon$26 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$12.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                this.inheritedAttributes$12 = null;
                return this.decider;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.decider;
            }

            private Out agg() {
                return this.agg;
            }

            private void agg_$eq(Out x$1) {
                this.agg = x$1;
            }

            private long left() {
                return this.left;
            }

            private void left_$eq(long x$1) {
                this.left = x$1;
            }

            private In pending() {
                return this.pending;
            }

            private void pending_$eq(In x$1) {
                this.pending = x$1;
            }

            private ContextPropagation contextPropagation() {
                return this.contextPropagation;
            }

            private void flush() {
                if (this.agg() != null) {
                    this.push(this.$outer.out(), this.agg());
                    this.left_$eq(this.$outer.max());
                }
                if (this.pending() != null) {
                    try {
                        this.agg_$eq(this.$outer.seed().apply(this.pending()));
                        this.left_$eq(this.left() - BoxesRunTime.unboxToLong(this.$outer.costFn().apply(this.pending())));
                        this.pending_$eq(null);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable ex = option.get();
                            Supervision.Directive directive = this.decider().apply(ex);
                            if (Supervision$Stop$.MODULE$.equals(directive)) {
                                this.failStage(ex);
                            }
                            if (Supervision$Restart$.MODULE$.equals(directive)) {
                                this.restartState();
                            }
                            if (Supervision$Resume$.MODULE$.equals(directive)) {
                                this.pending_$eq(null);
                            }
                            throw new MatchError(directive);
                        }
                        throw throwable;
                    }
                    return;
                }
                this.agg_$eq(null);
            }

            public void preStart() {
                this.pull(this.$outer.in());
            }

            public void onPush() {
                In elem = this.grab(this.$outer.in());
                long cost = BoxesRunTime.unboxToLong(this.$outer.costFn().apply(elem));
                this.contextPropagation().suspendContext();
                if (this.agg() == null) {
                    try {
                        this.agg_$eq(this.$outer.seed().apply(elem));
                        this.left_$eq(this.left() - cost);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable ex = option.get();
                            Supervision.Directive directive = this.decider().apply(ex);
                            if (Supervision$Stop$.MODULE$.equals(directive)) {
                                this.failStage(ex);
                            }
                            if (Supervision$Restart$.MODULE$.equals(directive)) {
                                this.restartState();
                            }
                            if (Supervision$Resume$.MODULE$.equals(directive)) {
                            }
                            throw new MatchError(directive);
                        }
                        throw throwable;
                    }
                } else if (this.left() < cost) {
                    this.pending_$eq(elem);
                } else {
                    try {
                        this.agg_$eq(this.$outer.aggregate().apply(this.agg(), elem));
                        this.left_$eq(this.left() - cost);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable3 = throwable;
                        if (throwable3 != null && !(option = NonFatal$.MODULE$.unapply(throwable3)).isEmpty()) {
                            Throwable ex = option.get();
                            Supervision.Directive directive = this.decider().apply(ex);
                            if (Supervision$Stop$.MODULE$.equals(directive)) {
                                this.failStage(ex);
                            }
                            if (Supervision$Restart$.MODULE$.equals(directive)) {
                                this.restartState();
                            }
                            if (Supervision$Resume$.MODULE$.equals(directive)) {
                            }
                            throw new MatchError(directive);
                        }
                        throw throwable;
                    }
                }
                if (this.isAvailable(this.$outer.out())) {
                    this.flush();
                }
                if (this.pending() == null) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onUpstreamFinish() {
                if (this.agg() == null) {
                    this.completeStage();
                    return;
                }
            }

            public void onPull() {
                if (this.agg() == null) {
                    if (this.isClosed(this.$outer.in())) {
                        this.completeStage();
                        return;
                    }
                    if (!this.hasBeenPulled(this.$outer.in())) {
                        this.pull(this.$outer.in());
                        return;
                    }
                    return;
                }
                if (this.isClosed(this.$outer.in())) {
                    this.contextPropagation().resumeContext();
                    this.push(this.$outer.out(), this.agg());
                    if (this.pending() == null) {
                        this.completeStage();
                        return;
                    }
                    try {
                        this.agg_$eq(this.$outer.seed().apply(this.pending()));
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable ex = option.get();
                            Supervision.Directive directive = this.decider().apply(ex);
                            if (Supervision$Stop$.MODULE$.equals(directive)) {
                                this.failStage(ex);
                            }
                            if (Supervision$Resume$.MODULE$.equals(directive)) {
                            }
                            if (Supervision$Restart$.MODULE$.equals(directive)) {
                                this.restartState();
                                if (!this.hasBeenPulled(this.$outer.in())) {
                                    this.pull(this.$outer.in());
                                }
                            }
                            throw new MatchError(directive);
                        }
                        throw throwable;
                    }
                    this.pending_$eq(null);
                    return;
                }
                this.contextPropagation().resumeContext();
                this.flush();
                if (!this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            private void restartState() {
                this.agg_$eq(null);
                this.left_$eq(this.$outer.max());
                this.pending_$eq(null);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$12 = inheritedAttributes$12;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.agg = null;
                this.left = $outer.max();
                this.pending = null;
                this.contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <In, Out> Batch<In, Out> copy(long max, Function1<In, Object> costFn, Function1<In, Out> seed, Function2<Out, In, Out> aggregate) {
        return new Batch<In, Out>(max, costFn, seed, aggregate);
    }

    public <In, Out> long copy$default$1() {
        return this.max();
    }

    public <In, Out> Function1<In, Object> copy$default$2() {
        return this.costFn();
    }

    public <In, Out> Function1<In, Out> copy$default$3() {
        return this.seed();
    }

    public <In, Out> Function2<Out, In, Out> copy$default$4() {
        return this.aggregate();
    }

    @Override
    public String productPrefix() {
        return "Batch";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToLong(this.max());
            }
            case 1: {
                return this.costFn();
            }
            case 2: {
                return this.seed();
            }
            case 3: {
                return this.aggregate();
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
        return x$1 instanceof Batch;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "max";
            }
            case 1: {
                return "costFn";
            }
            case 2: {
                return "seed";
            }
            case 3: {
                return "aggregate";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.longHash(this.max()));
        n = Statics.mix(n, Statics.anyHash(this.costFn()));
        n = Statics.mix(n, Statics.anyHash(this.seed()));
        n = Statics.mix(n, Statics.anyHash(this.aggregate()));
        return Statics.finalizeHash(n, 4);
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
        if (!(object instanceof Batch)) return false;
        boolean bl = true;
        if (!bl) return false;
        Batch batch = (Batch)x$1;
        if (this.max() != batch.max()) return false;
        Function1<In, Object> function1 = this.costFn();
        Function1<In, Object> function12 = batch.costFn();
        if (function1 == null) {
            if (function12 != null) {
                return false;
            }
        } else if (!function1.equals(function12)) return false;
        Function1<In, Out> function13 = this.seed();
        Function1<In, Out> function14 = batch.seed();
        if (function13 == null) {
            if (function14 != null) {
                return false;
            }
        } else if (!function13.equals(function14)) return false;
        Function2<Out, In, Out> function2 = this.aggregate();
        Function2<Out, In, Out> function22 = batch.aggregate();
        if (function2 == null) {
            if (function22 == null) return true;
            return false;
        } else {
            if (!function2.equals(function22)) return false;
            return true;
        }
    }

    public Batch(long max, Function1<In, Object> costFn, Function1<In, Out> seed, Function2<Out, In, Out> aggregate) {
        this.max = max;
        this.costFn = costFn;
        this.seed = seed;
        this.aggregate = aggregate;
        Product.$init$(this);
        this.in = Inlet$.MODULE$.apply("Batch.in");
        this.out = Outlet$.MODULE$.apply("Batch.out");
        this.shape = FlowShape$.MODULE$.of(this.in(), this.out());
    }
}

