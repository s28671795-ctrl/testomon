/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.japi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y2Q\u0001B\u0003\u0002\u0002)AQA\t\u0001\u0005\u0002\rBQ!\n\u0001\u0005V\u0019BQ!\u000e\u0001\u0007\u0002Y\u0012qAU3d_Z,'O\u0003\u0002\u0007\u000f\u0005AA-[:qCR\u001c\u0007NC\u0001\t\u0003\u0011\t7n[1\u0004\u0001U\u00111BF\n\u0003\u00011\u00012!D\t\u0015\u001d\tqq\"D\u0001\u0006\u0013\t\u0001R!\u0001\u0003kCBL\u0017B\u0001\n\u0014\u00055\u0011VmY8wKJ\u0014%/\u001b3hK*\u0011\u0001#\u0002\t\u0003+Ya\u0001\u0001\u0002\u0004\u0018\u0001\u0011\u0015\r\u0001\u0007\u0002\u0002)F\u0011\u0011d\b\t\u00035ui\u0011a\u0007\u0006\u00029\u0005)1oY1mC&\u0011ad\u0007\u0002\b\u001d>$\b.\u001b8h!\tQ\u0002%\u0003\u0002\"7\t\u0019\u0011I\\=\u0002\rqJg.\u001b;?)\u0005!\u0003c\u0001\b\u0001)\u0005A\u0011N\u001c;fe:\fG\u000e\u0006\u0002\u0015O!)\u0001F\u0001a\u0001S\u00051!/Z:vYR\u0004\"A\u000b\u001a\u000f\u0005-\u0002dB\u0001\u00170\u001b\u0005i#B\u0001\u0018\n\u0003\u0019a$o\\8u}%\tA$\u0003\u000227\u00059\u0001/Y2lC\u001e,\u0017BA\u001a5\u0005%!\u0006N]8xC\ndWM\u0003\u000227\u00059!/Z2pm\u0016\u0014HC\u0001\u000b8\u0011\u0015A4\u00011\u0001*\u0003\u001d1\u0017-\u001b7ve\u0016D3a\u0001\u001e>!\rQ2(K\u0005\u0003ym\u0011a\u0001\u001e5s_^\u001c8%A\u0015")
public abstract class Recover<T>
extends japi.RecoverBridge<T> {
    @Override
    public final T internal(Throwable result) {
        return this.recover(result);
    }

    public abstract T recover(Throwable var1) throws Throwable;
}

