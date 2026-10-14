/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import scala.None$;
import scala.Some;
import scala.collection.IterableOnce;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.AnyValManifest;
import scala.reflect.ClassTag$;
import scala.reflect.Manifest;
import scala.reflect.ManifestFactory;
import scala.runtime.Nothing$;
import scala.runtime.Null$;
import scala.runtime.ScalaRunTime$;

public final class ManifestFactory$ {
    public static final ManifestFactory$ MODULE$ = new ManifestFactory$();
    private static final ManifestFactory.ByteManifest Byte = new ManifestFactory.ByteManifest();
    private static final ManifestFactory.ShortManifest Short = new ManifestFactory.ShortManifest();
    private static final ManifestFactory.CharManifest Char = new ManifestFactory.CharManifest();
    private static final ManifestFactory.IntManifest Int = new ManifestFactory.IntManifest();
    private static final ManifestFactory.LongManifest Long = new ManifestFactory.LongManifest();
    private static final ManifestFactory.FloatManifest Float = new ManifestFactory.FloatManifest();
    private static final ManifestFactory.DoubleManifest Double = new ManifestFactory.DoubleManifest();
    private static final ManifestFactory.BooleanManifest Boolean = new ManifestFactory.BooleanManifest();
    private static final ManifestFactory.UnitManifest Unit = new ManifestFactory.UnitManifest();
    public static final Class<Object> scala$reflect$ManifestFactory$$ObjectTYPE = Object.class;
    public static final Class<Nothing$> scala$reflect$ManifestFactory$$NothingTYPE = Nothing$.class;
    public static final Class<Null$> scala$reflect$ManifestFactory$$NullTYPE = Null$.class;
    private static final Manifest<Object> Any = new ManifestFactory.AnyManifest();
    private static final Manifest<Object> Object = new ManifestFactory.ObjectManifest();
    private static final Manifest<Object> AnyRef = MODULE$.Object();
    private static final Manifest<Object> AnyVal = new ManifestFactory.AnyValPhantomManifest();
    private static final Manifest<Null$> Null = new ManifestFactory.NullManifest();
    private static final Manifest<Nothing$> Nothing = new ManifestFactory.NothingManifest();

    /*
     * WARNING - void declaration
     */
    public List<AnyValManifest<?>> valueManifests() {
        void apply_elems;
        ArraySeq<AnyValManifest> arraySeq = ScalaRunTime$.MODULE$.wrapRefArray(new AnyValManifest[]{this.Byte(), this.Short(), this.Char(), this.Int(), this.Long(), this.Float(), this.Double(), this.Boolean(), this.Unit()});
        if (package$.MODULE$.List() == null) {
            throw null;
        }
        return Nil$.MODULE$.prependedAll((IterableOnce)apply_elems);
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
        List list = args2.toList();
        if (list == null) {
            throw null;
        }
        List $colon$colon_this = list;
        Object var4_4 = null;
        return new ManifestFactory.ClassTypeManifest(None$.MODULE$, clazz, new $colon$colon(arg1, $colon$colon_this));
    }

    public <T> Manifest<T> classType(Manifest<?> prefix, Class<?> clazz, Seq<Manifest<?>> args2) {
        return new ManifestFactory.ClassTypeManifest(new Some(prefix), clazz, args2.toList());
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
        return new ManifestFactory.IntersectionTypeManifest((Manifest[])parents.toArray(ClassTag$.MODULE$.apply(Manifest.class)));
    }

    private ManifestFactory$() {
    }
}

