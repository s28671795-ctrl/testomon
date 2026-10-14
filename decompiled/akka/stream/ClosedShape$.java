/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.ClosedShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.util.Collections$EmptyImmutableSeq$;
import scala.collection.immutable.Seq;

public final class ClosedShape$
extends ClosedShape {
    public static final ClosedShape$ MODULE$ = new ClosedShape$();
    private static final Seq<Inlet<?>> inlets = Collections$EmptyImmutableSeq$.MODULE$;
    private static final Seq<Outlet<?>> outlets = Collections$EmptyImmutableSeq$.MODULE$;

    @Override
    public Seq<Inlet<?>> inlets() {
        return inlets;
    }

    @Override
    public Seq<Outlet<?>> outlets() {
        return outlets;
    }

    @Override
    public ClosedShape$ deepCopy() {
        return this;
    }

    @Override
    public ClosedShape getInstance() {
        return this;
    }

    public String toString() {
        return "ClosedShape";
    }

    private ClosedShape$() {
    }
}

