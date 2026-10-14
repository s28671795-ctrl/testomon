/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern.internal;

import akka.annotation.InternalStableApi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U2q!\u0003\u0006\u0011\u0002G\u0005\u0011\u0003C\u0003\u0019\u0001\u0019\u0005\u0011\u0004C\u0003\u001e\u0001\u0019\u0005\u0011\u0004C\u0003\u001f\u0001\u0019\u0005\u0011\u0004C\u0003 \u0001\u0019\u0005\u0001\u0005C\u0003'\u0001\u0019\u0005q\u0005C\u0003*\u0001\u0019\u0005!\u0006C\u0003-\u0001\u0019\u0005\u0011\u0004C\u0003.\u0001\u0019\u0005\u0011DA\fDSJ\u001cW/\u001b;Ce\u0016\f7.\u001a:UK2,W.\u001a;ss*\u00111\u0002D\u0001\tS:$XM\u001d8bY*\u0011QBD\u0001\ba\u0006$H/\u001a:o\u0015\u0005y\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001%A\u00111CF\u0007\u0002))\tQ#A\u0003tG\u0006d\u0017-\u0003\u0002\u0018)\t1\u0011I\\=SK\u001a\faa\u001c8Pa\u0016tG#\u0001\u000e\u0011\u0005MY\u0012B\u0001\u000f\u0015\u0005\u0011)f.\u001b;\u0002\u000f=t7\t\\8tK\u0006QqN\u001c%bY\u001a|\u0005/\u001a8\u0002\u001b=t7)\u00197m'V\u001c7-Z:t)\tQ\u0012\u0005C\u0003#\t\u0001\u00071%\u0001\u0007fY\u0006\u00048/\u001a3OC:|7\u000f\u0005\u0002\u0014I%\u0011Q\u0005\u0006\u0002\u0005\u0019>tw-A\u0007p]\u000e\u000bG\u000e\u001c$bS2,(/\u001a\u000b\u00035!BQAI\u0003A\u0002\r\nAc\u001c8DC2dG+[7f_V$h)Y5mkJ,GC\u0001\u000e,\u0011\u0015\u0011c\u00011\u0001$\u0003aygnQ1mY\n\u0013X-Y6fe>\u0003XM\u001c$bS2,(/Z\u0001\bgR|\u0007\u000f]3eQ\t\u0001q\u0006\u0005\u00021g5\t\u0011G\u0003\u00023\u001d\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Q\n$!E%oi\u0016\u0014h.\u00197Ti\u0006\u0014G.Z!qS\u0002")
@InternalStableApi
public interface CircuitBreakerTelemetry {
    public void onOpen();

    public void onClose();

    public void onHalfOpen();

    public void onCallSuccess(long var1);

    public void onCallFailure(long var1);

    public void onCallTimeoutFailure(long var1);

    public void onCallBreakerOpenFailure();

    public void stopped();
}

