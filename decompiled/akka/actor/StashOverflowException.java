/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.AkkaException;
import akka.actor.StashOverflowException$;
import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0003\u0006\u0001\u001f!Aa\u0004\u0001B\u0001B\u0003%q\u0004\u0003\u0005+\u0001\t\u0005\t\u0015!\u0003,\u0011\u0015!\u0004\u0001\"\u00016\u000f\u001dQ$\"!A\t\u0002m2q!\u0003\u0006\u0002\u0002#\u0005A\bC\u00035\u000b\u0011\u0005\u0011\nC\u0004K\u000bE\u0005I\u0011A&\t\u000fY+\u0011\u0011!C\u0005/\n12\u000b^1tQ>3XM\u001d4m_^,\u0005pY3qi&|gN\u0003\u0002\f\u0019\u0005)\u0011m\u0019;pe*\tQ\"\u0001\u0003bW.\f7\u0001A\n\u0004\u0001A!\u0002CA\t\u0013\u001b\u0005a\u0011BA\n\r\u00055\t5n[1Fq\u000e,\u0007\u000f^5p]B\u0011Q\u0003H\u0007\u0002-)\u0011q\u0003G\u0001\bG>tGO]8m\u0015\tI\"$\u0001\u0003vi&d'\"A\u000e\u0002\u000bM\u001c\u0017\r\\1\n\u0005u1\"\u0001\u0004(p'R\f7m\u001b+sC\u000e,\u0017aB7fgN\fw-\u001a\t\u0003A\u001dr!!I\u0013\u0011\u0005\tRR\"A\u0012\u000b\u0005\u0011r\u0011A\u0002\u001fs_>$h(\u0003\u0002'5\u00051\u0001K]3eK\u001aL!\u0001K\u0015\u0003\rM#(/\u001b8h\u0015\t1#$A\u0003dCV\u001cX\r\u0005\u0002-c9\u0011Qf\f\b\u0003E9J\u0011aG\u0005\u0003ai\tq\u0001]1dW\u0006<W-\u0003\u00023g\tIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003ai\ta\u0001P5oSRtDc\u0001\u001c9sA\u0011q\u0007A\u0007\u0002\u0015!)ad\u0001a\u0001?!9!f\u0001I\u0001\u0002\u0004Y\u0013AF*uCNDwJ^3sM2|w/\u0012=dKB$\u0018n\u001c8\u0011\u0005]*1cA\u0003>\u0003B\u0011ahP\u0007\u00025%\u0011\u0001I\u0007\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\t;U\"A\"\u000b\u0005\u0011+\u0015AA5p\u0015\u00051\u0015\u0001\u00026bm\u0006L!\u0001S\"\u0003\u0019M+'/[1mSj\f'\r\\3\u0015\u0003m\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012T#\u0001'+\u0005-j5&\u0001(\u0011\u0005=#V\"\u0001)\u000b\u0005E\u0013\u0016!C;oG\",7m[3e\u0015\t\u0019&$\u0001\u0006b]:|G/\u0019;j_:L!!\u0016)\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001Y!\tIF,D\u0001[\u0015\tYV)\u0001\u0003mC:<\u0017BA/[\u0005\u0019y%M[3di\u0002")
public class StashOverflowException
extends AkkaException
implements NoStackTrace {
    public static Throwable $lessinit$greater$default$2() {
        return StashOverflowException$.MODULE$.$lessinit$greater$default$2();
    }

    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    public StashOverflowException(String message, Throwable cause) {
        super(message, cause);
        NoStackTrace.$init$(this);
    }
}

