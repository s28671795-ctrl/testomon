/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.fusing.GraphStageModule$;
import akka.stream.stage.GraphStageWithMaterializedValue;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u001db!\u0002\u0010 \u0005\u0016:\u0003\u0002C5\u0001\u0005+\u0007I\u0011\u00016\t\u0011-\u0004!\u0011#Q\u0001\n\u0001C\u0001\u0002\u001c\u0001\u0003\u0016\u0004%\t!\u001c\u0005\tc\u0002\u0011\t\u0012)A\u0005]\"A!\u000f\u0001BK\u0002\u0013\u00051\u000f\u0003\u0005z\u0001\tE\t\u0015!\u0003u\u0011\u0015Q\b\u0001\"\u0001|\u0011\u001d\t\u0019\u0001\u0001C!\u0003\u000bA\u0001\"!\u0003\u0001\t\u0003\u001a\u00131\u0002\u0005\b\u0003+\u0001A\u0011IA\f\u0011%\tI\u0003AA\u0001\n\u0003\tY\u0003C\u0005\u0002P\u0001\t\n\u0011\"\u0001\u0002R!I\u00111\f\u0001\u0012\u0002\u0013\u0005\u0011Q\f\u0005\n\u0003O\u0002\u0011\u0013!C\u0001\u0003SB\u0011\"a\u001d\u0001\u0003\u0003%\t%!\u001e\t\u0013\u0005\u0015\u0005!!A\u0005\u0002\u0005\u001d\u0005\"CAH\u0001\u0005\u0005I\u0011AAI\u0011%\t9\nAA\u0001\n\u0003\nI\nC\u0005\u0002(\u0002\t\t\u0011\"\u0001\u0002*\"I\u00111\u0017\u0001\u0002\u0002\u0013\u0005\u0013Q\u0017\u0005\n\u0003s\u0003\u0011\u0011!C!\u0003wC\u0011\"!0\u0001\u0003\u0003%\t%a0\b\u0015\u0005=w$!A\t\u0002\u0015\n\tNB\u0005\u001f?\u0005\u0005\t\u0012A\u0013\u0002T\"1!\u0010\u0007C\u0001\u0003?D\u0011\"!\u0006\u0019\u0003\u0003%)%!9\t\u0013\u0005\r\b$!A\u0005\u0002\u0006\u0015\b\"CA~1\u0005\u0005I\u0011QA\u007f\u0011%\u0011i\u0002GA\u0001\n\u0013\u0011yB\u0001\tHe\u0006\u0004\bn\u0015;bO\u0016lu\u000eZ;mK*\u0011\u0001%I\u0001\u0007MV\u001c\u0018N\\4\u000b\u0005\t\u001a\u0013\u0001B5na2T!\u0001J\u0013\u0002\rM$(/Z1n\u0015\u00051\u0013\u0001B1lW\u0006,2\u0001\u000b\"X'\u0015\u0001\u0011fL/a!\tQS&D\u0001,\u0015\u0005a\u0013!B:dC2\f\u0017B\u0001\u0018,\u0005\u0019\te.\u001f*fMB!\u0001'\u0010!W\u001d\t\t4H\u0004\u00023u9\u00111'\u000f\b\u0003iaj\u0011!\u000e\u0006\u0003m]\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002M%\u0011A%J\u0005\u0003E\rJ!\u0001P\u0011\u0002\u0019M#(/Z1n\u0019\u0006Lx.\u001e;\n\u0005yz$\u0001D!u_6L7-T8ek2,'B\u0001\u001f\"!\t\t%\t\u0004\u0001\u0005\r\r\u0003AQ1\u0001E\u0005\u0005\u0019\u0016CA#I!\tQc)\u0003\u0002HW\t9aj\u001c;iS:<'FA%N!\tQ5*D\u0001$\u0013\ta5EA\u0003TQ\u0006\u0004XmK\u0001O!\tyE+D\u0001Q\u0015\t\t&+A\u0005v]\u000eDWmY6fI*\u00111kK\u0001\u000bC:tw\u000e^1uS>t\u0017BA+Q\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\t\u0003\u0003^#a\u0001\u0017\u0001\u0005\u0006\u0004I&!A'\u0012\u0005\u0015S\u0006C\u0001\u0016\\\u0013\ta6FA\u0002B]f\u0004\"A\u000b0\n\u0005}[#a\u0002)s_\u0012,8\r\u001e\t\u0003C\u001at!A\u00193\u000f\u0005Q\u001a\u0017\"\u0001\u0017\n\u0005\u0015\\\u0013a\u00029bG.\fw-Z\u0005\u0003O\"\u0014AbU3sS\u0006d\u0017N_1cY\u0016T!!Z\u0016\u0002\u000bMD\u0017\r]3\u0016\u0003\u0001\u000baa\u001d5ba\u0016\u0004\u0013AC1uiJL'-\u001e;fgV\ta\u000e\u0005\u0002K_&\u0011\u0001o\t\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018aC1uiJL'-\u001e;fg\u0002\nQa\u001d;bO\u0016,\u0012\u0001\u001e\t\u0005k^\u0004e+D\u0001w\u0015\t\u00118%\u0003\u0002ym\nyrI]1qQN#\u0018mZ3XSRDW*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0002\rM$\u0018mZ3!\u0003\u0019a\u0014N\\5u}Q)AP`@\u0002\u0002A!Q\u0010\u0001!W\u001b\u0005y\u0002\"B5\b\u0001\u0004\u0001\u0005\"\u00027\b\u0001\u0004q\u0007\"\u0002:\b\u0001\u0004!\u0018AD<ji\"\fE\u000f\u001e:jEV$Xm\u001d\u000b\u0004_\u0005\u001d\u0001\"\u00027\t\u0001\u0004q\u0017\u0001\u0005;sCZ,'o]1m\u0005VLG\u000eZ3s+\t\ti\u0001\u0005\u0003\u0002\u0010\u0005EQ\"A\u0011\n\u0007\u0005M\u0011E\u0001\fMS:,\u0017M\u001d+sCZ,'o]1m\u0005VLG\u000eZ3s\u0003!!xn\u0015;sS:<GCAA\r!\u0011\tY\"a\t\u000f\t\u0005u\u0011q\u0004\t\u0003i-J1!!\t,\u0003\u0019\u0001&/\u001a3fM&!\u0011QEA\u0014\u0005\u0019\u0019FO]5oO*\u0019\u0011\u0011E\u0016\u0002\t\r|\u0007/_\u000b\u0007\u0003[\t\u0019$!\u0012\u0015\u0011\u0005=\u0012qIA%\u0003\u0017\u0002b! \u0001\u00022\u0005\r\u0003cA!\u00024\u001111i\u0003b\u0001\u0003k\t2!RA\u001cU\r\tI$\u0014\t\u0005\u0003w\ti$D\u0001\u0001\u0013\ra\u0015qH\u0005\u0004\u0003\u0003\u001a#!B$sCBD\u0007cA!\u0002F\u0011)\u0001l\u0003b\u00013\"A\u0011n\u0003I\u0001\u0002\u0004\t\t\u0004C\u0004m\u0017A\u0005\t\u0019\u00018\t\u0011I\\\u0001\u0013!a\u0001\u0003\u001b\u0002b!^<\u00022\u0005\r\u0013AD2paf$C-\u001a4bk2$H%M\u000b\u0007\u0003'\n9&!\u0017\u0016\u0005\u0005U#F\u0001!N\t\u0019\u0019EB1\u0001\u00026\u0011)\u0001\f\u0004b\u00013\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCBA0\u0003G\n)'\u0006\u0002\u0002b)\u0012a.\u0014\u0003\u0007\u00076\u0011\r!!\u000e\u0005\u000bak!\u0019A-\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU1\u00111NA8\u0003c*\"!!\u001c+\u0005QlEAB\"\u000f\u0005\u0004\t)\u0004B\u0003Y\u001d\t\u0007\u0011,A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003o\u0002B!!\u001f\u0002\u00046\u0011\u00111\u0010\u0006\u0005\u0003{\ny(\u0001\u0003mC:<'BAAA\u0003\u0011Q\u0017M^1\n\t\u0005\u0015\u00121P\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u0013\u00032AKAF\u0013\r\tii\u000b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u00045\u0006M\u0005\"CAK#\u0005\u0005\t\u0019AAE\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\u0014\t\u0006\u0003;\u000b\u0019KW\u0007\u0003\u0003?S1!!),\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003K\u000byJ\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAV\u0003c\u00032AKAW\u0013\r\tyk\u000b\u0002\b\u0005>|G.Z1o\u0011!\t)jEA\u0001\u0002\u0004Q\u0016A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\u001e\u00028\"I\u0011Q\u0013\u000b\u0002\u0002\u0003\u0007\u0011\u0011R\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011\u0011R\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005-\u0016\u0011\u0019\u0005\t\u0003+3\u0012\u0011!a\u00015\"\u001a\u0001!!2\u0011\t\u0005\u001d\u00171Z\u0007\u0003\u0003\u0013T!aU\u0013\n\t\u00055\u0017\u0011\u001a\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\tHe\u0006\u0004\bn\u0015;bO\u0016lu\u000eZ;mKB\u0011Q\u0010G\n\u00051%\n)\u000e\u0005\u0003\u0002X\u0006uWBAAm\u0015\u0011\tY.a \u0002\u0005%|\u0017bA4\u0002ZR\u0011\u0011\u0011\u001b\u000b\u0003\u0003o\nQ!\u00199qYf,b!a:\u0002n\u0006EH\u0003CAu\u0003g\f)0a>\u0011\ru\u0004\u00111^Ax!\r\t\u0015Q\u001e\u0003\u0006\u0007n\u0011\r\u0001\u0012\t\u0004\u0003\u0006EH!\u0002-\u001c\u0005\u0004I\u0006BB5\u001c\u0001\u0004\tY\u000fC\u0003m7\u0001\u0007a\u000e\u0003\u0004s7\u0001\u0007\u0011\u0011 \t\u0007k^\fY/a<\u0002\u000fUt\u0017\r\u001d9msV1\u0011q B\b\u0005+!BA!\u0001\u0003\u0018A)!Fa\u0001\u0003\b%\u0019!QA\u0016\u0003\r=\u0003H/[8o!!Q#\u0011\u0002B\u0007]\nE\u0011b\u0001B\u0006W\t1A+\u001e9mKN\u00022!\u0011B\b\t\u0015\u0019ED1\u0001E!\u0019)xO!\u0004\u0003\u0014A\u0019\u0011I!\u0006\u0005\u000bac\"\u0019A-\t\u0013\teA$!AA\u0002\tm\u0011a\u0001=%aA1Q\u0010\u0001B\u0007\u0005'\tAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"A!\t\u0011\t\u0005e$1E\u0005\u0005\u0005K\tYH\u0001\u0004PE*,7\r\u001e")
@InternalApi
public final class GraphStageModule<S extends Shape, M>
implements StreamLayout.AtomicModule<S, M>,
Product,
Serializable {
    private final S shape;
    private final Attributes attributes;
    private final GraphStageWithMaterializedValue<S, M> stage;

    public static <S extends Shape, M> Option<Tuple3<S, Attributes, GraphStageWithMaterializedValue<S, M>>> unapply(GraphStageModule<S, M> x$0) {
        return GraphStageModule$.MODULE$.unapply(x$0);
    }

    public static <S extends Shape, M> GraphStageModule<S, M> apply(S shape, Attributes attributes, GraphStageWithMaterializedValue<S, M> stage) {
        return GraphStageModule$.MODULE$.apply(shape, attributes, stage);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

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

    @Override
    public S shape() {
        return this.shape;
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public GraphStageWithMaterializedValue<S, M> stage() {
        return this.stage;
    }

    @Override
    public StreamLayout.AtomicModule<S, M> withAttributes(Attributes attributes) {
        if (attributes != this.attributes()) {
            return new GraphStageModule<S, M>(this.shape(), attributes, this.stage());
        }
        return this;
    }

    @Override
    public LinearTraversalBuilder traversalBuilder() {
        return LinearTraversalBuilder$.MODULE$.fromModule(this, this.attributes());
    }

    public String toString() {
        return StringOps$.MODULE$.format$extension("GraphStage(%s) [%08x]", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.stage(), BoxesRunTime.boxToInteger(System.identityHashCode(this))}));
    }

    public <S extends S, M> GraphStageModule<S, M> copy(S shape, Attributes attributes, GraphStageWithMaterializedValue<S, M> stage) {
        return new GraphStageModule<S, M>(shape, attributes, stage);
    }

    public <S extends S, M> S copy$default$1() {
        return this.shape();
    }

    public <S extends S, M> Attributes copy$default$2() {
        return this.attributes();
    }

    public <S extends S, M> GraphStageWithMaterializedValue<S, M> copy$default$3() {
        return this.stage();
    }

    @Override
    public String productPrefix() {
        return "GraphStageModule";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.shape();
            }
            case 1: {
                return this.attributes();
            }
            case 2: {
                return this.stage();
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
        return x$1 instanceof GraphStageModule;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "shape";
            }
            case 1: {
                return "attributes";
            }
            case 2: {
                return "stage";
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
        if (!(object instanceof GraphStageModule)) return false;
        boolean bl = true;
        if (!bl) return false;
        GraphStageModule graphStageModule = (GraphStageModule)x$1;
        S s2 = this.shape();
        S s3 = graphStageModule.shape();
        if (s2 == null) {
            if (s3 != null) {
                return false;
            }
        } else if (!s2.equals(s3)) return false;
        Attributes attributes = this.attributes();
        Attributes attributes2 = graphStageModule.attributes();
        if (attributes == null) {
            if (attributes2 != null) {
                return false;
            }
        } else if (!((Object)attributes).equals(attributes2)) return false;
        GraphStageWithMaterializedValue<S, M> graphStageWithMaterializedValue = this.stage();
        GraphStageWithMaterializedValue<S, M> graphStageWithMaterializedValue2 = graphStageModule.stage();
        if (graphStageWithMaterializedValue == null) {
            if (graphStageWithMaterializedValue2 == null) return true;
            return false;
        } else {
            if (!graphStageWithMaterializedValue.equals(graphStageWithMaterializedValue2)) return false;
            return true;
        }
    }

    public GraphStageModule(S shape, Attributes attributes, GraphStageWithMaterializedValue<S, M> stage) {
        this.shape = shape;
        this.attributes = attributes;
        this.stage = stage;
        Graph.$init$(this);
        Product.$init$(this);
    }
}

