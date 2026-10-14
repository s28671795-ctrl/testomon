/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.WildcardTree;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.collection.immutable.HashMap;
import scala.collection.immutable.HashMap$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class WildcardTree$
implements Serializable {
    public static final WildcardTree$ MODULE$ = new WildcardTree$();
    private static final WildcardTree<Nothing$> empty = new WildcardTree<Nothing$>(MODULE$.$lessinit$greater$default$1(), MODULE$.$lessinit$greater$default$2(), MODULE$.$lessinit$greater$default$3());

    public <T> None$ $lessinit$greater$default$1() {
        return None$.MODULE$;
    }

    public <T> HashMap<String, WildcardTree<T>> $lessinit$greater$default$2() {
        return (HashMap)HashMap$.MODULE$.apply((Seq)Nil$.MODULE$);
    }

    public <T> HashMap<String, WildcardTree<T>> $lessinit$greater$default$3() {
        return (HashMap)HashMap$.MODULE$.apply((Seq)Nil$.MODULE$);
    }

    private WildcardTree<Nothing$> empty() {
        return empty;
    }

    public <T> WildcardTree<T> apply() {
        return this.empty();
    }

    public <T> None$ apply$default$1() {
        return None$.MODULE$;
    }

    public <T> HashMap<String, WildcardTree<T>> apply$default$2() {
        return (HashMap)HashMap$.MODULE$.apply((Seq)Nil$.MODULE$);
    }

    public <T> HashMap<String, WildcardTree<T>> apply$default$3() {
        return (HashMap)HashMap$.MODULE$.apply((Seq)Nil$.MODULE$);
    }

    public <T> WildcardTree<T> apply(Option<T> data, Map<String, WildcardTree<T>> children, Map<String, WildcardTree<T>> wildcardSuffixChildren) {
        return new WildcardTree<T>(data, children, wildcardSuffixChildren);
    }

    public <T> Option<Tuple3<Option<T>, Map<String, WildcardTree<T>>, Map<String, WildcardTree<T>>>> unapply(WildcardTree<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Option<T>, Map<String, WildcardTree<T>>, Map<String, WildcardTree<T>>>>(new Tuple3<Option<T>, Map<String, WildcardTree<T>>, Map<String, WildcardTree<T>>>(x$0.data(), x$0.children(), x$0.wildcardSuffixChildren()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(WildcardTree$.class);
    }

    private WildcardTree$() {
    }
}

