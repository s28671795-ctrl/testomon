/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.Dispatcher;
import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.MessageDispatcherConfigurator;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import com.typesafe.config.Config;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E2Aa\u0002\u0005\u0001\u001b!A!\u0003\u0001B\u0001B\u0003%1\u0003C\u0005\u001d\u0001\t\u0005\t\u0015!\u0003\u001eA!)\u0011\u0005\u0001C\u0001E!9a\u0005\u0001b\u0001\n\u00139\u0003BB\u0016\u0001A\u0003%\u0001\u0006C\u0003-\u0001\u0011\u0005SF\u0001\fESN\u0004\u0018\r^2iKJ\u001cuN\u001c4jOV\u0014\u0018\r^8s\u0015\tI!\"\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005Y\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001\u001dA\u0011q\u0002E\u0007\u0002\u0011%\u0011\u0011\u0003\u0003\u0002\u001e\u001b\u0016\u001c8/Y4f\t&\u001c\b/\u0019;dQ\u0016\u00148i\u001c8gS\u001e,(/\u0019;pe\u000611m\u001c8gS\u001e\u0004\"\u0001\u0006\u000e\u000e\u0003UQ!A\u0005\f\u000b\u0005]A\u0012\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003e\t1aY8n\u0013\tYRC\u0001\u0004D_:4\u0017nZ\u0001\u000eaJ,'/Z9vSNLG/Z:\u0011\u0005=q\u0012BA\u0010\t\u0005]!\u0015n\u001d9bi\u000eDWM\u001d)sKJ,\u0017/^5tSR,7/\u0003\u0002\u001d!\u00051A(\u001b8jiz\"2a\t\u0013&!\ty\u0001\u0001C\u0003\u0013\u0007\u0001\u00071\u0003C\u0003\u001d\u0007\u0001\u0007Q$\u0001\u0005j]N$\u0018M\\2f+\u0005A\u0003CA\b*\u0013\tQ\u0003B\u0001\u0006ESN\u0004\u0018\r^2iKJ\f\u0011\"\u001b8ti\u0006t7-\u001a\u0011\u0002\u0015\u0011L7\u000f]1uG\",'\u000fF\u0001/!\tyq&\u0003\u00021\u0011\t\tR*Z:tC\u001e,G)[:qCR\u001c\u0007.\u001a:")
public class DispatcherConfigurator
extends MessageDispatcherConfigurator {
    private final Dispatcher instance;

    private Dispatcher instance() {
        return this.instance;
    }

    @Override
    public MessageDispatcher dispatcher() {
        return this.instance();
    }

    public DispatcherConfigurator(Config config, DispatcherPrerequisites prerequisites) {
        super(config, prerequisites);
        this.instance = new Dispatcher(this, config.getString("id"), config.getInt("throughput"), Helpers$ConfigOps$.MODULE$.getNanosDuration$extension(Helpers$.MODULE$.ConfigOps(config), "throughput-deadline-time"), this.configureExecutor(), Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "shutdown-timeout"));
    }
}

