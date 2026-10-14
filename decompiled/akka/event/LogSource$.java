/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.event.ActorWithLogClass;
import akka.event.DummyClassForStringSources;
import akka.event.LogSource;
import akka.event.Logging$;
import scala.Option;
import scala.Predef$;
import scala.Tuple2;
import scala.util.control.NonFatal$;

public final class LogSource$ {
    public static final LogSource$ MODULE$ = new LogSource$();
    private static final LogSource<String> fromString = new LogSource<String>(){

        public String genString(String s2) {
            return s2;
        }

        public String genString(String s2, ActorSystem system) {
            return new StringBuilder(2).append(s2).append("(").append(system).append(")").toString();
        }

        public Class<DummyClassForStringSources> getClazz(String s2) {
            return DummyClassForStringSources.class;
        }
        {
            LogSource.$init$(this);
        }
    };
    private static final LogSource<Actor> fromActor = new LogSource<Actor>(){

        public Class getClazz(Object t2) {
            return LogSource.getClazz$(this, t2);
        }

        public String genString(Actor a) {
            return LogSource$.MODULE$.fromActorRef().genString(a.self());
        }

        public String genString(Actor a, ActorSystem system) {
            return LogSource$.MODULE$.fromActorRef().genString(a.self(), system);
        }
        {
            LogSource.$init$(this);
        }
    };
    private static final LogSource<ActorRef> fromActorRef = new LogSource<ActorRef>(){

        public Class getClazz(Object t2) {
            return LogSource.getClazz$(this, t2);
        }

        public String genString(ActorRef a) {
            return a.path().toString();
        }

        public String genString(ActorRef a, ActorSystem system) {
            String string;
            try {
                string = a.path().toStringWithAddress(((ExtendedActorSystem)system).provider().getDefaultAddress());
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    string = a.path().toString();
                }
                throw throwable;
            }
            return string;
        }
        {
            LogSource.$init$(this);
        }
    };
    @InternalApi
    private static final LogSource<ActorWithLogClass> fromActorWithLoggerClass = new LogSource<ActorWithLogClass>(){

        public String genString(ActorWithLogClass a) {
            return LogSource$.MODULE$.fromActor().genString(a.actor());
        }

        public String genString(ActorWithLogClass a, ActorSystem system) {
            return LogSource$.MODULE$.fromActor().genString(a.actor(), system);
        }

        public Class<?> getClazz(ActorWithLogClass a) {
            return a.logClass();
        }
        {
            LogSource.$init$(this);
        }
    };
    private static final LogSource<Class<?>> fromClass = new LogSource<Class<?>>(){

        public String genString(Class<?> c) {
            return Logging$.MODULE$.simpleName(c);
        }

        public String genString(Class<?> c, ActorSystem system) {
            return new StringBuilder(2).append(this.genString(c)).append("(").append(system).append(")").toString();
        }

        public Class<?> getClazz(Class<?> c) {
            return c;
        }
        {
            LogSource.$init$(this);
        }
    };

    public LogSource<String> fromString() {
        return fromString;
    }

    public LogSource<Actor> fromActor() {
        return fromActor;
    }

    public LogSource<ActorRef> fromActorRef() {
        return fromActorRef;
    }

    public LogSource<ActorWithLogClass> fromActorWithLoggerClass() {
        return fromActorWithLoggerClass;
    }

    public LogSource<Class<?>> fromClass() {
        return fromClass;
    }

    public <T> LogSource<Class<T>> fromAnyClass() {
        return this.fromClass();
    }

    public <T> Tuple2<String, Class<?>> apply(T o, LogSource<T> evidence$1) {
        LogSource<T> ls = Predef$.MODULE$.implicitly(evidence$1);
        return new Tuple2(ls.genString(o), ls.getClazz(o));
    }

    public <T> Tuple2<String, Class<?>> apply(T o, ActorSystem system, LogSource<T> evidence$2) {
        LogSource<T> ls = Predef$.MODULE$.implicitly(evidence$2);
        return new Tuple2(ls.genString(o, system), ls.getClazz(o));
    }

    public Tuple2<String, Class<?>> fromAnyRef(Object o) {
        Object object = o;
        if (object instanceof Class) {
            Class clazz = (Class)object;
            return this.apply(clazz, this.fromAnyClass());
        }
        if (object instanceof Actor) {
            Actor actor = (Actor)object;
            return this.apply(actor, this.fromActor());
        }
        if (object instanceof ActorRef) {
            ActorRef actorRef2 = (ActorRef)object;
            return this.apply(actorRef2, this.fromActorRef());
        }
        if (object instanceof String) {
            String string = (String)object;
            return this.apply(string, this.fromString());
        }
        return new Tuple2(Logging$.MODULE$.simpleName(object), object.getClass());
    }

    public Tuple2<String, Class<?>> fromAnyRef(Object o, ActorSystem system) {
        Object object = o;
        if (object instanceof Class) {
            Class clazz = (Class)object;
            return this.apply(clazz, this.fromAnyClass());
        }
        if (object instanceof Actor) {
            Actor actor = (Actor)object;
            return this.apply(actor, this.fromActor());
        }
        if (object instanceof ActorRef) {
            ActorRef actorRef2 = (ActorRef)object;
            return this.apply(actorRef2, this.fromActorRef());
        }
        if (object instanceof String) {
            String string = (String)object;
            return this.apply(string, this.fromString());
        }
        return new Tuple2(new StringBuilder(2).append(Logging$.MODULE$.simpleName(object)).append("(").append(system).append(")").toString(), object.getClass());
    }

    private LogSource$() {
    }
}

