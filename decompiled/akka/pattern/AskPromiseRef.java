/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRefProvider;
import akka.pattern.AskPromiseRef$;
import akka.pattern.PromiseActorRef;
import akka.pattern.PromiseRefImpl;
import akka.util.Timeout;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m2Qa\u0002\u0005\u0003\u00151A\u0011b\u0006\u0001\u0003\u0002\u0003\u0006I!\u0007\u000f\t\u000by\u0001A\u0011B\u0010\b\r\tB\u0001\u0012\u0001\u0006$\r\u00199\u0001\u0002#\u0001\u000bI!)a\u0004\u0002C\u0001Q!)\u0011\u0006\u0002C\u0001U\ti\u0011i]6Qe>l\u0017n]3SK\u001aT!!\u0003\u0006\u0002\u000fA\fG\u000f^3s]*\t1\"\u0001\u0003bW.\f7C\u0001\u0001\u000e!\rqq\"E\u0007\u0002\u0011%\u0011\u0001\u0003\u0003\u0002\u000f!J|W.[:f%\u00164\u0017*\u001c9m!\t\u0011R#D\u0001\u0014\u0015\u0005!\u0012!B:dC2\f\u0017B\u0001\f\u0014\u0005\r\te._\u0001\u0010aJ|W.[:f\u0003\u000e$xN\u001d*fM\u000e\u0001\u0001C\u0001\b\u001b\u0013\tY\u0002BA\bQe>l\u0017n]3BGR|'OU3g\u0013\tir\"A\u0002sK\u001a\fa\u0001P5oSRtDC\u0001\u0011\"!\tq\u0001\u0001C\u0003\u0018\u0005\u0001\u0007\u0011$A\u0007Bg.\u0004&o\\7jg\u0016\u0014VM\u001a\t\u0003\u001d\u0011\u0019\"\u0001B\u0013\u0011\u0005I1\u0013BA\u0014\u0014\u0005\u0019\te.\u001f*fMR\t1%A\u0003baBd\u0017\u0010F\u0002!WMBQ\u0001\f\u0004A\u00025\n\u0001\u0002\u001d:pm&$WM\u001d\t\u0003]Ej\u0011a\f\u0006\u0003a)\tQ!Y2u_JL!AM\u0018\u0003!\u0005\u001bGo\u001c:SK\u001a\u0004&o\u001c<jI\u0016\u0014\b\"\u0002\u001b\u0007\u0001\u0004)\u0014a\u0002;j[\u0016|W\u000f\u001e\t\u0003mej\u0011a\u000e\u0006\u0003q)\tA!\u001e;jY&\u0011!h\u000e\u0002\b)&lWm\\;u\u0001")
public final class AskPromiseRef
extends PromiseRefImpl<Object> {
    public static AskPromiseRef apply(ActorRefProvider provider, Timeout timeout2) {
        return AskPromiseRef$.MODULE$.apply(provider, timeout2);
    }

    public AskPromiseRef(PromiseActorRef promiseActorRef) {
        super(promiseActorRef, promiseActorRef.result());
    }
}

