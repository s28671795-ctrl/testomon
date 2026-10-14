/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.event.LoggingAdapter;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Attributes;
import akka.stream.ClosedShape;
import akka.stream.Graph;
import akka.stream.MaterializationContext;
import akka.stream.Materializer;
import akka.stream.impl.ExtendedActorMaterializer;
import akka.stream.impl.GraphStageIsland;
import akka.stream.impl.IslandTag;
import akka.stream.impl.Phase;
import akka.stream.impl.PhaseIsland;
import akka.stream.impl.PhasedFusingActorMaterializer;
import akka.stream.impl.PhasedFusingActorMaterializer$;
import akka.stream.impl.fusing.GraphInterpreterShell;
import akka.util.OptionVal$;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.Map;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ef!\u0002\f\u0018\u0001mi\u0002\u0002\u0003\u0012\u0001\u0005\u000b\u0007I\u0011\u0001\u0013\t\u0011%\u0002!\u0011!Q\u0001\n\u0015B\u0001B\u000b\u0001\u0003\u0002\u0003\u0006Ia\u000b\u0005\u0006{\u0001!\tA\u0010\u0005\b\u0005\u0002\u0011\r\u0011\"\u0001D\u0011\u00199\u0005\u0001)A\u0005\t\")\u0011\u000b\u0001C!%\")\u0011\f\u0001C!5\")\u0011\f\u0001C!Y\")q\u000f\u0001C!q\"9\u0011Q\u0004\u0001\u0005B\u0005}\u0001bBA\u0015\u0001\u0011\u0005\u00131\u0006\u0005\b\u0003k\u0001A\u0011IA\u001c\u0011\u001d\ty\u0004\u0001C!\u0003\u0003Bq!!\u0018\u0001\t\u0003\ny\u0006C\u0004\u0002h\u0001!\t%!\u001b\t\u000f\u0005E\u0004\u0001\"\u0011\u0002t!A\u00111\u0010\u0001\u0005Bm\ti\b\u0003\u0005\u0002\f\u0002!\teGAG\u0011!\ty\t\u0001C!7\u0005E\u0005bBAT\u0001\u0011\u0005\u0013\u0011\u0016\u0002\u001f'V\u0014g)^:j]\u001e\f5\r^8s\u001b\u0006$XM]5bY&TXM]%na2T!\u0001G\r\u0002\t%l\u0007\u000f\u001c\u0006\u00035m\taa\u001d;sK\u0006l'\"\u0001\u000f\u0002\t\u0005\\7.Y\n\u0003\u0001y\u0001\"a\b\u0011\u000e\u0003eI!!I\r\u0003\u00195\u000bG/\u001a:jC2L'0\u001a:\u0002\u0011\u0011,G.Z4bi\u0016\u001c\u0001!F\u0001&!\t1s%D\u0001\u0018\u0013\tAsCA\rFqR,g\u000eZ3e\u0003\u000e$xN]'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\u0018!\u00033fY\u0016<\u0017\r^3!\u00035\u0011XmZ5ti\u0016\u00148\u000b[3mYB!AfL\u00198\u001b\u0005i#\"\u0001\u0018\u0002\u000bM\u001c\u0017\r\\1\n\u0005Aj#!\u0003$v]\u000e$\u0018n\u001c82!\t\u0011T'D\u00014\u0015\t!t#\u0001\u0004gkNLgnZ\u0005\u0003mM\u0012Qc\u0012:ba\"Le\u000e^3saJ,G/\u001a:TQ\u0016dG\u000e\u0005\u00029w5\t\u0011H\u0003\u0002;7\u0005)\u0011m\u0019;pe&\u0011A(\u000f\u0002\t\u0003\u000e$xN\u001d*fM\u00061A(\u001b8jiz\"2a\u0010!B!\t1\u0003\u0001C\u0003#\t\u0001\u0007Q\u0005C\u0003+\t\u0001\u00071&\u0001\btk\n4Uo]5oOBC\u0017m]3\u0016\u0003\u0011\u00132!\u0012%L\r\u00111e\u0001\u0001#\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0002\u001fM,(MR;tS:<\u0007\u000b[1tK\u0002\u0002\"\u0001L%\n\u0005)k#AB!osJ+g\rE\u0002'\u0019:K!!T\f\u0003\u000bAC\u0017m]3\u0011\u00051z\u0015B\u0001).\u0005\r\te._\u0001\u0011Kb,7-\u001e;j_:\u001cuN\u001c;fqR,\u0012a\u0015\t\u0003)^k\u0011!\u0016\u0006\u0003-6\n!bY8oGV\u0014(/\u001a8u\u0013\tAVK\u0001\rFq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0016CXmY;u_J\f1\"\\1uKJL\u0017\r\\5{KV\u00111L\u0018\u000b\u00039\u0012\u0004\"!\u00180\r\u0001\u0011)q\f\u0003b\u0001A\n\u0019Q*\u0019;\u0012\u0005\u0005t\u0005C\u0001\u0017c\u0013\t\u0019WFA\u0004O_RD\u0017N\\4\t\u000b\u0015D\u0001\u0019\u00014\u0002\u0011I,hN\\1cY\u0016\u0004BaH4j9&\u0011\u0001.\u0007\u0002\u0006\u000fJ\f\u0007\u000f\u001b\t\u0003?)L!a[\r\u0003\u0017\rcwn]3e'\"\f\u0007/Z\u000b\u0003[>$2A\u001c9s!\tiv\u000eB\u0003`\u0013\t\u0007\u0001\rC\u0003f\u0013\u0001\u0007\u0011\u000f\u0005\u0003 O&t\u0007\"B:\n\u0001\u0004!\u0018!\u00053fM\u0006,H\u000e^!uiJL'-\u001e;fgB\u0011q$^\u0005\u0003mf\u0011!\"\u0011;ue&\u0014W\u000f^3t\u00031\u00198\r[3ek2,wJ\\2f)\u0011IH0!\u0003\u0011\u0005aR\u0018BA>:\u0005-\u0019\u0015M\\2fY2\f'\r\\3\t\u000buT\u0001\u0019\u0001@\u0002\u000b\u0011,G.Y=\u0011\u0007}\f)!\u0004\u0002\u0002\u0002)\u0019\u00111A+\u0002\u0011\u0011,(/\u0019;j_:LA!a\u0002\u0002\u0002\tqa)\u001b8ji\u0016$UO]1uS>t\u0007bBA\u0006\u0015\u0001\u0007\u0011QB\u0001\u0005i\u0006\u001c8\u000e\u0005\u0003\u0002\u0010\u0005eQBAA\t\u0015\u0011\t\u0019\"!\u0006\u0002\t1\fgn\u001a\u0006\u0003\u0003/\tAA[1wC&!\u00111DA\t\u0005!\u0011VO\u001c8bE2,\u0017AF:dQ\u0016$W\u000f\\3XSRDg)\u001b=fI\u0012+G.Y=\u0015\u000fe\f\t#!\n\u0002(!1\u00111E\u0006A\u0002y\fA\"\u001b8ji&\fG\u000eR3mCfDQ!`\u0006A\u0002yDq!a\u0003\f\u0001\u0004\ti!A\ntG\",G-\u001e7f\u0003R4\u0015\u000e_3e%\u0006$X\rF\u0004z\u0003[\ty#a\r\t\r\u0005\rB\u00021\u0001\u007f\u0011\u0019\t\t\u0004\u0004a\u0001}\u0006A\u0011N\u001c;feZ\fG\u000eC\u0004\u0002\f1\u0001\r!!\u0004\u0002)M\u001c\u0007.\u001a3vY\u0016\u0004VM]5pI&\u001c\u0017\r\u001c7z)\u001dI\u0018\u0011HA\u001e\u0003{Aa!a\t\u000e\u0001\u0004q\bBBA\u0019\u001b\u0001\u0007a\u0010C\u0004\u0002\f5\u0001\r!!\u0004\u0002\u001d]LG\u000f\u001b(b[\u0016\u0004&/\u001a4jqR\u0019q(a\u0011\t\u000f\u0005\u0015c\u00021\u0001\u0002H\u0005!a.Y7f!\u0011\tI%a\u0016\u000f\t\u0005-\u00131\u000b\t\u0004\u0003\u001bjSBAA(\u0015\r\t\tfI\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005US&\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u00033\nYF\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003+j\u0013\u0001C:ikR$wn\u001e8\u0015\u0005\u0005\u0005\u0004c\u0001\u0017\u0002d%\u0019\u0011QM\u0017\u0003\tUs\u0017\u000e^\u0001\u000bSN\u001c\u0006.\u001e;e_^tWCAA6!\ra\u0013QN\u0005\u0004\u0003_j#a\u0002\"p_2,\u0017M\\\u0001\u0007gf\u001cH/Z7\u0016\u0005\u0005U\u0004c\u0001\u001d\u0002x%\u0019\u0011\u0011P\u001d\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0001\u0007Y><w-\u001a:\u0016\u0005\u0005}\u0004\u0003BAA\u0003\u000fk!!a!\u000b\u0007\u0005\u00155$A\u0003fm\u0016tG/\u0003\u0003\u0002\n\u0006\r%A\u0004'pO\u001eLgnZ!eCB$XM]\u0001\u000bgV\u0004XM\u001d<jg>\u0014X#A\u001c\u0002\u000f\u0005\u001cGo\u001c:PMR)q'a%\u0002\u001e\"9\u0011Q\u0013\u000bA\u0002\u0005]\u0015aB2p]R,\u0007\u0010\u001e\t\u0004?\u0005e\u0015bAAN3\t1R*\u0019;fe&\fG.\u001b>bi&|gnQ8oi\u0016DH\u000fC\u0004\u0002 R\u0001\r!!)\u0002\u000bA\u0014x\u000e]:\u0011\u0007a\n\u0019+C\u0002\u0002&f\u0012Q\u0001\u0015:paN\f\u0001b]3ui&twm]\u000b\u0003\u0003W\u00032aHAW\u0013\r\ty+\u0007\u0002\u001a\u0003\u000e$xN]'bi\u0016\u0014\u0018.\u00197ju\u0016\u00148+\u001a;uS:<7\u000f")
public class SubFusingActorMaterializerImpl
extends Materializer {
    private final ExtendedActorMaterializer delegate;
    public final Function1<GraphInterpreterShell, ActorRef> akka$stream$impl$SubFusingActorMaterializerImpl$$registerShell;
    private final Phase<Object> subFusingPhase;

    public ExtendedActorMaterializer delegate() {
        return this.delegate;
    }

    public Phase<Object> subFusingPhase() {
        return this.subFusingPhase;
    }

    @Override
    public ExecutionContextExecutor executionContext() {
        return this.delegate().executionContext();
    }

    @Override
    public <Mat> Mat materialize(Graph<ClosedShape, Mat> runnable2) {
        ExtendedActorMaterializer extendedActorMaterializer = this.delegate();
        if (extendedActorMaterializer instanceof PhasedFusingActorMaterializer) {
            PhasedFusingActorMaterializer phasedFusingActorMaterializer = (PhasedFusingActorMaterializer)extendedActorMaterializer;
            return this.materialize(runnable2, phasedFusingActorMaterializer.defaultAttributes());
        }
        throw new IllegalStateException(new StringBuilder(82).append("SubFusing only supported by [PhasedFusingActorMaterializer], ").append("yet was used with [").append(extendedActorMaterializer.getClass().getName()).append("]!").toString());
    }

    @Override
    public <Mat> Mat materialize(Graph<ClosedShape, Mat> runnable2, Attributes defaultAttributes) {
        if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
            Predef$.MODULE$.println(new StringBuilder(26).append("Using [").append(this.getClass().getSimpleName()).append("] to materialize [").append(runnable2).append("]").toString());
        }
        Map<IslandTag, Phase<Object>> phases = PhasedFusingActorMaterializer$.MODULE$.DefaultPhases();
        return this.delegate().materialize(runnable2, defaultAttributes, this.subFusingPhase(), phases);
    }

    @Override
    public Cancellable scheduleOnce(FiniteDuration delay, Runnable task) {
        return this.delegate().scheduleOnce(delay, task);
    }

    @Override
    public Cancellable scheduleWithFixedDelay(FiniteDuration initialDelay, FiniteDuration delay, Runnable task) {
        return this.delegate().scheduleWithFixedDelay(initialDelay, delay, task);
    }

    @Override
    public Cancellable scheduleAtFixedRate(FiniteDuration initialDelay, FiniteDuration interval, Runnable task) {
        return this.delegate().scheduleAtFixedRate(initialDelay, interval, task);
    }

    @Override
    public Cancellable schedulePeriodically(FiniteDuration initialDelay, FiniteDuration interval, Runnable task) {
        return this.scheduleAtFixedRate(initialDelay, interval, task);
    }

    @Override
    public SubFusingActorMaterializerImpl withNamePrefix(String name) {
        return new SubFusingActorMaterializerImpl(this.delegate().withNamePrefix(name), this.akka$stream$impl$SubFusingActorMaterializerImpl$$registerShell);
    }

    @Override
    public void shutdown() {
        this.delegate().shutdown();
    }

    @Override
    public boolean isShutdown() {
        return this.delegate().isShutdown();
    }

    @Override
    public ActorSystem system() {
        return this.delegate().system();
    }

    @Override
    public LoggingAdapter logger() {
        return this.delegate().logger();
    }

    @Override
    public ActorRef supervisor() {
        return this.delegate().supervisor();
    }

    @Override
    public ActorRef actorOf(MaterializationContext context, Props props) {
        return this.delegate().actorOf(context, props);
    }

    @Override
    public ActorMaterializerSettings settings() {
        return this.delegate().settings();
    }

    public SubFusingActorMaterializerImpl(ExtendedActorMaterializer delegate, Function1<GraphInterpreterShell, ActorRef> registerShell) {
        this.delegate = delegate;
        this.akka$stream$impl$SubFusingActorMaterializerImpl$$registerShell = registerShell;
        this.subFusingPhase = new Phase<Object>(this){
            private final /* synthetic */ SubFusingActorMaterializerImpl $outer;

            public PhaseIsland<Object> apply(ActorMaterializerSettings settings, Attributes attributes, PhasedFusingActorMaterializer materializer, String islandName) {
                return new GraphStageIsland(attributes, materializer, islandName, OptionVal$.MODULE$.apply(this.$outer.akka$stream$impl$SubFusingActorMaterializerImpl$$registerShell));
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }
}

