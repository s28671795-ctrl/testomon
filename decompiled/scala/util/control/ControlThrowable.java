/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!2Q\u0001B\u0003\u0002\u00021A\u0001\"\u0007\u0001\u0003\u0002\u0003\u0006IA\u0007\u0005\u0006E\u0001!\ta\t\u0005\u0006E\u0001!\ta\n\u0002\u0011\u0007>tGO]8m)\"\u0014xn^1cY\u0016T!AB\u0004\u0002\u000f\r|g\u000e\u001e:pY*\u0011\u0001\"C\u0001\u0005kRLGNC\u0001\u000b\u0003\u0015\u00198-\u00197b\u0007\u0001\u0019\"\u0001A\u0007\u0011\u000591bBA\b\u0015\u001d\t\u00012#D\u0001\u0012\u0015\t\u00112\"\u0001\u0004=e>|GOP\u0005\u0002\u0015%\u0011Q#C\u0001\ba\u0006\u001c7.Y4f\u0013\t9\u0002DA\u0005UQJ|w/\u00192mK*\u0011Q#C\u0001\b[\u0016\u001c8/Y4f!\tYrD\u0004\u0002\u001d;A\u0011\u0001#C\u0005\u0003=%\ta\u0001\u0015:fI\u00164\u0017B\u0001\u0011\"\u0005\u0019\u0019FO]5oO*\u0011a$C\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u00112\u0003CA\u0013\u0001\u001b\u0005)\u0001\"B\r\u0003\u0001\u0004QB#\u0001\u0013")
public abstract class ControlThrowable
extends Throwable {
    public ControlThrowable(String message) {
        super(message, null, false, false);
    }

    public ControlThrowable() {
        this((String)null);
    }
}

