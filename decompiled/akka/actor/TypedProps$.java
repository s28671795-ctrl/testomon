/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Deploy;
import akka.actor.Props$;
import akka.actor.TypedProps;
import akka.japi.Util$;
import akka.util.Reflect$;
import akka.util.Timeout;
import java.io.Serializable;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple6;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag;
import scala.runtime.ModuleSerializationProxy;

public final class TypedProps$
implements Serializable {
    public static final TypedProps$ MODULE$ = new TypedProps$();
    private static final String defaultDispatcherId = "akka.actor.default-dispatcher";
    private static final Option<Timeout> defaultTimeout = None$.MODULE$;
    private static final Option<ClassLoader> defaultLoader = None$.MODULE$;

    public <T> String $lessinit$greater$default$3() {
        return this.defaultDispatcherId();
    }

    public <T> Deploy $lessinit$greater$default$4() {
        return Props$.MODULE$.defaultDeploy();
    }

    public <T> Option<Timeout> $lessinit$greater$default$5() {
        return this.defaultTimeout();
    }

    public <T> Option<ClassLoader> $lessinit$greater$default$6() {
        return this.defaultLoader();
    }

    public String defaultDispatcherId() {
        return defaultDispatcherId;
    }

    public Option<Timeout> defaultTimeout() {
        return defaultTimeout;
    }

    public Option<ClassLoader> defaultLoader() {
        return defaultLoader;
    }

    public Seq<Class<?>> extractInterfaces(Class<?> clazz) {
        if (clazz.isInterface()) {
            return Util$.MODULE$.immutableSingletonSeq(clazz);
        }
        return Util$.MODULE$.immutableSeq(clazz.getInterfaces());
    }

    public <T> TypedProps<T> apply(Class<T> implementation) {
        return new TypedProps<T>(implementation);
    }

    public <T> TypedProps<T> apply(Class<? super T> clazz, Class<T> implementation) {
        return new TypedProps<T>(this.extractInterfaces(clazz), Reflect$.MODULE$.instantiator(implementation), this.$lessinit$greater$default$3(), this.$lessinit$greater$default$4(), this.$lessinit$greater$default$5(), this.$lessinit$greater$default$6());
    }

    public <T> TypedProps<T> apply(Class<? super T> clazz, Function0<T> creator) {
        return new TypedProps<T>(this.extractInterfaces(clazz), creator, this.$lessinit$greater$default$3(), this.$lessinit$greater$default$4(), this.$lessinit$greater$default$5(), this.$lessinit$greater$default$6());
    }

    public <T> TypedProps<T> apply(ClassTag<T> evidence$1) {
        return new TypedProps(Predef$.MODULE$.implicitly(evidence$1).runtimeClass());
    }

    public <T> TypedProps<T> apply(Seq<Class<?>> interfaces, Function0<T> creator) {
        return new TypedProps<T>(interfaces, creator, this.$lessinit$greater$default$3(), this.$lessinit$greater$default$4(), this.$lessinit$greater$default$5(), this.$lessinit$greater$default$6());
    }

    public <T> String apply$default$3() {
        return this.defaultDispatcherId();
    }

    public <T> Deploy apply$default$4() {
        return Props$.MODULE$.defaultDeploy();
    }

    public <T> Option<Timeout> apply$default$5() {
        return this.defaultTimeout();
    }

    public <T> Option<ClassLoader> apply$default$6() {
        return this.defaultLoader();
    }

    public <T> TypedProps<T> apply(Seq<Class<?>> interfaces, Function0<T> creator, String dispatcher, Deploy deploy, Option<Timeout> timeout2, Option<ClassLoader> loader) {
        return new TypedProps<T>(interfaces, creator, dispatcher, deploy, timeout2, loader);
    }

    public <T> Option<Tuple6<Seq<Class<?>>, Function0<T>, String, Deploy, Option<Timeout>, Option<ClassLoader>>> unapply(TypedProps<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some(new Tuple6(x$0.interfaces(), x$0.creator(), x$0.dispatcher(), x$0.deploy(), x$0.timeout(), x$0.loader()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TypedProps$.class);
    }

    private TypedProps$() {
    }
}

