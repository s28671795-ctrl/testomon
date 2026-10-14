/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.runtime.ScalaRunTime$;

public final class BalancingPoolDeploy$ {
    public static final BalancingPoolDeploy$ MODULE$ = new BalancingPoolDeploy$();
    private static final List<Object> invalidConfigKeyChars = (List)package$.MODULE$.List().apply((Seq)ScalaRunTime$.MODULE$.wrapCharArray(new char[]{'$', '@', ':'}));

    public List<Object> invalidConfigKeyChars() {
        return invalidConfigKeyChars;
    }

    private BalancingPoolDeploy$() {
    }
}

