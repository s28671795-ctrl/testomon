/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.TokenBucket;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}1A!\u0002\u0004\u0003\u0017!A\u0001\u0003\u0001B\u0001B\u0003%\u0011\u0003\u0003\u0005\u0018\u0001\t\u0005\t\u0015!\u0003\u0012\u0011\u0015A\u0002\u0001\"\u0001\u001a\u0011\u0015i\u0002\u0001\"\u0011\u001f\u0005Mq\u0015M\\8US6,Gk\\6f]\n+8m[3u\u0015\t9\u0001\"\u0001\u0003vi&d'\"A\u0005\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001A\u0002\u0005\u0002\u000e\u001d5\ta!\u0003\u0002\u0010\r\tYAk\\6f]\n+8m[3u\u0003\u0011y6-\u00199\u0011\u0005I)R\"A\n\u000b\u0003Q\tQa]2bY\u0006L!AF\n\u0003\t1{gnZ\u0001\b?B,'/[8e\u0003\u0019a\u0014N\\5u}Q\u0019!d\u0007\u000f\u0011\u00055\u0001\u0001\"\u0002\t\u0004\u0001\u0004\t\u0002\"B\f\u0004\u0001\u0004\t\u0012aC2veJ,g\u000e\u001e+j[\u0016,\u0012!\u0005")
public final class NanoTimeTokenBucket
extends TokenBucket {
    @Override
    public long currentTime() {
        return System.nanoTime();
    }

    public NanoTimeTokenBucket(long _cap, long _period) {
        super(_cap, _period);
    }
}

