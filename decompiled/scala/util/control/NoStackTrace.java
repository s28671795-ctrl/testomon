/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace$;

@ScalaSignature(bytes="\u0006\u0005\u00193qAC\u0006\u0011\u0002\u0007\u0005!\u0003C\u0003\u001c\u0001\u0011\u0005A\u0004C\u0003!\u0001\u0011\u0005\u0013\u0005C\u0006#\u0001A\u0005\u0019\u0011!A\u0005\n\rZs!\u0002\u0017\f\u0011\u0003ic!\u0002\u0006\f\u0011\u0003y\u0003\"B\u001d\u0006\t\u0003Q\u0004\"B\u001e\u0006\t\u000ba\u0004B\u0002!\u0006A\u00036Q\bC\u0004B\u000b\u0005\u0005I\u0011\u0002\"\u0003\u00199{7\u000b^1dWR\u0013\u0018mY3\u000b\u00051i\u0011aB2p]R\u0014x\u000e\u001c\u0006\u0003\u001d=\tA!\u001e;jY*\t\u0001#A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0005\u0001\u0019\u0002C\u0001\u000b\u0019\u001d\t)b#D\u0001\u0010\u0013\t9r\"A\u0004qC\u000e\\\u0017mZ3\n\u0005eQ\"!\u0003+ie><\u0018M\u00197f\u0015\t9r\"\u0001\u0004%S:LG\u000f\n\u000b\u0002;A\u0011QCH\u0005\u0003?=\u0011A!\u00168ji\u0006\u0001b-\u001b7m\u0013:\u001cF/Y2l)J\f7-\u001a\u000b\u0002'\u000512/\u001e9fe\u00122\u0017\u000e\u001c7J]N#\u0018mY6Ue\u0006\u001cW\rF\u0001%!\t)#&D\u0001'\u0015\t9\u0003&\u0001\u0003mC:<'\"A\u0015\u0002\t)\fg/Y\u0005\u00033\u0019J!\u0001\t\u0016\u0002\u00199{7\u000b^1dWR\u0013\u0018mY3\u0011\u00059*Q\"A\u0006\u0014\u0007\u0015\u00014\u0007\u0005\u0002\u0016c%\u0011!g\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0005Q:T\"A\u001b\u000b\u0005YB\u0013AA5p\u0013\tATG\u0001\u0007TKJL\u0017\r\\5{C\ndW-\u0001\u0004=S:LGO\u0010\u000b\u0002[\u0005ian\\*vaB\u0014Xm]:j_:,\u0012!\u0010\t\u0003+yJ!aP\b\u0003\u000f\t{w\u000e\\3b]\u0006qqL\\8TkB\u0004(/Z:tS>t\u0017\u0001D<sSR,'+\u001a9mC\u000e,G#A\"\u0011\u0005\u0015\"\u0015BA#'\u0005\u0019y%M[3di\u0002")
public interface NoStackTrace {
    public static boolean noSuppression() {
        return NoStackTrace$.MODULE$.noSuppression();
    }

    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace();

    public static /* synthetic */ Throwable fillInStackTrace$(NoStackTrace $this) {
        return $this.fillInStackTrace();
    }

    default public Throwable fillInStackTrace() {
        if (NoStackTrace$.MODULE$.noSuppression()) {
            return this.scala$util$control$NoStackTrace$$super$fillInStackTrace();
        }
        return (Throwable)((Object)this);
    }

    public static void $init$(NoStackTrace $this) {
    }
}

