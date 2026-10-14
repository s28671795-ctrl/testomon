/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.DynamicAccess;
import akka.annotation.DoNotInherit;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.util.Failure;
import scala.util.Try;
import scala.util.Try$;

@ScalaSignature(bytes="\u0006\u0005\u0005Ec\u0001B\u0005\u000b\u0001=A\u0001\u0002\u0006\u0001\u0003\u0006\u0004%\t!\u0006\u0005\t=\u0001\u0011\t\u0011)A\u0005-!)q\u0004\u0001C\u0001A!)1\u0005\u0001C!I!)\u0001\f\u0001C!3\")\u0001\f\u0001C!}\"9\u0011\u0011\u0005\u0001\u0005B\u0005\r\u0002bBA\u0017\u0001\u0011\u0005\u0013q\u0006\u0002\u0018%\u00164G.Z2uSZ,G)\u001f8b[&\u001c\u0017iY2fgNT!a\u0003\u0007\u0002\u000b\u0005\u001cGo\u001c:\u000b\u00035\tA!Y6lC\u000e\u00011C\u0001\u0001\u0011!\t\t\"#D\u0001\u000b\u0013\t\u0019\"BA\u0007Es:\fW.[2BG\u000e,7o]\u0001\fG2\f7o\u001d'pC\u0012,'/F\u0001\u0017!\t9B$D\u0001\u0019\u0015\tI\"$\u0001\u0003mC:<'\"A\u000e\u0002\t)\fg/Y\u0005\u0003;a\u00111b\u00117bgNdu.\u00193fe\u0006a1\r\\1tg2{\u0017\rZ3sA\u00051A(\u001b8jiz\"\"!\t\u0012\u0011\u0005E\u0001\u0001\"\u0002\u000b\u0004\u0001\u00041\u0012aC4fi\u000ec\u0017m]:G_J,\"!J#\u0015\u0005\u0019\u001aFCA\u0014L!\rASfL\u0007\u0002S)\u0011!fK\u0001\u0005kRLGNC\u0001-\u0003\u0015\u00198-\u00197b\u0013\tq\u0013FA\u0002Uef\u0004$\u0001M\u001f\u0011\u0007EB4H\u0004\u00023mA\u00111gK\u0007\u0002i)\u0011QGD\u0001\u0007yI|w\u000e\u001e \n\u0005]Z\u0013A\u0002)sK\u0012,g-\u0003\u0002:u\t)1\t\\1tg*\u0011qg\u000b\t\u0003yub\u0001\u0001B\u0005?\t\u0005\u0005\t\u0011!B\u0001\u007f\t\u0019q\fJ\u0019\u0012\u0005\u0001#\u0005CA!C\u001b\u0005Y\u0013BA\",\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001P#\u0005\u000b\u0019#!\u0019A$\u0003\u0003Q\u000b\"\u0001\u0011%\u0011\u0005\u0005K\u0015B\u0001&,\u0005\r\te.\u001f\u0005\b\u0019\u0012\t\t\u0011q\u0001N\u0003))g/\u001b3f]\u000e,G%\r\t\u0004\u001dF#U\"A(\u000b\u0005A[\u0013a\u0002:fM2,7\r^\u0005\u0003%>\u0013\u0001b\u00117bgN$\u0016m\u001a\u0005\u0006)\u0012\u0001\r!V\u0001\u0005MF\u001cg\u000e\u0005\u00022-&\u0011qK\u000f\u0002\u0007'R\u0014\u0018N\\4\u0002#\r\u0014X-\u0019;f\u0013:\u001cH/\u00198dK\u001a{'/\u0006\u0002[=R\u00191LY5\u0015\u0005q{\u0006c\u0001\u0015.;B\u0011AH\u0018\u0003\u0006\r\u0016\u0011\ra\u0012\u0005\bA\u0016\t\t\u0011q\u0001b\u0003))g/\u001b3f]\u000e,GE\r\t\u0004\u001dFk\u0006\"B2\u0006\u0001\u0004!\u0017!B2mCjT\bGA3h!\r\t\u0004H\u001a\t\u0003y\u001d$\u0011\u0002\u001b2\u0002\u0002\u0003\u0005)\u0011A$\u0003\u0007}#C\u0007C\u0003k\u000b\u0001\u00071.\u0001\u0003be\u001e\u001c\bc\u00017rg6\tQN\u0003\u0002o_\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003a.\n!bY8mY\u0016\u001cG/[8o\u0013\t\u0011XNA\u0002TKF\u0004B!\u0011;ww&\u0011Qo\u000b\u0002\u0007)V\u0004H.\u001a\u001a1\u0005]L\bcA\u00199qB\u0011A(\u001f\u0003\nu&\f\t\u0011!A\u0003\u0002\u001d\u00131a\u0018\u00136!\t\tE0\u0003\u0002~W\t1\u0011I\\=SK\u001a,2a`A\u0004)\u0019\t\t!a\u0004\u0002\u0012Q!\u00111AA\u0005!\u0011AS&!\u0002\u0011\u0007q\n9\u0001B\u0003G\r\t\u0007q\tC\u0005\u0002\f\u0019\t\t\u0011q\u0001\u0002\u000e\u0005QQM^5eK:\u001cW\rJ\u001a\u0011\t9\u000b\u0016Q\u0001\u0005\u0006)\u001a\u0001\r!\u0016\u0005\u0007U\u001a\u0001\r!a\u0005\u0011\t1\f\u0018Q\u0003\t\u0006\u0003R\f9b\u001f\u0019\u0005\u00033\ti\u0002\u0005\u00032q\u0005m\u0001c\u0001\u001f\u0002\u001e\u0011Y\u0011qDA\t\u0003\u0003\u0005\tQ!\u0001H\u0005\ryFEN\u0001\u0013G2\f7o]%t\u001f:\u001cE.Y:ta\u0006$\b\u000e\u0006\u0003\u0002&\u0005-\u0002cA!\u0002(%\u0019\u0011\u0011F\u0016\u0003\u000f\t{w\u000e\\3b]\")Ak\u0002a\u0001+\u0006aq-\u001a;PE*,7\r\u001e$peV!\u0011\u0011GA\u001d)\u0011\t\u0019$!\u0011\u0015\t\u0005U\u00121\b\t\u0005Q5\n9\u0004E\u0002=\u0003s!QA\u0012\u0005C\u0002\u001dC\u0011\"!\u0010\t\u0003\u0003\u0005\u001d!a\u0010\u0002\u0015\u00154\u0018\u000eZ3oG\u0016$C\u0007\u0005\u0003O#\u0006]\u0002\"\u0002+\t\u0001\u0004)\u0006f\u0001\u0001\u0002FA!\u0011qIA'\u001b\t\tIEC\u0002\u0002L1\t!\"\u00198o_R\fG/[8o\u0013\u0011\ty%!\u0013\u0003\u0019\u0011{gj\u001c;J]\",'/\u001b;")
@DoNotInherit
public class ReflectiveDynamicAccess
extends DynamicAccess {
    private final ClassLoader classLoader;

    @Override
    public ClassLoader classLoader() {
        return this.classLoader;
    }

    @Override
    public <T> Try<Class<? extends T>> getClassFor(String fqcn, ClassTag<T> evidence$1) {
        return Try$.MODULE$.apply((Function0<Class> & Serializable)() -> {
            Class<?> c = Class.forName(fqcn, false, this.classLoader());
            Class<?> t2 = Predef$.MODULE$.implicitly(evidence$1).runtimeClass();
            if (t2.isAssignableFrom(c)) {
                return c;
            }
            throw new ClassCastException(new StringBuilder(24).append(t2.toString()).append(" is not assignable from ").append(c).toString());
        });
    }

    @Override
    public <T> Try<T> createInstanceFor(Class<?> clazz, Seq<Tuple2<Class<?>, Object>> args2, ClassTag<T> evidence$2) {
        return Try$.MODULE$.apply((Function0<Object> & Serializable)() -> {
            Class[] types = (Class[])((IterableOnceOps)args2.map((Function1<Tuple2, Class> & Serializable)x$1 -> (Class)x$1._1())).toArray(ClassTag$.MODULE$.apply(Class.class));
            Object[] values2 = (Object[])((IterableOnceOps)args2.map((Function1<Tuple2, Object> & Serializable)x$2 -> x$2._2())).toArray(ClassTag$.MODULE$.AnyRef());
            Constructor constructor = clazz.getDeclaredConstructor(types);
            constructor.setAccessible(true);
            Object obj = constructor.newInstance(values2);
            Class<?> t2 = Predef$.MODULE$.implicitly(evidence$2).runtimeClass();
            if (t2.isInstance(obj)) {
                return obj;
            }
            throw new ClassCastException(new StringBuilder(21).append(clazz.getName()).append(" is not a subtype of ").append(t2).toString());
        }).recover(new Serializable(null){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                InvocationTargetException invocationTargetException;
                A1 A1 = x1;
                if (A1 instanceof InvocationTargetException && (invocationTargetException = (InvocationTargetException)A1).getTargetException() != null) {
                    throw invocationTargetException.getTargetException();
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                InvocationTargetException invocationTargetException;
                Throwable throwable = x1;
                return throwable instanceof InvocationTargetException && (invocationTargetException = (InvocationTargetException)throwable).getTargetException() != null;
            }
        });
    }

    @Override
    public <T> Try<T> createInstanceFor(String fqcn, Seq<Tuple2<Class<?>, Object>> args2, ClassTag<T> evidence$3) {
        return this.getClassFor(fqcn, evidence$3).flatMap((Function1<Class, Try> & Serializable)c -> this.createInstanceFor((Class<?>)c, args2, evidence$3));
    }

    @Override
    public boolean classIsOnClasspath(String fqcn) {
        Failure failure;
        Try<Class<Object>> try_ = this.getClassFor(fqcn, ClassTag$.MODULE$.Any());
        return !(try_ instanceof Failure) || !((failure = (Failure)try_).exception() instanceof ClassNotFoundException ? true : failure.exception() instanceof NoClassDefFoundError);
    }

    @Override
    public <T> Try<T> getObjectFor(String fqcn, ClassTag<T> evidence$4) {
        Try<Class<T>> classTry = fqcn.endsWith("$") ? this.getClassFor(fqcn, evidence$4) : this.getClassFor(new StringBuilder(1).append(fqcn).append("$").toString(), evidence$4).recoverWith(new Serializable(this, fqcn, evidence$4){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ ReflectiveDynamicAccess $outer;
            private final String fqcn$2;
            private final ClassTag evidence$4$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                return (B1)this.$outer.getClassFor(this.fqcn$2, this.evidence$4$1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.fqcn$2 = fqcn$2;
                this.evidence$4$1 = evidence$4$1;
            }
        });
        return classTry.flatMap((Function1<Class, Try> & Serializable)c -> Try$.MODULE$.apply((Function0<Object> & Serializable)() -> {
            Option option;
            Field module = c.getDeclaredField("MODULE$");
            module.setAccessible(true);
            Class<?> t2 = Predef$.MODULE$.implicitly(evidence$4).runtimeClass();
            Object object = module.get(null);
            if (object == null) {
                throw new NullPointerException();
            }
            if (!t2.isInstance(object)) {
                throw new ClassCastException(new StringBuilder(21).append(fqcn).append(" is not a subtype of ").append(t2).toString());
            }
            if (object != null && !(option = evidence$4.unapply(object)).isEmpty() && option.get() instanceof Object) {
                return object;
            }
            throw new IllegalArgumentException(new StringBuilder(25).append("Unexpected module field: ").append(object).toString());
        }).recover(new Serializable(null){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x2, Function1<A1, B1> function1) {
                InvocationTargetException invocationTargetException;
                A1 A1 = x2;
                if (A1 instanceof InvocationTargetException && (invocationTargetException = (InvocationTargetException)A1).getTargetException() != null) {
                    throw invocationTargetException.getTargetException();
                }
                return function1.apply(x2);
            }

            public final boolean isDefinedAt(Throwable x2) {
                InvocationTargetException invocationTargetException;
                Throwable throwable = x2;
                return throwable instanceof InvocationTargetException && (invocationTargetException = (InvocationTargetException)throwable).getTargetException() != null;
            }
        }));
    }

    public ReflectiveDynamicAccess(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}

