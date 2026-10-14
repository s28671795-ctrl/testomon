/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.io.Dns;
import akka.io.Dns$Resolved$;
import akka.io.SimpleDnsCache;
import akka.io.dns.AAAARecord;
import akka.io.dns.ARecord;
import akka.io.dns.CachePolicy;
import akka.io.dns.CachePolicy$Forever$;
import akka.io.dns.CachePolicy$Never$;
import akka.io.dns.CachePolicy$Ttl$;
import akka.io.dns.DnsProtocol;
import akka.io.dns.DnsProtocol$Ip$;
import akka.io.dns.DnsProtocol$Resolved$;
import akka.io.dns.DnsProtocol$Srv$;
import akka.io.dns.ResourceRecord;
import akka.util.Helpers$;
import akka.util.Helpers$Requiring$;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.concurrent.TimeUnit;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.PartialFunction;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.StringOps$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.Try$;

@ScalaSignature(bytes="\u0006\u0005\u0005mf\u0001B\u000f\u001f\u0001\rB\u0001b\r\u0001\u0003\u0002\u0003\u0006I\u0001\u000e\u0005\tq\u0001\u0011\t\u0011)A\u0005s!)!\t\u0001C\u0001\u0007\"9q\t\u0001b\u0001\n\u001bA\u0005B\u0002'\u0001A\u00035\u0011\nC\u0004N\u0001\t\u0007IQ\u0002(\t\rI\u0003\u0001\u0015!\u0004P\u0011\u001d\u0019\u0006A1A\u0005\u000eQCa\u0001\u0017\u0001!\u0002\u001b)\u0006bB-\u0001\u0005\u0004%iA\u0017\u0005\u0007=\u0002\u0001\u000bQB.\t\u000f}\u0003!\u0019!C\u0007A\"11\u000f\u0001Q\u0001\u000e\u0005D\u0001\u0002\u001e\u0001\t\u0006\u0004%I!\u001e\u0005\ts\u0002A)\u0019!C\u0005k\")!\u0010\u0001C\u0005w\"9\u00111\u0001\u0001\u0005\n\u0005\u0015\u0001\u0002CA\u0013\u0001\t\u0007I\u0011A;\t\u000f\u0005\u001d\u0002\u0001)A\u0005m\"A\u0011\u0011\u0006\u0001C\u0002\u0013\u0005Q\u000fC\u0004\u0002,\u0001\u0001\u000b\u0011\u0002<\t\u0013\u00055\u0002A1A\u0005\u0002\u0005=\u0002\u0002CA&\u0001\u0001\u0006I!!\r\t\u0013\u0005=\u0003A1A\u0005\u0002\u0005=\u0002\u0002CA,\u0001\u0001\u0006I!!\r\t\u000f\u0005m\u0003\u0001\"\u0003\u0002^!9\u00111\r\u0001\u0005B\u0005\u0015\u0004bBA8\u0001\u0011%\u0011\u0011\u000f\u0002\u0017\u0013:,G/\u00113ee\u0016\u001c8\u000f\u00128t%\u0016\u001cx\u000e\u001c<fe*\u0011q\u0004I\u0001\u0003S>T\u0011!I\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\t\u0001!#\u0006\r\t\u0003K!j\u0011A\n\u0006\u0002O\u0005)1oY1mC&\u0011\u0011F\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005-rS\"\u0001\u0017\u000b\u00055\u0002\u0013!B1di>\u0014\u0018BA\u0018-\u0005\u0015\t5\r^8s!\tY\u0013'\u0003\u00023Y\ta\u0011i\u0019;pe2{wmZ5oO\u0006)1-Y2iKB\u0011QGN\u0007\u0002=%\u0011qG\b\u0002\u000f'&l\u0007\u000f\\3E]N\u001c\u0015m\u00195f\u0003\u0019\u0019wN\u001c4jOB\u0011!\bQ\u0007\u0002w)\u0011\u0001\b\u0010\u0006\u0003{y\n\u0001\u0002^=qKN\fg-\u001a\u0006\u0002\u007f\u0005\u00191m\\7\n\u0005\u0005[$AB\"p]\u001aLw-\u0001\u0004=S:LGO\u0010\u000b\u0004\t\u00163\u0005CA\u001b\u0001\u0011\u0015\u00194\u00011\u00015\u0011\u0015A4\u00011\u0001:\u0003=\u0019\u0015m\u00195f!>d\u0017nY=Qe>\u0004X#A%\u0010\u0003)\u000b\u0013aS\u0001\u0019]\u0016$xo\u001c:lC\u0012$'/Z:t]\r\f7\r[3/iRd\u0017\u0001E\"bG\",\u0007k\u001c7jGf\u0004&o\u001c9!\u0003]\u0019\u0015m\u00195f!>d\u0017nY=Qe>\u0004h)\u00197mE\u0006\u001c7.F\u0001P\u001f\u0005\u0001\u0016%A)\u0002)M,hN\f8fi:Jg.\u001a;bI\u0012\u0014h\u0006\u001e;m\u0003a\u0019\u0015m\u00195f!>d\u0017nY=Qe>\u0004h)\u00197mE\u0006\u001c7\u000eI\u0001\u0018\u001d\u0016<\u0017\r^5wK\u000e\u000b7\r[3Q_2L7-\u001f)s_B,\u0012!V\b\u0002-\u0006\nq+A\u0011oKR<xN]6bI\u0012\u0014Xm]:/G\u0006\u001c\u0007.\u001a\u0018oK\u001e\fG/\u001b<f]Q$H.\u0001\rOK\u001e\fG/\u001b<f\u0007\u0006\u001c\u0007.\u001a)pY&\u001c\u0017\u0010\u0015:pa\u0002\nqDT3hCRLg/Z\"bG\",\u0007k\u001c7jGf\u0004&o\u001c9GC2d'-Y2l+\u0005Yv\"\u0001/\"\u0003u\u000bQd];o]9,GOL5oKR\fG\r\u001a:/]\u0016<\u0017\r^5wK:\"H\u000f\\\u0001!\u001d\u0016<\u0017\r^5wK\u000e\u000b7\r[3Q_2L7-\u001f)s_B4\u0015\r\u001c7cC\u000e\\\u0007%A\bEK\u001a\fW\u000f\u001c;Q_NLG/\u001b<f+\u0005\t\u0007C\u00012q\u001d\t\u0019WN\u0004\u0002eW:\u0011QM\u001b\b\u0003M&l\u0011a\u001a\u0006\u0003Q\n\na\u0001\u0010:p_Rt\u0014\"A\u0011\n\u0005}\u0001\u0013B\u00017\u001f\u0003\r!gn]\u0005\u0003]>\f1bQ1dQ\u0016\u0004v\u000e\\5ds*\u0011ANH\u0005\u0003cJ\u00141\u0001\u0016;m\u0015\tqw.\u0001\tEK\u001a\fW\u000f\u001c;Q_NLG/\u001b<fA\u0005\u0011B-\u001a4bk2$8)Y2iKB{G.[2z+\u00051\bC\u00012x\u0013\tA(OA\u0006DC\u000eDW\rU8mS\u000eL\u0018A\u00073fM\u0006,H\u000e\u001e(fO\u0006$\u0018N^3DC\u000eDW\rU8mS\u000eL\u0018a\u00039beN,\u0007k\u001c7jGf$\"A\u001e?\t\u000bu\u0004\u0002\u0019\u0001@\u0002\u00039\u0004\"!J@\n\u0007\u0005\u0005aEA\u0002J]R\faaZ3u)RdG#\u0002<\u0002\b\u0005m\u0001bBA\u0005#\u0001\u0007\u00111B\u0001\u0005a\u0006$\b\u000e\u0005\u0003\u0002\u000e\u0005Ua\u0002BA\b\u0003#\u0001\"A\u001a\u0014\n\u0007\u0005Ma%\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003/\tIB\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003'1\u0003bBA\u000f#\u0001\u0007\u0011qD\u0001\ta>\u001c\u0018\u000e^5wKB\u0019Q%!\t\n\u0007\u0005\rbEA\u0004C_>dW-\u00198\u0002'A|7/\u001b;jm\u0016\u001c\u0015m\u00195f!>d\u0017nY=\u0002)A|7/\u001b;jm\u0016\u001c\u0015m\u00195f!>d\u0017nY=!\u0003MqWmZ1uSZ,7)Y2iKB{G.[2z\u0003QqWmZ1uSZ,7)Y2iKB{G.[2zA\u0005Y\u0001o\\:ji&4X\r\u0016;m+\t\t\t\u0004E\u0002&\u0003gI1!!\u000e'\u0005\u0011auN\\4)\u0017Y\tI$a\u0010\u0002B\u0005\u0015\u0013q\t\t\u0004K\u0005m\u0012bAA\u001fM\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\u0012\u00111I\u0001\"+N,\u0007\u0005]8tSRLg/Z\"bG\",G)\u001e:bi&|g\u000eI5ogR,\u0017\rZ\u0001\u0006g&t7-Z\u0011\u0003\u0003\u0013\naA\r\u00186]E:\u0014\u0001\u00049pg&$\u0018N^3Ui2\u0004\u0003fC\f\u0002:\u0005}\u0012\u0011IA#\u0003\u000f\n1B\\3hCRLg/\u001a+uY\"Z\u0001$!\u000f\u0002@\u0005M\u0013QIA$C\t\t)&A\u0011Vg\u0016\u0004c.Z4bi&4XmQ1dQ\u0016$UO]1uS>t\u0007%\u001b8ti\u0016\fG-\u0001\u0007oK\u001e\fG/\u001b<f)Rd\u0007\u0005K\u0006\u001a\u0003s\ty$a\u0015\u0002F\u0005\u001d\u0013!\u0003;p\u0019>tw\r\u0016;m)\u0011\t\t$a\u0018\t\r\u0005\u0005$\u00041\u0001w\u0003\t\u0019\u0007/A\u0004sK\u000e,\u0017N^3\u0016\u0005\u0005\u001d\u0004\u0003BA5\u0003Wj\u0011\u0001A\u0005\u0004\u0003[r#a\u0002*fG\u0016Lg/Z\u0001\u0011C\u0012$'/Z:t)>\u0014VmY8sIN$\"\"a\u001d\u0002\f\u0006=\u0015QUAU!\u0019\t)(a \u0002\u00046\u0011\u0011q\u000f\u0006\u0005\u0003s\nY(A\u0005j[6,H/\u00192mK*\u0019\u0011Q\u0010\u0014\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\u0002\u0006]$aA*fcB!\u0011QQAD\u001b\u0005y\u0017bAAE_\nq!+Z:pkJ\u001cWMU3d_J$\u0007bBAG9\u0001\u0007\u00111B\u0001\u0005]\u0006lW\rC\u0004\u0002\u0012r\u0001\r!a%\u0002\u0013\u0005$GM]3tg\u0016\u001c\bCBA;\u0003\u007f\n)\n\u0005\u0003\u0002\u0018\u0006\u0005VBAAM\u0015\u0011\tY*!(\u0002\u00079,GO\u0003\u0002\u0002 \u0006!!.\u0019<b\u0013\u0011\t\u0019+!'\u0003\u0017%sW\r^!eIJ,7o\u001d\u0005\b\u0003Oc\u0002\u0019AA\u0010\u0003\u0011I\u0007O\u001e\u001b\t\u000f\u0005-F\u00041\u0001\u0002 \u0005!\u0011\u000e\u001d<7Q\r\u0001\u0011q\u0016\t\u0005\u0003c\u000b9,\u0004\u0002\u00024*\u0019\u0011Q\u0017\u0011\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002:\u0006M&aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public class InetAddressDnsResolver
implements Actor,
ActorLogging {
    private CachePolicy.CachePolicy defaultCachePolicy;
    private CachePolicy.CachePolicy defaultNegativeCachePolicy;
    public final SimpleDnsCache akka$io$InetAddressDnsResolver$$cache;
    private final Config config;
    private final CachePolicy.Ttl DefaultPositive;
    private final CachePolicy.CachePolicy positiveCachePolicy;
    private final CachePolicy.CachePolicy negativeCachePolicy;
    private final long positiveTtl;
    private final long negativeTtl;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;
    private volatile byte bitmap$0;

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void postStop() throws Exception {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    private final String CachePolicyProp() {
        return "networkaddress.cache.ttl";
    }

    private final String CachePolicyPropFallback() {
        return "sun.net.inetaddr.ttl";
    }

    private final String NegativeCachePolicyProp() {
        return "networkaddress.cache.negative.ttl";
    }

    private final String NegativeCachePolicyPropFallback() {
        return "sun.net.inetaddr.negative.ttl";
    }

    private final CachePolicy.Ttl DefaultPositive() {
        return this.DefaultPositive;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private CachePolicy.CachePolicy defaultCachePolicy$lzycompute() {
        block7: {
            var2_1 = this;
            synchronized (var2_1) {
                block9: {
                    block8: {
                        if ((byte)(this.bitmap$0 & 1) != 0) break block7;
                        var3_2 = false;
                        var4_3 = null;
                        var5_4 = Option$.MODULE$.apply(Security.getProperty("networkaddress.cache.ttl")).filter((Function1<String, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$defaultCachePolicy$1$adapted(java.lang.String ), (Ljava/lang/String;)Ljava/lang/Object;)()).orElse((Function0<Option> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, ()Ljava/lang/Object;, $anonfun$defaultCachePolicy$2(), ()Lscala/Option;)()).filter((Function1<String, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$defaultCachePolicy$3$adapted(java.lang.String ), (Ljava/lang/String;)Ljava/lang/Object;)()).map((Function1<String, Try> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$defaultCachePolicy$4(java.lang.String ), (Ljava/lang/String;)Lscala/util/Try;)());
                        if (!None$.MODULE$.equals(var5_4)) break block8;
                        v0 /* !! */  = this.DefaultPositive();
                        break block9;
                    }
                    if (!(var5_4 instanceof Some)) ** GOTO lbl-1000
                    var3_2 = true;
                    var4_3 = (Some)var5_4;
                    var6_5 = (Try)var4_3.value();
                    if (var6_5 instanceof Success) {
                        var7_6 = (Success)var6_5;
                        n = BoxesRunTime.unboxToInt(var7_6.value());
                        v0 /* !! */  = this.parsePolicy(n);
                    } else if (var3_2 && (var9_8 = (Try)var4_3.value()) instanceof Failure) {
                        this.log().warning("Caching TTL misconfigured. Using default value {}.", this.DefaultPositive());
                        v0 /* !! */  = this.DefaultPositive();
                    } else {
                        throw new MatchError(var5_4);
                    }
                }
                this.defaultCachePolicy = v0 /* !! */ ;
                this.bitmap$0 = (byte)(this.bitmap$0 | 1);
            }
        }
        return this.defaultCachePolicy;
    }

    private CachePolicy.CachePolicy defaultCachePolicy() {
        if ((byte)(this.bitmap$0 & 1) == 0) {
            return this.defaultCachePolicy$lzycompute();
        }
        return this.defaultCachePolicy;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private CachePolicy.CachePolicy defaultNegativeCachePolicy$lzycompute() {
        block7: {
            var2_1 = this;
            synchronized (var2_1) {
                block9: {
                    block8: {
                        if ((byte)(this.bitmap$0 & 2) != 0) break block7;
                        var3_2 = false;
                        var4_3 = null;
                        var5_4 = Option$.MODULE$.apply(Security.getProperty("networkaddress.cache.negative.ttl")).filter((Function1<String, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$defaultNegativeCachePolicy$1$adapted(java.lang.String ), (Ljava/lang/String;)Ljava/lang/Object;)()).orElse((Function0<Option> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, ()Ljava/lang/Object;, $anonfun$defaultNegativeCachePolicy$2(), ()Lscala/Option;)()).filter((Function1<String, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$defaultNegativeCachePolicy$3$adapted(java.lang.String ), (Ljava/lang/String;)Ljava/lang/Object;)()).map((Function1<String, Try> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$defaultNegativeCachePolicy$4(java.lang.String ), (Ljava/lang/String;)Lscala/util/Try;)());
                        if (!None$.MODULE$.equals(var5_4)) break block8;
                        v0 /* !! */  = CachePolicy$Never$.MODULE$;
                        break block9;
                    }
                    if (!(var5_4 instanceof Some)) ** GOTO lbl-1000
                    var3_2 = true;
                    var4_3 = (Some)var5_4;
                    var6_5 = (Try)var4_3.value();
                    if (var6_5 instanceof Success) {
                        var7_6 = (Success)var6_5;
                        n = BoxesRunTime.unboxToInt(var7_6.value());
                        v0 /* !! */  = this.parsePolicy(n);
                    } else if (var3_2 && (var9_8 = (Try)var4_3.value()) instanceof Failure) {
                        this.log().warning("Negative caching TTL misconfigured. Using default value {}.", CachePolicy$Never$.MODULE$);
                        v0 /* !! */  = CachePolicy$Never$.MODULE$;
                    } else {
                        throw new MatchError(var5_4);
                    }
                }
                this.defaultNegativeCachePolicy = v0 /* !! */ ;
                this.bitmap$0 = (byte)(this.bitmap$0 | 2);
            }
        }
        return this.defaultNegativeCachePolicy;
    }

    private CachePolicy.CachePolicy defaultNegativeCachePolicy() {
        if ((byte)(this.bitmap$0 & 2) == 0) {
            return this.defaultNegativeCachePolicy$lzycompute();
        }
        return this.defaultNegativeCachePolicy;
    }

    private CachePolicy.CachePolicy parsePolicy(int n) {
        int n2 = n;
        switch (n2) {
            case 0: {
                return CachePolicy$Never$.MODULE$;
            }
        }
        if (n2 < 0) {
            return CachePolicy$Forever$.MODULE$;
        }
        return CachePolicy$Ttl$.MODULE$.fromPositive(new package.DurationInt(package$.MODULE$.DurationInt(n2)).seconds());
    }

    private CachePolicy.CachePolicy getTtl(String path, boolean positive) {
        String string = this.config.getString(path);
        switch (string == null ? 0 : string.hashCode()) {
            case -677662361: {
                if (!"forever".equals(string)) break;
                return CachePolicy$Forever$.MODULE$;
            }
            case 104712844: {
                if (!"never".equals(string)) break;
                return CachePolicy$Never$.MODULE$;
            }
            case 1544803905: {
                if (!"default".equals(string)) break;
                if (positive) {
                    return this.defaultCachePolicy();
                }
                return this.defaultNegativeCachePolicy();
            }
        }
        long finiteTtl = BoxesRunTime.unboxToLong(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToLong(this.config.getDuration(path, TimeUnit.SECONDS))), x$5 -> x$5 > 0L, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(71).append("akka.io.dns.").append(path).append(" must be 'default', 'forever', 'never' or positive duration").toString()));
        return CachePolicy$Ttl$.MODULE$.fromPositive(new package.DurationLong(package$.MODULE$.DurationLong(finiteTtl)).seconds());
    }

    public CachePolicy.CachePolicy positiveCachePolicy() {
        return this.positiveCachePolicy;
    }

    public CachePolicy.CachePolicy negativeCachePolicy() {
        return this.negativeCachePolicy;
    }

    public long positiveTtl() {
        return this.positiveTtl;
    }

    public long negativeTtl() {
        return this.negativeTtl;
    }

    private long toLongTtl(CachePolicy.CachePolicy cp) {
        CachePolicy.CachePolicy cachePolicy = cp;
        if (CachePolicy$Forever$.MODULE$.equals(cachePolicy)) {
            return Long.MAX_VALUE;
        }
        if (CachePolicy$Never$.MODULE$.equals(cachePolicy)) {
            return 0L;
        }
        if (cachePolicy instanceof CachePolicy.Ttl) {
            CachePolicy.Ttl ttl = (CachePolicy.Ttl)cachePolicy;
            return ttl.value().toMillis();
        }
        throw new MatchError(cachePolicy);
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ InetAddressDnsResolver $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                boolean bl = false;
                DnsProtocol.Resolve resolve = null;
                A1 A1 = x1;
                if (A1 instanceof DnsProtocol.Resolve) {
                    bl = true;
                    resolve = (DnsProtocol.Resolve)A1;
                    DnsProtocol.RequestType requestType = resolve.requestType();
                    if (DnsProtocol$Srv$.MODULE$.equals(requestType)) {
                        this.$outer.sender().$bang(new Status.Failure(new IllegalArgumentException("SRV request sent to InetResolver. SRV requests are only supported by async-dns resolver.")), this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                if (bl) {
                    String name = resolve.name();
                    DnsProtocol.RequestType ip2 = resolve.requestType();
                    if (ip2 instanceof DnsProtocol.Ip) {
                        DnsProtocol.Resolved resolved;
                        DnsProtocol.Ip ip3 = (DnsProtocol.Ip)ip2;
                        boolean ipv4 = ip3.ipv4();
                        boolean ipv6 = ip3.ipv6();
                        Option<DnsProtocol.Resolved> option = this.$outer.akka$io$InetAddressDnsResolver$$cache.cached(resolve);
                        if (option instanceof Some) {
                            DnsProtocol.Resolved a;
                            Some some = (Some)option;
                            resolved = a = (DnsProtocol.Resolved)some.value();
                        } else if (None$.MODULE$.equals(option)) {
                            this.$outer.log().debug("Request for [{}] was not yet cached", name);
                            try {
                                InetAddress[] addresses = InetAddress.getAllByName(name);
                                Seq<ResourceRecord> records = this.$outer.akka$io$InetAddressDnsResolver$$addressToRecords(name, Predef$.MODULE$.wrapRefArray((Object[])addresses).toList(), ipv4, ipv6);
                                DnsProtocol.Resolved answer = DnsProtocol$Resolved$.MODULE$.apply(name, records.toList());
                                CachePolicy.CachePolicy cachePolicy = this.$outer.positiveCachePolicy();
                                CachePolicy$Never$ cachePolicy$Never$ = CachePolicy$Never$.MODULE$;
                                if (cachePolicy == null ? cachePolicy$Never$ != null : !cachePolicy.equals(cachePolicy$Never$)) {
                                    this.$outer.akka$io$InetAddressDnsResolver$$cache.put(new Tuple2<String, DnsProtocol.RequestType>(name, new DnsProtocol.Ip(DnsProtocol$Ip$.MODULE$.apply$default$1(), DnsProtocol$Ip$.MODULE$.apply$default$2())), DnsProtocol$Resolved$.MODULE$.apply(name, records), this.$outer.positiveCachePolicy());
                                }
                                resolved = answer;
                            }
                            catch (UnknownHostException unknownHostException) {
                                DnsProtocol.Resolved answer = DnsProtocol$Resolved$.MODULE$.apply(name, (Seq)Seq$.MODULE$.empty());
                                CachePolicy.CachePolicy cachePolicy = this.$outer.negativeCachePolicy();
                                CachePolicy$Never$ cachePolicy$Never$ = CachePolicy$Never$.MODULE$;
                                if (cachePolicy == null ? cachePolicy$Never$ != null : !cachePolicy.equals(cachePolicy$Never$)) {
                                    this.$outer.akka$io$InetAddressDnsResolver$$cache.put(new Tuple2<String, DnsProtocol.RequestType>(name, ip3), answer, this.$outer.negativeCachePolicy());
                                }
                                resolved = answer;
                            }
                        } else {
                            throw new MatchError(option);
                        }
                        DnsProtocol.Resolved answer = resolved;
                        this.$outer.sender().$bang(answer, this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                if (A1 instanceof Dns.Resolve) {
                    Dns.Resolved resolved;
                    Dns.Resolve resolve2 = (Dns.Resolve)A1;
                    String name = resolve2.name();
                    Option<Dns.Resolved> option = this.$outer.akka$io$InetAddressDnsResolver$$cache.cached(name);
                    if (option instanceof Some) {
                        Dns.Resolved a;
                        Some some = (Some)option;
                        resolved = a = (Dns.Resolved)some.value();
                    } else if (None$.MODULE$.equals(option)) {
                        try {
                            InetAddress[] addresses = InetAddress.getAllByName(name);
                            Dns.Resolved answer = Dns$Resolved$.MODULE$.apply(name, Predef$.MODULE$.wrapRefArray((Object[])addresses));
                            CachePolicy.CachePolicy cachePolicy = this.$outer.positiveCachePolicy();
                            CachePolicy$Never$ cachePolicy$Never$ = CachePolicy$Never$.MODULE$;
                            if (cachePolicy == null ? cachePolicy$Never$ != null : !cachePolicy.equals(cachePolicy$Never$)) {
                                Seq<ResourceRecord> records = this.$outer.akka$io$InetAddressDnsResolver$$addressToRecords(name, Predef$.MODULE$.wrapRefArray((Object[])addresses).toList(), true, true);
                                this.$outer.akka$io$InetAddressDnsResolver$$cache.put(new Tuple2<String, DnsProtocol.RequestType>(name, new DnsProtocol.Ip(DnsProtocol$Ip$.MODULE$.apply$default$1(), DnsProtocol$Ip$.MODULE$.apply$default$2())), DnsProtocol$Resolved$.MODULE$.apply(name, records), this.$outer.positiveCachePolicy());
                            }
                            resolved = answer;
                        }
                        catch (UnknownHostException unknownHostException) {
                            Dns.Resolved answer = new Dns.Resolved(name, (Seq)Seq$.MODULE$.empty(), (Seq)Seq$.MODULE$.empty());
                            CachePolicy.CachePolicy cachePolicy = this.$outer.negativeCachePolicy();
                            CachePolicy$Never$ cachePolicy$Never$ = CachePolicy$Never$.MODULE$;
                            if (cachePolicy == null ? cachePolicy$Never$ != null : !cachePolicy.equals(cachePolicy$Never$)) {
                                this.$outer.akka$io$InetAddressDnsResolver$$cache.put(new Tuple2<String, DnsProtocol.RequestType>(name, new DnsProtocol.Ip(DnsProtocol$Ip$.MODULE$.apply$default$1(), DnsProtocol$Ip$.MODULE$.apply$default$2())), DnsProtocol$Resolved$.MODULE$.apply(name, (Seq)Seq$.MODULE$.empty()), this.$outer.negativeCachePolicy());
                            }
                            resolved = answer;
                        }
                    } else {
                        throw new MatchError(option);
                    }
                    Dns.Resolved answer = resolved;
                    this.$outer.sender().$bang(answer, this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                DnsProtocol.RequestType ip2;
                boolean bl = false;
                DnsProtocol.Resolve resolve = null;
                Object object = x1;
                if (object instanceof DnsProtocol.Resolve) {
                    bl = true;
                    resolve = (DnsProtocol.Resolve)object;
                    DnsProtocol.RequestType requestType = resolve.requestType();
                    if (DnsProtocol$Srv$.MODULE$.equals(requestType)) {
                        return true;
                    }
                }
                if (bl && (ip2 = resolve.requestType()) instanceof DnsProtocol.Ip) {
                    return true;
                }
                return object instanceof Dns.Resolve;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public Seq<ResourceRecord> akka$io$InetAddressDnsResolver$$addressToRecords(String name, Seq<InetAddress> addresses, boolean ipv4, boolean ipv6) {
        return (Seq)addresses.collect(new Serializable(this, ipv4, name, ipv6){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ InetAddressDnsResolver $outer;
            private final boolean ipv4$1;
            private final String name$1;
            private final boolean ipv6$1;

            public final <A1 extends InetAddress, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Inet4Address) {
                    Inet4Address inet4Address = (Inet4Address)A1;
                    if (this.ipv4$1) {
                        return (B1)new ARecord(this.name$1, CachePolicy$Ttl$.MODULE$.toTll(this.$outer.positiveCachePolicy()), inet4Address);
                    }
                }
                if (A1 instanceof Inet6Address) {
                    Inet6Address inet6Address = (Inet6Address)A1;
                    if (this.ipv6$1) {
                        return (B1)new AAAARecord(this.name$1, CachePolicy$Ttl$.MODULE$.toTll(this.$outer.positiveCachePolicy()), inet6Address);
                    }
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(InetAddress x1) {
                InetAddress inetAddress = x1;
                if (inetAddress instanceof Inet4Address && this.ipv4$1) {
                    return true;
                }
                return inetAddress instanceof Inet6Address && this.ipv6$1;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.ipv4$1 = ipv4$1;
                this.name$1 = name$1;
                this.ipv6$1 = ipv6$1;
            }
        });
    }

    public static final /* synthetic */ boolean $anonfun$defaultCachePolicy$1(String x$1) {
        String string = x$1;
        String string2 = "";
        return string == null ? string2 != null : !string.equals(string2);
    }

    public static final /* synthetic */ Option $anonfun$defaultCachePolicy$2() {
        return Option$.MODULE$.apply(System.getProperty("sun.net.inetaddr.ttl"));
    }

    public static final /* synthetic */ boolean $anonfun$defaultCachePolicy$3(String x$2) {
        String string = x$2;
        String string2 = "";
        return string == null ? string2 != null : !string.equals(string2);
    }

    public static final /* synthetic */ Try $anonfun$defaultCachePolicy$4(String x) {
        return Try$.MODULE$.apply(() -> StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(x)));
    }

    public static final /* synthetic */ boolean $anonfun$defaultNegativeCachePolicy$1(String x$3) {
        String string = x$3;
        String string2 = "";
        return string == null ? string2 != null : !string.equals(string2);
    }

    public static final /* synthetic */ Option $anonfun$defaultNegativeCachePolicy$2() {
        return Option$.MODULE$.apply(System.getProperty("sun.net.inetaddr.negative.ttl"));
    }

    public static final /* synthetic */ boolean $anonfun$defaultNegativeCachePolicy$3(String x$4) {
        String string = x$4;
        String string2 = "";
        return string == null ? string2 != null : !string.equals(string2);
    }

    public static final /* synthetic */ Try $anonfun$defaultNegativeCachePolicy$4(String x) {
        return Try$.MODULE$.apply(() -> StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(x)));
    }

    public InetAddressDnsResolver(SimpleDnsCache cache, Config config) {
        this.akka$io$InetAddressDnsResolver$$cache = cache;
        this.config = config;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        this.DefaultPositive = CachePolicy$Ttl$.MODULE$.fromPositive(new package.DurationInt(package$.MODULE$.DurationInt(30)).seconds());
        this.positiveCachePolicy = this.getTtl("positive-ttl", true);
        this.negativeCachePolicy = this.getTtl("negative-ttl", false);
        this.positiveTtl = this.toLongTtl(this.positiveCachePolicy());
        this.negativeTtl = this.toLongTtl(this.negativeCachePolicy());
        Statics.releaseFence();
    }

    public static final /* synthetic */ Object $anonfun$defaultCachePolicy$1$adapted(String x$1) {
        return BoxesRunTime.boxToBoolean(InetAddressDnsResolver.$anonfun$defaultCachePolicy$1(x$1));
    }

    public static final /* synthetic */ Object $anonfun$defaultCachePolicy$3$adapted(String x$2) {
        return BoxesRunTime.boxToBoolean(InetAddressDnsResolver.$anonfun$defaultCachePolicy$3(x$2));
    }

    public static final /* synthetic */ Object $anonfun$defaultNegativeCachePolicy$1$adapted(String x$3) {
        return BoxesRunTime.boxToBoolean(InetAddressDnsResolver.$anonfun$defaultNegativeCachePolicy$1(x$3));
    }

    public static final /* synthetic */ Object $anonfun$defaultNegativeCachePolicy$3$adapted(String x$4) {
        return BoxesRunTime.boxToBoolean(InetAddressDnsResolver.$anonfun$defaultNegativeCachePolicy$3(x$4));
    }
}

