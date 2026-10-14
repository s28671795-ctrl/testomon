/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRefScope;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a1\u0001BA\u0002\u0011\u0002G\u0005Qa\u0002\u0005\u0006%\u00011\t\u0001\u0006\u0002\u000f%\u0016\u0004x.\u001b8uC\ndWMU3g\u0015\t!Q!A\u0003bGR|'OC\u0001\u0007\u0003\u0011\t7n[1\u0014\u0007\u0001Aa\u0002\u0005\u0002\n\u00195\t!BC\u0001\f\u0003\u0015\u00198-\u00197b\u0013\ti!B\u0001\u0004B]f\u0014VM\u001a\t\u0003\u001fAi\u0011aA\u0005\u0003#\r\u0011Q\"Q2u_J\u0014VMZ*d_B,\u0017!C5t'R\f'\u000f^3e\u0007\u0001)\u0012!\u0006\t\u0003\u0013YI!a\u0006\u0006\u0003\u000f\t{w\u000e\\3b]\u0002")
public interface RepointableRef
extends ActorRefScope {
    public boolean isStarted();
}

