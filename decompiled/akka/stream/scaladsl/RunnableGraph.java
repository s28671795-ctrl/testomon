/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.ClosedShape;
import akka.stream.ClosedShape$;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.impl.TraversalBuilder;
import akka.stream.scaladsl.RunnableGraph$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tMq!B\u0011#\u0011\u0003Ic!B\u0016#\u0011\u0003a\u0003\"B\u001e\u0002\t\u0003a\u0004\"B\u001f\u0002\t\u0003q\u0004\"CAs\u0003\u0005\u0005I\u0011QAt\u0011%\t\u00190AA\u0001\n\u0003\u000b)\u0010C\u0005\u0003\n\u0005\t\t\u0011\"\u0003\u0003\f\u0019!1F\t\"B\u0011!\u0019wA!f\u0001\n\u0003\"\u0007\u0002C6\b\u0005#\u0005\u000b\u0011B3\t\u000bm:A\u0011\u00017\t\u000b=<A\u0011\t9\t\u000bQ<A\u0011A;\t\u000f\u0005\u0005q\u0001\"\u0001\u0002\u0004!9\u0011\u0011C\u0004\u0005B\u0005M\u0001bBA\u0010\u000f\u0011\u0005\u0013\u0011\u0005\u0005\b\u0003K9A\u0011IA\u0014\u0011\u001d\tid\u0002C!\u0003\u007fAq!!\u0010\b\t\u0003\n\t\u0005C\u0004\u0002>\u001d!\t%a\u0012\t\u000f\u0005Us\u0001\"\u0001\u0002X!9\u00111M\u0004\u0005B\u0005\u0015\u0004\"CA4\u000f\u0005\u0005I\u0011AA5\u0011%\t)hBI\u0001\n\u0003\t9\bC\u0005\u0002\u0012\u001e\t\t\u0011\"\u0011\u0002\u0014\"I\u0011qT\u0004\u0002\u0002\u0013\u0005\u0011\u0011\u0015\u0005\n\u0003G;\u0011\u0011!C\u0001\u0003KC\u0011\"a+\b\u0003\u0003%\t%!,\t\u0013\u0005mv!!A\u0005\u0002\u0005u\u0006\"CAd\u000f\u0005\u0005I\u0011IAe\u0011%\timBA\u0001\n\u0003\ny\rC\u0005\u0002R\u001e\t\t\u0011\"\u0011\u0002T\"I\u0011Q[\u0004\u0002\u0002\u0013\u0005\u0013q[\u0001\u000e%Vtg.\u00192mK\u001e\u0013\u0018\r\u001d5\u000b\u0005\r\"\u0013\u0001C:dC2\fGm\u001d7\u000b\u0005\u00152\u0013AB:ue\u0016\fWNC\u0001(\u0003\u0011\t7n[1\u0004\u0001A\u0011!&A\u0007\u0002E\ti!+\u001e8oC\ndWm\u0012:ba\"\u001c2!A\u00174!\tq\u0013'D\u00010\u0015\u0005\u0001\u0014!B:dC2\f\u0017B\u0001\u001a0\u0005\u0019\te.\u001f*fMB\u0011A'O\u0007\u0002k)\u0011agN\u0001\u0003S>T\u0011\u0001O\u0001\u0005U\u00064\u0018-\u0003\u0002;k\ta1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012!K\u0001\nMJ|Wn\u0012:ba\",2aPAo)\r\u0001\u0015q\u001c\t\u0005U\u001d\tY.\u0006\u0002C\u0019N)q!L\"V1B!A)R$K\u001b\u0005!\u0013B\u0001$%\u0005\u00159%/\u00199i!\t!\u0005*\u0003\u0002JI\tY1\t\\8tK\u0012\u001c\u0006.\u00199f!\tYE\n\u0004\u0001\u0005\r5;AQ1\u0001O\u0005\ri\u0015\r^\t\u0003\u001fJ\u0003\"A\f)\n\u0005E{#a\u0002(pi\"Lgn\u001a\t\u0003]MK!\u0001V\u0018\u0003\u0007\u0005s\u0017\u0010\u0005\u0002/-&\u0011qk\f\u0002\b!J|G-^2u!\tI\u0016M\u0004\u0002[?:\u00111LX\u0007\u00029*\u0011Q\fK\u0001\u0007yI|w\u000e\u001e \n\u0003AJ!\u0001Y\u0018\u0002\u000fA\f7m[1hK&\u0011!H\u0019\u0006\u0003A>\n\u0001\u0003\u001e:bm\u0016\u00148/\u00197Ck&dG-\u001a:\u0016\u0003\u0015\u0004\"AZ5\u000e\u0003\u001dT!\u0001\u001b\u0013\u0002\t%l\u0007\u000f\\\u0005\u0003U\u001e\u0014\u0001\u0003\u0016:bm\u0016\u00148/\u00197Ck&dG-\u001a:\u0002#Q\u0014\u0018M^3sg\u0006d')^5mI\u0016\u0014\b\u0005\u0006\u0002n]B\u0019!f\u0002&\t\u000b\rT\u0001\u0019A3\u0002\u000bMD\u0017\r]3\u0016\u0003E\u0004\"\u0001\u0012:\u000b\u0005M$\u0013aC\"m_N,Gm\u00155ba\u0016\fA#\\1q\u001b\u0006$XM]5bY&TX\r\u001a,bYV,WC\u0001<z)\t98\u0010E\u0002+\u000fa\u0004\"aS=\u0005\u000bid!\u0019\u0001(\u0003\t5\u000bGO\r\u0005\u0006y2\u0001\r!`\u0001\u0002MB!aF &y\u0013\tyxFA\u0005Gk:\u001cG/[8oc\u0005\u0019!/\u001e8\u0015\u0005\u0005\u0015Ac\u0001&\u0002\b!9\u0011\u0011B\u0007A\u0004\u0005-\u0011\u0001D7bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\bc\u0001#\u0002\u000e%\u0019\u0011q\u0002\u0013\u0003\u00195\u000bG/\u001a:jC2L'0\u001a:\u0002\u001b\u0005$G-\u0011;ue&\u0014W\u000f^3t)\ri\u0017Q\u0003\u0005\b\u0003/q\u0001\u0019AA\r\u0003\u0011\tG\u000f\u001e:\u0011\u0007\u0011\u000bY\"C\u0002\u0002\u001e\u0011\u0012!\"\u0011;ue&\u0014W\u000f^3t\u000399\u0018\u000e\u001e5BiR\u0014\u0018NY;uKN$2!\\A\u0012\u0011\u001d\t9b\u0004a\u0001\u00033\tQA\\1nK\u0012$2!\\A\u0015\u0011\u001d\tY\u0003\u0005a\u0001\u0003[\tAA\\1nKB!\u0011qFA\u001c\u001d\u0011\t\t$a\r\u0011\u0005m{\u0013bAA\u001b_\u00051\u0001K]3eK\u001aLA!!\u000f\u0002<\t11\u000b\u001e:j]\u001eT1!!\u000e0\u0003\u0015\t7/\u001f8d+\u0005iGcA7\u0002D!9\u0011Q\t\nA\u0002\u00055\u0012A\u00033jgB\fGo\u00195feR)Q.!\u0013\u0002L!9\u0011QI\nA\u0002\u00055\u0002bBA''\u0001\u0007\u0011qJ\u0001\u0010S:\u0004X\u000f\u001e\"vM\u001a,'oU5{KB\u0019a&!\u0015\n\u0007\u0005MsFA\u0002J]R\fa!Y:KCZ\fWCAA-!\u0015\tY&!\u0019K\u001b\t\tiFC\u0002\u0002`\u0011\nqA[1wC\u0012\u001cH.C\u0002,\u0003;\nQbZ3u\u0003R$(/\u001b2vi\u0016\u001cXCAA\r\u0003\u0011\u0019w\u000e]=\u0016\t\u0005-\u0014\u0011\u000f\u000b\u0005\u0003[\n\u0019\b\u0005\u0003+\u000f\u0005=\u0004cA&\u0002r\u0011)QJ\u0006b\u0001\u001d\"91M\u0006I\u0001\u0002\u0004)\u0017AD2paf$C-\u001a4bk2$H%M\u000b\u0005\u0003s\ny)\u0006\u0002\u0002|)\u001aQ-! ,\u0005\u0005}\u0004\u0003BAA\u0003\u0017k!!a!\u000b\t\u0005\u0015\u0015qQ\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!#0\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u001b\u000b\u0019IA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Q!T\fC\u00029\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAAK!\u0011\t9*!(\u000e\u0005\u0005e%bAANo\u0005!A.\u00198h\u0013\u0011\tI$!'\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005=\u0013A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004%\u0006\u001d\u0006\"CAU5\u0005\u0005\t\u0019AA(\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0016\t\u0006\u0003c\u000b9LU\u0007\u0003\u0003gS1!!.0\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003s\u000b\u0019L\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA`\u0003\u000b\u00042ALAa\u0013\r\t\u0019m\f\u0002\b\u0005>|G.Z1o\u0011!\tI\u000bHA\u0001\u0002\u0004\u0011\u0016A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!&\u0002L\"I\u0011\u0011V\u000f\u0002\u0002\u0003\u0007\u0011qJ\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011qJ\u0001\ti>\u001cFO]5oOR\u0011\u0011QS\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005}\u0016\u0011\u001c\u0005\t\u0003S\u0003\u0013\u0011!a\u0001%B\u00191*!8\u0005\u000b5\u001b!\u0019\u0001(\t\u000f\u0005\u00058\u00011\u0001\u0002d\u0006\tq\rE\u0003E\u000b\u001e\u000bY.A\u0003baBd\u00170\u0006\u0003\u0002j\u0006=H\u0003BAv\u0003c\u0004BAK\u0004\u0002nB\u00191*a<\u0005\u000b5#!\u0019\u0001(\t\u000b\r$\u0001\u0019A3\u0002\u000fUt\u0017\r\u001d9msV!\u0011q\u001fB\u0004)\u0011\tI0a@\u0011\t9\nY0Z\u0005\u0004\u0003{|#AB(qi&|g\u000eC\u0005\u0003\u0002\u0015\t\t\u00111\u0001\u0003\u0004\u0005\u0019\u0001\u0010\n\u0019\u0011\t):!Q\u0001\t\u0004\u0017\n\u001dA!B'\u0006\u0005\u0004q\u0015\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\u0007!\u0011\t9Ja\u0004\n\t\tE\u0011\u0011\u0014\u0002\u0007\u001f\nTWm\u0019;")
public final class RunnableGraph<Mat>
implements Graph<ClosedShape, Mat>,
Product,
Serializable {
    private final TraversalBuilder traversalBuilder;

    public static <Mat> Option<TraversalBuilder> unapply(RunnableGraph<Mat> x$0) {
        return RunnableGraph$.MODULE$.unapply(x$0);
    }

    public static <Mat> RunnableGraph<Mat> apply(TraversalBuilder traversalBuilder) {
        return RunnableGraph$.MODULE$.apply(traversalBuilder);
    }

    public static <Mat> RunnableGraph<Mat> fromGraph(Graph<ClosedShape, Mat> g2) {
        return RunnableGraph$.MODULE$.fromGraph(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public TraversalBuilder traversalBuilder() {
        return this.traversalBuilder;
    }

    @Override
    public ClosedShape$ shape() {
        return ClosedShape$.MODULE$;
    }

    public <Mat2> RunnableGraph<Mat2> mapMaterializedValue(Function1<Mat, Mat2> f) {
        return this.copy(this.traversalBuilder().transformMat(f));
    }

    public Mat run(Materializer materializer) {
        return materializer.materialize(this);
    }

    public RunnableGraph<Mat> addAttributes(Attributes attr) {
        return this.withAttributes(this.traversalBuilder().attributes().and(attr));
    }

    public RunnableGraph<Mat> withAttributes(Attributes attr) {
        return new RunnableGraph<Mat>(this.traversalBuilder().setAttributes(attr));
    }

    public RunnableGraph<Mat> named(String name) {
        return this.addAttributes(Attributes$.MODULE$.name(name));
    }

    public RunnableGraph<Mat> async() {
        return (RunnableGraph)Graph.async$(this);
    }

    public RunnableGraph<Mat> async(String dispatcher) {
        return (RunnableGraph)Graph.async$(this, dispatcher);
    }

    public RunnableGraph<Mat> async(String dispatcher, int inputBufferSize) {
        return (RunnableGraph)Graph.async$(this, dispatcher, inputBufferSize);
    }

    public akka.stream.javadsl.RunnableGraph<Mat> asJava() {
        return akka.stream.javadsl.RunnableGraph$.MODULE$.fromGraph(this);
    }

    @Override
    public Attributes getAttributes() {
        return this.traversalBuilder().attributes();
    }

    public <Mat> RunnableGraph<Mat> copy(TraversalBuilder traversalBuilder) {
        return new RunnableGraph<Mat>(traversalBuilder);
    }

    public <Mat> TraversalBuilder copy$default$1() {
        return this.traversalBuilder();
    }

    @Override
    public String productPrefix() {
        return "RunnableGraph";
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
                return this.traversalBuilder();
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
        return x$1 instanceof RunnableGraph;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "traversalBuilder";
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
        if (!(object instanceof RunnableGraph)) return false;
        boolean bl = true;
        if (!bl) return false;
        RunnableGraph runnableGraph = (RunnableGraph)x$1;
        TraversalBuilder traversalBuilder = this.traversalBuilder();
        TraversalBuilder traversalBuilder2 = runnableGraph.traversalBuilder();
        if (traversalBuilder != null) {
            if (!traversalBuilder.equals(traversalBuilder2)) return false;
            return true;
        }
        if (traversalBuilder2 == null) return true;
        return false;
    }

    public RunnableGraph(TraversalBuilder traversalBuilder) {
        this.traversalBuilder = traversalBuilder;
        Graph.$init$(this);
        Product.$init$(this);
    }
}

