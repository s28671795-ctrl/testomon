/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.serialization.AsyncSerializer;
import akka.serialization.SerializerWithStringManifest;
import scala.concurrent.Await$;
import scala.concurrent.duration.Duration$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)3Qa\u0002\u0005\u0002\u00025A\u0001\"\u0006\u0001\u0003\u0002\u0003\u0006IA\u0006\u0005\u00069\u0001!\t!\b\u0005\bA\u0001\u0011\r\u0011\"\u0003\"\u0011\u0019A\u0003\u0001)A\u0005E!)\u0011\u0006\u0001C#U!)\u0011\b\u0001C#u\t\t\u0013i]=oGN+'/[1mSj,'oV5uQN#(/\u001b8h\u001b\u0006t\u0017NZ3ti*\u0011\u0011BC\u0001\u000eg\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8\u000b\u0003-\tA!Y6lC\u000e\u00011c\u0001\u0001\u000f%A\u0011q\u0002E\u0007\u0002\u0011%\u0011\u0011\u0003\u0003\u0002\u001d'\u0016\u0014\u0018.\u00197ju\u0016\u0014x+\u001b;i'R\u0014\u0018N\\4NC:Lg-Z:u!\ty1#\u0003\u0002\u0015\u0011\ty\u0011i]=oGN+'/[1mSj,'/\u0001\u0004tsN$X-\u001c\t\u0003/ii\u0011\u0001\u0007\u0006\u00033)\tQ!Y2u_JL!a\u0007\r\u0003'\u0015CH/\u001a8eK\u0012\f5\r^8s'f\u001cH/Z7\u0002\rqJg.\u001b;?)\tqr\u0004\u0005\u0002\u0010\u0001!)QC\u0001a\u0001-\u0005\u0019An\\4\u0016\u0003\t\u0002\"a\t\u0014\u000e\u0003\u0011R!!\n\u0006\u0002\u000b\u00154XM\u001c;\n\u0005\u001d\"#A\u0004'pO\u001eLgnZ!eCB$XM]\u0001\u0005Y><\u0007%\u0001\u0005u_\nKg.\u0019:z)\tYC\u0007E\u0002-_Ej\u0011!\f\u0006\u0002]\u0005)1oY1mC&\u0011\u0001'\f\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0003YIJ!aM\u0017\u0003\t\tKH/\u001a\u0005\u0006k\u0015\u0001\rAN\u0001\u0002_B\u0011AfN\u0005\u0003q5\u0012a!\u00118z%\u00164\u0017A\u00034s_6\u0014\u0015N\\1ssR\u0019agO\u001f\t\u000bq2\u0001\u0019A\u0016\u0002\u000b\tLH/Z:\t\u000by2\u0001\u0019A \u0002\u00115\fg.\u001b4fgR\u0004\"\u0001Q$\u000f\u0005\u0005+\u0005C\u0001\".\u001b\u0005\u0019%B\u0001#\r\u0003\u0019a$o\\8u}%\u0011a)L\u0001\u0007!J,G-\u001a4\n\u0005!K%AB*ue&twM\u0003\u0002G[\u0001")
public abstract class AsyncSerializerWithStringManifest
extends SerializerWithStringManifest
implements AsyncSerializer {
    private final LoggingAdapter log;

    private LoggingAdapter log() {
        return this.log;
    }

    @Override
    public final byte[] toBinary(Object o) {
        this.log().warning("Async serializer called synchronously. This will block. Async serializers should only be used for akka persistence plugins that support them. Class: {}", o.getClass());
        return Await$.MODULE$.result(this.toBinaryAsync(o), Duration$.MODULE$.Inf());
    }

    @Override
    public final Object fromBinary(byte[] bytes, String manifest) {
        this.log().warning("Async serializer called synchronously. This will block. Async serializers should only be used for akka persistence plugins that support them. Manifest: [{}]", (Object)manifest);
        return Await$.MODULE$.result(this.fromBinaryAsync(bytes, manifest), Duration$.MODULE$.Inf());
    }

    public AsyncSerializerWithStringManifest(ExtendedActorSystem system) {
        this.log = Logging$.MODULE$.apply(system, AsyncSerializerWithStringManifest.class, LogSource$.MODULE$.fromAnyClass());
    }
}

