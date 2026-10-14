/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.actor.ActorCell;
import akka.actor.ActorCell$;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.InternalActorRef;
import akka.actor.NotInfluenceReceiveTimeout;
import akka.actor.dungeon.ReceiveTimeout$;
import akka.dispatch.MessageDispatcher;
import scala.MatchError;
import scala.Tuple2;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00051<aAE\n\t\u0002]IbAB\u000e\u0014\u0011\u00039B\u0004C\u0003$\u0003\u0011\u0005Q\u0005C\u0004'\u0003\t\u0007IQA\u0014\t\r]\n\u0001\u0015!\u0004)\r%Y2\u0003%A\u0002\u0002]A\u0014\u000eC\u0003:\u000b\u0011\u0005!\bC\u0004?\u000b\u0001\u0007I\u0011B\u0014\t\u000f}*\u0001\u0019!C\u0005\u0001\")1)\u0002C\u0003\t\")Q)\u0002C\u0003\r\")\u0011*\u0002C\t\u0015\")!+\u0002C\u0003'\")\u0011,\u0002C\u00055\")\u0001-\u0002C\u0005C\")!-\u0002C\u0005G\")Q-\u0002C\tM\"1\u0001.\u0002C\u0001/i\naBU3dK&4X\rV5nK>,HO\u0003\u0002\u0015+\u00059A-\u001e8hK>t'B\u0001\f\u0018\u0003\u0015\t7\r^8s\u0015\u0005A\u0012\u0001B1lW\u0006\u0004\"AG\u0001\u000e\u0003M\u0011aBU3dK&4X\rV5nK>,Ho\u0005\u0002\u0002;A\u0011a$I\u0007\u0002?)\t\u0001%A\u0003tG\u0006d\u0017-\u0003\u0002#?\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u00023\u00059R-\u001c9usJ+7-Z5wKRKW.Z8vi\u0012\u000bG/Y\u000b\u0002QA!a$K\u00164\u0013\tQsD\u0001\u0004UkBdWM\r\t\u0003YEj\u0011!\f\u0006\u0003]=\n\u0001\u0002Z;sCRLwN\u001c\u0006\u0003a}\t!bY8oGV\u0014(/\u001a8u\u0013\t\u0011TF\u0001\u0005EkJ\fG/[8o!\t!T'D\u0001\u0016\u0013\t1TCA\u0006DC:\u001cW\r\u001c7bE2,\u0017\u0001G3naRL(+Z2fSZ,G+[7f_V$H)\u0019;bAM\u0011Q!H\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003m\u0002\"A\b\u001f\n\u0005uz\"\u0001B+oSR\f!C]3dK&4X\rV5nK>,H\u000fR1uC\u00061\"/Z2fSZ,G+[7f_V$H)\u0019;b?\u0012*\u0017\u000f\u0006\u0002<\u0003\"9!\tCA\u0001\u0002\u0004A\u0013a\u0001=%c\u0005q!/Z2fSZ,G+[7f_V$X#A\u0016\u0002#M,GOU3dK&4X\rV5nK>,H\u000f\u0006\u0002<\u000f\")\u0001J\u0003a\u0001W\u00059A/[7f_V$\u0018aG2iK\u000e\\'+Z2fSZ,G+[7f_V$\u0018J\u001a(fK\u0012,G\rF\u0002<\u0017BCQ\u0001T\u0006A\u00025\u000bq!\\3tg\u0006<W\r\u0005\u0002\u001f\u001d&\u0011qj\b\u0002\u0004\u0003:L\b\"B)\f\u0001\u0004A\u0013!\u00042fM>\u0014XMU3dK&4X-A\ndQ\u0016\u001c7NU3dK&4X\rV5nK>,H\u000f\u0006\u0002<)\")Q\u000b\u0004a\u0001-\u0006Q!/Z:dQ\u0016$W\u000f\\3\u0011\u0005y9\u0016B\u0001- \u0005\u001d\u0011un\u001c7fC:\f\u0001D]3tG\",G-\u001e7f%\u0016\u001cW-\u001b<f)&lWm\\;u)\tY4\fC\u0003]\u001b\u0001\u0007Q,A\u0001g!\tac,\u0003\u0002`[\tqa)\u001b8ji\u0016$UO]1uS>t\u0017A\u00045bgRKW.Z8vi\u0012\u000bG/Y\u000b\u0002-\u0006)\"/Z2fSZ,G+[7f_V$8\t[1oO\u0016$GC\u0001,e\u0011\u0015\tv\u00021\u0001)\u0003q\u0019\u0017M\\2fYJ+7-Z5wKRKW.Z8vi&3g*Z3eK\u0012$\"\u0001K4\t\u000b1\u0003\u0002\u0019A'\u00021\r\fgnY3m%\u0016\u001cW-\u001b<f)&lWm\\;u)\u0006\u001c8\u000e\u0005\u00025U&\u00111.\u0006\u0002\n\u0003\u000e$xN]\"fY2\u0004")
public interface ReceiveTimeout {
    public static Tuple2<Duration, Cancellable> emptyReceiveTimeoutData() {
        return ReceiveTimeout$.MODULE$.emptyReceiveTimeoutData();
    }

