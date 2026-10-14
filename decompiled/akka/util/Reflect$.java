/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.BoxedType$;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Null$;
import scala.util.Try$;
import scala.util.control.NonFatal$;

@InternalApi
public final class Reflect$ {
    public static final Reflect$ MODULE$ = new Reflect$();
    private static final Option<Function1<Object, Class<?>>> getCallerClass = Reflect$.liftedTree1$1();

    public Option<Function1<Object, Class<?>>> getCallerClass() {
        return getCallerClass;
    }

    public <T> T instantiate(Class<T> clazz) {
        T t2;
        Constructor<T> ctor = clazz.getDeclaredConstructor(new Class[0]);
        try {
            t2 = ctor.newInstance(new Object[0]);
        }
        catch (IllegalAccessException illegalAccessException) {
            ctor.setAccessible(true);
            t2 = ctor.newInstance(new Object[0]);
        }
        return t2;
    }

    public <T> T instantiate(Class<T> clazz, Seq<Object> args2) {
        return this.instantiate(this.findConstructor(clazz, args2), args2);
    }

    public <T> T instantiate(Constructor<T> constructor, Seq<Object> args2) {
        T t2;
        constructor.setAccessible(true);
        try {
            t2 = constructor.newInstance((Object[])args2.toArray(ClassTag$.MODULE$.AnyRef()));
        }
        catch (IllegalArgumentException e) {
            String argString = ((IterableOnceOps)args2.map((Function1<Object, Class> & Serializable)a -> MODULE$.safeGetClass(a))).mkString("[", ", ", "]");
            throw new IllegalArgumentException(new StringBuilder(44).append("constructor ").append(constructor).append(" is incompatible with arguments ").append(argString).toString(), e);
        }
        return t2;
    }

    public <T> Constructor<T> findConstructor(Class<T> clazz, Seq<Object> args2) {
        Constructor constructor;
        Constructor constructor2;
        if (args2.isEmpty()) {
            constructor2 = (Constructor)Try$.MODULE$.apply((Function0<Constructor> & Serializable)() -> clazz.getDeclaredConstructor(new Class[0])).getOrElse((Function0<Null$> & Serializable)() -> null);
        } else {
            int length = args2.length();
            Iterator candidates = ArrayOps$.MODULE$.iterator$extension(Predef$.MODULE$.refArrayOps(clazz.getDeclaredConstructors())).filter((Function1<Constructor, Object> & Serializable)c -> BoxesRunTime.boxToBoolean(Reflect$.$anonfun$findConstructor$4(length, args2, c)));
            if (candidates.hasNext()) {
                Constructor cstrtr = (Constructor)candidates.next();
                if (candidates.hasNext()) {
                    throw Reflect$.error$1("multiple matching constructors", args2, clazz);
                }
                constructor2 = cstrtr;
            } else {
                constructor2 = constructor = null;
            }
        }
        if (constructor == null) {
            throw Reflect$.error$1("no matching constructor", args2, clazz);
        }
        return constructor;
    }

    private Class<?> safeGetClass(Object a) {
        if (a == null) {
            return Object.class;
        }
        return a.getClass();
    }

    public <T> Function0<T> instantiator(Class<T> clazz) {
        return (Function0<Object> & Serializable)() -> MODULE$.instantiate(clazz);
    }

    public Type findMarker(Class<?> root, Class<?> marker) {
        return this.rec$1(root, marker, root);
    }

    public ClassLoader findClassLoader() {
        return (ClassLoader)Option$.MODULE$.apply(Thread.currentThread().getContextClassLoader()).orElse((Function0<Option> & Serializable)() -> MODULE$.getCallerClass().map((Function1<Function1, ClassLoader> & Serializable)get2 -> this.findCaller$1((Function1)get2))).getOrElse((Function0<ClassLoader> & Serializable)() -> MODULE$.getClass().getClassLoader());
    }

    public static final /* synthetic */ Class $anonfun$getCallerClass$1(Method m$1, int i) {
        return (Class)m$1.invoke(null, BoxesRunTime.boxToInteger(i));
    }

