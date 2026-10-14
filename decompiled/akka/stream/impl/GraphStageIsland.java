/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.impl.PhaseIsland;
import akka.stream.impl.PhasedFusingActorMaterializer;
import akka.stream.impl.PhasedFusingActorMaterializer$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.fusing.ActorGraphInterpreter;
import akka.stream.impl.fusing.ActorGraphInterpreter$;
import akka.stream.impl.fusing.GraphInterpreter;
import akka.stream.impl.fusing.GraphInterpreterShell;
import akka.stream.impl.fusing.GraphStageModule;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import org.reactivestreams.Publisher;
import scala.Array$;
import scala.Function1;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.math.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\t=a!\u0002\u0012$\u0005\u001dJ\u0003\u0002\u0003\u001e\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001f\t\u0011\u0001\u0003!\u0011!Q\u0001\n\u0005C\u0001\u0002\u0012\u0001\u0003\u0002\u0003\u0006I!\u0012\u0005\t!\u0002\u0011\t\u0011)A\u0005#\")a\r\u0001C\u0001O\"9Q\u000e\u0001b\u0001\n\u0013q\u0007B\u0002:\u0001A\u0003%q\u000e\u0003\u0004t\u0001\u0001\u0006I\u0001\u001e\u0005\bw\u0002\u0001\r\u0011\"\u0003}\u0011%\tY\u0002\u0001a\u0001\n\u0013\ti\u0002C\u0004\u0002*\u0001\u0001\u000b\u0015B?\t\u0013\u0005-\u0002\u00011A\u0005\n\u00055\u0002\"CA\u001b\u0001\u0001\u0007I\u0011BA\u001c\u0011!\tY\u0004\u0001Q!\n\u0005=\u0002\"CA\u001f\u0001\u0001\u0007I\u0011BA \u0011%\t\u0019\u0006\u0001a\u0001\n\u0013\t)\u0006\u0003\u0005\u0002Z\u0001\u0001\u000b\u0015BA!\u0011%\tY\u0006\u0001a\u0001\n\u0013\ti\u0006C\u0005\u0002b\u0001\u0001\r\u0011\"\u0003\u0002d!A\u0011q\r\u0001!B\u0013\ty\u0006C\u0005\u0002j\u0001\u0011\r\u0011\"\u0001\u0002l!9\u0011Q\u000e\u0001!\u0002\u0013Q\u0006bBA8\u0001\u0011\u0005\u0013\u0011\u000f\u0005\b\u0003g\u0002A\u0011IA;\u0011\u001d\ty\n\u0001C\u0001\u0003CCq!a*\u0001\t\u0003\tI\u000bC\u0004\u0002,\u0002!\t%!,\t\u000f\u0005-\u0006\u0001\"\u0011\u0002@\"9\u0011q\u001a\u0001\u0005B\u0005E\u0007bBAt\u0001\u0011\u0005\u0013\u0011\u001e\u0005\b\u0003g\u0004A\u0011IA{\u0011\u001d\t9\u0010\u0001C\u0005\u0003sDq!!@\u0001\t\u0003\nyP\u0001\tHe\u0006\u0004\bn\u0015;bO\u0016L5\u000f\\1oI*\u0011A%J\u0001\u0005S6\u0004HN\u0003\u0002'O\u000511\u000f\u001e:fC6T\u0011\u0001K\u0001\u0005C.\\\u0017mE\u0002\u0001UA\u0002\"a\u000b\u0018\u000e\u00031R\u0011!L\u0001\u0006g\u000e\fG.Y\u0005\u0003_1\u0012a!\u00118z%\u00164\u0007cA\u00193i5\t1%\u0003\u00024G\tY\u0001\u000b[1tK&\u001bH.\u00198e!\t)\u0004(D\u00017\u0015\t9T%A\u0003ti\u0006<W-\u0003\u0002:m\tyqI]1qQN#\u0018mZ3M_\u001eL7-A\nfM\u001a,7\r^5wK\u0006#HO]5ckR,7o\u0001\u0001\u0011\u0005urT\"A\u0013\n\u0005}*#AC!uiJL'-\u001e;fg\u0006aQ.\u0019;fe&\fG.\u001b>feB\u0011\u0011GQ\u0005\u0003\u0007\u000e\u0012Q\u0004\u00155bg\u0016$g)^:j]\u001e\f5\r^8s\u001b\u0006$XM]5bY&TXM]\u0001\u000bSNd\u0017M\u001c3OC6,\u0007C\u0001$N\u001d\t95\n\u0005\u0002IY5\t\u0011J\u0003\u0002Kw\u00051AH]8pizJ!\u0001\u0014\u0017\u0002\rA\u0013X\rZ3g\u0013\tquJ\u0001\u0004TiJLgn\u001a\u0006\u0003\u00192\nAb];cM2|wOR;tKJ\u00042AU+X\u001b\u0005\u0019&B\u0001+(\u0003\u0011)H/\u001b7\n\u0005Y\u001b&!C(qi&|gNV1m!\u0011Y\u0003L\u00171\n\u0005ec#!\u0003$v]\u000e$\u0018n\u001c82!\tYf,D\u0001]\u0015\ti6%\u0001\u0004gkNLgnZ\u0005\u0003?r\u0013Qc\u0012:ba\"Le\u000e^3saJ,G/\u001a:TQ\u0016dG\u000e\u0005\u0002bI6\t!M\u0003\u0002dO\u0005)\u0011m\u0019;pe&\u0011QM\u0019\u0002\t\u0003\u000e$xN\u001d*fM\u00061A(\u001b8jiz\"R\u0001[5kW2\u0004\"!\r\u0001\t\u000bi*\u0001\u0019\u0001\u001f\t\u000b\u0001+\u0001\u0019A!\t\u000b\u0011+\u0001\u0019A#\t\u000bA+\u0001\u0019A)\u0002\u001d1|w-[2BeJ\f\u0017\u0010V=qKV\tq\u000eE\u0002,aRJ!!\u001d\u0017\u0003\u000b\u0005\u0013(/Y=\u0002\u001f1|w-[2BeJ\f\u0017\u0010V=qK\u0002\na\u0001\\8hS\u000e\u001c\bcA;zi5\taO\u0003\u0002Uo*\t\u00010\u0001\u0003kCZ\f\u0017B\u0001>w\u0005%\t%O]1z\u0019&\u001cH/A\u0006d_:tWm\u0019;j_:\u001cX#A?\u0011\u0007-\u0002h\u0010E\u0002\u0000\u0003+qA!!\u0001\u0002\u00129!\u00111AA\b\u001d\u0011\t)!!\u0004\u000f\t\u0005\u001d\u00111\u0002\b\u0004\u0011\u0006%\u0011\"\u0001\u0015\n\u0005\u0019:\u0013B\u0001\u0013&\u0013\ti6%C\u0002\u0002\u0014q\u000b\u0001c\u0012:ba\"Le\u000e^3saJ,G/\u001a:\n\t\u0005]\u0011\u0011\u0004\u0002\u000b\u0007>tg.Z2uS>t'bAA\n9\u0006y1m\u001c8oK\u000e$\u0018n\u001c8t?\u0012*\u0017\u000f\u0006\u0003\u0002 \u0005\u0015\u0002cA\u0016\u0002\"%\u0019\u00111\u0005\u0017\u0003\tUs\u0017\u000e\u001e\u0005\t\u0003OQ\u0011\u0011!a\u0001{\u0006\u0019\u0001\u0010J\u0019\u0002\u0019\r|gN\\3di&|gn\u001d\u0011\u0002\u001d5\f\u0007pQ8o]\u0016\u001cG/[8ogV\u0011\u0011q\u0006\t\u0004W\u0005E\u0012bAA\u001aY\t\u0019\u0011J\u001c;\u0002%5\f\u0007pQ8o]\u0016\u001cG/[8og~#S-\u001d\u000b\u0005\u0003?\tI\u0004C\u0005\u0002(5\t\t\u00111\u0001\u00020\u0005yQ.\u0019=D_:tWm\u0019;j_:\u001c\b%\u0001\bpkR\u001cuN\u001c8fGRLwN\\:\u0016\u0005\u0005\u0005\u0003#BA\"\u0003\u001brh\u0002BA#\u0003\u0013r1\u0001SA$\u0013\u0005i\u0013bAA&Y\u00059\u0001/Y2lC\u001e,\u0017\u0002BA(\u0003#\u0012A\u0001T5ti*\u0019\u00111\n\u0017\u0002%=,HoQ8o]\u0016\u001cG/[8og~#S-\u001d\u000b\u0005\u0003?\t9\u0006C\u0005\u0002(A\t\t\u00111\u0001\u0002B\u0005yq.\u001e;D_:tWm\u0019;j_:\u001c\b%\u0001\bgk2d\u0017j\u001d7b]\u0012t\u0015-\\3\u0016\u0005\u0005}\u0003c\u0001*V\u000b\u0006\u0011b-\u001e7m\u0013Nd\u0017M\u001c3OC6,w\fJ3r)\u0011\ty\"!\u001a\t\u0013\u0005\u001d2#!AA\u0002\u0005}\u0013a\u00044vY2L5\u000f\\1oI:\u000bW.\u001a\u0011\u0002\u000bMDW\r\u001c7\u0016\u0003i\u000baa\u001d5fY2\u0004\u0013\u0001\u00028b[\u0016,\u0012!R\u0001\u0012[\u0006$XM]5bY&TX-\u0011;p[&\u001cGCBA<\u0003\u0007\u000bY\n\u0005\u0004,\u0003s\"\u0014QP\u0005\u0004\u0003wb#A\u0002+va2,'\u0007E\u0002,\u0003\u007fJ1!!!-\u0005\r\te.\u001f\u0005\b\u0003\u000bC\u0002\u0019AAD\u0003\riw\u000e\u001a\t\t\u0003\u0013\u000by)!&\u0002~9!\u00111AAF\u0013\r\tiiI\u0001\r'R\u0014X-Y7MCf|W\u000f^\u0005\u0005\u0003#\u000b\u0019J\u0001\u0007Bi>l\u0017nY'pIVdWMC\u0002\u0002\u000e\u000e\u00022!PAL\u0013\r\tI*\n\u0002\u0006'\"\f\u0007/\u001a\u0005\u0007\u0003;C\u0002\u0019\u0001\u001f\u0002\u0015\u0005$HO]5ckR,7/\u0001\u0003d_:tGc\u0001@\u0002$\"9\u0011QU\rA\u0002\u0005=\u0012\u0001B:m_R\fqa\\;u\u0007>tg\u000eF\u0001\u007f\u0003)\t7o]5h]B{'\u000f\u001e\u000b\t\u0003?\ty+!/\u0002<\"9\u0011\u0011W\u000eA\u0002\u0005M\u0016AA5o!\ri\u0014QW\u0005\u0004\u0003o+#AB%o!>\u0014H\u000fC\u0004\u0002&n\u0001\r!a\f\t\r\u0005u6\u00041\u00015\u0003\u0015awnZ5d)!\ty\"!1\u0002L\u00065\u0007bBAb9\u0001\u0007\u0011QY\u0001\u0004_V$\bcA\u001f\u0002H&\u0019\u0011\u0011Z\u0013\u0003\u000f=+H\u000fU8si\"9\u0011Q\u0015\u000fA\u0002\u0005=\u0002BBA_9\u0001\u0007A'A\bde\u0016\fG/\u001a)vE2L7\u000f[3s)\u0019\t\u0019.a9\u0002fB1\u0011Q[Ap\u0003{j!!a6\u000b\t\u0005e\u00171\\\u0001\u0010e\u0016\f7\r^5wKN$(/Z1ng*\u0011\u0011Q\\\u0001\u0004_J<\u0017\u0002BAq\u0003/\u0014\u0011\u0002U;cY&\u001c\b.\u001a:\t\u000f\u0005\rW\u00041\u0001\u0002F\"1\u0011QX\u000fA\u0002Q\nQ\u0002^1lKB+(\r\\5tQ\u0016\u0014H\u0003CA\u0010\u0003W\fi/!=\t\u000f\u0005\u0015f\u00041\u0001\u00020!9\u0011q\u001e\u0010A\u0002\u0005M\u0017!\u00039vE2L7\u000f[3s\u0011\u0019\tiJ\ba\u0001y\u0005iqN\\%tY\u0006tGMU3bIf$\"!a\b\u0002)\u0019\f\u0017\u000e\\(o\u001b&\u001c8/\u001b8h\u0011\u0006tG\r\\3s)\u0011\ty\"a?\t\r\u0005u\u0006\u00051\u00015\u0003!!xn\u0015;sS:<G#A#)\u0007\u0001\u0011\u0019\u0001\u0005\u0003\u0003\u0006\t-QB\u0001B\u0004\u0015\r\u0011IaJ\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002\u0002B\u0007\u0005\u000f\u00111\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public final class GraphStageIsland
implements PhaseIsland<GraphStageLogic> {
    private final Attributes effectiveAttributes;
    private final PhasedFusingActorMaterializer materializer;
    private final String islandName;
    private final Function1<GraphInterpreterShell, ActorRef> subflowFuser;
    private final GraphStageLogic[] logicArrayType;
    private final ArrayList<GraphStageLogic> logics;
    private GraphInterpreter.Connection[] connections;
    private int maxConnections;
    private List<GraphInterpreter.Connection> outConnections;
    private String fullIslandName;
    private final GraphInterpreterShell shell;

    private GraphStageLogic[] logicArrayType() {
        return this.logicArrayType;
    }

    private GraphInterpreter.Connection[] connections() {
        return this.connections;
    }

    private void connections_$eq(GraphInterpreter.Connection[] x$1) {
        this.connections = x$1;
    }

    private int maxConnections() {
        return this.maxConnections;
    }

    private void maxConnections_$eq(int x$1) {
        this.maxConnections = x$1;
    }

    private List<GraphInterpreter.Connection> outConnections() {
        return this.outConnections;
    }

    private void outConnections_$eq(List<GraphInterpreter.Connection> x$1) {
        this.outConnections = x$1;
    }

    private String fullIslandName() {
        return this.fullIslandName;
    }

    private void fullIslandName_$eq(String x$1) {
        this.fullIslandName = x$1;
    }

    public GraphInterpreterShell shell() {
        return this.shell;
    }

    @Override
    public String name() {
        return "Fusing GraphStages phase";
    }

    @Override
    public Tuple2<GraphStageLogic, Object> materializeAtomic(StreamLayout.AtomicModule<Shape, Object> mod, Attributes attributes) {
        Tuple2<GraphStageLogic, Object> matAndLogic;
        block0: {
            GraphStageModule stageModule = (GraphStageModule)mod;
            GraphStageWithMaterializedValue stage = stageModule.stage();
            matAndLogic = stage.createLogicAndMaterializedValue(attributes, this.materializer);
            GraphStageLogic logic = matAndLogic._1();
            logic.originalStage_$eq(OptionVal$Some$.MODULE$.apply(stage));
            logic.attributes_$eq(attributes);
            this.logics.add(logic);
            logic.stageId_$eq(this.logics.size() - 1);
            String string = this.fullIslandName();
            OptionVal$.MODULE$.None();
            String string2 = string;
            if (null != null ? !((Object)null).equals(string2) : string2 != null) break block0;
            Attributes qual$1 = logic.attributes();
            String x$1 = qual$1.nameForActorRef$default$1();
            this.fullIslandName_$eq(OptionVal$Some$.MODULE$.apply(new StringBuilder(1).append(this.islandName).append("-").append(qual$1.nameForActorRef(x$1)).toString()));
        }
        return matAndLogic;
    }

    public GraphInterpreter.Connection conn(int slot) {
        GraphInterpreter.Connection c2;
        GraphInterpreter.Connection c;
        this.maxConnections_$eq(package$.MODULE$.max(slot, this.maxConnections()));
        if (this.maxConnections() >= this.connections().length) {
            this.connections_$eq((GraphInterpreter.Connection[])Arrays.copyOf((Object[])this.connections(), this.connections().length * 2));
        }
        if ((c = this.connections()[slot]) != null) {
            return c;
        }
        this.connections()[slot] = c2 = new GraphInterpreter.Connection(0, null, null, null, null);
        return c2;
    }

    public GraphInterpreter.Connection outConn() {
        GraphInterpreter.Connection connection = new GraphInterpreter.Connection(0, null, null, null, null);
        this.outConnections_$eq(this.outConnections().$colon$colon(connection));
        return connection;
    }

    @Override
    public void assignPort(InPort in, int slot, GraphStageLogic logic) {
        GraphInterpreter.Connection connection = this.conn(slot);
        connection.inOwner_$eq(logic);
        connection.id_$eq(slot);
        connection.inHandler_$eq((InHandler)logic.handlers()[in.id()]);
        if (connection.inHandler() == null) {
            this.failOnMissingHandler(logic);
        }
        logic.portToConn()[in.id()] = connection;
    }

    @Override
    public void assignPort(OutPort out, int slot, GraphStageLogic logic) {
        GraphInterpreter.Connection connection = this.conn(slot);
        connection.outOwner_$eq(logic);
        connection.id_$eq(slot);
        connection.outHandler_$eq((OutHandler)logic.handlers()[logic.inCount() + out.id()]);
        if (connection.outHandler() == null) {
            this.failOnMissingHandler(logic);
        }
        logic.portToConn()[logic.inCount() + out.id()] = connection;
    }

    @Override
    public Publisher<Object> createPublisher(OutPort out, GraphStageLogic logic) {
        GraphInterpreter.Connection connection;
        ActorGraphInterpreter.ActorOutputBoundary boundary = new ActorGraphInterpreter.ActorOutputBoundary(this.shell(), out.toString());
        this.logics.add(boundary);
        boundary.stageId_$eq(this.logics.size() - 1);
        boundary.attributes_$eq(logic.attributes().and(Stages$DefaultAttributes$.MODULE$.outputBoundary()));
        boundary.portToConn()[boundary.in().id()] = connection = this.outConn();
        connection.inHandler_$eq((InHandler)boundary.handlers()[0]);
        connection.inOwner_$eq(boundary);
        connection.outOwner_$eq(logic);
        connection.id_$eq(-1);
        connection.outHandler_$eq((OutHandler)logic.handlers()[logic.inCount() + out.id()]);
        if (connection.outHandler() == null) {
            this.failOnMissingHandler(logic);
        }
        logic.portToConn()[logic.inCount() + out.id()] = connection;
        return boundary.publisher();
    }

    @Override
    public void takePublisher(int slot, Publisher<Object> publisher, Attributes attributes) {
        GraphInterpreter.Connection connection = this.conn(slot);
        int bufferSize = ((Attributes.InputBuffer)connection.inOwner().attributes().mandatoryAttribute(ClassTag$.MODULE$.apply(Attributes.InputBuffer.class))).max();
        ActorGraphInterpreter.BatchingActorInputBoundary boundary = new ActorGraphInterpreter.BatchingActorInputBoundary(bufferSize, this.shell(), publisher, "publisher.in");
        this.logics.add(boundary);
        boundary.stageId_$eq(this.logics.size() - 1);
        boundary.attributes_$eq(connection.inOwner().attributes().and(Stages$DefaultAttributes$.MODULE$.inputBoundary()));
        boundary.portToConn()[boundary.out().id() + boundary.inCount()] = connection;
        connection.outHandler_$eq((OutHandler)boundary.handlers()[0]);
        connection.outOwner_$eq(boundary);
    }

    @Override
    public void onIslandReady() {
        String n;
        int totalConnections = this.maxConnections() + this.outConnections().size() + 1;
        GraphInterpreter.Connection[] finalConnections = (GraphInterpreter.Connection[])Arrays.copyOf((Object[])this.connections(), totalConnections);
        List outConns = this.outConnections();
        for (int i = this.maxConnections() + 1; i < totalConnections; ++i) {
            GraphInterpreter.Connection conn = (GraphInterpreter.Connection)outConns.head();
            outConns = (List)outConns.tail();
            if (conn.inHandler() == null) {
                this.failOnMissingHandler(conn.inOwner());
            } else if (conn.outHandler() == null) {
                this.failOnMissingHandler(conn.outOwner());
            }
            finalConnections[i] = conn;
            conn.id_$eq(i);
        }
        this.shell().connections_$eq(finalConnections);
        this.shell().logics_$eq((GraphStageLogic[])this.logics.toArray((Object[])this.logicArrayType()));
        Function1<GraphInterpreterShell, ActorRef> function1 = this.subflowFuser;
        Function1<GraphInterpreterShell, ActorRef> function12 = OptionVal$Some$.MODULE$.unapply(function1);
        if (!OptionVal$.MODULE$.isEmpty$extension(function12)) {
            Function1<GraphInterpreterShell, ActorRef> fuseIntoExistingInterpreter = OptionVal$.MODULE$.get$extension(function12);
            fuseIntoExistingInterpreter.apply(this.shell());
            return;
        }
        Props props = ActorGraphInterpreter$.MODULE$.props(this.shell()).withDispatcher(((ActorAttributes.Dispatcher)this.effectiveAttributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.Dispatcher.class))).dispatcher()).withMailbox(PhasedFusingActorMaterializer$.MODULE$.Mailbox());
        String string = this.fullIslandName();
        String string2 = OptionVal$Some$.MODULE$.unapply(string);
        String actorName = !OptionVal$.MODULE$.isEmpty$extension(string2) ? (n = OptionVal$.MODULE$.get$extension(string2)) : this.islandName;
        ActorRef ref = this.materializer.actorOf(props, actorName);
        if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
            Predef$.MODULE$.println(new StringBuilder(29).append("Spawned actor [").append(ref).append("] with shell: ").append(this.shell()).toString());
            return;
        }
    }

    private void failOnMissingHandler(GraphStageLogic logic) {
        String string;
        int x$2;
        Function1<Object, Object> & Serializable x$1;
        Object qual$1 = Predef$.MODULE$.genericArrayOps(logic.handlers());
        int missingHandlerIdx = ArrayOps$.MODULE$.indexWhere$extension(qual$1, x$1 = (Function1<Object, Object> & Serializable)x$5 -> BoxesRunTime.boxToBoolean(GraphStageIsland.$anonfun$failOnMissingHandler$1(x$5)), x$2 = ArrayOps$.MODULE$.indexWhere$default$2$extension(qual$1));
        boolean isIn = missingHandlerIdx < logic.inCount();
        GraphStageWithMaterializedValue<? extends Shape, ?> graphStageWithMaterializedValue = logic.originalStage();
        GraphStageWithMaterializedValue<? extends Shape, ?> graphStageWithMaterializedValue2 = OptionVal$Some$.MODULE$.unapply(graphStageWithMaterializedValue);
        if (!OptionVal$.MODULE$.isEmpty$extension(graphStageWithMaterializedValue2)) {
            GraphStageWithMaterializedValue<Shape, ?> stage = OptionVal$.MODULE$.get$extension(graphStageWithMaterializedValue2);
            string = isIn ? new StringBuilder(10).append("in port [").append(((Shape)stage.shape()).inlets().apply(missingHandlerIdx)).append("]").toString() : new StringBuilder(10).append("out port [").append(((Shape)stage.shape()).outlets().apply(missingHandlerIdx - logic.inCount())).toString();
        } else {
            string = isIn ? new StringBuilder(13).append("in port id [").append(missingHandlerIdx).append("]").toString() : new StringBuilder(14).append("out port id [").append(missingHandlerIdx).append("]").toString();
        }
        String portLabel = string;
        throw new IllegalStateException(new StringBuilder(148).append("No handler defined in stage [").append(logic.toString()).append("] for ").append(portLabel).append(".").append(" All inlets and outlets must be assigned a handler with setHandler in the constructor of your graph stage logic.").toString());
    }

    public String toString() {
        return "GraphStagePhase";
    }

    public static final /* synthetic */ boolean $anonfun$failOnMissingHandler$1(Object x$5) {
        return x$5 == null;
    }

    public GraphStageIsland(Attributes effectiveAttributes, PhasedFusingActorMaterializer materializer, String islandName, Function1<GraphInterpreterShell, ActorRef> subflowFuser) {
        this.effectiveAttributes = effectiveAttributes;
        this.materializer = materializer;
        this.islandName = islandName;
        this.subflowFuser = subflowFuser;
        this.logicArrayType = (GraphStageLogic[])Array$.MODULE$.empty(ClassTag$.MODULE$.apply(GraphStageLogic.class));
        this.logics = new ArrayList(16);
        this.connections = new GraphInterpreter.Connection[16];
        this.maxConnections = 0;
        this.outConnections = Nil$.MODULE$;
        OptionVal$.MODULE$.None();
        this.fullIslandName = null;
        this.shell = new GraphInterpreterShell(null, null, effectiveAttributes, materializer);
    }
}

