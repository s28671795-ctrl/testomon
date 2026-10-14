/*
 * Decompiled with CFR 0.152.
 */
package akka.compat;

import akka.annotation.InternalApi;
import akka.compat.PartialFunction$;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y:a\u0001B\u0003\t\u0002\u001dIaAB\u0006\u0006\u0011\u00039A\u0002C\u0003\u0014\u0003\u0011\u0005Q\u0003C\u0003\u0017\u0003\u0011\u0005q#A\bQCJ$\u0018.\u00197Gk:\u001cG/[8o\u0015\t1q!\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002\u0011\u0005!\u0011m[6b!\tQ\u0011!D\u0001\u0006\u0005=\u0001\u0016M\u001d;jC24UO\\2uS>t7CA\u0001\u000e!\tq\u0011#D\u0001\u0010\u0015\u0005\u0001\u0012!B:dC2\f\u0017B\u0001\n\u0010\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\n\u000311'o\\7Gk:\u001cG/[8o+\rARd\n\u000b\u00033%\u0002BA\u0004\u000e\u001cM%\u00111b\u0004\t\u00039ua\u0001\u0001B\u0003\u001f\u0007\t\u0007qDA\u0001B#\t\u00013\u0005\u0005\u0002\u000fC%\u0011!e\u0004\u0002\b\u001d>$\b.\u001b8h!\tqA%\u0003\u0002&\u001f\t\u0019\u0011I\\=\u0011\u0005q9C!\u0002\u0015\u0004\u0005\u0004y\"!\u0001\"\t\u000b)\u001a\u0001\u0019A\u0016\u0002\u0003\u0019\u0004BA\u0004\u0017\u001cM%\u0011Qf\u0004\u0002\n\rVt7\r^5p]FB#!A\u0018\u0011\u0005A\u001aT\"A\u0019\u000b\u0005I:\u0011AC1o]>$\u0018\r^5p]&\u0011A'\r\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001_\u0001")
@InternalApi
public final class PartialFunction {
    public static <A, B> scala.PartialFunction<A, B> fromFunction(Function1<A, B> f) {
        return PartialFunction$.MODULE$.fromFunction(f);
    }
}

