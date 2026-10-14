/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.serialization.BaseSerializer;
import akka.serialization.ByteBufferSerializer;
import akka.serialization.Serializer;
import java.io.NotSerializableException;
import java.nio.ByteBuffer;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005m4A!\u0003\u0006\u0001\u001f!AQ\u0004\u0001BC\u0002\u0013\u0005a\u0004\u0003\u0005&\u0001\t\u0005\t\u0015!\u0003 \u0011\u00151\u0003\u0001\"\u0001(\u0011\u0015Q\u0003\u0001\"\u0001,\u0011\u0015y\u0003\u0001\"\u00011\u0011\u0015I\u0004\u0001\"\u0001;\u0011\u0015y\u0003\u0001\"\u0011g\u0011\u0015I\u0004\u0001\"\u0011t\u0005M\u0011\u0015\u0010^3BeJ\f\u0017pU3sS\u0006d\u0017N_3s\u0015\tYA\"A\u0007tKJL\u0017\r\\5{CRLwN\u001c\u0006\u0002\u001b\u0005!\u0011m[6b\u0007\u0001\u0019B\u0001\u0001\t\u00175A\u0011\u0011\u0003F\u0007\u0002%)\t1#A\u0003tG\u0006d\u0017-\u0003\u0002\u0016%\t1\u0011I\\=SK\u001a\u0004\"a\u0006\r\u000e\u0003)I!!\u0007\u0006\u0003\u001d\t\u000b7/Z*fe&\fG.\u001b>feB\u0011qcG\u0005\u00039)\u0011ACQ=uK\n+hMZ3s'\u0016\u0014\u0018.\u00197ju\u0016\u0014\u0018AB:zgR,W.F\u0001 !\t\u00013%D\u0001\"\u0015\t\u0011C\"A\u0003bGR|'/\u0003\u0002%C\t\u0019R\t\u001f;f]\u0012,G-Q2u_J\u001c\u0016p\u001d;f[\u000691/_:uK6\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002)SA\u0011q\u0003\u0001\u0005\u0006;\r\u0001\raH\u0001\u0010S:\u001cG.\u001e3f\u001b\u0006t\u0017NZ3tiV\tA\u0006\u0005\u0002\u0012[%\u0011aF\u0005\u0002\b\u0005>|G.Z1o\u0003!!xNQ5oCJLHCA\u00198!\r\t\"\u0007N\u0005\u0003gI\u0011Q!\u0011:sCf\u0004\"!E\u001b\n\u0005Y\u0012\"\u0001\u0002\"zi\u0016DQ\u0001O\u0003A\u0002A\t\u0011a\\\u0001\u000bMJ|WNQ5oCJLHc\u0001\t<{!)AH\u0002a\u0001c\u0005)!-\u001f;fg\")aH\u0002a\u0001\u007f\u0005)1\r\\1{uB\u0019\u0011\u0003\u0011\"\n\u0005\u0005\u0013\"AB(qi&|g\u000e\r\u0002D!B\u0019Ai\u0013(\u000f\u0005\u0015K\u0005C\u0001$\u0013\u001b\u00059%B\u0001%\u000f\u0003\u0019a$o\\8u}%\u0011!JE\u0001\u0007!J,G-\u001a4\n\u00051k%!B\"mCN\u001c(B\u0001&\u0013!\ty\u0005\u000b\u0004\u0001\u0005\u0013Ek\u0014\u0011!A\u0001\u0006\u0003\u0011&\u0001B0%cA\n\"a\u0015,\u0011\u0005E!\u0016BA+\u0013\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!E,\n\u0005a\u0013\"aA!os\"\u001aaAW3\u0011\u0007EYV,\u0003\u0002]%\t1A\u000f\u001b:poN\u0004\"AX2\u000e\u0003}S!\u0001Y1\u0002\u0005%|'\"\u00012\u0002\t)\fg/Y\u0005\u0003I~\u0013\u0001DT8u'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z#yG\u0016\u0004H/[8oG\u0005iFcA4kWB\u0011\u0011\u0003[\u0005\u0003SJ\u0011A!\u00168ji\")\u0001h\u0002a\u0001!!)An\u0002a\u0001[\u0006\u0019!-\u001e4\u0011\u00059\fX\"A8\u000b\u0005A\f\u0017a\u00018j_&\u0011!o\u001c\u0002\u000b\u0005f$XMQ;gM\u0016\u0014Hc\u0001\tuk\")A\u000e\u0003a\u0001[\")a\u000f\u0003a\u0001o\u0006AQ.\u00198jM\u0016\u001cH\u000f\u0005\u0002Eq&\u0011\u00110\u0014\u0002\u0007'R\u0014\u0018N\\4)\u0007!QV\r")
public class ByteArraySerializer
implements BaseSerializer,
ByteBufferSerializer {
    private final ExtendedActorSystem system;
    private int identifier;

    @Override
    @InternalApi
    public int identifierFromConfig() {
        return BaseSerializer.identifierFromConfig$(this);
    }

    @Override
    public final Object fromBinary(byte[] bytes) {
        return Serializer.fromBinary$(this, bytes);
    }

    @Override
    public final Object fromBinary(byte[] bytes, Class<?> clazz) throws NotSerializableException {
        return Serializer.fromBinary$(this, bytes, clazz);
    }

    @Override
    public int identifier() {
        return this.identifier;
    }

    @Override
    public void akka$serialization$BaseSerializer$_setter_$identifier_$eq(int x$1) {
        this.identifier = x$1;
    }

    @Override
    public ExtendedActorSystem system() {
        return this.system;
    }

    @Override
    public boolean includeManifest() {
        return false;
    }

    @Override
    public byte[] toBinary(Object o) {
        Object object = o;
        if (object == null) {
            return null;
        }
        if (object instanceof byte[]) {
            byte[] byArray = (byte[])object;
            return byArray;
        }
        throw new IllegalArgumentException(new StringBuilder(36).append(this.getClass().getName()).append(" only serializes byte arrays, not [").append(object.getClass().getName()).append("]").toString());
    }

    @Override
    public Object fromBinary(byte[] bytes, Option<Class<?>> clazz) throws NotSerializableException {
        return bytes;
    }

    @Override
    public void toBinary(Object o, ByteBuffer buf) {
        Object object = o;
        if (object == null) {
            return;
        }
        if (object instanceof byte[]) {
            byte[] byArray = (byte[])object;
            buf.put(byArray);
            return;
        }
        throw new IllegalArgumentException(new StringBuilder(36).append(this.getClass().getName()).append(" only serializes byte arrays, not [").append(object.getClass().getName()).append("]").toString());
    }

    @Override
    public Object fromBinary(ByteBuffer buf, String manifest) throws NotSerializableException {
        byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);
        return bytes;
    }

    public ByteArraySerializer(ExtendedActorSystem system) {
        this.system = system;
        Serializer.$init$(this);
        BaseSerializer.$init$(this);
        Statics.releaseFence();
    }
}

