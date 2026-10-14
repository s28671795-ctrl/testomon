/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.event.LogMarker;
import akka.event.LoggingAdapter;
import akka.event.MarkerLoggingAdapter;
import akka.japi.Pair;
import akka.stream.Attributes;
import akka.stream.FanInShape2;
import akka.stream.FanOutShape2;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.GraphDelegate;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Keep$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.FlowWithContext$;
import akka.stream.scaladsl.FlowWithContextOps;
import akka.stream.scaladsl.GraphDSL;
import akka.stream.scaladsl.GraphDSL$;
import akka.stream.scaladsl.GraphDSL$Implicits$;
import akka.stream.scaladsl.Unzip$;
import akka.stream.scaladsl.Zip$;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dx!\u0002\t\u0012\u0011\u0003Ab!\u0002\u000e\u0012\u0011\u0003Y\u0002\"\u0002\u0012\u0002\t\u0003\u0019\u0003\"\u0002\u0013\u0002\t\u0003)\u0003bBAb\u0003\u0011\u0005\u0011Q\u0019\u0004\u00055E\u0011\u0001\u0006\u0003\u0005P\u000b\t\u0005\t\u0015!\u0003Q\u0011\u0015\u0011S\u0001\"\u0001T\u000b\u00111V\u0001I,\t\u000b9,A\u0011I8\t\u000f\u0005-Q\u0001\"\u0011\u0002\u000e!9\u0011\u0011E\u0003\u0005B\u0005\r\u0002bBA)\u000b\u0011\u0005\u00131\u000b\u0005\b\u0003?*A\u0011AA1\u0011\u001d\t)(\u0002C\u0001\u0003oBq!!\u001f\u0006\t\u0003\tY(A\bGY><x+\u001b;i\u0007>tG/\u001a=u\u0015\t\u00112#\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\t!R#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002-\u0005!\u0011m[6b\u0007\u0001\u0001\"!G\u0001\u000e\u0003E\u0011qB\u00127po^KG\u000f[\"p]R,\u0007\u0010^\n\u0003\u0003q\u0001\"!\b\u0011\u000e\u0003yQ\u0011aH\u0001\u0006g\u000e\fG.Y\u0005\u0003Cy\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u0019\u0003\u0015\t\u0007\u000f\u001d7z+\u00151\u00131WA\\+\u00059\u0003\u0003D\r\u0006\u0003c\u000b),!-\u00026\u0006mVCB\u00157\u0001\u0012;%jE\u0002\u0006U1\u0003Ba\u000b\u0017/\u00136\t1#\u0003\u0002.'\tiqI]1qQ\u0012+G.Z4bi\u0016\u0004BaK\u00182\u0005&\u0011\u0001g\u0005\u0002\n\r2|wo\u00155ba\u0016\u0004B!\b\u001a5\u007f%\u00111G\b\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0005U2D\u0002\u0001\u0003\u0007o\u0015A)\u0019\u0001\u001d\u0003\u0005%s\u0017CA\u001d=!\ti\"(\u0003\u0002<=\t9aj\u001c;iS:<\u0007CA\u000f>\u0013\tqdDA\u0002B]f\u0004\"!\u000e!\u0005\r\u0005+\u0001R1\u00019\u0005\u0015\u0019E\u000f_%o!\u0011i\"g\u0011$\u0011\u0005U\"EAB#\u0006\t\u000b\u0007\u0001HA\u0002PkR\u0004\"!N$\u0005\r!+AQ1\u00019\u0005\u0019\u0019E\u000f_(viB\u0011QG\u0013\u0003\u0007\u0017\u0016!)\u0019\u0001\u001d\u0003\u00075\u000bG\u000fE\u0003\u001a\u001b\u000e3\u0015*\u0003\u0002O#\t\u0011b\t\\8x/&$\bnQ8oi\u0016DHo\u00149t\u0003!!W\r\\3hCR,\u0007#B\rRc\tK\u0015B\u0001*\u0012\u0005\u00111En\\<\u0015\u0005Q+\u0006cB\r\u0006i}\u001ae)\u0013\u0005\u0006\u001f\u001e\u0001\r\u0001\u0015\u0002\b%\u0016\u0004(/T1u+\u0011AV\r\u001b7\u0011\u000fe)\u0011l\u00193hU*\u0012AGW\u0016\u00027B\u0011A,Y\u0007\u0002;*\u0011alX\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u0019\u0010\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002c;\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3+\u0005}R\u0006CA\u001bf\t\u00191\u0007\u0002\"b\u0001q\t\tq\n\u0005\u00026Q\u00121\u0011\u000e\u0003CC\u0002a\u0012\u0011a\u0011\u0016\u0003Wj\u0003\"!\u000e7\u0005\r5DAQ1\u00019\u0005\u0005i\u0015a\u0001<jCV)\u0001O^=\u0002\bQ\u0011\u0011o\u001f\t\u0005eN,\b0D\u0001\u0006\u0013\t!XJ\u0001\u0003SKB\u0014\bCA\u001bw\t\u00159\u0018B1\u00019\u0005\u0011yU\u000f\u001e\u001a\u0011\u0005UJH!\u0002>\n\u0005\u0004A$\u0001B\"uqJBQ\u0001`\u0005A\u0002u\fqA^5b\r2|w\u000f\u0005\u0004,}\u0006\u0005\u0011QA\u0005\u0003\u007fN\u0011Qa\u0012:ba\"\u0004RaK\u0018C\u0003\u0007\u0001B!\b\u001avqB\u0019Q'a\u0002\u0005\r\u0005%\u0011B1\u00019\u0005\u0011i\u0015\r\u001e\u001a\u0002\u001bUt7/\u00194f\t\u0006$\u0018MV5b+\u0019\ty!!\u0006\u0002 Q!\u0011\u0011CA\f!\u0015\u00118/a\u0005G!\r)\u0014Q\u0003\u0003\u0006o*\u0011\r\u0001\u000f\u0005\u0007y*\u0001\r!!\u0007\u0011\r-r\u00181DA\u000f!\u0015YsfQA\n!\r)\u0014q\u0004\u0003\u0007\u0003\u0013Q!\u0019\u0001\u001d\u0002\rYL\u0017-T1u+)\t)#!\f\u00022\u0005\u0015\u0013Q\u0007\u000b\u0005\u0003O\t9\u0005\u0006\u0003\u0002*\u0005e\u0002CC\r\u0006i}\nY#a\f\u00024A\u0019Q'!\f\u0005\u000b]\\!\u0019\u0001\u001d\u0011\u0007U\n\t\u0004B\u0003{\u0017\t\u0007\u0001\bE\u00026\u0003k!a!a\u000e\f\u0005\u0004A$\u0001B'biNBq!a\u000f\f\u0001\u0004\ti$A\u0004d_6\u0014\u0017N\\3\u0011\u0011u\ty$SA\"\u0003gI1!!\u0011\u001f\u0005%1UO\\2uS>t'\u0007E\u00026\u0003\u000b\"a!!\u0003\f\u0005\u0004A\u0004bBA%\u0017\u0001\u0007\u00111J\u0001\u0005M2|w\u000f\u0005\u0004,}\u00065\u00131\t\t\u0006W=\u0012\u0015q\n\t\u0007;I\nY#a\f\u0002\u001d]LG\u000f[!uiJL'-\u001e;fgR\u0019A+!\u0016\t\u000f\u0005]C\u00021\u0001\u0002Z\u0005!\u0011\r\u001e;s!\rY\u00131L\u0005\u0004\u0003;\u001a\"AC!uiJL'-\u001e;fg\u0006!R.\u00199NCR,'/[1mSj,GMV1mk\u0016,B!a\u0019\u0002jQ!\u0011QMA6!!IR\u0001N D\r\u0006\u001d\u0004cA\u001b\u0002j\u00111\u0011\u0011B\u0007C\u0002aBq!!\u001c\u000e\u0001\u0004\ty'A\u0001g!\u0019i\u0012\u0011O%\u0002h%\u0019\u00111\u000f\u0010\u0003\u0013\u0019+hn\u0019;j_:\f\u0014AB1t\r2|w/F\u0001Q\u0003\u0019\t7OS1wCVa\u0011QPAF\u0003'\u000bY*a)\u0002,V\u0011\u0011q\u0010\t\u000f\u0003\u0003\u000b9)!#\u0002\u0012\u0006e\u0015\u0011UAU\u001b\t\t\u0019IC\u0002\u0002\u0006N\tqA[1wC\u0012\u001cH.C\u0002\u001b\u0003\u0007\u00032!NAF\t\u001d\tii\u0004b\u0001\u0003\u001f\u00131AS%o#\tID\u0007E\u00026\u0003'#q!!&\u0010\u0005\u0004\t9J\u0001\u0004K\u0007RD\u0018J\\\t\u0003s}\u00022!NAN\t\u001d\tij\u0004b\u0001\u0003?\u0013AAS(viF\u00111\t\u0010\t\u0004k\u0005\rFaBAS\u001f\t\u0007\u0011q\u0015\u0002\b\u0015\u000e#\bpT;u#\t1E\bE\u00026\u0003W#q!!,\u0010\u0005\u0004\tyK\u0001\u0003K\u001b\u0006$\u0018CA%=!\r)\u00141\u0017\u0003\u0006o\r\u0011\r\u0001\u000f\t\u0004k\u0005]FABA]\u0007\t\u0007\u0001HA\u0002Dib\u0004B!!0\u0002@6\tQ#C\u0002\u0002BV\u0011qAT8u+N,G-\u0001\u0006ge>lG+\u001e9mKN,B\"a2\u0002N\u0006E\u0017Q[Am\u0003;$B!!3\u0002`Ba\u0011$BAf\u0003\u001f\f\u0019.a6\u0002\\B\u0019Q'!4\u0005\u000b]\"!\u0019\u0001\u001d\u0011\u0007U\n\t\u000eB\u0003B\t\t\u0007\u0001\bE\u00026\u0003+$Q!\u0012\u0003C\u0002a\u00022!NAm\t\u0015AEA1\u00019!\r)\u0014Q\u001c\u0003\u0006\u0017\u0012\u0011\r\u0001\u000f\u0005\b\u0003\u0013\"\u0001\u0019AAq!!I\u0012+a9\u0002f\u0006m\u0007CB\u000f3\u0003\u0017\fy\r\u0005\u0004\u001ee\u0005M\u0017q\u001b")
public final class FlowWithContext<In, CtxIn, Out, CtxOut, Mat>
extends GraphDelegate<FlowShape<Tuple2<In, CtxIn>, Tuple2<Out, CtxOut>>, Mat>
implements FlowWithContextOps<Out, CtxOut, Mat> {
    private final Flow<Tuple2<In, CtxIn>, Tuple2<Out, CtxOut>, Mat> delegate;

    public static <In, CtxIn, Out, CtxOut, Mat> FlowWithContext<In, CtxIn, Out, CtxOut, Mat> fromTuples(Flow<Tuple2<In, CtxIn>, Tuple2<Out, CtxOut>, Mat> flow) {
        return FlowWithContext$.MODULE$.fromTuples(flow);
    }

    public static <In, Ctx> FlowWithContext<In, Ctx, In, Ctx, NotUsed> apply() {
        return FlowWithContext$.MODULE$.apply();
    }

    @Override
    public <Out2> FlowWithContextOps map(Function1<Out, Out2> f) {
        return FlowWithContextOps.map$(this, f);
    }

    @Override
    public FlowWithContextOps mapError(PartialFunction<Throwable, Throwable> pf) {
        return FlowWithContextOps.mapError$(this, pf);
    }

    @Override
    public <Out2> FlowWithContextOps mapAsync(int parallelism, Function1<Out, Future<Out2>> f) {
        return FlowWithContextOps.mapAsync$(this, parallelism, f);
    }

    @Override
    public <Out2, P> FlowWithContextOps mapAsyncPartitioned(int parallelism, int perPartition, Function1<Out, P> partitioner, Function2<Out, P, Future<Out2>> f) {
        return FlowWithContextOps.mapAsyncPartitioned$(this, parallelism, perPartition, partitioner, f);
    }

    @Override
    public <Out2> FlowWithContextOps collect(PartialFunction<Out, Out2> f) {
        return FlowWithContextOps.collect$(this, f);
    }

    @Override
    public FlowWithContextOps filter(Function1<Out, Object> pred) {
        return FlowWithContextOps.filter$(this, pred);
    }

    @Override
    public FlowWithContextOps filterNot(Function1<Out, Object> pred) {
        return FlowWithContextOps.filterNot$(this, pred);
    }

    @Override
    public FlowWithContextOps grouped(int n) {
        return FlowWithContextOps.grouped$(this, n);
    }

    @Override
    public FlowWithContextOps sliding(int n, int step) {
        return FlowWithContextOps.sliding$(this, n, step);
    }

    @Override
    public int sliding$default$2() {
        return FlowWithContextOps.sliding$default$2$(this);
    }

    @Override
    public <Out2> FlowWithContextOps mapConcat(Function1<Out, IterableOnce<Out2>> f) {
        return FlowWithContextOps.mapConcat$(this, f);
    }

    @Override
    public <Ctx2> FlowWithContextOps mapContext(Function1<CtxOut, Ctx2> f) {
        return FlowWithContextOps.mapContext$(this, f);
    }

    @Override
    public FlowWithContextOps log(String name, Function1<Out, Object> extract, LoggingAdapter log) {
        return FlowWithContextOps.log$(this, name, extract, log);
    }

    @Override
    public Function1<Out, Object> log$default$2() {
        return FlowWithContextOps.log$default$2$(this);
    }

    @Override
    public LoggingAdapter log$default$3(String name, Function1<Out, Object> extract) {
        return FlowWithContextOps.log$default$3$(this, name, extract);
    }

    @Override
    public FlowWithContextOps logWithMarker(String name, Function2<Out, CtxOut, LogMarker> marker, Function1<Out, Object> extract, MarkerLoggingAdapter log) {
        return FlowWithContextOps.logWithMarker$(this, name, marker, extract, log);
    }

    @Override
    public Function1<Out, Object> logWithMarker$default$3() {
        return FlowWithContextOps.logWithMarker$default$3$(this);
    }

    @Override
    public MarkerLoggingAdapter logWithMarker$default$4(String name, Function2<Out, CtxOut, LogMarker> marker, Function1<Out, Object> extract) {
        return FlowWithContextOps.logWithMarker$default$4$(this, name, marker, extract);
    }

    @Override
    public FlowWithContextOps throttle(int elements, FiniteDuration per) {
        return FlowWithContextOps.throttle$(this, elements, per);
    }

    @Override
    public FlowWithContextOps throttle(int elements, FiniteDuration per, int maximumBurst, ThrottleMode mode) {
        return FlowWithContextOps.throttle$(this, elements, per, maximumBurst, mode);
    }

    @Override
    public FlowWithContextOps throttle(int cost, FiniteDuration per, Function1<Out, Object> costCalculation) {
        return FlowWithContextOps.throttle$(this, cost, per, costCalculation);
    }

    @Override
    public FlowWithContextOps throttle(int cost, FiniteDuration per, int maximumBurst, Function1<Out, Object> costCalculation, ThrottleMode mode) {
        return FlowWithContextOps.throttle$(this, cost, per, maximumBurst, costCalculation, mode);
    }

    @Override
    public <T, C> Flow<Tuple2<T, C>, Tuple2<T, C>, NotUsed> flow() {
        return FlowWithContextOps.flow$(this);
    }

    public <Out2, Ctx2, Mat2> FlowWithContext<In, CtxIn, Out2, Ctx2, Mat> via(Graph<FlowShape<Tuple2<Out, CtxOut>, Tuple2<Out2, Ctx2>>, Mat2> viaFlow) {
        return new FlowWithContext<In, CtxIn, Out, CtxOut, Mat>(this.delegate.via(viaFlow));
    }

    @Override
    public <Out2, Mat2> FlowWithContext<In, CtxIn, Out2, CtxOut, Mat> unsafeDataVia(Graph<FlowShape<Out, Out2>, Mat2> viaFlow) {
        return FlowWithContext$.MODULE$.fromTuples(Flow$.MODULE$.fromGraph(GraphDSL$.MODULE$.createGraph(this.delegate, (Function1<GraphDSL.Builder, Function1> & Serializable)b -> (Function1<FlowShape, FlowShape> & Serializable)d -> {
            FanOutShape2 unzip = (FanOutShape2)b.add(Unzip$.MODULE$.apply());
            FanInShape2 zipper = (FanInShape2)b.add(Zip$.MODULE$.apply());
            GraphDSL$Implicits$.MODULE$.flow2flow(d, (GraphDSL.Builder<?>)b).$tilde$greater(unzip.in(), (GraphDSL.Builder<?>)b);
            ((GraphDSL.Implicits.CombinerBase)((Object)GraphDSL$Implicits$.MODULE$.port2flow(unzip.out0(), (GraphDSL.Builder<?>)b).via(viaFlow))).$tilde$greater(zipper.in0(), (GraphDSL.Builder<?>)b);
            GraphDSL$Implicits$.MODULE$.port2flow(unzip.out1(), (GraphDSL.Builder<?>)b).$tilde$greater(zipper.in1(), (GraphDSL.Builder<?>)b);
            return new FlowShape(d.in(), zipper.out());
        })));
    }

    public <Out2, Ctx2, Mat2, Mat3> FlowWithContext<In, CtxIn, Out2, Ctx2, Mat3> viaMat(Graph<FlowShape<Tuple2<Out, CtxOut>, Tuple2<Out2, Ctx2>>, Mat2> flow, Function2<Mat, Mat2, Mat3> combine) {
        return new FlowWithContext<In, CtxIn, Out, CtxOut, Mat>(this.delegate.viaMat(flow, (Function2)combine));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> withAttributes(Attributes attr) {
        return new FlowWithContext<In, CtxIn, Out, CtxOut, Mat>(this.delegate.withAttributes(attr));
    }

    public <Mat2> FlowWithContext<In, CtxIn, Out, CtxOut, Mat2> mapMaterializedValue(Function1<Mat, Mat2> f) {
        return new FlowWithContext<In, CtxIn, Out, CtxOut, Mat>(this.delegate.mapMaterializedValue((Function1)f));
    }

    public Flow<Tuple2<In, CtxIn>, Tuple2<Out, CtxOut>, Mat> asFlow() {
        return this.delegate;
    }

    public <JIn extends In, JCtxIn extends CtxIn, JOut, JCtxOut, JMat> akka.stream.javadsl.FlowWithContext<JIn, JCtxIn, JOut, JCtxOut, JMat> asJava() {
        return new akka.stream.javadsl.FlowWithContext(akka.stream.javadsl.Flow$.MODULE$.create().map((Out x$1) -> x$1.toScala()).viaMat(((Flow)this.delegate.map((Function1<Tuple2, Pair> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object first = tuple2._1();
                Object second = tuple2._2();
                return new Pair(first, second);
            }
            throw new MatchError(tuple2);
        })).asJava(), Keep$.MODULE$.right()));
    }

    public FlowWithContext(Flow<Tuple2<In, CtxIn>, Tuple2<Out, CtxOut>, Mat> delegate) {
        this.delegate = delegate;
        super(delegate);
        FlowWithContextOps.$init$(this);
    }
}

