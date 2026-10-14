/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.NotInitialized$;
import akka.stream.impl.TransferState;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005):a!\u0002\u0004\t\u0002)aaA\u0002\b\u0007\u0011\u0003Qq\u0002C\u0003\u001a\u0003\u0011\u00051\u0004C\u0003\u001d\u0003\u0011\u0005Q\u0004C\u0003\"\u0003\u0011\u0005Q$\u0001\bO_RLe.\u001b;jC2L'0\u001a3\u000b\u0005\u001dA\u0011\u0001B5na2T!!\u0003\u0006\u0002\rM$(/Z1n\u0015\u0005Y\u0011\u0001B1lW\u0006\u0004\"!D\u0001\u000e\u0003\u0019\u0011aBT8u\u0013:LG/[1mSj,GmE\u0002\u0002!Y\u0001\"!\u0005\u000b\u000e\u0003IQ\u0011aE\u0001\u0006g\u000e\fG.Y\u0005\u0003+I\u0011a!\u00118z%\u00164\u0007CA\u0007\u0018\u0013\tAbAA\u0007Ue\u0006t7OZ3s'R\fG/Z\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\tA\"A\u0004jgJ+\u0017\rZ=\u0016\u0003y\u0001\"!E\u0010\n\u0005\u0001\u0012\"a\u0002\"p_2,\u0017M\\\u0001\fSN\u001cu.\u001c9mKR,G\r\u000b\u0002\u0002GA\u0011AeJ\u0007\u0002K)\u0011aEC\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001\u0015&\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0005\u0001\u0019\u0003")
@InternalApi
public final class NotInitialized {
    public static boolean isCompleted() {
        return NotInitialized$.MODULE$.isCompleted();
    }

    public static boolean isReady() {
        return NotInitialized$.MODULE$.isReady();
    }

    public static TransferState $amp$amp(TransferState other) {
        return NotInitialized$.MODULE$.$amp$amp(other);
    }

    public static TransferState $bar$bar(TransferState other) {
        return NotInitialized$.MODULE$.$bar$bar(other);
    }

    public static boolean isExecutable() {
        return NotInitialized$.MODULE$.isExecutable();
    }
}

