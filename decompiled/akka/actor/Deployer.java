/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPath;
import akka.actor.ActorPath$;
import akka.actor.ActorSystem;
import akka.actor.Deploy;
import akka.actor.Deploy$;
import akka.actor.Deployer$;
import akka.actor.DynamicAccess;
import akka.actor.InvalidActorNameException;
import akka.actor.NoScopeGiven$;
import akka.routing.NoRouter$;
import akka.routing.RouterConfig;
import akka.util.WildcardIndex;
import akka.util.WildcardIndex$;
import akka.util.ccompat.package$JavaConverters$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.Iterable;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Nothing$;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\u0005}b!B\u000b\u0017\u0001aQ\u0002\u0002C\u0011\u0001\u0005\u000b\u0007I\u0011A\u0012\t\u00111\u0002!\u0011!Q\u0001\n\u0011B\u0001\"\f\u0001\u0003\u0006\u0004%\tA\f\u0005\te\u0001\u0011\t\u0011)A\u0005_!)1\u0007\u0001C\u0001i!9\u0001\b\u0001b\u0001\n\u0013I\u0004B\u0002#\u0001A\u0003%!\bC\u0004F\u0001\t\u0007I\u0011\u0002$\t\rm\u0003\u0001\u0015!\u0003H\u0011\u001di\u0004A1A\u0005\neBa\u0001\u0018\u0001!\u0002\u0013Q\u0004bB/\u0001\u0005\u0004%\t\"\u000f\u0005\u0007=\u0002\u0001\u000b\u0011\u0002\u001e\t\u000f}\u0003!\u0019!C\u0001A\"1q\u000e\u0001Q\u0001\n\u0005DQ\u0001\u001d\u0001\u0005\u0002EDQ\u0001\u001d\u0001\u0005\u0002iDq!a\u0003\u0001\t\u0003\ti\u0001C\u0004\u0002\u001a\u0001!\t!a\u0007\t\u000f\u0005\r\u0002\u0001\"\u0005\u0002&\tAA)\u001a9m_f,'O\u0003\u0002\u00181\u0005)\u0011m\u0019;pe*\t\u0011$\u0001\u0003bW.\f7C\u0001\u0001\u001c!\tar$D\u0001\u001e\u0015\u0005q\u0012!B:dC2\f\u0017B\u0001\u0011\u001e\u0005\u0019\te.\u001f*fM\u0006A1/\u001a;uS:<7o\u0001\u0001\u0016\u0003\u0011\u0002\"!J\u0015\u000f\u0005\u0019:S\"\u0001\f\n\u0005!2\u0012aC!di>\u00148+_:uK6L!AK\u0016\u0003\u0011M+G\u000f^5oONT!\u0001\u000b\f\u0002\u0013M,G\u000f^5oON\u0004\u0013!\u00043z]\u0006l\u0017nY!dG\u0016\u001c8/F\u00010!\t1\u0003'\u0003\u00022-\tiA)\u001f8b[&\u001c\u0017iY2fgN\fa\u0002Z=oC6L7-Q2dKN\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0004kY:\u0004C\u0001\u0014\u0001\u0011\u0015\tS\u00011\u0001%\u0011\u0015iS\u00011\u00010\u00039\u0011Xm]5{KJ,e.\u00192mK\u0012,\u0012A\u000f\t\u0003w\tk\u0011\u0001\u0010\u0006\u0003{y\naaY8oM&<'BA A\u0003!!\u0018\u0010]3tC\u001a,'\"A!\u0002\u0007\r|W.\u0003\u0002Dy\t11i\u001c8gS\u001e\fqB]3tSj,'/\u00128bE2,G\rI\u0001\fI\u0016\u0004Hn\\=nK:$8/F\u0001H!\rA\u0015kU\u0007\u0002\u0013*\u0011!jS\u0001\u0007CR|W.[2\u000b\u00051k\u0015AC2p]\u000e,(O]3oi*\u0011ajT\u0001\u0005kRLGNC\u0001Q\u0003\u0011Q\u0017M^1\n\u0005IK%aD!u_6L7MU3gKJ,gnY3\u0011\u0007Q3\u0006,D\u0001V\u0015\tq\u0005$\u0003\u0002X+\niq+\u001b7eG\u0006\u0014H-\u00138eKb\u0004\"AJ-\n\u0005i3\"A\u0002#fa2|\u00170\u0001\u0007eKBdw._7f]R\u001c\b%A\u0004d_:4\u0017n\u001a\u0011\u0002\u000f\u0011,g-Y;mi\u0006AA-\u001a4bk2$\b%A\ts_V$XM\u001d+za\u0016l\u0015\r\u001d9j]\u001e,\u0012!\u0019\t\u0005E&dGN\u0004\u0002dOB\u0011A-H\u0007\u0002K*\u0011aMI\u0001\u0007yI|w\u000e\u001e \n\u0005!l\u0012A\u0002)sK\u0012,g-\u0003\u0002kW\n\u0019Q*\u00199\u000b\u0005!l\u0002C\u00012n\u0013\tq7N\u0001\u0004TiJLgnZ\u0001\u0013e>,H/\u001a:UsB,W*\u00199qS:<\u0007%\u0001\u0004m_>\\W\u000f\u001d\u000b\u0003eV\u00042\u0001H:Y\u0013\t!XD\u0001\u0004PaRLwN\u001c\u0005\u0006mB\u0001\ra^\u0001\u0005a\u0006$\b\u000e\u0005\u0002'q&\u0011\u0011P\u0006\u0002\n\u0003\u000e$xN\u001d)bi\"$\"A]>\t\u000bY\f\u0002\u0019\u0001?\u0011\tu\f)\u0001\u001c\b\u0004}\u0006\u0005aB\u00013\u0000\u0013\u0005q\u0012bAA\u0002;\u00059\u0001/Y2lC\u001e,\u0017\u0002BA\u0004\u0003\u0013\u0011\u0001\"\u0013;fe\u0006\u0014G.\u001a\u0006\u0004\u0003\u0007i\u0012A\u00023fa2|\u0017\u0010\u0006\u0003\u0002\u0010\u0005U\u0001c\u0001\u000f\u0002\u0012%\u0019\u00111C\u000f\u0003\tUs\u0017\u000e\u001e\u0005\u0007\u0003/\u0011\u0002\u0019\u0001-\u0002\u0003\u0011\f1\u0002]1sg\u0016\u001cuN\u001c4jOR)!/!\b\u0002\"!1\u0011qD\nA\u00021\f1a[3z\u0011\u0015i4\u00031\u0001;\u0003I\u0019'/Z1uKJ{W\u000f^3s\u0007>tg-[4\u0015\u0015\u0005\u001d\u00121GA\u001c\u0003s\tY\u0004\u0005\u0003\u0002*\u0005=RBAA\u0016\u0015\r\ti\u0003G\u0001\be>,H/\u001b8h\u0013\u0011\t\t$a\u000b\u0003\u0019I{W\u000f^3s\u0007>tg-[4\t\r\u0005UB\u00031\u0001m\u0003)\u0011x.\u001e;feRK\b/\u001a\u0005\u0007\u0003?!\u0002\u0019\u00017\t\u000bu\"\u0002\u0019\u0001\u001e\t\r\u0005uB\u00031\u0001;\u0003)!W\r\u001d7ps6,g\u000e\u001e")
public class Deployer {
    private final ActorSystem.Settings settings;
    private final DynamicAccess dynamicAccess;
    private final Config resizerEnabled;
    private final AtomicReference<WildcardIndex<Deploy>> deployments;
    private final Config config;
    private final Config default;
    private final Map<String, String> routerTypeMapping;

