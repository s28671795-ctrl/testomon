/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Address;
import akka.actor.AddressFromURIString$;
import java.net.URI;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}:Qa\u0002\u0005\t\u000251Qa\u0004\u0005\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQ!G\u0001\u0005\u00029BQ!O\u0001\u0005\u0002iBQ\u0001P\u0001\u0005\u0002u\nA#\u00113ee\u0016\u001c8O\u0012:p[V\u0013\u0016j\u0015;sS:<'BA\u0005\u000b\u0003\u0015\t7\r^8s\u0015\u0005Y\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u000f\u00035\t\u0001B\u0001\u000bBI\u0012\u0014Xm]:Ge>lWKU%TiJLgnZ\n\u0003\u0003E\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u000e\u0003\u001d)h.\u00199qYf$\"aG\u0011\u0011\u0007Iab$\u0003\u0002\u001e'\t1q\n\u001d;j_:\u0004\"AD\u0010\n\u0005\u0001B!aB!eIJ,7o\u001d\u0005\u0006E\r\u0001\raI\u0001\u0005C\u0012$'\u000f\u0005\u0002%W9\u0011Q%\u000b\t\u0003MMi\u0011a\n\u0006\u0003Q1\ta\u0001\u0010:p_Rt\u0014B\u0001\u0016\u0014\u0003\u0019\u0001&/\u001a3fM&\u0011A&\f\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005)\u001aBCA\u000e0\u0011\u0015\u0001D\u00011\u00012\u0003\r)(/\u001b\t\u0003e]j\u0011a\r\u0006\u0003iU\n1A\\3u\u0015\u00051\u0014\u0001\u00026bm\u0006L!\u0001O\u001a\u0003\u0007U\u0013\u0016*A\u0003baBd\u0017\u0010\u0006\u0002\u001fw!)!%\u0002a\u0001G\u0005)\u0001/\u0019:tKR\u0011aD\u0010\u0005\u0006E\u0019\u0001\ra\t")
public final class AddressFromURIString {
    public static Address parse(String addr) {
        return AddressFromURIString$.MODULE$.parse(addr);
    }

    public static Address apply(String addr) {
        return AddressFromURIString$.MODULE$.apply(addr);
    }

    public static Option<Address> unapply(URI uri) {
        return AddressFromURIString$.MODULE$.unapply(uri);
    }

    public static Option<Address> unapply(String addr) {
        return AddressFromURIString$.MODULE$.unapply(addr);
    }
}

