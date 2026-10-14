/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.util;

import com.typesafe.config.Config;
import com.typesafe.sslconfig.util.ConfigLoader$;
import com.typesafe.sslconfig.util.EnrichedConfig;
import scala.Function1;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\teaa\u0002\u0016,!\u0003\r\t\u0001\u000e\u0005\u0006y\u0001!\t!\u0010\u0005\u0006\u0003\u00021\tA\u0011\u0005\u0006E\u0002!\taY\u0004\u0006_.B\t\u0001\u001d\u0004\u0006U-B\t!\u001d\u0005\u0006e\u0016!\ta\u001d\u0005\u0006i\u0016!\t!\u001e\u0005\u0006{\u0016!IA \u0005\n\u0003O)!\u0019!C\u0002\u0003SA\u0001\"!\f\u0006A\u0003%\u00111\u0006\u0005\n\u0003_)!\u0019!C\u0002\u0003cA\u0001\"a\u000e\u0006A\u0003%\u00111\u0007\u0005\n\u0003s)!\u0019!C\u0002\u0003wA\u0001\"!\u0012\u0006A\u0003%\u0011Q\b\u0005\n\u0003\u000f*!\u0019!C\u0002\u0003\u0013B\u0001\"a\u0014\u0006A\u0003%\u00111\n\u0005\n\u0003#*!\u0019!C\u0002\u0003'B\u0001\"!\u0018\u0006A\u0003%\u0011Q\u000b\u0005\n\u0003?*!\u0019!C\u0002\u0003CB\u0001\"a\u001a\u0006A\u0003%\u00111\r\u0005\n\u0003S*!\u0019!C\u0002\u0003WB\u0001\"a \u0006A\u0003%\u0011Q\u000e\u0005\n\u0003\u0003+!\u0019!C\u0002\u0003\u0007C\u0001\"a&\u0006A\u0003%\u0011Q\u0011\u0005\n\u00033+!\u0019!C\u0002\u00037C\u0001\"!*\u0006A\u0003%\u0011Q\u0014\u0005\n\u0003O+!\u0019!C\u0002\u0003SC\u0001\"!/\u0006A\u0003%\u00111\u0016\u0005\n\u0003w+!\u0019!C\u0002\u0003{C\u0001\"a2\u0006A\u0003%\u0011q\u0018\u0005\n\u0003\u0013,!\u0019!C\u0002\u0003\u0017D\u0001\"!6\u0006A\u0003%\u0011Q\u001a\u0005\n\u0003/,!\u0019!C\u0002\u00033D\u0001\"!8\u0006A\u0003%\u00111\u001c\u0005\n\u0003?,!\u0019!C\u0002\u0003CD\u0001\"a:\u0006A\u0003%\u00111\u001d\u0005\n\u0003S,!\u0019!C\u0002\u0003WD\u0001\"!>\u0006A\u0003%\u0011Q\u001e\u0005\n\u0003o,!\u0019!C\u0002\u0003sD\u0001\"a@\u0006A\u0003%\u00111 \u0005\b\u0005\u0003)A1\u0001B\u0002\u00051\u0019uN\u001c4jO2{\u0017\rZ3s\u0015\taS&\u0001\u0003vi&d'B\u0001\u00180\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u00021c\u0005AA/\u001f9fg\u00064WMC\u00013\u0003\r\u0019w.\\\u0002\u0001+\t)Ti\u0005\u0002\u0001mA\u0011qGO\u0007\u0002q)\t\u0011(A\u0003tG\u0006d\u0017-\u0003\u0002<q\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#\u0001 \u0011\u0005]z\u0014B\u0001!9\u0005\u0011)f.\u001b;\u0002\t1|\u0017\r\u001a\u000b\u0004\u0007:+\u0006C\u0001#F\u0019\u0001!QA\u0012\u0001C\u0002\u001d\u0013\u0011!Q\t\u0003\u0011.\u0003\"aN%\n\u0005)C$a\u0002(pi\"Lgn\u001a\t\u0003o1K!!\u0014\u001d\u0003\u0007\u0005s\u0017\u0010C\u0003P\u0005\u0001\u0007\u0001+\u0001\u0004d_:4\u0017n\u001a\t\u0003#Nk\u0011A\u0015\u0006\u0003\u001f>J!\u0001\u0016*\u0003\r\r{gNZ5h\u0011\u00151&\u00011\u0001X\u0003\u0011\u0001\u0018\r\u001e5\u0011\u0005a{fBA-^!\tQ\u0006(D\u0001\\\u0015\ta6'\u0001\u0004=e>|GOP\u0005\u0003=b\na\u0001\u0015:fI\u00164\u0017B\u00011b\u0005\u0019\u0019FO]5oO*\u0011a\fO\u0001\u0004[\u0006\u0004XC\u00013i)\t)'\u000eE\u0002g\u0001\u001dl\u0011a\u000b\t\u0003\t\"$Q![\u0002C\u0002\u001d\u0013\u0011A\u0011\u0005\u0006W\u000e\u0001\r\u0001\\\u0001\u0002MB!q'\\\"h\u0013\tq\u0007HA\u0005Gk:\u001cG/[8oc\u0005a1i\u001c8gS\u001edu.\u00193feB\u0011a-B\n\u0003\u000bY\na\u0001P5oSRtD#\u00019\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0005YLHCA<{!\r1\u0007\u0001\u001f\t\u0003\tf$QAR\u0004C\u0002\u001dCQa[\u0004A\u0002m\u0004BaN7QyB!q'\\,y\u0003\u001d!xnU2bY\u0006,2a`A\n)\u0011\t\t!!\u0006\u0011\r\u0005\r\u0011QBA\t\u001b\t\t)A\u0003\u0003\u0002\b\u0005%\u0011!C5n[V$\u0018M\u00197f\u0015\r\tY\u0001O\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA\b\u0003\u000b\u00111aU3r!\r!\u00151\u0003\u0003\u0006\r\"\u0011\ra\u0012\u0005\b\u0003/A\u0001\u0019AA\r\u0003\t\t7\u000f\u0005\u0004\u0002\u001c\u0005\r\u0012\u0011C\u0007\u0003\u0003;Q1\u0001LA\u0010\u0015\t\t\t#\u0001\u0003kCZ\f\u0017\u0002BA\u0013\u0003;\u0011A\u0001T5ti\u0006a1\u000f\u001e:j]\u001edu.\u00193feV\u0011\u00111\u0006\t\u0004M\u00029\u0016!D:ue&tw\rT8bI\u0016\u0014\b%A\btKF\u001cFO]5oO2{\u0017\rZ3s+\t\t\u0019\u0004\u0005\u0003g\u0001\u0005U\u0002#BA\u0002\u0003\u001b9\u0016\u0001E:fcN#(/\u001b8h\u0019>\fG-\u001a:!\u0003%Ig\u000e\u001e'pC\u0012,'/\u0006\u0002\u0002>A!a\rAA !\r9\u0014\u0011I\u0005\u0004\u0003\u0007B$aA%oi\u0006Q\u0011N\u001c;M_\u0006$WM\u001d\u0011\u0002\u0019M,\u0017/\u00138u\u0019>\fG-\u001a:\u0016\u0005\u0005-\u0003\u0003\u00024\u0001\u0003\u001b\u0002b!a\u0001\u0002\u000e\u0005}\u0012!D:fc&sG\u000fT8bI\u0016\u0014\b%A\u0007c_>dW-\u00198M_\u0006$WM]\u000b\u0003\u0003+\u0002BA\u001a\u0001\u0002XA\u0019q'!\u0017\n\u0007\u0005m\u0003HA\u0004C_>dW-\u00198\u0002\u001d\t|w\u000e\\3b]2{\u0017\rZ3sA\u0005\u00012/Z9C_>dW-\u00198M_\u0006$WM]\u000b\u0003\u0003G\u0002BA\u001a\u0001\u0002fA1\u00111AA\u0007\u0003/\n\u0011c]3r\u0005>|G.Z1o\u0019>\fG-\u001a:!\u0003Q1\u0017N\\5uK\u0012+(/\u0019;j_:du.\u00193feV\u0011\u0011Q\u000e\t\u0005M\u0002\ty\u0007\u0005\u0003\u0002r\u0005mTBAA:\u0015\u0011\t)(a\u001e\u0002\u0011\u0011,(/\u0019;j_:T1!!\u001f9\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0005\u0003{\n\u0019H\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\u0002+\u0019Lg.\u001b;f\tV\u0014\u0018\r^5p]2{\u0017\rZ3sA\u000592/Z9GS:LG/\u001a#ve\u0006$\u0018n\u001c8M_\u0006$WM]\u000b\u0003\u0003\u000b\u0003BA\u001a\u0001\u0002\bB1\u0011\u0011RAJ\u0003_rA!a#\u0002\u0010:\u0019!,!$\n\u0003eJ1!!%9\u0003\u001d\u0001\u0018mY6bO\u0016LA!a\u0004\u0002\u0016*\u0019\u0011\u0011\u0013\u001d\u00021M,\u0017OR5oSR,G)\u001e:bi&|g\u000eT8bI\u0016\u0014\b%\u0001\u0007e_V\u0014G.\u001a'pC\u0012,'/\u0006\u0002\u0002\u001eB!a\rAAP!\r9\u0014\u0011U\u0005\u0004\u0003GC$A\u0002#pk\ndW-A\u0007e_V\u0014G.\u001a'pC\u0012,'\u000fI\u0001\u0010g\u0016\fHi\\;cY\u0016du.\u00193feV\u0011\u00111\u0016\t\u0005M\u0002\ti\u000b\u0005\u0004\u0002\u0004\u00055\u0011q\u0016\t\u0005\u0003c\u000b9,\u0004\u0002\u00024*!\u0011QWA\u0010\u0003\u0011a\u0017M\\4\n\t\u0005\r\u00161W\u0001\u0011g\u0016\fHi\\;cY\u0016du.\u00193fe\u0002\n!\u0002\\8oO2{\u0017\rZ3s+\t\ty\f\u0005\u0003g\u0001\u0005\u0005\u0007cA\u001c\u0002D&\u0019\u0011Q\u0019\u001d\u0003\t1{gnZ\u0001\fY>tw\rT8bI\u0016\u0014\b%A\u0007tKFduN\\4M_\u0006$WM]\u000b\u0003\u0003\u001b\u0004BA\u001a\u0001\u0002PB1\u00111AA\u0007\u0003#\u0004B!!-\u0002T&!\u0011QYAZ\u00039\u0019X-\u001d'p]\u001edu.\u00193fe\u0002\nAbY8oM&<Gj\\1eKJ,\"!a7\u0011\u0007\u0019\u0004\u0001+A\u0007d_:4\u0017n\u001a'pC\u0012,'\u000fI\u0001\u0010g\u0016\f8i\u001c8gS\u001edu.\u00193feV\u0011\u00111\u001d\t\u0005M\u0002\t)\u000fE\u0003\u0002\n\u0006M\u0005+\u0001\ttKF\u001cuN\u001c4jO2{\u0017\rZ3sA\u0005\u0001\u0002\u000f\\1z\u0007>tg-[4M_\u0006$WM]\u000b\u0003\u0003[\u0004BA\u001a\u0001\u0002pB\u0019a-!=\n\u0007\u0005M8F\u0001\bF]JL7\r[3e\u0007>tg-[4\u0002#Ad\u0017-_\"p]\u001aLw\rT8bI\u0016\u0014\b%A\ftKF,eN]5dQ\u0016$7i\u001c8gS\u001edu.\u00193feV\u0011\u00111 \t\u0005M\u0002\ti\u0010\u0005\u0004\u0002\n\u0006M\u0015q^\u0001\u0019g\u0016\fXI\u001c:jG\",GmQ8oM&<Gj\\1eKJ\u0004\u0013!C7ba2{\u0017\rZ3s+\u0011\u0011)A!\u0005\u0015\t\t\u001d!1\u0003\t\u0005M\u0002\u0011I\u0001\u0005\u0004Y\u0005\u00179&qB\u0005\u0004\u0005\u001b\t'aA'baB\u0019AI!\u0005\u0005\u000b\u0019K#\u0019A$\t\u000f\tU\u0011\u0006q\u0001\u0003\u0018\u0005Ya/\u00197vK2{\u0017\rZ3s!\u00111\u0007Aa\u0004")
public interface ConfigLoader<A> {
    public static <A> ConfigLoader<Map<String, A>> mapLoader(ConfigLoader<A> valueLoader) {
        return ConfigLoader$.MODULE$.mapLoader(valueLoader);
    }

