/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.annotation.InternalApi;
import akka.stream.impl.SeqActorName$;
import akka.stream.impl.SeqActorNameImpl;
import akka.stream.impl.streamref.StreamRefsMaster$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0011<a!\u0004\b\t\u0002I1bA\u0002\r\u000f\u0011\u0003\u0011\u0012\u0004C\u0003,\u0003\u0011\u0005A\nC\u0003N\u0003\u0011\u0005c\nC\u0003U\u0003\u0011\u0005S\u000bC\u0003X\u0003\u0011\u0005\u0003\fC\u0003X\u0003\u0011\u0005SLB\u0003\u0019\u001d\t\u0011r\u0005C\u0003,\u000f\u0011\u0005Q\u0006\u0003\u0004/\u000f\u0001\u0006Ia\f\u0005\u0007g\u001d\u0001\u000b\u0011B\u0018\t\u000bQ:A\u0011A\u001b\t\u000b\u0005;A\u0011A\u001b\u0002!M#(/Z1n%\u001647/T1ti\u0016\u0014(BA\b\u0011\u0003%\u0019HO]3b[J,gM\u0003\u0002\u0012%\u0005!\u0011.\u001c9m\u0015\t\u0019B#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002+\u0005!\u0011m[6b!\t9\u0012!D\u0001\u000f\u0005A\u0019FO]3b[J+gm]'bgR,'o\u0005\u0003\u00025\u0001J\u0005CA\u000e\u001f\u001b\u0005a\"\"A\u000f\u0002\u000bM\u001c\u0017\r\\1\n\u0005}a\"AB!osJ+g\rE\u0002\"I\u0019j\u0011A\t\u0006\u0003GQ\tQ!Y2u_JL!!\n\u0012\u0003\u0017\u0015CH/\u001a8tS>t\u0017\n\u001a\t\u0003/\u001d\u00192a\u0002\u000e)!\t\t\u0013&\u0003\u0002+E\tIQ\t\u001f;f]NLwN\\\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\ta%A\nt_V\u00148-\u001a*fMN#\u0018mZ3OC6,7\u000f\u0005\u00021c5\t\u0001#\u0003\u00023!\t\u00012+Z9BGR|'OT1nK&k\u0007\u000f\\\u0001\u0012g&t7NU3g'R\fw-\u001a(b[\u0016\u001c\u0018A\u00068fqR\u001cv.\u001e:dKJ+gm\u0015;bO\u0016t\u0015-\\3\u0015\u0003Y\u0002\"a\u000e \u000f\u0005ab\u0004CA\u001d\u001d\u001b\u0005Q$BA\u001e-\u0003\u0019a$o\\8u}%\u0011Q\bH\u0001\u0007!J,G-\u001a4\n\u0005}\u0002%AB*ue&twM\u0003\u0002>9\u0005!b.\u001a=u'&t7NU3g'R\fw-\u001a(b[\u0016D#aB\"\u0011\u0005\u0011;U\"A#\u000b\u0005\u0019#\u0012AC1o]>$\u0018\r^5p]&\u0011\u0001*\u0012\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u0005\u0002\"\u0015&\u00111J\t\u0002\u0014\u000bb$XM\\:j_:LE\r\u0015:pm&$WM\u001d\u000b\u0002-\u0005y1M]3bi\u0016,\u0005\u0010^3og&|g\u000e\u0006\u0002'\u001f\")\u0001k\u0001a\u0001#\u000611/_:uK6\u0004\"!\t*\n\u0005M\u0013#aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0017A\u00027p_.,\b/F\u0001W\u001d\t9\u0002!A\u0002hKR$\"AJ-\t\u000bA+\u0001\u0019\u0001.\u0011\u0005\u0005Z\u0016B\u0001/#\u0005-\t5\r^8s'f\u001cH/Z7\u0015\u0005\u0019r\u0006\"\u0002)\u0007\u0001\u0004y\u0006CA\u0011a\u0013\t\t'E\u0001\u000eDY\u0006\u001c8/[2BGR|'oU=ti\u0016l\u0007K]8wS\u0012,'\u000f\u000b\u0002\u0002\u0007\"\u0012\u0001a\u0011")
@InternalApi
public final class StreamRefsMaster
implements Extension {
    private final SeqActorNameImpl sourceRefStageNames = SeqActorName$.MODULE$.apply("SourceRef");
    private final SeqActorNameImpl sinkRefStageNames = SeqActorName$.MODULE$.apply("SinkRef");

    public static StreamRefsMaster get(ClassicActorSystemProvider system) {
        return StreamRefsMaster$.MODULE$.get(system);
    }

    public static StreamRefsMaster get(ActorSystem system) {
        return StreamRefsMaster$.MODULE$.get(system);
    }

    public static StreamRefsMaster$ lookup() {
        return StreamRefsMaster$.MODULE$.lookup();
    }

    public static StreamRefsMaster createExtension(ExtendedActorSystem system) {
        return StreamRefsMaster$.MODULE$.createExtension(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return StreamRefsMaster$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return StreamRefsMaster$.MODULE$.apply(system);
    }

    public String nextSourceRefStageName() {
        return this.sourceRefStageNames.next();
    }

    public String nextSinkRefStageName() {
        return this.sinkRefStageNames.next();
    }
}

