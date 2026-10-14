/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Completed$;
import akka.stream.impl.NotInitialized$;
import akka.stream.impl.TransferPhase;
import akka.stream.impl.TransferState;
import akka.stream.impl.WaitingForUpstreamSubscription;
import java.io.Serializable;
import scala.Function0;
import scala.Option;
import scala.Predef$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Nothing$;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005%4\u0001b\u0004\t\u0011\u0002\u0007\u0005AC\u0006\u0005\u0006;\u0001!\ta\b\u0005\bG\u0001\u0001\r\u0011\"\u0003%\u0011\u001dI\u0003\u00011A\u0005\n)Bq!\f\u0001A\u0002\u0013%a\u0006C\u00043\u0001\u0001\u0007I\u0011B\u001a\t\u000bU\u0002AQ\u0001\u001c\t\u000b\u0005\u0003AQ\u0001\"\t\u000b\u0011\u0003A\u0011A\u0010\t\u000b\u0015\u0003AQ\u0001$\t\u000b%\u0003AQ\u0001&\t\u000f9\u0003!\u0019!C\u000b\u001f\")\u0001\u000b\u0001C\u0003?!)\u0011\u000b\u0001D\t%\")\u0011\r\u0001D\t?\t!\u0001+^7q\u0015\t\t\"#\u0001\u0003j[Bd'BA\n\u0015\u0003\u0019\u0019HO]3b[*\tQ#\u0001\u0003bW.\f7C\u0001\u0001\u0018!\tA2$D\u0001\u001a\u0015\u0005Q\u0012!B:dC2\f\u0017B\u0001\u000f\u001a\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\u001a\u0001\u0001F\u0001!!\tA\u0012%\u0003\u0002#3\t!QK\\5u\u00035!(/\u00198tM\u0016\u00148\u000b^1uKV\tQ\u0005\u0005\u0002'O5\t\u0001#\u0003\u0002)!\tiAK]1og\u001a,'o\u0015;bi\u0016\f\u0011\u0003\u001e:b]N4WM]*uCR,w\fJ3r)\t\u00013\u0006C\u0004-\u0007\u0005\u0005\t\u0019A\u0013\u0002\u0007a$\u0013'A\u0007dkJ\u0014XM\u001c;BGRLwN\\\u000b\u0002_A\u0019\u0001\u0004\r\u0011\n\u0005EJ\"!\u0003$v]\u000e$\u0018n\u001c81\u0003E\u0019WO\u001d:f]R\f5\r^5p]~#S-\u001d\u000b\u0003AQBq\u0001L\u0003\u0002\u0002\u0003\u0007q&\u0001\u0007j]&$\u0018.\u00197QQ\u0006\u001cX\rF\u0002!oqBQ\u0001\u000f\u0004A\u0002e\nqb^1ji\u001a{'/\u00169tiJ,\u0017-\u001c\t\u00031iJ!aO\r\u0003\u0007%sG\u000fC\u0003>\r\u0001\u0007a(A\u0004b]\u0012$\u0006.\u001a8\u0011\u0005\u0019z\u0014B\u0001!\u0011\u00055!&/\u00198tM\u0016\u0014\b\u000b[1tK\u0006\u0001r/Y5u\r>\u0014X\u000b]:ue\u0016\fWn\u001d\u000b\u0003A\rCQ\u0001O\u0004A\u0002e\nqcZ8u+B\u001cHO]3b[N+(m]2sSB$\u0018n\u001c8\u0002\u00139,\u0007\u0010\u001e)iCN,GC\u0001\u0011H\u0011\u0015A\u0015\u00021\u0001?\u0003\u0015\u0001\b.Y:f\u00039I7\u000fU;na\u001aKg.[:iK\u0012,\u0012a\u0013\t\u000311K!!T\r\u0003\u000f\t{w\u000e\\3b]\u0006q1m\\7qY\u0016$X\r\u001a)iCN,W#\u0001 \u0002\tA,X\u000e]\u0001\u000baVl\u0007OR1jY\u0016$GC\u0001\u0011T\u0011\u0015!V\u00021\u0001V\u0003\u0005)\u0007C\u0001,_\u001d\t9FL\u0004\u0002Y76\t\u0011L\u0003\u0002[=\u00051AH]8pizJ\u0011AG\u0005\u0003;f\tq\u0001]1dW\u0006<W-\u0003\u0002`A\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003;f\tA\u0002];na\u001aKg.[:iK\u0012D#\u0001A2\u0011\u0005\u0011<W\"A3\u000b\u0005\u0019$\u0012AC1o]>$\u0018\r^5p]&\u0011\u0001.\u001a\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public interface Pump {
    public void akka$stream$impl$Pump$_setter_$completedPhase_$eq(TransferPhase var1);

    public TransferState akka$stream$impl$Pump$$transferState();

    public void akka$stream$impl$Pump$$transferState_$eq(TransferState var1);

    public Function0<BoxedUnit> akka$stream$impl$Pump$$currentAction();

    public void akka$stream$impl$Pump$$currentAction_$eq(Function0<BoxedUnit> var1);

    public static /* synthetic */ void initialPhase$(Pump $this, int waitForUpstream, TransferPhase andThen) {
        $this.initialPhase(waitForUpstream, andThen);
    }

    default public void initialPhase(int waitForUpstream, TransferPhase andThen) {
        Predef$.MODULE$.require(waitForUpstream >= 1, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(35).append("waitForUpstream must be >= 1 (was ").append(waitForUpstream).append(")").toString());
        TransferState transferState = this.akka$stream$impl$Pump$$transferState();
        NotInitialized$ notInitialized$ = NotInitialized$.MODULE$;
        if (transferState == null ? notInitialized$ != null : !transferState.equals(notInitialized$)) {
            throw new IllegalStateException(new StringBuilder(48).append("initialPhase expected NotInitialized, but was [").append(this.akka$stream$impl$Pump$$transferState()).append("]").toString());
        }
        this.akka$stream$impl$Pump$$transferState_$eq(new WaitingForUpstreamSubscription(waitForUpstream, andThen));
    }

    public static /* synthetic */ void waitForUpstreams$(Pump $this, int waitForUpstream) {
        $this.waitForUpstreams(waitForUpstream);
    }

    default public void waitForUpstreams(int waitForUpstream) {
        Predef$.MODULE$.require(waitForUpstream >= 1, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(35).append("waitForUpstream must be >= 1 (was ").append(waitForUpstream).append(")").toString());
        this.akka$stream$impl$Pump$$transferState_$eq(new WaitingForUpstreamSubscription(waitForUpstream, new TransferPhase(this.akka$stream$impl$Pump$$transferState(), this.akka$stream$impl$Pump$$currentAction())));
    }

    public static /* synthetic */ void gotUpstreamSubscription$(Pump $this) {
        $this.gotUpstreamSubscription();
    }

    /*
     * Unable to fully structure code
     */
    default public void gotUpstreamSubscription() {
        var2_1 = false;
        var3_2 = null;
        var4_3 = this.akka$stream$impl$Pump$$transferState();
        if (!(var4_3 instanceof WaitingForUpstreamSubscription)) ** GOTO lbl-1000
        var2_1 = true;
        var3_2 = (WaitingForUpstreamSubscription)var4_3;
        var5_4 = var3_2.remaining();
        andThen = var3_2.andThen();
        if (1 == var5_4) {
            this.akka$stream$impl$Pump$$transferState_$eq(andThen.precondition());
            this.akka$stream$impl$Pump$$currentAction_$eq(andThen.action());
        } else if (var2_1) {
            remaining = var3_2.remaining();
            andThen = var3_2.andThen();
            this.akka$stream$impl$Pump$$transferState_$eq(new WaitingForUpstreamSubscription(remaining - 1, andThen));
        }
        this.pump();
    }

    public static /* synthetic */ void nextPhase$(Pump $this, TransferPhase phase) {
        $this.nextPhase(phase);
    }

    default public void nextPhase(TransferPhase phase) {
        TransferState transferState = this.akka$stream$impl$Pump$$transferState();
        if (transferState instanceof WaitingForUpstreamSubscription) {
            WaitingForUpstreamSubscription waitingForUpstreamSubscription = (WaitingForUpstreamSubscription)transferState;
            int remaining = waitingForUpstreamSubscription.remaining();
            this.akka$stream$impl$Pump$$transferState_$eq(new WaitingForUpstreamSubscription(remaining, phase));
            return;
        }
        this.akka$stream$impl$Pump$$transferState_$eq(phase.precondition());
        this.akka$stream$impl$Pump$$currentAction_$eq(phase.action());
    }

    public static /* synthetic */ boolean isPumpFinished$(Pump $this) {
        return $this.isPumpFinished();
    }

    default public boolean isPumpFinished() {
        return this.akka$stream$impl$Pump$$transferState().isCompleted();
    }

    public TransferPhase completedPhase();

    public static /* synthetic */ void pump$(Pump $this) {
        $this.pump();
    }

    default public void pump() {
        try {
            while (this.akka$stream$impl$Pump$$transferState().isExecutable()) {
                this.akka$stream$impl$Pump$$currentAction().apply$mcV$sp();
            }
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.pumpFailed(e);
            }
            throw throwable;
        }
        if (this.isPumpFinished()) {
            this.pumpFinished();
            return;
        }
    }

    public void pumpFailed(Throwable var1);

    public void pumpFinished();

    public static void $init$(Pump $this) {
        $this.akka$stream$impl$Pump$$transferState_$eq(NotInitialized$.MODULE$);
        $this.akka$stream$impl$Pump$$currentAction_$eq((Function0<BoxedUnit>)(Function0<Nothing$> & Serializable)() -> {
            throw new IllegalStateException("Pump has been not initialized with a phase");
        });
        $this.akka$stream$impl$Pump$_setter_$completedPhase_$eq(new TransferPhase(Completed$.MODULE$, (Function0<BoxedUnit>)(Function0<Nothing$> & Serializable)() -> {
            throw new IllegalStateException("The action of completed phase must be never executed");
        }));
    }
}

