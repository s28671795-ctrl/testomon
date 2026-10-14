/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.ldaptive.LdapURL;

public final class LdapURLActivatorService {
    private static final String ACTIVATOR_PERIOD_PROPERTY = "org.ldaptive.urlActivatorPeriod";
    private static final Duration ACTIVATOR_PERIOD = Duration.ofMinutes(Long.parseLong(System.getProperty("org.ldaptive.urlActivatorPeriod", "5")));
    private static final LdapURLActivatorService INSTANCE = new LdapURLActivatorService();
    private final List<LdapURL> inactiveUrls = new ArrayList<LdapURL>();
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, r -> {
        Thread t2 = new Thread(r, this.getClass().getSimpleName() + "@" + this.hashCode());
        t2.setDaemon(true);
        return t2;
    });

    private LdapURLActivatorService() {
        this.executor.scheduleAtFixedRate(this::testInactiveUrls, ACTIVATOR_PERIOD.toMillis(), ACTIVATOR_PERIOD.toMillis(), TimeUnit.MILLISECONDS);
    }

    public static LdapURLActivatorService getInstance() {
        return INSTANCE;
    }

    public static Duration getPeriod() {
        return ACTIVATOR_PERIOD;
    }

    public void registerUrl(LdapURL url2) {
        this.inactiveUrls.add(url2);
    }

    public List<LdapURL> getInactiveUrls() {
        return Collections.unmodifiableList(this.inactiveUrls);
    }

    protected void testInactiveUrls() {
        for (LdapURL url2 : this.inactiveUrls) {
            if (url2.isActive() || !url2.getRetryMetadata().getConnectionStrategy().getRetryCondition().test(url2)) continue;
            if (url2.getRetryMetadata().getConnectionStrategy().getActivateCondition().test(url2)) {
                url2.getRetryMetadata().getConnectionStrategy().success(url2);
                continue;
            }
            url2.getRetryMetadata().recordFailure(Instant.now());
        }
        this.inactiveUrls.removeIf(LdapURL::isActive);
    }

    void clear() {
        this.inactiveUrls.clear();
    }
}

