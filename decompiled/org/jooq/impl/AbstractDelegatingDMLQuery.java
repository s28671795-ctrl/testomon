/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.concurrent.Flow;
import org.jooq.Record;
import org.jooq.RowCountQuery;
import org.jooq.impl.AbstractDMLQuery;
import org.jooq.impl.AbstractDelegatingQuery;
import org.reactivestreams.Subscriber;

abstract class AbstractDelegatingDMLQuery<R extends Record, Q extends AbstractDMLQuery<R>>
extends AbstractDelegatingQuery<R, Q>
implements RowCountQuery {
    AbstractDelegatingDMLQuery(Q delegate) {
        super(delegate);
    }

    @Override
    public final void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        ((AbstractDMLQuery)this.getDelegate()).subscribe(subscriber);
    }

    @Override
    public final void subscribe(Subscriber<? super Integer> subscriber) {
        ((AbstractDMLQuery)this.getDelegate()).subscribe(subscriber);
    }
}

