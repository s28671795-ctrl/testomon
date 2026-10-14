/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.dispatch.AbstractNodeQueue;
import akka.util.SerializedSuspendableExecutionContext$;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import scala.Function0;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.concurrent.ExecutionContext;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005%tA\u0002\u000f\u001e\u0011\u0003y\u0012E\u0002\u0004$;!\u0005q\u0004\n\u0005\u0006g\u0005!\t!\u000e\u0005\bm\u0005\u0011\r\u0011\"\u00028\u0011\u0019Q\u0014\u0001)A\u0007q!91(\u0001b\u0001\n\u000ba\u0004BB \u0002A\u00035Q\bC\u0004A\u0003\t\u0007IQA!\t\r\u0011\u000b\u0001\u0015!\u0004C\u0011\u0015)\u0015\u0001\"\u0001G\u0011%\ty&AA\u0001\n\u0013\t\tGB\u0003$;\ty\u0012\n\u0003\u0005]\u0017\t\u0005\t\u0015!\u0003^\u0011!\u00017B!b\u0001\n\u0003\t\u0007\u0002\u00032\f\u0005\u0003\u0005\u000b\u0011\u0002,\t\u000bMZA\u0011A2\t\u000f\u001d\\!\u0019!C\u0007Q\"1\u0011o\u0003Q\u0001\u000e%DQA]\u0006\u0005\u000eMDq!!\u0001\f\t\u001b\t\u0019\u0001C\u0004\u0002\u0012-!)!a\u0005\t\u000f\u0005U1\u0002\"\u0002\u0002\u0014!9\u0011qC\u0006\u0005\u0006\u0005M\u0001bBA\r\u0017\u0011\u0015\u00111\u0003\u0005\b\u00037YAQIA\u000f\u0011\u001d\t\u0019c\u0003C#\u0003KAq!a\u0011\f\t\u000b\t)\u0005C\u0004\u0002H-!)%!\u0013\u0002KM+'/[1mSj,GmU;ta\u0016tG-\u00192mK\u0016CXmY;uS>t7i\u001c8uKb$(B\u0001\u0010 \u0003\u0011)H/\u001b7\u000b\u0003\u0001\nA!Y6lCB\u0011!%A\u0007\u0002;\t)3+\u001a:jC2L'0\u001a3TkN\u0004XM\u001c3bE2,W\t_3dkRLwN\\\"p]R,\u0007\u0010^\n\u0004\u0003\u0015Z\u0003C\u0001\u0014*\u001b\u00059#\"\u0001\u0015\u0002\u000bM\u001c\u0017\r\\1\n\u0005):#AB!osJ+g\r\u0005\u0002-c5\tQF\u0003\u0002/_\u0005\u0011\u0011n\u001c\u0006\u0002a\u0005!!.\u0019<b\u0013\t\u0011TF\u0001\u0007TKJL\u0017\r\\5{C\ndW-\u0001\u0004=S:LGOP\u0002\u0001)\u0005\t\u0013aA(gMV\t\u0001hD\u0001:;\u0005\u0001\u0011\u0001B(gM\u0002\n!a\u00148\u0016\u0003uz\u0011AP\u000f\u0002\u0003\u0005\u0019qJ\u001c\u0011\u0002\u0013M+8\u000f]3oI\u0016$W#\u0001\"\u0010\u0003\rk\u0012AA\u0001\u000b'V\u001c\b/\u001a8eK\u0012\u0004\u0013!B1qa2LHcA$\u0002^Q\u0019\u0001*a\u0017\u0011\u0005\tZ1\u0003B\u0006K!Z\u00032a\u0013(Q\u001b\u0005a%BA' \u0003!!\u0017n\u001d9bi\u000eD\u0017BA(M\u0005E\t%m\u001d;sC\u000e$hj\u001c3f#V,W/\u001a\t\u0003#Rk\u0011A\u0015\u0006\u0003'>\nA\u0001\\1oO&\u0011QK\u0015\u0002\t%Vtg.\u00192mKB\u0011qKW\u0007\u00021*\u0011\u0011lJ\u0001\u000bG>t7-\u001e:sK:$\u0018BA.Y\u0005A)\u00050Z2vi&|gnQ8oi\u0016DH/\u0001\u0006uQJ|Wo\u001a5qkR\u0004\"A\n0\n\u0005};#aA%oi\u000691m\u001c8uKb$X#\u0001,\u0002\u0011\r|g\u000e^3yi\u0002\"\"\u0001\u001a4\u0015\u0005!+\u0007\"\u00021\u0010\u0001\u00041\u0006\"\u0002/\u0010\u0001\u0004i\u0016!B:uCR,W#A5\u0011\u0005)|W\"A6\u000b\u00051l\u0017AB1u_6L7M\u0003\u0002Z]*\u0011adL\u0005\u0003a.\u0014Q\"\u0011;p[&\u001c\u0017J\u001c;fO\u0016\u0014\u0018AB:uCR,\u0007%\u0001\u0005bI\u0012\u001cF/\u0019;f)\t!x\u000f\u0005\u0002'k&\u0011ao\n\u0002\b\u0005>|G.Z1o\u0011\u0015A(\u00031\u0001^\u0003!qWm^*uCR,\u0007F\u0001\n{!\tYh0D\u0001}\u0015\tix%\u0001\u0006b]:|G/\u0019;j_:L!a ?\u0003\u000fQ\f\u0017\u000e\u001c:fG\u0006A!/Z7Ti\u0006$X\r\u0006\u0003\u0002\u0006\u0005-\u0001c\u0001\u0014\u0002\b%\u0019\u0011\u0011B\u0014\u0003\tUs\u0017\u000e\u001e\u0005\u0007\u0003\u001b\u0019\u0002\u0019A/\u0002\u0011=dGm\u0015;bi\u0016D#a\u0005>\u0002\rI,7/^7f)\t\t)!A\u0004tkN\u0004XM\u001c3\u0002\u0007I,h.\u0001\u0004biR\f7\r[\u0001\bKb,7-\u001e;f)\u0011\t)!a\b\t\r\u0005\u0005\u0002\u00041\u0001Q\u0003\u0011!\u0018m]6\u0002\u001bI,\u0007o\u001c:u\r\u0006LG.\u001e:f)\u0011\t)!a\n\t\u000f\u0005%\u0012\u00041\u0001\u0002,\u0005\tA\u000f\u0005\u0003\u0002.\u0005ub\u0002BA\u0018\u0003sqA!!\r\u000285\u0011\u00111\u0007\u0006\u0004\u0003k!\u0014A\u0002\u001fs_>$h(C\u0001)\u0013\r\tYdJ\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\ty$!\u0011\u0003\u0013QC'o\\<bE2,'bAA\u001eO\u0005!1/\u001b>f)\u0005i\u0016\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005-\u0003\u0003BA'\u0003+rA!a\u0014\u0002RA\u0019\u0011\u0011G\u0014\n\u0007\u0005Ms%\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003/\nIF\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003':\u0003\"\u00021\n\u0001\b1\u0006\"\u0002/\n\u0001\u0004i\u0016\u0001D<sSR,'+\u001a9mC\u000e,GCAA2!\r\t\u0016QM\u0005\u0004\u0003O\u0012&AB(cU\u0016\u001cG\u000f")
public final class SerializedSuspendableExecutionContext
extends AbstractNodeQueue<Runnable>
implements Runnable,
ExecutionContext {
    private final int throughput;
    private final ExecutionContext context;
    private final AtomicInteger state;

    public static SerializedSuspendableExecutionContext apply(int throughput, ExecutionContext context) {
        return SerializedSuspendableExecutionContext$.MODULE$.apply(throughput, context);
    }

    public static int Suspended() {
        return SerializedSuspendableExecutionContext$.MODULE$.Suspended();
    }

    public static int On() {
        return SerializedSuspendableExecutionContext$.MODULE$.On();
    }

    public static int Off() {
        return SerializedSuspendableExecutionContext$.MODULE$.Off();
    }

    @Override
    public ExecutionContext prepare() {
        return ExecutionContext.prepare$(this);
    }

    public ExecutionContext context() {
        return this.context;
    }

    private final AtomicInteger state() {
        return this.state;
    }

    private final boolean addState(int newState) {
        int c;
        do {
            c = this.state().get();
        } while (!this.state().compareAndSet(c, c | newState));
        return true;
    }

    private final void remState(int oldState) {
        while (true) {
            int c = this.state().get();
            if (this.state().compareAndSet(c, c & ~oldState)) break;
        }
        this.attach();
    }

    public final void resume() {
        this.remState(2);
    }

    public final void suspend() {
        this.addState(2);
    }

    @Override
    public final void run() {
        try {
            this.run$1(0);
        }
        finally {
            this.remState(1);
        }
    }

    public final void attach() {
        if (!this.isEmpty() && this.state().compareAndSet(0, 1)) {
            this.context().execute(this);
            return;
        }
    }

    @Override
    public final void execute(Runnable task) {
        try {
            this.add(task);
        }
        finally {
            this.attach();
        }
    }

    @Override
    public final void reportFailure(Throwable t2) {
        this.context().reportFailure(t2);
    }

    public final int size() {
        return this.count();
    }

    @Override
    public final String toString() {
        int n = this.state().get();
        switch (n) {
            case 0: {
                return "Off";
            }
            case 1: {
                return "On";
            }
            case 2: {
                return "Off & Suspended";
            }
            case 3: {
                return "On & Suspended";
            }
        }
        throw new MatchError(BoxesRunTime.boxToInteger(n));
    }

    private final void run$1(int done) {
        block5: {
            BoxedUnit boxedUnit;
            while (done < this.throughput && this.state().get() == 1) {
                Runnable runnable2 = (Runnable)this.poll();
                if (runnable2 != null) {
                    try {
                        runnable2.run();
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable t2 = option.get();
                            this.context().reportFailure(t2);
                        }
                        throw throwable;
                    }
                    ++done;
                    continue;
                }
                boxedUnit = BoxedUnit.UNIT;
                break block5;
            }
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    public SerializedSuspendableExecutionContext(int throughput, ExecutionContext context) {
        this.throughput = throughput;
        this.context = context;
        ExecutionContext.$init$(this);
        Predef$.MODULE$.require(throughput > 0, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(80).append("SerializedSuspendableExecutionContext.throughput must be greater than 0 but was ").append($this.throughput).toString());
        this.state = new AtomicInteger(0);
    }
}

