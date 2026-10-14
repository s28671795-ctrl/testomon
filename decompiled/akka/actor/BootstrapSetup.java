/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.BootstrapSetup$;
import akka.actor.ProviderSelection;
import akka.actor.setup.Setup;
import com.typesafe.config.Config;
import java.util.Optional;
import scala.Option;
import scala.Some;
import scala.concurrent.ExecutionContext;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005s!B\u000e\u001d\u0011\u0003\tc!B\u0012\u001d\u0011\u0003!\u0003\"B\u0016\u0002\t\u0003a\u0003\"B\u0017\u0002\t\u0003q\u0003\"B\u0017\u0002\t\u00031\b\"B\u0017\u0002\t\u0003Q\b\"\u0002?\u0002\t\u0003i\bB\u0002?\u0002\t\u0003\t\u0019\u0002C\u0003}\u0003\u0011\u0005a\u0006C\u0005\u0002\u0018\u0005\t\n\u0011\"\u0003\u0002\u001a!I\u0011qF\u0001\u0012\u0002\u0013%\u0011\u0011\u0007\u0005\n\u0003k\t\u0011\u0013!C\u0005\u0003oA\u0011\"a\u000f\u0002#\u0003%I!!\u0010\u0007\t\rb\"\u0001\r\u0005\to5\u0011)\u0019!C\u0001q!AA)\u0004B\u0001B\u0003%\u0011\b\u0003\u0005F\u001b\t\u0015\r\u0011\"\u0001G\u0011!\tVB!A!\u0002\u00139\u0005\u0002\u0003*\u000e\u0005\u000b\u0007I\u0011A*\t\u0011mk!\u0011!Q\u0001\nQC\u0001\u0002X\u0007\u0003\u0006\u0004%\t!\u0018\u0005\tE6\u0011\t\u0011)A\u0005=\")1&\u0004C\u0005G\")\u0001.\u0004C\u0001S\")1.\u0004C\u0001Y\")a.\u0004C\u0001_\")!/\u0004C\u0001g\u0006q!i\\8ugR\u0014\u0018\r]*fiV\u0004(BA\u000f\u001f\u0003\u0015\t7\r^8s\u0015\u0005y\u0012\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002#\u00035\tAD\u0001\bC_>$8\u000f\u001e:baN+G/\u001e9\u0014\u0005\u0005)\u0003C\u0001\u0014*\u001b\u00059#\"\u0001\u0015\u0002\u000bM\u001c\u0017\r\\1\n\u0005):#AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002C\u0005)\u0011\r\u001d9msR\tq\u0006\u0005\u0002#\u001bM\u0011Q\"\r\t\u0003eUj\u0011a\r\u0006\u0003iq\tQa]3ukBL!AN\u001a\u0003\u000bM+G/\u001e9\u0002\u0017\rd\u0017m]:M_\u0006$WM]\u000b\u0002sA\u0019aE\u000f\u001f\n\u0005m:#AB(qi&|g\u000e\u0005\u0002>\u00056\taH\u0003\u0002@\u0001\u0006!A.\u00198h\u0015\u0005\t\u0015\u0001\u00026bm\u0006L!a\u0011 \u0003\u0017\rc\u0017m]:M_\u0006$WM]\u0001\rG2\f7o\u001d'pC\u0012,'\u000fI\u0001\u0007G>tg-[4\u0016\u0003\u001d\u00032A\n\u001eI!\tIu*D\u0001K\u0015\t)5J\u0003\u0002M\u001b\u0006AA/\u001f9fg\u00064WMC\u0001O\u0003\r\u0019w.\\\u0005\u0003!*\u0013aaQ8oM&<\u0017aB2p]\u001aLw\rI\u0001\u0018I\u00164\u0017-\u001e7u\u000bb,7-\u001e;j_:\u001cuN\u001c;fqR,\u0012\u0001\u0016\t\u0004Mi*\u0006C\u0001,Z\u001b\u00059&B\u0001-(\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u00035^\u0013\u0001#\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;\u00021\u0011,g-Y;mi\u0016CXmY;uS>t7i\u001c8uKb$\b%\u0001\tbGR|'OU3g!J|g/\u001b3feV\ta\fE\u0002'u}\u0003\"A\t1\n\u0005\u0005d\"!\u0005)s_ZLG-\u001a:TK2,7\r^5p]\u0006\t\u0012m\u0019;peJ+g\r\u0015:pm&$WM\u001d\u0011\u0015\u000b=\"WMZ4\t\u000f]2\u0002\u0013!a\u0001s!9QI\u0006I\u0001\u0002\u00049\u0005b\u0002*\u0017!\u0003\u0005\r\u0001\u0016\u0005\b9Z\u0001\n\u00111\u0001_\u0003=9\u0018\u000e\u001e5DY\u0006\u001c8\u000f\\8bI\u0016\u0014HCA\u0018k\u0011\u00159t\u00031\u0001=\u0003)9\u0018\u000e\u001e5D_:4\u0017n\u001a\u000b\u0003_5DQ!\u0012\rA\u0002!\u000b1d^5uQ\u0012+g-Y;mi\u0016CXmY;uS>t7i\u001c8uKb$HCA\u0018q\u0011\u0015\t\u0018\u00041\u0001V\u0003A)\u00070Z2vi&|gnQ8oi\u0016DH/\u0001\u000bxSRD\u0017i\u0019;peJ+g\r\u0015:pm&$WM\u001d\u000b\u0003_QDQ!\u001e\u000eA\u0002}\u000bAA\\1nKR!qf\u001e=z\u0011\u00159D\u00011\u0001:\u0011\u0015)E\u00011\u0001H\u0011\u0015\u0011F\u00011\u0001U)\ty3\u0010C\u0003F\u000b\u0001\u0007\u0001*\u0001\u0004de\u0016\fG/\u001a\u000b\u0007_y\fY!a\u0004\t\u000b]2\u0001\u0019A@\u0011\u000b\u0005\u0005\u0011q\u0001\u001f\u000e\u0005\u0005\r!bAA\u0003\u0001\u0006!Q\u000f^5m\u0013\u0011\tI!a\u0001\u0003\u0011=\u0003H/[8oC2Da!\u0012\u0004A\u0002\u00055\u0001#BA\u0001\u0003\u000fA\u0005B\u0002*\u0007\u0001\u0004\t\t\u0002E\u0003\u0002\u0002\u0005\u001dQ\u000bF\u00020\u0003+AQ!R\u0004A\u0002!\u000b1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\nTCAA\u000eU\rI\u0014QD\u0016\u0003\u0003?\u0001B!!\t\u0002,5\u0011\u00111\u0005\u0006\u0005\u0003K\t9#A\u0005v]\u000eDWmY6fI*\u0019\u0011\u0011F\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002.\u0005\r\"!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII*\"!a\r+\u0007\u001d\u000bi\"A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HeM\u000b\u0003\u0003sQ3\u0001VA\u000f\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u0011q\b\u0016\u0004=\u0006u\u0001")
public final class BootstrapSetup
extends Setup {
    private final Option<ClassLoader> classLoader;
    private final Option<Config> config;
    private final Option<ExecutionContext> defaultExecutionContext;
    private final Option<ProviderSelection> actorRefProvider;

    public static BootstrapSetup create() {
        return BootstrapSetup$.MODULE$.create();
    }

    public static BootstrapSetup create(Config config) {
        return BootstrapSetup$.MODULE$.create(config);
    }

    public static BootstrapSetup create(Optional<ClassLoader> classLoader, Optional<Config> config, Optional<ExecutionContext> defaultExecutionContext) {
        return BootstrapSetup$.MODULE$.create(classLoader, config, defaultExecutionContext);
    }

    public static BootstrapSetup apply(Config config) {
        return BootstrapSetup$.MODULE$.apply(config);
    }

    public static BootstrapSetup apply(Option<ClassLoader> classLoader, Option<Config> config, Option<ExecutionContext> defaultExecutionContext) {
        return BootstrapSetup$.MODULE$.apply(classLoader, config, defaultExecutionContext);
    }

    public static BootstrapSetup apply() {
        return BootstrapSetup$.MODULE$.apply();
    }

    public Option<ClassLoader> classLoader() {
        return this.classLoader;
    }

    public Option<Config> config() {
        return this.config;
    }

    public Option<ExecutionContext> defaultExecutionContext() {
        return this.defaultExecutionContext;
    }

    public Option<ProviderSelection> actorRefProvider() {
        return this.actorRefProvider;
    }

    public BootstrapSetup withClassloader(ClassLoader classLoader) {
        return new BootstrapSetup(new Some<ClassLoader>(classLoader), this.config(), this.defaultExecutionContext(), this.actorRefProvider());
    }

    public BootstrapSetup withConfig(Config config) {
        return new BootstrapSetup(this.classLoader(), new Some<Config>(config), this.defaultExecutionContext(), this.actorRefProvider());
    }

    public BootstrapSetup withDefaultExecutionContext(ExecutionContext executionContext) {
        return new BootstrapSetup(this.classLoader(), this.config(), new Some<ExecutionContext>(executionContext), this.actorRefProvider());
    }

    public BootstrapSetup withActorRefProvider(ProviderSelection name) {
        return new BootstrapSetup(this.classLoader(), this.config(), this.defaultExecutionContext(), new Some<ProviderSelection>(name));
    }

    public BootstrapSetup(Option<ClassLoader> classLoader, Option<Config> config, Option<ExecutionContext> defaultExecutionContext, Option<ProviderSelection> actorRefProvider) {
        this.classLoader = classLoader;
        this.config = config;
        this.defaultExecutionContext = defaultExecutionContext;
        this.actorRefProvider = actorRefProvider;
    }
}

