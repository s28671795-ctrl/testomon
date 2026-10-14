/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.actor.setup.Setup;
import akka.japi.Function;
import akka.serialization.SerializationSetup$;
import akka.serialization.SerializerDetails;
import java.util.List;
import scala.Function1;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A;Q!\u0003\u0006\t\u0002=1Q!\u0005\u0006\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQaP\u0001\u0005\u0002\u00013A!\u0005\u0006\u0003=!Aq%\u0002BC\u0002\u0013\u0005\u0001\u0006\u0003\u0005<\u000b\t\u0005\t\u0015!\u0003*\u0011\u0015IR\u0001\"\u0003=\u0003I\u0019VM]5bY&T\u0018\r^5p]N+G/\u001e9\u000b\u0005-a\u0011!D:fe&\fG.\u001b>bi&|gNC\u0001\u000e\u0003\u0011\t7n[1\u0004\u0001A\u0011\u0001#A\u0007\u0002\u0015\t\u00112+\u001a:jC2L'0\u0019;j_:\u001cV\r^;q'\t\t1\u0003\u0005\u0002\u0015/5\tQCC\u0001\u0017\u0003\u0015\u00198-\u00197b\u0013\tARC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003=\tQ!\u00199qYf$\"!\b \u0011\u0005A)1CA\u0003 !\t\u0001S%D\u0001\"\u0015\t\u00113%A\u0003tKR,\bO\u0003\u0002%\u0019\u0005)\u0011m\u0019;pe&\u0011a%\t\u0002\u0006'\u0016$X\u000f]\u0001\u0012GJ,\u0017\r^3TKJL\u0017\r\\5{KJ\u001cX#A\u0015\u0011\tQQC\u0006M\u0005\u0003WU\u0011\u0011BR;oGRLwN\\\u0019\u0011\u00055rS\"A\u0012\n\u0005=\u001a#aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0007cA\u00197q5\t!G\u0003\u00024i\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003kU\t!bY8mY\u0016\u001cG/[8o\u0013\t9$GA\u0002TKF\u0004\"\u0001E\u001d\n\u0005iR!!E*fe&\fG.\u001b>fe\u0012+G/Y5mg\u0006\u00112M]3bi\u0016\u001cVM]5bY&TXM]:!)\tiR\bC\u0003(\u0011\u0001\u0007\u0011\u0006C\u0003(\u0007\u0001\u0007\u0011&\u0001\u0004de\u0016\fG/\u001a\u000b\u0003;\u0005CQa\n\u0003A\u0002\t\u0003Ba\u0011$-\u00116\tAI\u0003\u0002F\u0019\u0005!!.\u00199j\u0013\t9EI\u0001\u0005Gk:\u001cG/[8o!\rIe\nO\u0007\u0002\u0015*\u00111\nT\u0001\u0005kRLGNC\u0001N\u0003\u0011Q\u0017M^1\n\u0005=S%\u0001\u0002'jgR\u0004")
public final class SerializationSetup
extends Setup {
    private final Function1<ExtendedActorSystem, Seq<SerializerDetails>> createSerializers;

    public static SerializationSetup create(Function<ExtendedActorSystem, List<SerializerDetails>> createSerializers) {
        return SerializationSetup$.MODULE$.create(createSerializers);
    }

    public static SerializationSetup apply(Function1<ExtendedActorSystem, Seq<SerializerDetails>> createSerializers) {
        return SerializationSetup$.MODULE$.apply(createSerializers);
    }

    public Function1<ExtendedActorSystem, Seq<SerializerDetails>> createSerializers() {
        return this.createSerializers;
    }

    public SerializationSetup(Function1<ExtendedActorSystem, Seq<SerializerDetails>> createSerializers) {
        this.createSerializers = createSerializers;
    }
}