    public Tuple2<Duration, Cancellable> akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData();

    public void akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData_$eq(Tuple2<Duration, Cancellable> var1);

    public static /* synthetic */ Duration receiveTimeout$(ReceiveTimeout $this) {
        return $this.receiveTimeout();
    }

    default public Duration receiveTimeout() {
        return this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData()._1();
    }

    public static /* synthetic */ void setReceiveTimeout$(ReceiveTimeout $this, Duration timeout2) {
        $this.setReceiveTimeout(timeout2);
    }

    default public void setReceiveTimeout(Duration timeout2) {
        Tuple2<Duration, Cancellable> qual$1 = this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData();
        Duration x$1 = timeout2;
        Cancellable x$2 = qual$1.copy$default$2();
        this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData_$eq(qual$1.copy(x$1, x$2));
    }

    public static /* synthetic */ void checkReceiveTimeoutIfNeeded$(ReceiveTimeout $this, Object message, Tuple2 beforeReceive) {
        $this.checkReceiveTimeoutIfNeeded(message, beforeReceive);
    }

    default public void checkReceiveTimeoutIfNeeded(Object message, Tuple2<Duration, Cancellable> beforeReceive) {
        if (this.hasTimeoutData() || this.receiveTimeoutChanged(beforeReceive)) {
            this.checkReceiveTimeout(!(message instanceof NotInfluenceReceiveTimeout) || this.receiveTimeoutChanged(beforeReceive));
            return;
        }
    }

    public static /* synthetic */ void checkReceiveTimeout$(ReceiveTimeout $this, boolean reschedule) {
        $this.checkReceiveTimeout(reschedule);
    }

    default public void checkReceiveTimeout(boolean reschedule) {
        Tuple2<Duration, Cancellable> tuple2 = this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData();
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        Duration recvTimeout = tuple2._1();
        Cancellable task = tuple2._2();
        Tuple2<Duration, Cancellable> tuple22 = new Tuple2<Duration, Cancellable>(recvTimeout, task);
        Duration recvTimeout2 = tuple22._1();
        Cancellable task2 = tuple22._2();
        Duration duration = recvTimeout2;
        if (duration instanceof FiniteDuration) {
            FiniteDuration finiteDuration = (FiniteDuration)duration;
            if (reschedule || task2 == ActorCell$.MODULE$.emptyCancellable()) {
                this.rescheduleReceiveTimeout(finiteDuration);
                return;
            }
            return;
        }
        this.cancelReceiveTimeoutTask();
    }

    private void rescheduleReceiveTimeout(FiniteDuration f) {
        this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData()._2().cancel();
        FiniteDuration x$1 = f;
        InternalActorRef x$2 = ((ActorCell)this).self();
        akka.actor.ReceiveTimeout$ x$3 = akka.actor.ReceiveTimeout$.MODULE$;
        MessageDispatcher x$4 = ((ActorCell)this).dispatcher();
        ActorRef x$5 = ((ActorCell)this).system().scheduler().scheduleOnce$default$5(x$1, x$2, x$3);
        Cancellable task = ((ActorCell)this).system().scheduler().scheduleOnce(x$1, (ActorRef)x$2, (Object)x$3, (ExecutionContext)x$4, x$5);
        this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData_$eq(new Tuple2<Duration, Cancellable>(f, task));
    }

    private boolean hasTimeoutData() {
        return this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData() != ReceiveTimeout$.MODULE$.emptyReceiveTimeoutData();
    }

    private boolean receiveTimeoutChanged(Tuple2<Duration, Cancellable> beforeReceive) {
        return this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData() != beforeReceive;
    }

    public static /* synthetic */ Tuple2 cancelReceiveTimeoutIfNeeded$(ReceiveTimeout $this, Object message) {
        return $this.cancelReceiveTimeoutIfNeeded(message);
    }

    default public Tuple2<Duration, Cancellable> cancelReceiveTimeoutIfNeeded(Object message) {
        block0: {
            if (!this.hasTimeoutData() || message instanceof NotInfluenceReceiveTimeout) break block0;
            this.cancelReceiveTimeoutTask();
        }
        return this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData();
    }

    public static /* synthetic */ void cancelReceiveTimeoutTask$(ReceiveTimeout $this) {
        $this.cancelReceiveTimeoutTask();
    }

    default public void cancelReceiveTimeoutTask() {
        if (this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData()._2() != ActorCell$.MODULE$.emptyCancellable()) {
            this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData()._2().cancel();
            this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData_$eq(new Tuple2<Duration, Cancellable>(this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData()._1(), ActorCell$.MODULE$.emptyCancellable()));
            return;
        }
    }

    public static void $init$(ReceiveTimeout $this) {
        $this.akka$actor$dungeon$ReceiveTimeout$$receiveTimeoutData_$eq(ReceiveTimeout$.MODULE$.emptyReceiveTimeoutData());
    }
}

