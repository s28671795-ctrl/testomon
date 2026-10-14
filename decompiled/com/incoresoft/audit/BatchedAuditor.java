/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.audit;

import com.google.inject.Inject;
import com.incoresoft.audit.AuditEventsFactory;
import com.incoresoft.audit.BasicAuditor;
import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.model.user.User;
import io.javalin.http.Context;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BatchedAuditor
implements Auditor {
    private static final Duration DEFAULT_FLUSH_INTERVAL = Duration.ofSeconds(1L);
    private final BasicAuditor basicAuditor;
    private final AuditEventsFactory eventsFactory;
    private final ScheduledExecutorService scheduler;
    private final Queue<AuditEvent> auditQueue = new ConcurrentLinkedQueue<AuditEvent>();
    private final AtomicBoolean isBatchScheduled = new AtomicBoolean(false);

    @Inject
    public BatchedAuditor(BasicAuditor basicAuditor, AuditEventsFactory eventsFactory) {
        this(basicAuditor, eventsFactory, Executors.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory()));
    }

    @Override
    public void audit(@NotNull AuditEvent auditEvent) {
        this.auditQueue.add(auditEvent);
        if (!this.isBatchScheduled.getAndSet(true)) {
            this.scheduler.schedule(this::flushAuditEvents, DEFAULT_FLUSH_INTERVAL.toMillis(), TimeUnit.MILLISECONDS);
        }
    }

    private void flushAuditEvents() {
        try {
            ArrayList<AuditEvent> batch = new ArrayList<AuditEvent>();
            while (!this.auditQueue.isEmpty()) {
                batch.add(this.auditQueue.poll());
            }
            if (!batch.isEmpty()) {
                this.basicAuditor.audit(batch);
            }
        }
        finally {
            this.isBatchScheduled.set(false);
        }
    }

    @Override
    public void audit(Context ctx, User user, AuditEventAction action, String sourceId) {
        AuditEvent event = this.eventsFactory.create(ctx, user, action, sourceId);
        this.audit(event);
    }

    @Override
    public void audit(@Nullable Context ctx, User user, AuditEventAction action, String sourceId, Object dto) {
        AuditEvent event = this.eventsFactory.create(ctx, user, action, sourceId, dto);
        this.audit(event);
    }

    @Override
    public void audit(Context context, AuditEventAction auditEventAction, String sourceId) {
        AuditEvent event = this.eventsFactory.create(context, auditEventAction, sourceId);
        this.audit(event);
    }

    @Override
    public void audit(Context context, AuditEventAction auditEventAction, String sourceId, Object dto) {
        AuditEvent event = this.eventsFactory.create(context, auditEventAction, sourceId, dto);
        this.audit(event);
    }

    BatchedAuditor(BasicAuditor basicAuditor, AuditEventsFactory eventsFactory, ScheduledExecutorService scheduler) {
        this.basicAuditor = basicAuditor;
        this.eventsFactory = eventsFactory;
        this.scheduler = scheduler;
    }
}

