/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.internal.ResponseCode$;
import scala.Enumeration;
import scala.Enumeration$ValueOrdering$;
import scala.Enumeration$ValueSet$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%;a\u0001E\t\t\u0002EIbAB\u000e\u0012\u0011\u0003\tB\u0004C\u0003$\u0003\u0011\u0005Q\u0005C\u0004'\u0003\t\u0007I\u0011A\u0014\t\r1\n\u0001\u0015!\u0003)\u0011\u001di\u0013A1A\u0005\u0002\u001dBaAL\u0001!\u0002\u0013A\u0003bB\u0018\u0002\u0005\u0004%\ta\n\u0005\u0007a\u0005\u0001\u000b\u0011\u0002\u0015\t\u000fE\n!\u0019!C\u0001O!1!'\u0001Q\u0001\n!BqaM\u0001C\u0002\u0013\u0005q\u0005\u0003\u00045\u0003\u0001\u0006I\u0001\u000b\u0005\bk\u0005\u0011\r\u0011\"\u0001(\u0011\u00191\u0014\u0001)A\u0005Q!9q'AA\u0001\n\u0013A\u0014\u0001\u0004*fgB|gn]3D_\u0012,'B\u0001\n\u0014\u0003!Ig\u000e^3s]\u0006d'B\u0001\u000b\u0016\u0003\r!gn\u001d\u0006\u0003-]\t!![8\u000b\u0003a\tA!Y6lCB\u0011!$A\u0007\u0002#\ta!+Z:q_:\u001cXmQ8eKN\u0011\u0011!\b\t\u0003=\u0005j\u0011a\b\u0006\u0002A\u0005)1oY1mC&\u0011!e\b\u0002\f\u000b:,X.\u001a:bi&|g.\u0001\u0004=S:LGOP\u0002\u0001)\u0005I\u0012aB*V\u0007\u000e+5kU\u000b\u0002QA\u0011\u0011FK\u0007\u0002\u0003%\u00111&\t\u0002\u0006-\u0006dW/Z\u0001\t'V\u001b5)R*TA\u0005aai\u0014*N\u0003R{VI\u0015*P%\u0006iai\u0014*N\u0003R{VI\u0015*P%\u0002\nabU#S-\u0016\u0013vLR!J\u0019V\u0013V)A\bT\u000bJ3VIU0G\u0003&cUKU#!\u0003)q\u0015)T#`\u000bJ\u0013vJU\u0001\f\u001d\u0006kUiX#S%>\u0013\u0006%A\bO\u001fR{\u0016*\u0014)M\u000b6+e\nV#E\u0003Aqu\nV0J\u001bBcU)T#O)\u0016#\u0005%A\u0004S\u000b\u001a+6+\u0012#\u0002\u0011I+e)V*F\t\u0002\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\u0012!\u000f\t\u0003u}j\u0011a\u000f\u0006\u0003yu\nA\u0001\\1oO*\ta(\u0001\u0003kCZ\f\u0017B\u0001!<\u0005\u0019y%M[3di\"\u0012\u0011A\u0011\t\u0003\u0007\u001ak\u0011\u0001\u0012\u0006\u0003\u000b^\t!\"\u00198o_R\fG/[8o\u0013\t9EIA\u0006J]R,'O\\1m\u0003BL\u0007F\u0001\u0001C\u0001")
@InternalApi
public final class ResponseCode {
    public static Enumeration.Value REFUSED() {
        return ResponseCode$.MODULE$.REFUSED();
    }

    public static Enumeration.Value NOT_IMPLEMENTED() {
        return ResponseCode$.MODULE$.NOT_IMPLEMENTED();
    }

    public static Enumeration.Value NAME_ERROR() {
        return ResponseCode$.MODULE$.NAME_ERROR();
    }

    public static Enumeration.Value SERVER_FAILURE() {
        return ResponseCode$.MODULE$.SERVER_FAILURE();
    }

    public static Enumeration.Value FORMAT_ERROR() {
        return ResponseCode$.MODULE$.FORMAT_ERROR();
    }

    public static Enumeration.Value SUCCESS() {
        return ResponseCode$.MODULE$.SUCCESS();
    }

    public static Enumeration$ValueSet$ ValueSet() {
        return ResponseCode$.MODULE$.ValueSet();
    }

    public static Enumeration$ValueOrdering$ ValueOrdering() {
        return ResponseCode$.MODULE$.ValueOrdering();
    }

    public static Enumeration.Value withName(String s2) {
        return ResponseCode$.MODULE$.withName(s2);
    }

    public static Enumeration.Value apply(int x) {
        return ResponseCode$.MODULE$.apply(x);
    }

    public static int maxId() {
        return ResponseCode$.MODULE$.maxId();
    }

    public static Enumeration.ValueSet values() {
        return ResponseCode$.MODULE$.values();
    }

    public static String toString() {
        return ResponseCode$.MODULE$.toString();
    }
}

