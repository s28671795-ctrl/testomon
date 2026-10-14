/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Always$;
import akka.stream.impl.TransferState;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005):a!\u0002\u0004\t\u0002)aaA\u0002\b\u0007\u0011\u0003Qq\u0002C\u0003\u001a\u0003\u0011\u00051\u0004C\u0003\u001d\u0003\u0011\u0005Q\u0004C\u0003\"\u0003\u0011\u0005Q$\u0001\u0004BY^\f\u0017p\u001d\u0006\u0003\u000f!\tA![7qY*\u0011\u0011BC\u0001\u0007gR\u0014X-Y7\u000b\u0003-\tA!Y6lCB\u0011Q\"A\u0007\u0002\r\t1\u0011\t\\<bsN\u001c2!\u0001\t\u0017!\t\tB#D\u0001\u0013\u0015\u0005\u0019\u0012!B:dC2\f\u0017BA\u000b\u0013\u0005\u0019\te.\u001f*fMB\u0011QbF\u0005\u00031\u0019\u0011Q\u0002\u0016:b]N4WM]*uCR,\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u00031\tq![:SK\u0006$\u00170F\u0001\u001f!\t\tr$\u0003\u0002!%\t9!i\\8mK\u0006t\u0017aC5t\u0007>l\u0007\u000f\\3uK\u0012D#!A\u0012\u0011\u0005\u0011:S\"A\u0013\u000b\u0005\u0019R\u0011AC1o]>$\u0018\r^5p]&\u0011\u0001&\n\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001G\u0001")
@InternalApi
public final class Always {
    public static boolean isCompleted() {
        return Always$.MODULE$.isCompleted();
    }

    public static boolean isReady() {
        return Always$.MODULE$.isReady();
    }

    public static TransferState $amp$amp(TransferState other) {
        return Always$.MODULE$.$amp$amp(other);
    }

    public static TransferState $bar$bar(TransferState other) {
        return Always$.MODULE$.$bar$bar(other);
    }

    public static boolean isExecutable() {
        return Always$.MODULE$.isExecutable();
    }
}

