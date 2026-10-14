/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.websocket;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsMessageContext;
import java.nio.channels.ClosedChannelException;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Predicate;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.util.StaticException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WSBroadcaster {
    private static final Logger log = LoggerFactory.getLogger(WSBroadcaster.class);
    private static final Duration SEND_TIMEOUT = Duration.ofSeconds(5L);
    private final ExecutorService sendExecutor = Executors.newVirtualThreadPerTaskExecutor();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, Thread.ofVirtual().factory());
    private final Set<WsContext> allSessions = Collections.newSetFromMap(new ConcurrentHashMap());
    protected final UsersManager usersManager;

    public WSBroadcaster(UsersManager usersManager, boolean setUpHeathCheckScheduler) {
        this.usersManager = usersManager;
        if (setUpHeathCheckScheduler) {
            this.setUpHeathCheckScheduler();
        }
    }

    public void init(WsConfig wsConfig) {
        wsConfig.onConnect(this::subscribe);
        wsConfig.onClose(this::unsubscribe);
        wsConfig.onMessage(this::onMessage);
        wsConfig.onError(this::onError);
    }

    public void broadcast(Object message) {
        this.broadcast((User u) -> true, (User u) -> message);
    }

    public void broadcast(@NotNull Object message, @NotNull Predicate<User> accessFilter) {
        this.broadcast(accessFilter, (User u) -> message);
    }

    public void broadcast(@NotNull Predicate<User> accessFilter, @NotNull Function<User, Object> messageFactory) {
        for (WsContext session : this.allSessions) {
            User user;
            if (!this.isOpen(session)) {
                this.allSessions.remove(session);
                continue;
            }
            try {
                user = this.usersManager.getUser(session);
            }
            catch (Exception e) {
                log.warn("Failed to resolve user for WS session, removing session", e);
                this.allSessions.remove(session);
                continue;
            }
            if (user == null || !accessFilter.test(user)) continue;
            this.sendWithTimeout(session, messageFactory.apply(user));
        }
    }

    private boolean isOpen(WsContext session) {
        try {
            return session.session.isOpen();
        }
        catch (Exception e) {
            return false;
        }
    }

    protected void send(WsContext session, Object message) {
        try {
            this.sendWithTimeout(session, message);
        }
        catch (Exception e) {
            if (e.getCause() instanceof ClosedChannelException) {
                return;
            }
            log.error("Error sending message to WS session", e);
        }
    }

    private void sendWithTimeout(WsContext session, Object message) {
        CompletableFuture<Void> f = CompletableFuture.runAsync(() -> session.send(message), this.sendExecutor);
        f.orTimeout(SEND_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS).exceptionally(ex -> {
            Throwable cause;
            Throwable throwable = cause = ex instanceof CompletionException && ex.getCause() != null ? ex.getCause() : ex;
            if (cause instanceof TimeoutException) {
                log.warn("WS send timeout ({} ms), removing session", (Object)SEND_TIMEOUT.toMillis());
                this.safeCloseAndRemove(session);
                return null;
            }
            if (cause instanceof ClosedChannelException || cause.getCause() instanceof ClosedChannelException) {
                this.allSessions.remove(session);
                return null;
            }
            if (cause instanceof EofException || cause.getCause() instanceof EofException) {
                this.allSessions.remove(session);
                return null;
            }
            if (cause instanceof StaticException || cause.getCause() instanceof StaticException) {
                log.error("Error sending message to WS session - \"{}\", removing session", (Object)cause.getMessage());
                this.safeCloseAndRemove(session);
                return null;
            }
            log.error("Error sending message to WS session, removing session", cause);
            this.safeCloseAndRemove(session);
            return null;
        });
    }

    private void safeCloseAndRemove(WsContext session) {
        this.allSessions.remove(session);
        try {
            session.session.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void subscribe(WsConnectContext ctx) {
        this.allSessions.add(ctx);
    }

    public void unsubscribe(WsContext ctx) {
        this.allSessions.remove(ctx);
    }

    public void onMessage(WsMessageContext ctx) {
    }

    public void onError(WsErrorContext ctx) {
        if (ctx.error() instanceof ClosedChannelException) {
            return;
        }
        if (ctx.error() instanceof EofException) {
            return;
        }
        if (ctx.error() instanceof StaticException) {
            log.error("Error sending message to WS session - \"{}\"", (Object)ctx.error().getMessage());
            return;
        }
        log.error("Error in WS connection", ctx.error());
        this.unsubscribe(ctx);
    }

    private void setUpHeathCheckScheduler() {
        this.scheduler.scheduleAtFixedRate(() -> this.broadcast("health_check", (User u) -> true), 10L, 10L, TimeUnit.SECONDS);
    }
}

