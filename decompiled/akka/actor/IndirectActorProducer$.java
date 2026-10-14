/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.ArgsReflectConstructor;
import akka.actor.CreatorConsumer;
import akka.actor.CreatorFunctionConsumer;
import akka.actor.IndirectActorProducer;
import akka.actor.NoArgsReflectConstructor;
import akka.actor.TypedCreatorFunctionConsumer;
import akka.japi.Creator;
import akka.util.Reflect$;
import scala.Function0;
import scala.collection.IterableOps;
import scala.collection.immutable.Seq;

public final class IndirectActorProducer$ {
    public static final IndirectActorProducer$ MODULE$ = new IndirectActorProducer$();
    private static final Class<CreatorFunctionConsumer> CreatorFunctionConsumerClass = CreatorFunctionConsumer.class;
    private static final Class<CreatorConsumer> CreatorConsumerClass = CreatorConsumer.class;
    private static final Class<TypedCreatorFunctionConsumer> TypedCreatorFunctionConsumerClass = TypedCreatorFunctionConsumer.class;

    public Class<CreatorFunctionConsumer> CreatorFunctionConsumerClass() {
        return CreatorFunctionConsumerClass;
    }

    public Class<CreatorConsumer> CreatorConsumerClass() {
        return CreatorConsumerClass;
    }

    public Class<TypedCreatorFunctionConsumer> TypedCreatorFunctionConsumerClass() {
        return TypedCreatorFunctionConsumerClass;
    }

    public IndirectActorProducer apply(Class<?> clazz, Seq<Object> args2) {
        if (IndirectActorProducer.class.isAssignableFrom(clazz)) {
            Class<?> clazz2 = clazz;
            Class<TypedCreatorFunctionConsumer> clazz3 = this.TypedCreatorFunctionConsumerClass();
            Class<?> clazz4 = clazz2;
            if (!(clazz3 != null ? !clazz3.equals(clazz4) : clazz4 != null)) {
                return new TypedCreatorFunctionConsumer((Class)IndirectActorProducer$.get1stArg$1(args2), (Function0)IndirectActorProducer$.get2ndArg$1(args2));
            }
            Class<CreatorFunctionConsumer> clazz5 = this.CreatorFunctionConsumerClass();
            Class<?> clazz6 = clazz2;
            if (!(clazz5 != null ? !clazz5.equals(clazz6) : clazz6 != null)) {
                return new CreatorFunctionConsumer((Function0)IndirectActorProducer$.get1stArg$1(args2));
            }
            Class<CreatorConsumer> clazz7 = this.CreatorConsumerClass();
            Class<?> clazz8 = clazz2;
            if (!(clazz7 != null ? !clazz7.equals(clazz8) : clazz8 != null)) {
                return new CreatorConsumer((Class)IndirectActorProducer$.get1stArg$1(args2), (Creator)IndirectActorProducer$.get2ndArg$1(args2));
            }
            return (IndirectActorProducer)Reflect$.MODULE$.instantiate(clazz, args2);
        }
        if (Actor.class.isAssignableFrom(clazz)) {
            if (args2.isEmpty()) {
                return new NoArgsReflectConstructor(clazz);
            }
            return new ArgsReflectConstructor(clazz, args2);
        }
        throw new IllegalArgumentException(new StringBuilder(24).append("unknown actor creator [").append(clazz).append("]").toString());
    }

    private static final Object get1stArg$1(Seq args$1) {
        return args$1.head();
    }

    private static final Object get2ndArg$1(Seq args$1) {
        return ((IterableOps)args$1.tail()).head();
    }

    private IndirectActorProducer$() {
    }
}

