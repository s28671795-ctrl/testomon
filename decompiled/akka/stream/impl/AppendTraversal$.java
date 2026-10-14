/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.AppendTraversal;
import akka.stream.impl.Traversal;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class AppendTraversal$
extends AbstractFunction1<Traversal, AppendTraversal>
implements Serializable {
    public static final AppendTraversal$ MODULE$ = new AppendTraversal$();

    @Override
    public final String toString() {
        return "AppendTraversal";
    }

    @Override
    public AppendTraversal apply(Traversal traversal) {
        return new AppendTraversal(traversal);
    }

    public Option<Traversal> unapply(AppendTraversal x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Traversal>(x$0.traversal());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AppendTraversal$.class);
    }

    private AppendTraversal$() {
    }
}

