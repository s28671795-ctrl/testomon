/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.ProcessorModule$;
import akka.stream.impl.ProcessorModuleIslandTag$;
import akka.stream.impl.StreamLayout;
import java.io.Serializable;
import org.reactivestreams.Processor;
import scala.Function0;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t]c!B\u0012%\u0005\"R\u0003\u0002C0\u0001\u0005+\u0007I\u0011\u00011\t\u0011=\u0004!\u0011#Q\u0001\n\u0005D\u0001\u0002\u001d\u0001\u0003\u0016\u0004%\t!\u001d\u0005\tk\u0002\u0011\t\u0012)A\u0005e\")a\u000f\u0001C\u0001o\"91\u0010\u0001b\u0001\n\u0003a\bbBA\u0001\u0001\u0001\u0006I! \u0005\n\u0003\u0007\u0001!\u0019!C\u0001\u0003\u000bA\u0001\"!\u0004\u0001A\u0003%\u0011q\u0001\u0005\n\u0003\u001f\u0001!\u0019!C!\u0003#Aq!a\u0005\u0001A\u0003%!\bC\u0004\u0002\u0016\u0001!\t%a\u0006\t\u000f\u0005m\u0001\u0001\"\u0011\u0002\u001e!A\u0011q\u0006\u0001\u0005B\u0019\n\t\u0004C\u0005\u0002:\u0001\t\t\u0011\"\u0001\u0002<!I\u0011q\u000b\u0001\u0012\u0002\u0013\u0005\u0011\u0011\f\u0005\n\u0003o\u0002\u0011\u0013!C\u0001\u0003sB\u0011\"!\"\u0001\u0003\u0003%\t%a\"\t\u0013\u0005]\u0005!!A\u0005\u0002\u0005e\u0005\"CAQ\u0001\u0005\u0005I\u0011AAR\u0011%\tI\u000bAA\u0001\n\u0003\nY\u000bC\u0005\u0002:\u0002\t\t\u0011\"\u0001\u0002<\"I\u0011Q\u0019\u0001\u0002\u0002\u0013\u0005\u0013q\u0019\u0005\n\u0003\u0017\u0004\u0011\u0011!C!\u0003\u001bD\u0011\"a4\u0001\u0003\u0003%\t%!5\b\u0015\u0005\u0005H%!A\t\u0002!\n\u0019OB\u0005$I\u0005\u0005\t\u0012\u0001\u0015\u0002f\"1ao\u0007C\u0001\u0003cD\u0011\"a\u0007\u001c\u0003\u0003%)%a=\t\u0013\u0005U8$!A\u0005\u0002\u0006]\b\"\u0003B\n7E\u0005I\u0011\u0001B\u000b\u0011%\u0011ibGA\u0001\n\u0003\u0013y\u0002C\u0005\u0003Dm\t\n\u0011\"\u0001\u0003F!I!QJ\u000e\u0002\u0002\u0013%!q\n\u0002\u0010!J|7-Z:t_Jlu\u000eZ;mK*\u0011QEJ\u0001\u0005S6\u0004HN\u0003\u0002(Q\u000511\u000f\u001e:fC6T\u0011!K\u0001\u0005C.\\\u0017-\u0006\u0003,\u0001.s5#\u0002\u0001-eA\u001b\u0006CA\u00171\u001b\u0005q#\"A\u0018\u0002\u000bM\u001c\u0017\r\\1\n\u0005Er#AB!osJ+g\r\u0005\u00034oijeB\u0001\u001b6\u001b\u0005!\u0013B\u0001\u001c%\u00031\u0019FO]3b[2\u000b\u0017p\\;u\u0013\tA\u0014H\u0001\u0007Bi>l\u0017nY'pIVdWM\u0003\u00027IA!1\b\u0010 K\u001b\u00051\u0013BA\u001f'\u0005%1En\\<TQ\u0006\u0004X\r\u0005\u0002@\u00012\u0001A!B!\u0001\u0005\u0004\u0019%AA%o\u0007\u0001\t\"\u0001R$\u0011\u00055*\u0015B\u0001$/\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\f%\n\u0005%s#aA!osB\u0011qh\u0013\u0003\u0006\u0019\u0002\u0011\ra\u0011\u0002\u0004\u001fV$\bCA O\t\u0015y\u0005A1\u0001D\u0005\ri\u0015\r\u001e\t\u0003[EK!A\u0015\u0018\u0003\u000fA\u0013x\u000eZ;diB\u0011A\u000b\u0018\b\u0003+js!AV-\u000e\u0003]S!\u0001\u0017\"\u0002\rq\u0012xn\u001c;?\u0013\u0005y\u0013BA./\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u00180\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005ms\u0013aD2sK\u0006$X\r\u0015:pG\u0016\u001c8o\u001c:\u0016\u0003\u0005\u00042!\f2e\u0013\t\u0019gFA\u0005Gk:\u001cG/[8oaA!Q&Z4N\u0013\t1gF\u0001\u0004UkBdWM\r\t\u0005Q6t$*D\u0001j\u0015\tQ7.A\bsK\u0006\u001cG/\u001b<fgR\u0014X-Y7t\u0015\u0005a\u0017aA8sO&\u0011a.\u001b\u0002\n!J|7-Z:t_J\f\u0001c\u0019:fCR,\u0007K]8dKN\u001cxN\u001d\u0011\u0002\u0015\u0005$HO]5ckR,7/F\u0001s!\tY4/\u0003\u0002uM\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0017\u0005$HO]5ckR,7\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007aL(\u0010E\u00035\u0001yRU\nC\u0003`\u000b\u0001\u0007\u0011\rC\u0004q\u000bA\u0005\t\u0019\u0001:\u0002\r%t\u0007k\u001c:u+\u0005i\bcA\u001e\u007f}%\u0011qP\n\u0002\u0006\u0013:dW\r^\u0001\bS:\u0004vN\u001d;!\u0003\u001dyW\u000f\u001e)peR,\"!a\u0002\u0011\tm\nIAS\u0005\u0004\u0003\u00171#AB(vi2,G/\u0001\u0005pkR\u0004vN\u001d;!\u0003\u0015\u0019\b.\u00199f+\u0005Q\u0014AB:iCB,\u0007%\u0001\bxSRD\u0017\t\u001e;sS\n,H/Z:\u0015\u0007a\fI\u0002C\u0003q\u0019\u0001\u0007!/\u0001\u0005u_N#(/\u001b8h)\t\ty\u0002\u0005\u0003\u0002\"\u0005%b\u0002BA\u0012\u0003K\u0001\"A\u0016\u0018\n\u0007\u0005\u001db&\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003W\tiC\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003Oq\u0013\u0001\u0005;sCZ,'o]1m\u0005VLG\u000eZ3s+\t\t\u0019\u0004E\u00025\u0003kI1!a\u000e%\u0005Ya\u0015N\\3beR\u0013\u0018M^3sg\u0006d')^5mI\u0016\u0014\u0018\u0001B2paf,\u0002\"!\u0010\u0002D\u0005\u001d\u00131\n\u000b\u0007\u0003\u007f\ti%!\u0016\u0011\u0011Q\u0002\u0011\u0011IA#\u0003\u0013\u00022aPA\"\t\u0015\tuB1\u0001D!\ry\u0014q\t\u0003\u0006\u0019>\u0011\ra\u0011\t\u0004\u007f\u0005-C!B(\u0010\u0005\u0004\u0019\u0005\u0002C0\u0010!\u0003\u0005\r!a\u0014\u0011\t5\u0012\u0017\u0011\u000b\t\u0007[\u0015\f\u0019&!\u0013\u0011\r!l\u0017\u0011IA#\u0011\u001d\u0001x\u0002%AA\u0002I\fabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0005\u0002\\\u0005E\u00141OA;+\t\tiFK\u0002b\u0003?Z#!!\u0019\u0011\t\u0005\r\u0014QN\u0007\u0003\u0003KRA!a\u001a\u0002j\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003Wr\u0013AC1o]>$\u0018\r^5p]&!\u0011qNA3\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006\u0003B\u0011\ra\u0011\u0003\u0006\u0019B\u0011\ra\u0011\u0003\u0006\u001fB\u0011\raQ\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+!\tY(a \u0002\u0002\u0006\rUCAA?U\r\u0011\u0018q\f\u0003\u0006\u0003F\u0011\ra\u0011\u0003\u0006\u0019F\u0011\ra\u0011\u0003\u0006\u001fF\u0011\raQ\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005%\u0005\u0003BAF\u0003+k!!!$\u000b\t\u0005=\u0015\u0011S\u0001\u0005Y\u0006twM\u0003\u0002\u0002\u0014\u0006!!.\u0019<b\u0013\u0011\tY#!$\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005m\u0005cA\u0017\u0002\u001e&\u0019\u0011q\u0014\u0018\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0007\u001d\u000b)\u000bC\u0005\u0002(R\t\t\u00111\u0001\u0002\u001c\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!,\u0011\u000b\u0005=\u0016QW$\u000e\u0005\u0005E&bAAZ]\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005]\u0016\u0011\u0017\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002>\u0006\r\u0007cA\u0017\u0002@&\u0019\u0011\u0011\u0019\u0018\u0003\u000f\t{w\u000e\\3b]\"A\u0011q\u0015\f\u0002\u0002\u0003\u0007q)\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BAE\u0003\u0013D\u0011\"a*\u0018\u0003\u0003\u0005\r!a'\u0002\u0011!\f7\u000f[\"pI\u0016$\"!a'\u0002\r\u0015\fX/\u00197t)\u0011\ti,a5\t\u0011\u0005\u001d\u0016$!AA\u0002\u001dC3\u0001AAl!\u0011\tI.!8\u000e\u0005\u0005m'bAA6Q%!\u0011q\\An\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u001fA\u0013xnY3tg>\u0014Xj\u001c3vY\u0016\u0004\"\u0001N\u000e\u0014\tma\u0013q\u001d\t\u0005\u0003S\fy/\u0004\u0002\u0002l*!\u0011Q^AI\u0003\tIw.C\u0002^\u0003W$\"!a9\u0015\u0005\u0005%\u0015!B1qa2LX\u0003CA}\u0003\u007f\u0014\u0019Aa\u0002\u0015\r\u0005m(\u0011\u0002B\t!!!\u0004!!@\u0003\u0002\t\u0015\u0001cA \u0002\u0000\u0012)\u0011I\bb\u0001\u0007B\u0019qHa\u0001\u0005\u000b1s\"\u0019A\"\u0011\u0007}\u00129\u0001B\u0003P=\t\u00071\t\u0003\u0004`=\u0001\u0007!1\u0002\t\u0005[\t\u0014i\u0001\u0005\u0004.K\n=!Q\u0001\t\u0007Q6\fiP!\u0001\t\u000fAt\u0002\u0013!a\u0001e\u0006y\u0011\r\u001d9ms\u0012\"WMZ1vYR$#'\u0006\u0005\u0002|\t]!\u0011\u0004B\u000e\t\u0015\tuD1\u0001D\t\u0015auD1\u0001D\t\u0015yuD1\u0001D\u0003\u001d)h.\u00199qYf,\u0002B!\t\u00034\t]\"1\b\u000b\u0005\u0005G\u0011i\u0004E\u0003.\u0005K\u0011I#C\u0002\u0003(9\u0012aa\u00149uS>t\u0007#B\u0017f\u0005W\u0011\b\u0003B\u0017c\u0005[\u0001b!L3\u00030\te\u0002C\u00025n\u0005c\u0011)\u0004E\u0002@\u0005g!Q!\u0011\u0011C\u0002\r\u00032a\u0010B\u001c\t\u0015a\u0005E1\u0001D!\ry$1\b\u0003\u0006\u001f\u0002\u0012\ra\u0011\u0005\n\u0005\u007f\u0001\u0013\u0011!a\u0001\u0005\u0003\n1\u0001\u001f\u00131!!!\u0004A!\r\u00036\te\u0012a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$#'\u0006\u0005\u0002|\t\u001d#\u0011\nB&\t\u0015\t\u0015E1\u0001D\t\u0015a\u0015E1\u0001D\t\u0015y\u0015E1\u0001D\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011\t\u0006\u0005\u0003\u0002\f\nM\u0013\u0002\u0002B+\u0003\u001b\u0013aa\u00142kK\u000e$\b")
@InternalApi
public final class ProcessorModule<In, Out, Mat>
implements StreamLayout.AtomicModule<FlowShape<In, Out>, Mat>,
Product,
Serializable {
    private final Function0<Tuple2<Processor<In, Out>, Mat>> createProcessor;
    private final Attributes attributes;
    private final Inlet<In> inPort;
    private final Outlet<Out> outPort;
    private final FlowShape<In, Out> shape;

    public static <In, Out, Mat> Attributes $lessinit$greater$default$2() {
        return ProcessorModule$.MODULE$.$lessinit$greater$default$2();
    }

    public static <In, Out, Mat> Option<Tuple2<Function0<Tuple2<Processor<In, Out>, Mat>>, Attributes>> unapply(ProcessorModule<In, Out, Mat> x$0) {
        return ProcessorModule$.MODULE$.unapply(x$0);
    }

    public static <In, Out, Mat> Attributes apply$default$2() {
        return ProcessorModule$.MODULE$.apply$default$2();
    }

    public static <In, Out, Mat> ProcessorModule<In, Out, Mat> apply(Function0<Tuple2<Processor<In, Out>, Mat>> createProcessor, Attributes attributes) {
        return ProcessorModule$.MODULE$.apply(createProcessor, attributes);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Graph<FlowShape<In, Out>, Mat> named(String name) {
        return Graph.named$(this, name);
    }

    @Override
    public Graph<FlowShape<In, Out>, Mat> async() {
        return Graph.async$(this);
    }

    @Override
    public Graph<FlowShape<In, Out>, Mat> async(String dispatcher) {
        return Graph.async$(this, dispatcher);
    }

    @Override
    public Graph<FlowShape<In, Out>, Mat> async(String dispatcher, int inputBufferSize) {
        return Graph.async$(this, dispatcher, inputBufferSize);
    }

    @Override
    public Graph<FlowShape<In, Out>, Mat> addAttributes(Attributes attr) {
        return Graph.addAttributes$(this, attr);
    }

    @Override
    public Attributes getAttributes() {
        return Graph.getAttributes$(this);
    }

    public Function0<Tuple2<Processor<In, Out>, Mat>> createProcessor() {
        return this.createProcessor;
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public Inlet<In> inPort() {
        return this.inPort;
    }

    public Outlet<Out> outPort() {
        return this.outPort;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    public ProcessorModule<In, Out, Mat> withAttributes(Attributes attributes) {
        Attributes x$1 = attributes;
        Function0<Tuple2<Processor<In, Out>, Mat>> x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    public String toString() {
        return StringOps$.MODULE$.format$extension("ProcessorModule [%08x]", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToInteger(System.identityHashCode(this))}));
    }

    @Override
    public LinearTraversalBuilder traversalBuilder() {
        return LinearTraversalBuilder$.MODULE$.fromModule(this, this.attributes()).makeIsland(ProcessorModuleIslandTag$.MODULE$);
    }

    public <In, Out, Mat> ProcessorModule<In, Out, Mat> copy(Function0<Tuple2<Processor<In, Out>, Mat>> createProcessor, Attributes attributes) {
        return new ProcessorModule<In, Out, Mat>(createProcessor, attributes);
    }

    public <In, Out, Mat> Function0<Tuple2<Processor<In, Out>, Mat>> copy$default$1() {
        return this.createProcessor();
    }

    public <In, Out, Mat> Attributes copy$default$2() {
        return this.attributes();
    }

    @Override
    public String productPrefix() {
        return "ProcessorModule";
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
                return this.createProcessor();
            }
            case 1: {
                return this.attributes();
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
        return x$1 instanceof ProcessorModule;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "createProcessor";
            }
            case 1: {
                return "attributes";
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
        if (!(object instanceof ProcessorModule)) return false;
        boolean bl = true;
        if (!bl) return false;
        ProcessorModule processorModule = (ProcessorModule)x$1;
        Function0<Tuple2<Processor<In, Out>, Mat>> function0 = this.createProcessor();
        Function0<Tuple2<Processor<In, Out>, Mat>> function02 = processorModule.createProcessor();
        if (function0 == null) {
            if (function02 != null) {
                return false;
            }
        } else if (!function0.equals(function02)) return false;
        Attributes attributes = this.attributes();
        Attributes attributes2 = processorModule.attributes();
        if (attributes == null) {
            if (attributes2 == null) return true;
            return false;
        } else {
            if (!((Object)attributes).equals(attributes2)) return false;
            return true;
        }
    }

    public ProcessorModule(Function0<Tuple2<Processor<In, Out>, Mat>> createProcessor, Attributes attributes) {
        this.createProcessor = createProcessor;
        this.attributes = attributes;
        Graph.$init$(this);
        Product.$init$(this);
        this.inPort = Inlet$.MODULE$.apply("ProcessorModule.in");
        this.outPort = Outlet$.MODULE$.apply("ProcessorModule.out");
        this.shape = new FlowShape<In, Out>(this.inPort(), this.outPort());
    }
}

