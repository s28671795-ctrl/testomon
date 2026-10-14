/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.serialization.Serializer;
import akka.serialization.SerializerDetails$;
import java.util.List;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U<Q!\u0004\b\t\u0002M1Q!\u0006\b\t\u0002YAQ!H\u0001\u0005\u0002yAQaH\u0001\u0005\u0002\u0001BQaY\u0001\u0005\u0002\u00114A!\u0006\b\u0003E!A1%\u0002BC\u0002\u0013\u0005A\u0005\u0003\u00051\u000b\t\u0005\t\u0015!\u0003&\u0011!\tTA!b\u0001\n\u0003\u0011\u0004\u0002\u0003\u001c\u0006\u0005\u0003\u0005\u000b\u0011B\u001a\t\u0011]*!Q1A\u0005\u0002aB\u0001\"S\u0003\u0003\u0002\u0003\u0006I!\u000f\u0005\u0006;\u0015!I!U\u0001\u0012'\u0016\u0014\u0018.\u00197ju\u0016\u0014H)\u001a;bS2\u001c(BA\b\u0011\u00035\u0019XM]5bY&T\u0018\r^5p]*\t\u0011#\u0001\u0003bW.\f7\u0001\u0001\t\u0003)\u0005i\u0011A\u0004\u0002\u0012'\u0016\u0014\u0018.\u00197ju\u0016\u0014H)\u001a;bS2\u001c8CA\u0001\u0018!\tA2$D\u0001\u001a\u0015\u0005Q\u0012!B:dC2\f\u0017B\u0001\u000f\u001a\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012aE\u0001\u0006CB\u0004H.\u001f\u000b\u0005Ci[F\f\u0005\u0002\u0015\u000bM\u0011QaF\u0001\u0006C2L\u0017m]\u000b\u0002KA\u0011a%\f\b\u0003O-\u0002\"\u0001K\r\u000e\u0003%R!A\u000b\n\u0002\rq\u0012xn\u001c;?\u0013\ta\u0013$\u0001\u0004Qe\u0016$WMZ\u0005\u0003]=\u0012aa\u0015;sS:<'B\u0001\u0017\u001a\u0003\u0019\tG.[1tA\u0005Q1/\u001a:jC2L'0\u001a:\u0016\u0003M\u0002\"\u0001\u0006\u001b\n\u0005Ur!AC*fe&\fG.\u001b>fe\u0006Y1/\u001a:jC2L'0\u001a:!\u0003\u0019)8/\u001a$peV\t\u0011\bE\u0002;\u007f\u0005k\u0011a\u000f\u0006\u0003yu\n\u0011\"[7nkR\f'\r\\3\u000b\u0005yJ\u0012AC2pY2,7\r^5p]&\u0011\u0001i\u000f\u0002\u0004'\u0016\f\bG\u0001\"H!\r13)R\u0005\u0003\t>\u0012Qa\u00117bgN\u0004\"AR$\r\u0001\u0011I\u0001jCA\u0001\u0002\u0003\u0015\tA\u0013\u0002\u0004?\u0012\u001a\u0014aB;tK\u001a{'\u000fI\t\u0003\u0017:\u0003\"\u0001\u0007'\n\u00055K\"a\u0002(pi\"Lgn\u001a\t\u00031=K!\u0001U\r\u0003\u0007\u0005s\u0017\u0010\u0006\u0003\"%N#\u0006\"B\u0012\r\u0001\u0004)\u0003\"B\u0019\r\u0001\u0004\u0019\u0004\"B\u001c\r\u0001\u0004)\u0006c\u0001\u001e@-B\u0012q+\u0017\t\u0004M\rC\u0006C\u0001$Z\t%AE+!A\u0001\u0002\u000b\u0005!\nC\u0003$\u0007\u0001\u0007Q\u0005C\u00032\u0007\u0001\u00071\u0007C\u00038\u0007\u0001\u0007Q\fE\u0002;\u007fy\u0003$aX1\u0011\u0007\u0019\u001a\u0005\r\u0005\u0002GC\u0012I!\rXA\u0001\u0002\u0003\u0015\tA\u0013\u0002\u0004?\u0012\n\u0014AB2sK\u0006$X\r\u0006\u0003\"K\u001a<\u0007\"B\u0012\u0005\u0001\u0004)\u0003\"B\u0019\u0005\u0001\u0004\u0019\u0004\"B\u001c\u0005\u0001\u0004A\u0007cA5oa6\t!N\u0003\u0002lY\u0006!Q\u000f^5m\u0015\u0005i\u0017\u0001\u00026bm\u0006L!a\u001c6\u0003\t1K7\u000f\u001e\u0019\u0003cN\u00042AJ\"s!\t15\u000fB\u0005uO\u0006\u0005\t\u0011!B\u0001\u0015\n\u0019q\f\n\u001a")
public final class SerializerDetails {
    private final String alias;
    private final Serializer serializer;
    private final Seq<Class<?>> useFor;

    public static SerializerDetails create(String alias, Serializer serializer, List<Class<?>> useFor) {
        return SerializerDetails$.MODULE$.create(alias, serializer, useFor);
    }

    public static SerializerDetails apply(String alias, Serializer serializer, Seq<Class<?>> useFor) {
        return SerializerDetails$.MODULE$.apply(alias, serializer, useFor);
    }

    public String alias() {
        return this.alias;
    }

    public Serializer serializer() {
        return this.serializer;
    }

    public Seq<Class<?>> useFor() {
        return this.useFor;
    }

    public SerializerDetails(String alias, Serializer serializer, Seq<Class<?>> useFor) {
        this.alias = alias;
        this.serializer = serializer;
        this.useFor = useFor;
    }
}