    private static final /* synthetic */ Option liftedTree1$1() {
        Option option;
        try {
            Class<?> c = Class.forName("sun.reflect.Reflection");
            Method m4 = c.getMethod("getCallerClass", (Class[])((Object[])new Class[]{Integer.TYPE}));
            option = new Some<Function1<Object, Class> & Serializable>((Function1<Object, Class> & Serializable)i -> Reflect$.$anonfun$getCallerClass$1(m4, BoxesRunTime.unboxToInt(i)));
        }
        catch (Throwable throwable) {
            Option<Throwable> option2;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option2 = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                option = None$.MODULE$;
            }
            throw throwable;
        }
        return option;
    }

    private static final Nothing$ error$1(String msg, Seq args$1, Class clazz$1) {
        String argClasses = ((IterableOnceOps)args$1.map((Function1<Object, Class> & Serializable)a -> MODULE$.safeGetClass(a))).mkString(", ");
        throw new IllegalArgumentException(new StringBuilder(27).append(msg).append(" found on ").append(clazz$1).append(" for arguments [").append(argClasses).append("]").toString());
    }

    public static final /* synthetic */ boolean $anonfun$findConstructor$5(Tuple2 x0$1) {
        Tuple2 tuple2 = x0$1;
        if (tuple2 != null) {
            Object required;
            Class found = (Class)tuple2._1();
            return found.isInstance(required = tuple2._2()) || BoxedType$.MODULE$.apply(found).isInstance(required) || required == null && !found.isPrimitive();
        }
        throw new MatchError(tuple2);
    }

    public static final /* synthetic */ boolean $anonfun$findConstructor$4(int length$1, Seq args$1, Constructor c) {
        Class<?>[] parameterTypes = c.getParameterTypes();
        return parameterTypes.length == length$1 && ArrayOps$.MODULE$.iterator$extension(Predef$.MODULE$.refArrayOps(parameterTypes)).zip(args$1.iterator()).forall((Function1<Tuple2, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(Reflect$.$anonfun$findConstructor$5(x0$1)));
    }

    private final Type rec$1(Class curr, Class marker$1, Class root$1) {
        while (true) {
            Type t2;
            if (curr.getSuperclass() != null && marker$1.isAssignableFrom(curr.getSuperclass())) {
                curr = curr.getSuperclass();
                continue;
            }
            boolean bl = false;
            Some some = null;
            Option<Type> option = ArrayOps$.MODULE$.collectFirst$extension(Predef$.MODULE$.refArrayOps(curr.getGenericInterfaces()), new Serializable(marker$1){
                private static final long serialVersionUID = 0L;
                private final Class marker$1;

                public final <A1 extends Type, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    ParameterizedType parameterizedType;
                    Class clazz;
                    A1 A1 = x1;
                    if (A1 instanceof Class && this.marker$1.isAssignableFrom(clazz = (Class)A1)) {
                        return (B1)clazz;
                    }
                    if (A1 instanceof ParameterizedType && this.marker$1.isAssignableFrom((Class)(parameterizedType = (ParameterizedType)A1).getRawType())) {
                        return (B1)parameterizedType;
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Type x1) {
                    ParameterizedType parameterizedType;
                    Class clazz;
                    Type type = x1;
                    if (type instanceof Class && this.marker$1.isAssignableFrom(clazz = (Class)type)) {
                        return true;
                    }
                    return type instanceof ParameterizedType && this.marker$1.isAssignableFrom((Class)(parameterizedType = (ParameterizedType)type).getRawType());
                }
                {
                    this.marker$1 = marker$1;
                }
            });
            if (None$.MODULE$.equals(option)) {
                throw new IllegalArgumentException(new StringBuilder(33).append("cannot find [").append(marker$1).append("] in ancestors of [").append(root$1).append("]").toString());
            }
            if (option instanceof Some) {
                bl = true;
                some = (Some)option;
                Type c = (Type)some.value();
                if (c instanceof Class) {
                    Class clazz;
                    Class clazz2 = clazz = (Class)c;
                    Class clazz3 = marker$1;
                    if (!(clazz2 != null ? !clazz2.equals(clazz3) : clazz3 != null)) {
                        return clazz;
                    }
                    curr = clazz;
                    continue;
                }
            }
            if (!bl || !((t2 = (Type)some.value()) instanceof ParameterizedType)) break;
            ParameterizedType parameterizedType = (ParameterizedType)t2;
            Type type = parameterizedType.getRawType();
            Class clazz = marker$1;
            if (!(type != null ? !type.equals(clazz) : clazz != null)) {
                return parameterizedType;
            }
            curr = (Class)parameterizedType.getRawType();
        }
        throw Predef$.MODULE$.$qmark$qmark$qmark();
    }

    public static final /* synthetic */ boolean $anonfun$findClassLoader$1(Class c) {
        return c != null && (c.getName().startsWith("akka.actor.ActorSystem") || c.getName().startsWith("scala.Option") || c.getName().startsWith("scala.collection.Iterator") || c.getName().startsWith("akka.util.Reflect"));
    }

    private final ClassLoader findCaller$1(Function1 get2) {
        Class clazz = (Class)package$.MODULE$.Iterator().from(2).map(get2).dropWhile((Function1<Class, Object> & Serializable)c -> BoxesRunTime.boxToBoolean(Reflect$.$anonfun$findClassLoader$1(c))).next();
        if (clazz == null) {
            return this.getClass().getClassLoader();
        }
        return clazz.getClassLoader();
    }

    private Reflect$() {
    }
}

