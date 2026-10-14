/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.concurrent.Map;
import scala.collection.convert.ToScalaImplicits;
import scala.collection.mutable.Buffer;

public final class ImplicitConversionsToScala$
implements ToScalaImplicits {
    public static final ImplicitConversionsToScala$ MODULE$ = new ImplicitConversionsToScala$();

    @Override
    public <A> Iterator<A> iterator$u0020asScala(java.util.Iterator<A> it) {
        return ToScalaImplicits.iterator$u0020asScala$(this, it);
    }

    @Override
    public <A> Iterator<A> enumeration$u0020AsScalaIterator(Enumeration<A> i) {
        return ToScalaImplicits.enumeration$u0020AsScalaIterator$(this, i);
    }

    @Override
    public <A> Iterable<A> iterable$u0020AsScalaIterable(java.lang.Iterable<A> i) {
        return ToScalaImplicits.iterable$u0020AsScalaIterable$(this, i);
    }

    @Override
    public <A> Iterable<A> collection$u0020AsScalaIterable(Collection<A> i) {
        return ToScalaImplicits.collection$u0020AsScalaIterable$(this, i);
    }

    @Override
    public <A> Buffer<A> list$u0020asScalaBuffer(List<A> l) {
        return ToScalaImplicits.list$u0020asScalaBuffer$(this, l);
    }

    @Override
    public <A> scala.collection.mutable.Set<A> set$u0020asScala(Set<A> s2) {
        return ToScalaImplicits.set$u0020asScala$(this, s2);
    }

    @Override
    public <K, V> scala.collection.mutable.Map<K, V> map$u0020AsScala(java.util.Map<K, V> m4) {
        return ToScalaImplicits.map$u0020AsScala$(this, m4);
    }

    @Override
    public <K, V> Map<K, V> map$u0020AsScalaConcurrentMap(ConcurrentMap<K, V> m4) {
        return ToScalaImplicits.map$u0020AsScalaConcurrentMap$(this, m4);
    }

    @Override
    public <K, V> scala.collection.mutable.Map<K, V> dictionary$u0020AsScalaMap(Dictionary<K, V> p) {
        return ToScalaImplicits.dictionary$u0020AsScalaMap$(this, p);
    }

    @Override
    public scala.collection.mutable.Map<String, String> properties$u0020AsScalaMap(Properties p) {
        return ToScalaImplicits.properties$u0020AsScalaMap$(this, p);
    }

    private ImplicitConversionsToScala$() {
    }
}

