/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.time.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.jdk.DurationConverters$;
import scala.jdk.DurationConverters$JavaDurationOps$;
import scala.jdk.DurationConverters$ScalaDurationOps$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=q!B\u0010!\u0011\u0003)c!B\u0014!\u0011\u0003A\u0003\"B\u0017\u0002\t\u0003qc\u0001B\u0018\u0002\u0007ABA\u0002N\u0002\u0005\u0002\u0003\u0015)Q1A\u0005\nUB\u0011BP\u0002\u0003\u0006\u0003\u0005\u000b\u0011\u0002\u001c\t\u000b5\u001aA\u0011A \t\u000b\u0011\u001bA\u0011A#\t\u000f5\u001b\u0011\u0011!C!\u001d\"9!kAA\u0001\n\u0003\u001ava\u0002/\u0002\u0003\u0003E\t!\u0018\u0004\b_\u0005\t\t\u0011#\u0001_\u0011\u0015i3\u0002\"\u0001`\u0011\u0015\u00017\u0002\"\u0002b\u0011\u001d!7\"!A\u0005\u0006\u0015DqaZ\u0006\u0002\u0002\u0013\u0015\u0001\u000eC\u0004]\u0003\u0005\u0005I1\u00017\u0007\t9\f1a\u001c\u0005\raF!\t\u0011!B\u0003\u0006\u0004%I!\u0012\u0005\ncF\u0011)\u0011!Q\u0001\n\u0019CQ!L\t\u0005\u0002IDQ!^\t\u0005\u0002UBq!T\t\u0002\u0002\u0013\u0005c\nC\u0004S#\u0005\u0005I\u0011\t<\b\u000fa\f\u0011\u0011!E\u0001s\u001a9a.AA\u0001\u0012\u0003Q\b\"B\u0017\u001a\t\u0003Y\b\"\u0002?\u001a\t\u000bi\bb\u00023\u001a\u0003\u0003%)a \u0005\tOf\t\t\u0011\"\u0002\u0002\u0004!A\u00010AA\u0001\n\u000f\tY!\u0001\nEkJ\fG/[8o\u0007>tg/\u001a:uKJ\u001c(BA\u0011#\u0003\rQGm\u001b\u0006\u0002G\u0005)1oY1mC\u000e\u0001\u0001C\u0001\u0014\u0002\u001b\u0005\u0001#A\u0005#ve\u0006$\u0018n\u001c8D_:4XM\u001d;feN\u001c\"!A\u0015\u0011\u0005)ZS\"\u0001\u0012\n\u00051\u0012#AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002K\ty!*\u0019<b\tV\u0014\u0018\r^5p]>\u00038o\u0005\u0002\u0004cA\u0011!FM\u0005\u0003g\t\u0012a!\u00118z-\u0006d\u0017AN:dC2\fGE\u001b3lI\u0011+(/\u0019;j_:\u001cuN\u001c<feR,'o\u001d\u0013KCZ\fG)\u001e:bi&|gn\u00149tI\u0011\"WO]1uS>tW#\u0001\u001c\u0011\u0005]bT\"\u0001\u001d\u000b\u0005eR\u0014\u0001\u0002;j[\u0016T\u0011aO\u0001\u0005U\u00064\u0018-\u0003\u0002>q\tAA)\u001e:bi&|g.A\u001ctG\u0006d\u0017\r\n6eW\u0012\"UO]1uS>t7i\u001c8wKJ$XM]:%\u0015\u00064\u0018\rR;sCRLwN\\(qg\u0012\"C-\u001e:bi&|g\u000e\t\u000b\u0003\u0001\n\u0003\"!Q\u0002\u000e\u0003\u0005AQa\u0011\u0004A\u0002Y\n\u0001\u0002Z;sCRLwN\\\u0001\bi>\u001c6-\u00197b+\u00051\u0005CA$L\u001b\u0005A%BA\"J\u0015\tQ%%\u0001\u0006d_:\u001cWO\u001d:f]RL!\u0001\u0014%\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006A\u0001.Y:i\u0007>$W\rF\u0001P!\tQ\u0003+\u0003\u0002RE\t\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\t!v\u000b\u0005\u0002++&\u0011aK\t\u0002\b\u0005>|G.Z1o\u0011\u001dA\u0016\"!AA\u0002e\u000b1\u0001\u001f\u00132!\tQ#,\u0003\u0002\\E\t\u0019\u0011I\\=\u0002\u001f)\u000bg/\u0019#ve\u0006$\u0018n\u001c8PaN\u0004\"!Q\u0006\u0014\u0005-IC#A/\u0002#Q|7kY1mC\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002GE\")1-\u0004a\u0001\u0001\u0006)A\u0005\u001e5jg\u0006\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o)\tqe\rC\u0003d\u001d\u0001\u0007\u0001)\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R\u0011\u0011n\u001b\u000b\u0003)*Dq\u0001W\b\u0002\u0002\u0003\u0007\u0011\fC\u0003d\u001f\u0001\u0007\u0001\t\u0006\u0002A[\")1\t\u0005a\u0001m\t\u00012kY1mC\u0012+(/\u0019;j_:|\u0005o]\n\u0003#E\nqg]2bY\u0006$#\u000eZ6%\tV\u0014\u0018\r^5p]\u000e{gN^3si\u0016\u00148\u000fJ*dC2\fG)\u001e:bi&|gn\u00149tI\u0011\"WO]1uS>t\u0017\u0001O:dC2\fGE\u001b3lI\u0011+(/\u0019;j_:\u001cuN\u001c<feR,'o\u001d\u0013TG\u0006d\u0017\rR;sCRLwN\\(qg\u0012\"C-\u001e:bi&|g\u000e\t\u000b\u0003gR\u0004\"!Q\t\t\u000b\r#\u0002\u0019\u0001$\u0002\rQ|'*\u0019<b)\t!v\u000fC\u0004Y/\u0005\u0005\t\u0019A-\u0002!M\u001b\u0017\r\\1EkJ\fG/[8o\u001fB\u001c\bCA!\u001a'\tI\u0012\u0006F\u0001z\u0003A!xNS1wC\u0012*\u0007\u0010^3og&|g\u000e\u0006\u00027}\")1m\u0007a\u0001gR\u0019a*!\u0001\t\u000b\rd\u0002\u0019A:\u0015\t\u0005\u0015\u0011\u0011\u0002\u000b\u0004)\u0006\u001d\u0001b\u0002-\u001e\u0003\u0003\u0005\r!\u0017\u0005\u0006Gv\u0001\ra\u001d\u000b\u0004g\u00065\u0001\"B\"\u001f\u0001\u00041\u0005")
public final class DurationConverters {
    public static FiniteDuration ScalaDurationOps(FiniteDuration duration) {
        return DurationConverters$.MODULE$.ScalaDurationOps(duration);
    }

