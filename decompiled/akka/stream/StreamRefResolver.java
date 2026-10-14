/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.annotation.DoNotInherit;
import akka.stream.SinkRef;
import akka.stream.SourceRef;
import akka.stream.StreamRefResolver$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]<Qa\u0003\u0007\t\u0002E1Qa\u0005\u0007\t\u0002QAQaY\u0001\u0005\u0002\u0011DQ!Z\u0001\u0005B\u0019DQ!Z\u0001\u0005B1DQ!]\u0001\u0005BI4qa\u0005\u0007\u0011\u0002G\u0005!\u0005C\u0003'\r\u0019\u0005q\u0005C\u0003'\r\u0019\u0005A\tC\u0003M\r\u0019\u0005Q\nC\u0003U\r\u0019\u0005Q+A\tTiJ,\u0017-\u001c*fMJ+7o\u001c7wKJT!!\u0004\b\u0002\rM$(/Z1n\u0015\u0005y\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0013\u00035\tABA\tTiJ,\u0017-\u001c*fMJ+7o\u001c7wKJ\u001c2!A\u000b\u001c!\t1\u0012$D\u0001\u0018\u0015\u0005A\u0012!B:dC2\f\u0017B\u0001\u000e\u0018\u0005\u0019\te.\u001f*fMB\u0019AdH\u0011\u000e\u0003uQ!A\b\b\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\u0001j\"aC#yi\u0016t7/[8o\u0013\u0012\u0004\"A\u0005\u0004\u0014\u0007\u0019)2\u0005\u0005\u0002\u001dI%\u0011Q%\b\u0002\n\u000bb$XM\\:j_:\fQ\u0003^8TKJL\u0017\r\\5{CRLwN\u001c$pe6\fG/\u0006\u0002)wQ\u0011\u0011\u0006\u000e\t\u0003UEr!aK\u0018\u0011\u00051:R\"A\u0017\u000b\u00059\u0002\u0012A\u0002\u001fs_>$h(\u0003\u00021/\u00051\u0001K]3eK\u001aL!AM\u001a\u0003\rM#(/\u001b8h\u0015\t\u0001t\u0003C\u00036\u000f\u0001\u0007a'A\u0002sK\u001a\u00042AE\u001c:\u0013\tADBA\u0005T_V\u00148-\u001a*fMB\u0011!h\u000f\u0007\u0001\t\u0015atA1\u0001>\u0005\u0005!\u0016C\u0001 B!\t1r(\u0003\u0002A/\t9aj\u001c;iS:<\u0007C\u0001\fC\u0013\t\u0019uCA\u0002B]f,\"!R&\u0015\u0005%2\u0005\"B\u001b\t\u0001\u00049\u0005c\u0001\nI\u0015&\u0011\u0011\n\u0004\u0002\b'&t7NU3g!\tQ4\nB\u0003=\u0011\t\u0007Q(\u0001\tsKN|GN^3T_V\u00148-\u001a*fMV\u0011a*\u0015\u000b\u0003\u001fJ\u00032AE\u001cQ!\tQ\u0014\u000bB\u0003=\u0013\t\u0007Q\bC\u0003T\u0013\u0001\u0007\u0011&A\ntKJL\u0017\r\\5{K\u0012\u001cv.\u001e:dKJ+g-\u0001\bsKN|GN^3TS:\\'+\u001a4\u0016\u0005YKFCA,[!\r\u0011\u0002\n\u0017\t\u0003ue#Q\u0001\u0010\u0006C\u0002uBQa\u0017\u0006A\u0002%\n\u0011c]3sS\u0006d\u0017N_3e'&t7NU3gQ\t1Q\f\u0005\u0002_C6\tqL\u0003\u0002a\u001d\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\t|&\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\u0018A\u0002\u001fj]&$h\bF\u0001\u0012\u0003\r9W\r\u001e\u000b\u0003C\u001dDQ\u0001[\u0002A\u0002%\faa]=ti\u0016l\u0007C\u0001\u000fk\u0013\tYWDA\u0006BGR|'oU=ti\u0016lGCA\u0011n\u0011\u0015AG\u00011\u0001o!\tar.\u0003\u0002q;\tQ2\t\\1tg&\u001c\u0017i\u0019;peNK8\u000f^3n!J|g/\u001b3fe\u0006y1M]3bi\u0016,\u0005\u0010^3og&|g\u000e\u0006\u0002\"g\")\u0001.\u0002a\u0001iB\u0011A$^\u0005\u0003mv\u00111#\u0012=uK:$W\rZ!di>\u00148+_:uK6\u0004")
@DoNotInherit
public interface StreamRefResolver
extends Extension {
    public static StreamRefResolver createExtension(ExtendedActorSystem system) {
        return StreamRefResolver$.MODULE$.createExtension(system);
    }

    public static StreamRefResolver get(ClassicActorSystemProvider system) {
        return StreamRefResolver$.MODULE$.get(system);
    }

    public static StreamRefResolver get(ActorSystem system) {
        return StreamRefResolver$.MODULE$.get(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return StreamRefResolver$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return StreamRefResolver$.MODULE$.apply(system);
    }

    public <T> String toSerializationFormat(SourceRef<T> var1);

    public <T> String toSerializationFormat(SinkRef<T> var1);

    public <T> SourceRef<T> resolveSourceRef(String var1);

    public <T> SinkRef<T> resolveSinkRef(String var1);
}

