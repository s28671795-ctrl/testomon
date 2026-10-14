/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$ScalaDurationOps$;
import java.time.Duration;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}v!\u0002\u001a4\u0011\u0003Qd!\u0002\u001f4\u0011\u0003i\u0004\"\u0002#\u0002\t\u0003)ea\u0002\u001f\u0002!\u0003\r\nCR\u0004\b\u0003;\n\u0001\u0012QA\u000e\r\u001d\t)\"\u0001EA\u0003/Aa\u0001R\u0003\u0005\u0002\u0005e\u0001bB/\u0006\u0003\u0003%\tE\u0018\u0005\bO\u0016\t\t\u0011\"\u0001i\u0011!aW!!A\u0005\u0002\u0005u\u0001bB:\u0006\u0003\u0003%\t\u0005\u001e\u0005\tw\u0016\t\t\u0011\"\u0001\u0002\"!I\u00111A\u0003\u0002\u0002\u0013\u0005\u0013Q\u0001\u0005\n\u0003\u000f)\u0011\u0011!C!\u0003\u0013A\u0011\"a\u0003\u0006\u0003\u0003%I!!\u0004\b\r\u0005}\u0013\u0001#!]\r\u0015A\u0015\u0001#!J\u0011\u0015!\u0005\u0003\"\u0001\\\u0011\u001di\u0006#!A\u0005ByCqa\u001a\t\u0002\u0002\u0013\u0005\u0001\u000eC\u0004m!\u0005\u0005I\u0011A7\t\u000fM\u0004\u0012\u0011!C!i\"91\u0010EA\u0001\n\u0003a\b\"CA\u0002!\u0005\u0005I\u0011IA\u0003\u0011%\t9\u0001EA\u0001\n\u0003\nI\u0001C\u0005\u0002\fA\t\t\u0011\"\u0003\u0002\u000e\u00191\u0011QE\u0001\u0003\u0003OA!\"!\u000b\u001b\u0005\u000b\u0007I\u0011AA\u0016\u0011)\tiD\u0007B\u0001B\u0003%\u0011Q\u0006\u0005\u0007\tj!I!a\u0010\t\u000f\u0005\u0015#\u0004\"\u0001\u0002H!9\u0011Q\u000b\u000e\u0005B\u0005]\u0003bBA\u00025\u0011\u0005\u0013Q\u0001\u0005\b\u0003\u000fQB\u0011IA\u0005\u000f\u001d\t\t'\u0001E\u0001\u0003G2q!!\n\u0002\u0011\u0003\t)\u0007\u0003\u0004EG\u0011\u0005\u0011q\r\u0005\b\u0003S\u001aC\u0011AA6\u0011\u001d\t9h\tC\u0001\u0003sBq!a\u001e$\t\u0003\ti\b\u0003\u0005\u0002\u0002\u000e\"\taNAB\u0011%\t9j\tb\u0001\n\u0003\tI\n\u0003\u0005\u0002\u001c\u000e\u0002\u000b\u0011BA!\u0011%\tij\tb\u0001\n\u0003\tI\n\u0003\u0005\u0002 \u000e\u0002\u000b\u0011BA!\u000f\u001d\t\tk\tE\u0002\u0003G3q!a*$\u0011\u0003\tI\u000b\u0003\u0004E]\u0011\u0005\u0011\u0011\u0017\u0005\b\u0003gsC\u0011AA[\u0011%\tYALA\u0001\n\u0013\ti!A\u0006DC\u000eDW\rU8mS\u000eL(B\u0001\u001b6\u0003\r!gn\u001d\u0006\u0003m]\n!![8\u000b\u0003a\nA!Y6lC\u000e\u0001\u0001CA\u001e\u0002\u001b\u0005\u0019$aC\"bG\",\u0007k\u001c7jGf\u001c\"!\u0001 \u0011\u0005}\u0012U\"\u0001!\u000b\u0003\u0005\u000bQa]2bY\u0006L!a\u0011!\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!h\u0005\u0002\u0004}%\"1\u0001E\u0003\u001b\u0005\u001d1uN]3wKJ\u001cR\u0001\u0005 K\u0019>\u0003\"aS\u0002\u000e\u0003\u0005\u0001\"aP'\n\u00059\u0003%a\u0002)s_\u0012,8\r\u001e\t\u0003!bs!!\u0015,\u000f\u0005I+V\"A*\u000b\u0005QK\u0014A\u0002\u001fs_>$h(C\u0001B\u0013\t9\u0006)A\u0004qC\u000e\\\u0017mZ3\n\u0005eS&\u0001D*fe&\fG.\u001b>bE2,'BA,A)\u0005a\u0006CA&\u0011\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tq\f\u0005\u0002aK6\t\u0011M\u0003\u0002cG\u0006!A.\u00198h\u0015\u0005!\u0017\u0001\u00026bm\u0006L!AZ1\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005I\u0007CA k\u0013\tY\u0007IA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002ocB\u0011qh\\\u0005\u0003a\u0002\u00131!\u00118z\u0011\u001d\u0011H#!AA\u0002%\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A;\u0011\u0007YLh.D\u0001x\u0015\tA\b)\u0001\u0006d_2dWm\u0019;j_:L!A_<\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0004{\u0006\u0005\u0001CA \u007f\u0013\ty\bIA\u0004C_>dW-\u00198\t\u000fI4\u0012\u0011!a\u0001]\u0006A\u0001.Y:i\u0007>$W\rF\u0001j\u0003!!xn\u0015;sS:<G#A0\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005=\u0001c\u00011\u0002\u0012%\u0019\u00111C1\u0003\r=\u0013'.Z2u\u0005\u0015qUM^3s'\u0015)aH\u0013'P)\t\tY\u0002\u0005\u0002L\u000bQ\u0019a.a\b\t\u000fIL\u0011\u0011!a\u0001SR\u0019Q0a\t\t\u000fI\\\u0011\u0011!a\u0001]\n\u0019A\u000b\u001e7\u0014\u0007iq$*A\u0003wC2,X-\u0006\u0002\u0002.A!\u0011qFA\u001d\u001b\t\t\tD\u0003\u0003\u00024\u0005U\u0012\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0007\u0005]\u0002)\u0001\u0006d_:\u001cWO\u001d:f]RLA!a\u000f\u00022\tqa)\u001b8ji\u0016$UO]1uS>t\u0017A\u0002<bYV,\u0007\u0005\u0006\u0003\u0002B\u0005\r\u0003CA&\u001b\u0011\u001d\tI#\ba\u0001\u0003[\t\u0001bZ3u-\u0006dW/Z\u000b\u0003\u0003\u0013\u0002B!a\u0013\u0002R5\u0011\u0011Q\n\u0006\u0004\u0003\u001f\u001a\u0017\u0001\u0002;j[\u0016LA!a\u0015\u0002N\tAA)\u001e:bi&|g.\u0001\u0004fcV\fGn\u001d\u000b\u0004{\u0006e\u0003BBA.?\u0001\u0007a.A\u0003pi\",'/A\u0003OKZ,'/A\u0004G_J,g/\u001a:\u0002\u0007Q#H\u000e\u0005\u0002LGM\u00111E\u0010\u000b\u0003\u0003G\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002n\u0005M\u0004#B \u0002p\u00055\u0012bAA9\u0001\n1q\n\u001d;j_:Dq!!\u001e&\u0001\u0004\t\t%A\u0002ui2\fAB\u001a:p[B{7/\u001b;jm\u0016$B!!\u0011\u0002|!9\u0011\u0011\u0006\u0014A\u0002\u00055B\u0003BA!\u0003\u007fBq!!\u000b(\u0001\u0004\tI%A\u0003u_RcG\u000e\u0006\u0003\u0002B\u0005\u0015\u0005BBADQ\u0001\u0007!*\u0001\u0004q_2L7-\u001f\u0015\u0004Q\u0005-\u0005\u0003BAG\u0003'k!!a$\u000b\u0007\u0005Eu'\u0001\u0006b]:|G/\u0019;j_:LA!!&\u0002\u0010\nY\u0011J\u001c;fe:\fG.\u00119j\u0003\u0015qWM^3s+\t\t\t%\u0001\u0004oKZ,'\u000fI\u0001\u0013K\u001a4Wm\u0019;jm\u0016d\u0017PR8sKZ,'/A\nfM\u001a,7\r^5wK2Lhi\u001c:fm\u0016\u0014\b%\u0001\u0007Ui2L5o\u0014:eKJ,G\rE\u0002\u0002&:j\u0011a\t\u0002\r)Rd\u0017j](sI\u0016\u0014X\rZ\n\u0006]\u0005=\u00111\u0016\t\u0006!\u00065\u0016\u0011I\u0005\u0004\u0003_S&\u0001C(sI\u0016\u0014\u0018N\\4\u0015\u0005\u0005\r\u0016aB2p[B\f'/\u001a\u000b\u0006S\u0006]\u00161\u0018\u0005\b\u0003s\u0003\u0004\u0019AA!\u0003\u0005\t\u0007bBA_a\u0001\u0007\u0011\u0011I\u0001\u0002E\u0002")
public final class CachePolicy {

    public static interface CachePolicy {
    }

    public static final class Ttl
    implements CachePolicy {
        private final FiniteDuration value;

        public FiniteDuration value() {
            return this.value;
        }

        public Duration getValue() {
            return JavaDurationConverters$ScalaDurationOps$.MODULE$.asJava$extension(JavaDurationConverters$.MODULE$.ScalaDurationOps(this.value()));
        }

        public boolean equals(Object other) {
            Object object = other;
            if (object instanceof Ttl) {
                Ttl ttl = (Ttl)object;
                FiniteDuration finiteDuration = this.value();
                FiniteDuration finiteDuration2 = ttl.value();
                return !(finiteDuration != null ? !((Object)finiteDuration).equals(finiteDuration2) : finiteDuration2 != null);
            }
            return false;
        }

        public int hashCode() {
            return this.value().hashCode();
        }

        public String toString() {
            return new StringBuilder(5).append("Ttl(").append(this.value()).append(")").toString();
        }

        public Ttl(FiniteDuration value) {
            this.value = value;
            if (value.$less(Duration$.MODULE$.Zero())) {
                throw new IllegalArgumentException("TTL values must be a positive value (zero included).");
            }
        }
    }
}

