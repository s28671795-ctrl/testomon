/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

public class ReconnectionPolicy {
    public static final int INFINITE = -1;
    private int maxSendRetries = 10;
    private long sendRetryDelay = 1000L;
    private int maxReconnectAttempts = -1;
    private int maxInitialConnectAttempts = -1;
    private long maximumReconnectDelay = 30000L;
    private long initialReconnectDelay = 1000L;
    private boolean useExponentialBackOff = false;
    private double backOffMultiplier = 2.0;

    public int getMaxSendRetries() {
        return this.maxSendRetries;
    }

    public void setMaxSendRetries(int maxSendRetries) {
        this.maxSendRetries = maxSendRetries;
    }

    public long getSendRetryDelay() {
        return this.sendRetryDelay;
    }

    public void setSendRetyDelay(long sendRetryDelay) {
        if (sendRetryDelay < 1000L) {
            this.sendRetryDelay = 1000L;
        }
        this.sendRetryDelay = sendRetryDelay;
    }

    public int getMaxReconnectAttempts() {
        return this.maxReconnectAttempts;
    }

    public void setMaxReconnectAttempts(int maxReconnectAttempts) {
        this.maxReconnectAttempts = maxReconnectAttempts;
    }

    public int getMaxInitialConnectAttempts() {
        return this.maxInitialConnectAttempts;
    }

    public void setMaxInitialConnectAttempts(int maxAttempts) {
        this.maxInitialConnectAttempts = maxAttempts;
    }

    public long getMaximumReconnectDelay() {
        return this.maximumReconnectDelay;
    }

    public void setMaximumReconnectDelay(long maximumReconnectDelay) {
        this.maximumReconnectDelay = maximumReconnectDelay;
    }

    public long getInitialReconnectDelay() {
        return this.initialReconnectDelay;
    }

    public void setInitialReconnectDelay(long initialReconnectDelay) {
        this.initialReconnectDelay = initialReconnectDelay;
    }

    public boolean isUseExponentialBackOff() {
        return this.useExponentialBackOff;
    }

    public void setUseExponentialBackOff(boolean useExponentialBackOff) {
        this.useExponentialBackOff = useExponentialBackOff;
    }

    public double getBackOffMultiplier() {
        return this.backOffMultiplier;
    }

    public void setBackOffMultiplier(double backOffMultiplier) {
        this.backOffMultiplier = backOffMultiplier;
    }

    public long getNextDelay(int attempt) {
        if (attempt == 0) {
            return 0L;
        }
        long nextDelay = this.initialReconnectDelay;
        if (this.useExponentialBackOff) {
            nextDelay = Math.max(this.initialReconnectDelay, nextDelay * (long)((double)(attempt - 1) * this.backOffMultiplier));
        }
        if (this.maximumReconnectDelay > 0L && nextDelay > this.maximumReconnectDelay) {
            nextDelay = this.maximumReconnectDelay;
        }
        return nextDelay;
    }
}