    public static ConfigLoader<Seq<EnrichedConfig>> seqEnrichedConfigLoader() {
        return ConfigLoader$.MODULE$.seqEnrichedConfigLoader();
    }

    public static ConfigLoader<EnrichedConfig> playConfigLoader() {
        return ConfigLoader$.MODULE$.playConfigLoader();
    }

    public static ConfigLoader<Seq<Config>> seqConfigLoader() {
        return ConfigLoader$.MODULE$.seqConfigLoader();
    }

    public static ConfigLoader<Config> configLoader() {
        return ConfigLoader$.MODULE$.configLoader();
    }

    public static ConfigLoader<Seq<Long>> seqLongLoader() {
        return ConfigLoader$.MODULE$.seqLongLoader();
    }

    public static ConfigLoader<Object> longLoader() {
        return ConfigLoader$.MODULE$.longLoader();
    }

    public static ConfigLoader<Seq<Double>> seqDoubleLoader() {
        return ConfigLoader$.MODULE$.seqDoubleLoader();
    }

    public static ConfigLoader<Object> doubleLoader() {
        return ConfigLoader$.MODULE$.doubleLoader();
    }

    public static ConfigLoader<Seq<FiniteDuration>> seqFiniteDurationLoader() {
        return ConfigLoader$.MODULE$.seqFiniteDurationLoader();
    }

