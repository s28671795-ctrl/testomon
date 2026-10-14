/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.concurrent.CompletionStage;
import scala.concurrent.Future;
import scala.jdk.FutureConverters$CompletionStageOps$;
import scala.jdk.FutureConverters$FutureOps$;
import scala.jdk.javaapi.FutureConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005et!B\u0010!\u0011\u0003)c!B\u0014!\u0011\u0003A\u0003\"B\u0017\u0002\t\u0003qc\u0001B\u0018\u0002\u0007ABA\"N\u0002\u0005\u0002\u0003\u0015)Q1A\u0005\nYB\u0011\u0002S\u0002\u0003\u0006\u0003\u0005\u000b\u0011B\u001c\t\u000b5\u001aA\u0011A%\t\u000b9\u001bA\u0011A(\t\u000fe\u001b\u0011\u0011!C!5\"9alAA\u0001\n\u0003zvaB3\u0002\u0003\u0003E\tA\u001a\u0004\b_\u0005\t\t\u0011#\u0001h\u0011\u0015i3\u0002\"\u0001i\u0011\u0015I7\u0002\"\u0002k\u0011\u001d\u00118\"!A\u0005\u0006MDq!_\u0006\u0002\u0002\u0013\u0015!\u0010\u0003\u0005f\u0003\u0005\u0005I1AA\u0003\r\u0019\t\u0019\"A\u0002\u0002\u0016!q\u0011\u0011D\t\u0005\u0002\u0003\u0015)Q1A\u0005\n\u0005m\u0001bCA\u0012#\t\u0015\t\u0011)A\u0005\u0003;Aa!L\t\u0005\u0002\u0005\u0015\u0002bBA\u0017#\u0011\u0005\u0011q\u0006\u0005\b3F\t\t\u0011\"\u0011[\u0011!q\u0016#!A\u0005B\u0005Mr!CA\u001c\u0003\u0005\u0005\t\u0012AA\u001d\r%\t\u0019\"AA\u0001\u0012\u0003\tY\u0004\u0003\u0004.3\u0011\u0005\u0011Q\b\u0005\b\u0003\u007fIBQAA!\u0011!\u0011\u0018$!A\u0005\u0006\u0005=\u0003\u0002C=\u001a\u0003\u0003%)!a\u0017\t\u0013\u0005]\u0012!!A\u0005\u0004\u0005-\u0014\u0001\u0005$viV\u0014XmQ8om\u0016\u0014H/\u001a:t\u0015\t\t#%A\u0002kI.T\u0011aI\u0001\u0006g\u000e\fG.Y\u0002\u0001!\t1\u0013!D\u0001!\u0005A1U\u000f^;sK\u000e{gN^3si\u0016\u00148o\u0005\u0002\u0002SA\u0011!fK\u0007\u0002E%\u0011AF\t\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005)#!\u0003$viV\u0014Xm\u00149t+\t\tth\u0005\u0002\u0004eA\u0011!fM\u0005\u0003i\t\u0012a!\u00118z-\u0006d\u0017aJ:dC2\fGE\u001b3lI\u0019+H/\u001e:f\u0007>tg/\u001a:uKJ\u001cHER;ukJ,w\n]:%I\u0019,\u0012a\u000e\t\u0004qmjT\"A\u001d\u000b\u0005i\u0012\u0013AC2p]\u000e,(O]3oi&\u0011A(\u000f\u0002\u0007\rV$XO]3\u0011\u0005yzD\u0002\u0001\u0003\u0006\u0001\u000e\u0011\r!\u0011\u0002\u0002)F\u0011!)\u0012\t\u0003U\rK!\u0001\u0012\u0012\u0003\u000f9{G\u000f[5oOB\u0011!FR\u0005\u0003\u000f\n\u00121!\u00118z\u0003!\u001a8-\u00197bI)$7\u000e\n$viV\u0014XmQ8om\u0016\u0014H/\u001a:tI\u0019+H/\u001e:f\u001fB\u001cH\u0005\n4!)\tQE\nE\u0002L\u0007uj\u0011!\u0001\u0005\u0006\u001b\u001a\u0001\raN\u0001\u0002M\u00061\u0011m\u001d&bm\u0006,\u0012\u0001\u0015\t\u0004#^kT\"\u0001*\u000b\u0005i\u001a&B\u0001+V\u0003\u0011)H/\u001b7\u000b\u0003Y\u000bAA[1wC&\u0011\u0001L\u0015\u0002\u0010\u0007>l\u0007\u000f\\3uS>t7\u000b^1hK\u0006A\u0001.Y:i\u0007>$W\rF\u0001\\!\tQC,\u0003\u0002^E\t\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\t\u00017\r\u0005\u0002+C&\u0011!M\t\u0002\b\u0005>|G.Z1o\u0011\u001d!\u0017\"!AA\u0002\u0015\u000b1\u0001\u001f\u00132\u0003%1U\u000f^;sK>\u00038\u000f\u0005\u0002L\u0017M\u00111\"\u000b\u000b\u0002M\u0006\u0001\u0012m\u001d&bm\u0006$S\r\u001f;f]NLwN\\\u000b\u0003W:$\"\u0001\\8\u0011\u0007E;V\u000e\u0005\u0002?]\u0012)\u0001)\u0004b\u0001\u0003\")\u0001/\u0004a\u0001c\u0006)A\u0005\u001e5jgB\u00191jA7\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\\\u000b\u0003ib$\"AW;\t\u000bAt\u0001\u0019\u0001<\u0011\u0007-\u001bq\u000f\u0005\u0002?q\u0012)\u0001I\u0004b\u0001\u0003\u0006\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0004w\u0006\rAC\u0001?\u007f)\t\u0001W\u0010C\u0004e\u001f\u0005\u0005\t\u0019A#\t\u000bA|\u0001\u0019A@\u0011\t-\u001b\u0011\u0011\u0001\t\u0004}\u0005\rA!\u0002!\u0010\u0005\u0004\tU\u0003BA\u0004\u0003\u001b!B!!\u0003\u0002\u0010A!1jAA\u0006!\rq\u0014Q\u0002\u0003\u0006\u0001B\u0011\r!\u0011\u0005\u0007\u001bB\u0001\r!!\u0005\u0011\taZ\u00141\u0002\u0002\u0013\u0007>l\u0007\u000f\\3uS>t7\u000b^1hK>\u00038/\u0006\u0003\u0002\u0018\u0005\u00052CA\t3\u0003E\u001a8-\u00197bI)$7\u000e\n$viV\u0014XmQ8om\u0016\u0014H/\u001a:tI\r{W\u000e\u001d7fi&|gn\u0015;bO\u0016|\u0005o\u001d\u0013%GN,\"!!\b\u0011\tE;\u0016q\u0004\t\u0004}\u0005\u0005B!\u0002!\u0012\u0005\u0004\t\u0015AM:dC2\fGE\u001b3lI\u0019+H/\u001e:f\u0007>tg/\u001a:uKJ\u001cHeQ8na2,G/[8o'R\fw-Z(qg\u0012\"3m\u001d\u0011\u0015\t\u0005\u001d\u0012\u0011\u0006\t\u0005\u0017F\ty\u0002C\u0004\u0002,Q\u0001\r!!\b\u0002\u0005\r\u001c\u0018aB1t'\u000e\fG.Y\u000b\u0003\u0003c\u0001B\u0001O\u001e\u0002 Q\u0019\u0001-!\u000e\t\u000f\u0011<\u0012\u0011!a\u0001\u000b\u0006\u00112i\\7qY\u0016$\u0018n\u001c8Ti\u0006<Wm\u00149t!\tY\u0015d\u0005\u0002\u001aSQ\u0011\u0011\u0011H\u0001\u0012CN\u001c6-\u00197bI\u0015DH/\u001a8tS>tW\u0003BA\"\u0003\u0013\"B!!\u0012\u0002LA!\u0001hOA$!\rq\u0014\u0011\n\u0003\u0006\u0001n\u0011\r!\u0011\u0005\u0007an\u0001\r!!\u0014\u0011\t-\u000b\u0012qI\u000b\u0005\u0003#\nI\u0006F\u0002[\u0003'Ba\u0001\u001d\u000fA\u0002\u0005U\u0003\u0003B&\u0012\u0003/\u00022APA-\t\u0015\u0001ED1\u0001B+\u0011\ti&!\u001b\u0015\t\u0005}\u00131\r\u000b\u0004A\u0006\u0005\u0004b\u00023\u001e\u0003\u0003\u0005\r!\u0012\u0005\u0007av\u0001\r!!\u001a\u0011\t-\u000b\u0012q\r\t\u0004}\u0005%D!\u0002!\u001e\u0005\u0004\tU\u0003BA7\u0003g\"B!a\u001c\u0002vA!1*EA9!\rq\u00141\u000f\u0003\u0006\u0001z\u0011\r!\u0011\u0005\b\u0003Wq\u0002\u0019AA<!\u0011\tv+!\u001d")
public final class FutureConverters {
    public static CompletionStage CompletionStageOps(CompletionStage cs) {
        return scala.jdk.FutureConverters$.MODULE$.CompletionStageOps(cs);
    }

