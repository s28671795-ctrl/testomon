/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.WildcardIndex;
import akka.util.WildcardTree;
import akka.util.WildcardTree$;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.ModuleSerializationProxy;

public final class WildcardIndex$
implements Serializable {
    public static final WildcardIndex$ MODULE$ = new WildcardIndex$();

    public <T> WildcardTree<T> $lessinit$greater$default$1() {
        return WildcardTree$.MODULE$.apply();
    }

    public <T> WildcardTree<T> $lessinit$greater$default$2() {
        return WildcardTree$.MODULE$.apply();
    }

    public final String toString() {
        return "WildcardIndex";
    }

    public <T> WildcardIndex<T> apply(WildcardTree<T> wildcardTree, WildcardTree<T> doubleWildcardTree) {
        return new WildcardIndex<T>(wildcardTree, doubleWildcardTree);
    }

    public <T> WildcardTree<T> apply$default$1() {
        return WildcardTree$.MODULE$.apply();
    }

    public <T> WildcardTree<T> apply$default$2() {
        return WildcardTree$.MODULE$.apply();
    }

    public <T> Option<Tuple2<WildcardTree<T>, WildcardTree<T>>> unapply(WildcardIndex<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<WildcardTree<T>, WildcardTree<T>>>(new Tuple2<WildcardTree<T>, WildcardTree<T>>(x$0.wildcardTree(), x$0.doubleWildcardTree()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(WildcardIndex$.class);
    }

    private WildcardIndex$() {
    }
}

