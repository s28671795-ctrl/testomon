/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.Attributes;
import akka.stream.impl.PushAttributes;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class PushAttributes$
extends AbstractFunction1<Attributes, PushAttributes>
implements Serializable {
    public static final PushAttributes$ MODULE$ = new PushAttributes$();

    @Override
    public final String toString() {
        return "PushAttributes";
    }

    @Override
    public PushAttributes apply(Attributes attributes) {
        return new PushAttributes(attributes);
    }

    public Option<Attributes> unapply(PushAttributes x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Attributes>(x$0.attributes());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PushAttributes$.class);
    }

    private PushAttributes$() {
    }
}

