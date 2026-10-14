/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.r2dbc;

import io.r2dbc.spi.Batch;
import io.r2dbc.spi.Result;
import org.jooq.tools.JooqLogger;
import org.reactivestreams.Publisher;

public class LoggingBatch
implements Batch {
    private static final JooqLogger log = JooqLogger.getLogger(LoggingBatch.class);
    private final Batch delegate;

    public LoggingBatch(Batch delegate) {
        this.delegate = delegate;
    }

    public Batch getDelegate() {
        return this.delegate;
    }

    @Override
    public Batch add(String sql) {
        if (log.isDebugEnabled()) {
            log.debug((Object)"Batch::add", sql);
        }
        this.getDelegate().add(sql);
        return this;
    }

    @Override
    public Publisher<? extends Result> execute() {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug("Batch::execute");
            }
            this.getDelegate().execute().subscribe(s2);
        };
    }
}

