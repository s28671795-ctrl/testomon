/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractProps;
import akka.actor.Actor;
import akka.actor.CreatorFunctionConsumer;
import akka.actor.Deploy;
import akka.actor.Deploy$;
import akka.actor.Props;
import akka.actor.TypedCreatorFunctionConsumer;
import akka.japi.Creator;
import akka.routing.NoRouter$;
import akka.routing.RouterConfig;
import java.io.Serializable;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple3;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class Props$
implements AbstractProps,
Serializable {
    public static final Props$ MODULE$ = new Props$();
    private static final Function0<Actor> defaultCreator;
    private static final RouterConfig defaultRoutedProps;
    private static final Deploy defaultDeploy;
    private static final Props empty;
    private static final Props default;

    static {
        AbstractProps.$init$(MODULE$);
        defaultCreator = (Function0<Nothing$> & Serializable)() -> {
            throw new UnsupportedOperationException("No actor creator specified!");
        };
        defaultRoutedProps = NoRouter$.MODULE$;
        defaultDeploy = Deploy$.MODULE$.apply(Deploy$.MODULE$.apply$default$1(), Deploy$.MODULE$.apply$default$2(), Deploy$.MODULE$.apply$default$3(), Deploy$.MODULE$.apply$default$4(), Deploy$.MODULE$.apply$default$5(), Deploy$.MODULE$.apply$default$6());
        empty = MODULE$.apply(ClassTag$.MODULE$.apply(Props.EmptyActor.class));
        default = new Props(MODULE$.defaultDeploy(), CreatorFunctionConsumer.class, new $colon$colon<Object>(MODULE$.defaultCreator(), Nil$.MODULE$));
    }

    @Override
    public void validate(Class<?> clazz) {
        AbstractProps.validate$(this, clazz);
    }

    @Override
    public Props create(Class<?> clazz, Seq<Object> args2) {
        return AbstractProps.create$((AbstractProps)this, clazz, args2);
    }

    @Override
    public <T extends Actor> Props create(Creator<T> creator) {
        return AbstractProps.create$(this, creator);
    }

    @Override
    public <T extends Actor> Props create(Class<T> actorClass, Creator<T> creator) {
        return AbstractProps.create$((AbstractProps)this, actorClass, creator);
    }

    @Override
    public Props create(Class<?> clazz, Object ... args2) {
        return AbstractProps.create$((AbstractProps)this, clazz, args2);
    }

    public final Function0<Actor> defaultCreator() {
        return defaultCreator;
    }

    public final RouterConfig defaultRoutedProps() {
        return defaultRoutedProps;
    }

    public final Deploy defaultDeploy() {
        return defaultDeploy;
    }

    public final Props empty() {
        return empty;
    }

    public final Props default() {
        return default;
    }

    public <T extends Actor> Props apply(ClassTag<T> evidence$1) {
        return new Props(this.defaultDeploy(), Predef$.MODULE$.implicitly(evidence$1).runtimeClass(), (Seq<Object>)package$.MODULE$.List().empty());
    }

    public <T extends Actor> Props apply(Function0<T> creator, ClassTag<T> evidence$2) {
        return this.mkProps(Predef$.MODULE$.implicitly(evidence$2).runtimeClass(), creator);
    }

    private Props mkProps(Class<?> classOfActor, Function0<Actor> ctor) {
        return this.apply(TypedCreatorFunctionConsumer.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{classOfActor, ctor}));
    }

    public Props apply(Class<?> clazz, Seq<Object> args2) {
        return new Props(this.defaultDeploy(), clazz, args2.toList());
    }

    public Props apply(Deploy deploy, Class<?> clazz, Seq<Object> args2) {
        return new Props(deploy, clazz, args2);
    }

    public Option<Tuple3<Deploy, Class<?>, Seq<Object>>> unapply(Props x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some(new Tuple3(x$0.deploy(), x$0.clazz(), x$0.args()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Props$.class);
    }

    private Props$() {
    }
}

