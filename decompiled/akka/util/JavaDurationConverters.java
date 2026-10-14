/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalStableApi;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.JavaDurationConverters$ScalaDurationOps$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00055rA\u0002\u0011\"\u0011\u0003\u0019SE\u0002\u0004(C!\u00051\u0005\u000b\u0005\u0006_\u0005!\t!\r\u0005\u0006e\u0005!\ta\r\u0004\u0005\u000b\u0006\u0019a\t\u0003\u0005K\t\t\u0015\r\u0011\"\u0001L\u0011!aEA!A!\u0002\u0013i\u0004\"B\u0018\u0005\t\u0003i\u0005\"B)\u0005\t\u0003\u0011\u0006bB*\u0005\u0003\u0003%\t\u0005\u0016\u0005\b1\u0012\t\t\u0011\"\u0011Z\u000f\u001d\u0011\u0017!!A\t\u0002\r4q!R\u0001\u0002\u0002#\u0005A\rC\u00030\u0019\u0011\u0005Q\rC\u0003g\u0019\u0011\u0015q\rC\u0004k\u0019\u0005\u0005IQA6\t\u000f5d\u0011\u0011!C\u0003]\"9!-AA\u0001\n\u000f\u0011h\u0001\u0002;\u0002\u0007UD\u0001B\u0013\n\u0003\u0006\u0004%\tA\u001e\u0005\t\u0019J\u0011\t\u0011)A\u0005o\")qF\u0005C\u0001s\")AP\u0005C\u0001\u0017\"91KEA\u0001\n\u0003\"\u0006b\u0002-\u0013\u0003\u0003%\t%`\u0004\t\u007f\u0006\t\t\u0011#\u0001\u0002\u0002\u0019AA/AA\u0001\u0012\u0003\t\u0019\u0001\u0003\u000405\u0011\u0005\u0011Q\u0001\u0005\b\u0003\u000fQBQAA\u0005\u0011!Q'$!A\u0005\u0006\u00055\u0001\u0002C7\u001b\u0003\u0003%)!!\u0005\t\u0011}\f\u0011\u0011!C\u0004\u00033\taCS1wC\u0012+(/\u0019;j_:\u001cuN\u001c<feR,'o\u001d\u0006\u0003E\r\nA!\u001e;jY*\tA%\u0001\u0003bW.\f\u0007C\u0001\u0014\u0002\u001b\u0005\t#A\u0006&bm\u0006$UO]1uS>t7i\u001c8wKJ$XM]:\u0014\u0005\u0005I\u0003C\u0001\u0016.\u001b\u0005Y#\"\u0001\u0017\u0002\u000bM\u001c\u0017\r\\1\n\u00059Z#AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005)\u0013\u0001E1t\r&t\u0017\u000e^3EkJ\fG/[8o)\t!D\b\u0005\u00026u5\taG\u0003\u00028q\u0005AA-\u001e:bi&|gN\u0003\u0002:W\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005m2$A\u0004$j]&$X\rR;sCRLwN\u001c\u0005\u0006o\r\u0001\r!\u0010\t\u0003}\rk\u0011a\u0010\u0006\u0003\u0001\u0006\u000bA\u0001^5nK*\t!)\u0001\u0003kCZ\f\u0017B\u0001#@\u0005!!UO]1uS>t'a\u0004&bm\u0006$UO]1uS>tw\n]:\u0014\u0005\u00119\u0005C\u0001\u0016I\u0013\tI5F\u0001\u0004B]f4\u0016\r\\\u0001\u0005g\u0016dg-F\u0001>\u0003\u0015\u0019X\r\u001c4!)\tq\u0005\u000b\u0005\u0002P\t5\t\u0011\u0001C\u0003K\u000f\u0001\u0007Q(A\u0004bgN\u001b\u0017\r\\1\u0016\u0003Q\n\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002+B\u0011!FV\u0005\u0003/.\u00121!\u00138u\u0003\u0019)\u0017/^1mgR\u0011!,\u0018\t\u0003UmK!\u0001X\u0016\u0003\u000f\t{w\u000e\\3b]\"9aLCA\u0001\u0002\u0004y\u0016a\u0001=%cA\u0011!\u0006Y\u0005\u0003C.\u00121!\u00118z\u0003=Q\u0015M^1EkJ\fG/[8o\u001fB\u001c\bCA(\r'\ta\u0011\u0006F\u0001d\u0003E\t7oU2bY\u0006$S\r\u001f;f]NLwN\u001c\u000b\u0003i!DQ!\u001b\bA\u00029\u000bQ\u0001\n;iSN\f!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]R\u0011A\u000b\u001c\u0005\u0006S>\u0001\rAT\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:$\"a\\9\u0015\u0005i\u0003\bb\u00020\u0011\u0003\u0003\u0005\ra\u0018\u0005\u0006SB\u0001\rA\u0014\u000b\u0003\u001dNDQAS\tA\u0002u\u0012\u0001cU2bY\u0006$UO]1uS>tw\n]:\u0014\u0005I9U#A<\u0011\u0005UB\u0018B\u0001#7)\tQ8\u0010\u0005\u0002P%!)!*\u0006a\u0001o\u00061\u0011m\u001d&bm\u0006$\"A\u0017@\t\u000fyC\u0012\u0011!a\u0001?\u0006\u00012kY1mC\u0012+(/\u0019;j_:|\u0005o\u001d\t\u0003\u001fj\u0019\"AG\u0015\u0015\u0005\u0005\u0005\u0011\u0001E1t\u0015\u00064\u0018\rJ3yi\u0016t7/[8o)\ri\u00141\u0002\u0005\u0006Sr\u0001\rA\u001f\u000b\u0004)\u0006=\u0001\"B5\u001e\u0001\u0004QH\u0003BA\n\u0003/!2AWA\u000b\u0011\u001dqf$!AA\u0002}CQ!\u001b\u0010A\u0002i$2A_A\u000e\u0011\u0015Qu\u00041\u0001xQ\r\t\u0011q\u0004\t\u0005\u0003C\t9#\u0004\u0002\u0002$)\u0019\u0011QE\u0012\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002*\u0005\r\"!E%oi\u0016\u0014h.\u00197Ti\u0006\u0014G.Z!qS\"\u001a\u0001!a\b")
@InternalStableApi
public final class JavaDurationConverters {
    public static Duration ScalaDurationOps(Duration self) {
        return JavaDurationConverters$.MODULE$.ScalaDurationOps(self);
    }

