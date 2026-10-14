/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers;

import com.google.inject.Singleton;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InFlightGpuReservations {
    public static final Duration TTL = Duration.ofSeconds(90L);
    private final Duration ttl;
    private final ConcurrentHashMap<Integer, Reservation> byAnalyticsId = new ConcurrentHashMap();

    public InFlightGpuReservations() {
        this(TTL);
    }

    InFlightGpuReservations(Duration ttl) {
        this.ttl = ttl;
    }

    public void record(int analyticsId, String serverId, int gpuId, double gb) {
        this.byAnalyticsId.put(analyticsId, new Reservation(analyticsId, serverId, gpuId, gb, Instant.now()));
    }

    public void clear(int analyticsId) {
        this.byAnalyticsId.remove(analyticsId);
    }

    public List<Reservation> reservationsFor(String serverId) {
        Instant cutoff = Instant.now().minus(this.ttl);
        ArrayList<Reservation> result = new ArrayList<Reservation>();
        for (Map.Entry<Integer, Reservation> entry : this.byAnalyticsId.entrySet()) {
            Reservation reservation = entry.getValue();
            if (reservation.placedAt().isBefore(cutoff)) {
                this.byAnalyticsId.remove(entry.getKey(), reservation);
                continue;
            }
            if (!reservation.serverId().equals(serverId)) continue;
            result.add(reservation);
        }
        return result;
    }

    public int size() {
        return this.byAnalyticsId.size();
    }

    public record Reservation(int analyticsId, String serverId, int gpuId, double gb, Instant placedAt) {
    }
}