    public ActorSystem.Settings settings() {
        return this.settings;
    }

    public DynamicAccess dynamicAccess() {
        return this.dynamicAccess;
    }

    private Config resizerEnabled() {
        return this.resizerEnabled;
    }

    private AtomicReference<WildcardIndex<Deploy>> deployments() {
        return this.deployments;
    }

    private Config config() {
        return this.config;
    }

    public Config default() {
        return this.default;
    }

    public Map<String, String> routerTypeMapping() {
        return this.routerTypeMapping;
    }

    public Option<Deploy> lookup(ActorPath path) {
        return this.lookup((Iterable)path.elements().drop(1));
    }

    public Option<Deploy> lookup(Iterable<String> path) {
        return this.deployments().get().find(path);
    }

    public void deploy(Deploy d) {
        this.add$1((String[])ArrayOps$.MODULE$.drop$extension(Predef$.MODULE$.refArrayOps(d.path().split("/")), 1), d);
    }

    public Option<Deploy> parseConfig(String key, Config config) {
        Config deployment = config.withFallback(this.default());
        RouterConfig router = this.createRouterConfig(deployment.getString("router"), key, config, deployment);
        String dispatcher = deployment.getString("dispatcher");
        String mailbox = deployment.getString("mailbox");
        return new Some<Deploy>(Deploy$.MODULE$.apply(key, deployment, router, NoScopeGiven$.MODULE$, dispatcher, mailbox));
    }

