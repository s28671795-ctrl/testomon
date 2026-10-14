/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.tools.StopWatch;

public class StopWatchListener
implements ExecuteListener {
    private final StopWatch watch = new StopWatch();

    @Override
    public void start(ExecuteContext ctx) {
        this.watch.splitTrace("Initialising");
    }

    @Override
    public void renderStart(ExecuteContext ctx) {
        this.watch.splitTrace("Rendering query");
    }

    @Override
    public void renderEnd(ExecuteContext ctx) {
        this.watch.splitTrace("Query rendered");
    }

    @Override
    public void prepareStart(ExecuteContext ctx) {
        this.watch.splitTrace("Preparing statement");
    }

    @Override
    public void prepareEnd(ExecuteContext ctx) {
        this.watch.splitTrace("Statement prepared");
    }

    @Override
    public void bindStart(ExecuteContext ctx) {
        this.watch.splitTrace("Binding variables");
    }

    @Override
    public void bindEnd(ExecuteContext ctx) {
        this.watch.splitTrace("Variables bound");
    }

    @Override
    public void executeStart(ExecuteContext ctx) {
        this.watch.splitTrace("Executing query");
    }

    @Override
    public void executeEnd(ExecuteContext ctx) {
        this.watch.splitDebug("Query executed");
    }

    @Override
    public void outStart(ExecuteContext ctx) {
        this.watch.splitDebug("Fetching out values");
    }

    @Override
    public void outEnd(ExecuteContext ctx) {
        this.watch.splitDebug("Out values fetched");
    }

    @Override
    public void fetchStart(ExecuteContext ctx) {
        this.watch.splitTrace("Fetching results");
    }

    @Override
    public void resultStart(ExecuteContext ctx) {
        this.watch.splitTrace("Fetching result");
    }

    @Override
    public void recordStart(ExecuteContext ctx) {
        this.watch.splitTrace("Fetching record");
    }

    @Override
    public void recordEnd(ExecuteContext ctx) {
        this.watch.splitTrace("Record fetched");
    }

    @Override
    public void resultEnd(ExecuteContext ctx) {
        this.watch.splitTrace("Result fetched");
    }

    @Override
    public void fetchEnd(ExecuteContext ctx) {
        this.watch.splitTrace("Results fetched");
    }

    @Override
    public void end(ExecuteContext ctx) {
        this.watch.splitDebug("Finishing");
    }

    @Override
    public void exception(ExecuteContext ctx) {
        this.watch.splitDebug("Exception");
    }

    @Override
    public void warning(ExecuteContext ctx) {
        this.watch.splitDebug("Warning");
    }
}

