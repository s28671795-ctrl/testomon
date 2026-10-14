/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.EventListener;
import java.util.function.Consumer;
import org.jooq.VisitContext;
import org.jooq.impl.CallbackVisitListener;

public interface VisitListener
extends EventListener {
    default public void clauseStart(VisitContext context) {
    }

    default public void clauseEnd(VisitContext context) {
    }

    default public void visitStart(VisitContext context) {
    }

    default public void visitEnd(VisitContext context) {
    }

    public static CallbackVisitListener onClauseStart(Consumer<? super VisitContext> onClauseStart) {
        return new CallbackVisitListener().onClauseStart(onClauseStart);
    }

    public static CallbackVisitListener onClauseEnd(Consumer<? super VisitContext> onClauseEnd) {
        return new CallbackVisitListener().onClauseEnd(onClauseEnd);
    }

    public static CallbackVisitListener onVisitStart(Consumer<? super VisitContext> onVisitStart) {
        return new CallbackVisitListener().onVisitStart(onVisitStart);
    }

    public static CallbackVisitListener onVisitEnd(Consumer<? super VisitContext> onVisitEnd) {
        return new CallbackVisitListener().onVisitEnd(onVisitEnd);
    }
}

