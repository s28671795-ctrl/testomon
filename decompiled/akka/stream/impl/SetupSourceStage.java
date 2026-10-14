/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.SetupSourceStage$;
import akka.stream.impl.SetupStage$;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005]4QAC\u0006\u0003\u001bEA\u0001\"\u000e\u0001\u0003\u0002\u0003\u0006IA\u000e\u0005\u0006\u000b\u0002!\tA\u0012\u0005\b\u0015\u0002\u0011\r\u0011\"\u0003L\u0011\u0019y\u0005\u0001)A\u0005\u0019\"9\u0001\u000b\u0001b\u0001\n\u0003\n\u0006B\u0002*\u0001A\u0003%\u0011\u0004C\u0003T\u0001\u0011EC\u000bC\u0003V\u0001\u0011\u0005c\u000bC\u0003`\u0001\u0011%\u0001M\u0001\tTKR,\boU8ve\u000e,7\u000b^1hK*\u0011A\"D\u0001\u0005S6\u0004HN\u0003\u0002\u000f\u001f\u000511\u000f\u001e:fC6T\u0011\u0001E\u0001\u0005C.\\\u0017-F\u0002\u0013?M\u001a\"\u0001A\n\u0011\tQ9\u0012\u0004L\u0007\u0002+)\u0011a#D\u0001\u0006gR\fw-Z\u0005\u00031U\u0011qd\u0012:ba\"\u001cF/Y4f/&$\b.T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f!\rQ2$H\u0007\u0002\u001b%\u0011A$\u0004\u0002\f'>,(oY3TQ\u0006\u0004X\r\u0005\u0002\u001f?1\u0001A!\u0002\u0011\u0001\u0005\u0004\u0011#!\u0001+\u0004\u0001E\u00111%\u000b\t\u0003I\u001dj\u0011!\n\u0006\u0002M\u0005)1oY1mC&\u0011\u0001&\n\u0002\b\u001d>$\b.\u001b8h!\t!#&\u0003\u0002,K\t\u0019\u0011I\\=\u0011\u00075\u0002$'D\u0001/\u0015\tyS%\u0001\u0006d_:\u001cWO\u001d:f]RL!!\r\u0018\u0003\r\u0019+H/\u001e:f!\tq2\u0007B\u00035\u0001\t\u0007!EA\u0001N\u0003\u001d1\u0017m\u0019;pef\u0004R\u0001J\u001c:y}J!\u0001O\u0013\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0001\u000e;\u0013\tYTB\u0001\u0007NCR,'/[1mSj,'\u000f\u0005\u0002\u001b{%\u0011a(\u0004\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\b\u0003\u0002!D;Ij\u0011!\u0011\u0006\u0003\u00056\t\u0001b]2bY\u0006$7\u000f\\\u0005\u0003\t\u0006\u0013aaU8ve\u000e,\u0017A\u0002\u001fj]&$h\b\u0006\u0002H\u0013B!\u0001\nA\u000f3\u001b\u0005Y\u0001\"B\u001b\u0003\u0001\u00041\u0014aA8viV\tA\nE\u0002\u001b\u001bvI!AT\u0007\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f\u001e\u0011\u0002\u000bMD\u0017\r]3\u0016\u0003e\taa\u001d5ba\u0016\u0004\u0013!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\tA(A\u0010de\u0016\fG/\u001a'pO&\u001c\u0017I\u001c3NCR,'/[1mSj,GMV1mk\u0016$\"aV/\u0011\t\u0011B&\fL\u0005\u00033\u0016\u0012a\u0001V;qY\u0016\u0014\u0004C\u0001\u000b\\\u0013\taVCA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015q\u0006\u00021\u0001=\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003A\u0019'/Z1uKN#\u0018mZ3M_\u001eL7\r\u0006\u0002bWJ\u0011!M\u0017\u0004\u0005G&\u0001\u0011M\u0001\u0007=e\u00164\u0017N\\3nK:$h\bC\u0004fE\n\u0007I\u0011\u00014\u0002\u0011M,(-\u00138mKR,\u0012a\u001a\t\u0004Q&lR\"\u00012\n\u0005)\\&\u0001D*vENKgn[%oY\u0016$\b\"\u00027\n\u0001\u0004i\u0017AC7biB\u0013x.\\5tKB\u0019QF\u001c\u001a\n\u0005=t#a\u0002)s_6L7/\u001a\u0015\u0003\u0001E\u0004\"A];\u000e\u0003MT!\u0001^\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002wg\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class SetupSourceStage<T, M>
extends GraphStageWithMaterializedValue<SourceShape<T>, Future<M>> {
    public final Function2<Materializer, Attributes, Source<T, M>> akka$stream$impl$SetupSourceStage$$factory;
    private final Outlet<T> akka$stream$impl$SetupSourceStage$$out;
    private final SourceShape<T> shape;

    public Outlet<T> akka$stream$impl$SetupSourceStage$$out() {
        return this.akka$stream$impl$SetupSourceStage$$out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("setup").and(Attributes$SourceLocation$.MODULE$.forLambda(this.akka$stream$impl$SetupSourceStage$$factory));
    }

    @Override
    public Tuple2<GraphStageLogic, Future<M>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise matPromise = Promise$.MODULE$.apply();
        return new Tuple2<GraphStageLogic, Future<M>>(this.createStageLogic(matPromise), matPromise.future());
    }

    private GraphStageLogic createStageLogic(Promise<M> matPromise) {
        return new GraphStageLogic(this, matPromise){
            private final GraphStageLogic.SubSinkInlet<T> subInlet;
            private final /* synthetic */ SetupSourceStage $outer;
            private final Promise matPromise$2;

            public GraphStageLogic.SubSinkInlet<T> subInlet() {
                return this.subInlet;
            }

            public void preStart() {
                try {
                    Source<T, M> source2 = this.$outer.akka$stream$impl$SetupSourceStage$$factory.apply(this.materializer(), this.attributes());
                    Mat mat = this.subFusingMaterializer().materialize(source2.to(Sink$.MODULE$.fromGraph(this.subInlet().sink())), this.attributes());
                    this.matPromise$2.success(mat);
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        this.matPromise$2.failure(ex);
                        throw ex;
                    }
                    throw throwable;
                }
            }

            public static final /* synthetic */ void $anonfun$new$7($anon$2 $this, Object x$3) {
                $this.push($this.$outer.akka$stream$impl$SetupSourceStage$$out(), x$3);
            }

            public static final /* synthetic */ void $anonfun$new$9($anon$2 $this, Throwable x$4) {
                $this.fail($this.$outer.akka$stream$impl$SetupSourceStage$$out(), x$4);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.matPromise$2 = matPromise$2;
                super($outer.shape());
                this.subInlet = new GraphStageLogic.SubSinkInlet<T>(this, "SetupSourceStage");
                this.subInlet().setHandler(SetupStage$.MODULE$.delegateToOutlet((Function1<Object, Object> & Serializable)x$3 -> {
                    $anon$2.$anonfun$new$7(this, x$3);
                    return BoxedUnit.UNIT;
                }, () -> this.complete($this.$outer.akka$stream$impl$SetupSourceStage$$out()), (Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)x$4 -> {
                    $anon$2.$anonfun$new$9(this, x$4);
                    return BoxedUnit.UNIT;
                }, this.subInlet()));
                this.setHandler($outer.akka$stream$impl$SetupSourceStage$$out(), SetupStage$.MODULE$.delegateToSubInlet(this.subInlet()));
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$new$7$adapted(akka.stream.impl.SetupSourceStage$$anon$2 java.lang.Object ), $anonfun$new$8(akka.stream.impl.SetupSourceStage$$anon$2 ), $anonfun$new$9$adapted(akka.stream.impl.SetupSourceStage$$anon$2 java.lang.Throwable )}, serializedLambda);
            }
        };
    }

    public SetupSourceStage(Function2<Materializer, Attributes, Source<T, M>> factory2) {
        this.akka$stream$impl$SetupSourceStage$$factory = factory2;
        this.akka$stream$impl$SetupSourceStage$$out = Outlet$.MODULE$.apply("SetupSourceStage.out");
        this.shape = new SourceShape<T>(this.akka$stream$impl$SetupSourceStage$$out());
    }
}

