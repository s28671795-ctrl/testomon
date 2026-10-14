/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import scala.Option;
import scala.Some;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$cache$;
import scala.reflect.Manifest$;
import scala.reflect.ManifestFactory;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.runtime.Null$;

public final class ClassTag$
implements Serializable {
    public static final ClassTag$ MODULE$ = new ClassTag$();
    public static final Class<Object> scala$reflect$ClassTag$$ObjectTYPE = Object.class;
    public static final Class<Nothing$> scala$reflect$ClassTag$$NothingTYPE = Nothing$.class;
    public static final Class<Null$> scala$reflect$ClassTag$$NullTYPE = Null$.class;
    private static final ManifestFactory.ByteManifest Byte = Manifest$.MODULE$.Byte();
    private static final ManifestFactory.ShortManifest Short = Manifest$.MODULE$.Short();
    private static final ManifestFactory.CharManifest Char = Manifest$.MODULE$.Char();
    private static final ManifestFactory.IntManifest Int = Manifest$.MODULE$.Int();
    private static final ManifestFactory.LongManifest Long = Manifest$.MODULE$.Long();
    private static final ManifestFactory.FloatManifest Float = Manifest$.MODULE$.Float();
    private static final ManifestFactory.DoubleManifest Double = Manifest$.MODULE$.Double();
    private static final ManifestFactory.BooleanManifest Boolean = Manifest$.MODULE$.Boolean();
    private static final ManifestFactory.UnitManifest Unit = Manifest$.MODULE$.Unit();
    private static final ClassTag<Object> Any = Manifest$.MODULE$.Any();
    private static final ClassTag<Object> Object = Manifest$.MODULE$.Object();
    private static final ClassTag<Object> AnyVal = Manifest$.MODULE$.AnyVal();
    private static final ClassTag<Object> AnyRef = Manifest$.MODULE$.AnyRef();
    private static final ClassTag<Nothing$> Nothing = Manifest$.MODULE$.Nothing();
    private static final ClassTag<Null$> Null = Manifest$.MODULE$.Null();
    private static final boolean cacheDisabled = java.lang.Boolean.getBoolean("scala.reflect.classtag.cache.disable");

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

    public ClassTag<Object> Any() {
        return Any;
    }

    public ClassTag<Object> Object() {
        return Object;
    }

    public ClassTag<Object> AnyVal() {
        return AnyVal;
    }

    public ClassTag<Object> AnyRef() {
        return AnyRef;
    }

    public ClassTag<Nothing$> Nothing() {
        return Nothing;
    }

    public ClassTag<Null$> Null() {
        return Null;
    }

    private boolean cacheDisabled() {
        return cacheDisabled;
    }

    public <T> ClassTag<T> apply(Class<?> runtimeClass1) {
        if (this.cacheDisabled()) {
            return ClassTag$cache$.MODULE$.computeTag(runtimeClass1);
        }
        ClassTag<?> tag = (ClassTag<?>)((WeakReference)ClassTag$cache$.MODULE$.get(runtimeClass1)).get();
        if (tag == null) {
            ClassTag$cache$.MODULE$.remove(runtimeClass1);
            tag = ClassTag$cache$.MODULE$.computeTag(runtimeClass1);
        }
        return tag;
    }

    public <T> Option<Class<?>> unapply(ClassTag<T> ctag) {
        return new Some(ctag.runtimeClass());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ClassTag$.class);
    }

    private ClassTag$() {
    }
}

