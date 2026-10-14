/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.internal.DomainName$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=;aAB\u0004\t\u00025yaAB\t\b\u0011\u0003i!\u0003C\u0003\u001a\u0003\u0011\u00051\u0004C\u0003\u001d\u0003\u0011\u0005Q\u0004C\u0003/\u0003\u0011\u0005q\u0006C\u0003=\u0003\u0011\u0005Q(\u0001\u0006E_6\f\u0017N\u001c(b[\u0016T!\u0001C\u0005\u0002\u0011%tG/\u001a:oC2T!AC\u0006\u0002\u0007\u0011t7O\u0003\u0002\r\u001b\u0005\u0011\u0011n\u001c\u0006\u0002\u001d\u0005!\u0011m[6b!\t\u0001\u0012!D\u0001\b\u0005)!u.\\1j]:\u000bW.Z\n\u0003\u0003M\u0001\"\u0001F\f\u000e\u0003UQ\u0011AF\u0001\u0006g\u000e\fG.Y\u0005\u00031U\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u0003=\ta\u0001\\3oORDGC\u0001\u0010\"!\t!r$\u0003\u0002!+\t)1\u000b[8si\")!e\u0001a\u0001G\u0005!a.Y7f!\t!3F\u0004\u0002&SA\u0011a%F\u0007\u0002O)\u0011\u0001FG\u0001\u0007yI|w\u000e\u001e \n\u0005)*\u0012A\u0002)sK\u0012,g-\u0003\u0002-[\t11\u000b\u001e:j]\u001eT!AK\u000b\u0002\u000b]\u0014\u0018\u000e^3\u0015\u0007A\u001a4\b\u0005\u0002\u0015c%\u0011!'\u0006\u0002\u0005+:LG\u000fC\u00035\t\u0001\u0007Q'\u0001\u0002jiB\u0011a'O\u0007\u0002o)\u0011\u0001(D\u0001\u0005kRLG.\u0003\u0002;o\t\t\")\u001f;f'R\u0014\u0018N\\4Ck&dG-\u001a:\t\u000b\t\"\u0001\u0019A\u0012\u0002\u000bA\f'o]3\u0015\u0007\rr$\tC\u00035\u000b\u0001\u0007q\b\u0005\u00027\u0001&\u0011\u0011i\u000e\u0002\r\u0005f$X-\u0013;fe\u0006$xN\u001d\u0005\u0006\u0007\u0016\u0001\r\u0001R\u0001\u0004[N<\u0007C\u0001\u001cF\u0013\t1uG\u0001\u0006CsR,7\u000b\u001e:j]\u001eD#!\u0001%\u0011\u0005%cU\"\u0001&\u000b\u0005-k\u0011AC1o]>$\u0018\r^5p]&\u0011QJ\u0013\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001\u0011\u0002")
@InternalApi
public final class DomainName {
    public static String parse(ByteIterator it, ByteString msg) {
        return DomainName$.MODULE$.parse(it, msg);
    }

    public static void write(ByteStringBuilder it, String name) {
        DomainName$.MODULE$.write(it, name);
    }

    public static short length(String name) {
        return DomainName$.MODULE$.length(name);
    }
}

