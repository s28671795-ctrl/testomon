/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.io.dns.CachePolicy;
import akka.io.dns.CachePolicy$Forever$;
import akka.io.dns.CachePolicy$Never$;
import akka.io.dns.CachePolicy$Ttl$;
import akka.io.dns.DnsSettings$;
import akka.io.dns.internal.ResolvConf;
import akka.io.dns.internal.ResolvConfParser$;
import akka.util.Helpers$;
import akka.util.Helpers$Requiring$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.ccompat.package$JavaConverters$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValueType;
import java.io.File;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.time.Duration;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.IterableFactory$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005%e!\u0002\u000f\u001e\u0005u\u0019\u0003\u0002\u0003\u0016\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0017\t\u0011I\u0002!\u0011!Q\u0001\nMBQ!\u0010\u0001\u0005\u0002yBqa\u0011\u0001C\u0002\u0013\u0005A\t\u0003\u0004Z\u0001\u0001\u0006I!\u0012\u0005\b5\u0002\u0011\r\u0011\"\u0001\\\u0011\u0019!\u0007\u0001)A\u00059\"9Q\r\u0001b\u0001\n\u00031\u0007B\u0002;\u0001A\u0003%q\rC\u0004v\u0001\t\u0007I\u0011\u00014\t\rY\u0004\u0001\u0015!\u0003h\u0011\u00159\b\u0001\"\u0003y\u0011)\t9\u0001\u0001EC\u0002\u0013%\u0011\u0011\u0002\u0005\n\u0003;\u0001!\u0019!C\u0001\u0003?A\u0001\"a\t\u0001A\u0003%\u0011\u0011\u0005\u0005\n\u0003K\u0001!\u0019!C\u0001\u0003OA\u0001\"a\f\u0001A\u0003%\u0011\u0011\u0006\u0005\b\u0003c\u0001A\u0011AA\u001a\u000f\u001d\tI%\bE\u0001\u0003\u00172a\u0001H\u000f\t\u0002\u00055\u0003BB\u001f\u0015\t\u0003\ty\u0005C\u0005\u0002RQ\u0011\r\u0011\"\u0004\u0002T!A\u0011\u0011\f\u000b!\u0002\u001b\t)\u0006C\u0005\u0002\\Q\u0011\r\u0011\"\u0003\u0002^!A\u0011q\u000e\u000b!\u0002\u0013\ty\u0006\u0003\u0005\u0002rQ!\t!IA:\u0011!\tY\b\u0006C\u0001C\u0005u$a\u0003#ogN+G\u000f^5oONT!AH\u0010\u0002\u0007\u0011t7O\u0003\u0002!C\u0005\u0011\u0011n\u001c\u0006\u0002E\u0005!\u0011m[6b'\t\u0001A\u0005\u0005\u0002&Q5\taEC\u0001(\u0003\u0015\u00198-\u00197b\u0013\tIcE\u0001\u0004B]f\u0014VMZ\u0001\u0007gf\u001cH/Z7\u0004\u0001A\u0011Q\u0006M\u0007\u0002])\u0011q&I\u0001\u0006C\u000e$xN]\u0005\u0003c9\u00121#\u0012=uK:$W\rZ!di>\u00148+_:uK6\f\u0011a\u0019\t\u0003imj\u0011!\u000e\u0006\u0003m]\naaY8oM&<'B\u0001\u001d:\u0003!!\u0018\u0010]3tC\u001a,'\"\u0001\u001e\u0002\u0007\r|W.\u0003\u0002=k\t11i\u001c8gS\u001e\fa\u0001P5oSRtDcA B\u0005B\u0011\u0001\tA\u0007\u0002;!)!f\u0001a\u0001Y!)!g\u0001a\u0001g\u0005Ya*Y7f'\u0016\u0014h/\u001a:t+\u0005)\u0005c\u0001$O#:\u0011q\t\u0014\b\u0003\u0011.k\u0011!\u0013\u0006\u0003\u0015.\na\u0001\u0010:p_Rt\u0014\"A\u0014\n\u000553\u0013a\u00029bG.\fw-Z\u0005\u0003\u001fB\u0013A\u0001T5ti*\u0011QJ\n\t\u0003%^k\u0011a\u0015\u0006\u0003)V\u000b1A\\3u\u0015\u00051\u0016\u0001\u00026bm\u0006L!\u0001W*\u0003#%sW\r^*pG.,G/\u00113ee\u0016\u001c8/\u0001\u0007OC6,7+\u001a:wKJ\u001c\b%\u0001\bSKN|GN^3US6,w.\u001e;\u0016\u0003q\u0003\"!\u00182\u000e\u0003yS!a\u00181\u0002\u0011\u0011,(/\u0019;j_:T!!\u0019\u0014\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0002d=\nqa)\u001b8ji\u0016$UO]1uS>t\u0017a\u0004*fg>dg/\u001a+j[\u0016|W\u000f\u001e\u0011\u0002'A{7/\u001b;jm\u0016\u001c\u0015m\u00195f!>d\u0017nY=\u0016\u0003\u001d\u0004\"\u0001[9\u000f\u0005%|gB\u00016o\u001d\tYWN\u0004\u0002IY&\t!%\u0003\u0002!C%\u0011adH\u0005\u0003av\t1bQ1dQ\u0016\u0004v\u000e\\5ds&\u0011!o\u001d\u0002\f\u0007\u0006\u001c\u0007.\u001a)pY&\u001c\u0017P\u0003\u0002q;\u0005!\u0002k\\:ji&4XmQ1dQ\u0016\u0004v\u000e\\5ds\u0002\n1CT3hCRLg/Z\"bG\",\u0007k\u001c7jGf\fACT3hCRLg/Z\"bG\",\u0007k\u001c7jGf\u0004\u0013AB4fiR#H\u000e\u0006\u0002hs\")!\u0010\u0004a\u0001w\u0006!\u0001/\u0019;i!\ra\u0018\u0011\u0001\b\u0003{z\u0004\"\u0001\u0013\u0014\n\u0005}4\u0013A\u0002)sK\u0012,g-\u0003\u0003\u0002\u0004\u0005\u0015!AB*ue&twM\u0003\u0002\u0000M\u0005Q!/Z:pYZ\u001cuN\u001c4\u0016\u0005\u0005-\u0001#B\u0013\u0002\u000e\u0005E\u0011bAA\bM\t1q\n\u001d;j_:\u0004B!a\u0005\u0002\u001a5\u0011\u0011Q\u0003\u0006\u0004\u0003/i\u0012\u0001C5oi\u0016\u0014h.\u00197\n\t\u0005m\u0011Q\u0003\u0002\u000b%\u0016\u001cx\u000e\u001c<D_:4\u0017!D*fCJ\u001c\u0007\u000eR8nC&t7/\u0006\u0002\u0002\"A\u0019aIT>\u0002\u001dM+\u0017M]2i\t>l\u0017-\u001b8tA\u0005)a\nR8ugV\u0011\u0011\u0011\u0006\t\u0004K\u0005-\u0012bAA\u0017M\t\u0019\u0011J\u001c;\u0002\r9#u\u000e^:!\u0003\u001d2\u0017-\u001b7V]\u0006\u0014G.\u001a+p\t\u0016$XM]7j]\u0016$UMZ1vYRt\u0015-\\3tKJ4XM]:\u0016\u0005\u0005U\u0002cA\u0013\u00028%\u0019\u0011\u0011\b\u0014\u0003\u000f9{G\u000f[5oO\"\u001a\u0001!!\u0010\u0011\t\u0005}\u0012QI\u0007\u0003\u0003\u0003R1!a\u0011\"\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u000f\n\tEA\u0006J]R,'O\\1m\u0003BL\u0017a\u0003#ogN+G\u000f^5oON\u0004\"\u0001\u0011\u000b\u0014\u0005Q!CCAA&\u0003=!en\u001d$bY2\u0014\u0017mY6Q_J$XCAA+\u001f\t\t9&H\u00016\u0003A!en\u001d$bY2\u0014\u0017mY6Q_J$\b%A\tj]\u0016$8k\\2lKR\fE\r\u001a:fgN,\"!a\u0018\u0011\t\u0005\u0005\u00141N\u0007\u0003\u0003GRA!!\u001a\u0002h\u0005AQ.\u0019;dQ&twMC\u0002\u0002j\u0019\nA!\u001e;jY&!\u0011QNA2\u0005\u0015\u0011VmZ3y\u0003IIg.\u001a;T_\u000e\\W\r^!eIJ,7o\u001d\u0011\u0002-A\f'o]3OC6,7/\u001a:wKJ\fE\r\u001a:fgN$2!UA;\u0011\u0019\t9H\u0007a\u0001w\u0006\u00191\u000f\u001e:)\u0007i\ti$A\u000bhKR$UMZ1vYRt\u0015-\\3TKJ4XM]:\u0015\t\u0005}\u0014q\u0011\t\u0006\u0003\u0003\u000b\u0019)R\u0007\u0003\u0003OJA!!\"\u0002h\t\u0019AK]=\t\u000b)Z\u0002\u0019\u0001\u0017")
@InternalApi
public final class DnsSettings {
    private Option<ResolvConf> resolvConf;
    private final ExtendedActorSystem system;
    private final Config c;
    private final List<InetSocketAddress> NameServers;
    private final FiniteDuration ResolveTimeout;
    private final CachePolicy.CachePolicy PositiveCachePolicy;
    private final CachePolicy.CachePolicy NegativeCachePolicy;
    private final List<String> SearchDomains;
    private final int NDots;
    private volatile boolean bitmap$0;

