/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.time.Instant;
import java.util.Deque;
import java.util.LinkedList;

public class PooledConnectionStatistics {
    private final int size;
    private final Deque<Instant> availableStats;
    private final Deque<Instant> activeStats;

    public PooledConnectionStatistics(int i) {
        this.size = i;
        this.availableStats = new LinkedList<Instant>(){

            @Override
            public boolean add(Instant e) {
                if (PooledConnectionStatistics.this.size < 1) {
                    return false;
                }
                boolean b = super.add(e);
                while (this.size() > PooledConnectionStatistics.this.size) {
                    this.remove();
                }
                return b;
            }
        };
        this.activeStats = new LinkedList<Instant>(){

            @Override
            public boolean add(Instant e) {
                if (PooledConnectionStatistics.this.size < 1) {
                    return false;
                }
                boolean b = super.add(e);
                while (this.size() > PooledConnectionStatistics.this.size) {
                    this.remove();
                }
                return b;
            }
        };
    }

    public Deque<Instant> getAvailableStats() {
        return this.availableStats;
    }

    public Instant getLastAvailableStat() {
        return this.availableStats.peekLast();
    }

    public synchronized void addAvailableStat() {
        this.availableStats.add(Instant.now());
    }

    public Deque<Instant> getActiveStats() {
        return this.activeStats;
    }

    public Instant getLastActiveStat() {
        return this.activeStats.peekLast();
    }

    public synchronized void addActiveStat() {
        this.activeStats.add(Instant.now());
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "size=" + this.size + "]";
    }
}

