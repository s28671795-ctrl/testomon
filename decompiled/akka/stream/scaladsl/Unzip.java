/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Unzip$;
import akka.stream.scaladsl.UnzipWith2;
import akka.util.ConstantFun$;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0015;Qa\u0002\u0005\t\u0002=1Q!\u0005\u0005\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002q1A!\u0005\u0005\u0003?!)\u0011\u0004\u0002C\u0001k!)q\u0007\u0002C!q\u0005)QK\u001c>ja*\u0011\u0011BC\u0001\tg\u000e\fG.\u00193tY*\u00111\u0002D\u0001\u0007gR\u0014X-Y7\u000b\u00035\tA!Y6lC\u000e\u0001\u0001C\u0001\t\u0002\u001b\u0005A!!B+ou&\u00048CA\u0001\u0014!\t!r#D\u0001\u0016\u0015\u00051\u0012!B:dC2\f\u0017B\u0001\r\u0016\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012aD\u0001\u0006CB\u0004H._\u000b\u0004;\t#E#\u0001\u0010\u0011\tA!\u0011iQ\u000b\u0004A%\u001a4C\u0001\u0003\"!\u0015\u0001\"\u0005J\u00143\u0013\t\u0019\u0003B\u0001\u0006V]jL\u0007oV5uQJ\u0002B\u0001F\u0013(e%\u0011a%\u0006\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0005!JC\u0002\u0001\u0003\u0006U\u0011\u0011\ra\u000b\u0002\u0002\u0003F\u0011Af\f\t\u0003)5J!AL\u000b\u0003\u000f9{G\u000f[5oOB\u0011A\u0003M\u0005\u0003cU\u00111!\u00118z!\tA3\u0007B\u00035\t\t\u00071FA\u0001C)\u00051\u0004\u0003\u0002\t\u0005OI\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002sA\u0011!hP\u0007\u0002w)\u0011A(P\u0001\u0005Y\u0006twMC\u0001?\u0003\u0011Q\u0017M^1\n\u0005\u0001[$AB*ue&tw\r\u0005\u0002)\u0005\u0012)!f\u0001b\u0001WA\u0011\u0001\u0006\u0012\u0003\u0006i\r\u0011\ra\u000b")
public final class Unzip<A, B>
extends UnzipWith2<Tuple2<A, B>, A, B> {
    public static <A, B> Unzip<A, B> apply() {
        return Unzip$.MODULE$.apply();
    }

    @Override
    public String toString() {
        return "Unzip";
    }

    public Unzip() {
        super(ConstantFun$.MODULE$.scalaIdentityFunction());
    }
}