    public List<InetSocketAddress> NameServers() {
        return this.NameServers;
    }

    public FiniteDuration ResolveTimeout() {
        return this.ResolveTimeout;
    }

    public CachePolicy.CachePolicy PositiveCachePolicy() {
        return this.PositiveCachePolicy;
    }

    public CachePolicy.CachePolicy NegativeCachePolicy() {
        return this.NegativeCachePolicy;
    }

    private CachePolicy.CachePolicy getTtl(String path) {
        String string = this.c.getString(path);
        switch (string == null ? 0 : string.hashCode()) {
            case -677662361: {
                if (!"forever".equals(string)) break;
                return CachePolicy$Forever$.MODULE$;
            }
            case 104712844: {
                if (!"never".equals(string)) break;
                return CachePolicy$Never$.MODULE$;
            }
        }
        Duration finiteTtl = Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(this.c.getDuration(path)), (Function1<Duration, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(DnsSettings.$anonfun$getTtl$1(x$1)), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(71).append("akka.io.dns.").append(path).append(" must be 'default', 'forever', 'never' or positive duration").toString());
        return CachePolicy$Ttl$.MODULE$.fromPositive(finiteTtl);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Option<ResolvConf> resolvConf$lzycompute() {
        DnsSettings dnsSettings = this;
        synchronized (dnsSettings) {
            Option option;
            if (this.bitmap$0) return this.resolvConf;
            File etcResolvConf = new File("/etc/resolv.conf");
            if (Helpers$.MODULE$.isWindows()) {
                option = None$.MODULE$;
            } else if (etcResolvConf.exists()) {
                Try<ResolvConf> parsed = ResolvConfParser$.MODULE$.parseFile(etcResolvConf);
                Try<ResolvConf> try_ = parsed;
                if (try_ instanceof Success) {
                    Success success = (Success)try_;
                    ResolvConf value = (ResolvConf)success.value();
                    option = new Some<ResolvConf>(value);
                } else {
                    if (!(try_ instanceof Failure)) throw new MatchError(try_);
                    Failure failure = (Failure)try_;
                    Throwable exception = failure.exception();
                    LoggingAdapter log = Logging$.MODULE$.apply(this.system, DnsSettings.class, LogSource$.MODULE$.fromAnyClass());
                    if (log.isWarningEnabled()) {
                        log.error(exception, "Error parsing /etc/resolv.conf, ignoring.");
                    }
                    option = None$.MODULE$;
                }
            } else {
                option = None$.MODULE$;
            }
            this.resolvConf = option;
            this.bitmap$0 = true;
            return this.resolvConf;
        }
    }

    private Option<ResolvConf> resolvConf() {
        if (!this.bitmap$0) {
            return this.resolvConf$lzycompute();
        }
        return this.resolvConf;
    }

    public List<String> SearchDomains() {
        return this.SearchDomains;
    }

    public int NDots() {
        return this.NDots;
    }

    public Nothing$ failUnableToDetermineDefaultNameservers() {
        throw new IllegalStateException("Unable to obtain default nameservers from JNDI or via reflection. Please set `akka.io.dns.async-dns.nameservers` explicitly in order to be able to resolve domain names. ");
    }

    public static final /* synthetic */ boolean $anonfun$getTtl$1(Duration x$1) {
        return !x$1.isNegative();
    }

    public DnsSettings(ExtendedActorSystem system, Config c) {
        int n;
        block22: {
            ConfigValueType configValueType;
            block21: {
                List list;
                block20: {
                    ConfigValueType configValueType2;
                    block19: {
                        List list2;
                        block18: {
                            ConfigValueType configValueType3;
                            block17: {
                                this.system = system;
                                this.c = c;
                                configValueType3 = c.getValue("nameservers").valueType();
                                if (!((Object)((Object)ConfigValueType.STRING)).equals((Object)configValueType3)) break block17;
                                String string = c.getString("nameservers");
                                switch (string == null ? 0 : string.hashCode()) {
                                    case 1544803905: {
                                        if (!"default".equals(string)) break;
                                        List osAddresses = (List)DnsSettings$.MODULE$.getDefaultNameServers(system).getOrElse((Function0<Nothing$> & Serializable)() -> this.failUnableToDetermineDefaultNameservers());
                                        if (osAddresses.isEmpty()) {
                                            throw this.failUnableToDetermineDefaultNameservers();
                                        }
                                        list2 = osAddresses;
                                        break block18;
                                    }
                                }
                                InetSocketAddress inetSocketAddress = DnsSettings$.MODULE$.parseNameserverAddress(string);
                                list2 = Nil$.MODULE$.$colon$colon(inetSocketAddress);
                                break block18;
                            }
                            if (((Object)((Object)ConfigValueType.LIST)).equals((Object)configValueType3)) {
                                IndexedSeq userAddresses = package$JavaConverters$.MODULE$.ListHasAsScala(c.getStringList("nameservers")).asScala().iterator().map((Function1<String, InetSocketAddress> & Serializable)str -> DnsSettings$.MODULE$.parseNameserverAddress((String)str)).to(IterableFactory$.MODULE$.toFactory(IndexedSeq$.MODULE$));
                                Predef$.MODULE$.require(userAddresses.nonEmpty(), (Function0<Object>)(Function0<String> & Serializable)() -> "nameservers can not be empty");
                                list2 = userAddresses.toList();
                            } else {
                                throw new IllegalArgumentException("Invalid type for nameservers. Must be a string or string list");
                            }
                        }
                        this.NameServers = list2;
                        this.ResolveTimeout = JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(c.getDuration("resolve-timeout")));
                        this.PositiveCachePolicy = this.getTtl("positive-ttl");
                        this.NegativeCachePolicy = this.getTtl("negative-ttl");
                        configValueType2 = c.getValue("search-domains").valueType();
                        if (!((Object)((Object)ConfigValueType.STRING)).equals((Object)configValueType2)) break block19;
                        String string = c.getString("search-domains");
                        switch (string == null ? 0 : string.hashCode()) {
                            case 1544803905: {
                                if (!"default".equals(string)) break;
                                list = (List)this.resolvConf().map((Function1<ResolvConf, List> & Serializable)x$2 -> x$2.search()).getOrElse((Function0<Nil$> & Serializable)() -> Nil$.MODULE$);
                                break block20;
                            }
                        }
                        list = new $colon$colon<Nothing$>((Nothing$)((Object)string), Nil$.MODULE$);
                        break block20;
                    }
                    if (((Object)((Object)ConfigValueType.LIST)).equals((Object)configValueType2)) {
                        list = package$JavaConverters$.MODULE$.ListHasAsScala(c.getStringList("search-domains")).asScala().toList();
                    } else {
                        throw new IllegalArgumentException("Invalid type for search-domains. Must be a string or string list.");
                    }
                }
                this.SearchDomains = list;
                configValueType = c.getValue("ndots").valueType();
                if (!((Object)((Object)ConfigValueType.STRING)).equals((Object)configValueType)) break block21;
                String string = c.getString("ndots");
                switch (string == null ? 0 : string.hashCode()) {
                    case 1544803905: {
                        if (!"default".equals(string)) break;
                        n = BoxesRunTime.unboxToInt(this.resolvConf().map((Function1<ResolvConf, Object> & Serializable)x$3 -> BoxesRunTime.boxToInteger(x$3.ndots())).getOrElse(() -> 1));
                        break block22;
                    }
                }
                throw new IllegalArgumentException("Invalid value for ndots. Must be the string 'default' or an integer.");
            }
            if (((Object)((Object)ConfigValueType.NUMBER)).equals((Object)configValueType)) {
                int ndots = c.getInt("ndots");
                if (ndots < 0) {
                    throw new IllegalArgumentException("Invalid value for ndots, ndots must not be negative.");
                }
                n = ndots;
            } else {
                throw new IllegalArgumentException("Invalid value for ndots. Must be the string 'default' or an integer.");
            }
        }
        this.NDots = n;
    }
}

