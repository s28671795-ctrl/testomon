/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.RecordClass;
import akka.io.dns.internal.RecordClassSerializer$;
import akka.util.ByteIterator;
import akka.util.ByteStringBuilder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u:a!\u0002\u0004\t\u00021qaA\u0002\t\u0007\u0011\u0003a\u0011\u0003C\u0003\u0019\u0003\u0011\u0005!\u0004C\u0003\u001c\u0003\u0011\u0005A\u0004C\u0003*\u0003\u0011\u0005!&A\u000bSK\u000e|'\u000fZ\"mCN\u001c8+\u001a:jC2L'0\u001a:\u000b\u0005\u001dA\u0011\u0001C5oi\u0016\u0014h.\u00197\u000b\u0005%Q\u0011a\u00013og*\u00111\u0002D\u0001\u0003S>T\u0011!D\u0001\u0005C.\\\u0017\r\u0005\u0002\u0010\u00035\taAA\u000bSK\u000e|'\u000fZ\"mCN\u001c8+\u001a:jC2L'0\u001a:\u0014\u0005\u0005\u0011\u0002CA\n\u0017\u001b\u0005!\"\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\n\u0005]!\"AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005q\u0011!\u00029beN,GCA\u000f\"!\tqr$D\u0001\t\u0013\t\u0001\u0003BA\u0006SK\u000e|'\u000fZ\"mCN\u001c\b\"\u0002\u0012\u0004\u0001\u0004\u0019\u0013AA5u!\t!s%D\u0001&\u0015\t1C\"\u0001\u0003vi&d\u0017B\u0001\u0015&\u00051\u0011\u0015\u0010^3Ji\u0016\u0014\u0018\r^8s\u0003\u00159(/\u001b;f)\rYcf\r\t\u0003'1J!!\f\u000b\u0003\tUs\u0017\u000e\u001e\u0005\u0006_\u0011\u0001\r\u0001M\u0001\u0004_V$\bC\u0001\u00132\u0013\t\u0011TEA\tCsR,7\u000b\u001e:j]\u001e\u0014U/\u001b7eKJDQ\u0001\u000e\u0003A\u0002u\t\u0011a\u0019\u0015\u0003\u0003Y\u0002\"a\u000e\u001e\u000e\u0003aR!!\u000f\u0007\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002<q\tY\u0011J\u001c;fe:\fG.\u00119jQ\t\u0001a\u0007")
@InternalApi
public final class RecordClassSerializer {
    public static void write(ByteStringBuilder out, RecordClass c) {
        RecordClassSerializer$.MODULE$.write(out, c);
    }

    public static RecordClass parse(ByteIterator it) {
        return RecordClassSerializer$.MODULE$.parse(it);
    }
}

