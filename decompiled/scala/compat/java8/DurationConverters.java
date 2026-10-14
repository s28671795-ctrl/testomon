/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import java.time.Duration;
import scala.compat.java8.DurationConverters$;
import scala.compat.java8.DurationConverters$DurationOps$;
import scala.compat.java8.DurationConverters$FiniteDurationops$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005eq!B\u0011#\u0011\u0003Ic!B\u0016#\u0011\u0003a\u0003\"B\u0019\u0002\t\u0003\u0011\u0004\"B\u001a\u0002\t\u000b!\u0004\"\u0002$\u0002\t\u000b9e\u0001B%\u0002\u0007)C\u0001\u0002O\u0003\u0003\u0006\u0004%\tA\u0014\u0005\t\u001f\u0016\u0011\t\u0011)A\u0005}!)\u0011'\u0002C\u0001!\")1'\u0002C\u0001)\"9Q+BA\u0001\n\u00032\u0006b\u0002.\u0006\u0003\u0003%\teW\u0004\bI\u0006\t\t\u0011#\u0001f\r\u001dI\u0015!!A\t\u0002\u0019DQ!M\u0007\u0005\u0002\u001dDQ\u0001[\u0007\u0005\u0006%Dq\u0001\\\u0007\u0002\u0002\u0013\u0015Q\u000eC\u0004p\u001b\u0005\u0005IQ\u00019\t\u000f\u0011\f\u0011\u0011!C\u0004i\u001a!a/A\u0002x\u0011!A4C!b\u0001\n\u0003!\u0006\u0002C(\u0014\u0005\u0003\u0005\u000b\u0011B\u001b\t\u000bE\u001aB\u0011\u0001=\t\u000b\u0019\u001bB\u0011\u0001(\t\u000fU\u001b\u0012\u0011!C!-\"9!lEA\u0001\n\u0003ZxaB?\u0002\u0003\u0003E\tA \u0004\bm\u0006\t\t\u0011#\u0001\u0000\u0011\u0019\t4\u0004\"\u0001\u0002\u0002!9\u00111A\u000e\u0005\u0006\u0005\u0015\u0001\u0002\u00037\u001c\u0003\u0003%)!!\u0003\t\u0011=\\\u0012\u0011!C\u0003\u0003\u001bA\u0001\"`\u0001\u0002\u0002\u0013\u001d\u0011QC\u0001\u0013\tV\u0014\u0018\r^5p]\u000e{gN^3si\u0016\u00148O\u0003\u0002$I\u0005)!.\u0019<bq)\u0011QEJ\u0001\u0007G>l\u0007/\u0019;\u000b\u0003\u001d\nQa]2bY\u0006\u001c\u0001\u0001\u0005\u0002+\u00035\t!E\u0001\nEkJ\fG/[8o\u0007>tg/\u001a:uKJ\u001c8CA\u0001.!\tqs&D\u0001'\u0013\t\u0001dE\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003%\nq\u0001^8TG\u0006d\u0017\r\u0006\u00026{A\u0011agO\u0007\u0002o)\u0011\u0001(O\u0001\tIV\u0014\u0018\r^5p]*\u0011!HJ\u0001\u000bG>t7-\u001e:sK:$\u0018B\u0001\u001f8\u000591\u0015N\\5uK\u0012+(/\u0019;j_:DQ\u0001O\u0002A\u0002y\u0002\"a\u0010#\u000e\u0003\u0001S!!\u0011\"\u0002\tQLW.\u001a\u0006\u0002\u0007\u0006!!.\u0019<b\u0013\t)\u0005I\u0001\u0005EkJ\fG/[8o\u0003\u0019!xNS1wCR\u0011a\b\u0013\u0005\u0006q\u0011\u0001\r!\u000e\u0002\f\tV\u0014\u0018\r^5p]>\u00038o\u0005\u0002\u0006\u0017B\u0011a\u0006T\u0005\u0003\u001b\u001a\u0012a!\u00118z-\u0006dW#\u0001 \u0002\u0013\u0011,(/\u0019;j_:\u0004CCA)T!\t\u0011V!D\u0001\u0002\u0011\u0015A\u0004\u00021\u0001?+\u0005)\u0014\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003]\u0003\"A\f-\n\u0005e3#aA%oi\u00061Q-];bYN$\"\u0001X0\u0011\u00059j\u0016B\u00010'\u0005\u001d\u0011un\u001c7fC:Dq\u0001Y\u0006\u0002\u0002\u0003\u0007\u0011-A\u0002yIE\u0002\"A\f2\n\u0005\r4#aA!os\u0006YA)\u001e:bi&|gn\u00149t!\t\u0011Vb\u0005\u0002\u000e[Q\tQ-A\tu_N\u001b\u0017\r\\1%Kb$XM\\:j_:$\"!\u000e6\t\u000b-|\u0001\u0019A)\u0002\u000b\u0011\"\b.[:\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0003-:DQa\u001b\tA\u0002E\u000b\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005E\u001cHC\u0001/s\u0011\u001d\u0001\u0017#!AA\u0002\u0005DQa[\tA\u0002E#\"!U;\t\u000ba\u0012\u0002\u0019\u0001 \u0003#\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]>\u00048o\u0005\u0002\u0014\u0017R\u0011\u0011P\u001f\t\u0003%NAQ\u0001\u000f\fA\u0002U\"\"\u0001\u0018?\t\u000f\u0001L\u0012\u0011!a\u0001C\u0006\tb)\u001b8ji\u0016$UO]1uS>tw\u000e]:\u0011\u0005I[2CA\u000e.)\u0005q\u0018\u0001\u0005;p\u0015\u00064\u0018\rJ3yi\u0016t7/[8o)\rq\u0014q\u0001\u0005\u0006Wv\u0001\r!\u001f\u000b\u0004-\u0006-\u0001\"B6\u001f\u0001\u0004IH\u0003BA\b\u0003'!2\u0001XA\t\u0011\u001d\u0001w$!AA\u0002\u0005DQa[\u0010A\u0002e$2!_A\f\u0011\u0015A\u0004\u00051\u00016\u0001")
public final class DurationConverters {
    public static FiniteDuration FiniteDurationops(FiniteDuration finiteDuration) {
        return DurationConverters$.MODULE$.FiniteDurationops(finiteDuration);
    }

