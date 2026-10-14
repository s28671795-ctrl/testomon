/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.RestartSettings;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.RestartWithBackoffFlow$;
import akka.stream.scaladsl.RestartWithBackoffLogic;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageLogic$EagerTerminateInput$;
import akka.stream.stage.GraphStageLogic$EagerTerminateOutput$;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t%d\u0001B\u0015+\rEB\u0001B\u0014\u0001\u0003\u0002\u0003\u0006Ia\u0014\u0005\t5\u0002\u0011\t\u0011)A\u00057\"Aa\f\u0001B\u0001B\u0003%q\fC\u0003c\u0001\u0011\u00051\rC\u0004n\u0001\t\u0007I\u0011\u00018\t\rI\u0004\u0001\u0015!\u0003p\u0011\u001d\u0019\bA1A\u0005\u0002QDa\u0001\u001f\u0001!\u0002\u0013)\b\"B=\u0001\t\u0003R\b\"B>\u0001\t\u0003bxaBA\u0006U!\u0005\u0011Q\u0002\u0004\u0007S)B\t!a\u0004\t\r\tdA\u0011AA\f\r\u0019\tI\u0002\u0004!\u0002\u001c!Q\u0011\u0011\u000b\b\u0003\u0016\u0004%\t!a\u0015\t\u0015\u0005\rdB!E!\u0002\u0013\t)\u0006\u0003\u0004c\u001d\u0011\u0005\u0011Q\r\u0005\n\u0003[r\u0011\u0011!C\u0001\u0003_B\u0011\"a\u001d\u000f#\u0003%\t!!\u001e\t\u0013\u0005-e\"!A\u0005B\u00055\u0005\"CAP\u001d\u0005\u0005I\u0011AAQ\u0011%\tIKDA\u0001\n\u0003\tY\u000bC\u0005\u00022:\t\t\u0011\"\u0011\u00024\"I\u0011\u0011\u0019\b\u0002\u0002\u0013\u0005\u00111\u0019\u0005\n\u0003\u000ft\u0011\u0011!C!\u0003\u0013D\u0011\"!4\u000f\u0003\u0003%\t%a4\t\u0013\u0005Eg\"!A\u0005B\u0005M\u0007\"CAk\u001d\u0005\u0005I\u0011IAl\u000f%\tY\u000eDA\u0001\u0012\u0003\tiNB\u0005\u0002\u001a1\t\t\u0011#\u0001\u0002`\"1!M\bC\u0001\u0003oD\u0011\"!5\u001f\u0003\u0003%)%a5\t\u0013\u0005eh$!A\u0005\u0002\u0006m\b\"CA\u0000=\u0005\u0005I\u0011\u0011B\u0001\u0011%\u0011iAHA\u0001\n\u0013\u0011y\u0001C\u0004\u0003\u00181!IA!\u0007\u0007\r\t=BB\u0002B\u0019\u0011)\u00119&\nB\u0001B\u0003%\u0011Q\u000b\u0005\u0007E\u0016\"\tA!\u0017\t\rm,C\u0011\tB0\u0005Y\u0011Vm\u001d;beR<\u0016\u000e\u001e5CC\u000e\\wN\u001a4GY><(BA\u0016-\u0003!\u00198-\u00197bINd'BA\u0017/\u0003\u0019\u0019HO]3b[*\tq&\u0001\u0003bW.\f7\u0001A\u000b\u0004e}b5C\u0001\u00014!\r!t'O\u0007\u0002k)\u0011a\u0007L\u0001\u0006gR\fw-Z\u0005\u0003qU\u0012!b\u0012:ba\"\u001cF/Y4f!\u0011Q4(P&\u000e\u00031J!\u0001\u0010\u0017\u0003\u0013\u0019cwn^*iCB,\u0007C\u0001 @\u0019\u0001!Q\u0001\u0011\u0001C\u0002\u0005\u0013!!\u00138\u0012\u0005\tC\u0005CA\"G\u001b\u0005!%\"A#\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u001d#%a\u0002(pi\"Lgn\u001a\t\u0003\u0007&K!A\u0013#\u0003\u0007\u0005s\u0017\u0010\u0005\u0002?\u0019\u0012)Q\n\u0001b\u0001\u0003\n\u0019q*\u001e;\u0002\u0017\u0019dwn\u001e$bGR|'/\u001f\t\u0004\u0007B\u0013\u0016BA)E\u0005%1UO\\2uS>t\u0007\u0007\r\u0002T1B)A+V\u001fL/6\t!&\u0003\u0002WU\t!a\t\\8x!\tq\u0004\fB\u0005Z\u0003\u0005\u0005\t\u0011!B\u0001\u0003\n\u0019q\f\n\u001c\u0002\u0011M,G\u000f^5oON\u0004\"A\u000f/\n\u0005uc#a\u0004*fgR\f'\u000f^*fiRLgnZ:\u0002\u001d=tG._(o\r\u0006LG.\u001e:fgB\u00111\tY\u0005\u0003C\u0012\u0013qAQ8pY\u0016\fg.\u0001\u0004=S:LGO\u0010\u000b\u0005I\u0016\\G\u000e\u0005\u0003U\u0001uZ\u0005\"\u0002(\u0005\u0001\u00041\u0007cA\"QOB\u0012\u0001N\u001b\t\u0006)Vk4*\u001b\t\u0003})$\u0011\"W3\u0002\u0002\u0003\u0005)\u0011A!\t\u000bi#\u0001\u0019A.\t\u000by#\u0001\u0019A0\u0002\u0005%tW#A8\u0011\u0007i\u0002X(\u0003\u0002rY\t)\u0011J\u001c7fi\u0006\u0019\u0011N\u001c\u0011\u0002\u0007=,H/F\u0001v!\rQdoS\u0005\u0003o2\u0012aaT;uY\u0016$\u0018\u0001B8vi\u0002\nQa\u001d5ba\u0016,\u0012!O\u0001\fGJ,\u0017\r^3M_\u001eL7\rF\u0002~\u0003\u0003\u00012\u0001\u0016@:\u0013\ty(FA\fSKN$\u0018M\u001d;XSRD')Y2l_\u001a4Gj\\4jG\"9\u00111\u0001\u0006A\u0002\u0005\u0015\u0011aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\bc\u0001\u001e\u0002\b%\u0019\u0011\u0011\u0002\u0017\u0003\u0015\u0005#HO]5ckR,7/\u0001\fSKN$\u0018M\u001d;XSRD')Y2l_\u001a4g\t\\8x!\t!FbE\u0002\r\u0003#\u00012aQA\n\u0013\r\t)\u0002\u0012\u0002\u0007\u0003:L(+\u001a4\u0015\u0005\u00055!!\u0002#fY\u0006L8#\u0003\b\u0002\u0012\u0005u\u0011\u0011HA !\u0011\ty\"a\r\u000f\t\u0005\u0005\u0012q\u0006\b\u0005\u0003G\tiC\u0004\u0003\u0002&\u0005-RBAA\u0014\u0015\r\tI\u0003M\u0001\u0007yI|w\u000e\u001e \n\u0003=J!!\f\u0018\n\u0007\u0005EB&\u0001\u0006BiR\u0014\u0018NY;uKNLA!!\u000e\u00028\tI\u0011\t\u001e;sS\n,H/\u001a\u0006\u0004\u0003ca\u0003cA\"\u0002<%\u0019\u0011Q\b#\u0003\u000fA\u0013x\u000eZ;diB!\u0011\u0011IA&\u001d\u0011\t\u0019%a\u0012\u000f\t\u0005\u0015\u0012QI\u0005\u0002\u000b&\u0019\u0011\u0011\n#\u0002\u000fA\f7m[1hK&!\u0011QJA(\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\tI\u0005R\u0001\tIV\u0014\u0018\r^5p]V\u0011\u0011Q\u000b\t\u0005\u0003/\ny&\u0004\u0002\u0002Z)!\u0011\u0011KA.\u0015\r\ti\u0006R\u0001\u000bG>t7-\u001e:sK:$\u0018\u0002BA1\u00033\u0012aBR5oSR,G)\u001e:bi&|g.A\u0005ekJ\fG/[8oAQ!\u0011qMA6!\r\tIGD\u0007\u0002\u0019!9\u0011\u0011K\tA\u0002\u0005U\u0013\u0001B2paf$B!a\u001a\u0002r!I\u0011\u0011\u000b\n\u0011\u0002\u0003\u0007\u0011QK\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\t9H\u000b\u0003\u0002V\u0005e4FAA>!\u0011\ti(a\"\u000e\u0005\u0005}$\u0002BAA\u0003\u0007\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005\u0015E)\u0001\u0006b]:|G/\u0019;j_:LA!!#\u0002\u0000\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\ty\t\u0005\u0003\u0002\u0012\u0006mUBAAJ\u0015\u0011\t)*a&\u0002\t1\fgn\u001a\u0006\u0003\u00033\u000bAA[1wC&!\u0011QTAJ\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u00111\u0015\t\u0004\u0007\u0006\u0015\u0016bAAT\t\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019\u0001*!,\t\u0013\u0005=f#!AA\u0002\u0005\r\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u00026B)\u0011qWA_\u00116\u0011\u0011\u0011\u0018\u0006\u0004\u0003w#\u0015AC2pY2,7\r^5p]&!\u0011qXA]\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007}\u000b)\r\u0003\u0005\u00020b\t\t\u00111\u0001I\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005=\u00151\u001a\u0005\n\u0003_K\u0012\u0011!a\u0001\u0003G\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003G\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u001f\u000ba!Z9vC2\u001cHcA0\u0002Z\"A\u0011q\u0016\u000f\u0002\u0002\u0003\u0007\u0001*A\u0003EK2\f\u0017\u0010E\u0002\u0002jy\u0019RAHAq\u0003[\u0004\u0002\"a9\u0002j\u0006U\u0013qM\u0007\u0003\u0003KT1!a:E\u0003\u001d\u0011XO\u001c;j[\u0016LA!a;\u0002f\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005=\u0018Q_\u0007\u0003\u0003cTA!a=\u0002\u0018\u0006\u0011\u0011n\\\u0005\u0005\u0003\u001b\n\t\u0010\u0006\u0002\u0002^\u0006)\u0011\r\u001d9msR!\u0011qMA\u007f\u0011\u001d\t\t&\ta\u0001\u0003+\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003\u0004\t%\u0001#B\"\u0003\u0006\u0005U\u0013b\u0001B\u0004\t\n1q\n\u001d;j_:D\u0011Ba\u0003#\u0003\u0003\u0005\r!a\u001a\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003\u0012A!\u0011\u0011\u0013B\n\u0013\u0011\u0011)\"a%\u0003\r=\u0013'.Z2u\u0003E!W\r\\1z\u0007\u0006t7-\u001a7mCRLwN\\\u000b\u0005\u00057\u0011\t\u0003\u0006\u0003\u0003\u001e\t5\u0002\u0003\u0003+V\u0005?\u0011yB!\n\u0011\u0007y\u0012\t\u0003\u0002\u0004\u0003$\u0011\u0012\r!\u0011\u0002\u0002)B!!q\u0005B\u0015\u001b\u0005q\u0013b\u0001B\u0016]\t9aj\u001c;Vg\u0016$\u0007bBA)I\u0001\u0007\u0011Q\u000b\u0002\u0017\t\u0016d\u0017-_\"b]\u000e,G\u000e\\1uS>t7\u000b^1hKV!!1\u0007B+'\r)#Q\u0007\t\u0007\u0005o\u0011iEa\u0015\u000f\t\te\"q\t\b\u0005\u0005w\u0011\tE\u0004\u0003\u0002\"\tu\u0012b\u0001B Y\u0005!\u0011.\u001c9m\u0013\u0011\u0011\u0019E!\u0012\u0002\r\u0019,8/\u001b8h\u0015\r\u0011y\u0004L\u0005\u0005\u0005\u0013\u0012Y%A\u0006He\u0006\u0004\bn\u0015;bO\u0016\u001c(\u0002\u0002B\"\u0005\u000bJAAa\u0014\u0003R\t12+[7qY\u0016d\u0015N\\3be\u001e\u0013\u0018\r\u001d5Ti\u0006<WM\u0003\u0003\u0003J\t-\u0003c\u0001 \u0003V\u00111!1E\u0013C\u0002\u0005\u000bQ\u0001Z3mCf$BAa\u0017\u0003^A)\u0011\u0011N\u0013\u0003T!9!qK\u0014A\u0002\u0005UC\u0003\u0002B1\u0005O\u00022\u0001\u000eB2\u0013\r\u0011)'\u000e\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\"9\u00111\u0001\u0015A\u0002\u0005\u0015\u0001")
public final class RestartWithBackoffFlow<In, Out>
extends GraphStage<FlowShape<In, Out>> {
    public final Function0<Flow<In, Out, ?>> akka$stream$scaladsl$RestartWithBackoffFlow$$flowFactory;
    public final RestartSettings akka$stream$scaladsl$RestartWithBackoffFlow$$settings;
    public final boolean akka$stream$scaladsl$RestartWithBackoffFlow$$onlyOnFailures;
    private final Inlet<In> in;
    private final Outlet<Out> out;

    public Inlet<In> in() {
        return this.in;
    }

    public Outlet<Out> out() {
        return this.out;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return new FlowShape<In, Out>(this.in(), this.out());
    }

    @Override
    public RestartWithBackoffLogic<FlowShape<In, Out>> createLogic(Attributes inheritedAttributes) {
        return new RestartWithBackoffLogic<FlowShape<In, Out>>(this, inheritedAttributes){
            private final FiniteDuration delay;
            private Option<Tuple2<GraphStageLogic.SubSourceOutlet<In>, GraphStageLogic.SubSinkInlet<Out>>> activeOutIn;
            private final /* synthetic */ RestartWithBackoffFlow $outer;
            private final Attributes inheritedAttributes$1;

            private FiniteDuration delay() {
                return this.delay;
            }

            private Option<Tuple2<GraphStageLogic.SubSourceOutlet<In>, GraphStageLogic.SubSinkInlet<Out>>> activeOutIn() {
                return this.activeOutIn;
            }

            private void activeOutIn_$eq(Option<Tuple2<GraphStageLogic.SubSourceOutlet<In>, GraphStageLogic.SubSinkInlet<Out>>> x$1) {
                this.activeOutIn = x$1;
            }

            public Class<?> logSource() {
                return this.$outer.getClass();
            }

            public void startGraph() {
                GraphStageLogic.SubSourceOutlet<In> sourceOut = this.createSubOutlet(this.$outer.in());
                GraphStageLogic.SubSinkInlet<Out> sinkIn = this.createSubInlet(this.$outer.out());
                Object graph = ((Source)((Source)Source$.MODULE$.fromGraph(sourceOut.source()).via(RestartWithBackoffFlow$.MODULE$.akka$stream$scaladsl$RestartWithBackoffFlow$$delayCancellation(this.delay()))).via(this.$outer.akka$stream$scaladsl$RestartWithBackoffFlow$$flowFactory.apply())).to(sinkIn.sink());
                this.subFusingMaterializer().materialize(graph, this.inheritedAttributes$1);
                if (this.isAvailable(this.$outer.out())) {
                    sinkIn.pull();
                }
                this.activeOutIn_$eq(new Some<Tuple2<GraphStageLogic.SubSourceOutlet<In>, GraphStageLogic.SubSinkInlet<Out>>>(new Tuple2<GraphStageLogic.SubSourceOutlet<In>, GraphStageLogic.SubSinkInlet<Out>>(sourceOut, sinkIn)));
            }

            public void backoff() {
                this.setHandler(this.$outer.in(), GraphStageLogic$EagerTerminateInput$.MODULE$);
                this.setHandler(this.$outer.out(), GraphStageLogic$EagerTerminateOutput$.MODULE$);
                this.activeOutIn().foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$backoff$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
            }

            public static final /* synthetic */ void $anonfun$backoff$1($anon$1 $this, Tuple2 x0$1) {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    GraphStageLogic.SubSourceOutlet sourceOut = (GraphStageLogic.SubSourceOutlet)tuple2._1();
                    GraphStageLogic.SubSinkInlet sinkIn = (GraphStageLogic.SubSinkInlet)tuple2._2();
                    if (!sourceOut.isClosed()) {
                        sourceOut.complete();
                    }
                    if (!sinkIn.isClosed()) {
                        sinkIn.cancel();
                    }
                    $this.activeOutIn_$eq(None$.MODULE$);
                    return;
                }
                throw new MatchError(tuple2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super("Flow", $outer.shape(), inheritedAttributes$1, $outer.akka$stream$scaladsl$RestartWithBackoffFlow$$settings, $outer.akka$stream$scaladsl$RestartWithBackoffFlow$$onlyOnFailures);
                this.delay = inheritedAttributes$1.get(new Delay(new package.DurationInt(package$.MODULE$.DurationInt(50)).millis()), ClassTag$.MODULE$.apply(Delay.class)).duration();
                this.activeOutIn = None$.MODULE$;
                this.backoff();
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$backoff$1$adapted(akka.stream.scaladsl.RestartWithBackoffFlow$$anon$1 scala.Tuple2 )}, serializedLambda);
            }
        };
    }

    public RestartWithBackoffFlow(Function0<Flow<In, Out, ?>> flowFactory, RestartSettings settings, boolean onlyOnFailures) {
        this.akka$stream$scaladsl$RestartWithBackoffFlow$$flowFactory = flowFactory;
        this.akka$stream$scaladsl$RestartWithBackoffFlow$$settings = settings;
        this.akka$stream$scaladsl$RestartWithBackoffFlow$$onlyOnFailures = onlyOnFailures;
        this.in = Inlet$.MODULE$.apply("RestartWithBackoffFlow.in");
        this.out = Outlet$.MODULE$.apply("RestartWithBackoffFlow.out");
    }

    public static class Delay
    implements Attributes.Attribute,
    Product,
    Serializable {
        private final FiniteDuration duration;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public FiniteDuration duration() {
            return this.duration;
        }

        public Delay copy(FiniteDuration duration) {
            return new Delay(duration);
        }

        public FiniteDuration copy$default$1() {
            return this.duration();
        }

        @Override
        public String productPrefix() {
            return "Delay";
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
                    return this.duration();
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
            return x$1 instanceof Delay;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "duration";
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
            if (!(object instanceof Delay)) return false;
            boolean bl = true;
            if (!bl) return false;
            Delay delay = (Delay)x$1;
            FiniteDuration finiteDuration = this.duration();
            FiniteDuration finiteDuration2 = delay.duration();
            if (finiteDuration == null) {
                if (finiteDuration2 != null) {
                    return false;
                }
            } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            if (!delay.canEqual(this)) return false;
            return true;
        }

        public Delay(FiniteDuration duration) {
            this.duration = duration;
            Product.$init$(this);
        }
    }

    private static final class DelayCancellationStage<T>
    extends GraphStages.SimpleLinearGraphStage<T> {
        public final FiniteDuration akka$stream$scaladsl$RestartWithBackoffFlow$DelayCancellationStage$$delay;

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new InHandler(this){
                private Throwable cause;
                private LoggingAdapter akka$stream$stage$StageLogging$$_log;
                private final /* synthetic */ DelayCancellationStage $outer;

                public LoggingAdapter log() {
                    return StageLogging.log$(this);
                }

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onUpstreamFinish() throws Exception {
                    InHandler.onUpstreamFinish$(this);
                }

                public void onUpstreamFailure(Throwable ex) throws Exception {
                    InHandler.onUpstreamFailure$(this, ex);
                }

                public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                    return this.akka$stream$stage$StageLogging$$_log;
                }

                public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                    this.akka$stream$stage$StageLogging$$_log = x$1;
                }

                public Class<?> logSource() {
                    return DelayCancellationStage.class;
                }

                private Throwable cause() {
                    return this.cause;
                }

                private void cause_$eq(Throwable x$1) {
                    this.cause = x$1;
                }

                public void onPush() {
                    this.push(this.$outer.out(), this.grab(this.$outer.in()));
                }

                public void onPull() {
                    this.pull(this.$outer.in());
                }

                public void onDownstreamFinish(Throwable cause) {
                    this.cause_$eq(OptionVal$Some$.MODULE$.apply(cause));
                    this.scheduleOnce((Object)"CompleteState", this.$outer.akka$stream$scaladsl$RestartWithBackoffFlow$DelayCancellationStage$$delay);
                    this.setHandler(this.$outer.in(), GraphStageLogic$EagerTerminateInput$.MODULE$);
                }

                public void onTimer(Object timerKey) {
                    this.log().debug(new StringBuilder(34).append("Stage was canceled after delay of ").append(this.$outer.akka$stream$scaladsl$RestartWithBackoffFlow$DelayCancellationStage$$delay).toString());
                    Throwable throwable = this.cause();
                    Throwable throwable2 = OptionVal$Some$.MODULE$.unapply(throwable);
                    if (!OptionVal$.MODULE$.isEmpty$extension(throwable2)) {
                        Throwable ex = OptionVal$.MODULE$.get$extension(throwable2);
                        this.cancelStage(ex);
                        return;
                    }
                    throw new IllegalStateException("Timer hitting without first getting a cancel cannot happen");
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    StageLogging.$init$(this);
                    OptionVal$.MODULE$.None();
                    this.cause = null;
                    this.setHandlers($outer.in(), $outer.out(), this);
                }
            };
        }

        public DelayCancellationStage(FiniteDuration delay) {
            this.akka$stream$scaladsl$RestartWithBackoffFlow$DelayCancellationStage$$delay = delay;
        }
    }
}

