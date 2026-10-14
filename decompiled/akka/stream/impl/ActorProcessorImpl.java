/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.AbruptTerminationException;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.impl.BatchingInputBuffer;
import akka.stream.impl.ExposedPublisher;
import akka.stream.impl.ExposedPublisherReceive;
import akka.stream.impl.Inputs;
import akka.stream.impl.Outputs;
import akka.stream.impl.Pump;
import akka.stream.impl.SimpleOutputs;
import akka.stream.impl.TransferPhase;
import akka.stream.impl.TransferState;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005%uA\u0002\u0011\"\u0011\u0003)sE\u0002\u0004*C!\u0005QE\u000b\u0005\u0006c\u0005!\taM\u0004\u0006i\u0005A\t)\u000e\u0004\u0006o\u0005A\t\t\u000f\u0005\u0006c\u0011!\t\u0001\u0013\u0005\b\u0013\u0012\t\t\u0011\"\u0011K\u0011\u001d\u0019F!!A\u0005\u0002QCq\u0001\u0017\u0003\u0002\u0002\u0013\u0005\u0011\fC\u0004`\t\u0005\u0005I\u0011\t1\t\u000f\u001d$\u0011\u0011!C\u0001Q\"9Q\u000eBA\u0001\n\u0003r\u0007bB8\u0005\u0003\u0003%\t\u0005\u001d\u0005\bc\u0012\t\t\u0011\"\u0003s\r\u0019I\u0013%!\u0001&m\"Q\u0011q\u0001\b\u0003\u0002\u0003\u0006I!!\u0003\t\rErA\u0011AA\t\u0011%\t9B\u0004b\u0001\n\u0013\tI\u0002C\u0004\u0002\u001c9\u0001\u000b\u0011B5\t\u0013\u0005uaB1A\u0005\u0012\u0005}\u0001\u0002CA\u0014\u001d\u0001\u0006I!!\t\t\u0013\u0005%bB1A\u0005\u0012\u0005-\u0002\u0002CA\u001a\u001d\u0001\u0006I!!\f\t\u000f\u0005UbB\"\u0001\u00028!9\u0011\u0011\t\b\u0005F\u0005\r\u0003bBA&\u001d\u0011\u0005\u0011q\u0007\u0005\b\u0003\u001brA\u0011CA(\u0011\u001d\t\tG\u0004C\t\u0003GBq!a\u001a\u000f\t\u0003\nI\u0007C\u0004\u0002l9!\t%!\u001c\t\u000f\u0005Ed\u0002\"\u0011\u0002j!9\u00111\u000f\b\u0005B\u0005U\u0014AE!di>\u0014\bK]8dKN\u001cxN]%na2T!AI\u0012\u0002\t%l\u0007\u000f\u001c\u0006\u0003I\u0015\naa\u001d;sK\u0006l'\"\u0001\u0014\u0002\t\u0005\\7.\u0019\t\u0003Q\u0005i\u0011!\t\u0002\u0013\u0003\u000e$xN\u001d)s_\u000e,7o]8s\u00136\u0004Hn\u0005\u0002\u0002WA\u0011AfL\u0007\u0002[)\ta&A\u0003tG\u0006d\u0017-\u0003\u00021[\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002O\u0005\u00192+\u001e2tGJL\u0007\u000f^5p]RKW.Z8viB\u0011a\u0007B\u0007\u0002\u0003\t\u00192+\u001e2tGJL\u0007\u000f^5p]RKW.Z8viN!AaK\u001d=!\ta#(\u0003\u0002<[\t9\u0001K]8ek\u000e$\bCA\u001fF\u001d\tq4I\u0004\u0002@\u00056\t\u0001I\u0003\u0002Be\u00051AH]8pizJ\u0011AL\u0005\u0003\t6\nq\u0001]1dW\u0006<W-\u0003\u0002G\u000f\na1+\u001a:jC2L'0\u00192mK*\u0011A)\f\u000b\u0002k\u0005i\u0001O]8ek\u000e$\bK]3gSb,\u0012a\u0013\t\u0003\u0019Fk\u0011!\u0014\u0006\u0003\u001d>\u000bA\u0001\\1oO*\t\u0001+\u0001\u0003kCZ\f\u0017B\u0001*N\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\tQ\u000b\u0005\u0002--&\u0011q+\f\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u00035v\u0003\"\u0001L.\n\u0005qk#aA!os\"9a\fCA\u0001\u0002\u0004)\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001b!\r\u0011WMW\u0007\u0002G*\u0011A-L\u0001\u000bG>dG.Z2uS>t\u0017B\u00014d\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005%d\u0007C\u0001\u0017k\u0013\tYWFA\u0004C_>dW-\u00198\t\u000fyS\u0011\u0011!a\u00015\u0006A\u0001.Y:i\u0007>$W\rF\u0001V\u0003!!xn\u0015;sS:<G#A&\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003M\u0004\"\u0001\u0014;\n\u0005Ul%AB(cU\u0016\u001cGo\u0005\u0004\u000fW]l\u0018\u0011\u0001\t\u0003qnl\u0011!\u001f\u0006\u0003u\u0016\nQ!Y2u_JL!\u0001`=\u0003\u000b\u0005\u001bGo\u001c:\u0011\u0005at\u0018BA@z\u00051\t5\r^8s\u0019><w-\u001b8h!\rA\u00131A\u0005\u0004\u0003\u000b\t#\u0001\u0002)v[B\f!\"\u0019;ue&\u0014W\u000f^3t!\u0011\tY!!\u0004\u000e\u0003\rJ1!a\u0004$\u0005)\tE\u000f\u001e:jEV$Xm\u001d\u000b\u0005\u0003'\t)\u0002\u0005\u0002)\u001d!9\u0011q\u0001\tA\u0002\u0005%\u0011a\u00053fEV<Gj\\4hS:<WI\\1cY\u0016$W#A5\u0002)\u0011,'-^4M_\u001e<\u0017N\\4F]\u0006\u0014G.\u001a3!\u00035\u0001(/[7befLe\u000e];ugV\u0011\u0011\u0011\u0005\t\u0004Q\u0005\r\u0012bAA\u0013C\t1\u0011J\u001c9viN\fa\u0002\u001d:j[\u0006\u0014\u00180\u00138qkR\u001c\b%\u0001\bqe&l\u0017M]=PkR\u0004X\u000f^:\u0016\u0005\u00055\u0002c\u0001\u0015\u00020%\u0019\u0011\u0011G\u0011\u0003\u000f=+H\u000f];ug\u0006y\u0001O]5nCJLx*\u001e;qkR\u001c\b%\u0001\ntk\n$\u0016.\\3pkRD\u0015M\u001c3mS:<WCAA\u001d!\u0011\tY$!\u0010\u000e\u00039I1!a\u0010|\u0005\u001d\u0011VmY3jm\u0016\fqA]3dK&4X-\u0006\u0002\u0002FA\u0019\u0001&a\u0012\n\u0007\u0005%\u0013EA\fFqB|7/\u001a3Qk\nd\u0017n\u001d5feJ+7-Z5wK\u0006i\u0011m\u0019;jm\u0016\u0014VmY3jm\u0016\fqa\u001c8FeJ|'\u000f\u0006\u0003\u0002R\u0005]\u0003c\u0001\u0017\u0002T%\u0019\u0011QK\u0017\u0003\tUs\u0017\u000e\u001e\u0005\b\u00033R\u0002\u0019AA.\u0003\u0005)\u0007cA\u001f\u0002^%\u0019\u0011qL$\u0003\u0013QC'o\\<bE2,\u0017\u0001\u00024bS2$B!!\u0015\u0002f!9\u0011\u0011L\u000eA\u0002\u0005m\u0013\u0001\u00049v[B4\u0015N\\5tQ\u0016$GCAA)\u0003)\u0001X/\u001c9GC&dW\r\u001a\u000b\u0005\u0003#\ny\u0007C\u0004\u0002Zu\u0001\r!a\u0017\u0002\u0011A|7\u000f^*u_B\f1\u0002]8tiJ+7\u000f^1siR!\u0011\u0011KA<\u0011\u001d\tIh\ba\u0001\u00037\naA]3bg>t\u0007f\u0001\b\u0002~A!\u0011qPAC\u001b\t\t\tIC\u0002\u0002\u0004\u0016\n!\"\u00198o_R\fG/[8o\u0013\u0011\t9)!!\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public abstract class ActorProcessorImpl
implements Actor,
ActorLogging,
Pump {
    private final boolean debugLoggingEnabled;
    private final Inputs primaryInputs;
    private final Outputs primaryOutputs;
    private TransferState akka$stream$impl$Pump$$transferState;
    private Function0<BoxedUnit> akka$stream$impl$Pump$$currentAction;
    private TransferPhase completedPhase;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    @Override
    public final void initialPhase(int waitForUpstream, TransferPhase andThen) {
        Pump.initialPhase$(this, waitForUpstream, andThen);
    }

    @Override
    public final void waitForUpstreams(int waitForUpstream) {
        Pump.waitForUpstreams$(this, waitForUpstream);
    }

    @Override
    public void gotUpstreamSubscription() {
        Pump.gotUpstreamSubscription$(this);
    }

    @Override
    public final void nextPhase(TransferPhase phase) {
        Pump.nextPhase$(this, phase);
    }

    @Override
    public final boolean isPumpFinished() {
        return Pump.isPumpFinished$(this);
    }

    @Override
    public final void pump() {
        Pump.pump$(this);
    }

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public TransferState akka$stream$impl$Pump$$transferState() {
        return this.akka$stream$impl$Pump$$transferState;
    }

    @Override
    public void akka$stream$impl$Pump$$transferState_$eq(TransferState x$1) {
        this.akka$stream$impl$Pump$$transferState = x$1;
    }

    @Override
    public Function0<BoxedUnit> akka$stream$impl$Pump$$currentAction() {
        return this.akka$stream$impl$Pump$$currentAction;
    }

    @Override
    public void akka$stream$impl$Pump$$currentAction_$eq(Function0<BoxedUnit> x$1) {
        this.akka$stream$impl$Pump$$currentAction = x$1;
    }

    @Override
    public final TransferPhase completedPhase() {
        return this.completedPhase;
    }

    @Override
    public final void akka$stream$impl$Pump$_setter_$completedPhase_$eq(TransferPhase x$1) {
        this.completedPhase = x$1;
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    private boolean debugLoggingEnabled() {
        return this.debugLoggingEnabled;
    }

    public Inputs primaryInputs() {
        return this.primaryInputs;
    }

    public Outputs primaryOutputs() {
        return this.primaryOutputs;
    }

    public abstract PartialFunction<Object, BoxedUnit> subTimeoutHandling();

    public final ExposedPublisherReceive receive() {
        return new ExposedPublisherReceive(this){
            private final /* synthetic */ ActorProcessorImpl $outer;

            public void receiveExposedPublisher(ExposedPublisher ep) {
                this.$outer.primaryOutputs().subreceive().apply(ep);
                this.$outer.context().become(this.$outer.activeReceive());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.activeReceive(), (Function1<Object, BoxedUnit>)((Object)new Serializable($outer){
                    private static final long serialVersionUID = 0L;
                    private final /* synthetic */ ActorProcessorImpl $outer;

                    public final void apply(Object message) {
                        this.$outer.unhandled(message);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                    }
                }));
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> activeReceive() {
        return this.primaryInputs().subreceive().orElse(this.primaryOutputs().subreceive()).orElse(this.subTimeoutHandling());
    }

    public void onError(Throwable e) {
        this.fail(e);
    }

    public void fail(Throwable e) {
        if (this.debugLoggingEnabled()) {
            this.log().debug("fail due to: {}", e.getMessage());
        }
        this.primaryInputs().cancel();
        this.primaryOutputs().error(e);
        this.context().stop(this.self());
    }

    @Override
    public void pumpFinished() {
        this.primaryInputs().cancel();
        this.primaryOutputs().complete();
        this.context().stop(this.self());
    }

    @Override
    public void pumpFailed(Throwable e) {
        this.fail(e);
    }

    @Override
    public void postStop() {
        this.primaryInputs().cancel();
        this.primaryOutputs().error(new AbruptTerminationException(this.self()));
    }

    @Override
    public void postRestart(Throwable reason) {
        Actor.postRestart$(this, reason);
        throw new IllegalStateException("This actor cannot be restarted", reason);
    }

    public ActorProcessorImpl(Attributes attributes) {
        Actor.$init$(this);
        ActorLogging.$init$(this);
        Pump.$init$(this);
        this.debugLoggingEnabled = ((ActorAttributes.DebugLogging)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.DebugLogging.class))).enabled();
        int initialInputBufferSize = ((Attributes.InputBuffer)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(Attributes.InputBuffer.class))).initial();
        this.primaryInputs = new BatchingInputBuffer(this, initialInputBufferSize){
            private final /* synthetic */ ActorProcessorImpl $outer;

            public void inputOnError(Throwable e) {
                this.$outer.onError(e);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super(initialInputBufferSize$1, $outer);
            }
        };
        this.primaryOutputs = new SimpleOutputs(this.self(), this);
        Statics.releaseFence();
    }
}

