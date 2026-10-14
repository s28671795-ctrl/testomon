/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.BidiShape;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.KillSwitches$UniqueBidiKillSwitchStage$;
import akka.stream.KillSwitches$UniqueKillSwitchStage$;
import akka.stream.SharedKillSwitch;
import akka.stream.UniqueKillSwitch;

public final class KillSwitches$ {
    public static final KillSwitches$ MODULE$ = new KillSwitches$();

    public SharedKillSwitch shared(String name) {
        return new SharedKillSwitch(name);
    }

    public <T> Graph<FlowShape<T, T>, UniqueKillSwitch> single() {
        return KillSwitches$UniqueKillSwitchStage$.MODULE$;
    }

    public <T1, T2> Graph<BidiShape<T1, T1, T2, T2>, UniqueKillSwitch> singleBidi() {
        return KillSwitches$UniqueBidiKillSwitchStage$.MODULE$;
    }

    private KillSwitches$() {
    }
}