    public static Future FutureOps(Future f) {
        return scala.jdk.FutureConverters$.MODULE$.FutureOps(f);
    }

    public static final class CompletionStageOps<T> {
        private final CompletionStage<T> scala$jdk$FutureConverters$CompletionStageOps$$cs;

        public CompletionStage<T> scala$jdk$FutureConverters$CompletionStageOps$$cs() {
            return this.scala$jdk$FutureConverters$CompletionStageOps$$cs;
        }

        public Future<T> asScala() {
            CompletionStage<T> asScala$extension_$this = this.scala$jdk$FutureConverters$CompletionStageOps$$cs();
            return FutureConverters$.MODULE$.asScala(asScala$extension_$this);
        }

        public int hashCode() {
            return this.scala$jdk$FutureConverters$CompletionStageOps$$cs().hashCode();
        }

        public boolean equals(Object x$1) {
            return FutureConverters$CompletionStageOps$.MODULE$.equals$extension(this.scala$jdk$FutureConverters$CompletionStageOps$$cs(), x$1);
        }

        public CompletionStageOps(CompletionStage<T> cs) {
            this.scala$jdk$FutureConverters$CompletionStageOps$$cs = cs;
        }
    }

    public static final class FutureOps<T> {
        private final Future<T> scala$jdk$FutureConverters$FutureOps$$f;

        public Future<T> scala$jdk$FutureConverters$FutureOps$$f() {
            return this.scala$jdk$FutureConverters$FutureOps$$f;
        }

        public CompletionStage<T> asJava() {
            Future<T> asJava$extension_$this = this.scala$jdk$FutureConverters$FutureOps$$f();
            return FutureConverters$.MODULE$.asJava(asJava$extension_$this);
        }

        public int hashCode() {
            return this.scala$jdk$FutureConverters$FutureOps$$f().hashCode();
        }

        public boolean equals(Object x$1) {
            return FutureConverters$FutureOps$.MODULE$.equals$extension(this.scala$jdk$FutureConverters$FutureOps$$f(), x$1);
        }

        public FutureOps(Future<T> f) {
            this.scala$jdk$FutureConverters$FutureOps$$f = f;
        }
    }
}

