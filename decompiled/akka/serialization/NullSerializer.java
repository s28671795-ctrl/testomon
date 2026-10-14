/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.serialization.Serializer;
import java.io.NotSerializableException;
import scala.Array$;
import scala.Option;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d<Qa\u0003\u0007\t\u0002E1Qa\u0005\u0007\t\u0002QAQ\u0001I\u0001\u0005\u0002\u00194Aa\u0005\u0007\u0001-!)\u0001e\u0001C\u0001C!9!e\u0001b\u0001\n\u0003\u0019\u0003B\u0002\u0016\u0004A\u0003%A\u0005C\u0003,\u0007\u0011\u0005A\u0006C\u00031\u0007\u0011\u0005\u0011\u0007C\u00036\u0007\u0011\u0005a\u0007C\u0003:\u0007\u0011\u0005!(\u0001\bOk2d7+\u001a:jC2L'0\u001a:\u000b\u00055q\u0011!D:fe&\fG.\u001b>bi&|gNC\u0001\u0010\u0003\u0011\t7n[1\u0004\u0001A\u0011!#A\u0007\u0002\u0019\tqa*\u001e7m'\u0016\u0014\u0018.\u00197ju\u0016\u00148CA\u0001\u0016!\t\u00112aE\u0002\u0004/u\u0001\"\u0001G\u000e\u000e\u0003eQ\u0011AG\u0001\u0006g\u000e\fG.Y\u0005\u00039e\u0011a!\u00118z%\u00164\u0007C\u0001\n\u001f\u0013\tyBB\u0001\u0006TKJL\u0017\r\\5{KJ\fa\u0001P5oSRtD#A\u000b\u0002\u00179,H\u000e\\!t\u0005f$Xm]\u000b\u0002IA\u0019\u0001$J\u0014\n\u0005\u0019J\"!B!se\u0006L\bC\u0001\r)\u0013\tI\u0013D\u0001\u0003CsR,\u0017\u0001\u00048vY2\f5OQ=uKN\u0004\u0013aD5oG2,H-Z'b]&4Wm\u001d;\u0016\u00035\u0002\"\u0001\u0007\u0018\n\u0005=J\"a\u0002\"p_2,\u0017M\\\u0001\u000bS\u0012,g\u000e^5gS\u0016\u0014X#\u0001\u001a\u0011\u0005a\u0019\u0014B\u0001\u001b\u001a\u0005\rIe\u000e^\u0001\ti>\u0014\u0015N\\1ssR\u0011Ae\u000e\u0005\u0006q%\u0001\raF\u0001\u0002_\u0006QaM]8n\u0005&t\u0017M]=\u0015\u0007]YT\bC\u0003=\u0015\u0001\u0007A%A\u0003csR,7\u000fC\u0003?\u0015\u0001\u0007q(A\u0003dY\u0006T(\u0010E\u0002\u0019\u0001\nK!!Q\r\u0003\r=\u0003H/[8oa\t\u0019\u0005\u000bE\u0002E\u0017:s!!R%\u0011\u0005\u0019KR\"A$\u000b\u0005!\u0003\u0012A\u0002\u001fs_>$h(\u0003\u0002K3\u00051\u0001K]3eK\u001aL!\u0001T'\u0003\u000b\rc\u0017m]:\u000b\u0005)K\u0002CA(Q\u0019\u0001!\u0011\"U\u001f\u0002\u0002\u0003\u0005)\u0011\u0001*\u0003\u0007}#\u0013(\u0005\u0002T-B\u0011\u0001\u0004V\u0005\u0003+f\u0011qAT8uQ&tw\r\u0005\u0002\u0019/&\u0011\u0001,\u0007\u0002\u0004\u0003:L\bf\u0001\u0006[KB\u0019\u0001dW/\n\u0005qK\"A\u0002;ie><8\u000f\u0005\u0002_G6\tqL\u0003\u0002aC\u0006\u0011\u0011n\u001c\u0006\u0002E\u0006!!.\u0019<b\u0013\t!wL\u0001\rO_R\u001cVM]5bY&T\u0018M\u00197f\u000bb\u001cW\r\u001d;j_:\u001c\u0013!\u0018\u000b\u0002#\u0001")
public class NullSerializer
implements Serializer {
    private final byte[] nullAsBytes;

    @Override
    public final Object fromBinary(byte[] bytes) {
        return Serializer.fromBinary$(this, bytes);
    }

    @Override
    public final Object fromBinary(byte[] bytes, Class<?> clazz) throws NotSerializableException {
        return Serializer.fromBinary$(this, bytes, clazz);
    }

    public byte[] nullAsBytes() {
        return this.nullAsBytes;
    }

    @Override
    public boolean includeManifest() {
        return false;
    }

    @Override
    public int identifier() {
        return 0;
    }

    @Override
    public byte[] toBinary(Object o) {
        return this.nullAsBytes();
    }

    @Override
    public Object fromBinary(byte[] bytes, Option<Class<?>> clazz) throws NotSerializableException {
        return null;
    }

    public NullSerializer() {
        Serializer.$init$(this);
        this.nullAsBytes = (byte[])Array$.MODULE$.apply(Nil$.MODULE$, ClassTag$.MODULE$.Byte());
    }
}

