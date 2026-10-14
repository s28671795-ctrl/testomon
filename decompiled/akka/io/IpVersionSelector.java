/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.annotation.InternalApi;
import akka.io.IpVersionSelector$;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a:Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQ\u0001F\u0001\u0005\u0002UAQAF\u0001\u0005\u0002]\t\u0011#\u00139WKJ\u001c\u0018n\u001c8TK2,7\r^8s\u0015\t1q!\u0001\u0002j_*\t\u0001\"\u0001\u0003bW.\f7\u0001\u0001\t\u0003\u0017\u0005i\u0011!\u0002\u0002\u0012\u0013B4VM]:j_:\u001cV\r\\3di>\u00148CA\u0001\u000f!\ty!#D\u0001\u0011\u0015\u0005\t\u0012!B:dC2\f\u0017BA\n\u0011\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AC\u0001\u000fO\u0016$\u0018J\\3u\u0003\u0012$'/Z:t)\rA2%\u000b\t\u0004\u001feY\u0012B\u0001\u000e\u0011\u0005\u0019y\u0005\u000f^5p]B\u0011A$I\u0007\u0002;)\u0011adH\u0001\u0004]\u0016$(\"\u0001\u0011\u0002\t)\fg/Y\u0005\u0003Eu\u00111\"\u00138fi\u0006#GM]3tg\")Ae\u0001a\u0001K\u0005!\u0011\u000e\u001d<5!\ry\u0011D\n\t\u00039\u001dJ!\u0001K\u000f\u0003\u0019%sW\r\u001e\u001bBI\u0012\u0014Xm]:\t\u000b)\u001a\u0001\u0019A\u0016\u0002\t%\u0004hO\u000e\t\u0004\u001fea\u0003C\u0001\u000f.\u0013\tqSD\u0001\u0007J]\u0016$h'\u00113ee\u0016\u001c8\u000f\u000b\u0002\u0004aA\u0011\u0011\u0007N\u0007\u0002e)\u00111gB\u0001\u000bC:tw\u000e^1uS>t\u0017BA\u001b3\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0005\u0005\u0001\u0004F\u0001\u00011\u0001")
@InternalApi
public final class IpVersionSelector {
    @InternalApi
    public static Option<InetAddress> getInetAddress(Option<Inet4Address> ipv4, Option<Inet6Address> ipv6) {
        return IpVersionSelector$.MODULE$.getInetAddress(ipv4, ipv6);
    }
}