    public static Duration DurationOps(Duration duration) {
        return DurationConverters$.MODULE$.DurationOps(duration);
    }

    public static Duration toJava(FiniteDuration finiteDuration) {
        return DurationConverters$.MODULE$.toJava(finiteDuration);
    }

    public static FiniteDuration toScala(Duration duration) {
        return DurationConverters$.MODULE$.toScala(duration);
    }

    public static final class DurationOps {
        private final Duration duration;

        public Duration duration() {
            return this.duration;
        }

        public FiniteDuration toScala() {
            return DurationConverters$DurationOps$.MODULE$.toScala$extension(this.duration());
        }

        public int hashCode() {
            return DurationConverters$DurationOps$.MODULE$.hashCode$extension(this.duration());
        }

        public boolean equals(Object x$1) {
            return DurationConverters$DurationOps$.MODULE$.equals$extension(this.duration(), x$1);
        }

        public DurationOps(Duration duration) {
            this.duration = duration;
        }
    }

    public static final class FiniteDurationops {
        private final FiniteDuration duration;

        public FiniteDuration duration() {
            return this.duration;
        }

        public Duration toJava() {
            return DurationConverters$FiniteDurationops$.MODULE$.toJava$extension(this.duration());
        }

        public int hashCode() {
            return DurationConverters$FiniteDurationops$.MODULE$.hashCode$extension(this.duration());
        }

        public boolean equals(Object x$1) {
            return DurationConverters$FiniteDurationops$.MODULE$.equals$extension(this.duration(), x$1);
        }

        public FiniteDurationops(FiniteDuration duration) {
            this.duration = duration;
        }
    }
}

