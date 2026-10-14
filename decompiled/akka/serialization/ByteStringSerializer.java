/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.serialization.BaseSerializer$;
import akka.serialization.ByteBufferSerializer;
import akka.serialization.Serializer;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.NotSerializableException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u4Qa\u0003\u0007\u0003\u001dAA\u0001B\b\u0001\u0003\u0006\u0004%\t\u0001\t\u0005\tO\u0001\u0011\t\u0011)A\u0005C!)\u0001\u0006\u0001C\u0001S!)A\u0006\u0001C![!9\u0011\u0007\u0001b\u0001\n\u0003\u0012\u0004B\u0002\u001c\u0001A\u0003%1\u0007C\u00038\u0001\u0011\u0005\u0003\bC\u0003I\u0001\u0011\u0005\u0013\nC\u00038\u0001\u0011\u0005\u0003\fC\u0003I\u0001\u0011\u0005\u0003M\u0001\u000bCsR,7\u000b\u001e:j]\u001e\u001cVM]5bY&TXM\u001d\u0006\u0003\u001b9\tQb]3sS\u0006d\u0017N_1uS>t'\"A\b\u0002\t\u0005\\7.Y\n\u0005\u0001E92\u0004\u0005\u0002\u0013+5\t1CC\u0001\u0015\u0003\u0015\u00198-\u00197b\u0013\t12C\u0001\u0004B]f\u0014VM\u001a\t\u00031ei\u0011\u0001D\u0005\u000351\u0011!bU3sS\u0006d\u0017N_3s!\tAB$\u0003\u0002\u001e\u0019\t!\")\u001f;f\u0005V4g-\u001a:TKJL\u0017\r\\5{KJ\faa]=ti\u0016l7\u0001A\u000b\u0002CA\u0011!%J\u0007\u0002G)\u0011AED\u0001\u0006C\u000e$xN]\u0005\u0003M\r\u00121#\u0012=uK:$W\rZ!di>\u00148+_:uK6\fqa]=ti\u0016l\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003U-\u0002\"\u0001\u0007\u0001\t\u000by\u0019\u0001\u0019A\u0011\u0002\u001f%t7\r\\;eK6\u000bg.\u001b4fgR,\u0012A\f\t\u0003%=J!\u0001M\n\u0003\u000f\t{w\u000e\\3b]\u0006Q\u0011\u000eZ3oi&4\u0017.\u001a:\u0016\u0003M\u0002\"A\u0005\u001b\n\u0005U\u001a\"aA%oi\u0006Y\u0011\u000eZ3oi&4\u0017.\u001a:!\u0003!!xNQ5oCJLHcA\u001d=}A\u0011!CO\u0005\u0003wM\u0011A!\u00168ji\")Qh\u0002a\u0001#\u0005\tq\u000eC\u0003@\u000f\u0001\u0007\u0001)A\u0002ck\u001a\u0004\"!\u0011$\u000e\u0003\tS!a\u0011#\u0002\u00079LwNC\u0001F\u0003\u0011Q\u0017M^1\n\u0005\u001d\u0013%A\u0003\"zi\u0016\u0014UO\u001a4fe\u0006QaM]8n\u0005&t\u0017M]=\u0015\u0007EQ5\nC\u0003@\u0011\u0001\u0007\u0001\tC\u0003M\u0011\u0001\u0007Q*\u0001\u0005nC:Lg-Z:u!\tqUK\u0004\u0002P'B\u0011\u0001kE\u0007\u0002#*\u0011!kH\u0001\u0007yI|w\u000e\u001e \n\u0005Q\u001b\u0012A\u0002)sK\u0012,g-\u0003\u0002W/\n11\u000b\u001e:j]\u001eT!\u0001V\n\u0015\u0005e{\u0006c\u0001\n[9&\u00111l\u0005\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0003%uK!AX\n\u0003\t\tKH/\u001a\u0005\u0006{%\u0001\r!\u0005\u000b\u0004#\u0005\u001c\u0007\"\u00022\u000b\u0001\u0004I\u0016!\u00022zi\u0016\u001c\b\"\u0002'\u000b\u0001\u0004!\u0007c\u0001\nfO&\u0011am\u0005\u0002\u0007\u001fB$\u0018n\u001c81\u0005!l\u0007c\u0001(jW&\u0011!n\u0016\u0002\u0006\u00072\f7o\u001d\t\u0003Y6d\u0001\u0001B\u0005oG\u0006\u0005\t\u0011!B\u0001_\n\u0019q\f\n\u001b\u0012\u0005A\u001c\bC\u0001\nr\u0013\t\u00118CA\u0004O_RD\u0017N\\4\u0011\u0005I!\u0018BA;\u0014\u0005\r\te.\u001f\u0015\u0003\u0001]\u0004\"\u0001_>\u000e\u0003eT!A\u001f\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002}s\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class ByteStringSerializer
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
        ByteString bs = (ByteString)o;
        if (bs.copyToBuffer(buf) < bs.length()) {
            throw new BufferOverflowException();
        }
    }

    @Override
    public Object fromBinary(ByteBuffer buf, String manifest) {
        return ByteString$.MODULE$.fromByteBuffer(buf);
    }

    @Override
    public byte[] toBinary(Object o) {
        ByteString bs = (ByteString)o;
        byte[] result = new byte[bs.length()];
        bs.copyToArray(result, 0, bs.length());
        return result;
    }

    @Override
    public Object fromBinary(byte[] bytes, Option<Class<?>> manifest) {
        return ByteString$.MODULE$.apply(bytes);
    }

    public ByteStringSerializer(ExtendedActorSystem system) {
        this.system = system;
        Serializer.$init$(this);
        this.identifier = BaseSerializer$.MODULE$.identifierFromConfig("primitive-bytestring", system);
    }
}

