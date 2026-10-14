/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.httpclient;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.component.DestroyedComponentException;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.http.conn.HttpClientConnectionManager;

public class IdleConnectionSweeper
implements DestructableComponent {
    private boolean destroyed;
    private boolean createdTimer;
    private final HttpClientConnectionManager connectionManager;
    private final Timer taskTimer;
    private final TimerTask sweeper;

    public IdleConnectionSweeper(@Nonnull HttpClientConnectionManager manager, long idleTimeout, long sweepInterval) {
        this(manager, idleTimeout, sweepInterval, new Timer(true));
        this.createdTimer = true;
    }

    public IdleConnectionSweeper(@Nonnull HttpClientConnectionManager manager, final long idleTimeout, long sweepInterval, @Nonnull Timer backgroundTimer) {
        this.connectionManager = Constraint.isNotNull(manager, "HttpClientConnectionManager can not be null");
        this.taskTimer = Constraint.isNotNull(backgroundTimer, "Sweeper task timer can not be null");
        this.sweeper = new TimerTask(){

            @Override
            public void run() {
                IdleConnectionSweeper.this.connectionManager.closeIdleConnections(idleTimeout, TimeUnit.MILLISECONDS);
            }
        };
        this.taskTimer.schedule(this.sweeper, sweepInterval, sweepInterval);
    }

    public long scheduledExecutionTime() {
        if (this.isDestroyed()) {
            throw new DestroyedComponentException();
        }
        return this.sweeper.scheduledExecutionTime();
    }

    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }

    @Override
    public synchronized void destroy() {
        this.sweeper.cancel();
        if (this.createdTimer) {
            this.taskTimer.cancel();
        }
        this.destroyed = true;
    }
}

