/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.util.Collections$EmptyImmutableSeq$;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import scala.Function1;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.VectorBuilder;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

public final class Util$ {
    public static final Util$ MODULE$ = new Util$();

    public <T> ClassTag<T> classTag(Class<T> clazz) {
        return ClassTag$.MODULE$.apply(clazz);
    }

    public Seq<Class<?>> immutableSeq(Class<?>[] arr) {
        return this.immutableSeq((Object)arr);
    }

    public <T> Seq<T> immutableSeq(Object arr) {
        if (arr != null && ScalaRunTime$.MODULE$.array_length(arr) > 0) {
            return ArrayOps$.MODULE$.toIndexedSeq$extension(Predef$.MODULE$.genericArrayOps(arr));
        }
        return Nil$.MODULE$;
    }

    public <T> Seq<T> immutableSeq(Iterable<T> iterable) {
        Iterable<T> iterable2 = iterable;
        if (iterable2 instanceof Seq) {
            Iterable<T> iterable3 = iterable2;
            return (Seq)((Object)iterable3);
        }
        Iterator<T> i = iterable2.iterator();
        if (i.hasNext()) {
            VectorBuilder<T> builder = new VectorBuilder<T>();
            do {
                builder.$plus$eq(i.next());
            } while (i.hasNext());
            return builder.result();
        }
        return Collections$EmptyImmutableSeq$.MODULE$;
    }

    public <T> Seq<T> immutableSingletonSeq(T value) {
        T t2 = value;
        return Nil$.MODULE$.$colon$colon(t2);
    }

    public <T> List<T> javaArrayList(Seq<T> seq) {
        int size = seq.size();
        ArrayList l = new ArrayList(size);
        seq.foreach((Function1<Object, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(l.add(x$1)));
        return l;
    }

    public <T> IndexedSeq<T> immutableIndexedSeq(Iterable<T> iterable) {
        return this.immutableSeq(iterable).toVector();
    }

    public <T> Option<T> option(Optional<T> jOption) {
        return Option$.MODULE$.apply(jOption.orElse(null));
    }

    private Util$() {
    }
}

