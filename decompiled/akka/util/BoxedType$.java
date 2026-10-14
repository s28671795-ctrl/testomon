/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;

public final class BoxedType$ {
    public static final BoxedType$ MODULE$ = new BoxedType$();
    private static final Map<Class<?>, Class<?>> toBoxed = (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Boolean.TYPE), Boolean.class), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Byte.TYPE), Byte.class), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Character.TYPE), Character.class), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Short.TYPE), Short.class), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Integer.TYPE), Integer.class), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Long.TYPE), Long.class), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Float.TYPE), Float.class), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Double.TYPE), Double.class), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxedUnit.TYPE), BoxedUnit.class)}));

    private Map<Class<?>, Class<?>> toBoxed() {
        return toBoxed;
    }

    public final Class<?> apply(Class<?> c) {
        if (c.isPrimitive()) {
            return (Class)this.toBoxed().apply(c);
        }
        return c;
    }

    private BoxedType$() {
    }
}

