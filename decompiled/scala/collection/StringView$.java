/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.StringView;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class StringView$
extends AbstractFunction1<String, StringView>
implements Serializable {
    public static final StringView$ MODULE$ = new StringView$();

    @Override
    public final String toString() {
        return "StringView";
    }

    @Override
    public StringView apply(String s2) {
        return new StringView(s2);
    }

    public Option<String> unapply(StringView x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.s());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(StringView$.class);
    }

    private StringView$() {
    }
}

