/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ContextualTypedActorFactory;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.actor.TypedActorExtension;
import akka.actor.TypedActorFactory;
import scala.concurrent.ExecutionContextExecutor;

public final class TypedActor$
implements ExtensionId<TypedActorExtension>,
ExtensionIdProvider {
    public static final TypedActor$ MODULE$ = new TypedActor$();
    private static final ThreadLocal<Object> akka$actor$TypedActor$$selfReference;
    private static final ThreadLocal<ActorContext> akka$actor$TypedActor$$currentContext;

    static {
        ExtensionId.$init$(MODULE$);
        akka$actor$TypedActor$$selfReference = new ThreadLocal();
        akka$actor$TypedActor$$currentContext = new ThreadLocal();
    }

    @Override
    public Extension apply(ActorSystem system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public Extension apply(ClassicActorSystemProvider system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public final int hashCode() {
        return ExtensionId.hashCode$(this);
    }

    @Override
    public final boolean equals(Object other) {
        return ExtensionId.equals$(this, other);
    }

    @Override
    public TypedActorExtension get(ActorSystem system) {
        return (TypedActorExtension)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public TypedActorExtension get(ClassicActorSystemProvider system) {
        return (TypedActorExtension)ExtensionId.get$((ExtensionId)this, system);
    }

    public TypedActor$ lookup() {
        return this;
    }

    @Override
    public TypedActorExtension createExtension(ExtendedActorSystem system) {
        return new TypedActorExtension(system);
    }

    public TypedActorFactory apply(ActorContext context) {
        return new ContextualTypedActorFactory((TypedActorExtension)this.apply(context.system()), context);
    }

    public TypedActorFactory get(ActorContext context) {
        return this.apply(context);
    }

    public ThreadLocal<Object> akka$actor$TypedActor$$selfReference() {
        return akka$actor$TypedActor$$selfReference;
    }

    public ThreadLocal<ActorContext> akka$actor$TypedActor$$currentContext() {
        return akka$actor$TypedActor$$currentContext;
    }

    public <T> T self() {
        Object object = this.akka$actor$TypedActor$$selfReference().get();
        if (object == null) {
            throw new IllegalStateException("Calling TypedActor.self outside of a TypedActor implementation method!");
        }
        return (T)object;
    }

    public ActorContext context() {
        ActorContext actorContext = this.akka$actor$TypedActor$$currentContext().get();
        if (actorContext == null) {
            throw new IllegalStateException("Calling TypedActor.context outside of a TypedActor implementation method!");
        }
        return actorContext;
    }

    public ExecutionContextExecutor dispatcher() {
        return this.context().dispatcher();
    }

    private TypedActor$() {
    }
}

