/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor;
import akka.actor.CreatorConsumer;
import akka.actor.IndirectActorProducer;
import akka.actor.Props;
import akka.actor.Props$;
import akka.japi.Creator;
import akka.util.Reflect$;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005a4\u0001b\u0002\u0005\u0011\u0002\u0007\u0005!\u0002\u0004\u0005\u0006'\u0001!\t!\u0006\u0005\u00073\u0001!\tA\u0003\u000e\t\u000bQ\u0002A\u0011A\u001b\t\u000bQ\u0002A\u0011\u0001'\t\u000bQ\u0002A\u0011A4\t\u000bA\u0004A\u0011B9\u0003\u001b\u0005\u00137\u000f\u001e:bGR\u0004&o\u001c9t\u0015\tI!\"A\u0003bGR|'OC\u0001\f\u0003\u0011\t7n[1\u0014\u0005\u0001i\u0001C\u0001\b\u0012\u001b\u0005y!\"\u0001\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005Iy!AB!osJ+g-\u0001\u0004%S:LG\u000fJ\u0002\u0001)\u00051\u0002C\u0001\b\u0018\u0013\tArB\u0001\u0003V]&$\u0018\u0001\u0003<bY&$\u0017\r^3\u0015\u0005YY\u0002\"\u0002\u000f\u0003\u0001\u0004i\u0012!B2mCjT\bG\u0001\u0010,!\ryb%\u000b\b\u0003A\u0011\u0002\"!I\b\u000e\u0003\tR!a\t\u000b\u0002\rq\u0012xn\u001c;?\u0013\t)s\"\u0001\u0004Qe\u0016$WMZ\u0005\u0003O!\u0012Qa\u00117bgNT!!J\b\u0011\u0005)ZC\u0002\u0001\u0003\nYm\t\t\u0011!A\u0003\u00025\u00121a\u0018\u00132#\tq\u0013\u0007\u0005\u0002\u000f_%\u0011\u0001g\u0004\u0002\b\u001d>$\b.\u001b8h!\tq!'\u0003\u00024\u001f\t\u0019\u0011I\\=\u0002\r\r\u0014X-\u0019;f)\r1$\b\u0011\t\u0003oaj\u0011\u0001C\u0005\u0003s!\u0011Q\u0001\u0015:paNDQ\u0001H\u0002A\u0002m\u0002$\u0001\u0010 \u0011\u0007}1S\b\u0005\u0002+}\u0011IqHOA\u0001\u0002\u0003\u0015\t!\f\u0002\u0004?\u0012\u0012\u0004\"B!\u0004\u0001\u0004\u0011\u0015\u0001B1sON\u00042AD\"\u000e\u0013\t!uB\u0001\u0006=e\u0016\u0004X-\u0019;fIzB#a\u0001$\u0011\u0005\u001dSU\"\u0001%\u000b\u0005%{\u0011AC1o]>$\u0018\r^5p]&\u00111\n\u0013\u0002\bm\u0006\u0014\u0018M]4t+\tiu\u000b\u0006\u00027\u001d\")q\n\u0002a\u0001!\u000691M]3bi>\u0014\bcA)U-6\t!K\u0003\u0002T\u0015\u0005!!.\u00199j\u0013\t)&KA\u0004De\u0016\fGo\u001c:\u0011\u0005):F!\u0002-\u0005\u0005\u0004I&!\u0001+\u0012\u00059R\u0006CA\u001c\\\u0013\ta\u0006BA\u0003BGR|'\u000f\u000b\u0004\u0005=\u0006\u0014G-\u001a\t\u0003\u001d}K!\u0001Y\b\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003\r\f1,V:fAA\u0013x\u000e]:/GJ,\u0017\r^3)C\u000e$xN]\"mCN\u001cH\u0006I2sK\u0006$xN]\u0015!S:\u001cH/Z1eY\u0001\u001a\u0018N\\2fAQD\u0017n\u001d\u0011dC:<C\u000f\t2fAU\u001cX\r\u001a\u0011xSRD\u0007ES1wC\u0002B\u0004\u0005\\1nE\u0012\fg&A\u0003tS:\u001cW-I\u0001g\u0003\u0019\u0011d&\u000e\u00182qU\u0011\u0001.\u001c\u000b\u0004m%t\u0007\"\u00026\u0006\u0001\u0004Y\u0017AC1di>\u00148\t\\1tgB\u0019qD\n7\u0011\u0005)jG!\u0002-\u0006\u0005\u0004I\u0006\"B(\u0006\u0001\u0004y\u0007cA)UY\u000692\r[3dW\u000e\u0013X-\u0019;pe\u000ecwn]5oO>3XM\u001d\u000b\u0003-IDQ\u0001\b\u0004A\u0002M\u0004$\u0001\u001e<\u0011\u0007}1S\u000f\u0005\u0002+m\u0012IqO]A\u0001\u0002\u0003\u0015\t!\f\u0002\u0004?\u0012\"\u0004")
public interface AbstractProps {
    public static /* synthetic */ Props create$(AbstractProps $this, Class clazz, Object ... args2) {
        return $this.create(clazz, args2);
    }

