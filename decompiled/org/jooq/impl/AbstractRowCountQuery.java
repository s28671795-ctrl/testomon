/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.ConnectionFactory;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.RowCountQuery;
import org.jooq.impl.AbstractQuery;
import org.jooq.impl.NoConnectionFactory;
import org.jooq.impl.R2DBC;
import org.reactivestreams.Subscriber;

abstract class AbstractRowCountQuery
extends AbstractQuery<Record>
implements RowCountQuery {
    AbstractRowCountQuery(Configuration configuration) {
        super(configuration);
    }

    @Override
    public final void subscribe(Subscriber<? super Integer> subscriber) {
        ConnectionFactory cf = this.configuration().connectionFactory();
        if (!(cf instanceof NoConnectionFactory)) {
            subscriber.onSubscribe(new R2DBC.QuerySubscription<Integer, AbstractRowCountQuery>(this, subscriber, (t2, u) -> new R2DBC.RowCountSubscriber((R2DBC.AbstractNonBlockingSubscription<? super Integer>)u)));
        } else {
            subscriber.onSubscribe(new R2DBC.BlockingRowCountSubscription(this, subscriber));
        }
    }
}