    public static ConfigLoader<FiniteDuration> finiteDurationLoader() {
        return ConfigLoader$.MODULE$.finiteDurationLoader();
    }

    public static ConfigLoader<Seq<Object>> seqBooleanLoader() {
        return ConfigLoader$.MODULE$.seqBooleanLoader();
    }

    public static ConfigLoader<Object> booleanLoader() {
        return ConfigLoader$.MODULE$.booleanLoader();
    }

    public static ConfigLoader<Seq<Object>> seqIntLoader() {
        return ConfigLoader$.MODULE$.seqIntLoader();
    }

    public static ConfigLoader<Object> intLoader() {
        return ConfigLoader$.MODULE$.intLoader();
    }

    public static ConfigLoader<Seq<String>> seqStringLoader() {
        return ConfigLoader$.MODULE$.seqStringLoader();
    }

    public static ConfigLoader<String> stringLoader() {
        return ConfigLoader$.MODULE$.stringLoader();
    }

    public static <A> ConfigLoader<A> apply(Function1<Config, Function1<String, A>> f) {
        return ConfigLoader$.MODULE$.apply(f);
    }

    public A load(Config var1, String var2);

    public static /* synthetic */ ConfigLoader map$(ConfigLoader $this, Function1 f) {
        return $this.map(f);
    }

    default public <B> ConfigLoader<B> map(Function1<A, B> f) {
        return new ConfigLoader<B>(this, f){
            private final /* synthetic */ ConfigLoader $outer;
            private final Function1 f$1;

            public <B> ConfigLoader<B> map(Function1<B, B> f) {
                return ConfigLoader.map$(this, f);
            }

            public B load(Config config, String path) {
                return (B)this.f$1.apply(this.$outer.load(config, path));
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.f$1 = f$1;
                ConfigLoader.$init$(this);
            }
        };
    }

    public static void $init$(ConfigLoader $this) {
    }
}

