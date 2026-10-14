/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import java.util.Objects;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000593q!\u0003\u0006\u0011\u0002\u0007\u0005q\u0002C\u0003\u0018\u0001\u0011\u0005\u0001\u0004C\u0003\u001d\u0001\u0011\u0005Q\u0004C\u0003\u001d\u0001\u0011\u0005q\u0006C\u00035\u0001\u0011\u0005Q\u0007C\u00035\u0001\u0011\u0005q\u0007C\u0003:\u0001\u0019\u0005!\bC\u0003@\u0001\u0011\u0015\u0003\tC\u0003E\u0001\u0011\u0015SIA\u0006FqR,gn]5p]&#'BA\u0006\r\u0003\u0015\t7\r^8s\u0015\u0005i\u0011\u0001B1lW\u0006\u001c\u0001!\u0006\u0002\u0011AM\u0011\u0001!\u0005\t\u0003%Ui\u0011a\u0005\u0006\u0002)\u0005)1oY1mC&\u0011ac\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005I\u0002C\u0001\n\u001b\u0013\tY2C\u0001\u0003V]&$\u0018!B1qa2LHC\u0001\u0010+!\ty\u0002\u0005\u0004\u0001\u0005\u000b\u0005\u0002!\u0019\u0001\u0012\u0003\u0003Q\u000b\"a\t\u0014\u0011\u0005I!\u0013BA\u0013\u0014\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\n\u0015\u000e\u0003)I!!\u000b\u0006\u0003\u0013\u0015CH/\u001a8tS>t\u0007\"B\u0016\u0003\u0001\u0004a\u0013AB:zgR,W\u000e\u0005\u0002([%\u0011aF\u0003\u0002\f\u0003\u000e$xN]*zgR,W\u000e\u0006\u0002\u001fa!)1f\u0001a\u0001cA\u0011qEM\u0005\u0003g)\u0011!d\u00117bgNL7-Q2u_J\u001c\u0016p\u001d;f[B\u0013xN^5eKJ\f1aZ3u)\tqb\u0007C\u0003,\t\u0001\u0007A\u0006\u0006\u0002\u001fq!)1&\u0002a\u0001c\u0005y1M]3bi\u0016,\u0005\u0010^3og&|g\u000e\u0006\u0002\u001fw!)1F\u0002a\u0001yA\u0011q%P\u0005\u0003})\u00111#\u0012=uK:$W\rZ!di>\u00148+_:uK6\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0003B\u0011!CQ\u0005\u0003\u0007N\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011a)\u0013\t\u0003%\u001dK!\u0001S\n\u0003\u000f\t{w\u000e\\3b]\")!\n\u0003a\u0001\u0017\u0006)q\u000e\u001e5feB\u0011!\u0003T\u0005\u0003\u001bN\u00111!\u00118z\u0001")
public interface ExtensionId<T extends Extension> {
    public static /* synthetic */ Extension apply$(ExtensionId $this, ActorSystem system) {
        return $this.apply(system);
    }

    default public T apply(ActorSystem system) {
        return Objects.requireNonNull(system, "system must not be null!").registerExtension(this);
    }

    public static /* synthetic */ Extension apply$(ExtensionId $this, ClassicActorSystemProvider system) {
        return $this.apply(system);
    }

    default public T apply(ClassicActorSystemProvider system) {
        return this.apply(system.classicSystem());
    }

    public static /* synthetic */ Extension get$(ExtensionId $this, ActorSystem system) {
        return $this.get(system);
    }

    default public T get(ActorSystem system) {
        return this.apply(system);
    }

    public static /* synthetic */ Extension get$(ExtensionId $this, ClassicActorSystemProvider system) {
        return $this.get(system);
    }

    default public T get(ClassicActorSystemProvider system) {
        return this.apply(system);
    }

    public T createExtension(ExtendedActorSystem var1);

    public static /* synthetic */ int hashCode$(ExtensionId $this) {
        return $this.hashCode();
    }

    default public int hashCode() {
        return System.identityHashCode(this);
    }

    public static /* synthetic */ boolean equals$(ExtensionId $this, Object other) {
        return $this.equals(other);
    }

    default public boolean equals(Object other) {
        return this == other;
    }

    public static void $init$(ExtensionId $this) {
    }
}

