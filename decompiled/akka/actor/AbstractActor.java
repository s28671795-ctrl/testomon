/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractActor$;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.japi.pf.ReceiveBuilder;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.JavaDurationConverters$ScalaDurationOps$;
import java.time.Duration;
import java.util.Optional;
import scala.Option;
import scala.PartialFunction;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionForJava8$;
import scala.compat.java8.OptionConverters$RichOptionalGeneric$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t%q!B\u0014)\u0011\u0003ic!B\u0018)\u0011\u0003\u0001\u0004\"B\u001c\u0002\t\u0003Ad\u0001B\u001d\u0002\u0005iB\u0001bO\u0002\u0003\u0006\u0004%\t\u0001\u0010\u0005\t\u0013\u000e\u0011\t\u0011)A\u0005{!)qg\u0001C\u0001\u0015\")aj\u0001C\u0001\u001f\"9!+\u0001b\u0001\n\u000b\u0019\u0006B\u0002+\u0002A\u000351JB\u0004V\u0003A\u0005\u0019\u0011\u0001,\t\u000beSA\u0011\u0001.\t\u000bySa\u0011A0\t\u000b\rTa\u0011\u00013\t\u000b!TA\u0011A0\t\u000b%Ta\u0011\u00016\t\u000bMTa\u0011\u0001;\t\r\u0005E!B\"\u0001`\u0011\u001d\t\u0019B\u0003D\u0001\u0003+Aq!!\b\u000b\r\u0003\ty\u0002C\u0004\u0002.)!\t!a\f\t\u000f\u00055\"\u0002\"\u0001\u00026!9\u00111\t\u0006\u0005\u0002\u0005\u0015\u0003bBA*\u0015\u0011\u0005\u0011Q\u000b\u0005\u0007\u00037RA\u0011\u0001.\u0007\r=B\u0013\u0011AA6\u0011\u00199\u0014\u0004\"\u0001\u0002t!9\u0011qO\r\u0005\u0002\u0005e\u0004\"\u00020\u001a\t\u0003y\u0006\"\u00025\u001a\t\u0003y\u0006bBA@3\u0011\u0005\u0013\u0011\u0011\u0005\u0007\u0003\u0013KB\u0011\t.\t\r\u0005\u001d\u0016\u0004\"\u0011[\u0011\u001d\tY+\u0007C!\u0003[Cq!a+\u001a\t\u0003\t9\u000eC\u0004\u0002bf!\t%a9\t\u000f\u0005%\u0018D\"\u0001\u0002l\"9\u0011q^\r\u0005B\u0005E\bbBA{3\u0011\u0015\u0011q_\u0001\u000e\u0003\n\u001cHO]1di\u0006\u001bGo\u001c:\u000b\u0005%R\u0013!B1di>\u0014(\"A\u0016\u0002\t\u0005\\7.Y\u0002\u0001!\tq\u0013!D\u0001)\u00055\t%m\u001d;sC\u000e$\u0018i\u0019;peN\u0011\u0011!\r\t\u0003eUj\u0011a\r\u0006\u0002i\u0005)1oY1mC&\u0011ag\r\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005i#a\u0002*fG\u0016Lg/Z\n\u0003\u0007E\n\u0011b\u001c8NKN\u001c\u0018mZ3\u0016\u0003u\u0002BA\r A\u0007&\u0011qh\r\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0011!'Q\u0005\u0003\u0005N\u00121!\u00118z!\t!u)D\u0001F\u0015\t15'A\u0004sk:$\u0018.\\3\n\u0005!+%!\u0003\"pq\u0016$WK\\5u\u0003)yg.T3tg\u0006<W\r\t\u000b\u0003\u00176\u0003\"\u0001T\u0002\u000e\u0003\u0005AQa\u000f\u0004A\u0002u\naa\u001c:FYN,GCA&Q\u0011\u0015\tv\u00011\u0001L\u0003\u0015yG\u000f[3s\u00035)W\u000e\u001d;z\u0005\u0016D\u0017M^5peV\t1*\u0001\bf[B$\u0018PQ3iCZLwN\u001d\u0011\u0003\u0019\u0005\u001bGo\u001c:D_:$X\r\u001f;\u0014\u0007)\tt\u000b\u0005\u0002/1&\u0011Q\u000bK\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003m\u0003\"A\r/\n\u0005u\u001b$\u0001B+oSR\fqaZ3u'\u0016dg\rF\u0001a!\tq\u0013-\u0003\u0002cQ\tA\u0011i\u0019;peJ+g-\u0001\u0005hKR\u0004&o\u001c9t)\u0005)\u0007C\u0001\u0018g\u0013\t9\u0007FA\u0003Qe>\u00048/A\u0005hKR\u001cVM\u001c3fe\u0006Yq-\u001a;DQ&dGM]3o)\u0005Y\u0007c\u00017rA6\tQN\u0003\u0002o_\u0006!A.\u00198h\u0015\u0005\u0001\u0018\u0001\u00026bm\u0006L!A]7\u0003\u0011%#XM]1cY\u0016\f\u0011BZ5oI\u000eC\u0017\u000e\u001c3\u0015\u0005U\\\bc\u0001<zA6\tqO\u0003\u0002y_\u0006!Q\u000f^5m\u0013\tQxO\u0001\u0005PaRLwN\\1m\u0011\u0015a\b\u00031\u0001~\u0003\u0011q\u0017-\\3\u0011\u0007y\fYAD\u0002\u0000\u0003\u000f\u00012!!\u00014\u001b\t\t\u0019AC\u0002\u0002\u00061\na\u0001\u0010:p_Rt\u0014bAA\u0005g\u00051\u0001K]3eK\u001aLA!!\u0004\u0002\u0010\t11\u000b\u001e:j]\u001eT1!!\u00034\u0003%9W\r\u001e)be\u0016tG/A\u0005hKR\u001c\u0016p\u001d;f[R\u0011\u0011q\u0003\t\u0004]\u0005e\u0011bAA\u000eQ\tY\u0011i\u0019;peNK8\u000f^3n\u000359W\r\u001e#jgB\fGo\u00195feR\u0011\u0011\u0011\u0005\t\u0005\u0003G\tI#\u0004\u0002\u0002&)\u0019\u0011qE\u001a\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0003\u0002,\u0005\u0015\"\u0001G#yK\u000e,H/[8o\u0007>tG/\u001a=u\u000bb,7-\u001e;pe\u00061!-Z2p[\u0016$2aWA\u0019\u0011\u0019\t\u0019\u0004\u0006a\u0001\u0017\u0006A!-\u001a5bm&|'\u000fF\u0003\\\u0003o\tI\u0004\u0003\u0004\u00024U\u0001\ra\u0013\u0005\b\u0003w)\u0002\u0019AA\u001f\u0003)!\u0017n]2be\u0012|E\u000e\u001a\t\u0004e\u0005}\u0012bAA!g\t9!i\\8mK\u0006t\u0017!E4fiJ+7-Z5wKRKW.Z8viR\u0011\u0011q\t\t\u0005\u0003\u0013\ny%\u0004\u0002\u0002L)\u0019\u0011QJ8\u0002\tQLW.Z\u0005\u0005\u0003#\nYE\u0001\u0005EkJ\fG/[8o\u0003E\u0019X\r\u001e*fG\u0016Lg/\u001a+j[\u0016|W\u000f\u001e\u000b\u00047\u0006]\u0003bBA-/\u0001\u0007\u0011qI\u0001\bi&lWm\\;u\u0003Q\u0019\u0017M\\2fYJ+7-Z5wKRKW.Z8vi\"\u001a!\"a\u0018\u0011\t\u0005\u0005\u0014qM\u0007\u0003\u0003GR1!!\u001a+\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003S\n\u0019G\u0001\u0007E_:{G/\u00138iKJLGo\u0005\u0003\u001ac\u00055\u0004c\u0001\u0018\u0002p%\u0019\u0011\u0011\u000f\u0015\u0003\u000b\u0005\u001bGo\u001c:\u0015\u0005\u0005U\u0004C\u0001\u0018\u001a\u0003)9W\r^\"p]R,\u0007\u0010\u001e\u000b\u0003\u0003w\u00022!! \u000b\u001d\tq\u0003!\u0001\ntkB,'O^5t_J\u001cFO]1uK\u001eLXCAAB!\rq\u0013QQ\u0005\u0004\u0003\u000fC#AE*va\u0016\u0014h/[:peN#(/\u0019;fOf\f\u0001\u0002\u001d:f'R\f'\u000f\u001e\u0015\u0006?\u00055\u0015Q\u0015\t\u0006e\u0005=\u00151S\u0005\u0004\u0003#\u001b$A\u0002;ie><8\u000f\u0005\u0003\u0002\u0016\u0006}e\u0002BAL\u00037sA!!\u0001\u0002\u001a&\tA'C\u0002\u0002\u001eN\nq\u0001]1dW\u0006<W-\u0003\u0003\u0002\"\u0006\r&!C#yG\u0016\u0004H/[8o\u0015\r\tijM\u0012\u0003\u0003'\u000b\u0001\u0002]8tiN#x\u000e\u001d\u0015\u0006A\u00055\u0015QU\u0001\u000baJ,'+Z:uCJ$H#B.\u00020\u0006e\u0006bBAYC\u0001\u0007\u00111W\u0001\u0007e\u0016\f7o\u001c8\u0011\t\u0005U\u0015QW\u0005\u0005\u0003o\u000b\u0019KA\u0005UQJ|w/\u00192mK\"9\u00111X\u0011A\u0002\u0005u\u0016aB7fgN\fw-\u001a\t\u0005e\u0005}\u0006)C\u0002\u0002BN\u0012aa\u00149uS>t\u0007&B\u0011\u0002\u000e\u0006\u0015\u0006fC\u0011\u0002H\u0006m\u0016QZAi\u0003'\u00042AMAe\u0013\r\tYm\r\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017EAAh\u0003\u0015{e/\u001a:sS\u0012,\u0007\u0005\u001d:f%\u0016\u001cH/\u0019:uA]LG\u000f\u001b\u0011nKN\u001c\u0018mZ3!a\u0006\u0014\u0018-\\3uKJ\u0004s/\u001b;iA=\u0003H/[8oC2\u0004C/\u001f9fA%t7\u000f^3bI\u0006)1/\u001b8dK\u0006\u0012\u0011Q[\u0001\u0006e9*d\u0006\r\u000b\u00067\u0006e\u00171\u001c\u0005\b\u0003c\u0013\u0003\u0019AAZ\u0011\u001d\tYL\ta\u0001\u0003;\u00042A^=AQ\u0015\u0011\u0013QRAS\u0003-\u0001xn\u001d;SKN$\u0018M\u001d;\u0015\u0007m\u000b)\u000fC\u0004\u00022\u000e\u0002\r!a-)\u000b\r\ni)!*\u0002\u001b\r\u0014X-\u0019;f%\u0016\u001cW-\u001b<f)\t\ti\u000fE\u0002\u0002~\r\tqA]3dK&4X-\u0006\u0002\u0002tB!!G\u0010!\\\u00039\u0011XmY3jm\u0016\u0014U/\u001b7eKJ$\"!!?\u0011\t\u0005m(QA\u0007\u0003\u0003{TA!a@\u0003\u0002\u0005\u0011\u0001O\u001a\u0006\u0004\u0005\u0007Q\u0013\u0001\u00026ba&LAAa\u0002\u0002~\nq!+Z2fSZ,')^5mI\u0016\u0014\b")
public abstract class AbstractActor
implements Actor {
    private akka.actor.ActorContext context;
    private ActorRef self;

    public static Receive emptyBehavior() {
        return AbstractActor$.MODULE$.emptyBehavior();
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
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public akka.actor.ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(akka.actor.ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    public ActorContext getContext() {
        return (ActorContext)this.context();
    }

    public ActorRef getSelf() {
        return this.self();
    }

    public ActorRef getSender() {
        return this.sender();
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
    public void postStop() throws Exception {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        this.preRestart(reason, OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(OptionConverters$.MODULE$.RichOptionForJava8(message)));
    }

    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
        Actor.preRestart$(this, reason, OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(message)));
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    public abstract Receive createReceive();

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return this.createReceive().onMessage();
    }

    public final ReceiveBuilder receiveBuilder() {
        return ReceiveBuilder.create();
    }

    public AbstractActor() {
        Actor.$init$(this);
        Statics.releaseFence();
    }

    @DoNotInherit
    public static interface ActorContext
    extends akka.actor.ActorContext {
        public ActorRef getSelf();

        public Props getProps();

        public static /* synthetic */ ActorRef getSender$(ActorContext $this) {
            return $this.getSender();
        }

        default public ActorRef getSender() {
            return this.sender();
        }

        public Iterable<ActorRef> getChildren();

        public Optional<ActorRef> findChild(String var1);

        public ActorRef getParent();

        public ActorSystem getSystem();

        public ExecutionContextExecutor getDispatcher();

        public static /* synthetic */ void become$(ActorContext $this, Receive behavior) {
            $this.become(behavior);
        }

        default public void become(Receive behavior) {
            this.become(behavior, true);
        }

        public static /* synthetic */ void become$(ActorContext $this, Receive behavior, boolean discardOld) {
            $this.become(behavior, discardOld);
        }

        default public void become(Receive behavior, boolean discardOld) {
            this.become(behavior.onMessage(), discardOld);
        }

        public static /* synthetic */ Duration getReceiveTimeout$(ActorContext $this) {
            return $this.getReceiveTimeout();
        }

        default public Duration getReceiveTimeout() {
            return JavaDurationConverters$ScalaDurationOps$.MODULE$.asJava$extension(JavaDurationConverters$.MODULE$.ScalaDurationOps(this.receiveTimeout()));
        }

        public static /* synthetic */ void setReceiveTimeout$(ActorContext $this, Duration timeout2) {
            $this.setReceiveTimeout(timeout2);
        }

        default public void setReceiveTimeout(Duration timeout2) {
            this.setReceiveTimeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)));
        }

        public static /* synthetic */ void cancelReceiveTimeout$(ActorContext $this) {
            $this.cancelReceiveTimeout();
        }

        default public void cancelReceiveTimeout() {
            this.setReceiveTimeout(Duration$.MODULE$.Undefined());
        }

        public static void $init$(ActorContext $this) {
        }
    }

    public static final class Receive {
        private final PartialFunction<Object, BoxedUnit> onMessage;

        public PartialFunction<Object, BoxedUnit> onMessage() {
            return this.onMessage;
        }

        public Receive orElse(Receive other) {
            return new Receive(this.onMessage().orElse(other.onMessage()));
        }

        public Receive(PartialFunction<Object, BoxedUnit> onMessage2) {
            this.onMessage = onMessage2;
        }
    }
}