    public RouterConfig createRouterConfig(String routerType, String key, Config config, Config deployment) {
        String string = routerType;
        String string2 = "from-code";
        if (!(string != null ? !string.equals(string2) : string2 != null)) {
            return NoRouter$.MODULE$;
        }
        Config deployment2 = config.hasPath("resizer") && !deployment.getBoolean("resizer.enabled") ? this.resizerEnabled().withFallback(deployment) : deployment;
        String fqn = (String)this.routerTypeMapping().getOrElse(routerType, (Function0<String> & Serializable)() -> routerType);
        $colon$colon<Nothing$> args1 = new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Config.class), deployment2)), Nil$.MODULE$);
        $colon$colon<Nothing$> args2 = new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Config.class), deployment2)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(DynamicAccess.class), this.dynamicAccess())), Nil$.MODULE$));
        return this.dynamicAccess().createInstanceFor(fqn, args1, ClassTag$.MODULE$.apply(RouterConfig.class)).recover(new Serializable(this, fqn, args2, key){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ Deployer $outer;
            public final String fqn$1;
            public final List args2$1;
            public final String key$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof IllegalArgumentException ? true : A1 instanceof ConfigException) {
                    throw A1;
                }
                if (A1 instanceof NoSuchMethodException) {
                    NoSuchMethodException noSuchMethodException = (NoSuchMethodException)A1;
                    return (B1)this.$outer.dynamicAccess().createInstanceFor(this.fqn$1, this.args2$1, ClassTag$.MODULE$.apply(RouterConfig.class)).recover(new Serializable(this, noSuchMethodException){
                        private static final long serialVersionUID = 0L;
                        private final /* synthetic */ $anonfun$createRouterConfig$2 $outer;
                        private final NoSuchMethodException x4$1;

                        public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                            A1 A1 = x1;
                            if (A1 instanceof IllegalArgumentException ? true : A1 instanceof ConfigException) {
                                throw A1;
                            }
                            throw Deployer.akka$actor$Deployer$$throwCannotInstantiateRouter$1(this.$outer.args2$1, this.x4$1, this.$outer.fqn$1, this.$outer.key$1);
                        }

                        public final boolean isDefinedAt(Throwable x1) {
                            Throwable throwable = x1;
                            if (throwable instanceof IllegalArgumentException ? true : throwable instanceof ConfigException) {
                                return true;
                            }
                            return true;
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.x4$1 = x4$1;
                        }
                    }).get();
                }
                throw Deployer.akka$actor$Deployer$$throwCannotInstantiateRouter$1(this.args2$1, A1, this.fqn$1, this.key$1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                if (throwable instanceof IllegalArgumentException ? true : throwable instanceof ConfigException) {
                    return true;
                }
                if (throwable instanceof NoSuchMethodException) {
                    return true;
                }
                return true;
            }

            public /* synthetic */ Deployer akka$actor$Deployer$$anonfun$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.fqn$1 = fqn$1;
                this.args2$1 = args2$1;
                this.key$1 = key$1;
            }
        }).get();
    }

    private final void add$1(String[] path, Deploy d) {
        WildcardIndex<Deploy> w;
        do {
            w = this.deployments().get();
            ArrayOps$.MODULE$.indices$extension(Predef$.MODULE$.refArrayOps(path)).foreach$mVc$sp(i -> {
                String string = path[i];
                switch (string == null ? 0 : string.hashCode()) {
                    case 0: {
                        if (!"".equals(string)) break;
                        throw new InvalidActorNameException(new StringBuilder(45).append("Actor name in deployment [").append(d.path()).append("] must not be empty").toString());
                    }
                }
                ActorPath$.MODULE$.validatePathElement(string, d.path());
            });
        } while (!this.deployments().compareAndSet(w, w.insert(path, d)));
    }

    public static final Nothing$ akka$actor$Deployer$$throwCannotInstantiateRouter$1(Seq args2, Throwable cause, String fqn$1, String key$1) {
        throw new IllegalArgumentException(new StringBuilder(122).append("Cannot instantiate router [").append(fqn$1).append("], defined in [").append(key$1).append("], ").append("make sure it extends [").append(RouterConfig.class).append("] and has constructor with ").append("[").append(((Class)((Tuple2)args2.apply(false))._1()).getName()).append("] and optional [").append(((Class)((Tuple2)args2.apply(true))._1()).getName()).append("] parameter").toString(), cause);
    }

    public Deployer(ActorSystem.Settings settings, DynamicAccess dynamicAccess) {
        this.settings = settings;
        this.dynamicAccess = dynamicAccess;
        this.resizerEnabled = ConfigFactory.parseString("resizer.enabled=on");
        this.deployments = new AtomicReference(new WildcardIndex(WildcardIndex$.MODULE$.apply$default$1(), WildcardIndex$.MODULE$.apply$default$2()));
        this.config = settings.config().getConfig("akka.actor.deployment");
        this.default = this.config().getConfig("default");
        this.routerTypeMapping = package$JavaConverters$.MODULE$.MapHasAsScala(settings.config().getConfig("akka.actor.router.type-mapping").root().unwrapped()).asScala().collect(new Serializable(null){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Tuple2<String, Object>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 != null) {
                    String key = A1._1();
                    Object value = A1._2();
                    if (value instanceof String) {
                        String string = (String)value;
                        return (B1)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(key), string);
                    }
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Tuple2<String, Object> x1) {
                Object value;
                Tuple2<String, Object> tuple2 = x1;
                return tuple2 != null && (value = tuple2._2()) instanceof String;
            }
        }).toMap($less$colon$less$.MODULE$.refl());
        ((IterableOnceOps)package$JavaConverters$.MODULE$.MapHasAsScala(this.config().root()).asScala().flatMap((Function1<Tuple2, Option> & Serializable)x0$1 -> {
            String string;
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null && "default".equals(string = (String)tuple2._1())) {
                return None$.MODULE$;
            }
            if (tuple2 != null) {
                String key = (String)tuple2._1();
                ConfigValue value = (ConfigValue)tuple2._2();
                if (value instanceof ConfigObject) {
                    ConfigObject configObject = (ConfigObject)value;
                    return this.parseConfig(key, configObject.toConfig());
                }
            }
            return None$.MODULE$;
        })).foreach((Function1<Deploy, Object> & Serializable)d -> {
            this.deploy(d);
            return BoxedUnit.UNIT;
        });
    }
}

