/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.AkkaException;
import akka.pattern.CircuitBreakerOpenException$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u00014AAC\u0006\u0001!!Aq\u0004\u0001BC\u0002\u0013\u0005\u0001\u0005\u0003\u0005*\u0001\t\u0005\t\u0015!\u0003\"\u0011!Q\u0003A!A!\u0002\u0013Y\u0003\"\u0002\u001c\u0001\t\u00039ta\u0002\u001f\f\u0003\u0003E\t!\u0010\u0004\b\u0015-\t\t\u0011#\u0001?\u0011\u00151d\u0001\"\u0001L\u0011\u001dae!%A\u0005\u00025Cq\u0001\u0017\u0004\u0002\u0002\u0013%\u0011LA\u000eDSJ\u001cW/\u001b;Ce\u0016\f7.\u001a:Pa\u0016tW\t_2faRLwN\u001c\u0006\u0003\u00195\tq\u0001]1ui\u0016\u0014hNC\u0001\u000f\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001!E\u000b\u0011\u0005I\u0019R\"A\u0007\n\u0005Qi!!D!lW\u0006,\u0005pY3qi&|g\u000e\u0005\u0002\u0017;5\tqC\u0003\u0002\u00193\u000591m\u001c8ue>d'B\u0001\u000e\u001c\u0003\u0011)H/\u001b7\u000b\u0003q\tQa]2bY\u0006L!AH\f\u0003\u00199{7\u000b^1dWR\u0013\u0018mY3\u0002#I,W.Y5oS:<G)\u001e:bi&|g.F\u0001\"!\t\u0011s%D\u0001$\u0015\t!S%\u0001\u0005ekJ\fG/[8o\u0015\t13$\u0001\u0006d_:\u001cWO\u001d:f]RL!\u0001K\u0012\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006\u0011\"/Z7bS:Lgn\u001a#ve\u0006$\u0018n\u001c8!\u0003\u001diWm]:bO\u0016\u0004\"\u0001L\u001a\u000f\u00055\n\u0004C\u0001\u0018\u001c\u001b\u0005y#B\u0001\u0019\u0010\u0003\u0019a$o\\8u}%\u0011!gG\u0001\u0007!J,G-\u001a4\n\u0005Q*$AB*ue&twM\u0003\u000237\u00051A(\u001b8jiz\"2\u0001\u000f\u001e<!\tI\u0004!D\u0001\f\u0011\u0015yB\u00011\u0001\"\u0011\u001dQC\u0001%AA\u0002-\n1dQ5sGVLGO\u0011:fC.,'o\u00149f]\u0016C8-\u001a9uS>t\u0007CA\u001d\u0007'\r1qh\u0011\t\u0003\u0001\u0006k\u0011aG\u0005\u0003\u0005n\u0011a!\u00118z%\u00164\u0007C\u0001#J\u001b\u0005)%B\u0001$H\u0003\tIwNC\u0001I\u0003\u0011Q\u0017M^1\n\u0005)+%\u0001D*fe&\fG.\u001b>bE2,G#A\u001f\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133+\u0005q%FA\u0016PW\u0005\u0001\u0006CA)W\u001b\u0005\u0011&BA*U\u0003%)hn\u00195fG.,GM\u0003\u0002V7\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005]\u0013&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006aqO]5uKJ+\u0007\u000f\\1dKR\t!\f\u0005\u0002\\=6\tAL\u0003\u0002^\u000f\u0006!A.\u00198h\u0013\tyFL\u0001\u0004PE*,7\r\u001e")
public class CircuitBreakerOpenException
extends AkkaException
implements NoStackTrace {
    private final FiniteDuration remainingDuration;

    public static String $lessinit$greater$default$2() {
        return CircuitBreakerOpenException$.MODULE$.$lessinit$greater$default$2();
    }

    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    public FiniteDuration remainingDuration() {
        return this.remainingDuration;
    }

    public CircuitBreakerOpenException(FiniteDuration remainingDuration, String message) {
        this.remainingDuration = remainingDuration;
        super(message);
        NoStackTrace.$init$(this);
    }
}

