/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.serialization.BaseSerializer$;
import akka.serialization.ByteBufferSerializer;
import akka.serialization.Serializer;
import java.io.NotSerializableException;
import java.nio.ByteBuffer;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005u4Qa\u0003\u0007\u0003\u001dAA\u0001B\b\u0001\u0003\u0006\u0004%\t\u0001\t\u0005\tO\u0001\u0011\t\u0011)A\u0005C!)\u0001\u0006\u0001C\u0001S!)A\u0006\u0001C![!9\u0011\u0007\u0001b\u0001\n\u0003\u0012\u0004B\u0002\u001c\u0001A\u0003%1\u0007C\u00038\u0001\u0011\u0005\u0003\bC\u0003I\u0001\u0011\u0005\u0013\nC\u00038\u0001\u0011\u0005\u0003\fC\u0003I\u0001\u0011\u0005\u0003M\u0001\bM_:<7+\u001a:jC2L'0\u001a:\u000b\u00055q\u0011!D:fe&\fG.\u001b>bi&|gNC\u0001\u0010\u0003\u0011\t7n[1\u0014\t\u0001\trc\u0007\t\u0003%Ui\u0011a\u0005\u0006\u0002)\u0005)1oY1mC&\u0011ac\u0005\u0002\u0007\u0003:L(+\u001a4\u0011\u0005aIR\"\u0001\u0007\n\u0005ia!AC*fe&\fG.\u001b>feB\u0011\u0001\u0004H\u0005\u0003;1\u0011ACQ=uK\n+hMZ3s'\u0016\u0014\u0018.\u00197ju\u0016\u0014\u0018AB:zgR,Wn\u0001\u0001\u0016\u0003\u0005\u0002\"AI\u0013\u000e\u0003\rR!\u0001\n\b\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\u0019\u001a#aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0017aB:zgR,W\u000eI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005)Z\u0003C\u0001\r\u0001\u0011\u0015q2\u00011\u0001\"\u0003=Ign\u00197vI\u0016l\u0015M\\5gKN$X#\u0001\u0018\u0011\u0005Iy\u0013B\u0001\u0019\u0014\u0005\u001d\u0011un\u001c7fC:\f!\"\u001b3f]RLg-[3s+\u0005\u0019\u0004C\u0001\n5\u0013\t)4CA\u0002J]R\f1\"\u001b3f]RLg-[3sA\u0005AAo\u001c\"j]\u0006\u0014\u0018\u0010F\u0002:yy\u0002\"A\u0005\u001e\n\u0005m\u001a\"\u0001B+oSRDQ!P\u0004A\u0002E\t\u0011a\u001c\u0005\u0006\u007f\u001d\u0001\r\u0001Q\u0001\u0004EV4\u0007CA!G\u001b\u0005\u0011%BA\"E\u0003\rq\u0017n\u001c\u0006\u0002\u000b\u0006!!.\u0019<b\u0013\t9%I\u0001\u0006CsR,')\u001e4gKJ\f!B\u001a:p[\nKg.\u0019:z)\r\t\"j\u0013\u0005\u0006\u007f!\u0001\r\u0001\u0011\u0005\u0006\u0019\"\u0001\r!T\u0001\t[\u0006t\u0017NZ3tiB\u0011a*\u0016\b\u0003\u001fN\u0003\"\u0001U\n\u000e\u0003ES!AU\u0010\u0002\rq\u0012xn\u001c;?\u0013\t!6#\u0001\u0004Qe\u0016$WMZ\u0005\u0003-^\u0013aa\u0015;sS:<'B\u0001+\u0014)\tIv\fE\u0002\u00135rK!aW\n\u0003\u000b\u0005\u0013(/Y=\u0011\u0005Ii\u0016B\u00010\u0014\u0005\u0011\u0011\u0015\u0010^3\t\u000buJ\u0001\u0019A\t\u0015\u0007E\t7\rC\u0003c\u0015\u0001\u0007\u0011,A\u0003csR,7\u000fC\u0003M\u0015\u0001\u0007A\rE\u0002\u0013K\u001eL!AZ\n\u0003\r=\u0003H/[8oa\tAW\u000eE\u0002OS.L!A[,\u0003\u000b\rc\u0017m]:\u0011\u00051lG\u0002\u0001\u0003\n]\u000e\f\t\u0011!A\u0003\u0002=\u00141a\u0018\u00132#\t\u00018\u000f\u0005\u0002\u0013c&\u0011!o\u0005\u0002\b\u001d>$\b.\u001b8h!\t\u0011B/\u0003\u0002v'\t\u0019\u0011I\\=)\u0005\u00019\bC\u0001=|\u001b\u0005I(B\u0001>\u000f\u0003)\tgN\\8uCRLwN\\\u0005\u0003yf\u00141\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public final class LongSerializer
implements Serializer,
ByteBufferSerializer {
    private final ExtendedActorSystem system;
    private final int identifier;

    @Override
    public final Object fromBinary(byte[] bytes) {
        return Serializer.fromBinary$(this, bytes);
    }

    @Override
    public final Object fromBinary(byte[] bytes, Class<?> clazz) throws NotSerializableException {
        return Serializer.fromBinary$(this, bytes, clazz);
    }

    public ExtendedActorSystem system() {
        return this.system;
    }

    @Override
    public boolean includeManifest() {
        return false;
    }

    @Override
    public int identifier() {
        return this.identifier;
    }

    @Override
    public void toBinary(Object o, ByteBuffer buf) {
        buf.putLong(BoxesRunTime.unboxToLong(o));
    }

    @Override
    public Object fromBinary(ByteBuffer buf, String manifest) {
        return BoxesRunTime.boxToLong(buf.getLong());
    }

    @Override
    public byte[] toBinary(Object o) {
        byte[] result = new byte[8];
        int i = 0;
        for (long j = BoxesRunTime.unboxToLong(o); j != 0L; j >>>= 8) {
            result[i] = (byte)(j & 0xFFL);
            ++i;
        }
        return result;
    }

    @Override
    public Object fromBinary(byte[] bytes, Option<Class<?>> manifest) {
        long result = 0L;
        for (int i = 7; i >= 0; --i) {
            result <<= 8;
            result |= (long)bytes[i] & 0xFFL;
        }
        return BoxesRunTime.boxToLong(result);
    }

    public LongSerializer(ExtendedActorSystem system) {
        this.system = system;
        Serializer.$init$(this);
        this.identifier = BaseSerializer$.MODULE$.identifierFromConfig("primitive-long", system);
    }
}

