/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import scala.MatchError;
import scala.None$;
import scala.Some;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ClassManifestFactory;
import scala.reflect.ClassTag;
import scala.reflect.ClassTypeManifest;
import scala.reflect.Manifest;
import scala.reflect.ManifestFactory;
import scala.reflect.ManifestFactory$;
import scala.reflect.NoManifest$;
import scala.reflect.OptManifest;
import scala.runtime.Nothing$;
import scala.runtime.Null$;

public final class ClassManifestFactory$ {
    public static final ClassManifestFactory$ MODULE$ = new ClassManifestFactory$();
    private static final ManifestFactory.ByteManifest Byte = ManifestFactory$.MODULE$.Byte();
    private static final ManifestFactory.ShortManifest Short = ManifestFactory$.MODULE$.Short();
    private static final ManifestFactory.CharManifest Char = ManifestFactory$.MODULE$.Char();
    private static final ManifestFactory.IntManifest Int = ManifestFactory$.MODULE$.Int();
    private static final ManifestFactory.LongManifest Long = ManifestFactory$.MODULE$.Long();
    private static final ManifestFactory.FloatManifest Float = ManifestFactory$.MODULE$.Float();
    private static final ManifestFactory.DoubleManifest Double = ManifestFactory$.MODULE$.Double();
    private static final ManifestFactory.BooleanManifest Boolean = ManifestFactory$.MODULE$.Boolean();
    private static final ManifestFactory.UnitManifest Unit = ManifestFactory$.MODULE$.Unit();
    private static final Manifest<Object> Any = ManifestFactory$.MODULE$.Any();
    private static final Manifest<Object> Object = ManifestFactory$.MODULE$.Object();
    private static final Manifest<Object> AnyVal = ManifestFactory$.MODULE$.AnyVal();
    private static final Manifest<Nothing$> Nothing = ManifestFactory$.MODULE$.Nothing();
    private static final Manifest<Null$> Null = ManifestFactory$.MODULE$.Null();

    public ManifestFactory.ByteManifest Byte() {
        return Byte;
    }

    public ManifestFactory.ShortManifest Short() {
        return Short;
    }

    public ManifestFactory.CharManifest Char() {
        return Char;
    }

    public ManifestFactory.IntManifest Int() {
        return Int;
    }

    public ManifestFactory.LongManifest Long() {
        return Long;
    }

    public ManifestFactory.FloatManifest Float() {
        return Float;
    }

    public ManifestFactory.DoubleManifest Double() {
        return Double;
    }

    public ManifestFactory.BooleanManifest Boolean() {
        return Boolean;
    }

    public ManifestFactory.UnitManifest Unit() {
        return Unit;
    }

    public Manifest<Object> Any() {
        return Any;
    }

    public Manifest<Object> Object() {
        return Object;
    }

    public Manifest<Object> AnyVal() {
        return AnyVal;
    }

    public Manifest<Nothing$> Nothing() {
        return Nothing;
    }

    public Manifest<Null$> Null() {
        return Null;
    }

    public <T> ClassTag<T> fromClass(Class<T> clazz) {
        Class<Byte> clazz2 = java.lang.Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            return this.Byte();
        }
        Class<Short> clazz3 = java.lang.Short.TYPE;
        if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
            return this.Short();
        }
        Class<Character> clazz4 = Character.TYPE;
        if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
            return this.Char();
        }
        Class<Integer> clazz5 = Integer.TYPE;
        if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
            return this.Int();
        }
        Class<Long> clazz6 = java.lang.Long.TYPE;
        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
            return this.Long();
        }
        Class<Float> clazz7 = java.lang.Float.TYPE;
        if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
            return this.Float();
        }
        Class<Double> clazz8 = java.lang.Double.TYPE;
        if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
            return this.Double();
        }
        Class<Boolean> clazz9 = java.lang.Boolean.TYPE;
        if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
            return this.Boolean();
        }
        Class<Void> clazz10 = Void.TYPE;
        if (!(clazz10 != null ? !clazz10.equals(clazz) : clazz != null)) {
            return this.Unit();
        }
        return new ClassTypeManifest(None$.MODULE$, clazz, Nil$.MODULE$);
    }

    public <T> Manifest<T> singleType(Object value) {
        return new ManifestFactory.SingletonTypeManifest(value);
    }

    public <T> ClassTag<T> classType(Class<?> clazz) {
        return new ClassTypeManifest(None$.MODULE$, clazz, Nil$.MODULE$);
    }

    public <T> ClassTag<T> classType(Class<?> clazz, OptManifest<?> arg1, Seq<OptManifest<?>> args2) {
        List list = args2.toList();
        if (list == null) {
            throw null;
        }
        List $colon$colon_this = list;
        Object var4_4 = null;
        return new ClassTypeManifest(None$.MODULE$, clazz, new $colon$colon(arg1, $colon$colon_this));
    }

    public <T> ClassTag<T> classType(OptManifest<?> prefix, Class<?> clazz, Seq<OptManifest<?>> args2) {
        return new ClassTypeManifest(new Some(prefix), clazz, args2.toList());
    }

    public <T> ClassTag<Object> arrayType(OptManifest<?> arg) {
        if (NoManifest$.MODULE$.equals(arg)) {
            return this.Object();
        }
        if (arg instanceof ClassTag) {
            return ((ClassTag)arg).arrayManifest();
        }
        throw new MatchError(arg);
    }

    public <T> ClassTag<T> abstractType(OptManifest<?> prefix, String name, Class<?> clazz, Seq<OptManifest<?>> args2) {
        return new ClassManifestFactory.AbstractTypeClassManifest(prefix, name, clazz, Nil$.MODULE$);
    }

    public <T> ClassTag<T> abstractType(OptManifest<?> prefix, String name, ClassTag<?> upperbound, Seq<OptManifest<?>> args2) {
        return new ClassManifestFactory.AbstractTypeClassManifest(prefix, name, upperbound.runtimeClass(), Nil$.MODULE$);
    }

    private ClassManifestFactory$() {
    }
}

