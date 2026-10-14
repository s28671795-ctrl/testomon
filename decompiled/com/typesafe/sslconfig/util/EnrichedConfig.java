/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigOrigin;
import com.typesafe.sslconfig.util.ConfigLoader;
import com.typesafe.sslconfig.util.ConfigLoader$;
import com.typesafe.sslconfig.util.EnrichedConfig$;
import java.io.Serializable;
import scala.$less$colon$less$;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.collection.MapOps;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-f\u0001B\f\u0019\u0001\u0005B\u0001\u0002\u000b\u0001\u0003\u0006\u0004%\t!\u000b\u0005\ta\u0001\u0011\t\u0011)A\u0005U!)\u0011\u0007\u0001C\u0001e!)a\u0007\u0001C\u0001o!)q\u000b\u0001C\u00011\")\u0001\u000e\u0001C\u0001S\")Q\u000f\u0001C\u0001m\"91\u0010AI\u0001\n\u0003a\bbBA\b\u0001\u0011\u0005\u0011\u0011\u0003\u0005\t\u0003;\u0001\u0011\u0013!C\u0001y\"9\u0011q\u0004\u0001\u0005\u0002\u0005\u0005\u0002bBA\u001d\u0001\u0011\u0005\u00111\b\u0005\b\u0003\u001f\u0002A\u0011AA)\u0011!\tY\u0006AI\u0001\n\u0003a\bbBA/\u0001\u0011\u0005\u0011q\f\u0005\n\u0003\u007f\u0002\u0011\u0013!C\u0001\u0003\u0003Cq!!\"\u0001\t\u0003\t9\tC\u0004\u0002\u0010\u0002!\t!!%\b\u000f\u0005u\u0005\u0004#\u0001\u0002 \u001a1q\u0003\u0007E\u0001\u0003CCa!\r\u000b\u0005\u0002\u0005\r\u0006bBAS)\u0011\u0005\u0011q\u0015\u0002\u000f\u000b:\u0014\u0018n\u00195fI\u000e{gNZ5h\u0015\tI\"$\u0001\u0003vi&d'BA\u000e\u001d\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002\u001e=\u0005AA/\u001f9fg\u00064WMC\u0001 \u0003\r\u0019w.\\\u0002\u0001'\t\u0001!\u0005\u0005\u0002$M5\tAEC\u0001&\u0003\u0015\u00198-\u00197b\u0013\t9CE\u0001\u0004B]f\u0014VMZ\u0001\u000bk:$WM\u001d7zS:<W#\u0001\u0016\u0011\u0005-rS\"\u0001\u0017\u000b\u00055b\u0012AB2p]\u001aLw-\u0003\u00020Y\t11i\u001c8gS\u001e\f1\"\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"aM\u001b\u0011\u0005Q\u0002Q\"\u0001\r\t\u000b!\u001a\u0001\u0019\u0001\u0016\u0002\u0007\u001d,G/\u0006\u00029yQ\u0011\u0011H\u0013\u000b\u0003u\u0015\u0003\"a\u000f\u001f\r\u0001\u0011)Q\b\u0002b\u0001}\t\t\u0011)\u0005\u0002@\u0005B\u00111\u0005Q\u0005\u0003\u0003\u0012\u0012qAT8uQ&tw\r\u0005\u0002$\u0007&\u0011A\t\n\u0002\u0004\u0003:L\b\"\u0002$\u0005\u0001\b9\u0015A\u00027pC\u0012,'\u000fE\u00025\u0011jJ!!\u0013\r\u0003\u0019\r{gNZ5h\u0019>\fG-\u001a:\t\u000b-#\u0001\u0019\u0001'\u0002\tA\fG\u000f\u001b\t\u0003\u001bRs!A\u0014*\u0011\u0005=#S\"\u0001)\u000b\u0005E\u0003\u0013A\u0002\u001fs_>$h(\u0003\u0002TI\u00051\u0001K]3eK\u001aL!!\u0016,\u0003\rM#(/\u001b8h\u0015\t\u0019F%\u0001\u0004hKR\u001cV-]\u000b\u00033\u0012$\"AW4\u0015\u0005m+\u0007c\u0001/bG6\tQL\u0003\u0002_?\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003A\u0012\n!bY8mY\u0016\u001cG/[8o\u0013\t\u0011WLA\u0002TKF\u0004\"a\u000f3\u0005\u000bu*!\u0019\u0001 \t\u000b\u0019+\u00019\u00014\u0011\u0007QB5\fC\u0003L\u000b\u0001\u0007A*A\u0006hKR|\u0005\u000f^5p]\u0006dWC\u00016q)\tYG\u000f\u0006\u0002mcB\u00191%\\8\n\u00059$#AB(qi&|g\u000e\u0005\u0002<a\u0012)QH\u0002b\u0001}!9!OBA\u0001\u0002\b\u0019\u0018AC3wS\u0012,gnY3%cA\u0019A\u0007S8\t\u000b-3\u0001\u0019\u0001'\u0002!\u001d,G\u000f\u0015:pi>$\u0018\u0010]3e'\u0016\fHcA<ysB\u0019A,Y\u001a\t\u000b-;\u0001\u0019\u0001'\t\u000fi<\u0001\u0013!a\u0001\u0019\u0006i\u0001O]8u_RL\b/\u001a)bi\"\f!dZ3u!J|Go\u001c;za\u0016$7+Z9%I\u00164\u0017-\u001e7uII*\u0012! \u0016\u0003\u0019z\\\u0013a \t\u0005\u0003\u0003\tY!\u0004\u0002\u0002\u0004)!\u0011QAA\u0004\u0003%)hn\u00195fG.,GMC\u0002\u0002\n\u0011\n!\"\u00198o_R\fG/[8o\u0013\u0011\ti!a\u0001\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\thKR\u0004&o\u001c;pif\u0004X\rZ'baR1\u00111CA\r\u00037\u0001R!TA\u000b\u0019NJ1!a\u0006W\u0005\ri\u0015\r\u001d\u0005\u0006\u0017&\u0001\r\u0001\u0014\u0005\bu&\u0001\n\u00111\u0001M\u0003i9W\r\u001e)s_R|G/\u001f9fI6\u000b\u0007\u000f\n3fM\u0006,H\u000e\u001e\u00133\u0003U9W\r^(qi&|g.\u00197EKB\u0014XmY1uK\u0012,B!a\t\u0002,Q1\u0011QEA\u001a\u0003k!B!a\n\u0002.A!1%\\A\u0015!\rY\u00141\u0006\u0003\u0006{-\u0011\rA\u0010\u0005\n\u0003_Y\u0011\u0011!a\u0002\u0003c\t!\"\u001a<jI\u0016t7-\u001a\u00133!\u0011!\u0004*!\u000b\t\u000b-[\u0001\u0019\u0001'\t\r\u0005]2\u00021\u0001M\u0003)!W\r\u001d:fG\u0006$X\rZ\u0001\u000eO\u0016$H)\u001a9sK\u000e\fG/\u001a3\u0016\t\u0005u\u00121\t\u000b\u0007\u0003\u007f\tY%!\u0014\u0015\t\u0005\u0005\u0013Q\t\t\u0004w\u0005\rC!B\u001f\r\u0005\u0004q\u0004\"CA$\u0019\u0005\u0005\t9AA%\u0003))g/\u001b3f]\u000e,Ge\r\t\u0005i!\u000b\t\u0005C\u0003L\u0019\u0001\u0007A\n\u0003\u0004\u000281\u0001\r\u0001T\u0001\u001aO\u0016$H)\u001a9sK\u000e\fG/\u001a3XSRDg)\u00197mE\u0006\u001c7\u000eF\u00044\u0003'\n)&a\u0016\t\u000b-k\u0001\u0019\u0001'\t\r\u0005]R\u00021\u0001M\u0011!\tI&\u0004I\u0001\u0002\u0004a\u0015A\u00029be\u0016tG/A\u0012hKR$U\r\u001d:fG\u0006$X\rZ,ji\"4\u0015\r\u001c7cC\u000e\\G\u0005Z3gCVdG\u000fJ\u001a\u0002\u0017I,\u0007o\u001c:u\u000bJ\u0014xN\u001d\u000b\t\u0003C\n\u0019(!\u001e\u0002zA!\u00111MA7\u001d\u0011\t)'!\u001b\u000f\u0007=\u000b9'C\u0001&\u0013\r\tY\u0007J\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\ty'!\u001d\u0003\u0013QC'o\\<bE2,'bAA6I!)1j\u0004a\u0001\u0019\"1\u0011qO\bA\u00021\u000bq!\\3tg\u0006<W\rC\u0005\u0002|=\u0001\n\u00111\u0001\u0002~\u0005\tQ\r\u0005\u0003$[\u0006\u0005\u0014!\u0006:fa>\u0014H/\u0012:s_J$C-\u001a4bk2$HeM\u000b\u0003\u0003\u0007S3!! \u007f\u0003\u001d\u0019XOY&fsN,\"!!#\u0011\t5\u000bY\tT\u0005\u0004\u0003\u001b3&aA*fi\u0006\t\"/\u001a9peR$U\r\u001d:fG\u0006$\u0018n\u001c8\u0015\r\u0005M\u0015\u0011TAN!\r\u0019\u0013QS\u0005\u0004\u0003/##\u0001B+oSRDQa\u0013\nA\u00021Ca!a\u000e\u0013\u0001\u0004a\u0015AD#oe&\u001c\u0007.\u001a3D_:4\u0017n\u001a\t\u0003iQ\u0019\"\u0001\u0006\u0012\u0015\u0005\u0005}\u0015!B1qa2LHcA\u001a\u0002*\")\u0001F\u0006a\u0001U\u0001")
public class EnrichedConfig {
    private final Config underlying;

