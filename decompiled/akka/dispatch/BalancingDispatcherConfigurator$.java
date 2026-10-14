/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public final class BalancingDispatcherConfigurator$ {
    public static final BalancingDispatcherConfigurator$ MODULE$ = new BalancingDispatcherConfigurator$();
    private static final Config defaultRequirement = ConfigFactory.parseString("mailbox-requirement = akka.dispatch.MultipleConsumerSemantics");

    private Config defaultRequirement() {
        return defaultRequirement;
    }

    public Config amendConfig(Config config) {
        String string = config.getString("mailbox-requirement");
        String string2 = "";
        if (string == null ? string2 != null : !string.equals(string2)) {
            return config;
        }
        return this.defaultRequirement().withFallback(config);
    }

    private BalancingDispatcherConfigurator$() {
    }
}

