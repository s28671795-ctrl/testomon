/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i2Aa\u0002\u0005\u0003\u001b!AA\u0004\u0001B\u0001B\u0003%Q\u0004\u0003\u0005&\u0001\t\u0015\r\u0011\"\u0001'\u0011!Y\u0003A!A!\u0002\u00139\u0003\u0002\u0003\u0017\u0001\u0005\u0003\u0005\u000b\u0011B\u0017\t\u000bA\u0002A\u0011A\u0019\t\u000bA\u0002A\u0011A\u001c\u0003=%{u\n]3sCRLwN\\%oG>l\u0007\u000f\\3uK\u0016C8-\u001a9uS>t'BA\u0005\u000b\u0003\u0019\u0019HO]3b[*\t1\"\u0001\u0003bW.\f7\u0001A\n\u0003\u00019\u0001\"aD\r\u000f\u0005A1bBA\t\u0015\u001b\u0005\u0011\"BA\n\r\u0003\u0019a$o\\8u}%\tQ#A\u0003tG\u0006d\u0017-\u0003\u0002\u00181\u00059\u0001/Y2lC\u001e,'\"A\u000b\n\u0005iY\"\u0001\u0005*v]RLW.Z#yG\u0016\u0004H/[8o\u0015\t9\u0002$A\u0004nKN\u001c\u0018mZ3\u0011\u0005y\u0011cBA\u0010!!\t\t\u0002$\u0003\u0002\"1\u00051\u0001K]3eK\u001aL!a\t\u0013\u0003\rM#(/\u001b8h\u0015\t\t\u0003$A\u0003d_VtG/F\u0001(!\tA\u0013&D\u0001\u0019\u0013\tQ\u0003D\u0001\u0003M_:<\u0017AB2pk:$\b%A\u0003dCV\u001cX\r\u0005\u0002\u0010]%\u0011qf\u0007\u0002\n)\"\u0014xn^1cY\u0016\fa\u0001P5oSRtD\u0003\u0002\u001a5kY\u0002\"a\r\u0001\u000e\u0003!AQ\u0001H\u0003A\u0002uAQ!J\u0003A\u0002\u001dBQ\u0001L\u0003A\u00025\"2A\r\u001d:\u0011\u0015)c\u00011\u0001(\u0011\u0015ac\u00011\u0001.\u0001")
public final class IOOperationIncompleteException
extends RuntimeException {
    private final long count;

    public long count() {
        return this.count;
    }

    public IOOperationIncompleteException(String message, long count, Throwable cause) {
        this.count = count;
        super(message, cause);
    }

    public IOOperationIncompleteException(long count, Throwable cause) {
        this(new StringBuilder(62).append("IO operation was stopped unexpectedly after ").append(count).append(" bytes because of ").append(cause).toString(), count, cause);
    }
}

