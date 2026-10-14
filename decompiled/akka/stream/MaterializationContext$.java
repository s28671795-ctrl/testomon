/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Attributes;
import akka.stream.MaterializationContext;
import akka.stream.Materializer;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

public final class MaterializationContext$
extends AbstractFunction3<Materializer, Attributes, String, MaterializationContext>
implements Serializable {
    public static final MaterializationContext$ MODULE$ = new MaterializationContext$();

    @Override
    public final String toString() {
        return "MaterializationContext";
    }

    @Override
    public MaterializationContext apply(Materializer materializer, Attributes effectiveAttributes, String islandName) {
        return new MaterializationContext(materializer, effectiveAttributes, islandName);
    }

    public Option<Tuple3<Materializer, Attributes, String>> unapply(MaterializationContext x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Materializer, Attributes, String>>(new Tuple3<Materializer, Attributes, String>(x$0.materializer(), x$0.effectiveAttributes(), x$0.islandName()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MaterializationContext$.class);
    }

    private MaterializationContext$() {
    }
}

