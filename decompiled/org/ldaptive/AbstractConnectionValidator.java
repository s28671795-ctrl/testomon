/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConnectionValidator
implements ConnectionValidator {
    public static final Duration DEFAULT_VALIDATE_PERIOD = Duration.ofMinutes(30L);
    public static final Duration DEFAULT_VALIDATE_TIMEOUT = Duration.ofSeconds(5L);
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Duration validatePeriod;
    private Duration validateTimeout;

    @Override
    public Duration getValidatePeriod() {
        return this.validatePeriod;
    }

    public void setValidatePeriod(Duration period) {
        if (period == null || period.isNegative() || period.isZero()) {
            throw new IllegalArgumentException("Period cannot be null, negative or zero");
        }
        this.validatePeriod = period;
    }

    @Override
    public Duration getValidateTimeout() {
        return this.validateTimeout;
    }

    public void setValidateTimeout(Duration timeout2) {
        if (timeout2 == null || timeout2.isNegative()) {
            throw new IllegalArgumentException("Timeout cannot be null or negative");
        }
        this.validateTimeout = timeout2;
    }

    @Override
    public Boolean apply(Connection conn) {
        if (conn == null) {
            return false;
        }
        return this.applyAsync(conn).get();
    }

    @Override
    public Supplier<Boolean> applyAsync(Connection conn) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean result = new AtomicBoolean();
        this.applyAsync(conn, value -> {
            result.set((boolean)value);
            latch.countDown();
        });
        return () -> {
            try {
                if (Duration.ZERO.equals(this.getValidateTimeout())) {
                    latch.await();
                } else {
                    latch.await(this.getValidateTimeout().toMillis(), TimeUnit.MILLISECONDS);
                }
            }
            catch (Exception e) {
                this.logger.debug("Validating {} threw unexpected exception", (Object)conn, (Object)e);
            }
            return result.get();
        };
    }

    protected static abstract class AbstractBuilder<B, T extends AbstractConnectionValidator> {
        protected final T object;

        protected AbstractBuilder(T t2) {
            this.object = t2;
        }

        protected abstract B self();

        public B period(Duration period) {
            ((AbstractConnectionValidator)this.object).setValidatePeriod(period);
            return this.self();
        }

        public B timeout(Duration timeout2) {
            ((AbstractConnectionValidator)this.object).setValidateTimeout(timeout2);
            return this.self();
        }

        public T build() {
            return this.object;
        }
    }
}

