/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.Shape;
import akka.stream.impl.TraversalBuilder;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.impl.fusing.GraphStageModule;
import akka.stream.scaladsl.GenericGraphWithChangedAttributes;
import akka.stream.stage.GraphStageLogic;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}4QAC\u0006\u0002\u0002IAQ\u0001\r\u0001\u0005\u0002EBa\u0001\u000e\u0001\u0005\u0002=)\u0004\"\u0002\u001b\u0001\r\u0003!\u0006\"B4\u0001\t#A\u0007\"C5\u0001\u0001\u0004\u0005\r\u0011\"\u0003k\u0011%\t\b\u00011AA\u0002\u0013%!\u000fC\u0005y\u0001\u0001\u0007\t\u0011)Q\u0005W\"1\u0011\u0010\u0001C#\u001f)DQa\u001f\u0001\u0005Fq\u0014qd\u0012:ba\"\u001cF/Y4f/&$\b.T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f\u0015\taQ\"A\u0003ti\u0006<WM\u0003\u0002\u000f\u001f\u000511\u000f\u001e:fC6T\u0011\u0001E\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0007M\u0001#fE\u0002\u0001)i\u0001\"!\u0006\r\u000e\u0003YQ\u0011aF\u0001\u0006g\u000e\fG.Y\u0005\u00033Y\u0011a!\u00118z%\u00164\u0007\u0003B\u000e\u001d=%j\u0011!D\u0005\u0003;5\u0011Qa\u0012:ba\"\u0004\"a\b\u0011\r\u0001\u00111\u0011\u0005\u0001CC\u0002\t\u0012\u0011aU\t\u0003G\u0019\u0002\"!\u0006\u0013\n\u0005\u00152\"a\u0002(pi\"Lgn\u001a\t\u00037\u001dJ!\u0001K\u0007\u0003\u000bMC\u0017\r]3\u0011\u0005}QCAB\u0016\u0001\t\u000b\u0007AFA\u0001N#\t\u0019S\u0006\u0005\u0002\u0016]%\u0011qF\u0006\u0002\u0004\u0003:L\u0018A\u0002\u001fj]&$h\bF\u00013!\u0011\u0019\u0004AH\u0015\u000e\u0003-\tqd\u0019:fCR,Gj\\4jG\u0006sG-T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f)\r1D(\u0011\t\u0005+]J\u0014&\u0003\u00029-\t1A+\u001e9mKJ\u0002\"a\r\u001e\n\u0005mZ!aD$sCBD7\u000b^1hK2{w-[2\t\u000bu\u0012\u0001\u0019\u0001 \u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0011\u0005my\u0014B\u0001!\u000e\u0005)\tE\u000f\u001e:jEV$Xm\u001d\u0005\u0006\u0005\n\u0001\raQ\u0001\r[\u0006$XM]5bY&TXM\u001d\t\u00037\u0011K!!R\u0007\u0003\u00195\u000bG/\u001a:jC2L'0\u001a:)\u0005\u0005;\u0005C\u0001%L\u001b\u0005I%B\u0001&\u0010\u0003\u0011)H/\u001b7\n\u00051K%AB;okN,G\r\u000b\u0002\u0003\u001dB\u0011qJU\u0007\u0002!*\u0011\u0011kD\u0001\u000bC:tw\u000e^1uS>t\u0017BA*Q\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0015\u0005Y*\u0006\"B\u001f\u0004\u0001\u0004q\u0004fA\u0002XMB\u0019Q\u0003\u0017.\n\u0005e3\"A\u0002;ie><8\u000f\u0005\u0002\\G:\u0011A,\u0019\b\u0003;\u0002l\u0011A\u0018\u0006\u0003?F\ta\u0001\u0010:p_Rt\u0014\"A\f\n\u0005\t4\u0012a\u00029bG.\fw-Z\u0005\u0003I\u0016\u0014\u0011\"\u0012=dKB$\u0018n\u001c8\u000b\u0005\t42%\u0001.\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001?\u0003EyFO]1wKJ\u001c\u0018\r\u001c\"vS2$WM]\u000b\u0002WB\u0011An\\\u0007\u0002[*\u0011a.D\u0001\u0005S6\u0004H.\u0003\u0002q[\n\u0001BK]1wKJ\u001c\u0018\r\u001c\"vS2$WM]\u0001\u0016?R\u0014\u0018M^3sg\u0006d')^5mI\u0016\u0014x\fJ3r)\t\u0019h\u000f\u0005\u0002\u0016i&\u0011QO\u0006\u0002\u0005+:LG\u000fC\u0004x\r\u0005\u0005\t\u0019A6\u0002\u0007a$\u0013'\u0001\n`iJ\fg/\u001a:tC2\u0014U/\u001b7eKJ\u0004\u0013\u0001\u0005;sCZ,'o]1m\u0005VLG\u000eZ3sQ\tAa*\u0001\bxSRD\u0017\t\u001e;sS\n,H/Z:\u0015\u0005ii\b\"\u0002@\n\u0001\u0004q\u0014\u0001B1uiJ\u0004")
public abstract class GraphStageWithMaterializedValue<S extends Shape, M>
implements Graph<S, M> {
    private TraversalBuilder _traversalBuilder;

    @Override
    public Graph<S, M> named(String name) {
        return Graph.named$(this, name);
    }

    @Override
    public Graph<S, M> async() {
        return Graph.async$(this);
    }

    @Override
    public Graph<S, M> async(String dispatcher) {
        return Graph.async$(this, dispatcher);
    }

    @Override
    public Graph<S, M> async(String dispatcher, int inputBufferSize) {
        return Graph.async$(this, dispatcher, inputBufferSize);
    }

    @Override
    public Graph<S, M> addAttributes(Attributes attr) {
        return Graph.addAttributes$(this, attr);
    }

    @Override
    public Attributes getAttributes() {
        return Graph.getAttributes$(this);
    }

    @InternalApi
    public Tuple2<GraphStageLogic, M> createLogicAndMaterializedValue(Attributes inheritedAttributes, Materializer materializer) {
        return this.createLogicAndMaterializedValue(inheritedAttributes);
    }

    public abstract Tuple2<GraphStageLogic, M> createLogicAndMaterializedValue(Attributes var1) throws Exception;

    public Attributes initialAttributes() {
        return Attributes$.MODULE$.none();
    }

    private TraversalBuilder _traversalBuilder() {
        return this._traversalBuilder;
    }

    private void _traversalBuilder_$eq(TraversalBuilder x$1) {
        this._traversalBuilder = x$1;
    }

    @Override
    @InternalApi
    public final TraversalBuilder traversalBuilder() {
        block0: {
            if (this._traversalBuilder() != null) break block0;
            Attributes attr = this.initialAttributes();
            this._traversalBuilder_$eq(TraversalBuilder$.MODULE$.atomic(new GraphStageModule<Shape, Object>((Shape)this.shape(), attr, this), attr));
        }
        return this._traversalBuilder();
    }

    @Override
    public final Graph<S, M> withAttributes(Attributes attr) {
        return new GenericGraphWithChangedAttributes(this.shape(), this.traversalBuilder(), attr);
    }

    public GraphStageWithMaterializedValue() {
        Graph.$init$(this);
    }
}

