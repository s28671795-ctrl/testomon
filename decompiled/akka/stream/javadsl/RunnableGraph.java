/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.actor.ClassicActorSystemProvider;
import akka.japi.function.Function;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.ClosedShape;
import akka.stream.ClosedShape$;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.SystemMaterializer;
import akka.stream.SystemMaterializer$;
import akka.stream.impl.TraversalBuilder;
import akka.stream.javadsl.RunnableGraph$;
import java.io.Serializable;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015t!B\f\u0019\u0011\u0003yb!B\u0011\u0019\u0011\u0003\u0011\u0003\"B\u0015\u0002\t\u0003Q\u0003\"B\u0016\u0002\t\u0003acABA\f\u0003\u0019\tI\u0002\u0003\u0006\u0002$\u0011\u0011\t\u0011)A\u0005\u0003KAa!\u000b\u0003\u0005\u0002\u0005\u001d\u0002bBA\u0018\t\u0011\u0005\u0013\u0011\u0007\u0005\b\u0003s!A\u0011IA\u001e\u0011\u001d\tI\u0005\u0002C!\u0003\u0017Ba!\u0016\u0003\u0005B\u00055\u0003BB#\u0005\t\u0003\nY\u0006\u0003\u0004g\t\u0011\u0005\u0013q\f\u0005\u0007\u007f\u0012!\t%a\u0019\u0007\u000b\u0005B\u0012\u0011A\u0018\t\u000b%rA\u0011A\"\t\u000b\u0015sA\u0011\u0001$\t\u000b\u0015sa\u0011A(\t\u000bUsa\u0011\u0001,\t\u000b\u0019ta\u0011I4\t\u000b5tA\u0011\t8\t\u000bAtA\u0011I9\t\r}ta\u0011AA\u0001\u00035\u0011VO\u001c8bE2,wI]1qQ*\u0011\u0011DG\u0001\bU\u00064\u0018\rZ:m\u0015\tYB$\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002;\u0005!\u0011m[6b\u0007\u0001\u0001\"\u0001I\u0001\u000e\u0003a\u0011QBU;o]\u0006\u0014G.Z$sCBD7CA\u0001$!\t!s%D\u0001&\u0015\u00051\u0013!B:dC2\f\u0017B\u0001\u0015&\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012aH\u0001\nMJ|Wn\u0012:ba\",2!LA\b)\rq\u0013\u0011\u0003\t\u0005A9\ti!\u0006\u00021uM\u0019abI\u0019\u0011\tI\u001aT\u0007O\u0007\u00025%\u0011AG\u0007\u0002\u0006\u000fJ\f\u0007\u000f\u001b\t\u0003eYJ!a\u000e\u000e\u0003\u0017\rcwn]3e'\"\f\u0007/\u001a\t\u0003sib\u0001\u0001\u0002\u0004<\u001d\u0011\u0015\r\u0001\u0010\u0002\u0004\u001b\u0006$\u0018CA\u001fA!\t!c(\u0003\u0002@K\t9aj\u001c;iS:<\u0007C\u0001\u0013B\u0013\t\u0011UEA\u0002B]f$\u0012\u0001\u0012\t\u0004A9A\u0014a\u0001:v]R\u0011\u0001h\u0012\u0005\u0006\u0011B\u0001\r!S\u0001\u000fgf\u001cH/Z7Qe>4\u0018\u000eZ3s!\tQU*D\u0001L\u0015\taE$A\u0003bGR|'/\u0003\u0002O\u0017\nQ2\t\\1tg&\u001c\u0017i\u0019;peNK8\u000f^3n!J|g/\u001b3feR\u0011\u0001\b\u0015\u0005\u0006#F\u0001\rAU\u0001\r[\u0006$XM]5bY&TXM\u001d\t\u0003eMK!\u0001\u0016\u000e\u0003\u00195\u000bG/\u001a:jC2L'0\u001a:\u0002)5\f\u0007/T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f+\t9&\f\u0006\u0002Y9B\u0019\u0001ED-\u0011\u0005eRF!B.\u0013\u0005\u0004a$\u0001B'biJBQ!\u0018\nA\u0002y\u000b\u0011A\u001a\t\u0005?\u0012D\u0014,D\u0001a\u0015\t\t'-\u0001\u0005gk:\u001cG/[8o\u0015\t\u0019G$\u0001\u0003kCBL\u0017BA3a\u0005!1UO\\2uS>t\u0017AD<ji\"\fE\u000f\u001e:jEV$Xm\u001d\u000b\u0003\t\"DQ![\nA\u0002)\fA!\u0019;ueB\u0011!g[\u0005\u0003Yj\u0011!\"\u0011;ue&\u0014W\u000f^3t\u00035\tG\rZ!uiJL'-\u001e;fgR\u0011Ai\u001c\u0005\u0006SR\u0001\rA[\u0001\u0006]\u0006lW\r\u001a\u000b\u0003\tJDQa]\u000bA\u0002Q\fAA\\1nKB\u0011Q\u000f \b\u0003mj\u0004\"a^\u0013\u000e\u0003aT!!\u001f\u0010\u0002\rq\u0012xn\u001c;?\u0013\tYX%\u0001\u0004Qe\u0016$WMZ\u0005\u0003{z\u0014aa\u0015;sS:<'BA>&\u0003\u001d\t7oU2bY\u0006,\"!a\u0001\u0011\u000b\u0005\u0015\u00111\u0002\u001d\u000e\u0005\u0005\u001d!bAA\u00055\u0005A1oY1mC\u0012\u001cH.C\u0002\"\u0003\u000f\u00012!OA\b\t\u0015Y4A1\u0001=\u0011\u001d\t\u0019b\u0001a\u0001\u0003+\tQa\u001a:ba\"\u0004RAM\u001a6\u0003\u001b\u0011ACU;o]\u0006\u0014G.Z$sCBD\u0017\tZ1qi\u0016\u0014X\u0003BA\u000e\u0003C\u00192\u0001BA\u000f!\u0011\u0001c\"a\b\u0011\u0007e\n\t\u0003B\u0003<\t\t\u0007A(\u0001\u0005sk:t\u0017M\u00197f!\u0019\t)!a\u0003\u0002 Q!\u0011\u0011FA\u0017!\u0015\tY\u0003BA\u0010\u001b\u0005\t\u0001bBA\u0012\r\u0001\u0007\u0011QE\u0001\u0006g\"\f\u0007/Z\u000b\u0003\u0003g\u00012AMA\u001b\u0015\r\t9DG\u0001\f\u00072|7/\u001a3TQ\u0006\u0004X-\u0001\tue\u00064XM]:bY\n+\u0018\u000e\u001c3feV\u0011\u0011Q\b\t\u0005\u0003\u007f\t)%\u0004\u0002\u0002B)\u0019\u00111\t\u000e\u0002\t%l\u0007\u000f\\\u0005\u0005\u0003\u000f\n\tE\u0001\tUe\u00064XM]:bY\n+\u0018\u000e\u001c3fe\u0006AAo\\*ue&tw\rF\u0001u+\u0011\ty%!\u0016\u0015\t\u0005E\u0013q\u000b\t\u0006\u0003W!\u00111\u000b\t\u0004s\u0005UC!B.\u000b\u0005\u0004a\u0004BB/\u000b\u0001\u0004\tI\u0006\u0005\u0004`I\u0006}\u00111\u000b\u000b\u0005\u0003?\ti\u0006C\u0003R\u0017\u0001\u0007!\u000b\u0006\u0003\u0002*\u0005\u0005\u0004\"B5\r\u0001\u0004QWCAA\u0013\u0001")
public abstract class RunnableGraph<Mat>
implements Graph<ClosedShape, Mat> {
    public static <Mat> RunnableGraph<Mat> fromGraph(Graph<ClosedShape, Mat> graph) {
        return RunnableGraph$.MODULE$.fromGraph(graph);
    }

    @Override
    public Graph<ClosedShape, Mat> async() {
        return Graph.async$(this);
    }

    @Override
    public Graph<ClosedShape, Mat> async(String dispatcher) {
        return Graph.async$(this, dispatcher);
    }

    @Override
    public Graph<ClosedShape, Mat> async(String dispatcher, int inputBufferSize) {
        return Graph.async$(this, dispatcher, inputBufferSize);
    }

    @Override
    public Attributes getAttributes() {
        return Graph.getAttributes$(this);
    }

    public Mat run(ClassicActorSystemProvider systemProvider) {
        return this.run(((SystemMaterializer)SystemMaterializer$.MODULE$.apply(systemProvider.classicSystem())).materializer());
    }

    public abstract Mat run(Materializer var1);

    public abstract <Mat2> RunnableGraph<Mat2> mapMaterializedValue(Function<Mat, Mat2> var1);

    public abstract RunnableGraph<Mat> withAttributes(Attributes var1);

    public RunnableGraph<Mat> addAttributes(Attributes attr) {
        return this.withAttributes(this.traversalBuilder().attributes().and(attr));
    }

    public RunnableGraph<Mat> named(String name) {
        return this.withAttributes(Attributes$.MODULE$.name(name));
    }

    public abstract akka.stream.scaladsl.RunnableGraph<Mat> asScala();

    public RunnableGraph() {
        Graph.$init$(this);
    }

    private static final class RunnableGraphAdapter<Mat>
    extends RunnableGraph<Mat> {
        private final akka.stream.scaladsl.RunnableGraph<Mat> runnable;

        @Override
        public ClosedShape$ shape() {
            return ClosedShape$.MODULE$;
        }

        @Override
        public TraversalBuilder traversalBuilder() {
            return this.runnable.traversalBuilder();
        }

        public String toString() {
            return this.runnable.toString();
        }

        @Override
        public <Mat2> RunnableGraphAdapter<Mat2> mapMaterializedValue(Function<Mat, Mat2> f) {
            return new RunnableGraphAdapter(this.runnable.mapMaterializedValue((Function1<Object, Object> & Serializable)(Mat param2) -> f.apply(param2)));
        }

        @Override
        public Mat run(Materializer materializer) {
            return this.runnable.run(materializer);
        }

        @Override
        public RunnableGraphAdapter<Mat> withAttributes(Attributes attr) {
            Graph newRunnable = this.runnable.withAttributes(attr);
            if (newRunnable == this.runnable) {
                return this;
            }
            return new RunnableGraphAdapter<Mat>(newRunnable);
        }

        @Override
        public akka.stream.scaladsl.RunnableGraph<Mat> asScala() {
            return this.runnable;
        }

        public RunnableGraphAdapter(akka.stream.scaladsl.RunnableGraph<Mat> runnable2) {
            this.runnable = runnable2;
        }
    }
}

