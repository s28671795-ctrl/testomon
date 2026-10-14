/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.serialization.BaseSerializer;
import akka.serialization.DisabledJavaSerializer;
import akka.serialization.JavaSerializer$;
import akka.serialization.Serializer;
import akka.util.ClassLoaderObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.Callable;
import scala.Function0;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;
import scala.util.DynamicVariable;

@ScalaSignature(bytes="\u0006\u0005\u0005eq!B\b\u0011\u0011\u0003)b!B\f\u0011\u0011\u0003A\u0002\"B\u0010\u0002\t\u0003\u0001\u0003bB\u0011\u0002\u0005\u0004%\tA\t\u0005\u0007\u001f\u0006\u0001\u000b\u0011B\u0012\u0007\t\u0015\n!A\n\u0005\u0006?\u0015!\ta\r\u0005\u0006i\u0015!\t!\u000e\u0004\u0005/A\u0001\u0001\u000b\u0003\u0005U\u0011\t\u0015\r\u0011\"\u0001V\u0011!1\u0006B!A!\u0002\u0013i\u0003\"B\u0010\t\t\u00039\u0006\"\u0002.\t\t\u0003Y\u0006\"B0\t\t\u0003\u0001\u0007\"B5\t\t\u0003Q\u0017A\u0004&bm\u0006\u001cVM]5bY&TXM\u001d\u0006\u0003#I\tQb]3sS\u0006d\u0017N_1uS>t'\"A\n\u0002\t\u0005\\7.Y\u0002\u0001!\t1\u0012!D\u0001\u0011\u00059Q\u0015M^1TKJL\u0017\r\\5{KJ\u001c\"!A\r\u0011\u0005iiR\"A\u000e\u000b\u0003q\tQa]2bY\u0006L!AH\u000e\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tQ#A\u0007dkJ\u0014XM\u001c;TsN$X-\\\u000b\u0002GA\u0011A%B\u0007\u0002\u0003\ti1)\u001e:sK:$8+_:uK6\u001c\"!B\u0014\u0011\u0007!ZS&D\u0001*\u0015\tQ3$\u0001\u0003vi&d\u0017B\u0001\u0017*\u0005=!\u0015P\\1nS\u000e4\u0016M]5bE2,\u0007C\u0001\u00182\u001b\u0005y#B\u0001\u0019\u0013\u0003\u0015\t7\r^8s\u0013\t\u0011tFA\nFqR,g\u000eZ3e\u0003\u000e$xN]*zgR,W\u000eF\u0001$\u0003%9\u0018\u000e\u001e5WC2,X-\u0006\u00027sQ\u0019qG\u0011#\u0011\u0005aJD\u0002\u0001\u0003\u0006u\u001d\u0011\ra\u000f\u0002\u0002'F\u0011Ah\u0010\t\u00035uJ!AP\u000e\u0003\u000f9{G\u000f[5oOB\u0011!\u0004Q\u0005\u0003\u0003n\u00111!\u00118z\u0011\u0015\u0019u\u00011\u0001.\u0003\u00151\u0018\r\\;f\u0011\u0015)u\u00011\u0001G\u0003!\u0019\u0017\r\u001c7bE2,\u0007cA$No5\t\u0001J\u0003\u0002J\u0015\u0006Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005)Z%\"\u0001'\u0002\t)\fg/Y\u0005\u0003\u001d\"\u0013\u0001bQ1mY\u0006\u0014G.Z\u0001\u000fGV\u0014(/\u001a8u'f\u001cH/Z7!'\rA\u0011$\u0015\t\u0003-IK!a\u0015\t\u0003\u001d\t\u000b7/Z*fe&\fG.\u001b>fe\u000611/_:uK6,\u0012!L\u0001\bgf\u001cH/Z7!)\tA\u0016\f\u0005\u0002\u0017\u0011!)Ak\u0003a\u0001[\u0005y\u0011N\\2mk\u0012,W*\u00198jM\u0016\u001cH/F\u0001]!\tQR,\u0003\u0002_7\t9!i\\8mK\u0006t\u0017\u0001\u0003;p\u0005&t\u0017M]=\u0015\u0005\u0005<\u0007c\u0001\u000ecI&\u00111m\u0007\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u00035\u0015L!AZ\u000e\u0003\t\tKH/\u001a\u0005\u0006Q6\u0001\r!G\u0001\u0002_\u0006QaM]8n\u0005&t\u0017M]=\u0015\u0007eYW\u000eC\u0003m\u001d\u0001\u0007\u0011-A\u0003csR,7\u000fC\u0003o\u001d\u0001\u0007q.A\u0003dY\u0006T(\u0010E\u0002\u001baJL!!]\u000e\u0003\r=\u0003H/[8oa\t\u0019x\u0010E\u0002uwzt!!^=\u0011\u0005Y\\R\"A<\u000b\u0005a$\u0012A\u0002\u001fs_>$h(\u0003\u0002{7\u00051\u0001K]3eK\u001aL!\u0001`?\u0003\u000b\rc\u0017m]:\u000b\u0005i\\\u0002C\u0001\u001d\u0000\t)\t\t!\\A\u0001\u0002\u0003\u0015\ta\u000f\u0002\u0004?\u0012:\u0004&\u0002\b\u0002\u0006\u0005]\u0001#\u0002\u000e\u0002\b\u0005-\u0011bAA\u00057\t1A\u000f\u001b:poN\u0004B!!\u0004\u0002\u00145\u0011\u0011q\u0002\u0006\u0004\u0003#Y\u0015AA5p\u0013\u0011\t)\"a\u0004\u000319{GoU3sS\u0006d\u0017N_1cY\u0016,\u0005pY3qi&|gn\t\u0002\u0002\f\u0001")
public class JavaSerializer
implements BaseSerializer {
    private final ExtendedActorSystem system;
    private int identifier;

    public static CurrentSystem currentSystem() {
        return JavaSerializer$.MODULE$.currentSystem();
    }

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
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        JavaSerializer$.MODULE$.currentSystem().withValue(this.system(), () -> out.writeObject(o));
        out.close();
        return bos.toByteArray();
    }

    @Override
    public Object fromBinary(byte[] bytes, Option<Class<?>> clazz) throws NotSerializableException {
        ClassLoaderObjectInputStream in = new ClassLoaderObjectInputStream(this.system().dynamicAccess().classLoader(), new ByteArrayInputStream(bytes));
        Object obj = JavaSerializer$.MODULE$.currentSystem().withValue(this.system(), (Function0<Object> & Serializable)() -> in.readObject());
        in.close();
        return obj;
    }

    public JavaSerializer(ExtendedActorSystem system) {
        this.system = system;
        Serializer.$init$(this);
        BaseSerializer.$init$(this);
        if (!system.settings().AllowJavaSerialization()) {
            throw new DisabledJavaSerializer.JavaSerializationException("Attempted creation of `JavaSerializer` while `akka.actor.allow-java-serialization = off` was set!");
        }
        Statics.releaseFence();
    }

    public static final class CurrentSystem
    extends DynamicVariable<ExtendedActorSystem> {
        @Override
        public <S> S withValue(ExtendedActorSystem value, Callable<S> callable) {
            return super.withValue(value, (Function0<Object> & Serializable)() -> callable.call());
        }

        public CurrentSystem() {
            super(null);
        }
    }
}