    public static java.time.Duration JavaDurationOps(java.time.Duration self) {
        return JavaDurationConverters$.MODULE$.JavaDurationOps(self);
    }

    public static FiniteDuration asFiniteDuration(java.time.Duration duration) {
        return JavaDurationConverters$.MODULE$.asFiniteDuration(duration);
    }

    public static final class JavaDurationOps {
        private final java.time.Duration self;

        public java.time.Duration self() {
            return this.self;
        }

        public FiniteDuration asScala() {
            return JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(this.self());
        }

        public int hashCode() {
            return JavaDurationConverters$JavaDurationOps$.MODULE$.hashCode$extension(this.self());
        }

        public boolean equals(Object x$1) {
            return JavaDurationConverters$JavaDurationOps$.MODULE$.equals$extension(this.self(), x$1);
        }

        public JavaDurationOps(java.time.Duration self) {
            this.self = self;
        }
    }

    public static final class ScalaDurationOps {
        private final Duration self;

        public Duration self() {
            return this.self;
        }

        public java.time.Duration asJava() {
            return JavaDurationConverters$ScalaDurationOps$.MODULE$.asJava$extension(this.self());
        }

        public int hashCode() {
            return JavaDurationConverters$ScalaDurationOps$.MODULE$.hashCode$extension(this.self());
        }

        public boolean equals(Object x$1) {
            return JavaDurationConverters$ScalaDurationOps$.MODULE$.equals$extension(this.self(), x$1);
        }

        public ScalaDurationOps(Duration self) {
            this.self = self;
        }
    }
}

