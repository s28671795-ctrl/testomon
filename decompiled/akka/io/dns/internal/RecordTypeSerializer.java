/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.io.dns.RecordType;
import akka.io.dns.internal.RecordTypeSerializer$;
import akka.util.ByteIterator;
import akka.util.ByteStringBuilder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U:a!\u0002\u0004\t\u00021qaA\u0002\t\u0007\u0011\u0003a\u0011\u0003C\u0003\u0019\u0003\u0011\u0005!\u0004C\u0003\u001c\u0003\u0011\u0005A\u0004C\u0003/\u0003\u0011\u0005q&\u0001\u000bSK\u000e|'\u000f\u001a+za\u0016\u001cVM]5bY&TXM\u001d\u0006\u0003\u000f!\t\u0001\"\u001b8uKJt\u0017\r\u001c\u0006\u0003\u0013)\t1\u0001\u001a8t\u0015\tYA\"\u0001\u0002j_*\tQ\"\u0001\u0003bW.\f\u0007CA\b\u0002\u001b\u00051!\u0001\u0006*fG>\u0014H\rV=qKN+'/[1mSj,'o\u0005\u0002\u0002%A\u00111CF\u0007\u0002))\tQ#A\u0003tG\u0006d\u0017-\u0003\u0002\u0018)\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002\u001d\u0005)qO]5uKR\u0019Q\u0004\t\u0015\u0011\u0005Mq\u0012BA\u0010\u0015\u0005\u0011)f.\u001b;\t\u000b\u0005\u001a\u0001\u0019\u0001\u0012\u0002\u0007=,H\u000f\u0005\u0002$M5\tAE\u0003\u0002&\u0019\u0005!Q\u000f^5m\u0013\t9CEA\tCsR,7\u000b\u001e:j]\u001e\u0014U/\u001b7eKJDQ!K\u0002A\u0002)\nQA^1mk\u0016\u0004\"a\u000b\u0017\u000e\u0003!I!!\f\u0005\u0003\u0015I+7m\u001c:e)f\u0004X-A\u0003qCJ\u001cX\r\u0006\u0002+a!)\u0011\u0007\u0002a\u0001e\u0005\u0011\u0011\u000e\u001e\t\u0003GMJ!\u0001\u000e\u0013\u0003\u0019\tKH/Z%uKJ\fGo\u001c:")
public final class RecordTypeSerializer {
    public static RecordType parse(ByteIterator it) {
        return RecordTypeSerializer$.MODULE$.parse(it);
    }

    public static void write(ByteStringBuilder out, RecordType value) {
        RecordTypeSerializer$.MODULE$.write(out, value);
    }
}

