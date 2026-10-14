/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.Serializable;
import java.util.Random;
import org.apache.activemq.filter.DestinationMapEntry;
import org.apache.activemq.util.IntrospectionSupport;

public class RedeliveryPolicy
extends DestinationMapEntry
implements Cloneable,
Serializable {
    public static final int NO_MAXIMUM_REDELIVERIES = -1;
    public static final int DEFAULT_MAXIMUM_REDELIVERIES = 6;
    private static Random randomNumberGenerator;
    protected double collisionAvoidanceFactor = 0.15;
    protected int maximumRedeliveries = 6;
    protected long maximumRedeliveryDelay = -1L;
    protected long initialRedeliveryDelay = 1000L;
    protected boolean useCollisionAvoidance;
    protected boolean useExponentialBackOff;
    protected double backOffMultiplier = 5.0;
    protected long redeliveryDelay = this.initialRedeliveryDelay;
    protected boolean preDispatchCheck = true;

    public RedeliveryPolicy copy() {
        try {
            return (RedeliveryPolicy)this.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("Could not clone: " + e, e);
        }
    }

    public double getBackOffMultiplier() {
        return this.backOffMultiplier;
    }

    public void setBackOffMultiplier(double backOffMultiplier) {
        this.backOffMultiplier = backOffMultiplier;
    }

    public short getCollisionAvoidancePercent() {
        return (short)Math.round(this.collisionAvoidanceFactor * 100.0);
    }

    public void setCollisionAvoidancePercent(short collisionAvoidancePercent) {
        this.collisionAvoidanceFactor = (double)collisionAvoidancePercent * 0.01;
    }

    public long getInitialRedeliveryDelay() {
        return this.initialRedeliveryDelay;
    }

    public void setInitialRedeliveryDelay(long initialRedeliveryDelay) {
        this.initialRedeliveryDelay = initialRedeliveryDelay;
    }

    public long getMaximumRedeliveryDelay() {
        return this.maximumRedeliveryDelay;
    }

    public void setMaximumRedeliveryDelay(long maximumRedeliveryDelay) {
        this.maximumRedeliveryDelay = maximumRedeliveryDelay;
    }

    public int getMaximumRedeliveries() {
        return this.maximumRedeliveries;
    }

    public void setMaximumRedeliveries(int maximumRedeliveries) {
        this.maximumRedeliveries = maximumRedeliveries;
    }

    public long getNextRedeliveryDelay(long previousDelay) {
        long nextDelay = this.redeliveryDelay;
        if (previousDelay > 0L && this.useExponentialBackOff && this.backOffMultiplier > 1.0) {
            nextDelay = (long)((double)previousDelay * this.backOffMultiplier);
            if (this.maximumRedeliveryDelay != -1L && nextDelay > this.maximumRedeliveryDelay) {
                nextDelay = Math.max(this.maximumRedeliveryDelay, this.redeliveryDelay);
            }
        }
        if (this.useCollisionAvoidance) {
            Random random = RedeliveryPolicy.getRandomNumberGenerator();
            double variance = (random.nextBoolean() ? this.collisionAvoidanceFactor : -this.collisionAvoidanceFactor) * random.nextDouble();
            nextDelay = (long)((double)nextDelay + (double)nextDelay * variance);
        }
        return nextDelay;
    }

    public boolean isUseCollisionAvoidance() {
        return this.useCollisionAvoidance;
    }

    public void setUseCollisionAvoidance(boolean useCollisionAvoidance) {
        this.useCollisionAvoidance = useCollisionAvoidance;
    }

    public boolean isUseExponentialBackOff() {
        return this.useExponentialBackOff;
    }

    public void setUseExponentialBackOff(boolean useExponentialBackOff) {
        this.useExponentialBackOff = useExponentialBackOff;
    }

    protected static synchronized Random getRandomNumberGenerator() {
        if (randomNumberGenerator == null) {
            randomNumberGenerator = new Random();
        }
        return randomNumberGenerator;
    }

    public void setRedeliveryDelay(long redeliveryDelay) {
        this.redeliveryDelay = redeliveryDelay;
    }

    public long getRedeliveryDelay() {
        return this.redeliveryDelay;
    }

    public String toString() {
        return IntrospectionSupport.toString(this, DestinationMapEntry.class, null);
    }

    public void setPreDispatchCheck(boolean preDispatchCheck) {
        this.preDispatchCheck = preDispatchCheck;
    }

    public boolean isPreDispatchCheck() {
        return this.preDispatchCheck;
    }
}

