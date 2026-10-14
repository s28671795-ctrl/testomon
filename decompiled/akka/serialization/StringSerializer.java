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

@ScalaSignature(bytes="\u0006\u0005u4Qa\u0003\u0007\u0003\u001dAA\u0001B\b\u0001\u0003\u0006\u0004%\t\u0001\t\u0005\tO\u0001\u0011\t\u0011)A\u0005C!)\u0001\u0006\u0001C\u0001S!)A\u0006\u0001C![!9\u0011\u0007\u0001b\u0001\n\u0003\u0012\u0004B\u0002\u001c\u0001A\u0003%1\u0007C\u00038\u0001\u0011\u0005\u0003\bC\u0003I\u0001\u0011\u0005\u0013\nC\u00038\u0001\u0011\u0005\u0003\fC\u0003I\u0001\u0011\u0005\u0003M\u0001\tTiJLgnZ*fe&\fG.\u001b>fe*\u0011QBD\u0001\u000eg\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8\u000b\u0003=\tA!Y6lCN!\u0001!E\f\u001c!\t\u0011R#D\u0001\u0014\u0015\u0005!\u0012!B:dC2\f\u0017B\u0001\f\u0014\u0005\u0019\te.\u001f*fMB\u0011\u0001$G\u0007\u0002\u0019%\u0011!\u0004\u0004\u0002\u000b'\u0016\u0014\u0018.\u00197ju\u0016\u0014\bC\u0001\r\u001d\u0013\tiBB\u0001\u000bCsR,')\u001e4gKJ\u001cVM]5bY&TXM]\u0001\u0007gf\u001cH/Z7\u0004\u0001U\t\u0011\u0005\u0005\u0002#K5\t1E\u0003\u0002%\u001d\u0005)\u0011m\u0019;pe&\u0011ae\t\u0002\u0014\u000bb$XM\u001c3fI\u0006\u001bGo\u001c:TsN$X-\\\u0001\bgf\u001cH/Z7!\u0003\u0019a\u0014N\\5u}Q\u0011!f\u000b\t\u00031\u0001AQAH\u0002A\u0002\u0005\nq\"\u001b8dYV$W-T1oS\u001a,7\u000f^\u000b\u0002]A\u0011!cL\u0005\u0003aM\u0011qAQ8pY\u0016\fg.\u0001\u0006jI\u0016tG/\u001b4jKJ,\u0012a\r\t\u0003%QJ!!N\n\u0003\u0007%sG/A\u0006jI\u0016tG/\u001b4jKJ\u0004\u0013\u0001\u0003;p\u0005&t\u0017M]=\u0015\u0007ebd\b\u0005\u0002\u0013u%\u00111h\u0005\u0002\u0005+:LG\u000fC\u0003>\u000f\u0001\u0007\u0011#A\u0001p\u0011\u0015yt\u00011\u0001A\u0003\r\u0011WO\u001a\t\u0003\u0003\u001ak\u0011A\u0011\u0006\u0003\u0007\u0012\u000b1A\\5p\u0015\u0005)\u0015\u0001\u00026bm\u0006L!a\u0012\"\u0003\u0015\tKH/\u001a\"vM\u001a,'/\u0001\u0006ge>l')\u001b8bef$2!\u0005&L\u0011\u0015y\u0004\u00021\u0001A\u0011\u0015a\u0005\u00021\u0001N\u0003!i\u0017M\\5gKN$\bC\u0001(V\u001d\ty5\u000b\u0005\u0002Q'5\t\u0011K\u0003\u0002S?\u00051AH]8pizJ!\u0001V\n\u0002\rA\u0013X\rZ3g\u0013\t1vK\u0001\u0004TiJLgn\u001a\u0006\u0003)N!\"!W0\u0011\u0007IQF,\u0003\u0002\\'\t)\u0011I\u001d:bsB\u0011!#X\u0005\u0003=N\u0011AAQ=uK\")Q(\u0003a\u0001#Q\u0019\u0011#Y2\t\u000b\tT\u0001\u0019A-\u0002\u000b\tLH/Z:\t\u000b1S\u0001\u0019\u00013\u0011\u0007I)w-\u0003\u0002g'\t1q\n\u001d;j_:\u0004$\u0001[7\u0011\u00079K7.\u0003\u0002k/\n)1\t\\1tgB\u0011A.\u001c\u0007\u0001\t%q7-!A\u0001\u0002\u000b\u0005qNA\u0002`IM\n\"\u0001]:\u0011\u0005I\t\u0018B\u0001:\u0014\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0005;\n\u0005U\u001c\"aA!os\"\u0012\u0001a\u001e\t\u0003qnl\u0011!\u001f\u0006\u0003u:\t!\"\u00198o_R\fG/[8o\u0013\ta\u0018PA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class StringSerializer
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
        buf.put(this.toBinary(o));
    }

    @Override
    public Object fromBinary(ByteBuffer buf, String manifest) {
        byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);
        return new String(bytes, "UTF-8");
    }

    @Override
    public byte[] toBinary(Object o) {
        return ((String)o).getBytes("UTF-8");
    }

    @Override
    public Object fromBinary(byte[] bytes, Option<Class<?>> manifest) {
        return new String(bytes, "UTF-8");
    }

    public StringSerializer(ExtendedActorSystem system) {
        this.system = system;
        Serializer.$init$(this);
        this.identifier = BaseSerializer$.MODULE$.identifierFromConfig("primitive-string", system);
    }
}

