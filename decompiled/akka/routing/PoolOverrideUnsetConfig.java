/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.SupervisorStrategy;
import akka.routing.NoRouter$;
import akka.routing.Pool;
import akka.routing.Pool$;
import akka.routing.Resizer;
import akka.routing.RouterConfig;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q2\u0001\"\u0002\u0004\u0011\u0002\u0007\u0005\u0001B\u0003\u0005\u0006-\u0001!\t\u0001\u0007\u0005\u00069\u0001!)!\b\u0005\u0006G\u00011\t\u0001\n\u0005\u0006k\u00011\tA\u000e\u0002\u0018!>|Gn\u0014<feJLG-Z+og\u0016$8i\u001c8gS\u001eT!a\u0002\u0005\u0002\u000fI|W\u000f^5oO*\t\u0011\"\u0001\u0003bW.\fWCA\u0006('\r\u0001AB\u0005\t\u0003\u001bAi\u0011A\u0004\u0006\u0002\u001f\u0005)1oY1mC&\u0011\u0011C\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0005M!R\"\u0001\u0004\n\u0005U1!\u0001\u0002)p_2\fa\u0001J5oSR$3\u0001\u0001\u000b\u00023A\u0011QBG\u0005\u000379\u0011A!\u00168ji\u0006\u0019rN^3se&$W-\u00168tKR\u001cuN\u001c4jOR\u0011a$\t\t\u0003'}I!\u0001\t\u0004\u0003\u0019I{W\u000f^3s\u0007>tg-[4\t\u000b\t\u0012\u0001\u0019\u0001\u0010\u0002\u000b=$\b.\u001a:\u0002-]LG\u000f[*va\u0016\u0014h/[:peN#(/\u0019;fOf$\"!J\u0017\u0011\u0005\u0019:C\u0002\u0001\u0003\u0006Q\u0001\u0011\r!\u000b\u0002\u0002)F\u0011!F\u0005\t\u0003\u001b-J!\u0001\f\b\u0003\u000f9{G\u000f[5oO\")af\u0001a\u0001_\u0005A1\u000f\u001e:bi\u0016<\u0017\u0010\u0005\u00021g5\t\u0011G\u0003\u00023\u0011\u0005)\u0011m\u0019;pe&\u0011A'\r\u0002\u0013'V\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u00170A\u0006xSRD'+Z:ju\u0016\u0014HCA\u00138\u0011\u0015AD\u00011\u0001:\u0003\u001d\u0011Xm]5{KJ\u0004\"a\u0005\u001e\n\u0005m2!a\u0002*fg&TXM\u001d")
public interface PoolOverrideUnsetConfig<T extends Pool>
extends Pool {
    public static /* synthetic */ RouterConfig overrideUnsetConfig$(PoolOverrideUnsetConfig $this, RouterConfig other) {
        return $this.overrideUnsetConfig(other);
    }

    default public RouterConfig overrideUnsetConfig(RouterConfig other) {
        RouterConfig routerConfig = other;
        NoRouter$ noRouter$ = NoRouter$.MODULE$;
        if (!(routerConfig != null ? !routerConfig.equals(noRouter$) : noRouter$ != null)) {
            return this;
        }
        RouterConfig routerConfig2 = other;
        if (routerConfig2 instanceof Pool) {
            PoolOverrideUnsetConfig wssConf;
            Pool pool = (Pool)routerConfig2;
            PoolOverrideUnsetConfig poolOverrideUnsetConfig = wssConf = this.supervisorStrategy() == Pool$.MODULE$.defaultSupervisorStrategy() && pool.supervisorStrategy() != Pool$.MODULE$.defaultSupervisorStrategy() ? (PoolOverrideUnsetConfig)this.withSupervisorStrategy(pool.supervisorStrategy()) : this;
            if (wssConf.resizer().isEmpty() && pool.resizer().isDefined()) {
                return wssConf.withResizer(pool.resizer().get());
            }
            return wssConf;
        }
        return this;
    }

    public T withSupervisorStrategy(SupervisorStrategy var1);

    public T withResizer(Resizer var1);

    public static void $init$(PoolOverrideUnsetConfig $this) {
    }
}

