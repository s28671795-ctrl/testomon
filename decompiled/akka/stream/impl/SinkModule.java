/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.DoNotInherit;
import akka.event.Logging$;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Inlet$;
import akka.stream.MaterializationContext;
import akka.stream.SinkShape;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.SinkModuleIslandTag$;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.TraversalBuilder;
import scala.Tuple2;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005=4aAC\u0006\u0002\u0002=\t\u0002\u0002\u0003\u001f\u0001\u0005\u000b\u0007I\u0011A\u001f\t\u0011y\u0002!\u0011!Q\u0001\n)BQa\u0010\u0001\u0005\u0002\u0001CQ\u0001\u0012\u0001\u0007\u0002\u0015CQA\u0014\u0001\u0007\u0002=CQa\u0015\u0001\u0005BQCQ\u0001\u0017\u0001\u0005\u0012eCQ\u0001\u0018\u0001\u0005\u0012uCQA\u001a\u0001\u0005F\u001d\u0014!bU5oW6{G-\u001e7f\u0015\taQ\"\u0001\u0003j[Bd'B\u0001\b\u0010\u0003\u0019\u0019HO]3b[*\t\u0001#\u0001\u0003bW.\fWc\u0001\n1uM\u0019\u0001aE\r\u0011\u0005Q9R\"A\u000b\u000b\u0003Y\tQa]2bY\u0006L!\u0001G\u000b\u0003\r\u0005s\u0017PU3g!\u0011QrEK\u001d\u000f\u0005m)cB\u0001\u000f%\u001d\ti2E\u0004\u0002\u001fE5\tqD\u0003\u0002!C\u00051AH]8piz\u001a\u0001!C\u0001\u0011\u0013\tqq\"\u0003\u0002\r\u001b%\u0011aeC\u0001\r'R\u0014X-Y7MCf|W\u000f^\u0005\u0003Q%\u0012A\"\u0011;p[&\u001cWj\u001c3vY\u0016T!AJ\u0006\u0011\u0007-bc&D\u0001\u000e\u0013\tiSBA\u0005TS:\\7\u000b[1qKB\u0011q\u0006\r\u0007\u0001\t\u0019\t\u0004\u0001#b\u0001e\t\u0011\u0011J\\\t\u0003gY\u0002\"\u0001\u0006\u001b\n\u0005U*\"a\u0002(pi\"Lgn\u001a\t\u0003)]J!\u0001O\u000b\u0003\u0007\u0005s\u0017\u0010\u0005\u00020u\u0011)1\b\u0001b\u0001e\t\u0019Q*\u0019;\u0002\u000bMD\u0017\r]3\u0016\u0003)\naa\u001d5ba\u0016\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002B\u0007B!!\t\u0001\u0018:\u001b\u0005Y\u0001\"\u0002\u001f\u0004\u0001\u0004Q\u0013AB2sK\u0006$X\r\u0006\u0002G\u0013B!AcR\n:\u0013\tAUC\u0001\u0004UkBdWM\r\u0005\u0006\u0015\u0012\u0001\raS\u0001\bG>tG/\u001a=u!\tYC*\u0003\u0002N\u001b\t1R*\u0019;fe&\fG.\u001b>bi&|gnQ8oi\u0016DH/\u0001\u0006biR\u0014\u0018NY;uKN,\u0012\u0001\u0015\t\u0003WEK!AU\u0007\u0003\u0015\u0005#HO]5ckR,7/\u0001\tue\u00064XM]:bY\n+\u0018\u000e\u001c3feV\tQ\u000b\u0005\u0002C-&\u0011qk\u0003\u0002\u0011)J\fg/\u001a:tC2\u0014U/\u001b7eKJ\f!\"Y7f]\u0012\u001c\u0006.\u00199f)\tQ#\fC\u0003\\\u000f\u0001\u0007\u0001+\u0001\u0003biR\u0014\u0018!\u00027bE\u0016dW#\u00010\u0011\u0005}\u001bgB\u00011b!\tqR#\u0003\u0002c+\u00051\u0001K]3eK\u001aL!\u0001Z3\u0003\rM#(/\u001b8h\u0015\t\u0011W#\u0001\u0005u_N#(/\u001b8h)\u0005q\u0006F\u0001\u0001j!\tQW.D\u0001l\u0015\taw\"\u0001\u0006b]:|G/\u0019;j_:L!A\\6\u0003\u0019\u0011{gj\u001c;J]\",'/\u001b;")
@DoNotInherit
public abstract class SinkModule<In, Mat>
implements StreamLayout.AtomicModule<SinkShape<In>, Mat> {
    private final SinkShape<In> shape;

    @Override
    public Graph<SinkShape<In>, Mat> named(String name) {
        return Graph.named$(this, name);
    }

    @Override
    public Graph<SinkShape<In>, Mat> async() {
        return Graph.async$(this);
    }

    @Override
    public Graph<SinkShape<In>, Mat> async(String dispatcher) {
        return Graph.async$(this, dispatcher);
    }

    @Override
    public Graph<SinkShape<In>, Mat> async(String dispatcher, int inputBufferSize) {
        return Graph.async$(this, dispatcher, inputBufferSize);
    }

    @Override
    public Graph<SinkShape<In>, Mat> addAttributes(Attributes attr) {
        return Graph.addAttributes$(this, attr);
    }

    @Override
    public Attributes getAttributes() {
        return Graph.getAttributes$(this);
    }

    @Override
    public SinkShape<In> shape() {
        return this.shape;
    }

    public abstract Tuple2<Object, Mat> create(MaterializationContext var1);

    public abstract Attributes attributes();

    @Override
    public TraversalBuilder traversalBuilder() {
        return LinearTraversalBuilder$.MODULE$.fromModule(this, this.attributes()).makeIsland(SinkModuleIslandTag$.MODULE$);
    }

    public SinkShape<In> amendShape(Attributes attr) {
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
        return ((SinkShape)this.shape()).copy(Inlet$.MODULE$.apply(new StringBuilder(3).append(thatN).append(".in").toString()));
    }

    public String label() {
        return Logging$.MODULE$.simpleName(this);
    }

    public final String toString() {
        return StringOps$.MODULE$.format$extension("%s [%08x]", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.label(), BoxesRunTime.boxToInteger(System.identityHashCode(this))}));
    }

    public SinkModule(SinkShape<In> shape) {
        this.shape = shape;
        Graph.$init$(this);
    }
}

