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

@ScalaSignature(bytes="\u0006\u0005\u00055a!B\b\u0011\u0005I!\u0002\u0002\u0003\u0012\u0001\u0005\u000b\u0007I\u0011\u0001\u0013\t\u0011-\u0002!\u0011!Q\u0001\n\u0015BQ\u0001\f\u0001\u0005\u00025Bq\u0001\r\u0001C\u0002\u0013%\u0011\u0007\u0003\u00046\u0001\u0001\u0006IA\r\u0005\bm\u0001\u0011\r\u0011\"\u00032\u0011\u00199\u0004\u0001)A\u0005e!)\u0001\b\u0001C!s!9Q\b\u0001b\u0001\n\u0003r\u0004B\u0002\"\u0001A\u0003%q\bC\u0003D\u0001\u0011\u0005C\tC\u0003U\u0001\u0011\u0005S\u000bC\u0003D\u0001\u0011\u0005C\rC\u0003U\u0001\u0011\u0005\u0013NA\tC_>dW-\u00198TKJL\u0017\r\\5{KJT!!\u0005\n\u0002\u001bM,'/[1mSj\fG/[8o\u0015\u0005\u0019\u0012\u0001B1lW\u0006\u001cB\u0001A\u000b\u001c?A\u0011a#G\u0007\u0002/)\t\u0001$A\u0003tG\u0006d\u0017-\u0003\u0002\u001b/\t1\u0011I\\=SK\u001a\u0004\"\u0001H\u000f\u000e\u0003AI!A\b\t\u0003\u0015M+'/[1mSj,'\u000f\u0005\u0002\u001dA%\u0011\u0011\u0005\u0005\u0002\u0015\u0005f$XMQ;gM\u0016\u00148+\u001a:jC2L'0\u001a:\u0002\rML8\u000f^3n\u0007\u0001)\u0012!\n\t\u0003M%j\u0011a\n\u0006\u0003QI\tQ!Y2u_JL!AK\u0014\u0003'\u0015CH/\u001a8eK\u0012\f5\r^8s'f\u001cH/Z7\u0002\u000fML8\u000f^3nA\u00051A(\u001b8jiz\"\"AL\u0018\u0011\u0005q\u0001\u0001\"\u0002\u0012\u0004\u0001\u0004)\u0013A\u0002$bYN,')F\u00013!\t12'\u0003\u00025/\t!!)\u001f;f\u0003\u001d1\u0015\r\\:f\u0005\u0002\nQ\u0001\u0016:vK\n\u000ba\u0001\u0016:vK\n\u0003\u0013aD5oG2,H-Z'b]&4Wm\u001d;\u0016\u0003i\u0002\"AF\u001e\n\u0005q:\"a\u0002\"p_2,\u0017M\\\u0001\u000bS\u0012,g\u000e^5gS\u0016\u0014X#A \u0011\u0005Y\u0001\u0015BA!\u0018\u0005\rIe\u000e^\u0001\fS\u0012,g\u000e^5gS\u0016\u0014\b%\u0001\u0005u_\nKg.\u0019:z)\r)\u0005J\u0013\t\u0003-\u0019K!aR\f\u0003\tUs\u0017\u000e\u001e\u0005\u0006\u0013.\u0001\r!F\u0001\u0002_\")1j\u0003a\u0001\u0019\u0006\u0019!-\u001e4\u0011\u00055\u0013V\"\u0001(\u000b\u0005=\u0003\u0016a\u00018j_*\t\u0011+\u0001\u0003kCZ\f\u0017BA*O\u0005)\u0011\u0015\u0010^3Ck\u001a4WM]\u0001\u000bMJ|WNQ5oCJLHcA\u000bW/\")1\n\u0004a\u0001\u0019\")\u0001\f\u0004a\u00013\u0006AQ.\u00198jM\u0016\u001cH\u000f\u0005\u0002[C:\u00111l\u0018\t\u00039^i\u0011!\u0018\u0006\u0003=\u000e\na\u0001\u0010:p_Rt\u0014B\u00011\u0018\u0003\u0019\u0001&/\u001a3fM&\u0011!m\u0019\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u0001<BCA3i!\r1bMM\u0005\u0003O^\u0011Q!\u0011:sCfDQ!S\u0007A\u0002U!2!\u00066m\u0011\u0015Yg\u00021\u0001f\u0003\u0015\u0011\u0017\u0010^3t\u0011\u0015Af\u00021\u0001n!\r1b\u000e]\u0005\u0003_^\u0011aa\u00149uS>t\u0007GA9w!\rQ&\u000f^\u0005\u0003g\u000e\u0014Qa\u00117bgN\u0004\"!\u001e<\r\u0001\u0011Iq\u000f\\A\u0001\u0002\u0003\u0015\t\u0001\u001f\u0002\u0004?\u0012*\u0014CA=}!\t1\"0\u0003\u0002|/\t9aj\u001c;iS:<\u0007C\u0001\f~\u0013\tqxCA\u0002B]fD3\u0001AA\u0001!\u0011\t\u0019!!\u0003\u000e\u0005\u0005\u0015!bAA\u0004%\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005-\u0011Q\u0001\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public final class BooleanSerializer
implements Serializer,
ByteBufferSerializer {
    private final ExtendedActorSystem system;
    private final byte FalseB;
    private final byte TrueB;
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

    private byte FalseB() {
        return this.FalseB;
    }

    private byte TrueB() {
        return this.TrueB;
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
        byte by;
        Object object = o;
        if (BoxesRunTime.equals(Boolean.TRUE, object)) {
            by = this.TrueB();
        } else if (BoxesRunTime.equals(Boolean.FALSE, object)) {
            by = this.FalseB();
        } else {
            throw new IllegalArgumentException(new StringBuilder(18).append("Non boolean flag: ").append(object).toString());
        }
        byte flag = by;
        buf.put(flag);
    }

    @Override
    public Object fromBinary(ByteBuffer buf, String manifest) {
        byte by = buf.get();
        if (this.TrueB() == by) {
            return Boolean.TRUE;
        }
        if (this.FalseB() == by) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException(new StringBuilder(23).append("Non boolean flag byte: ").append(by).toString());
    }

    @Override
    public byte[] toBinary(Object o) {
        byte by;
        Object object = o;
        if (BoxesRunTime.equals(Boolean.TRUE, object)) {
            by = this.TrueB();
        } else if (BoxesRunTime.equals(Boolean.FALSE, object)) {
            by = this.FalseB();
        } else {
            throw new IllegalArgumentException(new StringBuilder(18).append("Non boolean flag: ").append(object).toString());
        }
        byte flag = by;
        byte[] result = new byte[]{flag};
        return result;
    }

    @Override
    public Object fromBinary(byte[] bytes, Option<Class<?>> manifest) {
        byte by = bytes[0];
        if (this.TrueB() == by) {
            return Boolean.TRUE;
        }
        if (this.FalseB() == by) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException(new StringBuilder(23).append("Non boolean flag byte: ").append(by).toString());
    }

    public BooleanSerializer(ExtendedActorSystem system) {
        this.system = system;
        Serializer.$init$(this);
        this.FalseB = (byte)0;
        this.TrueB = (byte)1;
        this.identifier = BaseSerializer$.MODULE$.identifierFromConfig("primitive-boolean", system);
    }
}

