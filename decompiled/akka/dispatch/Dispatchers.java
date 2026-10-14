/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.ConfigurationException;
import akka.actor.ActorSystem;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.dispatch.BalancingDispatcherConfigurator;
import akka.dispatch.CachingConfig;
import akka.dispatch.DispatcherConfigurator;
import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.Dispatchers$;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.MessageDispatcherConfigurator;
import akka.dispatch.PinnedDispatcherConfigurator;
import akka.event.LoggingAdapter;
import akka.util.ccompat.package$JavaConverters$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import scala.Function1;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.Map;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015w!\u0002\u0014(\u0011\u0003ac!\u0002\u0018(\u0011\u0003y\u0003\"\u0002\u001c\u0002\t\u00039\u0004b\u0002\u001d\u0002\u0005\u0004%)!\u000f\u0005\u0007{\u0005\u0001\u000bQ\u0002\u001e\t\u000fy\n!\u0019!C\u0003\u007f!11*\u0001Q\u0001\u000e\u0001C\u0001\u0002T\u0001C\u0002\u0013\u0015\u0011&\u0014\u0005\u0007#\u0006\u0001\u000bQ\u0002(\t\u000fe\u000b!\u0019!C\u00055\"1a,\u0001Q\u0001\nmCaaX\u0001\u0005\u0002%\u0002\u0007\u0002\u00039\u0002#\u0003%\t!K9\u0007\t9:\u0003a\u001f\u0005\ty6\u0011)\u0019!C\u0001{\"I\u0011\u0011C\u0007\u0003\u0002\u0003\u0006IA \u0005\u000b\u0003'i!Q1A\u0005\u0002\u0005U\u0001BCA\u000f\u001b\t\u0005\t\u0015!\u0003\u0002\u0018!Q\u0011qD\u0007\u0003\u0002\u0003\u0006I!!\t\t\u000fYjA\u0011A\u0015\u0002.!I\u0011\u0011H\u0007C\u0002\u0013\u0005\u00111\b\u0005\t\u0003\u0007j\u0001\u0015!\u0003\u0002>!I\u0011QI\u0007C\u0002\u0013\u0005\u0011q\t\u0005\b\u0003\u0013j\u0001\u0015!\u0003b\u0011\u001d\tY%\u0004C\u0001\u0003\u001bB\u0011\"!\u0016\u000e\u0005\u0004%I!a\u0016\t\u0011\u0005MT\u0002)A\u0005\u00033B!\"!\u001e\u000e\u0005\u0004%\t!KA'\u0011!\t9(\u0004Q\u0001\n\u0005=\u0003bBA=\u001b\u0011\u0005\u00111\u0010\u0005\b\u0003\u007fjA\u0011AAA\u0011\u001d\tY)\u0004C\u0005\u0003\u001bCq!a%\u000e\t\u0003\t)\nC\u0004e\u001b\u0011\u0005\u0011&!(\t\u000f\u0011lA\u0011A\u0015\u0002\"\"9\u0011\u0011V\u0007\u0005\n\u0005-\u0006\u0002CAX\u001b\u0011\u0005\u0011&!-\t\u000f\u0005]V\u0002\"\u0003\u0002:\u0006YA)[:qCR\u001c\u0007.\u001a:t\u0015\tA\u0013&\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005Q\u0013\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002.\u00035\tqEA\u0006ESN\u0004\u0018\r^2iKJ\u001c8CA\u00011!\t\tD'D\u00013\u0015\u0005\u0019\u0014!B:dC2\f\u0017BA\u001b3\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012\u0001L\u0001\u0014\t\u00164\u0017-\u001e7u\t&\u001c\b/\u0019;dQ\u0016\u0014\u0018\nZ\u000b\u0002u=\t1(I\u0001=\u0003u\t7n[1/C\u000e$xN\u001d\u0018eK\u001a\fW\u000f\u001c;.I&\u001c\b/\u0019;dQ\u0016\u0014\u0018\u0001\u0006#fM\u0006,H\u000e\u001e#jgB\fGo\u00195fe&#\u0007%A\u000eEK\u001a\fW\u000f\u001c;CY>\u001c7.\u001b8h\t&\u001c\b/\u0019;dQ\u0016\u0014\u0018\nZ\u000b\u0002\u0001B\u0011\u0011\t\u0013\b\u0003\u0005\u001a\u0003\"a\u0011\u001a\u000e\u0003\u0011S!!R\u0016\u0002\rq\u0012xn\u001c;?\u0013\t9%'\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u0013*\u0013aa\u0015;sS:<'BA$3\u0003q!UMZ1vYR\u0014En\\2lS:<G)[:qCR\u001c\u0007.\u001a:JI\u0002\nA#\u00138uKJt\u0017\r\u001c#jgB\fGo\u00195fe&#W#\u0001(\u0010\u0003=\u000b\u0013\u0001U\u0001\u001fC.\\\u0017ML1di>\u0014h&\u001b8uKJt\u0017\r\\\u0017eSN\u0004\u0018\r^2iKJ\fQ#\u00138uKJt\u0017\r\u001c#jgB\fGo\u00195fe&#\u0007\u0005\u000b\u0002\t'B\u0011AkV\u0007\u0002+*\u0011a+K\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001-V\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002/5\u000b\u0007\u0010R5ta\u0006$8\r[3s\u00032L\u0017m\u001d#faRDW#A.\u0011\u0005Eb\u0016BA/3\u0005\rIe\u000e^\u0001\u0019\u001b\u0006DH)[:qCR\u001c\u0007.\u001a:BY&\f7\u000fR3qi\"\u0004\u0013!C4fi\u000e{gNZ5h)\u0011\t7\u000e\u001c8\u0011\u0005\tLW\"A2\u000b\u0005\u0011,\u0017AB2p]\u001aLwM\u0003\u0002gO\u0006AA/\u001f9fg\u00064WMC\u0001i\u0003\r\u0019w.\\\u0005\u0003U\u000e\u0014aaQ8oM&<\u0007\"\u00023\f\u0001\u0004\t\u0007\"B7\f\u0001\u0004\u0001\u0015AA5e\u0011\u001dy7\u0002%AA\u0002m\u000bQ\u0001Z3qi\"\f1cZ3u\u0007>tg-[4%I\u00164\u0017-\u001e7uIM*\u0012A\u001d\u0016\u00037N\\\u0013\u0001\u001e\t\u0003kfl\u0011A\u001e\u0006\u0003ob\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005Y\u0013\u0014B\u0001>w\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\n\u0003\u001bA\n\u0001b]3ui&twm]\u000b\u0002}B\u0019q0a\u0003\u000f\t\u0005\u0005\u0011qA\u0007\u0003\u0003\u0007Q1!!\u0002*\u0003\u0015\t7\r^8s\u0013\u0011\tI!a\u0001\u0002\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0005\u0005\u0003\u001b\tyA\u0001\u0005TKR$\u0018N\\4t\u0015\u0011\tI!a\u0001\u0002\u0013M,G\u000f^5oON\u0004\u0013!\u00049sKJ,\u0017/^5tSR,7/\u0006\u0002\u0002\u0018A\u0019Q&!\u0007\n\u0007\u0005mqEA\fESN\u0004\u0018\r^2iKJ\u0004&/\u001a:fcVL7/\u001b;fg\u0006q\u0001O]3sKF,\u0018n]5uKN\u0004\u0013A\u00027pO\u001e,'\u000f\u0005\u0003\u0002$\u0005%RBAA\u0013\u0015\r\t9#K\u0001\u0006KZ,g\u000e^\u0005\u0005\u0003W\t)C\u0001\bM_\u001e<\u0017N\\4BI\u0006\u0004H/\u001a:\u0015\u0011\u0005=\u0012\u0011GA\u001a\u0003k\u0001\"!L\u0007\t\u000bq\u001c\u0002\u0019\u0001@\t\u000f\u0005M1\u00031\u0001\u0002\u0018!9\u0011qD\nA\u0002\u0005\u0005\u0002FA\nT\u00035\u0019\u0017m\u00195j]\u001e\u001cuN\u001c4jOV\u0011\u0011Q\b\t\u0004[\u0005}\u0012bAA!O\ti1)Y2iS:<7i\u001c8gS\u001e\fabY1dQ&twmQ8oM&<\u0007%A\feK\u001a\fW\u000f\u001c;ESN\u0004\u0018\r^2iKJ\u001cuN\u001c4jOV\t\u0011-\u0001\reK\u001a\fW\u000f\u001c;ESN\u0004\u0018\r^2iKJ\u001cuN\u001c4jO\u0002\nq\u0003Z3gCVdGo\u00127pE\u0006dG)[:qCR\u001c\u0007.\u001a:\u0016\u0005\u0005=\u0003cA\u0017\u0002R%\u0019\u00111K\u0014\u0003#5+7o]1hK\u0012K7\u000f]1uG\",'/A\feSN\u0004\u0018\r^2iKJ\u001cuN\u001c4jOV\u0014\u0018\r^8sgV\u0011\u0011\u0011\f\t\b\u00037\nI\u0007QA7\u001b\t\tiF\u0003\u0003\u0002`\u0005\u0005\u0014AC2p]\u000e,(O]3oi*!\u00111MA3\u0003\u0011)H/\u001b7\u000b\u0005\u0005\u001d\u0014\u0001\u00026bm\u0006LA!a\u001b\u0002^\t\t2i\u001c8dkJ\u0014XM\u001c;ICNDW*\u00199\u0011\u00075\ny'C\u0002\u0002r\u001d\u0012Q$T3tg\u0006<W\rR5ta\u0006$8\r[3s\u0007>tg-[4ve\u0006$xN]\u0001\u0019I&\u001c\b/\u0019;dQ\u0016\u00148i\u001c8gS\u001e,(/\u0019;peN\u0004\u0013AE5oi\u0016\u0014h.\u00197ESN\u0004\u0018\r^2iKJ\f1#\u001b8uKJt\u0017\r\u001c#jgB\fGo\u00195fe\u0002\na\u0001\\8pWV\u0004H\u0003BA(\u0003{BQ!\\\u000fA\u0002\u0001\u000bQ\u0002[1t\t&\u001c\b/\u0019;dQ\u0016\u0014H\u0003BAB\u0003\u0013\u00032!MAC\u0013\r\t9I\r\u0002\b\u0005>|G.Z1o\u0011\u0015ig\u00041\u0001A\u0003Iawn\\6va\u000e{gNZ5hkJ\fGo\u001c:\u0015\r\u00055\u0014qRAI\u0011\u0015iw\u00041\u0001A\u0011\u0015yw\u00041\u0001\\\u0003Q\u0011XmZ5ti\u0016\u00148i\u001c8gS\u001e,(/\u0019;peR1\u00111QAL\u00033CQ!\u001c\u0011A\u0002\u0001Cq!a'!\u0001\u0004\ti'\u0001\u0007d_:4\u0017nZ;sCR|'\u000fF\u0002b\u0003?CQ!\\\u0011A\u0002\u0001#R!YAR\u0003KCQ!\u001c\u0012A\u0002\u0001Ca!a*#\u0001\u0004\t\u0017!C1qa\u000e{gNZ5h\u0003!IGmQ8oM&<GcA1\u0002.\")Qn\ta\u0001\u0001\u0006!aM]8n)\u0011\ty%a-\t\r\u0005UF\u00051\u0001b\u0003\r\u0019gmZ\u0001\u0011G>tg-[4ve\u0006$xN\u001d$s_6$B!!\u001c\u0002<\"1\u0011QW\u0013A\u0002\u0005D3!DA`!\r!\u0016\u0011Y\u0005\u0004\u0003\u0007,&\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\b")
@DoNotInherit
public class Dispatchers {
    private final ActorSystem.Settings settings;
    private final DispatcherPrerequisites prerequisites;
    private final LoggingAdapter logger;
    private final CachingConfig cachingConfig;
    private final Config defaultDispatcherConfig;
    private final ConcurrentHashMap<String, MessageDispatcherConfigurator> dispatcherConfigurators;
    private final MessageDispatcher internalDispatcher;

    public static String DefaultBlockingDispatcherId() {
        return Dispatchers$.MODULE$.DefaultBlockingDispatcherId();
    }

    public static String DefaultDispatcherId() {
        return Dispatchers$.MODULE$.DefaultDispatcherId();
    }

    public ActorSystem.Settings settings() {
        return this.settings;
    }

    public DispatcherPrerequisites prerequisites() {
        return this.prerequisites;
    }

    public CachingConfig cachingConfig() {
        return this.cachingConfig;
    }

    public Config defaultDispatcherConfig() {
        return this.defaultDispatcherConfig;
    }

    public MessageDispatcher defaultGlobalDispatcher() {
        return this.lookup("akka.actor.default-dispatcher");
    }

    private ConcurrentHashMap<String, MessageDispatcherConfigurator> dispatcherConfigurators() {
        return this.dispatcherConfigurators;
    }

    public MessageDispatcher internalDispatcher() {
        return this.internalDispatcher;
    }

    public MessageDispatcher lookup(String id) {
        return this.lookupConfigurator(id, 0).dispatcher();
    }

    public boolean hasDispatcher(String id) {
        return this.dispatcherConfigurators().containsKey(id) || this.cachingConfig().hasPath(id);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private MessageDispatcherConfigurator lookupConfigurator(String id, int depth) {
        MessageDispatcherConfigurator messageDispatcherConfigurator;
        if (depth > Dispatchers$.MODULE$.akka$dispatch$Dispatchers$$MaxDispatcherAliasDepth()) {
            throw new ConfigurationException(new StringBuilder(114).append("Didn't find a concrete dispatcher config after following ").append(Dispatchers$.MODULE$.akka$dispatch$Dispatchers$$MaxDispatcherAliasDepth()).append(", ").append("is there a loop in your config? last looked for id was ").append(id).toString());
        }
        MessageDispatcherConfigurator messageDispatcherConfigurator2 = this.dispatcherConfigurators().get(id);
        if (messageDispatcherConfigurator2 != null) return messageDispatcherConfigurator2;
        if (!this.cachingConfig().hasPath(id)) throw new ConfigurationException(new StringBuilder(28).append("Dispatcher [").append(id).append("] not configured").toString());
        ConfigValue valueAtPath = this.cachingConfig().getValue(id);
        ConfigValueType configValueType = valueAtPath.valueType();
        if (((Object)((Object)ConfigValueType.STRING)).equals((Object)configValueType)) {
            String actualId = (String)valueAtPath.unwrapped();
            this.logger.debug("Dispatcher id [{}] is an alias, actual dispatcher will be [{}]", id, actualId);
            messageDispatcherConfigurator = this.lookupConfigurator(actualId, depth + 1);
        } else {
            if (!((Object)((Object)ConfigValueType.OBJECT)).equals((Object)configValueType)) throw new ConfigurationException(new StringBuilder(66).append("Expected either a dispatcher config or an alias at [").append(id).append("] but found [").append((Object)configValueType).append("]").toString());
            messageDispatcherConfigurator = this.configuratorFrom(this.config(id));
        }
        MessageDispatcherConfigurator newConfigurator = messageDispatcherConfigurator;
        MessageDispatcherConfigurator messageDispatcherConfigurator3 = this.dispatcherConfigurators().putIfAbsent(id, newConfigurator);
        if (messageDispatcherConfigurator3 != null) return messageDispatcherConfigurator3;
        return newConfigurator;
    }

    public boolean registerConfigurator(String id, MessageDispatcherConfigurator configurator2) {
        return this.dispatcherConfigurators().putIfAbsent(id, configurator2) == null;
    }

    public Config config(String id) {
        return this.config(id, this.settings().config().getConfig(id));
    }

    public Config config(String id, Config appConfig) {
        return this.idConfig(id).withFallback(appConfig).withFallback(ConfigFactory.parseMap(package$JavaConverters$.MODULE$.MapHasAsJava((Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("name"), Dispatchers.simpleName$1(id))}))).asJava())).withFallback(this.defaultDispatcherConfig());
    }

    private Config idConfig(String id) {
        return ConfigFactory.parseMap(package$JavaConverters$.MODULE$.MapHasAsJava((Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("id"), id)}))).asJava());
    }

    public MessageDispatcher from(Config cfg) {
        return this.configuratorFrom(cfg).dispatcher();
    }

    private MessageDispatcherConfigurator configuratorFrom(Config cfg) {
        if (!cfg.hasPath("id")) {
            throw new ConfigurationException(new StringBuilder(44).append("Missing dispatcher 'id' property in config: ").append(cfg.root().render()).toString());
        }
        String string = cfg.getString("type");
        switch (string == null ? 0 : string.hashCode()) {
            case 780924615: {
                if (!"Dispatcher".equals(string)) break;
                return new DispatcherConfigurator(cfg, this.prerequisites());
            }
            case 785504256: {
                if (!"BalancingDispatcher".equals(string)) break;
                throw new IllegalArgumentException(new StringBuilder(163).append("BalancingDispatcher is deprecated, use a BalancingPool instead. During a migration period you can still use BalancingDispatcher by specifying the full class name: ").append(BalancingDispatcherConfigurator.class.getName()).toString());
            }
            case 1126523071: {
                if (!"PinnedDispatcher".equals(string)) break;
                return new PinnedDispatcherConfigurator(cfg, this.prerequisites());
            }
        }
        $colon$colon<Nothing$> args2 = new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Config.class), cfg)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(DispatcherPrerequisites.class), this.prerequisites())), Nil$.MODULE$));
        return (MessageDispatcherConfigurator)((Object)this.prerequisites().dynamicAccess().createInstanceFor(string, args2, ClassTag$.MODULE$.apply(MessageDispatcherConfigurator.class)).recover(new Serializable(null, string, cfg){
            private static final long serialVersionUID = 0L;
            private final String x1$1;
            private final Config cfg$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                throw new ConfigurationException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("Cannot instantiate MessageDispatcherConfigurator type [%s], defined in [%s], make sure it has constructor with [com.typesafe.config.Config] and [akka.dispatch.DispatcherPrerequisites] parameters"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.x1$1, this.cfg$1.getString("id")})), A1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return true;
            }
            {
                this.x1$1 = x1$1;
                this.cfg$1 = cfg$1;
            }
        }).get());
    }

    private static final String simpleName$1(String id$1) {
        return id$1.substring(id$1.lastIndexOf(46) + 1);
    }

    @InternalApi
    public Dispatchers(ActorSystem.Settings settings, DispatcherPrerequisites prerequisites, LoggingAdapter logger2) {
        this.settings = settings;
        this.prerequisites = prerequisites;
        this.logger = logger2;
        this.cachingConfig = new CachingConfig(settings.config());
        this.defaultDispatcherConfig = this.idConfig("akka.actor.default-dispatcher").withFallback(settings.config().getConfig("akka.actor.default-dispatcher"));
        this.dispatcherConfigurators = new ConcurrentHashMap();
        this.internalDispatcher = this.lookup("akka.actor.internal-dispatcher");
    }
}

