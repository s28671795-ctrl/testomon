/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.annotation.InternalApi;
import akka.pattern.AutoReset;
import akka.pattern.BackoffReset;
import akka.pattern.BackoffSupervisor;
import akka.pattern.BackoffSupervisor$GetCurrentChild$;
import akka.pattern.BackoffSupervisor$GetRestartCount$;
import akka.pattern.BackoffSupervisor$Reset$;
import akka.pattern.BackoffSupervisor$StartChild$;
import akka.pattern.ManualReset$;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005a4\u0011b\u0004\t\u0011\u0002\u0007\u0005!\u0003\u00066\t\u000bm\u0001A\u0011A\u000f\t\u000b\u0005\u0002a\u0011\u0001\u0012\t\u000b%\u0002a\u0011\u0001\u0016\t\u000bY\u0002a\u0011A\u001c\t\u000bq\u0002a\u0011C\u001f\t\u000f\r\u0003\u0001\u0019!C\u0001\t\"91\n\u0001a\u0001\n\u0003a\u0005bB(\u0001\u0001\u0004%\t\u0001\u0015\u0005\b)\u0002\u0001\r\u0011\"\u0001V\u0011\u001d9\u0006\u00011A\u0005\u0002aCq\u0001\u0018\u0001A\u0002\u0013\u0005Q\fC\u0003`\u0001\u0011\u0005S\u0004C\u0003a\u0001\u0011\u0005Q\u0004C\u0003b\u0001\u0011\u0005!MA\u0007IC:$G.\u001a\"bG.|gM\u001a\u0006\u0003#I\tq\u0001]1ui\u0016\u0014hNC\u0001\u0014\u0003\u0011\t7n[1\u0014\u0005\u0001)\u0002C\u0001\f\u001a\u001b\u00059\"\"\u0001\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005i9\"AB!osJ+g-\u0001\u0004%S:LG\u000fJ\u0002\u0001)\u0005q\u0002C\u0001\f \u0013\t\u0001sC\u0001\u0003V]&$\u0018AC2iS2$\u0007K]8qgV\t1\u0005\u0005\u0002%O5\tQE\u0003\u0002'%\u0005)\u0011m\u0019;pe&\u0011\u0001&\n\u0002\u0006!J|\u0007o]\u0001\nG\"LG\u000e\u001a(b[\u0016,\u0012a\u000b\t\u0003YMr!!L\u0019\u0011\u00059:R\"A\u0018\u000b\u0005Ab\u0012A\u0002\u001fs_>$h(\u0003\u00023/\u00051\u0001K]3eK\u001aL!\u0001N\u001b\u0003\rM#(/\u001b8h\u0015\t\u0011t#A\u0003sKN,G/F\u00019!\tI$(D\u0001\u0011\u0013\tY\u0004C\u0001\u0007CC\u000e\\wN\u001a4SKN,G/\u0001\u000biC:$G.Z'fgN\fw-\u001a+p\u0007\"LG\u000e\u001a\u000b\u0003=yBQaP\u0003A\u0002\u0001\u000b\u0011!\u001c\t\u0003-\u0005K!AQ\f\u0003\u0007\u0005s\u00170A\u0003dQ&dG-F\u0001F!\r1b\tS\u0005\u0003\u000f^\u0011aa\u00149uS>t\u0007C\u0001\u0013J\u0013\tQUE\u0001\u0005BGR|'OU3g\u0003%\u0019\u0007.\u001b7e?\u0012*\u0017\u000f\u0006\u0002\u001f\u001b\"9ajBA\u0001\u0002\u0004)\u0015a\u0001=%c\u0005a!/Z:uCJ$8i\\;oiV\t\u0011\u000b\u0005\u0002\u0017%&\u00111k\u0006\u0002\u0004\u0013:$\u0018\u0001\u0005:fgR\f'\u000f^\"pk:$x\fJ3r)\tqb\u000bC\u0004O\u0013\u0005\u0005\t\u0019A)\u00021\u0019Lg.\u00197Ti>\u0004X*Z:tC\u001e,'+Z2fSZ,G-F\u0001Z!\t1\",\u0003\u0002\\/\t9!i\\8mK\u0006t\u0017\u0001\b4j]\u0006d7\u000b^8q\u001b\u0016\u001c8/Y4f%\u0016\u001cW-\u001b<fI~#S-\u001d\u000b\u0003=yCqAT\u0006\u0002\u0002\u0003\u0007\u0011,\u0001\u0005qe\u0016\u001cF/\u0019:u\u0003)\u0019H/\u0019:u\u0007\"LG\u000eZ\u0001\u000eQ\u0006tG\r\\3CC\u000e\\wN\u001a4\u0016\u0003\r\u0004\"\u0001Z4\u000f\u0005\u0011*\u0017B\u00014&\u0003\u0015\t5\r^8s\u0013\tA\u0017NA\u0004SK\u000e,\u0017N^3\u000b\u0005\u0019,#cA6n]\u001a!A\u000e\u0001\u0001k\u00051a$/\u001a4j]\u0016lWM\u001c;?!\tI\u0004\u0001\u0005\u0002%_&\u0011\u0001/\n\u0002\u0006\u0003\u000e$xN\u001d\u0015\u0003\u0001I\u0004\"a\u001d<\u000e\u0003QT!!\u001e\n\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002xi\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public interface HandleBackoff {
    public Props childProps();

    public String childName();

    public BackoffReset reset();

    public void handleMessageToChild(Object var1);

    public Option<ActorRef> child();

    public void child_$eq(Option<ActorRef> var1);

    public int restartCount();

    public void restartCount_$eq(int var1);

    public boolean finalStopMessageReceived();

    public void finalStopMessageReceived_$eq(boolean var1);

    public static /* synthetic */ void preStart$(HandleBackoff $this) {
        $this.preStart();
    }

    default public void preStart() {
        this.startChild();
    }

    public static /* synthetic */ void startChild$(HandleBackoff $this) {
        $this.startChild();
    }

    default public void startChild() {
        if (this.child().isEmpty()) {
            this.child_$eq(new Some<ActorRef>(((Actor)((Object)this)).context().watch(((Actor)((Object)this)).context().actorOf(this.childProps(), this.childName()))));
            return;
        }
    }

    public static /* synthetic */ PartialFunction handleBackoff$(HandleBackoff $this) {
        return $this.handleBackoff();
    }

    default public PartialFunction<Object, BoxedUnit> handleBackoff() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ HandleBackoff $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (BackoffSupervisor$StartChild$.MODULE$.equals(A1)) {
                    this.$outer.startChild();
                    BackoffReset backoffReset = this.$outer.reset();
                    if (backoffReset instanceof AutoReset) {
                        AutoReset autoReset = (AutoReset)backoffReset;
                        FiniteDuration resetBackoff = autoReset.resetBackoff();
                        if (this.$outer.restartCount() > 0) {
                            ((Actor)((Object)this.$outer)).context().system().scheduler().scheduleOnce(resetBackoff, ((Actor)((Object)this.$outer)).self(), (Object)new BackoffSupervisor.ResetRestartCount(this.$outer.restartCount()), (ExecutionContext)((Actor)((Object)this.$outer)).context().dispatcher(), ((Actor)((Object)this.$outer)).self());
                        }
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (BackoffSupervisor$Reset$.MODULE$.equals(A1)) {
                    BackoffReset backoffReset = this.$outer.reset();
                    if (ManualReset$.MODULE$.equals(backoffReset)) {
                        this.$outer.restartCount_$eq(0);
                    } else {
                        ((Actor)((Object)this.$outer)).unhandled(backoffReset);
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof BackoffSupervisor.ResetRestartCount) {
                    BackoffSupervisor.ResetRestartCount resetRestartCount = (BackoffSupervisor.ResetRestartCount)A1;
                    int seenRestartCount = resetRestartCount.current();
                    if (seenRestartCount == this.$outer.restartCount() && this.$outer.restartCount() > 0) {
                        this.$outer.restartCount_$eq(0);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (BackoffSupervisor$GetRestartCount$.MODULE$.equals(A1)) {
                    ((Actor)((Object)this.$outer)).sender().$bang(new BackoffSupervisor.RestartCount(this.$outer.restartCount()), ((Actor)((Object)this.$outer)).self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (BackoffSupervisor$GetCurrentChild$.MODULE$.equals(A1)) {
                    ((Actor)((Object)this.$outer)).sender().$bang(new BackoffSupervisor.CurrentChild(this.$outer.child()), ((Actor)((Object)this.$outer)).self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (this.$outer.child().contains(((Actor)((Object)this.$outer)).sender())) {
                    ((Actor)((Object)this.$outer)).context().parent().$bang(A1, ((Actor)((Object)this.$outer)).self());
                    return (B1)BoxedUnit.UNIT;
                }
                this.$outer.handleMessageToChild(A1);
                return (B1)BoxedUnit.UNIT;
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (BackoffSupervisor$StartChild$.MODULE$.equals(object)) {
                    return true;
                }
                if (BackoffSupervisor$Reset$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof BackoffSupervisor.ResetRestartCount) {
                    return true;
                }
                if (BackoffSupervisor$GetRestartCount$.MODULE$.equals(object)) {
                    return true;
                }
                if (BackoffSupervisor$GetCurrentChild$.MODULE$.equals(object)) {
                    return true;
                }
                if (this.$outer.child().contains(((Actor)((Object)this.$outer)).sender())) {
                    return true;
                }
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public static void $init$(HandleBackoff $this) {
        $this.child_$eq(None$.MODULE$);
        $this.restartCount_$eq(0);
        $this.finalStopMessageReceived_$eq(false);
    }
}

