/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.EnterIsland;
import akka.stream.impl.IslandTag;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class EnterIsland$
extends AbstractFunction1<IslandTag, EnterIsland>
implements Serializable {
    public static final EnterIsland$ MODULE$ = new EnterIsland$();

    @Override
    public final String toString() {
        return "EnterIsland";
    }

    @Override
    public EnterIsland apply(IslandTag islandTag) {
        return new EnterIsland(islandTag);
    }

    public Option<IslandTag> unapply(EnterIsland x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<IslandTag>(x$0.islandTag());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(EnterIsland$.class);
    }

    private EnterIsland$() {
    }
}

