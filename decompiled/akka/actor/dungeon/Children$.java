/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.actor.Nobody$;
import java.io.Serializable;
import scala.Function0;

public final class Children$ {
    public static final Children$ MODULE$ = new Children$();
    private static final Function0<Nobody$> GetNobody = (Function0<Nobody$> & Serializable)() -> Nobody$.MODULE$;

    public Function0<Nobody$> GetNobody() {
        return GetNobody;
    }

    private Children$() {
    }
}

