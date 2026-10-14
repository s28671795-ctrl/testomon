/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.DoNotInherit;
import akka.event.Logging$;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.MaterializationContext;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.SourceModuleIslandTag$;
import akka.stream.impl.StreamLayout;
import org.reactivestreams.Publisher;
import scala.Tuple2;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005aA\u0002\u0006\f\u0003\u0003y\u0011\u0003\u0003\u0005=\u0001\t\u0015\r\u0011\"\u0001>\u0011!q\u0004A!A!\u0002\u0013Q\u0003\"B \u0001\t\u0003\u0001\u0005\"\u0002#\u0001\t#)\u0005\"\u0002(\u0001\t\u000bz\u0005\"\u0002)\u0001\r\u0003\t\u0006\"\u00027\u0001\r\u0003i\u0007\"B9\u0001\t#\u0011\bBB;\u0001\t\u0003jaO\u0001\u0007T_V\u00148-Z'pIVdWM\u0003\u0002\r\u001b\u0005!\u0011.\u001c9m\u0015\tqq\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002!\u0005!\u0011m[6b+\r\u0011\u0002GO\n\u0004\u0001MI\u0002C\u0001\u000b\u0018\u001b\u0005)\"\"\u0001\f\u0002\u000bM\u001c\u0017\r\\1\n\u0005a)\"AB!osJ+g\r\u0005\u0003\u001bO)JdBA\u000e&\u001d\taBE\u0004\u0002\u001eG9\u0011aDI\u0007\u0002?)\u0011\u0001%I\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\t\u0001#\u0003\u0002\u000f\u001f%\u0011A\"D\u0005\u0003M-\tAb\u0015;sK\u0006lG*Y=pkRL!\u0001K\u0015\u0003\u0019\u0005#x.\\5d\u001b>$W\u000f\\3\u000b\u0005\u0019Z\u0001cA\u0016-]5\tQ\"\u0003\u0002.\u001b\tY1k\\;sG\u0016\u001c\u0006.\u00199f!\ty\u0003\u0007\u0004\u0001\u0005\rE\u0002AQ1\u00013\u0005\ryU\u000f^\t\u0003gY\u0002\"\u0001\u0006\u001b\n\u0005U*\"a\u0002(pi\"Lgn\u001a\t\u0003)]J!\u0001O\u000b\u0003\u0007\u0005s\u0017\u0010\u0005\u00020u\u001111\b\u0001CC\u0002I\u00121!T1u\u0003\u0015\u0019\b.\u00199f+\u0005Q\u0013AB:iCB,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003\u0003\u000e\u0003BA\u0011\u0001/s5\t1\u0002C\u0003=\u0007\u0001\u0007!&A\u0003mC\n,G.F\u0001G!\t95J\u0004\u0002I\u0013B\u0011a$F\u0005\u0003\u0015V\ta\u0001\u0015:fI\u00164\u0017B\u0001'N\u0005\u0019\u0019FO]5oO*\u0011!*F\u0001\ti>\u001cFO]5oOR\ta)\u0001\u0004de\u0016\fG/\u001a\u000b\u0003%\u001e\u0004B\u0001F*Vs%\u0011A+\u0006\u0002\u0007)V\u0004H.\u001a\u001a+\u0005Ys\u0006cA,]]5\t\u0001L\u0003\u0002Z5\u0006y!/Z1di&4Xm\u001d;sK\u0006l7OC\u0001\\\u0003\ry'oZ\u0005\u0003;b\u0013\u0011\u0002U;cY&\u001c\b.\u001a:,\u0003}\u0003\"\u0001Y3\u000e\u0003\u0005T!AY2\u0002\u0013Ut7\r[3dW\u0016$'B\u00013\u0016\u0003)\tgN\\8uCRLwN\\\u0005\u0003M\u0006\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0011\u0015Ag\u00011\u0001j\u0003\u001d\u0019wN\u001c;fqR\u0004\"a\u000b6\n\u0005-l!AF'bi\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8D_:$X\r\u001f;\u0002\u0015\u0005$HO]5ckR,7/F\u0001o!\tYs.\u0003\u0002q\u001b\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0015\u0005lWM\u001c3TQ\u0006\u0004X\r\u0006\u0002+g\")A\u000f\u0003a\u0001]\u0006!\u0011\r\u001e;s\u0003A!(/\u0019<feN\fGNQ;jY\u0012,'/F\u0001x!\t\u0011\u00050\u0003\u0002z\u0017\t1B*\u001b8fCJ$&/\u0019<feN\fGNQ;jY\u0012,'\u000f\u000b\u0002\u0001wB\u0011AP`\u0007\u0002{*\u0011AmD\u0005\u0003\u007fv\u0014A\u0002R8O_RLe\u000e[3sSR\u0004")
@DoNotInherit
public abstract class SourceModule<Out, Mat>
implements StreamLayout.AtomicModule<SourceShape<Out>, Mat> {
    private final SourceShape<Out> shape;

    @Override
    public Graph<SourceShape<Out>, Mat> named(String name) {
        return Graph.named$(this, name);
    }

    @Override
    public Graph<SourceShape<Out>, Mat> async() {
        return Graph.async$(this);
    }

    @Override
    public Graph<SourceShape<Out>, Mat> async(String dispatcher) {
        return Graph.async$(this, dispatcher);
    }

    @Override
    public Graph<SourceShape<Out>, Mat> async(String dispatcher, int inputBufferSize) {
        return Graph.async$(this, dispatcher, inputBufferSize);
    }

    @Override
    public Graph<SourceShape<Out>, Mat> addAttributes(Attributes attr) {
        return Graph.addAttributes$(this, attr);
    }

    @Override
    public Attributes getAttributes() {
        return Graph.getAttributes$(this);
    }

    @Override
    public SourceShape<Out> shape() {
        return this.shape;
    }

    public String label() {
        return Logging$.MODULE$.simpleName(this);
    }

    public final String toString() {
        return StringOps$.MODULE$.format$extension("%s [%08x]", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.label(), BoxesRunTime.boxToInteger(System.identityHashCode(this))}));
    }

    public abstract Tuple2<Publisher<Out>, Mat> create(MaterializationContext var1);

    public abstract Attributes attributes();

    public SourceShape<Out> amendShape(Attributes attr) {
        String thatN;
        block3: {
            block2: {
                String thisN = this.traversalBuilder().attributes().nameOrDefault(null);
                thatN = attr.nameOrDefault(null);
                if (thatN == null) break block2;
                String string = thisN;
                String string2 = thatN;
                if (string != null ? !string.equals(string2) : string2 != null) break block3;
            }
            return this.shape();
        }
        return ((SourceShape)this.shape()).copy(Outlet$.MODULE$.apply(new StringBuilder(4).append(thatN).append(".out").toString()));
    }

    @Override
    public LinearTraversalBuilder traversalBuilder() {
        return LinearTraversalBuilder$.MODULE$.fromModule(this, this.attributes()).makeIsland(SourceModuleIslandTag$.MODULE$);
    }

    public SourceModule(SourceShape<Out> shape) {
        this.shape = shape;
        Graph.$init$(this);
    }
}