    public static EnrichedConfig apply(Config underlying) {
        return EnrichedConfig$.MODULE$.apply(underlying);
    }

    public Config underlying() {
        return this.underlying;
    }

    public <A> A get(String path, ConfigLoader<A> loader) {
        return loader.load(this.underlying(), path);
    }

    public <A> Seq<A> getSeq(String path, ConfigLoader<Seq<A>> loader) {
        return loader.load(this.underlying(), path);
    }

    public <A> Option<A> getOptional(String path, ConfigLoader<A> evidence$1) {
        Option option;
        try {
            option = Option$.MODULE$.apply(this.get(path, evidence$1));
        }
        catch (ConfigException.Missing e) {
            option = None$.MODULE$;
        }
        return option;
    }

    public Seq<EnrichedConfig> getPrototypedSeq(String path, String prototypePath) {
        Config prototype = this.underlying().getConfig(prototypePath.replace("$path", path));
        return ((IterableOnceOps)((IterableOps)this.get(path, ConfigLoader$.MODULE$.seqConfigLoader())).map((Function1<Config, EnrichedConfig> & Serializable)config -> new EnrichedConfig(config.withFallback(prototype)))).toList();
    }

    public String getPrototypedSeq$default$2() {
        return "prototype.$path";
    }

    public Map<String, EnrichedConfig> getPrototypedMap(String path, String prototypePath) {
        Config prototype = prototypePath.isEmpty() ? this.underlying() : this.underlying().getConfig(prototypePath.replace("$path", path));
        return ((Map)((MapOps)this.get(path, ConfigLoader$.MODULE$.mapLoader(ConfigLoader$.MODULE$.configLoader()))).map((Function1<Tuple2, Tuple2> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            String key = (String)tuple2._1();
            Config config = (Config)tuple2._2();
            Tuple2<String, EnrichedConfig> tuple22 = Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(key), new EnrichedConfig(config.withFallback(prototype)));
            return tuple22;
        })).toMap($less$colon$less$.MODULE$.refl());
    }

    public String getPrototypedMap$default$2() {
        return "prototype.$path";
    }

    public <A> Option<A> getOptionalDeprecated(String path, String deprecated2, ConfigLoader<A> evidence$2) {
        Option<A> option;
        if (this.underlying().hasPath(deprecated2)) {
            this.reportDeprecation(path, deprecated2);
            option = this.getOptional(deprecated2, evidence$2);
        } else {
            option = this.getOptional(path, evidence$2);
        }
        return option;
    }

    public <A> A getDeprecated(String path, String deprecated2, ConfigLoader<A> evidence$3) {
        A a;
        if (this.underlying().hasPath(deprecated2)) {
            this.reportDeprecation(path, deprecated2);
            a = this.get(deprecated2, evidence$3);
        } else {
            a = this.get(path, evidence$3);
        }
        return a;
    }

    public EnrichedConfig getDeprecatedWithFallback(String path, String deprecated2, String parent) {
        Config config;
        Config config2 = this.get(path, ConfigLoader$.MODULE$.configLoader());
        if (this.underlying().hasPath(deprecated2)) {
            this.reportDeprecation(path, deprecated2);
            config = this.get(deprecated2, ConfigLoader$.MODULE$.configLoader()).withFallback(config2);
        } else {
            config = config2;
        }
        Config merged = config;
        return new EnrichedConfig(merged);
    }

    public String getDeprecatedWithFallback$default$3() {
        return "";
    }

    public Throwable reportError(String path, String message, Option<Throwable> e) {
        return e.get();
    }

    public Option<Throwable> reportError$default$3() {
        return None$.MODULE$;
    }

    public Set<String> subKeys() {
        return ((IterableOnceOps)JavaConverters$.MODULE$.asScalaSetConverter(this.underlying().root().keySet()).asScala()).toSet();
    }

    public void reportDeprecation(String path, String deprecated2) {
        ConfigOrigin configOrigin = this.underlying().getValue(deprecated2).origin();
    }

    public EnrichedConfig(Config underlying) {
        this.underlying = underlying;
    }
}

