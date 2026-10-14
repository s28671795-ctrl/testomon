/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.annotation.InternalApi;
import akka.util.Clock$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005];aAC\u0006\t\u00025yaAB\t\f\u0011\u0003i!\u0003C\u0003@\u0003\u0011\u0005\u0001\tC\u0003B\u0003\u0011\u0005#\tC\u0003B\u0003\u0011\u0005\u0003\nC\u0003N\u0003\u0011\u0005c\nC\u0003P\u0003\u0011\u0005\u0003K\u0002\u0005\u0012\u0017A\u0005\u0019\u0013A\u0007!\u0011\u0015!sA\"\u0001'\u0011\u0015QsA\"\u0001,\u0003\u0015\u0019En\\2l\u0015\taQ\"\u0001\u0003vi&d'\"\u0001\b\u0002\t\u0005\\7.\u0019\t\u0003!\u0005i\u0011a\u0003\u0002\u0006\u00072|7m[\n\u0005\u0003MIB\b\u0005\u0002\u0015/5\tQCC\u0001\u0017\u0003\u0015\u00198-\u00197b\u0013\tARC\u0001\u0004B]f\u0014VM\u001a\t\u00045uyR\"A\u000e\u000b\u0005qi\u0011!B1di>\u0014\u0018B\u0001\u0010\u001c\u0005-)\u0005\u0010^3og&|g.\u00133\u0011\u0005A91cA\u0004\u0014CA\u0011!DI\u0005\u0003Gm\u0011\u0011\"\u0012=uK:\u001c\u0018n\u001c8\u0002\u0017\r,(O]3oiRKW.Z\u0002\u0001)\u00059\u0003C\u0001\u000b)\u0013\tISC\u0001\u0003M_:<\u0017aC3be2LWM\u001d+j[\u0016$\"a\n\u0017\t\u000b5J\u0001\u0019\u0001\u0018\u0002\u0011\u0011,(/\u0019;j_:\u0004\"aL\u001a\u000e\u0003AR!!L\u0019\u000b\u0005I*\u0012AC2p]\u000e,(O]3oi&\u0011A\u0007\r\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8oQ\t9a\u0007\u0005\u00028u5\t\u0001H\u0003\u0002:\u001b\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005mB$aC%oi\u0016\u0014h.\u00197Ba&\u0004\"AG\u001f\n\u0005yZ\"aE#yi\u0016t7/[8o\u0013\u0012\u0004&o\u001c<jI\u0016\u0014\u0018A\u0002\u001fj]&$h\bF\u0001\u0010\u0003\r9W\r\u001e\u000b\u0003?\rCQ\u0001R\u0002A\u0002\u0015\u000baa]=ti\u0016l\u0007C\u0001\u000eG\u0013\t95DA\u0006BGR|'oU=ti\u0016lGCA\u0010J\u0011\u0015!E\u00011\u0001K!\tQ2*\u0003\u0002M7\tQ2\t\\1tg&\u001c\u0017i\u0019;peNK8\u000f^3n!J|g/\u001b3fe\u00061An\\8lkB,\u0012aD\u0001\u0010GJ,\u0017\r^3FqR,gn]5p]R\u0011q$\u0015\u0005\u0006\t\u001a\u0001\rA\u0015\t\u00035MK!\u0001V\u000e\u0003'\u0015CH/\u001a8eK\u0012\f5\r^8s'f\u001cH/Z7)\u0005\u00051\u0004F\u0001\u00017\u0001")
@InternalApi
public interface Clock
extends Extension {
    public static Clock createExtension(ExtendedActorSystem system) {
        return Clock$.MODULE$.createExtension(system);
    }

    public static Clock$ lookup() {
        return Clock$.MODULE$.lookup();
    }

    public static Clock get(ClassicActorSystemProvider system) {
        return Clock$.MODULE$.get(system);
    }

    public static Clock get(ActorSystem system) {
        return Clock$.MODULE$.get(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return Clock$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return Clock$.MODULE$.apply(system);
    }

    public long currentTime();

    public long earlierTime(FiniteDuration var1);
}

