/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import java.io.Serializable;
import scala.Function0;
import scala.Predef$;
import scala.math.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592a!\u0003\u0006\u0002\u00021q\u0001\u0002C\u000b\u0001\u0005\u0003\u0005\u000b\u0011B\f\t\u0011i\u0001!\u0011!Q\u0001\n]AQa\u0007\u0001\u0005\u0002qA\u0011\"\t\u0001A\u0002\u0003\u0005\u000b\u0015B\f\t\u0013\t\u0002\u0001\u0019!A!B\u00139\u0002\"B\u0012\u0001\t\u0003!\u0003\"\u0002\u0015\u0001\r\u0003I\u0003\"\u0002\u0016\u0001\t\u0003Y#a\u0003+pW\u0016t')^2lKRT!a\u0003\u0007\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u001b\u0005!\u0011m[6b'\t\u0001q\u0002\u0005\u0002\u0011'5\t\u0011CC\u0001\u0013\u0003\u0015\u00198-\u00197b\u0013\t!\u0012C\u0001\u0004B]f\u0014VMZ\u0001\tG\u0006\u0004\u0018mY5us\u000e\u0001\u0001C\u0001\t\u0019\u0013\tI\u0012C\u0001\u0003M_:<\u0017A\u00058b]>\u001c()\u001a;xK\u0016tGk\\6f]N\fa\u0001P5oSRtDcA\u000f AA\u0011a\u0004A\u0007\u0002\u0015!)Qc\u0001a\u0001/!)!d\u0001a\u0001/\u0005y\u0011M^1jY\u0006\u0014G.\u001a+pW\u0016t7/\u0001\u0006mCN$X\u000b\u001d3bi\u0016\fA!\u001b8jiR\tQ\u0005\u0005\u0002\u0011M%\u0011q%\u0005\u0002\u0005+:LG/A\u0006dkJ\u0014XM\u001c;US6,W#A\f\u0002\u000b=4g-\u001a:\u0015\u0005]a\u0003\"B\u0017\t\u0001\u00049\u0012\u0001B2pgR\u0004")
public abstract class TokenBucket {
    private final long capacity;
    private final long nanosBetweenTokens;
    private long availableTokens;
    private long lastUpdate;

    public void init() {
        this.availableTokens = this.capacity;
        this.lastUpdate = this.currentTime();
    }

    public abstract long currentTime();

    public long offer(long cost) {
        long l;
        if (cost < 0L) {
            throw new IllegalArgumentException("Cost must be non-negative");
        }
        long now = this.currentTime();
        long timeElapsed = now - this.lastUpdate;
        if (timeElapsed >= this.nanosBetweenTokens) {
            if (timeElapsed < this.nanosBetweenTokens * 2L) {
                this.lastUpdate += this.nanosBetweenTokens;
                l = 1;
            } else {
                long tokensArrived = timeElapsed / this.nanosBetweenTokens;
                this.lastUpdate += tokensArrived * this.nanosBetweenTokens;
                l = tokensArrived;
            }
        } else {
            l = 0L;
        }
        long tokensArrived = l;
        this.availableTokens = package$.MODULE$.min(this.availableTokens + tokensArrived, this.capacity);
        if (cost <= this.availableTokens) {
            this.availableTokens -= cost;
            return 0L;
        }
        long remainingCost = cost - this.availableTokens;
        long timeSinceTokenArrival = now - this.lastUpdate;
        long delay = remainingCost * this.nanosBetweenTokens - timeSinceTokenArrival;
        this.availableTokens = 0L;
        this.lastUpdate = now + delay;
        return delay;
    }

    public TokenBucket(long capacity, long nanosBetweenTokens) {
        this.capacity = capacity;
        this.nanosBetweenTokens = nanosBetweenTokens;
        Predef$.MODULE$.require(capacity >= 0L, (Function0<Object>)(Function0<String> & Serializable)() -> "Capacity must be non-negative.");
        Predef$.MODULE$.require(nanosBetweenTokens > 0L, (Function0<Object>)(Function0<String> & Serializable)() -> "Time between tokens must be larger than zero nanoseconds.");
    }
}

