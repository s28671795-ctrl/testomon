/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.event.LogMarker;
import akka.event.LoggingAdapter;
import akka.event.MarkerLoggingAdapter;
import akka.stream.Attributes;
import akka.stream.FanInShape2;
import akka.stream.FanOutShape2;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.GraphDelegate;
import akka.stream.Materializer;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.ThrottleMode;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.FlowWithContextOps;
import akka.stream.scaladsl.GraphDSL;
import akka.stream.scaladsl.GraphDSL$;
import akka.stream.scaladsl.GraphDSL$Implicits$;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.RunnableGraph;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.scaladsl.SourceWithContext$;
import akka.stream.scaladsl.Unzip$;
import akka.stream.scaladsl.Zip$;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005ux!\u0002\n\u0014\u0011\u0003Qb!\u0002\u000f\u0014\u0011\u0003i\u0002\"\u0002\u0013\u0002\t\u0003)\u0003\"\u0002\u0014\u0002\t\u00039c\u0001\u0002\u000f\u0014\u0005)B\u0001B\u0013\u0003\u0003\u0002\u0003\u0006Ia\u0013\u0005\u0007I\u0011!\t!\u0006(\u0006\tE#\u0001E\u0015\u0005\u0006O\u0012!\t\u0005\u001b\u0005\b\u0003\u0003!A\u0011IA\u0002\u0011\u001d\t9\u0002\u0002C!\u00033Aq!a\u0012\u0005\t\u0003\nI\u0005C\u0004\u0002V\u0011!\t!a\u0016\t\u000f\u0005-D\u0001\"\u0001\u0002n!9\u0011q\u0011\u0003\u0005\u0002\u0005%\u0005bBAQ\t\u0011\u0005\u00111\u0015\u0005\b\u0003w#A\u0011AA_\u0011\u001d\ty\f\u0002C\u0001\u0003\u0003\f\u0011cU8ve\u000e,w+\u001b;i\u0007>tG/\u001a=u\u0015\t!R#\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\t1r#\u0001\u0004tiJ,\u0017-\u001c\u0006\u00021\u0005!\u0011m[6b\u0007\u0001\u0001\"aG\u0001\u000e\u0003M\u0011\u0011cU8ve\u000e,w+\u001b;i\u0007>tG/\u001a=u'\t\ta\u0004\u0005\u0002 E5\t\u0001EC\u0001\"\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0003E\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003i\t!B\u001a:p[R+\b\u000f\\3t+\u001dA\u0013\u0011^Aw\u0003g$2!KA{!!YB!a:\u0002l\u0006EX\u0003B\u00169\u0005\u0016\u001b2\u0001\u0002\u0017H!\u0011ic\u0006\r#\u000e\u0003UI!aL\u000b\u0003\u001b\u001d\u0013\u0018\r\u001d5EK2,w-\u0019;f!\ri\u0013gM\u0005\u0003eU\u00111bU8ve\u000e,7\u000b[1qKB!q\u0004\u000e\u001cB\u0013\t)\u0004E\u0001\u0004UkBdWM\r\t\u0003oab\u0001\u0001\u0002\u0004:\t\u0011\u0015\rA\u000f\u0002\u0004\u001fV$\u0018CA\u001e?!\tyB(\u0003\u0002>A\t9aj\u001c;iS:<\u0007CA\u0010@\u0013\t\u0001\u0005EA\u0002B]f\u0004\"a\u000e\"\u0005\r\r#AQ1\u0001;\u0005\r\u0019E\u000f\u001f\t\u0003o\u0015#aA\u0012\u0003\u0005\u0006\u0004Q$aA'biB)1\u0004\u0013\u001cB\t&\u0011\u0011j\u0005\u0002\u0013\r2|woV5uQ\u000e{g\u000e^3yi>\u00038/\u0001\u0005eK2,w-\u0019;f!\u0011YBj\r#\n\u00055\u001b\"AB*pkJ\u001cW\r\u0006\u0002P!B)1\u0004\u0002\u001cB\t\")!J\u0002a\u0001\u0017\n9!+\u001a9s\u001b\u0006$X\u0003B*V1r\u0003Ra\u0007\u0003U/j\u0003\"aN+\u0005\rY;AQ1\u0001;\u0005\u0005y\u0005CA\u001cY\t\u0019Iv\u0001\"b\u0001u\t\t1I\u000b\u0002\\=B\u0011q\u0007\u0018\u0003\u0007;\u001e!)\u0019\u0001\u001e\u0003\u00035[\u0013a\u0018\t\u0003A\u0016l\u0011!\u0019\u0006\u0003E\u000e\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005\u0011\u0004\u0013AC1o]>$\u0018\r^5p]&\u0011a-\u0019\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017a\u0001<jCV!\u0011n\u001c:\u007f)\tQG\u000f\u0005\u0003lY:\fX\"\u0001\u0003\n\u00055D%\u0001\u0002*faJ\u0004\"aN8\u0005\u000bAD!\u0019\u0001\u001e\u0003\t=+HO\r\t\u0003oI$Qa\u001d\u0005C\u0002i\u0012Aa\u0011;ye!)Q\u000f\u0003a\u0001m\u00069a/[1GY><\b\u0003B\u0017xsvL!\u0001_\u000b\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0011\t5R8\u0007`\u0005\u0003wV\u0011\u0011B\u00127poNC\u0017\r]3\u0011\t}!d.\u001d\t\u0003oy$Qa \u0005C\u0002i\u0012A!T1ue\u0005iQO\\:bM\u0016$\u0015\r^1WS\u0006,b!!\u0002\u0002\f\u0005UA\u0003BA\u0004\u0003\u001b\u0001Ra\u001b7\u0002\n\u0005\u00032aNA\u0006\t\u0015\u0001\u0018B1\u0001;\u0011\u0019)\u0018\u00021\u0001\u0002\u0010A1Qf^A\t\u0003'\u0001R!\f>7\u0003\u0013\u00012aNA\u000b\t\u0015y\u0018B1\u0001;\u0003\u00191\u0018.Y'biVQ\u00111DA\u0012\u0003O\tY$a\u000b\u0015\t\u0005u\u0011Q\b\u000b\u0005\u0003?\ty\u0003\u0005\u0005\u001c\t\u0005\u0005\u0012QEA\u0015!\r9\u00141\u0005\u0003\u0006a*\u0011\rA\u000f\t\u0004o\u0005\u001dB!B:\u000b\u0005\u0004Q\u0004cA\u001c\u0002,\u00111\u0011Q\u0006\u0006C\u0002i\u0012A!T1ug!9\u0011\u0011\u0007\u0006A\u0002\u0005M\u0012aB2p[\nLg.\u001a\t\t?\u0005UB)!\u000f\u0002*%\u0019\u0011q\u0007\u0011\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004cA\u001c\u0002<\u0011)qP\u0003b\u0001u!9\u0011q\b\u0006A\u0002\u0005\u0005\u0013\u0001\u00024m_^\u0004b!L<\u0002D\u0005e\u0002#B\u0017{g\u0005\u0015\u0003CB\u00105\u0003C\t)#\u0001\bxSRD\u0017\t\u001e;sS\n,H/Z:\u0015\u0007=\u000bY\u0005C\u0004\u0002N-\u0001\r!a\u0014\u0002\t\u0005$HO\u001d\t\u0004[\u0005E\u0013bAA*+\tQ\u0011\t\u001e;sS\n,H/Z:\u0002)5\f\u0007/T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f+\u0011\tI&a\u0018\u0015\t\u0005m\u0013\u0011\r\t\u00077\u00111\u0014)!\u0018\u0011\u0007]\ny\u0006B\u0003\u0000\u0019\t\u0007!\bC\u0004\u0002d1\u0001\r!!\u001a\u0002\u0003\u0019\u0004baHA4\t\u0006u\u0013bAA5A\tIa)\u001e8di&|g.M\u0001\u0003i>,B!a\u001c\u0002\u0006R!\u0011\u0011OA<!\u0011Y\u00121\u000f#\n\u0007\u0005U4CA\u0007Sk:t\u0017M\u00197f\u000fJ\f\u0007\u000f\u001b\u0005\b\u0003sj\u0001\u0019AA>\u0003\u0011\u0019\u0018N\\6\u0011\r5:\u0018QPAB!\u0011i\u0013qP\u001a\n\u0007\u0005\u0005UCA\u0005TS:\\7\u000b[1qKB\u0019q'!\"\u0005\u000b}l!\u0019\u0001\u001e\u0002\u000bQ|W*\u0019;\u0016\r\u0005-\u00151TAJ)\u0011\ti)!(\u0015\t\u0005=\u0015Q\u0013\t\u00067\u0005M\u0014\u0011\u0013\t\u0004o\u0005MEABA\u0017\u001d\t\u0007!\bC\u0004\u000229\u0001\r!a&\u0011\u0011}\t)\u0004RAM\u0003#\u00032aNAN\t\u0015yhB1\u0001;\u0011\u001d\tIH\u0004a\u0001\u0003?\u0003b!L<\u0002~\u0005e\u0015a\u0002:v]^KG\u000f[\u000b\u0005\u0003K\u000bY\u000b\u0006\u0003\u0002(\u0006]F\u0003BAU\u0003[\u00032aNAV\t\u0015yxB1\u0001;\u0011\u001d\tyk\u0004a\u0002\u0003c\u000bA\"\\1uKJL\u0017\r\\5{KJ\u00042!LAZ\u0013\r\t),\u0006\u0002\r\u001b\u0006$XM]5bY&TXM\u001d\u0005\b\u0003sz\u0001\u0019AA]!\u0019is/! \u0002*\u0006A\u0011m]*pkJ\u001cW-F\u0001L\u0003\u0019\t7OS1wCVA\u00111YAi\u00033\f\t/\u0006\u0002\u0002FBQ\u0011qYAg\u0003\u001f\f9.a8\u000e\u0005\u0005%'bAAf+\u00059!.\u0019<bINd\u0017b\u0001\u000f\u0002JB\u0019q'!5\u0005\u000f\u0005M\u0017C1\u0001\u0002V\n!!jT;u#\t1d\bE\u00028\u00033$q!a7\u0012\u0005\u0004\tiN\u0001\u0003K\u0007RD\u0018CA!?!\r9\u0014\u0011\u001d\u0003\b\u0003G\f\"\u0019AAs\u0005\u0011QU*\u0019;\u0012\u0005\u0011s\u0004cA\u001c\u0002j\u0012)\u0011h\u0001b\u0001uA\u0019q'!<\u0005\r\u0005=8A1\u0001;\u0005\u0019\u0019E\u000f_(viB\u0019q'a=\u0005\u000b\u0019\u001b!\u0019\u0001\u001e\t\u000f\u0005]8\u00011\u0001\u0002z\u000611o\\;sG\u0016\u0004ba\u0007'\u0002|\u0006E\bCB\u00105\u0003O\fY\u000f")
public final class SourceWithContext<Out, Ctx, Mat>
extends GraphDelegate<SourceShape<Tuple2<Out, Ctx>>, Mat>
implements FlowWithContextOps<Out, Ctx, Mat> {
    private final Source<Tuple2<Out, Ctx>, Mat> delegate;

    public static <Out, CtxOut, Mat> SourceWithContext<Out, CtxOut, Mat> fromTuples(Source<Tuple2<Out, CtxOut>, Mat> source2) {
        return SourceWithContext$.MODULE$.fromTuples(source2);
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
    public <Ctx2> FlowWithContextOps mapContext(Function1<Ctx, Ctx2> f) {
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
    public FlowWithContextOps logWithMarker(String name, Function2<Out, Ctx, LogMarker> marker, Function1<Out, Object> extract, MarkerLoggingAdapter log) {
        return FlowWithContextOps.logWithMarker$(this, name, marker, extract, log);
    }

    @Override
    public Function1<Out, Object> logWithMarker$default$3() {
        return FlowWithContextOps.logWithMarker$default$3$(this);
    }

    @Override
    public MarkerLoggingAdapter logWithMarker$default$4(String name, Function2<Out, Ctx, LogMarker> marker, Function1<Out, Object> extract) {
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

    @Override
    public <Out2, Ctx2, Mat2> SourceWithContext<Out2, Ctx2, Mat> via(Graph<FlowShape<Tuple2<Out, Ctx>, Tuple2<Out2, Ctx2>>, Mat2> viaFlow) {
        return new SourceWithContext<Out, Ctx, Mat>(this.delegate.via(viaFlow));
    }

    @Override
    public <Out2, Mat2> SourceWithContext<Out2, Ctx, Mat> unsafeDataVia(Graph<FlowShape<Out, Out2>, Mat2> viaFlow) {
        return SourceWithContext$.MODULE$.fromTuples(Source$.MODULE$.fromGraph(GraphDSL$.MODULE$.createGraph(this.delegate, (Function1<GraphDSL.Builder, Function1> & Serializable)b -> (Function1<SourceShape, SourceShape> & Serializable)d -> {
            FanOutShape2 unzip = (FanOutShape2)b.add(Unzip$.MODULE$.apply());
            FanInShape2 zipper = (FanInShape2)b.add(Zip$.MODULE$.apply());
            new GraphDSL.Implicits.SourceShapeArrow(GraphDSL$Implicits$.MODULE$.SourceShapeArrow(d)).$tilde$greater(unzip.in(), (GraphDSL.Builder<?>)b);
            ((GraphDSL.Implicits.CombinerBase)((Object)GraphDSL$Implicits$.MODULE$.port2flow(unzip.out0(), (GraphDSL.Builder<?>)b).via(viaFlow))).$tilde$greater(zipper.in0(), (GraphDSL.Builder<?>)b);
            GraphDSL$Implicits$.MODULE$.port2flow(unzip.out1(), (GraphDSL.Builder<?>)b).$tilde$greater(zipper.in1(), (GraphDSL.Builder<?>)b);
            return new SourceShape(zipper.out());
        })));
    }

    @Override
    public <Out2, Ctx2, Mat2, Mat3> SourceWithContext<Out2, Ctx2, Mat3> viaMat(Graph<FlowShape<Tuple2<Out, Ctx>, Tuple2<Out2, Ctx2>>, Mat2> flow, Function2<Mat, Mat2, Mat3> combine) {
        return new SourceWithContext<Out, Ctx, Mat>(this.delegate.viaMat(flow, (Function2)combine));
    }

    public SourceWithContext<Out, Ctx, Mat> withAttributes(Attributes attr) {
        return new SourceWithContext<Out, Ctx, Mat>(this.delegate.withAttributes(attr));
    }

    public <Mat2> SourceWithContext<Out, Ctx, Mat2> mapMaterializedValue(Function1<Mat, Mat2> f) {
        return new SourceWithContext<Out, Ctx, Mat>(this.delegate.mapMaterializedValue((Function1)f));
    }

    public <Mat2> RunnableGraph<Mat> to(Graph<SinkShape<Tuple2<Out, Ctx>>, Mat2> sink2) {
        return this.delegate.toMat((Graph)sink2, Keep$.MODULE$.left());
    }

    public <Mat2, Mat3> RunnableGraph<Mat3> toMat(Graph<SinkShape<Tuple2<Out, Ctx>>, Mat2> sink2, Function2<Mat, Mat2, Mat3> combine) {
        return this.delegate.toMat((Graph)sink2, (Function2)combine);
    }

    public <Mat2> Mat2 runWith(Graph<SinkShape<Tuple2<Out, Ctx>>, Mat2> sink2, Materializer materializer) {
        return this.delegate.runWith(sink2, materializer);
    }

    public Source<Tuple2<Out, Ctx>, Mat> asSource() {
        return this.delegate;
    }

    public <JOut, JCtx, JMat> akka.stream.javadsl.SourceWithContext<JOut, JCtx, JMat> asJava() {
        return new akka.stream.javadsl.SourceWithContext(this);
    }

    public SourceWithContext(Source<Tuple2<Out, Ctx>, Mat> delegate) {
        this.delegate = delegate;
        super(delegate);
        FlowWithContextOps.$init$(this);
    }
}

