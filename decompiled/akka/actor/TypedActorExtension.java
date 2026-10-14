/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.TypedActor;
import akka.actor.TypedActorFactory;
import akka.actor.TypedProps;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import akka.util.Timeout;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicReference;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a4A\u0001D\u0007\u0001%!A\u0001\u0005\u0001BC\u0002\u0013\u0005\u0011\u0005\u0003\u0005&\u0001\t\u0005\t\u0015!\u0003#\u0011\u00151\u0003\u0001\"\u0001(\u0011\u0015Q\u0003\u0001\"\u0005,\u0011\u0015y\u0003\u0001\"\u00051\u0011\u001d\t\u0004A1A\u0005\u0006IBa!\u000f\u0001!\u0002\u001b\u0019\u0004\"\u0002\u001e\u0001\t\u0003Y\u0004\"B!\u0001\t\u0003\u0011\u0005B\u0002%\u0001\t\u0003y\u0011\n\u0003\u0004o\u0001\u0011\u0005qb\u001c\u0002\u0014)f\u0004X\rZ!di>\u0014X\t\u001f;f]NLwN\u001c\u0006\u0003\u001d=\tQ!Y2u_JT\u0011\u0001E\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\t\u0001\u0019\u0012$\b\t\u0003)]i\u0011!\u0006\u0006\u0002-\u0005)1oY1mC&\u0011\u0001$\u0006\u0002\u0007\u0003:L(+\u001a4\u0011\u0005iYR\"A\u0007\n\u0005qi!!\u0005+za\u0016$\u0017i\u0019;pe\u001a\u000b7\r^8ssB\u0011!DH\u0005\u0003?5\u0011\u0011\"\u0012=uK:\u001c\u0018n\u001c8\u0002\rML8\u000f^3n+\u0005\u0011\u0003C\u0001\u000e$\u0013\t!SBA\nFqR,g\u000eZ3e\u0003\u000e$xN]*zgR,W.A\u0004tsN$X-\u001c\u0011\u0002\rqJg.\u001b;?)\tA\u0013\u0006\u0005\u0002\u001b\u0001!)\u0001e\u0001a\u0001E\u0005a\u0011m\u0019;pe\u001a\u000b7\r^8ssV\tA\u0006\u0005\u0002\u001b[%\u0011a&\u0004\u0002\u0010\u0003\u000e$xN\u001d*fM\u001a\u000b7\r^8ss\u0006QA/\u001f9fI\u0006\u001bGo\u001c:\u0016\u0003!\nA\u0003R3gCVdGOU3ukJtG+[7f_V$X#A\u001a\u0011\u0005Q:T\"A\u001b\u000b\u0005Yz\u0011\u0001B;uS2L!\u0001O\u001b\u0003\u000fQKW.Z8vi\u0006)B)\u001a4bk2$(+\u001a;ve:$\u0016.\\3pkR\u0004\u0013AD4fi\u0006\u001bGo\u001c:SK\u001a4uN\u001d\u000b\u0003y}\u0002\"AG\u001f\n\u0005yj!\u0001C!di>\u0014(+\u001a4\t\u000b\u0001C\u0001\u0019A\n\u0002\u000bA\u0014x\u000e_=\u0002\u0019%\u001cH+\u001f9fI\u0006\u001bGo\u001c:\u0015\u0005\r3\u0005C\u0001\u000bE\u0013\t)UCA\u0004C_>dW-\u00198\t\u000b\u001dK\u0001\u0019A\n\u0002\u0015A\u0014x\u000e_=Pe:{G/A\nde\u0016\fG/Z!di>\u0014(+\u001a4Qe>D\u00180F\u0002K\u001bf#BaS*]SB\u0011A*\u0014\u0007\u0001\t\u0015q%B1\u0001P\u0005\u0005\u0011\u0016C\u0001)\u0014!\t!\u0012+\u0003\u0002S+\t9aj\u001c;iS:<\u0007\"\u0002+\u000b\u0001\u0004)\u0016!\u00029s_B\u001c\bc\u0001\u000eW1&\u0011q+\u0004\u0002\u000b)f\u0004X\r\u001a)s_B\u001c\bC\u0001'Z\t\u0015Q&B1\u0001\\\u0005\u0005!\u0016C\u0001)L\u0011\u0015i&\u00021\u0001_\u0003!\u0001(o\u001c=z-\u0006\u0014\bcA0h\u00176\t\u0001M\u0003\u0002bE\u00061\u0011\r^8nS\u000eT!a\u00193\u0002\u0015\r|gnY;se\u0016tGO\u0003\u00027K*\ta-\u0001\u0003kCZ\f\u0017B\u00015a\u0005=\tEo\\7jGJ+g-\u001a:f]\u000e,\u0007B\u00026\u000b\t\u0003\u00071.\u0001\u0005bGR|'OU3g!\r!B\u000eP\u0005\u0003[V\u0011\u0001\u0002\u00102z]\u0006lWMP\u0001\u0015S:4xnY1uS>t\u0007*\u00198eY\u0016\u0014hi\u001c:\u0015\u0005A<\bCA9u\u001d\tQ\"/\u0003\u0002t\u001b\u0005QA+\u001f9fI\u0006\u001bGo\u001c:\n\u0005U4(a\u0007+za\u0016$\u0017i\u0019;pe&sgo\\2bi&|g\u000eS1oI2,'O\u0003\u0002t\u001b!)qf\u0003a\u0001'\u0001")
public class TypedActorExtension
implements TypedActorFactory,
Extension {
    private final ExtendedActorSystem system;
    private final Timeout DefaultReturnTimeout;

    @Override
    public boolean stop(Object proxy) {
        return TypedActorFactory.stop$(this, proxy);
    }

    @Override
    public boolean poisonPill(Object proxy) {
        return TypedActorFactory.poisonPill$(this, proxy);
    }

    @Override
    public <R, T extends R> R typedActorOf(TypedProps<T> props) {
        return (R)TypedActorFactory.typedActorOf$(this, props);
    }

    @Override
    public <R, T extends R> R typedActorOf(TypedProps<T> props, String name) {
        return (R)TypedActorFactory.typedActorOf$((TypedActorFactory)this, props, name);
    }

    @Override
    public <R, T extends R> R typedActorOf(TypedProps<T> props, ActorRef actorRef2) {
        return (R)TypedActorFactory.typedActorOf$((TypedActorFactory)this, props, actorRef2);
    }

    public ExtendedActorSystem system() {
        return this.system;
    }

    @Override
    public ActorRefFactory actorFactory() {
        return this.system();
    }

    @Override
    public TypedActorExtension typedActor() {
        return this;
    }

    public final Timeout DefaultReturnTimeout() {
        return this.DefaultReturnTimeout;
    }

    @Override
    public ActorRef getActorRefFor(Object proxy) {
        TypedActor.TypedActorInvocationHandler typedActorInvocationHandler = this.invocationHandlerFor(proxy);
        if (typedActorInvocationHandler == null) {
            return null;
        }
        return typedActorInvocationHandler.actor();
    }

    @Override
    public boolean isTypedActor(Object proxyOrNot) {
        return this.invocationHandlerFor(proxyOrNot) != null;
    }

    public <R, T extends R> R createActorRefProxy(TypedProps<T> props, AtomicReference<R> proxyVar, Function0<ActorRef> actorRef2) {
        AtomicReference<Object> actorVar = new AtomicReference<Object>(null);
        Object proxy = Proxy.newProxyInstance((ClassLoader)props.loader().orElse((Function0<Option> & Serializable)() -> props.interfaces().collectFirst(new Serializable(null){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Class<?>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                return (B1)A1.getClassLoader();
            }

            public final boolean isDefinedAt(Class<?> x1) {
                Class<?> clazz = x1;
                return true;
            }
        })).orNull($less$colon$less$.MODULE$.refl()), (Class[])props.interfaces().toArray(ClassTag$.MODULE$.apply(Class.class)), new TypedActor.TypedActorInvocationHandler(this, actorVar, (Timeout)props.timeout().getOrElse((Function0<Timeout> & Serializable)() -> this.DefaultReturnTimeout())));
        if (proxyVar == null) {
            actorVar.set(actorRef2.apply());
            return (R)proxy;
        }
        proxyVar.set(proxy);
        actorVar.set(actorRef2.apply());
        return proxyVar.get();
    }

    public TypedActor.TypedActorInvocationHandler invocationHandlerFor(Object typedActor) {
        if (typedActor != null && Proxy.class.isAssignableFrom(typedActor.getClass()) && Proxy.isProxyClass(typedActor.getClass())) {
            Object object = typedActor;
            if (object == null) {
                return null;
            }
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(object);
            if (invocationHandler == null) {
                return null;
            }
            if (invocationHandler instanceof TypedActor.TypedActorInvocationHandler) {
                TypedActor.TypedActorInvocationHandler typedActorInvocationHandler = (TypedActor.TypedActorInvocationHandler)invocationHandler;
                return typedActorInvocationHandler;
            }
            return null;
        }
        return null;
    }

    public TypedActorExtension(ExtendedActorSystem system) {
        this.system = system;
        TypedActorFactory.$init$(this);
        this.DefaultReturnTimeout = new Timeout(Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(system.settings().config()), "akka.actor.typed.timeout"));
    }
}

