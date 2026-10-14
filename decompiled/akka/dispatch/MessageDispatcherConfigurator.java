/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.CachingConfig;
import akka.dispatch.DefaultExecutorServiceConfigurator;
import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.ExecutorServiceConfigurator;
import akka.dispatch.ForkJoinExecutorConfigurator;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.ThreadPoolExecutorConfigurator;
import akka.dispatch.affinity.AffinityPoolConfigurator;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005e2Q!\u0003\u0006\u0002\u0002=A\u0001B\u0006\u0001\u0003\u0002\u0003\u0006Ia\u0006\u0005\tC\u0001\u0011)\u0019!C\u0001E!Aq\u0005\u0001B\u0001B\u0003%1\u0005C\u0003)\u0001\u0011\u0005\u0011\u0006C\u0004\u001b\u0001\t\u0007I\u0011A\u0017\t\r9\u0002\u0001\u0015!\u0003\u0018\u0011\u0015y\u0003A\"\u00011\u0011\u0015!\u0004\u0001\"\u00016\u0005uiUm]:bO\u0016$\u0015n\u001d9bi\u000eDWM]\"p]\u001aLw-\u001e:bi>\u0014(BA\u0006\r\u0003!!\u0017n\u001d9bi\u000eD'\"A\u0007\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001\u0001\u0003\u0005\u0002\u0012)5\t!CC\u0001\u0014\u0003\u0015\u00198-\u00197b\u0013\t)\"C\u0001\u0004B]f\u0014VMZ\u0001\b?\u000e|gNZ5h!\tAr$D\u0001\u001a\u0015\tQ2$\u0001\u0004d_:4\u0017n\u001a\u0006\u00039u\t\u0001\u0002^=qKN\fg-\u001a\u0006\u0002=\u0005\u00191m\\7\n\u0005\u0001J\"AB\"p]\u001aLw-A\u0007qe\u0016\u0014X-];jg&$Xm]\u000b\u0002GA\u0011A%J\u0007\u0002\u0015%\u0011aE\u0003\u0002\u0018\t&\u001c\b/\u0019;dQ\u0016\u0014\bK]3sKF,\u0018n]5uKN\fa\u0002\u001d:fe\u0016\fX/[:ji\u0016\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0004U-b\u0003C\u0001\u0013\u0001\u0011\u00151B\u00011\u0001\u0018\u0011\u0015\tC\u00011\u0001$+\u00059\u0012aB2p]\u001aLw\rI\u0001\u000bI&\u001c\b/\u0019;dQ\u0016\u0014H#A\u0019\u0011\u0005\u0011\u0012\u0014BA\u001a\u000b\u0005EiUm]:bO\u0016$\u0015n\u001d9bi\u000eDWM]\u0001\u0012G>tg-[4ve\u0016,\u00050Z2vi>\u0014H#\u0001\u001c\u0011\u0005\u0011:\u0014B\u0001\u001d\u000b\u0005m)\u00050Z2vi>\u00148+\u001a:wS\u000e,7i\u001c8gS\u001e,(/\u0019;pe\u0002")
public abstract class MessageDispatcherConfigurator {
    private final DispatcherPrerequisites prerequisites;
    private final Config config;

    public DispatcherPrerequisites prerequisites() {
        return this.prerequisites;
    }

    public Config config() {
        return this.config;
    }

    public abstract MessageDispatcher dispatcher();

    public ExecutorServiceConfigurator configureExecutor() {
        String string = this.config().getString("executor");
        switch (string == null ? 0 : string.hashCode()) {
            case 2119179999: {
                if (!"default-executor".equals(string)) break;
                return new DefaultExecutorServiceConfigurator(this.config().getConfig("default-executor"), this.prerequisites(), this.configurator$1(this.config().getString("default-executor.fallback")));
            }
        }
        return this.configurator$1(string);
    }

    private final ExecutorServiceConfigurator configurator$1(String executor2) {
        String string;
        block10: {
            string = executor2;
            switch (string == null ? 0 : string.hashCode()) {
                case -1394683829: {
                    if ("fork-join-executor".equals(string)) {
                        break;
                    }
                    break block10;
                }
                case -70555423: {
                    if ("thread-pool-executor".equals(string)) {
                        return new ThreadPoolExecutorConfigurator(this.config().getConfig("thread-pool-executor"), this.prerequisites());
                    }
                    break block10;
                }
                case 0: {
                    if (string == null || "".equals(string)) {
                        break;
                    }
                    break block10;
                }
                case 1768732543: {
                    if ("affinity-pool-executor".equals(string)) {
                        return new AffinityPoolConfigurator(this.config().getConfig("affinity-pool-executor"), this.prerequisites());
                    }
                    break block10;
                }
                default: {
                    break block10;
                }
            }
            return new ForkJoinExecutorConfigurator(this.config().getConfig("fork-join-executor"), this.prerequisites());
        }
        $colon$colon<Nothing$> args2 = new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Config.class), this.config())), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(DispatcherPrerequisites.class), this.prerequisites())), Nil$.MODULE$));
        return (ExecutorServiceConfigurator)((Object)this.prerequisites().dynamicAccess().createInstanceFor(string, args2, ClassTag$.MODULE$.apply(ExecutorServiceConfigurator.class)).recover(new Serializable(this, string){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ MessageDispatcherConfigurator $outer;
            private final String x1$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("Cannot instantiate ExecutorServiceConfigurator (\"executor = [%s]\"), defined in [%s],\n                make sure it has an accessible constructor with a [%s,%s] signature"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.x1$1, this.$outer.config().getString("id"), Config.class, DispatcherPrerequisites.class})), A1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.x1$1 = x1$1;
            }
        }).get());
    }

    public MessageDispatcherConfigurator(Config _config, DispatcherPrerequisites prerequisites) {
        this.prerequisites = prerequisites;
        this.config = new CachingConfig(_config);
    }
}