    public static Duration JavaDurationOps(Duration duration) {
        return DurationConverters$.MODULE$.JavaDurationOps(duration);
    }

    public static final class JavaDurationOps {
        private final Duration scala$jdk$DurationConverters$JavaDurationOps$$duration;

        public Duration scala$jdk$DurationConverters$JavaDurationOps$$duration() {
            return this.scala$jdk$DurationConverters$JavaDurationOps$$duration;
        }

        public FiniteDuration toScala() {
            Duration toScala$extension_$this = this.scala$jdk$DurationConverters$JavaDurationOps$$duration();
            return scala.jdk.javaapi.DurationConverters$.MODULE$.toScala(toScala$extension_$this);
        }

        public int hashCode() {
            return this.scala$jdk$DurationConverters$JavaDurationOps$$duration().hashCode();
        }

        public boolean equals(Object x$1) {
            return DurationConverters$JavaDurationOps$.MODULE$.equals$extension(this.scala$jdk$DurationConverters$JavaDurationOps$$duration(), x$1);
        }

        public JavaDurationOps(Duration duration) {
            this.scala$jdk$DurationConverters$JavaDurationOps$$duration = duration;
        }
    }

    public static final class ScalaDurationOps {
        private final FiniteDuration scala$jdk$DurationConverters$ScalaDurationOps$$duration;

        public FiniteDuration scala$jdk$DurationConverters$ScalaDurationOps$$duration() {
            return this.scala$jdk$DurationConverters$ScalaDurationOps$$duration;
        }

        public Duration toJava() {
            FiniteDuration toJava$extension_$this = this.scala$jdk$DurationConverters$ScalaDurationOps$$duration();
            return scala.jdk.javaapi.DurationConverters$.MODULE$.toJava(toJava$extension_$this);
        }

        public int hashCode() {
            FiniteDuration hashCode$extension_$this = this.scala$jdk$DurationConverters$ScalaDurationOps$$duration();
            if (hashCode$extension_$this == null) {
                throw null;
            }
            return (int)hashCode$extension_$this.toNanos();
        }

        public boolean equals(Object x$1) {
            return DurationConverters$ScalaDurationOps$.MODULE$.equals$extension(this.scala$jdk$DurationConverters$ScalaDurationOps$$duration(), x$1);
        }

        public ScalaDurationOps(FiniteDuration duration) {
            this.scala$jdk$DurationConverters$ScalaDurationOps$$duration = duration;
        }
    }
}

