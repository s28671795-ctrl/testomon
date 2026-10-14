/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools;

import org.jooq.tools.JooqLogger;

public final class StopWatch {
    private static final JooqLogger log = JooqLogger.getLogger(StopWatch.class);
    private long start;
    private long split;

    public StopWatch() {
        this.split = this.start = System.nanoTime();
    }

    public void splitTrace(String message) {
        if (log.isTraceEnabled()) {
            log.trace((Object)message, this.splitMessage(0L));
        }
    }

    public void splitTrace(String message, long thresholdNano) {
        String splitMessage;
        if (log.isTraceEnabled() && (splitMessage = this.splitMessage(thresholdNano)) != null) {
            log.trace((Object)message, splitMessage);
        }
    }

    public void splitDebug(String message) {
        if (log.isDebugEnabled()) {
            log.debug((Object)message, this.splitMessage(0L));
        }
    }

    public void splitDebug(String message, long thresholdNano) {
        String splitMessage;
        if (log.isDebugEnabled() && (splitMessage = this.splitMessage(thresholdNano)) != null) {
            log.debug((Object)message, splitMessage);
        }
    }

    public void splitInfo(String message) {
        if (log.isInfoEnabled()) {
            log.info((Object)message, this.splitMessage(0L));
        }
    }

    public void splitInfo(String message, long thresholdNano) {
        String splitMessage;
        if (log.isInfoEnabled() && (splitMessage = this.splitMessage(thresholdNano)) != null) {
            log.info((Object)message, splitMessage);
        }
    }

    public void splitWarn(String message) {
        log.warn((Object)message, this.splitMessage(0L));
    }

    public void splitWarn(String message, long thresholdNano) {
        String splitMessage = this.splitMessage(thresholdNano);
        if (splitMessage != null) {
            log.warn((Object)message, splitMessage);
        }
    }

    public long split() {
        return System.nanoTime() - this.start;
    }

    private String splitMessage(long thresholdNano) {
        long temp = this.split;
        this.split = System.nanoTime();
        long inc = this.split - temp;
        if (thresholdNano > 0L && inc < thresholdNano) {
            return null;
        }
        if (temp == this.start) {
            return "Total: " + StopWatch.format(this.split - this.start);
        }
        return "Total: " + StopWatch.format(this.split - this.start) + ", +" + StopWatch.format(inc);
    }

    public static String format(long nanoTime) {
        if (nanoTime > 60000000000L) {
            return StopWatch.formatHours(nanoTime / 1000000000L);
        }
        if (nanoTime > 1000000000L) {
            return (double)(nanoTime / 1000000L) / 1000.0 + "s";
        }
        return (double)(nanoTime / 1000L) / 1000.0 + "ms";
    }

    public static String formatHours(long seconds) {
        long s2 = seconds % 60L;
        long m4 = seconds / 60L % 60L;
        long h2 = seconds / 3600L;
        StringBuilder sb = new StringBuilder();
        if (h2 != 0L) {
            if (h2 < 10L) {
                sb.append("0");
                sb.append(h2);
                sb.append(":");
            } else {
                sb.append(h2);
                sb.append(":");
            }
        }
        if (m4 < 10L) {
            sb.append("0");
            sb.append(m4);
            sb.append(":");
        } else {
            sb.append(m4);
            sb.append(":");
        }
        if (s2 < 10L) {
            sb.append("0");
            sb.append(s2);
        } else {
            sb.append(s2);
        }
        return sb.toString();
    }
}

