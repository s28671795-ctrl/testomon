/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ProviderSelection;
import akka.actor.ProviderSelection$Cluster$;
import akka.actor.ProviderSelection$Local$;
import akka.actor.ProviderSelection$Remote$;
import akka.annotation.InternalApi;

public final class ProviderSelection$ {
    public static final ProviderSelection$ MODULE$ = new ProviderSelection$();
    private static final String RemoteActorRefProvider = "akka.remote.RemoteActorRefProvider";
    private static final String ClusterActorRefProvider = "akka.cluster.ClusterActorRefProvider";

    public String RemoteActorRefProvider() {
        return RemoteActorRefProvider;
    }

    public String ClusterActorRefProvider() {
        return ClusterActorRefProvider;
    }

    public ProviderSelection local() {
        return ProviderSelection$Local$.MODULE$;
    }

    public ProviderSelection remote() {
        return ProviderSelection$Remote$.MODULE$;
    }

    public ProviderSelection cluster() {
        return ProviderSelection$Cluster$.MODULE$;
    }

    @InternalApi
    public ProviderSelection apply(String providerClass) {
        boolean bl;
        boolean bl2;
        String string = providerClass;
        if ("local".equals(string)) {
            return ProviderSelection$Local$.MODULE$;
        }
        if ("remote".equals(string)) {
            bl2 = true;
        } else {
            String string2 = this.RemoteActorRefProvider();
            String string3 = string;
            bl2 = !(string2 != null ? !string2.equals(string3) : string3 != null);
        }
        if (bl2) {
            return ProviderSelection$Remote$.MODULE$;
        }
        if ("cluster".equals(string)) {
            bl = true;
        } else {
            String string4 = this.ClusterActorRefProvider();
            String string5 = string;
            bl = !(string4 != null ? !string4.equals(string5) : string5 != null);
        }
        if (bl) {
            return ProviderSelection$Cluster$.MODULE$;
        }
        return new ProviderSelection.Custom(string);
    }

    private ProviderSelection$() {
    }
}

