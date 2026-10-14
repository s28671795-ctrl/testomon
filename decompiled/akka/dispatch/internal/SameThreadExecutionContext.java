/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.internal;

import akka.annotation.InternalApi;
import akka.dispatch.internal.SameThreadExecutionContext$;
import scala.concurrent.ExecutionContext;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!:a\u0001B\u0003\t\u0002\u001dYaAB\u0007\u0006\u0011\u00039a\u0002C\u0003\u0016\u0003\u0011\u0005q\u0003C\u0003\u0019\u0003\u0011\u0005\u0011$\u0001\u000eTC6,G\u000b\u001b:fC\u0012,\u00050Z2vi&|gnQ8oi\u0016DHO\u0003\u0002\u0007\u000f\u0005A\u0011N\u001c;fe:\fGN\u0003\u0002\t\u0013\u0005AA-[:qCR\u001c\u0007NC\u0001\u000b\u0003\u0011\t7n[1\u0011\u00051\tQ\"A\u0003\u00035M\u000bW.\u001a+ie\u0016\fG-\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;\u0014\u0005\u0005y\u0001C\u0001\t\u0014\u001b\u0005\t\"\"\u0001\n\u0002\u000bM\u001c\u0017\r\\1\n\u0005Q\t\"AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005Y\u0011!B1qa2LH#\u0001\u000e\u0011\u0005mqR\"\u0001\u000f\u000b\u0005u\t\u0012AC2p]\u000e,(O]3oi&\u0011q\u0004\b\u0002\u0011\u000bb,7-\u001e;j_:\u001cuN\u001c;fqRD#!A\u0011\u0011\u0005\t*S\"A\u0012\u000b\u0005\u0011J\u0011AC1o]>$\u0018\r^5p]&\u0011ae\t\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001C\u0001")
@InternalApi
public final class SameThreadExecutionContext {
    public static ExecutionContext apply() {
        return SameThreadExecutionContext$.MODULE$.apply();
    }
}

