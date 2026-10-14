/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import java.util.concurrent.locks.ReentrantLock;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2Aa\u0001\u0003\u0003\u0013!)Q\u0003\u0001C\u0001-!)\u0011\u0004\u0001C\u00035\tq!+Z3oiJ\fg\u000e^$vCJ$'BA\u0003\u0007\u0003\u0011)H/\u001b7\u000b\u0003\u001d\tA!Y6lC\u000e\u00011C\u0001\u0001\u000b!\tY1#D\u0001\r\u0015\tia\"A\u0003m_\u000e\\7O\u0003\u0002\u0010!\u0005Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005\u0015\t\"\"\u0001\n\u0002\t)\fg/Y\u0005\u0003)1\u0011QBU3f]R\u0014\u0018M\u001c;M_\u000e\\\u0017A\u0002\u001fj]&$h\bF\u0001\u0018!\tA\u0002!D\u0001\u0005\u0003%9\u0018\u000e\u001e5Hk\u0006\u0014H-\u0006\u0002\u001c=Q\u0011AD\u000b\t\u0003;ya\u0001\u0001B\u0003 \u0005\t\u0007\u0001EA\u0001U#\t\ts\u0005\u0005\u0002#K5\t1EC\u0001%\u0003\u0015\u00198-\u00197b\u0013\t13EA\u0004O_RD\u0017N\\4\u0011\u0005\tB\u0013BA\u0015$\u0005\r\te.\u001f\u0005\u0007W\t!\t\u0019\u0001\u0017\u0002\t\t|G-\u001f\t\u0004E5b\u0012B\u0001\u0018$\u0005!a$-\u001f8b[\u0016t\u0004F\u0001\u00021!\t\u0011\u0013'\u0003\u00023G\t1\u0011N\u001c7j]\u0016\u0004")
public final class ReentrantGuard
extends ReentrantLock {
    public final <T> T withGuard(Function0<T> body2) {
        T t2;
        this.lock();
        try {
            t2 = body2.apply();
        }
        finally {
            this.unlock();
        }
        return t2;
    }
}

