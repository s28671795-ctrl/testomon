/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t2QAA\u0002\u0002\u0002!AQa\b\u0001\u0005\u0002\u0001\u00121#\u00112tiJ\f7\r^#yi\u0016t7/[8o\u0013\u0012T!\u0001B\u0003\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003\u0019\tA!Y6lC\u000e\u0001QCA\u0005\u0017'\r\u0001!\u0002\u0005\t\u0003\u00179i\u0011\u0001\u0004\u0006\u0002\u001b\u0005)1oY1mC&\u0011q\u0002\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0007E\u0011B#D\u0001\u0004\u0013\t\u00192AA\u0006FqR,gn]5p]&#\u0007CA\u000b\u0017\u0019\u0001!Qa\u0006\u0001C\u0002a\u0011\u0011\u0001V\t\u00033q\u0001\"a\u0003\u000e\n\u0005ma!a\u0002(pi\"Lgn\u001a\t\u0003#uI!AH\u0002\u0003\u0013\u0015CH/\u001a8tS>t\u0017A\u0002\u001fj]&$h\bF\u0001\"!\r\t\u0002\u0001\u0006")
public abstract class AbstractExtensionId<T extends Extension>
implements ExtensionId<T> {
    @Override
    public T apply(ActorSystem system) {
        return (T)ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public T apply(ClassicActorSystemProvider system) {
        return (T)ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public T get(ActorSystem system) {
        return (T)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public T get(ClassicActorSystemProvider system) {
        return (T)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public final int hashCode() {
        return ExtensionId.hashCode$(this);
    }

    @Override
    public final boolean equals(Object other) {
        return ExtensionId.equals$(this, other);
    }

    public AbstractExtensionId() {
        ExtensionId.$init$(this);
    }
}

