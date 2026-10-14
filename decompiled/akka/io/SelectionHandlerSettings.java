/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.util.Helpers$;
import akka.util.Helpers$Requiring$;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Function0;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005%3QAD\b\u0002\u0002QA\u0001b\u0007\u0001\u0003\u0002\u0003\u0006I\u0001\b\u0005\u0006K\u0001!\tA\n\u0005\bU\u0001\u0011\r\u0011\"\u0001,\u0011\u0019y\u0003\u0001)A\u0005Y!9\u0001\u0007\u0001b\u0001\n\u0003Y\u0003BB\u0019\u0001A\u0003%A\u0006C\u00043\u0001\t\u0007I\u0011A\u001a\t\r}\u0002\u0001\u0015!\u00035\u0011\u001d\u0001\u0005A1A\u0005\u0002MBa!\u0011\u0001!\u0002\u0013!\u0004b\u0002\"\u0001\u0005\u0004%\ta\u0011\u0005\u0007\u000f\u0002\u0001\u000b\u0011\u0002#\t\u000b!\u0003a\u0011A\u0016\u00031M+G.Z2uS>t\u0007*\u00198eY\u0016\u00148+\u001a;uS:<7O\u0003\u0002\u0011#\u0005\u0011\u0011n\u001c\u0006\u0002%\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001A\u000b\u0011\u0005YIR\"A\f\u000b\u0003a\tQa]2bY\u0006L!AG\f\u0003\r\u0005s\u0017PU3g\u0003\u0019\u0019wN\u001c4jOB\u0011QdI\u0007\u0002=)\u00111d\b\u0006\u0003A\u0005\n\u0001\u0002^=qKN\fg-\u001a\u0006\u0002E\u0005\u00191m\\7\n\u0005\u0011r\"AB\"p]\u001aLw-\u0001\u0004=S:LGO\u0010\u000b\u0003O%\u0002\"\u0001\u000b\u0001\u000e\u0003=AQa\u0007\u0002A\u0002q\t1\"T1y\u0007\"\fgN\\3mgV\tA\u0006\u0005\u0002\u0017[%\u0011af\u0006\u0002\u0004\u0013:$\u0018\u0001D'bq\u000eC\u0017M\u001c8fYN\u0004\u0013AG*fY\u0016\u001cGo\u001c:BgN|7-[1uS>t'+\u001a;sS\u0016\u001c\u0018aG*fY\u0016\u001cGo\u001c:BgN|7-[1uS>t'+\u001a;sS\u0016\u001c\b%\u0001\nTK2,7\r^8s\t&\u001c\b/\u0019;dQ\u0016\u0014X#\u0001\u001b\u0011\u0005UbdB\u0001\u001c;!\t9t#D\u00019\u0015\tI4#\u0001\u0004=e>|GOP\u0005\u0003w]\ta\u0001\u0015:fI\u00164\u0017BA\u001f?\u0005\u0019\u0019FO]5oO*\u00111hF\u0001\u0014'\u0016dWm\u0019;pe\u0012K7\u000f]1uG\",'\u000fI\u0001\u0011/>\u00148.\u001a:ESN\u0004\u0018\r^2iKJ\f\u0011cV8sW\u0016\u0014H)[:qCR\u001c\u0007.\u001a:!\u00031!&/Y2f\u0019><w-\u001b8h+\u0005!\u0005C\u0001\fF\u0013\t1uCA\u0004C_>dW-\u00198\u0002\u001bQ\u0013\u0018mY3M_\u001e<\u0017N\\4!\u0003Yi\u0015\r_\"iC:tW\r\\:QKJ\u001cV\r\\3di>\u0014\b")
public abstract class SelectionHandlerSettings {
    private final int MaxChannels;
    private final int SelectorAssociationRetries;
    private final String SelectorDispatcher;
    private final String WorkerDispatcher;
    private final boolean TraceLogging;

    public int MaxChannels() {
        return this.MaxChannels;
    }

    public int SelectorAssociationRetries() {
        return this.SelectorAssociationRetries;
    }

    public String SelectorDispatcher() {
        return this.SelectorDispatcher;
    }

    public String WorkerDispatcher() {
        return this.WorkerDispatcher;
    }

    public boolean TraceLogging() {
        return this.TraceLogging;
    }

    public abstract int MaxChannelsPerSelector();

    public SelectionHandlerSettings(Config config) {
        int n;
        block3: {
            String string = config.getString("max-channels");
            switch (string == null ? 0 : string.hashCode()) {
                case 1887918305: {
                    if (!"unlimited".equals(string)) break;
                    n = -1;
                    break block3;
                }
            }
            n = BoxesRunTime.unboxToInt(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToInteger(config.getInt("max-channels"))), x$1 -> x$1 > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "max-channels must be > 0 or 'unlimited'"));
        }
        this.MaxChannels = n;
        this.SelectorAssociationRetries = BoxesRunTime.unboxToInt(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToInteger(config.getInt("selector-association-retries"))), x$2 -> x$2 >= 0, (Function0<Object>)(Function0<String> & Serializable)() -> "selector-association-retries must be >= 0"));
        this.SelectorDispatcher = config.getString("selector-dispatcher");
        this.WorkerDispatcher = config.getString("worker-dispatcher");
        this.TraceLogging = config.getBoolean("trace-logging");
    }
}

