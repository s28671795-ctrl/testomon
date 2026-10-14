/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.SeqActorName;
import java.util.concurrent.atomic.AtomicLong;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r3Qa\u0002\u0005\u0003\u00199A\u0001b\u0005\u0001\u0003\u0006\u0004%\t!\u0006\u0005\tG\u0001\u0011\t\u0011)A\u0005-!AA\u0005\u0001B\u0001B\u0003%Q\u0005C\u00032\u0001\u0011\u0005!\u0007C\u00037\u0001\u0011\u0005q\u0007C\u00039\u0001\u0011\u0005\u0011H\u0001\tTKF\f5\r^8s\u001d\u0006lW-S7qY*\u0011\u0011BC\u0001\u0005S6\u0004HN\u0003\u0002\f\u0019\u000511\u000f\u001e:fC6T\u0011!D\u0001\u0005C.\\\u0017m\u0005\u0002\u0001\u001fA\u0011\u0001#E\u0007\u0002\u0011%\u0011!\u0003\u0003\u0002\r'\u0016\f\u0018i\u0019;pe:\u000bW.Z\u0001\u0007aJ,g-\u001b=\u0004\u0001U\ta\u0003\u0005\u0002\u0018A9\u0011\u0001D\b\t\u00033qi\u0011A\u0007\u0006\u00037Q\ta\u0001\u0010:p_Rt$\"A\u000f\u0002\u000bM\u001c\u0017\r\\1\n\u0005}a\u0012A\u0002)sK\u0012,g-\u0003\u0002\"E\t11\u000b\u001e:j]\u001eT!a\b\u000f\u0002\u000fA\u0014XMZ5yA\u000591m\\;oi\u0016\u0014\bC\u0001\u00140\u001b\u00059#B\u0001\u0015*\u0003\u0019\tGo\\7jG*\u0011!fK\u0001\u000bG>t7-\u001e:sK:$(B\u0001\u0017.\u0003\u0011)H/\u001b7\u000b\u00039\nAA[1wC&\u0011\u0001g\n\u0002\u000b\u0003R|W.[2M_:<\u0017A\u0002\u001fj]&$h\bF\u00024iU\u0002\"\u0001\u0005\u0001\t\u000bM!\u0001\u0019\u0001\f\t\u000b\u0011\"\u0001\u0019A\u0013\u0002\t9,\u0007\u0010\u001e\u000b\u0002-\u0005!1m\u001c9z)\ty!\bC\u0003<\r\u0001\u0007a#A\u0005oK^\u0004&/\u001a4jq\"\u0012\u0001!\u0010\t\u0003}\u0005k\u0011a\u0010\u0006\u0003\u00012\t!\"\u00198o_R\fG/[8o\u0013\t\u0011uHA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class SeqActorNameImpl
extends SeqActorName {
    private final String prefix;
    private final AtomicLong counter;

    public String prefix() {
        return this.prefix;
    }

    @Override
    public String next() {
        return new StringBuilder(11).append(this.prefix()).append('-').append(this.counter.getAndIncrement()).toString();
    }

    @Override
    public SeqActorName copy(String newPrefix) {
        return new SeqActorNameImpl(newPrefix, this.counter);
    }

    public SeqActorNameImpl(String prefix, AtomicLong counter) {
        this.prefix = prefix;
        this.counter = counter;
    }
}

