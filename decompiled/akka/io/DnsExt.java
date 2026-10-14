/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.Deploy$;
import akka.actor.ExtendedActorSystem;
import akka.actor.Props$;
import akka.annotation.InternalApi;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.io.Dns;
import akka.io.DnsProvider;
import akka.io.IO;
import com.typesafe.config.Config;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u00055c\u0001B\u0011#\u0001\u001dB\u0001B\u000e\u0001\u0003\u0006\u0004%\ta\u000e\u0005\t}\u0001\u0011\t\u0011)A\u0005q!Aq\b\u0001B\u0001B\u0003%\u0001\t\u0003\u0005L\u0001\t\u0005\t\u0015!\u0003A\u0011\u0019a\u0005\u0001\"\u0001%\u001b\"9!\u000b\u0001b\u0001\n\u0013\u0019\u0006BB1\u0001A\u0003%A\u000b\u0003\u0004c\u0001\u0011\u0005Ae\u0019\u0005\u0006\u0019\u0002!\t\u0001\u001c\u0004\u0005_\u0002\u0001\u0001\u000f\u0003\u0005r\u0015\t\u0005\t\u0015!\u0003s\u0011!y$B!A!\u0002\u0013\u0001\u0005B\u0002'\u000b\t\u0003\u00011\u0010\u0003\u0004M\u0015\u0011\u0005\u0011\u0011\u0001\u0005\n\u0003\u000bQ!\u0019!C\u0001\u0003\u000fAq!!\u0003\u000bA\u0003%\u0001\tC\u0005\u0002\f)\u0011\r\u0011\"\u0001\u0002\b!9\u0011Q\u0002\u0006!\u0002\u0013\u0001\u0005\"CA\b\u0015\t\u0007I\u0011AA\t\u0011\u001d\t\u0019B\u0003Q\u0001\nID\u0011\"!\u0006\u000b\u0005\u0004%\t!a\u0002\t\u000f\u0005]!\u0002)A\u0005\u0001\"9\u0011\u0011\u0004\u0006\u0005B\u0005m\u0001\"CA\u0014\u0001\t\u0007I\u0011AA\u0015\u0011\u001d\tY\u0003\u0001Q\u0001\nqD\u0011\"!\f\u0001\u0005\u0004%\t!a\f\t\u0011\u0005]\u0002\u0001)A\u0005\u0003cA\u0011\"!\u000f\u0001\u0005\u0004%\t!a\u000f\t\u0011\u0005\r\u0003\u0001)A\u0005\u0003{A\u0011\"!\u0012\u0001\u0005\u0004%\t!a\u0012\t\u000f\u0005%\u0003\u0001)A\u0005=\"9\u00111\n\u0001\u0005\u0002\u0005\u001d#A\u0002#og\u0016CHO\u0003\u0002$I\u0005\u0011\u0011n\u001c\u0006\u0002K\u0005!\u0011m[6b\u0007\u0001\u00192\u0001\u0001\u0015/!\tIC&D\u0001+\u0015\u0005Y\u0013!B:dC2\f\u0017BA\u0017+\u0005\u0019\te.\u001f*fMB\u0011qf\r\b\u0003aEj\u0011AI\u0005\u0003e\t\n!!S(\n\u0005Q*$!C#yi\u0016t7/[8o\u0015\t\u0011$%\u0001\u0004tsN$X-\\\u000b\u0002qA\u0011\u0011\bP\u0007\u0002u)\u00111\bJ\u0001\u0006C\u000e$xN]\u0005\u0003{i\u00121#\u0012=uK:$W\rZ!di>\u00148+_:uK6\fqa]=ti\u0016l\u0007%\u0001\u0007sKN|GN^3s\u001d\u0006lW\r\u0005\u0002B\u0011:\u0011!I\u0012\t\u0003\u0007*j\u0011\u0001\u0012\u0006\u0003\u000b\u001a\na\u0001\u0010:p_Rt\u0014BA$+\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011J\u0013\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u001dS\u0013aC7b]\u0006<WM\u001d(b[\u0016\fa\u0001P5oSRtD\u0003\u0002(P!F\u0003\"\u0001\r\u0001\t\u000bY*\u0001\u0019\u0001\u001d\t\u000b}*\u0001\u0019\u0001!\t\u000b-+\u0001\u0019\u0001!\u0002\u0011\u0005\u001c\u0018P\\2E]N,\u0012\u0001\u0016\t\u0005+r\u0003e,D\u0001W\u0015\t9\u0006,\u0001\u0006d_:\u001cWO\u001d:f]RT!!\u0017.\u0002\tU$\u0018\u000e\u001c\u0006\u00027\u0006!!.\u0019<b\u0013\tifKA\tD_:\u001cWO\u001d:f]RD\u0015m\u001d5NCB\u0004\"!O0\n\u0005\u0001T$\u0001C!di>\u0014(+\u001a4\u0002\u0013\u0005\u001c\u0018P\\2E]N\u0004\u0013\u0001\u00047pC\u0012\f5/\u001f8d\t:\u001cHC\u00010e\u0011\u0015Y\u0005\u00021\u0001AQ\tAa\r\u0005\u0002hU6\t\u0001N\u0003\u0002jI\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005-D'aC%oi\u0016\u0014h.\u00197Ba&$\"AT7\t\u000bYJ\u0001\u0019\u0001\u001d)\u0005%1'\u0001C*fiRLgnZ:\u0014\u0005)A\u0013AB2p]\u001aLw\r\u0005\u0002ts6\tAO\u0003\u0002rk*\u0011ao^\u0001\tif\u0004Xm]1gK*\t\u00010A\u0002d_6L!A\u001f;\u0003\r\r{gNZ5h)\rahp \t\u0003{*i\u0011\u0001\u0001\u0005\u0006c6\u0001\rA\u001d\u0005\u0006\u007f5\u0001\r\u0001\u0011\u000b\u0004y\u0006\r\u0001\"B9\u000f\u0001\u0004\u0011\u0018A\u0003#jgB\fGo\u00195feV\t\u0001)A\u0006ESN\u0004\u0018\r^2iKJ\u0004\u0013\u0001\u0003*fg>dg/\u001a:\u0002\u0013I+7o\u001c7wKJ\u0004\u0013A\u0004*fg>dg/\u001a:D_:4\u0017nZ\u000b\u0002e\u0006y!+Z:pYZ,'oQ8oM&<\u0007%\u0001\nQe>4\u0018\u000eZ3s\u001f\nTWm\u0019;OC6,\u0017a\u0005)s_ZLG-\u001a:PE*,7\r\u001e(b[\u0016\u0004\u0013\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005u\u0001\u0003BA\u0010\u0003Ki!!!\t\u000b\u0007\u0005\r\",\u0001\u0003mC:<\u0017bA%\u0002\"\u0005A1+\u001a;uS:<7/F\u0001}\u0003%\u0019V\r\u001e;j]\u001e\u001c\b%\u0001\u0005qe>4\u0018\u000eZ3s+\t\t\t\u0004E\u00021\u0003gI1!!\u000e#\u0005-!en\u001d)s_ZLG-\u001a:\u0002\u0013A\u0014xN^5eKJ\u0004\u0013!B2bG\",WCAA\u001f!\r\u0001\u0014qH\u0005\u0004\u0003\u0003\u0012#a\u0001#og\u000611-Y2iK\u0002\nq!\\1oC\u001e,'/F\u0001_\u0003!i\u0017M\\1hKJ\u0004\u0013aC4fiJ+7o\u001c7wKJ\u0004")
public class DnsExt
implements IO.Extension {
    private final ExtendedActorSystem system;
    private final ConcurrentHashMap<String, ActorRef> asyncDns;
    private final Settings Settings;
    private final DnsProvider provider;
    private final Dns cache;
    private final ActorRef manager;

    public ExtendedActorSystem system() {
        return this.system;
    }

    private ConcurrentHashMap<String, ActorRef> asyncDns() {
        return this.asyncDns;
    }

    @InternalApi
    public ActorRef loadAsyncDns(String managerName) {
        return this.asyncDns().computeIfAbsent(managerName, new Function<String, ActorRef>(this, managerName){
            private final /* synthetic */ DnsExt $outer;
            private final String managerName$1;

            public <V> Function<V, ActorRef> compose(Function<? super V, ? extends String> x$1) {
                return Function.super.compose(x$1);
            }

            public <V> Function<String, V> andThen(Function<? super ActorRef, ? extends V> x$1) {
                return Function.super.andThen(x$1);
            }

            public ActorRef apply(String r) {
                Settings settings = this.$outer.new Settings(this.$outer.system().settings().config().getConfig("akka.io.dns"), "async-dns");
                DnsProvider provider = (DnsProvider)this.$outer.system().dynamicAccess().createInstanceFor(settings.ProviderObjectName(), Nil$.MODULE$, ClassTag$.MODULE$.apply(DnsProvider.class)).get();
                Logging$.MODULE$.apply(this.$outer.system(), DnsExt.class, LogSource$.MODULE$.fromAnyClass()).info("Creating async dns resolver {} with manager name {}", settings.Resolver(), this.managerName$1);
                return this.$outer.system().systemActorOf(Props$.MODULE$.apply(provider.managerClass(), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{settings.Resolver(), this.$outer.system(), settings.ResolverConfig(), provider.cache(), settings.Dispatcher(), provider})).withDeploy(Deploy$.MODULE$.local()).withDispatcher(settings.Dispatcher()), this.managerName$1);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.managerName$1 = managerName$1;
            }
        });
    }

    public Settings Settings() {
        return this.Settings;
    }

    public DnsProvider provider() {
        return this.provider;
    }

    public Dns cache() {
        return this.cache;
    }

    @Override
    public ActorRef manager() {
        return this.manager;
    }

    public ActorRef getResolver() {
        return this.manager();
    }

    public DnsExt(ExtendedActorSystem system, String resolverName, String managerName) {
        this.system = system;
        this.asyncDns = new ConcurrentHashMap();
        this.Settings = new Settings(system.settings().config().getConfig("akka.io.dns"), resolverName);
        this.provider = (DnsProvider)system.dynamicAccess().createInstanceFor(this.Settings().ProviderObjectName(), Nil$.MODULE$, ClassTag$.MODULE$.apply(DnsProvider.class)).get();
        this.cache = this.provider().cache();
        this.manager = system.systemActorOf(Props$.MODULE$.apply(this.provider().managerClass(), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this})).withDeploy(Deploy$.MODULE$.local()).withDispatcher(this.Settings().Dispatcher()), managerName);
    }

    @InternalApi
    public DnsExt(ExtendedActorSystem system) {
        this(system, system.settings().config().getString("akka.io.dns.resolver"), "IO-DNS");
    }

    public class Settings {
        private final String Dispatcher;
        private final String Resolver;
        private final Config ResolverConfig;
        private final String ProviderObjectName;

        public String Dispatcher() {
            return this.Dispatcher;
        }

        public String Resolver() {
            return this.Resolver;
        }

        public Config ResolverConfig() {
            return this.ResolverConfig;
        }

        public String ProviderObjectName() {
            return this.ProviderObjectName;
        }

        public String toString() {
            return new StringBuilder(16).append("Settings(").append(this.Dispatcher()).append(", ").append(this.Resolver()).append(", ").append(this.ResolverConfig()).append(", ").append(this.ProviderObjectName()).append(")").toString();
        }

        public /* synthetic */ DnsExt akka$io$DnsExt$Settings$$$outer() {
            return DnsExt.this;
        }

        public Settings(Config config, String resolverName) {
            if (DnsExt.this == null) {
                throw null;
            }
            this.Dispatcher = config.getString("dispatcher");
            this.Resolver = resolverName;
            this.ResolverConfig = config.getConfig(this.Resolver());
            this.ProviderObjectName = this.ResolverConfig().getString("provider-object");
        }

        public Settings(Config config) {
            this(config, config.getString("resolver"));
        }
    }
}

