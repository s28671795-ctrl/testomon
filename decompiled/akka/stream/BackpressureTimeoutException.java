/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.StreamTimeoutException;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00012Aa\u0001\u0003\u0003\u0013!Aa\u0002\u0001B\u0001B\u0003%q\u0002C\u0003\u001d\u0001\u0011\u0005QD\u0001\u000fCC\u000e\\\u0007O]3tgV\u0014X\rV5nK>,H/\u0012=dKB$\u0018n\u001c8\u000b\u0005\u00151\u0011AB:ue\u0016\fWNC\u0001\b\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001A\u0003\t\u0003\u00171i\u0011\u0001B\u0005\u0003\u001b\u0011\u0011ac\u0015;sK\u0006lG+[7f_V$X\t_2faRLwN\\\u0001\u0004[N<\u0007C\u0001\t\u001a\u001d\t\tr\u0003\u0005\u0002\u0013+5\t1C\u0003\u0002\u0015\u0011\u00051AH]8pizR\u0011AF\u0001\u0006g\u000e\fG.Y\u0005\u00031U\ta\u0001\u0015:fI\u00164\u0017B\u0001\u000e\u001c\u0005\u0019\u0019FO]5oO*\u0011\u0001$F\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005yy\u0002CA\u0006\u0001\u0011\u0015q!\u00011\u0001\u0010\u0001")
public final class BackpressureTimeoutException
extends StreamTimeoutException {
    public BackpressureTimeoutException(String msg) {
        super(msg);
    }
}

