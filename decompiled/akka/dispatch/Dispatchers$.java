/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueType;

public final class Dispatchers$ {
    public static final Dispatchers$ MODULE$ = new Dispatchers$();
    private static final String DefaultBlockingDispatcherId = "akka.actor.default-blocking-io-dispatcher";
    private static final int akka$dispatch$Dispatchers$$MaxDispatcherAliasDepth = 20;

    public final String DefaultDispatcherId() {
        return "akka.actor.default-dispatcher";
    }

    public final String DefaultBlockingDispatcherId() {
        return DefaultBlockingDispatcherId;
    }

    public final String InternalDispatcherId() {
        return "akka.actor.internal-dispatcher";
    }

    public int akka$dispatch$Dispatchers$$MaxDispatcherAliasDepth() {
        return akka$dispatch$Dispatchers$$MaxDispatcherAliasDepth;
    }

    public Config getConfig(Config config, String id, int depth) {
        block3: {
            ConfigValueType configValueType;
            while (true) {
                if (depth > this.akka$dispatch$Dispatchers$$MaxDispatcherAliasDepth()) {
                    return ConfigFactory.empty(new StringBuilder(44).append("Didn't find dispatcher config after ").append(this.akka$dispatch$Dispatchers$$MaxDispatcherAliasDepth()).append(" aliases").toString());
                }
                if (!config.hasPath(id)) break block3;
                configValueType = config.getValue(id).valueType();
                if (!((Object)((Object)ConfigValueType.STRING)).equals((Object)configValueType)) break;
                ++depth;
                id = config.getString(id);
            }
            if (((Object)((Object)ConfigValueType.OBJECT)).equals((Object)configValueType)) {
                return config.getConfig(id);
            }
            return ConfigFactory.empty(new StringBuilder(50).append("Expected either config or alias at [").append(id).append("] but found [").append((Object)configValueType).append("]").toString());
        }
        return ConfigFactory.empty(new StringBuilder(28).append("Dispatcher [").append(id).append("] not configured").toString());
    }

    public int getConfig$default$3() {
        return 0;
    }

    private Dispatchers$() {
    }
}

