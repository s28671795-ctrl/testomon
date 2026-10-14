/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.JavaVersion$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0015:a!\u0002\u0004\t\u0002!QaA\u0002\u0007\u0007\u0011\u0003AQ\u0002C\u0003\u0015\u0003\u0011\u0005a\u0003C\u0004\u0018\u0003\t\u0007I\u0011\u0001\r\t\rq\t\u0001\u0015!\u0003\u001a\u0003-Q\u0015M^1WKJ\u001c\u0018n\u001c8\u000b\u0005\u001dA\u0011\u0001B;uS2T\u0011!C\u0001\u0005C.\\\u0017\r\u0005\u0002\f\u00035\taAA\u0006KCZ\fg+\u001a:tS>t7CA\u0001\u000f!\ty!#D\u0001\u0011\u0015\u0005\t\u0012!B:dC2\f\u0017BA\n\u0011\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\u000b\u00031i\u0017M[8s-\u0016\u00148/[8o+\u0005I\u0002CA\b\u001b\u0013\tY\u0002CA\u0002J]R\fQ\"\\1k_J4VM]:j_:\u0004\u0003FA\u0001\u001f!\ty\"%D\u0001!\u0015\t\t\u0003\"\u0001\u0006b]:|G/\u0019;j_:L!a\t\u0011\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\u0015\u0003\u0001y\u0001")
@InternalApi
public final class JavaVersion {
    public static int majorVersion() {
        return JavaVersion$.MODULE$.majorVersion();
    }
}

