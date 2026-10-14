/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import java.io.Serializable;
import scala.None$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.AnyValManifest;
import scala.reflect.Manifest;
import scala.reflect.ManifestFactory;
import scala.reflect.ManifestFactory$;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.runtime.Null$;

public final class Manifest$
implements Serializable {
    public static final Manifest$ MODULE$ = new Manifest$();
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
    private static final Manifest<Object> AnyRef = ManifestFactory$.MODULE$.AnyRef();
    private static final Manifest<Object> AnyVal = ManifestFactory$.MODULE$.AnyVal();
    private static final Manifest<Null$> Null = ManifestFactory$.MODULE$.Null();
    private static final Manifest<Nothing$> Nothing = ManifestFactory$.MODULE$.Nothing();

    public List<AnyValManifest<?>> valueManifests() {
        return ManifestFactory$.MODULE$.valueManifests();
    }

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

    public Manifest<Object> AnyRef() {
        return AnyRef;
    }

    public Manifest<Object> AnyVal() {
        return AnyVal;
    }

    public Manifest<Null$> Null() {
        return Null;
    }

    public Manifest<Nothing$> Nothing() {
        return Nothing;
    }

    public <T> Manifest<T> singleType(Object value) {
        return new ManifestFactory.SingletonTypeManifest(value);
    }

    public <T> Manifest<T> classType(Class<?> clazz) {
        return new ManifestFactory.ClassTypeManifest(None$.MODULE$, clazz, Nil$.MODULE$);
    }

    public <T> Manifest<T> classType(Class<T> clazz, Manifest<?> arg1, Seq<Manifest<?>> args2) {
        return ManifestFactory$.MODULE$.classType(clazz, arg1, args2);
    }

    public <T> Manifest<T> classType(Manifest<?> prefix, Class<?> clazz, Seq<Manifest<?>> args2) {
        return ManifestFactory$.MODULE$.classType(prefix, clazz, args2);
    }

    public <T> Manifest<Object> arrayType(Manifest<?> arg) {
        return arg.arrayManifest();
    }

    public <T> Manifest<T> abstractType(Manifest<?> prefix, String name, Class<?> upperBound, Seq<Manifest<?>> args2) {
        return new ManifestFactory.AbstractTypeManifest(prefix, name, upperBound, args2);
    }

    public <T> Manifest<T> wildcardType(Manifest<?> lowerBound, Manifest<?> upperBound) {
        return new ManifestFactory.WildcardManifest(lowerBound, upperBound);
    }

    public <T> Manifest<T> intersectionType(Seq<Manifest<?>> parents) {
        return ManifestFactory$.MODULE$.intersectionType(parents);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Manifest$.class);
    }

    private Manifest$() {
    }
}