    default public Props create(Class<?> clazz, Object ... args2) {
        return this.create(clazz, ScalaRunTime$.MODULE$.wrapRefArray(args2));
    }

    public static /* synthetic */ void validate$(AbstractProps $this, Class clazz) {
        $this.validate(clazz);
    }

    default public void validate(Class<?> clazz) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            throw new IllegalArgumentException(new StringBuilder(35).append("Actor class [").append(clazz.getName()).append("] must not be abstract").toString());
        }
        if (!Actor.class.isAssignableFrom(clazz) && !IndirectActorProducer.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(new StringBuilder(88).append("Actor class [").append(clazz.getName()).append("] must be subClass of akka.actor.Actor or akka.actor.IndirectActorProducer.").toString());
        }
    }

    public static /* synthetic */ Props create$(AbstractProps $this, Class clazz, Seq args2) {
        return $this.create(clazz, args2);
    }

    default public Props create(Class<?> clazz, Seq<Object> args2) {
        return new Props(Props$.MODULE$.defaultDeploy(), clazz, args2.toList());
    }

    public static /* synthetic */ Props create$(AbstractProps $this, Creator creator) {
        return $this.create(creator);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    default public <T extends Actor> Props create(Creator<T> creator) {
        Class clazz;
        Class<?> cc = creator.getClass();
        this.checkCreatorClosingOver(cc);
        Class<Actor> ac = Actor.class;
        Class<Creator> coc = Creator.class;
        Type type = Reflect$.MODULE$.findMarker(cc, coc);
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type type2 = (Type)ArrayOps$.MODULE$.head$extension(Predef$.MODULE$.refArrayOps(parameterizedType.getActualTypeArguments()));
            if (type2 instanceof Class) {
                Class clazz2;
                clazz = clazz2 = (Class)type2;
            } else {
                if (!(type2 instanceof TypeVariable)) throw new IllegalArgumentException(new StringBuilder(45).append("unsupported type found in Creator argument [").append(type2).append("]").toString());
                TypeVariable typeVariable = (TypeVariable)type2;
                clazz = (Class)ArrayOps$.MODULE$.collectFirst$extension(Predef$.MODULE$.refArrayOps(typeVariable.getBounds()), new Serializable(null, ac){
                    private static final long serialVersionUID = 0L;
                    private final Class ac$1;

                    public final <A1 extends Type, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                        Class clazz;
                        A1 A1 = x1;
                        if (A1 instanceof Class && this.ac$1.isAssignableFrom(clazz = (Class)A1)) {
                            Class clazz2 = clazz;
                            Class clazz3 = this.ac$1;
                            if (clazz2 == null ? clazz3 != null : !clazz2.equals(clazz3)) {
                                return (B1)clazz;
                            }
                        }
                        return function1.apply(x1);
                    }

                    public final boolean isDefinedAt(Type x1) {
                        Class clazz;
                        Type type = x1;
                        if (type instanceof Class && this.ac$1.isAssignableFrom(clazz = (Class)type)) {
                            Class clazz2 = clazz;
                            Class clazz3 = this.ac$1;
                            if (clazz2 == null ? clazz3 != null : !clazz2.equals(clazz3)) {
                                return true;
                            }
                        }
                        return false;
                    }
                    {
                        this.ac$1 = ac$1;
                    }
                }).getOrElse((Function0<Class> & Serializable)() -> ac);
            }
        } else {
            Class clazz3;
            if (!(type instanceof Class)) throw new IllegalArgumentException(new StringBuilder(17).append("unexpected type: ").append(type).toString());
            Class clazz4 = clazz3 = (Class)type;
            Class<Creator> clazz5 = coc;
            if (clazz4 != null ? !clazz4.equals(clazz5) : clazz5 != null) throw new IllegalArgumentException(new StringBuilder(17).append("unexpected type: ").append(type).toString());
            throw new IllegalArgumentException("erased Creator types (e.g. lambdas) are unsupported, use Props.create(actorClass, creator) instead");
        }
        Class actorClass = clazz;
        return this.create(CreatorConsumer.class, ScalaRunTime$.MODULE$.wrapRefArray(new Object[]{actorClass, creator}));
    }

    public static /* synthetic */ Props create$(AbstractProps $this, Class actorClass, Creator creator) {
        return $this.create(actorClass, creator);
    }

    default public <T extends Actor> Props create(Class<T> actorClass, Creator<T> creator) {
        this.checkCreatorClosingOver(creator.getClass());
        return this.create(CreatorConsumer.class, ScalaRunTime$.MODULE$.wrapRefArray(new Object[]{actorClass, creator}));
    }

    private void checkCreatorClosingOver(Class<?> clazz) {
        Class<?> enclosingClass = clazz.getEnclosingClass();
        if (enclosingClass != null && !this.hasValidConstructor$1(clazz, enclosingClass)) {
            throw new IllegalArgumentException("cannot use non-static local Creator to create actors; make it static (e.g. local to a static method) or top-level");
        }
    }

    private boolean loop$1(int i, Constructor[] declaredConstructors$1) {
        while (i != declaredConstructors$1.length) {
            if (declaredConstructors$1[i].getParameterCount() == 0) {
                return true;
            }
            ++i;
        }
        return false;
    }

    private boolean hasDeclaredConstructorWithEmptyParams$1(Constructor[] declaredConstructors) {
        return this.loop$1(0, declaredConstructors);
    }

    private boolean loop$2(int i, Constructor[] declaredConstructors$2, Class enclosingClass$1) {
        while (i != declaredConstructors$2.length) {
            Constructor c = declaredConstructors$2[i];
            if (c.getParameterCount() >= 1) {
                Class<?> clazz = c.getParameterTypes()[0];
                Class clazz2 = enclosingClass$1;
                if (!(clazz != null ? !clazz.equals(clazz2) : clazz2 != null)) {
                    return true;
                }
            }
            ++i;
        }
        return false;
    }

    private boolean hasDeclaredConstructorWithEnclosingClassParam$1(Constructor[] declaredConstructors, Class enclosingClass$1) {
        return this.loop$2(0, declaredConstructors, enclosingClass$1);
    }

    private boolean hasValidConstructor$1(Class clazz$1, Class enclosingClass$1) {
        int constructorsLength = clazz$1.getConstructors().length;
        if (constructorsLength > 0) {
            return true;
        }
        Constructor[] decl = clazz$1.getDeclaredConstructors();
        return this.hasDeclaredConstructorWithEmptyParams$1(decl) || !this.hasDeclaredConstructorWithEnclosingClassParam$1(decl, enclosingClass$1);
    }

    public static void $init$(AbstractProps $this) {
    }
}

